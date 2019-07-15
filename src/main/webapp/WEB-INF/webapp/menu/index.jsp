<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>管理中心</title>
<meta http-equiv=Content-Type content=textml;charset=UTF-8>
</head>

<frameset rows="64,*" frameborder="NO" border="0" framespacing="0">
	<frame src="../login/top.shtml" noresize="noresize" frameborder="NO" name="topFrame" scrolling="no" marginwidth="0" marginheight="0" target="main"/>
     <frameset cols="220,*" rows="900,*" id="frame">
		<frame src="../login/left.shtml" name="leftFrame" noresize="noresize" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" target="main" style="box-shadow: 0 10px 20px #333;"/>
		<frame src="../login/right.shtml?res=${res}" name="main" marginwidth="0" marginheight="0" frameborder="0" scrolling="auto" target="_self" />
	</frameset> 
		<body></body>
</frameset>
</html>
