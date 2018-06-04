/**
 * Created by Administrator on 2017/9/27 0027.
 */
var init = {
    area:util.getInitArea().city,
    time:util.getTimestamp(new Date().format("yyyy-MM-dd hh"+":00"))
};
var windytyInit = {
    key: 'PsL-At-XpsPTZexBwUkO7Mx5I',
    lat: 30,
    lon: 104,
    zoom: 6,
    zoomSnap:1
};
var map_S;
function windytyMain(map) {
	map_S=map;
    //天地图划分区域镜像
    L.tileLayer("http://t{s}.tianditu.cn/ibo_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=ibo&tileMatrixSet=w&TileMatrix={z}&TileRow={y}&TileCol={x}&style=default&format=tiles", {
        subdomains: ["0", "1", "2", "3", "4", "5", "6", "7"]
    }).addTo(map);
    // 地名标注
    L.tileLayer("http://t{s}.tianditu.cn/cia_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=cia&tileMatrixSet=w&TileMatrix={z}&TileRow={y}&TileCol={x}&style=default&format=tiles", {
        subdomains: ["0", "1", "2", "3", "4", "5", "6", "7"]
    }).addTo(map);
    // 边界
    L.tileLayer("http://t{s}.tianditu.cn/ibo_w/wmts?service=wmts&request=GetTile&version=1.0.0&LAYER=ibo&tileMatrixSet=w&TileMatrix={z}&TileRow={y}&TileCol={x}&style=default&format=tiles", {
        subdomains: ["0", "1", "2", "3", "4", "5", "6", "7"]
    }).addTo(map);

    $("#area span").click(function () {
        $("#provinceselect").removeClass('active');
        $("#area span").removeClass('active');
        $(this).addClass("active");
        if($(this).html()=="全国"){
            map.setView([30.67,108],5);
        }else {
            map.setView([30,104],6);
        }
        var params = {cityName:init.area};
        index.getpaneldata(params)
    });
    $("#provinceselect").change(function () {
        if (!$(this).hasClass('active')){
            $("#area span").removeClass('active');
            $(this).addClass('active')
        }
        var txt =  $(this).val();
        for(var i=0;i<mockdata.area.length;i++){
            if(mockdata.area[i].name == txt){
                var e    = mockdata.area[i].point.lon;
                var n    = mockdata.area[i].point.lat,
                    zoom = mockdata.area[i].zoom;
                map.setView([n,e],zoom);
                var params = {cityName:mockdata.area[i].city};
//              index.getpaneldata(params)
            }
        }
    });


    // html elements
    var overlays = document.getElementById('overlays'),
        levels = document.getElementById('levels');
    // Display actual state of a map

    // Handle change of overlay
    overlays.onclick = function(event) {
        $("#overlays li").removeClass('active');
        event.target.className="active";
        W.setOverlay(event.target.id)
    };

    // Handle change of level
    levels.onclick = function(event) {
        $("#levels li").removeClass('active');
        event.target.className="active";
        W.setLevel(event.target.id);
    };

    $("#rightTop").addClass("control-sidebar-open");
    //初始化获取数据
    var mapparams = {time:"",cityName:init.area,timeType:0,type:2};
    index.getmapdata(mapparams,map);
    var params = {cityName:init.area};
    index.getpaneldata(params);
    /*//时间轴初始化
    $("#days").html(util.getCanlender(getChartDate(-4,0),"hour"));*/
   /* index.getPlayTime();*/


    //气体切换
    $("#gasType span").click(function () {
          $("#gasType span").removeClass('active');
          $(this).addClass('active');
          var data = index.getParams();
          index.getmapdata(data.params,map,data.gas)
    });
    //自动播放
    var timeer = "";
    $("#playIcon").click(function () {
        if ($("#rightTop").hasClass("control-sidebar-open")){
            $("#controlSidebar").click()
        }
        var $icon = $("#playIcon i");
        if ($icon.hasClass('icon-bofang')){
            index.getGasTypeHeight(false);
            $icon.removeClass('icon-bofang');
            $icon.addClass('icon-zanting');
            timeer = setInterval(_swiper(index),2000);
        }else {
            $icon.removeClass('icon-zanting');
            $icon.addClass('icon-bofang');
            clearInterval(timeer)
        }
    });

    //自动播放
    function swiper(obj) {
        var type   = obj.getdataType(),
            index  = $("#days .active").index(),
            total  = $("#days>li").length,
            childIndex,
            time = $("#days .active").attr('id'),
            today = new Date().format('yyyy-MM-dd');
        if (type==1){
            index += 1;
            if (index>=total){
                index = 0
            }
            $("#days li").removeClass('active');
            $("#days li").eq(index).addClass('active');
            obj.getPlayTime();
        }else {
            childIndex = $("#days .activebg").index();
            var day = util.compareDate(today,time);
            var hour = (new Date().getHours())-1;
            var hourIndex = ((day==1)?24:hour);
            childIndex += 1;
            if (childIndex>=hourIndex){
                childIndex =0;
                index += 1
            }
            if (index>=total){
                index = 0
            }
            $("#days>li").removeClass('active');
            $("#days>li").eq(index).addClass('active');
            $(".menu-grade li").removeClass('activebg');
            $("#days>.active li").eq(childIndex).addClass('activebg');
            obj.getPlayTime();
        }
        var data = obj.getParams();
        var _data = data;
        W.setTimestamp(+data.params.time);
        urlconfig.getAllCityAQIData(data.params,function (data) {
            if (!data.gasType){
                data.gasType="AQI"
            }
            util.setMarker(data.data,map,data.gasType,function (params) {
            	index.getpaneldata(params)
            },_data.params.timeType);
            
        });
    }
    //向定时器传入对象参数
    function _swiper(obj){
        return function(){
            swiper(obj);
        }
    }




//小时数据和日数据切换
    $("#timeconversation span").click(function () {
        clearInterval(timeer);
        var dataType = 0;
        if ($("#playIcon i").hasClass('icon-zanting')){
            $("#playIcon i").removeClass('icon-zanting');
            $("#playIcon i").addClass('icon-bofang');
        }
        $("#timeconversation span").removeClass('active');
        $(this).addClass('active');
       
        if (!$("#rightTop").hasClass("control-sidebar-open")){
            $("#controlSidebar").click();
        }
        if ($(this).html()=="日"){
            $("#days").html(util.getCanlender(getChartDate(-6,3)));
            index.getGasTypeHeight(false);
            ClassFun();
            dataType = 1;
        }else {
            index.getGasTypeHeight(true);
            $("#days").html(util.getCanlender(getChartDate(-4,0),"hour"));
            ClassFun();
            dataType = 0;
        }
        index.getPlayTime();
         var hour = (new Date().getHours())-1;
       
         var time = $("#days .active").find("span").html();
        if ($(this).html()=="小时"){
            time += " "+hour
        }
       
       $("#appointTime").html(time)
        var data = index.getParams();
        var header = {'Content-Type':'application/json;charset=UTF-8'};
        if(dataType==0){
        	data.params.timeType = 0;
        	data.params.time='';
        }else{
        	data.params.timeType = 1;
        }
        urlconfig.getAllCityAQIData(data.params,function (data) {
            if (!data.gasType){
                data.gasType="AQI"
            }
            util.setMarker(data.data,map,data.gasType,function (params) {
            	index.getpaneldata(params)
            },Number(dataType));
        },header);       
    });
}






