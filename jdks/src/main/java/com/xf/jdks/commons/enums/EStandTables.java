package com.xf.jdks.commons.enums;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 标准表枚举
 */
public enum EStandTables {
    性别("1", 1), 有效性("2", 2), 管理员类型("3", 3), 考务员类型("4", 4), 安全等级("5", 5), 
    出题顺序("6", 6), 答案顺序("7", 7), 证件类型("8", 8);
	
	
    EStandTables(String value, int ordinal) {
        this.name = value;
        this.ordinal = ordinal;
    }

    private String name;

    private int ordinal;

    public String getName() {
        return this.name;
    }

    public int getOrdinal() {
        return this.ordinal;
    }

    public static EStandTables createOf(String name) {
        for (EStandTables item : EStandTables.values()) {
            if (item.getName().equals(name.toUpperCase())) return item;
        }
        throw new IllegalArgumentException();
    }


    @Override
    public String toString() {
        return this.name.toLowerCase();
    }
}

