package com.echain.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;



/**
 * 字符串工具类
 * 
 * @author guoyun created on 2016年5月3日
 */
public class MyStringUtils extends StringUtils {
	private static String baseUrl = "";

	public static void setBaseUrl(String baseUrl) {
		MyStringUtils.baseUrl = baseUrl;
	}

	   // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
 
    // 完整的判断中文汉字和符号
    public static boolean isChinese(String strName) {
        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }
	
	/**
	 * 字符串转int
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int str2Int(String str, int defaultValue) {
		int result = defaultValue;

		try {
			if (StringUtils.isNotEmpty(str)) {
				result = Integer.parseInt(str);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 获取异常堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionStackInOneLine(Throwable e) {

		String msg = getExceptionStack(e);
//		msg = msg.replaceAll(MyFileUtils.LINE_SEPARATOR, "  ");
		return msg;
	}

	/**
	 * 获取异常堆栈信息
	 * 
	 * @param e
	 * @return
	 */
	public static String getExceptionStack(Throwable e) {
		/*
		 * StackTraceElement[] stackTraceElements = e.getStackTrace(); String
		 * result = e.toString() + BaseConstants.DEFAULT_LINE_SPLIT; for (int
		 * index = stackTraceElements.length - 1; index >= 0; --index) { result
		 * += "at [" + stackTraceElements[index].getClassName() + ","; result +=
		 * stackTraceElements[index].getFileName() + ","; result +=
		 * stackTraceElements[index].getMethodName() + ","; result +=
		 * stackTraceElements[index].getLineNumber() +
		 * "]"+BaseConstants.DEFAULT_LINE_SPLIT; } return result;
		 */
		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			sw.flush();
		} finally {
			if (sw != null) {
				try {
					sw.close();
				} catch (IOException e1) {
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
		return sw.toString();
	}

	/**
	 * 字符串转long
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static Long str2Long(String str, long defaultValue) {
		Long result = defaultValue;

		try {
			if (StringUtils.isNotEmpty(str)) {
				result = Long.parseLong(str);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * format html出来的文本内容
	 * 
	 * @param str
	 * @return
	 */
	public static String formatHtmlString(String str) {
		if (str == null || str.trim().equals("")) {
			return str;
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			int ascii = (int) ch;
			if (ascii == 12288) {// 全角中文空格
				builder.append(" ");
				continue;
			}
			builder.append(ch);
		}
		return builder.toString();
	}

	public static String removeNonBmp(String origStr) {
		if (null == origStr || "".equals(origStr))
			return origStr;
		StringBuilder buf = new StringBuilder();
		for (int i = 0, len = origStr.length(); i < len; ++i) {
			char ch = origStr.charAt(i);
			if (!(ch >= 0xd800 && ch <= 0xdfff))
				buf.append(ch);
		}
		return buf.toString();
	}

	public static String subStringLength(String source, int length) {
		if (null == source || "".equals(source) || source.length() < 2000)
			return source;
		source = source.substring(0, 2000);
		return source;
	}

	/**
	 * 替换传进来的字符串的所有标点符号
	 * 
	 * @param source
	 * @return
	 */
	public static String repalcePunctuation(String source) {
		String result = source;
		if (StringUtils.isNotEmpty(source)) {
			result = source.replaceAll("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]", "");
		}

		return result;
	}

	public static List<String> getStringBetween(String content, String first, String second) {

		List<String> rsList = new ArrayList<String>();
		if (StringUtils.isBlank(content)) {
			return rsList;
		}

		int fromIndex = 0;
		while (fromIndex > -1 && fromIndex < content.length()) {
			int firstIdx = content.indexOf(first, fromIndex);
			if (firstIdx > -1) {
				int secondIdx = content.indexOf(second, firstIdx + first.length());
				if (secondIdx > -1) {
					String str = content.substring(firstIdx + first.length(), secondIdx);
					rsList.add(str);
					fromIndex = secondIdx + second.length();
				} else {
					break;
				}
			} else {
				break;
			}

		}

		return rsList;
	}

	public static String getFirstBetween(String content, String first, String second) {
		List<String> rsList = getStringBetween(content, first, second);
		if (rsList.size() > 0) {
			return rsList.remove(0);
		}
		return null;
	}

	public static boolean isEnglish(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.BASIC_LATIN) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * getStrByRegex 方法
	 * <p>
	 * 方法说明:
	 * </p>
	 * 按照指定的正则表达式，查找组1的内容。
	 * 
	 * @param str
	 *            要匹配的字符串，不能为null（实际上经常是……）
	 * @param regex
	 *            正则表达式，不能为Null
	 * @return
	 * @return String
	 * @author gxy
	 * @date 2013-4-16
	 */
	public static String getStrByRegex(String str, String regex) {
		if (str == null)
			return null;
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(str);
		if (m.find())
			return m.group(1);
		return null;
	}

//	/**
//	 * 
//	 * matchingTitleAndUrl1 方法
//	 * <p>
//	 * 方法说明:
//	 * </p>
//	 * 用于查找一个标题和一个url的方法，需要一个正则表达式，正则结果被分为两组，
//	 * 
//	 * @param str
//	 *            要匹配的字符串，不能为null（实际上经常是……）
//	 * @param regex正则表达式，不能为Null
//	 * @param titleIsFir
//	 *            标题是否为第一个组的内容？
//	 * @return ArrayList<Video> 返回一个Video数组；输入为null则返回null
//	 * @author gxy
//	 * @date 2013-4-16
//	 */
//	public static ArrayList<Video> matchingTitleAndUrl1(String str, String regex, boolean titleIsFir) {
//		if (str == null)
//			return null;
//		ArrayList<Video> list = new ArrayList<Video>();
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(str);
//		while (m.find()) {
//			Video rt = new Video();
//			if (titleIsFir) {
//				rt.setUrl(baseUrl + m.group(2));
//				rt.setTitle(m.group(1));
//			} else {
//				rt.setUrl(baseUrl + m.group(1));
//				rt.setTitle(m.group(2));
//			}
//			list.add(rt);
//		}
//		return list;
//	}

	/**
	 * isHaveNext 方法
	 * <p>
	 * 方法说明:
	 * </p>
	 * 从htmlcode中找出是否存在下一页，
	 * 
	 * @param htmlcode
	 * @param regex
	 *            查找需要的正则表达式，如果为Null,则按默认的正则：下一页[^<]*?</a> 来查找
	 * @return boolean 如果有则有true
	 * @author gxy
	 * @date 2013-4-16
	 */
	public static boolean isHaveNext(String htmlcode, String regex) {
		if (null == htmlcode)
			return false;
		if (null == regex)
			regex = "下一页[^<]*?</a>";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(htmlcode);
		return m.find();
	}

	/**
	 * 判断字符是否是中文
	 *
	 * @param c
	 *            字符
	 * @return 是否是中文
	 */
	public static boolean isMessyChar(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS || ub == Character.UnicodeBlock.VERTICAL_FORMS
				|| ub == Character.UnicodeBlock.ARROWS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.BASIC_LATIN
				|| ub == Character.UnicodeBlock.LATIN_1_SUPPLEMENT || ub == Character.UnicodeBlock.GEOMETRIC_SHAPES
				|| ub == Character.UnicodeBlock.BOX_DRAWING || ub == Character.UnicodeBlock.ENCLOSED_ALPHANUMERICS
				|| ub == Character.UnicodeBlock.NUMBER_FORMS) {
			return true;
		}

		return false;
	}

	/**
	 * 判断字符串是否是乱码
	 *
	 * @param strName
	 *            字符串
	 * @return 是否是乱码
	 */
	public static boolean isMessyCode(String strName) {
		if (isBlank(strName) || strName.length() > 3*1024*1024) {//太大的就忽略
			return false;
		}

		strName = replace(strName, " ", "");
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		// char[] ch = strName.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		int specialCharNumber = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isEnglish(c)) {
				continue;
			} else if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.SPECIALS) {
				specialCharNumber++;
				if (specialCharNumber > 5) {
					return true;
				}

			} else {
				chLength += 1;
			}

