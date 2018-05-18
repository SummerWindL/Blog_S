package com.sunyard.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sunyard.entity.TbArticle;
import com.sunyard.model.TbArticlehb;
import com.sunyard.model.TbUserhb;
import com.sunyard.service.ArticleService;
import com.sunyard.util.JsonData;

/** 
 * ���¿�����
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��25�� ����6:17:10 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("articleController")
public class ArticleController extends BaseController{

	protected Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	private List<TbArticle> articles;
	
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping("/toArticle")
	@ResponseBody
	public TbArticle getArticleById(Integer id){
		TbArticle article = articleService.getTbArticleById(id);
		return article;
	}
	
	@RequestMapping("/toArticlePage")
	@ResponseBody
	public JsonData toArticle(String id){
		JsonData jd = new JsonData();
		//һЩҵ���߼�
		jd.setSuccess(true);
		return jd;
	}
	
	/**
	 * ��������id ��ѯ����
	 * ������±��������������ҳ��
	 * @param id
	 * @return
	 */
	@RequestMapping("/article")
	public String article(String articleId,ModelMap map,HttpSession session){
		TbUserhb user = (TbUserhb) session.getAttribute("user");
		
		Integer id = Integer.parseInt(articleId);
		//���ݴ�������id��ѯ����

		TbArticlehb article = hqlOperate.getByHqlFirst("from TbArticlehb where id=?",TbArticlehb.class,id);	
		
		map.addAttribute("article",article);
		map.addAttribute("user",user);
		log.info("article:"+article);
		return "article";
	}
	
}











