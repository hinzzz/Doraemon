package com.hinz.jsr303.valid;

public interface ValidatorGroup {
    /**
     * 插入验证分组
     */
    interface Insert {
    }
 
    /**
     * 查询验证分组
     */
    interface Query {
    }
 
    /**
     * 删除验证分组
     */
    interface Delete {
    }
 
    /**
     * 更新验证分组
     */
    interface Update {
    }
}