package com.yan.architect.architectdemo.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;
import java.io.Serializable;
/**
 * <p>
 * 用户表
 * </p>
 * @description: 用户表
 * @author: Mr.Yan
 * @date: Created in 2020-09-01 00:01:46
 * @copyright: Copyright (c) 2020
 */
@Data
@NoArgsConstructor
@ApiModel(description = "UserDTO")
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分页编号")
    private Integer pageNumber;

    @ApiModelProperty(value = "分页尺寸")
    private Integer pageSize;
    /**
     * 用户id 自增
     */
    @ApiModelProperty(value = "用户id 自增")
    private Long id;
    /**
     * 账号 
     */
    @ApiModelProperty(value = "账号 ")
    private String account;
    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 性别 0 - 女 1 - 男 其他 - 其他
     */
    @ApiModelProperty(value = "性别 0 - 女 1 - 男 其他 - 其他")
    private Integer sex;
    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;
    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;
    /**
     * 创建时间 默认当前时间
     */
    @ApiModelProperty(value = "创建时间 默认当前时间")
    private LocalDateTime createTime;
    /**
     * 更新时间 默认当前时间 根据当前时间戳更新
     */
    @ApiModelProperty(value = "更新时间 默认当前时间 根据当前时间戳更新")
    private LocalDateTime updateTime;

}
