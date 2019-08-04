package com.bushengshi.miaosha.controller;

import com.bushengshi.miaosha.domain.MiaoshaUser;
import com.bushengshi.miaosha.redis.RedisService;
import com.bushengshi.miaosha.service.GoodsService;
import com.bushengshi.miaosha.service.MiaoshaUserService;
import com.bushengshi.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    /*@RequestMapping("/to_list")
    public String toList(Model model, HttpServletResponse response,
                         @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                         //RequestParam获取 token 为了兼容手机端
                         @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken
    ) {

        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser user = userService.getByToken(response, token);
        model.addAttribute("user", user);
        return "goods_list";
    }*/

    @RequestMapping("/to_list")
    public String toList(Model model, HttpServletResponse response, MiaoshaUser user) {
        //用argumentResolver来给 controller 的参数赋值,以此将通过 cookie 取 user 的方法分离出去
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);

        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, HttpServletResponse response, MiaoshaUser user,
                           @PathVariable("goodsId") long goodsId) {

        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) { //秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now) / 1000);
        } else if (now > endAt) { //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else { //秒杀正在进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);



        return "goods_detail";
    }
}
