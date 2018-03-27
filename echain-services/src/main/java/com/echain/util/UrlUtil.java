package com.echain.util;

import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;


public class UrlUtil {

	private static final String ACCESSTOKEN = "https://auth.om.qq.com/omoauth2/accesstoken";

	private static final String CLIENT_SECRET_QQ = "4365804166ca73fa359f9dfcaf7ce8fadabc617a";

	private static final String CLIENT_ID_QQ = "0b9a2ab08fa782fb224a0b2cc22b8b9a";

	// public static void main(String[] args) {
	// String urlHost = getUrlHost("vid-qq://f03777l3mey");
	// String urlDomain = getUrlDomain("vid-qq://f03777l3mey");
	// System.out.println(urlDomain);
	// Map<String, String> params = new HashMap<String, String>(4);
	// params.put("grant_type", "clientcredentials");
	// params.put("client_id", CLIENT_ID_QQ);
	// params.put("client_secret", CLIENT_SECRET_QQ);
	// String string = HttpFetch.doPostMethod(ACCESSTOKEN, params);
	// System.out.println(string);
	// System.out.println(getUrlHost("http://pan.baidu.com/share/link?shareid=1024719085&uk=3216790099&fid=922098133603897"));
	// System.out.println(getUkForBdWpLink("http://pan.baidu.com/share/link?shareid=1024719085&uk=3216790099&fid=922098133603897"));
	// System.out.println(getUkForWeiBoLink("pan.baidu.com/share/home?uk=3174848941"));
	// }

	/**
	 * 解析百度网盘链接 获得其中的uk
	 * 
	 * @param url
	 *            如：http://pan.baidu.com/share/link?shareid=1024719085&uk=
	 *            3216790099&fid=922098133603897
	 * @return 3216790099
	 */
	public static final String getUkForBdWpLink(String url) {

		String pattern = "";
		pattern = "uk=([\\d]+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(url);
		if (m.find()) {
			String str = m.group(1);
			return str;
		}

		return "";
	}

	/**
	 * 解析百度网盘链接 获得其中的uk
	 * 
	 * @param url
	 *            如：http://weibo.com/u/5019137757?is_hot=1
	 * @return 5019137757
	 */
	public static final String getUkForWeiBoLink(String url) {

		String pattern = "";
		pattern = "u/([\\d]+)";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(url);
		if (m.find()) {
			String str = m.group(1);
			return str;
		}

		return "";
	}
	
	
	/**
	 * 
	 * @param url:url = http://mp.weixin.qq.com/s/tMlnJv_urCTsHf60UUj1Uw&hgWebShareTitle=因为她，邳州一个月上了四次央视
	 * @return
	 */
	public static final String getTitle(String url) {

		String pattern = "";
		pattern = "hgWebShareTitle=([\\s\\S]*?)&";//hgWebShareTitle=([\\s\\S]*?)&
		Pattern p = Pattern.compile(pattern,Pattern.DOTALL);
		Matcher m = p.matcher(url);
		if (m.find()) {
			String str = m.group(1);
			return str;
		}

		return "";
	}
	
	public static final String getTitle1(String url) {
		
		Matcher m = Pattern.compile("hgWebShareImgUrl=.*?&").matcher(
                url);
		String share_title = "";
        if (m.find() && !TextUtils.isEmpty(m.group(1))) {
            try {
                share_title = URLDecoder.decode(m.group(1), "UTF-8");
            } catch (Exception e) {
                share_title = m.group(1);
            }
        }
		return share_title;
	}
	
	

	/**
	 * 获取链接二级域名
	 * 
	 * @param url
	 * @return
	 */
	public static final String getUrlHost(String url) {
		String host = "";
		try {
			URL url2 = new URL(url);
			host = url2.getHost();
			return host;
		} catch (Exception e) {
			int index = url.indexOf("://");
			if (index != -1) {
				url = url.substring(index + 3);
				if (url.length() > index)
					host = url.substring(0, index);
			} else {
				if(url.indexOf("/") == -1 && url.indexOf("?") == -1)
					host = url;
			}
			if (StringUtils.contains(url, host)) {
				return host;
			}
			return null;
		}
	}

	
	/**
	 * 获取链接顶级域名
	 * 
	 * @param url
	 * @return
	 */
	// public static final String getUrlDomain(String url) {
	// String reg =
	// "(?<=http://|\\.)[^.]*?\\.(com\\.cn|net\\.cn|org\\.cn|com|cn|net|org|biz|info|cc|tv|la|so|lu|tw|co|me|us|in|cm|hk)";
	// Pattern p = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
	// Matcher matcher = p.matcher(url);
	// matcher.find();
	// return matcher.group();
	// }
	public static final String getUrlDomain(String url) {
		String host = getUrlHost(url);
		String domain = host;
		String[] parts = host.split("\\.");
		if (parts.length > 2) {
			domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
			if (domain.equals("com.cn") || domain.equals("net.cn")
					|| domain.equals("org.cn") || domain.equals("gov.cn"))
				domain = parts[parts.length - 3] + "." + domain;
		}

		return domain;
	}

