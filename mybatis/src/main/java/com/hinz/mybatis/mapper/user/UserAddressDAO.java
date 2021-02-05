package com.hinz.mybatis.mapper.user;

import com.hinz.mybatis.bean.user.UserAddress;
import com.hinz.mybatis.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * UserAddressDAO继承基类
 */
@Mapper
@Repository
public interface UserAddressDAO extends MyBatisBaseDao<UserAddress, Long> {
}
