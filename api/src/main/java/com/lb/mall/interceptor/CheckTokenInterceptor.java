package com.lb.mall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * 受限资源拦截器
 */
@Component
public class CheckTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }

        String token = request.getHeader("token");
        if (token == null){
            ResultVo resultVo = new ResultVo(RespStatus.LOGIN_FAIL_NOT,"请先登录!",null);
            doResponse(response,resultVo);
        }else {
            //根据token从redis中获取⽤户信息
            String s = stringRedisTemplate.boundValueOps(token).get();
            if (s == null){
                //如果⽤户信息为空，表示⽤户未登录或者距离上⼀次访问超过30分钟
                ResultVo resultVO = new ResultVo(RespStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！",null);
                doResponse(response,resultVO);
            }else {
                // 如果不为空，表示⽤户登录成功，续命
                stringRedisTemplate.boundValueOps(token).expire(30, TimeUnit.MINUTES);
                return true;
            }


            /*try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("MALL666");

                //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVo resultVO = new ResultVo(RespStatus.LOGIN_FAIL_OVERDUE, "登录过期，请重新登录！",null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVo resultVO = new ResultVo(RespStatus.NO, "Token不合法！",null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVo resultVO = new ResultVo(RespStatus.LOGIN_FAIL_NOT, "请先登录！",null);
                doResponse(response,resultVO);
            }*/
        }
        return false;
    }

    private void doResponse(HttpServletResponse response,ResultVo resultVo) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVo);
        out.print(s);
        out.flush();
        out.close();
    }
}
