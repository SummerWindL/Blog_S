package com.sunyard.exception;
/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年1月31日 上午10:52:41 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class BlogRuntimeException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1578297788451484173L;
	
	public BlogRuntimeException() {
		super();
	}
	
	public BlogRuntimeException(String message) {
		super(message);
	}
	
	public BlogRuntimeException(String message,Throwable throwable) {
		super(message,throwable);
	}
	
	public BlogRuntimeException(Throwable throwable) {
		super(throwable);
	}

}
