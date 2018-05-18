<%@ page language="java" import="com.sunyard.util.*"  contentType="text/html; charset=utf-8"
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
<title>Blog-S</title>
<!-- css -->
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
<script type="text/javascript">
	var toastr;
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
	var emailAddressList = '${emailAddressList}';
	
	//第一种方法，后台传json格式的对象过来，从json中取emailAddress，不安全，所有信息都可以看到
	//var json = ${json};
	//第二种方法，后台重新组装一个List<String>，遍历List<TbUser>获取里面的emailAddress从新组装成新的list，在转换成String传到前端用el表达式接
	var addressList = emailAddressList.split(",");
	
	//addressList = addressList.subList(0,1);
	//addressList = addressList.subList(lasstIndexOf(']'));
	/* var addressList = JSON.parse(emailAddressList); */
	//console.log(addressList);
	//console.log(json);
	console.log("emailAddressList"+emailAddressList);
	console.log(addressList);
	//遍历json
	
	var loginMessage = '${requestScope.errorMessage}';//返回的错误消息
	var address ='';
	//判断当前登录状态
	window.onload = function(){
		//debugger; 
		//从cookie中拿到用户
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
			//遍历这个json,emailAddress的值
			<%-- for(var i=0;i<json.length;i++){
				//console.log(json[i].emailAddress);
				var useraddress = json[i].emailAddress;
				if(address==useraddress){
					document.getElementById('loginStauts').innerText= useraddress;
					//只要有一个账户等于就进来了然后出循环
					//这个时候给loginMessage赋新的值，欢迎来到本blog
					loginMessage='2';
					return;
				}else{
					document.getElementById('loginStauts').innerHTML='<a href="<%=path%>/pageController/loginForward" style="color:white;text-decoration: none">登录</a>';
				}
			} --%>
			//遍历userlist
			<%-- for(var i=0;i<addressList.length;i++){
				var useraddress = addressList[i];
				if(address==useraddress){
					document.getElementById('loginStauts').innerText= useraddress;
				}else{
					document.getElementById('loginStauts').innerHTML='<a href="<%=path%>/pageController/loginForward" style="color:white;text-decoration: none">登录</a>';
				}
			} --%>
		}
		
	}
	$(function(){
		if(null!=loginMessage && ''!=loginMessage){
			if(loginMessage=='1'){
				toastr.info("当前未登录，您可以尝试登录！");
			}else if(loginMessage=='2'){
				toastr.info("欢迎来到本blog");
			}else{
				toastr.error(loginMessage);
			}
		}
	})
	
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
	
	/**
	 * 回主页的方法
	 */
	 var edit = function(){
		$.ajax({
			type:"GET",
			//请求页面
			url:path+"/pageController/toEditpage",
			dataType:"json",
			async:false,
			success:function(data){
				if(data.success){
					//成功的话就跳转到eidt页面
					window.location.href=path+"/pageController/edit";
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
	
	var articlePage = function(articleId){
		$.ajax({
			type:"post",
			url:path+"/articleController/toArticlePage",
			dataType:"json",
			success:function(data){
				if(data.success){
					window.location.href=path+"/articleController/article?articleId="+articleId;
				}else{
					toastr.error("查看文章详情失败！");
					return;
				}
			},
			failure:function(data){
				toastr.error("请求异常！");
			}
		});
	}
	
	$(function () { $('#collapseThree').collapse('toggle')});
</script>
</head>
<body>
		<div class="div1 fixed-top" >
			<div class="div1_1">
				<strong><a id="blog_name" style="color: white;letter-spacing: 1px">S-ummerwindl</a></strong>
				<strong>
					<a id="blog_link" style="color: white;text-decoration: none;" href="https://weibo.com/u/5056032270" target="_blank">by yanl</a>
				</strong>
			</div>
			<div class="div1_2">
				<a style="color: white;" id="loginStauts"></a>
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
		<div class="div2">
			<!-- 文章发布按钮，跳转至/edit页面 -->	
			<a id="postArticle" href="javascript:void(0)" onclick="edit()" class="button button-glow button-rounded button-highlight">发表文章</a>
			<!-- bootstrap panel -->
			<!-- blog面板 -->
			<div class="panel-group" id="accordion">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" 
							   href="#collapseThree">
								Blog-message
							</a>
						</h4>
					</div>
					<div id="collapseThree" class="panel-collapse collapse">
						<div class="panel-body" style="text-decoration: none;">
						<a><strong>当前时间：</strong></a>
							<%
								out.println(DateUtil.getCurrentDate());
							%>
						<p>欢迎您：${user.emailAddress }</p>
						</div>
					</div>
				</div>
			</div>
		  <!-- 按钮组 -->
		    <!-- <div class="button-group">
			    <button type="button" class="button button-primary">Option 1</button>
			    <button type="button" class="button button-primary">Option 2</button>
			    <button type="button" class="button button-primary">Option 3</button>
			 
			    DROPDOWN MENU
			    <span class="button-dropdown button-dropdown-primary" data-buttons="dropdown">
			      <a href="#" class="button button-primary"> Select Me <i class="fa fa-caret-down"></i></a>
			 
			      <ul class="button-dropdown-list is-below">
			        <li><a href="http://www.bootcss.com/">Option Link 1</a></li>
			        <li><a href="http://www.bootcss.com/">Option Link 2</a></li>
			        <li class="button-dropdown-divider"><a href="#">Option Link 3</a></li>
			      </ul>
			    </span>
			</div> -->
		  <div class="div2_1"> 
				<div class="div2_1_1">
					<ul class="ul_1">
						<li>Table of Contents</li>
						<div class="ul_1_d label label-defult">Article</div>
					</ul>
					<!--
                    	作者：854757115@qq.com
                    	时间：2018-01-25
                    	描述：文章标题+部分内容显示
                    
					<p>&nbsp;&nbsp;This is a article.</p>  
					<p>&nbsp;&nbsp;This is another paragraph.</p>  
					<button>在每个 p 元素的结尾添加内容</button>
					-->
					<div class="content">
						<c:set var="totalArticles" value="${totalArticles}"/>
					    <c:set var="articlePerPage" value="${articlePerPage}"/>
					    <c:set var="totalPages" value="${totalPages}"/>
					    <c:set var="beginIndex" value="${beginIndex}"/>
					    <c:set var="endIndex" value="${endIndex}"/>
					    <c:set var="page" value="${page}"/>
					    <c:set var="currentPageArticle" value="${articles.subList(beginIndex,endIndex)}"/>
 						<!--<table class="table table-hover table-responsive table-striped table-bordered">
						        <thead>
						        <tr>
						            <td>文章编号</td>
						            <td>标题</td>
						            <td>文章内容</td>
						            <td>创建日期</td>
						            <td>作者</td>
						        </tr>
						        </thead>
						        <tbody> -->
						        <c:forEach var="article" items="${currentPageArticle}">
						        	<a name="articleId" hidden="hidden">${article.id }</a>
						        	<article id="post-286" class="post-286 post type-post status-publish format-standard hentry category-notes tag-web">
										<header class="entry-header">
											<h2 class="entry-title"><a href="javascript:void(0)" onclick="articlePage('${article.id}')" rel="bookmark">${article.title}</a></h2>
										</header><!-- .entry-header -->
									
										<div class="entry-content">
											<div class="entry-content">
											<p>${article.content }</p>
									<p> <a href="http://www.powerxing.com/mac-chrome-scroll-network-pending/#more-286" class="more-link" target="_blank">继续阅读 <i class="fa fa-angle-double-right"></i></a></p>
										</div><!-- .entry-content -->
										
										<footer class="entry-footer">
											<div class="entry-meta">
												<i class="fa fa-calendar"></i> <time datetime="2016-11-03T16:44:41+00:00">${article.createTime}</time>								<span class="cats">
													<i class="fa fa-folder-open-o"></i> <a href="http://www.powerxing.com/notes/" rel="category tag">笔记</a>			</span>
												
															<span class="tags">
													<i class="fa fa-tags"></i> <a href="http://www.powerxing.com/tag/web%e5%89%8d%e7%ab%af/" rel="tag">Web前端</a>			</span>
														
											
											</div><!-- .entry-meta -->
										</footer>
									</article><!-- #post-## -->
						        </c:forEach>
						            <%-- <%-- <tr>
						                <td>${article.id}</td>
						                <td>${article.title}</td>
						                <td>${article.content}</td>
						                <td>${article.createTime}</td>
						                <td>${article.author}</td>
						            </tr> --%> 
						           <!--  <div></div>
						        
						        </tbody>
						    </table> -->
						
						
						    <div class="text-center">
						        <nav>
						            <ul class="pagination pull-left pagination-detail pagination-sm">
						                <li class="page-item"><a class="page-link" href="<c:url value="/pageController/index?page=1"/>">首页</a></li>
						                <li class="page-item"><a class="page-link" href="<c:url value="/pageController/index?page=${page-1>1?page-1:1}"/>">&laquo;</a></li>
						
						                <c:forEach begin="1" end="${totalPages}" varStatus="loop">
						                    <c:set var="active" value="${loop.index==page?'active':''}"/>
						                    <li class="${active} page-item"><a class="page-link" 
						                            href="<c:url value="/pageController/index?page=${loop.index}"/>">${loop.index}</a>
						                    </li>
						                </c:forEach>
						                <li class="page-item">
						                    <a class="page-link" href="<c:url value="/pageController/index?page=${page+1<totalPages?page+1:totalPages}"/>">&raquo;</a>
						                </li>
						                <li class="page-item"><a class="page-link" href="<c:url value="/pageController/index?page=${totalPages}"/>">尾页</a></li>
						            </ul>
						        </nav>
						    </div>
					</div>
					
				</div>
			</div>
		</div>
		<footer class="footer">
			<div class="container" style="color: white;">
				<ul class="ul_2">
					<li>©2018 summerwindl</li>
					<div>Tweet</div>
				</ul>
			</div>
			
		</footer>
	</body>
</html>