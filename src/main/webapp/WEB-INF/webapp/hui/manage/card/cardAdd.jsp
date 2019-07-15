<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>添加卡片</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="cardForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label"><label style="color: red">*</label>卡片编号：</label>
        <div class="layui-input-block">
            <input type="text" id="cardId" name="cardId" required style="width: 200px" lay-verify="required" placeholder="卡片编号" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">卡片类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="cardType" id="cardType" lay-filter="cardType">
                <option value="1">类型I</option>
                <option value="2">类型II</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">卡片状态：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="cardStatus" id="cardStatus" lay-filter="cardStatus">
                <option value="1">有效</option>
                <option value="2">失效</option>
                <option value="3">停用</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>有效时间：</label>
        <div class="layui-input-block">
            <input name="invalidDate" id="invalidDate" type="text" lay-verify="required" class="input-text Wdate" style="width: 200px; height: 38px;border-color: #D2D2D2!important" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        </div>
    </div>
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">备注：</label>
        <div class="layui-input-block">
            <input type="text" id="remark" name="remark" required style="width: 200px" placeholder="备注" autocomplete="off" class="layui-input">
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
                url :"${ctx}/card/saveCard.shtml",
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

        <c:if test="${not empty card}">
        form.val("cardForm", {
            "id": "${card.id}"
            ,"cardId": "${card.cardId}"
            ,"remark": "${card.remark}"
            ,"invalidDate": "${card.invalidDate}"
        });
        $('#cardType').find("option[value='${card.cardType}']").attr({'selected':'selected'});
        $('#cardStatus').find("option[value='${card.cardStatus}']").attr({'selected':'selected'});
        $('#cardId').attr('readonly','readonly');
        $('#cardId').css('border','none');
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