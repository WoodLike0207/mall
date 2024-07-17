package com.lb.mall.dao;

import com.lb.mall.entity.Product;
import com.lb.mall.entity.ProductVO;
import com.lb.mall.general.GeneralDAO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper extends GeneralDAO<Product> {
    List<ProductVO> selectRecommendProducts();

    /**
     * 查询一级类别下销量最高的6个商品
     * @param cid
     * @return
     */
    List<ProductVO> selectTop6ByCategory(int cid);

    List<ProductVO> selectProductByCategoryId(@Param("cid") int cid,
                                              @Param("start") int start,
                                              @Param("limit") int limit);

    List<String> selectBrandCategoryId(int cid);
}