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
    <span class="c-gray en">&gt;</span> 远程发卡管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="layui-side" style="margin-top:39px;width: 248px">
        <div class="layui-collapse" lay-accordion>
            <ul id="treeStart" class="ztree"></ul>
        </div>
    </div>
    <div style="margin-top: 40px;margin-left: 30px" class="layui-body" id="mainframe">
        <!-- 内容主体区域 -->
    </div>

    <%----%>

</div>

<%@ include file="../../common/footer.jsp" %>


<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="band">绑定</a>
    <a class="layui-btn layui-btn-xs" lay-event="noband">解绑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="lose">挂失</a>
</script>

<script type="text/javascript">

    layui.tree({
        elem: '#treeStart' //传入元素选择器
        ,nodes: ${treeNode}
        ,click: function(node){
            //alert("id:"+node.id+",type:"+node.type);
            if (node.type==6){
                viewUnit(node.id,node.type); //node即为当前点击的节点数据
            }
        }
    });
    function viewUnit(id,type){
        $("#mainframe").html('<iframe scrolling="yes" frameborder="0" src="${ctx}/card/bandCardMainIndex.shtml?id='+id+'&type='+type+'"  style="bottom: 0; height: 100%;width: 100%;position:absolute;"></iframe>');
    }

</script>
</body>
</html>