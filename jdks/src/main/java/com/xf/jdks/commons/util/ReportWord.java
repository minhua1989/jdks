/**
 * 
 */
package com.xf.jdks.commons.util;

import com.xf.jdks.commons.global.StaticCaches;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * @author liu
 * 
 */
public class ReportWord {
	static BASE64Encoder base64Encoder = new BASE64Encoder();
	
	public static synchronized String base64Encode(InputStream in) throws IOException {
	    byte[] img = new byte[in.available()];
	    in.read(img);
	    return base64Encode(img);
	}
	    
	public static synchronized String base64Encode(String in) throws IOException {
	    return base64Encode(in.getBytes());
	}
	    
	public static synchronized String base64Encode(byte[] in) throws IOException {
	    return base64Encoder.encode(in);
	}
	
	public String replaceStr(String content, String oldcontent, String newcontent) {
		String aaa = encodeToUnicode("努尔比亚·阿力木");
		String rc = encodeToUnicode(newcontent);
		String target = "";
		oldcontent = "$" + oldcontent + "$";
		target = content.replace(oldcontent, rc);
		return target;
	}

	public String encodeToUnicode(String str) {
		if (str == null)
			return "";
		StringBuilder sb = new StringBuilder(str.length() * 2);
		for (int i = 0; i < str.length(); i++) {
			sb.append(encodeToUnicode(str.charAt(i)));
		}
		return sb.toString();
	}

	public String encodeToUnicode(char character) {
		return "&#" + (character & 0xffff) + ";";
//		if (character > 255) {
//			return "&#" + (character & 0xffff) + ";";
//		} else {
//			return String.valueOf(character);
//		}
	}

