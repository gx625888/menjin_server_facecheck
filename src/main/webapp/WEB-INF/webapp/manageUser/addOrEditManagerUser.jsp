<!-- 积分商城广告位添加或修改 -->
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../dist/css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="../styles/style.css" rel="stylesheet" type="text/css" />
<link href="../styles/docs.css" rel="stylesheet" type="text/css" />
<script src="../script/jquery.js" type="text/javascript"></script>
<script src="../script/mobileLayer/layer.m.js" type="text/javascript"></script>
<script src="../script/layer.min.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript" src="../script/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.tijiao {
	background: #e3e3e3;
	border: 1px solid #bbb;
	border-radius: 3px;
	color: #333;
	font: bold 12px/1 "helvetica neue", helvetica, arial, sans-serif;
	padding: 8px 0 9px;
	text-align: center;
	text-shadow: 0 1px 0 #fff;
	width: 90px;
	margin-left: 30px;
	margin-top: 10px;
	margin-bottom: 20px;
}

.tijiao:hover {
	background: #d9d9d9;
	-webkit-box-shadow: inset 0 0 1px 1px #eaeaea;
	box-shadow: inset 0 0 1px 1px #eaeaea;
	color: #222;
	cursor: pointer;
}
.error {
	color: red;
}
</style> 
</head>
<body style="padding:0 20px;">
	<table>
    	<thead>
        	<tr>
                <td><h3 class="page-header">商户信息管理</h3></td>
			</tr>
        </thead>
        <tbody>
            <tr>
                <td>
 					<h4 class="sub-header">当前位置：商户信息编辑</h4>
                </td>
         	</tr>
            <tr>
                <td>
                  <form method="post" action="" enctype="multipart/form-data" class="bs-docs-example">
                   	<table>
                   	      <c:if test="${empty mu.mid}">
	                       	  <tr>
	                           	<td>
	                               	<div class="box">
	                                    <div class="hd name"><label ><font color="red">*</font>&nbsp;登录名：</label></div>
	                                    <div class="form-group bd">                                       
										   <input class="form-control" id="userId" name="userId" type="text" value="${mu.userId}" maxlength="50"/>
	                                    </div>
	                                </div>
	                             </td>
	                           </tr>
                           </c:if> 
                           
                           <tr>
                           	<td>
                               	<div class="box">
                                    <div class="hd name"><label><font color="red">*</font>&nbsp;商户名称：</label></div>
                                    <div class="form-group bd">                                       
									   <input class="form-control" id="name" name="name" type="text" value="${mu.name}" maxlength="50"/>
                                    </div>
                                </div>
                             </td>
                           </tr>
                           
                           <tr>
                           	<td>
                               	<div class="box">
                                    <div class="hd name"><label><font color="red">*</font>&nbsp;电话：</label></div>
                                    <div class="form-group bd">                                       
									   <input class="form-control" id="phone" name="phone" type="text" value="${mu.phone}" maxlength="32"/>
                                    </div>
                                </div>
                             </td>
                           </tr>
                           
                           <tr>
                           	<td>
                               	<div class="box">
                                    <div class="hd name"><label><font color="red">*</font>&nbsp;地址：</label></div>
                                    <div class="form-group bd">                                       							  
									   <textarea rows="2" cols="22"  class="form-control" id="address" name="address" type="text" value="${mu.address}" maxlength="100" autocomplete="off" style="resize: none;">${mu.address}</textarea>
                                    </div>
                                </div>
                             </td>
                           </tr>
                           
                           <tr>
                           	<td>
                               	<div class="box">
                                    <div class="hd name"><label><font color="red">*</font>&nbsp;会员注册形式：</label></div>
                                    <div class="form-group bd">   
                                       <input type="radio" name="spvalue" <c:if test="${spvalue !='0'}">checked="checked"</c:if> value="1" style="width: 30px;">需要验证手机                                    							  
									   <input type="radio" name="spvalue" <c:if test="${spvalue =='0'}">checked="checked"</c:if> value="0" style="width: 30px;">无需验证手机		    							  
                                    </div>
                                </div> 
                             </td>
                           </tr>
                              
						  <tr>
						   <td>
							  <div class="box">
	                              <div class="hd name"><label><font color="red">*</font>&nbsp;餐饮经营模式：</label></div>
	                           	  <div class="form-group bd">	                           	    	                           	      
	                           	      <input type="checkbox" id="waimai" name="dataCheckBox" <c:if test="${managementMode.takeaway_flag == '1'}">checked="checked"</c:if>  value="waimai" style="width: 30px;">外卖
	                           	      <input type="checkbox" id="tangshi" name="dataCheckBox" <c:if test="${managementMode.dinner_flag == '1'}">checked="checked"</c:if>  value="tangshi" style="width: 30px;">堂食
	                           	      <input type="checkbox" id="kuaican" name="dataCheckBox" <c:if test="${managementMode.fast_food_flag == '1'}">checked="checked"</c:if>  value="kuaican" style="width: 30px;">快餐                              	                         	                          
	                             </div>
	                           </div>
							</td> 
						  </tr>	
						  
						  <tr>
						    <td>
						       <div class="box">
						           <div class="hd name"><label><font color="red">*</font>&nbsp;页面模版：</label></div>
		                    	   <div class="form-group bd">
										<select name="catalog" id="catalog" class="form-control" style="width: 176px;">											                                              
                                           <option value="lvhe_simple" <c:if test="${catalog=='lvhe_simple'}">selected="selected"</c:if>>绿和产品化默认</option>
									 	   <option value="lvhe_across" <c:if test="${catalog=='lvhe_across'}">selected="selected"</c:if>>绿和产品化横版</option>         
									       <option value="jfxlx"  <c:if test="${catalog=='jfxlx'}">selected="selected"</c:if>>卷福小龙虾</option>
									       <option value="dpxc" <c:if test="${catalog=='dpxc'}">selected="selected"</c:if> >大牌小厨</option>
									       <option value="wkwk" <c:if test="${catalog=='wkwk'}">selected="selected"</c:if> >哇卡哇卡</option>
									       <option value="sds" <c:if test="${catalog=='sds'}">selected="selected"</c:if> >石打食</option>
									 	   <option value="sjnp" <c:if test="${catalog=='sjnp'}">selected="selected"</c:if> >食间牛排</option>
									 	   <option value="jinli" <c:if test="${catalog=='jinli'}">selected="selected"</c:if> >晶丽酒店</option>
									 	   <option value="yhhg" <c:if test="${catalog=='yhhg'}">selected="selected"</c:if> >余欢火锅</option>
									 	   <option value="chals" <c:if test="${catalog=='chals'}">selected="selected"</c:if> >查理士</option>								 	                                                                    																	</select>
								   </div>
							   </div>
							</td>
						  </tr>
						  
						   <tr>
                           	<td>
                               	<div class="box">
                                    <div class="hd name"><label><font color="red">*</font>&nbsp;系统有效期：</label></div>
                                    <div class="form-group bd">                                       							  
									   <input name="startTime" id="startTime" type="text" class="form-control" style="display: inline-block;width: 130px;" value="${startTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> 到
									   <input name="endTime" id="endTime" type="text" class="form-control" style="display: inline-block;width: 130px;" value="${endTime}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
                                    </div>
                                </div>
                             </td>
                           </tr>
						  	                          		
                       </table>
                       <input type="hidden" value="" id="isNoQad" name="isNoQad">
                       <input type="hidden" value="" id="finalFactory" name="finalFactory"><!-- 勾选的经营模式 -->
					   <c:if test = "${not empty mu.mid }">
							<input type="hidden" id="mid" name="mid" value="${mu.mid}"/>
							<input type="hidden" id="userId" name="userId" value="${mu.userId }"/>
					   </c:if>
                    </form>
                    <input type="hidden" id="sid" value="${mu.mid}"/>
                    <input type="hidden" id="stime" value="${startTime}"/>
                    <input type="hidden" id="etime" value="${endTime}"/>
                    <input type="hidden" id="vid" />
                 </td>
            </tr>
             <tr>
               <td>
                   <input type="button" class="tijiao" value="保存" id="save" name="save" />
				   <input type="button" onclick="javascript:history.go(-1);" class="tijiao" value="取消"/>				 
				   <input type="button" class="tijiao" id="next" name="next" value="下一步"/>			  
               </td>
             </tr>
	</tbody>
