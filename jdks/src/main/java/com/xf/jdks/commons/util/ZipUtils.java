package com.xf.jdks.commons.util;

import com.xf.jdks.commons.componet.FileIOComponent;
import com.xf.jdks.commons.global.FileInfoPo;
import com.xf.jdks.exceptions.FileTypeNotFoundException;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.List;

/**
 * Created by root on 16-8-13.
 */
public class ZipUtils {
    static final int BUFFER = 8192;
    private static final String CHINESE_CHARSET = "GBK";
    private FileInfoPo zipPo;
    private File zipFile;

    public ZipUtils() throws FileTypeNotFoundException {
        FileInfoPo file = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE, "zip");
        this.zipPo = file;
        this.zipFile = file.getFileObject();
    }

    public ZipUtils(String fileName) throws FileTypeNotFoundException {
        FileInfoPo file = FileIOComponent.createTempFilePathByType(FileIOComponent.EXPORT_TYPE, "zip",fileName);
        this.zipPo = file;
        this.zipFile = file.getFileObject();
    }

    //压缩文件
    public FileInfoPo packToZip(List<File> files){
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)));
            zos.setEncoding(CHINESE_CHARSET);//避免中文乱码
            for (File f : files) {
                InputStream ios = null;
                try {
                    if (!f.exists()) continue;
                    ios = new BufferedInputStream(new FileInputStream(f));
                    zos.putNextEntry(new ZipEntry(f.getName()));
                    byte[] bytes = new byte[2048];
                    int read = 0;
                    while (-1 != (read = ios.read(bytes, 0, bytes.length))) {
                        zos.write(bytes, 0, read);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("文件路径没有找到");
                } finally {
                    if (ios != null) try {
                        ios.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (zos != null) {
                try {
                    zos.flush();
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.zipPo;
    }

    private void compress(File file, ZipOutputStream out, String basedir) {
        /* 判断是目录还是文件 */
        if (file.isDirectory()) {
            System.out.println("压缩：" + basedir + file.getName());
            this.compressDirectory(file, out, basedir);
        } else {
            System.out.println("压缩：" + basedir + file.getName());
            this.compressFile(file, out, basedir);
        }
    }

    /** 压缩一个目录 */
    private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
        if (!dir.exists())
            return;

        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            /* 递归 */
            compress(files[i], out, basedir + dir.getName() + "/");
        }
    }

    /** 压缩一个文件 */
    private void compressFile(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()) {
            return;
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            out.putNextEntry(entry);
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                out.write(data, 0, count);
            }
            bis.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
