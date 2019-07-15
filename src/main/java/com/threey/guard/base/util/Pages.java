package com.threey.guard.base.util;

import java.util.List;

/**
 * 文件名:com.lvhetech.manager.pages.java
 * 作者:chang.tang 日期: 2014-4-23上午09:35:46
 * 版本: 1.0 说明: 分页 
 * 修改记录:
 */
public class Pages<T> {
	public static final int pageSize = 10;
	public static final int maxPageSize = 999999;

	private int currentPage = 1;// 当前页

	private int priviousPage = 0;// 上一页

	private int nextPage;// 下一页

	private int totalCount = 0;// 总条数

	private int totalPage = 0;// 总页数

	private List<T> currList;

	public void paging(List list, int pageNumber, int count) {
		currentPage = pageNumber + 1;
		priviousPage = pageNumber - 1;
		nextPage = pageNumber + 1;
		totalCount = count;
		int i = totalCount / pageSize;
		int j = totalCount % pageSize;
		if (j != 0) {
			i = i + 1;
		}
		totalPage = i;
	}

	/**
	 * 根据总条数生成分页信息
	 * 
	 * @param count
	 */
	public void paging(int count) {

		totalCount = count;
		int i = totalCount / pageSize;
		int j = totalCount % pageSize;
		if (j != 0) {
			i = i + 1;
		}
		totalPage = i;
		if (currentPage < totalPage) {
			nextPage = currentPage + 1;
		} else {
			currentPage = totalPage;
			nextPage = -1;
		}
		priviousPage = currentPage - 1;
	}
	
	public void paging(List<T> list){
    	currList = list;
	}
	
	/**
	 * 获取 limit sql 文
	 * 
	 * @return
	 */
	public String getPagingSql() {
		currentPage = (currentPage < 1 ? 1 : (currentPage));
		StringBuilder sb = new StringBuilder();
		sb.append(" limit");
		sb.append(" " + (currentPage - 1) * pageSize);
		sb.append("," + pageSize);
		return sb.toString();
	}
	
	
	public String getPagingNewSql() {
		currentPage = (currentPage < 1 ? 0 : (currentPage));
		StringBuilder sb = new StringBuilder();
		sb.append(" limit");
		sb.append(" " + currentPage * pageSize);
		sb.append("," + pageSize);
		return sb.toString();
	}
	
	

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPriviousPage() {
		return priviousPage;
	}

	public void setPriviousPage(int priviousPage) {
		this.priviousPage = priviousPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		paging(totalCount);
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getCurrList() {
		return currList;
	}

	public void setCurrList(List<T> currList) {
		this.currList = currList;
	}

	public static int getPagesize() {
		return pageSize;
	}
	
}
