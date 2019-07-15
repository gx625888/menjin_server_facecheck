<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="CH">
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="../bootstrap-3.3.5-dist/css/bootstrap.min.css" />
<title>top</title>
<style>
*{
	padding:0;
	margin:0;
	}
.top_container{
	color: #3e5706;
	text-shadow: 1px 1px 1px rgba(255,255,255, .2);
	
	/*background: url(../img/active.png) repeat-x;*/
	 box-shadow: 0 1px 0 #333333;
	background-color: #333333;
    background-image: -webkit-gradient(linear, 0 0%, 0 100%, from(#3F3F3F), to(#222222));
    background-image: -webkit-linear-gradient(top, #3F3F3F 0%, #222222 100%);
    background-image: -moz-linear-gradient(top, #3F3F3F 0%, #222222 100%);
	background-image: -ms-linear-gradient(top, #3F3F3F 0%, #222222 100%);
	background-image: -o-linear-gradient(top, #3F3F3F 0%, #222222 100%);
	background-image: linear-gradient(top, #3F3F3F 0%, #222222 100%);
    border-bottom: 3px solid #627d28;
	overflow:hidden;
	padding:0px 22px;
	padding-top:5px;
	height:64px;
	}
.left,.right{
	display:table-cell;
	vertical-align:middle;
	white-space:nowrap;
	word-break:break-all;
	word-wrap:break-word;
	}
.left{
	width:2000px;
	white-space:nowrap;
	}
.hd,.bd{
	display:table-cell;
	vertical-align:middle;
	white-space:nowrap;
	word-break:break-all;
	word-wrap:break-word;
	}
.hd{
	color:#fff;
	font-size:12px;
	padding-right:10px;
	}
</style>
<script language=JavaScript>
	function logout() {
		if (confirm("您确定要退出控制面板吗？"))
			top.location = "../login/systemOut.shtml";
		return false;
	}
	
	function logedit() {
			top.location = "../login/toSetPwd.shtml";
	}
	function showIndex(){
	window.parent.frames['main'].window.location.href = "../login/right.shtml?res=";
	}
</script>
</head>

<body>
<div class="top_container">
	<div class="left">
    	<img src="../bootstrap-3.3.5-dist/logo.png">
    </div>
    <div class="right">
    	<div class="hd">管理员：${sessionScope.ConsoleUser.name }您好!&nbsp;感谢登录使用</div>
        <p class="bd">
        <button type="button" class="btn btn-primary" onClick="logout();">退出</button>&nbsp;&nbsp;
        <button type="button" class="btn btn-default" onClick="logedit();">修改</button>
        <button type="button" class="btn btn-default" onClick="showIndex();">显示首页</button>


        </p>
    </div>
</div>
</body>
</html>
