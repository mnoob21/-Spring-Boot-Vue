package com.auggie.student_server.service;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.entity.Teacher;
import com.auggie.student_server.mapper.StudentMapper;
import com.auggie.student_server.mapper.TeacherMapper;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLOutput;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public List<Teacher> findBySearch(Map<String, String> map) {
        Integer tid = null;
        String tname = null;
        Integer fuzzy = null;
        if (map.containsKey("tid")) {
            try {
                tid = Integer.parseInt(map.get("tid"));
            }
            catch (Exception e) {
            }
        }
        if (map.containsKey("tname")) {
            tname = map.get("tname");
        }
        if (map.containsKey("fuzzy")) {
            fuzzy = map.get("fuzzy").equals("true") ? 1 : 0;
        }
        System.out.println(map);
        System.out.println("查询类型：" + tid  + ", " + tname + ", " + fuzzy);
        return teacherMapper.findBySearch(tid, tname, fuzzy);
    }

    public List<Teacher> findByPage(Integer num, Integer size) {
        // num：第几页，size：一页多大
        // num：从零开始
        List<Teacher> teacherList = teacherMapper.findAll();
        ArrayList<Teacher> list = new ArrayList<>();

        int start = size * num;
        int end = size * (num + 1);
        int sz = teacherList.size();

        for (int i = start; i < end && i < sz; i++) {
            list.add(teacherList.get(i));
        }

        return list;
    }

    public Integer getLength() {
        return teacherMapper.findAll().size();
    }

    public Teacher findById(Integer tid) {
        return teacherMapper.findById(tid);
    }

    public Teacher findByPhone(String phone) {
        return teacherMapper.findByPhone(phone);
    }

    public boolean updateById(Teacher teacher) {
        //设置修改日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        teacher.setUpdate_time(timestamp);

        // 加密密码
        String passwordHash = hashPassword(teacher.getPassword());
        teacher.setPassword(passwordHash);


        return teacherMapper.updateById(teacher);
    }

    public boolean save(Teacher teacher) {
        return teacherMapper.save(teacher);
    }

    public boolean deleteById(Integer tid) {
        return teacherMapper.deleteById(tid);
    }

    //教师注册
    public AjaxResult tRegister(Teacher teacher){
        // 对生日字符串拼接操作
        String dateTimeString = teacher.getBirthday();
        // 取前10个字符
        String dateString = dateTimeString.substring(0, 10);
        teacher.setBirthday(dateString);

        //设置创建日期
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        teacher.setCreateTime(timestamp);

        // 加密密码
        String passwordHash = hashPassword(teacher.getPassWord());
        System.out.println(teacher.getPassWord());
        teacher.setPassWord(passwordHash);

        //判断身份证是否存在
        if(teacherMapper.idExist(teacher).isEmpty()){
            //判断手机号是否存在
            if(teacherMapper.phoneExist(teacher).isEmpty()){
                teacherMapper.tRegister(teacher);
                return AjaxResult.success("注册成功 欢迎加入 艺趣生活馆!");
            }else{
                return AjaxResult.error("手机号已存在 请重试!");
            }
        }else{
            return AjaxResult.error("身份证号已存在 请重试!");
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

    public boolean login(String phone, String inputPassword) {
        Teacher teacher = teacherMapper.findByPhone(phone);
        if (teacher == null) {
            return false;
        }
        String storedPasswordHashWithSalt = teacher.getPassword();
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
}
