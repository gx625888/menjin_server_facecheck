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
<%--<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 设备服务
    <span class="c-gray en">&gt;</span> 卡片管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>--%>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>卡片编号：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入卡片编号" id="p_cardId" name="p_cardId">
        <input style="display: none" value="1" name="p_cardStatus" id="p_cardStatus"/>
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <!-- 把快速绑定移动到列表操作中 -->
		<!-- <button type="submit" onclick="quickBand()" class="btn btn-success radius" id="quickBand" name="">快速绑定</button> -->
    </div>
    <div class="cl pd-5 bg-1 bk-gray">

    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">绑定</a>


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
            ,url: '${ctx}/card/page.shtml?p_cardStatus=1&p_band=1' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'cardId', title: '卡片编码', width:150}
                ,{field: 'cardType', title: '卡片类型', width:150,templet:"#r_cardType"}
                ,{field: 'invalidDate', title: '失效时间', width:150}
                ,{field: 'cardStatus', title: '卡片状态', width:150,templet:"#r_cardStatus"}
                ,{field: 'wealth', title: '操作', width: 100, sort: false,toolbar:'#barDemo'}
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
                        url :"${ctx}/card/del.shtml",
                        data:{id:obj.data.id,cardId:obj.data.cardId},
                        success:function(data){
                            layer.close(index);
                            if (data.ret==0){
                                layer.msg("操作成功!");
                                tableObj.reload();
                            } else if (data.ret==-1){
                                layer.msg(data.msg);
                            }else{
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
                $.ajax({
                    type:"post",
                    url :"${ctx}/card/addBindInfo.shtml?card="+obj.data.cardId+"&person=${personId}",
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
            }
        });
    });


    function query(){
        var params = {p_cardId:$('#p_cardId').val(),
            p_cardStatus:$('#p_cardStatus').val(),
            p_band:1
        };
        tableObj.reload({where:params,page:{curr:1}});
    }
    /*添加*/
    function add_person(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*编辑*/
    function person_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*删除*/
    function person_del(obj,id){
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
    function quickBand() {
        layer_show("快速绑定",'${ctx}/card/toQuickBandCard.shtml?person=${personId}','400','200');
    }
</script>
<script type="text/html" id="r_cardType">
    {{# if(d.cardType == 1){ }}类型I{{# }}}
    {{# if(d.cardType == 2){ }}类型II{{# }}}
</script>
<script type="text/html" id="r_cardStatus">
    {{# if(d.cardStatus == 1){ }}有效{{# }}}
    {{# if(d.cardStatus == 2){ }}失效{{# }}}
    {{# if(d.cardStatus == 3){ }}停用{{# }}}
</script>
</body>
</html>