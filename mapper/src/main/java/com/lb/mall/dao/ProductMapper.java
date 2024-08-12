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

    List<String> selectBrandByCategoryId(int cid);



    /**
     * 根据商品名字进行模糊查询
     * @param keyword
     * @param start
     * @param limit
     * @return
     */
    List<ProductVO> selectProductByKeyword(@Param("kw") String keyword,
                                              @Param("start") int start,
                                              @Param("limit") int limit);

    List<ProductVO> selectProducts();

    /**
     * 根据搜索关键字查询相关商品的品牌列表
     * @param kw
     * @return
     */
    List<String> selectBrandByKeyword(String kw);
}