<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>添加小区</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="residentailForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label"><label style="color: red">*</label>小区名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="name" required style="width: 200px" lay-verify="required" placeholder="请输入小区名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">物业公司：</label>
        <div class="layui-input-block">
            <input type="text" name="wyCompany" required style="width: 200px" placeholder="请输入物业公司" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">物业电话：</label>
        <div class="layui-input-block">
            <input type="text" name="wyTelphone" required style="width: 200px" placeholder="请输入物业电话" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">责任人：</label>
        <div class="layui-input-block">
            <input type="text" name="wyPerson" required style="width: 200px" placeholder="请输入物业责任人" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">责任电话：</label>
        <div class="layui-input-block">
            <input type="text" name="wyPhone" required style="width: 200px" placeholder="请输入责任人电话" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开门认证：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="flage" id="flage" lay-filter="flage">
                <option value="1">开通</option>
                <option value="2">关闭</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>所属社区：</label>
        <div class="layui-input-inline" style="width:200px;">
            <select name="community" id="community" lay-verify="required" lay-filter="community" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">小区地址：</label>
        <div class="layui-input-block">
            <input type="text" name="address" required style="width: 200px" placeholder="小区地址" autocomplete="off" class="layui-input">
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
    var flageobj;
    var communityobj;
    var opt = '${opt}';
    layui.use('form', function(){
        var form = layui.form;
        formobj = form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/residentail/save.shtml",
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
        
        <c:if test="${not empty residentail}">
        form.val("residentailForm", {
            "id": "${residentail.id}"
            ,"name": "${residentail.name}"
            ,"wyCompany": "${residentail.wyCompany}"
            ,"wyTelphone": "${residentail.wyTelphone}"
            ,"wyPerson": "${residentail.wyPerson}"
            ,"wyPhone": "${residentail.wyPhone}"
            ,"address": "${residentail.address}"
        });
        flageobj = ("${residentail.flage}");
        communityobj = ("${residentail.community}");
        $('#flage').find("option[value='"+flageobj+"']").attr('selected','selected');
        </c:if>
    });
    $(function () {
        if(opt=='edit'){
            $.ajax({
                url:"${ctx}/residentail/queryCommunity.shtml",
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
                    $('#community').find("option[value='"+communityobj+"']").attr('selected','selected');
                    formobj.render('select');
                }
            });
        }else{
            $.ajax({
                url:"${ctx}/residentail/queryCommunity.shtml",
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
            });
        }

    });
    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
</script>

</body>
</html>