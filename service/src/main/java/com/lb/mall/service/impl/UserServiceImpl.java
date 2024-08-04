package com.lb.mall.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lb.mall.dao.UsersMapper;
import com.lb.mall.entity.Users;
import com.lb.mall.service.UserService;
import com.lb.mall.utils.Base64Utils;
import com.lb.mall.utils.MD5Utils;
import com.lb.mall.vo.BasicResultVO;
import com.lb.mall.vo.RespStatus;
import com.lb.mall.vo.RespStatusEnum;
import com.lb.mall.vo.ResultVo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class  UserServiceImpl implements UserService {

    @Resource
    private UsersMapper usersMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public ResultVo userRegist(String name, String password) {
        synchronized (this){
            Example example = new Example(Users.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("username",name);
            List<Users> users = usersMapper.selectByExample(example);



            if (users.size() > 0 ){  // 账户已存在
                return new ResultVo(RespStatus.NO,"用户名已经被注册",null);
            }

            String md5Pwd = MD5Utils.md5(password);
            Users user = new Users();
            user.setUsername(name);
            user.setPassword(md5Pwd);
            user.setUserImg("img/default.png");
            user.setUserRegtime(new Date());
            user.setUserModtime(new Date());

            int i = usersMapper.insertUseGeneratedKeys(user);
            if (i > 0){
                return new ResultVo(RespStatus.OK,"注册成功",user);
            }else {
                return new ResultVo(RespStatus.NO,"注册失败",null);
            }
        }
    }

    @Override
    public ResultVo checkLogin(String name, String password) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",name);
        List<Users> users = usersMapper.selectByExample(example);


        if (users.size() == 0){
            return new ResultVo(RespStatus.NO,"用户名不存在",null);
        }else {
             String md5Pwd = MD5Utils.md5(password);
            if (users.get(0).getPassword().equals(md5Pwd)){

                JwtBuilder builder = Jwts.builder();
                HashMap<String,Object> map = new HashMap<>();
                map.put("key1","value1");

                String token = builder.setSubject(name)      // 主题，就是token中携带的数据
                        .setIssuedAt(new Date())             // 设置token的生成时间
                        .setId(users.get(0).getUserId() + "")   // 设置用户id 为token id
                        .setClaims(map)     // map中可以存放用户的角色权限
                        .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))  // 设置token的过期时间
                        .signWith(SignatureAlgorithm.HS256, "MALL666")  // 设置加密方式和加密密码
                        .compact();

                // 当⽤户登录成功之后，以token为key 将⽤户信息保存到redis
                try {
                    String userInfo = objectMapper.writeValueAsString(users.get(0));
                    stringRedisTemplate.boundValueOps(token).set(userInfo,1, TimeUnit.MINUTES);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }


                return new ResultVo(RespStatus.OK,token, users.get(0));
            }else {
                return new ResultVo(RespStatus.NO,"密码错误",null);
            }
        }
    }
}
