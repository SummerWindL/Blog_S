package com.sunyard.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sunyard.controller.BaseController;
import com.sunyard.dao.TbArticleMapper;
import com.sunyard.entity.ArticleList;
import com.sunyard.entity.TbArticle;
import com.sunyard.entity.TbArticleExample;
import com.sunyard.entity.TbArticleExample.Criteria;
import com.sunyard.model.TbArticlehb;
import com.sunyard.service.ArticleService;

/** 
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��25�� ����6:20:13 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Service
public class ArticleServiceImpl extends BaseController implements ArticleService{
	
	public static final int COUNTS = 302;
	
	@Autowired
	TbArticleMapper tbArticleMapper;
	
	/**
	 * ����id��ѯ������Ϣ
	 */
	@Override
	public TbArticle getTbArticleById(Integer id) {
		TbArticle article = tbArticleMapper.selectByPrimaryKey(id);
//		TbArticleExample example = new TbArticleExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andIdEqualTo(id);
//		
//		List<TbArticle> list = tbArticleMapper.selectByExample(example);
//		TbArticle article = null;
//		if(list!=null&list.size()>0){
//			article = list.get(0);
//			
//		}
		return article;
	}

	@Override
	public List<TbArticle> listAll(/*int page,int rows*/) {
		//��������
//		List<TbArticle> articles = new ArrayList<TbArticle>();
//        for (int i = 0; i < COUNTS; ++i) {
//        	TbArticle article = new TbArticle();
//        	article.setId(i + 1);
//        	article.setTitle("����" + i + 1);
//        	article.setContent("12345" + i);
//        	article.setCreatetime(new Date());
//        	article.setAuthor("����"+i);
//        	articles.add(article);
//        }
		
        //��ѯ������Ϣ�б�
//        TbArticleExample example = new TbArticleExample();
//        //��ҳ����
//        PageHelper.startPage(page, rows);
//        List<TbArticle> list = tbArticleMapper.selectByExample(example);
//        ArticleList articleList = new ArticleList();
//        articleList.setRows(list);
//        //ȡ��¼������
//        PageInfo<TbArticle> info = new PageInfo<>(list);
//        articleList.setTotal(info.getTotal());
        List<TbArticle> allArticles = tbArticleMapper.selectAllArticle();
        
        return allArticles;
	}

	@Override
	public List<TbArticlehb> listAllByHb() {
		List<TbArticlehb> articlehbs = hqlOperate.getByHql("from TbArticlehb",TbArticlehb.class);
		
		return articlehbs;
	}

}
