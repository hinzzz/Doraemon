package com.hinz.shiro.dao;

import com.hinz.shiro.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2020/1/6 9:50
 */
@Mapper
public interface UserMapper {


    @Select("select * from shiro_user where username = #{userName}")
    @ResultType(User.class)
    User findUserByUserName(@Param("userName") String userName);

    @Select("select * from shiro_user")
    @ResultType(User.class)
    List<User> listUser();
}
