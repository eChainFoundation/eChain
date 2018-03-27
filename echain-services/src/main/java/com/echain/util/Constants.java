package com.echain.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: Constants
 * @Description: TODO(常量)
 * @author wkq
 * @date 2016年9月1日 下午4:03:25
 */
public class Constants {

    public static final String CHARACTER_UTF8 = "UTF-8";

    public static final String URL_STATUS = "下线日期";

    public static final String NO_CONTENT = "没有数据";

    public static final String NO_ISHOUST = "未选择分Sheet类型";

    public static final String INVALID_SQL = "插入的导入sql不符合规范";

    public static final String NO_RESULT_ES = "根据关键词在EalsticSearch中没有搜到结果";

    public static final String NO_RESULT_KEY_FILTER = "根据关键词过滤完后没有数据";

    public static final String NO_SEARCH_KEYWORD = "没有搜索关键字词";

    public static final String INVALID_ES_TIME = "ES导出只能根据监测时间选择";

    public static final String NO_CHECK = "未检查";

    public static final String INVALID_EXPORT_NUM = "导出数量超过90万！";

    public static final int MAX_EXPORT_NUM = 900000;

    public static final String COLUMN_CRAWLER_KEYWORDS = "keywords";

    public static final String COLUMN_WORKSNAME = "works_name";

    public static final String COLUMN_SEARCH_KEYWORDS = "search_keywords";// 搜索关键字

    public static final String COLUMN_SEARCH_FILTER_KEYWORDS = "filter_keywords"; // 过滤

    public static final String COLUMN_SEARCH_INCLUDE_KEYWORDS = "include_keywords"; // 包含

    public static final String COLUMN_SEARCH_CRAWL_DENIED_KEYWORDS = "crawl_denied_keywords"; // 包含

    public static final String COLUMN_CONTAINS_CHINESE_SEARCH_KEYWORDS = "searchKeywordsContainsChinese"; // 搜索关键字中包含的中文

    public static final String COLUMN_CONTAINS_CHINESE_CRAWLER_KEYWORDS = "keywordsContainsChinese";// 爬虫关键字中包含的中文

    public static final String COLUMN_ADADMIN_URL = "doadminAndUrl";// doadmin和url过滤关键词

    public static final String COLUMN_WANGPAN_MD5 = "wangpan_md5"; // 网站MD5

    public static final String CHECK_EXCEL_OFFLINE_SUB = "查下线excel下载路径"; // 查下线 excel的 主题

    public static final String OFFLINE_CHECK_STATUS_3 = "3";// 生成excel失败

    public static final String OFFLINE_CHECK_STATUS_2 = "2";// EXCEL回写完成

    public static final String OFFLINE_CHECK_URL_STATUS_1 = "1";// fbm_offline_check_url 1 已下线

    public static final String OFFLINE_CHECK_URL_STATUS_0 = "0";// fbm_offline_check_url 0未检查

    // fbm_rights_news_url 状态
    public static final String RIGHT_NEWS_URL_STATUS_0 = "0";// 维权状态：0-未检查，1-爬虫校验已下线，2-爬虫未下线，3-爬虫不确定，4-人工未下线，5-人工已下线

    public static final String RIGHT_NEWS_URL_STATUS_1 = "1";//

    public static final String RIGHT_NEWS_URL_STATUS_2 = "2";//

    public static final String RIGHT_NEWS_URL_STATUS_3 = "3";//

    public static final String RIGHT_NEWS_URL_STATUS_4 = "4";//

    public static final String RIGHT_NEWS_URL_STATUS_5 = "5";//

    public static final String RIGHT_NEWS_URL_ENABLE_0 = "0";// 状态,0:不需要维权;1:需要维权;

    public static final String RIGHT_NEWS_URL_ENABLE_1 = "1";// 状态,0:不需要维权;1:需要维权;

    public static final String ERROR_INVALID_PARAM = "非法参数";

    public static final String ERROR_INVALID_SECONDCATEGORY = "分类不存在";

    public static final String ERROR_INVALID_WORKTYPE = "作品类型不存在";

    public static final String ERROR_INVALID_EMPTY_WORKS = "监测作品不能为空";

    public static final String ERROR_INVALID_EMPTY_SITE = "站点不能为空";

    public static final String ERROR_INVALID_EMAIL = "抄送邮箱格式错误";

    public static final String ERROR_INVALID_EMPTY_JOBNAME = "任务名不能为空";

    public static final String ERROR_INVALID_EMPTY_JOBDES = "任务描述不能为空";

    public static final String ERROR_INVALID_EMPTY_JOBPRI = "任务优先级不能为空";

    public static final String ERROR_INVALID_EMPTY_JOBINTERVALRAW = "间隔时间不能为空";

