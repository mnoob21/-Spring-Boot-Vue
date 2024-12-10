package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    //普通用户注册
    void userRegister(User user);

    //对手机号是否存在校验
    List<User> phoneExist(User user);
}
