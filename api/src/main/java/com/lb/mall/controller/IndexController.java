package com.lb.mall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lb.mall.service.CategoryService;
import com.lb.mall.service.IndexImgService;
import com.lb.mall.service.ProductService;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
@Api(tags = "首页管理")
public class IndexController {

    @Autowired
    private IndexImgService indexImgService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @ApiOperation("首页轮播图接口")
    @GetMapping("/indeximg")
    public ResultVo listIndexImgs(){
        try {
            return indexImgService.listIndexImgs();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("商品分类查询接口")
    @GetMapping("/category-list")
    public ResultVo listCategory(){
        return categoryService.listCategories();
    }


    @ApiOperation("新品推荐接口")
    @GetMapping("/list-recommends")
    public ResultVo listRecommendProducts(){
        return productService.listRecommendProducts();
    }

    @ApiOperation("分类推荐接口")
    @GetMapping("/category-recommends")
    public ResultVo listRecommendProductsByCategory(){
        return categoryService.listFirstLevelCategories();
    }
}
 