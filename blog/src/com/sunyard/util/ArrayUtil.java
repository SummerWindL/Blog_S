package com.sunyard.util;


public class ArrayUtil {

	/**
	 * �����������String������ָ�����ŷָ���
	 * @param <T>
	 * @param array ����
	 * @param separator �ָ���
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
