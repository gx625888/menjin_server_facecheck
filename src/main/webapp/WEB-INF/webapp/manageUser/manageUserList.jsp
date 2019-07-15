<!-- 积分商城广告位列表 -->
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.lvhetech.com/privilege-taglib" prefix="p" %>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<title></title>
<link href="../dist/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="../styles/style.css" rel="stylesheet" type="text/css" />
<script src="../script/jquery.js" type="text/javascript"></script>
<script src="../script/mobileLayer/layer.m.js" type="text/javascript"></script>
<script src="../script/layer.min.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="../script/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
function toPage(pageNumber){
	 document.forms[0].action = "../managerUser/toManagerUserList.shtml?currentPage="+pageNumber;
	 document.forms[0].submit();
}

function queryList(){	
	 document.forms[0].action = "../managerUser/toManagerUserList.shtml";

	 document.forms[0].submit();
}

function delManagerUser(mid,name){
	var conf = "确认要删除账户\""+name+"\"和其下面所有的子账户吗？";
	if(confirm(conf)){
		window.location.href = "../managerUser/delManagerUser.shtml?mid=" + mid;
	}
}

$(document).ready(function(){	
	$("#xz").click(function(){
		window.location.href = "../managerUser/toAddOrEditManagerUser.shtml";
	});
});

function update(mid,userId) {
	window.location.href = "../managerUser/toAddOrEditManagerUser.shtml?mid=" + mid + "&userId=" + userId;
}

function change(mid,userId,status){
	if (status =='0'){
		status ='1';
	}else{
		status ='0';
	}
	if(confirm("确定操作吗？")){
		window.location.href = "../managerUser/changeStatus.shtml?mid="+mid+"&userId="+userId+"&status="+status;
	}
}

function queryTime(mid){
	var content ='';
	$("#trdiv").html("");
	$.ajax({
	    url: "../managerUser/queryTime.shtml" ,
	    type: "post",
	    data:{
	    	mid:mid,
	    },
	    dataType : "json",
	    success: function(data){
	    	var list = eval(data.list);
	     	if (null !=list && list.length>0){		
	    		$("#itid").show();	
	    		content+='有效期调整记录：';
	    		for (var i=0;i<list.length;i++){		
					content+='<span style="margin-left: 30px;">'+list[i].startTime+'~'+list[i].endTime+'</span>';
				}		
	    	}else{
	    		content +='尚无记录';
	    	}
	     	$("#itid").show();
	     	$("#trdiv").append(content); 
	    }
	});
}

function cse(){
	$("#itid").hide();
}

function auth(mid,userId){
	window.location.href="../managerUser/toAddOrEditManagerAuth.shtml?mid="+mid+"&userId="+userId+"&isEdit=1";
}

