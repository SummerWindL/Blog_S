package com.sunyard.service;

import com.sunyard.model.TbArticlehb;

/** 
 * 文章发布服务层
* @author  作者 yanl.fu: 
* @date    时间：2018年2月1日 下午4:45:21 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface EditorService {

	/**
	 * 发布文章
	 * @param articlehb
	 */
	void addNewArticle(TbArticlehb articlehb);
}
