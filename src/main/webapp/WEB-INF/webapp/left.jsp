<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.lvhetech.com/privilege-taglib" prefix="p" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>管理页面</title>
<script src="../script/prototype.lite.js" type="text/javascript"></script>
<script src="../script/moo.fx.js" type="text/javascript"></script>
<script src="../script/moo.fx.pack.js" type="text/javascript"></script>
<style>
body {
	font: 12px Arial, Helvetica, sans-serif;
	color: #000;
	background-color: #EEF2FB;
	margin: 0px;
}

#container {
	width: 182px;
}

H1 {
	font-size: 12px;
	margin: 0px;
	width: 182px;
	cursor: pointer;
	height: 30px;
	line-height: 20px;
}

H1 a {
	display: block;
	width: 182px;
	color: #000;
	height: 30px;
	text-decoration: none;
	moz-outline-style: none;
	background-image: url(../images/system/menu_bgs.gif);
	background-repeat: no-repeat;
	line-height: 30px;
	text-align: center;
	margin: 0px;
	padding: 0px;
}

.content {
	width: 182px;
	height: 26px;
}

.MM ul {
	list-style-type: none;
	margin: 0px;
	padding: 0px;
	display: block;
}

.MM li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	list-style-type: none;
	display: block;
	text-decoration: none;
	height: 26px;
	width: 182px;
	padding-left: 0px;
}

.MM {
	width: 182px;
	margin: 0px;
	padding: 0px;
	left: 0px;
	top: 0px;
	right: 0px;
	bottom: 0px;
	clip: rect(0px, 0px, 0px, 0px);
}

