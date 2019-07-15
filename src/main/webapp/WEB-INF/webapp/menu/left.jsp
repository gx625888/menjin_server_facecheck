<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.lvhetech.com/privilege-taglib" prefix="p" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<title>左边菜单树</title>
<script src="../script/jquery2.1.1/jquery.js" type="text/javascript"></script>
<script src="../script/js/jquery-accordion-menu.js" type="text/javascript"></script>
<link href="../styles/jquery-accordion-menu.css" rel="stylesheet" type="text/css" />
<link href="../styles/font-awesome.css" rel="stylesheet" type="text/css" />
<style type="text/css">
*{box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box;margin:0;padding:0;}
body{background:#292929;}
.content{width:100%;}
.filterinput{
	background-color:rgba(249, 244, 244, 0);
	border-radius:15px;
	width:100%;
	border:thin solid #FFF;
	text-indent:0.5em;
	font-weight:bold;
	color:#FFF;
}
#demo-list a{
	overflow:hidden;
	text-overflow:ellipsis;
	-o-text-overflow:ellipsis;
	white-space:nowrap;
	width:100%;
}
</style>
<script type="text/javascript">


	/*
	function filterList(header, list) {
		var form = $("<form>").attr({
			"class":"filterform",
			action:"#"
		}), input = $("<input>").attr({
			"class":"filterinput",
			type:"text"
		});
		$(form).append(input).appendTo(header);
		$(input).change(function() {
			var filter = $(this).val();
			if (filter) {
				$matches = $(list).find("a:Contains(" + filter + ")").parent();
				$("li", list).not($matches).slideUp();
				$matches.slideDown();
			} else {
				$(list).find("li").slideDown();
			}
			return false;
		}).keyup(function() {
			$(this).change();
		});
	}
	$(function() {
		$.expr[":"].Contains = function(a, i, m) {
			return (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0;
		};
		filterList($("#form"), $("#demo-list"));
		$("#jquery-accordion-menu").jqueryAccordionMenu();
	});*/

	function showMenu(_this,event,level){
		 $("#demo-list li.active").removeClass("active");
		 $(_this).addClass("active");
		 if(level == 1){
			 $('ul.submenu').find('li').remove();
			 $('ul.submenu').hide();
		 }
		 var modularId=$(_this).children('a').attr("id");
		 $('#'+modularId).siblings('ul.submenu').find('li').size() > 0 ? $('#'+modularId).siblings('ul.submenu').find('li').remove() : nextli(modularId);
		 event.stopPropagation();
	}
function nextli(modularId){
	$.ajax({
        url: "../menu/menu.shtml?modularId=" + modularId,
        type: "post",
        dataType : "json", 
		cache:false,
		async:false,
        success : function(data){
        	var list=data.jsonArray;
        	 for (var i = 0; i < list.length; i++) {
        			var url = list[i].url ? list[i].url : 'javascript:void(0);';
        			if(list[i].url==""){
        				$('#'+modularId).siblings('ul.submenu').append("<li onclick='showMenu(this,event)' ><a  class='submenu-indicator-minus' href='"+ url  +"' id='"+list[i].modularId+"'>"+list[i].name+"<span class='submenu-indicator'>+</span></a><ul class='submenu'></ul></li>");
        			}else{
        				$('#'+modularId).siblings('ul.submenu').append("<li onclick='showMenu(this,event)' ><a   href='"+ url  +"' id='"+list[i].modularId+"' target='main'><img src='../images/arrow.png' width='10'>  "+list[i].name+"</a><ul class='submenu'></ul></li>");
        			}
			}
			$('#'+modularId).siblings('ul.submenu').show();
        }
    }); 
}

</script>
</head>
<body>
<div class="content">
	<div id="jquery-accordion-menu" class="jquery-accordion-menu black">
		<ul id="demo-list">
		 <p:auth privCodeList="manage" relationFlag="or">
		   <li onclick="showMenu(this,event,1)"><a href="#" id="1"><i class="fa fa-bank" value=''></i>管理服务 </a>
		   <ul class="submenu"> </ul>
		   </li>
		   </p:auth>
		    <p:auth privCodeList="statement" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="58"><i class="fa fa-file-text"></i>报表</a>
			<ul class="submenu"> </ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="weixinbusiness" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="57"><i class="fa fa-weixin"></i>微信业务 </a>
			<ul class="submenu"> </ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="settings" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="55"><i class="fa fa-gears"></i> 设置</a>
				<ul class="submenu"> 
				</ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="marketing" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="56"><i class="fa fa-sitemap"></i>营销 </a>
			<ul class="submenu"> </ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="checkstand" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="50"><i class="fa fa-desktop"></i>收银台</a>
			<ul class="submenu" > </ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="members" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="51" ><i class="fa fa-user"></i>会员管理</a>
			<ul class="submenu"> </ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="reserve" relationFlag="or">
			<li onclick="showMenu(this,event,1)"><a href="#" id="52"><i class="fa fa-clock-o"></i>预定</a>
			<ul class="submenu"> </ul>
			</li>
			</p:auth>
			 <p:auth privCodeList="takenum" relationFlag="or">
		   <li onclick="showMenu(this,event,1)"><a href="#" id="53"><i class="fa fa-table"></i>取号</a>
		   <ul class="submenu"> </ul>
		   </li>
		   </p:auth>
		    <p:auth privCodeList="orderdishs" relationFlag="or">
		   <li onclick="showMenu(this,event,1)"><a href="#" id="54"><i class="fa fa-cutlery"></i>点菜</a>
		   <ul class="submenu"> </ul>
		   </li>
		   </p:auth>
		</ul>
	</div>
</div>
</body>
</html>
