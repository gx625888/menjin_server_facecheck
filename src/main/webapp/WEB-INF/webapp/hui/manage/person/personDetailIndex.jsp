<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <script language="javascript" type="text/javascript" src="${ctx}/script/My97DatePicker/WdatePicker.js"></script>
    <title>居民详情</title>
</head>
<body>
<div class="layui-colla-content layui-show">
    <form>
        <fieldset>
            <legend>
                <span>基本信息</span>
            </legend>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">居民姓名:</label>
                    <div class="layui-form-mid layui-word-aux">${person.name}</div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">证件类型:</label>
                    <c:if test="${person.cardType==1}">
                        <div class="layui-form-mid layui-word-aux">身份证</div>
                    </c:if>
                    <c:if test="${person.cardType==2}">
                        <div class="layui-form-mid layui-word-aux">护照</div>
                    </c:if>
                    <c:if test="${person.cardType==3}">
                        <div class="layui-form-mid layui-word-aux">军人证</div>
                    </c:if>
                    <c:if test="${person.cardType==4}">
                        <div class="layui-form-mid layui-word-aux">其他证件</div>
                    </c:if>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">证件编号:</label>
                    <div class="layui-form-mid layui-word-aux">${person.cardNo}</div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">出生日期:</label>
                    <div class="layui-form-mid layui-word-aux">${person.birthday}</div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">手机号码:</label>
                    <div class="layui-form-mid layui-word-aux">${person.phone}</div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">性别:</label>
                    <c:if test="${person.sex==1}">
                        <div class="layui-form-mid layui-word-aux">女</div>
                    </c:if>
                    <c:if test="${person.sex==2}">
                        <div class="layui-form-mid layui-word-aux">男</div>
                    </c:if>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">国家:</label>
                    <div class="layui-form-mid layui-word-aux">${person.country}</div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">民族:</label>
                    <div class="layui-form-mid layui-word-aux">${person.nation}</div>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">关注类型:</label>
                    <c:if test="${person.type==1}">
                        <div class="layui-form-mid layui-word-aux">非关注人群</div>
                    </c:if>
                    <c:if test="${person.type==2}">
                        <div class="layui-form-mid layui-word-aux">独居老人</div>
                    </c:if>
                    <c:if test="${person.type==3}">
                        <div class="layui-form-mid layui-word-aux">残障人士</div>
                    </c:if>
                    <c:if test="${person.type==4}">
                        <div class="layui-form-mid layui-word-aux">涉案人员</div>
                    </c:if>
                    <c:if test="${person.type==5}">
                        <div class="layui-form-mid layui-word-aux">涉毒人员</div>
                    </c:if>
                    <c:if test="${person.type==6}">
                        <div class="layui-form-mid layui-word-aux">涉黑人员</div>
                    </c:if>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">地址:</label>
                    <div class="layui-form-mid layui-word-aux">${person.address}</div>
                </div>
            </div>
        </fieldset>
    </form>
    <span>【小区信息】</span>
    <table id="dataTable" lay-filter="test" >

    </table>
    <span>【门禁卡信息】</span>
    <table id="dataTable_c" lay-filter="test_c" >

    </table>



</div>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">解绑</a>
</script>
<script type="text/html" id="barDemo_c">
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">挂失</a>
</script>
<script>
    var tableObj;
    var tableObj_c;
    layui.select
    layui.use('table', function(){
        var table = layui.table;
        tableObj = table.render({
            elem: '#dataTable'

            ,height: 300
            ,even:true
            ,url: '${ctx}/person/ResidentailPage.shtml?id=${person.id}' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'person', title: 'person', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'house', title: 'house', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'residentail', title: '小区', width:200}
                ,{field: 'build', title: '楼栋', width:200}
                ,{field: 'unit', title: '单元', width:200}
                ,{field: 'name', title: '房号', width:200}
                ,{field: 'wealth', title: '操作', width: 190, sort: false,toolbar:'#barDemo'}
            ]]

        });
        tableObj_c = table.render({
            elem: '#dataTable_c'

            ,height: 300
            ,even:true
            ,url: '${ctx}/person/CardPage.shtml?id=${person.id}' //数据接口
            ,page: true //开启分页
            ,method: 'post'
            ,cols: [[ //表头
                {field: 'id', title: 'id', width:80, sort: false, fixed: 'left', hide:true}
                ,{field: 'cardId', title: '卡号', width:280}
                ,{field: 'invalidDate', title: '有效期', width:280}
                ,{field: 'cardStatus', title: '状态', width:280,templet:"#r_cardStatus"}
                ,{field: 'wealth', title: '操作', width: 150, sort: false,toolbar:'#barDemo_c'}
            ]]

        });
        table.on('tool(test)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确认解绑?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/person/cancelBand.shtml",
                        data:{person:obj.data.person,house:obj.data.house},
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
        table.on('tool(test_c)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data; //获得当前行数据
            var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr; //获得当前行 tr 的DOM对象
            if(layEvent === 'detail'){ //查看
                //do somehing
            } else if(layEvent === 'del'){ //删除
                layer.confirm('确认挂失?', function(index){
                    $.ajax({
                        type:"post",
                        url :"${ctx}/person/cardLoseStatus.shtml",
                        data:{id:obj.data.id},
                        success:function(data){
                            layer.close(index);
                            if (data.ret==0){
                                layer.msg("操作成功!")
                                tableObj_c.reload();
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
</script>
<script type="text/html" id="r_cardStatus">
    {{# if(d.cardStatus == 1){ }}有效{{# }}}
    {{# if(d.cardStatus == 2){ }}失效{{# }}}
    {{# if(d.cardStatus == 3){ }}停用{{# }}}
</script>
</body>
</html>