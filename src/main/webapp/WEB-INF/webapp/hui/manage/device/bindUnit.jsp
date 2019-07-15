<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>添加设备</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="deviceForm">

    <div class="layui-form-item">
        <label class="layui-form-label">社区：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="commmunity" id="commmunity" lay-filter="commmunity" lay-search="">
                <option value="">请选择</option>
                <c:forEach items="${communities}" var="c">
                    <option value="${c.id}">${c.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">小区：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="residentail" id="residentail" lay-filter="residentail" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">楼栋：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="build" id="build" lay-filter="build" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">单元：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="unit" id="unit" lay-filter="unit" lay-search="">
                <option value="">请选择</option>
            </select>
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
            var unitId = data.field.unit;
            if (unitId==''){
                layer.msg("请选择单元!");
                return false;
            }
            $.ajax({
                type:"post",
                url :"${ctx}/mangeHouse/doBindDevice.shtml",
                data:{deviceId:'${id}',unitId:unitId},
                success:function(data){
                    if (data.ret==0){
                        layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);

                        parent.layer.close(index);
                        parent.window.location.reload();
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

    });
    formobj.on('select(commmunity)',function (data) {
        $.ajax({
            url:"${ctx}/device/dic.shtml",
            type: "POST",
            data:{"id":data.value,"type":"residentail"},
            dataType:"json",
            success:function(data){
                $('#residentail').html('<option value="">请选择</option>');
                $('#build').html('<option value="">请选择</option>');
                $('#unit').html('<option value="">请选择</option>');
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#residentail').append(html);
                formobj.render('select');
            }
        });
    });
    formobj.on('select(residentail)',function (data) {
        $.ajax({
            url:"${ctx}/device/dic.shtml",
            type: "POST",
            data:{"id":data.value,"type":"build"},
            dataType:"json",
            success:function(data){
                $('#build').html('<option value="">请选择</option>');
                $('#unit').html('<option value="">请选择</option>');
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#build').append(html);
                formobj.render('select');
            }
        });
    });
    formobj.on('select(build)',function (data) {
        $.ajax({
            url:"${ctx}/device/dic.shtml",
            type: "POST",
            data:{"id":data.value,"type":"unit"},
            dataType:"json",
            success:function(data){
                $('#unit').html('<option value="">请选择</option>');
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#unit').append(html);
                formobj.render('select');
            }
        });
    });
    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
</script>

</body>
</html>