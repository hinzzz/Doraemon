package com.hinz.mybatis.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hinz.mybatis.bean.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ：hinzzz
 * @date ：Created in 2021/2/7 14:21
 * @Description : No Description
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 获取用户地址
     * @param userId
     * @return
     */
    UserInfo getUserInfoAddress(Long userId);

    /**
     * 分段查询用户地址
     * @param paramAccount
     * @return
     */
    List<UserInfo> getUserInfoAddress2(String paramAccount);
}
