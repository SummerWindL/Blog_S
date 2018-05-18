package com.sunyard.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunyard.model.TbUserhb;

/** 
 * 拦截器，拦截部分请求
* @author  作者 yanl.fu: 
* @date    时间：2018年2月6日 上午10:36:33 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class LogsInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(LogsInterceptor.class);
	
	private static final String[] IGNORE_URI={"/pageController/index","/pageController/loginForward","/pageController/login"};   //填写XX.do
	
	/**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
     * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
     * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，
     * 而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
     * SpringMVC的这种Interceptor链式结构也是可以进行中断的，
     * 这种中断方式是令preHandle的返回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object arg2) throws Exception {  
          
          //对请求路径进行判断
        String servletPath=request.getServletPath(); 
        boolean flag=false;   //用于存储判断登录的结果
        //判断请求是否需要拦截
        for(String s:IGNORE_URI){
            if(servletPath.contains(s)){
                flag=true;    //如果是不拦截的网站，flag为true,跳出循环，转向下个方法
                break;
            }
        }
        //拦截请求
        if(!flag){   //如果是非公开的页面↓
        	TbUserhb admin= (TbUserhb)request.getSession().getAttribute("user");
            if(admin==null){
                //System.out.println("AuthorizationInterceptor拦截请求");
                //request.setAttribute("message", "请先登录管理员后再访问网站");
            	request.setAttribute("errorMessage", "请先登录再访问网站!");
            	response.sendRedirect("/blog/pageController/loginForward");  
            }else if(admin.getLoginflag()==0){//判断是否登录
            	request.setAttribute("errorMessage", "请先登录再访问网站!");;
            	response.sendRedirect("/blog/pageController/loginForward");  
            }else{
                //用户登陆过，验证通过，放行
                //System.out.println("AuthorizationInterceptor放行请求");
                flag=true;
            }
        }
        return flag;
    }
	
	/**
     * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。
     * 该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行， 这个方法的主要作用是用于清理资源的，
     */
	@Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object obj, Exception err)  
            throws Exception {  
    }  
  
	/**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。
     * postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之 后， 也就是在Controller的方法调用之后执行，
     * 但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操作。
     * 这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，
     * 这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，
     * Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor或者是调用action，
     * 然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {  
//        response.sendRedirect("/pageController/loginForward");  
    }  

	
}
