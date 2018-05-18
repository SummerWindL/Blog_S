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
<link rel='stylesheet' id='yarppWidgetCss-css'  href='http://www.powerxing.com/wp-content/plugins/yet-another-related-posts-plugin/style/widget.css?ver=4.5.13' type='text/css' media='all' />
<link rel='stylesheet' id='bootstrap-css'  href='http://cdn.staticfile.org/twitter-bootstrap/3.2.0/css/bootstrap.min.css' type='text/css' media='all' />
<link rel='stylesheet' id='font-awesome-css'  href='http://cdn.staticfile.org/font-awesome/4.2.0/css/font-awesome.min.css' type='text/css' media='all' />
<link rel='stylesheet' id='power-css'  href='http://www.powerxing.com/wp-content/themes/power/style.css?ver=2.1' type='text/css' media='all' />
<link rel='stylesheet' id='google-code-prettify-css'  href='http://www.powerxing.com/wp-content/themes/power/css/prettify.css?ver=2.1' type='text/css' media='all' />
<!-- css -->

	<link rel="stylesheet" href="<%=path%>/css/bootstrap.css" />
	<link rel="stylesheet" href="<%=path%>/css/main.css" />
	<link rel="shortcut icon" href="<%=path%>/images/162-S-Letter.ico"/>
	<script type="text/javascript" src="<%=path%>/js/jquery-3.3.1.js"></script>
	<!-- toastr消息提示插件需要在jquery引入后使用 -->
	<script type="text/javascript" src="<%=path%>/js/attention/toastr.min.js"></script>
	<link rel="stylesheet" href="<%=path%>/css/attention/toastr.min.css" />
	<!-- 初始化toastr -->
	<script type="text/javascript" src="<%=path%>/js/util/toastr_init.js"></script>
	<script type="text/javascript" src="<%=path%>/js/login/cookie.js"></script>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="shortcut icon" href="<%=path%>/images/162-S-Letter.ico"/>
	<link rel="stylesheet" href="<%=path%>/css/editor/editor.css" />
	<link href="<%=path%>/editormd/css/editormd.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=path%>/editormd/editormd.min.js"></script>
    <script type="text/javascript">
    var testEditor;
    var toastr;
    var editErrorMessage='${errorMessage}';
    var path = '${pageContext.request.contextPath}';
	toastr.options = {  
       closeButton: false,  
       debug: false,  
       progressBar: false,  
       positionClass: "toast-bottom-center",  
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
    
    testEditor=$(function() {
       editormd("test-editormd", {
            width   : "90%",
            height  : 640,
            //markdown : md,
            codeFold : true,
            syncScrolling : "single",
            //你的lib目录的路径
            path    : path+"/editormd/lib/",
            imageUpload: false,//关闭图片上传功能
           /*  theme: "dark",//工具栏主题
            previewTheme: "dark",//预览主题
            editorTheme: "pastel-on-dark",//编辑主题 */
            emoji: false,
            taskList: true, 
            tocm: true,         // Using [TOCM]
            tex: true,                   // 开启科学公式TeX语言支持，默认关闭
            flowChart: true,             // 开启流程图支持，默认关闭
            sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
           //这个配置在simple.html中并没有，但是为了能够提交表单，使用这个配置可以让构造出来的HTML代码直接在第二个隐藏的textarea域中，方便post提交表单。
            saveHTMLToTextarea : false          
       });

   }); 
    
    function postArticle(){
    	
    	//document.getElementById("editorformID").submit();
    	//debugger;
    	//标题
    	var title = $("#articleTitleInput").val();
    	if(title==''||null==title){
    		toastr.error("标题不能为空！");
    		return;
    	}
    	//内容 -- 第一个textarea
    	var contentText = $("#editormd").val();
    	//内容 -- 第二个textarea
    	var contentHtml = $("#editorhtml").val();
    	if(contentText==''||null==contentText||contentHtml==''||null==contentHtml){
    		toastr.warning("请注意，内容为空！");
    	}
    	submitForm();
    }

    function submitForm() {
    	var form = document.getElementById('editorformID');
 	   form.action = path+'/editormdController/saveArticle';
 	   /* debugger; */
 	   form.submit();
	}
    
    $(function(){
    	/* debugger; */
 	   if(null!=editErrorMessage && ''!=editErrorMessage){
 		   toastr.error(editErrorMessage);
 	   }
    })
  </script>
	<title>EditorMD</title>
</head>
<body>
	<!-- navbar -->
	<c:import url="header.jsp"></c:import>
    <!-- editormd start -->
    <form class="editorform" method="post" id="editorformID">
    <!-- 发布按钮 -->
   	 <div class="post">
		<button class="btn btn-sm btn-primary btn-block" type="submit" onclick="javascript:postArticle()">发布</button>
	 </div>
	    <!-- 标题栏 input -->
	    <div class="articleTitle">
	    	<a id="title">标题：
	    	<input type="text" id="articleTitleInput" name="articletitle">
	    	</a>
	    </div>
	    	<!-- <pre class="prettyprint" >class Voila {
				public:
				  // Voila
				  static const string VOILA = "Voila";
				
				  // will not interfere with embedded <a href="#voila2">tags</a>.
				}
			</pre>
			
			<pre class="prettyprint">select 
			    user_id, count(distinct brand_id) as total
			group by
			    user_id
			</pre> -->
		    <div class="editormd" id="test-editormd">
		    <textarea class="editormd-markdown-textarea" name="content" id="editormd"></textarea>
		   	<!--  第二个隐藏文本域，用来构造生成的HTML代码，方便表单POST提交，这里的name可以任意取，后台接受时以这个name键为准
		    html textarea 需要开启配置项 saveHTMLToTextarea == true -->
		    <textarea class="editormd-html-textarea" name="editorhtml" id="editorhtml"></textarea>       
		    </div>
    </form>
    
    
    <!-- footer -->
    <c:import url="footer.jsp"></c:import>
    <script type="text/javascript" src="<%=path%>/js/tether.min.js"></script> 
    <script type="text/javascript" src="<%=path%>/js/bootstrap.min.js"></script>
</body>
</html>