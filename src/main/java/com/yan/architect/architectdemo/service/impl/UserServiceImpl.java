package com.yan.architect.architectdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.liaochong.myexcel.core.SaxExcelReader;
import com.yan.architect.architectdemo.pojo.ArtCrowd;
import com.yan.architect.architectdemo.pojo.User;
import com.yan.architect.architectdemo.mapper.UserMapper;
import com.yan.architect.architectdemo.service.UserService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @package: com.yan.architect.architectdemo.service.impl
 * @description: 用户
 * @author: Mr.Yan
 * @date: Created in 2020-09-01 13:37:28
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Mr.Yan
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public List<ArtCrowd> importExcel(InputStream inputStream) {
        // 方式一：全部读取后处理，SAX模式，避免OOM，建议大量数据使用
        List<ArtCrowd> result = SaxExcelReader.of(ArtCrowd.class)
                .sheet(0) // 0代表第一个，如果为0，可省略该操作，也可sheet("名称")读取
                .rowFilter(row -> row.getRowNum() > 0) // 如无需过滤，可省略该操作，0代表第一行
                .beanFilter(artCrowd -> artCrowd.getAge()>=20) // bean过滤
                .read(inputStream);// 可接收inputStream
        return result;
    }
}
