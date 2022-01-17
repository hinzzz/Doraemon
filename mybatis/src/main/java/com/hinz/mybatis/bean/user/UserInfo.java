package com.hinz.mybatis.bean.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hinz.mybatis.handler.ArrayTypeHandler;
import lombok.*;

/**
 * @author hinzzz
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName(autoResultMap = true)
public class UserInfo implements Serializable {
    private Long id;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String passwordSalt;

    /**
     * 手机号
     */
    private String mobileNo;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最后更新时间
     */
    private LocalDateTime lastUpdateTime;

    @TableField(exist = false)
    private List<UserAddress> userAddressList;

    private static final long serialVersionUID = 1L;
}
