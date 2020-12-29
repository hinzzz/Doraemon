package com.hinz.shiro.dao;

import com.hinz.shiro.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.Arrays;
import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2020/1/6 10:50
 */
@Mapper
public interface PermissionMapper {
    @Select(" select * from shiro_role_permission srp " +
            " left join shiro_role sr on sr.id = srp.role_id " +
            " left join shiro_permission sp on sp.id = srp.permission_id " +
            " where srp.role_id = #{roleId}")
    @ResultType(List.class)
    List<Permission> findPermissionByRoleId(@Param("roleId") Long roleId);
}
