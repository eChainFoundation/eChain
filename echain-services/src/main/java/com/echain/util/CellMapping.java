package com.echain.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellMapping {

	public static Map<String, String> worksMap = new HashMap<>();
	public static List<String> AmobileList = new ArrayList<>();
	public static List<String> ImobileList = new ArrayList<>();
	
	
	public static String excel_url = null;
	
	public static List<String> getAmobileList(){
		
		AmobileList.add("鲨鱼影视");
		AmobileList.add("超级看影视");
		AmobileList.add("极速影院");
		AmobileList.add("聚看影视");
		AmobileList.add("火影");
		AmobileList.add("影视大全");
		AmobileList.add("蛋蛋影视");
		AmobileList.add("旗鱼影视");
		AmobileList.add("冬瓜影视");
		AmobileList.add("新电影天堂");
		AmobileList.add("今日影视");
		
		return AmobileList;
	}
	
	public static List<String> getImobileList(){
		ImobileList.add("聚看飞速");
		ImobileList.add("MX播放器");
		ImobileList.add("天天看吧");
		ImobileList.add("天天视频");
		
		return ImobileList;
	}
	
	public static Map<String, String> getWorksMap(){
		
		worksMap.put("11", "视听作品（电影）");
		worksMap.put("18", "视听作品（电影）");
		worksMap.put("12", "视听作品（电视剧）");
		worksMap.put("13", "视听作品（电视剧）");
		worksMap.put("14", "视听作品（动漫）");
		worksMap.put("15", "视听作品（综艺）");
		worksMap.put("16", "体育");
		worksMap.put("19", "其他");
		worksMap.put("21", "音乐作品");
		worksMap.put("22", "音乐作品");
		worksMap.put("29", "音乐作品");
		worksMap.put("31", "文字作品");
		worksMap.put("32", "文字作品");
		worksMap.put("33", "文字作品");
		worksMap.put("34", "文字作品");
		worksMap.put("39", "文字作品");
		worksMap.put("41", "图片类");
		worksMap.put("42", "商标LOGO");
		worksMap.put("43", "摄影作品");
		worksMap.put("44", "漫画");
		worksMap.put("49", "其他");
		worksMap.put("51", "舆情");
		worksMap.put("59", "其他");
		
		return worksMap;
	}
	
	public  Map<String, Integer> getInitMap(){
		Map<String, Integer> map =  new HashMap<>();
		map.put("站点id", -1);
		map.put("站点名称", -1);
		map.put("作品名&id", -1);
		map.put("监测时间", -1);
		map.put("终端类型", -1);
		map.put("终端类型明细", -1);
		map.put("抓取方式", -1);
		map.put("首次发函时间", -1);
		map.put("最后发函时间", -1);
		map.put("发函次数", -1);
		map.put("下线日期", -1);
		map.put("下线时间", -1);
		map.put("状态", -1);
		map.put("是否整片", -1);
		map.put("播放源链接", -1);
		map.put("发布人", -1);
		map.put("百度网盘的pathmd5", -1);
		map.put("版本", -1);

		return map;
	}
	public  Map<String, Integer> getInitWangpanMap(){
		Map<String, Integer> wangpanMap =  new HashMap<>();
		wangpanMap.put("链接地址", -1);
		wangpanMap.put("标题", -1);
		return wangpanMap;
	}
	
	//小说数据
	public Map<List<String>, Integer> getInitNovelMap(){
		
		Map<List<String>, Integer> novelMap = new HashMap<>();
		
		List<String> list_I = new ArrayList<>();
		list_I.add("侵权文章章节名");
		list_I.add("标题");
		List<String> list_II = new ArrayList<>();
		list_II.add("书名");
		list_II.add("作品名");
		List<String> list_III = new ArrayList<>();
		list_III.add("监测日期");
		list_III.add("监测时间");
		List<String> list_IV = new ArrayList<>();
		list_IV.add("百度网盘的pathMd5");
		List<String> list_V = new ArrayList<>();
		list_V.add("侵权文章链接");
		list_V.add("链接地址");
		List<String> list_VI = new ArrayList<>();
		list_VI.add("站点id");
		List<String> list_VII = new ArrayList<>();
		list_VII.add("作品id");
		List<String> list_VIII = new ArrayList<>();
		list_VIII.add("小说id");
		List<String> list_IX = new ArrayList<>();
		list_IX.add("相似度");
		List<String> list_X = new ArrayList<>();
		list_X.add("来源地址");
		List<String> list_XI = new ArrayList<>();
		list_XI.add("发布者");
		list_XI.add("发布人");
		List<String> list_XII = new ArrayList<>();
		list_XII.add("章节名");
		List<String> list_XIII = new ArrayList<>();
		list_XIII.add("作品作者");
		list_XIII.add("作者");
		
		
		
		novelMap.put(list_I, -1);
		novelMap.put(list_II, -1);
		novelMap.put(list_III, -1);
		novelMap.put(list_IV, -1);
		novelMap.put(list_V, -1);
		novelMap.put(list_VI, -1);
		novelMap.put(list_VII, -1);
		novelMap.put(list_VIII, -1);
		novelMap.put(list_IX, -1);
		novelMap.put(list_X, -1);
		novelMap.put(list_XI, -1);
		novelMap.put(list_XII, -1);
		novelMap.put(list_XIII, -1);
		
		
//		novelMap.put("小说id", -1);
//		novelMap.put("书名", -1);
//		novelMap.put("作者", -1);
//		novelMap.put("发布人", -1);
//		novelMap.put("章节名", -1);
//		novelMap.put("侵权文章章节名", -1);
//		novelMap.put("来源地址", -1);
//		novelMap.put("侵权文章链接", -1);
//		novelMap.put("相似度", -1);
//		novelMap.put("监测时间", -1);
//		novelMap.put("终端类型", -1);
//		novelMap.put("终端类型明细", -1);
//		novelMap.put("站点id", -1);
//		novelMap.put("作品id", -1);
		return novelMap;
	}
	public  Map<String, Integer> getInitSentimentMap(){
		Map<String, Integer> sentimentMap =  new HashMap<>();
		sentimentMap.put("样本链接", -1);
		sentimentMap.put("侵权文章链接", -1);
		sentimentMap.put("样本标题", -1);
		return sentimentMap;
	}
	
	//新闻数据
	public  Map<String, Integer> getInitNewsMap(){
		Map<String, Integer> newsMap = new HashMap<>();
		newsMap.put("监测时间", -1);
		newsMap.put("站点id", -1);
		newsMap.put("侵权文章链接", -1);
		newsMap.put("样本链接", -1);
		newsMap.put("样本标题", -1);
		newsMap.put("终端类型", -1);
		newsMap.put("终端类型明细", -1);
		newsMap.put("作品id", -1);
		newsMap.put("跳转url", -1);
		newsMap.put("版权声明", -1);
		newsMap.put("相似度", -1);
		newsMap.put("来源", -1);
		newsMap.put("标题", -1);
		newsMap.put("样本栏目分类", -1);
		newsMap.put("侵权栏目分类", -1);
		newsMap.put("下线日期", -1);
		return newsMap;
	}
	
	
	
	public  Map<String, Integer> getCellMap(){
		Map<String, Integer> cellMap = new HashMap<>();
		//小说
		cellMap.put("小说id", -1);
		cellMap.put("书名", -1);
		cellMap.put("作者", -1);
		cellMap.put("侵权文章章节名", -1);
		cellMap.put("是否收费", -1);
		cellMap.put("真实链接地址", -1);
		cellMap.put("站点名", -1);
		cellMap.put("终端一级类型", -1);
		cellMap.put("标题相似度", -1);
		cellMap.put("内容相似度", -1);
		//舆情
		cellMap.put("重定向后的链接", -1);
		//cellMap.put("标题", -1);
		cellMap.put("真实内容链接", -1);
		cellMap.put("来源", -1);
		//新闻
		cellMap.put("栏目分类", -1);
		cellMap.put("作者id", -1);
		//搜索引擎
		cellMap.put("文本文件存储路径", -1);
		cellMap.put("referer地址", -1);
		cellMap.put("极性值", -1);
		//ott
		
		//应用市场
		cellMap.put("供应商", -1);
		cellMap.put("类别", -1);
		cellMap.put("下载次数", -1);
		cellMap.put("文件大小", -1);
		cellMap.put("logo地址", -1);
		//电商
		cellMap.put("信用等级", -1);
		cellMap.put("价格", -1);
		cellMap.put("区域", -1);
		cellMap.put("销量", -1);
		cellMap.put("卖家名称", -1);
		cellMap.put("店铺地址", -1);
		cellMap.put("店铺名", -1);
		//音乐
		cellMap.put("时长", -1);
		cellMap.put("发布人", -1);
		cellMap.put("专辑", -1);
		cellMap.put("点击数", -1);
		cellMap.put("缩列图", -1);
		//视频
		//动漫
		cellMap.put("引导过来的链接", -1);
		//小网站
		cellMap.put("播放源", -1);
		cellMap.put("播放源md5", -1);
		cellMap.put("深度", -1);
		//网盘
		cellMap.put("磁力链接", -1);
		cellMap.put("展示链接", -1);
		//cellMap.put("百度网盘的pathmd5", -1);
		cellMap.put("处理的次数", -1);
		cellMap.put("提取码", -1);
		//社交
		cellMap.put("栏目标签", -1);
		cellMap.put("正文内容链接", -1);
		cellMap.put("摘要", -1);
		cellMap.put("重定向url", -1);
		cellMap.put("极性状态", -1);
		
		
		cellMap.put("作品名", -1);
		cellMap.put("作品名称", -1);
		cellMap.put("站点名", -1);
		cellMap.put("站点名称", -1);
		cellMap.put("授权情况",-1);
		return cellMap;
	}
	
	public  Map<String, String> getCategoryMap(){
		Map<String, String> categoryMap = new HashMap<>();
		//小说
		categoryMap.put("小说id", "book_id");
		categoryMap.put("书名", "book_name");
		categoryMap.put("作者", "author");
		categoryMap.put("侵权文章章节名", "sub_title");
		categoryMap.put("是否收费", "is_charged");
		categoryMap.put("真实链接地址", "real_content_url");
		categoryMap.put("站点名", "site_name");
		categoryMap.put("终端一级类型", "terminal_first_category");
		categoryMap.put("标题相似度", "title_similarity");
		categoryMap.put("内容相似度", "content_similarity");
		//舆情
		categoryMap.put("重定向后的链接", "redirect_url");
		//categoryMap.put("标题", "sub_title");
		categoryMap.put("真实内容链接", "real_content_url");
		categoryMap.put("来源", "source");
		//新闻
		categoryMap.put("栏目分类", "category");
		categoryMap.put("作者id", "author_id");
		//搜索引擎
		categoryMap.put("文本文件存储路径", "store_path");
		categoryMap.put("referer地址", "referer_url");
		categoryMap.put("极性值", "polarity_value");
		//ott
		
		//应用市场
		categoryMap.put("供应商", "author");
		categoryMap.put("类别", "category");
		categoryMap.put("下载次数", "download_times");
		categoryMap.put("文件大小", "file_size");
		categoryMap.put("logo地址", "logo_url");
		//电商
		categoryMap.put("信用等级", "credit_level");
		categoryMap.put("价格", "price");
		categoryMap.put("区域", "region");
		categoryMap.put("销量", "sales");
		categoryMap.put("卖家名称", "seller");
		categoryMap.put("店铺地址", "shop_location");
		categoryMap.put("店铺名", "shop_name");
		//音乐
		categoryMap.put("时长", "length");
		categoryMap.put("发布人", "source");
		categoryMap.put("专辑", "album");
		categoryMap.put("点击数", "click_num");
		categoryMap.put("缩列图", "image_url");
		//视频
		//动漫
		categoryMap.put("引导过来的链接", "referer_url");
		//小网站
		categoryMap.put("播放源", "play_url");
		categoryMap.put("播放源md5", "play_url_md5");
		categoryMap.put("深度", "depth");
		//网盘
		categoryMap.put("磁力链接", "magnet_uri");
		categoryMap.put("展示链接", "real_content_url");
		categoryMap.put("百度网盘的pathmd5", "path_md5");
		categoryMap.put("处理的次数", "process_times");
		categoryMap.put("提取码", "extracted_code");
		//社交
		categoryMap.put("栏目标签", "category");
		categoryMap.put("正文内容链接", "content_link");
		categoryMap.put("摘要", "snippet");
		categoryMap.put("重定向url", "redirect_url");
		categoryMap.put("极性状态", "polarity_status");
		
		
		categoryMap.put("作品名", "works_name");
		categoryMap.put("作品名称", "works_name");
		categoryMap.put("站点名", "site_name");
		categoryMap.put("站点名称", "site_name");
		categoryMap.put("授权情况","is_authorize");
		return categoryMap;
	}
	
	//汪哥excel模板
	public  Map<List<String>,Integer> getInitSecondMap(){
		
		Map<List<String>, Integer> secondMap = new HashMap<>();
		
		List<String> list_I = new ArrayList<>();
		list_I.add("title");
		list_I.add("视频标题");
		list_I.add("外站标题");
		list_I.add("标题");
		List<String> list_II = new ArrayList<>();
		list_II.add("game");
		list_II.add("program");
		list_II.add("作品");
		list_II.add("节目");
		list_II.add("作品名");
		List<String> list_III = new ArrayList<>();
		list_III.add("first found");
		list_III.add("监测日期");
		list_III.add("监测时间");
		List<String> list_IV = new ArrayList<>();
		list_IV.add("takedown status");
		list_IV.add("下线状态");
		List<String> list_V = new ArrayList<>();
		list_V.add("webpage url");
		list_V.add("url");
		list_V.add("链接地址");
		List<String> list_VI = new ArrayList<>();
		list_VI.add("appid");
		list_VI.add("站点-端口");
		List<String> list_VII = new ArrayList<>();
		list_VII.add("作品id");
		list_VII.add("extf id");
		List<String> list_VIII = new ArrayList<>();
		list_VIII.add("总长");
		List<String> list_IX = new ArrayList<>();
		list_IX.add("匹配率");
		List<String> list_X = new ArrayList<>();
		list_X.add("首次发函");
		List<String> list_XI = new ArrayList<>();
		list_XI.add("nickname");
		list_XI.add("昵称");
		list_XI.add("发布者");
		List<String> list_XII = new ArrayList<>();
		list_XII.add("目标url");
		List<String> list_XIII = new ArrayList<>();
		list_XII.add("版本");
		
		secondMap.put(list_I, -1);
		secondMap.put(list_II, -1);
		secondMap.put(list_III, -1);
		secondMap.put(list_IV, -1);
		secondMap.put(list_V, -1);
		secondMap.put(list_VI, -1);
		secondMap.put(list_VII, -1);
		secondMap.put(list_VIII, -1);
		secondMap.put(list_IX, -1);
		secondMap.put(list_X, -1);
		secondMap.put(list_XI, -1);
		secondMap.put(list_XII, -1);
		secondMap.put(list_XIII, -1);
		
		return secondMap;
	}
}
