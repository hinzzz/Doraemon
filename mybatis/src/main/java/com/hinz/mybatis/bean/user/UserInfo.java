package com.hinz.mybatis.bean.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author
 *
 */
@Data
public class UserInfo implements Serializable {
    private Long id;

    /**
     * 登录账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nick_name;

    /**
     * 邀请码
     */
    private String invite_code;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String password_salt;

    /**
     * 手机号
     */
    private String mobile_no;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private LocalDateTime create_time;

    /**
     * 最后更新时间
     */
    private LocalDateTime last_update_time;

    private static final long serialVersionUID = 1L;
}
