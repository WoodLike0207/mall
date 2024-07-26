package com.lb.mall.controller;

import com.github.wxpay.sdk.WXPay;
import com.lb.mall.config.MyPayConfig;
import com.lb.mall.entity.Orders;
import com.lb.mall.service.OrderService;
import com.lb.mall.service.impl.OrderServiceImpl;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Api(tags = "订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public ResultVo add(String cids, @RequestBody Orders order){
        ResultVo resultVo = null;

        try {
            Map<String, String> orderInfo = orderService.addOrder(cids, order);
            String orderId = orderInfo.get("orderId");

            if(orderId!=null){
                //设置当前订单信息
                HashMap<String,String> data = new HashMap<>();
                data.put("body",orderInfo.get("productNames")); //商品描述
                data.put("out_trade_no",orderId); //使⽤当前⽤户订单的编号作为当前⽀付交易的交易号
                data.put("fee_type","CNY"); //⽀付币种
                //data.put("total_fee",order.getActualAmount()*100+""); //⽀付⾦额
                data.put("total_fee","1"); //⽀付⾦额
                data.put("trade_type","NATIVE"); //交易类型
                data.put("notify_url","http://8.130.130.65:8080/pay/callback"); //设置⽀付完成时的回调⽅法接⼝

                //发送请求，获取响应
                //微信⽀付：申请⽀付连接
                WXPay wxPay = new WXPay(new MyPayConfig());
                Map<String, String> resp = wxPay.unifiedOrder(data);

                System.out.println(resp);

                orderInfo.put("payUrl",resp.get("code_url"));
                resultVo = new ResultVo(RespStatus.OK,"提交订单成功！",orderInfo);
            }else {
                resultVo = new ResultVo(RespStatus.NO,"提交订单失败！",null);
            }

        }catch (SQLException e){
            resultVo = new ResultVo(RespStatus.NO,"提交订单失败！",null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return resultVo;
    }

    @GetMapping("/status/{oid}")
    public ResultVo getOrderStatus(@PathVariable("oid") String orderId, @RequestHeader("token") String token){
        ResultVo resultVo = orderService.getOrderById(orderId);
        return resultVo;
    }

    @GetMapping("/list")
    @ApiOperation("订单查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "string",name = "userId",value = "用户ID",required = true),
            @ApiImplicitParam(dataType = "string",name = "status",value = "订单状态",required = true),
            @ApiImplicitParam(dataType = "int",name = "pageNum",value = "当前页码",required = true),
            @ApiImplicitParam(dataType = "int",name = "limit",value = "每页显示条数",required = true)
    })
    public ResultVo list(@RequestHeader("token")String token,
                         String userId,String status,int pageNum,int limit){
        ResultVo resultVo = orderService.listOrders(userId, status, pageNum, limit);
        return resultVo;
    }
}
