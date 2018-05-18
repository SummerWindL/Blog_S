package com.sunyard.util;


public class ArrayUtil {

	/**
	 * 将数组输出成String，并用指定符号分隔。
	 * @param <T>
	 * @param array 数组
	 * @param separator 分隔符
	 * @return
	 */
	public static <T> String join(T[] array,String separator){
		if(null==array || array.length==0)
			return null;
		StringBuffer buff=new StringBuffer();
		for(T t:array)
			buff.append(t.toString()).append(separator);
		buff.deleteCharAt(buff.length()-1);
		return buff.toString();
	}
}
