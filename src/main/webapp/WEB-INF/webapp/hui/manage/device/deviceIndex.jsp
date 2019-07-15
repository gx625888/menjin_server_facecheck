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
    <span class="c-gray en">&gt;</span> 社区设备
    <span class="c-gray en">&gt;</span> 设备管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
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
        <label>所属小区：</label>
        <input type="text" class="input-text" style="width:150px;margin-left: 4px" placeholder="输入小区" id="p_residentail" name="p_residentail">
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <div>
            <span class="l" style="margin-left: 5px"> <%--<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">
            <i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <a class="btn btn-primary radius" href="javascript:;"
               onclick="add_device('添加设备','${ctx}/device/toAdd.shtml','450','450')">
                <i class="Hui-iconfont">&#xe600;</i> 添加设备</a> </span>
            <span class="l" style="margin-left: 5px">
               <a type="button" class="btn btn-primary radius" id="test1">
                批量导入
            </a>
            </span>
            <span class="l" style="margin-left: 5px">
               <a href="${ctx}/templet/device_import.xlsx" type="button" class="btn btn-primary radius">
                下载模板
            </a>
            </span>
            <span class="l" style="margin-left: 5px">
               <a type="button" class="btn btn-primary radius" onclick="fresh(1)" id="freshResource">
                批量刷新资源
            </a>
            </span>
            <span class="l" style="margin-left: 5px">
               <a type="button" class="btn btn-primary radius"  onclick="fresh(2)" id="freshUsers">
                批量刷新用户
            </a>
            </span>
            <span class="l" style="margin-left: 5px">
               <a type="button" class="btn btn-primary radius" onclick="batchDownload()">
                批量导出
            </a>
            </span>
        </div>
    </div>
    <%--<div class="cl pd-5 bg-1 bk-gray">--%>

    <%--</div>--%>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    {{#  if(d.online == true){ }}
    <a class="layui-btn layui-btn-xs" lay-event="open">开门</a>
    <a class="layui-btn layui-btn-xs" lay-event="resource">刷新资源</a>
    <a class="layui-btn layui-btn-xs" lay-event="users">同步用户</a>
    {{#  } }}

</script>
<script type="text/html" id="toolbarDemo">

</script>
<script type="text/javascript">
    function fresh(flag){
        var data = layui.table.checkStatus(tableObj.config.id).data;
        if (data.length==0){
            layer.msg('请选择一个设备!');
            return ;
        }
        var deviceIds =[];
        for(var i=0;i<data.length;i++){
            if (data[i].online){
                deviceIds.push(data[i].deviceId);
            }

        }
        if (deviceIds.length==0){
            layer.msg('所选设备没有在线的!');
            return ;
        }

        var message = "确认发送开门请求?";
        if (flag==1){
            message = "确认发送更新资源信息?";
        }
        if (flag==2){
            message = "确认同步用户?";
        }
        layer.confirm(message,function(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"post",
                url :"${ctx}/device/deviceopt.shtml",
                data:{deviceIds:deviceIds.join(","),flag:flag},
                success:function(data){
                    layer.close(index);
                    if (data.ret==0){

                        layer.msg("操作成功!");

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
    }
    var tableObj;
    layui.select
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
            /*,toolbar:'#toolbarDemo'*/
            ,height: 'full-180'
            ,even:true
            ,url: '${ctx}/device/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头

                {type: 'checkbox', fixed: 'left'}
                ,{field: 'id', title: 'ID', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'deviceId', title: '设备编号', width:180,templet:function(d){
                    return '<a onclick="viewDetail(\''+d.id+'\');" style="color:dodgerblue" href="#">'+d.deviceId+'</a>';
                    }}
                ,{field: 'deviceType', title: '设备类型', width:150,templet:"#r_deviceType"}
                ,{field: 'residentail', title: '所属小区', width:150}
                ,{field: 'build', title: '所属楼栋', width:150}
                ,{field: 'unit', title: '所属单元', width:150}
                ,{field: 'deviceStatus', title: '设备状态', width:150,templet:"#r_deviceStatus"}
                ,{field: 'online', title: '在线状态', width:150,templet:function(d){
                        if (d.online){
                            return '在线';
                        } else{
                            return '离线';
                        }
                    }}
                ,{field: 'deviceTel', title: '分机号', width:150}
                ,{field: 'devicePhone', title: '设备手机号', width:150}
                ,{field: 'wealth', title: '操作', width: 320, sort: false,toolbar:'#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除
                var unit = obj.data.unit;
                if (unit != '未绑定'){
                    layer.msg("必须先解绑才允许删除");
                    return;
                }
                layer.confirm('确认删除?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/device/del.shtml",
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
                layer_show("编辑",'${ctx}/device/toAdd.shtml?id='+obj.data.id,'450','500');
            }else if(layEvent ==='open'){
                openDoor(0,obj.data.deviceId);
            }else if(layEvent ==='resource'){
                openDoor(1,obj.data.deviceId);
            }else if(layEvent ==='users'){
                openDoor(2,obj.data.deviceId);
            }
        });
    });

    function openDoor(flag,deviceId){
        var message = "确认发送开门请求?";
        if (flag==1){
            message = "确认发送更新资源信息?";
        }
        if (flag==2){
            message = "确认同步用户?";
        }
        layer.confirm(message,function(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"post",
                url :"${ctx}/residentailDetail/opendoor.shtml",
                data:{deviceId:deviceId,flag:flag},
                success:function(data){
                    layer.close(index);
                    if (data.ret==0){
                        layer.msg("操作成功!");

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
    }

    function query(){
        var params = {p_deviceId:$('#p_deviceId').val(),
            p_deviceType:$('#p_deviceType').val(),
            p_deviceStatus:$('#p_deviceStatus').val(),
            p_residentail:$('#p_residentail').val()
        };
        tableObj.reload({where:params});
    }
    /*添加*/
    function add_device(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*编辑*/
    function device_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*删除*/
    function device_del(obj,id){
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
    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: '${ctx}/device/uploadFile.shtml' //上传接口
            ,accept: 'file'
            ,exts:  'xlsx|xls'
            ,before: function () {
                parent.layer.msg("正在上传，请稍后。。");
            }
            ,done: function(res){
                if(res.ret==0){
                    parent.layer.msg(res.msg);
                    //formobj.render();
                    var params = {p_devicId:$('#p_deviceId').val(),
                        p_deviceType:$('#p_deviceType').val(),
                        p_deviceStatus:$('#p_deviceStatus').val(),
                        p_residentail:$('#p_residentail').val()
                    };
                    tableObj.reload({where:params});
                }
            }
            ,error: function(){
                //请求异常回调
                layer.msg("上传失败!");
            }
        });
    });
    function viewDetail(id){
        var index =  layer.open({
            type: 2,
            area: [900+'px', 650+'px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title:"设备信息",
            content: '${ctx}/device/toDetail.shtml?id='+id
        });
        layer.full(index);
    }
    function batchDownload(){
        var p_deviceId = $('#p_deviceId').val();
        var p_deviceType = $('#p_deviceType').val();
        var p_deviceStatus = $('#p_deviceStatus').val();
        var p_residentail = $('#p_residentail').val();

        window.location.href="${ctx}/device/export.shtml?p_deviceId="+p_deviceId+"&p_deviceType="+p_deviceType+"&p_deviceStatus="+p_deviceStatus+"&p_residentail="+p_residentail;
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