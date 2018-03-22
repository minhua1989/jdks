package com.xf.jdks.commons.util;


import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Format {
    public final static char CHAR_ENTER10 = 10; //回车符
    public final static char CHAR_NEWLINE13 = 13; //换行符
    private static long num = 1;

    /**
     * date 转换为String
     * @param date 时间
     * @return
     */
    public static String getDateToString(Date date){
    	 DateFormat df = new SimpleDateFormat("yyyyMMdd");
    	 return df.format(date);
    }

    /**
     * 获取 系统时间 long 形式 到毫秒
     * @return
     */
    public static String getDateLong(String time){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        long date = 0;
        try {
            date = fmt.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date);
    }

    /*16进制到10进制转换*/
    public static int hex2Decimalization(String strHex)
        throws Exception {
        int iReturn = 0;
        int b = 0;
        char[] ch = strHex.trim().toLowerCase().toCharArray();

        for (int i = ch.length - 1, j = 0; i > -1; i--, j++) {
            if ((ch[i] >= '0') && (ch[i] <= '9')) {
                b = ch[i] - '0';
            } else if ((ch[i] >= 'a') && (ch[i] <= 'f')) {
                b = ch[i] - 'a' + 10;
            } else {
                throw new Exception("not 0~9 or a~f char error!");
            }

            long lValue = Math.round(Math.pow(16, j));

            //System.out.println(lValue) ;
            iReturn += (b * Integer.parseInt(Long.toString(lValue)));
        }

        return iReturn;
    }
    //add by jiwei:java中求2日期的天数差值
    public static long dateSub(String startTime,String endTime){
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	Calendar calendar = Calendar.getInstance();
    	long t1=0;
    	long t2=0;
    	long theday=0;
    	try {
			calendar.setTime(formatter.parse(startTime));
			t1 = calendar.getTimeInMillis();
			calendar.setTime(formatter.parse(endTime));
			t2 = calendar.getTimeInMillis();
			theday=(t2-t1)/(1000*60*60*24);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return theday;
    }

    public static String getDateTimeLong() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        Date Now = new Date();
        String NDate = formatter.format(Now);
        return NDate;
    }

    //对String类型的时间进行格式化
    public static String getDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formatter.format(date);
    }



    //对String类型的时间进行格式化
    public static String getStrData(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatter.format(date);
    }



    public static Date getDateFormat(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dd = null;

        try {
            dd = formatter.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return dd;
    }
    public static String convertDate(String date){
    	String conDate = date;
    	String year = conDate.substring(0,4);
    	String month = conDate.substring(4,6);
    	String day = conDate.substring(6,8);
    	String dates = year+"-"+month+"-"+day;
    	return dates;
    }

    //取得系统时间
    public static String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date Now = new Date();
        String NDate = formatter.format(Now);

        return NDate;
    }

    //取得系统时间
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date Now = new Date();
        String NDate = formatter.format(Now);

        return NDate;
    }

    //取得系统时间
    public static String getDateNumbers() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date Now = new Date();
        String NDate = formatter.format(Now);

        return NDate;
    }


    public static String getWeekOfDate() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }



    /**
     * @return
     * 取得当前年份
     */
    public static String getYearNumbers() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date Now = new Date();
        String NDate = formatter.format(Now);

        return NDate;
    }

    //取得系统时间
    public static String getIndexDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date Now = new Date();
        String NDate = formatter.format(Now);

        return NDate;
    }

    //取得系统时间 by kevin 在分区存储的时候用。分区的依据是月份
    public static String getMonthNumbers() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        Date Now = new Date();
        String NDate = formatter.format(Now);

        return NDate;
    }

    public static String getStrDate(String DateString, int length) {
        return DateString.substring(0, length);
    }

    public static String getNow(String dateStr) {
        return dateStr.substring(0, 8);
    }

    public static int getPageCount() {
        return 15;
    }

    public static boolean compareTo(String last, String now) {
        try {
            DateFormat formatter = DateFormat.getDateInstance();
            Date temp1 = formatter.parse(last);
            Date temp2 = formatter.parse(now);

            //  long longa=temp1.getTime();
            // long longb=temp2.getTime();
            //  if(longa>longb) return false;
            //   if(longa<longb) return true;
            if (temp1.after(temp2)) {
                return false;
            } else if (temp1.before(temp2)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * 字符串替换，将 source 中的 oldString 全部换成 newString
     *
     * @param source 源字符串
     * @param oldString 老的字符串
     * @param newString 新的字符串
     * @return 替换后的字符串
     */
    public static String Replace(String source, String oldString,
        String newString) {
        StringBuffer output = new StringBuffer();

        int lengthOfSource = source.length(); // 源字符串长度
        int lengthOfOld = oldString.length(); // 老字符串长度

        int posStart = 0; // 开始搜索位置
        int pos; // 搜索到老字符串的位置

        while ((pos = source.indexOf(oldString, posStart)) >= 0) {
            output.append(source.substring(posStart, pos));

            output.append(newString);
            posStart = pos + lengthOfOld;
        }

        if (posStart < lengthOfSource) {
            output.append(source.substring(posStart));
        }

        return output.toString();
    }

    /*
         public static String ReplaceIgnoreCase(String source, String oldString, String newString) {
         }
     */

    /**
     * 将字符串格式化成 HTML 代码输出
     * 只转换特殊字符，适合于 HTML 中的表单区域
     *
     * @param str 要格式化的字符串
     * @return 格式化后的字符串
     */
    public static String toHtmlInput(String str) {
        if (str == null) {
            return null;
        }

        String html = new String(str);

        html = Replace(html, "&", "&amp;");
        html = Replace(html, "<", "&lt;");
        html = Replace(html, ">", "&gt;");
        html = Replace(html, "'", "&#39;");

        return html;
    }

    public static String toHtmlOutput(String str) {
        if (str == null) {
            return null;
        }

        String html = new String(str);

        html = Replace(html, "&amp;", "&");
        html = Replace(html, "&lt;", "<");
        html = Replace(html, "&gt;", ">");
        html = Replace(html, "&#39;", "'");

        return html;
    }

    /**
     * 将字符串格式化成 HTML 代码输出
     * 除普通特殊字符外，还对空格、制表符和换行进行转换，
     * 以将内容格式化输出，
     * 适合于 HTML 中的显示输出
     *
     * @param str 要格式化的字符串
     * @return 格式化后的字符串
     */
    public static String toHtml(String str) {
        if (str == null) {
            return null;
        }

        String html = new String(str);

        html = toHtmlInput(html);
        html = Replace(html, "\r\n", "\n");
        html = Replace(html, "\n", "<br>\n");
        html = Replace(html, "\t", "    ");
        html = Replace(html, "  ", " &nbsp;");

        return html;
    }

    public static String outHtml(String str) {
        if (str == null) {
            return null;
        }

        String html = new String(str);

        html = toHtmlOutput(html);
        html = Replace(html, "\n", "\r\n");
        html = Replace(html, "<br>\n", "\n");
        html = Replace(html, "<br>", "");
        html = Replace(html, "    ", "\t");
        html = Replace(html, " &nbsp;", "  ");

        return html;
    }

    /**
     * 将普通字符串格式化成数据库认可的字符串格式
     *
     * @param str 要格式化的字符串
     * @return 合法的数据库字符串
     */
    public static String toSql(String str) {
        String sql = new String(str);

        return Replace(sql, "'", "''");
    }

    public static String getSearchDay(int limit) {
        int lim = limit * 24 * 60 * 60 * 1000;
        Date nowTime = new Date();
        long lnow = nowTime.getTime();

        //String strNow=String.valueOf(lnow);
        //int nnn=Integer.parseInt(strNow)-lim;
        Date aa = new Date(lnow + lim);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String NDate = formatter.format(aa);

        return NDate;
    }


    /*
     * @author: wujg
     * 取当前时间的前n天，后n天。调用searchDay，但是不要返回时间
     */
    public static String getSearchDayWithoutTime(int limit) {
    	return Format.getDate(Format.getSearchDay(limit));
    }

    //by wujg:java中怎么获得当前月份或指定月份的第一天及最后一天？  未调试

    /**
      * java获得所在月份的最后一天
      * @param d　月份所在的时间
      * @return 月份的最后一天
      * @author  获得所在月份的最后一天
      */
    public static Date getLastDateByMonth(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.MONTH, now.get(Calendar.MONTH) + 1);
        now.set(Calendar.DATE, 1);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - 1);
        now.set(Calendar.HOUR, 11);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);

        return now.getTime();
    }

    /**
     * java获得所在月份的第一天
     * @param d　月份所在的时间
     * @return 月份的最后一天
     * @author c
     */
    public static Date getFirstDateByMonth(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, 0);
        now.set(Calendar.HOUR, 12);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);

        return now.getTime();
    }

    //end  wujg

    /**
     * 输入日期，获得当周第一天，星期天
     * @author wujg
     * @param date String
     * @return String
     */
    public static String getFirstDayByWeek(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        String NDate = formatter.format(c.getTime());

        return NDate;
    }

    /**
     * 输入日期，获得当周最后一天，星期六
     * @author wujg
     * @param date String
     * @return String
     */
    public static String getLastDayByWeek(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);

        String NDate = formatter.format(c.getTime());

        return NDate;
    }

    /**
     * 编码转换没有启用
     * @param str String
     * @return String
     */
    public static String toChinese(String str) {
        String temp = "";

        try {
            if ((str != null) && (str.equals("") == false)) {
                temp = new String(str.getBytes("ISO8859-1"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp; //"null";
    }

    public static boolean IsNum(String str_x) {
        try {
            int i = Integer.parseInt(str_x);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public static Vector AnalyzeTonken(String oldStr, String token) {
        Vector vect = new Vector();
        StringTokenizer oldToken = new StringTokenizer(oldStr, token);

        while (oldToken.hasMoreElements()) {
            String newStr = oldToken.nextToken();
            vect.addElement(newStr);
        }

        return vect;
    }

    public static String[] AnalyzeTonkenB(String oldStr, String token) {
        String[] arr;
        StringTokenizer oldToken = new StringTokenizer(oldStr, token);
        int len = oldToken.countTokens();
        arr = new String[len];

        int i = 0;

        while (oldToken.hasMoreElements()) {
            String newStr = oldToken.nextToken();
            arr[i] = newStr;
            i++;
        }

        return arr;
    }

    /*public static String[] AnalyzeTonkenDelimsbak(String oldStr,String token){
      String[] arr;
      int i=0;
      int newlen=0,oldlen=0,startlen=0;
      StringTokenizer oldToken=new StringTokenizer(oldStr,token,true);
      int tokenlen = token.length();
      int tokennums=0;
      String newStr = oldStr;
      while((newlen=newStr.indexOf(token))!=-1)
      {
          tokennums++;
          newStr=newStr.substring(newlen+tokenlen);
      }
      int len=oldToken.countTokens()-tokennums-1;

      arr=new String[len];
      oldlen = oldStr.length();
      newStr = oldStr;
        while((newlen=newStr.indexOf(token))!=-1)
        {
          if(newlen==0)
            arr[i]="1";
          else
            arr[i]=newStr.substring(0,newlen);
          startlen = startlen+newlen+tokenlen;
          newStr=newStr.substring(newlen+tokenlen);
          i++;
        }
        if(startlen!=oldlen)
        {
          arr[i] = oldStr.substring(startlen);
        }
      return arr;
     }*/
    /*替代Split函数用,czh
     *oldStr 要转变的字符串,去除了空元素
     *token  分隔符
     */
    public static String[] splitToArray(String oldStr, String token) {
        String[] arr;
        StringTokenizer oldToken = new StringTokenizer(oldStr, token);
        int len = oldToken.countTokens();
        arr = new String[len];

        int i = 0;

        //System.out.println("c:"+oldToken.countTokens());
        while (oldToken.hasMoreElements()) {
            String newStr = oldToken.nextToken();
            arr[i] = newStr;
            i++;
        }

        return arr;
    }

    /*替代Split函数用
     *oldStr 要转变的字符串,不去除空元素
     *token  分隔符
     *ChenZhuHua 2004/9/2
     */
    public static String[] splitToArrayWithBlank(String strOld, String spliter) {
        String[] arr = new String[strOld.length() + 1];
        String[] arr2;

        if (strOld == null) {
            return null;
        }

        String sTemp = strOld;
        int pos = sTemp.indexOf(spliter);
        int iLen = spliter.length(); //分隔符的长度
        int i = 0; //计数器

        if (strOld.length() == 0) {
            arr[0] = "";

            return arr;
        }

        if (pos != -1) {
            while (pos != -1) {
                arr[i++] = sTemp.substring(0, pos);
                sTemp = sTemp.substring(pos + iLen, sTemp.length());
                pos = sTemp.indexOf(spliter);

                if ((sTemp.length() == 0) || (pos == -1)) {
                    arr[i++] = sTemp;

                    break;
                }
            }
        } else { //没有分隔符，返回本身的单元素数组
            arr[0] = sTemp;
            i = 1;
        }

        arr2 = new String[i];

        for (i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                arr2[i] = arr[i];
            } else {
                break;
            }
        }

        return arr2;
    }

    public static String[] AnalyzeTonkenDelims(String oldStr, String token) {
        String[] arr;
        String[] arrRet;
        StringTokenizer oldToken = new StringTokenizer(oldStr, token, true);
        int len = oldToken.countTokens();
        arr = new String[len];

        int i = 0;

        while (oldToken.hasMoreElements()) {
            String newStr = oldToken.nextToken();
            arr[i] = newStr;
            i++;
        }

        int pos = 0;
        Vector vect = new Vector();

        for (int j = 0; j < arr.length; j++) {
            if (arr[j].equals(token)) {
                if (j == 0) {
                    vect.insertElementAt(" ", pos);
                    pos++;
                } else {
                    if (arr[j - 1].equals(token)) {
                        arr[j - 1] = " ";
                        vect.insertElementAt(arr[j - 1], pos);
                        pos++;
                    }
                }
            } else {
                vect.insertElementAt(arr[j], pos);
                pos++;
            }
        }

        arrRet = new String[vect.size()];

        for (i = 0; i < vect.size(); i++) {
            arrRet[i] = vect.get(i).toString();
        }

        return arrRet;
    }

    // 将String转换成InputStream
    public static InputStream StringTOInputStream(String in) throws Exception {

        ByteArrayInputStream is = new ByteArrayInputStream(in.getBytes("ISO-8859-1"));

        return is;

    }

    //检查是否含有非法字符
    public static boolean illegalCharChecking(String str) {
        String[] illegalChar = {
                "~", "!", "#", "$", "%", "^", "&", "*", "{", "}", "\\", ";",
                "'"
            };

        for (int i = 0; i < illegalChar.length; i++) {
            if (str.indexOf(illegalChar[i]) >= 0) {
                return true;
            }
        }

        return false;
    }

    //是否由数字构成
    public static boolean isNumber(String str) {
        String number = "1234567890";
        char[] tmp = str.toCharArray();

        for (int i = 0; i < tmp.length; i++) {
            if (number.indexOf(String.valueOf(tmp[i])) == -1) {
                return false;
            }
        }

        return true;
    }

    //是否由数字和英文字母构成
    public static boolean isNumberAndLetter(String str) {
        String example = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] tmp = str.toCharArray();

        for (int i = 0; i < tmp.length; i++) {
            if (example.indexOf(String.valueOf(tmp[i])) == -1) {
                return false;
            }
        }

        return true;
    }

    //判断是否含有敏感词
    public static Boolean filterMsg(String inStr) {
        String rtStr = inStr;
        String[] strArray = {
                "妈妈的", "我靠", "fuck", "bitch", "他妈的", "性爱", "法轮", "falun", "江泽民",
                "snk.ni8.net", "操你妈", "三级片", "台独", "我操", "去死"
        };
        Boolean flag=false;
        for (int i = 0; i < strArray.length; i++) {
            if(rtStr.contains(strArray[i])){
                flag=true;
                break;
            }
        }
        return flag;
    }

    public static String filterStr(String inStr) {
        String rtStr = inStr;
        String[] strArray = {
                "妈妈的", "我靠", "fuck", "bitch", "他妈的", "性爱", "法轮", "falun", "江泽民",
                "snk.ni8.net", "操你妈", "三级片", "台独", "我操", "去死"
            };


        for (int i = 0; i < strArray.length; i++) {
            rtStr = rtStr.replaceAll(strArray[i], "****");
        }

        return rtStr;
    }

    public static String getLimitLengthString(String str) {
        try {
            int j = 0;
            String rtStr = "";

            for (int i = 0; i < str.length(); i++) {
                char tmpChar = str.charAt(i);

                if (((tmpChar >= '\u4E00') && (tmpChar <= '\u9FA5')) ||
                        ((tmpChar != 35) && (tmpChar != 38) && (tmpChar >= 33) &&
                        (tmpChar <= 46))) {
                    j++;
                    rtStr = rtStr + str.valueOf(tmpChar);
                }
            }

            return rtStr;
        } catch (Exception ex) {
            ex.printStackTrace();

            return "";
        }
    }

    public static String inHTML(String str) {
        String sTemp;
        sTemp = str;

        if (sTemp.equals("")) {
            System.exit(0);
        }

        sTemp = sTemp.replaceAll("&", "&amp;");
        sTemp = sTemp.replaceAll("<", "&lt;");
        sTemp = sTemp.replaceAll(">", "&gt;");
        sTemp = sTemp.replaceAll("\"", "&quot;");

        return sTemp;
    }

    /**
     * 返回随机数
     * */
    public static String getRandomNumber() {
        //    	Random rd = new Random();
        //    	return String.valueOf(rd.nextInt());
        int max = 9999;
        int min = 1000;
        int num = (int) (((max - min) * Math.random()) + min);
        System.out.println(num);

        return String.valueOf(num);
    }

    public static final String round(float x) {
        BigDecimal big = new BigDecimal(x);
        BigDecimal temp = big.setScale(2, BigDecimal.ROUND_HALF_UP);



        return  temp.toString();
    }

    /******************************************************************************************************/

    /**
     * 比较了两个LIST，将两个LIST中相同的OBJ移动掉 (OBJ == OBJ，完全相同)
     * 返回一个新的LIST，新的LIST中包含剩余的OBJ
     * @param list1 list1是全集
     * @param list2 list2是子集
     * */
    public static List compareListRemoveSameObj(List list1,List list2){
    	List compareResult = new ArrayList();
    	for(int i=0;i<list1.size();i++){
    		Object obj1 = list1.get(i);
    		//在比较结果的LIST中放入OBJ
    		compareResult.add(obj1);
    		for(int j=0;j<list2.size();j++){
    			Object obj2 = list2.get(j);
    			if(obj1 == obj2){
    				//在比较结果的LIST中移除OBJ
    				compareResult.remove(obj1);
    				break;
    			}
    		}
    	}
    	return compareResult;
    }
    public static void main(String[] args) {
        //    	String aa="&nbsp;&nbsp;大发洒的发阿,.，。！!瑟大<STRONG><EM><U><FONT size=3>阿瑟<FONT color=#ff0000>大发洒的发阿瑟大</FONT></FONT></U></EM></STRONG>发撒旦弗萨发洒的发洒的发阿瑟大发阿瑟大发阿瑟";
        //
        //    	String tmp=Format.getLimitLengthString( aa);
        //

    	//String d=Format.round(new Float(100.10).floatValue());

//    	String d=Format.getWeekOfDate();
//
//    	System.out.println("d--"+d);


        //    	String tmp=Format.getSearchDay(1);
        //    	System.out.println(tmp);

//    	String html = "<SPAN style='COLOR: #ff0000'>撒地方打三分倒萨</SPAN>";
//    	  System.out.println(removeInTag(html));

    	//--------生成指定数量的UUID-----start
//    	Connection con = null;
//    	try {
//			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
//			con = DriverManager.getConnection("jdbc:oracle:thin:@202.121.86.43:1521:elearn","COURSEEVALUSER","COURSEEVALUSER");
//			if (con != null) {
//				System.out.println("数据库连接成功");
//				int s = 1;
//		    	for (int i = 0; i < s; i++) {
//		    		String uid = UUID.randomUUID().toString();
//		    		System.out.println("第"+(i+1)+"个UUID：       "+uid);
////		    		String sql = "update t_roleinfo set id = '"+uid+"' where id = '"+(i+1)+"'";
////		    		PreparedStatement psmt = con.prepareStatement(sql);
////		    		psmt.execute();
//				}
//
//
//
//
//			}
//		} catch(ClassNotFoundException e1){
//			e1.printStackTrace();
//			System.out.println("could not find database driver exception.");
//		}catch(SQLException e2){
//			e2.printStackTrace();
//			System.out.println("could not connect to the database exception.");
//		}catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//--------生成指定数量的UUID-----end

		//-----求2日期的差值---------start
    	String startTime = "2011-4-7";
    	String endTime = "2011-4-20";
		long l = Format.dateSub(startTime, endTime);
		System.out.println(l);
		//-----求2日期的差值---------end


    }
    public static String removeInTag(String a) {
    	     return a.replaceAll("</?[^>]+>","");
    	 }
//get random number (six)
    public static String getRandomNum() {

		Random random = new Random();
		int ran = random.nextInt(999999);
		String nom = String.valueOf(ran);

		switch (nom.length()) {

		case 1:
			nom = "00000" + nom;
			break;
		case 2:
			nom = "0000" + nom;
			break;
		case 3:
			nom = "000" + nom;
			break;
		case 4:
			nom = "00" + nom;
			break;
		case 5:
			nom = "0" + nom;
			break;

		}

		return nom;

	}

    /**
	 * 以逗号隔开的字符串处理为SQL中使用
	 * @param param
	 * @return
	 */
	public static String paramColumns(String param) {
		String columns = "";
		if (param.indexOf(",") > 0) {
			for (String s : param.split(",")) {
				columns += "'" + s.trim() + "',";
			}
			return columns.substring(0, columns.length() - 1);
		} else {
			return "'" + param + "'";
		}
	}



	/**
	 * 对比字符串"b"是否在以逗号隔开的字符串"a"中，是返回ture，否返回false
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean isIn(String a,String b){
		for (String s : a.split(",")) {
			if(s.trim().equals(b)){
				return true;
			}
		}
		return false;
	}

	public static long dataComp(String test,String target){
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		long res=0L;
		if(null!=test&&null!=target){
			try{
				return formatter.parse(test).compareTo(formatter.parse(target));
			}
			catch(ParseException e){
				e.printStackTrace();
			}
		}
		return res;
	}


    public static String getIpAddr(HttpServletRequest request) {


       String ip = request.getHeader("x-forwarded-for");
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                 ip = request.getHeader("Proxy-Client-IP");
                }
           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
               ip = request.getHeader("WL-Proxy-Client-IP");
               }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT-IP");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED-FOR");
            }
            if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        return ip;
       }

    /**
     * 专门为了学籍系统记录用户访问市级webservice的日志
     * @param msg
     * @throws IOException
     */
    public static synchronized void printLog(String msg) throws IOException {
        String fileName ="shsimsj_webservice_"+Format.getDateNumbers()+".log";
        File log = new File("/home",fileName);
        FileWriter fw = new FileWriter(log,true);
        try {
            fw.write(num+":"+msg+"\n");
            fw.flush();
            num++;
        } finally {
            fw.close();
        }
    }

}
