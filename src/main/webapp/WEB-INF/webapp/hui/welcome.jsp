<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>

</head>
<body>

<div class="layui-fluid" style="padding: 10px; background-color: #F2F2F2;">
    <div class="layui-row ">
        <div class="layui-col-md12" style="margin-top: 10px">
            <div >
                <div class="layui-card">
                    <div class="layui-card-header">
                        开门统计
                        <div class="layui-btn-group" style="float: right">
                            <button class="layui-btn layui-btn-xs layui-btn-danger"   onclick="initOpenRecord(this,0)">今日</button>
                            <button class="layui-btn layui-btn-xs" onclick="initOpenRecord(this,2)">近三日</button>
                            <button class="layui-btn layui-btn-xs " onclick="initOpenRecord(this,6)">近一周</button>
                        </div>
                    </div>
                    <div id="testDiv" class="layui-card-body">
                        <div id="test" style="width: 100%;height: 250px"></div>

                    </div>
                </div>
            </div>


        </div>
        <div class="layui-col-md12">

            <div class="layui-card" style="margin-top: 10px">

                <div class="layui-card-header">告警统计
                    <div class="layui-btn-group" style="float: right">
                        <button class="layui-btn layui-btn-xs layui-btn-danger" onclick="initWarn(this,0)" >今日</button>
                        <button class="layui-btn layui-btn-xs " onclick="initWarn(this,2)" >近三日</button>
                        <button class="layui-btn layui-btn-xs " onclick="initWarn(this,6)" >近一周</button>
                    </div>
                </div>
                <div class="layui-card-body" id="warnDiv">

                    <div id="warntable" style="width: 100%;height: 250px"></div>

                </div>
            </div>
        </div>
        <div class="layui-col-md12" style="margin-top: 10px">

            <div class="layui-card">


                    <div class="layui-card-header">开门方式
                        <%--<div class="layui-btn-group" style="float: right">--%>
                            <%--<button class="layui-btn layui-btn-xs" onclick="initWarn(this,0)">今日</button>--%>
                            <%--<button class="layui-btn layui-btn-xs layui-btn-danger" onclick="initWarn(this,2)">近三日</button>--%>
                            <%--<button class="layui-btn layui-btn-xs layui-btn-danger" onclick="initWarn(this,6)">近一周</button>--%>
                        <%--</div>--%>
                    </div>
                    <div class="layui-card-body">

                        <div id="opentype" style="width: 100%;height: 250px">

                        </div>
                    </div>



            </div>

        </div>
        <div class="layui-col-md12" style="margin-top: 10px;">


            <div class="layui-card">
                <div class="layui-card-header">设备状态</div>
                <div class="layui-card-body">

                    <div id="deviceStatus" style="width: 100%;height: 250px">

                    </div>

                </div>
            </div>
        </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script type="text/javascript" src="${ctx}/ui/lib/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/ui/static/index/commnChart.js"></script>
<script type="text/javascript" src="${ctx}/ui/static/index/data.js"></script>
<script type="text/javascript" src="${ctx}/ui/static/index/open.js"></script>
<script>
    var ctx = '${ctx}';
    layui.use(['element'], function(){
        var element = layui.element;
    });
    $(document).ready(function(){
        initOpenRecord(null,0);
        initWarn(null,0);
        initOpenType();
        initDeviceStatus();
    });





</script>
</body>
</html>


