package com.sunyard.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PageController {
	private Collection model;

	// 数据总行数
	private int totalRowCount = 0; //

	// 总页数
	private int pageCount = 0;

	// 每页应显示的行数
	private int maxPageRowCount = 0;

	// 当前页行数
	private int currPageRowCount = 0;

	// 当前页号
	private int currPageNum;

	// 默认构造
	public PageController() {
		super();
	}

	// 传入模型
	public PageController(Collection model) {
		setPageController(model);
	}

	// 设一个分页模型
	public void setPageController(Collection model) {
		this.model = model;
		this.totalRowCount = model.size();
	}

	/**
	 * 总页数
	 * 
	 * @return int
	 */
	public int getPageCount() {
		return this.pageCount;
	}

	/**
	 * getPageContents
	 * 
	 * @param intPageNum
	 *            int
	 * @return Object
	 */
	public Object getPageContents(int intPageNum) {
		// 非法数据
		if (intPageNum < 1) {
			intPageNum = 1;
		}
		if (intPageNum > pageCount) {
			intPageNum = pageCount;
		}
		// 指定当前页
		this.currPageNum = intPageNum;
		int i = 0;
		ArrayList arr = new ArrayList();
		// 如果是合法的范围
		if (intPageNum > 0 && intPageNum <= pageCount) {
			// 计算该页的开始号和结束号
			int lfromrow = (intPageNum - 1) * maxPageRowCount;
			arr = (ArrayList) getElementsAt(model, lfromrow, lfromrow
					+ maxPageRowCount - 1);
		}
		currPageNum = intPageNum;
		return arr;
	}

	public Object getLastPage() {
		return this.getPageContents(pageCount);
	}

	public Object getFirstPage() {
		return this.getPageContents(0);
	}

	/**
	 * getCurrentPageRowsCount
	 * 
	 * @return int
	 */
	public int getCurrentPageRowsCount() {
		if (currPageNum < pageCount) {
			return maxPageRowCount;
		} else {// 最后一页
			return totalRowCount - (pageCount - 1) * maxPageRowCount;
		}
	}

	public int getCurrentPageNum() {
		return currPageNum;
	}

	/**
	 * setMaxPageRows
	 * 
	 * @return int
	 */
	public void setMaxPageRows(int rowCount) {
		maxPageRowCount = rowCount;
		// 计算总页数
		if (totalRowCount % maxPageRowCount > 0) { // 有余数
			pageCount = totalRowCount / maxPageRowCount + 1;
		} else {
			pageCount = totalRowCount / maxPageRowCount;
		}
	}

	/**
	 * getMaxPageRows
	 */
	public int getMaxPageRows() {
		return maxPageRowCount;
	}

	// 返回集合中指定范围的数据
	private Object getElementsAt(Collection model, int fromIndex, int toIndex) {
		Iterator iter = model.iterator();
		List arr = new ArrayList();
		if (iter != null) {
			int i = 0;
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (i >= fromIndex && i <= toIndex) {
					arr.add(obj);
				}
				if (i > toIndex) {
					break;
				}
				i = i + 1;
			}
		}
		return arr;
	}

	public List getCurrentPageList(List list, int start, int limit) {
		List pageList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			if ((i + 1) > start && (i + 1) <= (start + limit)) {
				pageList.add(list.get(i));
			}
		}
		return pageList;
	}

}
