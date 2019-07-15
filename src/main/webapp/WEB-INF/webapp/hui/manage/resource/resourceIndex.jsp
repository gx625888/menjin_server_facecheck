<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>小区管理</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 设备服务
    <span class="c-gray en">&gt;</span> 资源管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>资源名称：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入资源名称" id="p_resourceName" name="p_resourceName">
        <label>资源类型：</label>
        <span class="select-box inline">
            <select name="p_resourceType" id="p_resourceType" class="select" style="width: 100px">
                <option value="">全部</option>
                <option value="0">图片</option>
                <option value="1">视频</option>
            </select>
        </span>
        <label>资源状态：</label>
        <span class="select-box inline">
            <select name="p_resourceStatus" id="p_resourceStatus" class="select" style="width: 100px">
                <option value="">全部</option>
                <option value="0">未同步</option>
                <option value="1">已同步</option>
            </select>
        </span>
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <div>
            <span class="l"> <%--<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
            <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <a class="btn btn-primary radius" href="javascript:;"
               onclick="add_resource('添加资源','${ctx}/resource/toAdd.shtml','450','480')">
                <i class="Hui-iconfont">&#xe600;</i> 添加资源</a> </span>
        </div>
    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    <a class="layui-btn layui-btn-xs" lay-event="sync">绑定</a>


</script>

<script type="text/javascript">
    var tableObj;
    layui.select
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'

            ,height: 'full-120'
            ,even:true
            ,url: '${ctx}/resource/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left',hide:'hide'}
                ,{field: 'resourceName', title: '资源名', width:250}
                ,{field: 'resourceType', title: '资源类型', width:250,templet:"#r_resourceType"}
                ,{field: 'resourceStatus', title: '资源状态', width:250,templet:"#r_resourceStatus"}
                ,{field: 'wealth', title: '操作', width: 250, sort: false,toolbar:'#barDemo'}
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
                        url :"${ctx}/resource/del.shtml",
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
                layer_show("编辑",'${ctx}/resource/toAdd.shtml?id='+obj.data.id,'450','480');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            } else if(layEvent === 'sync'){ //编辑
                layer_show("同步",'${ctx}/resource/queryUnitByResidentail.shtml?id='+obj.data.id,'450','480');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            }
        });
    });


    function query(){
        var params = {p_resourceName:$('#p_resourceName').val(),
            p_resourceType:$('#p_resourceType').val(),
            p_resourceStatus:$('#p_resourceStatus').val()
        };
        tableObj.reload({where:params,page:{curr:1}});
    }
    /*添加*/
    function add_resource(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*编辑*/
    function resource_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*删除*/
    function resource_del(obj,id){
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
<script type="text/html" id="r_resourceType">
    {{# if(d.resourceType == 0){ }}图片{{# }}}
    {{# if(d.resourceType == 1){ }}视频{{# }}}
</script>
<script type="text/html" id="r_resourceStatus">
    {{# if(d.resourceStatus == 0){ }}未同步{{# }}}
    {{# if(d.resourceStatus == 1){ }}已同步{{# }}}
</script>
</body>
</html>