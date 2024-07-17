package com.lb.mall.dao;

import com.lb.mall.entity.ProductSku;
import com.lb.mall.general.GeneralDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSkuMapper extends GeneralDAO<ProductSku> {

    /**
     * 根据商品ID查询商品价格最低的套餐
     * @param productId
     * @return
     */
    List<ProductSku> selectLowerPriceByProductId(String productId);
}