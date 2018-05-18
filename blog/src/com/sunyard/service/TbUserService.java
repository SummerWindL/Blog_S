package com.sunyard.service;

import java.util.List;

import com.sunyard.entity.TbUser;
import com.sunyard.model.TbUserhb;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��30�� ����11:23:38 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface TbUserService {

	/**
	 * ����userId �û�
	 * @param userId
	 * @return
	 */
	TbUser getUserById(Integer userId);
	
	/**
	 * ����emailAddress �����û�
	 * @param emailAddress
	 * @return
	 */
	TbUser getUserByEmailAddress(String emailAddress);
	
	/**
	 * �������û�
	 * ����ǰ����֤���Ƿ�����Ч��email
	 * @return
	 */
	List<TbUser> getAllUsers();
	
	/**
	 * hibernate ��ʽ ��ȡ�����û�
	 * @return
	 */
	List<TbUserhb> getAllUsersFromHib();
}
