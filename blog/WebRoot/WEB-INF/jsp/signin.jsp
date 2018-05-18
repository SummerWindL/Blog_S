<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()+":"+request.getServletPath()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Blog-S</title>
<style type="text/css">.video_bj{width: 1366px;height: 100%;position: absolute;top: 0;left: 0;right: 0;bottom: 0;overflow-y: hidden;}</style>
<link rel="stylesheet" href="<%=path%>/css/bootstrap.css.map" />
<link rel="stylesheet" href="<%=path%>/css/bootstrap.css" />
<link rel="shortcut icon" href="<%=path%>/images/162-S-Letter.ico"/>
<link rel="stylesheet" href="<%=path%>/css/signin.css">
<script type="text/javascript" src="<%=path%>/js/jquery-3.3.1.js"></script>
<!-- toastr消息提示插件需要在jquery引入后使用 -->
<script type="text/javascript" src="<%=path%>/js/attention/toastr.min.js"></script>
<link rel="stylesheet" href="<%=path%>/css/attention/toastr.min.css" />
<script type="text/javascript" src="<%=path%>/js/login/cookie.js"></script>
<script type="text/javascript">
   /**
	* 初始化toastr 自定义toastr属性
	*/
	toastr.options = {  
	        closeButton: false,  
	        debug: false,  
	        progressBar: false,  
	        positionClass: "toast-top-center",  
	        onclick: null,  
	        showDuration: "300",  
	        hideDuration: "1000",  
	        timeOut: "2000",  
	        extendedTimeOut: "1000",  
	        showEasing: "swing",  
	        hideEasing: "linear",  
	        showMethod: "fadeIn",  
	        hideMethod: "fadeOut"  
	};
	
   var path = '${pageContext.request.contextPath}';
   var emailAddress = '${requestScope.emailAddress}';
   var password = '${requestScope.password}';
   var loginMessage = '${requestScope.errorMessage}';//返回的错误消息
   var cookieEmailAddress ='';
   toastr.info("path:"+path);
   
   window.onload =  function(){
	   cookieEmailAddress = getCookieValue("userName");
	   if(null!=cookieEmailAddress && ''!=cookieEmailAddress){
		   document.getElementById('inputEmail').value=cookieEmailAddress;
	   }
	   document.getElementById('inputPassword').focus();
   }
   //登录事件
	function login(){
		var emailAddress = document.getElementById("inputEmail");
		var password = document.getElementById("inputPassword");
		/* debugger; */
		if(emailAddress.value==''||null==emailAddress.value){
			toastr.error("用户名不能空！");
			return;
		}
		if(password.value==''){
			toastr.error("密码不能空！");
			return;
		}
		if(cookieEmailAddress!=emailAddress){
			if(null!=cookieEmailAddress && ""!=cookieEmailAddress){
				deleteCookie("userName", "/");
			}
			setCookie("userName", emailAddress.value, 24, "/")
		}
		submitForm();
	}
   	
   	/**
   	* 跨域请求获取 登录的ip及地址
   	*/
	function getIpAndAddr(){
	   var ip='';
	   var address='';
	   
	   $.ajax({
		    async: false,
		    type: "GET",
		    dataType: 'jsonp',
		    jsonp: 'callback',
		    jsonpCallback: 'callbackfunction',
		    url: "http://ip.chinaz.com/getip.aspx",
		    timeout: 3000,
		    contentType: "application/json;utf-8",
		    success: function(msg) {
		      ip = msg.ip,
		      address = msg.address
		      //将ip，address传入获取方法
		      getIpAndAddress(ip,address);
		    }
		  });
	}
	
   	//将ip，address赋值给隐藏组件，供后台接收
   function getIpAndAddress(ip,address){
	   /* toastr.info("ip:"+ip);
	   toastr.info("address:"+address); */
	   document.getElementById('ipAddr').value=ip;
	   document.getElementById('address').value=address;
	   /* toastr.info("ipAddr:"+document.getElementById('ipAddr').value); */
   }
   	
   function submitForm(){
	   var form = document.getElementById('loginForm');
	   form.action = path+'/pageController/login';
	   /* debugger; */
	   form.submit();
   }
   
   
   $(function(){
	   if(null!=loginMessage && ''!=loginMessage){
		   toastr.error(loginMessage);
	   }
	   /* debugger; */
	   getIpAndAddr();
   })
</script>
</head>
<body>
	<div class="video_bj">
	   		<video width="100%" autoplay="autoplay" loop="loop">
				<!-- <source src="http://cdn.moji.com/websrc/video/video621.mp4"> -->
				<source src="<%=path %>/video/video3.mp4" type="video/mp4" media=""></source>
			</video>
	</div>
	<div class="container">
		<div class="bg bg-blur"></div>
		<div class="content content-front">
	      <form class="form-signin" id="loginForm" method="post">
	        <h2 class="form-signin-heading"></h2>
	        <label for="inputEmail" class="sr-only">Email address</label>
	        <input type="email" id="inputEmail" name="emailAddress" class="form-control" placeholder="Email address" required autofocus>
	        <label for="inputPassword" class="sr-only">Password</label>
	        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
	        <input type="text" id="ipAddr" name="ip" hidden="hidden">
	        <input type="text" id="address" name="addr" hidden="hidden">
	        <div class="checkbox">
	          <label>
	            <input type="checkbox" value="remember-me"> Remember me
	          </label>
	        </div>
	        <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="javascript:login()">Sign in</button>
	      </form>
	     </div>
    </div> <!-- /container -->
 	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script type="text/javascript" src="<%=path%>/js/ie10-viewport-bug-workaround.js"></script>
    <script type="text/javascript" src="<%=path%>/js/tether.min.js"></script> 
    <script type="text/javascript" src="<%=path%>/js/bootstrap.min.js"></script>
</body>
</html>