package com.hinz.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hinz.shiro.dao.UserMapper;
import com.hinz.shiro.entity.User;
import com.hinz.shiro.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2020/1/6 9:58
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User findUserByUserName(String userName) {
        return userMapper.findUserByUserName(userName);
    }

    @Override
    public PageInfo listUser(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.listUser();     // 调用UserMapper接口的代理，实现查询
        PageInfo pageInfo = new PageInfo(users);      // 将结果集封装进PageInfo，已经在分页之后
        return pageInfo;
    }

}
