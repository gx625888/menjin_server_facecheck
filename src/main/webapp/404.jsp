<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../styles/basic.css" rel="stylesheet" type="text/css">
</head>
<body>
	<%
		Exception ex = (Exception) request.getAttribute("exception");
		ex.printStackTrace();
	%>
	<div class="mcontent">
		<form method="post">
			<div class="tfmcontent">
				<div class="nav">
					<div class="navtitle">
						<span class="">当前页面：</span> <span>错误提示页面</span>
					</div>
				</div>
				<div class="tableCon">
					<div style="background: gray;font-size: 20px;">
						错误编码：400
					</div>
					<div style="font-size: 20px;">
						错误内容：对不起，页面丢失了
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
