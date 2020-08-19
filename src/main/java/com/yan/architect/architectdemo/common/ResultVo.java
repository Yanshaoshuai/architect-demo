package com.yan.architect.architectdemo.common;

/**
 * @Author YSS
 * @Date 2020/8/18 18:54
 */
public class ResultVo<T> {

    /**
     * 状态码 0失败 1成功 2未登录 3没有权限
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public ResultVo(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVo(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static ResultVo result(ResultEnum resultEnum){
        return new ResultVo(resultEnum.code, resultEnum.message);
    }
    public static ResultVo resultWithData(ResultEnum resultEnum,Object data){
        return new ResultVo(resultEnum.code, resultEnum.message,data);
    }
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
