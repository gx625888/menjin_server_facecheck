<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.wicp.console.vo.UserVO"%>
<html>
<head>
<meta http-equiv=Content-Type content=text/html;charset=UTF-8>
<title>管理页面</title>
<link href="../styles/skin.css" rel="stylesheet" type="text/css">
<script language=JavaScript>
	function logout() {
		if (confirm("您确定要退出控制面板吗？"))
			top.location = "../login/systemOut.shtml";
		return false;
	}
</script>
<base target="main">
</head>
<body leftmargin="0" topmargin="0">
	<table width="100%" height="64" border="0" cellpadding="0" cellspacing="0" class="admin_topbg">
		<tr>
			<td width="50%" height="64"><img src="../images/system/logo1.png" width="262" height="64"></td>
			<td width="50%" valign="top">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="38" class="admin_txt" style='width:80%' align="right">管理员：${sessionScope.ConsoleUser.name }<b></b>
							您好,感谢登录使用！
						</td>
						<td ><a href="#" target="_self"
							onClick="logout();"><img src="../images/system/out.gif" alt="安全退出" width="46" height="20" border="0"></a></td>
						<td >&nbsp;<a style='color:white;text-decoration: none;font-size: 14px;' href="../login/toSetPwd.shtml" target="main">修改密码</a>
						</td>
					</tr>
					<tr>
						<td height="19" colspan="3">&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
