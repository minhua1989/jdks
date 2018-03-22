package com.xf.jdks.exceptions;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 表名为空或不正确异常
 */
public class TableNameNullOrNotRightError extends BaseException{
    public TableNameNullOrNotRightError(){
        super(10008);
    }
}
