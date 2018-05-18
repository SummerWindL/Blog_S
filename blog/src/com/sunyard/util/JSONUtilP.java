package com.sunyard.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.sunyard.entity.Element;

/**
 * JSON工具类【GSON实现】
 * @author jinhui.z
 *
 */
public class JSONUtilP {

	//获取json解析对象
	private static Gson gson=new Gson();
	
	/**
	 * 将一个对象转换成json字符串 
	 * @author glory
	 * @param object 待转换的对象
	 * @return json字符串
	 */
	public static String objConvertJSONStr(Object obj){
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(obj);
		}
		return jsonStr;  
	}
	
	/**
	 * 将一个json字符串转换成基本对象
	 * @author glory
	 * @param jsonStr json字符串
	 * @param cls 待转换的类的类
	 * @return 返回的json对象
	 */
	public static <T> T jsonStrConvertObj(String jsonStr,Class<T> cls){
		T t = null;  
		if (gson != null) {  
			t = gson.fromJson(jsonStr, cls);  
		}  
		return t;  
	}
	
	/**
	 * 将一个json字符串转换成list集合对象
	 * @author glory
	 * @param jsonStr
	 * @param cls
	 * @return
	 */
	public static <T> List<T> jsonStrConvertList(String jsonStr,Class<T> cls){
		List<T> list = new ArrayList<T>();
		JsonArray array = new JsonParser().parse(jsonStr).getAsJsonArray();
		for (final JsonElement elem : array) {
			list.add(gson.fromJson(elem, cls));
		}
		return list;
	}
	
	/**
	 * 将带有map的json字符串转成map集合
	 * @author glory
	 * @param gsonString
	 * @return
	 */
	public static <T> Map<String, T> GsonToMaps(String jsonStr) {
		Map<String, T> map = null;
		if (gson != null) {
			map = gson.fromJson(jsonStr, new TypeToken<Map<String, T>>() {}.getType());
		}
		return map;
	}
	
	/**
	 * 将装有map的list集合的json字符串转成list集合
	 * @author glory 
	 * @param jsonStr
	 * @return
	 */
	public static <T> List<Map<String, T>> GsonToListMaps(String jsonStr) {
		List<Map<String, T>> list = null;
		if (gson != null) {
			list = gson.fromJson(jsonStr,new TypeToken<List<Map<String, T>>>() {}.getType());
		}
		return list;
	}
	
	public static void main(String[] args) {
		List<Element> list = new ArrayList<Element>();
		Element element = new Element();
		element.setName("Joy");
		element.setAge("11");
		element.setAddress(null);
		
		Element element2 = new Element();
		element2.setName("Chou");
		element2.setAge("10");
		element2.setAddress(null);
		
		list.add(element);
		list.add(element2);
		
		System.out.println(JSONUtilP.objConvertJSONStr(list));
		
		JSONObject json = new JSONObject(element);
		System.out.println(json);
	}

}
