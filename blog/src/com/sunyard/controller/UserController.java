package com.sunyard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��2��5�� ����3:12:42 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("userController")
public class UserController extends BaseController{

	@RequestMapping("/userPage")
	public String toUserPage(){
		return "/person/usermessage";
	}
}
