package com.hikvision.pbg.sitecodeprj.common;

/**
 * 分页bean
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListBean<T> {
	// 总条数
	private Long totalNum;
	// 分页结果
	private List<T> list;
}