    public static final String ERROR_INVALID_JOBINTERVALRAW_VALUE = "间隔时间1到30";

    public static final String ERROR_INVALID_EMPTY_JOBINTERVALUNIT = "间隔时间单位不能为空";

    public static final String ERROR_INVALID_JOBINTERVALUNIT_VALUE = "间隔时间单位值非法(小时，天)";

    public static final String ERROR_DURATION_IS_NULL = "最少时长不能为空";

    public static final String ERROR_DURATION_IS_NEGATIVE = "最少时长不能为负数";

    public static final String ERROR_INVALID_EMPTY_JOBSAMPLERAW = "样本有效期不能为空";

    public static final String ERROR_INVALID_JOBSAMPLERAW_VALUE = "样本有效期只 需 0到30";

    public static final String ERROR_INVALID_EMPTY_JOBSAMPLEUNIT = "样本时间间隔单位不能为空";

    public static final String ERROR_INVALID_JOBSAMPLEUNIT_VALUE = "样本时间间隔单位值非法(天，周，月)";

    public static final String ERROR_INVALID_EMPTY_SAMPLE_URL = "url不能为空";

    public static final String ERROR_CATEGORY_IS_HAVE = "你已有此分类的导出配置,不可再添加相同分类配置";

    public static final String ERROR_TEMPLATEDATA_IS_HAVE = "你还未勾选配置";

    public static final String ERROR_TEMPLATEDATA_TYPE_IS_REPEAT = "请看清注释！只能勾选同一个分类下的";

    public static final String SUCCESS = "success";

    public static final String FAIL = "fail";

    public static final String REPEAT = "新增失败,此样本链接已存在";

    public static final String OPERATOR_ADD = "add";

    public static final String OPERATOR_EDIT = "edit";

    public static final String WORKS_TYPE_1 = "1"; // 1 镜头

    public static final String WORKS_TYPE_2 = "2"; // 2:动态作品(抓站点)

    public static final String WORKS_TYPE_3 = "3"; // 3:动态作品(直接抓URL)

    // fbm_mail_send
    public static final int MAIL_SEND_STATUS_0 = 0; // 待发送

    public static final int MAIL_SEND_STATUS_1 = 1; // 发送中

    public static final int MAIL_SEND_STATUS_2 = 2; // 发送成功

    public static final int MAIL_SEND_STATUS_3 = 3; // 发送失败

    // 对应 work.js中的数据
    public static List<Integer> secondCategorySet = new ArrayList<Integer>();

    // workType 作品类型
    public static Map<Integer, Set<Integer>> categoryMap = new HashMap<Integer, Set<Integer>>();

    // 作品分类
    public static Map<String, String> workCategoryMap = new HashMap<>();

    static{
        workCategoryMap.put("11", "电影");
        workCategoryMap.put("18", "院线");
        workCategoryMap.put("12", "电视剧");
        workCategoryMap.put("13", "自制剧");
        workCategoryMap.put("14", "动漫");
        workCategoryMap.put("15", "综艺");
        workCategoryMap.put("16", "体育");
        workCategoryMap.put("19", "视频类其他");
        workCategoryMap.put("21", "音乐");
        workCategoryMap.put("22", "有声阅读");
        workCategoryMap.put("29", "音频类其他");
        workCategoryMap.put("31", "新闻");
        workCategoryMap.put("32", "独家报道");
        workCategoryMap.put("33", "文学");
        workCategoryMap.put("34", "自媒体");
        workCategoryMap.put("39", "文字类其他");
        workCategoryMap.put("41", "图片类");
        workCategoryMap.put("42", "商标LOGO");
        workCategoryMap.put("43", "摄影图片");
        workCategoryMap.put("44", "漫画");
        workCategoryMap.put("49", "图片类其他");
        workCategoryMap.put("51", "舆情");
        workCategoryMap.put("59", "舆情类其他");
    }

    static{
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        Set<Integer> set3 = new HashSet<Integer>();
        Set<Integer> set4 = new HashSet<Integer>();
        Set<Integer> set5 = new HashSet<Integer>();

        set1.add(11);
        set1.add(18);
        set1.add(12);
        set1.add(13);
        set1.add(14);
        set1.add(15);
        set1.add(16);
        set1.add(19);
        categoryMap.put(1, set1);

        set2.add(21);
        set2.add(22);
        set2.add(29);
        categoryMap.put(2, set2);

        set3.add(31);
        set3.add(32);
        set3.add(33);
        set3.add(34);
        set3.add(39);
        categoryMap.put(3, set3);

        set4.add(41);
        set4.add(42);
        set4.add(43);
        set4.add(44);
        set4.add(49);
        categoryMap.put(4, set4);

        set5.add(51);
        set5.add(59);
        categoryMap.put(5, set5);
    }
}
