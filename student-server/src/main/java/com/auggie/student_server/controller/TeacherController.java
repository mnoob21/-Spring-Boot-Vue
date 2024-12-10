package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.service.TeacherService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin("*")
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @PostMapping("/addTeacher")
    public boolean addTeacher(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Teacher teacher) {

        try {
            boolean success = teacherService.login(teacher.getPhone(), teacher.getPassword());
            System.out.println(teacher.getPassword());
            if (success) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }


    @GetMapping("/findById/{tid}")
    public Teacher findById(@PathVariable("tid") Integer tid) {
        System.out.println("正在查询学生信息 By id " + tid);
        return teacherService.findById(tid);
    }

    @GetMapping("/findByPhone/{phone}")
    public Teacher findByPhone(@PathVariable("phone") String phone) {
        System.out.println(phone);
        return teacherService.findByPhone(phone);
    }

    @PostMapping("/findBySearch")
    public List<Teacher> findBySearch(@RequestBody Map<String, String> map) {
        return teacherService.findBySearch(map);
    }

    @GetMapping("/deleteById/{tid}")
    public boolean deleteById(@PathVariable("tid") int tid) {
        System.out.println("正在删除学生 tid：" + tid);
        return teacherService.deleteById(tid);
    }

    @PostMapping("/updateTeacher")
    public Teacher updateTeacher(@RequestBody Teacher teacher) {
        System.out.println("更新 " + teacher);
        teacherService.updateById(teacher);
        return teacher;
    }

    @PostMapping("/register")
    public AjaxResult register(@RequestBody Teacher teacher){

        return teacherService.tRegister(teacher);
    }
}
