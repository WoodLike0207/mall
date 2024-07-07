package com.lb.mall.dao;

import com.lb.mall.entity.ProductImg;
import com.lb.mall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgMapper extends GeneralDAO<ProductImg> {
    List<ProductImg> selectProductImgByProductId(int productId);
}