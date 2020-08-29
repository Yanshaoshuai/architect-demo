package com.yan.architect.architectdemo.controller;

import com.yan.architect.architectdemo.common.entity.ResultEnum;
import com.yan.architect.architectdemo.common.entity.ResultVO;
import com.yan.architect.architectdemo.pojo.Student;
import com.yan.architect.architectdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * (Student)表控制层
 *
 * @author makejava
 * @since 2020-08-18 18:52:20
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    /**
     * 服务对象
     */
    @Autowired
    private StudentService studentService;


    @RequestMapping("getList")
    public ResultVO list(Student student) {
        List<Student> list = studentService.changeModel(studentService.queryAll(student));
        return ResultVO.result(ResultEnum.SUCCESS);
    }


    /**
     * 查询id
     */
    @RequestMapping("/{id}")
    public ResultVO detail(@PathVariable Integer id) {
        Student student = studentService.changePojo(studentService.queryById(id));
        return ResultVO.result(ResultEnum.SUCCESS);
    }

    /**
     * 新增
     */
    @RequestMapping("insert")
    public ResultVO addSave(Student student) {
        return ResultVO.result(ResultEnum.SUCCESS);
    }


    /**
     * 修改
     */
    @RequestMapping("update")
    public ResultVO update(Student student) {
        Integer i = studentService.update(student);
        return ResultVO.result(ResultEnum.SUCCESS);
    }

    /**
     * 删除
     */
    @RequestMapping("del/{id}")
    public ResultVO delete(@PathVariable Integer id) {
        Boolean i = studentService.deleteById(id);
        return ResultVO.result(ResultEnum.SUCCESS);
    }
}