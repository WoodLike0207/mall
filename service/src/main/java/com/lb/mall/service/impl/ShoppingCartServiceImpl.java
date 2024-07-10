package com.lb.mall.service.impl;

import com.lb.mall.dao.ShoppingCartMapper;
import com.lb.mall.entity.ShoppingCart;
import com.lb.mall.service.ShoppingCartService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public ResultVo addShoppingCart(ShoppingCart cart) {
        cart.setCartTime(sdf.format(new Date()));
        int i = shoppingCartMapper.insert(cart);
        if (i > 0){
            return new ResultVo(RespStatus.OK,"success",null);
        }else {
            return new ResultVo(RespStatus.NO,"fail",null);
        }
    }
}
