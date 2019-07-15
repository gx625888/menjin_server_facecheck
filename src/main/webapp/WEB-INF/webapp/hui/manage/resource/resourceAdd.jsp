<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>添加资源</title>
</head>
<body>

<form style="margin-top: 20px" class="layui-form" action="" enctype="multipart/form-data" lay-filter="resourceForm">
    <div class="layui-form-item">
        <input type="hidden" name ="id" value = "" >
        <label class="layui-form-label">资源名称：</label>
        <div class="layui-input-block">
            <input type="text" name="resourceName" required style="width: 200px" lay-verify="required" placeholder="请输入资源名称" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">上传资源：</label>
        <button type="button" class="layui-btn" id="test1">
            <i class="layui-icon">&#xe67c;</i>上传文件
        </button>
        <span id="fileName"></span>
        <input hidden="true" id="filePath" name="filePath"/>
        <input hidden="true" id="fileExtension" name="fileExtension"/>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">资源类型：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="resourceType" id="resourceType" lay-filter="sex">
                <option value="0">图片</option>
                <option value="1">视频</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">资源状态：</label>
        <div class="layui-input-block" style="width:200px;">
            <select name="resourceStatus" id="resourceStatus" lay-filter="sex">
                <option value="0">未同步</option>
                <option value="1">已同步</option>
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
            $.ajax({
                type:"post",
                url :"${ctx}/resource/save.shtml",
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

        <c:if test="${not empty resource}">
        form.val("resourceForm", {
            "id": "${resource.id}"
            ,"resourceName": "${resource.resourceName}"
        });
        $('#resourceType').find("option[value='${resource.resourceType}']").attr('selected','selected');
        $('#resourceStatus').find("option[value='${resource.resourceStatus}']").attr('selected','selected');
        formobj.render('select');
        </c:if>
    });
    function cancel() {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }
    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: '${ctx}/resource/uploadFile.shtml' //上传接口
            ,accept: 'file'
            ,exts:  'mp4|MP4|jpg|png'
            ,done: function(res){
                if(res.ret==0){
                    $("#fileName").val(res.oldName);
                    $("#filePath").val(res.resFileDbPath);
                    $("#fileExtension").val(res.fileExt);
                    formobj.render();
                }
                layer.msg(res.oldName+'资源上传'+res.msg);
            }
            ,error: function(){
                //请求异常回调
                layer.msg("上传失败!");
            }
        });
    });
</script>

</body>
</html>