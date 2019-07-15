<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" href="${ctx}/ui/lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="${ctx}/ui/lib/jquery/1.9.1/jquery.min.js"></script>
    <title>添加角色</title>
</head>
<body>

<ul id="demo"></ul>
<form style="margin-top: 20px" class="layui-form" action="" lay-filter="roleForm">
    <div class="layui-form-item">
        <label class="layui-form-label">选择权限</label>
        <div class="layui-input-block">
            <div id="LAY-auth-tree-index"></div>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" type="submit" lay-submit lay-filter="LAY-auth-tree-submit">提交</button>
            <button class="layui-btn layui-btn-primary" type="reset">重置</button>
        </div>
    </div>
   <%-- <div class="layui-form-item">
        <label class="layui-form-label">密码框</label>
        <div class="layui-input-inline">
            <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid layui-word-aux">辅助文字</div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">选择框</label>
        <div class="layui-input-block">
            <select name="city" lay-verify="required">
                <option value=""></option>
                <option value="0">北京</option>
                <option value="1">上海</option>
                <option value="2">广州</option>
                <option value="3">深圳</option>
                <option value="4">杭州</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">复选框</label>
        <div class="layui-input-block">
            <input type="checkbox" name="like[write]" title="写作">
            <input type="checkbox" name="like[read]" title="阅读" checked>
            <input type="checkbox" name="like[dai]" title="发呆">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">开关</label>
        <div class="layui-input-block">
            <input type="checkbox" name="switch" lay-skin="switch">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">单选框</label>
        <div class="layui-input-block">
            <input type="radio" name="sex" value="男" title="男">
            <input type="radio" name="sex" value="女" title="女" checked>
        </div>
    </div>
    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">文本域</label>
        <div class="layui-input-block">
            <textarea name="desc" placeholder="请输入内容" class="layui-textarea"></textarea>
        </div>
    </div>
   --%>
    <%--<div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" lay-submit lay-filter="formDemo">立即提交</button>
        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
    </div>
</div>--%>
</form>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script>

   /* layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(formDemo)', function(data){
            //layer.msg(JSON.stringify(data.field));
            $.ajax({
                type:"post",
                url :"${ctx}/manageRole/save.shtml",
                data:data.field,
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.tableObj.reload();
                        parent.layer.close(index);
                    } else{
                        layer.msg("服务器开小差了!");
                    }
                },
                error:function(){
                    layer.msg("服务器开小差了!");
                }
            });

            return false;
        });
    });*/
   var menus = ${menus};


    layui.config({
        base: '${ctx}/ui/lib/layui/lay/modules/'//模块存放的目录
    }).extend({
        authtree: 'authtree',
    });
    layui.use(['jquery', 'authtree', 'form', 'layer'], function(){
        var $ = layui.jquery;
        var authtree = layui.authtree;
        var form = layui.form;
        var layer = layui.layer;

        form.on('checkbox(lay-check-auth)', function(data){
            // 获取所有节点
            var all = authtree.getAll('#LAY-auth-tree-index');
            console.log('all', all);
            // 获取所有已选中节点
            var checked = authtree.getChecked('#LAY-auth-tree-index');
            console.log('checked', checked);
            // 获取所有未选中节点
            var notchecked = authtree.getNotChecked('#LAY-auth-tree-index');
            console.log('notchecked', notchecked);
            // 注意这里：需要等待事件冒泡完成，不然获取叶子节点不准确。
            setTimeout(function(){
                // 获取选中的叶子节点
                var leaf = authtree.getLeaf('#LAY-auth-tree-index');
                console.log('leaf', leaf);
            }, 100);
        });

        authtree.render('#LAY-auth-tree-index', initMenus(), {inputname: 'authids[]', layfilter: 'lay-check-auth', openall: false});
        // 初始化
        // 监听自定义lay-filter选中状态，PS:layui现在不支持多次监听，所以扩展里边只能改变触发逻辑，然后引起了事件冒泡延迟的BUG，要是谁有好的建议可以反馈我
        /*form.on('checkbox(lay-check-auth)', function(data){
            // 获取所有节点
            var all = authtree.getAll('#LAY-auth-tree-index');
            console.log('all', all);
            // 获取所有已选中节点
            var checked = authtree.getChecked('#LAY-auth-tree-index');
            console.log('checked', checked);
            // 获取所有未选中节点
            var notchecked = authtree.getNotChecked('#LAY-auth-tree-index');
            console.log('notchecked', notchecked);
            // 注意这里：需要等待事件冒泡完成，不然获取叶子节点不准确。
            setTimeout(function(){
                // 获取选中的叶子节点
                var leaf = authtree.getLeaf('#LAY-auth-tree-index');
                console.log('leaf', leaf);
            }, 100);
        });*/
        form.on('submit(LAY-auth-tree-submit)', function(obj){
            var authids = authtree.getChecked('#LAY-auth-tree-index');
            $.ajax({
                type:"post",
                url :"${ctx}/manageRole/saveRoleMenu.shtml",
                data:{roleId:'${roleId}',menus:authids.join(",")},
                success:function(data){
                    if (data.ret==0){
                        parent.layer.msg("操作成功!");
                        var index = parent.layer.getFrameIndex(window.name);
                       // parent.tableObj.reload();
                        parent.layer.close(index);
                    } else{
                        layer.msg("服务器开小差了!");
                    }
                },
                error:function(){
                    layer.msg("服务器开小差了!");
                }
            });
            return false;
        });
    });




   function initMenus(){
       var tree = new treeUtil(menus,'id','parentId');
       treeMenu = tree.toTree();
       var treeJsonObj=[];
       for (var i=0;i<treeMenu.length;i++){
           var obj = treeMenu[i];
           var temp =  {"name": obj.name, "value": obj.id, "checked": obj.modularId !=undefined,"list":[]};
           treeJsonObj.push(temp);
           for(var j=0;j<obj.children.length ;j++){
               var child = obj.children[j];
               var temp2 = {"name": child.name, "value": child.id, "checked": child.modularId !=undefined};
               temp.list.push(temp2);
           }
       }
      return treeJsonObj;

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