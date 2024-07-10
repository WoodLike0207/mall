package com.lb.mall.dao;

import com.lb.mall.entity.ProductSku;
import com.lb.mall.general.GeneralDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSkuMapper extends GeneralDAO<ProductSku> {
}