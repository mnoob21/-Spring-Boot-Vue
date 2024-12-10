package com.auggie.student_server.mapper;

import com.auggie.student_server.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface AdminMapper {

    Admin findByPhone(Admin admin);
}
