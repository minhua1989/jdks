package com.xf.jdks.commons.util;

import com.xf.jdks.commons.global.DataMap;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Rio-Lee on 2016/7/3.
 * 导出Excel工具
 */
public class ExportExcel {

    private static final String JSON_STR = "\\{(\\w+:.+)(,\\w+:.+)*\\}";

    private Workbook workbook;

    private String filePath;

    private String firstRowString;

    private String secondRowString;

    private String threeRowString;//第三行，共计多少人

    private String lastTime = "打印日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

    private String lastRowString;

    private String  remarkString;

    private Map<String, String> dataHeadMap = new DataMap<>();

    private Map<String, Integer> headIndexMap = new DataMap<>();

    private List<Map<String, Object>> currentData = new ArrayList<>();

    private Map<String,Object> head2Map = new LinkedHashMap<>();//有序第二行表头

    public ExportExcel() {
    }

    public  void setThreeRowString(String threeRowString){this.threeRowString = threeRowString;}
    public  String  getThreeRowString() {
        return this.threeRowString;
    }

    public String getRemarkString() {
        return remarkString;
    }
    public void setRemarkString(String remarkString) {
        this.remarkString = remarkString;
    }

    public void setFirstRowString(String firstRowString) {
        this.firstRowString = firstRowString;
    }

    public void setSecondRowString(String secondRowString) {
        this.secondRowString = secondRowString;
    }

    public void setLastRowString(String lastRowString) {
        this.lastRowString = lastRowString;
    }

    public String getFirstRowString() {
        return firstRowString;
    }

    public String getSecondRowString() {
        return secondRowString;
    }

    public String getLastTime() {
        return lastTime;
    }

    public String getLastRowString() {
        return lastRowString;
    }

    private String searchKeyByValue(String val) {
        for (Map.Entry<String, String> data : dataHeadMap.entrySet()) {
            if (data.getValue().equals(val)) return data.getKey();
        }
        return null;
    }

    private String searchKeyByIndex(int i){
        for(Map.Entry<String,Integer> entry:headIndexMap.entrySet()){
            if(i==entry.getValue())return entry.getKey();
        }
        return null;
    }

