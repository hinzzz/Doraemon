package com.hinz.mybatis.datasource;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态数据源Mybatis拦截器插件
 * query拦截方法有两个 一个是四个参数一个是六个参数 因为拦截器链条，和拦截器顺序的原因，在这行到当前 DynamicPlugin拦截器的时候可能调用了六个参数的方法，
 * 避免放生问题 四个参数和六个参数都做了拦截 保证走到当前拦截器
 *
 * @author hinzzz
 */
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {
        MappedStatement.class, Object.class}),
    @Signature(type = Executor.class, method = "query", args = {
            MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
    @Signature(type = Executor.class, method = "query", args = {
            MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})})
@Slf4j
public class DynamicPlugin implements Interceptor {

    /**
     * 拦截SQL表达式
     */
    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
     * 动态数据源缓存
     */
    private static final Map<String, DynamicDataSourceGlobal> CACHE_MAP = new ConcurrentHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
        //如果没有事务
        if (!synchronizationActive) {
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];

            DynamicDataSourceGlobal dynamicDataSourceGlobal;

            if ((dynamicDataSourceGlobal = CACHE_MAP.get(ms.getId())) == null) {
                dynamicDataSourceGlobal = getDynamicDataSource(ms, objects[1]);
                log.debug("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]..", ms.getId(), dynamicDataSourceGlobal.name(), ms.getSqlCommandType().name());
                CACHE_MAP.put(ms.getId(), dynamicDataSourceGlobal);
            }
            DynamicDataSourceHolder.putDataSource(dynamicDataSourceGlobal);
        }

        return invocation.proceed();
    }

    /**
     * 获得动态数据源
     *
     * @param ms              MappedStatement
     * @param parameterObject Parameter
     * @return DynamicDataSourceGlobal
     */
    private DynamicDataSourceGlobal getDynamicDataSource(MappedStatement ms, Object parameterObject) {
        DynamicDataSourceGlobal dynamicDataSourceGlobal;
        //读方法
        if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
            //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
            if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
            } else {
                BoundSql boundSql = ms.getSqlSource().getBoundSql(parameterObject);
                String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                if (sql.matches(REGEX)) {
                    dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
                } else {
                    dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
                }
            }
        } else {
            dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
        }
        return dynamicDataSourceGlobal;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
        //
    }
}