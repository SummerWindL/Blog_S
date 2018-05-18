package com.sunyard.service;

import java.util.List;

import com.sunyard.entity.TbUser;
import com.sunyard.model.TbUserhb;

/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年1月30日 上午11:23:38 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface TbUserService {

	/**
	 * 根据userId 用户
	 * @param userId
	 * @return
	 */
	TbUser getUserById(Integer userId);
	
	/**
	 * 根据emailAddress 查找用户
	 * @param emailAddress
	 * @return
	 */
	TbUser getUserByEmailAddress(String emailAddress);
	
	/**
	 * 查所有用户
	 * 用于前端验证，是否是有效的email
	 * @return
	 */
	List<TbUser> getAllUsers();
	
	/**
	 * hibernate 方式 获取所有用户
	 * @return
	 */
	List<TbUserhb> getAllUsersFromHib();
}
