package com.auggie.student_server.controller;


import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseType;
import com.auggie.student_server.service.CourseTeacherService;
import com.auggie.student_server.service.CourseTypeService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/courseType")
public class CourseTypeController {

    @Autowired
    private CourseTypeService courseTypeService;


    @PostMapping("/add")
    public boolean addCourseType(@RequestBody CourseType courseType) {

        courseTypeService.findAll(courseType);
        return courseTypeService.addCourseType(courseType);
    }

    @PostMapping("/findBySearch")
    public List<CourseType> findBySearch(@RequestBody Map<String, String> map) {
        return courseTypeService.findBySearch(map);
    }

    @GetMapping("/deleteById/{ctype_id}")
    public AjaxResult deleteById(@PathVariable Integer ctype_id) {
        try{

            return AjaxResult.success(courseTypeService.deleteById(ctype_id));
        }catch (Exception e){
            return AjaxResult.error("删除失败！");
        }

    }

    @GetMapping("/findById/{ctype_id}")
    public CourseType findById(@PathVariable Integer ctype_id){

        return courseTypeService.findById(ctype_id);
    }

    @PostMapping("/updateCourseType")
    public AjaxResult updateCourseType(@RequestBody CourseType courseType){

        return courseTypeService.updateCourseType(courseType);
    }

    @PostMapping("/getAllCourseType")
    public List<String> getAllCourseType(@RequestBody CourseType courseType){
        return courseTypeService.getAllType(courseType);
    }
}
