package com.sunyard.entity;

import java.util.List;

/** 
 *	����һ��jsp���ܵĶ���
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��26�� ����10:20:12 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class ArticleList {

	//����������
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
