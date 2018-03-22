package com.xf.jdks.exceptions;

/**
 * Created by J-Ren on 2016/8/20.
 * 缺少条件参数
 */
public class MissingConditionException extends BaseException{
    public MissingConditionException() {
        super(10009);
    }
}
