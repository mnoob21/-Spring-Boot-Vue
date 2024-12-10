package com.auggie.student_server.service;


import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseType;
import com.auggie.student_server.mapper.CourseTypeMapper;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    public boolean addCourseType(CourseType courseType){

        System.out.println(courseTypeMapper.findByName(courseType));
        if(courseTypeMapper.findByName(courseType) == null){
            //设置创建日期
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            courseType.setCreate_time(timestamp);
            return courseTypeMapper.addCourseType(courseType);
        }

        return false;
    }

    public List<CourseType> findAll(CourseType courseType){
        //获取查询结果
        List<CourseType> list = courseTypeMapper.findAll(courseType);
        // 创建一个数组来存储ctype_name
        String[] ctypeNames = new String[list.size()];

        // 遍历List并提取ctype_name
        for (int i = 0; i < list.size(); i++) {
            ctypeNames[i] = list.get(i).getCtype_name();
        }

        for (String name : ctypeNames) {
            System.out.println(name);
        }
        return courseTypeMapper.findAll(courseType);
    }

    public List<CourseType> findBySearch(Map<String, String> map) {

        Integer ctype_id = null;
        Integer fuzzy = 0;
        String ctype_name = null;

        if (map.containsKey("ctype_id")) {
            try {
                ctype_id = Integer.parseInt(map.get("ctype_id"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("ctype_name")) {
            ctype_name = map.get("ctype_name");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = (map.get("fuzzy").equals("true")) ? 1 : 0;
        }
        System.out.println("查询课程 map：" + map);
        System.out.println(ctype_id + " " + ctype_name + " " + fuzzy);
        return courseTypeMapper.findBySearch(ctype_id, ctype_name, fuzzy);
    }

    public boolean deleteById(Integer ctype_id) {
        return courseTypeMapper.deleteById(ctype_id);
    }

    public CourseType findById(Integer ctype_id){
        return courseTypeMapper.findById(ctype_id);
    }

    public AjaxResult updateCourseType(CourseType courseType){
        //设置修改日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        courseType.setUpdate_time(timestamp);
        return AjaxResult.success(courseTypeMapper.updateCourseType(courseType));
    }

    public List<String> getAllType(CourseType courseType) {
        // 首先获取所有课程类型的列表
        List<CourseType> allTypes = courseTypeMapper.findAll(courseType);

        // 使用Java 8的流来收集课程类型的名称

        return allTypes.stream().map(CourseType::getCtype_name).collect(Collectors.toList());
    }
}