</script>
</head>
<body style="padding:0 20px;">
<table>
    	<thead>
        	<tr>
                <h3 class="page-header">商户信息列表</h3>
			</tr>
        </thead>
            <tr>
                <td>
 					<h4 class="sub-header">当前位置：商户信息列表</h4>
                </td>
         	</tr>
            <tr>
                <td>
                <form method="post">
                	<table class="table table-bordered table-striped">
                    	<tr>
                    	    <td>
	                    	    <div class="form-group hd w_primary">
									商户名：<input name="name" type="text" class="queryfield" style="width: 130px;" value="${mu.name}" />
								</div>
							</td>						
							<td>
	                    	    <div class="form-group hd w_primary">
									手机号：<input name="phone" type="text" class="queryfield" style="width: 130px;" value="${mu.phone}" />
								</div>
							</td>
							<td>
	                    	    <div class="form-group hd w_primary">
								      <input name="sTime" id="sTime" type="text" class="queryfield" style="width: 130px;" value="${mu.sTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'eTime\')}'})" readonly="readonly" placeholder="开始时间"/> 到
									  <input name="eTime" id="eTime" type="text" class="queryfield" style="width: 130px;" value="${mu.eTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sTime\')}'})" readonly="readonly" placeholder="结束时间"/>
								</div>
							</td>												
							
                        	<td>	                       
	                             <p class="bd primary"><button type="submit" class="btn btn-success" onclick="queryList()">查询</button></p>
	                             <p:auth privCodeList="managerUser_c">
	                                 <p class="ft primary"><button type="button" id="xz" class="btn btn-default">新增</button></p>
	                             </p:auth>
                            </td>
                        </tr>
                    </table>
                    </form>
                  </td>
              </tr>
                        
             <tr>
                   <td>
                    <h4 class="sub-header">商户信息</h4>
                  </td>
             </tr>
                        <tr>
                             <td>
                                <table class="table table-bordered table-striped" >
                                <tr class="tit">
									<td>序号</td>
									<td>商户号</td>
									<td>登录名</td>
									<td>商户名</td>
									<td>手机</td>
									<td>到期时间</td>
									<td>操作栏</td>
								</tr>
								
								<c:forEach varStatus="status" var="m" items="${pages.currList}">
									<tr class='${(status.index+1)%2==0?"ov":"" }'>
										<td>${status.index + 1 }</td>
										<td>
										  <a href="javascript:void(0)" style="color: #337ab7;text-decoration: none;" onclick="queryTime('${m.mid}')">${m.mid}</a>
										</td>
										<td>${m.userId}</td>
										<td>${m.name}</td>
										<td>${m.phone}</td>
										<td>${m.endTime}</td>														
										<td width="120">
										    <c:if test="${m.status == '0'}">
											   <p class="hd"><button type="button" class="btn btn-sm btn-success" style="background-color: red;border-color:red;" onclick="change('${m.mid}','${m.userId}','${m.status}')">终止</button>&nbsp;</p>
											</c:if>
											<c:if test="${m.status != '0'}">
											   <p class="hd"><button type="button" class="btn btn-sm btn-success"  onclick="change('${m.mid}','${m.userId}','${m.status}')">开启</button>&nbsp;</p>
											</c:if>
										    <p:auth privCodeList="managerUser_d">
                                              <p class="hd"><button type="button" class="btn btn-sm btn-success" onclick="delManagerUser('${m.mid}','${m.name}')">删除</button>&nbsp;</p>
											</p:auth>	
											<p:auth privCodeList="managerUser_u">
                                              <p class="hd"><button type="button" class="btn btn-sm btn-success" onclick="update('${m.mid}','${m.userId}')">更新</button>&nbsp;</p>
											</p:auth>											
											<p class="hd"><button type="button" class="btn btn-sm btn-success" onclick="auth('${m.mid}','${m.userId}')">权限</button>&nbsp;</p>	
										</td>
									</tr>
									</c:forEach>
									<tr id="itid" style="display: none;">
									   <td colspan="6" id="trdiv" align="center"></td>
									   <td align="center"><p class="hd"><button type="button" class="btn btn-sm btn-success" onclick="cse()">关闭</button></p></td>									 
									</tr>
                                </table>
                               </td>
                          </tr>
                   <tr>
				<td>
					<table>
						<tr>
							<td>
								<ul class="pager">
									当前是第${pages.currentPage}页
									<c:if test="${pages.currentPage!=1}">
										<li><a style="cursor: pointer;" onclick="toPage(1)">首页</a></li>
										<li><a style="cursor: pointer;" onclick="toPage('${pages.priviousPage}')">上一页</a></li>
									</c:if>
									<c:if test="${pages.totalPage>pages.currentPage}">
										<li><a style="cursor: pointer;" onclick="toPage('${pages.nextPage}')">下一页</a></li>
										<li><a style="cursor: pointer;" onclick="toPage('${pages.totalPage-1}')">末页</a></li>
									</c:if>
									共${pages.totalCount}条&nbsp; 共${pages.totalPage}页
								</ul>
							</td>
						</tr>
					</table>
				</td>
			</tr>
                </td>
            </tr>
	</table>
</body>
</html>