</table>
<script type="text/javascript">
$(document).ready(function(){	
	var vid=$("#vid").val();
	if (vid !=''){
		 $("#save").attr("disabled" ,"disabled");
		 $("#save").val("已保存");
	}		
	
	$('#save').click(function() {		
		var sid=$("#sid").val();
		var userId ='';
		if (sid ==''){
			userId=$("#userId").val();
			if (userId ==''){
				alert("请输入登录名");
				return false;
			}else{
				var g =/^[A-Za-z0-9]*$/;
				if (!g.test(userId)){
					alert("登录名只支持数字和字母");
					return false;
				} 
			}	
		}	
		
		var name=$("#name").val();
		if(name==""){
			alert("请输入商户名称！");
			return false;
		}
		
		var phone=$("#phone").val();
		if(phone==""){
			alert("请输入手机号码！");
			return false;
		}
		if(phone.length !=11 || Number(phone) != phone){
			alert("输入的手机号码格式不正确");
			return false;
		}
	
		var boxes = document.getElementsByName("dataCheckBox");
		var idsValue = '';
		for(var i=0;i<boxes.length;i++){
			if(boxes[i].checked){
				idsValue += boxes[i].value;
				if(i<boxes.length-1){
					idsValue += ',';
				}
			}
		}
		if(idsValue==''){
			alert("请选择经营类型(至少选择一种)");
			return false;
		}	
		
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		if (startTime == '' || endTime==''){
			alert("系统有效期的开始时间和结束时间不能为空");
			return false;
		}
		
		if (startTime > endTime){
			alert("开始时间不能大于结束时间");
			return false;
		}
		pop.loading('正在保存，请稍等....');
		$("#finalFactory").val(idsValue);
		 if (sid !=''){
			var stime=$("#stime").val();
			var etime=$("#etime").val();		
			if (stime == startTime && etime == endTime){
				$("#isNoQad").val("1");			
			}
			document.forms[0].action = "../managerUser/doEditManagerUser.shtml";
		    document.forms[0].submit();
		    pop.close();
		}else{
			 var spvalue = $("input[name='spvalue']:checked").val();
			$.ajax({
			     url: "../managerUser/doAddManagerUser.shtml",
			     type: "post",
			     data:{
			    	 userId:userId,
			    	 name:name,
			    	 phone:phone,
			    	 address:$("#address").val(),
			    	 spvalue:spvalue,
			    	 finalFactory:$("#finalFactory").val(),
			    	 catalog:$("#catalog").val(),
			    	 startTime:startTime,
			    	 endTime:endTime,    	 
			     },
			     dataType : "json",
			     success: function(data){
			    	 if (data.result=='ok'){
			    		 $("#save").attr("disabled" ,"disabled");
			    		 $("#save").val("已保存");
			    		 $("#vid").val(data.mid);
			    		 alert('保存成功');
			    		 pop.close();
			    		 return;
			    	 }
			     }
			});

		} 

	});	
	
	$('#next').click( function() {
		var userId=$("#userId").val();
		var mid = $("#vid").val();
		var sid=$("#sid").val();
		if (sid ==''){
			if ($("#finalFactory").val() ==''){
				alert("请先保存商户信息");
				return false;
			}else{				
				window.location.href="../managerUser/toAddOrEditManagerAuth.shtml?mid="+mid+"&userId="+userId;							
			}
		}else{
			window.location.href="../managerUser/toAddOrEditManagerAuth.shtml?mid="+sid+"&userId="+userId+"&isEdit=1";
		}
	});
});

</script>
</body>
</html>