<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib uri="http://www.lvhetech.com/privilege-taglib" prefix="p" %>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="../../common/resource.jsp" %>
	<title>用户管理</title>
</head>
<body>
<nav class="breadcrumb">
	<i class="Hui-iconfont">&#xe67f;</i> 首页
	<span class="c-gray en">&gt;</span> 管理服务
	<span class="c-gray en">&gt;</span> 管理员管理
	<a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
		<i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
	<div class="cl pd-5 bg-1 bk-gray">
		<input type="text" class="input-text" style="width:250px;margin-left: 4px" placeholder="输入名称" id="queryName" name="">
		<button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
		<%-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius">--%>
		<%--<i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
		<p:auth privCodeList="manageuser_c">
			<span class="l"><a class="btn btn-primary radius" href="javascript:;"
							   onclick="admin_role_add('添加用户','${ctx}/manageUser/toAddOrEditManagerUser.shtml','850','450')">
                <i class="Hui-iconfont">&#xe600;</i> 添加用户</a> </span>
		</p:auth>

	</div>
	<table id="dataTable" lay-filter="test" >

	</table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">

	<p:auth privCodeList="manageuser_role">
		<a class="layui-btn layui-btn-xs" lay-event="users">分配角色</a>
	</p:auth>
	<p:auth privCodeList="manageuser_u">
		<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
	</p:auth>
	<p:auth privCodeList="manageuser_d">
		<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
	</p:auth>
</script>

<script type="text/javascript">
    var tableObj;
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
            ,height: 'full-135'
            ,even:true
            ,url: '${ctx}/manageUser/page.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'userId', title: '登录账号', width:100, sort: false}
                ,{field: 'name', title: '姓名', width:150}
                ,{field: 'phone', title: '手机号', width:150, sort: false}
                ,{field: 'cityId', title: '证件类型', width:150, sort: false,templet: function(d){
                    	if (d.cityId=='1'){
                    	    return '身份证'
						}
                        if (d.cityId=='2'){
                            return '护照'
                        }
                        if (d.cityId=='3'){
                            return '军人证'
                        }
                        if (d.cityId=='4'){
                            return '其他证件'
                        }
                        return '';
                    }}
                ,{field: 'areaId', title: '证件号码', width:150, sort: false}
                ,{field: 'managerCompanyName', title: '归属公司', width:150, sort: false}
                ,{field: 'managerType', title: '用户类型', width:150, sort: false,templet:'#userType'}
                ,{field: 'managerProvinceName', title: '省', width:150, sort: false}
                ,{field: 'managerCityName', title: '市', width:150, sort: false}
                ,{field: 'managerResidentailName', title: '小区', width:150, sort: false}
                ,{field: 'wealth', title: '操作', width: 200, sort: false,toolbar:'#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'users'){ //分配角色

                layer_show(obj.data.name+" 角色分配",'${ctx}/manageRole/queryUserRole.shtml?userId='+obj.data.mid,'800','600');
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确认删除?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/manageUser/del.shtml",
                        data:{id:obj.data.mid},
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
                layer_show("编辑",'${ctx}/manageUser/toAddOrEditManagerUser.shtml?mid='+obj.data.mid,'850','450');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            }
        });
    });
    function query(){
        var params = {name:$("#queryName").val()};
        tableObj.reload({where:params,page:{curr:1}});
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
</script>
<script type="text/html" id="userType">
	{{# if(d.managerType == 0){ }}超级管理员{{# }}}
	{{# if(d.managerType == 1){ }}公司管理员{{# }}}
	{{# if(d.managerType == 2){ }}省管理员{{# }}}
	{{# if(d.managerType == 3){ }}市管理员{{# }}}
	{{# if(d.managerType == 4){ }}小区管理员{{# }}}
</script>
</body>
</html>