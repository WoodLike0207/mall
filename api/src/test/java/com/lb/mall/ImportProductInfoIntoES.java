package com.lb.mall;

import com.alibaba.fastjson.JSON;
import com.lb.ApiApplication;
import com.lb.mall.dao.ProductMapper;
import com.lb.mall.entity.Product4ES;
import com.lb.mall.entity.ProductSku;
import com.lb.mall.entity.ProductVO;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiApplication.class)
public class ImportProductInfoIntoES {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testCreateIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("mall_product_index");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse.isAcknowledged());
    }

    @Test
    public void testImportData() throws IOException {
        List<ProductVO> productVOS = productMapper.selectProducts();
        System.out.println(productVOS.size());

        for (int i = 0; i < productVOS.size(); i++) {
            ProductVO productVO = productVOS.get(i);

            String productId = productVO.getProductId();
            String productName = productVO.getProductName();
            Integer soldNum = productVO.getSoldNum();

            List<ProductSku> skus = productVO.getSkus();

            String skuName = skus.size()==0 ? "" : skus.get(0).getSkuName();
            String skuImg = skus.size()==0 ? "" : skus.get(0).getSkuImg();
            Integer sellPrice = skus.size()==0 ? 0 : skus.get(0).getSellPrice();

            Product4ES product = new Product4ES(productId,productName,skuImg,soldNum,skuName,sellPrice);
            IndexRequest indexRequest = new IndexRequest("mall_product_index");
            indexRequest.id(productId);
            indexRequest.source(JSON.toJSONString(product), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            System.out.println((i+1)+"---"+indexResponse);

        }
    }
}
