package com.bushengshi.miaosha.controller;

import com.bushengshi.miaosha.domain.MiaoshaOrder;
import com.bushengshi.miaosha.domain.MiaoshaUser;
import com.bushengshi.miaosha.domain.OrderInfo;
import com.bushengshi.miaosha.redis.RedisService;
import com.bushengshi.miaosha.result.CodeMsg;
import com.bushengshi.miaosha.result.Result;
import com.bushengshi.miaosha.service.GoodsService;
import com.bushengshi.miaosha.service.MiaoshaService;
import com.bushengshi.miaosha.service.MiaoshaUserService;
import com.bushengshi.miaosha.service.OrderService;
import com.bushengshi.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {
    private static Logger log = LoggerFactory.getLogger(MiaoshaController.class);

    @Autowired
    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("/do_miaosha")
    public String doMiaosha(Model mode, MiaoshaUser user,@RequestParam("goodsId")long goodsId) {
        mode.addAttribute("user",user);
        if(user == null){
            return "login";
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            mode.addAttribute("errMsg", CodeMsg.MIAOSHA_OVER);
            return "miaosha_fail";
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            mode.addAttribute("errMsg", CodeMsg.MIAOSHA_REPEAT);
            return "miaosha_fail";
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);
        mode.addAttribute("orderInfo", orderInfo);
        mode.addAttribute("goods", goods);


        return "order_detail";
    }

    /*
    * GET POST区别
    * GET不对服务端数据产生影响到请求用GET,对服务端数据产生影响的用POST
    * */
    @RequestMapping("/do_miaosha2")
    @ResponseBody
    public Result<OrderInfo> doMiaosha2(Model mode, MiaoshaUser user, @RequestParam("goodsId")long goodsId) {
        if(user == null){
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0){
            return Result.error(CodeMsg.MIAOSHA_OVER);
        }
        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if(order != null){
            return Result.error(CodeMsg.MIAOSHA_REPEAT);
        }
        //减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goods);

        return Result.success(orderInfo);
    }
}
