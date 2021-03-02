package com.hinz.jsr303.controller;

import com.hinz.jsr303.bean.AddressInfo;
import com.hinz.jsr303.common.R;
import com.hinz.jsr303.bean.UserInfo;
import com.hinz.jsr303.valid.AddGroup;
import com.hinz.jsr303.valid.UpdateGroup;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/19 11:17
 * @Description : No Description
 */
@RestController
@RequestMapping("/valid")
@Slf4j
@Validated
public class JSR303Controller {

    @Resource
    private MethodValidationPostProcessor methodValidationPostProcessor;

    @RequestMapping("add")
    public R add( @Validated({AddGroup.class}) @RequestBody UserInfo userInfo){
        log.info(userInfo.toString());
        return R.ok();
    }

    @RequestMapping("update")
    public R update(@RequestBody @Validated({UpdateGroup.class}) UserInfo userInfo){
        log.info(userInfo.toString());
        return R.ok();
    }

    @RequestMapping("test")
    public R test(@RequestBody @Validated UserInfo userInfo){
        log.info(userInfo.toString());
        return R.ok();
    }


    @RequestMapping("testAddr")
    public R testAddr(@Validated @RequestBody AddressInfo addressInfo){
        log.info("testAddr : "+addressInfo.toString());
        return R.ok();
    }

    @RequestMapping("testSingleParam")
    public R testSingleParam(@Length(min = 1) String name){
        log.info("name : "+name);
        return R.ok();
    }
}