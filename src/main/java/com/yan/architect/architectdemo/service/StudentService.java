package com.yan.architect.architectdemo.service;

import com.yan.architect.architectdemo.pojo.Student;

import java.util.List;

/**
 * (Student)表服务接口
 *
 * @author makejava
 * @since 2020-08-18 18:52:20
 */
public interface StudentService {

    /**
     * 查询全部
     */
    List<Student> queryAll(Student student);

    /**
     * 查总数
     */
    Integer getCount();

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Student queryById(Integer id);


    /**
     * 新增数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    Student insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 实例对象
     */
    Integer update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 转换方法
     */
    Student changePojo(Student student);

    /**
     * 列表转换
     */
    List<Student> changeModel(List<Student> student);

}