    public List<Map<String, Object>> readExcel() throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();
        Workbook read;
        if (filePath.toLowerCase().endsWith(".xlsx")) {
//            read = new HSSFWorkbook(new FileInputStream(filePath));
            read = new XSSFWorkbook(new FileInputStream(filePath));
        } else if (filePath.toLowerCase().endsWith("xlsx")) {
            read = new XSSFWorkbook(new FileInputStream(filePath));
        } else {
            throw new IOException();
        }
        Sheet sheet = read.getSheetAt(0);
        Row headRow = sheet.getRow(0);
        String[] heads = new String[headRow.getLastCellNum()];
        for (int i = 0, l = headRow.getLastCellNum(); i < l; i++) {
            String ch = headRow.getCell(i).getStringCellValue();
//            String en = searchKeyByValue(ch);
            String en = searchKeyByIndex(i);
            heads[i] = en == null ? String.valueOf(i) : en;
        }
        for (int row = 1, len = sheet.getLastRowNum(); row <= len; row++) {
            Map<String, Object> item = new DataMap<>();
            Row currentRow = sheet.getRow(row);
            if(currentRow==null)continue;
            Cell tempCell = currentRow.getCell(1);
            if (tempCell == null || "".equals(tempCell.getStringCellValue().trim())) break;
            for (int col = 0, last = heads.length; col < last; col++) {
                Cell currentCell = currentRow.getCell(col);
                if (currentCell == null) {
                    String currKey = heads[col];
                    item.put(currKey, null);
                    continue;
                }
                currentRow.getCell(col).setCellType(XSSFCell.CELL_TYPE_STRING);
                String currValue = currentRow.getCell(col).getStringCellValue();
                String currKey = heads[col];
                item.put(currKey, currValue);
            }
            result.add(item);
        }
        return result;
    }


    public void writeCurrentData() throws IOException {
        flushCurrentDataToWorkbook();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 班级人数统计
     * @param xxList 小学数据集
     * @param czList 初中数据集
     * @param gzList 高中数据集
     * @param tjList 特教数据集
     * @throws IOException
     */
    public void writeCurrentDataQqTj(List<Map<String,Object>> xxList,List<Map<String,Object>> czList,List<Map<String,Object>> gzList,List<Map<String,Object>> tjList) throws IOException {
        flushCurrentQqTjDataToWorkbook(0,"小学","{SCHOOLNAME:学校名称,B1:班级数,S1:学生数,BE1:班额数,B2:班级数,S2:学生数,BE2:班额数,B3:班级数,S3:学生数,BE3:班额数,B4:班级数,S4:学生数,BE4:班额数,B5:班级数,S5:学生数,BE5:班额数,B6:班级数,S6:学生数,BE6:班额数,BJZ:班级数,XSZ:学生数,BEZ:班额数}",xxList);
        flushCurrentQqTjDataToWorkbook(1,"初中","{SCHOOLNAME:学校名称,B6:班级数,S6:学生数,BE6:班额数,B7:班级数,S7:学生数,BE7:班额数,B8:班级数,S8:学生数,BE8:班额数,B9:班级数,S9:学生数,BE9:班额数,BJZ:班级数,XSZ:学生数,BEZ:班额数}",czList);
        flushCurrentQqTjDataToWorkbook(2,"高中","{SCHOOLNAME:学校名称,B10:班级数,S10:学生数,BE10:班额数,B11:班级数,S11:学生数,BE11:班额数,B12:班级数,S12:学生数,BE12:班额数,B13:班级数,S13:学生数,BE13:班额数,B14:班级数,S14:学生数,BE14:班额数,BJZ:班级数,XSZ:学生数,BEZ:班额数}",gzList);
        flushCurrentQqTjDataToWorkbook(3,"特教","{SCHOOLNAME:学校名称,B1:班级数,S1:学生数,BE1:班额数,B2:班级数,S2:学生数,BE2:班额数,B3:班级数,S3:学生数,BE3:班额数,B4:班级数,S4:学生数,BE4:班额数,B5:班级数,S5:学生数,BE5:班额数,B6:班级数,S6:学生数,BE6:班额数,B7:班级数,S7:学生数,BE7:班额数,B8:班级数,S8:学生数,BE8:班额数,B9:班级数,S9:学生数,BE9:班额数,B11:班级数,S11:学生数,BE11:班额数,B12:班级数,S12:学生数,BE12:班额数,B13:班级数,S13:学生数,BE13:班额数,B14:班级数,S14:学生数,BE14:班额数,BJZ:班级数,XSZ:学生数,BEZ:班额数}",tjList);
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 长宁在读/在籍学生统计
     * @param type 0全区、1小学初中高中
     * @param cnZdList 在读学生集合
     * @param cnZjList 在籍学生集合
     * @throws IOException
     */
    public void writeCurrentDataCnTj(int type,List<Map<String,Object>> cnZdList,List<Map<String,Object>> cnZjList) throws IOException {

        if(type == 0){
            String sheetTitle = "{XULIE:序号,SCHOOLNAME:学校名称,B1:班级数,S1:学生数,B2:班级数,S2:学生数,B3:班级数,S3:学生数,B4:班级数,S4:学生数,B5:班级数,S5:学生数,B6:班级数,S6:学生数,B7:班级数,S7:学生数,B8:班级数,S8:学生数,B9:班级数,S9:学生数,B10:班级数,S10:学生数,B11:班级数,S11:学生数,B12:班级数,S12:学生数,B13:班级数,S13:学生数,B14:班级数,S14:学生数,BJZS:班级数,XSZS:学生数,TWXJ:小计,TWXX:小学,TWCZ:初中,TWGZ:高中,XGXJ:小计,XGXX:小学,XGCZ:初中,XGGZ:高中,AMXJ:小计,AMXX:小学,AMCZ:初中,AMGZ:高中,HQZNXJ:小计,HQZNXX:小学,HQZNCZ:初中,HQZNGZ:高中,WJXJ:小计,WJXX:小学,WJCZ:初中,WJGZ:高中,MZXJ:小计,MZXX:小学,MZCZ:初中,MZGZ:高中,BSWQXJ:小计,BSWQXX:小学,BSWQCZ:初中,BSWQGZ:高中,FBSCZXJ:小计,FBSCZXX:小学,FBSCZCZ:初中,FBSCZGZ:高中,WHJXJ:小计,WHJXX:小学,WHJCZ:初中,WHJGZ:高中,FBSNCXJ:小计,FBSNCXX:小学,FBSNCCZ:初中,FBSNCGZ:高中,SSJD:所属街道}";
            flushCurrentCnTjDataToWorkbook(type,0,"在读学生统计",sheetTitle,cnZdList);
            flushCurrentCnTjDataToWorkbook(type,1,"在籍学生统计",sheetTitle,cnZjList);

        }else{
            String sheetTitle = "{XULIE:序号,SCHOOLNAME:学校名称,B1:班级数,S1:学生数,B2:班级数,S2:学生数,B3:班级数,S3:学生数,B4:班级数,S4:学生数,B5:班级数,S5:学生数,B6:班级数,S6:学生数,B7:班级数,S7:学生数,B8:班级数,S8:学生数,B9:班级数,S9:学生数,B10:班级数,S10:学生数,B11:班级数,S11:学生数,B12:班级数,S12:学生数,B13:班级数,S13:学生数,B14:班级数,S14:学生数,BJZS:班级数,XSZS:学生数,TW:台湾,XG:香港,AM:澳门,HQZN:华侨子女,WJ:外籍,MZ:民族,BSWQ:本市外区,FBSCZ:非本市城镇,WHJ:无户籍,FBSNC:非本市农村,SSJD:所属街道}";
            flushCurrentCnTjDataToWorkbook(type,0,"在读学生统计",sheetTitle,cnZdList);
            flushCurrentCnTjDataToWorkbook(type,1,"在籍学生统计",sheetTitle,cnZjList);
        }

        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }


    /**
     * 基础基报表统计
     * @param type 0小学、1中学
     * @param result 当前处理结果集
     * @throws IOException
     */
    public void writeCurrentDataJcTj(int type,List<Map<String,Object>> result) throws IOException {
        if(type == 0) {//小学
            String sheetTitle = "{BENAME:班额,XH:编号,HJ:合计,BJ1:一年级,BJ2:二年级,BJ3:三年级,BJ4:四年级,BJ5:五年级,BJ6:六年级,BJ7:复式班}";
            flushCurrentJcTjDataToWorkbook(type,"212基础基小学统计",sheetTitle,result);
        }else{//中学
            String sheetTitle = "{BENAME:班额,XH:编号,HJ:合计,BJZX:合计,BJ1:一年级,BJ2:二年级,BJ3:三年级,BJ4:四年级,BJGZ:合计,BJ5:一年级,BJ6:二年级,BJ7:三年级}";
            flushCurrentJcTjDataToWorkbook(type,"213基础基中学统计",sheetTitle,result);
        }

        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    public void writeCurrentStuData() throws IOException {
        flushCurrentStuDataToWorkbook();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    //户籍类型统计报表
    public void writeCurrentStuHjData(List<Map<String,Object>> xx,List<Map<String,Object>> cz,List<Map<String,Object>> gz,List<Map<String,Object>> tj) throws IOException {
        //第二行表头
        String title1 = "schoolname:学校名称," +
                "a1:一年级,a2:二年级,a3:三年级,a4:四年级,a5:五年级,a6:六年级,a16:合计,"+
                "b1:一年级,b2:二年级,b3:三年级,b4:四年级,b5:五年级,b6:六年级,b16:合计,"+
                "c1:一年级,c2:二年级,c3:三年级,c4:四年级,c5:五年级,c6:六年级,c16:合计,"+
                "d1:一年级,d2:二年级,d3:三年级,d4:四年级,d5:五年级,d6:六年级,d16:合计,"+
                "e1:一年级,e2:二年级,e3:三年级,e4:四年级,e5:五年级,e6:六年级,e16:合计,"+
                "f1:一年级,f2:二年级,f3:三年级,f4:四年级,f5:五年级,f6:六年级,f16:合计,"+
                "g1:一年级,g2:二年级,g3:三年级,g4:四年级,g5:五年级,g6:六年级,g16:合计,"+
                "h1:总计";
        String title2 = "schoolname:学校名称," +
                "a6:六年级,a7:七年级,a8:八年级,a9:九年级,a16:合计,"+
                "b6:六年级,b7:七年级,b8:八年级,b9:九年级,b16:合计,"+
                "c6:六年级,c7:七年级,c8:八年级,c9:九年级,c16:合计,"+
                "d6:六年级,d7:七年级,d8:八年级,d9:九年级,d16:合计,"+
                "e6:六年级,e7:七年级,e8:八年级,e9:九年级,e16:合计,"+
                "f6:六年级,f7:七年级,f8:八年级,f9:九年级,f16:合计,"+
                "g6:六年级,g7:七年级,g8:八年级,g9:九年级,g16:合计,"+
                "h1:总计";
        String title3 = "schoolname:学校名称," +
                "a13:高预,a10:高一,a11:高二,a12:高三,a16:合计,"+
                "b13:高预,b10:高一,b11:高二,b12:高三,b16:合计,"+
                "c13:高预,c10:高一,c11:高二,c12:高三,c16:合计,"+
                "d13:高预,d10:高一,d11:高二,d12:高三,d16:合计,"+
                "e13:高预,e10:高一,e11:高二,e12:高三,e16:合计,"+
                "f13:高预,f10:高一,f11:高二,f12:高三,f16:合计,"+
                "g13:高预,g10:高一,g11:高二,g12:高三,g16:合计,"+
                "h1:总计";
        String title4 = "schoolname:学校名称," +
                "a1:一年级,a2:二年级,a3:三年级,a4:四年级,a5:五年级,a6:六年级,a7:七年级,a8:八年级,a9:九年级,a10:高一,a11:高二,a12:高三,a14:高四,a16:合计,"+
                "b1:一年级,b2:二年级,b3:三年级,b4:四年级,b5:五年级,b6:六年级,b7:七年级,b8:八年级,b9:九年级,b10:高一,b11:高二,b12:高三,b14:高四,b16:合计,"+
                "c1:一年级,c2:二年级,c3:三年级,c4:四年级,c5:五年级,c6:六年级,c7:七年级,c8:八年级,c9:九年级,c10:高一,c11:高二,c12:高三,c14:高四,c16:合计,"+
                "d1:一年级,d2:二年级,d3:三年级,d4:四年级,d5:五年级,d6:六年级,d7:七年级,d8:八年级,d9:九年级,d10:高一,d11:高二,d12:高三,d14:高四,d16:合计,"+
                "e1:一年级,e2:二年级,e3:三年级,e4:四年级,e5:五年级,e6:六年级,e7:七年级,e8:八年级,e9:九年级,e10:高一,e11:高二,e12:高三,e14:高四,e16:合计,"+
                "f1:一年级,f2:二年级,f3:三年级,f4:四年级,f5:五年级,f6:六年级,f7:七年级,f8:八年级,f9:九年级,f10:高一,f11:高二,f12:高三,f14:高四,f16:合计,"+
                "g1:一年级,g2:二年级,g3:三年级,g4:四年级,g5:五年级,g6:六年级,g7:七年级,g8:八年级,g9:九年级,g10:高一,g11:高二,g12:高三,g14:高四,g16:合计,"+
                "h1:总计";
        flushCurrentStuHjDataToWorkbook("1",title1,xx);
        flushCurrentStuHjDataToWorkbook("2",title2,cz);
        flushCurrentStuHjDataToWorkbook("3",title3,gz);
        flushCurrentStuHjDataToWorkbook("4",title4,tj);
        //创建目录
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0,index));
        if(!dir.exists()) dir.mkdirs();
        //写入excel
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 特殊处理，班级情况汇总
     * @throws IOException
     */
    public void writeCurrentDataPdClass()throws  IOException{
        flushCurrentPdDataToWorkbookOtherMode();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 浦东新区，班级汇总情况特殊处理
     */
    private void flushCurrentPdDataToWorkbookOtherMode() {
        Sheet sheet = workbook.createSheet();
        int totalColumnNum = headIndexMap.size()-1;
        /*  合并所有列
            设置字体
            写入第一行数据*/
        Row firstRow = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, totalColumnNum);
        Cell cell_1 = firstRow.createCell(0);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

        Font fontds = workbook.createFont();
        fontds.setFontHeightInPoints((short) 12);//字体大小
        fontds.setFontName("宋体");
        fontds.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyle.setFont(fontds);


        firstRow.setHeightInPoints(40);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


        cell_1.setCellStyle(cellStyle);
        cell_1.setCellValue(getFirstRowString());
        sheet.addMergedRegion(cra);

        //在sheet里增加合并单元格
        /*  合并所有列
            设置字体
            写入第二行数据*/
        Row secondRow = sheet.createRow(1);
        CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, totalColumnNum);
        Cell cell_2 = secondRow.createCell(0);
        cell_2.setCellValue(getSecondRowString());//第二行

        secondRow.setHeightInPoints(40);


        //加大字体
        CellStyle cellStyle2 = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);//字体大小
        font.setFontName("楷体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyle2.setFont(font);
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cell_2.setCellStyle(cellStyle2);

        sheet.addMergedRegion(cra1);

        //空一行
        Row threeRow = sheet.createRow(3);
        CellRangeAddress craT = new CellRangeAddress(3, 3, 0, totalColumnNum);
        Cell cell_9 = threeRow.createCell(0);
        cell_9.setCellValue("学校盖章: ");//第三行
        sheet.addMergedRegion(craT);

        sheet.setColumnWidth(10, 220 * 15);
        // write data head
        Row headRow = sheet.createRow(5);
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                //颜色特技
                Cell headCell = headRow.createCell(cellIndex);
                CellStyle cellStyle3 = workbook.createCellStyle();
                Font font1 = workbook.createFont();
                font1.setFontName("楷体");
                font1.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
                cellStyle3.setFont(font1);
                cellStyle3.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
                        .getIndex());
                cellStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);

                cellStyle3.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyle3.setBorderTop(CellStyle.BORDER_THIN);
                cellStyle3.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyle3.setBorderRight(CellStyle.BORDER_THIN);

                headCell.setCellStyle(cellStyle3);
                headCell.setCellValue(head.getValue());
            }
        }
        // write data
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 6);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                CellStyle cellStyleis = workbook.createCellStyle();
                cellStyleis.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyleis.setBorderTop(CellStyle.BORDER_THIN);
                cellStyleis.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyleis.setBorderRight(CellStyle.BORDER_THIN);
                contentCell.setCellStyle(cellStyleis);

                contentCell.setCellValue(val);
            }
        }
        // write lastTime 打印日期
        int lastTimeRowNum = currentData.size() + 7;
        //1.注：（1）本表请校长、学籍管理员确认上述信息后，签字并加盖学校公章；


        // write lastRowString
        //盖章
        Row lastRowStringRow = sheet.createRow(lastTimeRowNum);
        Cell cell_4 = lastRowStringRow.createCell(0);
        cell_4.setCellValue("注：（1）本表请校长、学籍管理员确认上述信息后，签字并加盖学校公章；");

        Row lastRowStringRows = sheet.createRow(lastTimeRowNum + 1);
        Cell cell_5 = lastRowStringRows.createCell(0);
        cell_5.setCellValue("（2）《学校分年级、分班级学生名册表》加盖学校公章，并请各班主任签字确认，附在本表后；");



        Row lastRowStringRowess = sheet.createRow(lastTimeRowNum + 2);
        Cell cell_7 = lastRowStringRowess.createCell(0);
        cell_7.setCellValue("（3）9月13日前请学校将表格报招生办备案。谢谢配合！");


        Row endRow = sheet.createRow(lastTimeRowNum + 4);
        Cell endCell = endRow.createCell(0);
        endCell.setCellValue("校长（签名）：               核对人（签名）：                   日期："+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        CellRangeAddress cra3 = new CellRangeAddress(lastTimeRowNum + 4, lastTimeRowNum + 4, 0, totalColumnNum);

        sheet.addMergedRegion(cra3);

    }

    /**
     * 特殊处理，花名册
     * @throws IOException
     */
    public void writeCurrentDataForOtherMode() throws IOException {
        flushCurrentDataToWorkbookOtherMode();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    public void setCurrentData(List<Map<String, Object>> datas) {
        currentData.clear();
        for (Map<String, Object> data : datas) {
            Map<String, Object> tempCurrentItem = new DataMap<>();
            for (Map.Entry<String, Integer> headIndex : headIndexMap.entrySet()) {
                String key = headIndex.getKey();
                Integer idx = headIndex.getValue();
                Object value = data.get(key);
                tempCurrentItem.put(idx.toString(),value==null?"":value);
            }
            currentData.add(tempCurrentItem);
        }
    }


    /**
     * 高中生是否合格
     * @throws IOException
     */
    public void writeGraduateStudent() throws IOException {
        exportGraduateStudent();
        int index = filePath.lastIndexOf("/");
        File dir = new File(filePath.substring(0, index));
        if (!dir.exists()) dir.mkdirs();
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        fos.close();
    }

    /**
     * 导出高中学生成绩是否合格
     */
    private void exportGraduateStudent(){
        String title = "2017届高中毕业年级学生毕业合格情况信息表";
        String zpDate = "2017年4月13日";
        String ksyDate = "2017年4月13";
        String twoContent = "说明：学业水平考试成绩由考试院+"+ksyDate+"+提供；综合素质评价反馈由上海市普通高中综合素质评价信息管理系统"+zpDate+"提供。如对合格情况有异议，请联系考试院或综评系统。";

        Sheet sheet = workbook.createSheet();
        int totalColumnNum = headIndexMap.size() - 1;
        Row firstRow = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, totalColumnNum);
        Cell cell_1 = firstRow.createCell(0);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        //指定列宽
        sheet.setColumnWidth(1, 330 * 15);

        Font fontds = workbook.createFont();
        fontds.setFontHeightInPoints((short) 14);//字体大小
        fontds.setFontName("宋体");
        fontds.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyle.setFont(fontds);

        firstRow.setHeightInPoints(40);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        cell_1.setCellStyle(cellStyle);
        cell_1.setCellValue(title);
        sheet.addMergedRegion(cra);

        //在sheet里增加合并单元格
        /*  合并所有列
            设置字体
            写入第二行数据*/
        Row secondRow = sheet.createRow(1);
        CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, totalColumnNum);
        Cell cell_2 = secondRow.createCell(0);
        cell_2.setCellValue(twoContent);//第二行

        //加大字体
        CellStyle cellStyle2 = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);//字体大小
        font.setFontName("楷体");
        font.setColor(Font.COLOR_RED);//红色字体
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyle2.setFont(font);
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cell_2.setCellStyle(cellStyle2);
        sheet.addMergedRegion(cra1);

        //空一行
        Row threeRow = sheet.createRow(3);
        CellRangeAddress craT = new CellRangeAddress(3, 3, 0, totalColumnNum);
        Cell cell_9 = threeRow.createCell(0);
        cell_9.setCellValue(getThreeRowString());//第三行
        sheet.addMergedRegion(craT);


        Row headRow = sheet.createRow(4);
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                //颜色特技
                Cell headCell = headRow.createCell(cellIndex);
                CellStyle cellStyle3 = workbook.createCellStyle();
                Font font1 = workbook.createFont();
                font1.setFontName("楷体");
                font1.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
                cellStyle3.setFont(font1);
                cellStyle3.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
                        .getIndex());
                cellStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);
                cellStyle3.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyle3.setBorderTop(CellStyle.BORDER_THIN);
                cellStyle3.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyle3.setBorderRight(CellStyle.BORDER_THIN);

                headCell.setCellStyle(cellStyle3);
                headCell.setCellValue(head.getValue());
            }
        }
        // write data
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 5);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                CellStyle cellStyleis = workbook.createCellStyle();
                cellStyleis.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyleis.setBorderTop(CellStyle.BORDER_THIN);
                cellStyleis.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyleis.setBorderRight(CellStyle.BORDER_THIN);
                contentCell.setCellStyle(cellStyleis);

                contentCell.setCellValue(val);
            }
        }

    }

    /**
     * 花名册
     */
    private void flushCurrentDataToWorkbookOtherMode() {
        Sheet sheet = workbook.createSheet();
        int totalColumnNum = headIndexMap.size() - 1;
        /*  合并所有列
            设置字体
            写入第一行数据*/
        Row firstRow = sheet.createRow(0);
        CellRangeAddress cra = new CellRangeAddress(0, 0, 0, totalColumnNum);
        Cell cell_1 = firstRow.createCell(0);
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

        //指定列宽
        sheet.setColumnWidth(1, 330 * 15);

        Font fontds = workbook.createFont();
        fontds.setFontHeightInPoints((short) 12);//字体大小
        fontds.setFontName("宋体");
        fontds.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyle.setFont(fontds);


        firstRow.setHeightInPoints(40);
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        cell_1.setCellStyle(cellStyle);
        cell_1.setCellValue(getFirstRowString());
        sheet.addMergedRegion(cra);

        //在sheet里增加合并单元格
        /*  合并所有列
            设置字体
            写入第二行数据*/
        Row secondRow = sheet.createRow(1);
        CellRangeAddress cra1 = new CellRangeAddress(1, 1, 0, totalColumnNum);
        Cell cell_2 = secondRow.createCell(0);
        cell_2.setCellValue(getSecondRowString());//第二行

        //加大字体
        CellStyle cellStyle2 = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);//字体大小
        font.setFontName("楷体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyle2.setFont(font);
        cellStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        cell_2.setCellStyle(cellStyle2);

        sheet.addMergedRegion(cra1);

        //空一行
        Row threeRow = sheet.createRow(3);
        CellRangeAddress craT = new CellRangeAddress(3, 3, 0, totalColumnNum);
        Cell cell_9 = threeRow.createCell(0);
        cell_9.setCellValue(getThreeRowString());//第三行
        sheet.addMergedRegion(craT);

        // write data head
        Row headRow = sheet.createRow(4);
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                //颜色特技
                Cell headCell = headRow.createCell(cellIndex);
                CellStyle cellStyle3 = workbook.createCellStyle();
                Font font1 = workbook.createFont();
                font1.setFontName("楷体");
                font1.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
                cellStyle3.setFont(font1);
                cellStyle3.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE
                        .getIndex());
                cellStyle3.setFillPattern(CellStyle.SOLID_FOREGROUND);

                cellStyle3.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyle3.setBorderTop(CellStyle.BORDER_THIN);
                cellStyle3.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyle3.setBorderRight(CellStyle.BORDER_THIN);

                headCell.setCellStyle(cellStyle3);
                headCell.setCellValue(head.getValue());
            }
        }
        // write data
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 5);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                CellStyle cellStyleis = workbook.createCellStyle();
                cellStyleis.setBorderBottom(CellStyle.BORDER_THIN);
                cellStyleis.setBorderTop(CellStyle.BORDER_THIN);
                cellStyleis.setBorderLeft(CellStyle.BORDER_THIN);
                cellStyleis.setBorderRight(CellStyle.BORDER_THIN);
                contentCell.setCellStyle(cellStyleis);

                contentCell.setCellValue(val);
            }
        }
        // write lastTime 打印日期
        int lastTimeRowNum = currentData.size() + 7;
        if(!BaseChecks.hasEmptyStr(getRemarkString())) {
            Row lastTimeRow = sheet.createRow(lastTimeRowNum-2);
             //先写入值，在进行合并
            Cell cell_3 = lastTimeRow.createCell(0);
            cell_3.setCellValue(lastTime);
            CellRangeAddress cra2 = new CellRangeAddress(lastTimeRowNum-2, lastTimeRowNum-2, 0, totalColumnNum);
            CellStyle cellStyle1 = workbook.createCellStyle();
            sheet.addMergedRegion(cra2);
            cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
            cell_3.setCellStyle(cellStyle1);
            cell_3.setCellValue(getLastTime());

            //指定列宽第一个为学籍副号
//            sheet.setColumnWidth(0, 330 * 15);
            //指定列宽
            sheet.setColumnWidth(7, 330 * 15);
            //指定列宽
            sheet.setColumnWidth(8, 430 * 15);
            //备注
            Row remarkRow = sheet.createRow(lastTimeRowNum+2);
            //先写入值，在进行合并
            Cell cell_r = remarkRow.createCell(0);
            cell_r.setCellValue(lastTime);
            CellRangeAddress craR = new CellRangeAddress(lastTimeRowNum+2, lastTimeRowNum+2, 0, totalColumnNum);
            cell_r.setCellValue(getRemarkString());
            sheet.addMergedRegion(craR);


        }
