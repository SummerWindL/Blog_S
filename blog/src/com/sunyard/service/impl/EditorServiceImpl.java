package com.sunyard.service.impl;

import org.springframework.stereotype.Service;

import com.sunyard.controller.BaseController;
import com.sunyard.model.TbArticlehb;
import com.sunyard.service.EditorService;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��2��1�� ����4:47:03 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Service
public class EditorServiceImpl extends BaseController implements EditorService {

	@Override
	public void addNewArticle(TbArticlehb articlehb) {
		hqlOperate.save(articlehb);
	}

}
