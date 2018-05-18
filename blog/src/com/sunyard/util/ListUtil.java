package com.sunyard.util;

import java.util.List;

/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年1月31日 下午1:59:15 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class ListUtil {

	
	/**
	 * List<String> 转 String
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list){
	      if(list==null){
	      return null;
	   }
	   StringBuilder result = new StringBuilder();
	   boolean first = true;
	   //第一个前面不拼接","
	   for(String string :list) {
	      if(first) {
	         first=false;
	      }else{
	         result.append(",");
	      }
	      result.append(string);
	   }
	   return result.toString();
	}
}
