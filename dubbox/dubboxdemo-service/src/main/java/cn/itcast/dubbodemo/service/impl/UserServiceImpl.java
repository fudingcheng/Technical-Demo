package cn.itcast.dubbodemo.service.impl;


import cn.itcast.dubbodemo.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;

@com.alibaba.dubbo.config.annotation.Service
@org.springframework.stereotype.Service
public class UserServiceImpl implements UserService {

    @Override
    public String getName() {
        return "Hello Dobbox";
    }
}
