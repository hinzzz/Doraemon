package com.hinz.mybatis.mapper.user;

import com.hinz.mybatis.bean.user.UserInfo;
import com.hinz.mybatis.mapper.MyBatisBaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * UserInfoDAO继承基类
 */
@Mapper
@Repository
public interface UserInfoDAO extends MyBatisBaseDao<UserInfo, Long> {
}
