package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Student")
public class Student extends User{
    private Integer sid;
    private String sname;
    private String password;
    private String ssex;                 //用户性别
    private String sphone;               //用户手机号
    private String semail;               //用户邮箱
    private String sbirthday;            //用户出生年月
    private Timestamp create_time;       //用户创建日期
    private Timestamp update_time;       //用户修改日期
}
