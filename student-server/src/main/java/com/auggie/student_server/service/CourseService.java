package com.auggie.student_server.service;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CourseService {
    @Autowired
    private CourseMapper courseMapper;

    public List<Course> findBySearch(Map<String, String> map) {
        Integer cid = null;
        String ctype_name = null;
        Integer fuzzy = 0;
        String cname = null;
        String c_author = null;
        String courseStatus = null;
        int cstatus = 5;

        if (map.containsKey("cid")) {
            try {
                cid = Integer.parseInt(map.get("cid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("ctype_name")) {
            ctype_name = map.get("ctype_name");
        }
        if (map.containsKey("cname")) {
            cname = map.get("cname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = (map.get("fuzzy").equals("true")) ? 1 : 0;
        }if (map.containsKey("c_author")) {
            c_author = map.get("c_author");
        }
        if(map.containsKey("cstatus")){
            courseStatus = map.get("cstatus");
            if(courseStatus.equals("待审核")){

                cstatus = 0;

            }else if(courseStatus.equals("课程未开启")){
                cstatus = 1;

            }else if(courseStatus.equals("正在进行")){
                cstatus = 2;

            }else if(courseStatus.equals("已结束")){
                cstatus = 3;

            }else if(courseStatus.equals("已驳回")){
                cstatus = 4;

            }else{
                cstatus = 5;
            }
        }
        System.out.println(courseStatus);
        System.out.println(cstatus);


        return courseMapper.findBySearch(cid, cname, fuzzy, ctype_name,c_author,cstatus);
    }

    public List<Course> findBySearch(Integer cid) {
        return courseMapper.findBySearch(cid, null, 0, null,null,5);
    }

    public List<Course> findById(Integer cid) {
        HashMap<String, String> map = new HashMap<>();
        if (cid != null)
            map.put("cid", String.valueOf(cid));
        return findBySearch(map);
    }

    public boolean updateById(Course course) {
        //设置修改日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        course.setUpdate_time(timestamp);
        return courseMapper.updateById(course);
    }

    public boolean insertCourse(Course course) {
        //设置创建日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        course.setCreate_time(timestamp);

        //设置课程状态为0（已申请未审核）
        course.setC_status(0);
        return courseMapper.insertCourse(course);
    }

    public boolean deleteById(Integer cid) {

        return courseMapper.deleteById(cid);
    }


    public String review(Course course) {
        courseMapper.review(course);
        return "审核通过!";
    }

    public String overrule(Course course) {

        courseMapper.overrule(course);
        return "驳回成功!";
    }

    public String openCourse(Integer cid) {
        courseMapper.openCourse(cid);
        return "开启成功!";
    }

    public String terminateCourse(Integer cid) {
        courseMapper.terminateCourse(cid);
        return "终止成功!";
    }
}
