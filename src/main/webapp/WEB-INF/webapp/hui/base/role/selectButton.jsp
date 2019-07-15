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
</form>
<script type="text/javascript" src="${ctx}/ui/lib/layui/layui.all.js"></script>
<script>

   var privileges = ${privileges};


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


        authtree.render('#LAY-auth-tree-index', initMenus(), {inputname: 'authids[]', layfilter: 'lay-check-auth', openall: true});
        form.on('submit(LAY-auth-tree-submit)', function(obj){
            var authids = authtree.getChecked('#LAY-auth-tree-index');
            $.ajax({
                type:"post",
                url :"${ctx}/manageRole/saveRolePrivilege.shtml",
                data:{roleId:'${roleId}',privilege:authids.join(",")},
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
       var tree = new treeUtil(privileges,'privilegeCode','modularId');
       treeMenu = tree.toTree();
       var treeJsonObj=[];
       for (var i=0;i<treeMenu.length;i++){
           var obj = treeMenu[i];
           var temp =  {"name": obj.privilegeName, "value": obj.privilegeCode, "checked": obj.hasPrivilege !=undefined,"list":[]};
           treeJsonObj.push(temp);
           for(var j=0;j<obj.children.length ;j++){
               var child = obj.children[j];
               var temp2 = {"name": child.privilegeName, "value": child.privilegeCode, "checked": child.hasPrivilege !=undefined,"list":[]};
               temp.list.push(temp2);
               nextlevel(temp2.list,child);
           }
       }
      return treeJsonObj;

   }
   function nextlevel(arr,obj){
       for(var j=0;j<obj.children.length ;j++){
           var child = obj.children[j];
           var temp2 = {"name": child.privilegeName, "value": child.privilegeCode, "checked": child.hasPrivilege !=undefined};
           arr.push(temp2);
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