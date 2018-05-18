package com.sunyard.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sunyard.exception.BlogRuntimeException;
import com.sunyard.model.TbArticlehb;
import com.sunyard.model.TbUserhb;
import com.sunyard.service.impl.EditorServiceImpl;
import com.sunyard.util.JsonData;

/** 
*editormd控制类
* 文章发布控制类
* @author  作者 yanl.fu: 
* @date    时间：2018年2月1日 下午4:41:57 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("editormdController")
public class EditormdController extends BaseController{

	protected static final Logger log = LoggerFactory.getLogger(EditormdController.class);
	
	@Autowired
	private EditorServiceImpl editorServiceImpl;
	
	@RequestMapping("/saveArticle")
	public String saveArticle(String articletitle,String content,String editorhtml,HttpSession session,RedirectAttributes attr){
		JsonData jd = new JsonData();
		TbUserhb user = (TbUserhb) session.getAttribute("user");
		if(user==null||user.getLoginflag()==0){//未登录
//			jd.setSuccess(false);
//			jd.setErrorMessage("请先登录");
//			return jd;
			//attr.addAttribute("errorMessage", "请先登录!");//带参数拼接到url后面
			attr.addFlashAttribute("errorMessage", "请先登录!");
			return "redirect:/pageController/edit";
		}
		TbArticlehb articlehb = new TbArticlehb();
		try {
			String maxId = "select max(id) from tb_article";
			//获得表中最大id
			int id = jdbcTemplate.queryForInt(maxId);
			articlehb.setId(id+1);
			articlehb.setTitle(articletitle);
			articlehb.setContent(content);
			articlehb.setAuthor(user.getEmailAddress());
			articlehb.setCreateTime(new Date());
			editorServiceImpl.addNewArticle(articlehb);
			jd.setSuccess(true);
			log.info("成功发布文章！");
		} catch (BlogRuntimeException e) {
			log.error(e.getMessage());
			jd.setSuccess(false);
			jd.setErrorMessage("发布文章出错！");
		}
		return "redirect:/pageController/index";
	}
	
}
