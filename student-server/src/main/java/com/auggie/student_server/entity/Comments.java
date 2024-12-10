package com.auggie.student_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comments {
    private Integer ccomment_id;
    private String ccomment_content;
    private String ccomment_author;
    private String ccomment_time;
    private Integer c_id;

}
