<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()+":"+request.getServletPath()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' id='yarppWidgetCss-css'  href='http://www.powerxing.com/wp-content/plugins/yet-another-related-posts-plugin/style/widget.css?ver=4.5.13' type='text/css' media='all' />
<link rel='stylesheet' id='bootstrap-css'  href='http://cdn.staticfile.org/twitter-bootstrap/3.2.0/css/bootstrap.min.css' type='text/css' media='all' />
<link rel='stylesheet' id='font-awesome-css'  href='http://cdn.staticfile.org/font-awesome/4.2.0/css/font-awesome.min.css' type='text/css' media='all' />
<link rel='stylesheet' id='power-css'  href='http://www.powerxing.com/wp-content/themes/power/style.css?ver=2.1' type='text/css' media='all' />
<link rel='stylesheet' id='google-code-prettify-css'  href='http://www.powerxing.com/wp-content/themes/power/css/prettify.css?ver=2.1' type='text/css' media='all' />
<!-- css -->
<link rel="stylesheet" href="<%=path%>/css/bootstrap.css" />
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css" />
<!-- bootstrap button css -->
<link rel="stylesheet" href="<%=path%>/css/button.css" />
<link rel="stylesheet" href="<%=path%>/css/main.css" />
<link rel="shortcut icon" href="<%=path%>/images/162-S-Letter.ico"/>
<script type="text/javascript" src="<%=path%>/js/jquery-3.2.1.min.js"></script>
<!-- toastr消息提示插件需要在jquery引入后使用 -->
<script type="text/javascript" src="<%=path%>/js/attention/toastr.min.js"></script>
<link rel="stylesheet" href="<%=path%>/css/attention/toastr.min.css" />
<!-- 初始化toastr -->
<script type="text/javascript" src="<%=path%>/js/util/toastr_init.js"></script>
<script type="text/javascript" src="<%=path%>/js/login/cookie.js"></script>
<!-- bootstrap min js -->
<script type="text/javascript" src="<%=path%>/js/tether.min.js"></script> 
<script type="text/javascript" src="<%=path%>/js/bootstrap.min.js"></script>
<!-- google 前端代码高亮插件 -->
<link rel="stylesheet" href="http://www.powerxing.com/wp-content/themes/power/css/prettify.css?ver=2.1" />
<script src="http://www.powerxing.com/wp-content/themes/power/js/google-code-prettify/prettify.js?ver=2.1"></script>
<script type="text/javascript" src="<%=path%>/js/jquery-3.2.1.min.js"></script>
<title>Blog-S</title>
<script type="text/javascript">

var path='${pageContext.request.contextPath}';
/**
 * 回主页的方法
 */
 var index = function(){
	$.ajax({
		type:"GET",
		url:path+"/pageController/toIndex",
		dataType:"json",
		async:false,
		success:function(data){
			if(data.success){
				window.location.href=path+"/pageController/index";
			}else{
				toastr.error("主页跳转失败！");
				return;
			}
		},
		failure:function(){
			toastr.error("请求异常！");
		}
	});
}

/**
* 登出
*/
var loginout = function(){
	$.ajax({
		type:'GET',
		url:path+"/pageController/loginout",
		dataType:"json",
		async:false,
		success:function(data){
			if(data.success){
				window.location.href=path+"/pageController/loginForward";
			}else{
				toastr.error("登出失败！");
				return;
			}
		},
		failure:function(){
			toastr.error("登出系统失败！");
		}
	});
}

var toUserPage = function(){
	debugger;
	var user='${user.emailAddress}';
	//toastr.info(user);
	//未登录是请跳转登录
	if(user==null||user==""){
		window.location.href=path+"/pageController/loginForward";
	}else{
		$.ajax({
			type:'GET',
			url:path+'/pageController/toUserPage',
			dataType:"json",
			async:false,
			success:function(data){
				if(data.success){
					window.location.href=path+"/userController/userPage";
				}else{
					toastr.error("跳转失败！");
					return;
				}
			},
			failure:function(){
				toastr.error("网络错误！");
			}
		});
	}
}

//判断当前登录状态
<%-- window.onload = function(){
	//debugger; 
	//从cookie中拿到用户
	debugger;
	address = getCookieValue("userName");
	if(''==address){
		document.getElementById('loginStauts').innerHTML='<a href="<%=path%>/pageController/loginForward" style="color:white;text-decoration: none">登录</a>';
	}else{//这个地方需要判断如果cookie存的不是数据库表tb_user里存的用户，需要进行判断，显示登录二字
		for(var i=0;i<addressList.length;i++){
			//console.log(addressList[i]);
			var useraddress = addressList[i];
			if(address==useraddress){
				document.getElementById('loginStauts').innerText= useraddress;
				//只要有一个账户等于就进来了然后出循环
				//这个时候给loginMessage赋新的值，欢迎来到本blog
				loginMessage='2';
				return;
			}else{
				document.getElementById('loginStauts').innerHTML='<a href="<%=path%>/pageController/loginForward" style="color:white;text-decoration: none">登录</a>';
			}
		}
	}
} --%>
</script>
</head>
<body>
		<div class="div1 fixed-top" >
			<div class="div1_1">
				<strong><a id="blog_name" style="color: white;letter-spacing: 1px;text-decoration: none" href="javascript:void(0)" onclick="index()">S-ummerwindl</a></strong>
				<strong>
					<a id="blog_link" style="color: white;text-decoration: none;" href="https://weibo.com/u/5056032270" target="_blank">by yanl</a>
				</strong>
			</div>
			<div class="div1_2">
				<a style="color: white;text-decoration: none" id="loginStauts" href="javascript:viod(0)" onclick="toUserPage()">${user.emailAddress==null?'请登录':user.emailAddress }</a>
				<a style="color: white;text-decoration: none" id="loginout" href="javascript:void(0)" onclick="loginout()">注销</a>
			</div>
			<div class="div1_3">
				<form class="form-inline mt-2 mt-md-0" id="nav_bar_1">
					<input class="form-control mr-sm-2" type="text" placeholder="Search">
					<button class="btn btn-outline-success my-2 my-sm-0" 
					type="submit" 
					onclick="javascript:search()">Search</button>
				</form>
			</div>
		</div>

</body>
</html>