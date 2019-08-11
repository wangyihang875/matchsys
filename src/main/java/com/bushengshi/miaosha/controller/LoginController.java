package com.bushengshi.miaosha.controller;

import com.bushengshi.miaosha.result.CodeMsg;
import com.bushengshi.miaosha.result.Result;
import com.bushengshi.miaosha.service.MiaoshaUserService;
import com.bushengshi.miaosha.util.ValidatorUtil;
import com.bushengshi.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());

        //参数校验 使用 jsr303参数校验

        /*String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if(StringUtils.isEmpty(passInput)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if(StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/

        //登录
        /*CodeMsg cm = userService.login(loginVo);
        if(cm.getCode() == 0){
            return Result.success(true);
        }else{
            return Result.error(cm);
        }*/

        //使用全局异常拦截器捕捉异常
        String token = userService.login(response,loginVo);
        return Result.success(token);

    }
}
