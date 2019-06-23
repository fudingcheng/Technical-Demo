package cn.itcast.service.impl;

import cn.itcast.service.IUserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

@Service
public class UserServiceImpl implements IUserService {


    @Override
    public void findAll() {
        System.out.println("UserServiceImpl...findAll...");
    }

    @RolesAllowed({"ROLE_ADDUSER"})
    //@Secured("ROLE_ADDUSER")
    @Override
    public void add() {
        System.out.println("UserServiceImpl...add...");
    }

    @PreAuthorize("'zs'.equals(authentication.principal.username)")
    @Override
    public void findByName(String name) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.getUsername();
        System.out.println("访问者："+name);
    }
}
