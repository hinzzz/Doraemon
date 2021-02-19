package com.hinz.mybatis.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hinz.mybatis.bean.user.UserInfo;
import com.hinz.mybatis.mapper.user.UserInfoMapper;
import com.hinz.mybatis.service.user.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/5 11:28
 * @Description : No Description
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