//        Row lastTimeRow = sheet.createRow(lastTimeRowNum);
//        //先写入值，在进行合并
//        Cell cell_3 = lastTimeRow.createCell(0);
//        cell_3.setCellValue(lastTime);
//        CellRangeAddress cra2 = new CellRangeAddress(lastTimeRowNum, lastTimeRowNum, 0, totalColumnNum);
//
//        CellStyle cellStyle1 = workbook.createCellStyle();;
//        sheet.addMergedRegion(cra2);
//        cellStyle1.setAlignment(CellStyle.ALIGN_RIGHT);
//        cell_3.setCellStyle(cellStyle1);

        // write lastRowString
        //盖章
        Row lastRowStringRow = sheet.createRow(lastTimeRowNum);
        Cell cell_4 = lastRowStringRow.createCell(0);

        CellStyle cellStyled = workbook.createCellStyle();
        Font font3 = workbook.createFont();
        font3.setFontHeightInPoints((short) 12);//字体大小
        font3.setFontName("楷体");
        font3.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体
        cellStyled.setFont(font3);
        cellStyled.setAlignment(CellStyle.ALIGN_LEFT);
        cell_4.setCellStyle(cellStyled);

        cell_4.setCellValue(getLastRowString());
        CellRangeAddress cra3 = new CellRangeAddress(lastTimeRowNum, lastTimeRowNum, 0, totalColumnNum);

        sheet.addMergedRegion(cra3);

