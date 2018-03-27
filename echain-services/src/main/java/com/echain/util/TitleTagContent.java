package com.echain.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 标题添加标签
 * @author cjx
 * 2017年12月8日
 */
public class TitleTagContent {

	private String beginTag, endTag;

	/**
	 * 构造开始标签，结束标签
	 * @param beginTag 开始标签
	 * @param endTag 结束标签
	 */
	public TitleTagContent(String beginTag, String endTag) {
		this.beginTag = beginTag;
		this.endTag = endTag;
	}
	
	/**
	 * 创建标签
	 * @author cjx
	 * 2017年12月8日 下午4:52:38
	 * @param title
	 * @param matchWordIndex 标签下标字符串 eg.  11;12
	 * @return
	 */
	public String tagContent(String title, String matchWordIndex) {
		Map<Integer, Integer> wordIndexMap = getWordIndexMap(matchWordIndex);
		
		

		return tagContent(title,wordIndexMap);
	}
	
	/**
	 * 创建标签
	 * @author cjx
	 * 2017年12月8日 下午4:53:11
	 * @param title 
	 * @param wordIndexMap  标签下标，key：起始位置,value:结束位置
	 * @return
	 */
	public String tagContent(String title,Map<Integer, Integer> wordIndexMap ) {
	
		
		int beginOffe = 0;
		
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, Integer> item : wordIndexMap.entrySet()) {
			sb.append(title.substring(beginOffe,item.getKey()));
			sb.append(beginTag);
			sb.append(title.substring(item.getKey(),item.getValue()));
			sb.append(endTag);
			beginOffe = item.getValue();
		}
		
		if (beginOffe <= title.length() - 1) {
			sb.append(title.substring(beginOffe, title.length()));
		}

		return sb.toString();
	}
	
	/**
	 * 下标将字符串转换为map默认;分隔
	 * @author cjx
	 * 2017年12月8日 下午4:31:31
	 * @param matchWordIndex
	 * @return
	 */
	public Map<Integer,Integer> getWordIndexMap(String matchWordIndex){
		return getWordIndexMap(matchWordIndex,";");
	}
	
	/**
	 * 下标将字符串转换为map
	 * @author cjx
	 * 2017年12月8日 下午4:30:38
	 * @param matchWordIndex
	 * @return
	 */
	public Map<Integer,Integer> getWordIndexMap(String matchWordIndex,String splitStr){
		Map<Integer,Integer> indexMap = new TreeMap<Integer,Integer>();
		
		if (matchWordIndex == null || matchWordIndex.isEmpty()) {
			return indexMap;
		}
		int beginValue = -1;
		int lastValue = -1;
		String[] splitIndex = matchWordIndex.split(splitStr);
		for (int i = 0; i < splitIndex.length; i++) {
		
			int nowValue = Integer.parseInt(splitIndex[i]);
			//初始值
			if (beginValue==-1) {
				beginValue=nowValue;
				
			}
			if (nowValue - lastValue >1 && i !=0) {
				indexMap.put(beginValue, lastValue+1);
				beginValue = nowValue;
				lastValue = nowValue;
				
			}else{
				lastValue = nowValue;
			}
			
			//最后一个
			if(i ==splitIndex.length-1 ){
				indexMap.put(beginValue, nowValue+1);
			}
		}
		
		return indexMap;
		
	}
	public static void main(String[] args) {
		TitleTagContent tt = new TitleTagContent("<span style=\"color:red\">", "</span>");
		System.out.println(tt.tagContent("交易助手[1.2].zip","0;1;3;12"));
	}
}
