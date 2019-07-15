<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>角色管理</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 管理服务
    <span class="c-gray en">&gt;</span> 角色管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <input type="text" class="input-text" style="width:250px;margin-left: 4px" placeholder="输入名称" id="queryName" name="">
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <%-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">--%>
            <%--<i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
        <span class="l"><a class="btn btn-primary radius" href="javascript:;"
               onclick="admin_role_add('添加角色','${ctx}/manageRole/toAdd.shtml','350','200')">
                <i class="Hui-iconfont">&#xe600;</i> 添加角色</a> </span>
    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="privaligeMenu">菜单权限</a>
    <a class="layui-btn layui-btn-xs" lay-event="privalige">按钮权限</a>
    <%--<a class="layui-btn layui-btn-xs" lay-event="users">用户</a>--%>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script type="text/javascript">
    var tableObj;
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'

             ,height: 'full-120'
            ,even:true
            ,url: '${ctx}/manageRole/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left',checkbox: true}
                ,{field: 'name', title: '角色', width:300}
                // ,{field: 'level', title: '性别', width:150, sort: false}
                 ,{field: 'wealth', title: '操作', width: 365, sort: false,toolbar:'#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确认删除?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/manageRole/del.shtml",
                        data:{id:obj.data.id},
                        success:function(data){
                            layer.close(index);
                            if (data.ret==0){
                                layer.msg("操作成功!")
                                tableObj.reload();
                            } else{
                                layer.msg("服务器开小差了!");
                            }
                        },
                        error:function(){
                            layer.close(index);
                            layer.msg("服务器开小差了!");
                        }
                    });


                });
            } else if(layEvent === 'edit'){ //编辑
                layer_show("编辑",'${ctx}/manageRole/toAdd.shtml?id='+obj.data.id,'350','200');
            }else if (layEvent ==='privaligeMenu'){
                layer_show(obj.data.name +" 菜单分配",'${ctx}/manageRole/queryRoleMenu.shtml?roleId='+obj.data.id,'400','600');
            }else if(layEvent==='privalige'){
                layer_show(obj.data.name +" 操作按钮权限",'${ctx}/manageRole/queryRolePrivilege.shtml?roleId='+obj.data.id,'400','600');

            }
        });
    });
    function query(){
        var params = {name:$("#queryName").val()};
        tableObj.reload({where:params,page:{curr:1}});
    }
    /*管理员-角色-添加*/
    function admin_role_add(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*管理员-角色-编辑*/
    function admin_role_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*管理员-角色-删除*/
    function admin_role_del(obj,id){
        layer.confirm('角色删除须谨慎，确认要删除吗？',function(index){
            $.ajax({
                type: 'POST',
                url: '',
                dataType: 'json',
                success: function(data){
                    $(obj).parents("tr").remove();
                    layer.msg('已删除!',{icon:1,time:1000});
                },
                error:function(data) {
                    console.log(data.msg);
                },
            });
        });
    }
</script>
</body>
</html>