/**
 * 首页仪表数据加载缓存
 *
 */
var DATA = {
    CACHE:{},
    loadData:function(url,param,type,cacheType,callBack){

        var data = DATA.CACHE[type+param[cacheType]];
        if (type == null){
            data = null;
        }

        if(data==undefined||data==null){
            $.post(url,param,function (result) {
                if(cacheType!=undefined){
                    DATA.CACHE[type+param[cacheType]]=result;
                }
                result = $.extend(true,{},result);
                callBack.call(this,result);
            });

        }else {
            data = $.extend(true,{},data);
            callBack.call(this,data);
        }
    }

};