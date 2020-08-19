package com.yan.architect.architectdemo.mapper;

import com.yan.architect.architectdemo.pojo.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (Student)表数据库访问层
 *
 * @author makejava
 * @since 2020-08-18 18:52:19
 */
@Mapper
public interface StudentMapper {

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
     * @return 影响行数
     */
    int insert(Student student);

    /**
     * 修改数据
     *
     * @param student 实例对象
     * @return 影响行数
     */
    int update(Student student);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}