package com.xf.jdks.exceptions;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 不能正确生成可执行SQL语句
 */
public class CanNotBeExecuteError extends BaseException{

    public CanNotBeExecuteError(){
        this(10001);
    }

    public CanNotBeExecuteError(Integer code) {
        super(code);
    }
}
