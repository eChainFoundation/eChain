package com.echain.plugin;

import com.echain.constant.PageConstant;


/** 
 * @ClassName: Page 
 * @Description: TODO(分页模板) 
 * @author wkq 
 * @date 2016年9月1日 下午4:03:25 
 *  
 */
public class Page {
	
	private int showStyleType;//分页模板  1：云平台   2：工作平台
	private int showCount; //每页显示记录数
	private int totalPage;		//总页数
	private int totalResult;	//总记录数
	private int currentPage;	//当前页
	private int currentResult;	//当前记录起始索引
	private boolean entityOrField;	//true:需要分页的地方，传入的参数就是Page实体；false:需要分页的地方，传入的参数所代表的实体拥有Page属性
	private String pageStr;		//最终页面显示的底部翻页导航，详细见：getPageStr();
	private PageData pd = new PageData();
	

	
	public Page(){
		try {
			this.showCount = PageConstant.DEFAULT_PAGE_COUNT;
		} catch (Exception e) {
			this.showCount = 15;
		}
	}
	
	public int getShowStyleType() {
		if(showStyleType!=2){
			showStyleType =1;
		}
		return showStyleType;
	}

	public void setShowStyleType(int showStyleType) {
		this.showStyleType = showStyleType;
	}
	
	public int getTotalPage() {
		if(totalResult%showCount==0)
			totalPage = totalResult/showCount;
		else
			totalPage = totalResult/showCount+1;
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalResult() {
		return totalResult;
	}
	
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	
	public int getCurrentPage() {
		if(currentPage<=0){
			currentPage = 1;
		}
		//处理子定义分页，没有totalPage数据导致当前页出错问题
		int totalPage2 = getTotalPage();
		if(currentPage>totalPage2 && totalPage2 >0)
			currentPage = totalPage2;
		return currentPage;
	}
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	//拼接分页 页面及JS函数
	public String getPageStr() {
		StringBuffer sb = new StringBuffer();
		if(totalResult>0){
			
			if(showStyleType==0 || showStyleType==1){
				sb.append("	<ul class=\"pagination pull-right no-margin\">\n");
				if(currentPage==1){
					sb.append("	<li><a>共<font color=red>"+totalResult+"</font>条</a></li>\n");
					sb.append("	<li><input type=\"number\" value=\"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left\" placeholder=\"页码\"/></li>\n");
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">跳转</a></li>\n");
					/*sb.append("	<li><a>首页</a></li>\n");
					sb.append("	<li><a>上页</a></li>\n");*/
				}else{
					sb.append("	<li><a>共<font color=red>"+totalResult+"</font>条</a></li>\n");
					sb.append("	<li><input type=\"number\" value=\"\" id=\"toGoPage\" style=\"width:50px;text-align:center;float:left\" placeholder=\"页码\"/></li>\n");
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"toTZ();\"  class=\"btn btn-mini btn-success\">跳转</a></li>\n");
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage(1)\">首页</a></li>\n");
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+(currentPage-1)+")\">上页</a></li>\n");
				}
				int showTag = 5;//分页标签显示数量
				int startTag = 1;
				if(currentPage>showTag){
					startTag = currentPage-1;
				}
				int endTag = startTag+showTag-1;
				for(int i=startTag; i<=totalPage && i<=endTag; i++){
					if(currentPage==i)
						sb.append("<li class=\"active\"><a><font color='white'>"+i+"</font></a></li>\n");
					else
						sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+i+")\">"+i+"</a></li>\n");
				}
				if(currentPage==totalPage){
					/*sb.append("	<li><a>下页</a></li>\n");
					sb.append("	<li><a>尾页</a></li>\n");*/
				}else{
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+(currentPage+1)+")\">下页</a></li>\n");
					sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+totalPage+")\">尾页</a></li>\n");
				}
				sb.append("	<li><a>共"+totalPage+"页</a></li>\n");
				/*sb.append("	<li><select title='显示条数' style=\"width:55px;float:left;margin-top:1px;\" onchange=\"changeCount(this.value)\">\n");
				sb.append("	<option value='"+showCount+"'>"+showCount+"</option>\n");
				sb.append("	<option value='10'>10</option>\n");
				sb.append("	<option value='20'>20</option>\n");
				sb.append("	<option value='30'>30</option>\n");
				sb.append("	<option value='40'>40</option>\n");
				sb.append("	<option value='50'>50</option>\n");
				sb.append("	<option value='60'>60</option>\n");
				sb.append("	<option value='70'>70</option>\n");
				sb.append("	<option value='80'>80</option>\n");
				sb.append("	<option value='90'>90</option>\n");
				sb.append("	<option value='99'>99</option>\n");
				sb.append("	</select>\n");
				sb.append("	</li>\n");*/
				
				sb.append("</ul>\n");
			}else if(showStyleType==2){
				sb.append("<div class=\"paging\">\n");
				sb.append("<span>当前记录：<i>"+totalResult+"</i>条</span>\n");
				
				//分页展示
				sb.append("<span class=\"paging-box\">\n");
				if(currentPage!=1){
					sb.append("<span class=\"paging-list\" onclick=\"nextPage(1)\">首页</span>\n");
					sb.append("<span class=\"paging-list\" onclick=\"nextPage("+(currentPage-1)+")\">上页</span>\n");
				}
				int showTag = 5;//分页标签显示数量
				int startTag = 1;
				if(currentPage>showTag){
					startTag = currentPage-1;
				}
				int endTag = startTag+showTag-1;
				for(int i=startTag; i<=totalPage && i<=endTag; i++){
					if(currentPage==i)
						//sb.append("<li class=\"active\"><a><font color='white'>"+i+"</font></a></li>\n");
						sb.append("<span class=\"paging-list active\">"+i+"</span>\n");
					else
						//sb.append("	<li style=\"cursor:pointer;\"><a onclick=\"nextPage("+i+")\">"+i+"</a></li>\n");
						sb.append("<span class=\"paging-list\" onclick=\"nextPage("+i+")\">"+i+"</span>\n");
				}
				if(currentPage!=totalPage){
					sb.append("<span class=\"paging-list\" onclick=\"nextPage("+(currentPage+1)+")\">下页</span>\n");
					sb.append("<span class=\"paging-list\" onclick=\"nextPage("+totalPage+")\">尾页</span>\n");
				}
				sb.append("</span>\n");
				sb.append("<span>共：<i>"+totalPage+"</i>页</span>\n");
				
				//跳转到指定页
				sb.append("<span class=\"paging-s\">\n");
				sb.append("<input type=\"number\" value=\"\" id=\"toGoPage\" placeholder=\"页码\" class=\"paging-input\">\n");
				sb.append("<div class=\"paging-s-btn bgbtn\"><a onclick=\"toTZ();\">GO</a></div>\n");
				sb.append("</span>\n");
				
				//每页显示多少数据
				sb.append("<span class=\"paging-select\">\n");
				sb.append("<select onchange=\"changeCount(this.value)\">\n");
				switch(showCount){
					case 10:
						sb.append("<option selected='selected' value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 20:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option selected='selected' value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 30:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option selected='selected' value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 50:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option selected='selected' value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 100:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option selected='selected' value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 500:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option selected='selected'  value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 1000:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option selected='selected'  value='1000'>1000条</option>\n");
						sb.append("<option value='5000'>5000条</option>\n");
						break;
					case 5000:
						sb.append("<option value='10'>10条</option>\n");
						sb.append("<option value='20'>20条</option>\n");
						sb.append("<option value='30'>30条</option>\n");
						sb.append("<option value='50'>50条</option>\n");
						sb.append("<option value='100'>100条</option>\n");
						sb.append("<option value='500'>500条</option>\n");
						sb.append("<option value='1000'>1000条</option>\n");
						sb.append("<option selected='selected'  value='5000'>5000条</option>\n");
						break;
				}
				sb.append("	</select>\n");
				sb.append("</span>\n");
			}
			
			
			sb.append("<script type=\"text/javascript\">\n");
			
			//换页函数
			sb.append("function nextPage(page){");
