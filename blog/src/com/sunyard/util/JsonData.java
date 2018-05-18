package com.sunyard.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * 返回格式
* @author  作者 yanl.fu: 
* @date    时间：2018年1月31日 下午5:45:20 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class JsonData {

	protected static final Logger log = LoggerFactory.getLogger(JsonData.class);
	
	private boolean success = true;
	private String errorMessage;
	private List<Map<String,Object>> results;
	
	public boolean isSuccess(){
		return this.success;
	}
	
	public void setSuccess(boolean success){
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<Map<String, Object>> getResults() {
		return results;
	}

	public void setResults(List<Map<String, Object>> results) {
		this.results = results;
	}
	
	
}
