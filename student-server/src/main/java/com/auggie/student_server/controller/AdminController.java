package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Admin;
import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Admin login(@RequestBody Admin admin) {
        System.out.println(adminService.findByPhone(admin));
        return adminService.findByPhone(admin);

    }



}