//        //外籍备注
//        Row remarkRow = sheet.createRow(lastTimeRowNum + 4);
//        Cell cell_5 = remarkRow.createCell(0);
//        cell_5.setCellValue(getRemarkString());
//        CellRangeAddress cra4 = new CellRangeAddress(lastTimeRowNum + 4, lastTimeRowNum + 4, 0, totalColumnNum);
//        sheet.addMergedRegion(cra4);

    }

    /** 徐汇学生格式化导出 特殊处理 **/
    private void flushCurrentStuDataToWorkbook(){
        Sheet sheet = workbook.createSheet();
        int k = 0;
        for (int row = 1, len = currentData.size()*4; row < len; row=row+4) {
            String schoolname = (String) currentData.get(k).remove("0");
            Row contentRow = sheet.createRow(row);   //创建一行
            Cell contentCell = contentRow.createCell(0);
            contentCell.setCellValue(schoolname+"学生用户帐号");

            Row contentRow_1 = sheet.createRow(row + 1);   //创建一行
            String head = "年级,班级,姓名,性别,用户名,密码,访问地址";
            int cellIndex = 0 ;
            for (String key :head.split(",")) {
                Cell contentCell_1 = contentRow_1.createCell(cellIndex);
                contentCell_1.setCellValue(key);
                cellIndex++;
            }
            Row contentRow_2 = sheet.createRow(row + 2);   //创建一行
            for (Map.Entry<String, Object> curr : currentData.get(k).entrySet()) {
                int cellIndex_2 = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell_2 = contentRow_2.createCell(cellIndex_2-1);
                contentCell_2.setCellValue(val);
            }
            k++;
        }
    }

    /**
     * 户籍类型统计报表
     */
    private void flushCurrentStuHjDataToWorkbook(String xdid,String title,List<Map<String,Object>> result){
        headIndexMap.clear();
        currentData.clear();
        dataHeadMap.clear();
        head2Map.clear();

        //生成表格
        Sheet sheet = workbook.createSheet();
        if("1".equals(xdid)){
            workbook.setSheetName(0,"小学生户籍类别统计");//设置表格名称
        }
        if("2".equals(xdid)){
            workbook.setSheetName(1,"初中学生户籍类别统计");//设置表格名称
        }
        if("3".equals(xdid)){
            workbook.setSheetName(2,"高中学生户籍类别统计");//设置表格名称
        }
        if("4".equals(xdid)){
            workbook.setSheetName(3,"特教学生户籍类别统计");//设置表格名称
        }
        //设置表格整体样式
        sheet.setDefaultColumnWidth((short)7);//设置表格整体宽度
        sheet.setColumnWidth(0,9000);//设置表格第一列宽度
        sheet.createFreezePane(0,2,0,2);//冻结两行

        //定义结果集样式
        CellStyle resultStyle = workbook.createCellStyle();
        //表格边框
        resultStyle.setBorderBottom(CellStyle.BORDER_THIN);
        resultStyle.setBorderTop(CellStyle.BORDER_THIN);
        resultStyle.setBorderRight(CellStyle.BORDER_THIN);
        resultStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        resultStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 表格背景色
        resultStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        resultStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        resultStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


        //定义表头样式
        CellStyle headStyle = workbook.createCellStyle();
        //表格边框
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        headStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //设置表头参数
        headMergedRegionHj(xdid,sheet);

        //设置第二行表头参数
        String[] titles = title.split(",");
        Row row2 = sheet.createRow(1);
        head2Map.put("schoolname","学校名称");
        for(int i=1,size=titles.length;i<size;i++){
            String[] temp = titles[i].split(":");
            String key = temp[0];
            String val = temp[1];
            Cell cell = row2.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(val);
            head2Map.put(key,val);
        }


        //设置结果参数
        for (int row = 0, len = result.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 2);
            Map<String,Object> data = result.get(row);
            int cellNo = 0;
            for (Map.Entry<String, Object> curr : head2Map.entrySet()) {
                String key = curr.getKey();
                String val = data.get(key) == null ? "" : data.get(key).toString().trim();
                Cell contentCell = contentRow.createCell(cellNo++);
                contentCell.setCellStyle(resultStyle);
                contentCell.setCellValue(val);
                contentRow.setHeight((short) 350);
            }
        }
    }



    /**
     * 全区班级、学生、班额统计
     * @param sheetNum sheet页
     * @param sheetTitle sheet名称
     * @param headConfig excel表头
     * @param result 当前处理结果集
     */
    private void flushCurrentQqTjDataToWorkbook(int sheetNum,String sheetTitle, String headConfig,List<Map<String,Object>> result) {
        headIndexMap.clear();
        currentData.clear();
        dataHeadMap.clear();

        // 生成一个表格
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为8个字节
        sheet.setDefaultColumnWidth((short) 7);
        // 设置第一列宽度
        sheet.setColumnWidth(0, 9000);
        // 冻结第一行和第二行
        sheet.createFreezePane( 0, 2, 0, 2 );
        //定义表格样式
        CellStyle style = workbook.createCellStyle();
        //表格边框
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


        //定义表格表头样式
        CellStyle headStyle = workbook.createCellStyle();
        //表格边框
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        headStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);



        //第一行表头合并居中
        headMergedRegion(sheet,sheetNum);

        //第二行表头开始
        Row headRow1 = sheet.createRow(1);
        //设置第二行
        sheet.getRow(1).setHeight((short)500);


        //表头处理
        if (headConfig != null && headConfig.matches(JSON_STR)) {
            headConfig = headConfig.substring(1, headConfig.length() - 1);
            String[] items = headConfig.split(",");
            for (int i = 0, len = items.length; i < len; i++) {
                String ch = items[i].split(":")[1];
                String en = items[i].split(":")[0];
                headIndexMap.put(en, i);
                dataHeadMap.put(en, ch);
            }
        }

        //当前结果集处理
        for (Map<String, Object> data : result) {
            Map<String, Object> tempCurrentItem = new DataMap<>();
            for (Map.Entry<String, Integer> headIndex : headIndexMap.entrySet()) {
                String key = headIndex.getKey();
                Integer idx = headIndex.getValue();
                Object value = data.get(key);
                tempCurrentItem.put(idx.toString(),value==null?"":value);
            }
            currentData.add(tempCurrentItem);
        }

        // 产生表格标题行
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                Cell headCell = headRow1.createCell(cellIndex);
                headCell.setCellType(Cell.CELL_TYPE_STRING);
                headCell.setCellValue(head.getValue());
                headCell.setCellStyle(headStyle);

            }
        }
        // 遍历集合数据，产生数据行
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 2);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                contentCell.setCellValue(val);
                contentCell.setCellStyle(style);
                sheet.getRow(row+2).setHeight((short)350);
            }
        }
    }

    //学生户籍统计表
    private void headMergedRegionHj(String xdid,Sheet sheet){
        //定义表头样式
        CellStyle headerStyle = workbook.createCellStyle();
        //头部边框
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表头水平居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //表头垂直居中
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 表头背景色
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Row row0 = sheet.createRow(0);//创建第一行
        row0.setHeight((short)500);//设置第一行行高


        Cell cell0 = row0.createCell(0);//创建第一列
        CellRangeAddress cra0 = new CellRangeAddress(0,1,0,0);//初始化单元格合并对象
        sheet.addMergedRegion(cra0);//合并单元格
        cell0.setCellStyle(headerStyle);//设置样式
        cell0.setCellValue("学校名称");

        if("1".equals(xdid)){
            Cell cell1 = row0.createCell(1);//创建第1列
            CellRangeAddress ca1 = new CellRangeAddress(0,0,1,7);
            sheet.addMergedRegion(ca1);
            cell1.setCellStyle(headerStyle);
            cell1.setCellValue("上海户籍");

            Cell cell2 = row0.createCell(8);//创建第8列
            CellRangeAddress ca2 = new CellRangeAddress(0,0,8,14);
            sheet.addMergedRegion(ca2);
            cell2.setCellStyle(headerStyle);
            cell2.setCellValue("外省市户籍");

            Cell cell3 = row0.createCell(15);
            CellRangeAddress ca3 = new CellRangeAddress(0,0,15,21);
            sheet.addMergedRegion(ca3);
            cell3.setCellStyle(headerStyle);
            cell3.setCellValue("港澳");

            Cell cell4 = row0.createCell(22);
            CellRangeAddress ca4 = new CellRangeAddress(0,0,22,28);
            sheet.addMergedRegion(ca4);
            cell4.setCellStyle(headerStyle);
            cell4.setCellValue("台湾");

            Cell cell5 = row0.createCell(29);
            CellRangeAddress ca5 = new CellRangeAddress(0,0,29,35);
            sheet.addMergedRegion(ca5);
            cell5.setCellStyle(headerStyle);
            cell5.setCellValue("外籍");

            Cell cell6 = row0.createCell(36);
            CellRangeAddress ca6 = new CellRangeAddress(0,0,36,42);
            sheet.addMergedRegion(ca6);
            cell6.setCellStyle(headerStyle);
            cell6.setCellValue("无户口");

            Cell cell7 = row0.createCell(43);
            CellRangeAddress ca7 = new CellRangeAddress(0,0,43,49);
            sheet.addMergedRegion(ca7);
            cell7.setCellStyle(headerStyle);
            cell7.setCellValue("未填写");

            Cell cell8 = row0.createCell(50);
            CellRangeAddress ca8 = new CellRangeAddress(0,1,50,50);
            sheet.addMergedRegion(ca8);
            cell8.setCellStyle(headerStyle);
            cell8.setCellValue("总计");

        }else if("2".equals(xdid)){
            Cell cell1 = row0.createCell(1);//创建第1列
            CellRangeAddress ca1 = new CellRangeAddress(0,0,1,5);
            sheet.addMergedRegion(ca1);
            cell1.setCellStyle(headerStyle);
            cell1.setCellValue("上海户籍");

            Cell cell2 = row0.createCell(6);//创建第8列
            CellRangeAddress ca2 = new CellRangeAddress(0,0,6,10);
            sheet.addMergedRegion(ca2);
            cell2.setCellStyle(headerStyle);
            cell2.setCellValue("外省市户籍");

            Cell cell3 = row0.createCell(11);
            CellRangeAddress ca3 = new CellRangeAddress(0,0,11,15);
            sheet.addMergedRegion(ca3);
            cell3.setCellStyle(headerStyle);
            cell3.setCellValue("港澳");

            Cell cell4 = row0.createCell(16);
            CellRangeAddress ca4 = new CellRangeAddress(0,0,16,20);
            sheet.addMergedRegion(ca4);
            cell4.setCellStyle(headerStyle);
            cell4.setCellValue("台湾");

            Cell cell5 = row0.createCell(21);
            CellRangeAddress ca5 = new CellRangeAddress(0,0,21,25);
            sheet.addMergedRegion(ca5);
            cell5.setCellStyle(headerStyle);
            cell5.setCellValue("外籍");

            Cell cell6 = row0.createCell(26);
            CellRangeAddress ca6 = new CellRangeAddress(0,0,26,30);
            sheet.addMergedRegion(ca6);
            cell6.setCellStyle(headerStyle);
            cell6.setCellValue("无户口");

            Cell cell7 = row0.createCell(31);
            CellRangeAddress ca7 = new CellRangeAddress(0,0,31,35);
            sheet.addMergedRegion(ca7);
            cell7.setCellStyle(headerStyle);
            cell7.setCellValue("未填写");

            Cell cell8 = row0.createCell(36);
            CellRangeAddress ca8 = new CellRangeAddress(0,1,36,36);
            sheet.addMergedRegion(ca8);
            cell8.setCellStyle(headerStyle);
            cell8.setCellValue("总计");

        }else if("3".equals(xdid)){
            Cell cell1 = row0.createCell(1);//创建第1列
            CellRangeAddress ca1 = new CellRangeAddress(0,0,1,5);
            sheet.addMergedRegion(ca1);
            cell1.setCellStyle(headerStyle);
            cell1.setCellValue("上海户籍");

            Cell cell2 = row0.createCell(6);//创建第8列
            CellRangeAddress ca2 = new CellRangeAddress(0,0,6,10);
            sheet.addMergedRegion(ca2);
            cell2.setCellStyle(headerStyle);
            cell2.setCellValue("外省市户籍");

            Cell cell3 = row0.createCell(11);
            CellRangeAddress ca3 = new CellRangeAddress(0,0,11,15);
            sheet.addMergedRegion(ca3);
            cell3.setCellStyle(headerStyle);
            cell3.setCellValue("港澳");

            Cell cell4 = row0.createCell(16);
            CellRangeAddress ca4 = new CellRangeAddress(0,0,16,20);
            sheet.addMergedRegion(ca4);
            cell4.setCellStyle(headerStyle);
            cell4.setCellValue("台湾");

            Cell cell5 = row0.createCell(21);
            CellRangeAddress ca5 = new CellRangeAddress(0,0,21,25);
            sheet.addMergedRegion(ca5);
            cell5.setCellStyle(headerStyle);
            cell5.setCellValue("外籍");

            Cell cell6 = row0.createCell(26);
            CellRangeAddress ca6 = new CellRangeAddress(0,0,26,30);
            sheet.addMergedRegion(ca6);
            cell6.setCellStyle(headerStyle);
            cell6.setCellValue("无户口");

            Cell cell7 = row0.createCell(31);
            CellRangeAddress ca7 = new CellRangeAddress(0,0,31,35);
            sheet.addMergedRegion(ca7);
            cell7.setCellStyle(headerStyle);
            cell7.setCellValue("未填写");

            Cell cell8 = row0.createCell(36);
            CellRangeAddress ca8 = new CellRangeAddress(0,1,36,36);
            sheet.addMergedRegion(ca8);
            cell8.setCellStyle(headerStyle);
            cell8.setCellValue("总计");

        }else if("4".equals(xdid)){
            Cell cell1 = row0.createCell(1);//创建第1列
            CellRangeAddress ca1 = new CellRangeAddress(0,0,1,14);
            sheet.addMergedRegion(ca1);
            cell1.setCellStyle(headerStyle);
            cell1.setCellValue("上海户籍");

            Cell cell2 = row0.createCell(15);//创建第8列
            CellRangeAddress ca2 = new CellRangeAddress(0,0,15,28);
            sheet.addMergedRegion(ca2);
            cell2.setCellStyle(headerStyle);
            cell2.setCellValue("外省市户籍");

            Cell cell3 = row0.createCell(29);
            CellRangeAddress ca3 = new CellRangeAddress(0,0,29,42);
            sheet.addMergedRegion(ca3);
            cell3.setCellStyle(headerStyle);
            cell3.setCellValue("港澳");

            Cell cell4 = row0.createCell(43);
            CellRangeAddress ca4 = new CellRangeAddress(0,0,43,56);
            sheet.addMergedRegion(ca4);
            cell4.setCellStyle(headerStyle);
            cell4.setCellValue("台湾");

            Cell cell5 = row0.createCell(57);
            CellRangeAddress ca5 = new CellRangeAddress(0,0,57,70);
            sheet.addMergedRegion(ca5);
            cell5.setCellStyle(headerStyle);
            cell5.setCellValue("外籍");

            Cell cell6 = row0.createCell(71);
            CellRangeAddress ca6 = new CellRangeAddress(0,0,71,84);
            sheet.addMergedRegion(ca6);
            cell6.setCellStyle(headerStyle);
            cell6.setCellValue("无户口");

            Cell cell7 = row0.createCell(85);
            CellRangeAddress ca7 = new CellRangeAddress(0,0,85,98);
            sheet.addMergedRegion(ca7);
            cell7.setCellStyle(headerStyle);
            cell7.setCellValue("未填写");

            Cell cell8 = row0.createCell(99);
            CellRangeAddress ca8 = new CellRangeAddress(0,1,99,99);
            sheet.addMergedRegion(ca8);
            cell8.setCellStyle(headerStyle);
            cell8.setCellValue("总计");
        }
    }
    /**
     * 全区班级、学生、班额统计的表头样式
     */
    private void headMergedRegion(Sheet sheet,int sheetNum){
        CellStyle headerStyle = workbook.createCellStyle();
        //头部边框
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表头水平居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //表头垂直居中
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 表头背景色
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Row headRow0 = sheet.createRow(0);
        //设置第一行
        sheet.getRow(0).setHeight((short)500);

        //第一行第一列
        Cell cell0 = headRow0.createCell(0);
        CellRangeAddress cra0=new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(cra0);
        cell0.setCellStyle(headerStyle);
        cell0.setCellValue("学校名称");

        if(sheetNum == 0){
            Cell cell1 = headRow0.createCell(1);
            cell1.setCellValue("一年级");
            CellRangeAddress cra1=new CellRangeAddress(0, 0, 1, 3);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);

            Cell cell4 = headRow0.createCell(4);
            cell4.setCellValue("二年级");
            CellRangeAddress cra2=new CellRangeAddress(0, 0, 4, 6);
            sheet.addMergedRegion(cra2);
            cell4.setCellStyle(headerStyle);

            Cell cell7 = headRow0.createCell(7);
            cell7.setCellValue("三年级");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 7, 9);
            sheet.addMergedRegion(cra3);
            cell7.setCellStyle(headerStyle);

            Cell cell10 = headRow0.createCell(10);
            cell10.setCellValue("四年级");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 10, 12);
            sheet.addMergedRegion(cra4);
            cell10.setCellStyle(headerStyle);

            Cell cell13 = headRow0.createCell(13);
            cell13.setCellValue("五年级");
            CellRangeAddress cra5=new CellRangeAddress(0, 0, 13, 15);
            sheet.addMergedRegion(cra5);
            cell13.setCellStyle(headerStyle);

            Cell cell16 = headRow0.createCell(16);
            cell16.setCellValue("六年级");
            CellRangeAddress cra6=new CellRangeAddress(0, 0, 16, 18);
            sheet.addMergedRegion(cra6);
            cell16.setCellStyle(headerStyle);

            Cell cell19 = headRow0.createCell(19);
            cell19.setCellValue("合计");
            CellRangeAddress craHj=new CellRangeAddress(0, 0, 19, 21);
            sheet.addMergedRegion(craHj);
            cell19.setCellStyle(headerStyle);
        }else if(sheetNum == 1){
            Cell cell1 = headRow0.createCell(1);
            cell1.setCellValue("六年级");
            CellRangeAddress cra1=new CellRangeAddress(0, 0, 1, 3);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);

            Cell cell4 = headRow0.createCell(4);
            cell4.setCellValue("七年级");
            CellRangeAddress cra2=new CellRangeAddress(0, 0, 4, 6);
            sheet.addMergedRegion(cra2);
            cell4.setCellStyle(headerStyle);

            Cell cell7 = headRow0.createCell(7);
            cell7.setCellValue("八年级");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 7, 9);
            sheet.addMergedRegion(cra3);
            cell7.setCellStyle(headerStyle);

            Cell cell10 = headRow0.createCell(10);
            cell10.setCellValue("九年级");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 10, 12);
            sheet.addMergedRegion(cra4);
            cell10.setCellStyle(headerStyle);

            Cell cellHj = headRow0.createCell(13);
            cellHj.setCellValue("合计");
            CellRangeAddress cra5=new CellRangeAddress(0, 0, 13, 15);
            sheet.addMergedRegion(cra5);
            cellHj.setCellStyle(headerStyle);
        }else if(sheetNum == 2){
            Cell cell1 = headRow0.createCell(1);
            cell1.setCellValue("高预");
            CellRangeAddress cra1=new CellRangeAddress(0, 0, 1, 3);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);

            Cell cell4 = headRow0.createCell(4);
            cell4.setCellValue("高一");
            CellRangeAddress cra2=new CellRangeAddress(0, 0, 4, 6);
            sheet.addMergedRegion(cra2);
            cell4.setCellStyle(headerStyle);

            Cell cell7 = headRow0.createCell(7);
            cell7.setCellValue("高二");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 7, 9);
            sheet.addMergedRegion(cra3);
            cell7.setCellStyle(headerStyle);

            Cell cell10 = headRow0.createCell(10);
            cell10.setCellValue("高三");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 10, 12);
            sheet.addMergedRegion(cra4);
            cell10.setCellStyle(headerStyle);

            Cell cell13 = headRow0.createCell(13);
            cell13.setCellValue("高四");
            CellRangeAddress cra5=new CellRangeAddress(0, 0, 13, 15);
            sheet.addMergedRegion(cra5);
            cell13.setCellStyle(headerStyle);

            Cell cellHj = headRow0.createCell(16);
            cellHj.setCellValue("合计");
            CellRangeAddress cra6=new CellRangeAddress(0, 0, 16, 18);
            sheet.addMergedRegion(cra6);
            cellHj.setCellStyle(headerStyle);
        }else if(sheetNum == 3){
            Cell cell1 = headRow0.createCell(1);
            cell1.setCellValue("一年级");
            CellRangeAddress cra1=new CellRangeAddress(0, 0, 1, 3);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);

            Cell cell4 = headRow0.createCell(4);
            cell4.setCellValue("二年级");
            CellRangeAddress cra2=new CellRangeAddress(0, 0, 4, 6);
            sheet.addMergedRegion(cra2);
            cell4.setCellStyle(headerStyle);

            Cell cell7 = headRow0.createCell(7);
            cell7.setCellValue("三年级");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 7, 9);
            sheet.addMergedRegion(cra3);
            cell7.setCellStyle(headerStyle);

            Cell cell10 = headRow0.createCell(10);
            cell10.setCellValue("四年级");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 10, 12);
            sheet.addMergedRegion(cra4);
            cell10.setCellStyle(headerStyle);

            Cell cell13 = headRow0.createCell(13);
            cell13.setCellValue("五年级");
            CellRangeAddress cra5=new CellRangeAddress(0, 0, 13, 15);
            sheet.addMergedRegion(cra5);
            cell13.setCellStyle(headerStyle);

            Cell cell16 = headRow0.createCell(16);
            cell16.setCellValue("六年级");
            CellRangeAddress cra6=new CellRangeAddress(0, 0, 16, 18);
            sheet.addMergedRegion(cra6);
            cell16.setCellStyle(headerStyle);

            Cell cell19 = headRow0.createCell(19);
            cell19.setCellValue("七年级");
            CellRangeAddress cra7=new CellRangeAddress(0, 0, 19, 21);
            sheet.addMergedRegion(cra7);
            cell19.setCellStyle(headerStyle);

            Cell cell22 = headRow0.createCell(22);
            cell22.setCellValue("八年级");
            CellRangeAddress cra8=new CellRangeAddress(0, 0, 22, 24);
            sheet.addMergedRegion(cra8);
            cell22.setCellStyle(headerStyle);

            Cell cell25 = headRow0.createCell(25);
            cell25.setCellValue("九年级");
            CellRangeAddress cra9=new CellRangeAddress(0, 0, 25, 27);
            sheet.addMergedRegion(cra9);
            cell25.setCellStyle(headerStyle);

            Cell cell28 = headRow0.createCell(28);
            cell28.setCellValue("高一");
            CellRangeAddress cra10=new CellRangeAddress(0, 0, 28, 30);
            sheet.addMergedRegion(cra10);
            cell28.setCellStyle(headerStyle);

            Cell cell31 = headRow0.createCell(31);
            cell31.setCellValue("高二");
            CellRangeAddress cra11=new CellRangeAddress(0, 0, 31, 33);
            sheet.addMergedRegion(cra11);
            cell31.setCellStyle(headerStyle);

            Cell cell34 = headRow0.createCell(34);
            cell34.setCellValue("高三");
            CellRangeAddress cra12=new CellRangeAddress(0, 0, 34, 36);
            sheet.addMergedRegion(cra12);
            cell34.setCellStyle(headerStyle);

            Cell cell37 = headRow0.createCell(37);
            cell37.setCellValue("高四");
            CellRangeAddress cra13=new CellRangeAddress(0, 0, 37, 39);
            sheet.addMergedRegion(cra13);
            cell37.setCellStyle(headerStyle);

            Cell cellHj = headRow0.createCell(40);
            cellHj.setCellValue("合计");
            CellRangeAddress cra14=new CellRangeAddress(0, 0, 40, 42);
            sheet.addMergedRegion(cra14);
            cellHj.setCellStyle(headerStyle);
        }
    }


    /**
     * 长宁在读/在籍学生统计
     * @param type 0全区、1小学初中高中
     * @param sheetNum  sheet页
     * @param sheetTitle sheet名称
     * @param headConfig 表头
     * @param result 当前数据集
     */
    private void flushCurrentCnTjDataToWorkbook(int type,int sheetNum,String sheetTitle, String headConfig,List<Map<String,Object>> result) {
        headIndexMap.clear();
        currentData.clear();
        dataHeadMap.clear();

        // 生成一个表格
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为8个字节
        sheet.setDefaultColumnWidth((short) 8);
        // 设置第一列宽度
        sheet.setColumnWidth(1, 9000);
        // 冻结第一行和第二行
        sheet.createFreezePane( 0, 2, 0, 2 );
        //定义表格样式
        CellStyle style = workbook.createCellStyle();
        //表格边框
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


        //定义表格表头样式
        CellStyle headStyle = workbook.createCellStyle();
        //表格边框
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        headStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //第一行表头合并居中
        headMergedRegionCn(sheet,type,result);

        //第二行表头开始
        Row headRow1 = sheet.createRow(1);
        //设置第二行
        sheet.getRow(1).setHeight((short)500);


        //表头处理
        if (headConfig != null && headConfig.matches(JSON_STR)) {
            headConfig = headConfig.substring(1, headConfig.length() - 1);
            String[] items = headConfig.split(",");
            for (int i = 0, len = items.length; i < len; i++) {
                String ch = items[i].split(":")[1];
                String en = items[i].split(":")[0];
                headIndexMap.put(en, i);
                dataHeadMap.put(en, ch);
            }
        }

        //当前结果集处理
        int i = 1;
        for (Map<String, Object> data : result) {
            Map<String, Object> tempCurrentItem = new DataMap<>();
            for (Map.Entry<String, Integer> headIndex : headIndexMap.entrySet()) {
                String key = headIndex.getKey();
//                key = key.toUpperCase();
                Integer idx = headIndex.getValue();
                Object value = data.get(key);
                tempCurrentItem.put(idx.toString(),value==null?"":value);
            }
            tempCurrentItem.put("0",i++);
            currentData.add(tempCurrentItem);
        }

        // 产生表格标题行
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                Cell headCell = headRow1.createCell(cellIndex);
                headCell.setCellType(Cell.CELL_TYPE_STRING);
                headCell.setCellValue(head.getValue());
                headCell.setCellStyle(headStyle);

            }
        }
        // 遍历集合数据，产生数据行
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 2);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                contentCell.setCellValue(val);
                contentCell.setCellStyle(style);
                sheet.getRow(row+2).setHeight((short)350);
            }
        }
    }

    /**
     * 长宁全区班级、学生、班额统计的表头样式
     */
    private void headMergedRegionCn(Sheet sheet,int type,List<Map<String,Object>> result){
        CellStyle headerStyle = workbook.createCellStyle();
        //头部边框
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表头水平居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //表头垂直居中
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 表头背景色
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        //取得第一行
        Row headRow0 = sheet.createRow(0);
        //设置第一行
        sheet.getRow(0).setHeight((short)500);

        //第一行第一列
        Cell cell0 = headRow0.createCell(0);
        CellRangeAddress cra0=new CellRangeAddress(0, 1, 0, 0);
        sheet.addMergedRegion(cra0);
        cell0.setCellStyle(headerStyle);
        cell0.setCellValue("序号");

        Cell cell00 = headRow0.createCell(1);
        CellRangeAddress cra00=new CellRangeAddress(0, 1, 1, 1);
        sheet.addMergedRegion(cra00);
        cell00.setCellStyle(headerStyle);
        cell00.setCellValue("学校名称");

        if(type == 0){//全区
            Cell cell1 = headRow0.createCell(2);
            cell1.setCellValue("一年级");
            CellRangeAddress cra1=new CellRangeAddress(0, 0, 2, 3);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);

            Cell cell3 = headRow0.createCell(4);
            cell3.setCellValue("二年级");
            CellRangeAddress cra2=new CellRangeAddress(0, 0, 4, 5);
            sheet.addMergedRegion(cra2);
            cell3.setCellStyle(headerStyle);

            Cell cell5 = headRow0.createCell(6);
            cell5.setCellValue("三年级");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 6, 7);
            sheet.addMergedRegion(cra3);
            cell5.setCellStyle(headerStyle);

            Cell cell7 = headRow0.createCell(8);
            cell7.setCellValue("四年级");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 8, 9);
            sheet.addMergedRegion(cra4);
            cell7.setCellStyle(headerStyle);

            Cell cell9 = headRow0.createCell(10);
            cell9.setCellValue("五年级");
            CellRangeAddress cra5=new CellRangeAddress(0, 0, 10, 11);
            sheet.addMergedRegion(cra5);
            cell9.setCellStyle(headerStyle);

            Cell cell11 = headRow0.createCell(12);
            cell11.setCellValue("六年级");
            CellRangeAddress cra6=new CellRangeAddress(0, 0, 12, 13);
            sheet.addMergedRegion(cra6);
            cell11.setCellStyle(headerStyle);

            Cell cell13 = headRow0.createCell(14);
            cell13.setCellValue("七年级");
            CellRangeAddress cra7=new CellRangeAddress(0, 0, 14, 15);
            sheet.addMergedRegion(cra7);
            cell13.setCellStyle(headerStyle);

            Cell cell15 = headRow0.createCell(16);
            cell15.setCellValue("八年级");
            CellRangeAddress cra8=new CellRangeAddress(0, 0, 16, 17);
            sheet.addMergedRegion(cra8);
            cell15.setCellStyle(headerStyle);

            Cell cell17 = headRow0.createCell(18);
            cell17.setCellValue("九年级");
            CellRangeAddress cra9=new CellRangeAddress(0, 0, 18, 19);
            sheet.addMergedRegion(cra9);
            cell17.setCellStyle(headerStyle);

            Cell cell19 = headRow0.createCell(20);
            cell19.setCellValue("高一年级");
            CellRangeAddress cra10=new CellRangeAddress(0, 0, 20, 21);
            sheet.addMergedRegion(cra10);
            cell19.setCellStyle(headerStyle);

            Cell cell21 = headRow0.createCell(22);
            cell21.setCellValue("高二年级");
            CellRangeAddress cra11=new CellRangeAddress(0, 0, 22, 23);
            sheet.addMergedRegion(cra11);
            cell21.setCellStyle(headerStyle);

            Cell cell23 = headRow0.createCell(24);
            cell23.setCellValue("高三年级");
            CellRangeAddress cra12=new CellRangeAddress(0, 0, 24, 25);
            sheet.addMergedRegion(cra12);
            cell23.setCellStyle(headerStyle);

            Cell cell25 = headRow0.createCell(26);
            cell25.setCellValue("高预年级");
            CellRangeAddress cra13=new CellRangeAddress(0, 0, 26, 27);
            sheet.addMergedRegion(cra13);
            cell25.setCellStyle(headerStyle);

            Cell cell27 = headRow0.createCell(28);
            cell27.setCellValue("高四年级");
            CellRangeAddress cra14=new CellRangeAddress(0, 0, 28, 29);
            sheet.addMergedRegion(cra14);
            cell27.setCellStyle(headerStyle);

            Cell cell29 = headRow0.createCell(30);
            cell29.setCellValue("全校总人数");
            CellRangeAddress craHj=new CellRangeAddress(0, 0, 30, 31);
            sheet.addMergedRegion(craHj);
            cell29.setCellStyle(headerStyle);

            Cell cell31 = headRow0.createCell(32);
            cell31.setCellValue("台湾");
            CellRangeAddress cra15=new CellRangeAddress(0, 0, 32, 35);
            sheet.addMergedRegion(cra15);
            cell31.setCellStyle(headerStyle);

            Cell cell35 = headRow0.createCell(36);
            cell35.setCellValue("香港");
            CellRangeAddress cra16=new CellRangeAddress(0, 0, 36, 39);
            sheet.addMergedRegion(cra16);
            cell35.setCellStyle(headerStyle);

            Cell cell39 = headRow0.createCell(40);
            cell39.setCellValue("澳门");
            CellRangeAddress cra17=new CellRangeAddress(0, 0, 40, 43);
            sheet.addMergedRegion(cra17);
            cell39.setCellStyle(headerStyle);

            Cell cell43 = headRow0.createCell(44);
            cell43.setCellValue("华侨子女");
            CellRangeAddress cra18=new CellRangeAddress(0, 0, 44, 47);
            sheet.addMergedRegion(cra18);
            cell43.setCellStyle(headerStyle);

            Cell cell47 = headRow0.createCell(48);
            cell47.setCellValue("外籍");
            CellRangeAddress cra19=new CellRangeAddress(0, 0, 48, 51);
            sheet.addMergedRegion(cra19);
            cell47.setCellStyle(headerStyle);

            Cell cell51 = headRow0.createCell(52);
            cell51.setCellValue("少数民族");
            CellRangeAddress cra20=new CellRangeAddress(0, 0, 52, 55);
            sheet.addMergedRegion(cra20);
            cell51.setCellStyle(headerStyle);

            Cell cell55 = headRow0.createCell(56);
            cell55.setCellValue("本市外区");
            CellRangeAddress cra21=new CellRangeAddress(0, 0, 56, 59);
            sheet.addMergedRegion(cra21);
            cell55.setCellStyle(headerStyle);

            Cell cell59 = headRow0.createCell(60);
            cell59.setCellValue("非本市城镇");
            CellRangeAddress cra22=new CellRangeAddress(0, 0, 60, 63);
            sheet.addMergedRegion(cra22);
            cell59.setCellStyle(headerStyle);

            Cell cell63 = headRow0.createCell(64);
            cell63.setCellValue("无户籍");
            CellRangeAddress cra23=new CellRangeAddress(0, 0, 64, 67);
            sheet.addMergedRegion(cra23);
            cell63.setCellStyle(headerStyle);

            Cell cell67 = headRow0.createCell(68);
            cell67.setCellValue("非本市农村");
            CellRangeAddress cra24=new CellRangeAddress(0, 0, 68, 71);
            sheet.addMergedRegion(cra24);
            cell67.setCellStyle(headerStyle);

            Cell cell71 = headRow0.createCell(72);
            cell71.setCellValue("所属街道");
            CellRangeAddress cra25=new CellRangeAddress(0, 1, 72, 72);
            sheet.addMergedRegion(cra25);
            cell71.setCellStyle(headerStyle);

            //最后两行合计格式处理
            CellRangeAddress cra26=new CellRangeAddress(result.size(), result.size(), 30, 31);
            sheet.addMergedRegion(cra26);
            CellRangeAddress cra27=new CellRangeAddress(result.size(), result.size()+1, 0, 0);
            sheet.addMergedRegion(cra27);
            CellRangeAddress cra28=new CellRangeAddress(result.size(), result.size()+1, 1, 1);
            sheet.addMergedRegion(cra28);
            CellRangeAddress cra29=new CellRangeAddress(result.size()+1, result.size()+1, 2, 11);
            sheet.addMergedRegion(cra29);
            CellRangeAddress cra30=new CellRangeAddress(result.size()+1, result.size()+1, 12, 19);
            sheet.addMergedRegion(cra30);
            CellRangeAddress cra31=new CellRangeAddress(result.size()+1, result.size()+1, 20, 29);
            sheet.addMergedRegion(cra31);

        }else{//小学、初中、高中
            Cell cell1 = headRow0.createCell(2);
            cell1.setCellValue("一年级");
            CellRangeAddress cra1=new CellRangeAddress(0, 0, 2, 3);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);

            Cell cell3 = headRow0.createCell(4);
            cell3.setCellValue("二年级");
            CellRangeAddress cra2=new CellRangeAddress(0, 0, 4, 5);
            sheet.addMergedRegion(cra2);
            cell3.setCellStyle(headerStyle);

            Cell cell5 = headRow0.createCell(6);
            cell5.setCellValue("三年级");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 6, 7);
            sheet.addMergedRegion(cra3);
            cell5.setCellStyle(headerStyle);

            Cell cell7 = headRow0.createCell(8);
            cell7.setCellValue("四年级");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 8, 9);
            sheet.addMergedRegion(cra4);
            cell7.setCellStyle(headerStyle);

            Cell cell9 = headRow0.createCell(10);
            cell9.setCellValue("五年级");
            CellRangeAddress cra5=new CellRangeAddress(0, 0, 10, 11);
            sheet.addMergedRegion(cra5);
            cell9.setCellStyle(headerStyle);

            Cell cell11 = headRow0.createCell(12);
            cell11.setCellValue("六年级");
            CellRangeAddress cra6=new CellRangeAddress(0, 0, 12, 13);
            sheet.addMergedRegion(cra6);
            cell11.setCellStyle(headerStyle);

            Cell cell13 = headRow0.createCell(14);
            cell13.setCellValue("七年级");
            CellRangeAddress cra7=new CellRangeAddress(0, 0, 14, 15);
            sheet.addMergedRegion(cra7);
            cell13.setCellStyle(headerStyle);

            Cell cell15 = headRow0.createCell(16);
            cell15.setCellValue("八年级");
            CellRangeAddress cra8=new CellRangeAddress(0, 0, 16, 17);
            sheet.addMergedRegion(cra8);
            cell15.setCellStyle(headerStyle);

            Cell cell17 = headRow0.createCell(18);
            cell17.setCellValue("九年级");
            CellRangeAddress cra9=new CellRangeAddress(0, 0, 18, 19);
            sheet.addMergedRegion(cra9);
            cell17.setCellStyle(headerStyle);

            Cell cell19 = headRow0.createCell(20);
            cell19.setCellValue("高一年级");
            CellRangeAddress cra10=new CellRangeAddress(0, 0, 20, 21);
            sheet.addMergedRegion(cra10);
            cell19.setCellStyle(headerStyle);

            Cell cell21 = headRow0.createCell(22);
            cell21.setCellValue("高二年级");
            CellRangeAddress cra11=new CellRangeAddress(0, 0, 22, 23);
            sheet.addMergedRegion(cra11);
            cell21.setCellStyle(headerStyle);

            Cell cell23 = headRow0.createCell(24);
            cell23.setCellValue("高三年级");
            CellRangeAddress cra12=new CellRangeAddress(0, 0, 24, 25);
            sheet.addMergedRegion(cra12);
            cell23.setCellStyle(headerStyle);

            Cell cell25 = headRow0.createCell(26);
            cell25.setCellValue("高预年级");
            CellRangeAddress cra13=new CellRangeAddress(0, 0, 26, 27);
            sheet.addMergedRegion(cra13);
            cell25.setCellStyle(headerStyle);

            Cell cell27 = headRow0.createCell(28);
            cell27.setCellValue("高四年级");
            CellRangeAddress cra14=new CellRangeAddress(0, 0, 28, 29);
            sheet.addMergedRegion(cra14);
            cell27.setCellStyle(headerStyle);

            Cell cell29 = headRow0.createCell(30);
            cell29.setCellValue("全校总人数");
            CellRangeAddress craHj=new CellRangeAddress(0, 0, 30, 31);
            sheet.addMergedRegion(craHj);
            cell29.setCellStyle(headerStyle);

            Cell cell31 = headRow0.createCell(32);
            cell31.setCellValue("台湾");
            CellRangeAddress cra15=new CellRangeAddress(0, 1, 32, 32);
            sheet.addMergedRegion(cra15);
            cell31.setCellStyle(headerStyle);

            Cell cell32 = headRow0.createCell(33);
            cell32.setCellValue("香港");
            CellRangeAddress cra16=new CellRangeAddress(0, 1, 33, 33);
            sheet.addMergedRegion(cra16);
            cell32.setCellStyle(headerStyle);

            Cell cell33 = headRow0.createCell(34);
            cell33.setCellValue("澳门");
            CellRangeAddress cra17=new CellRangeAddress(0, 1, 34, 34);
            sheet.addMergedRegion(cra17);
            cell33.setCellStyle(headerStyle);

            Cell cell34 = headRow0.createCell(35);
            cell34.setCellValue("华侨子女");
            CellRangeAddress cra18=new CellRangeAddress(0, 1, 35, 35);
            sheet.addMergedRegion(cra18);
            cell34.setCellStyle(headerStyle);

            Cell cell35 = headRow0.createCell(36);
            cell35.setCellValue("外籍");
            CellRangeAddress cra19=new CellRangeAddress(0, 1, 36, 36);
            sheet.addMergedRegion(cra19);
            cell35.setCellStyle(headerStyle);

            Cell cell36 = headRow0.createCell(37);
            cell36.setCellValue("少数民族");
            CellRangeAddress cra20=new CellRangeAddress(0, 1, 37, 37);
            sheet.addMergedRegion(cra20);
            cell36.setCellStyle(headerStyle);

            Cell cell37 = headRow0.createCell(38);
            cell37.setCellValue("本市外区");
            CellRangeAddress cra21=new CellRangeAddress(0, 1, 38, 38);
            sheet.addMergedRegion(cra21);
            cell37.setCellStyle(headerStyle);

            Cell cell38 = headRow0.createCell(39);
            cell38.setCellValue("非本市城镇");
            CellRangeAddress cra22=new CellRangeAddress(0, 1, 39, 39);
            sheet.addMergedRegion(cra22);
            cell38.setCellStyle(headerStyle);

            Cell cell39 = headRow0.createCell(40);
            cell39.setCellValue("无户籍");
            CellRangeAddress cra23=new CellRangeAddress(0, 1, 40, 40);
            sheet.addMergedRegion(cra23);
            cell39.setCellStyle(headerStyle);

            Cell cell40 = headRow0.createCell(41);
            cell40.setCellValue("非本市农村");
            CellRangeAddress cra24=new CellRangeAddress(0, 1, 41, 41);
            sheet.addMergedRegion(cra24);
            cell40.setCellStyle(headerStyle);

            Cell cell41 = headRow0.createCell(42);
            cell41.setCellValue("所属街道");
            CellRangeAddress cra25=new CellRangeAddress(0, 1, 42, 42);
            sheet.addMergedRegion(cra25);
            cell41.setCellStyle(headerStyle);

            //最后两行合计格式处理
            CellRangeAddress cra26=new CellRangeAddress(result.size(), result.size(), 30, 31);
            sheet.addMergedRegion(cra26);
            CellRangeAddress cra27=new CellRangeAddress(result.size(), result.size()+1, 0, 0);
            sheet.addMergedRegion(cra27);
            CellRangeAddress cra28=new CellRangeAddress(result.size(), result.size()+1, 1, 1);
            sheet.addMergedRegion(cra28);
            CellRangeAddress cra29=new CellRangeAddress(result.size()+1, result.size()+1, 2, 11);
            sheet.addMergedRegion(cra29);
            CellRangeAddress cra30=new CellRangeAddress(result.size()+1, result.size()+1, 12, 19);
            sheet.addMergedRegion(cra30);
            CellRangeAddress cra31=new CellRangeAddress(result.size()+1, result.size()+1, 20, 29);
            sheet.addMergedRegion(cra31);
        }
    }


    /**
     * 基础基报表统计
     * @param type 0小学、1中学
     * @param sheetTitle sheet名称
     * @param headConfig 表头
     * @param result 当前数据集
     */
    private void flushCurrentJcTjDataToWorkbook(int type,String sheetTitle, String headConfig,List<Map<String,Object>> result) {
        headIndexMap.clear();
        currentData.clear();
        dataHeadMap.clear();

        // 生成一个表格
        Sheet sheet = workbook.createSheet();
        workbook.setSheetName(0, sheetTitle);
        // 设置表格默认列宽度为8个字节
        sheet.setDefaultColumnWidth((short) 8);
        // 设置第一列宽度
        sheet.setColumnWidth(0, 9000);
        // 冻结第一行和第二行
        sheet.createFreezePane( 0, 2, 0, 2 );
        //定义表格样式
        CellStyle style = workbook.createCellStyle();
        //表格边框
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        style.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);


        //定义表格表头样式
        CellStyle headStyle = workbook.createCellStyle();
        //表格边框
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表格水平居中
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 表头背景色
        headStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //表头垂直居中
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        //第一行表头合并居中
        headMergedRegionJc(sheet,type);

        //第二行表头开始
        Row headRow1 = sheet.createRow(1);
        //设置第二行
        sheet.getRow(1).setHeight((short)500);

        //表头处理
        if (headConfig != null && headConfig.matches(JSON_STR)) {
            headConfig = headConfig.substring(1, headConfig.length() - 1);
            String[] items = headConfig.split(",");
            for (int i = 0, len = items.length; i < len; i++) {
                String ch = items[i].split(":")[1];
                String en = items[i].split(":")[0];
                headIndexMap.put(en, i);
                dataHeadMap.put(en, ch);
            }
        }

        //当前结果集处理
        for (Map<String, Object> data : result) {
            Map<String, Object> tempCurrentItem = new DataMap<>();
            for (Map.Entry<String, Integer> headIndex : headIndexMap.entrySet()) {
                String key = headIndex.getKey();
                key = key.toUpperCase();
                Integer idx = headIndex.getValue();
                Object value = data.get(key);
                tempCurrentItem.put(idx.toString(),value==null?"":value);
            }
            currentData.add(tempCurrentItem);
        }

        // 产生表格标题行
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                Cell headCell = headRow1.createCell(cellIndex);
                headCell.setCellType(Cell.CELL_TYPE_STRING);
                headCell.setCellValue(head.getValue());
                headCell.setCellStyle(headStyle);

            }
        }
        // 遍历集合数据，产生数据行
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 2);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                contentCell.setCellValue(val);
                contentCell.setCellStyle(style);
                sheet.getRow(row+2).setHeight((short)350);
            }
        }
    }


    /**
     * 基础基统计表头样式
     */
    private void headMergedRegionJc(Sheet sheet,int type){
        CellStyle headerStyle = workbook.createCellStyle();
        //头部边框
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        //表头水平居中
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        //表头垂直居中
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 表头背景色
        headerStyle.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Row headRow0 = sheet.createRow(0);
        //设置第一行
        sheet.getRow(0).setHeight((short)500);

        if(type == 0){//小学

//            //第一行第一列
//            Cell cell0 = headRow0.createCell(0);
//            CellRangeAddress cra0=new CellRangeAddress(0, 1, 0, 0);
//            sheet.addMergedRegion(cra0);
//            cell0.setCellStyle(headerStyle);
//            cell0.setCellValue("班额");

        }else{//初中、高中

            //第一行第一列
            Cell cell0 = headRow0.createCell(0);
            CellRangeAddress cra0=new CellRangeAddress(0, 1, 0, 0);
            sheet.addMergedRegion(cra0);
            cell0.setCellStyle(headerStyle);
            cell0.setCellValue("班额");

            //第一行第二列
            Cell cell1 = headRow0.createCell(1);
            CellRangeAddress cra1=new CellRangeAddress(0, 1, 1, 1);
            sheet.addMergedRegion(cra1);
            cell1.setCellStyle(headerStyle);
            cell1.setCellValue("编号");

            //第一行第三列
            Cell cell2 = headRow0.createCell(2);
            CellRangeAddress cra2=new CellRangeAddress(0, 1, 2, 2);
            sheet.addMergedRegion(cra2);
            cell2.setCellStyle(headerStyle);
            cell2.setCellValue("合计");

            Cell cell3 = headRow0.createCell(3);
            cell3.setCellValue("初中");
            CellRangeAddress cra3=new CellRangeAddress(0, 0, 3, 7);
            sheet.addMergedRegion(cra3);
            cell3.setCellStyle(headerStyle);

            Cell cell4 = headRow0.createCell(8);
            cell4.setCellValue("高中");
            CellRangeAddress cra4=new CellRangeAddress(0, 0, 8, 11);
            sheet.addMergedRegion(cra4);
            cell4.setCellStyle(headerStyle);
        }
    }


    private void flushCurrentDataToWorkbook() {
        Sheet sheet = workbook.createSheet();
        Row headRow = sheet.createRow(0);
        for (Map.Entry<String, String> head : dataHeadMap.entrySet()) {
            Integer cellIndex = headIndexMap.get(head.getKey());
            if (cellIndex != null) {
                Cell headCell = headRow.createCell(cellIndex);
                headCell.setCellType(Cell.CELL_TYPE_STRING);
                headCell.setCellValue(head.getValue());
            }
        }
        for (int row = 0, len = currentData.size(); row < len; row++) {
            Row contentRow = sheet.createRow(row + 1);
            for (Map.Entry<String, Object> curr : currentData.get(row).entrySet()) {
                int cellIndex = Integer.valueOf(curr.getKey());
                String val = curr.getValue() == null ? "" : curr.getValue().toString().trim();
                Cell contentCell = contentRow.createCell(cellIndex);
                contentCell.setCellValue(val);
            }
        }
    }

    private ExportExcel(String filePath, String headConfig) throws IOException {
        if (filePath != null && filePath.toLowerCase().endsWith("xlsx")) {
            this.workbook = new XSSFWorkbook();
        } else if (filePath != null && filePath.toLowerCase().endsWith(".xlsx")) {
//            this.workbook = new HSSFWorkbook();
            this.workbook = new XSSFWorkbook();
        } else {
            throw new IOException();
        }
        this.filePath = filePath;
        if (headConfig != null && headConfig.matches(JSON_STR)) {
            headConfig = headConfig.substring(1, headConfig.length() - 1);
            String[] items = headConfig.split(",");
            for (int i = 0, len = items.length; i < len; i++) {
                String ch = items[i].split(":")[1];
                String en = items[i].split(":")[0];
                headIndexMap.put(en, i);
                dataHeadMap.put(en, ch);
            }
        }
    }

    public static ExportExcel createExportExcel(String filePath, String headConfig) throws IOException {
        return new ExportExcel(filePath, headConfig);
    }


    public void createExportExcel(String filePath) throws IOException {
        if (filePath != null && filePath.toLowerCase().endsWith("xlsx")) {
            this.workbook = new XSSFWorkbook();
        } else if (filePath != null && filePath.toLowerCase().endsWith(".xlsx")) {
//            this.workbook = new HSSFWorkbook();
            this.workbook = new XSSFWorkbook();
        } else {
            throw new IOException();
        }
        this.filePath = filePath;
    }

}
