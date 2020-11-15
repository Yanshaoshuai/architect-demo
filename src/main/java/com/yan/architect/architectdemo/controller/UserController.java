package com.yan.architect.architectdemo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yan.architect.architectdemo.common.PageResult;
import com.yan.architect.architectdemo.common.R;
import com.yan.architect.architectdemo.pojo.ArtCrowd;
import com.yan.architect.architectdemo.pojo.User;
import com.yan.architect.architectdemo.pojo.dto.UserDTO;
import com.yan.architect.architectdemo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 用户
 * </p>
 *
 * @package:  com.yan.architect.architectdemo.controller
 * @description: 用户
 * @author: Mr.Yan
 * @date: Created in 2020-09-01 13:37:28
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Mr.Yan
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api("UserController")
public class UserController {
    @Autowired
    private  UserService userService;

    /**
     * 分页查询用户
     * @param userDTO 数据传输对象
     * @return R
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询用户", notes = "分页查询用户")
    public R listUser(@ModelAttribute(name = "数据传输对象") UserDTO userDTO) {
        log.info("listUser-->{}",userDTO);
        Page page=new Page(userDTO.getPageNumber(),userDTO.getPageSize());
        User user=new User();
        BeanUtils.copyProperties(userDTO,user);
        userService.page(page, Wrappers.query(user));
        PageResult<User> pageResult=new PageResult(page.getTotal(),page.getCurrent(),page.getSize(),page.getRecords());
        return R.success(pageResult);
    }


    /**
     * 通过id查询用户
     * @param id id
     * @return R
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过id查询用户", notes = "通过id查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true,paramType="path")
    })
    public R getUser(@PathVariable("id") Long id){
      log.info("getUser-->{}",id);
      return R.success(userService.getById(id));
    }

    /**
     * 新增用户
     * @param user 用户
     * @return R
     */
    @PostMapping
    @ApiOperation(value = "新增用户", notes = "新增用户")
    public R saveUser(@RequestBody User user){
      log.info("saveUser-->{}",user);
      return R.success(userService.save(user));
    }

    /**
     * 修改用户
     * @param user 用户
     * @return R
     */
    @PutMapping
    @ApiOperation(value = "修改用户", notes = "修改用户")
    public R updateUser(@RequestBody User user){
      log.info("updateUser-->{}",user);
      return R.success(userService.updateById(user));
    }

    /**
     * 通过id删除用户
     * @param id id
     * @return R
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true,paramType="path")
    })
    public R deleteUser(@PathVariable Long id){
      log.info("deleteUser-->{}",id);
      return R.success(userService.removeById(id));
    }

    /**
     * excel导入
     */
    @PostMapping(value = "/import",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "导入excel", notes = "导入excel")
    public R importUserExcel(@ApiParam(value = "file", required = true)MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<ArtCrowd> artCrowds = userService.importExcel(inputStream);
        return R.success(artCrowds);
    }
}
