<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>绑定手机号</title>
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
<div >
    <div class="weui-cells__title">绑定手机号</div>
    <div class="weui-cells weui-cells_form">
        <div class="weui-cell weui-cell_vcode">
            <div class="weui-cell__hd">
                <label class="weui-label">手机号</label>
            </div>
            <div class="weui-cell__bd">
                <input class="weui-input" id="phone" type="tel" placeholder="请输入手机号">
            </div>
            <div class="weui-cell__ft">
                <a href="#" onclick="sendCode(this)" class="weui-vcode-btn">获取验证码</a>
            </div>
        </div>

    </div>
    <div class="weui-cell">
        <div class="weui-cell__hd"><label class="weui-label">验证码</label></div>
        <div class="weui-cell__bd">
            <input class="weui-input" id="code" type="text" placeholder="请输入短信验证码">
        </div>
    </div>

    <div ></div>
    <div class="weui-btn-area">
        <a  href="#" onclick="bind()" class="weui-btn   weui-btn_primary">绑定手机</a>
        <a  href="#" onclick="test()" class="weui-btn   weui-btn_primary">返回</a>
    </div>
</div>


<script>
    function test() {
        window.location.href="${ctx}/wechat/index.act";
    }

    
    function bind(){

        if ($("#phone").val()=='') {
            weui.toast('请输入手机号!', 1000);
            return ;
        }
        if ($("#code").val()=='') {
                weui.toast('请输入验证码!', 1000);
            return ;
        }
        var loading = weui.loading('操作中', {
            className: 'custom-classname'
        });
        $.ajax({
            type:"post",
            url :"${ctx}/wechat/my/bindPhone.act",
            data:{phone:$("#phone").val(),code:$("#code").val()},
            success:function(data){

                if (data.ret==0){
                    loading.hide(function() {
                        weui.toast('操作成功!', 1000);
                        window.location.href = "${ctx}/wechat/index.act";
                    });
                } else{
                    loading.hide(function() {
                        weui.toast(data.msg, 1000);
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
    var countdown=60;
    function sendCode(obj){

           if (countdown>0&&countdown<60){
               return ;
           } else {
                if(countdown==60||countdown==0){
                    if ($("#phone").val()==''||$("#phone").val().length!=11){
                        weui.alert('请输入正确的手机号码!');
                        return ;
                    }
                    var loading = weui.loading('操作中', {
                        className: 'custom-classname'
                    });
                    $.ajax({
                        type:"post",
                        url :"${ctx}/wechat/my/sendCode.act",
                        data:{phone:$("#phone").val()},
                        success:function(data){

                            if (data.ret==0){
                                loading.hide(function() {
                                    weui.toast("发送成功!",1000)
                                });
                                settime(obj);
                            } else{
                                loading.hide(function() {
                                    weui.toast(data.msg, 1000);
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

           }
    }

    function settime(val) {
        if (countdown == 0) {
           // val.removeAttribute("disabled");
           $(val).html("获取验证码");
            countdown = 60;
        } else {
           // val.setAttribute("disabled", true);
            $(val).html("重新发送(" + countdown + ")");
            countdown--;
            setTimeout(function() {
                settime(val)
            },1000)
        }

    }

</script>

</body>
</html>

