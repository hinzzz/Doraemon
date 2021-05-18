package com.hinz.jsr303.bean;

import lombok.Data;

import javax.validation.constraints.Email;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/19 15:08
 * @Description : No Description
 */
@Data
public class AddressInfo {

    private Long id;

    @Email(message = "嵌套参数：email不合法")
    private String email;

    private String detail;
}
