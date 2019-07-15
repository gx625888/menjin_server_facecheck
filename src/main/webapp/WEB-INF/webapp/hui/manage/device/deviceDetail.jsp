<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>楼栋单元</title>
</head>
<body>

<%--<form style="margin-top: 20px" class="layui-form" action="" lay-filter="roleForm">--%>

<div class="layui-form-item">
    <div class="layui-inline">
        <label class="layui-form-label">设备编号:</label>
        <div class="layui-form-mid layui-word-aux">${device.deviceId}</div>
    </div>
    <c:if test="${'未绑定' eq device.residentail}">
        <div class="layui-inline">
            <label class="layui-form-label">楼栋单元:</label>
            <div class="layui-form-mid layui-word-aux">未绑定</div>
        </div>

        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="bindDevice()">绑定新单元</button>
        </div>

    </c:if>
    <c:if test="${'未绑定' ne device.residentail}">
        <div class="layui-inline">
            <label class="layui-form-label">楼栋单元:</label>
            <div class="layui-form-mid layui-word-aux">${device.residentail}&nbsp;
                    ${device.build}&nbsp;
                    ${device.unit}&nbsp;
            </div>
        </div>
        <div class="layui-inline">
            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="unBindDevice()">解绑单元</button>
        </div>
    </c:if>



</div>
<div class="layui-form-item">
    <c:if test="${'未绑定' eq device.residentail}">
        <div class="layui-inline">
            <label class="layui-form-label">设备状态:</label>
            <div class="layui-form-mid layui-word-aux">未绑定</div>
        </div>
    </c:if>
    <c:if test="${'未绑定' ne device.residentail}">
        <c:if test="${isOnline eq true}">
            <div class="layui-inline">
                <label class="layui-form-label">设备状态:</label>
                <div class="layui-form-mid layui-word-aux">在线</div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="openDoor(0)">开门</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="openDoor(1)">刷新资源</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="openDoor(2)">同步用户</button>
                <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="openDoor(5)">重启设备</button>
            </div>
        </c:if>
        <c:if test="${isOnline eq false}">
            <div class="layui-inline">
                <label class="layui-form-label">设备状态:</label>
                <div class="layui-form-mid layui-word-aux">离线</div>
            </div>
            <div class="layui-inline">
                <button class="layui-btn layui-btn-sm  layui-btn-disabled" >开门</button>
                <button class="layui-btn layui-btn-sm  layui-btn-disabled" >刷新资源</button>
                <button class="layui-btn layui-btn-sm  layui-btn-disabled" >同步用户</button>
                <button class="layui-btn layui-btn-sm  layui-btn-disabled" >重启设备</button>
            </div>
        </c:if>

    </c:if>

</div>


<%--</form>--%>

  <%--  <div class="layui-collapse">
        <div class="layui-colla-item">
            <h2 class="layui-colla-title ">开门信息  </h2>
            <div class="layui-colla-content layui-show">
             </div>
        </div>
    </div>--%>







<%--</div>--%>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>



<script type="text/javascript">

    $(document).ready(function(){
        layui.use('element', function(){
            var element = layui.element;
            element.render();

            element.on('collapse', function(data){
                if(data.show){
                    loadTable($(data.title).data('id'));
                }
            });
        });
    });


    function openDoor(flag){
        var message = "确认发送开门请求?";
        if (flag==1){
            message = "确认发送更新资源信息?";
        }
        if (flag==2){
            message = "确认同步用户?";
        }
        if (flag==5){
            message = "确认重启设备?";
        }
        layer.confirm(message,function(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"post",
                url :"${ctx}/residentailDetail/opendoor.shtml",
                data:{deviceId:'${device.deviceId}',flag:flag},
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



    function layer_show(title,url,w,h){
        if (title == null || title == '') {
            title=false;
        };
        if (url == null || url == '') {
            url="404.html";
        };
        if (w == null || w == '') {
            w=800;
        };
        if (h == null || h == '') {
            h=($(window).height() - 50);
        };
        layer.open({
            type: 2,
            area: [w+'px', h +'px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: title,
            content: url
        });
    }

    function bindDevice(){
        layer_show("绑定设备",'${ctx}/device/tobinduint.shtml?id=${device.id}','400','400');
    }
    function unBindDevice(){
        layer.confirm("确定解绑设备?",function(){
            $.ajax({
                type:"post",
                url :"${ctx}/mangeHouse/unBindDevice.shtml",
                data:{unitId:'${unitId}',deviceId:'${device.id}'},
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        window.location.href = window.location.href;
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