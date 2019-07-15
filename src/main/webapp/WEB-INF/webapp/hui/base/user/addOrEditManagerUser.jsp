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

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="roleForm">
	<div class="layui-form-item">
		<input type="hidden" name ="mid" value = "" >
		<div class="layui-inline">
			<label class="layui-form-label"><label style="color: red">*</label>用户姓名</label>
			<div class="layui-input-inline">
			<input type="text" name="name" required  lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label"><label style="color: red">*</label>手机号</label>
			<div class="layui-input-inline">
				<input type="tel" name="phone" lay-verify="required|phone" placeholder="手机号" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label"><label style="color: red">*</label>登录账号</label>
			<div class="layui-input-inline">
				<input type="text" name="userId" required  lay-verify="required"
				<c:if test="${not empty mu}"> disabled </c:if>
					   placeholder="请输入账号" autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label"><label style="color: red">*</label>密码</label>
			<div class="layui-input-inline">
				<input type="text" name="password"
                <c:if test="${ empty mu}"> lay-verify="required" </c:if>
                       autocomplete="off" class="layui-input">
			</div>
			<%--<div class="layui-form-mid layui-word-aux">密码会被md5加密存储</div>--%>
		</div>

	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">证件类型</label>
			<div class="layui-input-inline">
				<select name="cityId">
					<option value="">请选择</option>
					<option value="1">身份证</option>
					<option value="2">护照</option>
					<option value="3">军人证</option>
					<option value="4">其他证件</option>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">证件号码</label>
			<div class="layui-input-inline">
				<input type="text" name="areaId" style="width: 300px" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">用户类型</label>
			<div class="layui-input-inline">
				<select name="managerType">
					<c:if test="${loginUser.managerType==0}">
						<%--<option value="0">超管理员</option>--%>
						<option value="1">公司管理员</option>
						<option value="3">市管理员</option>
						<option value="4">小区管理员</option>
					</c:if>
					<c:if test="${loginUser.managerType==1}">
						<option value="3">市管理员</option>
						<option value="4">小区管理员</option>
					</c:if>
					<c:if test="${loginUser.managerType==3||loginUser.managerType==4}">
						<option value="4">小区管理员</option>
					</c:if>
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">归属公司</label>
			<div class="layui-input-inline">
				<select name="managerCompany">
					<c:forEach var="item" items="${companies}">
						<option value="${item.id}">${item.companyName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
	</div>

	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label">省</label>
			<div class="layui-input-inline">
				<select id ="managerProvince" name="managerProvince"  lay-filter="province" >
					<option value="">请选择</option>

				</select>
			</div>

		</div>
		<div class="layui-inline">

			<label class="layui-form-label">市</label>
			<div class="layui-input-inline">
				<select id ="managerCity" name="managerCity"  lay-filter="city">
					<option value="">请选择</option>

				</select>
			</div>
		</div>

	</div>
	<div class="layui-form-item">
		<div class="layui-inline">

			<label class="layui-form-label">社区</label>
			<div class="layui-input-inline">
				<select id ="community" name="address"  lay-filter="community">
					<option value="">请选择</option>

				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label">小区</label>
			<div class="layui-input-inline">
				<select id ="managerResidentail" name="managerResidentail">
					<option value="">请选择</option>

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
    var formobj;
    layui.use('form', function(){
        var form = layui.form;
        formobj = form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/manageUser/save.shtml",
                data:data.field,
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.tableObj.reload();
                        parent.layer.close(index);
                    } else{
                        layer.msg(data.msg);
                    }
                },
                error:function(){
                    layer.msg("服务器开小差了!");
                }
            });

            return false;
        });
        form.on('select(province)',function (data) {
            $.ajax({
                url:"${ctx}/community/queryArea.shtml",
                type: "POST",
                data:{"parentId":data.value,"aType":1},
                dataType:"json",
                success:function(data){
                    $('#managerResidentail').html('<option value="">请选择</option>');
                    $('#managerCity').html('<option value="">请选择</option>');
                    $('#community').html('<option value="">请选择</option>');
                    var html = '';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#managerCity').append(html);
                    formobj.render('select');
                }
            });
        });
        form.on('select(city)',function (data) {
            $.ajax({
                url:"${ctx}/dataServer/queryCommunity.shtml",
                type: "POST",
                data:{"parentId":data.value,"aType":2},
                dataType:"json",
                success:function(data){
                    $('#managerResidentail').html('<option value="">请选择</option>');
                    $('#community').html('<option value="">请选择</option>');
                    var html = '';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#community').append(html);
                    formobj.render('select');
                }
            });
        });
        form.on('select(community)',function (data) {
            $.ajax({
                url:"${ctx}/dataServer/queryResidentail.shtml",
                type: "POST",
                data:{"parentId":data.value,"id":data.value},
                dataType:"json",
                success:function(data){
                    $('#managerResidentail').html('<option value="">请选择</option>');
                    var html = '';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#managerResidentail').append(html);
                    formobj.render('select');
                }
            });
        });
       /* $.ajax({
            url:"${ctx}/dataServer/queryCommunity.shtml",
            type: "POST",
            data:{},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#community').append(html);
                formobj.render('select');
            }
        });*/
        $.ajax({
            url:"${ctx}/community/queryArea.shtml",
            type: "POST",
            data:{"parentId":0,"aType":0},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#managerProvince').append(html);
                formobj.render('select');
            }
        });
    });
</script>

</body>
</html>