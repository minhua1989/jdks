package com.xf.jdks.commons.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * 处理图片损坏
 * Created by Administrator on 2016/10/17.
 */
public class ThumbnailConvert {
    private static String CMYK_COMMAND = "mogrify -colorspace RGB -quality 80 file1";//转换cmyk格式

    public static void setCMYK_COMMAND(String file1) {
        exeCommand(CMYK_COMMAND.replace("file1", file1));
    }

    public static boolean exeCommand(String cmd){
        InputStreamReader ir = null;
        LineNumberReader input = null;
        try
        {
            //linux下java执行指令：Runtime.getRuntime().exec (String str);
            Process process = Runtime.getRuntime().exec (cmd);
            ir=new InputStreamReader(process.getInputStream());
            input = new LineNumberReader (ir);
            while ((input.readLine ()) != null){
            }
            ir.close();
            input.close();
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
