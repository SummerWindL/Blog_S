package com.sunyard.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sunyard.exception.BlogRuntimeException;

//import com.sunyard.tonglu.AppException;


public class BeanUtil {	
	/**
	 * 对象克隆函数，假如被复制对象某属性的值不为null将被复制，一般用于页面表单的全部提交
	 * @param fromObj  被复制对象
	 * @param fromClazz  被复制对象类型，假如有超类，需要得到超类的类型
	 * @param toObj		复制对象
	 * @param toClazz	被复制对象类型，假如有超类，需要得到超类的类型
	 */
	public static void copyFields(Object fromObj, Class fromClazz, Object toObj, Class toClazz){
		Field[] fields = (fromClazz != null) ? fromClazz.getDeclaredFields() 
									: fromObj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			//fromObj中get方法得到的值
			Object value = invokeGetMethod(fields[i], fromObj);
			if (value != null)
			//将值传给toObj对象
    			invokeSetMethod(fields[i], toObj, value);
		}
	}
	
	/**
	 * 对象克隆函数，假如被复制对象某属性的值不为null且不为""将被复制，一般用于查询时对象拷贝
	 * @param fromObj  被复制对象
	 * @param fromClazz  被复制对象类型，假如有超类，需要得到超类的类型
	 * @param toObj		复制对象
	 * @param toClazz	被复制对象类型，假如有超类，需要得到超类的类型
	 */
	public static void copyFieldsValues(Object fromObj, Class fromClazz, Object toObj, Class toClazz)  {
		Field[] fields = (fromClazz != null) ? fromClazz.getDeclaredFields() 
									: fromObj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Object value = invokeGetMethod(fields[i], fromObj);
			if (value != null && !value.equals("") && !value.equals("0.00"))
    			invokeSetMethod(fields[i], toObj, value);
		}
	}
	
	/**
	 * 对象组装sql语句的函数，当对象的属性不为null或者不为""时，将按照对象属性的类型组织sql语句
	 * @param fromObj
	 * @param fromClazz
	 * @return
	 */
	public static String copyFieldsToSql(Object fromObj, Class fromClazz)  {
		String Sql = "";
		Field[] fields = (fromClazz != null) ? fromClazz.getDeclaredFields() 
									: fromObj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Object value = invokeGetMethod(fields[i], fromObj);
			if (value != null && !value.equals("") && !value.equals("0.00"))
			{
				if("java.lang.Integer".equals(fields[i].getType().getName()))
				{
					Sql += " and " + fields[i].getName() + " = " + value;
				}
				else if(((String)value).charAt(0)>128)
				{
					Sql += " and " + fields[i].getName() + " like '%" + value + "%'"; 
				}
				else 
				{
					Sql += " and " + fields[i].getName() + " = '" + value + "'"; 
				}
					
			}	
		}
		return Sql;
	}
	
	/**
	 * 对象比较函数
	 * @param fromObj   被比较对象
	 * @param fromClazz  被比较对象类型，假如有超类，需要得到超类的类型
	 * @param toObj		比较对象
	 * @param toClazz	比较对象类型，假如有超类，需要得到超类的类型
	 * @return
	 */
	public static HashMap compareFields(Object fromObj, Class fromClazz, Object toObj, Class toClazz) {
		Field[] fields = (fromClazz != null) ? fromClazz.getDeclaredFields() 
									: fromObj.getClass().getDeclaredFields();
		HashMap map = new HashMap();
		for (int i = 0; i < fields.length; i++) {
			Object value1 = invokeGetMethod(fields[i], fromObj);
			if (value1 != null) {
    			Object value2 = invokeGetMethod(fields[i].getName(), toObj);
    			if (value2 != null) {
        			if (value2.equals(value1) == false) {
        				map.put(fields[i].getName(), "比对不一致");
        			}
    			}
			}
		}
		return map;
	}
	
	/**
	 * 对象属性打印
	 * @param obj
	 * @param clazz
	 */
	public static void printFields(Object obj, Class clazz){
		Field[] fields = (clazz != null) ? clazz.getDeclaredFields() 
									: obj.getClass().getDeclaredFields();
//		log.debug("======打印"+obj.getClass().getName()+"信息======");
		String logerStr = "";
		for (int i = 0; i < fields.length; i++) {
			Object value = invokeGetMethod(fields[i], obj);
			if (value != null){
    			//loger.debug(fields[i].getName().trim()+":"+value.toString());
				logerStr += fields[i].getName().trim()+":"+value.toString();
			}
		}
		
//		loger.debug(logerStr);
//		loger.debug("======打印完成======");
	}
	
	/**
	 * 对象属性全部trim操作
	 * @param obj
	 * @param clazz
	 * @return
	 * @throws AppException
	 */
	public static Object trimFields(Object obj, Class clazz) throws Exception {
		Field[] fields = (clazz != null) ? clazz.getDeclaredFields() 
									: obj.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Object value = invokeGetMethod(fields[i], obj);
			if (value != null && fields[i].getType().getName().equals("java.lang.String"))
			{
				Object val = value.toString().trim();
    			invokeSetMethod(fields[i], obj, val);
			}
		}
		return obj;
	}
	
	private static Object invokeGetMethod(String fieldName, Object fromObj){
		Object[] in=new Object[1];
		try {
    		StringBuffer buf = new StringBuffer();
    		buf.append("get");
    		buf.append(fieldName.substring(0,1).toUpperCase());
    		buf.append(fieldName.substring(1));
    		String methodName = buf.toString();
    		Method method = fromObj.getClass().getMethod(methodName, null);
    		in[0] = method.invoke(fromObj, null); 
    	} catch (SecurityException e1) {
//    		loger.error("其它错");
    	} catch (NoSuchMethodException e1) {
//    		loger.error("没有"+fieldName+"属性对应的Get方法");
    	} catch (Exception e1) {
//    		loger.error("调用"+fieldName+"属性对应的Get方法失败");
    	}
		return in[0];
	}
	
	private static Object invokeGetMethod(Field field, Object fromObj) {
		Object[] in=new Object[1];
		String fieldName = field.getName();
		try {
    		StringBuffer buf = new StringBuffer();
    		buf.append("get");
    		buf.append(fieldName.substring(0,1).toUpperCase());
    		buf.append(fieldName.substring(1));
    		String methodName = buf.toString();
    		//返回由methodName指定的方法method
    		Method method = fromObj.getClass().getMethod(methodName, null);
    		//执行fromObj的method方法
    		in[0] = method.invoke(fromObj, null); 
    	} catch (SecurityException e1) {
//    		loger.error("其它错");
    	} catch (NoSuchMethodException e1) {
//    		loger.error("没有"+fieldName+"属性对应的Get方法");
    	} catch (Exception e1) {
//    		loger.error("调用"+fieldName+"属性对应的Get方法失败");
    	}
    	//返回get方法的结果
		return in[0];
	}
	
	public static void invokeSetMethod(Field field, Object toObj, Object value) {
		try {
    		String fieldName = field.getName();
    		StringBuffer buf = new StringBuffer();
    		buf.append("set");
    		buf.append(fieldName.substring(0,1).toUpperCase());
    		buf.append(fieldName.substring(1));
    		String methodName = buf.toString();
    		Class[] pm=new Class[1];
    		pm[0] = field.getType();
    		Object[] in = new Object[1];
    		in[0] = value; 
        	Method method = toObj.getClass().getMethod(methodName, pm);
    		method.invoke(toObj, in); 
    	} catch (SecurityException e1) {
//    		loger.error("其它错");
    	} catch (NoSuchMethodException e1) {
//    		loger.error("没有"+field.getName()+"属性对应的Set方法");
    	} catch (Exception e1) {
//    		loger.error("调用"+field.getName()+"属性对应的Set方法失败");
    	}
	}
	
	public static List<String[]> changeObjectArrayToStringArray(List<Object[]> list) {
		if(list == null) {
			return null;
		}	
		
		List<String[]>retList = new ArrayList<String[]>();
		for(Object[] objectArr:list) {
			String[] stringArr = new String[objectArr.length];
			for(int i = 0; i < objectArr.length; i++) {
				if(objectArr[i] == null) {
					stringArr[i] = "";
				} else if(objectArr[i] instanceof Integer) {
					stringArr[i] = String.valueOf((Integer)objectArr[i]);
				} else if(objectArr[i] instanceof Double) {
					stringArr[i] = String.valueOf((Double)objectArr[i]);
				} else if(objectArr[i] instanceof Float) {
					stringArr[i] = String.valueOf((Float)objectArr[i]);
				} else if(objectArr[i] instanceof Long) {
					stringArr[i] = String.valueOf((Long)objectArr[i]);
				} else if(objectArr[i] instanceof Short) {
					stringArr[i] = String.valueOf((Short)objectArr[i]);
				} else if(objectArr[i] instanceof String) {
					stringArr[i] = String.valueOf((String)objectArr[i]);
				} else {
					stringArr[i] = objectArr[i].toString();
				}
			}
			retList.add(stringArr);
		}
		
		return retList;
	}
	
	public static String[] changeObjectToString(Object[] objectArr) {
		if(objectArr == null) return null;
		String[] stringArr = new String[objectArr.length];
		for(int i = 0; i < objectArr.length; i++) {
			if(objectArr[i] == null) {
				stringArr[i] = "";
			} else if(objectArr[i] instanceof Integer) {
				stringArr[i] = String.valueOf((Integer)objectArr[i]);
			} else if(objectArr[i] instanceof Double) {
				stringArr[i] = String.valueOf((Double)objectArr[i]);
			} else if(objectArr[i] instanceof Float) {
				stringArr[i] = String.valueOf((Float)objectArr[i]);
			} else if(objectArr[i] instanceof Long) {
				stringArr[i] = String.valueOf((Long)objectArr[i]);
			} else if(objectArr[i] instanceof Short) {
				stringArr[i] = String.valueOf((Short)objectArr[i]);
			} else if(objectArr[i] instanceof String) {
				stringArr[i] = String.valueOf((String)objectArr[i]);
			} else {
				stringArr[i] = objectArr[i].toString();
			}
		}
		return stringArr;
	}
	
	public static void copyProperties(Object dest, Object ori) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, ori);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BlogRuntimeException("copy properties faile!");
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BlogRuntimeException("copy properties faile!");
		}
	}
	
	/**
	 * 获取某个对象中某个属性的值。
	 * @param <T> 对象类型
	 * @param t 需要获取的对象实例
	 * @param fieldName 属性名称
	 * @return 属性值
	 * @throws Exception
	 */
	public static <T> Object getFieldValue(T t,String fieldName)throws Exception{
		Object value=null;
		if(null!=t){
			BeanInfo beanInfo=Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor propertyDescriptor: propertyDescriptors)
				if(fieldName.equals(propertyDescriptor.getName())){
					value=propertyDescriptor.getReadMethod().invoke(t);
					break;
				}
		}
		return value;
	}
	
	/**
	 * 为某个对象中的某个属性赋值
	 * @param <T> 对象类型
	 * @param t 需要获取的对象实例
	 * @param fieldName 属性名称
	 * @param fieldType 属性的类型
	 * @param value 属性值
	 * @throws Exception
	 */
	public static <T,F> void setFieldValue(T t,String fieldName,Class<F> fieldType,Object value)throws Exception{
		if(null!=t){
			//BeanWrapperImpl bwi = new BeanWrapperImpl(fieldType);
			//bwi.setPropertyValues(map);
			BeanInfo beanInfo=Introspector.getBeanInfo(t.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for(PropertyDescriptor propertyDescriptor: propertyDescriptors)
				if(fieldName.equals(propertyDescriptor.getName())){
					F defaultValue=null;
					if(Number.class.isAssignableFrom(fieldType)){
						defaultValue=fieldType.getConstructor(String.class).newInstance("1");
					}
					F f=ConvertUtil.convert(fieldType,value,defaultValue);
					propertyDescriptor.getWriteMethod().invoke(t,f);
					break;
				}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
	}

}
