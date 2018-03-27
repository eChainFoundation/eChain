package com.echain.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil
{

    /**
     * 判断字符串是否全是数字 只能是正整数
     * @param str
     * @return
     */
    public static boolean isNumeric(String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return false;
        }
        return StringUtils.isNumeric(str);
    }

    /**
     * 判断是否是数字 可以有小数点
     * @param str
     * @return
     */
    public static boolean isDigit(String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return false;
        }
        try
        {
            Double.parseDouble(str.trim());
            return true;
        }
        catch (Throwable e)
        {
            return false;
        }
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str)
    {
        if (str == null || "".equals(str.trim()))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        return !isNotEmpty(str);
    }

    /**
     * 如果字符串为空 就给默认值
     * @param str
     * @param defalutValue
     * @return
     */
    public static String defaultValue(String str, String defalutValue)
    {
        if (isNotEmpty(str))
        {
            return str;
        }
        else
        {
            return defalutValue;
        }
    }

    public static String trimSpace(String str)
    {
        if (str != null)
        {
            str = str.trim();
            return EmojiFilterUtil.filterEmoji(str);
        }
        return "";
    }

    public static Long numbericToLong(String str)
    {
        try
        {
            Double dv = Double.parseDouble(str);
            return dv.longValue();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    /**
     * 消除所有空格
     * @param str
     * @return
     */
    public static String eraseAllBlank(String str)
    {
        if (StringUtils.isEmpty(str))
        {
            return null;
        }

        return str.replaceAll(" ", "");
    }

    /**
     * 如果对象为空 就给默认值
     * @param str
     * @param defalutValue
     * @return
     */
    public static String defaultValue(Object obj, String defalutValue)
    {
        if (obj != null)
        {
            return obj.toString();
        }
        else
        {
            return defalutValue;
        }
    }

    /**
     * 验证是否是手机号
     * @param str
     * @return
     */
    public static boolean isMobile(String str)
    {
        if (str == null || str.trim().equals(""))
        {
            return false;
        }
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3-9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 验证是否是邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email)
    {
        boolean tag = true;
        final String pattern1 = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find())
        {
            tag = false;
        }
        return tag;
    }

    public static String splitString(String str, String begin, String end)
    {
        if (str == null)
        {
            return null;
        }
        int firstInx = str.indexOf(begin);
        if (firstInx != -1)
        {
            int secondeIndx = str.indexOf(end, firstInx + begin.length());
            if (secondeIndx != -1)
            {
                return str.substring(firstInx + begin.length(), secondeIndx);
            }
        }
        return null;
    }

    /**
     * URL 应 UTF-8 方式解码
     * 
     * @param src
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getUTF8URLDecoder(String src)
    {
        try
        {
            return URLDecoder.decode(src, "utf-8");
        }
        catch (Exception e)
        {
            return src;
        }
    }

    public static String encode(String src)
    {
        try
        {
            return URLEncoder.encode(src, "utf-8");
        }
        catch (Exception e)
        {
            return src;
        }
    }

    /**
     * hasSign为true时返回str1/str2的百分比(带%) hasSign为false时返回str1/str2的比值(不带%)
     * @param str1
     * @param str2
     * @return
     */
    public static String getPercentage(String str1, String str2,
            boolean hasSign)
    {
        if (Float.parseFloat(str1) == 0)
        {
            if (hasSign)
            {
                return "0%";
            }
            else
            {
                return "0";
            }
        }
        if (Float.parseFloat(str2) == 0)
        {
            if (hasSign)
            {
                return "100%";
            }
            else
            {
                return "100";
            }
        }

        float num1 = Float.parseFloat(str1);
        float num2 = Float.parseFloat(str2);
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2); // 设置精确到小数点后2位
        String result = numberFormat.format((float) num1 / (float) num2 * 100);
        if (hasSign)
        {
            return result + "%";
        }
        else
        {
            return result;
        }
    }

    public static boolean checkUrl(String url)
    {
        boolean flag = false;
        try
        {
            String check = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(url);
            flag = matcher.matches();
        }
        catch (Exception e)
        {
            flag = false;
        }
        return flag;
    }
    
    public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    
    public static String getMatchString(String content, String pattern) {
    	Pattern p = Pattern.compile(pattern);//"\"md5\":\"(.*?)\",\"file_key");
		Matcher m = p.matcher(content);
		String s =  null;
		if(m.find()){
			s = m.group(1);
		}
		
		return s;
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        // System.out.println(">>"+StringUtil.getUTF8URLDecoder("http%3A//www.17shenghuo.cn/app_lottery/send_lottery.html")+"<<");
    	System.out.println(isContainChinese(""));
    }

}
