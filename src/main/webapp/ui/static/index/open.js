var openRecordChart = echarts.init(document.getElementById('test'));
function initOpenRecord(obj,days) {
    $("#testDiv").html(' <div id="test" style="width: 100%;height: 250px"></div>');
    openRecordChart = echarts.init(document.getElementById('test'));
    if (obj!=null){
        $(obj).parent().find("button").removeClass('layui-btn-danger');
        $(obj).addClass('layui-btn-danger');
    }

    openRecordChart.showLoading();

    DATA.loadData(ctx+"/index/chart/openrecord.shtml?day="+days,{"days":days},"openredord","days",function (data) {
        loadOpenRecord(data);
    });


}
function loadOpenRecord(data) {
    
    var title = '今日开门统计';
    if (data.days==2){
        title = '近三日开门统计';
    } else if (data.days==6) {
        title = '一周开门统计';
    }
    var dayList = data.dayList;
    var xq = {};
    dayList.push("共计");
    for (var i=0;i<data.data.length;i++){
        var obj = data.data[i];
        if (xq[obj.name]==undefined){
            xq[obj.name]={};
            xq[obj.name].all = 0;
        }
        xq[obj.name][obj.time] = obj.num;
        xq[obj.name].all += obj.num;
    }
    var xAxisArr = [];

    for(var xqName in xq){
        xAxisArr.push(xqName);
    }

    var seriesArr = [];

    for (var i=0;i<dayList.length;i++){
        var daySeries = {};
        var name = dayList[i];
        daySeries.name=name;
        daySeries.type = 'bar';
        daySeries.data=[];
        if (name=="共计"){
            daySeries.type = 'line';
            for(var xqName in xq){
                daySeries.data.push(xq[xqName]['all']);
            }
        }else{
            for(var xqName in xq){
                daySeries.data.push(xq[xqName][name]==null?0:xq[xqName][name]);
            }
        }


        seriesArr.push(daySeries);
    }


    var option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data:dayList
        },
        xAxis: {
            type: 'category',
            data: xAxisArr,
            axisPointer: {
                type: 'shadow'
            }
        },
        yAxis: {},
        series: seriesArr
    };

    openRecordChart.setOption(option);
    openRecordChart.hideLoading();
}





var warnChart = echarts.init(document.getElementById('test'));
function initWarn(obj,days) {
    $("#warnDiv").html(' <div id="warntable" style="width: 100%;height: 250px"></div>');
    warnChart = echarts.init(document.getElementById('warntable'));
    if (obj!=null){
        $(obj).parent().find("button").removeClass('layui-btn-danger');
        $(obj).addClass('layui-btn-danger');
    }

    warnChart.showLoading();

    DATA.loadData(ctx+"/index/chart/warn.shtml?day="+days,{"days":days},"warn","days",function (data) {
        loadWarn(data);
    });


}
function loadWarn(data) {

    var title = '今日告警统计';
    if (data.days==2){
        title = '近三日告警统计';
    } else if (data.days==6) {
        title = '一周告警统计';
    }
    var dayList = data.dayList;
    var xq = {};
    dayList.push("共计");
    for (var i=0;i<data.data.length;i++){
        var obj = data.data[i];
        if (xq[obj.name]==undefined){
            xq[obj.name]={};
            xq[obj.name].all = 0;
        }
        xq[obj.name][obj.time] = obj.num;
        xq[obj.name].all += obj.num;
    }
    var xAxisArr = [];

    for(var xqName in xq){
        xAxisArr.push(xqName);
    }

    var seriesArr = [];

    for (var i=0;i<dayList.length;i++){
        var daySeries = {};
        var name = dayList[i];
        daySeries.name=name;
        daySeries.type = 'bar';
        daySeries.data=[];
        if (name=="共计"){
            daySeries.type = 'line';
            for(var xqName in xq){
                daySeries.data.push(xq[xqName]['all']);
            }
        }else{
            for(var xqName in xq){
                daySeries.data.push(xq[xqName][name]==null?0:xq[xqName][name]);
            }
        }


        seriesArr.push(daySeries);
    }


    var option = {
        title: {
            text: title
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        legend: {
            data:dayList
        },
        xAxis: {
            type: 'category',
            data: xAxisArr,
            axisPointer: {
                type: 'shadow'
            }
        },
        yAxis: {},
        series: seriesArr
    };

    warnChart.setOption(option);
    warnChart.hideLoading();
}




function initOpenType(flag){
    var opentype = echarts.init(document.getElementById('opentype'));

    opentype.showLoading();

    DATA.loadData(ctx+"/index/chart/openType.shtml",{"days":1},"openType","days",function (data) {

        var seriesData = [
            {value:0, name:'刷卡开门'},
            {value:0, name:'管理员开门'},
            {value:0, name:'呼叫开门'},
            {value:0, name:'微信开门'},
            {value:0, name:'临时密码开门'}
        ];
        for (var i=0;i<seriesData.length;i++){
            if (data[i]!=undefined){
                seriesData[data[i]['openType']].value = data[i]['num'];
            }
        }

       var  option = {
            title : {
                text: '开门方式统计',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['刷卡开门','管理员开门','呼叫开门','微信开门','临时密码开门']
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:seriesData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        opentype.setOption(option);
        opentype.hideLoading();
    });

}



function initDeviceStatus(flag){
    var deviceStatus = echarts.init(document.getElementById('deviceStatus'));
    deviceStatus.showLoading();

    DATA.loadData(ctx+"/index/chart/deviceStatus.shtml",{"days":1},"deviceStatus","days",function (data) {
        var xAxisArr = [];
        var seriesArr = [
            {
                name: '离线',
                type: 'bar',
                data: []
            },
            {
                name: '在线',
                type: 'bar',
                data: []
            }
        ];
        for (var key in data){
            var obj = data[key];

            xAxisArr.push(obj.name);
            seriesArr[0].data.push(obj.allCount - obj.online);
            seriesArr[1].data.push( obj.online);

        }

        var option = {
            title: {
                text: '设备状态'
            },
            tooltip: {},
            legend: {
                data:['离线','在线']
            },
            xAxis: {
                data: xAxisArr
            },
            yAxis: {},
            series: seriesArr
        };
        deviceStatus.setOption(option);
        deviceStatus.hideLoading();


    });

}