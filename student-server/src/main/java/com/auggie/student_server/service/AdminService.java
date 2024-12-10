package com.auggie.student_server.service;

import com.auggie.student_server.entity.Admin;
import com.auggie.student_server.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    public Admin findByPhone(Admin admin){

        Optional<Admin> optional = Optional.ofNullable(adminMapper.findByPhone(admin));
        if (optional.isPresent()) {
            Admin resultAdmin = optional.get();
            // 使用实体对象
            if(resultAdmin.getAname().equals(admin.getAname()) && resultAdmin.getPassword().equals(admin.getPassword())){
                return adminMapper.findByPhone(admin);
            }else{
                return null;
            }
        } else {
            // 返回null或构建一个表示无数据的对象
            return null;
        }
//        Admin errorAdmin = null;
//        Admin resultAdmin = adminMapper.findByPhone(admin);
//        if(resultAdmin.getAname().equals(admin.getAname()) && resultAdmin.getPassword().equals(admin.getPassword())){
//            return adminMapper.findByPhone(admin);
//        }else{
//            return errorAdmin;
//        }
    }
}
