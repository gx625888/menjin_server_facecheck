<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>添加居民</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="personForm">
    <div class="layui-form-item">
        <c:if test="${not empty houseId}">
            <input type="hidden" name ="houseId" value = "${houseId}" >
        </c:if>
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label"><label style="color: red">*</label>手机号码：</label>
        <div class="layui-input-block">
            <input type="text" name="phone" required style="width: 200px" lay-verify="required" placeholder="请输入手机号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>居民姓名：</label>
        <div class="layui-input-block">
            <input type="text" name="name" required style="width: 200px" lay-verify="required" placeholder="请输入居民姓名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="sex" id="sex" lay-filter="sex">
                <option value="1">女</option>
                <option value="2">男</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">证件类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="cardType" id="cardType" lay-filter="cardType">
                <option value="1">身份证</option>
                <option value="2">护照</option>
                <option value="3">军人证</option>
                <option value="4">其他证件</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>证件编号：</label>
        <div class="layui-input-block">
            <input type="text" name="cardNo" required style="width: 200px" lay-verify="required" placeholder="请输入证件编号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">出生日期：</label>
        <div class="layui-input-block">
            <input name="birthday" id="birthday" type="text" class="input-text Wdate" style="width: 200px; height: 38px;border-color: #D2D2D2!important" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        </div>
    </div>
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">国家：</label>
        <div class="layui-input-block">
            <input type="text" name="country" required style="width: 200px" placeholder="请输入国家" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">民族：</label>
        <div class="layui-input-block">
            <input type="text" name="nation" required style="width: 200px" placeholder="请输入民族" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">关注类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="type" id="type" lay-filter="type">
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
        <label class="layui-form-label">户籍地址：</label>
        <div class="layui-input-block">
            <input type="text" name="address" required style="width: 200px" placeholder="请输入户籍地址" autocomplete="off" class="layui-input">
        </div>
    </div>
    <c:if test="${bind == '1'}">
        <input type="hidden" name="bind" id="bind" value="1">
        <div class="layui-form-item">
            <label class="layui-form-label">居住类型：</label>
            <div class="layui-input-block" style="width:200px;">
                <select name="liveType" id="liveType" lay-filter="liveType">
                    <option value="1">房主</option>
                    <option value="2">租客</option>
                </select>
            </div>
        </div>
    </c:if>
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
                url :"${ctx}/person/save.shtml",
                data:data.field,
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        <c:if test="${empty houseId}">
                             parent.tableObj.reload();
                        </c:if>
                        <c:if test="${not empty houseId}">
                             parent.tableLoadMap['${unitId}'].reload();
                        </c:if>
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

        <c:if test="${not empty person}">
        form.val("personForm", {
            "id": "${person.id}"
            ,"name": "${person.name}"
            ,"phone": "${person.phone}"
            ,"cardNo": "${person.cardNo}"
            ,"address": "${person.address}"
            ,"birthday": "${person.birthday}"
            ,"country": "${person.country}"
            ,"nation": "${person.nation}"
            ,"address": "${person.address}"
        });
        $('#sex').find("option[value='${person.sex}']").attr('selected','selected');
        $('#cardType').find("option[value='${person.cardType}']").attr('selected','selected');
        $('#type').find("option[value='${person.type}']").attr('selected','selected');
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