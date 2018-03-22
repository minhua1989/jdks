package com.xf.jdks.exceptions;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 没有找到对应的字典
 */
public class DictIsNotFoundException extends BaseException{
    private String msg;
    public DictIsNotFoundException() {
        super(10004);
    }
    public DictIsNotFoundException(String msg){
        super(10004);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
