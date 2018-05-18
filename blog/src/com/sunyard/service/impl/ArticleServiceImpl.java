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
* @author  作者 yanl.fu: 
* @date    时间：2018年1月25日 下午6:20:13 
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
	 * 根据id查询文章信息
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
		//测试数据
//		List<TbArticle> articles = new ArrayList<TbArticle>();
//        for (int i = 0; i < COUNTS; ++i) {
//        	TbArticle article = new TbArticle();
//        	article.setId(i + 1);
//        	article.setTitle("文章" + i + 1);
//        	article.setContent("12345" + i);
//        	article.setCreatetime(new Date());
//        	article.setAuthor("作者"+i);
//        	articles.add(article);
//        }
		
        //查询文章信息列表
//        TbArticleExample example = new TbArticleExample();
//        //分页处理
//        PageHelper.startPage(page, rows);
//        List<TbArticle> list = tbArticleMapper.selectByExample(example);
//        ArticleList articleList = new ArticleList();
//        articleList.setRows(list);
//        //取记录总条数
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
