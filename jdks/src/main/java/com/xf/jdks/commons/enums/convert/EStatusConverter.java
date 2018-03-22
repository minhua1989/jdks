package com.xf.jdks.commons.enums.convert;

import org.springframework.core.convert.converter.Converter;

import com.xf.jdks.commons.enums.EStatus;


public class EStatusConverter implements Converter<String, EStatus> {

	@Override
	public EStatus convert(String source) {
		return EStatus.getEnum(source);
	}

}
