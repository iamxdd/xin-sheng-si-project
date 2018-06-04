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
    var startdate = new Date(new Date()-24*60*6*60*1000).format("yyyy/MM/dd 00:00:00");
    var enddate = new Date(new Date()-(24*60*(-8)*60*1000)).format("yyyy/MM/dd 00:00:00");
    var formdate = new Date(new Date()).getTime();
//  console.log('startdate',startdate)
    SetProgressTime(null, startdate, enddate,formdate)
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
            $('.line-box').hide();
            $('.pp').children('dl').hide();
            $('.pp').children('p').css('line-height','40px')
        }else {
            index.getGasTypeHeight(true);
            $("#days").html(util.getCanlender(getChartDate(-4,0),"hour"));
            ClassFun();
            dataType = 2;
            $('.line-box').show();
            $('.pp').children('dl').show();
            $('.pp').children('p').css('line-height','0')
        }
         var hour = (new Date().getHours())-1;
       
         var time = $("#days .active").find("span").html();
        if ($(this).html()=="小时"){
            time += " "+hour
        }
       
       		$("#appointTime").html(time)
         	var mapparams = {time:"",cityName:init.area,timeType:0,type:dataType};
	    	index.getmapdata(mapparams,map);       
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
//          var timeHourType= data.data[0].time2;
//          if(glag==1){
//          	
//          }else{
//          	 $("#days").html(util.getCanlender(getChartDate(-4,0),"hour",timeHourType));
//          }
           
            index.getPlayTime();
            util.setMarker(data.data,map,type,function (params) {
            	index.getpaneldata(params);
            	urlconfig.getBouncedData(params,function(datas){
            		var lastdata = datas.data.length-1;
            		console.log(lastdata)
            		if(datas.data.length<28){
            			datas.data.length=28;
            			for(var i=0;i<28;i++){
            				if(datas.data[i]==undefined){
            					datas.data[i] = {
            						AQI:'0',
									CO:'0',
									NO2:'0',
									O3:'0',
									PM2_5:'0',
									PM10:'0',
									SO2:'0',
									cityName:datas.data[lastdata].cityName,
									time:new Date(new Date(datas.data[lastdata].time)-24*60*(-Number(i-lastdata))*60*1000).format("yyyy-MM-dd"+" "+"00:00:00")
            					};
            				}
            			}
            		}
            		var colorData =getcolordata(datas.data,'AQI'),
            			xAxisData = getxAxis(datas.data),
            			seriesData = getoptiondata(datas.data,'AQI'),
            			changeGas = 'AQI',
            			today = new Date().format("MM-dd"),
            			nextday = new Date(new Date()-24*60*(-1)*60*1000).format("MM-dd");
            		var option = {
            			 tooltip : {//鼠标悬浮弹窗提示  
           /*  trigger: 'axis' */  
		            	  trigger : 'item',  
			              show:true,  
			              showDelay: 0,  
			              hideDelay: 0,  
			             transitionDuration:0,   
			             backgroundColor : '#fff',
			             borderColor : 'yellow',  
			             borderRadius : 8,  
			             borderWidth: 2,  
			             padding: 10,    // [5, 10, 15, 20]  
		                formatter: function (params,ticket,callback) {  
		                	if(params.componentType=='series'){       		
				                 var res = datas.data[lastdata].cityName+':'+params.name+   '<br/>'+"最近及未来"+changeGas+"变化曲线"+' : ' + params.data;  
				                 return res; 
		                	}
		                   
		               } ,
		               textStyle : {
			            color: '#000',
			            decoration: 'none',
			            fontFamily: 'Verdana, sans-serif',
			            fontSize: 10,
			            fontStyle: 'italic',
			            fontWeight: 'bold'
			        },
		        },  
				     xAxis: [{
				                type : 'category',
				                data :  xAxisData,
				                axisTick: {
					                alignWithLabel: false
					            },
					             splitLine:{show: false}
					         }],
					 yAxis: [{
					            type : 'value',
					            splitLine:{show: false}
							}],
				     series: [{
					        data: seriesData,
					        type: 'bar',
					        //配置样式
					        itemStyle: {   
					            //通常情况下：
					            normal:{  
					　　　　　　　　　　　　//每个柱子的颜色即为colorList数组里的每一项，如果柱子数目多于colorList的长度，则柱子颜色循环使用该数组
					                color: function (params){
					                    var colorList = colorData;
					                    return colorList[params.dataIndex];
					                }
					            },
					     
					            //鼠标悬停时：
					            emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					            }
					        },
					        //设置柱子的宽度
					         barWidth : 10,
					         markArea: {
					                data: [[{
					                    name: '历史', 
					                }, {
					                    xAxis: nextday,
					                     itemStyle:{
					                        color:'rgb(235,248,248)'
					                    },
					                    label:{
					                    	color:'#333333'
					                    }
					                }], [{
					                    name: '预测',
					                    xAxis: nextday
					                }, {
					                   
					                     itemStyle:{
					                        color:'rgb(248,235,248)'
					                    },
					                    label:{
					                    	color:'#333333'
					                    }
					                }] ]
					           }
					    }]
					};
					var myChart = echarts.init(document.getElementById('barset'));
					 myChart.setOption(option);
					 
            	},header)
            },params.timeType,'AQI');
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
            console.log('obj=>',obj)
            
            util.getlevel(obj);
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
             time = $("#scroll_Thumb").attr('datatime')
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



