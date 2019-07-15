<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>操作日志</title>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 管理服务
    <span class="c-gray en">&gt;</span> 操作日志
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <label>操作类型：</label>
        <span class="select-box inline">
            <select name="p_community" id="opttype" style="width: 152px" class="select">
                 <option value="">全部</option>
                <option value="2">开门操作</option>
                <option value="1">登录</option>
            </select>
        </span>
        <label>开始时间：</label>
        <input name="p_sDate" id="p_sDate" type="text" class="input-text Wdate" style="width: 165px; height: 31px;border-color: #D2D2D2!important" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        <label>结束日期：</label>
        <input name="p_eDate" id="p_eDate" type="text" class="input-text Wdate" style="width: 163px; height: 31px;border-color: #D2D2D2!important" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        <button type="submit" onclick="batchDownload()" class="btn btn-primary radius"  name=""> 批量导出</button>
    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
</script>
<script type="text/html" id="toolbarDemo">

</script>

<script type="text/javascript">
    var tableObj;
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
            /*,toolbar:'#toolbarDemo'*/
             ,height: 'full-120'
            ,even:true
            ,url: '${ctx}/log/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'userName', title: '操作人', width:200}
                ,{field: 'userId', title: '操作人账号', width:150, sort: false}
                ,{field: 'optType', title: '操作类型', width:150, sort: false}
                ,{field: 'optTime', title: '操作时间', width:200, sort: false}
                ,{field: 'ipInfo', title: 'ip地址', width:150, sort: false}
                ,{field: 'info', title: '描述', width:300, sort: false}
                // ,{field: 'wealth', title: '操作', width: 365, sort: false,toolbar:'#barDemo'}
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
                layer_show("编辑",'${ctx}/manageRole/toAdd.shtml?id='+obj.data.id,'350','200');
            }else if (layEvent ==='privaligeMenu'){
                layer_show(obj.data.name +" 菜单分配",'${ctx}/manageRole/queryRoleMenu.shtml?roleId='+obj.data.id,'400','600');
            }else if(layEvent==='privalige'){
                layer_show(obj.data.name +" 操作按钮权限",'${ctx}/manageRole/queryRolePrivilege.shtml?roleId='+obj.data.id,'400','600');

            }
        });

    });
    function query(){
        var params = {type:$("#opttype").val(),p_sDate:$("#p_sDate").val(),p_eDate:$("#p_eDate").val()};
        tableObj.reload({where:params});
    }
    /*管理员-角色-添加*/
    function admin_role_add(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*管理员-角色-编辑*/
    function admin_role_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    /*管理员-角色-删除*/
    function admin_role_del(obj,id){
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
        var type=$("#opttype").val();
        var p_sDate=$("#p_sDate").val();
        var p_eDate=$("#p_eDate").val();
        window.location.href="${ctx}/log/export.shtml?type="+type+"&p_sDate="+p_sDate+"&p_eDate="+p_eDate;
    }
</script>
</body>
</html>