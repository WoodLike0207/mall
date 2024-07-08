package com.lb.mall.dao;

import com.lb.mall.entity.ProductComments;
import com.lb.mall.entity.ProductCommentsVO;
import com.lb.mall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentsMapper extends GeneralDAO<ProductComments> {

    /**
     * 分页查询
     * @param productId 商品ID
     * @param start 起始索引
     * @param limit 查询条数
     * @return
     */
    List<ProductCommentsVO> selectCommentsByProductId(@Param("productId") String productId,
                                                      @Param("start") int start,
                                                      @Param("limit") int limit);
}