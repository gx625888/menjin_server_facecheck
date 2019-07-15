<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>用户角色</title>
</head>
<body >
<form style="margin-top: 20px" class="layui-form" action="" lay-filter="roleForm">
    <div class="layui-form-item">
        <label class="layui-form-label">已分配</label>
        <div  id ="roles" class="layui-input-block">

        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">未分配</label>
        <div  id ="unroles" class="layui-input-block">

        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
            <%--<button type="reset" class="layui-btn layui-btn-primary">重置</button>--%>
        </div>
    </div>

</div>

</form>
</body>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script>

    var roles = ${roles};
    var htmlroles = "";
    var htmlunroles = "";
    for (var i=0;i<roles.length;i++){


        if ( roles[i].modularid) {
            htmlroles+='<input type="checkbox" id="'+roles[i].id+'" name="'+roles[i].id+'" title="'+roles[i].name+'"';
            htmlroles+=' checked '
            htmlroles+=    '>';
        }else{
            htmlunroles+='<input type="checkbox" id="'+roles[i].id+'" name="'+roles[i].id+'" title="'+roles[i].name+'">';
        }


    }
    $("#roles").html(htmlroles);
    $("#unroles").html(htmlunroles);


    var formObj;
    layui.use('form', function(){
        var form = layui.form;
        formObj = form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            var postdata = {userId:"${userId}",roles:""};
            var temp =[];
            for(var obj in data.field){
                temp.push(obj);
            }
            postdata.roles = temp.join(",");
            //layer.msg(JSON.stringify(postdata));
            $.ajax({
                type:"post",
                url :"${ctx}/manageRole/saveUserRole.shtml",
                data:postdata,
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        //parent.tableObj.reload();
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
        form.on('checkbox', function(data){
            if (data.elem.checked) {
                var html = data.elem;
                $("#"+$(data.elem).attr('id')).remove();
                data.othis.remove();
                $("#roles").append(html);
            }else{
                var html = data.elem;
                $("#"+$(data.elem).attr('id')).remove();
                data.othis.remove();
                $("#unroles").append(html);
            }
            formObj.render('checkbox');

        });

    });
    formObj.render('checkbox');
</script>
</html>
