package com.lb.mall.service;

import com.lb.mall.vo.ResultVo;
import org.springframework.stereotype.Service;


public interface ProductCommentsService {
    ResultVo listCommentsByProductId(String productId,int pageNum,int limit);

    /**
     * 根据商品id统计当前商品的评价信息
     * @param productId
     * @return
     */
    ResultVo getCommentsCountByProductId(String productId);
}
