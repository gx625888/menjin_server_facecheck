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

<%--<div class="page-container">--%>

    <div class="layui-collapse">
        <div class="layui-colla-item">
            <h2 class="layui-colla-title ">基本信息  </h2>
            <div class="layui-colla-content layui-show">
                <%--<form style="margin-top: 20px" class="layui-form" action="" lay-filter="roleForm">--%>

                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">楼栋单元:</label>
                            <div class="layui-form-mid layui-word-aux">${build.name}&nbsp;${unit.name}</div>
                        </div>
                        <c:if test="${empty device}">
                            <div class="layui-inline">
                                <label class="layui-form-label">设备编号:</label>
                                <div class="layui-form-mid layui-word-aux">未绑定</div>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="bindDevice()">绑定新设备</button>
                            </div>

                        </c:if>
                        <c:if test="${not empty device}">
                            <div class="layui-inline">
                                <label class="layui-form-label">设备编号:</label>
                                <div class="layui-form-mid layui-word-aux">${device.deviceId}</div>
                            </div>
                            <div class="layui-inline">
                                <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="unBindDevice()">解绑设备</button>
                            </div>
                        </c:if>



                    </div>
                    <div class="layui-form-item">
                        <c:if test="${empty device}">
                            <div class="layui-inline">
                                <label class="layui-form-label">设备状态:</label>
                                <div class="layui-form-mid layui-word-aux">未绑定</div>
                            </div>
                        </c:if>
                        <c:if test="${not empty device}">
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
                                    <button class="layui-btn layui-btn-sm  layui-btn-disabled"  >开门</button>
                                    <button class="layui-btn layui-btn-sm  layui-btn-disabled" >刷新资源</button>
                                    <button class="layui-btn layui-btn-sm  layui-btn-disabled" >同步用户</button>
                                    <button class="layui-btn layui-btn-sm  layui-btn-disabled" >重启设备</button>
                                </div>
                            </c:if>

                        </c:if>

                    </div>
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="addBuild()">新增楼层</button>
                            <button class="layui-btn layui-btn-sm layui-btn-normal" id="batchImport">批量导入</button>
                            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="downloadTemplet()" >下载模版</button>
                        </div>
                    </div>

               <%-- </form>--%>
        </div>
     </div>
    </div>
     <div class="layui-collapse" id="treeStart">
        <div class="layui-colla-item">
            <h2 class="layui-colla-title">1楼
                <div class="layui-btn-group" style="float: right">
                    <button class="layui-btn layui-btn-xs" onclick="addHouse(event)">添加住户</button>
                    <button class="layui-btn layui-btn-xs layui-btn-danger">删除楼层</button>
                </div>
            </h2>

            <div class="layui-colla-content " style="padding: 0">
                <table id="dataTable1" lay-filter="test" >

                </table>
            </div>
        </div>
        <div class="layui-colla-item">
            <h2 class="layui-colla-title">2楼
                <div class="layui-btn-group" style="float: right">
                    <button class="layui-btn layui-btn-xs" onclick="addHouse(event)">添加住户</button>
                    <button class="layui-btn layui-btn-xs layui-btn-danger">删除楼层</button>
                </div>
            </h2>
            <div class="layui-colla-content " style="padding: 0">
                <table id="dataTable2" lay-filter="test" >

                </table>
            </div>
        </div>
    </div>






<%--</div>--%>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>

<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>

    <a class="layui-btn layui-btn-xs" lay-event="admember">添加未录入居民</a>

    <a class="layui-btn layui-btn-xs" lay-event="selmember">添加已录入居民</a>

    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除住户</a>
</script>

