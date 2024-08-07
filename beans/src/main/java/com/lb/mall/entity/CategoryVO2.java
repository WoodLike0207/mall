package com.lb.mall.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.List;

/**
 *  首页类别商品推荐
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryVO2 {

    private Integer categoryId;

    private String categoryName;

    private Integer categoryLevel;

    private Integer parentId;

    private String categoryIcon;

    private String categorySlogan;

    private String categoryPic;

    private String categoryBgColor;

    // 实现首页的类别显示
    private List<Category> categories;

    // 实现首页分类商品推荐
    private List<ProductVO> products;
}