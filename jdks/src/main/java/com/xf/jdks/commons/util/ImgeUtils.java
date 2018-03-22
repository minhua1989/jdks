package com.xf.jdks.commons.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGDecodeParam;
import com.sun.image.codec.jpeg.JPEGImageDecoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 验证图片是否损坏，图片格式为jpg
 * Created by Administrator on 2016/10/17.
 */
public class ImgeUtils {


    public  static  void  main(String[] arge) {
        //测试单个文件
//        System.out.println(checkImge("D:\\as\\4.jpg"));
        String localImagePath = "D:\\as\\8.jpg";
        String localImagePath_1 = "D:\\as\\333.jpg";

        File imgFile = new File(localImagePath);
        if (!imgFile.isFile()) {
            System.out.println("不是图片");
        }
        Image img;
        try {
            img = ImageIO.read(imgFile);
            if (imgFile.length() == 0 || img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                System.out.println("不是图片");
            }
            addWaterMark(localImagePath,localImagePath_1,0,0,0.5f);
        } catch (Exception e) {
            System.out.println("不是图片");
        }
    }

    /**
     *图片安全处理,清除图片中的病毒代码
     * @param srcImg 图片路径
     * @param waterImg  水印图片路径
     * @param x x轴位置
     * @param y y轴位置
     * @param alpha alpha值,范围(0 - 1)之间,0:完全透明,1:完全不透明
     * @throws IOException
     */
    public final static void addWaterMark(String srcImg, String waterImg, int x, int y, float alpha) throws IOException {
        // 加载目标图片
        File file = new File(srcImg);
        String ext = srcImg.substring(srcImg.lastIndexOf(".") + 1);
        Image image = ImageIO.read(file);
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        // 将目标图片加载到内存。
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        // 加载水印图片。
        Image waterImage = ImageIO.read(new File(waterImg));
        int width_1 = waterImage.getWidth(null);
        int height_1 = waterImage.getHeight(null);
        // 设置水印图片的透明度。
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        // 设置水印图片的位置。
        int widthDiff = width - width_1;
        int heightDiff = height - height_1;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }

        // 将水印图片“画”在原有的图片的制定位置。
        g.drawImage(waterImage, x, y, width_1, height_1, null);
        // 关闭画笔。
        g.dispose();

        // 保存目标图片。
        ImageIO.write(bufferedImage, ext, file);
    }


    /**
     * 单张图片校验是否损坏
     * @param filePath 文件路径：D:\\imge\\admin.jpg
     * @return 是否损坏，false损坏，true正常
     * @throws IOException
     */
    public static boolean checkImge(String filePath) {
        boolean isImge = true;
        File file = new File(filePath);
//        String fileName = file.getName();
//        //当图片文件为jpg的时候校验图片是否损坏
//        FileInputStream input = null;
////        FileInputStream inputs = null;
//        try {
//            input = new FileInputStream(file);
////            inputs = new FileInputStream(file);
//            if (fileName.endsWith(".jpg")) {
//                JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(input);
//                BufferedImage image = decoder.decodeAsBufferedImage();
//                JPEGDecodeParam param = decoder.getJPEGDecodeParam();
//                isImge = true;
//                //getXDensity方法： 获取此值的水平像素密度从APP0符号。它不是由JPEG编码的应用。如果APP0标记是不存在的，那么你不能依靠这个值。
////                int xDensity = param.getXDensity();
////                int yDensity = param.getYDensity();
////                BufferedImage sourceImg = ImageIO.read(inputs);
////                double mm = Double.valueOf(String.format("%.1f", file.length() / 1024.0));
////                int width = sourceImg.getWidth();
////                int height = sourceImg.getHeight();
////                if (width != 358 || height != 441 || mm < 15 || xDensity != 96 || yDensity != 96) {
////
////                }
//            }
//        }catch (Exception e) {
//            //异常表示图片损坏改为false
//            isImge= false;
////            e.printStackTrace();
//        }finally {
//            //关闭流
//            if(null!=input) {
//                try {
//                    input.close();
////                    inputs.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        Image img;
        try {
            img = ImageIO.read(file);
            if (file.length() == 0 || img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                System.out.println(img+"------"+file);
                isImge = false;
            }
            addWaterMark(filePath,filePath,0,0,0);
        } catch (Exception e) {
            e.printStackTrace();
            isImge = false;
        }
        return isImge;
    }

    /**
     * 验证图片是否损坏
     * @param file 文件路径：D:\\imge\\
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void check(File file) throws FileNotFoundException, IOException {

        File[] files = file.listFiles();
        for (File f : files) {
            try {
                if (f.isDirectory()) {
                    check(f);
                } else {
                    String filename = f.getName();

                    if (filename.endsWith(".jpg")) {
                        JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream(f));
                        //读取图片文件
                        BufferedImage image = decoder.decodeAsBufferedImage();
                        JPEGDecodeParam param = decoder.getJPEGDecodeParam();
//                        int xDensity = param.getXDensity();
//                        int yDensity = param.getYDensity();
                        BufferedImage sourceImg = ImageIO.read(new FileInputStream(f));
                        double mm = Double.valueOf(String.format("%.1f", f.length() / 1024.0));
                        int width = sourceImg.getWidth();
                        int height = sourceImg.getHeight();
                        //验证图片大小
//                        if (width != 358 || height != 441 || mm < 15 || xDensity != 96 || yDensity != 96) {
//                            System.out.println(xDensity);
//                            System.out.println(yDensity);
//                            System.out.println(mm); //大小
//                            System.out.println(sourceImg.getWidth());  //像素
//                            System.out.println(sourceImg.getHeight()); //像素
//                            System.out.println(filename);
//                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
//                System.out.println(f.getName());
                go1(f);
            }
        }
    }


    /**
     * 处理被损坏的图片
     *
     * @param file
     */
    public static void go1(File file) {
        BufferedImage bis = null;
        try {
            bis = ImageIO.read(file);
            bis.getWidth();
            bis.getHeight();
        } catch (Exception e) {
            try {
                //这两句代码，是处理cmyk类型的图片，需要ImageMagick的支持
                ThumbnailConvert tc = new ThumbnailConvert();
                tc.setCMYK_COMMAND(file.getPath());
                Image image = null;
                image = Toolkit.getDefaultToolkit().getImage(file.getPath());
                MediaTracker mediaTracker = new MediaTracker(new Container());
                mediaTracker.addImage(image, 0);
                mediaTracker.waitForID(0);
//                System.out.println(image.getWidth(null));
//                System.out.println(image.getHeight(null));
                double mm = Double.valueOf(String.format("%.1f", file.length() / 1024.0));
                JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(new FileInputStream(file));
                JPEGDecodeParam param = decoder.getJPEGDecodeParam();
                int xDensity = param.getXDensity();
//                System.out.println(xDensity);
                int yDensity = param.getYDensity();
//                System.out.println("chuio" + yDensity + "?");
//                System.out.println(mm);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 破损图片处理
     *
     * @param file
     */
    public static void go(File file) {
        BufferedImage bis = null;
        try {
            bis = ImageIO.read(file);
            bis.getWidth();
            bis.getHeight();
        } catch (Exception e) {
            try {
                ThumbnailConvert tc = new ThumbnailConvert();
                tc.setCMYK_COMMAND(file.getPath());
                Image image = null;
                image = Toolkit.getDefaultToolkit().getImage(file.getPath());
                MediaTracker mediaTracker = new MediaTracker(new Container());
                mediaTracker.addImage(image, 0);
                mediaTracker.waitForID(0);
                image.getWidth(null);
                image.getHeight(null);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}

