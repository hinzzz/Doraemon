package com.hinz.bean;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/28 10:05
 * @Description : No Description
 */
@Data
@Builder
public class User {
    private Long id;
    private String userName;
    private Date birthDay;
}
