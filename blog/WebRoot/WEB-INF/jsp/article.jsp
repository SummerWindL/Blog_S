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
<link rel='stylesheet' href="<%=path %>/css/article.css" media='all' />
<title>${article.title }</title>
</head>
<body>
	<!-- navbar -->
	<c:import url="header.jsp"></c:import>
	
	<div class="col-sm-9 site-main" id="articleMain">
	<article id="post-338" class="post-338 post type-post status-publish format-standard hentry category-notes tag-mysql tag-sequelize">
	<header class="entry-header">
		<h1 class="entry-title">${article.title }</h1>

		<div class="entry-meta">
			<span class="date"><i class="fa fa-calendar"></i> <time datetime="2017-01-25T11:15:19+00:00">${article.createTime }</time> <span class="updated">(updated: <time class="updated" datetime="2017-03-03T10:27:45+00:00">${article.createTime }</time>)</span></span><span class="views" id="views"></span><span class="comments"><i class="fa fa-comments-o"></i> 6</span>		</div><!-- .entry-meta -->
	</header><!-- .entry-header -->

	<div class="entry-content">
		<%-- <div class="articleContent">
			${article.content }
		</div> --%>
		<h2>假装是标题</h2>
		<article>${article.content }</article>
		<p>我最近刚完成部门调岗，不再参与之前的IM项目，参与的第一个项目是一个内部的调查问卷系统。该调查问卷系统跟其他几个系统部署在同一个服务上，而当前问卷系统存在比较明显的性能问题，在问卷使用量较大时，容易导致整个问卷系统崩溃，进而也会导致其他部署在一块的系统一同崩溃。新部门刚刚接手此项目，交接方希望我们能尽快解决这个性能问题，毕竟年底很多人都需要用到问卷，不想系统再崩溃。</p>
		<p><span id="more-338"></span></p>
		<h2>性能问题</h2>
		<p>通过访问某一问卷可以发现，尽管题目只有10道，但获取问卷内容的时间长达 4s，并且多次访问，时间也都是稳定在 4s 多，这个显然是很严重的性能问题。一方面，获取问卷内容的时间不应该这么长，另一方面，对于同一问卷来说，只要问卷题目没修改过，那么每次获取到的内容都是一样的，那就可以加个缓存，多次访问时可改善性能，显然该问卷系统没有做这一方面的缓存。</p>
		<p><img src="http://cdn.powerxing.com/imgs/survey-performance-issue-01.png" alt="获取问卷内容的时间特别久" /><span class="img-caption">获取问卷内容的时间特别久</span></p>
		<p>由于才刚接手项目，也没有相关的设计文档，处于完全不熟悉的状态，但从上述情况来看，直觉地认为性能应该出现在数据库层面（问卷系统用的是 MySQL）。</p>
		<h2>定位性能问题&#8211;SQL语句</h2>
		<p>为了能更方便地定位问题所在，需要将获取问卷的 SQL 语句都打印出来。问卷系统使用了 Node.js ORM &#8212; <a href="http://sequelizejs.com" target="_blank">Sequelize</a> 来操作 MySQL，通过设定 logging 可以打印执行的每一条 SQL 语句。</p>
		<pre><code class="javascript">var sequelize = new Sequelize(config.database, {
		  logging: console.log,
		  ...
		});
		</code></pre>
		<p>将 SQL 输出后，就可以很明显的看到几个问题了：</p>
		<p>1&#46; 获取问卷的具体题目时，执行了 N(题目数) 条 SQL 语句，这些语句看上去并不需要单独获取。假如一条查询语句只需要 0.05s 就可以获取，那么 10 条查询语句就需要至少 0.5s 了，对性能的影响还是很大的。</p>
		<p><img src="http://cdn.powerxing.com/imgs/survey-performance-issue-02.png" alt="执行了N条SQL语句来获取N道题目" /><span class="img-caption">执行了N条SQL语句来获取N道题目</span></p>
		<p>2&#46; 获取问卷时，还获取了<strong>所有该问卷的回答内容</strong>，SQL 语句本身没问题，但问题在于，获取问卷内容需要这个数据么？感觉是多余的。</p>
		<p><img src="http://cdn.powerxing.com/imgs/survey-performance-issue-03.png" alt="获取问卷所有的回答内容" /><span class="img-caption">获取问卷所有的回答内容</span></p>
		<p>3&#46; 提交问卷时，也存在上述两个问题，因此提交问卷时也很慢。另外，还存在一个问题：问卷回答内容是通过 N 条 SQL 语句来写入的。这步看上去也是没有必要的，完全可以通过一条 insert 来完成。一次写入 10 条数据跟一次写入 1 条数据的时间几乎是一样的，但通过 N 条分别写入就很浪费时间了。</p>
		<p><img src="http://cdn.powerxing.com/imgs/survey-performance-issue-04.png" alt="执行了N条SQL语句来写入N道题目的回答" /><span class="img-caption">执行了N条SQL语句来写入N道题目的回答</span></p>
		<h2>复现问题</h2>
		<p>一开始在线下环境新建了一份与 <a href="https://survey.sankuai.com/#/35900/question" target="_blank">https://survey.sankuai.com/#/35900/question</a> 一模一样的问卷，但获取时间只要 0.6s 左右，无法复现问题。后来看了 SQL 语句才意识到跟回答问卷的数量是有关的，线上这个问卷有 4223 人填写过。</p>
		<p><img src="http://cdn.powerxing.com/imgs/survey-performance-issue-05.png" alt="4000多人填写过该问卷" /><span class="img-caption">4000多人填写过该问卷</span></p>
		<p><strong>线下写入 4300 个人的回答后，该问题得以复现，需要 2.5s 才能获取到问卷内容，而仅仅获取问卷所有的回答内容这一条 SQL 语句的执行时间就占到了接近 2s。</strong></p>
		<h2>解决方案</h2>
		<p>前面已经描述了解决思路了，现在从代码层面上来解决这些问题。</p>
		<p>第 1 个问题，显然是循环执行了 SQL，改成一次性获取就好了：</p>
		<pre><code class="javascript">// 获取问题所对应的答案
		// 原先会执行多条SQL语句, 但用一句SQL即可实现
		
		// 原来的代码
		// let answers =
		//   yield questions.map(question =&gt; {
		//     return models.survey.answer.find({
		//       where: {
		//         question_id  : question.id,
		//         committer_id : committerId
		//       }
		//     })
		//   })
		//
		// answers = answers.map(answer =&gt;
		//   getSequelizeInstanceDataValues(answer)).filter(answer =&gt; answer)
		//
		// questions.forEach((question, index) =&gt; {
		//   question.answer = answers[index] || null
		// })
		
		// 改进的代码，使用 findAll 替代 find
		let questionsIds = questions.map(question =&gt; question.id)
		let answers =
		  yield models.survey.answer.findAll({
		      where: {
		        committer_id : committerId,
		        question_id  : questionsIds
		      }
		    })
		
		answers = answers.map(answer =&gt;
		  getSequelizeInstanceDataValues(answer)).filter(answer =&gt; answer)
		
		questions.forEach((question, index) =&gt; {
		  question.answer = 
		    answers.find(answer =&gt; answer.question_id === question.id) || null
		})
		</code></pre>
		<p>第 2 个问题，看了下代码，获取问卷回答后只是统计了数量，那就完全没有必要将所有的回答内容都取回来，改成用 count(*) 完成统计就好了：</p>
		<pre><code class="javascript">// 获取问卷所有的回答
		
		// 原来的代码
		// const answers =
		//   yield models.survey.answer.findAll({
		//     where: {
		//       group_id: groupIds
		//     }
		//   })
		//
		// survey.hasAnswers = !!(answers &amp;&amp; answers.length)
		
		// 改进的代码，使用 Sequelize.fn('COUNT', ...) 完成统计
		const answers =
		  yield models.survey.answer.findAll({
		    attributes: [[Sequelize.fn('COUNT', Sequelize.col('id')), 'count']],
		    where: {
		      group_id: groupIds
		    }
		  })
		
		survey.hasAnswers = !!(answers &amp;&amp; getSequelizeInstanceDataValues(answers).count)
		</code></pre>
		<p>第 3 个问题，可能考虑到要获取到写入数据的自增 id，所以采用了逐条写入的方式（Sequelize 提供了 <code>bulkCreate()</code> 来批量写入，但 MySQL 难以做到返回批量写入数据的自增 id）。但如果是为了获取自增 id，可以在批量写入之后再重新查询一次，时间上是优于逐条写入 N 次的。</p>
		<pre><code class="javascript">// 写入问卷的回答, 这边使用了多条SQL语句来写入, 可以用单条SQL语句来写入
		
		// 原来的代码
		// yield answeredQuestions.map(question =&gt; {
		//   const answer = question.answer
		//   if (answer) {
		//     return models.survey.answer.create(answer).then(answer =&gt; {
		//       question.answer = getSequelizeInstanceDataValues(answer)
		//     })
		//   } else {
		//     return Promise.resolve(null)
		//   }
		// })
		
		// 改进的代码, 使用 bulkCreate 批量写入
		const creatingAnswers =
		answeredQuestions.map(question =&gt; question.answer).filter(answer =&gt; answer)
		
		yield models.survey.answer.bulkCreate(creatingAnswers)
		
		// 为了获得批量写入的数据所自动生成的id, 需要再发起一次查询
		const questionsIds = answeredQuestions.map(question =&gt; question.id)
		let answers =
		yield models.survey.answer.findAll({
		  where: {
		    committer_id : committerId,
		    question_id  : questionsIds
		  }
		})
		
		answers = answers.map(answer =&gt;
		getSequelizeInstanceDataValues(answer)).filter(answer =&gt; answer)
		
		answeredQuestions.map(question =&gt; {
		question.answer =
		  answers.find(answer =&gt; answer.question_id === question.id) || null
		})
		</code></pre>
		<h2>效果</h2>
		<p>线下的优化效果如下：</p>
		<table>
		<thead>
		<tr>
		<th align="left">测试项</th>
		<th align="right">优化前</th>
		<th align="center">优化后</th>
		</tr>
		</thead>
		<tbody>
		<tr>
		<td align="left">获取问卷内容</td>
		<td align="right">2.5s</td>
		<td align="center">0.4s</td>
		</tr>
		<tr>
		<td align="left">提交问卷回答</td>
		<td align="right">2.6s</td>
		<td align="center">0.5s</td>
		</tr>
		</tbody>
		</table>
		<p>可以看出，优化之后的效果非常明显，获取问卷、提交问卷的时间从超过 2s 稳定在了 0.4s 和 0.5s 左右。更重要的是，执行 SQL 语句的时间不再跟回答问卷的数量显著相关。</p>
		<h2>总结</h2>
		<p>总之，问卷系统主要存在如下三个 SQL 语句上的问题：</p>
		<ol>
		<li>为了获取问卷的题目，采用 N 条 SQL 语句逐条获取，导致获取的时间多了 N 倍，但使用一条查询语句替代即可。</li>
		<li>为了统计问卷回答的数量，将所有问卷回答内容都取回来了，导致数据量较大时耗费时间过长，但用一条 count(*) 语句替代即可。</li>
		<li>插入多条数据时使用了 N 条 insert，导致插入数据的时间多了 N 倍，但使用一条 insert 语句替代即可；</li>
		</ol>
		<p>单就这些问题来看，其实都算不上什么很复杂的问题，知晓常见的 MySQL/SQL 优化就能避免。可能还是<strong>因为使用了 ORM，在写代码的思路上跟直接使用 SQL 语句存在区别吧</strong>。</p>
		<p>因此，使用 ORM 在带来便利的同时，也意味着我们对最终生成的 SQL 语句失去了一定的掌控，那么就<strong>很有必要将最终生成的 SQL 语句打印出来，再人为检查一次</strong>，特别是要看下整个流程都执行了哪些 SQL 语句，有时 SQL 语句单独来看没有问题，但整个流程可能执行了太多 SQL 语句也会出现问题，避免产生此类失误影响性能。</p>
		</div><!-- .entry-content -->
		
			<footer class="entry-footer">
				<div class="entry-info">
					<span class="permalink"><i class="fa fa-external-link"></i> <a href="http://www.powerxing.com/survey-performance-issue/" title="调查问卷系统性能问题排查_Sequelize_MySQL">http://www.powerxing.com/survey-performance-issue/</a></span><span class="category"><i class="fa fa-folder-open-o"></i> <a href="http://www.powerxing.com/notes/" rel="category tag">笔记</a></span><span class="tags"><i class="fa fa-tags"></i><a href="http://www.powerxing.com/tag/mysql/" rel="tag">MySQL</a>, <a href="http://www.powerxing.com/tag/sequelize/" rel="tag">Sequelize</a></span>		</div>
				<div class='yarpp-related yarpp-related-none'>
		</div>
			</footer><!-- .entry-footer -->
		</article>
	</div>
	<script type='text/javascript' src='http://www.powerxing.com/wp-content/plugins/akismet/_inc/form.js?ver=3.3.2'></script>
	<link rel='stylesheet' id='yarppRelatedCss-css'  href='http://www.powerxing.com/wp-content/plugins/yet-another-related-posts-plugin/style/related.css?ver=4.5.13' type='text/css' media='all' />
	<script type='text/javascript' src='http://www.powerxing.com/wp-includes/js/comment-reply.min.js?ver=4.5.13'></script>
	<script type='text/javascript' src='http://www.powerxing.com/wp-content/themes/power/js/google-code-prettify/prettify.js?ver=2.1'></script>
	<script type='text/javascript' src='http://www.powerxing.com/wp-content/themes/power/js/power.js?ver=2.1.1'></script>
	<script type='text/javascript' src='http://www.powerxing.com/wp-includes/js/wp-embed.min.js?ver=4.5.13'></script>
    <script type="text/javascript" src="<%=path%>/js/tether.min.js"></script> 
    <script type="text/javascript" src="<%=path%>/js/bootstrap.min.js"></script>
</body>
</html>