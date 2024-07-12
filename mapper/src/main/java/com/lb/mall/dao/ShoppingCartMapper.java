package com.lb.mall.dao;

import com.lb.mall.entity.ShoppingCart;
import com.lb.mall.entity.ShoppingCartVO;
import com.lb.mall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartMapper extends GeneralDAO<ShoppingCart> {
    List<ShoppingCartVO> selectShopcartByUserId(int userId);

    int updateCartnumByCartid(@Param("cartId") int cartId,
                              @Param("cartNum") int cartNum);

    List<ShoppingCartVO> selectShopcartByCids(List<Integer> cids);
}