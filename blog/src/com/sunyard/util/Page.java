package com.sunyard.util;
/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��25�� ����7:50:34 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class Page {

	private int everyPage;          //ÿҳ��ʾ��¼��
    private int totalCount;         //�ܼ�¼��
    private int totalPage;          //��ҳ��
    private int currentPage;        //��ǰҳ
    private int beginIndex;         //��ѯ��ʼ��
    private boolean hasPrePage;     //�Ƿ�����һҳ
    private boolean hasNextPage;    //�Ƿ�����һҳ
    
    public Page(){}
    
    public Page(int everyPage, int totalCount, int totalPage, 
            int currentPage,int beginIndex, boolean hasPrePage,
            boolean hasNextPage) {  //�Զ��幹�췽��
        this.everyPage = everyPage;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.beginIndex = beginIndex;
        this.hasPrePage = hasPrePage;
        this.hasNextPage = hasNextPage;
    }
	public int getEveryPage() {
		return everyPage;
	}
	public void setEveryPage(int everyPage) {
		this.everyPage = everyPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public boolean isHasPrePage() {
		return hasPrePage;
	}
	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
    
}