var lines = (new Date().getHours())+(6*24);
var _index = lines;//进度
var _mProgressTimer;//定时器
var _speed = 1000;
var myfun;//执行方法，当前时间为参数
function SetProgressTime(fun, startTime, endTime,nowTime) {
    myfun = fun;
    $("#progressTime").show();
    // 开始时间
    var startDate = new Date(startTime);
    var Year = startDate.getFullYear();
    var Month = (startDate.getMonth()+1) < 10 ? "0" + (startDate.getMonth()+1) : (startDate.getMonth()+1);
    var currentDate = startDate.getDate() < 10 ? "0" + startDate.getDate() : startDate.getDate();
    var Hours = startDate.getHours() < 10 ? "0" + startDate.getHours() : startDate.getHours();
    var Minutes = startDate.getMinutes() < 10 ? "0" + startDate.getMinutes() : startDate.getMinutes();
    var weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
    var week = weekArray[new Date(startDate).getDay()];
    // var indexStart2 = week + "  " + currentDate + " - " + Hours + ":" + Minutes;
    var indexStart2 = Hours + ":" + Minutes;
    var indexStart3 = Hours + ":" + Minutes;
    var firstStart = Year + "-" + Month + "-" + currentDate;
    // 结束时间
    var endDate = new Date(endTime);
    var endYear = endDate.getFullYear();
    var endMonth = (endDate.getMonth()+1) < 10 ? "0" + (endDate.getMonth()+1) : (endDate.getMonth()+1);
    var endcurrentDate = endDate.getDate() < 10 ? "0" + endDate.getDate() : endDate.getDate();
    var endHours = endDate.getHours() < 10 ? "0" + endDate.getHours() : endDate.getHours();
    var endMinutes = endDate.getMinutes() < 10 ? "0" + endDate.getMinutes() : endDate.getMinutes();
    var lastEnd = endYear + "-" + endMonth + "-" + endcurrentDate;
    $("#scroll_Thumb").html(indexStart2);
      $("#scroll_Thumb").attr('datatime',nowTime)
    $(".timecode").html(indexStart3);

    $("#startTime").text(startTime);
    $("#endTime").text(endTime);
    // 得到总天数
    function getDateDiff(date1,date2){
        var arr1=date1.split('-');
        var arr2=date2.split('-');
        var d1=new Date(arr1[0],arr1[1],arr1[2]);
        var d2=new Date(arr2[0],arr2[1],arr2[2]);
        return (d2.getTime()-d1.getTime())/(1000*3600*24);
    }
    var dateNum = getDateDiff(firstStart,lastEnd);
    var str = '';var olstr='';var linestr = '';
    $('.line-box').html('')
    for(var i = 0; i < dateNum; i ++){
        var d1 = new Date(startTime);
        var d2 = new Date(d1);
        d2.setDate(d1.getDate() + i);
        var weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
        var week = weekArray[new Date(d2).getDay()];
        var monthNum = d2.getDate() < 10 ? "0" + d2.getDate() : d2.getDate();
        var daymonth = Number(d2.getMonth()+1) < 10 ? "0" + Number(d2.getMonth()+1) : Number(d2.getMonth()+1)
        str += '<div class="pp"><p>'+daymonth+"-"+monthNum+"("+week+")" +'</p></div>';
        linestr+='<div class="line">'
//                         +' <ii class="timeline_i0" style="left: 12px;"></ii>'
                            +'<ii class="timeline_i0" style="left: 24px;"></ii>'
//                           +'<ii class="timeline_i0" style="left: 38px;"></ii>'
                           +'<ii class="timeline_i0" style="left: 55px;"></ii>'
//                         +'<ii class="timeline_i0" style="left: 62px;"></ii>'
//                          +'<ii class="timeline_i0" style="left: 74px;"></ii>'
                            +'<ii class="timeline_i0" style="left: 86px;"></ii>'
//                         +'<ii class="timeline_i0" style="left: 98px;"></ii>'
                           +'<ii class="timeline_i0" style="left: 111px;"></ii>'
                    +'</div>'
       
    }
    $(".time_slot").html(str);
    olstr='<dl>'
//  +'<dd>03</dd>'
     +'<dd>06</dd>'
//    +'<dd>09</dd>'
       +'<dd>12</dd>'
//      +'<dd>15</dd>'
         +'<dd class="eighten">18</dd>'
//        +'<dd>21</dd>'
          +'</dl>'
    $(".pp p").before(olstr);
    $('.line-box').html(linestr)
    console.log('olstr',olstr)
    // $(".pp").css({"width":"calc("+100/dateNum+"% - 1px)"});
    //设置最大值
    var qdsjDate = new Date(startTime);
    var jssjDate = new Date(endTime);
    ScrollBar.maxValue = Math.abs(qdsjDate - jssjDate) / 1000 / 60 / 60;
    //初始化
    ScrollBar.Initialize();
}
//滑块
var ScrollBar = {
    value: 0,
    maxValue: 40,
    step: 1,
    currentX: 0,
    Initialize: function () {
        if (this.value > this.maxValue) {
            alert("给定当前值大于了最大值");
            return;
        }
        this.GetValue();
        $("#scroll_Track").css("width", this.currentX + "px");
        $("#scroll_Thumb").css("margin-left", this.currentX + "px");
        this.Value();
        SetInterval(lines)
    },
    SetValue: function (aValue) {
        this.value = aValue;
        if (this.value >= this.maxValue) this.value = this.maxValue;
        if (this.value <= 0) this.value = 0;
        var mWidth = this.value / this.maxValue * $("#scrollBar").width() + "px";
        // console.log('mWidth=>',mWidth)
        $("#scroll_Track").css("width", mWidth);
        $("#scroll_Thumb").css("margin-left", mWidth);
    },
    Value: function () {
        var valite = false;
        var currentValue;
        // 点击进度条时滑块到达对应位置
        $("#scrollBarBox").click(function (event) {
            var changeX = event.clientX - ScrollBar.currentX;
            currentValue = changeX - ScrollBar.currentX - $("#scrollBar").offset().left;
            $("#scroll_Thumb").css("margin-left", currentValue + "px");
            $("#scroll_Track").css("width", currentValue + 2 + "px");
            if ((currentValue + 1) >= $("#scrollBar").width()) {
                $("#scroll_Thumb").css("margin-left", $("#scrollBar").width() - 1 + "px");
                $("#scroll_Track").css("width", $("#scrollBar").width() + 2 + "px");
                ScrollBar.value = ScrollBar.maxValue;
            } else if (currentValue <= 0) {
                $("#scroll_Thumb").css("margin-left", "0px");
                $("#scroll_Track").css("width", "0px");
                ScrollBar.value = 0;
            } else {
                ScrollBar.value = Math.round(currentValue * ScrollBar.maxValue / $("#scrollBar").width());
            }
            SetTime(ScrollBar.value);
            SetInterval(ScrollBar.value);
            _index = ScrollBar.value;
              
        });
        // 鼠标在进度条上面滑动时小滑块显示并对应相应的时间
        $("#scrollBarBox").mousemove(function (event) {
            var changeX = event.clientX - ScrollBar.currentX;
            currentValue = changeX - ScrollBar.currentX - $("#scrollBar").offset().left;
            $(".timecode").show().css("left", currentValue -28 + "px");
            if ((currentValue + 1) >= $("#scrollBar").width()) {
                $(".timecode").css("left", $("#scrollBar").width() - 43 + "px");
                ScrollBar.value = ScrollBar.maxValue;
            } else if (currentValue <= 0) {
                $(".timecode").css("left", "-28px");
                ScrollBar.value = 0;
            } else {
                ScrollBar.value = Math.round(currentValue * ScrollBar.maxValue / $("#scrollBar").width());
            }
            SetTime1(ScrollBar.value);
            
        });
        // 鼠标移入进度条时小滑块显示
        $("#scrollBarBox").mouseover(function (event) {
            $(".timecode").show();
        });
        // 鼠标移除进度条时小滑块消失
        $("#scrollBarBox").mouseout(function (event) {
            $(".timecode").hide();
             
        });
         $("#scrollBarBox").moveStopEvent(function(){
         	var time = $('#scroll_Thumb').attr('datatime');
            var _datatype = ($('#timeconversation').children('.active').text()==='日' ?  1 : 2);
            var mapparams = {time:time,cityName:init.area,timeType:0,type:_datatype};
	    	index.getmapdata(mapparams,map_S); 
         })
    },
    GetValue: function () {
        this.currentX = $("#scrollBar").width() * (this.value / this.maxValue);
    }
}

