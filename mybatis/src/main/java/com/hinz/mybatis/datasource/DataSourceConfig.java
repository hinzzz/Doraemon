package com.hinz.mybatis.datasource;



import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.hinz.mybatis.exception.MyBatisException;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@MapperScan("com.hinz.mybatis.mapper.*")
//@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig {
    /**
     * Write data source druid data source.
     *
     * @return the druid data source
     */
    @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties("spring.datasource.write")
    public DruidDataSource writeDataSource() {
        return new DruidDataSource();
    }

    /**
     * Read data source druid data source.
     *
     * @return the druid data source
     */
    @Bean(name = "readDataSource")
    @ConfigurationProperties("spring.datasource.read")
    public DruidDataSource readDataSource() {
        return new DruidDataSource();
    }

    /**
     * Dynamic data source data source.
     *
     * @return the data source
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setWriteDataSource(writeDataSource());
        dynamicDataSource.setReadDataSource(readDataSource());

        return dynamicDataSource;
    }

    /**
     * Dynamic transaction manager data source transaction manager.
     *
     * @param dynamicDataSource the dynamic data source
     * @return the data source transaction manager
     */
    @Bean(name = "dynamicTransactionManager")
    public DataSourceTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DynamicDataSourceTransactionManager(dynamicDataSource);
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor()
    {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // interceptor.addInnerInterceptor(tenantLineInnerInterceptor());
        // 如果用了分页插件注意先 add TenantLineInnerInterceptor 再 add
        // PaginationInnerInterceptor
        // 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
        // 分页插件
        // interceptor.addInnerInterceptor(paginationInnerInterceptor());
        // 乐观锁插件
        //interceptor.addInnerInterceptor(optimisticLockerInnerInterceptor());
        // 阻断插件
        //interceptor.addInnerInterceptor(blockAttackInnerInterceptor());
        //动态数据源
        return interceptor;
    }

    public DynamicPlugin dynamicPlugin(){
        return new DynamicPlugin();
    }


    /**
     * 分页插件，自动识别数据库类型
     * https://baomidou.com/guide/interceptor-pagination.html
     */
    public PaginationInnerInterceptor paginationInnerInterceptor()
    {
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 设置数据库类型为mysql
        paginationInnerInterceptor.setDbType(DbType.MYSQL);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInnerInterceptor.setMaxLimit(-1L);
        return paginationInnerInterceptor;
    }
    /**
     * 如果是对全表的删除或更新操作，就会终止该操作
     * https://baomidou.com/guide/interceptor-block-attack.html
     */
    public BlockAttackInnerInterceptor blockAttackInnerInterceptor()
    {
        return new BlockAttackInnerInterceptor();
    }

    /**
     * 乐观锁插件
     * https://baomidou.com/guide/interceptor-optimistic-locker.html
     */
    public OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor()
    {
        return new OptimisticLockerInnerInterceptor();
    }


    /**
     * Dynamic sql session factory sql session factory.
     *
     * @param dynamicDataSource the dynamic data source
     * @param properties        the properties
     * @return the sql session factory
     */
    @Bean
    @ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
    public SqlSessionFactory dynamicSqlSessionFactory(
            @Qualifier("dynamicDataSource") DataSource dynamicDataSource,
            MybatisPlusProperties properties) {
        final MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(properties.getConfigLocation()));
        sessionFactory.setMapperLocations(properties.resolveMapperLocations());
        Interceptor[] interceptors = {dynamicPlugin()};
        sessionFactory.setPlugins(interceptors);
        try {
            return sessionFactory.getObject();
        } catch (Exception e) {
            throw new MyBatisException("SqlSessionFactory build failed:"+ e.getMessage());
        }
    }

}
