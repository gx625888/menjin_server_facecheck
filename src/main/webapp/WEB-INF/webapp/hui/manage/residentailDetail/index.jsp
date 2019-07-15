<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common/resource.jsp" %>
    <title>小区详情</title>

    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
</head>
<body style="padding:0 20px;">
<div >
    <div class="layui-side " >

        <div class="layui-collapse"  id="treeStart" lay-accordion>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">1栋--%>
               <%--</h2>--%>
                <%--<div class="layui-colla-content layui-show">--%>
                    <%--<ul class="layui-timeline">--%>
                        <%--<li class="layui-timeline-item">--%>
                            <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                            <%--<div class="layui-timeline-content layui-text">--%>
                                <%--<div class="layui-timeline-title">1单元--%>
                                    <%--<div class="layui-btn-group" style="float: right">--%>
                                        <%--<button class="layui-btn layui-btn-primary layui-btn-sm">--%>
                                            <%--<i class="layui-icon">&#xe642;</i>--%>
                                        <%--</button>--%>
                                        <%--<button class="layui-btn layui-btn-primary layui-btn-sm">--%>
                                            <%--<i class="layui-icon">&#xe640;</i>--%>
                                        <%--</button>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li class="layui-timeline-item">--%>
                            <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                            <%--<div class="layui-timeline-content layui-text">--%>
                                <%--<div class="layui-timeline-title">2单元</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li class="layui-timeline-item">--%>
                            <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                            <%--<div class="layui-timeline-content layui-text">--%>
                                <%--<div class="layui-timeline-title">3单元</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                        <%--<li class="layui-timeline-item">--%>
                            <%--<i class="layui-icon layui-timeline-axis"></i>--%>
                            <%--<div class="layui-timeline-content layui-text">--%>
                                <%--<div class="layui-timeline-title">4单元</div>--%>
                            <%--</div>--%>
                        <%--</li>--%>
                    <%--</ul>--%>
                    <%--<button class="layui-btn layui-btn-sm layui-btn-normal">新增单元</button>--%>
                    <%--<button class="layui-btn layui-btn-sm layui-btn-normal">删除该栋</button>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">2栋</h2>--%>
                <%--<div class="layui-colla-content ">内容区域</div>--%>
            <%--</div>--%>
            <%--<div class="layui-colla-item">--%>
                <%--<h2 class="layui-colla-title">3栋</h2>--%>
                <%--<div class="layui-colla-content ">--%>
                    <%--${buildUnits}--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>

        <div style="margin: 20px">
            <button class="layui-btn layui-btn-sm layui-btn-normal" onclick="addBuild()">新增楼栋</button>

        </div>

    </div>

    <div class="layui-body" id="mainframe">
        <!-- 内容主体区域 -->
        点击单元查看详细信息
        <%--<iframe scrolling="yes" frameborder="0" src="${ctx}/residentailDetail/unitIndex.shtml"  style="    position: absolute; bottom: 0; height: 100%;width: 100%;"></iframe>--%>
    </div>

    </div>


