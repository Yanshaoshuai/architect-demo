package com.yan.architect.architectdemo.pojo;

import com.github.liaochong.myexcel.core.annotation.ExcelColumn;
import lombok.Data;

import java.util.Date;

/**
 * @Author Mr.Yan
 * @Date 2020 / 11 /15 18:58
 **/
@Data
public class ArtCrowd {
    // index代表列索引，从0开始
    @ExcelColumn(index = 0)
    private String name;

    @ExcelColumn(index = 1)
    private Integer age;

    @ExcelColumn(index = 2,dateFormatPattern="yyyy-MM-dd")
    private Date birthday;
}
