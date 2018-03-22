package com.xf.jdks.commons.util;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * Created by root on 16-10-20.
 */
public class ApacheZipTool {

    private ApacheZipTool(){}

    private ApacheZipTool(String zipPath,int buffSize){
        this.buffSize = buffSize;
        this.zipPath = zipPath;
    }

    private int buffSize;

    private String zipPath;

    public int getBuffSize() {
        return buffSize;
    }

    public void setBuffSize(int buffSize) {
        this.buffSize = buffSize;
    }

    public String getZipPath() {
        return zipPath;
    }

    public void setZipPath(String zipPath) {
        this.zipPath = zipPath;
    }

    public void unZip(String destRoot){
        File dir = null;
        if(destRoot!=null) {
            dir = new File(destRoot);
            if (!dir.isDirectory()) return;
            if (!dir.exists()) dir.mkdirs();
        }
        File tmp;
        FileOutputStream out;
        InputStream in;
        ZipFile zip;
        byte[] data = new byte[getBuffSize()];
        try {
            zip = new ZipFile(getZipPath());
            Enumeration items = zip.getEntries();
            while (items.hasMoreElements()){
                ZipEntry item = (ZipEntry) items.nextElement();
                if(dir!=null){
                    tmp = new File(dir,item.getName());
                }else {
                    tmp = new File(item.getName());
                }
                if (tmp.isDirectory()){
                    tmp.mkdirs();
                }else {
                    File parent = tmp.getParentFile();
                    if (!parent.exists()){
                        parent.mkdirs();
                    }
                    in = zip.getInputStream(item);
                    out = new FileOutputStream(tmp);
                    while ((buffSize=in.read(data))>0){
                        out.write(data,0,buffSize);
                    }
                    out.close();
                    in.close();
                }
                zip.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
