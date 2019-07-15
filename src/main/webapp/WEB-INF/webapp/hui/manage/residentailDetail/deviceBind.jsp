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
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>设备编号：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入设备编号" id="p_deviceId" name="p_deviceId">
        <label>设备类型：</label>
        <span class="select-box inline">
            <select name="p_deviceType" id="p_deviceType" class="select" style="width: 100px">
                <option value="">全部</option>
                <option value="1">类型I</option>
                <option value="2">类型II</option>
            </select>
        </span>
        <label>设备状态：</label>
        <span class="select-box inline">
            <select name="p_bandStatus" id="p_deviceStatus" class="select" style="width: 100px">
                <option value="">全部</option>
                <option value="0">可用</option>
                <option value="1">停用</option>
                <option value="2">报修</option>
                <option value="3">废弃</option>
            </select>
        </span>

        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""> 查询</button>
        <button type="submit" onclick="bind()" class="btn btn-success radius" id="bindBtn" name="">绑定</button>

    </div>
    <div class="cl pd-5 bg-1 bk-gray">

    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>



<script type="text/javascript">
    var tableObj;

    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
            ,height: 'full-120'
            ,even:true
            ,url: '${ctx}/mangeHouse/pagedevice.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {type:'radio'}
                ,{field: 'id', title: 'ID', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'deviceId', title: '设备编号', width:150}
                ,{field: 'deviceType', title: '设备类型', width:150,templet:"#r_deviceType"}
                ,{field: 'residentail', title: '所属小区', width:150}
              //  ,{field: 'build', title: '所属楼栋', width:150}
                //,{field: 'unit', title: '所属单元', width:150}
                ,{field: 'deviceStatus', title: '设备状态', width:150,templet:"#r_deviceStatus"}
                //,{field: 'wealth', title: '操作', width: 150, sort: false,toolbar:'#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除

            } else if(layEvent === 'edit'){ //编辑

            }
        });
    });


    function query(){
        var params = {p_devicId:$('#p_deviceId').val(),
            p_deviceType:$('#p_deviceType').val(),
            p_deviceStatus:$('#p_deviceStatus').val(),
            p_residentail:$('#p_residentail').val()
        };
        tableObj.reload({where:params});
    }


    function bind(){
        var data = layui.table.checkStatus(tableObj.config.id).data;
        if (data.length==0){
            layer.msg('请选择一个设备!');
            return ;
        }
        $.ajax({
            type:"post",
            url :"${ctx}/mangeHouse/doBindDevice.shtml",
            data:{unitId:'${unitId}',deviceId:data[0].id},
            success:function(data){
                if (data.ret==0){
                    parent.layer.msg("操作成功!");
                    var index = parent.layer.getFrameIndex(window.name);

                    parent.layer.close(index);
                    parent.location.href = parent.location.href;
                } else{
                    layer.msg("服务器开小差了!");
                }
            },
            error:function(){
                layer.msg("服务器开小差了!");
            }
        });
    }
</script>
<script type="text/html" id="r_deviceType">
    {{# if(d.deviceType == 1){ }}类型I{{# }}}
    {{# if(d.deviceType == 2){ }}类型II{{# }}}
</script>
<script type="text/html" id="r_deviceStatus">
    {{# if(d.deviceStatus == 0){ }}可用{{# }}}
    {{# if(d.deviceStatus == 1){ }}停用{{# }}}
    {{# if(d.deviceStatus == 2){ }}报修{{# }}}
    {{# if(d.deviceStatus == 3){ }}废弃{{# }}}
</script>


</body>
</html>