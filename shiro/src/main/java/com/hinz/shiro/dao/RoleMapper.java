package com.hinz.shiro.dao;

import com.hinz.shiro.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2020/1/6 10:49
 */
@Mapper
public interface RoleMapper {

    @Select(" SELECT " +
            "  r.id,r.name,r.description " +
            "        FROM " +
            "          shiro_user_role ur" +
            "        LEFT JOIN shiro_user u ON ur.user_id = u.id " +
            "        LEFT JOIN shiro_role r ON ur.role_id = r.id " +
            "        WHERE u.username=#{username} ")
    @ResultType(List.class)
    List<Role> findRoleByUsername(@Param("username") String username);
}
