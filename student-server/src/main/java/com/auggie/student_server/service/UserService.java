package com.auggie.student_server.service;

import com.auggie.student_server.entity.User;
import com.auggie.student_server.util.AjaxResult;

public interface UserService {

    //普通用户注册
    public AjaxResult UserRegister(User user);
}