.MM a:link {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(../images/system/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}

.MM a:visited {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(../images/system/menu_bg1.gif);
	background-repeat: no-repeat;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}

.MM a:active {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	color: #333333;
	background-image: url(../images/system/menu_bg1.gif);
	background-repeat: no-repeat;
	height: 26px;
	width: 182px;
	display: block;
	text-align: center;
	margin: 0px;
	padding: 0px;
	overflow: hidden;
	text-decoration: none;
}

.MM a:hover {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 12px;
	line-height: 26px;
	font-weight: bold;
	color: #006600;
  	background-image: url(../images/system/menu_bg2.gif); 
	background-repeat: no-repeat;
	text-align: center;
	display: block;
	margin: 0px;
	padding: 0px;
	height: 26px;
	width: 182px;
	text-decoration: none;
}
</style>
</head>
<body>
	<table width="100%" height="280" border="0" cellpadding="0" cellspacing="0" bgcolor="#EEF2FB">
		<tr>
			<td width="182" valign="top">
				<div id="container">
					<h1>
						<a href="#" style="text-align: left;padding-left:10px;">常用功能</a>
					</h1>>
					<p:auth privCodeList="businessInfo_r,cropConfig_r,customManager_r,managerUser_r,myAccount_r,myChildAccount_r,portConfig_r,privilege_r,rmid_r,template_r,posTime_r,modelPos_r,SMSTunnel_r,RechargeProperties_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">系统设置</a>
					</h1>
					<div class="content">
					 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
				        <p:auth privCodeList="managerUser_r">
						<ul class="MM">
							<li><a href="../managerUser/toManagerUserList.shtml" target="main">用户管理</a></li>
						</ul>
						</p:auth>
						
						<p:auth privCodeList="myAccount_r">
						<ul class="MM">
							<li><a href="../managerUser/toMyManagerUser.shtml" target="main">我的账户</a></li>
						</ul>
						</p:auth>						
						 <p:auth privCodeList="businessInfo_r">
						<ul class="MM">
							<li><a href="../summary/selSummaryList.shtml" target="main">商户信息</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="portConfig_r">
						<ul class="MM">
							<li><a href="../wpc/toModifyWechatPropertiesPage.shtml" target="main">接口配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="cropConfig_r">
						<ul class="MM">
							<li><a href="../cwp/toModifyCorpWechatPropertiesPage.shtml" target="main">企业号配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="template_r">	
						<ul class="MM">
							<li><a href="../template/getTemplateList.shtml" target="main">模板消息配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="SMSTunnel_r">	
						<ul class="MM">
							<li><a href="../sms/toEditSMSTunnel.shtml" target="main">短信通道配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="PhoneConfig_r">	
						<ul class="MM">
							<li><a href="../sms/toEditPhoneConfig.shtml" target="main">关注即会员配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="SMSsignature_r">	
						<ul class="MM">
							<li><a href="../sms/toEditSMSsignature.shtml" target="main">短信签名配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="customManager_r">	
						<ul class="MM">
							<li><a href="../cmc/toCustomMenuListPage.shtml" target="main">自定义菜单</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="rmid_r">	
						<ul class="MM">
							<li><a href="../rmid/getRmidList.shtml" target="main">关联商户</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="privilege_r">
						<ul class="MM">
							<li><a href="../privilege/getAllPrivileges.shtml" target="main">权限配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="Mode_r">
						<ul class="MM">
							<li><a href="../modular/selModularList.shtml" target="main">模块管理</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="posTime_r">
						<ul class="MM">
							<li><a href="../common/toSetPostime.shtml" target="main">POS延迟时间配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="modelPos_r">
							<ul class="MM">
								<li><a href="../midFacatory/midFactoryList.shtml" target="main">关联厂商</a></li>
							</ul>
						</p:auth>
						<p:auth privCodeList="RechargePrties_r">
							<ul class="MM">
								<li><a href="../rechargeProperties/toMyRechargeProperties.shtml" target="main">储值支付配置</a></li>
							</ul>
						</p:auth>
						<p:auth privCodeList="orderHint_r">
							<ul class="MM">
								<li><a href="../common/toSetHint.shtml" target="main">订单提示配置</a></li>
							</ul>
						</p:auth>						
					</div>
					</p:auth>
					
					<p:auth privCodeList="catalog_r,pageStyle_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">页面布局</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="pageStyle_r">
							<li><a href="../pageStyle/getPageStyle.shtml" target="main">页面样式配置</a></li>
							</p:auth>
						</ul>
						<ul class="MM">
						    <p:auth privCodeList="catalog_r">
							<li><a href="../catalog/toCatalogListPage.shtml" target="main">特制化页面配置</a></li>
						    </p:auth>
						</ul>
					</div>
					</p:auth>
					
					
					<!-- 转盘配置 -->
					<p:auth privCodeList="turntableVO_r,turntablePrize_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">转盘配置</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="turntableVO_r">
						<ul class="MM">
							<li><a href="../turnTable/toEidtTurntableVO.shtml" target="main">转盘基本信息配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="turntablePrize_r">
						<ul class="MM">
							<li><a href="../turnTable/getTurnTablePrizeList.shtml" target="main">转盘奖项配置</a></li>
						</ul>
						</p:auth>
					</div>
					</p:auth>
					
					<p:auth privCodeList="manageMemberCount_r,manageMember_r,waiter_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">会员管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="manageMember_r">	
						<ul class="MM">
							<li><a href="../member/getMemberList.shtml" target="main">会员管理</a></li>
						</ul>
						</p:auth>
						
						<p:auth privCodeList="waiter_r">
						<ul class="MM">
							<li><a href="../waiter/toSelWaiter.shtml" target="main">服务员管理</a></li>
						</ul>
						</p:auth>
						
						<p:auth privCodeList="manageMemberCount_r">	
						<ul class="MM">
							<li><a href="../highchart/selHighChartList.shtml" target="main">会员数量折线图</a></li>
						</ul>
						</p:auth>
						
					</div>
					</p:auth>
					
					<p:auth privCodeList="payType_r,tradeType_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">微信支付配置</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="tradeType_r">	
						<ul class="MM">
							<li><a href="../mpayType/toSetPayType.shtml" target="main">交易方式配置</a></li>
						</ul>
						</p:auth>	
						<p:auth privCodeList="payType_r">			
						<ul class="MM">
							<li><a href="../mpayType/toPayTypePermisson.shtml" target="main">支付方式授权</a></li>
						</ul>
						</p:auth>
					</div>
					</p:auth>
					
					<p:auth privCodeList="rechargeAccount_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">微信充值设置</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="rechargeAccount_r">	
						<ul class="MM">
							<li><a href="../wpc/rechargeAccountSet.shtml" target="main">充值账户设置</a></li>
						</ul>
						</p:auth>
					</div>
					</p:auth>
					
					<p:auth privCodeList="keywords_r,moPicture_r,picture_r,text_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">自定义回复</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<p:auth privCodeList="keywords_r">
							<li><a href="../keyword/selKeywordList.shtml" target="main">关键字维护</a></li>
						</p:auth>
						<p:auth privCodeList="text_r">	
							<li><a href="../message/getTextsList.shtml" target="main">文本回复</a></li>
						</p:auth>
						 <p:auth privCodeList="picture_r">	
							<li><a href="../ImageText/getImageTextList.shtml" target="main">单图文回复</a></li>
						</p:auth>
						 <p:auth privCodeList="moPicture_r">	
							<li><a href="../multi/list.shtml?currentPage=1" target="main">多图文回复</a></li>
						</p:auth>
						</ul>
					</div>
					</p:auth>
					
					<p:auth privCodeList="areaManager_r,cityManager_r,shopSet_r,two_dimension_r,QRcode_r,orderTimeSynManage_u,orderCount_r,two_paydimension_r,deskManager_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">门店管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<p:auth privCodeList="areaManager_r">	
							<li><a href="../cityController/toAreasPage.shtml" target="main">区域管理</a></li>
						 </p:auth>
						 <p:auth privCodeList="cityManager_r">	
							<li><a href="../cityController/createForm.shtml" target="main">城市管理</a></li>
						 </p:auth>
						 <p:auth privCodeList="shopSet_r">	
							<li><a href="../store/selStoreInfo.shtml" target="main">店铺设置</a></li>
						 </p:auth>				 
						 
						 <p:auth privCodeList="import_deskno_r">
							 <ul class="MM">
								<li><a href="../deskImport/toDeskImportPage.shtml" target="main">导入桌台号</a></li>
							 </ul>
						 </p:auth>
						 
						 <p:auth privCodeList="two_dimension_r">	
							<li><a href="../qrcode/toCodePage.shtml" target="main">生成桌号二维码</a></li>
						</p:auth>
						 <p:auth privCodeList="QRcode_r">	
							<li><a href="../qrcode/tobulkExportQrcode.shtml" target="main">快餐二位码生成导出</a></li>
						</p:auth>
						
						 <p:auth privCodeList="deskManager_r">
							 <ul class="MM">
								<li><a href="../desk/initDesk.shtml" target="main">桌台号管理</a></li>
							 </ul>
						 </p:auth>
						
						<p:auth privCodeList="two_paydimension_r">	
							<li><a href="../paycode/toCodePage.shtml" target="main">生成下单二维码</a></li>
						</p:auth>							
						</ul>
						
						<p:auth privCodeList="orderCount_r">
						<ul class="MM">
							<li><a href="../orderCount/togetOrderCountList.shtml" target="main">同步账单数编辑</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="orderTimeSynManage_u">
						<ul class="MM">
							<li><a href="../store/toOrderTime.shtml" target="main">同步账单时间配置</a></li>
						</ul>
						</p:auth>
					</div>
					</p:auth>
					
					<p:auth privCodeList="reListQuery_r,tableNumbers_r,timeManager_r,guestRoomManager_r,tableAudioManager_r,roomAudioManager_r,guestRoomQuery_r,businessFormatListQuery_r,bedTypeListQuery_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">预定管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>	
						<ul class="MM">
						<p:auth privCodeList="timeManager_r">
							<li><a href="../business/selectBusinessHours.shtml" target="main">预定时间段管理</a></li>
						</p:auth>
						<p:auth privCodeList="tableNumbers_r">
							<li><a href="../room/getRoomList.shtml?roomType=0" target="main">包间桌号管理</a></li>
						</p:auth>
						<p:auth privCodeList="guestRoomManager_r">	
							<li><a href="../room/getRoomList.shtml?roomType=1" target="main">客房信息管理</a></li>
						</p:auth>
						<p:auth privCodeList="guestManager_r">	
						    <li><a href="../guestType/getGuestRoomTypeList.shtml" target="main">客房类型管理</a></li>
						</p:auth>						
						<p:auth privCodeList="tableAudioManager_r">	
							<li><a href="../audio/toEditReservationAudio.shtml?type=0" target="main">包间预定音频配置</a></li>
						</p:auth>
						<p:auth privCodeList="roomAudioManager_r">	
							<li><a href="../audio/toEditReservationAudio.shtml?type=1" target="main">客房预定音频配置</a></li>
						</p:auth>
						<p:auth privCodeList="reListQuery_r">
							<li><a href="../reserve/reserveList.shtml?reserveType=0" target="main">包间预订列表</a></li>
						</p:auth>
						<p:auth privCodeList="guestRoomQuery_r">
							<li><a href="../reserve/reserveList.shtml?reserveType=1" target="main">客房预订列表</a></li>
						</p:auth>
						
						<p:auth privCodeList="businessFormatListQuery_r">
							<li><a href="../reserve/businessFormatList.shtml" target="main">业态配置列表</a></li>
						</p:auth>
						
						<p:auth privCodeList="bedTypeListQuery_r">
							<li><a href="../reserve/bedTypeList.shtml" target="main">床型设置</a></li>
						</p:auth>
															
						</ul>						
					</div>
					</p:auth>
					
					<p:auth privCodeList="dishCategory_r,dishSynchronizes_r,dish_r,placeOrder_u,posSynchronizes_r,areaOrder_r,dishClaer_r,dishStoreOrder_u" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">点菜管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="dishCategory_r">
						<ul class="MM">
							<li><a href="../dishType/toDishTypeList.shtml" target="main">菜品类别管理</a></li>
						</ul>
						</p:auth>	
						<p:auth privCodeList="dishStall_r">
						<ul class="MM">
							<li><a href="../dishStall/toDishStallList.shtml" target="main">菜品档口管理</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="print_r">
							<ul class="MM">
								<li><a href="../dishStall/initPrints.shtml" target="main">打印机管理</a></li>
							</ul>
						</p:auth>
						<p:auth privCodeList="printPro_r">
							<ul class="MM">
								<li><a href="../dishStall/printStallsList.shtml" target="main">打印机配置</a></li>
							</ul>
						</p:auth>
						<p:auth privCodeList="dishRemark_r">
						<ul class="MM">
							<li><a href="../dishRemark/todishRemarkList.shtml" target="main">菜品备注管理</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="dish_r">
						<ul class="MM">
							<li><a href="../dishController/toDishList.shtml" target="main">菜品管理</a></li>
						</ul>
						</p:auth>
						
						<p:auth privCodeList="dishSynchronizes_r">
						<ul class="MM">
							<li><a href="../dishSynchronize/selectDishSynchronizes.shtml" target="main">菜品同步</a></li>
						</ul>
						</p:auth>
						<!-- dyl s-->
						<p:auth privCodeList="posDishSynchronizes_r">
							<ul class="MM">
								<li>
									<a href="../posDishSyn/toDishSynchronizes.shtml" target="main">POS同步菜品</a>
								</li>
							</ul>
						</p:auth>
						<p:auth privCodeList="posSynchronizesStore_r">
							<ul class="MM">
								<li><a href="../dishSynchronizeStore/selectDishSynchronizes.shtml" target="main">同步菜品到门店</a></li>
							</ul>
						</p:auth>
						<!-- <ul class="MM">
							<li><a href="../dishManageController/toDishList.shtml" target="main">菜品编辑</a></li>
						</ul> -->
						<!-- dyl e-->
						<p:auth privCodeList="placeOrder_u">
						<ul class="MM">
							<li><a href="../canOrder/toCanOrder.shtml" target="main">是否可以下单</a></li>
						</ul>	
						</p:auth>	
						<p:auth privCodeList="posSynchronizes_r">
						<ul class="MM">
							<li><a href="../posSynchronize/synSet.shtml" target="main">POS同步设置</a></li>
						</ul>							
						</p:auth>	
						<p:auth privCodeList="areaOrder_r">
						<ul class="MM">
							<li><a href="../dishExController/createForm.shtml" target="main">菜品区域排序</a></li>
						</ul>
						</p:auth>						
						<p:auth privCodeList="dishStoreOrder_u">
						<ul class="MM">
							<li><a href="../dishExController/orderStoreForm.shtml" target="main">菜品门店排序</a></li>
						</ul>	
						</p:auth>			
						<p:auth privCodeList="dishClaer_r">
                        <ul class="MM">
							<li><a href="../clearDish/selectClearDishTime.shtml" target="main">菜品清理配置</a></li>
						</ul>	
						</p:auth>
						<p:auth privCodeList="dishForms_r">
						<ul class="MM">
							<li><a href="../dishController/toDishListReport.shtml" target="main">菜品报表</a></li>
						</ul>
						</p:auth>
					</div>
					</p:auth>				
					<p:auth privCodeList="12322_r,order_r,sorder_r,rechargeRecord_r,taOrderList_r,sordersum_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">订单管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>	
						<ul class="MM">
							<p:auth privCodeList="order_r">
							<li><a href="../morder/toOrderLst.shtml" target="main">订单列表</a></li>
							</p:auth>
							<p:auth privCodeList="rechargeRecord_r">
							<li><a href="../recharge/rechargeRecord.shtml" target="main">充值记录</a></li>
							</p:auth>
							<p:auth privCodeList="taOrderList_r">			
							<li><a href="../taorder/selTaOrderList.shtml" target="main">外卖订单查询</a></li>						
							</p:auth>
							<p:auth privCodeList="sorder_r">
							<li><a href="../sorder/toOrderLst.shtml" target="main">食间牛排订单列表</a></li>
							</p:auth>
							<p:auth privCodeList="sordersum_r">
							<li><a href="../sorder/ordersum.shtml" target="main">账单统计</a></li>	
							</p:auth>
						</ul>						
					</div>
					</p:auth>
					<!-- dyl start -->
					<p:auth privCodeList="commentTimeManage_r,time_r,qsWeiXinInfo_r,getOpenId_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">评价设置</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="commentTimeManage_r">
						<ul class="MM">
							<li><a href="../comment/toCommentDeploy.shtml" target="main">评价有效时间配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="time_r">
						<ul class="MM">
							<li><a href="../comment/toTimeSet.shtml" target="main">就餐时间段配置</a></li>
						</ul>
						</p:auth>
						<p:auth privCodeList="qsWeiXinInfo_r">
						 <ul class="MM">
							<li><a href="../comment/toSelQsWeiXinInfo.shtml" target="main">评价次数分析</a></li>
						</ul>
						 </p:auth>
						 <p:auth privCodeList="getOpenId_r">
						 <ul class="MM">
						 <li><a href="../comment/toGetOpenIdConfigure.shtml" target="main">获取OpenId配置</a></li>
						 </ul>
						 </p:auth>
					</div>
					</p:auth>
					<!-- dyl end -->
					<p:auth privCodeList="turntable_r,bigWheel_c" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">大转盘管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="bigWheel_c">
							<li><a href="../dzp/selectDzp.shtml" target="main">大转盘信息配置</a></li>
							</p:auth>
							<p:auth privCodeList="turntable_r">
							<li><a href="../config/selectConfig.shtml" target="main">转盘配置列表</a></li>
							</p:auth>
						</ul>
					</div>	
					</p:auth>
					
					<p:auth privCodeList="dynamic_r" relationFlag="or">	
						<h1 class="type">
						<a href="javascript:void(0)">新闻动态</a>
					    </h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="dynamic_r">
						<ul class="MM">
							<li><a href="../dynamic/toDynamicList.shtml" target="main">新闻动态管理</a></li>
						</ul>
						</p:auth>
					</div>	
					</p:auth>
					
					<p:auth privCodeList="storeHours_r" relationFlag="or">	
					<h1 class="type">
						<a href="javascript:void(0)">门店营业时间</a>
					    </h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<p:auth privCodeList="storeHours_r">
						<ul class="MM">
							<li><a href="../storeHours/toStoreHoursList.shtml" target="main">门店营业时间</a></li>
						</ul>
						</p:auth>
					</div>		
					</p:auth>
					
					<p:auth privCodeList="deliveryTime_r,inviteTime_r" relationFlag="or">				
					<h1 class="type">

						<a href="javascript:void(0)">送达时间管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="deliveryTime_r">
							<li><a href="../delivery/getDeliveryTime.shtml?obligate=1" target="main">送达时间列表</a></li>
							</p:auth>
						</ul>
						<ul class="MM">
							<p:auth privCodeList="inviteTime_r">
							<li><a href="../delivery/getDeliveryTime.shtml?obligate=2" target="main">自取时间列表</a></li>
							</p:auth>
						</ul>
					</div>	
					</p:auth>
					
					
					<!-- 储值报表开始 -->
					<p:auth privCodeList="storedHighcharts_r,ycjsbb_r,czjytjb_r,xfjytjb_r,czlstjb_r,yhqtjb_r,yhqjcsytj_r,jftjbb_r,jfjsbb_r" relationFlag="or">				
					<h1 class="type">
						<a href="javascript:void(0)">储值报表</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						
						<p:auth privCodeList="storedHighcharts_r">
								<li><a href="../storedReport/storedHighcharts.shtml?flag=1" target="main">储值一览</a></li>
							</p:auth>
							<p:auth privCodeList="ycjsbb_r">
								<li><a href="../storedReport/ycjsbb.shtml?flag=1" target="main">预存结算报表</a></li>
							</p:auth>
						
							<p:auth privCodeList="czjytjb_r">
								<li><a href="../storedReport/czjytjb.shtml?flag=1" target="main">预存统计报表</a></li>
							</p:auth>
								<p:auth privCodeList="xfjytjb_r">
								<li><a href="../storedReport/xfjytjb.shtml?flag=1" target="main">消费流水统计表</a></li>
							</p:auth>
							<p:auth privCodeList="czlstjb_r">
								<li><a href="../storedReport/czlstjb.shtml?flag=1" target="main">充值流水统计表</a></li>
							</p:auth>
							<p:auth privCodeList="yhqtjb_r">
								<li><a href="../storedReport/yhqtjb.shtml?flag=1" target="main">优惠券统计表</a></li>
							</p:auth>
							<p:auth privCodeList="yhqjcsytj_r">
								<li><a href="../storedReport/yhqjcsytj.shtml?flag=1" target="main">优惠券交叉使用统计表</a></li>
							</p:auth>
							<p:auth privCodeList="jftjbb_r">
								<li><a href="../storedReport/jftjbb.shtml?flag=1" target="main">积分统计报表</a></li>
							</p:auth>
							<p:auth privCodeList="jfjsbb_r">
								<li><a href="../storedReport/jfjsbb.shtml?flag=1" target="main">积分结算报表</a></li>
							</p:auth>
						</ul>
					</div>	
					</p:auth>
					<!-- 储值报表结束 -->
					
				
					<p:auth privCodeList="serveranalyze_r,dishanalyze_r,totalanalyze_r,qsService_r,qsdish_r" relationFlag="or">				
					<h1 class="type">
						<a href="javascript:void(0)">报表分析</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="serveranalyze_r">
								<li><a href="../analyzing/getServerAnalyze.shtml?boxn=1" target="main">服务分析</a></li>
							</p:auth>
							<p:auth privCodeList="dishanalyze_r">
								<li><a href="../analyzing/getDishAnalyze.shtml?boxn=1" target="main">菜品分析</a></li>
							</p:auth>
							<p:auth privCodeList="totalanalyze_r">
								<li><a href="../analyzing/getTotalAnalyze.shtml?boxn=1" target="main">总体分析</a></li>
							</p:auth>
						</ul>
						<p:auth privCodeList="qsService_r">
						<ul class="MM">
						 <li><a href="../comment/toSelQsService.shtml" target="main">服务意见</a></li> 
						</ul>
						 </p:auth>
						 <p:auth privCodeList="qsdish_r">
						 <ul class="MM">
						  <li><a href="../comment/toSelQsDish.shtml" target="main">菜品意见</a></li>
						  </ul>
						  </p:auth>
					</div>	
					</p:auth>
					
					<p:auth privCodeList="serveranalyzebak_r,dishanalyzebak_r,totalanalyzebak_r" relationFlag="or">				
					<h1 class="type">
						<a href="javascript:void(0)">历史报表分析</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="serveranalyzebak_r">
								<li><a href="../analyzing/getServerAnalyze.shtml?boxn=2" target="main">历史服务分析</a></li>
							</p:auth>
							<p:auth privCodeList="dishanalyzebak_r">
								<li><a href="../analyzing/getDishAnalyze.shtml?boxn=2" target="main">历史菜品分析</a></li>
							</p:auth>
							<p:auth privCodeList="totalanalyzebak_r">
								<li><a href="../analyzing/getTotalAnalyze.shtml?boxn=2" target="main">历史总体分析</a></li>
							</p:auth>
						</ul>
					</div>	
					</p:auth>
					
					<p:auth privCodeList="question_r,option_r" relationFlag="or">				
					<h1 class="type">
						<a href="javascript:void(0)">评价问卷</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="question_r">
								<li><a href="../questionnaire/getQuestionList.shtml" target="main">题目配置</a></li>
							</p:auth>
							<p:auth privCodeList="option_r">
								<li><a href="../questionnaire/getAnswerList.shtml" target="main">选项配置</a></li>
							</p:auth>						
						</ul>
					</div>	
					</p:auth>
					
					
					<p:auth privCodeList="crm_r" relationFlag="or">			
					<h1 class="type">
						<a href="javascript:void(0)">CRM系统</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>	
						<p:auth privCodeList="crm_r">
						<ul class="MM">
							<li><a href="${sessionScope.sendLogin}?userId=${sessionScope.ConsoleUser.userId }&password=${sessionScope.extendField2}&mid=${sessionScope.ConsoleUser.mid }" target="_blank">前往CRM系统</a></li>
						</ul>
						</p:auth>
					</div>	
					</p:auth>
					
					<p:auth privCodeList="PaymentProcess_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">支付流程配置</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="PaymentProcess_r">
							<li><a href="../keyvalues/toModifyKeyValuesPage.shtml" target="main">支付流程配置</a></li>
							</p:auth>
						</ul>
					</div>
					</p:auth>
					<p:auth privCodeList="WrongTime_r" relationFlag="or">
						<h1 class="type">
						<a href="javascript:void(0)">错时营销配置</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="WrongTime_r">
							<li><a href="../wrongtime/toWrongTimePage.shtml" target="main">错时营销配置</a></li>
							</p:auth>
						</ul>
					</div>
					</p:auth>
					<p:auth privCodeList="grade_r,upGrade_r" relationFlag="or">				
					<h1 class="type">
						<a href="javascript:void(0)">会员等级</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<p:auth privCodeList="grade_r">
							<li><a href="../grade/toGradeListPage.shtml" target="main">会员等级设置</a></li>
						</p:auth>
						<p:auth privCodeList="upGrade_r">
							<li><a href="../upgrade/toUpgrade.shtml" target="main">会员升级方式设置</a></li>
						</p:auth>
						</ul>						
					</div>
					</p:auth>
					
					<p:auth privCodeList="rechargeActivity_r">
					<h1 class="type">
						<a href="javascript:void(0)">充值活动管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>	
						<ul class="MM">
						<p:auth privCodeList="rechargeActivity_r">
							<li><a href="../reConfig/getReGiveRuleList.shtml" target="main">充值赠送</a></li>
						</p:auth>
						</ul>						
					</div>
					</p:auth>
					<p:auth privCodeList="amtBalIntegral_r">
					<h1 class="type">
						<a href="javascript:void(0)">储值是否积分</a>
					</h1>
					<div class="content">
						<ul class="MM">
						<p:auth privCodeList="amtBalIntegral_r">
							<li><a href="../upgrade/upgradeConfig.shtml" target="main">储值是否积分</a></li>
						</p:auth>
						</ul>						
					</div>
					</p:auth>
					<p:auth privCodeList="integralScale_r">
					<h1 class="type">
						<a href="javascript:void(0)">积分比例设置</a>
					</h1>
					<div class="content">
						<ul class="MM">
						<p:auth privCodeList="integralScale_r">
							<li><a href="../integral/inteProportion.shtml" target="main">积分比例设置</a></li>
						</p:auth>
						</ul>						
					</div>
					</p:auth>
					<p:auth privCodeList="statistics_r">
					<h1 class="type">
						<a href="javascript:void(0)">核销统计管理</a>
					</h1>
					<div class="content">
						<ul class="MM">
						<p:auth privCodeList="statistics_r">
							<li><a href="../statistics/getStatisList.shtml" target="main">核销统计</a></li>					
						</p:auth>									
						</ul>
					</div>
				    </p:auth>	
				    
				    <p:auth privCodeList="weixinTimes_r,commentTotal_r" relationFlag="or">
				     <h1 class="type">
					 <a href="javascript:void(0)">评价统计</a>
					 </h1>
					 <div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						
						<p:auth privCodeList="weixinTimes_r">
							<ul class="MM">
							  <li><a href="../comment/toSelQsWeiXinInfo.shtml" target="main">评价次数分析</a></li>
							  </ul>
						</p:auth>
						<p:auth privCodeList="commentTotal_r">
							  <ul class="MM">
							  <li><a href="../comment/toCommentList.shtml" target="main">评价统计</a></li></li>
							</ul>
						</p:auth>
					</div>
					 </p:auth>	    			    
				    <p:auth privCodeList="balance_r,integral_r,level_r,mcard_r,recharge_r,score_r,ticketRecord_r" relationFlag="or">		
				    <h1 class="type">
						<a href="javascript:void(0)">会员管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<p:auth privCodeList="mcard_r">
							<li><a href="../mcards/selectMcards.shtml" target="main">会员管理</a></li>
						</p:auth>
							<!-- <li><a href="../order/toOrderList.shtml" target="main">消费记录</a></li> -->
						<p:auth privCodeList="recharge_r">
							<li><a href="../cpdetail/selectCpDetail.shtml" target="main">充值记录</a></li>
						</p:auth>
						<p:auth privCodeList="integral_r">
						<li><a href="../integral/selectIntegral.shtml" target="main">积分记录</a></li>
						</p:auth>
						
						<p:auth privCodeList="ticketRecord_r">
						<li><a href="../ticket/ticketRecord.shtml" target="main">优惠券记录</a></li>
						</p:auth>
						
						<p:auth privCodeList="balance_r">
							<li><a href="../rechargeCrm/tobr.shtml" target="main">余额调整</a></li>
						</p:auth>
						<p:auth privCodeList="score_r">
							<li><a href="../rechargeCrm/tosr.shtml" target="main">积分调整</a></li>
						</p:auth>
						<p:auth privCodeList="level_r">
							<li><a href="../rechargeCrm/tosl.shtml" target="main">等级调整</a></li>
						</p:auth>
						
						</ul>						
					</div>
					</p:auth>
					
					<p:auth privCodeList="crmConsumption_c,crmRecharge_c,crmConsumptionSeq_r,memberTicket_r,cash_r,rechargeProperties_r,rechargeRerd_r" relationFlag="or">		
					<h1 class="type">
						<a href="javascript:void(0)">收银管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="crmConsumption_c">
							<li><a href="../consume/toFindMember.shtml" target="main">消费</a></li>
							</p:auth>
							<p:auth privCodeList="crmRecharge_c">
							<li><a href="../rechargeCrm/toRecharge.shtml" target="main">充值</a></li>
							</p:auth>
							
							<p:auth privCodeList="crmgoConsumption_c">
							<li><a href="../consume/goFindMember.shtml" target="main">去消费</a></li>
							</p:auth>
							<p:auth privCodeList="crmgoRecharge_c">
							<li><a href="../rechargeCrm/goRecharge.shtml" target="main">去充值</a></li>
							</p:auth>
							
							<p:auth privCodeList="crmConsumptionSeq_r">
							<li><a href="../order/toOrderList.shtml" target="main">消费流水详情</a></li>
							</p:auth>
							<p:auth privCodeList="memberTicket_r">
							<li><a href="../memberTicket/toMemberTicketList.shtml" target="main">会员优惠券管理</a></li>
							</p:auth>
							<p:auth privCodeList="cash_r">
							<li><a href="../reInfo/reInfoList.shtml" target="main">收银统计</a></li>
							
						</p:auth>
						
							<p:auth privCodeList="rechargeProperties_r">
								<li><a href="../rechargeProperties/toConsumeRecordList.shtml" target="main">消费流水列表</a></li>
							</p:auth>
							<p:auth privCodeList="rechargeRerd_r">
								<li><a href="../rechargeProperties/RechargeRecordList.shtml" target="main">充值流水列表</a></li>
							</p:auth>
						</ul>						
					</div>
					</p:auth>	
									
					<p:auth privCodeList="ticket_c,couponRemind_r,batchImportTicket_u,ticketType_r" relationFlag="or">
				    <h1 class="type">
						<a href="javascript:void(0)">优惠劵管理</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<p:auth privCodeList="ticketType_r">
							<li><a href="../ticketType/selectTicketType.shtml" target="main">优惠券类型</a></li>
						</p:auth>
						<p:auth privCodeList="ticket_c">
							<li><a href="../ticket/selectTicket.shtml" target="main">优惠券设置</a></li>
						</p:auth>
						<p:auth privCodeList="couponRemind_r">
							<li><a href="../couponRemind/findCouponRemindByMid.shtml" target="main">优惠券到期提醒</a></li>
						</p:auth>
						
						<p:auth privCodeList="batchImportTicket_u">
							<li><a href="../memberTicket/toBatchImportPage.shtml" target="main">券批量导入</a></li>
						</p:auth>
						
						</ul>						
					</div>
					</p:auth>					
					<p:auth privCodeList="ticket0_r,ticket1_r,ticket2_r,ticket3_r,ticket4_r,ticket5_r,ticket6_r,ticket7_r,ticket8_r,ticket9_r,ticket11_r,ticket12_r,redMoneyManager_r,redGroup_r,couponStatistics_r,fissionActivity_r,fissionGroup_r,fissionPrize_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">营销活动</a>
					</h1>
					<div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						
						
						<p:auth privCodeList="fissionActivity_r">
					   <li><a href="../fissionCoupon/fessionList.shtml" target="main">裂变优惠券活动</a></li> 
					   </p:auth>
					   <p:auth privCodeList="fissionGroup_r">
					   <li><a href="../fissionCoupon/fissionGroupList.shtml" target="main">裂变优惠券组配置</a></li>
					   </p:auth>
					   <p:auth privCodeList="fissionPrize_r">
					   <li><a href="../fissionCoupon/toPrizeList.shtml" target="main">裂变优惠券奖项配置</a></li>
					   </p:auth>
					    
						<p:auth privCodeList="ticket6_r">
						<li><a href="../payticket/toPayTicketList.shtml?type=6" target="main">支付优惠</a></li>
						</p:auth>
						<p:auth privCodeList="ticket7_r">
						<li><a href="../payticket/toPayTicketList.shtml?type=7" target="main">抢红包配置</a></li>
						</p:auth>
						<p:auth privCodeList="redMoneyManager_r">
						<li><a href="../comment/toRedMoneyPage.shtml" target="main">红包金额配置</a></li>
						</p:auth>
						<p:auth privCodeList="ticket2_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=2" target="main">注册送券管理</a></li>
						</p:auth>
						<p:auth privCodeList="ticket1_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=1" target="main">消费送券管理</a></li>
						</p:auth>
						<p:auth privCodeList="ticket0_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=0" target="main">累计消费金额送券管理</a></li>
						</p:auth>
						<p:auth privCodeList="ticket4_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=4" target="main">累计消费次数管理</a></li>
						</p:auth>
						<p:auth privCodeList="ticket3_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=3" target="main">生日送券管理</a></li>
						</p:auth>
						<p:auth privCodeList="ticket5_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=5" target="main">转盘奖品劵管理</a></li>
						</p:auth>
						<p:auth privCodeList="redGroup_r">
						  <li><a href="../redGroup/redPaperGroupList.shtml" target="main">红包配置</a></li>
						</p:auth>
						<p:auth privCodeList="ticket8_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=8" target="main">积分商城送券管理</a></li>
						</p:auth>
						
						<p:auth privCodeList="ticket11_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=11" target="main">电子劵商城送券管理</a></li>
						</p:auth>	
						<p:auth privCodeList="ticket12_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=12" target="main">充值送券管理</a></li>
						</p:auth>						
						<p:auth privCodeList="activity_r">
							<li><a href="../activity/toPayTicketList.shtml" target="main">优惠活动管理</a></li>
						</p:auth>
						<p:auth privCodeList="couponStatistics_r">
							<li><a href="../ticket/toCouponStatisticsPage.shtml" target="main">优惠券核销统计</a></li>
						</p:auth>
						<p:auth privCodeList="ticket9_r">
							<li><a href="../payticket/toPayTicketList.shtml?type=9" target="main">老带新活动管理</a></li>
						</p:auth>
						<p:auth privCodeList="given_r">
							<li><a href="../common/tosetImg.shtml" target="main">二维码配置</a></li>
						</p:auth>
						</ul>						
					</div>
					</p:auth>	
					
					<p:auth privCodeList="orderComment_r,orderComment_d,orderComment_c" relationFlag="or">
					 <h1 class="type">
						<a href="javascript:void(0)">舆情客服</a>
					 </h1>
					 
					 <div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
						<p:auth privCodeList="orderComment_r">
							<li><a href="../orderComment/toOrderCommentList.shtml" target="main">订单评论列表</a></li>
						</p:auth>
						</ul>						
					</div>
					</p:auth>
					<p:auth privCodeList="storeReport_r,ticketReport_r" relationFlag="or">
					<h1 class="type">
						<a href="javascript:void(0)">条码券</a>
					 </h1>
					 <div class="content">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="storeReport_r">
							<li><a href="../barCodeReport/initStoreReport.shtml" target="main">门店券销售回收明细表</a></li>
							</p:auth>
							<p:auth privCodeList="ticketReport_r">
							<li><a href="../barCodeReport/initTicketReport.shtml" target="main">券销售回收汇总表</a></li>
							</p:auth>
						</ul>						
					</div>	
					</p:auth>
				<p:auth privCodeList="JifenShop_r" relationFlag="and">
					
					<h1 class="type">
						<a href="javascript:void(0)">积分商城</a>
					 </h1>
					 <div class="content">
					 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="Advertisement_r">
								<li><a href="../adPosition/adList.shtml?usty=0" target="main">广告位管理</a></li>
							</p:auth>
							<p:auth privCodeList="ProductType_r">
								<li><a href="../type/protypelist.shtml?usty=0" target="main">商品类别管理</a></li>
							</p:auth>
							<p:auth privCodeList="Product_r">
								<li><a href="../product/findProductList.shtml?usty=0" target="main">商品管理</a></li>
							</p:auth>
							<p:auth privCodeList="ActivityArea_r">
								<!-- <li><a href="../marea/selectMArea.shtml?usty=0" target="main">活动专区管理</a></li> -->
							</p:auth>
							<p:auth privCodeList="ExchangeCoupon_r">
								<li><a href="../mcoupon/selectMCoupon.shtml?usty=0" target="main">兑换券管理</a></li>
							</p:auth>
							<p:auth privCodeList="selOrder_r">
								<li><a href="../morder/getAllOrder.shtml?usty=0" target="main">订单查询</a></li>
							</p:auth>
							<p:auth privCodeList="Refund_r">
								<li><a href="../morder/selectDetailByStatus.shtml?usty=0" target="main">退款处理</a></li>
							</p:auth>
							<p:auth privCodeList="reply_r">
								<li><a href="../reply/selectReply.shtml?usty=0" target="main">舆情回复</a></li>
							</p:auth>
							<p:auth privCodeList="SalesStatistic_r">
								<li><a href="../mstatistics/selectOrderByTime.shtml?usty=0" target="main">售卖统计</a></li>
							</p:auth>
							<p:auth privCodeList="VerificationStatistics_r">
								<li><a href="../mstatistics/verificationStatistics.shtml?usty=0" target="main">核销统计</a></li>
							</p:auth>
						</ul>
					 </div>				
				</p:auth>
				
				<p:auth privCodeList="PiaoShop_r" relationFlag="and">			
					<h1 class="type">
						<a href="javascript:void(0)">电子票商城</a>
					 </h1>
					 <div class="content">
					 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
						<ul class="MM">
							<p:auth privCodeList="Advertisement_r">
								<li><a href="../adPosition/adList.shtml?usty=1" target="main">广告位管理</a></li>
							</p:auth>
							<p:auth privCodeList="ProductType_r">
								<li><a href="../type/protypelist.shtml?usty=1" target="main">商品类别管理</a></li>
							</p:auth>
							<p:auth privCodeList="Product_r">
								<li><a href="../product/findProductList.shtml?usty=1" target="main">商品管理</a></li>
							</p:auth>
							<p:auth privCodeList="selOrder_r">
								<li><a href="../morder/getAllOrder.shtml?usty=1" target="main">订单查询</a></li>
							</p:auth>
							<p:auth privCodeList="reply_r">
								<li><a href="../reply/selectReply.shtml?usty=1" target="main">舆情回复</a></li>
							</p:auth>
							<p:auth privCodeList="SalesStatistic_r">
								<li><a href="../mstatistics/selectOrderByTime.shtml?usty=1" target="main">售卖统计</a></li>
							</p:auth>
							<p:auth privCodeList="VerificationStatistics_r">
								<li><a href="../mstatistics/verificationStatistics.shtml?usty=1" target="main">核销统计</a></li>
							</p:auth>
							<p:auth privCodeList="Verification_r">
								<li><a href="../mstatistics/verification.shtml?usty=1" target="main">核销券统计</a></li>
							</p:auth>						
						</ul>
					 </div>				
				</p:auth>
				
				
			  <p:auth privCodeList="synCorpEmployee_u,RewardAmount_r,RewardCoupon_r,employee_r,RewardRecordHistory_r,ImageCode_r" relationFlag="or">
				 <h1 class="type">
					<a href="javascript:void(0)">打赏</a>
				 </h1>
				 <div class="content">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td>
								<img src="../images/system/menu_topline.gif" width="182" height="5" />
							</td>
						</tr>
					</table>
					<ul class="MM">
						<p:auth privCodeList="synCorpEmployee_u">
							<li><a href="../employee/createSynEmployeeForm.shtml" target="main">同步企业号员工</a></li>
						</p:auth>
						<%-- <p:auth privCodeList="RewardWechatPayAcount_r"> --%>
							<!-- <li><a href="../RewardWeiPayAccount/toAccountList.shtml" target="main">收款账户配置</a></li> -->
						<%-- </p:auth> --%>
						<p:auth privCodeList="RewardAmount_r">
							<li><a href="../rewardAmount/toRewardAmount.shtml" target="main">打赏金额配置</a></li>
						</p:auth>
						<p:auth privCodeList="RewardCoupon_r">
							<li><a href="../coupon/toRewardCoupon.shtml" target="main">打赏赠券配置</a></li>
						</p:auth>
						<p:auth privCodeList="employee_r">
							<li><a href="../employee/toEmployeePage.shtml" target="main">员工信息管理</a></li>
						</p:auth>
						<p:auth privCodeList="RewardRecordHistory_r">
						<li><a href="../rewardRecordHistory/selRewardRecordHistoryList.shtml" target="main">打赏评价记录</a></li>	
						</p:auth>
						<p:auth privCodeList="ImageCode_r">
							<li><a href="../employeeCode/toMakeImage.shtml" target="main">员工二维码</a></li>	
						</p:auth>
					</ul>						
				  </div>
				</p:auth>
				
				<p:auth privCodeList="HeadquartersReport_r,RegionalReport_r,StoreReport_r,HRReport_r,ExceptionReport_r,FinancialRecords_r" relationFlag="or">
					 <h1 class="type">
						<a href="javascript:void(0)">打赏报表</a>
					 </h1>
					 <div class="content">
					 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
					<ul class="MM">
					<p:auth privCodeList="HeadquartersReport_r">
						<li><a href="../rewardRecordHistory/initHeadQuartersReport.shtml" target="main">总部报表</a></li>
					</p:auth>
					<p:auth privCodeList="RegionalReport_r">
						<li><a href="../rewardRecordHistory/initAreaReport.shtml" target="main">区域报表</a></li>
					</p:auth>
					<p:auth privCodeList="StoreReport_r">
						<li><a href="../rewardRecordHistory/initStoreReport.shtml" target="main">门店报表</a></li>
					</p:auth>
					<p:auth privCodeList="HRReport_r">
						<li><a href="../rewardRecordHistory/initHRReport.shtml" target="main">HR报表</a></li>
					</p:auth>
					<p:auth privCodeList="ExceptionReport_r">
						<li><a href="../rewardRecordHistory/selectWrongRecords.shtml" target="main">异常打赏报表</a></li>
					</p:auth>
					<p:auth privCodeList="FinancialRecords_r">
						<li><a href="../rewardRecordHistory/initRecord.shtml" target="main">财务扣款记录导入</a></li>
					</p:auth>
					</ul>
					</div>
				</p:auth>
				
				<p:auth privCodeList="AcPrizeList_r,AcReportList_r" relationFlag="or">
				     <h1 class="type">
						<a href="javascript:void(0)">活动管理</a>
					 </h1>
					 <div class="content">
					 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
					<ul class="MM">
						<p:auth privCodeList="AcPrizeList_r">
							<li><a href="../ac/acPrizeList.shtml" target="main">活动奖品列表</a></li>
						</p:auth>
						<p:auth privCodeList="AcReportList_r">
							<li><a href="../ac/acReportList.shtml" target="main">活动记录列表</a></li>
						</p:auth>					
					</ul>
					</div>
				 </p:auth>
				 
				 <p:auth privCodeList="RoomReserveReport_r,DinnerReserveReport_r,TicketReport_r,StoreConsumeReport_r" relationFlag="or">
				     <h1 class="type">
						<a href="javascript:void(0)">报表管理</a>
					 </h1>
					 <div class="content">
					 	<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td>
									<img src="../images/system/menu_topline.gif" width="182" height="5" />
								</td>
							</tr>
						</table>
					<ul class="MM">
						<p:auth privCodeList="RoomReserveReport_r">
							<li><a href="../report/roomReserveReport.shtml" target="main">客房预定报表</a></li>
						</p:auth>
						<p:auth privCodeList="DinnerReserveReport_r">
							<li><a href="../report/dinnerReserveReport.shtml" target="main">餐饮预定报表</a></li>
						</p:auth>
						<p:auth privCodeList="TicketReport_r">
							<li><a href="../report/ticketReport.shtml" target="main">券销售明细报表</a></li>
						</p:auth>
						<p:auth privCodeList="StoreConsumeReport_r">
							<li><a href="../report/storeConsumeReport.shtml" target="main">门店会员消费报表</a></li>
						</p:auth>
					</ul>
					</div>
				 </p:auth>
				</div>				
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	var contents = document.getElementsByClassName('content');
	var toggles = document.getElementsByClassName('type');
	var myAccordion = new fx.Accordion(toggles, contents, {
		opacity : true,
		duration : 400
	});
	myAccordion.showThisHideOpen(contents[0]);
</script>
</html>