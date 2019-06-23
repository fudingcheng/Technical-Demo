package cn.itcast.controller;


import cn.itcast.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RolesAllowed;

@RequestMapping("/user")
@Controller
public class UserController {


    @Autowired
    private IUserService userService;

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(){
        userService.add();
        return "add success";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public String findAll(){
        userService.findAll();
        return "findAdd success";
    }

    @RequestMapping("/findByName")
    @ResponseBody
    public String findByName(String name){
        userService.findByName(name);
        return "findByName success";
    }

}
