package com.yan.architect.architectdemo.common.exception;

import com.yan.architect.architectdemo.common.entity.ErrorEntity;

/**
 * @Author Mr.Yan
 * @Date 2020 / 08 /29 20:13
 **/
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -2964851871181700073L;
    private String code;
    private Object data;
    private Integer level;

    public BusinessException(){
        super();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = String.valueOf(code);
    }

    public BusinessException(Integer code, String message, Integer level) {
        super(message);
        this.code = String.valueOf(code);
        this.level = level;
    }

    public BusinessException(ErrorEntity error) {
        super(error.getMessage());
        this.code = String.valueOf(error.getCode());
    }

    public BusinessException(String code, String message, Object data, Integer level) {
        super(message);
        this.code = code;
        this.level = level;
        this.data = data;
    }

    public BusinessException(String code, String message, Throwable t, Integer level) {
        super(message, t);
        this.code = code;
        this.level = level;
    }

    public BusinessException(String code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

