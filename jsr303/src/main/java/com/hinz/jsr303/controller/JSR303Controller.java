package com.hinz.jsr303.controller;

import com.hinz.jsr303.bean.AddressInfo;
import com.hinz.jsr303.common.R;
import com.hinz.jsr303.bean.UserInfo;
import com.hinz.jsr303.valid.ValidatorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

/**
 * @author ：quanhz
 * @date ：Created in 2021/2/19 11:17
 * @Description : No Description
 */
@RestController
@RequestMapping("/valid")
@Slf4j
public class JSR303Controller {

    @Resource
    private MethodValidationPostProcessor methodValidationPostProcessor;


    @RequestMapping("addWithOutGroup")
    public R addWithOutGroup( @Validated @RequestBody UserInfo userInfo ){
        log.info(userInfo.toString());
        return R.ok();
    }

    @RequestMapping("add")
    public R add( @Validated({ValidatorGroup.Add.class}) @RequestBody UserInfo userInfo){
        log.info(userInfo.toString());
        return R.ok();
    }

    @RequestMapping("update")
    public R update(@RequestBody @Validated({ValidatorGroup.Edit.class}) UserInfo userInfo){
        log.info(userInfo.toString());
        return R.ok();
    }

    @RequestMapping("nest")
    public R nest(@RequestBody @Validated UserInfo userInfo){
        log.info(userInfo.toString());
        return R.ok();
    }


    @RequestMapping("testAddr")
    public R testAddr(@Validated @RequestBody AddressInfo addressInfo){
        log.info("testAddr : "+addressInfo.toString());
        return R.ok();
    }

    @RequestMapping("testSingleParam")
    public R testSingleParam(@Validated @NotBlank(message = "name不能为空") String name, @Validated  @NotBlank(message = "id不能为空") String id){
        log.info("name : "+name);
        return R.ok();
    }

    @RequestMapping("testReq")
    public R testSingleParam(@RequestParam String name){
        log.info("name : "+name);
        return R.ok();
    }

    @RequestMapping("saveUser")
    public R testSingleParam(@RequestBody UserInfo userInfo){
        log.info("name : "+userInfo);
        return R.ok();
    }

}
