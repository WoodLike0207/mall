package com.lb.mall.controller;

import com.lb.mall.entity.ShoppingCart;
import com.lb.mall.service.ShoppingCartService;
import com.lb.mall.utils.Base64Utils;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @GetMapping("/list")
    @ApiImplicitParam(dataType = "int",name = "userId",value = "用户id",required = true)
    public ResultVo list(Integer userId,@RequestHeader("token") String token){
         ResultVo resultVo = shoppingCartService.listShoppingCartByUserId(userId);
         return resultVo;
    }

    @PutMapping("/update/{cid}/{cnum}")
    public ResultVo updateNum(@PathVariable("cid") Integer cartId,
                              @PathVariable("cnum") Integer cartNum,
                              @RequestHeader("token") String token){
        ResultVo resultVO = shoppingCartService.updateCartNum(cartId, cartNum);
        return resultVO;
    }

    @GetMapping("/listByCids")
    @ApiImplicitParam(dataType = "String",name = "cids", value = "选择的购物⻋记录id",required = true)
    public ResultVo listByCids(String cids,@RequestHeader("token")String token){
        ResultVo resultVo = shoppingCartService.listShoppingCartsByCids(cids);
        return resultVo;
    }
}