<script type="text/javascript">
    layui.use('upload', function(){
        var upload = layui.upload;


        //执行实例
        var uploadInst = upload.render({
            elem: '#batchImport' //绑定元素
            ,url: '${ctx}/residentailDetail/uploadFile.shtml?unitId=${unit.id}' //上传接口
            ,accept: 'file'
            ,exts:  'xlsx|xls'
            ,before:function() {
                var index = layer.load(1, {
                    shade: [0.1,'#fff'] //0.1透明度的白色背景
                });
            }
            ,done: function(res){
                if(res.ret==0){
                    parent.layer.msg(res.msg);
                    window.location.href = window.location.href;
                }
            }
            ,error: function(){
                //请求异常回调
                layer.close(index);
                layer.msg("上传失败!");
            }
        });
    });
    $(document).ready(function(){
       initMenus(true);
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
    function downloadTemplet() {
        window.location.href = "${ctx}/templet/template.xlsx";
    }
    function reload(){
        initMenus(false);
        tableLoadMap = {};
        layui.use('element', function(){
            var element = layui.element;
            element.render();

            element.on('collapse', function(data){
                if(data.show){
                    loadTable($(data.title).data('id'));
                }
            });
        });
    }
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

    function addBuild(){
        currOpenIndex =  layer.open({
            type: 1,
            area: [400+'px', 200+'px'],
            fix: false, //不固定
            maxmin: false,
            shade:0.4,
            title:"${build.name} ${unit.name}  添加楼层",
            content: '<div class="layui-form-item">' +
            '        <input type="hidden" name ="id" value = "" >' +
            '        <label class="layui-form-label">名称</label>' +
            '        <div class="layui-input-block">' +
            '            <input type="text" id="addName" name="name" required style="width: 130px" lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">' +
            '        </div>' +
            '    </div>'+
            '<div class="layui-form-item">' +
            '    <div class="layui-input-block">' +
            '        <button onclick="submitAdd(\'${unit.id}\',3,\'${unit.residentailId}\')" class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>' +
            '    </div></div>'
        });
    }

    function submitAdd(parentId,type,residentailId){
        var value = $("#addName").val().trim();
        if (value.length==0){
            layer.msg('请填写名称!');
            return ;
        }
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        var menuObj = {parentId:parentId,type:type,residentailId:residentailId,name:value};
        $.ajax({
            type:"post",
            url :"${ctx}/residentailDetail/add.shtml",
            data:menuObj,
            success:function(data){
                layer.close(index);
                if (data.ret==0){
                    layer.close(currOpenIndex);
                    menuObj.id = data.id;
                    menuCopy.push(menuObj);
                    reload();
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

    }

    var menus = ${buildUnits};
    var menuCopy;
    function initMenus(first){
        if(menuCopy!=null){
            menus = menuCopy.slice();
        }else{
            menuCopy = menus.slice();
        }

        var html = "";
        if (menus.length==0){
            $("#treeStart").html("暂无楼层信息,点击新增!");
        }
        for (var i=0;i<menus.length;i++){
            var obj = menus[i];
            html += getHtmlBuild(obj,i);
            $("#treeStart").html(html);
        }

    }
    function getHtmlBuild(obj,i){
        var html = '<div class="layui-colla-item">' +
            '            <h2 class="layui-colla-title" data-id="'+obj.id+'">' +obj.name +
            '                <div class="layui-btn-group" style="float: right">' +
            '                    <button class="layui-btn layui-btn-xs" onclick="addHouse(event,'+obj.id+')">添加住户</button>' +
            '                    <button class="layui-btn layui-btn-xs layui-btn-danger" onclick="delHouse(event,'+obj.id+')">删除楼层</button>' +
            '                </div>' +
            '            </h2>' +
            '' +
            '            <div class="layui-colla-content " style="padding: 0">' +
            '                <table id="dataTable-'+obj.id+'" lay-filter="filter-'+obj.id+'" >' +
            '                </table>' +
            '            </div>' +
            '        </div>';
        return html;
    }


    function addHouse(e,id){
        e.stopPropagation();

        layer.open({
            type: 2,
            area: ['400px','400px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '添加住户',
            content: '${ctx}/mangeHouse/toAdd.shtml?pid='+id
        });
    }
    function delHouse(e,id){
        e.stopPropagation();
        layer.confirm('确认删除该楼层?',function(){
            doDel(id);
        });


    }
    function doDel(id){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            type:"post",
            url :"${ctx}/residentailDetail/del.shtml",
            data:{id:id},
            success:function(data){
                layer.close(index);
                if (data.ret==0){
                    removeFromMenuCopy(id);
                    reload();
                    layer.msg("操作成功!");

                } else if (data.ret==-1){
                    layer.msg(data.msg);
                }else{
                    layer.msg("服务器开小差了!");
                }
            },
            error:function(){
                layer.close(index);
                layer.msg("服务器开小差了!");
            }
        });
    }
    function removeFromMenuCopy(id){
        for (var i=0;i<menuCopy.length;i++){
            if (id==menuCopy[i].id){
                menuCopy.splice(i,1);
                break;
            }
        }
    }


    var tableLoadMap = {};
    function loadTable(id){
        if(id==undefined || id ==null){
            return ;
        }
        if(tableLoadMap[id]){
            return ;
        }

        layui.use('table', function(){
            var table = layui.table;
            tableLoadMap[id]  = table.render({
                elem: '#dataTable-'+id
                ,height: '250'
                ,even:true
                ,url: '${ctx}/mangeHouse/page.shtml?unitId='+id //数据接口
                ,page: false //开启分页
                ,method: 'post'
                ,cols: [[ //表头
                    {field: 'name', title: '门号', width:200}
                    ,{field: 'phone', title: '开门电话', width:150, sort: false}
                    ,{field: 'phoneTwo', title: '开门电话2', width:150, sort: false}
                    ,{field: 'phoneThr', title: '开门电话3', width:150, sort: false}
                    ,{field: 'level', title: '人员', width:150, sort: false,templet: function(d){
                            return '<a style="color: blue" href="javascript:;" onclick="show_person(\''+d.name+'\','+d.id+')">'+d.level+'</a>'
                        }}
                    ,{field: 'status', title: '状态', width:150, sort: false,templet: function(d){
                            if(d.status==1){
                                return '左开门';
                            }
                            if(d.status==2){
                                return '右开门';
                            }
                            return '全开门'
                        }}
                    ,{field: 'wealth', title: '操作', width: 365, sort: false,toolbar:'#barDemo'}
                ]]

            });
            table.on('tool(filter-'+id+')', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
                var data = obj.data; //获得当前行数据
                var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
                var tr = obj.tr; //获得当前行 tr 的DOM对象
                if(layEvent === 'del'){ //删除
                    layer.confirm('确认删除?', function(index){
                        $.ajax({
                            type:"post",
                            url :"${ctx}/mangeHouse/del.shtml",
                            data:{id:obj.data.id},
                            success:function(data){
                                layer.close(index);
                                if (data.ret==0){
                                    layer.msg("操作成功!")
                                    tableLoadMap[id].reload();
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
                    layer_show("编辑",'${ctx}/mangeHouse/toAdd.shtml?id='+obj.data.id+'&pid='+id,'400','400');
                }else if (layEvent ==='admember'){
                    add_person('添加居民','${ctx}/person/toAdd.shtml?bind=1&houseId='+obj.data.id+'&pid='+id,'450','550')
                }else if(layEvent ==='selmember'){
                    select_person('选择居民','${ctx}/residentailDetail/selectPerson.shtml?houseId='+obj.data.id+'&pid='+id,'750','450')
                }
            });
        });
    }

    function show_person(name,id){
        layer_show(name+" 人员信息","${ctx}/person/toPersonList.shtml?houseId="+id,800,500);
    }

    function add_person(title,url,w,h){
        layer_show(title,url,w,h);
    }
    function select_person(title,url,w,h){
        layer_show(title,url,w,h);
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
        layer_show("绑定设备",'${ctx}/mangeHouse/bindDevice.shtml?unitId=${unitId}','700','500');
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