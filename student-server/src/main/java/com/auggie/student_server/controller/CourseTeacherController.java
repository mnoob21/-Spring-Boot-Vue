package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseTeacher;
import com.auggie.student_server.entity.CourseTeacherInfo;
import com.auggie.student_server.service.CourseTeacherService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin("*")
@RequestMapping("/courseTeacher")
public class CourseTeacherController {
    @Autowired
    private CourseTeacherService courseTeacherService;

    @GetMapping("/insert/{cid}/{tid}/{term}")
    public AjaxResult insert(@PathVariable Integer cid, @PathVariable Integer tid, @PathVariable String term) {
        if (courseTeacherService.findBySearch(cid, tid, term).size() != 0) {
            return AjaxResult.error("false");
        }
        return AjaxResult.success(courseTeacherService.insertCourseTeacher(cid, tid, term));
    }

    @GetMapping("/findMyCourse/{tid}/{term}")
    public List<Course> findMyCourse(@PathVariable Integer tid, @PathVariable String term) {
        System.out.println("查询教师课程：" + tid + " " + term);
        return courseTeacherService.findMyCourse(tid, term);
    }

    @PostMapping("/findCourseTeacherInfo")
    public List<CourseTeacherInfo> findCourseTeacherInfo(@RequestBody Map<String, String> map) {
        System.out.println(map);
        return courseTeacherService.findCourseTeacherInfo(map);
    }

    @PostMapping("/deleteById")
    public boolean deleteById(@RequestBody CourseTeacher courseTeacher) {
        return courseTeacherService.deleteById(courseTeacher);
    }


    @GetMapping("/delete/{cid}")
    public AjaxResult delete(@PathVariable int cid){

        return AjaxResult.success(courseTeacherService.delete(cid));
    }
}
