package com.sunyard.entity;
/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��24�� ����3:50:48 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class Element {
	
	public String name;
	public String age;
	public String address;
	
	public Element(){
		
	}
	
	public Element(String name, String age, String address) {
		super();
		this.name = name;
		this.age = age;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
