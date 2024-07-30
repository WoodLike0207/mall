package com.lb.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lb.mall.vo.ResultVo;

public interface ProductService {
    ResultVo listRecommendProducts();

    ResultVo getProductBasicInfo(String productId) throws JsonProcessingException;

    ResultVo getProductParamsById(String productId);

    ResultVo getProductByCategoryId(int categoryId,int pageNum,int limit);

    ResultVo listBrands(int categoryId);

    ResultVo searchProduct(String kw,int pageNum,int limit);

    ResultVo listBrands(String kw);
}
