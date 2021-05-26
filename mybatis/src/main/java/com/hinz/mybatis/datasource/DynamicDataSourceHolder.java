package com.hinz.mybatis.datasource;

/**
 * 动态数据源持有者
 *
 * @author hinzzz
 */
public final class DynamicDataSourceHolder {

    /**
     * 动态数据源存储
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<DynamicDataSourceGlobal> DYNAMIC_DATA_SOURCE_GLOBAL_THREAD_LOCAL = new ThreadLocal<>();

    private DynamicDataSourceHolder() {
        //
    }

    /**
     * 存放数据源
     *
     * @param dataSource 数据源
     */
    public static void putDataSource(DynamicDataSourceGlobal dataSource) {
        DYNAMIC_DATA_SOURCE_GLOBAL_THREAD_LOCAL.set(dataSource);
    }

    /**
     * 获取数据源
     *
     * @return the data source
     */
    public static DynamicDataSourceGlobal getDataSource() {
        return DYNAMIC_DATA_SOURCE_GLOBAL_THREAD_LOCAL.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        DYNAMIC_DATA_SOURCE_GLOBAL_THREAD_LOCAL.remove();
    }

}