<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script>
    var residentailId = '${residentailId}';
    $(document).ready(function(){
        initMenus(true);
        layui.use('element', function(){
            var element = layui.element;
            element.render();

            element.on('collapse', function(data){
               if(data.show){
                   currShow = $(data.title).data('id');
               }
            });
        });
    });
    function reload(){
        initMenus(false);
        layui.use('element', function(){
            var element = layui.element;
            element.render();

            element.on('collapse', function(data){
                if(data.show){
                    currShow = $(data.title).data('id');
                }
            });
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

        var tree = new treeUtil(menus,'id','parentId').toTree();
        var html = "";
        if (tree.length==0){
            $("#treeStart").html("暂无楼栋信息,点击新增!");
        }
        for (var i=0;i<tree.length;i++){
            var obj = tree[i];
            if (first&&i==0){
                currShow = obj.id;
            }
            html += getHtmlBuild(obj,i);
            $("#treeStart").html(html);
        }

    }
    var currShow ;
    function getHtmlBuild(obj,index){
        var html = "";
        var show ="";
        if (obj.id==currShow){
            show = "layui-show";
        }
        html += '<div class="layui-colla-item"> ' +
            '                <h2 class="layui-colla-title" data-id = "'+obj.id+'">' + obj.name+
            '               </h2> ' +
            '                <div class="layui-colla-content '+show+'">'+getHtmlUnits(obj.children,obj.id)+
            '<button onclick="addUnit('+obj.id+',\''+obj.name+'\')" class="layui-btn layui-btn-sm layui-btn-normal">新增单元</button>' +
            '                    <button onclick="delBuild('+obj.id+',\''+obj.name+'\')" class="layui-btn layui-btn-sm layui-btn-normal">删除该栋</button></div></div>';

        return html;

    }
    function getHtmlUnits(obj,buildId){
        var html="";
        if (obj==undefined||obj.length==0){
            return "<div>暂无单元信息</div>"
        } else{
            html+=' <ul class="layui-timeline">';
            for(var i=0 ;i<obj.length;i++){
                html+=getHtmlUnit(obj[i],buildId);
            }
            html+='</ul>';
        }
        return html;

    }
    function getHtmlUnit(obj,buildId){

        var html= "";

        html+='<li class="layui-timeline-item">' +
            '                            <i class="layui-icon layui-timeline-axis"></i>' +
            '                            <div class="layui-timeline-content layui-text">' +
            '                                <div class="layui-timeline-title">' +obj.name+
            '                                    <div class="layui-btn-group" style="float: right">' +
            '                                        <button onclick="viewUnit('+obj.id+','+buildId+')" class="layui-btn layui-btn-primary layui-btn-sm">' +
            '                                            <i class="layui-icon">&#xe642;</i>' +
            '                                        </button>' +
            '                                        <button onclick="delUnit('+obj.id+',\''+obj.name+'\')" class="layui-btn layui-btn-primary layui-btn-sm">' +
            '                                            <i class="layui-icon">&#xe640;</i>' +
            '                                        </button>' +
            '                                    </div>' +
            '                                </div>' +
            '                            </div>' +
            '                        </li>';
        return html;
    }
    var currOpenIndex;
    function addBuild(){
        currOpenIndex =  layer.open({
            type: 1,
            area: [400+'px', 200+'px'],
            fix: false, //不固定
            maxmin: false,
            shade:0.4,
            title:"添加楼栋",
            content: '<div class="layui-form-item">' +
            '        <input type="hidden" name ="id" value = "" >' +
            '        <label class="layui-form-label">名称</label>' +
            '        <div class="layui-input-block">' +
            '            <input type="text" id="addName" name="name" required style="width: 130px" lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">' +
            '        </div>' +
            '    </div>'+
                '<div class="layui-form-item">' +
            '    <div class="layui-input-block">' +
            '        <button onclick="submitAdd('+0+','+'1,\''+residentailId+'\')" class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>' +
            // '        <button type="reset" class="layui-btn layui-btn-primary">重置</button>' +
            '    </div></div>'
        });
    }
    function addUnit(buildId,name){
        currOpenIndex =  layer.open({
            type: 1,
            area: [400+'px', 200+'px'],
            fix: false, //不固定
            maxmin: false,
            shade:0.4,
            title:name + "添加单元",
            content: '<div class="layui-form-item">' +
            '        <input type="hidden" name ="id" value = "" >' +
            '        <label class="layui-form-label">名称</label>' +
            '        <div class="layui-input-block">' +
            '            <input type="text" id="addName" name="name" required style="width: 130px" lay-verify="required" placeholder="请输入名称" autocomplete="off" class="layui-input">' +
            '        </div>' +
            '    </div>'+
            '<div class="layui-form-item">' +
            '    <div class="layui-input-block">' +
            '        <button onclick="submitAdd('+buildId+','+'2,\''+residentailId+'\')" class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>' +
            // '        <button type="reset" class="layui-btn layui-btn-primary">重置</button>' +
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
    function viewUnit(unitId,buildId){
        $("#mainframe").html('<iframe scrolling="yes" frameborder="0" src="${ctx}/residentailDetail/unitIndex.shtml?id='+unitId+'&pid='+buildId+'"  style="position: absolute; bottom: 0; height: 100%;width: 100%;"></iframe>');
    }
    function delBuild(buildId,name){
        layer.confirm("确认删除"+name+"?",function(){
            doDel(buildId,'build');
        });
    }
    function delUnit(unitId,name){
        layer.confirm("确认删除"+name+"?",function(){
            doDel(unitId,'unit');
        });
    }
    function doDel(id,type){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            type:"post",
            url :"${ctx}/residentailDetail/del.shtml",
            data:{id:id,type:type},
            success:function(data){
                layer.close(index);
                if (data.ret==0){
                    removeFromMenuCopy(id);
                    reload();
                    layer.msg("操作成功!");

                } else if(data.ret==-1){
                    layer.msg(data.msg);
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
    function removeFromMenuCopy(id){
        for (var i=0;i<menuCopy.length;i++){
            if (id==menuCopy[i].id){
                menuCopy.splice(i,1);
                break;
            }
        }
    }

    function treeUtil(data,key,parentKey,map) {
        this.data=data;
        this.key=key;
        this.parentKey=parentKey;
        this.treeParentKey=parentKey;   //parentKey要转换成什么属性名称
        this.treeKey=key;           //key要转换成什么属性名称
        this.map=map;
        if(map){
            if(map[key])this.treeKey=map[key];
        }
        this.toTree=function () {
            var data=this.data;
            var pos={};
            var tree=[];
            var i=0;
            while(data.length!=0){
                if(data[i][this.parentKey]==0){
                    var _temp = this.copy(data[i]);
                    tree.push(_temp);
                    pos[data[i][this.key]]=[tree.length-1];
                    data.splice(i,1);
                    i--;
                }else{
                    var posArr=pos[data[i][this.parentKey]];
                    if(posArr!=undefined){
                        var obj=tree[posArr[0]];
                        for(var j=1;j<posArr.length;j++){
                            obj=obj.children[posArr[j]];
                        }
                        var _temp=this.copy(data[i]);
                        obj.children.push(_temp);
                        pos[data[i][this.key]]=posArr.concat([obj.children.length-1]);
                        data.splice(i,1);
                        i--;
                    }
                }
                i++;
                if(i>data.length-1){
                    i=0;
                }
            }
            return tree;
        }
        this.copy=function (item) {
            var _temp={
                children:[]
            };
            _temp[this.treeKey]=item[this.key];
            for(var _index in item){
                if(_index!=this.key && _index!=this.parentKey){
                    var _property = item[_index];
                    if((!!this.map) && this.map[_index])
                        _temp[this.map[_index]]=_property;
                    else
                        _temp[_index]=_property;
                }
            }
            return _temp;
        }
    }

</script>
</body>
</html>