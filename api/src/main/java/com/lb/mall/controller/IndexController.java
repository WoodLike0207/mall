package com.lb.mall.controller;

import com.lb.mall.service.CategoryService;
import com.lb.mall.service.IndexImgService;
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

    @ApiOperation("首页轮播图接口")
    @GetMapping("/indeximg")
    public ResultVo listIndexImgs(){
        return indexImgService.listIndexImgs();
    }

    @ApiOperation("商品分类查询接口")
    @GetMapping("/category-list")
    public ResultVo listCategory(){
        return categoryService.listCategories();
    }
}
 