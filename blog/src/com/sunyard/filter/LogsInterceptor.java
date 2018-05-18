package com.sunyard.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sunyard.model.TbUserhb;

/** 
 * �����������ز�������
* @author  ���� yanl.fu: 
* @date    ʱ�䣺2018��2��6�� ����10:36:33 
* @version 1.0 
* @parameter  
* @since  
* @return  
*/
public class LogsInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(LogsInterceptor.class);
	
	private static final String[] IGNORE_URI={"/pageController/index","/pageController/loginForward","/pageController/login"};   //��дXX.do
	
	/**
     * preHandle�����ǽ��д����������õģ�����˼�壬�÷�������Controller����֮ǰ���е��ã�
     * SpringMVC�е�Interceptor����������ʽ�ģ�����ͬʱ���ڶ��Interceptor��
     * Ȼ��SpringMVC�����������ǰ��˳��һ����һ����ִ�У�
     * �������е�Interceptor�е�preHandle����������Controller��������֮ǰ���á�
     * SpringMVC������Interceptor��ʽ�ṹҲ�ǿ��Խ����жϵģ�
     * �����жϷ�ʽ����preHandle�ķ���ֵΪfalse����preHandle�ķ���ֵΪfalse��ʱ����������ͽ����ˡ�
     */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  
            Object arg2) throws Exception {  
          
          //������·�������ж�
        String servletPath=request.getServletPath(); 
        boolean flag=false;   //���ڴ洢�жϵ�¼�Ľ��
        //�ж������Ƿ���Ҫ����
        for(String s:IGNORE_URI){
            if(servletPath.contains(s)){
                flag=true;    //����ǲ����ص���վ��flagΪtrue,����ѭ����ת���¸�����
                break;
            }
        }
        //��������
        if(!flag){   //����Ƿǹ�����ҳ���
        	TbUserhb admin= (TbUserhb)request.getSession().getAttribute("user");
            if(admin==null){
                //System.out.println("AuthorizationInterceptor��������");
                //request.setAttribute("message", "���ȵ�¼����Ա���ٷ�����վ");
            	request.setAttribute("errorMessage", "���ȵ�¼�ٷ�����վ!");
            	response.sendRedirect("/blog/pageController/loginForward");  
            }else if(admin.getLoginflag()==0){//�ж��Ƿ��¼
            	request.setAttribute("errorMessage", "���ȵ�¼�ٷ�����վ!");;
            	response.sendRedirect("/blog/pageController/loginForward");  
            }else{
                //�û���½������֤ͨ��������
                //System.out.println("AuthorizationInterceptor��������");
                flag=true;
            }
        }
        return flag;
    }
	
	/**
     * �÷���Ҳ����Ҫ��ǰ��Ӧ��Interceptor��preHandle�����ķ���ֵΪtrueʱ�Ż�ִ�С�
     * �÷������������������֮��Ҳ����DispatcherServlet��Ⱦ����ͼִ�У� �����������Ҫ����������������Դ�ģ�
     */
	@Override  
    public void afterCompletion(HttpServletRequest request,  
            HttpServletResponse response, Object obj, Exception err)  
            throws Exception {  
    }  
  
	/**
     * �������ֻ���ڵ�ǰ���Interceptor��preHandle��������ֵΪtrue��ʱ��Ż�ִ�С�
     * postHandle�ǽ��д����������õģ�����ִ��ʱ�����ڴ��������д���֮ �� Ҳ������Controller�ķ�������֮��ִ�У�
     * ����������DispatcherServlet������ͼ����Ⱦ֮ǰִ�У�Ҳ����˵���������������Զ�ModelAndView���в�����
     * �����������ʽ�ṹ���������ʵķ������෴�ģ�Ҳ����˵��������Interceptor�������÷������������ã�
     * ���Struts2�������������ִ�й����е���
     * ֻ��Struts2�����intercept������Ҫ�ֶ��ĵ���ActionInvocation��invoke������
     * Struts2�е���ActionInvocation��invoke�������ǵ�����һ��Interceptor�����ǵ���action��
     * Ȼ��Ҫ��Interceptor֮ǰ���õ����ݶ�д�ڵ���invoke֮ǰ��Ҫ��Interceptor֮����õ����ݶ�д�ڵ���invoke����֮��
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response) throws Exception {  
//        response.sendRedirect("/pageController/loginForward");  
    }  

	
}
