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
*editormd������
* ���·���������
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��2��1�� ����4:41:57 
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
		if(user==null||user.getLoginflag()==0){//δ��¼
//			jd.setSuccess(false);
//			jd.setErrorMessage("���ȵ�¼");
//			return jd;
			//attr.addAttribute("errorMessage", "���ȵ�¼!");//������ƴ�ӵ�url����
			attr.addFlashAttribute("errorMessage", "���ȵ�¼!");
			return "redirect:/pageController/edit";
		}
		TbArticlehb articlehb = new TbArticlehb();
		try {
			String maxId = "select max(id) from tb_article";
			//��ñ������id
			int id = jdbcTemplate.queryForInt(maxId);
			articlehb.setId(id+1);
			articlehb.setTitle(articletitle);
			articlehb.setContent(content);
			articlehb.setAuthor(user.getEmailAddress());
			articlehb.setCreateTime(new Date());
			editorServiceImpl.addNewArticle(articlehb);
			jd.setSuccess(true);
			log.info("�ɹ��������£�");
		} catch (BlogRuntimeException e) {
			log.error(e.getMessage());
			jd.setSuccess(false);
			jd.setErrorMessage("�������³���");
		}
		return "redirect:/pageController/index";
	}
	
}
