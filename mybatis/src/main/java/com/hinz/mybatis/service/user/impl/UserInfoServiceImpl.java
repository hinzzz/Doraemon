package com.hinz.mybatis.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.mybatis.bean.user.UserInfo;
import com.hinz.mybatis.mapper.user.UserInfoMapper;
import com.hinz.mybatis.service.user.UserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/5 11:28
 * @Description : No Description
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoAddress(Long userId) {
        return userInfoMapper.getUserInfoAddress(userId);
    }

    @Override
    public List<UserInfo> getUserInfoAddress2(String paramAccount) {
        return userInfoMapper.getUserInfoAddress2(paramAccount);
    }

    @Override
    @Transactional
    public void testTransaction() {
        //System.out.println("TransactionAspectSupport.currentTransactionStatus() = " + TransactionAspectSupport.currentTransactionStatus());
        UserInfo other = UserInfo.builder().account("other").build();
        UserInfo hinz = UserInfo.builder().account("hinzzz").build();
        userInfoMapper.insert(other);
        userInfoMapper.insert(hinz);
    }
}
