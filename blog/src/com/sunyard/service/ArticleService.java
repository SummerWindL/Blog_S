package com.sunyard.service;

import java.util.List;

import com.sunyard.entity.ArticleList;
import com.sunyard.entity.TbArticle;
import com.sunyard.model.TbArticlehb;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��25�� ����6:18:14 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public interface ArticleService {

	List<TbArticle> listAll(/*int page,int rows*/);	
	
	/**
	 * hibernate ��ѯȫ��
	 * @return
	 */
	List<TbArticlehb> listAllByHb();
	/**
	 * ����ID��ѯ����
	 * @param id
	 * @return
	 */
	TbArticle getTbArticleById(Integer id);
}
