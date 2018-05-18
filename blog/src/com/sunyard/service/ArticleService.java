package com.sunyard.service;

import java.util.List;

import com.sunyard.entity.ArticleList;
import com.sunyard.entity.TbArticle;
import com.sunyard.model.TbArticlehb;

/** 
* @author  作者 yanl.fu: 
* @date    时间：2018年1月25日 下午6:18:14 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface ArticleService {

	List<TbArticle> listAll(/*int page,int rows*/);	
	
	/**
	 * hibernate 查询全部
	 * @return
	 */
	List<TbArticlehb> listAllByHb();
	/**
	 * 根据ID查询文章
	 * @param id
	 * @return
	 */
	TbArticle getTbArticleById(Integer id);
}
