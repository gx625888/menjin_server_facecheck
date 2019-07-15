<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>

	<%@ include file="common/resource.jsp" %>
	<title>管理后台</title>
</head>
<body>
<header class="navbar-wrapper">
	<div class="navbar navbar-fixed-top">
		<div class="container-fluid cl"> <a class="logo navbar-logo f-l mr-10 hidden-xs" href="/aboutHui.shtml">门禁管理系统</a> <a class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml">H-ui</a>
			<span class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0</span>
			<a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
			<nav class="nav navbar-nav">
				<%--<ul class="cl">
					<li class="dropDown dropDown_hover"><a href="javascript:;" class="dropDown_A"><i class="Hui-iconfont">&#xe600;</i> 新增 <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onclick="article_add('添加资讯','article-add.html')"><i class="Hui-iconfont">&#xe616;</i> 资讯</a></li>
							<li><a href="javascript:;" onclick="picture_add('添加资讯','picture-add.html')"><i class="Hui-iconfont">&#xe613;</i> 图片</a></li>
							<li><a href="javascript:;" onclick="product_add('添加资讯','product-add.html')"><i class="Hui-iconfont">&#xe620;</i> 产品</a></li>
							<li><a href="javascript:;" onclick="member_add('添加用户','member-add.html','','510')"><i class="Hui-iconfont">&#xe60d;</i> 用户</a></li>
						</ul>
					</li>
				</ul>--%>
			</nav>
			<nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
				<ul class="cl">
					<li>${ConsoleUser.name}</li>
					<li class="dropDown dropDown_hover">
						<a href="#" class="dropDown_A">admin <i class="Hui-iconfont">&#xe6d5;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" onClick="myselfinfo()">个人信息</a></li>
							<%--<li><a href="#">切换账户</a></li>--%>
							<li><a href="javascript:;" onclick="logout()">退出</a></li>
						</ul>
					</li>
					<%--<li id="Hui-msg"> <a href="#" title="消息"><span class="badge badge-danger">1</span><i class="Hui-iconfont" style="font-size:18px">&#xe68a;</i></a> </li>--%>
					<li id="Hui-skin" class="dropDown right dropDown_hover"> <a href="javascript:;" class="dropDown_A" title="换肤"><i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i></a>
						<ul class="dropDown-menu menu radius box-shadow">
							<li><a href="javascript:;" data-val="default" title="默认（黑色）">默认（黑色）</a></li>
							<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
							<li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
							<li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
							<li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
							<li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
						</ul>
					</li>
				</ul>
			</nav>
		</div>
	</div>
</header>
<aside class="Hui-aside">
	<div id='menu_div' class="menu_dropdown bk_2">

	</div>
</aside>
<div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<section class="Hui-article-box">
	<div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
		<div class="Hui-tabNav-wp">
			<ul id="min_title_list" class="acrossTab cl">
				<li class="active">
					<span title="首页" data-href="${ctx}/login/welcome.shtml">首页</span>
					<em></em></li>
			</ul>
		</div>
		<div class="Hui-tabNav-more btn-group"><a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d4;</i></a><a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;"><i class="Hui-iconfont">&#xe6d7;</i></a></div>
	</div>
	<div id="iframe_box" class="Hui-article">
		<div class="show_iframe">
			<div style="display:none" class="loading"></div>
			<iframe scrolling="yes" frameborder="0" src="${ctx}/login/welcome.shtml"></iframe>
		</div>
	</div>
</section>

<div class="contextMenu" id="Huiadminmenu">
	<ul>
		<li id="closethis">关闭当前 </li>
		<li id="closeall">关闭全部 </li>
	</ul>
</div>
<%@ include file="common/footer.jsp" %>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="${ctx}/ui/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
<script type="text/javascript">
	var menus = ${menus};
    var treeMenu ={};
    $(function(){
        initMenus(1);//初始化菜单
        /*左侧菜单*/
        $(".Hui-aside").Huifold({
            titCell:'.menu_dropdown dl dt',
            mainCell:'.menu_dropdown dl dd',
        });

        /*选项卡导航*/
        $(".Hui-aside").on("click",".menu_dropdown a",function(){
            Hui_admin_tab(this);
        });
        /*$("#min_title_list li").contextMenu('Huiadminmenu', {
            bindings: {
                'closethis': function(t) {
                    console.log(t);
                    if(t.find("i")){
                        t.find("i").trigger("click");
                    }
                },
                'closeall': function(t) {
                    alert('Trigger was '+t.id+'\nAction was Email');
                },
            }
        });*/
    });
    /*个人信息*/
    function myselfinfo(){
        layer.open({
            type: 2,
            area: ['600px','500px'],
            fix: false, //不固定
            maxmin: true,
            shade:0.4,
            title: '个人信息',
            content: '${ctx}/personCenter/toManagerPersonInfo.shtml'
        });
    }

    /*资讯-添加*/
    function article_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*图片-添加*/
    function picture_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*产品-添加*/
    function product_add(title,url){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*用户-添加*/
    function member_add(title,url,w,h){
        layer_show(title,url,w,h);
    }

    function logout() {
        layer.confirm("您确定要退出控制面板吗",function () {
            top.location = "../login/systemOut.shtml";
        });
       /* if (confirm("您确定要退出控制面板吗？"))

        return false;*/
    }
    function initMenus(startLeval){
        var tree = new treeUtil(menus,'id','parentId');
       treeMenu = tree.toTree();
       var html = '';
       for (var i=0;i<treeMenu.length;i++){
           var obj = treeMenu[i];
           html+='<dl id="menu-article">';
           html+='<dt><i class="Hui-iconfont">&#xe616;</i> '+obj.name+'<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>';
		   html+='<dd><ul>';
		   for(var j=0;j<obj.children.length ;j++){
		       var child = obj.children[j];
		       html+=' <li><a data-href="'+child.url+'" data-title="'+child.name+'" href="javascript:void(0)">'+child.name+'</a></li>';
		   }


		   html+='</ul></dd></dl>';
	   }
	   $('#menu_div').html(html);

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
  