package com.huachuan.controller;


import com.huachuan.dao.StudentMapper;
import com.huachuan.domain.Student;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    @Resource
    StudentMapper mapper;
    @RequestMapping("/hello")
    @ResponseBody
    String hello() {
        System.out.println("hello world");
        List<Student> students = mapper.queryAll();
        System.out.println(students);
        return "{\"topic\":\"hello world\"}";
    }
}
