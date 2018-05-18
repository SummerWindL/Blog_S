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
 * 页面跳转控制器
* @author  作者 yanl.fu: 
* @date    时间：2018年1月29日 下午6:46:32 
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
	 * 登录页面跳转
	 * @return
	 */
	@RequestMapping("/loginForward")
	public String signin(){
		return "signin";
	}
	
	/**
	 * 用户登录
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
		String ipAddr = req.getParameter("ip");//登录ip
		String address = req.getParameter("addr");//登录地址
		if(StringUtil.isNull(emailAddress)){
			map.put("errorMessage", "错误：用户名不能空！");
			return "signin";
		}
		if(StringUtil.isNull(password)){
			map.put("errorMessage", "错误：密码不能空！");
			return "signin";
		}
		map.put("emailAddress", emailAddress);
		
		//密码加密
		Descrypt des = new Descrypt();
		StringBuffer pwdBuffer = new StringBuffer(password);
		StringBuffer newPwdBuffer = new StringBuffer();
		newPwdBuffer = des.StrEnscrypt(pwdBuffer, newPwdBuffer);
		
		
		//使用hibernate
		TbUserhb userhb = hqlOperate.getByHqlFirst("from TbUserhb where emailAddress=?",TbUserhb.class,emailAddress);
		
		TbUser user = tbUserService.getUserByEmailAddress(emailAddress);
		if(user == null){
			log.error(emailAddress+"登入失败，当前用户不存在。");
			map.put("errorMessage", "错误：当前用户不存在！");
			return "signin";
		}
		if(!newPwdBuffer.toString().equals(user.getPassword())){
			log.error(emailAddress+"登入失败，密码不正确。");
			map.put("errorMessage", "错误：密码不正确。");
			return "signin";
		}
		
//		String ipAddr = req.getRemoteAddr();//客户端Ip
		//将用户信息存入session
		session.setAttribute("user", userhb);
//		attr.addFlashAttribute("user",user);
//		m.addAttribute("user",user);
		log.info("用户ID为："+user.getUserid()+"登录成功！IP："+ipAddr+",地址："+address);
		res.addHeader("JSESSIONID", session.getId());
//		return "index";
		return "redirect:index";
	}
	
	/**
	 * 首页
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
		
		ModelMap errorMap = new ModelMap();//存放错误信息map
		List<String> emailAddressList = new ArrayList<String>();
		List<TbUserhb> userlist = tbUserService.getAllUsersFromHib();
		//遍历userlist
		for(int i=0;i<userlist.size();i++){
			String address = userlist.get(i).getEmailAddress();
			emailAddressList.add(address);
		}
	    
		String emailAddress = ListUtil.listToString(emailAddressList);
		
		JSONArray json = JSONArray.fromObject(userlist);
		
		byte[] jsonByte = json.toString().getBytes();
		
		String base64 = AESUtils.encryptBASE64(jsonByte);
		//主要是获取用户信息
		if(user==null){
			//1.未登录，请尝试登录
			map.addAttribute("errorMessage", "1");
			
//			map.addAttribute("emailAddressList", emailAddress);
			map.addAttribute("json",json);
			return "index";//未登录看到的页面
		}else{
			//判断是否登录
			//从数据库表中获取当前user的登录状态
//			TbUserhb nowUser = hqlOperate.getByHqlFirst(" from TbUserhb where userId=?",TbUserhb.class,user.getUserId());
			if(Consts.loginflag == user.getLoginflag()){//未登录
				map.addAttribute("errorMessage","请登录");
				return "login";
			}else{
				//判断是否直接访问的主页
				
				//登陆后设置登录状态为1
				user.setLoginflag(1);
				hqlOperate.merge(user);
				map.addAttribute("user",user);
				map.addAttribute("emailAddressList", emailAddress);
				List<TbArticlehb> oldarticlehbs = articleService.listAllByHb();
				List<TbArticlehb> articlehbs = new ArrayList<TbArticlehb>();
				String content="";
				if(oldarticlehbs.size()>0){
					
					for(TbArticlehb article:oldarticlehbs){
						//截取content长度做部分展示
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
					String p = paraMap.get("page");//使用这个的时候，前端有可能传null过来，这个是直接将null作为一个字符串使用
					
					try {
						//当前页数
						page = Integer.valueOf(p);
					} catch (NumberFormatException e) {
						page = 1;
					}
					//文章总数
					int totalArticles = articlehbs.size();
					//每页文章数
					//配置为常量
					int articlePerPage = Consts.pageArticle;
					//总页数
					int totalPages = totalArticles % articlePerPage == 0 ? totalArticles/articlePerPage:totalArticles/articlePerPage+1;
					//本页起始文章序号
					int beginIndex = (page-1) * articlePerPage;
					//本页末尾文章序号下一个
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
					
					//用户信息
					request.setAttribute("user", map.get("user"));
					
					logger.info("page："+page);
					logger.info("文章ID："+articlehbs.get(0).getId());
					return "index";//登录后页面
				}else{
					page = 0;
					request.setAttribute("page", page);
					return "index";
				}
				

			}
			
		}
		
	}
	
	/**
	 * 系统登出
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
//					user.setLoginflag(0);//未登录
//					hqlOperate.merge(user);
//					jd.setSuccess(true);
//					model.put("loginMessage",Consts.loginMessage_3);//3  登出成功
//					log.info("用户"+user.getEmailAddress()+"退出了系统！");
//				}else{
//					jd.setSuccess(false);
//					model.put("loginMessage", Consts.loginMessage_4);//4 登出失败 
//				}
				Integer loginflag = 0;//状态修改为未登录
				user.setLoginflag(loginflag);
				System.out.println(user.getLoginflag());
				hqlOperate.merge(user);
				jd.setSuccess(true);
				model.put("loginMessage",Consts.loginMessage_3);//3  登出成功
				log.info("用户"+user.getEmailAddress()+"退出了系统！");
			}else{
				jd.setSuccess(false);
				jd.setErrorMessage("程序错误！");
				log.error("登出错误。"+PageController.class.getMethods());
				throw new BlogRuntimeException("程序错误！");
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
	 * 跳转至文章发布页面
	 * @param attr
	 * @param map
	 * @param session
	 * @return
	 */
	@RequestMapping("/edit")
	public String toEditorMd(RedirectAttributes attr,
			String content,String articletitle,ModelMap map,HttpSession session){
		Map attrmap = new HashMap<>();
		//判断标题是否为空
		if(null==articletitle||articletitle.equals("")){
			map.addAttribute("errorMessage","标题不能为空！");
			return "edit";
		}
		if(null==content||content.equals("")){
			map.addAttribute("errorMessage","警告：文本为空！");
//			return "edit";
		}
		//判断是否登录
		if(attr!=null&&!attr.equals("")&&!attr.equals(attrmap)){
			map.put("errorMessage", "请先登录!");
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
	 * 主页跳转方法
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
