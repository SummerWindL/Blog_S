package com.sunyard.entity;

import java.util.List;

/** 
 *	创建一个jsp接受的对象
* @author  作者 yanl.fu: 
* @date    时间：2018年1月26日 上午10:20:12 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class ArticleList {

	//文章总条数
	private long total;
	//rows
	private List<?> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
