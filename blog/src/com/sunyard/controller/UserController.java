package com.sunyard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年2月5日 下午3:12:42 
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