// 控制大滑块的当前时间
function SetTime(value) {
    var start = $("#startTime").html();
    var startDate = new Date(start);
    startDate.setHours(startDate.getHours() + 1 * value);//十五分钟为进度
    var month = startDate.getMonth() + 1 < 10 ? "0" + (startDate.getMonth() + 1) : startDate.getMonth() + 1;
    var currentDate = startDate.getDate() < 10 ? "0" + startDate.getDate() : startDate.getDate();
    var Hours = startDate.getHours() < 10 ? "0" + startDate.getHours() : startDate.getHours();
    var Minutes = startDate.getMinutes() < 10 ? "0" + startDate.getMinutes() : startDate.getMinutes();
    var Seconds = startDate.getSeconds() < 10 ? "0" + startDate.getSeconds() : startDate.getSeconds();
    var indexStart = startDate.getFullYear() + "/" + month + "/" + currentDate + " " + Hours + ":" + Minutes + ":" + Seconds;
    var weekArray = new Array("周日", "周一", "周二", "周三", "周四", "周五", "周六");
    var week = weekArray[new Date(startDate).getDay()];
    // var indexStart1 = week + "  " + currentDate + " - " + Hours + ":" + Minutes;
    var indexStart1 = Hours + ":" + Minutes;
    $("#scroll_Thumb").html(indexStart1);
    $("#scroll_Thumb").attr('datatime',startDate.getTime())
    if (window.parent.currentTime) {
        currentTime = indexStart;
    }
    if (typeof (myfun) == "function") {
        var jscode = new Function('return ' + myfun)();
        jscode(indexStart)
    }
    var time = $('#scroll_Thumb').attr('datatime');
            var _datatype = ($('#timeconversation').children('.active').text()==='日' ?  1 : 2);
            var mapparams = {time:time,cityName:init.area,timeType:0,type:_datatype};
	    	index.getmapdata(mapparams,map_S);  
}
// 控制小滑块的当前时间，小滑块时间变化时大滑块不变
function SetTime1(value) {
    var start = $("#startTime").html();
    var startDate = new Date(start);
    startDate.setHours(startDate.getHours() + 1 * value);//十五分钟为进度
    var Hours = startDate.getHours() < 10 ? "0" + startDate.getHours() : startDate.getHours();
    var Minutes = startDate.getMinutes() < 10 ? "0" + startDate.getMinutes() : startDate.getMinutes();
    var indexStart = Hours + ":" + Minutes;
    var indexStart2 = Hours + ":" + Minutes;
    $(".timecode").html(indexStart2);
    if (window.parent.currentTime) {
        currentTime = indexStart;
    }
    if (typeof (myfun) == "function") {
        var jscode = new Function('return ' + myfun)();
        jscode(indexStart)
    }
    
}

