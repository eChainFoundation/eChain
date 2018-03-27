package com.echain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class TitleReplaceUtil {

    public static void main(String [] args) {

        // String s = "/我的资源/春风十里不如你.全集..[百度云离线]①群52312 6296②群532155765.torrent@〖耻〗『羞』+V❤《羞C.skam》⊙〔③〕▲";
        String test = "/视频/电视剧/美剧/Agents of S.H.I.E.L.D. S02/S02E06.mkv";
        // String tmp = "/我的资源/国剧合集/[网]《使-徒-X-者2》2017.国粤双语更新/23 粤语.mp4";
        String ss = titleReplace(test);

        System.out.println("----------结果字符串-------:" + ss);
    }

    /**
     * 1. 去掉所有标点符号
     * 2. 去掉所有空格
     * 3. 全部转为小写
     * 
     * @param oldStr
     * @return
     */
    public static String titleReplace(String oldStr) {
        String newStr = "";
        if(oldStr != null && !"".equals(oldStr)){
            // 去除括号中的值，（包括括号本身）
            // newStr = oldStr.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|\\【.*?】|（.*?）", "");
            // System.out.println("去除括号中的值，（包括括号本身）----: "+newStr);

            // 去掉字符串中所有特殊符号,只保留中英文数字 [^a-zA-Z_\u4e00-\u9fa5]
            newStr = oldStr.replaceAll("[^a-zA-Z0-9\u4e00-\u9fa5]", "");
            newStr = upperToLower(newStr);
        }
        return newStr;
    }

    /***
     * 简介：只保留中文，并且不要小括号()里的内容。
     * 方法：去除括号中的值，（包括括号本身）
     * newStr = oldStr.replaceAll("\\(.*?\\)|\\{.*?}|\\[.*?]|\\【.*?】|（.*?）", "");
     * 
     * @author jemond
     * @date 2017年10月11日 下午3:14:13
     * @param oldStr
     * @return
     */
    public static String titleReplaceChinese(String oldStr) {
        try{
            String newStr = "";
            if(StringUtils.isNotBlank(oldStr)){
                oldStr = oldStr.replaceAll("\\(.*?\\)|\\【.*?】|（.*?）", "");
                String regex = String.format("%s.*%s", "第", "季");
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(oldStr);
                if(matcher.find()){
                    String replace = matcher.group();
                    oldStr = oldStr.replaceAll(replace, "");
                }
                String regexEp = String.format("%s.*%s", "第", "集");
                Pattern patternEp = Pattern.compile(regexEp);
                Matcher matcherEp = patternEp.matcher(oldStr);
                if(matcherEp.find()){
                    String repl = matcherEp.group();
                    oldStr = oldStr.replaceAll(repl, "");
                }
                newStr = oldStr.replaceAll("[^\u4e00-\u9fa5]", "");
                newStr = replaceString(newStr);
                newStr = upperToLower(newStr);
            }
            return newStr;
        }
        catch (Exception e){
            return oldStr;
        }
    }

    public static final String [] REGEX_STRING = {"综艺", "我的资源", "更新", "人人", "影视", "中英", "字幕", "更至", "集", "季", "国语", "公众号", "美国", "科幻",
            "同步", "连载", "容易", "系列", "最新", "国语", "超清", "完结", "推荐", "种子", "动漫", "资源", "韩剧", "删", "共", "待", "中字", "电视剧", "国产", "的" };

    /***
     * 简介：把指定的字符去掉
     * 
     * @author jemond
     * @date 2017年10月12日 上午10:56:59
     * @param name
     */
    public static String replaceString(String name) {
        if(StringUtils.isBlank(name)) return name;
        for (int i = 0; i < REGEX_STRING.length; i++){
            String rep = REGEX_STRING[i];
            name = name.replaceAll(rep, "");
        }
        return name;
    }

    /**
     * 字符串中所有大写字母转换为小写
     * 
     * @param oldStr
     * @return newStr
     */
    public static String upperToLower(String oldStr) {
        String newStr = "";
        if(oldStr != null && !"".equals(oldStr)){
            char c[] = oldStr.toCharArray();

            for (int i = 0; i < oldStr.length(); i++){

                if(c[i] < 97){
                    newStr += (c[i] + "").toLowerCase();// toUpperCase()
                }else{
                    newStr += c[i] + "";
                }
            }
        }
        return newStr;
    }

}
