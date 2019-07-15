<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>WeUI</title>
    <!-- 引入 WeUI -->
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.3/weui.min.css">
    <script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.4/weui.min.js"></script>
</head>
<body ontouchstart>
<!-- 白色按钮 -->
<!-- 绿色按钮 -->


<div class="weui-flex">
    <div class="weui-flex__item">
        <div style="height: 10rem;background-color: #00b7ee"></div>

    </div>
</div>
<div class="weui-flex">
    <div class="weui-flex__item"><a href="#" onclick="test()" class="weui-btn weui-btn_primary">按钮</a></div>
    <div class="weui-flex__item"><a href="#" onclick="test()" class="weui-btn weui-btn_primary">按钮</a></div>
</div>
<div class="weui-flex">
    <div class="weui-flex__item">123</div>
    <div class="weui-flex__item">123</div>
    <div class="weui-flex__item">123</div>
</div>
<script>
    function test() {
        weui.alert(123);
    }
    var loading = weui.loading('loading', {
        className: 'custom-classname'
    });
    setTimeout(function () {
        loading.hide(function() {
            console.log('`loading` has been hidden');
        });
    }, 3000);
</script>

</body>
</html>

