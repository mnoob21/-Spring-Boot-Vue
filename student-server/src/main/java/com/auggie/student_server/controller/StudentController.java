package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.entity.User;
import com.auggie.student_server.service.StudentService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;



@RestController
@CrossOrigin("*")
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("/addStudent")
    public boolean addStudent(@RequestBody Student student) {
        System.out.println("正在保存学生对象" + student);
        return studentService.save(student);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Student student) {
        System.out.println(student.getPassword());
        try {
            boolean success = studentService.login(student.getPhone(), student.getPassword());
            System.out.println(student);
            if (success) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }



    @PostMapping("/findBySearch")
    public List<Student> findBySearch(@RequestBody Student student) {
        Integer fuzzy = (student.getPassword() == null) ? 0 : 1;
        return studentService.findBySearch(student.getSid(), student.getSname(), fuzzy);
    }

    @GetMapping("/findById/{sid}")
    public Student findById(@PathVariable("sid") Integer sid) {
        System.out.println("正在查询学生信息 By id " + sid);
        return studentService.findById(sid);
    }

    @GetMapping("/findByPhone/{phone}")
    public Student findByPhone(@PathVariable("phone") String phone) {
        System.out.println(phone);
        return studentService.findByPhone(phone);
    }

    @GetMapping("/findByPage/{page}/{size}")
    public List<Student> findByPage(@PathVariable("page") int page, @PathVariable("size") int size) {
        System.out.println("查询学生列表分页 " + page + " " + size);
        return studentService.findByPage(page, size);
    }

    @GetMapping("/getLength")
    public Integer getLength() {
        return studentService.getLength();
    }

    @GetMapping("/deleteById/{sid}")
    public boolean deleteById(@PathVariable("sid") int sid) {
        System.out.println("正在删除学生 sid：" + sid);
        return studentService.deleteById(sid);
    }

    @PostMapping("/updateStudent")
    public Student updateStudent(@RequestBody Student student) {
        System.out.println("更新 " + student);
        studentService.updateById(student);
        return student;
    }
}
