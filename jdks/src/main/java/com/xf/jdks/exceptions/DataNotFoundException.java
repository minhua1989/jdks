package com.xf.jdks.exceptions;

/**
 * Created by Rio-Lee on 2016/6/14.
 * 找不到相应的数据
 */
public class DataNotFoundException extends BaseException{
    public DataNotFoundException() {
        super(10003);
    }
}
