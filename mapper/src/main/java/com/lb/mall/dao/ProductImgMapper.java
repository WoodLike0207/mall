package com.lb.mall.dao;

import com.lb.mall.entity.ProductImg;
import com.lb.mall.general.GeneralDAO;

import java.util.List;

public interface ProductImgMapper extends GeneralDAO<ProductImg> {
    List<ProductImg> selectProductImgByProductId(int productId);
}