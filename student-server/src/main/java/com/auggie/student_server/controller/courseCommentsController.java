package com.auggie.student_server.controller;

import com.auggie.student_server.entity.Comments;
import com.auggie.student_server.service.CourseCommentsService;
import com.auggie.student_server.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/courseComments")
public class courseCommentsController {

    @Autowired
    private CourseCommentsService courseCommentsService;

    @GetMapping("/findByCourseId/{cid}")
    public List<Comments> findByCid(@PathVariable int cid){

        return courseCommentsService.findAllByCid(cid);
    }

    @PostMapping("/insertComment")
    public AjaxResult insertComment(@RequestBody Comments comments){
        System.out.println(comments);

        return AjaxResult.success(courseCommentsService.insertComment(comments));
    }

    @GetMapping("/deleteComment/{commentId}")
    public AjaxResult deleteComment(@PathVariable int commentId){

        return AjaxResult.success(courseCommentsService.deleteComment(commentId));
    }
}
