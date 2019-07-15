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

    <table id="dataTable" lay-filter="test" >

    </table>
</div>
<%@ include file="../../common/footer.jsp" %>

<script type="text/html" id="barDemo">
    <%--<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>--%>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">解绑</a>


</script>

<script type="text/javascript">
    var tableObj;
    layui.select
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'
            ,where:{houseId:'${houseId}'}
            ,height: '450'
            ,even:true
            ,url: '${ctx}/person/page.shtml' //数据接口
            ,page: false //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'ID', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'name', title: '姓名', width:150}
                ,{field: 'sex', title: '性别', width:150,templet:"#r_sex"}
                ,{field: 'nation', title: '民族', width:150}
                ,{field: 'phone', title: '手机', width:150}
                ,{field: 'cardType', title: '证件类型', width:150,templet:"#r_cardType"}
                ,{field: 'cardNo', title: '证件编号', width:150}
                ,{field: 'liveType', title: '居住类型', width:150,templet:"#r_liveType"}
                ,{field: 'wealth', title: '操作', width: 100, sort: false,toolbar:'#barDemo',fixed:'right'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确认从该房间中删除该用户?', function(index){
                        $.ajax({
                            type:"post",
                            url :"${ctx}/person/cancelBand.shtml",
                            data:{person:data.id,house:'${houseId}'},
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
                layer_show("编辑",'${ctx}/person/toAdd.shtml?id='+obj.data.id,'450','550');

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
            p_bandStatus:$('#p_bandStatus').val()
        };
        tableObj.reload({where:params,page:{curr:1}});
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
    layui.use('upload', function(){
        var upload = layui.upload;

        //执行实例
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: '${ctx}/person/uploadFile.shtml' //上传接口
            ,accept: 'file'
            ,exts:  'xlsx|xls'
            ,done: function(res){
                if(res.ret==0){
                    parent.layer.msg(res.msg);
                    //formobj.render();
                    var params = {p_name:$('#p_name').val(),
                        p_phone:$('#p_phone').val(),
                        p_cardNo:$('#p_cardNo').val(),
                        p_bandStatus:$('#p_bandStatus').val()
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
    function toPersonDetail(id) {
        var index =  layer.open({
            type: 2,
            area: [900+'px', 650+'px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            content: '${ctx}/person/toPersonDetail.shtml?id='+id
        });
        layer.full(index);
    }
</script>
<script type="text/html" id="r_sex">
    {{# if(d.sex == 2){ }}男{{# }}}
    {{# if(d.sex == 1){ }}女{{# }}}
</script>
<script type="text/html" id="r_liveType">
    {{# if(d.liveType == 2){ }}租客{{# }}}
    {{# if(d.liveType == 1){ }}房主{{# }}}
</script>
<script type="text/html" id="r_cardType">
    {{# if(d.cardType == 1){ }}身份证{{# }}}
    {{# if(d.cardType == 2){ }}护照{{# }}}
    {{# if(d.cardType == 3){ }}军人证{{# }}}
    {{# if(d.cardType == 4){ }}其他证件{{# }}}
</script>
</body>
</html>