//			sb.append(" top.jzts();");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"showCount":"page.showCount")+"="+showCount+"\";\n");
			sb.append("		document.forms[0].action = url;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + page + \"&" +(entityOrField?"showCount":"page.showCount")+"="+showCount+"\";\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");
			
			//调整每页显示条数
			sb.append("function changeCount(value){");
//			sb.append(" top.jzts();");
			sb.append("	if(true && document.forms[0]){\n");
			sb.append("		var url = document.forms[0].getAttribute(\"action\");\n");
			sb.append("		if(url.indexOf('?')>-1){url += \"&"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + \"1&" +(entityOrField?"showCount":"page.showCount")+"=\"+value;\n");
			sb.append("		document.forms[0].action = url;\n");
			sb.append("		document.forms[0].submit();\n");
			sb.append("	}else{\n");
			sb.append("		var url = document.location+'';\n");
			sb.append("		if(url.indexOf('?')>-1){\n");
			sb.append("			if(url.indexOf('currentPage')>-1){\n");
			sb.append("				var reg = /currentPage=\\d*/g;\n");
			sb.append("				url = url.replace(reg,'currentPage=');\n");
			sb.append("			}else{\n");
			sb.append("				url += \"1&"+(entityOrField?"currentPage":"page.currentPage")+"=\";\n");
			sb.append("			}\n");
			sb.append("		}else{url += \"?"+(entityOrField?"currentPage":"page.currentPage")+"=\";}\n");
			sb.append("		url = url + \"&" +(entityOrField?"showCount":"page.showCount")+"=\"+value;\n");
			sb.append("		document.location = url;\n");
			sb.append("	}\n");
			sb.append("}\n");
			
			//跳转函数 
			sb.append("function toTZ(){");
			sb.append("var toPaggeVlue = document.getElementById(\"toGoPage\").value;");
			sb.append("if(toPaggeVlue == ''){document.getElementById(\"toGoPage\").value=1;return;}");
			sb.append("if(isNaN(Number(toPaggeVlue))){document.getElementById(\"toGoPage\").value=1;return;}");
			sb.append("nextPage(toPaggeVlue);");
			sb.append("}\n");
			sb.append("</script>\n");
			
			
			
			
		}
		pageStr = sb.toString();
		return pageStr;
	}
	
	public void setPageStr(String pageStr) {
		this.pageStr = pageStr;
	}
	
	public int getShowCount() {
		return showCount;
	}
	
	public void setShowCount(int showCount) {
		
		this.showCount = showCount;
	}
	
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getShowCount();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}
	
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	
	public boolean isEntityOrField() {
		return entityOrField;
	}
	
	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
	
	public PageData getPd() {
		return pd;
	}

	public void setPd(PageData pd) {
		this.pd = pd;
	}
	
}
