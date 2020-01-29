package com.bushengshi.miaosha.controller;

import com.bushengshi.miaosha.domain.User;
import com.bushengshi.miaosha.rabbitmq.MQSender;
import com.bushengshi.miaosha.redis.RedisService;
import com.bushengshi.miaosha.redis.UserKey;
import com.bushengshi.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bushengshi.miaosha.result.CodeMsg;
import com.bushengshi.miaosha.result.Result;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    //1.rest api json输出 2.页面
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,bushengshi");
        // return new Result(0, "success", "hello,imooc");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
        //return new Result(500102, "XXX");
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "bushengshi");
        return "hello";
    }

    @RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
        mqSender.send("hello,wangyihang mq");
        return Result.success("hello,wangyihang");
    }

    @RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> mqTopic() {
        mqSender.sendTopic("hello,wangyihang topic");
        return Result.success("hello,wangyihang");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> mqFanout() {
        mqSender.sendFanout("hello,wangyihang fanout");
        return Result.success("hello,wangyihang");
    }

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
        mqSender.sendHeader("hello,wangyihang header");
        return Result.success("hello,wangyihang ");
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(Model model) {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(Model model) {
        userService.tx();
        return Result.success(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(Model model) {
        User u1 = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(u1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<User> redisSet(Model model) {
//        User u1 = new User(1,"wangyh");

//        Boolean ret = redisService.set(UserKey.getById,""+1,u1);
        User user = redisService.get(UserKey.getById,""+1,User.class);
        return Result.success(user);
    }
}
