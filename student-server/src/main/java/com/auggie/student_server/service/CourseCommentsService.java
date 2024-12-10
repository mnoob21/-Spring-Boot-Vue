package com.auggie.student_server.service;

import com.auggie.student_server.entity.Comments;
import com.auggie.student_server.mapper.CourseCommentsMapper;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseCommentsService {

    @Autowired
    private CourseCommentsMapper courseCommentsMapper;

    public List<Comments> findAllByCid(int cid){

        return courseCommentsMapper.findAllByCid(cid);
    }

    public AjaxResult insertComment(Comments comments){
        courseCommentsMapper.insertComment(comments);
        return AjaxResult.success("发表成功!");
    }

    public AjaxResult deleteComment(int commentId) {
        courseCommentsMapper.deleteComment(commentId);
        return AjaxResult.success("删除成功！");
    }
}
