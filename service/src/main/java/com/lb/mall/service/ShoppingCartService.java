package com.lb.mall.service;

import com.lb.mall.entity.ShoppingCart;
import com.lb.mall.vo.ResultVo;

public interface ShoppingCartService {
    ResultVo addShoppingCart(ShoppingCart cart);
}