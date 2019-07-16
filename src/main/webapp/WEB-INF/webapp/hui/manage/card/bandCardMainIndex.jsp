<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>远程绑卡管理</title>
</head>
<body>
<div class="page-container">
    <div class="cl pd-5 bg-1 bk-gray">
        <div class="layui-input-inline">
            <label>卡片编号：</label>
            <input type="text" class="input-text" style="width:150px;height:30px" placeholder="输入卡片编号" id="p_cardId" name="p_cardId">
            <label>居民姓名：</label>
            <input type="text" class="input-text" style="width:150px;height:30px;" placeholder="输入居民姓名" id="p_personName" name="p_personName">
            <label>绑卡状态：</label>
            <div class="layui-input-inline">
                <select name="p_band" id="p_band" class="select" style="height: 30px;width: 150px" lay-search="">
                    <option value="">全部</option>
                    <option value="0">未绑定</option>
                    <option value="1">已绑定</option>
                </select>
            </div>
        </div>
        <div class="layui-input-inline" style="margin-top: 5px">
            <button type="submit" onclick="query()" class="btn btn-success radius" id="queryBtn" name=""><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </div>
        <c:if test="${areaType==6}">
            <div class="layui-input-inline" style="margin-top: 5px">
                <button type="submit" onclick="syncInfo()" class="btn btn-success radius" id="syncInfo" name="">同步用户</button>
            </div>
        </c:if>
    </div>
    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="quickband">快绑</a> <!-- 快速绑定--author 高翔 -->
    <a class="layui-btn layui-btn-xs" lay-event="band">单绑</a>
    <a class="layui-btn layui-btn-xs" lay-event="noband">解绑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="lose">挂失</a>
</script>

<script type="text/javascript">
function showloading(t) {
	if (t) {
		//如果是true则显示loading
		console.log(t);
		loading = layer.load(1, {
			shade: [0.1, '#fff'] //0.1透明度的白色背景 
		});
	} else {//如果是false则关闭loading
		console.log("关闭loading层:" + t);
		layer.closeAll('loading');
	}
}



    var tableObj;
    layui.select
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
			,limit: 50
            ,height: 'full-120'
            ,even:true
            ,loading: true
            ,url: '${ctx}/card/queryBandCardNew.shtml?p_areaId=${areaId}&p_areaType=${areaType}' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'personId', title: 'ID', width:80, sort: false, fixed: 'left',hide:true}
                ,{field: 'personName', title: '居民姓名', width:100}
                // ,{field: 'personCard', title: '居民证件号', width:150}
                ,{field: 'personPhone', title: '居民手机号', width:150}
                ,{field: 'cardId', title: '门禁卡号', width:150}
                ,{field: 'house', title: '房间号', width:95, sort: true}
                ,{field: 'bandStatus', title: '绑定状态', width:90,templet:"#r_bandStatus"}
                ,{field: 'wealth', title: '操作', width: 220, sort: false,toolbar:'#barDemo'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'lose'){ //删除
                if(obj.data.remark == '未绑定'){
                    layer.msg("该用户未绑定卡！")
                    return false;
                }
                layer.confirm('确认挂失?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/card/toLoseCard.shtml",
                        data:{id:obj.data.cardId},
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
            } else if(layEvent === 'quickband'){ //快速绑定--author 高翔
				var show_title = "房间号:"+obj.data.house+">>>快速绑定";
                layer_show(show_title,'${ctx}/card/toQuickBandCard.shtml?person='+obj.data.personId,'400','200');
        	} else if(layEvent === 'band'){ //编辑
                /*if(obj.data.remark != '未绑定'){
                    layer.msg("该卡已经被绑定！")
                    return false;
                }*/
                layer_show("绑定",'${ctx}/card/toAddBandCardNew.shtml?id='+obj.data.personId,'800','400');

                /*  //同步更新缓存对应的值
                  obj.update({
                      name: '123'
                      ,title: 'xxx'
                  });*/
            } else if(layEvent === 'noband'){ //编辑
                if(obj.data.remark == '未绑定'){
                    layer.msg("该卡未被绑定！")
                    return false;
                }
                layer.confirm('确认解绑?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/card/toNoBand.shtml",
                        data:{id:obj.data.cardId},
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
            }
        });
    });


    function query(){
        var params = {p_cardId:$('#p_cardId').val(),
            p_personName:$('#p_personName').val(),
            p_residentailId:$('#p_residentailId').val(),
            p_band:$('#p_band').val(),
            p_areaId:'${areaId}',
            p_areaType:'${areaType}'
        };
        tableObj.reload({where:params,page:{curr:1}});
    }
    function syncInfo(){
        layer.confirm("确认同步用户?",function(){
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                type:"post",
                url :"${ctx}/card/syncInfo.shtml",
                data:{deviceId:${areaId},flag:2},
                success:function(data){
                    layer.close(index);
                    if (data.ret==0){
                        layer.msg("操作成功!");

                    } else{
                        layer.msg(data.msg);
                    }
                },
                error:function(){
                    layer.close(index);
                    layer.msg("服务器开小差了!");
                }
            });
        });
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
        layer_show("绑定",'${ctx}/card/toAddBandCard.shtml?type=qBand','800','400');
    }
</script>
<script type="text/html" id="r_cardType">
    {{# if(d.cardType == 1){ }}类型I{{# }}}
    {{# if(d.cardType == 2){ }}类型II{{# }}}
</script>
<script type="text/html" id="r_cardStatus">
    {{# if(d.cardStatus == 0){ }}未绑定{{# }}}
    {{# if(d.cardStatus == 1){ }}有效{{# }}}
    {{# if(d.cardStatus == 2){ }}失效{{# }}}
    {{# if(d.cardStatus == 3){ }}停用{{# }}}
</script>
<script type="text/html" id="r_bandStatus">
{{#  if(d.bandStatus === '未绑定'){ }}
    <span style="color: #ff0000;">{{ d.bandStatus }}</span>
  {{#  } else { }}
    {{ d.bandStatus }}
  {{#  } }}
</script>
</body>
</html>