	public void exportWordFile(String inputPath, String outPath,
			Map<String, Object> data) {
		String sourcecontent = "";
		InputStream ins = null;
	
		try {
			ins = new FileInputStream(inputPath);
			byte[] b = new byte[1638400];// 提高对文件的读取速度，特别是对于1M以上的文件
			if (!new File(inputPath).isFile()) {
				System.out.println("源模板文件不存在");
				return;
			}
			int bytesRead = 0;
			while (true) {
				bytesRead = ins.read(b, 0, 1638400);
				if (bytesRead == -1) {
					System.out.println("读取模板文件结束");
					break;
				}
				sourcecontent += new String(b, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(ins!=null){
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		String targetcontent = "";
		String oldText = "";
		Object newValue;
		FileWriter fw = null; //字符输出流
		PrintWriter out = null; //
		try {	
			Iterator<String> keys = data.keySet().iterator();
			int keysfirst = 0;
			while (keys.hasNext()) {
				oldText = keys.next();

				newValue = data.get(oldText);
				String newText = (String) newValue;
				if (keysfirst == 0) {
					targetcontent = replaceStr(sourcecontent, oldText, newText);
					keysfirst = 1;
				} else {
					targetcontent = replaceStr(targetcontent, oldText, newText);
					keysfirst = 1;
				}

				//设置word中的头像图片
				if(oldText.equals("ABSPATH") && BaseChecks.hasEmptyStr((String)newValue)){//没有头像时
					targetcontent=targetcontent.replaceAll(srcimg, "/9j/4AAQSkZJRgABAQEAYABgAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wgARCADIAKoDASIAAhEBAxEB/8QAGgABAAMBAQEAAAAAAAAAAAAAAAQFBgMCB//EABQBAQAAAAAAAAAAAAAAAAAAAAD/2gAMAwEAAhADEAAAAd+AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAoepco9eXDnVlwh+SciygAAAAADB8NXHLDM7KGMH9GojrW6COeZffqewAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf//EACAQAAMBAAEEAwEAAAAAAAAAAAIDBAEFABITFBEwcCT/2gAIAQEAAQUC/eGcl2WBabyYwg69/wDoAtIE3+wmWgK5tezCW0zL7eVS469l+De8JVzjaN4Vg2IO2eXjsFc9dLVTZujZ7GHv2WQ+05fG9lfYHkVN4q2BpjvDq0Uy+EKOPFsjUrd1g4P7z//EABQRAQAAAAAAAAAAAAAAAAAAAHD/2gAIAQMBAT8BD//EABQRAQAAAAAAAAAAAAAAAAAAAHD/2gAIAQIBAT8BD//EADEQAAIBAwIDBAgHAAAAAAAAAAECAwAREgQhEyIxQVFhcRQjMHCBkcHRJDJCUnKS4f/aAAgBAQAGPwL38ejjTsf3HNR5dvb3Uy6fTMcHKMXYKBb50MYXk/jbb5muB6LPxMc7cvT50CUKHuNCWLSzsh6Hl+9LOgIVu+iPRJj4gp96s2nkj8WK/Q+2iTgwO0kmSYLZmAG9ya0sMml/ES6jiOSFtb9QG/SkREyc8scS9v8Ala1hIrzLwyyWsr7dPChqokd1IviBvWowj0k66cIQ7wbvl8afRI7HgcplA2ud9vKtU8OqnLQFRdlTEk/ChA+q1GX5lyCWfy2r1bKea1u/2sEmSeqvyumQN6hnvAvDvtFDje4864mIztbK29q1E+d+NjtbpYVZZGj8Vt9alXjS4y2yAxHTp2VgJXKWtjZVt/UCl0sbcKHtUDqKTiLfBg6+dGwtc3Pv5//EACUQAQACAgIBAwQDAAAAAAAAAAERIQAxQVFhcZGhMHCBwbHR8f/aAAgBAQABPyH78RyMYS06k2exGLjJZCvltqTxxzm7HuZ+Bkvy288TNd4yadcnspjTedhmGOcyqrgN0p+sJARggHzeWQKJlD0s+sJJEBpJNNRwjHGkXotWzcJMOnfxnQgarODocugxz1jgC6u4aHxeRgOLWKSF2X+pxXUQAr26ROKq0gRSdlyucYW5YQJBNyb6wwCEBjNiOPJT/ORo4BolFSl8X7fV9iFfBZJqM+BT3LZaz/Y6ET1ntuFrb5wsmmYU+lHI+8EpPpV/GCjagQS5JA/3kHWgLYEd8Nb8sjnlRwkHTj8bSOX78//aAAwDAQACAAMAAAAQ88888888888888888888888888888888888888888888888888888888888888888888888s48888888888888888888888888888888888888888888888888888888888888888888888//8QAFBEBAAAAAAAAAAAAAAAAAAAAcP/aAAgBAwEBPxAP/8QAFBEBAAAAAAAAAAAAAAAAAAAAcP/aAAgBAgEBPxAP/8QAJBABAQACAgIABgMAAAAAAAAAAREhMQBBUWEwcHGBkbGh4fD/2gAIAQEAAT8Q+fEFLyhXRIlNDFmMhzmmXJ5UEnyI873noyS929XTZi/5z9vokt9cplyu5qZcxvC78455tAnhYFMiZOYxUeDVoKbXfHnfChHSLHeQfIcEI2GBphXc3Uw51fi5bCE9l2FAHIEg5Y0E91oSJE6Cysa2J4IfQJFP4As+iSIqShSvKEtYiJJfMRAFDAWzsLbZnyWKVpRFgGJxYf1d0KSyAGGUzOCCGzRCCjAwpON4Rym6xL5ADTHDVeZjOBDAqxMui/FxSf28wQe545+V47O9djHb55+L3bPayzLLz93vh3OVuie+BFpTIRyjmbq4M7qQGlpxACEnhe7yggujKrlByPZ3EbQvWyKs12rsQiff8VLoSyuGnrjQ4nmIC/eH7+fP/9k=");
				}
				if(oldText.equals("ABSPATH") && !BaseChecks.hasEmptyStr((String)newValue)){
//				if(oldText.equals("XB1")){
					String imgValue = (String) data.get(oldText);
//					boolean imgFlag = ImgeUtils.checkImge("c:"+imgValue);
					boolean imgFlag = ImgeUtils.checkImge(imgValue);
					if(imgFlag){
//					imgValue = "D:/444.jpg";
						if(imgValue!=null&&!imgValue.equals("")){
							InputStream is = null;
							try {
								File file = new File(imgValue);
								is = new FileInputStream(file);
								String tmp=base64Encode(is);
								//srcimg是在mht模板中的图片base64替换内容
								//每次重新生成模板时，需要将其进行替换
//						targetcontent=targetcontent.replaceAll(srcimg, " <img width='180' height='230' src='data:image/jpg;base64,"+tmp+"' />");
								targetcontent=targetcontent.replaceAll(srcimg, tmp);
							}catch (Exception e){
								e.printStackTrace();
								System.out.println("头像路径不存在,无法导出,..........");
							}finally {
								if(is!=null){
									is.close();
								}
							}
						}
					}
				}
				
				//设置word中的头像图片
				if(oldText.equals("photoabcdefg")){
					try {
						InputStream is=Format.StringTOInputStream((String)data.get(oldText));
						
						String tmp=base64Encode(is);
						//System.out.println("tmp--"+tmp);
						//srcimg是在mht模板中的图片base64替换内容
						//每次重新生成模板时，需要将其进行替换
						targetcontent=targetcontent.replaceAll(srcimg, tmp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
			fw = new FileWriter(outPath, true);
			out = new PrintWriter(fw);
			if (null==targetcontent || "".equals(targetcontent)) {
				out.println(sourcecontent);
			} else {
				out.println(targetcontent);
			}

			System.out.println(outPath + " 生成文件成功");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fw!=null){
				try {
					fw.flush();
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	public String getSeal(Map<String,Object> map,String fileAbsPath){
		String realPath = StaticCaches.getRealRootPath();
		String rtPathUrl="";
//		ReportWord rw = new ReportWord();

		String inUrl = realPath+File.separator+"word_res"+File.separator+"seal.mht";

		File fileOut = new File(fileAbsPath);
		File fileIn = new File(inUrl);

		if (fileOut.exists()) {
			boolean flag = fileOut.delete();
			System.out.println(inUrl + "文件已存在，已删除！");
		}
		if (!fileIn.exists()) {
			System.out.println(inUrl + "模板文件未找到！");
		}
		this.exportWordFile(inUrl, fileAbsPath, map);

		return fileAbsPath;

	}
	
	public String generateWord(Map<String,Object> map,String fileAbsPath,int fs){
		String realPath = StaticCaches.getRealRootPath();
		String rtPathUrl="";
//		ReportWord rw = new ReportWord();

		String inUrl = realPath+File.separator+"word_res"+File.separator+"userconfirm1.mht";

		if(fs > 1){
			inUrl = realPath+File.separator+"word_res"+File.separator+"userconfirm2.mht";
		}

		
//		String outUrl = absPath+ File.separator + fileName+".doc";
		File fileOut = new File(fileAbsPath);
		File fileIn = new File(inUrl);
		
		if (fileOut.exists()) {
			boolean flag = fileOut.delete();
			System.out.println(inUrl + "文件已存在，已删除！");
		}
		if (!fileIn.exists()) {
			System.out.println(inUrl + "模板文件未找到！");
		}
		this.exportWordFile(inUrl, fileAbsPath, map);
		
//		rtPathUrl=absPath+ "/" +fileName+ ".doc";
		
		return fileAbsPath;
		
	}
	
//	public void DownFile(String url){
//		HttpServletResponse response = ServletActionContext.getResponse();
//
//		OutputStream toClient = null;
//		try {
//			File file = new File(url);
//			String filename = file.getName();
//			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
//			InputStream fis = null;
//			fis = new BufferedInputStream(new FileInputStream(url));
//			byte[] buffer = new byte[fis.available()];
//			fis.read(buffer);
//			fis.close();
//			response.reset();
//			response.addHeader("Content-Disposition", "attachment;filename="+ new String(filename.getBytes()));
//			response.addHeader("Content-Length", "" + file.length()); // 设置返回的文件类型
//			toClient = new BufferedOutputStream(response.getOutputStream());// 得到向客户端输出二进制数据的对象
//			if (ext.equals("DOC")){
//				response.setContentType("application/msword");
//			}else{
//				response.setContentType("application/octet-stream");// 设置返回的文件类型
//			}
//			if(toClient != null){
//				toClient.write(buffer); // 输出数据
//				toClient.flush();
//			}
//			toClient.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
//		ReportWord rw = new ReportWord();
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("1", "张三");
//		map.put("2", "李四");
//		map.put("3", "王五");
//		map.put("4", "赵六");
//		map.put("5", "彩笔");
//		map.put("6", "张三");
//		map.put("7", "李四");
//		map.put("8", "王五");
//		map.put("9", "赵六");
//		map.put("10", "彩笔");
//		map.put("11", "张三");
//		map.put("12", "李四");
//		map.put("13", "王五");
//		map.put("14", "赵六");
//		map.put("15", "彩笔");
//		String inUrl = "D:" + File.separator + "itest.mht";
//		String outUrl = "C:" + File.separator + "itest1111111.doc";
//		File fileOut = new File(outUrl);
//		File fileIn = new File(inUrl);
//		if (fileOut.exists()) {
//			fileOut.delete();
//			System.out.println(inUrl + "文件已存在，已删除！");
//		}
//		if (!fileIn.exists()) {
//			return;
//		}
//		rw.exportWordFile(inUrl, outUrl, map);
	}
	
	
//	static final String srcimg="replacepicreplacepicreplacepic";
//	static final String srcimg="&#26242;&#19981;&#26174;&#31034;";
	static final String srcimg="\\$base64StringImg\\$";
}

