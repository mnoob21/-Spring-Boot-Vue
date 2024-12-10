package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("Teacher")
public class Teacher extends User{
    private Integer tid;
    private String tname;
    private String password;


    private String realName;
    private String idNumber;

    private String treal_name;
    private String tid_number;
    private String tsex;
    private String tphone;
    private String temail;
    private String tbirthday;
    private Timestamp create_time;
    private Timestamp update_time;

}
