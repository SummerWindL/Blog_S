package com.sunyard.util;

import java.math.BigDecimal;

public class NumberUtils {
	// Ĭ�ϳ������㾫��,������С�������λ
	private static final int DEFAULT_DIV_SCALE = 10;

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2  ����
	 * @return ���������ĺ�
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.add(b2)).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ļ������㡣
	 * 
	 * @param v1  ������
	 * @param v2  ����
	 * @return ���������Ĳ�
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.subtract(b2)).doubleValue();
	}

	/**
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1   ������
	 * @param v2   ����
	 * @return ���������Ļ�
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.multiply(b2)).doubleValue();
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㣬�����������������ʱ��
	 * ��ȷ�� С�����Ժ����λ���Ժ�������������롣
	 * 
	 * @param v1  ������
	 * @param v2  ����
	 * @return ������������
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * �ṩ����ԣ���ȷ�ĳ������㡣�����������������ʱ��
	 * ��scale����ָ �����ȣ��Ժ�������������롣
	 * 
	 * @param v1     ������
	 * @param v2     ����
	 * @param scale  ��ʾ��Ҫ��ȷ��С�����Ժ�λ��
	 * @return       ������������
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP)).doubleValue();
	}
	
	/**
	 * ���ȸ�ʽ��, K����1M=1024;
	 * @param number
	 * @param scale
	 * @return
	 * 2013-5-1
	 */
	public static double formatKB(Number number, int scale){
		return div(number.doubleValue(), 1024, scale);
	}
	
	/**
	 * ���ȸ�ʽ��, K����1M=1024;
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatKB(Number number){
		return div(number.doubleValue(), 1024);
	}
	
	/**
	 * ���ȸ�ʽ��, M����1M=1024*1024;
	 * @param number
	 * @param scale
	 * @return
	 * 2013-5-1
	 */
	public static double formatMB(Number number, int scale){
		number = div(number.doubleValue(), 1024, scale);
		return formatKB(number.doubleValue(), scale);
	}
	
	/**
	 * ���ȸ�ʽ��, M����1M=1024*1024;
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatMB(Number number){
		number = div(number.doubleValue(), 1024);
		return formatKB(number.doubleValue());
	}
	
	/**
	 * ���ȸ�ʽ��, G����1M=1024*1024*1024;
	 * @param number
	 * @param scale
	 * @return
	 * 2013-5-1
	 */
	public static double formatGB(Number number, int scale){
		number = div(number.doubleValue(), 1024, scale);
		return formatMB(number.doubleValue(), scale);
	}
	
	/**
	 * ���ȸ�ʽ��, G����1M=1024*1024*1024;
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatGB(Number number){
		number = div(number.doubleValue(), 1024);
		return formatMB(number.doubleValue());
	}
	
	/**
	 * �ٷֱȸ�ʽ��
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatPercent(Number number){
		return mul(number.doubleValue(), 100);
	}
	
	/**
	 * �ٷֱȸ�ʽ��
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatPercent(Number number, int scale){
		return StringUtil.round(mul(number.doubleValue(), 100), scale);
	}
	
	public static void main(String[] args) {
		System.out.println(formatPercent(0.23456723, 5));
	}
}
