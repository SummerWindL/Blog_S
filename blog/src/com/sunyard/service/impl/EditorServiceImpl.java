package com.sunyard.service.impl;

import org.springframework.stereotype.Service;

import com.sunyard.controller.BaseController;
import com.sunyard.model.TbArticlehb;
import com.sunyard.service.EditorService;

/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年2月1日 下午4:47:03 
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
