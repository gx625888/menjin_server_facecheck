<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>公司资料</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="personForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">公司名称：</label>
        <div class="layui-input-block">
            <input type="text" name="companyName" required style="width: 200px" lay-verify="required" placeholder="请输入公司名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">平台名称：</label>
        <div class="layui-input-block">
            <input type="text" name="platformName" required style="width: 200px" lay-verify="required" placeholder="请输入平台名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系人：</label>
        <div class="layui-input-block">
            <input type="text" name="person" required style="width: 200px" lay-verify="required" placeholder="请输入联系人" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">电话：</label>
        <div class="layui-input-block">
            <input type="text" name="phone" required style="width: 200px" lay-verify="required" placeholder="请输入电话" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所在区域：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="province" id="province" lay-filter="province">
                <option value="">请选择</option>
            </select>
            <select name="city" id="city" lay-filter="city">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系地址：</label>
        <div class="layui-input-block">
            <input type="text" name="adress" required style="width: 200px" lay-verify="required" placeholder="请输入联系地址" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">短信后缀：</label>
        <div class="layui-input-block">
            <input type="text" name="msgDot" required style="width: 200px" lay-verify="required" placeholder="请输入短信后缀" autocomplete="off" class="layui-input">
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
    var provinceobj = '${company.province}';
    var cityobj = '${company.city}';
    layui.use('form', function(){
        var form = layui.form;
        formobj = form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/personCenter/save.shtml",
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

        <c:if test="${not empty company}">
        form.val("personForm", {
            "id": "${company.id}"
            ,"companyName": "${company.companyName}"
            ,"platformName": "${company.platformName}"
            ,"person": "${company.person}"
            ,"phone": "${company.phone}"
            ,"adress": "${company.adress}"
            ,"msgDot": "${company.msgDot}"
        });
        $('#sex').find("option[value='${person.sex}']").attr('selected','selected');
        $('#cardType').find("option[value='${person.cardType}']").attr('selected','selected');
        $('#type').find("option[value='${person.type}']").attr('selected','selected');
        formobj.render('select');
        </c:if>
    });
    $(function(){
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
                $('#province').append(html);
                $('#province').find("option[value='"+provinceobj+"']").attr('selected','selected');
                formobj.render('select');
            }
        });
        $.ajax({
            url:"${ctx}/community/queryArea.shtml",
            type: "POST",
            data:{"parentId":provinceobj,"aType":1},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#city').append(html);
                $('#city').find("option[value='"+cityobj+"']").attr('selected','selected');
                formobj.render('select');
            }
        });
    })
    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
</script>

</body>
</html>