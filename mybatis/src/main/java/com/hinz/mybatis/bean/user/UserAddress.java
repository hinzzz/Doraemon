package com.hinz.mybatis.bean.user;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author
 *
 */
@Data
public class UserAddress implements Serializable {
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 是否默认地址1-是，0-否
     */
    private Byte defaultFlag;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 联系电话
     */
    private String mobileNo;

    /**
     * 省
     */
    private String province;

    /**
     * 是
     */
    private String city;

    /**
     * 区/县
     */
    private String county;

    /**
     * 详细地址（具体街道门牌）
     */
    private String detailedAddress;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private LocalDateTime lastUpdateTime;

    private static final long serialVersionUID = 1L;
}
