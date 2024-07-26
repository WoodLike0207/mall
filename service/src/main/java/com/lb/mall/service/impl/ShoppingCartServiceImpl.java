package com.lb.mall.service.impl;

import com.lb.mall.dao.ShoppingCartMapper;
import com.lb.mall.entity.ShoppingCart;
import com.lb.mall.entity.ShoppingCartVO;
import com.lb.mall.service.ShoppingCartService;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultVo listShoppingCartByUserId(int userId) {
        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByUserId(userId);
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", list);
        return resultVo;
    }

    @Override
    public ResultVo updateCartNum(int cartId, int cartNum) {
        int i = shoppingCartMapper.updateCartnumByCartid(cartId, cartNum);
        if (i > 0){
            return new ResultVo(RespStatus.OK,"update success",null);
        }else {
            return new ResultVo(RespStatus.NO,"update fail",null);
        }
    }

    @Override
    public ResultVo listShoppingCartsByCids(String cids) {
        String[] arr = cids.split(",");
        List<Integer> cartIds = new ArrayList<>();

        for (int i = 0; i < arr.length; i++){
            cartIds.add(Integer.parseInt(arr[i]));
        }

        List<ShoppingCartVO> list = shoppingCartMapper.selectShopcartByCids(cartIds);
        ResultVo resultVo = new ResultVo(RespStatus.OK, "success", list);
        return resultVo;
    }
}
