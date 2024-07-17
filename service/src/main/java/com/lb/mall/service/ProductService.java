package com.lb.mall.service;

import com.lb.mall.vo.ResultVo;

public interface ProductService {
    ResultVo listRecommendProducts();

    ResultVo getProductBasicInfo(String productId);

    ResultVo getProductParamsById(String productId);

    ResultVo getProductByCategoryId(int categoryId,int pageNum,int limit);

    ResultVo listBrands(int categoryId);

    ResultVo searchProduct(String kw,int pageNum,int limit);
}
