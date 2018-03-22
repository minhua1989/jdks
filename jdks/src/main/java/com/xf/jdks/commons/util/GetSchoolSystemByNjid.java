package com.xf.jdks.commons.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lirushan on 2016/8/3.
 */
public class GetSchoolSystemByNjid {

//    public static String getNjid(String xz,String xd) throws DictIsNotFoundException {
//        GetSchoolSystemByNjid g = new GetSchoolSystemByNjid();
//        String[] njdms = g.getNjCode(xz,xd).split(",");
//        StringBuffer sb = new StringBuffer();
//        for(String njdm:njdms){
//
//        }
//    }

    public static String getNjCode(String xz,String xd){
        //5:一年级, 6:二年级 7:三年级,8:四年级,9:五年级,10:六年级,11:七年级,12:八年级,13:九年级,14:高一,15:高二,16:高三
        if("01".equals(xz)){//小学五年制
            if("1".equals(xd)) return "11,12,13,14,15";
            else return null;
        }
        if("02".equals(xz)){//小学6年制
            if("1".equals(xd))return "11,12,13,14,15,16";
            else return null;
        }
        if("03".equals(xz)){//初中3年制
            if("2".equals(xd)) return "17,18,19";
            else return null;
        }
        if("04".equals(xz)){//初中4年制
            if("2".equals(xd)) return "16,17,18,19";
            else return null;
        }
        if("05".equals(xz)){//高中三年制
            if("3".equals(xd)) return "31,32,33";
            else return null;
        }
        if("06".equals(xz)){//完中
            if("2".equals(xd)) return "16,17,18,19";
            else if("3".equals(xd)) return "31,32,33";
            else if("23".equals(xd)) return "16,17,18,19,31,32,33";
            else return null;
        }
        if("07".equals(xz)){//九年一贯制
            if("1".equals(xd)) return "11,12,13,14,15";
            else if("2".equals(xd)) return "16,17,18,19";
            else if("12".equals(xd)) return "11,12,13,14,15,16,17,18,19";
            else return null;
        }
        if("08".equals(xz)){//十年一贯制(同上)
            if("1".equals(xd)) return "11,12,13,14,15";
            else if("2".equals(xd)) return "16,17,18,19";
            else if("12".equals(xd)) return "11,12,13,14,15,16,17,18,19";
            else return null;
        }
        if("09".equals(xz)){//十二年一贯制
            if("1".equals("xd")) return "11,12,13,14,15";
            else if("2".equals(xd)) return "16,17,18,19";
            else if("3".equals(xd)) return "31,32,33";
            else if("12".equals(xd)) return "11,12,13,14,15,16,17,18,19";
            else if("13".equals(xd)) return "11,12,13,14,15,31,32,33";
            else if("23".equals(xd)) return "16,17,18,19,31,32,33";
            else if("123".equals(xd)) return "11,12,13,14,15,16,17,18,19,31,32,33";
            else return null;
        }
        return null;
    }



