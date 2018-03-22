package com.xf.jdks.commons.util;

import org.apache.commons.codec.digest.DigestUtils;

//ORACLE T_SB_ADMININFO上报TW_SCHOOLCONTACT--MD5加密
public final class MD5Encrypt {

    private static final ThreadLocal<MD5Encrypt> local = new ThreadLocal<MD5Encrypt>();

    private MD5Encrypt() {
        super();
    }

    public static MD5Encrypt getEncrypt() {
        MD5Encrypt encrypt = local.get();
        if (encrypt == null) {
            encrypt = new MD5Encrypt();
            local.set(encrypt);
        }
        return encrypt;
    }

    public static String encodeMD5(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex(s);
    }

    public static String encodeSHA(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.shaHex(s);
    }

    public static void main(String arg[]){
        System.out.println("MD5: " + MD5Encrypt.getEncrypt().encodeMD5("zhouyh123"));
    }
}