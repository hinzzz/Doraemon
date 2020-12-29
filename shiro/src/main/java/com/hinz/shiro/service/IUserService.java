package com.hinz.shiro.service;

import com.github.pagehelper.PageInfo;
import com.hinz.shiro.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author ：quanhz
 * @date ：Created in 2020/1/6 9:58
 */
public interface IUserService {
    User findUserByUserName(@Param("userName") String userName);
    /**
     *  分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo listUser(int pageNum, int pageSize);
}
