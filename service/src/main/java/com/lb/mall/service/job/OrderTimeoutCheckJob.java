package com.lb.mall.service.job;

import com.github.wxpay.sdk.WXPay;
import com.lb.mall.dao.OrderItemMapper;
import com.lb.mall.dao.OrdersMapper;
import com.lb.mall.dao.ProductSkuMapper;
import com.lb.mall.entity.OrderItem;
import com.lb.mall.entity.Orders;
import com.lb.mall.entity.ProductSku;
import com.lb.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderTimeoutCheckJob {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderService orderService;

    private WXPay wxPay = new WXPay(new MyPayConfig());

    @Scheduled(cron = "0/5 * * * * ?")
    public void checkAndCloseOrder(){
        // 1.查询超过30min状态依然为待支付的订单
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status","1");
        Date time = new Date(System.currentTimeMillis() - 30 * 60 * 1000);
        criteria.andLessThan("createTime",time);
        List<Orders> orders = ordersMapper.selectByExample(example);

        // 2.访问微信平台接口，确认当前订单最终的支付状态
        try{
            for (int i = 0; i < orders.size(); i++) {
                Orders order = orders.get(i);
                HashMap<String,String> params = new HashMap<>();
                params.put("out_trade_no",order.getOrderId());
                Map<String, String> resp = wxPay.orderQuery(params);

                if ("SUCCESS".equalsIgnoreCase(resp.get("trade_state"))){
                    // 2.1 如果订单已支付，则修改订单状态为“代发货/已支付” status = 2
                    Orders updateOrder = new Orders();
                    updateOrder.setOrderId(order.getOrderId());
                    updateOrder.setStatus("2");
                    ordersMapper.updateByPrimaryKeySelective(updateOrder);
                }else if ("NOTPAY".equalsIgnoreCase(resp.get("trade_state"))){
                    // 2.2 如果确实未支付，取消订单:
                    // a.向微信支付平台发送请求，关闭当前订单的支付链接
                    Map<String, String> map = wxPay.closeOrder(params);

                    // b.关闭订单
                    orderService.closeOrder(order.getOrderId());
                }
            }
        }catch (Exception e){

        }
    }
}
