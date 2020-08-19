package com.yan.architect.architectdemo.service;

import com.yan.architect.architectdemo.mapper.StudentMapper;
import com.yan.architect.architectdemo.pojo.Student;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * (Student)表服务实现类
 *
 * @author makejava
 * @since 2020-08-18 18:52:20
 */
@Service("studentService")
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    /**
     * 通过ID查询单条数据
     */
    @Override
    public Student queryById(Integer id) {
        return this.studentMapper.queryById(id);
    }

    /**
     * 查询全部
     */
    @Override
    public List<Student> queryAll(Student student) {
        return this.studentMapper.queryAll(student);
    }

    /**
     * 查总数
     */
    @Override
    public Integer getCount() {
        return this.studentMapper.getCount();
    }


    /**
     * 新增数据
     */
    @Override
    public Student insert(Student student) {
        this.studentMapper.insert(student);
        return student;
    }

    /**
     * 修改数据
     */
    @Override
    public Integer update(Student student) {
        return this.studentMapper.update(student);
    }

    /**
     * 通过主键删除数据
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.studentMapper.deleteById(id) > 0;
    }


    /**
     * 转换方法
     */
    @Override
    public Student changePojo(Student student) {
        //这里写转换逻辑
        return student;
    }

    /**
     * 列表转换
     */
    @Override
    public List<Student> changeModel(List<Student> student) {
        List<Student> result = new ArrayList<Student>();
        if (null != student) {
            for (Student data : student) {
                result.add(changePojo(data));
            }
        }
        return result;
    }
}