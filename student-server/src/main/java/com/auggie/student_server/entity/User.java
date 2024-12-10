package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;                    //用户自增主键
    private String nickName;            //用户名
    private String passWord;            //用户密码
    private String sex;                 //用户性别
    private String phone;               //用户手机号
    private String email;               //用户邮箱
    private String birthday;            //用户出生年月
    private Timestamp createTime;       //用户创建日期
    private Timestamp updateTime;       //用户修改日期


}
