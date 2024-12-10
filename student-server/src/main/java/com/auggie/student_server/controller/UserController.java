package com.auggie.student_server.controller;

import com.auggie.student_server.entity.User;
import com.auggie.student_server.service.impl.UserServiceImpl;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private UserServiceImpl userServiceImpl;

    //用户注册
    @PostMapping("/register")
    public AjaxResult register(@RequestBody User user){
        return userServiceImpl.UserRegister(user);
    }

    //发送验证码
    @PostMapping("sendSMS")
    public String sendSMS(@RequestBody User user) throws Exception {

//        String phone = user.getPhone();
        //SMSUtile 阿里云短信服务工具类
//        String captcha = SMSUtil.SendSMS(phone);

        return "123123";
    }

}
