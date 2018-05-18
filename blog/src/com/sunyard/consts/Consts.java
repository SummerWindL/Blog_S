package com.sunyard.consts;
/** 
 * 系统基本常量 常量管理类
 *  		     系统常量定义
* @author  作者 yanl.fu: 
* @date    时间：2018年1月30日 上午11:12:04 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class Consts {
	
	/**
	 * 每页显示条数
	 */
	public static Integer pageArticle = 4;
	
	/**
	 * 数据库类型 
	 */
	public static String  DBTYPE="0";//0:mysql 1:oracle 3:IMFORMIX
	
	public static final String yyyyMMdd="yyyyMMdd";//日期格式化类型
	public static final String showDate="yyyy年MM月dd日";//日期格式化类型
	
	/**
	 * 表示错误信息
	 */
	public static final String loginMessage_1="";
	public static final String loginMessage_2="";
	public static final String loginMessage_3="成功登出！";
	public static final String loginMessage_4="登出失败！";
	public static final String loginMessage_5="登出异常！";
	
	public static final Integer loginFlag=0;//未登录
	public static final Integer loginflag=1;//已登录
}
