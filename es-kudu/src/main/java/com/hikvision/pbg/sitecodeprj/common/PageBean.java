package com.hikvision.pbg.sitecodeprj.common;

/**
 * 分页bean
 */

import lombok.Data;

import java.util.List;

@Data
public class PageBean<T> {
	
	// 当前页
	private Integer currentPage = 1;
	// 每页显示的总条数
	private Integer pageSize = 10;
	// 总条数
	private Long totalNum;
	// 是否有下一页
	private Integer isMore;
	// 总页数
	private Integer totalPage;
	// 开始索引
	private Integer startIndex;

	private Integer nextPage;

	private Integer prePage;
	// 分页结果
	private List<T> list;
	
	public PageBean(Integer currentPage, Integer pageSize, Long totalNum) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalNum = totalNum;
	}

	public PageBean(Integer currentPage, Integer pageSize, Long totalNum, Integer totalPage, Integer nextPage, Integer prePage) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalNum = totalNum;
		this.totalPage = totalPage;
		this.nextPage = nextPage;
		this.prePage = prePage;
	}
}