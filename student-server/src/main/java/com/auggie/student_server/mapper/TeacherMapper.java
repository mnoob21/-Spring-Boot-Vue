package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Student;
import com.auggie.student_server.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@Mapper
public interface TeacherMapper {
    //    select
    public List<Teacher> findAll();

    public Teacher findById(@Param("tid") Integer tid);

    Teacher findByPhone(@Param("phone") String phone);

    public List<Teacher> findBySearch(@Param("tid") Integer tid, @Param("tname") String tname, @Param("fuzzy") Integer fuzzy);

    //    update
    public boolean updateById(@Param("teacher") Teacher teacher);

    //    insert
    public boolean save(@Param("teacher") Teacher teacher);

    //    delete
    public boolean deleteById(@Param("tid") Integer tid);

    //教师注册
    public void tRegister(Teacher teacher);

    //教师身份证是否存在验证
    List<Teacher> idExist(Teacher teacher);

    //教师手机号是否存在验证
    List<Teacher> phoneExist(Teacher teacher);
}
