package com.xf.jdks.commons.util;


import com.xf.jdks.annotation.RuleMethod;
import com.xf.jdks.annotation.ValidateClass;

import java.util.regex.Pattern;

/**
 * Created by Rio-Lee on 2016/6/10.
 * 正则表达式检查
 */
@ValidateClass
public class RegxChecks {
    private static final String MOBILE_REGX = "1[3,5,6,7,8][0-9]{9}";//手机号
    private static final String PHONE_REGX = "0[0-9]{2,3}-[0-9]{7,8}";//座机号
    private static final String BIRTHDAY_REGX = "(19|20)[0-9]{2}(0[1-9]|[10,11,12])(0[1-9]|[1-2][0-9]|3[0,1])";//生日1900-2099
    private static final String NUMBER_REGX = "\\d+";//数字
    private static final String REG_CARDID = "^[1-9][0-9]{5}(19[0-9]{2}|20[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$";// 身份证号
    private static final String REG_ENANDSPACE = "([a-zA-Z]|\\s)+";// 英文或空格
    private static final String REG_CHINESE = "[\\u4E00-\\u9FA5]+";// 是否包含汉字
    private static final String REG_SPACE = "(\\s)+";// 是否包含空格

    protected static boolean matchRegx(String value, String regx) {
        return value != null && value.matches(regx);
    }

    /**
     * @param arg 需要被校验的字符串
     * @return 如果为数字返回True
     */
    @RuleMethod(ruledesc = "校验是否为数字")
    public static boolean isNumber(String arg) {
        return matchRegx(arg, NUMBER_REGX);
    }

    /**
     * @param arg 需要被校验的字符串
     * @return 如果为生日返回True
     */
    @RuleMethod(ruledesc = "校验是否为生日")
    public static boolean isBirthday(String arg) {
        return matchRegx(arg, BIRTHDAY_REGX);
    }

    /**
     * @param value 需要被校验的字符串
     * @return 如果为座机号返回True
     */
    @RuleMethod(ruledesc = "校验是否为座机号")
    public static boolean isPhoneNumber(String value) {
        return matchRegx(value, PHONE_REGX);
    }

    /**
     * @param value 需要被校验的字符串
     * @return 如果为手机号返回True
     */
    @RuleMethod(ruledesc = "校验是否为手机号")
    public static boolean isMobileNumber(String value) {
        return matchRegx(value, MOBILE_REGX);
    }

    /**
     * 是否符合身份证格式
     *
     * @param arg
     * @return
     */
    public static boolean isCardId(String arg) {
        return arg == null ? false : arg.matches(REG_CARDID);
    }

    /**
     * 是否由英文和空格组成
     *
     * @param arg
     * @return
     */
    public static boolean isEnglishOrSpace(String arg) {
        return arg == null ? false : arg.matches(REG_ENANDSPACE);
    }

    /**
     * 是否包含中文
     *
     * @param arg
     * @return
     */
    public static boolean hasChinese(String arg) {
        return arg == null ? false : Pattern.compile(REG_CHINESE).matcher(arg)
                .find();
    }

    /**
     * 是否包含空格
     *
     * @param arg
     * @return
     */
    public static boolean hasSpace(String arg) {
        return arg == null ? false : Pattern.compile(REG_SPACE).matcher(arg)
                .find();
    }

}
