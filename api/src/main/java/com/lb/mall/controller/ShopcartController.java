package com.lb.mall.controller;

import com.lb.mall.utils.Base64Utils;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.jsonwebtoken.*;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "购物车管理")
@RestController
@RequestMapping("/shopcart")
public class ShopcartController {

    @GetMapping("/list")
    public ResultVo listCarts(String token){
        if (token == null){
          return new ResultVo(RespStatus.NO,"请先登录",null);
        }

        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey("MALL666");

            //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            Claims body = claimsJws.getBody();  //获取token中⽤户数据
            String subject = body.getSubject(); //获取⽣成token设置的subject
            String v1 = body.get("key1", String.class); //获取⽣成token时存储的Claims的map中的值

            return new ResultVo(RespStatus.OK,"success",null);
        }catch (Exception e){
            return new ResultVo(RespStatus.NO,"登录过期，请重新登录",null);
        }

    }
}
