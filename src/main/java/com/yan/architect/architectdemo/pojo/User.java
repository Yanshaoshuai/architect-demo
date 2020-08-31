package com.yan.architect.architectdemo.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
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
/**
 * <p>
 * 用户表
 * </p>
 *
 * @package:  com.yan.architect.architectdemo.entity
 * @description: 用户表
 * @author: Mr.Yan
 * @date: Created in 2020-09-01 00:01:46
 * @copyright: Copyright (c) 2020
 * @version: V1.0
 * @modified: Mr.Yan
 */
@Data
@NoArgsConstructor
@TableName("tb_user")
@EqualsAndHashCode(callSuper = true)
public class User extends Model<User> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id 自增
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 账号 
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别 0 - 女 1 - 男 其他 - 其他
     */
    private Integer sex;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 创建时间 默认当前时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间 默认当前时间 根据当前时间戳更新
     */
    private LocalDateTime updateTime;

}