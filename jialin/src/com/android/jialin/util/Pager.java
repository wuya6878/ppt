package com.android.jialin.util;

import java.util.List;

public class Pager {
	
	private String nameTitle;
	
	// sort by
	public enum OrderType{
		asc, desc
	}
	
	public static final Integer MAX_PAGE_SIZE = 3;// limit maximum of records in each page

	private Integer pageNumber = 1;// current page number
	private Integer pageSize = MAX_PAGE_SIZE;// each page record number
	private Long totalCount = 0l;// total record
	private Integer pageCount = 0;// total page
	private String keyword;// find keywords
	private List list ;
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 1) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
		getPageCount();
	}

	public Integer getPageCount() {
		pageCount = totalCount.intValue() / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}
	
	public boolean hasNextPage(){
		if(getPageCount()<=1){
			return false;
		}
		if(getPageNumber()>= getPageCount()){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean hasPrePage(){
		if(getPageNumber()<=1){
			return false;
		}
		return true;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getNameTitle() {
		return nameTitle;
	}

	public void setNameTitle(String nameTitle) {
		this.nameTitle = nameTitle;
	}


	
}