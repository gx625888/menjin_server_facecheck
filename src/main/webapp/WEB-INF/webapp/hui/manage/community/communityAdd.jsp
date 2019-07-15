<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>添加行政社区</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" lay-filter="communityForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label"><label style="color: red">*</label>社区名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="name"   lay-verify="required" placeholder="请输入社区" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"><label style="color: red">*</label>所在区域：</label>
            <div class="layui-input-inline"  >
                <select name="province" id="province" lay-verify="required" lay-filter="province">
                    <option value="">请选择</option>
                </select>

            </div>
            <div class="layui-form-mid ">(省)</div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline"  >
                <select name="city" id="city" lay-verify="required" lay-filter="city">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="layui-form-mid ">(市)</div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label"></label>
            <div class="layui-input-inline"  >
                <select name="county" id="county" lay-verify="required" lay-filter="county">
                    <option value="">请选择</option>
                </select>
            </div>
            <div class="layui-form-mid ">(区)</div>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label"><label style="color: red">*</label>街道名称：</label>
        <div class="layui-input-inline">
            <input type="text" name="street" lay-verify="required" placeholder="请输入街道名" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注：</label>
        <div class="layui-input-inline">
            <input type="text" name="remark"  placeholder="备注    " autocomplete="off" class="layui-input">
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
    var provinceobj = '${community.province}';
    var cityobj = '${community.city}';
    var countyobj = '${community.county}';
    var opt = '${opt}';
    layui.use('form', function(){
        var form = layui.form;
        formobj = form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/community/save.shtml",
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
        form.on('select(province)',function (data) {
            $.ajax({
                url:"${ctx}/community/queryArea.shtml",
                type: "POST",
                data:{"parentId":data.value,"aType":1},
                dataType:"json",
                success:function(data){
                    $('#city').html('<option value="">请选择</option>');
                    $('#county').html('<option value="">请选择</option>');
                    var html = '';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#city').append(html);
                    formobj.render('select');
                }
            });
        });
        form.on('select(city)',function (data) {
            $.ajax({
                url:"${ctx}/community/queryArea.shtml",
                type: "POST",
                data:{"parentId":data.value,"aType":2},
                dataType:"json",
                success:function(data){
                    $('#county').html('<option value="">请选择</option>');
                    var html = '';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#county').append(html);
                    formobj.render('select');
                }
            });
        });
        <c:if test="${not empty community}">
        form.val("communityForm", {
            "id": "${community.id}"
            ,"name": "${community.name}"
            //,"province": "${community.province}"
           // ,"city": "${community.city}"
            //,"county": "${community.county}"
            ,"street": "${community.street}"
            ,"remark": "${community.remark}"
        });
        provinceobj = ("${community.province}");
        cityobj = ("${community.city}");
        countyobj = ("${community.county}");
        </c:if>
    });
    $(function () {
        if(opt=='edit'){
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
            $.ajax({
                url:"${ctx}/community/queryArea.shtml",
                type: "POST",
                data:{"parentId":cityobj,"aType":2},
                dataType:"json",
                success:function(data){
                    var html = '';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#county').append(html);
                    $('#county').find("option[value='"+countyobj+"']").attr('selected','selected');
                    formobj.render('select');
                }
            });
        }else{
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
                    formobj.render('select');
                }
            });
        }

    });
    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    function changeArea(pid,type) {
        $.ajax({
            url:"${ctx}/community/queryArea.shtml",
            type: "POST",
            data:{"parentId":pid,"aType":type},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                if(type==1){
                    $('#city').append(html);
                }else{
                    $('#county').append(html);
                }

            }
        });
    }
</script>

</body>
</html>