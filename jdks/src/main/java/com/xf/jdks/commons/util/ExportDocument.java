package com.xf.jdks.commons.util;

import com.xf.jdks.annotation.AuthorityDeclaration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by root on 16-10-25.
 */
public class ExportDocument {

    private ExportDocument(){}

    private String filePath;

    private Workbook workbook;

    private List<Class<?>> classList;

    public static ExportDocument createNewInstance(String filePath){
        ExportDocument rs = new ExportDocument();
        rs.filePath = filePath;
        return rs;
    }

    public void writeClassListDocument(List<Class<?>> classList) throws IOException {
        this.classList = classList;
        if(workbook==null){
            workbook = new XSSFWorkbook();
        }
        writeDocumnet();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    private void writeDocumnet(){
        Sheet sheet = workbook.createSheet();
        int index = 1;
        int rowIndex = 0;
        for(int i=0,len=classList.size();i<len;i++){
            Class<?> clz = classList.get(i);
            String className = clz.getSimpleName();
            RequestMapping crm =clz.getAnnotation(RequestMapping.class);
            List<Method> methods=null;
            if(crm!=null) {
                methods = ClassScans.getMethodList(clz, RequestMapping.class);
            }
            if(methods==null)continue;
            for(int j=0,jen=methods.size();j<jen;j++){
                Method method = methods.get(j);
                RequestMapping rm = method.getAnnotation(RequestMapping.class);
                AuthorityDeclaration ad = method.getAnnotation(AuthorityDeclaration.class);
                if(rm!=null && ad!=null){
                    String rowString = index+". "+ ad.desc() + ":"+className+"."+method.getName()+"()";
                    Row rowHead = sheet.createRow(rowIndex);
                    Row urlRow = sheet.createRow(rowIndex+1);
                    Row paramRow = sheet.createRow(rowIndex+2);
                    Row rsRow = sheet.createRow(rowIndex+3);
                    Row errRow = sheet.createRow(rowIndex+4);
                    rowHead.createCell(0).setCellValue(rowString);
                    CellRangeAddress cra = new CellRangeAddress(rowIndex,rowIndex,0,1);
                    sheet.addMergedRegion(cra);
                    urlRow.createCell(0).setCellValue("接口URL");
                    urlRow.createCell(1).setCellValue(crm.value()[0]+rm.value()[0]);
                    paramRow.createCell(0).setCellValue("请求参数");
                    paramRow.createCell(1).setCellValue("{}");
                    rsRow.createCell(0).setCellValue("返回报文");
                    rsRow.createCell(1).setCellValue("data:{}");
                    errRow.createCell(0).setCellValue("失败信息");
                    errRow.createCell(1).setCellValue("失败信息1,失败信息2");
                    rowIndex+=6;
                    index++;
                }
            }
        }
    }
}