//开始 暂停
function progressTimeControl(img) {
    if ($(img).attr("title") == "暂停") {
        $(img).attr("title", "开始");
        $(img).addClass('runaction').removeClass('runpause');
        window.clearInterval(_mProgressTimer);
        
    }else {
        $(img).attr("title", "暂停");
       $(img).removeClass('runaction').addClass('runpause');
        _mProgressTimer = window.setInterval(function () {
            if (_index <= ScrollBar.maxValue) {
                _index += 1;
                ScrollBar.SetValue(_index);
                SetTime(_index)
            }else {
                progressTimeStop()
            }
        }, _speed);
    }
}

//停止
function progressTimeStop() {
    $("#progressTime_control").attr("title", "开始");
    $("#progressTime_control").css("background-image", "url(img/play.png)");
    $("#scroll_Thumb").css("margin-left", "0px");
    $("#scroll_Track").css("width", "0px");
    ScrollBar.value = 0;
    _index = 0;
    _speed = 1000;
    window.clearInterval(_mProgressTimer);
    SetTime(ScrollBar.value);
    SetInterval(_index);
}

//重制时间
function SetInterval(_index) {
    window.clearInterval(_mProgressTimer);
    if ($("#progressTime_control").attr("title") == "开始") {
        ScrollBar.SetValue(_index);
        SetTime(_index)
    }else{
        _mProgressTimer = window.setInterval(function () {
            if (_index <= ScrollBar.maxValue) {
                _index += 1;
                ScrollBar.SetValue(_index);
                SetTime(_index)
            }else {
                progressTimeStop()
            }
        }, _speed);
    }
}
function isNull(object){ 
    if(object == null || typeof object == "undefined"){ 
        return true; 
    } 
    return false; 
};
/**
 * 根据日期字符串获取星期几
 * @param dateString 日期字符串（如：2016-12-29），为空时为用户电脑当前日期
 * @returns {String}
 */
