package com.lb.mall.controller;

import com.lb.mall.entity.ShoppingCart;
import com.lb.mall.service.ShoppingCartService;
import com.lb.mall.utils.Base64Utils;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "购物车管理")
@RestController
@RequestMapping("/shopcart")
public class ShopcartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public ResultVo addShoppingCart(@RequestBody ShoppingCart cart, @RequestHeader("token") String token){
        ResultVo resultVo = shoppingCartService.addShoppingCart(cart);
        return resultVo;
    }
}
