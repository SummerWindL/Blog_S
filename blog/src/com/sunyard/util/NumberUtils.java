package com.sunyard.util;

import java.math.BigDecimal;

public class NumberUtils {
	// 默认除法运算精度,即保留小数点多少位
	private static final int DEFAULT_DIV_SCALE = 10;

	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1 被加数
	 * @param v2  加数
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.add(b2)).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1  被减数
	 * @param v2  减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.subtract(b2)).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1   被乘数
	 * @param v2   乘数
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return (b1.multiply(b2)).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，
	 * 精确到 小数点以后多少位，以后的数字四舍五入。
	 * 
	 * @param v1  被除数
	 * @param v2  除数
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2) {
		return div(v1, v2, DEFAULT_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，
	 * 由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1     被除数
	 * @param v2     除数
	 * @param scale  表示需要精确到小数点以后几位。
	 * @return       两个参数的商
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
	 * 长度格式化, K规则：1M=1024;
	 * @param number
	 * @param scale
	 * @return
	 * 2013-5-1
	 */
	public static double formatKB(Number number, int scale){
		return div(number.doubleValue(), 1024, scale);
	}
	
	/**
	 * 长度格式化, K规则：1M=1024;
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatKB(Number number){
		return div(number.doubleValue(), 1024);
	}
	
	/**
	 * 长度格式化, M规则：1M=1024*1024;
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
	 * 长度格式化, M规则：1M=1024*1024;
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatMB(Number number){
		number = div(number.doubleValue(), 1024);
		return formatKB(number.doubleValue());
	}
	
	/**
	 * 长度格式化, G规则：1M=1024*1024*1024;
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
	 * 长度格式化, G规则：1M=1024*1024*1024;
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatGB(Number number){
		number = div(number.doubleValue(), 1024);
		return formatMB(number.doubleValue());
	}
	
	/**
	 * 百分比格式化
	 * @param number
	 * @return
	 * 2013-5-1
	 */
	public static double formatPercent(Number number){
		return mul(number.doubleValue(), 100);
	}
	
	/**
	 * 百分比格式化
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