	/**
	 * 根据host得到domain
	 * 
	 * @param host
	 * @return
	 */
	public static final String getHostDomain(String host) {
		String domain = host;
		String[] parts = host.split("\\.");
		if (parts.length > 2) {
			domain = parts[parts.length - 2] + "." + parts[parts.length - 1];
			if (domain.equals("com.cn") || domain.equals("net.cn")
					|| domain.equals("org.cn") || domain.equals("gov.cn"))
				domain = parts[parts.length - 3] + "." + domain;
		}

		return domain;
	}

	protected static int[] getNext(char[] p) {
		// 已知next[j] = k,利用递归的思想求出next[j+1]的值
		// 如果已知next[j] = k,如何求出next[j+1]呢?具体算法如下:
		// 1. 如果p[j] = p[k], 则next[j+1] = next[k] + 1;
		// 2. 如果p[j] != p[k], 则令k=next[k],如果此时p[j]==p[k],则next[j+1]=k+1,
		// 如果不相等,则继续递归前缀索引,令 k=next[k],继续判断,直至k=-1(即k=next[0])或者p[j]=p[k]为止
		int pLen = p.length;
		int[] next = new int[pLen];
		int k = -1;
		int j = 0;
		next[0] = -1; // next数组中next[0]为-1
		while (j < pLen - 1) {
			if (k == -1 || p[j] == p[k]) {
				k++;
				j++;
				// 修改next数组求法
				if (p[j] != p[k]) {
					next[j] = k;// KMPStringMatcher中只有这一行
				} else {
					// 不能出现p[j] = p[next[j]],所以如果出现这种情况则继续递归,如 k = next[k],
					// k = next[[next[k]]
					next[j] = next[k];
				}
			} else {
				k = next[k];
			}
		}
		return next;
	}

	public static int KMP_indexOf(String source, String pattern,
			boolean isWildCard) {
		int i = 0, j = 0;
		char[] src = source.toCharArray();
		char[] ptn = pattern.toCharArray();
		int sLen = src.length;
		int pLen = ptn.length;
		int[] next = getNext(ptn);
		while (i < sLen && j < pLen) {
			// 如果j = -1,或者当前字符匹配成功(src[i] = ptn[j]),都让i++,j++
			if (j == -1 || src[i] == ptn[j]) {
				i++;
				j++;
			} else {
				if (isWildCard && ptn[j] == '?') {
					i++;
					j++;
					continue;
				}
				// 如果j!=-1且当前字符匹配失败,则令i不变,j=next[j],即让pattern模式串右移j-next[j]个单位
				j = next[j];
			}
		}
		if (j == pLen)
			return i - j;
		return -1;
	}

	public static boolean isMatcher_WildCard(String content, String pattern) {
		if (content == null || content.isEmpty() || pattern == null
				|| pattern.isEmpty()) {
			return false;
		}

		int len_str = content.length();
		// 分段
		// 将pattern根据*号拆分
		String[] partArray = pattern.split("\\*");

		int partIndex = 0;
		// 根据切分后的pat序列进行匹配
		int first_index = -1; // 起始的下标
		int last_index = 0; // 最后的下标后面的一个位置
		int next_index = 0; // 下一次开始匹配的下标

		// 根据切分后的pat序列进行匹配
		while (partIndex < partArray.length) {
			String partStr = partArray[partIndex];
			if (partStr.isEmpty()) {
				partIndex++;
				continue;
			}

			next_index = KMP_indexOf(content.substring(next_index), partStr,
					true);
			if (next_index == -1) {

				// 没有找到
				break;
			} else {
				if (first_index == -1) { // 找到的第一个片段
					first_index = next_index;
				}
				last_index += next_index + partStr.length();
				next_index = last_index;
			}
			partIndex++;
		}
		// 匹配
		if (partIndex >= partArray.length) {
			if (pattern.startsWith("*")) {// 开头包含*号
				first_index = 0;
			}
			if (pattern.endsWith("*")) {
				last_index = len_str;
			}
			if (first_index == 0 && last_index == len_str) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		// String matchStr = StringUtil.matchStr("第?季", "第二季");
		// System.out.println(StringUtil.kmp_next("1第二季", "第?季",1));
//		String host = getUrlHost("shop1234.taobao.com");
//		boolean matcher_WildCard = isMatcher_WildCard(host,
//				"shop*.taobao.com");
//		System.out.println(matcher_WildCard);
		
		
		String url = "http://www.xiashu.la/73269/";
		String urlHost = UrlUtil.getUrlHost(url);
		System.out.println(urlHost);
	}

}
