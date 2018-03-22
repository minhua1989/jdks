package com.xf.jdks.commons.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

import com.xf.jdks.dao.entity.AdminInfo;


/**
 * @author Rio-Lee 业务规则验证类（未完成）
 */
public class RuleValidator {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final String REG_CHINESE = "[\\u4E00-\\u9FA5]+";// 是否包含汉字

	private static final String REG_SPACE = "(\\s)+";// 是否包含空格

	private static final String REG_ENANDSPACE = "([a-zA-Z]|\\s)+";// 英文或空格

	private static final String REG_CARDID = "^[1-9][0-9]{5}(19[0-9]{2}|20[0-9]{2})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9X]$";// 身份证号

	private static final String REG_BIRTHDAY = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))" +"|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))" +"|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";// 生日
	
	private static final String REG_BIRTHMONTH = "(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))" +"|((0[469]|11)(0[1-9]|[12][0-9]|30))|(02(0[1-9]|[1][0-9]|2[0-8]))))" +"|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))0229)";//入园年月

	private static final String REG_PHONE = "\\d{4}-\\d{8}|\\d{4}-\\d{7}|\\d{3}-\\d{8}";// 联系电话

	private static final String REG_MOBILE = "^[1][3,4,5,6,7,8]+\\d{9}";// 手机号

	private static final String REG_SENDCODE = "[1-9]\\d{5}(?!\\d)";// 邮编

	private static final String REG_WEBURL = "^(http|www|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";//主页Url
	
	private static final String REG_ENNAME = "([a-zA-Z]|\\d|-)+";//英文名称 
	
	private static final String REG_EMAIL ="^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";//邮箱格式

	private static final String REG_XJBH = "^[1-5][0-5]\\d{3}020\\d{2}\\d{2}\\d{2}$";//学籍编号
	/*public static void main(String[] args) {
		
		 * System.out.println("isSendCode:\t"+isSendCode("4300301"));
		 * System.out.
		 * println("isConcatNumber:\t"+isConcatNumber("139177927410"));
		 * System.out
		 * .println("isConcatNumber:\t"+isConcatNumber("027-833650212"));
		 * System.out.println("isBirthday:\t"+isBirthday("198602111"));
		 * System.out.println("isCardId:\t"+isCardId("42010419860211271f"));
		 * System
		 * .out.println("isEnglishOrSpace:\t"+isEnglishOrSpace("rio童 lee"));
		 * System.out.println("hasChinese:\t"+hasChinese("s"));
		 * System.out.println("hasSpace:\t"+hasSpace("李童健"));
		 
		colNameToGetMethodName("sc", "XXDM", "XXMC", "XXJC", "XXYWMC", "ZZJGM",
				"DISTRICTID", "XXDZ", "XXYB", "XXDH", "XXCZ", "EDUDEPARTID",
				"XXLB", "XXBB", "BXDJ", "SFYTB", "SFDEFAULT", "XZXM", "XZSJHM",
				"JIEZHEN");
	}
	
	

	private static void colNameToGetMethodName(String vname, String... cols) {
		StringBuffer sb = new StringBuffer();
		for (String s : cols) {
			sb.append(vname + ".get").append(s.substring(0, 1).toUpperCase())
					.append(s.substring(1).toLowerCase()).append("(),");
		}
		sb.setLength(sb.length() - 1);
		System.out.println(sb);
	}*/
	
	/**
	 * 验证入园年月
	 * @param arg 入园时间!
	 * @return
	 */
	public static boolean isRysj(String arg){
		return arg==null?false:arg.matches(REG_BIRTHMONTH);
	}
	
	/**
	 * 是否符合学籍编号格式
	 * @param arg 
	 * @return
	 */
	public static boolean isXjbh(String arg){
		return arg==null?false:arg.matches(REG_XJBH);
	}
	
	/**
	 * 根据年级来验证学籍编号中的年份
	 * @param arg 待验证的学籍编号
	 * @param nj 年级类型代码
	 * @return 合格返回true
	 */
	public static boolean checkRynf(String arg,String nj){
		if(!isXjbh(arg)||nj==null||nj.isEmpty())return false;
		//解析出学籍编号中入园年份部分
		Integer argYear = Integer.valueOf(arg.substring(6, 10));
		//当前时间
		Integer now = Integer.valueOf(new SimpleDateFormat("yyyy").format(System.currentTimeMillis()));
		//托班
		if("00".equals(nj.trim())&&argYear-now==1)return true;
		//小班
		if("01".equals(nj.trim())&&argYear-now==0)return true;
		//中班
		if("02".equals(nj.trim())&&argYear-now==-1)return true;
		//大班
		if("03".equals(nj.trim())&&argYear-now==-2)return true;
		return false;
	}
	
	
	/**
	 * 是否符合邮箱名称格式
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEmail(String arg){
		return arg==null?false:arg.matches(REG_EMAIL);
	}

	/**
	 * 是否符合浏览器主页地址名称格式
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isWebURLName(String arg) {
		return arg==null?false:arg.matches(REG_WEBURL);
	}

	/**
	 * 是符合英文名称格式(由大小写字母和数字、英文中划线组成的名称;例如PudongYoueryuan-02qu)
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEnglishName(String arg) {
		return arg==null?false:arg.matches(REG_ENNAME);
	}

	/**
	 * 是否符合邮编格式
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isSendCode(String arg) {
		return arg == null ? false : arg.matches(REG_SENDCODE);
	}

	/**
	 * 是否符合联系方式格式
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isConcatNumber(String arg) {
		return arg == null ? false : arg.matches(REG_PHONE)
				|| arg.matches(REG_MOBILE);
	}

	/**
	 * 是否符合生日格式
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isBirthday(String arg) {
		return arg == null ? false : arg.matches(REG_BIRTHDAY);
	}
     public static void main(String[] args) {
		//System.out.println(isBirthday("20061002"));
		System.out.println(isCardId("430199199102049182"));
		System.out.println(hasChinese("22222"));
		System.out.println(hasChinese("啊"));
		System.out.println(hasChinese("222啊"));
		System.out.println(hasChinese("DDD"));
	}
	
	/**
	 * 是否符合身份证格式
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isCardId(String arg) {
		return arg == null ? false : arg.matches(REG_CARDID);
	}

	/**
	 * 是否由英文和空格组成
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isEnglishOrSpace(String arg) {
		return arg == null ? false : arg.matches(REG_ENANDSPACE);
	}

	/**
	 * 是否包含中文
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean hasChinese(String arg) {
		return arg == null ? false : Pattern.compile(REG_CHINESE).matcher(arg)
				.find();
	}

	/**
	 * 是否包含空格
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean hasSpace(String arg) {
		return arg == null ? false : Pattern.compile(REG_SPACE).matcher(arg)
				.find();
	}

	/**
	 * 判断传入参数中是否有空值或空字符串
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean hasEmpty(Object... objs) {
		if (objs != null && objs.length > 0) {
			for (Object obj : objs) {
				if (obj == null || obj.toString().trim().isEmpty())
					return true;
			}
			return false;
		}
		return true;
	}

			/**
	 * 验证管理员业务规则
	 * 
	 * @param adm
	 *            管理员对象
	 * @return 成功返回ok,失败返回失败信息
	 */
	public static String validAdmin(AdminInfo adm) {
		 if(adm==null)return "传入参数不能为空";
	        if(hasEmpty(adm.getEname(),adm.getRealname(),adm.getLxdh(),adm.getEmail()))return "必填项不能为空";
	      if(!isEnglishName(adm.getEname()))  return"用户名不符合规则";
	      if(!hasChinese(adm.getRealname()))return "真实姓名只能为中文";
	      if(!isConcatNumber(adm.getLxdh()))return "联系电话不符合规则";
	      if(!isEmail(adm.getEmail()))return "邮箱不符合规则";
	        return "ok";
	}
	
	/**
	 * 在数组中校验字符串是否存在，存在返回true，不存在返回false
	 * @param str
	 * @param arrangeStr
	 * @return
	 */
	public static boolean checkStrIllegal(String str) {
		String[] arr = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",  "X" };
		for (String string : arr) {
			if (str.equals(string)) {
				return true;
			}
		}
		return false;
	}
	
}
