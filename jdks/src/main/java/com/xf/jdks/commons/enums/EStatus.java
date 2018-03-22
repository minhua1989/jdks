package com.xf.jdks.commons.enums;

import java.util.HashMap;
import java.util.Map;

public enum EStatus {

	有效("0"), 无效("1"), 男("1-1"),女("1-2"),系统管理员("0-0"),普通教师("0-1"),出卷老师 ("0-2"),H5("-5"),
	考评员("2-1"),考务("2-2"),技术支持("2-3");
	

	private String value;

	static Map<String, EStatus> enumMap = new HashMap<String, EStatus>();

	static {
		for (EStatus eStatus : EStatus.values()) {	
			enumMap.put(eStatus.getValue(), eStatus);
		}
	}

	private EStatus(String value){
		this.value = value;
	}

	public static EStatus getEnum(String value) {
		return enumMap.get(value);
	}

	
	
    
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
