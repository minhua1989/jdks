package com.xf.jdks.exceptions;

import com.xf.jdks.commons.global.ExceptionDict;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by Rio-Lee on 2016/7/12.
 */
public class BaseException extends Exception{

    protected String code;

    protected String msg;

    public BaseException(Integer code){
        String msg = ExceptionDict.getExceptionMessageByExceptionCode(code);
        this.msg=msg==null?this.code:msg;
    }

    /**
     * 新增抛出异常 向立军 2016年12月26日11:06:53
     * @param mes
     */
    public  BaseException(String mes){
        this.msg = mes;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

    @Override
    public void printStackTrace() {
        System.out.println("业务处理失败，失败原因："+this.msg);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        System.out.println(this.msg);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        System.out.println(this.msg);
    }
}
