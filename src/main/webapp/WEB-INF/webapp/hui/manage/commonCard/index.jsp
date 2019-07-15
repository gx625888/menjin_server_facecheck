<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>通卡管理</title>
</head>
<body>
<%--<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 管理服务
    <span class="c-gray en">&gt;</span> 角色管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>--%>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <%--<input type="text" class="input-text" style="width:250px;margin-left: 4px" placeholder="输入名称" id="queryName" name="">
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>--%>
        <%-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">--%>
            <%--<i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
        <span class="l"><a class="btn btn-primary radius" href="javascript:;"
               onclick="admin_role_add('添加通卡','${ctx}/manageCommonCard/toAdd.shtml?residentail=${residentail}','550','400')">
                <i class="Hui-iconfont">&#xe600;</i> 添加通卡</a> </span>

            &nbsp;
            <span class="l"><a class="btn btn-primary radius" href="javascript:;"
                               onclick="sycnData()">
                <i class="Hui-iconfont">&#xe68f;</i> 同步通卡数据</a> </span>
    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">

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
            ,url: '${ctx}/manageCommonCard/page.shtml?residentail=${residentail}' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left',checkbox: true}
                ,{field: 'cardId', title: '卡号', width:180}
                ,{field: 'userName', title: '持卡人', width:200}
                ,{field: 'userPhone', title: '持卡人电话', width:200}
                ,{field: 'userCard', title: '持卡人证件', width:200}
                // ,{field: 'level', title: '性别', width:150, sort: false}
                 ,{field: 'wealth', title: '操作', width: 150, sort: false,toolbar:'#barDemo'}
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
                        url :"${ctx}/manageCommonCard/del.shtml",
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
                layer_show("编辑",'${ctx}/manageCommonCard/toAdd.shtml?residentail=${residentail}&id='+obj.data.id,'550','400');
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
        layer.confirm('确认要删除吗？',function(index){
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

    function sycnData() {
        layer.confirm('确认同步数据吗？',function(index){
            $.ajax({
                type:"post",
                url :"${ctx}/manageCommonCard/sync.shtml",
                data:{residentail:'${residentail}'},
                success:function(data){
                    if (data.ret==0){
                        layer.msg("操作成功!")
                    } else{
                        layer.msg("服务器开小差了!");
                    }
                },
                error:function(){
                    layer.msg("服务器开小差了!");
                }
            });
        });
    }
</script>
</body>
</html>