			if (!(Character.isLetterOrDigit(c))) {
				if (!isMessyChar(c)) {
					// System.out.println(c+ ""+Character.UnicodeBlock.of(c));
					count = count + 1;
				}
			}

		}
		float result = 0.0f;
		if (chLength > 0) {
			result = count / chLength;
		}

		if (result > 0.2) {
			return true;
		} else {
			return false;
		}

	}

	public static int parseDuration(String str) {
		int hh, mm, ss;
		if (str.endsWith("\""))
			str = str.substring(0, str.length() - 1);
		str = str.replaceAll("：|: ?", ":");
		if (str.equals("0"))
			return 0; // occurs in some baiduVideo search results
		String fields[] = str.split("[:']");
		if (fields.length == 2) {
			hh = 0;
			mm = Integer.parseInt(fields[0]);
			ss = Integer.parseInt(fields[1]);
		} else if (fields.length == 3) {
			hh = Integer.parseInt(fields[0]);
			mm = Integer.parseInt(fields[1]);
			ss = Integer.parseInt(fields[2]);
		} else
			throw new RuntimeException("Invalid duration: " + str);
		if (ss < 0 || ss >= 60 || (fields.length == 3 && (mm < 0 || mm >= 60)))
			throw new RuntimeException("More invalid duration: " + str);
		return hh * 3600 + mm * 60 + ss;
	}

	public static char ascii2Char(int ASCII) {
		return (char) ASCII;
	}

	public static String ascii2String(String ASCIIs) {
		String[] ASCIIss = ASCIIs.split(",");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ASCIIss.length; i++) {
			sb.append((char) ascii2Char(Integer.parseInt(ASCIIss[i])));
		}
		return sb.toString();
	}

	public static String getBokePlayUrl(String vid) {
		return "/boke/play/" + vid + ".html";
	}

	public static String getPlayUrl(String id, String videoid) {
		String relationPath = "";
		if (id == null)
			return "";

		if (id.length() == 15) {
			relationPath = "/cover/" + id.charAt(0) + "/" + id + ".html";
			if (videoid != null) {
				relationPath += "?vid=" + videoid;
			}
		} else {
			relationPath = getVideoPlayUrl(id);
		}

		return relationPath;
	}

	public static final String getVideoPlayUrl(String vid) {
		if (vid.length() < 11)
			return "";
		// System.out.println("vid="+vid);
		// vid=84ImIcZ1PHW
		// System.out.println("s9="+vid.substring(10,11));
		// return "";
		String path = "/page/" + vid.substring(0, 1) + "/" + vid.substring(9, 10) + "/" + vid.substring(10, 11) + "/"
				+ vid + ".html";

		return path;
	}

	public static String randomIntStr(Integer length) {

		String rs = "";
		for (int i = 0; i < length; i++) {
			rs += new Random().nextInt(10);
		}
		return rs;

	}

	public static String getFullUrlPath(String path) {
		String h = "v.qq.com";
		String fullURL = "http://" + h + path;

		return fullURL;
	}

	public static String getURL(String cid, String isforeshow, String istxvideo) {
		if (cid == null)
			return "";
		String url = "";

		if (istxvideo.equals("0")) {
			url = getBokePlayUrl(cid);
		} else {
			if (isforeshow.equals("1")) {
				if (cid.length() == 15) {
					url = getPlayUrl(cid, null);
				} else {
					url = getPlayUrl(cid, null);
				}
			} else {
				if (cid.length() == 15) {
					url = getPlayUrl(cid, null);
				} else {
					url = getPlayUrl(cid, null);
				}
			}
		} // end-if
		url = getFullUrlPath(url);

		return url;
	}

	public static void main(String[] args) {
		// System.out.println(MyStringUtils.isMessyCode("*��J12TP.jar�ļ����JTP�ļ���ȡ��ͼƬ��Դ"));
		System.out.println(MyStringUtils.isMessyCode("鈥忔爧浠�,鈥忔爧浠擫OFTER,鍠滄,杞诲崥瀹�,LOFTER,涔愪箮"));
		System.out.println(Character.UnicodeBlock.of('�'));

	}

	public static String getFileContent(File file, String encoding) {
		StringBuilder sb = new StringBuilder();
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String line = null;
		try {
			is = new FileInputStream(file);
			isr = new InputStreamReader(is, encoding);
			br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (isr != null)
					isr.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public static String filterEmoji(String source) {

		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = null;

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}

				buf.append(codePoint);
			} else {
			}
		}

		if (buf == null) {
			return source;// 如果没有找到 emoji表情，则返回源字符串
		} else {
			if (buf.length() == len) {// 这里的意义在于尽可能少的toString，因为会重新生成字符串
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}

	}

	public static boolean containsEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);

			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}

	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

}
