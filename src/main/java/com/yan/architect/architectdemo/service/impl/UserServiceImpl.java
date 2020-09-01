package com.yan.architect.architectdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yan.architect.architectdemo.pojo.User;
import com.yan.architect.architectdemo.mapper.UserMapper;
import com.yan.architect.architectdemo.service.UserService;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
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

}
