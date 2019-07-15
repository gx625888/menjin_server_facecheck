<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>行政社区管理</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 社区服务
    <span class="c-gray en">&gt;</span> 行政社区管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>省份：</label>
        <span class="select-box inline">
            <select name="p_province" id="p_province" class="select" onchange="changeArea(this.value,1)">
                <option value="">全部</option>
            </select>
        </span>
        <label>城市：</label>
        <span class="select-box inline">
            <select name="p_city" id="p_city" class="select" onchange="changeArea(this.value,2)">
                <option value="">全部</option>
            </select>
        </span>
        <label>行政区：</label>
        <span class="select-box inline">
            <select name="p_county" id="p_county" class="select">
                <option value="">全部</option>
            </select>
        </span>
        <label>街道：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入街道名称" id="p_street" name="p_street">
        <label>社区：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入社区名称" id="p_name" name="p_name">
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <div>
            <span class="l"> <%--<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
            <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <a class="btn btn-primary radius" href="javascript:;"
               onclick="add_community('添加行政区域','${ctx}/community/toAdd.shtml','460','500')">
                <i class="Hui-iconfont">&#xe600;</i> 添加行政区域</a> </span>
        </div>
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
    layui.select
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'

            ,height: 'full-120'
            ,even:true
            ,url: '${ctx}/community/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'name', title: '社区名称', width:150}
                ,{field: 'province', title: '省份', width:150}
                ,{field: 'city', title: '城市', width:150}
                ,{field: 'county', title: '行政区', width:150}
                ,{field: 'street', title: '街道', width:150}
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
                        url :"${ctx}/community/del.shtml",
                        data:{id:obj.data.id},
                        success:function(data){
                            layer.close(index);
                            if (data.ret==0){
                                layer.msg("操作成功!")
                                tableObj.reload();
                            } else if (data.ret==-1){
                                layer.msg(data.msg);
                            } else {
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
                layer_show("编辑",'${ctx}/community/toAdd.shtml?id='+obj.data.id,'500','550');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            }
        });
    });
    $(function () {
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
                $('#p_province').append(html);
            }
        });
    });
    function changeArea(pid,type) {
        $.ajax({
            url:"${ctx}/community/queryArea.shtml",
            type: "POST",
            data:{"parentId":pid,"aType":type},
            dataType:"json",
            success:function(data){
                if(type==1){
                   $('#p_city').html('<option value="">全部</option>');
                   $('#p_county').html('<option value="">全部</option>');
                }
                if(type==2){
                    $('#p_county').html('<option value="">全部</option>');
                }
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                if(type==1){
                    $('#p_city').append(html);
                }else{
                    $('#p_county').append(html);
                }

            }
        });
    }
    function query(){
        var params = {p_province:$('#p_province').val(),
        p_city:$('#p_city').val(),
        p_county:$('#p_county').val(),
        p_street:$('#p_street').val(),
        p_name:$('#p_name').val()
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
</script>
</body>
</html>