<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>添加角色</title>
    <style>
        .layui-form-label{
            width: 100px;
        }
    </style>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="roleForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "0" >
        <input type="hidden" name ="residentail" value = "${residentail}" >
        <label class="layui-form-label"><span style="color: red">*</span>卡号:</label>
        <div class="layui-input-block">
            <input type="text" name="cardId" required style="width: 200px" lay-verify="required" placeholder="请输入卡号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: red">*</span>持卡人:</label>
        <div class="layui-input-block">
            <input type="text" name="userName" required style="width: 200px" lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><span style="color: red">*</span>持卡人电话:</label>
        <div class="layui-input-block">
            <input type="text" name="userPhone" required style="width: 200px" lay-verify="required" placeholder="请输入手机号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">持卡人证件号:</label>
        <div class="layui-input-block">
            <input type="text" name="userCard" required style="width: 200px"  placeholder="请输入证件号" autocomplete="off" class="layui-input">
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
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/manageCommonCard/save.shtml",
                data:data.field,
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.tableObj.reload();
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
                ,"cardId": "${role.cardId}"
                ,"userName": "${role.userName}"
                ,"userPhone": "${role.userPhone}"
                ,"userCard": "${role.userCard}"
                ,"residentail": "${role.residentail}"
            });
        </c:if>
    });
</script>

</body>
</html>