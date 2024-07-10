package com.lb.mall.service;

import com.lb.mall.vo.ResultVo;

public interface ProductService {
    ResultVo listRecommendProducts();

    ResultVo getProductBasicInfo(String productId);

    ResultVo getProductParamsById(String productId);
}
