package com.lb.mall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.ResultVo;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)){
            return true;
        }

        String token = request.getHeader("token");
        if (token == null){
            return false;
        }else {
            try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("MALL666");

                //如果token正确（密码正确，有效期内）则正常执⾏，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            }catch (ExpiredJwtException e){
                ResultVo resultVO = new ResultVo(RespStatus.NO, "登录过期，请重新登录！",null);
                doResponse(response,resultVO);
            }catch (UnsupportedJwtException e){
                ResultVo resultVO = new ResultVo(RespStatus.NO, "Token不合法！",null);
                doResponse(response,resultVO);
            }catch (Exception e){
                ResultVo resultVO = new ResultVo(RespStatus.NO, "请先登录！",null);
                doResponse(response,resultVO);
            }
            return false;
        }
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
