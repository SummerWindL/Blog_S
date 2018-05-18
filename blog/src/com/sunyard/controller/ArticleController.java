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
 * 文章控制类
* @author  作者 yanl.fu: 
* @date    时间：2018年1月25日 下午6:17:10 
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
		//一些业务逻辑
		jd.setSuccess(true);
		return jd;
	}
	
	/**
	 * 根据文章id 查询文章
	 * 点击文章标题进入文章详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/article")
	public String article(String articleId,ModelMap map,HttpSession session){
		TbUserhb user = (TbUserhb) session.getAttribute("user");
		
		Integer id = Integer.parseInt(articleId);
		//根据传过来的id查询文章

		TbArticlehb article = hqlOperate.getByHqlFirst("from TbArticlehb where id=?",TbArticlehb.class,id);	
		
		map.addAttribute("article",article);
		map.addAttribute("user",user);
		log.info("article:"+article);
		return "article";
	}
	
}











