package cn.itcast.dubbodemo.controller;

import cn.itcast.dubbodemo.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by fudingcheng on 2018-11-06.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @com.alibaba.dubbo.config.annotation.Reference
    @org.springframework.beans.factory.annotation.Autowired
    private UserService userService;

    @RequestMapping("/showName")
    @ResponseBody
    public String showName() {
        return userService.getName();
    }
}
