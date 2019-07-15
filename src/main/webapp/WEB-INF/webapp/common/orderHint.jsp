<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.lvhetech.com/privilege-taglib" prefix="p" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单提示配置</title>
<link href="../styles/skin.css" rel="stylesheet" type="text/css" />
<script src="../script/jquery.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#saveBtn').click(function(){
		$("form").attr("action","../common/updHint.shtml");
		$("form").submit();
	});
});
</script>

</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td width="17" valign="top"
				background="../images/system/mail_leftbg.gif"><img
				src="../images/system//left-top-right.gif" width="17" height="29" />
			</td>
			<td valign="top" background="../images/system/content-bg.gif">
				<table width="100%" height="31" border="0" cellpadding="0" cellspacing="0"
					class="left_topbg" id="table2">
					<tr>
						<td height="31"><div class="titlebt">订单提示配置</div></td>
					</tr>
				</table>
			</td>
			<td width="16" valign="top"
				background="../images/system//mail_rightbg.gif"><img
				src="../images/system//nav-right-bg.gif" width="16" height="29" />
			</td>
		</tr>
		<tr>
			<td valign="middle" background="../images/system//mail_leftbg.gif">&nbsp;</td>
			<td valign="top" bgcolor="#F7F8F9">
				<form method="post" enctype="multipart/form-data">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr>
							<td valign="top" colspan="4">
								<table width="98%" border="0"
									align="center" cellpadding="0" cellspacing="0">
									<tr>
										<td class="left_txt">当前位置：订单提示配置</td>
									</tr>
									<tr>
										<td height="20"><table width="100%" height="1" border="0"
												cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
												<tr>
													<td></td>
												</tr>
											</table></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td valign="top" colspan="4">
								<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
								    <input type="hidden" name="mid" value="${mid}">
									   <tr>
											<td width="20%" height="30" align="left" class="left_txt2" style="padding-bottom: 10px;">
												是否提示：
											 </td>
										</tr>
										<tr>
											<td width="20%" height="30" align="left" class="left_txt2" style="padding-bottom: 10px;">
												否<input type="radio" name="state" <c:if test="${value =='0'}">checked="checked"</c:if> value="0">
											            是<input type="radio" name="state" <c:if test="${value =='1'}">checked="checked"</c:if> value="1">
											</td>										
										</tr>								
									  <tr>
										<td width="20%" height="30" align="left" class="left_txt2">
											<input type="button" value="保存" id="saveBtn" name="saveBtn" class="qued"/>
										</td>
									</tr>
								 </table>
							</td>
						</tr>
					</table>
				</form>
			</td>
			<td background="../images/system//mail_rightbg.gif">&nbsp;</td>
		</tr>
		<tr>
			<td valign="bottom" background="../images/system//mail_leftbg.gif"><img
				src="../images/system//buttom_left2.gif" width="17" height="17" /></td>
			<td background="../images/system//buttom_bgs.gif"><img
				src="../images/system//buttom_bgs.gif" width="17" height="17"></td>
			<td valign="bottom" background="../images/system//mail_rightbg.gif"><img
				src="../images/system//buttom_right2.gif" width="16" height="17" /></td>
		</tr>
	</table>
</body>
</html>