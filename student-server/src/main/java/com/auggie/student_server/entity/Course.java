package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("Course")
public class Course {
    private Integer cid;
    private String cname;
    private Integer ccredit;
    private String ctype_name;
    private Timestamp create_time;
    private Timestamp update_time;
    private String c_author;
    private String term;
    private String c_startdate;
    private String c_enddate;
    private String c_link;
    private Integer c_status;
    private String c_reviewer;
    private String c_description;
}
