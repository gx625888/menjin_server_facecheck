<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>智慧门禁</title>
    <link rel="stylesheet" href="${ctx}/styles/wechart/comStyle.css">
    <link rel="stylesheet" href="${ctx}/styles/wechart/style.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/wechart/dialog.css" />
    <script type="text/javascript" src="${ctx}/script/wechart/dialog.js"></script>
    <script src="${ctx}/script/wechart/adaptive.js"></script>
    <script>
        window['adaptive'].desinWidth = 750;
        window['adaptive'].baseFont = 24;
        window['adaptive'].init();
    </script>
    <style>

    </style>
</head>
<body>
<div class="modal_login">
    <!-- 选择小区 -->
    <div class="modal_wrap">
        <div class="section_one clearfix">
            <p class="fl ">
                <a href="#"><</a> </p>
            添加门卡
        </div>
        <div class="section_two clearfix"  >
            <div class="seclect_community fl">
                <p>选择小区</p>
                <div class="line line-one active "></div>
            </div>

            <div class="seclect_room fl">
                <p>选择房间</p>
                <div class="line line-two"></div>
            </div>

            <div class="seclect_room fl">
                <p>绑定结果</p>
                <div class="line line-three"></div>
            </div>
        </div>
        <!-- 选择小区 -->
        <div class="choose_community"  >
            <div class="section_three clearfix">
                <select class="fl choose" onchange="querySelecetObj(this.options[this.options.selectedIndex].value,2)" id="residentail">
                    <option value="0">请选择您居住的小区</option>
                    <c:forEach items="${residentail}" var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="section_four clearfix" >

            </div>
            <div class="section_five " >
                下一步
            </div>
        </div>


        <!-- 选择房间 -->
        <div class="choose_room"  >
            <div class="wrap_room_one">
                <div class="section_three clearfix">
                    <select class="fl choose" id="build" onchange="querySelecetObj(this.options[this.options.selectedIndex].value,3)">
                        <option value="0">请选择您居住的小区栋号</option>
                    </select>
                </div>
                <div class="section_three clearfix">
                    <select class="fl choose" id="unit" onchange="querySelecetObj(this.options[this.options.selectedIndex].value,4)">
                        <option value="0">请选择您居住的小区单元号</option>
                    </select>
                </div>
                <div class="section_three clearfix">
                    <select class="fl choose" id="floor" onchange="querySelecetObj(this.options[this.options.selectedIndex].value,5)">
                        <option value="0">请输入您居住的小区楼层</option>
                    </select>
                </div>
                <div class="section_three clearfix">
                    <select class="fl choose" id="house">
                        <option value="0">请输入您居住的小区房间号</option>
                    </select>
                </div>
                <input type="text" id="phone" placeholder="请输入预留的电话/手机号码" class="r_five">
            </div>
            <div class="wrap-tips">
                <div class="tips_t_o">
                    <p>姓名和号码请如实填写，您的亲友将通过此信息添加门卡。</p><br>
                </div>
                <div class="tips-b">
                    <p>提示户主不存在说明房间号填写出错!</p><br>
                    <p>提示没有电话号码验证时请去物业填写房间信息!</p><br>
                </div>
            </div>
            <div class="oBnt clearfix">
                <div class="oBn_l fl">上一步</div>
                <div class="oBn_r fr">下一步</div>
            </div>
        </div>
        <div class="band_info">
            <div class="section_four clearfix" >
                <div class="click-search">
                    添加成功
                </div >
            </div>
            <div class="section_five finish" >
                完成
            </div>
        </div>
    </div>
</div>
<script src="${ctx}/script/wechart/jquery-1.11.3.min.js"></script>
<script src="${ctx}/script/wechart/login.js"></script>
<script>
    $(function () {
        $('.fr').click(function () {
            var build = $('#build').val();
            var unit = $('#unit').val();
            var floor = $('#floor').val();
            var house = $('#house').val();
            var person = $('#person').val();
            var phone = $('#phone').val();
            if (build==0){
                dialogAlert("请选择楼栋");
            }
            if (unit==0){
                dialogAlert("请选择单元");
            }
            if (floor==0){
                dialogAlert("请选择楼层");
            }
            if (house==0){
                dialogAlert("请选择房号");
            }

            if (phone==null || phone==''){
                dialogAlert("请输入手机号");
            }
            $.ajax({
                type:"post",
                url :"${ctx}/wechat/insertWxBand.act",
                data:{"house":house, "phone":phone},
                success:function(data){
                    if(data.ret==0){
                        $('.choose_community').hide();
                        $('.choose_room').hide();
                        $('.band_info').show();
                        $(' .line-two').removeClass('active').addClass('actived');
                        $('.line-three').addClass('active');
                    }else {
                        dialogAlert(data.msg);
                    }
                },
                error:function(){
                    dialogAlert("服务器开小差了!");
                }
            });

            //dialogAlert("1234");
        })
    })
    function querySelecetObj(id,s_type) {
        $.ajax({
            type:"post",
            url :"${ctx}/wechat/selectObj.act",
            data:{"id":id, "type":s_type},
            success:function(data){
                var html = '';
                var temp = eval(data);
                $.each(temp, function(i, prd) {
                    html += "<option value='"+prd.id+"'>"+prd.name+"</option>";
                });
                if (s_type==2){
                    $('#build').append(html);
                } else if (s_type==3){
                    $('#unit').append(html);
                } else if (s_type==4){
                    $('#floor').append(html);
                } else {
                    $('#house').append(html);
                }
            },
            error:function(){
                dialogAlert("服务器开小差了!");
            }
        });

    }
</script>

</body>
</html>