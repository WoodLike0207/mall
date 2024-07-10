package com.lb.mall.dao;

import com.lb.mall.entity.Category;
import com.lb.mall.entity.CategoryVO;
import com.lb.mall.general.GeneralDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends GeneralDAO<Category> {

    //1.连接查询
    List<CategoryVO> selectAllCategories();

    //2.⼦查询：根据parentId查询⼦分类
    List<CategoryVO> selectAllCategories2(int parentId);

    //3. 查询一级类别
    List<CategoryVO> selectFirstLevelCategories();
}