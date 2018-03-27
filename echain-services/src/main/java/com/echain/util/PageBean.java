package com.echain.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class PageBean implements Serializable {
	
	public static class PageInfo{
		private Integer pageNo;
		private String displayName;
		
		private String className="";
		
		public String getDisplayName() {
			return displayName;
		}
		public PageInfo setDisplayName(String displayName) {
			this.displayName = displayName;
			return this;
		}
		public Integer getPageNo() {
			return pageNo;
		}
		public PageInfo setPageNo(Integer pageNo) {
			this.pageNo = pageNo;
			return this;
		}
		public String getClassName() {
			return className;
		}
		public PageInfo setClassName(String className) {
			this.className = className;
			return this;
		}
		
		
	}
	
	public void initPageInfos(){
		this.initCountPage();
		 
		
		if( this.currentPage > 1){
			pageInfoList.add(new PageInfo().setDisplayName("首页").setPageNo(1));
		}else{
			pageInfoList.add(new PageInfo().setDisplayName("首页").setPageNo(1).setClassName("disabled"));
		} 
		if( this.currentPage > 1){
			pageInfoList.add(new PageInfo().setDisplayName("上一页").setPageNo( this.currentPage-1));
		}else{
			pageInfoList.add(new PageInfo().setDisplayName("上一页").setPageNo(1).setClassName("disabled"));
		}
		 
	   int prefix = 4;
	   int startNumber = this.currentPage - prefix;
	   if(startNumber < 1){
		   startNumber = 1;
	   }
		
	  for(Integer i = startNumber ; i <this.currentPage ;i++  ){
		  pageInfoList.add(new PageInfo().setDisplayName(i.toString()).setPageNo(i));  
	  } 
	  
	  for(Integer i = this.currentPage ; i <=this.countPage && i <= this.currentPage+4  ;i++  ){
		  pageInfoList.add(new PageInfo().setDisplayName(i.toString()).setPageNo(i));  
	  } 
	  if(this.countPage  >  this.currentPage ){
			pageInfoList.add(new PageInfo().setDisplayName("下一页").setPageNo(currentPage+1));
	  }else{
			pageInfoList.add(new PageInfo().setDisplayName("下一页").setPageNo(countPage).setClassName("disabled"));
	  }
	  if(this.countPage  >  this.currentPage  ){
			pageInfoList.add(new PageInfo().setDisplayName("尾页").setPageNo(countPage));
	 }else{
		 pageInfoList.add(new PageInfo().setDisplayName("尾页").setPageNo(countPage).setClassName("disabled"));
	 }
	  
	  for(PageInfo pageInfo : pageInfoList){
		  if(pageInfo.getPageNo().equals(this.currentPage)){
			  if(StringUtils.isEmpty(pageInfo.getClassName())){
				  pageInfo.setClassName("active");
			  }
			
		  }
	  }
		
	}
	
	public List<PageInfo> getPageInfoList() {
		return pageInfoList;
	}

	public void setPageInfoList(List<PageInfo> pageInfoList) {
		this.pageInfoList = pageInfoList;
	}

	private List<PageInfo> pageInfoList = new ArrayList<PageInfo>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 6244016222009755515L;
	/**
	 * 当前页(要显示页的num) begin 0
	 */
	private Integer currentPage=0;
	/**
	 * 每页显示行数
	 */
	private Integer pageSize;
	
	/**
	 * 总页数
	 */
	private Integer countPage = 0;
	/**
	 * 总共条数
	 */
	private Integer countRows=0;
	/**
	 * 待显示的 数据
	 */
	private List<?> data;	
	
	/**
	 * 设置页数 和每页显示多少条数据
	 * @param pageNumber
	 * @param pageSize
	 */
	public  void initNumber(Integer pageNo,Integer pageSize){
	 
		if(pageNo ==null){
			pageNo =1 ;
		}
		if(pageSize==null){
			pageSize = ServiceConstant.DEFAULT_PAGE_SIZE;
		} 
		this.setCurrentPage(pageNo);
		this.setPageSize(pageSize);
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public Integer getLimitNumber() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getStartNumber(){
		if(this.currentPage > 0){
			return this.pageSize * (this.currentPage-1);	
		}
		return 0;
	}

	public Integer getCountRows() {
		return countRows;
	}
	public void setCountRows(Integer countRows) {
		this.countRows = countRows;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}
	
	public void initCountPage(){
		if(countRows!=null && pageSize!=null){ 
			countPage =   (countRows % pageSize)>0?(countRows/pageSize+1):(countRows/pageSize); 
		}
		if(countPage <1){
			countPage = 1;
		}
	}
	public Integer getCountPage() { 
		return countPage;//页码从1 开始
	}
	
	public int getDataSize(){
		if(this.data == null){
			return 0;
		}else{
			return this.data.size();
		}
	}
	
	
}
