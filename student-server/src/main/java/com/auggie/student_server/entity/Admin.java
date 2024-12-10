package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Admin")
public class Admin {
    private Integer id;
    private String aname;
    private String password;
    private String aphone;
    private Timestamp create_time;
    private Timestamp update_time;
}
