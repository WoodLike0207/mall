package com.lb.mall.controller;

import com.lb.mall.entity.Users;
import com.lb.mall.service.UserService;
import com.lb.mall.vo.BasicResultVO;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation("用户登录")
    @GetMapping("/login")
    public ResultVo login(String username, String password){
        return userService.checkLogin(username, password);
    }

    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public ResultVo regist(@RequestBody Users user){
        return userService.userRegist(user.getUsername(),user.getPassword());
    }

    @ApiOperation("校验token是否过期")
    @GetMapping("/check")
    public ResultVo userTokenCheck(@RequestHeader("token") String token){
        return new ResultVo(RespStatus.OK,"success",null);
    }
}
