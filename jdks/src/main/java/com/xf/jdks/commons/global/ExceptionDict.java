package com.xf.jdks.commons.global;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Rio-Lee on 2016/7/12.
 * 异常信息字典
 */
public class ExceptionDict {

    private static Properties properties=new Properties();

    public static void loadProperties(InputStream inputStream) throws IOException {
        properties.load(inputStream);
    }

    public static String getExceptionMessageByExceptionCode(String code){
       return properties.getProperty(code);
    }

    public static String getExceptionMessageByExceptionCode(Integer code){
        return getExceptionMessageByExceptionCode(code.toString());
    }
}
