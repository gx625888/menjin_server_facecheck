<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>门禁后台管理系统</title>
<script src="../script/jquery.js" type="text/javascript"></script>
<style>
body{
	overflow:hidden;
	}
*{
	margin:0;
	padding:0;
	font-family:Gotham, "Helvetica Neue", Helvetica, Arial, sans-serif;
	}
.header_box{
	background:#98bf42;
	}
.header{
	width:1000px;
	margin:auto;
	height:38px;
	}
.content_box{
	width:1000px;
	margin:40px auto;
	}
.banner{
	float:left;
	background:url(../images/img_bg.jpg) no-repeat;
	background-size:100%;
	width:600px;
	height:568px;
	}
.enter{
	float:left;
	width:398px;
	border:1px solid #e7e7e7;
	}
.logo{
	background:url(../images/sub_img.jpg) no-repeat;
	background-size:100%;
	width:313px;
	height:48px;
	margin:40px auto;
	}
.group{
	width:300px;
	margin:auto;
	}
.group span{
	display:block;
	margin-bottom:20px;
	}
.rememberPwdIcon{
	width:16px;
	height:16px;
	vertical-align:middle;
	}
.input_number{
	background:url(../images/icon.jpg) no-repeat #EBEBEB;
	padding-left:36px;
	height:42px;
	width:264px;
	margin-bottom:20px;
	display:block;
	-webkit-appearance:none;
	-moz-appearance:none;
	-o-appearance:none;
	border:none;
	}
.input_name{
	background:url(../images/icon2.jpg) no-repeat #EBEBEB;
	padding-left:36px;
	height:42px;
	width:264px;
	margin-bottom:20px;
	display:block;
	-webkit-appearance:none;
	-moz-appearance:none;
	-o-appearance:none;
	border:none;
	}
.input_password{
	background:url(../images/icon3.jpg) no-repeat #EBEBEB;
	padding-left:36px;
	height:42px;
	width:264px;
	margin-bottom:20px;
	display:block;
	-webkit-appearance:none;
	-moz-appearance:none;
	-o-appearance:none;
	border:none;
	}
.remember{
	font-size:13px;
	color:#323232;
	}
.btn-login{
	display:block;
	background:#98BF42;
	width:300px;
	line-height:42px;
	-webkit-appearance:none;
	-moz-appearance:none;
	-o-appearance:none;
	border:none;
	margin-bottom:50px;
	color:#fff;
	font-size:20px;
	margin-top:20px
	}
</style>
<%
Cookie[] cookies = request.getCookies();
Cookie cookie = null;
String mid = "";
String userId = "";
if (cookies != null) {
	for (int i = 0; i < cookies.length; i++) {
		cookie = cookies[i];
		if ("PC_MID".equals(cookie.getName())) {
			mid = cookie.getValue();
		}
		if ("userId".equals(cookie.getName())) {
			userId = cookie.getValue();
		}
	}
}
%>
<script type="text/javascript">
	function loadTopWindow() {
		if (window.top != null && window.top.document.URL != document.URL) {
			window.top.location = document.URL;
		}
	}
	var mid = '${param.mid}';
	$(function(){
		if(mid!=null&&mid!=""){
			/* $("#mid").show();
			$("#adminnomid").show();
			$("#storeID").show(); */
			$("#mid").val(mid);
		}else{
			/* $("#mid").hide();
			$("#adminnomid").hide();
			$("#storeID").hide(); */
		} 
		if($("#mid").val()!=""){
            $("[name = midischeck]:checkbox").attr("checked", true);
	}
    if($("#userId").val()!=""){
            $("[name = userIdischeck]:checkbox").attr("checked", true);
	}
		$('#Submit').click(function(){
			$('#loginForm').attr('action','../login/systemLogin.shtml');
			/* if(mid){
				$('#loginForm').attr('action','../login/systemLogin.shtml?mid=' + mid);
			}else{
				$('#loginForm').attr('action','../login/systemLogin.shtml');
			} */
			$('#loginForm').submit();
		});
	});

   function keyLogin(event)
   {
       if (event.keyCode == 13)
       {
           myform.Submit.click();
       }
   } 
</script>
</head>
<body onload="loadTopWindow()">
<div class="header_box">
	<div class="header"></div>
</div>
<div class="content_box">
	<div class="banner">
	
    </div>
	<div class="enter">
    	<div class="logo"></div>
    	<div class="group">
        <span>账户登录</span>
        <form name="myform" id="loginForm" action="../login/systemLogin.shtml" method="post">
      <%--  <input class="input_number" type="tel" name='mid' id="mid" value="<%=mid %>" placeholder="请输入您的商户号">--%>
        <input class="input_name" type="tel" name="userId" id="userId" value="<%=userId %>" placeholder="请输入您的账户">
        <input class="input_password" type="password" name="password" onkeydown="keyLogin(event)" placeholder="请输入您的账户密码">
         <c:if test="${msg != '' && msg != null}">
              <span class="login_txt"><font color="red">${msg}</font></span>
         </c:if> 
        <%--<input class="rememberPwdIcon" type="checkbox"  name="midischeck" id="midischeck" value='y'>&nbsp;
        <label class="remember" id="adminnomid">记住商户号</label>&nbsp;--%>
        <input class="rememberPwdIcon" type="checkbox" name="userIdischeck" value='y' id="userIdischeck">&nbsp;
        <label class="remember">记住账号</label>&nbsp;
        <input name="Submit" type="button" class="btn-login" id="Submit" value="登 录"> 
        </form>
        
        </div>
    </div>
</div>
</body>
</html>