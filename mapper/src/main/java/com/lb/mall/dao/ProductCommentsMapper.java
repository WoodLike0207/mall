package com.lb.mall.dao;

import com.lb.mall.entity.ProductComments;
import com.lb.mall.entity.ProductCommentsVO;
import com.lb.mall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentsMapper extends GeneralDAO<ProductComments> {
    List<ProductCommentsVO> selectCommentsByProductId(String productId);
}