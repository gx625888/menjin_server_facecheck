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
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label"><label style="color: red">*</label>设备编号：</label>
        <div class="layui-input-block">
            <input type="text" id="deviceId" name="deviceId" required style="width: 200px" lay-verify="required" placeholder="设备编号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <c:if test="${not empty device}">
        <div class="layui-form-item">
            <input type="hidden" name ="id" value = "" >
            <label class="layui-form-label">所属小区：</label>
            <div class="layui-input-block">
                <input type="text" required style="width: 200px;border: none" lay-verify="required" value="${device.residentail}" readonly="readonly" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <input type="hidden" name ="id" value = "" >
            <label class="layui-form-label">所属单元：</label>
            <div class="layui-input-block">
                <input type="text" required style="width: 200px;border: none" lay-verify="required" value="${device.build},${device.unit}" readonly="readonly" autocomplete="off" class="layui-input">
            </div>
        </div>
    </c:if>
    <div class="layui-form-item">
        <label class="layui-form-label">设备类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="deviceType" id="deviceType" lay-filter="deviceType">
                <option value="1">类型I</option>
                <option value="2">类型II</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">设备状态：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="deviceStatus" id="deviceStatus" lay-filter="deviceStatus">
                <option value="0">可用</option>
                <option value="1">停用</option>
                <option value="2">报修</option>
                <option value="3">废弃</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>分机号：</label>
        <div class="layui-input-block">
            <input type="text" id="deviceTel" name="deviceTel" required style="width: 200px" lay-verify="required" placeholder="分机号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>分机密码：</label>
        <div class="layui-input-block">
            <input type="text" id="devicePhone" name="devicePhone" required style="width: 200px" lay-verify="required" placeholder="分机密码" autocomplete="off" class="layui-input">
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
                url :"${ctx}/device/save.shtml",
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

        <c:if test="${not empty device}">
        form.val("deviceForm", {
            "id": "${device.id}"
            ,"residentail": "${device.residentail}"
            ,"deviceId": "${device.deviceId}"
            ,"deviceTel": "${device.deviceTel}"
            ,"devicePhone": "${device.devicePhone}"
        });
        $('#deviceType').find("option[value='${device.deviceType}']").attr({'selected':'selected','readonly':'true'});
        $('#deviceStatus').find("option[value='${device.deviceStatus}']").attr({'selected':'selected'});
        $('#deviceId').attr('readonly','readonly');
        $('#deviceId').css('border','none');
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