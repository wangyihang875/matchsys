package com.bushengshi.miaosha.service;

import com.bushengshi.miaosha.dao.GoodsDao;
import com.bushengshi.miaosha.domain.Goods;
import com.bushengshi.miaosha.domain.MiaoshaUser;
import com.bushengshi.miaosha.domain.OrderInfo;
import com.bushengshi.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {
    //在当前 service 下不建议引入其他 service 对应的 dao

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        //order_info  miaosha_order
        return orderService.createOrder(user,goods);


    }
}
