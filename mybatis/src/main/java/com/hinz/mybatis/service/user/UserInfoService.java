package com.hinz.mybatis.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hinz.mybatis.bean.user.UserInfo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/5 11:27
 * @Description : No Description
 */
public interface UserInfoService extends IService<UserInfo> {

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

    void testTransaction();

    UserInfo testHandler(long l);
}
