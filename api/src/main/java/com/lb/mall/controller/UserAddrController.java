package com.lb.mall.controller;

import com.lb.mall.service.UserAddrService;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "提供收货地址相关接⼝",tags = "收货地址管理")
@RequestMapping("/useraddr")
public class UserAddrController {

      @Autowired
      private UserAddrService userAddrService;

      @GetMapping("/list")
      @ApiImplicitParam(dataType = "int",name = "userId", value = "⽤户ID",required = true)
      public ResultVo listAddr(Integer userId, @RequestHeader("token") String token){
           ResultVo resultVO = userAddrService.listAddrsByUid(userId);
           return resultVO;
      }
}