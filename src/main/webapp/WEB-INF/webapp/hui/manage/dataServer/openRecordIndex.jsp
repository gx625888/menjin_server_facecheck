<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>开门记录管理</title>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        .laytable-cell-1-0-10{
            height: 100%;
            width: 100%;
        }
    </style>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 数据服务
    <span class="c-gray en">&gt;</span> 开门记录管理
    <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
        <i class="Hui-iconfont">&#xe68f;</i></a></nav>
<div class="page-container">
    <div class="cl pd-5 bg-1">
        <label>行政社区：</label>
        <span class="select-box inline">
            <select name="p_community" id="p_community" style="width: 152px" class="select" onchange="changeArea(this.value,1)">
                <option value="">全部</option>
            </select>
        </span>
        <label>所属小区：</label>
        <span class="select-box inline">
            <select name="p_residentail" id="p_residentail" style="width: 152px" class="select" onchange="changeArea(this.value,2)">
                <option value="">全部</option>
            </select>
        </span>
        <label>所属楼栋：</label>
        <span class="select-box inline">
            <select name="p_build" id="p_build" class="select" style="width: 152px" onchange="changeArea(this.value,3)">
                <option value=''>全部</option>
            </select>
        </span>
        <label>所属单元：</label>
        <span class="select-box inline">
            <select name="p_unit" id="p_unit" class="select" style="width: 152px" onchange="changeArea(this.value,4)">
                <option value="">全部</option>
            </select>
        </span>
        </div>
    <div class="cl pd-5 bg-1">
        <label>门牌号码：</label>
        <input type="text" class="input-text" style="width:165px;" placeholder="输入门牌号" id="p_doorName" name="p_doorName">
        <label>刷卡人员：</label>
        <input type="text" class="input-text" style="width:165px;" placeholder="输入刷卡人员" id="p_personName" name="p_personName">
        <label>手机号码：</label>
        <input type="text" class="input-text" style="width:163px;" placeholder="输入手机号码" id="p_personPhone" name="p_personPhone">
        <label>证件号码：</label>
        <input type="text" class="input-text" style="width:163px;" placeholder="输入证件号码" id="p_cardNo" name="p_cardNo">
    </div>
    <div class="cl pd-5 bg-1">
        <label>开门方式：</label>
        <span class="select-box inline">
            <select name="p_openType" id="p_openType" style="width: 152px" class="select">
                <option value="">全部</option>
                <option value="0">门禁卡</option>
                <option value="1">远程开门</option>
                <option value="2">语音呼叫</option>
                <option value="3">微信</option>
                <option value="4">临时密码</option>
            </select>
        </span>
        <label>开始时间：</label>
        <input name="p_sDate" id="p_sDate" type="text" class="input-text Wdate" style="width: 165px; height: 31px;border-color: #D2D2D2!important" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        <label>结束日期：</label>
        <input name="p_eDate" id="p_eDate" type="text" class="input-text Wdate" style="width: 163px; height: 31px;border-color: #D2D2D2!important" value="" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly"/>
        <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i> 查询</button>
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
            ,url: '${ctx}/dataServer/selectOpenRecord.shtml' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'street', title: '街道名称', width:150}
                ,{field: 'community', title: '社区名称', width:150}
                ,{field: 'residentail', title: '小区名称', width:150}
                ,{field: 'build', title: '楼栋名称', width:150}
                ,{field: 'unit', title: '单元名称', width:150}
                ,{field: 'doorName', title: '门牌号码', width:150}
                ,{field: 'openType', title: '开门方式', width:150,templet:"#r_openType"}
                ,{field: 'personName', title: '持卡人', width:150}
                ,{field: 'personPhone', title: '制卡人电话', width:150}
                ,{field: 'cardId', title: '门禁卡号', width:150}
                ,{field: 'picture', title: '进门照片', width:150,templet:'<div><img src="{{ d.picture}}"></div>',style:'height:100px;width:80px;line-height:48px!important;'}
                ,{field: 'createDate', title: '时间', width:180}
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
                        url :"${ctx}/community/del.shtml",
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
                layer_show("编辑",'${ctx}/community/toAdd.shtml?id='+obj.data.id,'450','500');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            }
        });
    });
    $(function () {
        $.ajax({
            url:"${ctx}/dataServer/queryCommunity.shtml",
            type: "POST",
            data:{},
            dataType:"json",
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                $('#p_community').append(html);
            }
        });
    });
    function changeArea(pid,type) {
        var url = "";
        if(type==1){
            url="${ctx}/dataServer/queryResidentail.shtml";
        } else if(type==2){
            url="${ctx}/dataServer/queryBuild.shtml";
        } else if(type==3){
            url="${ctx}/dataServer/queryUnit.shtml";
        } else if(type==4){
            url="${ctx}/dataServer/queryHouse.shtml";
        }
        $.ajax({
            url:url,
            type: "POST",
            data:{"id":pid},
            dataType:"json",
            success:function(data){
                if(type==1){
                    $('#p_residentail').html('<option value="">全部</option>');
                    $('#p_build').html('<option value="">全部</option>');
                    $('#p_unit').html('<option value="">全部</option>');
                    //$('#p_doorName').html('<option value="">全部</option>');
                }
                if(type==2){
                    $('#p_build').html('<option value="">全部</option>');
                    $('#p_unit').html('<option value="">全部</option>');
                    //$('#p_doorName').html('<option value="">全部</option>');
                }
                if(type==3){
                    $('#p_unit').html('<option value="">全部</option>');
                    //$('#p_doorName').html('<option value="">全部</option>');
                }
                if(type==4){
                    //$('#p_doorName').html('<option value="">全部</option>');
                }
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                if(type==1){
                    $('#p_residentail').append(html);
                }else if(type==2){
                    $('#p_build').append(html);
                }else if(type==3){
                    $('#p_unit').append(html);
                }else{
                    $('#p_doorName').append(html);
                }

            }
        });
    }
    function query(){
        var params = {p_community:$('#p_community').val(),
            p_residentail:$('#p_residentail').val(),
            p_build:$('#p_build').val(),
            p_unit:$('#p_unit').val(),
            p_doorName:$('#p_doorName').val(),
            p_personName:$('#p_personName').val(),
            p_personPhone:$('#p_personPhone').val(),
            p_cardNo:$('#p_cardNo').val(),
            p_openType:$('#p_openType').val(),
            p_sDate:$('#p_sDate').val(),
            p_eDate:$('#p_eDate').val()
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
<script type="text/html" id="r_openType">
    {{# if(d.openType == 0){ }}门禁卡{{# }}}
    {{# if(d.openType == 1){ }}远程开门{{# }}}
    {{# if(d.openType == 2){ }}语音呼叫{{# }}}
    {{# if(d.openType == 3){ }}微信{{# }}}
    {{# if(d.openType == 4){ }}临时密码{{# }}}
</script>
</body>
</html>