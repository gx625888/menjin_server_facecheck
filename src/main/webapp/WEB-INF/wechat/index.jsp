<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>智慧门禁</title>
    <!-- 引入 WeUI -->
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
    <script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
</head>
<body ontouchstart>
<!-- 白色按钮 -->
<!-- 绿色按钮 -->


<div class="weui-flex">
    <div class="weui-flex__item">
        <div style="height: 13rem;background:url('${ctx}/wechat-static/img/ttt.jpeg ') no-repeat ;background-size:100% 100%;"></div>

    </div>
</div>
<br/>
<div class="weui-cells__title">
    <c:if test="${not empty bindPhone}">
        您的手机号为:${bindPhone}
    </c:if>
    <c:if test="${ empty bindPhone}">
       绑定手机查看关联的房间
    </c:if>

    <a style="float: right" href="#" onclick="test()" class="weui-btn weui-btn_mini weui-btn_primary">绑定手机</a>
    <div  class="weui-flex__item"></div>
</div>
<c:if test="${empty houses}">
    <div class="weui-form-preview" style="margin: 2rem">
        <!-- head 部分 -->
        <div class="weui-form-preview__hd">
            <div class="weui-form-preview__item">
                <label class="weui-form-preview__label">您还没有房间哦!</label>
            </div>
        </div>

    </div>
</c:if>
<c:if test="${ not empty houses}">
    <c:forEach items="${houses}" var="house">
        <div class="weui-form-preview" style="margin: 2rem">
            <!-- head 部分 -->
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">小区</label>
                    <em class="weui-form-preview__value">${house.residentialName}</em>
                </div>
            </div>
            <!-- body 部分 -->
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">楼橦</label>
                    <span class="weui-form-preview__value">${house.buildName}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">单元</label>
                    <span class="weui-form-preview__value">${house.unitName}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">房号</label>
                    <span class="weui-form-preview__value">${house.houseName}</span>
                </div>
            </div>
            <!-- foot 部分 -->
            <div class="weui-form-preview__ft">
                <%--<a href="#" onclick="unbind('${house.houseId}','${house.personId}')" style="color: red" class="weui-form-preview__btn weui-form-preview__btn_default">解绑</a>--%>
                <a href="#" onclick="createCode('${house.deviceId}','${house.deviceStatus}','${house.personId}')" class="weui-form-preview__btn weui-form-preview__btn_primary">临时密码</a>
                <a href="#" onclick="openDoor('${house.houseName}','${house.houseId}','${house.deviceId}','${house.deviceStatus}','${house.personId}','${house.houseOpenType}')" class="weui-form-preview__btn weui-form-preview__btn_primary">开门</a>
            </div>
        </div>
        <br />
    </c:forEach>
</c:if>





<div class="weui-footer">
    <p class="weui-footer__text">到底了</p>
</div>
<script>
    function test() {
        window.location.href="${ctx}/wechat/bandIndex.act";
    }

    function unbind(houseId,userId) {

        weui.confirm('确认解除绑定么?', {
            buttons: [{
                label: '取消',
                type: 'default',
                onClick: function(){

                }
            }, {
                label: '解绑',
                type: 'primary',
                onClick: function(){
                    var loading = weui.loading('操作中...', {
                        className: 'custom-classname'
                    });
                    $.ajax({
                        type:"post",
                        url :"${ctx}/wechat/my/unbind.act",
                        data:{houseId:houseId,userId:userId},
                        success:function(data){

                            if (data.ret==0){
                                loading.hide(function() {
                                    weui.toast('解绑成功!', 1000);
                                    window.location.href = window.location.href;
                                });
                            } else{
                                loading.hide(function() {
                                    weui.toast('服务器开小差了!', 1000);
                                });
                            }
                        },
                        error:function(){
                            loading.hide(function() {
                                weui.toast('服务器开小差了!', 1000);
                            });
                        }
                    });
                }
            }]
        });
    }
    function createCode(deviceId,flag,userId){
        if (deviceId==undefined||deviceId==null||deviceId==''){
            weui.toast('对不起,该房间未绑定设备!', 1000);
            return ;
        }
        // if(flag=='0'){
        //     weui.toast('对不起,房间设备未在线!', 1000);
        //     return ;
        // }

        var loading = weui.loading('临时密码生成中', {
            className: 'custom-classname'
        });
        $.ajax({
            type:"post",
            url :"${ctx}/wechat/my/pwd.act",
            data:{deviceId:deviceId,userId:userId},
            success:function(data){

                if (data.ret==0){
                    loading.hide(function() {
                        weui.alert("您的临时密码为"+data.code+"");
                    });
                } else{
                    loading.hide(function() {
                        weui.toast('服务器开小差了!', 1000);
                    });
                }
            },
            error:function(){
                loading.hide(function() {
                    weui.toast('服务器开小差了!', 1000);
                });
            }
        });
       /* weui.confirm('生成临时密码?', {
            buttons: [{
                label: '取消',
                type: 'default',
                onClick: function(){

                }
            }, {
                label: '生成',
                type: 'primary',
                onClick: function(){
                }
            }]
        });*/
    }
    function openDoor(houseNo, houseId, deviceId,flag,userId,openType){
        if (deviceId==undefined||deviceId==null||deviceId==''){
            weui.toast('对不起,该房间未绑定设备!', 1000);
            return ;
        }
        if(flag=='0'){
            weui.toast('对不起,房间设备未在线!', 1000);
            return ;
        }
        var loading = weui.loading('操作中', {
            className: 'custom-classname'
        });
        $.ajax({
            type:"post",
            url :"${ctx}/wechat/my/opendoor.act",
            data:{houseNo:houseNo, houseId:houseId, deviceId:deviceId,userId:userId,openType:openType},
            success:function(data){

                if (data.ret==0){
                    loading.hide(function() {
                        weui.toast('操作成功!', 1000);
                    });
                } else{
                    loading.hide(function() {
                        weui.toast(data.msg, 1000);
                    });
                }
            },
            error:function(){
                loading.hide(function() {
                    weui.toast('请求异常!', 1000);
                });
            }
        });
        /*weui.confirm('确认开门么?', {
            buttons: [{
                label: '取消',
                type: 'default',
                onClick: function(){

                }
            }, {
                label: '开门',
                type: 'primary',
                onClick: function(){

                }
            }]
        });*/
    }
</script>

</body>
</html>

