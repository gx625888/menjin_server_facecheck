<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>数据管理</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 数据服务
    <span class="c-gray en">&gt;</span> 关注计划管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>关注类型：</label>
        <span class="select-box inline">
            <select name="p_planType" id="p_planType" class="select">
                <option value="">全部</option>
                <option value="1">非关注人群</option>
                <option value="2">独居老人</option>
                <option value="3">残障人士</option>
                <option value="4">涉案人员</option>
                <option value="5">涉毒人员</option>
                <option value="6">涉黑人员</option>
            </select>
        </span>
        <label>提醒类型：</label>
        <span class="select-box inline">
            <select name="p_remindType" id="p_remindType" class="select">
                <option value="">全部</option>
                <option value="1">出入频次低</option>
                <option value="2">出入频次高</option>
            </select>
        </span>
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <div>
            <span class="l"> <%--<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
            <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <a class="btn btn-primary radius" href="javascript:;"
               onclick="add_community('添加关注计划','${ctx}/dataServer/toAdd.shtml','450','500')">
                <i class="Hui-iconfont">&#xe600;</i> 添加关注计划</a> </span>
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
            ,url: '${ctx}/dataServer/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'planType', title: '关注类型', width:150, templet:'#r_planType'}
                ,{field: 'remindType', title: '提醒类型', width:150, templet:'#r_remindType'}
                ,{field: 'remindCycle', title: '提醒周期', width:150, templet:'#r_remindCycle'}
                ,{field: 'remindRange', title: '提醒范围 ', width:150,templet:'#r_remindRange'}
                ,{field: 'frequency', title: '出入频率(次)', width:150}
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
                        url :"${ctx}/dataServer/del.shtml",
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
                layer_show("编辑",'${ctx}/dataServer/toAdd.shtml?id='+obj.data.id,'450','500');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            }
        });
    });

    function query(){
        var params = {p_planType:$('#p_planType').val(),
            p_remindType:$('#p_remindType').val()
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
<script type="text/html" id="r_planType">
    {{# if(d.planType == 1){ }}非关注人群{{# }}}
    {{# if(d.planType == 2){ }}独居老人{{# }}}
    {{# if(d.planType == 3){ }}残障人士{{# }}}
    {{# if(d.planType == 4){ }}涉案人员{{# }}}
    {{# if(d.planType == 5){ }}涉毒人员{{# }}}
    {{# if(d.planType == 6){ }}涉黑人员{{# }}}
</script>
<script type="text/html" id="r_remindType">
    {{# if(d.remindType == 1){ }}出入频次低{{# }}}
    {{# if(d.remindType == 2){ }}出入频次高{{# }}}
</script>
<script type="text/html" id="r_remindCycle">
    {{# if(d.remindCycle == 1){ }}每周{{# }}}
    {{# if(d.remindCycle == 2){ }}每月{{# }}}
    {{# if(d.remindCycle == 3){ }}季度{{# }}}
    {{# if(d.remindCycle == 4){ }}半年{{# }}}
    {{# if(d.remindCycle == 5){ }}一年{{# }}}
</script>
<script type="text/html" id="r_remindRange">
    {{# if(d.remindRange == 1){ }}小于{{# }}}
    {{# if(d.remindRange == 2){ }}大于{{# }}}
</script>
</body>
</html>