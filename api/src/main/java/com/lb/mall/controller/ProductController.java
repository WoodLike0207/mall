package com.lb.mall.controller;

import com.lb.mall.service.ProductService;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@Api(value = "商品信息相关接口",tags = "商品管理")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detail-info/{pid}")
    public ResultVo getProductBasicInfo(@PathVariable("pid") String pid){
        return productService.getProductBasicInfo(pid);
    }

    @ApiOperation("商品参数信息查询接口")
    @GetMapping("/detail-params/{pid}")
    public ResultVo getProductParams(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }
}
