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

    @Email
    private String description;
}
