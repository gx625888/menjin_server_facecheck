var commonChart = {
    textStile:{
        title:{
            color : 'rgba(30,144,255,0.8)',
            fontFamily : '微软雅黑',
            fontSize : 16,
            fontWeight : 'bolder'
        },
        legend: {
            color : 'rgba(30,144,255,0.8)',
            fontFamily : '微软雅黑',
            fontSize : 12,
            fontWeight : 'bolder'
        },
        axisLabel: {
            color: '#fff'
        },
        series:{
            color : 'rgba(30,144,255,0.8)',
            fontFamily : '微软雅黑',
            fontSize : 16,
            fontWeight : 'bolder'
        },
        dataZoom:{
            color: '#fff'
        }
    },
    util:{
        changeDataToArray:function (data,name) {
            var arr = [];
            for (var i=0;i<data.length;i++){
                arr.push(data[i][name]);
            }
            return arr;
        }
    }
}