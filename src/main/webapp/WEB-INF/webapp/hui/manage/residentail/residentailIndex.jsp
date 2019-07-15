<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <script type="text/javascript" src="${ctx}/ui/lib/layui/layui.js"></script>
    <title>小区管理</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 社区服务
    <span class="c-gray en">&gt;</span> 小区管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <form class="layui-form" action="">
            <div class="layui-inline">
                <label>社区：</label>
                <div class="layui-input-inline">
                    <select name="p_community" id="p_community" class="select" style="height: 30px;width: 150px" lay-search="" lay-filter="p_community">
                        <option value="">全部</option>
                        <c:forEach items="${communities}" var="c">
                            <option value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <label>小区：</label>
            <div class="layui-input-inline layui-form" lay-filter="p_name">
                <select name="p_name" id="p_name" class="select" style="height: 30px;width: 150px">
                    <option value="">全部</option>
                </select>
            </div>
            </form>
        <%--<span class="select-box inline">
            <select name="p_community" id="p_community" class="select" style="width: 150px" lay-search="">
                <option value="">全部</option>
            </select>
        </span>--%>

        <div style="margin-top: 10px;">
            <span class="l"> <%--<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
            <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
                <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
            <a class="btn btn-primary radius" href="javascript:;"
               onclick="add_community('添加小区','${ctx}/residentail/toAdd.shtml','450','550')">
                <i class="Hui-iconfont">&#xe600;</i> 添加小区</a>
                 <button type="submit" onclick="batchDownload()" class="btn btn-primary radius"  name=""> 批量导出</button>
            </span>
        </div>
    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-xs" lay-event="todetail">楼栋单元</a>
    <a class="layui-btn layui-btn-xs" lay-event="commoncard">通卡管理</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>


</script>
<script type="text/html" id="toolbarDemo">

</script>
<script type="text/javascript">
    var tableObj;
    var form;
    layui.use('form', function() {
        form = layui.form;
        form.on('select(p_community)', function(data){

            $.ajax({
                type:"post",
                url :"${ctx}/residentail/queryResidentailByCommunity.shtml",
                data:{community:data.value},
                success:function(data){
                    var html = '<option value="">全部</option>';
                    var temp = eval(data);
                    $.each(temp, function(i, prd) {
                        html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                    });
                    $('#p_name').html("");
                    $('#p_name').append(html);
                    form.render('select','p_name');
                },
                error:function(){
                    layer.close(index);
                    layer.msg("小区查询失败!");
                }
            });
        });
    });
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
            /*,toolbar:'#toolbarDemo'*/
            ,height: 'full-120'
            ,even:true
            ,url: '${ctx}/residentail/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false,hide:'hide'}
                ,{field: 'name', title: '小区名称', width:150}
                ,{field: 'community', title: '所属社区', width:150}
                ,{field: 'wyCompany', title: '物业公司', width:150}
                ,{field: 'wyTelphone', title: '物业电话', width:150}
                ,{field: 'wyPerson', title: '物业负责人', width:150}
                ,{field: 'wyPhone', title: '负责人电话', width:150}
                ,{field: 'wealth', title: '操作', width: 280, sort: false,toolbar:'#barDemo'}
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
                        url :"${ctx}/residentail/del.shtml",
                        data:{id:obj.data.id},
                        success:function(data){
                            layer.close(index);
                            if (data.ret==0){
                                layer.msg("操作成功!")
                                tableObj.reload();
                            } else{
                                layer.msg(data.msg);
                            }
                        },
                        error:function(){
                            layer.close(index);
                            layer.msg("服务器开小差了!");
                        }
                    });


                });
            } else if(layEvent === 'edit'){ //编辑
                layer_show("编辑",'${ctx}/residentail/toAdd.shtml?id='+obj.data.id,'450','550');
            }else if (layEvent ==='todetail'){
               var index =  layer.open({
                    type: 2,
                    area: [900+'px', 650+'px'],
                    fix: false, //不固定
                    maxmin: true,
                    shade:0.4,
                    title: obj.data.name +"楼栋单元信息",
                    content: '${ctx}/residentailDetail/index.shtml?id='+obj.data.id
                });
               layer.full(index);
            }else if (layEvent==='commoncard'){
                var index =  layer.open({
                    type: 2,
                    area: [800+'px', 600+'px'],
                    fix: false, //不固定
                    maxmin: true,
                    shade:0.4,
                    title: obj.data.name +"通卡信息",
                    content: '${ctx}/manageCommonCard/index.shtml?residentail='+obj.data.id
                });
                layer.full(index);
            }
        });
    });

    /*$(function () {
        $.ajax({
            url:"${ctx}/residentail/queryCommunity.shtml",
            type: "POST",
            data:{},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#p_community').append(html);
            }
        });
    });

    function queryCommunity() {
        $.ajax({
            url:"${ctx}/residentail/queryCommunity.shtml",
            type: "POST",
            data:{},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#p_community').append(html);
            }
        });
    }*/

    function query(){
        var params = {p_community:$('#p_community').val(),
            p_name:$('#p_name').find("option:selected").text()
        };
        tableObj.reload({where:params,page:{curr:1}});
    }
    /*添加*/
    function add_community(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*编辑*/
    function community_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*删除*/
    function community_del(obj,id){
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

    function batchDownload(){
        var p_community=$('#p_community').val();
        var p_name=$('#p_name').find("option:selected").text();

        window.location.href="${ctx}/residentail/export.shtml?p_community="+p_community+"&p_name="+encodeURIComponent(encodeURIComponent(p_name));
    }
</script>
</body>
</html>