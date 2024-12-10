package com.auggie.student_server.mapper;


import com.auggie.student_server.entity.Course;
import com.auggie.student_server.entity.CourseType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseTypeMapper {

    boolean addCourseType(CourseType courseType);

    CourseType findByName(CourseType courseType);

    List<CourseType> findAll(CourseType courseType);

    List<CourseType> findBySearch(@Param("ctype_id") Integer ctype_id,
                                     @Param("ctype_name") String ctype_name, @Param("fuzzy") Integer fuzzy);

    boolean deleteById(Integer ctype_id);

    CourseType findById(Integer ctype_id);

    boolean updateCourseType(CourseType courseType);
}
