<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
	<link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
	<script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
	<title>添加角色</title>
</head>
<body>

<form style="margin-top: 20px;margin-right: 20px" class="layui-form" action="" lay-filter="roleForm">
	<div class="layui-form-item">
		<input type="hidden" name ="unitId" value = "${unitId}" >
		<input type="hidden" name ="id" value = "0" >
		<div class="layui-inline" >
			<label class="layui-form-label">门号</label>
			<div class="layui-input-inline">
			<input type="text" name="name" required  lay-verify="required" placeholder="门号" autocomplete="off" class="layui-input">
			</div>
		</div>

	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">开门电话</label>
			<div class="layui-input-inline">
				<input type="tel" name="phone" lay-verify="required" placeholder="手机号" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>


	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">开门电话2</label>
			<div class="layui-input-inline">
				<input type="text" name="phoneTwo"   autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">开门电话3</label>
			<div class="layui-input-inline">
				<input type="text" name="phoneThr"   autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">开门方式</label>
			<div class="layui-input-inline">
				<select name="status">
					<option value="1">左开门</option>
					<option value="2">右开门</option>
					<option value="3">双开门</option>
				</select>
			</div>
		</div>
	</div>

	<div class="layui-form-item">
		<div class="layui-input-block">
			<button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
			<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		</div>
	</div>
</form>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            $.ajax({
                type:"post",
                url :"${ctx}/mangeHouse/save.shtml",
                data:data.field,
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.tableLoadMap[${unitId}].reload();
                        parent.layer.close(index);
                    } else{
                        layer.msg("服务器开小差了!");
                    }
                },
                error:function(){
                    layer.msg("服务器开小差了!");
                }
            });

            return false;
        });
        <c:if test="${not empty role}">
        form.val("roleForm", {
            "id": "${role.id}"
            ,"unitId": "${role.unitId}"
            ,"name": "${role.name}"
            ,"phone":"${role.phone}"
            ,"phoneTwo":"${role.phoneTwo}"
            ,"phoneThr":"${role.phoneThr}"
            ,"status":"${role.status}"
        });
        </c:if>
    });
</script>

</body>
</html>