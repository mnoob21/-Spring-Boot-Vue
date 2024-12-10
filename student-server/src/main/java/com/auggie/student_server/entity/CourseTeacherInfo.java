package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("CourseTeacherInfo")
public class CourseTeacherInfo {
    private Integer cid;
    private Integer tid;
    private String cname;
    private String tname;
    private String ctype_name;
    private String c_startdate;
    private String c_enddate;
    private String c_link;
    private Integer c_status;
    private Float grade;
    private String c_description;
}
