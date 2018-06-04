/**
 * Created by Administrator on 2017/9/13 0013.
 */
var util = {

    getCanlender:function (arr,dateType,timeHourType) {
        
        var str = "",
            datelength = arr.length,
            _class = "",
            childClass = "",
            width = ((10/datelength)*10)+"%";
        for (var i=0;i<datelength;i++){
            var  year = arr[i].year+"-"+arr[i].month+"-"+arr[i].day,
                type = arr[i].level;
            if(type==1){
                _class = "previous"
            }else if (type==2){
                _class = "afterwards"
            }else {
                _class = "nowadays active"
            }
            if (dateType=="hour"){
                var childstr = "<ul class='menu-grade'>",
                    hour = new Date().getHours();
                for (var j=1;j<=24;j++){
                    if (type==0 && j==timeHourType){
                        childClass = "activebg"
                    }else if (type==0 && j>hour){
                        childClass = "forbid"
                    }else {
                        childClass = ""
                    }
                    var  childdate = arr[i].year + "-"+arr[i].month+'-'+arr[i].day+' '+j+":00";
                    childstr += "<li class='"+childClass+"' id='"+childdate+"'>"+j+"h</li>"
                }
                childstr += "</ul>";
                str += "<li style='width: "+width+"' class='"+_class+"' id='"+year+"' ><span>"+arr[i].month+"-"+arr[i].day+"</span>"+childstr+"</li>"
            }else {
                str += "<li style='width: "+width+"' class='"+_class+"' id='"+year+"'><span>"+arr[i].month+"-"+arr[i].day+"</span></li>"
            }
        }
        return str
    },
    getInitArea:function () {
        try {
            var initProvince = remote_ip_info['province'],
                province     = "",
                needProvince = "四川省",
                city         = "成都市";
            if (initProvince == "西藏"){
                province     = "西藏自治区"
            }else if(initProvince=="重庆"){
                province     = "重庆市"
            }else {
                province     = initProvince+"省"
            }
            for (var i=0;i<area.length;i++){
                if(area[i].province==province){
                    needProvince = area[i].province;
                    city         = area[i].city[0]
                }
            }
            return {province:needProvince,city:city}
        }catch (e){
            throw Error(e)
        }
    },
    getinitDate:function () {
        var day = new Date().getDate(),
            month = new Date().getMonth()+1,
            year = new Date().getFullYear(),
            date = "";
        if (month==1 && day==1){
            month = 12
        }
        if (day==1){
            month-=1;
            var d = new Date(year,month , 0);
            day = d.getDate();
        }
        date = year+"-"+month+"-"+day;
        return date
    },
    loading:function (flag) {
        if (flag){
            if(!document.getElementById('loading')){
                var loading = "<div id='loading'>"+
                    "<div class='sk-fading-circle'>"+
                    "<div class='sk-circle1 sk-circle'></div>"+
                    "<div class='sk-circle2 sk-circle'></div>"+
                    "<div class='sk-circle3 sk-circle'></div>"+
                    "<div class='sk-circle4 sk-circle'></div>"+
                    "<div class='sk-circle5 sk-circle'></div>"+
                    "<div class='sk-circle6 sk-circle'></div>"+
                    "<div class='sk-circle7 sk-circle'></div>"+
                    "<div class='sk-circle8 sk-circle'></div>"+
                    "<div class='sk-circle9 sk-circle'></div>"+
                    "<div class='sk-circle10 sk-circle'></div>"+
                    "<div class='sk-circle11 sk-circle'></div>"+
                    "<div class='sk-circle12 sk-circle'></div>"+
                    "</div>"+
                    "</div>";
                $(document.body).append(loading);
            }else {
                $("#loading").fadeIn()
            }
        }else {
            $("#loading").fadeOut()
        }
    },
    compareDate:function (date1,date2) {
        if((new Date(date1.replace(/-/g,"\/"))) > (new Date(date2.replace(/-/g,"\/")))){
            return 1
        }else if((new Date(date1.replace(/-/g,"\/"))) < (new Date(date2.replace(/-/g,"\/")))){
            return 2
        }else {
            return 0
        }
    },
    message:function (title,message,time) {
        if (!time){
            time = 2400
        }
        if (!document.getElementById("message")){
            var msg = "<div id='message'><div class='modal'>"+
                           "<div class='modal-body'>"+message+"</div>"+
                      "</div></div>";
            $(document.body).append(msg);
            setTimeout(function () {
                $("#message").fadeOut()
            },time)
        }else {
            $("#message").fadeIn();
            setTimeout(function () {
                $("#message").fadeOut()
            },time)
        }
    },
    getTimestamp:function (date) {
        if (!date){
            return
        }
        return (new Date(date.replace(/-/g,'/'))).getTime();
    },
    getTimeDifference:function(date1,date2){
        var t1 = new Date(date1.replace(/-/g, "/"));
        var t2 = new Date(date2.replace(/-/g, "/"));
        return ((t2-t1)/1000/60/60/24)+1
    },
    getlevel:function (obj,majorpollutants,Pollutionconcentration) {
        var str ="";
        $("#polluteParam").html(" ");
        for(var i=0;i<obj.length;i++){
            if (obj[i].gas!="AQI"){
                    var width = ((obj[i].grade/6)*100)+"%";
                    var id = "level"+i,
                        value = "-";
                    var _class = "";
                    if(i%3==2){
                        _class = "center"
                    }
                    if (obj[i].value){
                        value = obj[i].value
                    }
                    str = "<div class='"+_class+"'>"+
                              " <div class='grade-param-level'>"+
                                   " <span>"+obj[i].gas+"<small class='subscript'>"+obj[i].last+"</small></span><span>"+value+"</span>"+
                              " </div>"+
                              "<div class='grade-level'>"+
                                     "<div  id='"+id+"'  style='background: "+obj[i].color+"'></div>"+
                              "</div>"+
                          "</div>";
                    $("#polluteParam").append(str);
                    $("#"+id).animate({"width":width})
            }
        }
    	$('.majorpollutants').html(majorpollutants);
    	$('.Pollutionconcentration').html(Pollutionconcentration);
    },
    levelReturn:function(type,value){
        if(value=="—" || value=="-" || value==null || value==""){
            return {type:"0",gas:type,last:"",value:value};
        }else{
            value=parseFloat(value);
            switch(type){
                case "AQI":
                    if(value>=0 && value<=50){
                        return {type:"1",gas:"AQI",last:"",value:value};
                    }else if(value>50 && value<=100){
                        return {type:"2",gas:"AQI",last:"",value:value};
                    }else if(value>100 && value<=150){
                        return {type:"3",gas:"AQI",last:"",value:value};
                    }else if(value>150 && value<=200){
                        return {type:"4",gas:"AQI",last:"",value:value};
                    }else if(value>200 && value<=300){
                        return {type:"5",gas:"AQI",last:"",value:value};
                    }else if(value>300){
                        return {type:"6",gas:"AQI",last:"",value:value};
                    }
                    break;
                case "SO2":
                    if(value>=0 && value<=50){
                        return {type:"1",gas:"SO",last:"2",value:value};
                    }else if(value>50 && value<=150){
                        return {type:"2",gas:"SO",last:"2",value:value};
                    }else if(value>150 && value<=475){
                        return {type:"3",gas:"SO",last:"2",value:value};
                    }else if(value>475 && value<=800){
                        return {type:"4",gas:"SO",last:"2",value:value};
                    }else if(value>800 && value<=1600){
                        return {type:"5",gas:"SO",last:"2",value:value};
                    }else if(value>1600){
                        return {type:"6",gas:"SO",last:"2",value:value};
                    }
                    break;
                case "NO2":
                    if(value>=0 && value<=40){
                        return {type:"1",gas:"NO",last:"2",value:value};
                    }else if(value>40 && value<=80){
                        return {type:"2",gas:"NO",last:"2",value:value};
                    }else if(value>80 && value<=180){
                        return {type:"3",gas:"NO",last:"2",value:value};
                    }else if(value>180 && value<=280){
                        return {type:"4",gas:"NO",last:"2",value:value};
                    }else if(value>280 && value<=565){
                        return {type:"5",gas:"NO",last:"2",value:value};
                    }else if(value>565){
                        return {type:"6",gas:"NO",last:"2",value:value};
                    }
                    break;
                case "CO":
                    if(value>=0 && value<=2){
                        return {type:"1",gas:"CO",last:"",value:value};
                    }else if(value>2 && value<=4){
                        return {type:"2",gas:"CO",last:"",value:value};
                    }else if(value>4 && value<=14){
                        return {type:"3",gas:"CO",last:"",value:value};
                    }else if(value>14 && value<=24){
                        return {type:"4",gas:"CO",last:"",value:value};
                    }else if(value>24 && value<=36){
                        return {type:"5",gas:"CO",last:"",value:value};
                    }else if(value>36){
                        return {type:"6",gas:"CO",last:"",value:value};
                    }
                    break;
                case "O3":
                    if(value>=0 && value<=100){
                        return {type:"1",gas:"O",last:"3",value:value};
                    }else if(value>100 && value<=160){
                        return {type:"2",gas:"O",last:"3",value:value};
                    }else if(value>160 && value<=215){
                        return {type:"3",gas:"O",last:"3",value:value};
                    }else if(value>215 && value<=265){
                        return {type:"4",gas:"O",last:"3",value:value};
                    }else if(value>265 && value<=800){
                        return {type:"5",gas:"O",last:"3",value:value};
                    }else if(value>800){
                        return {type:"6",gas:"O",last:"3",value:value};
                    }
                    break;
                case "PM10":
                    if(value>=0 && value<=50){
                        return {type:"1",gas:"PM",last:"10",value:value};
                    }else if(value>50 && value<=150){
                        return {type:"2",gas:"PM",last:"10",value:value};
                    }else if(value>150 && value<=250){
                        return {type:"3",gas:"PM",last:"10",value:value};
                    }else if(value>250 && value<=350){
                        return {type:"4",gas:"PM",last:"10",value:value};
                    }else if(value>350 && value<=420){
                        return {type:"5",gas:"PM",last:"10",value:value};
                    }else if(value>420){
                        return {type:"6",gas:"PM",last:"10",value:value};
                    }
                    break;
                case "PM25":
                    if(value>=0 && value<=35){
                        return {type:"1",gas:"PM",last:"2.5",value:value};
                    }else if(value>35 && value<=75){
                        return {type:"2",gas:"PM",last:"2.5",value:value};
                    }else if(value>75 && value<=115){
                        return {type:"3",gas:"PM",last:"2.5",value:value};
                    }else if(value>115 && value<=150){
                        return {type:"4",gas:"PM",last:"2.5",value:value};
                    }else if(value>150 && value<=250){
                        return {type:"5",gas:"PM",last:"2.5",value:value};
                    }else if(value>250){
                        return {type:"6",gas:"PM",last:"2.5",value:value};
                    }
                    break;
            }
        }
    },
    levelColor:function(level,type){
    	if(level.hasOwnProperty('type')){
    		var _type = level.type;
    	}else{
    		var _type = type;
    	}
        switch(_type){
            case "0":
                return {"color":"#A5A2A2","grade":"0","state":"离线","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "1":
                return {"color":"#00e400","grade":"1","state":"优","gas":level.gas,"last":level.last,"value":level.value };
                break;
            case "2":
                return {"color":"#ffff00","grade":"2","state":"良","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "3":
                return {"color":"#ff7e00","grade":"3","state":"轻度污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "4":
                return {"color":"#ff0000","grade":"4","state":"中度污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "5":
                return {"color":"#99004c","grade":"5","state":"重度污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "6":
                return {"color":"#7e0023","grade":"6","state":"严重污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "7":
                return {"color":"#ff0000","grade":"4","state":"中度污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "8":
                return {"color":"#99004c","grade":"5","state":"重度污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "9":
               return {"color":"#99004c","grade":"5","state":"重度污染","gas":level.gas,"last":level.last,"value":level.value};
                 break;
            case "10":
                 return {"color":"#7e0023","grade":"6","state":"严重污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
            case "11":
                return {"color":"#7e0023","grade":"6","state":"严重污染","gas":level.gas,"last":level.last,"value":level.value};
                break;
                default:
                break;
        }
    },
    levelColorBackCity:function(level){
        switch(level){
            case "0":
                return "rgba(167,167,167,0.2)";
                break;
            case "1":
                return "rgba(111,197,71,0.2)";
                break;
            case "2":
                return "rgba(253,198,30,0.2)";
                break;
            case "3":
                return "rgba(255,126,0,0.2)";
                break;
            case "4":
                return "rgba(255,0,0,0.2)";
                break;
            case "5":
                return "rgba(153,0,76,0.2)";
                break;
            case "6":
                return "rgba(126,0,35,0.2)";
                break;
        }
    },
    getActiveType:function (time) {
        var date = (new Date().getTime())-this.getTimestamp(time);
        if(date<0){
           return 2
        }else {
            var avrage = ((date/1000)/360)/24;
            var now = new Date().getHours();
            var value = now-avrage;
            if(value<0){
                return 1
            }else {
                return 0
            }
        }
    },
    getWindDirection:function (wind) {
            if (wind == "N") {
                return -0
            } else if (wind == "S") {
                return -180
            } else if (wind == "E") {
                return -90
            } else if (wind== "W") {
                return -270
            } else if (wind== "NE") {
                return -45
            } else if (wind== "NNE") {
                return -22.5
            } else if (wind== "ENE") {
                return -67.5
            } else if (wind== "SE") {
                return -135
            } else if (wind== "ESE") {
                return -112.5
            } else if (wind== "SSE") {
                return -157.5
            } else if (wind== "SW") {
                return -225
            } else if (wind== "SSW") {
                return -202.5
            } else if (wind== "WSW") {
                return -247.5
            } else if (wind== "NW") {
                return -315
            } else if (wind== "WNW") {
                return -292.5
            } else if (wind== "NNW") {
                return -337.5
            }else {
               return 0
            }
    },
    getWindLevel:function (number) {
        switch (number){
            case  0:
                return {direction:"-",chart:"-"};
                break;
            case -0:
                return {direction:"N",chart:"北"};
                break;
            case -180:
                return {direction:"S",chart:"南"};
                break;
            case -90:
                return {direction:"E",chart:"东"};
                break;
            case -270:
                return {direction:"W",chart:"西"};
                break;
            case -45:
                return {direction:"NE",chart:"东北"};
                break;
            case -22.5:
                return {direction:"NNE",chart:"北东北"};
                break;
            case -67.5:
                return {direction:"ENE",chart:"东东北"};
                break;
            case -135:
                return {direction:"SE",chart:"东南"};
                break;
            case -112.5:
                return {direction:"ESE",chart:"东东南"};
                break;
            case -157.5:
                return {direction:"SSE",chart:"南东南"};
                break;
            case -225:
                return {direction:"SW",chart:"西南"};
                break;
            case -202.5:
                return {direction:"SSW",chart:"南西南"};
                break;
            case -247.5:
                return {direction:"WSW",chart:"西西南"};
                break;
            case -315:
                return {direction:"NW",chart:"西北"};
                break;
            case -292.5:
                return {direction:"WNW",chart:"西西北"};
                break;
            case -337.5:
                return {direction:"NNW",chart:"北西北"};
                break;
        }
    },
    getMaxMin:function (arr) {
        var obj = {};
        obj.max = (Math.max.apply(null, arr)+20).toFixed(0);
        obj.min = (Math.min.apply(null, arr)-10)>0?(Math.min.apply(null, arr)-10).toFixed(0):(Math.min.apply(null, arr)-4).toFixed(0);
        return obj
    },
    getPrediction:function (char) {
        var grade =[];
        if (char){
            if (char.indexOf("优")>=0){
                grade.push("great")
            }
            if(char.indexOf("良")>=0){
                grade.push("good")
            }
            if(char.indexOf("轻度")>=0){
                grade.push("mild")
            }
            if(char.indexOf("中度")>=0){
                grade.push("middle")
            }
            if(char.indexOf("重度")>=0){
                grade.push("heavy")
            }
            if(char.indexOf("严重")>=0){
                grade.push("serious")
            }
        }
       return grade
    },
    getArea:function (name) {
        switch (name){
            case '西南五省':
                return "southwest";
                break;
            case '四川省':
                return "sichuan";
                break;
            case '云南省':
                return "yunnan";
                break;
            case '贵州省':
                return "guizhou";
                break;
            case '重庆市':
                return "chongqing";
                break;
            case '西藏自治区':
                return "xizang";
                break;
            default :
                return "sichuan";
                break
        }
    },
    getGasTitle:function (gas) {
        switch (gas){
            case 'AQI':
                return "空气质量指数AQI";
                break;
            case 'PM2.5':
                return "PM<small class='subscript'>2.5</small>污染物";
                break;
            case 'PM10':
                return "PM<small class='subscript'>10</small>污染物";
                break;
            case 'O3':
                return "O<small class='subscript'>3</small>污染物";
                break;
            case 'NO2':
                return "NO<small class='subscript'>2</small>污染物";
                break;
            case 'SO2':
                return "SO<small class='subscript'>2</small>污染物";
                break;
            case 'CO':
                return "CO污染物";
                break;
            case 'avgRH':
                return "相对湿度";
                break;
            case 'accPRECIP':
                return "降水量";
                break;
            case 'maxTEMP':
                return "最高气温";
                break;
            case 'minTEMP':
                return "最低气温";
                break;
            case 'maxPBL':
                return "混合层高";
                break;
            case 'avgCFRAC':
                return "云量";
                break;
            case 'winds':
                return "风场";
                break;
            case 'RH':
                return "相对湿度";
                break;
            case 'PRECIP':
                return "降水量";
                break;
            case 'TEMP2':
                return "气温";
                break;
            case 'PBL':
                return "混合层高";
                break;
            case 'CFRAC':
                return "云量";
                break;
            case 'WIND':
                return "风场";
                break;
        }
    },
    setMarker:function (arr,map,type,fn,timetype) {
        var layers = [];
        if (myGroup){
            myGroup.clearLayers();
        }

        console.log("圆点--->");
        console.log(arr);
        for(var i = 0;i< arr.length;i++){
            var lat        = parseFloat(arr[i].latitude),
                lon        = parseFloat(arr[i].longitude),
                value      =  arr[i][type],
                time       = new Date(arr[i].time1).format("yyyy-MM-dd hh:mm"),
                levelobj   = util.levelColor(util.levelReturn(type,value)),
                bgicon     = "",
                gas        = "",
                gaslevel   = "",
                levelstate = "";
            if (arr[i].quality){
                var picturearr = this.getPrediction(arr[i].quality),
                    pollutebg = "";
                for (var j=0 ;j<picturearr.length;j++){
                    pollutebg += picturearr[j];
                    if (j<picturearr.length-1){
                        pollutebg +="-"
                    }
                }
                bgicon      = "my-div-bg "+pollutebg;
                gas         = "AQI";
                gaslevel    = arr[i]["AQI"];
                levelstate  = arr[i]["quality"]
            }else {
                bgicon      = "my-div-icon level"+levelobj.grade;
                gas         = levelobj.gas;
                gaslevel    = levelobj.value;
                levelstate  = levelobj.state
            }
            var myIcon = L.divIcon({className: bgicon});
            //圆点点击弹框
            var str = "<div class='mark-container'>" +
              			  "<div><span>"+arr[i].cityName+"</span><span>"+time+"</span></div>"+
                         "<div><span>"+levelstate+"</span><span><span>"+gas+"<small class='subscript'>"+levelobj.last+"</small></span>:<span>"+gaslevel+"</span></span></div>"+
                      "</div>";

              
//             "<h5>城市详情</h5>"+
//          			 "<div><span>"+arr[i].cityName+"</span><span>"+time+"</span></div>"+
//                    	 "<div><span>"+levelstate+"</span><span><span>"+gas+"<small class='subscript'>"+levelobj.last+"</small></span>:<span>"+gaslevel+"</span></span></div>"+
//          			 "<p>主要污染物:<span class='majorpollutants'></span><p>"+
//          			 "<p>污染浓度:<span class='Pollutionconcentration'></span><p>"+
//          			 "<div class='pollute-param pollute-map' id='polluteParam'></div>"
              if(lat&&lon){
                   var layer =  L.marker([lat,lon],{icon: myIcon})
                       .bindPopup(str).openPopup();
                   layers.push(layer);
               }
        }
        myGroup = L.layerGroup(layers);
        map.addLayer(myGroup);
        /*地图点击事件*/
        layers.forEach(function(item ,i){
        	item.on('click',function(){
        		if(timetype==0){
        			fn&&fn({cityName: arr[i].cityName});
        		}
	        	
	        })
        })
    },
    concatArray:function (arr) {
       return Array.prototype.concat.apply([], arr)
    },
    getPrevday:function (year,month) {
        month = parseInt(month, 10);
        var d= new Date(year, month, 0);
        return d.getDate();
    }

};
