package com.xf.jdks.commons.enums;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 运算符枚举
 */
public enum EOperators {
    等于("="),
    不等于("!="),
    大于(">"),
    大于等于(">="),
    小于("<"),
    小于等于("<="),
    包含("in"),
    不包含("not in"),
    类似("like"),
    为空(" is null "),
    不为空("is not null");

    EOperators(String value) {
        this.value = value;
    }

    private String value;

    @Override
    public String toString() {
        return this.value;
    }
}
