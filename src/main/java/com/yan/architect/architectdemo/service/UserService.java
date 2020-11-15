package com.yan.architect.architectdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yan.architect.architectdemo.pojo.ArtCrowd;
import com.yan.architect.architectdemo.pojo.User;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @package:  com.yan.architect.architectdemo.service
 * @description: 用户
 * @author: Mr.Yan
 * @date: Created in 2020-09-01 13:37:28
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Mr.Yan
 */
public interface UserService extends IService<User> {
    /**
     * excel导入
     * @param inputStream
     * @return
     */
    public List<ArtCrowd> importExcel(InputStream inputStream);
}
