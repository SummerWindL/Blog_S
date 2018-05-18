<%@ page language="java" contentType="text/html; charset=utf-8"
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
	<link rel="stylesheet" href="<%=path%>/editormd/css/editormd.preview.min.css" />
    <link rel="stylesheet" href="<%=path%>/editormd/css/editormd.css" />
    <!-- <div class="content" id="content">${faq.text }</div>   ${faq.text }内容为从服务器获取的HTML-->
	<div class="content" id="content">
		<hr>
		<p>
			rewtf<strong>erwefgfewfwsvc</strong>
		</p>
		<blockquote>
			<blockquote>
				<hr style="page-break-after: always;"
					class="page-break editormd-page-break" />
			</blockquote>
		</blockquote>
		<hr style="page-break-after: always;"
			class="page-break editormd-page-break" />
		<p>
			[========]<br>| | |ul<br>| —————— | —————— |<br>| | |<br>|
			| |
		</p>
		<h1 id="h1-erftewg">
			<a name="erftewg" class="reference-link"></a><span
				class="header-link octicon octicon-link"></span>erftewg
		</h1>
		<pre>
			<code>@RequestMapping(&quot;/activatemail&quot;) private String activatemail(String actiCode,String email){ logger.debug(&quot;ws-----activatemail----actiCode=&quot;+actiCode+&quot; email=&quot;+email); Person person = new Person(); person.setActiCode(actiCode); person.setMail(email); boolean isAc = this.personService.activatEmail(person); if(isAc){//激活成功，3秒跳转 return &quot;activateCode&quot;; }else{ //激活失败页面 return &quot;activateCode&quot;; } }jyghjmmkghmkghm </code>
		</pre>
		<p>```uluil,ui.,u.,uo.uokiulk</p>
	</div>

<script type="text/javascript" src="<%=path%>/js/jquery-3.2.1.min.js"></script>
    <script src="<%=path%>/editormd/lib/marked.min.js"></script>
    <script src="<%=path%>/editormd/lib/prettify.min.js"></script>
    <script src="<%=path%>/editormd/editormd.min.js"></script>
  <script type="text/javascript">
    editormd.markdownToHTML("content",{
          htmlDecode      : "style,script,iframe",  // you can filter tags decode
            emoji           : true,
            taskList        : true,
            tex             : true,  // 默认不解析
            flowChart       : true,  // 默认不解析
            sequenceDiagram : true,  // 默认不解析
        });
  </script>
<title>Insert title here</title>
</head>
<body>

</body>
</html>