function getWeek(dateString){
    var date;
    if(isNull(dateString)){
        date = new Date();
    }else{
        var dateArray = dateString.split("-");
        date = new Date(dateArray[0], parseInt(dateArray[1] - 1), dateArray[2]);
    }
    //var weeks = new Array("日", "一", "二", "三", "四", "五", "六");
    //return "星期" + weeks[date.getDay()];
    return "星期" + "日一二三四五六".charAt(date.getDay());
};
/*得到横坐标*/
function getxAxis(arr){
	var result = [];
	arr.forEach(function(v){
		result.push(new Date(v.time).format("MM-dd"))
	});
	return result;
}
/*得到option数据*/
function getoptiondata(arr,gas){
	var result = [];
	arr.forEach(function(v){
		result.push(v[gas])
	});
	return result;
}
/*得到颜色数据*/
function getcolordata(arr,gas){
	var result = [];	
	arr.forEach(function(v){
		result.push(util.levelColor(util.levelReturn(gas,v[gas])).color)
	});
	return result;
}

(function($){
 $.fn.moveStopEvent = function(callback){
  return this.each(function() {
   var x = 0,
    y = 0,
    x1 = 0,
    y1 = 0,
    isRun = false,
    si,
    self = this;

 

   var sif = function(){
    si = setInterval(function(){
         if(x == x1 && y ==y1){
          clearInterval(si);
          isRun = false;
          callback && callback.call(self);
         }
         x = x1;
         y = y1;
        }, 500);
   }

   $(this).mousemove(function(e){
    x1 = e.pageX;
    y1 = e.pageY;
    !isRun && sif(), isRun = true;
   }).mouseout(function(){
    clearInterval(si);
    isRun = false;
   });
 });
 }
})(jQuery);