package com.lb.mall;

import com.lb.ApiApplication;
import com.lb.mall.dao.CategoryMapper;
import com.lb.mall.dao.ProductMapper;
import com.lb.mall.entity.Category;
import com.lb.mall.entity.CategoryVO;
import com.lb.mall.entity.ProductVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ApiApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void contextLoads(){
        List<CategoryVO> categoryVOS = categoryMapper.selectAllCategories2(0);

        for (CategoryVO c1 : categoryVOS) {
            System.out.println(c1);
            for (CategoryVO c2 : c1.getCategories()) {
                System.out.println("\t"+c2);
                for (CategoryVO c3 : c2.getCategories()) {
                    System.out.println("\t\t"+c3);
                }
            }
        }
    }

    @Test
    public void testRecommend(){
        List<ProductVO> productVOS = productMapper.selectRecommendProducts();
        for (ProductVO productVO : productVOS) {
            System.out.println(productVO);
        }
    }

    @Test
    public void testSelectFirstLevelCategory(){
        List<CategoryVO> categoryVOS = categoryMapper.selectFirstLevelCategories();
        for (CategoryVO categoryVO : categoryVOS) {
            System.out.println(categoryVO);
        }
    }
}
