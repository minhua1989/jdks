package com.xf.jdks.commons.util;

import com.xf.jdks.annotation.AuthorityDeclaration;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by root on 16-10-25.
 */
public class WordDocWriter {

    private XWPFDocument document;

    private String filePath;

    private List<Class<?>> classList;

    public WordDocWriter(String filePath){
        this.filePath = filePath;
    }

    public void writeDocument(List<Class<?>> classList) throws IOException {
        this.classList = classList;
        flushData();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        document.write(fos);
        fos.close();
    }

    private void flushData(){
        document = new XWPFDocument();
        int index = 1;
        for(Class<?> clz:classList){
            RequestMapping crm = clz.getAnnotation(RequestMapping.class);
            List<Method> methods = ClassScans.getMethodList(clz,RequestMapping.class);
            if(methods==null)continue;
            for(Method method:methods){
                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                AuthorityDeclaration ad = method.getAnnotation(AuthorityDeclaration.class);
                if(rm!=null&&ad!=null) {
                    XWPFParagraph headPh = document.createParagraph();
                    XWPFRun r = headPh.createRun();
                    r.setBold(true);
                    r.setText(index+"."+ad.desc()+":"+clz.getSimpleName()+"."+method.getName()+"()");
                    XWPFTable urlTab = document.createTable(4, 2);
                    urlTab.getRow(0).getCell(0).setText("接口URL");
                    urlTab.getRow(0).getCell(1).setText(crm.value()[0]+rm.value()[0]);
                    urlTab.getRow(1).getCell(0).setText("请求参数");
                    urlTab.getRow(1).getCell(1).setText(ad.reqParam());
                    urlTab.getRow(2).getCell(0).setText("返回报文");
                    urlTab.getRow(2).getCell(1).setText(ad.resBody());
                    urlTab.getRow(3).getCell(0).setText("失败信息");
                    String[] errArr = ad.errMsgs();
                    StringBuffer err = new StringBuffer();
                    if(errArr!=null){
                        for(int i=0,len=errArr.length;i<len;i++){
                            if(i>0)err.append(",");
                            err.append(i+1);
                            err.append("."+errArr[i]);
                        }
                    }
                    urlTab.getRow(3).getCell(1).setText(err.toString());
                    index++;
                }
            }
        }
    }
}
