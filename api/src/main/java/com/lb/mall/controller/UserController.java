package com.lb.mall.controller;

import com.lb.mall.service.UserService;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户登录")
    @GetMapping("/login")
    public ResultVo login(String name,String password){
        return userService.checkLogin(name,password);
    }

    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public ResultVo regist(String name,String password){
        return userService.userRegist(name,password);
    }
}
