package com.hinz.controller;

import com.hinz.bean.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ：quanhz
 * @date ：Created in 2021/1/28 10:08
 * @Description : No Description
 */
@RestController
@RequestMapping("/date")
public class DateFormatController {

    @GetMapping
    public User getUser(){
        return User.builder().id(1L).userName("hinzzz").birthDay(new Date()).build();
    }
}
