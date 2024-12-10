package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CourseCommentsMapper {

    List<Comments> findAllByCid(int cid);

    void insertComment(Comments comments);

    void deleteComment(int commentId);
}
