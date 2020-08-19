package com.yan.architect.architectdemo.common.global;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.yan.architect.architectdemo.common.ResultVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;
import java.net.ConnectException;

/**
 * @Description: 全局异常
 * Author lv bin
 * @date 2018/5/16 9:35
 * version V1.0.0
 */


@ControllerAdvice
public class GlobalExceptionHandler {

    protected static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResultVo<String> runtimeExceptionHandler(RuntimeException runtimeException) {
        return result(ErrorConstant.RUNTIME_EXCEPTION.getCode(), ErrorConstant.RUNTIME_EXCEPTION.getMsg(), runtimeException);
    }

    //空指针异常
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResultVo<String> nullPointerExceptionHandler(NullPointerException ex) {
        return result(ErrorConstant.NULL_POINTER_EXCEPTION.getCode(), ErrorConstant.NULL_POINTER_EXCEPTION.getMsg(), ex);
    }

    //类型转换异常
    @ExceptionHandler(ClassCastException.class)
    @ResponseBody
    public ResultVo<String> classCastExceptionHandler(ClassCastException ex) {
        return result(ErrorConstant.CLASS_CAST_EXCEPTION.getCode(), ErrorConstant.CLASS_CAST_EXCEPTION.getMsg(), ex);
    }

    //IO异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public ResultVo<String> iOExceptionHandler(IOException ex) {
        return result(ErrorConstant.IO_EXCEPTION.getCode(), ErrorConstant.IO_EXCEPTION.getMsg(), ex);
    }

    //未知方法异常
    @ExceptionHandler(NoSuchMethodException.class)
    @ResponseBody
    public ResultVo<String> noSuchMethodExceptionHandler(NoSuchMethodException ex) {
        return result(ErrorConstant.NO_SUCH_METHOD_EXCEPTION.getCode(), ErrorConstant.NO_SUCH_METHOD_EXCEPTION.getMsg(), ex);
    }

    //数组越界异常
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public ResultVo<String> indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
        return result(ErrorConstant.INDEX_OUT_OF_BOUNDS_EXCEPTION.getCode(), ErrorConstant.INDEX_OUT_OF_BOUNDS_EXCEPTION.getMsg(), ex);
    }

    //网络异常
    @ExceptionHandler(ConnectException.class)
    @ResponseBody
    public ResultVo<String> connectException(ConnectException ex) {
        return result(ErrorConstant.CONNECT_EXCEPTION.getCode(), ErrorConstant.CONNECT_EXCEPTION.getMsg(), ex);
    }

    //400错误
    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    public ResultVo<String> requestNotReadable(HttpMessageNotReadableException ex) {
        return result(ErrorConstant.BAD_REQUEST.getCode(), ErrorConstant.BAD_REQUEST.getMsg(), ex);
    }

    //400错误
    @ExceptionHandler({TypeMismatchException.class})
    @ResponseBody
    public ResultVo<String> requestTypeMismatch(TypeMismatchException ex) {
        return result(ErrorConstant.BAD_REQUEST.getCode(), ErrorConstant.BAD_REQUEST.getMsg(), ex);
    }

    //400错误
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    public ResultVo<String> requestMissingServletRequest(MissingServletRequestParameterException ex) {
        return result(ErrorConstant.BAD_REQUEST.getCode(), ErrorConstant.BAD_REQUEST.getMsg(), ex);
    }

    @ExceptionHandler({ServletException.class})
    @ResponseBody
    public ResultVo<String> http404(ServletException ex) {
        return result(ErrorConstant.NOT_FOUND_REQUEST.getCode(), ErrorConstant.NOT_FOUND_REQUEST.getMsg(), ex);
    }

    //405错误
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    public ResultVo<String> request405(HttpRequestMethodNotSupportedException ex) {
        return result(ErrorConstant.METHOD_NOT_ALLOWED.getCode(), ErrorConstant.METHOD_NOT_ALLOWED.getMsg(), ex);
    }

    //406错误
    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseBody
    public ResultVo<String> request406(HttpMediaTypeNotAcceptableException ex) {
        return result(ErrorConstant.NOT_ACCEPTABLE.getCode(), ErrorConstant.NOT_ACCEPTABLE.getMsg(), ex);
    }

    //500错误
    @ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
    @ResponseBody
    public ResultVo<String> server500(RuntimeException runtimeException) {
        return result(ErrorConstant.INTERNAL_SERVER_ERROR.getCode(), ErrorConstant.INTERNAL_SERVER_ERROR.getMsg(), runtimeException);
    }


    @ExceptionHandler({JsonMappingException.class})
    @ResponseBody
    public ResultVo<String> jsonMappingException(JsonMappingException jsonMappingException) {
        return result(ErrorConstant.ERROR_FORMAT_PARAMETER.getCode(), ErrorConstant.ERROR_FORMAT_PARAMETER.getMsg(), jsonMappingException);
    }


    /**
     * 结果集
     *
     * @param errCode
     * @param errMsg
     * @param e
     * @return
     */


    private ResultVo<String> result(int errCode, String errMsg, Exception e) {
        ResultVo<String> ResultVo = new ResultVo<String>(errCode, errMsg);

        logException(e);

        return ResultVo;
    }

    /**
     * 异常记录
     *
     * @param e
     */

    /**
     * app web 异常
     * @param appWebException
     * @return
     */
   /* @ExceptionHandler({AppWebException.class})
    @ResponseBody
    public ResultVo<String> appWebException(AppWebException appWebException) {
        return result(appWebException.getErrCode(), appWebException.getMessage(), appWebException);
    }*/

    private void logException(Exception e) {
       /* if(e instanceof AppWebException){
            LOGGER.warn(e.getMessage(), e);
        }else{
            LOGGER.error(e.getMessage(), e);
        }*/
        LOGGER.error(e.getMessage(), e);
    }
}