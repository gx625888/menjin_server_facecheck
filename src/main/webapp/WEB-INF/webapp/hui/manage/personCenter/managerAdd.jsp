<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>个人资料</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="personForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">登录名：</label>
        <div class="layui-input-block">
            <input type="text" name="loginName" readonly="readonly"  required style="width: 200px;border: hidden"   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">管理员名：</label>
        <div class="layui-input-block">
            <input type="text" name="managerName" required style="width: 200px"  autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属公司：</label>
        <div class="layui-input-block">
            <input type="text" name="company" readonly="readonly" required style="width: 200px;border: hidden"   autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号：</label>
        <div class="layui-input-block">
            <input type="text" name="phone" readonly="readonly" required style="width: 200px;border: hidden"  autocomplete="off" class="layui-input">
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
        <label class="layui-form-label">证件编号：</label>
        <div class="layui-input-block">
            <input type="text" name="cardNo" required style="width: 200px"  autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">管理角色：</label>
        <div class="layui-input-block">
            <input type="text" name="role" readonly="readonly" required style="width: 200px;border: hidden"  autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">所属小区：</label>
        <div class="layui-input-block">
            <input type="text" name="residentail" readonly="readonly" required style="width: 200px;border: hidden"  autocomplete="off" class="layui-input">
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
                url :"${ctx}/personCenter/saveManager.shtml",
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

        <c:if test="${not empty manager}">
        form.val("personForm", {
            "id": "${manager.id}"
            ,"loginName": "${manager.loginName}"
            ,"managerName": "${manager.managerName}"
            ,"company": "${manager.company}"
            ,"phone": "${manager.phone}"
            ,"cardNo": "${manager.cardNo}"
            ,"role": "${manager.role}"
            ,"residentail": "${manager.residentail}"
        });
        $('#cardType').find("option[value='${manager.cardType}']").attr('selected','selected');
        formobj.render('select');
        </c:if>
    });
    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    function updatePwd(title,url,w,h){
        layer_show(title,url,w,h);
    }
</script>

</body>
</html>