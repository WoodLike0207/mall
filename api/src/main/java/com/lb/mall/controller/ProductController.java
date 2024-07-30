package com.lb.mall.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lb.mall.service.ProductCommentsService;
import com.lb.mall.service.ProductService;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ProductCommentsService productCommentsService;

    @ApiOperation("商品基本信息查询接口")
    @GetMapping("/detail-info/{pid}")
    public ResultVo getProductBasicInfo(@PathVariable("pid") String pid)  {
        try {
            return productService.getProductBasicInfo(pid);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @ApiOperation("商品参数信息查询接口")
    @GetMapping("/detail-params/{pid}")
    public ResultVo getProductParams(@PathVariable("pid") String pid){
        return productService.getProductParamsById(pid);
    }

    @ApiOperation("商品评论信息查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    @GetMapping("/detail-comments/{pid}")
    public ResultVo getProductComments(@PathVariable("pid") String pid,int pageNum,int limit){
        return productCommentsService.listCommentsByProductId(pid,pageNum,limit);
    }

    @ApiOperation("商品评价统计查询接口")
    @GetMapping("/detail-commentscount/{pid}")
    public ResultVo getProductCommentsCount(@PathVariable("pid") String pid){
        return productCommentsService.getCommentsCountByProductId(pid);
    }

    @ApiOperation("根据类别查询商品接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    @GetMapping("/listbycid/{cid}")
    public ResultVo getProductsByCategoryId(@PathVariable("cid") int cid,int pageNum,int limit){
        return productService.getProductByCategoryId(cid,pageNum,limit);
    }

    @ApiOperation("根据类别查询商品品牌接口")
    @GetMapping("/listbrands/{cid}")
    public ResultVo getBrandsByCategoryId(@PathVariable("cid") int cid){
        return productService.listBrands(cid);
    }

    @ApiOperation("根据关键字查询商品接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "keyword",value = "搜索关键字",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    @GetMapping("/listbykeyword")
    public ResultVo searchProducts(String keyword,int pageNum,int limit){
        return productService.searchProduct(keyword, pageNum, limit);
    }

    @ApiOperation("根据关键字查询商品品牌接口")
    @ApiImplicitParam(dataType = "string",name = "keyword",value = "搜索关键字",required = true)
    @GetMapping("/listbrands-keyword")
    public ResultVo getBrandsByKeyword(String keyword){
        return productService.listBrands(keyword);
    }

}
