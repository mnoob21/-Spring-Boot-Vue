package com.auggie.student_server.service;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.mapper.StudentMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findByPage(Integer num, Integer size) {
        // num：第几页，size：一页多大
        // num：从零开始
        List<Student> studentList = studentMapper.findAll();
        ArrayList<Student> list = new ArrayList<>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = studentList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(studentList.get(i));

        }

        return list;
    }

    public List<Student> findBySearch(Integer sid, String sname, Integer fuzzy) {
        Student student = new Student();
        student.setSid(sid);
        student.setSname(sname);
        fuzzy = (fuzzy == null) ? 0 : fuzzy;

        System.out.println();

        return studentMapper.findBySearch(student, fuzzy);
    }

    public Integer getLength() {
        return studentMapper.findAll().size();
    }

    public Student findById(Integer sid) {
        return studentMapper.findById(sid);
    }

    public boolean updateById(Student student) {
        //设置修改日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        student.setUpdate_time(timestamp);

        // 加密密码
        String passwordHash = hashPassword(student.getPassword());
        student.setPassword(passwordHash);

        return studentMapper.updateById(student);
    }

    public boolean save(Student student) {
        return studentMapper.save(student);
    }

    public boolean deleteById(Integer sid) {
        return studentMapper.deleteById(sid);
    }

    public Student findByPhone(String phone) {
        return studentMapper.findByPhone(phone);
    }



    public boolean login(String phone, String inputPassword) {
        Student student = studentMapper.findByPhone(phone);
        if (student == null) {
            return false;
        }
        String storedPasswordHashWithSalt = student.getPassword();
        String[] parts = storedPasswordHashWithSalt.split(":");
        if (parts.length != 2) {
            throw new IllegalStateException("密码格式错误");
        }
        String storedHash = parts[0];
        String salt = parts[1];

        String inputHash = hashPassword(inputPassword, salt);
        return inputHash.equals(storedHash);
    }

    public String hashPassword(String inputPassword, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(inputPassword.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }


    public static String hashPassword(String password) {
        try {
            // 生成盐值
            String salt = getSalt();
            // 使用盐值和密码生成哈希
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            // 返回Base64编码的盐值和哈希值
            return Base64.getEncoder().encodeToString(hashedPassword) + ":" + salt;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }


}
