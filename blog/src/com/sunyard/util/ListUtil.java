package com.sunyard.util;

import java.util.List;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��31�� ����1:59:15 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class ListUtil {

	
	/**
	 * List<String> ת String
	 * @param list
	 * @return
	 */
	public static String listToString(List<String> list){
	      if(list==null){
	      return null;
	   }
	   StringBuilder result = new StringBuilder();
	   boolean first = true;
	   //��һ��ǰ�治ƴ��","
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
