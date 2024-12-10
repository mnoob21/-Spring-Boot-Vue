package com.auggie.student_server.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("CourseType")
public class CourseType {
    private Integer ctype_id;
    private String ctype_name;
    private String ctype_author;
    private Timestamp create_time;
    private Timestamp update_time;

}
