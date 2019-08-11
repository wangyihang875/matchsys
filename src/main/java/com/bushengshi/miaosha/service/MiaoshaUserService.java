package com.bushengshi.miaosha.service;

import com.bushengshi.miaosha.dao.MiaoshaUserDao;
import com.bushengshi.miaosha.domain.MiaoshaUser;
import com.bushengshi.miaosha.exception.GlobalException;
import com.bushengshi.miaosha.redis.MiaoshaUserKey;
import com.bushengshi.miaosha.redis.RedisService;
import com.bushengshi.miaosha.result.CodeMsg;
import com.bushengshi.miaosha.util.MD5Util;
import com.bushengshi.miaosha.util.UUIDUtil;
import com.bushengshi.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    public String login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            //return CodeMsg.SERVER_ERROR;
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String fromPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            //return CodeMsg.MOBILE_NOT_EXIST;
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //验证密码
        String dbPass = user.getPassword();
        String slatDB = user.getSalt();
        String calcPass = MD5Util.fromPassToDBPass(fromPass, slatDB);
        if (!calcPass.equals(dbPass)) {
            //return CodeMsg.PASSWORD_ERROR;
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //登录成功之后的操作
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);

        //return CodeMsg.SUCCESS;
        return token;

    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }

        return user;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
//        cookie.setMaxAge(5);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
