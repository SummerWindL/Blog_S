package com.sunyard.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sunyard.consts.Consts;
import com.sunyard.entity.TbArticle;
import com.sunyard.entity.TbUser;
import com.sunyard.exception.BlogRuntimeException;
import com.sunyard.model.TbArticlehb;
import com.sunyard.model.TbUserhb;
import com.sunyard.service.ArticleService;
import com.sunyard.service.TbUserService;
import com.sunyard.util.AESUtils;
import com.sunyard.util.Descrypt;
import com.sunyard.util.JsonData;
import com.sunyard.util.JsonUtil;
import com.sunyard.util.ListUtil;
import com.sunyard.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/** 
 * ҳ����ת������
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��1��29�� ����6:46:32 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
@Controller
@RequestMapping("pageController")
public class PageController extends BaseController{
	
	protected Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;

	@Autowired
	private TbUserService tbUserService;
	
	/**
	 * ��¼ҳ����ת
	 * @return
	 */
	@RequestMapping("/loginForward")
	public String signin(){
		return "signin";
	}
	
	/**
	 * �û���¼
	 * @param emailAddress
	 * @param password
	 * @param ignore
	 * @param req
	 * @param res
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/login")
	public String login(String emailAddress,String password, 
			HttpServletRequest req,HttpServletResponse res,HttpSession session,
			ModelMap map,RedirectAttributes attr,Model m){
		String ipAddr = req.getParameter("ip");//��¼ip
		String address = req.getParameter("addr");//��¼��ַ
		if(StringUtil.isNull(emailAddress)){
			map.put("errorMessage", "�����û������ܿգ�");
			return "signin";
		}
		if(StringUtil.isNull(password)){
			map.put("errorMessage", "�������벻�ܿգ�");
			return "signin";
		}
		map.put("emailAddress", emailAddress);
		
		//�������
		Descrypt des = new Descrypt();
		StringBuffer pwdBuffer = new StringBuffer(password);
		StringBuffer newPwdBuffer = new StringBuffer();
		newPwdBuffer = des.StrEnscrypt(pwdBuffer, newPwdBuffer);
		
		
		//ʹ��hibernate
		TbUserhb userhb = hqlOperate.getByHqlFirst("from TbUserhb where emailAddress=?",TbUserhb.class,emailAddress);
		
		TbUser user = tbUserService.getUserByEmailAddress(emailAddress);
		if(user == null){
			log.error(emailAddress+"����ʧ�ܣ���ǰ�û������ڡ�");
			map.put("errorMessage", "���󣺵�ǰ�û������ڣ�");
			return "signin";
		}
		if(!newPwdBuffer.toString().equals(user.getPassword())){
			log.error(emailAddress+"����ʧ�ܣ����벻��ȷ��");
			map.put("errorMessage", "�������벻��ȷ��");
			return "signin";
		}
		
//		String ipAddr = req.getRemoteAddr();//�ͻ���Ip
		//���û���Ϣ����session
		session.setAttribute("user", userhb);
//		attr.addFlashAttribute("user",user);
//		m.addAttribute("user",user);
		log.info("�û�IDΪ��"+user.getUserid()+"��¼�ɹ���IP��"+ipAddr+",��ַ��"+address);
		res.addHeader("JSESSIONID", session.getId());
//		return "index";
		return "redirect:index";
	}
	
	/**
	 * ��ҳ
	 * @param paraMap
	 * @param request
	 * @param response
	 * @param attr
	 * @param map
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/index")
	public String getAllArticle(@RequestParam Map<String,String> paraMap,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes attr,
			ModelMap map,HttpSession session) throws Exception {
		
		TbUserhb user = (TbUserhb) session.getAttribute("user");
		
		int page;
		
		ModelMap errorMap = new ModelMap();//��Ŵ�����Ϣmap
		List<String> emailAddressList = new ArrayList<String>();
		List<TbUserhb> userlist = tbUserService.getAllUsersFromHib();
		//����userlist
		for(int i=0;i<userlist.size();i++){
			String address = userlist.get(i).getEmailAddress();
			emailAddressList.add(address);
		}
	    
		String emailAddress = ListUtil.listToString(emailAddressList);
		
		JSONArray json = JSONArray.fromObject(userlist);
		
		byte[] jsonByte = json.toString().getBytes();
		
		String base64 = AESUtils.encryptBASE64(jsonByte);
		//��Ҫ�ǻ�ȡ�û���Ϣ
		if(user==null){
			//1.δ��¼���볢�Ե�¼
			map.addAttribute("errorMessage", "1");
			
//			map.addAttribute("emailAddressList", emailAddress);
			map.addAttribute("json",json);
			return "index";//δ��¼������ҳ��
		}else{
			//�ж��Ƿ��¼
			//�����ݿ���л�ȡ��ǰuser�ĵ�¼״̬
//			TbUserhb nowUser = hqlOperate.getByHqlFirst(" from TbUserhb where userId=?",TbUserhb.class,user.getUserId());
			if(Consts.loginflag == user.getLoginflag()){//δ��¼
				map.addAttribute("errorMessage","���¼");
				return "login";
			}else{
				//�ж��Ƿ�ֱ�ӷ��ʵ���ҳ
				
				//��½�����õ�¼״̬Ϊ1
				user.setLoginflag(1);
				hqlOperate.merge(user);
				map.addAttribute("user",user);
				map.addAttribute("emailAddressList", emailAddress);
				List<TbArticlehb> oldarticlehbs = articleService.listAllByHb();
				List<TbArticlehb> articlehbs = new ArrayList<TbArticlehb>();
				String content="";
				if(oldarticlehbs.size()>0){
					
					for(TbArticlehb article:oldarticlehbs){
						//��ȡcontent����������չʾ
						if(article.getContent().length()>0){
							if(article.getContent().contains(",")){
								content = article.getContent().substring(0,article.getContent().indexOf(","));
								article.setContent(content);
							}else if(article.getContent().contains("<pre>")){
								article.setContent(article.getContent().replace("<pre>", "").replace("</pre>", "").replace("<code class='javascript'>", "").replace("</code>", ""));
							}else{
								content = article.getContent().substring(0,10);
								article.setContent(content);
							}
						}else{
							
						}
						articlehbs.add(article);
					}
					
//					String p = request.getParameter("page");
					String p = paraMap.get("page");//ʹ�������ʱ��ǰ���п��ܴ�null�����������ֱ�ӽ�null��Ϊһ���ַ���ʹ��
					
					try {
						//��ǰҳ��
						page = Integer.valueOf(p);
					} catch (NumberFormatException e) {
						page = 1;
					}
					//��������
					int totalArticles = articlehbs.size();
					//ÿҳ������
					//����Ϊ����
					int articlePerPage = Consts.pageArticle;
					//��ҳ��
					int totalPages = totalArticles % articlePerPage == 0 ? totalArticles/articlePerPage:totalArticles/articlePerPage+1;
					//��ҳ��ʼ�������
					int beginIndex = (page-1) * articlePerPage;
					//��ҳĩβ���������һ��
					int endIndex = beginIndex + articlePerPage;
					if(endIndex > totalArticles)
						endIndex = totalArticles;
					
//					map.addAttribute("totalArticles", totalArticles);
//					map.addAttribute("articlePerPage", articlePerPage);
//					map.addAttribute("totalPages", totalPages);
//					map.addAttribute("beginIndex", beginIndex);
//					map.addAttribute("endIndex", endIndex);
//					map.addAttribute("page", page);
//					map.addAttribute("articles", articlehbs);
					
					request.setAttribute("totalArticles", totalArticles);
					request.setAttribute("articlePerPage", articlePerPage);
					request.setAttribute("totalPages", totalPages);
					request.setAttribute("beginIndex", beginIndex);
					request.setAttribute("endIndex", endIndex);
					request.setAttribute("page", page);
					request.setAttribute("articles", articlehbs);
					
					//�û���Ϣ
					request.setAttribute("user", map.get("user"));
					
					logger.info("page��"+page);
					logger.info("����ID��"+articlehbs.get(0).getId());
					return "index";//��¼��ҳ��
				}else{
					page = 0;
					request.setAttribute("page", page);
					return "index";
				}
				

			}
			
		}
		
	}
	
	/**
	 * ϵͳ�ǳ�
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/loginout")
	@ResponseBody
	public JsonData loginout(HttpSession session,ModelMap model){
		JsonData jd = new JsonData();
		TbUserhb user = (TbUserhb) session.getAttribute("user");
		try {
			if(user!=null){
//				List<TbUserhb> userList = hqlOperate.getByHql(" from TbUserhb where emailAddress=?",TbUserhb.class,user.getEmailAddress());
//				if(!userList.isEmpty()&&userList.size()>0){
//					user = userList.get(0);
//					user.setLoginflag(0);//δ��¼
//					hqlOperate.merge(user);
//					jd.setSuccess(true);
//					model.put("loginMessage",Consts.loginMessage_3);//3  �ǳ��ɹ�
//					log.info("�û�"+user.getEmailAddress()+"�˳���ϵͳ��");
//				}else{
//					jd.setSuccess(false);
//					model.put("loginMessage", Consts.loginMessage_4);//4 �ǳ�ʧ�� 
//				}
				Integer loginflag = 0;//״̬�޸�Ϊδ��¼
				user.setLoginflag(loginflag);
				System.out.println(user.getLoginflag());
				hqlOperate.merge(user);
				jd.setSuccess(true);
				model.put("loginMessage",Consts.loginMessage_3);//3  �ǳ��ɹ�
				log.info("�û�"+user.getEmailAddress()+"�˳���ϵͳ��");
			}else{
				jd.setSuccess(false);
				jd.setErrorMessage("�������");
				log.error("�ǳ�����"+PageController.class.getMethods());
				throw new BlogRuntimeException("�������");
			}
		} catch (BlogRuntimeException e) {
			e.printStackTrace();
			jd.setSuccess(false);
			log.error(e.getMessage());
			model.put("loginMessage", Consts.loginMessage_5);
		}
		return jd;
	}
	
	/**
	 * ��ת�����·���ҳ��
	 * @param attr
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEditorMd(RedirectAttributes attr,
			String content,String articletitle,ModelMap map,HttpSession session){
		Map attrmap = new HashMap<>();
		//�жϱ����Ƿ�Ϊ��
		if(null==articletitle||articletitle.equals("")){
			map.addAttribute("errorMessage","���ⲻ��Ϊ�գ�");
			return "edit";
		}
		if(null==content||content.equals("")){
			map.addAttribute("errorMessage","���棺�ı�Ϊ�գ�");
//			return "edit";
		}
		//�ж��Ƿ��¼
		if(attr!=null&&!attr.equals("")&&!attr.equals(attrmap)){
			map.put("errorMessage", "���ȵ�¼!");
		}else{
			TbUserhb user = (TbUserhb)session.getAttribute("user");
			map.put("user", user);
		}
		return "edit";
	}
	
	@RequestMapping("/editTest")
	public String toEditTest(){
		return "editTest";
	}
	
	/**
	 * ��ҳ��ת����
	 * @return
	 */
	@RequestMapping("/toIndex")
	@ResponseBody
	public JsonData toIndex(){
		JsonData jd = new JsonData();
		return jd;
	}
	
	@RequestMapping("/toEditpage")
	@ResponseBody
	public JsonData toEditpage(){
		JsonData jd = new JsonData();
		return jd;
	}
	
	@RequestMapping("/toUserPage")
	@ResponseBody
	public JsonData toUserPage(){
		JsonData jd = new JsonData();
		jd.setSuccess(true);
		return jd;
	}
}