//存放地图覆盖物变量
var myGroup;
var index = {
    //获取地图圆点数据
    getmapdata :function(params,map,type,glag){
    	var header = {'Content-Type':'application/json;charset=UTF-8'};
        urlconfig.getAllCityAQIData(params,function(data){
            if (!type){
                type="AQI"
            }
            var timeHourType= data.data[0].time2;
            if(glag==1){
            	
            }else{
            	 $("#days").html(util.getCanlender(getChartDate(-4,0),"hour",timeHourType));
            }
           
            index.getPlayTime();
            util.setMarker(data.data,map,type,function (params) {
            	index.getpaneldata(params)
            },params.timeType);
        },header);
    },
    //获取右边面板数据
    getpaneldata:function (params) {
    var header = {'Content-Type':'application/json;charset=UTF-8'};
        urlconfig.getPanelData(params,function (data) {
            var gas = data.cityData.primaryPollutant,
                pollutegas = "";
            $("#cityName").html(data.cityData.cityname);
            $("#freshTime").html(new Date(data.cityData.time).format("yyyy-MM-dd hh:mm")+"更新");
            $("#ranking").html(data.cityData["ranking"]);
            $("#rankingRate").html(data.cityData["rate"]);
            if(gas=="PM2.5"){
                gas = "PM25"
            }
            var primary = util.levelReturn(gas,"20");
            if (primary){
                pollutegas = primary.gas+"<small class='subscript'>"+primary.last
            }else {
                pollutegas = "-"
            }
            $("#primarypllute").html("<p>主要污染物："+pollutegas+"</small></p><p>污染浓度："+data.cityData["concentration"]+"</p>");
            var circle = util.levelColor(util.levelReturn("AQI",data.cityData["AQI"]));
            var a = new CircleProgress({
                element: document.getElementById('circle'),
                current: circle.grade/6,
                circleColor:circle.color,
                grade:circle.state,
                value:data.cityData["AQI"]
            });
            var obj = [];
            for (var i=0;i<mockdata.gas.length;i++){
                var gasname = mockdata.gas[i].name;
                var gasvalue = data.cityData[gasname];
                var gasdata = util.levelColor(util.levelReturn(gasname,gasvalue));
                obj.push(gasdata)
            }
            util.getlevel(obj,pollutegas,data.cityData["concentration"]);
            var list   = "<ul>",
                _class = "",
                length = data.allCityData.length;
            for(var j=0;j<length;j++){
                 var childgas = data.allCityData[j].primaryPollutant;
                 if (childgas == "PM2.5"){
                     childgas = "PM25"
                 }
                 var primarygas = util.levelReturn(childgas,"20"),
                     primarypollute;
                 if(primarygas){
                     primarypollute = primarygas.gas+"<small class='subscript'>"+primarygas.last+"</small>"
                 }else {
                     primarypollute = "-"
                 }
                 var bgcolor = (util.levelColor(util.levelReturn("AQI",data.allCityData[j].AQI))).color;
                 if (j%2==0){
                     _class = "withbg"
                 }else {
                     _class = ""
                 }
                list += "<li class='"+_class+"'><table><tr><td>"+(j+1)+"</td><td>"+data.allCityData[j].cityname+"</td><td><span class='pollute-grade' style='color: "+bgcolor+"'>"+data.allCityData[j].AQI+"</span></td><td>"+primarypollute+"</small></td></tr></table></li>"
            }
           /* if (length%2!==0){
                list += "<li><table><tr><td></td><td></td><td></td><td></td></tr></table></li>"
            }*/
            list+="</ul>";
            $(".table-container").html(list)
        },header);

    },
    //获取日数据类型
    getdataType:function () {
        var day = $("#timeconversation .active").html();
        if (day=="日"){
            return 1
        }else {
            return 2
        }
    },
    //获取气体
    getgasType:function () {
    	 ClassFun();
        return $("#gasType .active").attr("type")
    },
    //获取参数类型以及时间
    getTimeType:function () {
        var type = this.getdataType(),
            time = "" ,
            today;
        if (type==1){
             today = new Date().format("yyyy-MM-dd");
             time = $("#days .active").attr('id')
        }else {
             today = new Date().format("yyyy-MM-dd hh"+":00");
             time =  $("#days .activebg").attr('id');
             if(time==undefined){
             	time = new Date().format("yyyy-MM-dd hh"+":00");
             }
            
        }
        days = util.compareDate(today,time);
        var timestamp = util.getTimestamp(time);
        if (days==1){
            return {type:0,timestamp:timestamp}
        }else {
            return {type:1,timestamp:timestamp}
        }
    },
    //查询参数
    getParams:function (config) {
        if (!config){
            config = {}
        }
        var gasType    = this.getgasType(),
            type = this.getdataType(),
            timeType = this.getTimeType();
        var backparams = {
            gas:gasType,
            params:{
                type:config.type || type,
                timeType:config.timeType || timeType.type,
                time:config.timestamp || timeType.timestamp
            }
        };
        return backparams
    },
    //获取气体参数选择
    getGasTypeHeight:function (bool) {
        if (bool){
            $("#gasType").animate({"height":"178px"});
        }else {
            $("#gasType span").removeClass('active');
            $("#gasType li").eq(0).find('span').addClass('active');
            $("#gasType").animate({"height":"28px"});
        }
    },
    //获取播放时间
    getPlayTime:function () {
        var time = $("#days .active").find("span").html();
        if (this.getdataType()==2){
            time += " "+$(".menu-grade .activebg").html()
        }

        $("#appointTime").html(time)
    }
};

 //时间切换
    $("#days").on("click","li", function(event) {
        event.stopPropagation();
        var dataType = index.getdataType();
        var _calss = $(this).attr('class');
        if (_calss=="nowadays"){
               if (!$("#rightTop").hasClass('control-sidebar-open')){
                  $("#controlSidebar").click()
               }
        }else {
            if ($("#rightTop").hasClass('control-sidebar-open')){
                  $("#controlSidebar").click()
            }
        }
        if (dataType == 1){
            $(this).prevAll().removeClass("active");
            $(this).nextAll().removeClass("active");
            $(this).addClass('active');
            var time = $(this).attr('id'),
                today = new Date().format('yyyy-MM-dd');
            var days = util.compareDate(today,time);
            if (days==1){
                index.getGasTypeHeight(true)
            }else {
                index.getGasTypeHeight(false)
            }
        }else {
            if ($(this).hasClass("forbid")){
                return
            }
            $("#days>li").removeClass('active');
            $(".menu-grade li").removeClass('activebg');
            if ($(this).parent().hasClass('menu-grade')){
                $(this).parent().parent().addClass('active');
                $(this).addClass('activebg');
            }else {
                $(this).addClass('active');
                $(this).find('.menu-grade li').eq(0).addClass('activebg');
            }
        }
        index.getPlayTime();
        var data = index.getParams();
        W.setTimestamp(+data.params.time);
        index.getmapdata(data.params,map_S,data.gas,1)
    });

    //时间hover事件
    var _indexall = 0;
    setTimeout(function(){
    	var dataType = index.getdataType();
    	if(dataType==2){ 
    		
    		$.each($("#days>li"),function(i,v){
    			$(v).children('.menu-grade').addClass('menuGrede')
    		})
    	}else{
    		$.each($("#days>li"),function(i,v){
    			$(v).children('.menu-grade').removeClass('menuGrede')
    		})
    	}  	
		
    },800)

function ClassFun(){
	var dataType = index.getdataType();
    	if(dataType==2){
    		$.each($("#days>li"),function(i,v){
    			$(v).children('.menu-grade').addClass('menuGrede')
    		})
    	}else{
    		$.each($("#days>li"),function(i,v){
    			$(v).children('.menu-grade').removeClass('menuGrede')
    		})
    	}  	
}

