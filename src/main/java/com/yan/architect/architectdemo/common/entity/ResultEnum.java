package com.yan.architect.architectdemo.common.entity;

/**
 * @Author YSS
 * @Date 2020/8/18 19:04
 */
public enum ResultEnum {
    /*
     * 操作成功
     */
    SUCCESS(200,"操作成功"),
    /*
     * 无法判断
     */
    ERROR_PARAMS(402,"参数信息有误"),
    /*
     * 提示信息
     */
    ERROR_MSG(502,"系统异常");

    /**
     * 编码
     */
    public int code;

    /**
     * 消息
     */
    public String message;

     ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
