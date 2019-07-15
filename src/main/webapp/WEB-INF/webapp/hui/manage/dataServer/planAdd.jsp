<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>添加关注计划</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="planForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">关注类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="planType" id="planType" lay-filter="planType">
                <option value="1">非关注人群</option>
                <option value="2">独居老人</option>
                <option value="3">残障人士</option>
                <option value="4">涉案人员</option>
                <option value="5">涉毒人员</option>
                <option value="6">涉黑人员</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">提醒类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="remindType" id="remindType" lay-filter="remindType">
                <option value="1">出入频次低</option>
                <option value="2">出入频次高</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">提醒周期：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="remindCycle" id="remindCycle" lay-filter="remindCycle">
                <option value="1">每周</option>
                <option value="2">每月</option>
                <option value="3">季度</option>
                <option value="4">半年</option>
                <option value="5">一年</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">提醒范围：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="remindRange" id="remindRange" lay-filter="remindRange">
                <option value="1">小于</option>
                <option value="2">大于</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">出入次数：</label>
        <div class="layui-input-block">
            <input type="text" name="frequency" required style="width: 200px" lay-verify="required" placeholder="请输入出入次数" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">确认</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="cancel()">取消</button>
        </div>
    </div>
</form>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script>
    //Demo
    var formobj;
    var opt = '${opt}';
    layui.use('form', function(){
        var form = layui.form;
        formobj = form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/dataServer/save.shtml",
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


        <c:if test="${not empty plan}">
        form.val("planForm", {
            "id": "${plan.id}"
            ,"frequency": "${plan.frequency}"
        });
        $('#planType').find("option[value='${person.planType}']").attr('selected','selected');
        $('#remindType').find("option[value='${person.remindType}']").attr('selected','selected');
        $('#remindCycle').find("option[value='${person.remindCycle}']").attr('selected','selected');
        $('#remindRange').find("option[value='${person.remindRange}']").attr('selected','selected');
        $('#remindCycle').find("option[value='${person.remindCycle}']").attr('selected','selected');
        formobj.render('select');
        </c:if>
    });

    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    
</script>

</body>
</html>