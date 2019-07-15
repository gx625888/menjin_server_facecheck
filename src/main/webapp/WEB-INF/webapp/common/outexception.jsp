<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>操作失败！</title>
<style type="text/css">
<!--
.text1 {
    background-image: url(../images/common/error_line.jpg);
    background-repeat: repeat-x;
    background-position: top;
    font-size: 14px;
    line-height: 160%;
    color: #333333;
    text-align: left;
    vertical-align: top;
    font-weight: bold;
    padding-top: 8px;
    padding-right: 6px;
    padding-bottom: 8px;
    padding-left: 6px;
    height: 120px;
}
.text-tit {
    font-size: 16px;
    line-height: 160%;
    font-weight: bold;
    color: red;
    padding-left: 6px;
    padding-top: 4px;
    padding-bottom: 4px;
}
-->
<!--
.text2 {
    font-size: 12px;
    line-height: 160%;
    color: #333333;
    padding-top: 8px;
    padding-right: 8px;
    padding-bottom: 4px;
    padding-left: 8px;
}
-->
<!--
.end-l {
    background-image: url(../images/common/error_ag.jpg);
    background-repeat: no-repeat;
    background-position: left bottom;
    height: 38px;
    background-color: #E3EDFC;
}
.end-r {
    background-image: url(../images/common/error_ag2.jpg);
    background-repeat: no-repeat;
    background-position: right bottom;
    height: 38px;
    background-color: #E3EDFC;
    text-align: right;
    padding-right: 25px;
}
.error-btn {
    background-image: url(../images/common/error_btn_bg.jpg);
    background-repeat: repeat-x;
    background-position: center center;
    background-color: #FFFFFF;
    border: 1px ridge #4A84D8;
    /*padding-top: 3px;*/
    height: 21px;
    font-size: 12px;
    color: #333333;
}
a:link{font:12px;color:#274E9E;}
a:visited{font:12px;color:#274E9E;}
a:hover{font:12px;color:#2062E5;}
a:active{font:12px;color:#2062E5;}
-->
</style>
</head>

<body leftmargin="0" topmargin="0" style="background-color: #F8F9FA;">
<%
	Exception ex = (Exception) request.getAttribute("exception");
	ex.printStackTrace();
%>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center" valign="top"> 
      <table border="0" cellpadding="0" cellspacing="0"  style="width:60%;margin-top:110px;">
        <tr> 
          <td rowspan="2" align="left" valign="top" style="width:105px;"><img src="../images/common/error_icon.png" width="93" height="94"></td>
          <td class="text-tit" align="left">
				错误返回信息：
           </td>
        </tr>
        <tr> 
          <td class="text1">
          	<div style="white-space:normal; width:100%; ">
          	<%=ex.getMessage() %>
          </div>
          </td>
        </tr>
        <tr> 
          <td colspan="2" class="text2" align="left"><font color="#274E9E">注：</font>对不起，有错误产生。</td>
        </tr>
        <tr> 
          <td class="end-l">&nbsp; </td>
          <td class="end-r"><input name="subButton" id="subButton" type="button" class="error-btn" value="返回上一页" onclick="javascript:history.go(-1)"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
