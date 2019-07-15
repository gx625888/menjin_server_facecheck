<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>绑定</title>
</head>
<body>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>居民姓名：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入居民姓名" id="p_name" name="p_name">
        <label>手机号码：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入手机号码" id="p_phone" name="p_phone">
        <label>证件号码：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入证件号码" id="p_cardNo" name="p_cardNo">
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
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
            ,url: '${ctx}/person/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left',hide:true}
                ,{field: 'name', title: '姓名', width:150}
                ,{field: 'phone', title: '手机', width:150}
                ,{field: 'cardType', title: '证件类型', width:150,templet:"#r_cardType"}
                ,{field: 'cardNo', title: '证件编号', width:150}
                ,{field: 'wealth', title: '操作', width: 80, sort: false,toolbar:'#barDemo'}
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
                        url :"${ctx}/person/del.shtml",
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
                var sType = '${type}';
                if (sType=='qBand'){
                    layer_show("快速绑定",'${ctx}/card/toQuickBandCard.shtml?person='+obj.data.id+'&card=${cardId}','400','200');
                }else {
                    $.ajax({
                        type:"post",
                        url :"${ctx}/card/addBindInfo.shtml?person="+obj.data.id+"&card=${cardId}",
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
                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            }
        });
    });


    function query(){
        var params = {p_name:$('#p_name').val(),
            p_phone:$('#p_phone').val(),
            p_cardNo:$('#p_cardNo').val(),
        };
        tableObj.reload({where:params});
    }
</script>
<script type="text/html" id="r_cardType">
    {{# if(d.cardType == 1){ }}身份证{{# }}}
    {{# if(d.cardType == 2){ }}护照{{# }}}
    {{# if(d.cardType == 3){ }}军人证{{# }}}
    {{# if(d.cardType == 4){ }}其他证件{{# }}}
</script>
</body>
</html>