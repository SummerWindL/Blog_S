package com.sunyard.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PageController {
	private Collection model;

	// ����������
	private int totalRowCount = 0; //

	// ��ҳ��
	private int pageCount = 0;

	// ÿҳӦ��ʾ������
	private int maxPageRowCount = 0;

	// ��ǰҳ����
	private int currPageRowCount = 0;

	// ��ǰҳ��
	private int currPageNum;

	// Ĭ�Ϲ���
	public PageController() {
		super();
	}

	// ����ģ��
	public PageController(Collection model) {
		setPageController(model);
	}

	// ��һ����ҳģ��
	public void setPageController(Collection model) {
		this.model = model;
		this.totalRowCount = model.size();
	}

	/**
	 * ��ҳ��
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
		// �Ƿ�����
		if (intPageNum < 1) {
			intPageNum = 1;
		}
		if (intPageNum > pageCount) {
			intPageNum = pageCount;
		}
		// ָ����ǰҳ
		this.currPageNum = intPageNum;
		int i = 0;
		ArrayList arr = new ArrayList();
		// ����ǺϷ��ķ�Χ
		if (intPageNum > 0 && intPageNum <= pageCount) {
			// �����ҳ�Ŀ�ʼ�źͽ�����
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
		} else {// ���һҳ
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
		// ������ҳ��
		if (totalRowCount % maxPageRowCount > 0) { // ������
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

	// ���ؼ�����ָ����Χ������
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
