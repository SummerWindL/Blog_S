package com.sunyard.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunyard.controller.BaseController;
import com.sunyard.dao.TbUserMapper;
import com.sunyard.entity.TbUser;
import com.sunyard.entity.TbUserExample;
import com.sunyard.entity.TbUserExample.Criteria;
import com.sunyard.model.TbUserhb;
import com.sunyard.service.TbUserService;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��30�� ����11:25:59 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Service
public class TbUserServiceImpl extends BaseController implements TbUserService{

	@Autowired
	TbUserMapper tbUserMapper;
	
	@Override
	public TbUser getUserById(Integer userId) {
		
		TbUser user = tbUserMapper.selectByPrimaryKey(userId);
		return user;
	}

	@Override
	public TbUser getUserByEmailAddress(String emailAddress) {
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailaddressEqualTo(emailAddress);
		
		List<TbUser> list = tbUserMapper.selectByExample(example);
		
		if(null!=list&&list.size()>0){
			TbUser user = list.get(0);
			return user;
		}else{
			log.error("�����û�ʧ�ܣ�");
			return null;
		}
		
	}

	@Override
	public List<TbUserhb> getAllUsersFromHib() {
//		TbUserExample example = new TbUserExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andEmailaddressIsNotNull();
//		List<TbUser> userlist = tbUserMapper.selectByExample(example);
		
		List<TbUserhb> list = hqlOperate.getByHql("from TbUserhb",TbUserhb.class);
		return list;
	}

	@Override
	public List<TbUser> getAllUsers() {
		return null;
	}

}
