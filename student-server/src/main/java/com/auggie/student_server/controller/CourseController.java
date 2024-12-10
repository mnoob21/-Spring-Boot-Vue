package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.service.CourseService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin("*")
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("/findBySearch")
    public List<Course> findBySearch(@RequestBody Map<String, String> map) {

        return courseService.findBySearch(map);
    }

    @GetMapping("/findById/{cid}")
    public List<Course> findById(@PathVariable Integer cid) {

        return courseService.findBySearch(cid);
    }

    @PostMapping("/save")
    public boolean save(@RequestBody Course course) {

        System.out.println(course);
        return courseService.insertCourse(course);

    }

    @GetMapping("/deleteById/{cid}")
    public AjaxResult deleteById(@PathVariable Integer cid) {
        System.out.println("正在删除课程 cid: " + cid);
        return AjaxResult.success(courseService.deleteById(cid));
    }

    @PostMapping("/updateCourse")
    public boolean updateCourse(@RequestBody Course course) {
        System.out.println("正在修改课程: " + course);
        return courseService.updateById(course);
    }

    @PostMapping("/review")
    public AjaxResult review(@RequestBody Course course){

        return AjaxResult.success(courseService.review(course));
    }


    @PostMapping("/overrule")
    public AjaxResult overrule(@RequestBody Course course){
        System.out.println(course);
        return AjaxResult.success(courseService.overrule(course));
    }

    @GetMapping("/openCourse/{cid}")
    public AjaxResult openCourse(@PathVariable Integer cid){

        return AjaxResult.success(courseService.openCourse(cid));
    }

    @GetMapping("/terminateCourse/{cid}")
    public AjaxResult terminateCourse(@PathVariable Integer cid){

        return AjaxResult.success(courseService.terminateCourse(cid));
    }
}