     private List<String> schoolSystemRefGradeCode(String schoolSystem, String flag){
        List<String>gradeCodeList = new ArrayList<String>();
         //全局 NORMAL    新生 NEWEST     毕业GRADUATED
        //小学五年制
        if(schoolSystem.equals("01")){
            if(flag.equals("NORMAL")||flag.equals("NEWEST")){
                    gradeCodeList.add("11");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("12");
                gradeCodeList.add("13");
                gradeCodeList.add("14");
            }
            if(flag.equals("NORMAL")||flag.equals("GRADUATED")){
                gradeCodeList.add("15");
            }
            //小学六年制
        }else if(schoolSystem.equals("02")){
            if(flag.equals("NORMAL")||flag.equals("NEWEST")){
                gradeCodeList.add("11");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("12");
                gradeCodeList.add("13");
                gradeCodeList.add("14");
                gradeCodeList.add("15");
            }
            if(flag.equals("NORMAL")||flag.equals("GRADUATED")){
                gradeCodeList.add("16");
            }
            //初中三年制
        }else if(schoolSystem.equals("03")){
            if(flag.equals("NORMAL")||flag.equals("NEWEST")){
                gradeCodeList.add("17");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("18");
            }
            if(flag.equals("NORMAL")||flag.equals("GRADUATED")){
                gradeCodeList.add("19");
            }
            //初中四年制
        }else if(schoolSystem.equals("04")){
            if(flag.equals("NORMAL")||flag.equals("NEWEST")){
                gradeCodeList.add("16");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("17");
                gradeCodeList.add("18");
            }
            if(flag.equals("NORMAL")||flag.equals("GRADUATED")){
                gradeCodeList.add("19");
            }
            //高中三年制
        }else if(schoolSystem.equals("05")){
            if(flag.equals("NORMAL")||flag.equals("NEWEST")){
                gradeCodeList.add("31");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("32");
            }
            if(flag.equals("NORMAL")||flag.equals("GRADUATED")){
                gradeCodeList.add("33");
            }
            //完中
        }else if(schoolSystem.equals("06")){
            if(flag.equals("NEWEST")){
                gradeCodeList.add("16");
                gradeCodeList.add("31");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("16");
                gradeCodeList.add("17");
                gradeCodeList.add("18");
                gradeCodeList.add("19");
                gradeCodeList.add("31");
                gradeCodeList.add("32");
                gradeCodeList.add("33");
            }
            if(flag.equals("GRADUATED")){
                gradeCodeList.add("19");
                gradeCodeList.add("33");
            }
            //九年一贯制
        }else if(schoolSystem.equals("07")){
            if(flag.equals("NEWEST")){
                gradeCodeList.add("11");
                gradeCodeList.add("16");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("11");
                gradeCodeList.add("12");
                gradeCodeList.add("13");
                gradeCodeList.add("14");
                gradeCodeList.add("15");
                gradeCodeList.add("16");
                gradeCodeList.add("17");
                gradeCodeList.add("18");
                gradeCodeList.add("19");
            }
            if(flag.equals("GRADUATED")){
                gradeCodeList.add("15");
                gradeCodeList.add("19");
            }
            //十年一贯制
        }else if(schoolSystem.equals("08")){
            if(flag.equals("NEWEST")){
                gradeCodeList.add("11");
                gradeCodeList.add("16");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("11");
                gradeCodeList.add("12");
                gradeCodeList.add("13");
                gradeCodeList.add("14");
                gradeCodeList.add("15");
                gradeCodeList.add("16");
                gradeCodeList.add("17");
                gradeCodeList.add("18");
                gradeCodeList.add("19");
            }
            if(flag.equals("GRADUATED")){

                gradeCodeList.add("15");
                gradeCodeList.add("19");
            }
            //十二年一贯制
        }else if(schoolSystem.equals("09")){
            if(flag.equals("NEWEST")){
                gradeCodeList.add("11");
                gradeCodeList.add("16");
                gradeCodeList.add("31");
            }
            if(flag.equals("NORMAL")){
                gradeCodeList.add("11");
                gradeCodeList.add("12");
                gradeCodeList.add("13");
                gradeCodeList.add("14");
                gradeCodeList.add("15");
                gradeCodeList.add("16");
                gradeCodeList.add("17");
                gradeCodeList.add("18");
                gradeCodeList.add("19");
                gradeCodeList.add("31");
                gradeCodeList.add("32");
                gradeCodeList.add("33");
            }
            if(flag.equals("GRADUATED")){
                gradeCodeList.add("15");
                gradeCodeList.add("19");
                gradeCodeList.add("33");
            }

            //其他
        }else if(schoolSystem.equals("10")){

        }
        return gradeCodeList;
    }

    public static int getNjYears(String xz,String xd){
        //5:一年级, 6:二年级 7:三年级,8:四年级,9:五年级,10:六年级,11:七年级,12:八年级,13:九年级,14:高一,15:高二,16:高三
        if("01".equals(xz)){//小学五年制
            if("1".equals(xd)) return 5;
            else return 0;
        }
        if("02".equals(xz)){//小学6年制
            if("1".equals(xd))return 6;
            else return 0;
        }
        if("03".equals(xz)){//初中3年制
            if("2".equals(xd)) return 3;
            else return 0;
        }
        if("04".equals(xz)){//初中4年制
            if("2".equals(xd)) return 4;
            else return 0;
        }
        if("05".equals(xz)){//高中三年制
            if("3".equals(xd)) return 3;
            else return 0;
        }
        if("06".equals(xz)){//完中
            if("2".equals(xd)) return 4;
            else if("3".equals(xd)) return 3;
            else if("23".equals(xd)) return 7;
            else return 0;
        }
        if("07".equals(xz)){//九年一贯制
            if("1".equals(xd)) return 5;
            else if("2".equals(xd)) return 4;
            else if("12".equals(xd)) return 9;
            else return 0;
        }
        if("08".equals(xz)){//十年一贯制(同上)
            if("1".equals(xd)) return 5;
            else if("2".equals(xd)) return 4;
            else if("12".equals(xd)) return 9;
            else return 0;
        }
        if("09".equals(xz)){//十二年一贯制
            if("1".equals("xd")) return 5;
            else if("2".equals(xd)) return 4;
            else if("3".equals(xd)) return 3;
            else if("12".equals(xd)) return 9;
            else if("13".equals(xd)) return 8;
            else if("23".equals(xd)) return 7;
            else if("123".equals(xd)) return 12;
            else return 0;
        }
        return 0;
    }

}
