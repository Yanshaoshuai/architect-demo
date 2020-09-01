package com.yan.architect.architectdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import com.yan.architect.architectdemo.pojo.User;

/**
 * <p>
 * 用户
 * </p>
 *
 * @package:  com.yan.architect.architectdemo.mapper
 * @description: 用户
 * @author: Mr.Yan
 * @date: Created in 2020-09-01 13:37:28
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Mr.Yan
 */
@Component
public interface UserMapper extends BaseMapper<User> {

}
