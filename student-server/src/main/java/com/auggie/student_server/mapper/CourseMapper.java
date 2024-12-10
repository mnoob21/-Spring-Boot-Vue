package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
@Mapper
public interface CourseMapper {
//    select
    public List<Course> findBySearch(@Param("cid") Integer cid,
                                     @Param("cname") String cname, @Param("fuzzy") Integer fuzzy,
                                     @Param("ctype_name") String ctype_name,@Param("c_author") String c_author,@Param("cstatus") int cstatus);

//    insert
    public boolean insertCourse(@Param("course") Course course);

//    update
    public boolean updateById(@Param("course") Course course);

//    delete
    public boolean deleteById(@Param("cid") Integer cid);

    boolean review(Course course);

    boolean overrule(Course course);

    boolean openCourse(Integer cid);

    boolean terminateCourse(Integer cid);
}
