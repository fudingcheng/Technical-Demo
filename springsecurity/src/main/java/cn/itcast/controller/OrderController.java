package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/order")
@Controller
public class OrderController {


    @ResponseBody
    @RequestMapping("/add")
    public String addOrder(){
        System.out.println("添加订单成功");
        return "add success";
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public String findAll(){
        System.out.println("查询所有成功");
        return "findAdd success";
    }

}
