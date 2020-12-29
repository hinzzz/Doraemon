package com.hinz.shiro.controller;

import com.github.pagehelper.PageInfo;
import com.hinz.shiro.entity.User;
import com.hinz.shiro.service.IUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @RequestMapping("/getUser/{username}")
    @ResponseBody
    public User getUser(@PathVariable("username") String username) {
        LOGGER.info("username = {}", username);
        User user = userService.findUserByUserName(username);
        return user;
    }

    @RequestMapping("/userList")
    @RequiresPermissions("user:view")//权限管理;
    public String userList(@RequestParam(required = false,defaultValue = "0") int pageNum, @RequestParam(required = false,defaultValue = "10")int pageSize, Model model) {
        LOGGER.info("pageNum = {}, pageSize = {}", pageNum, pageSize);
        PageInfo pageInfo = userService.listUser(pageNum, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "user/userList";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("user:add")//权限管理;
    public String userAdd(){
        return "user/userAdd";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("user:del")//权限管理;
    public String userDel(){
        return "user/userDel";
    }
}
