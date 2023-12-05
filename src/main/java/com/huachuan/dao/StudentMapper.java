package com.huachuan.dao;

import com.huachuan.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface StudentMapper {
    //查询所有的学生
    List<Student> queryAll();

}
