package com.auggie.student_server.service.impl;

import com.auggie.student_server.entity.User;
import com.auggie.student_server.mapper.UserMapper;
import com.auggie.student_server.service.UserService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Base64;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public AjaxResult UserRegister(User user) {
        //对生日字符串拼接操作
        String dateTimeString = user.getBirthday();
        // 取前10个字符
        String dateString = dateTimeString.substring(0, 10);
        user.setBirthday(dateString);

        //设置创建日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setCreateTime(timestamp);

        // 加密密码
        String passwordHash = hashPassword(user.getPassWord());
        System.out.println(user.getPassWord());
        user.setPassWord(passwordHash);

        //对用户手机号是否存在进行校验

        if(userMapper.phoneExist(user).isEmpty()){
            userMapper.userRegister(user);
            return AjaxResult.success("注册成功 欢迎加入 艺趣生活馆！");
        }else{
            return AjaxResult.error("该手机号已存在 请重试！");
        }

    }

    public static String hashPassword(String password) {
        try {
            // 生成盐值
            String salt = getSalt();
            // 使用盐值和密码生成哈希
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            // 返回Base64编码的盐值和哈希值
            return Base64.getEncoder().encodeToString(hashedPassword) + ":" + salt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
