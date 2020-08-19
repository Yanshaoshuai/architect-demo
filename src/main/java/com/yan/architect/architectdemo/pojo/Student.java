package com.yan.architect.architectdemo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * (Student)实体类
 *
 * @author ykk
 * @since 2020-08-18 18:52:19
 */
@Data
@Builder
public class Student {
    /**
     * 主键 自动增长
     */
    private Integer id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别 -- 0女 1男
     */
    private Integer sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 创建时间 代码端插入
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 更新时间 自动维护
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}