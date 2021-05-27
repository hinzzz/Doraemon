package com.hinz.mybatis.exception;

/**
 * 抽象接口
 *
 * @author hinzzz
 * @Date 2021/05/27 下午10:33
 */
public interface ServiceExceptionEnum {
    
    /**
     * 请求是否成功
     */
    Boolean getIsSuccess();
    
    /**
     * 获取返回的code
     */
    Integer getResponseCode();
    
    /**
     * 获取返回的message
     */
    String getResponseMsg();
}
