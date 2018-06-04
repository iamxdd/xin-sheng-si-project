/*** Created by Administrator on 2017/10/11 0011.*/
var initdata =  {
        date        :util.getinitDate(),
        controlText :['DAY','HOUR'],
        controlEle  :['.day-temp','.hour-temp'],
        brotherList :['gas','water'],
        static      :'.png',
        dynamic     :'.gif'
};
//初始化事件
$("#datepicker").val(new Date(new Date()-24*60*60*1000).format("yyyy-MM-dd"));
//日期选择器
$("#datepicker").datepicker({
        language     :  'zh-CN',  //日历显示为中文
        autoclose    : true,
        minView      : "month",
        format       : "yyyy-mm-dd",
        endDate      :initdata.date
});
gettimelist(1,14,initdata.date);
getImgurl();
getOptions();
function getarea() {
   var txt = $(".area-select .active").find(".title").html();
   if (txt){
       return txt
   }
    return $(".area-select .active").html()
}
//获取日均和小时
function  getDaydata () {
      return $("#daytype .active").attr("type")
}
//整合所有的参数
function  getAllparams () {
    var obj = getParams(),
        tab = obj.myTab,
        params = {},
        singleParams = getsingleparam(),
        arr = [];
        params.date  = getTime().replace(/-/g,'');
        dataType = [];
        if (tab=="multiple-model"){
            var dataObj = {};
            if (obj.gas){
                dataObj.type = 'gas'
            }
            dataObj.name = obj.gas || obj.water;
            dataType.push(dataObj);
        }else {
            dataType = singleParams;
        }
        for (var i=0 ;i<dataType.length;i++){
            var data = {},
                water;
            if(!dataType[i].type){
                  data.name = dataType[i].name;
                  if(dataType[i].name.indexOf("_")>=0){
                      water      = dataType[i].name.split("_");
                      data.name  = obj.daytype=="DAY"?water[0]:water[1];
                      data.title = obj.daytype=="DAY"?water[0]:water[1];
                  }
            }else {
                 data.title = dataType[i].name;
                 if (obj.picturetype=="static"){
                     data.name = obj.daytype=="DAY"?"GRID_ALL_"+dataType[i].name:dataType[i].name;
                 }else {
                     data.name = obj.daytype=="DAY"?"GRID_"+dataType[i].name:dataType[i].name;
                 }
            }
            data.container = dataType[i].container?"."+dataType[i].container:"#modelA";
            arr.push(data)
        }
        if (obj.picturetype=="static"){
             time    = obj.daytype=="DAY"?"_"+obj.time:"_"+(obj.time+"_"+obj.hour);
        }else {
             time    ="";
        }
      params.dataType = arr;
      params.pictureType = obj.picturetype || 'static';
      params.dateType = obj.daytype;
      params.time =time;
      params.area = getarea();
      params.pictureSuffix = initdata[obj.picturetype];
      return params
}
/***********************************************************************************************************************
 *                                                      点击事件
 **********************************************************************************************************************/
//日期改变事件
$("#datepicker").change(function () {
    gettimelist(1,14,$(this).val());
    getImgurl();
});
var myTab = {
    "multiple-model":"single-model",
    "single-model":"multiple-model"
};
var daytype = {
      "HOUR":{
          show:"hour-temp",
          hide:"day-temp",
          _class:"active",
          params:[
              {container:"#singleModle1",value:"AQI",parameter:"AQI"},
              {container:"#singleModle2",value:"PM<small>2.5</small>",parameter:"PM2.5"},
              {container:"#singleModle3",value:"相对湿度",parameter:"RH"},
              {container:"#singleModle4",value:"降水量",parameter:"PRECIP"}
          ]
      },
      "DAY":{
          show:"day-temp",
          hide:"hour-temp",
          _class:"forbid",
          params:[
              {container:"#singleModle1",value:"AQI",parameter:"AQI"},
              {container:"#singleModle2",value:"PM<small>2.5</small>",parameter:"PM2.5"},
              {container:"#singleModle3",value:"相对湿度",parameter:"avgRH"},
              {container:"#singleModle4",value:"降水量",parameter:"accPRECIP"}
          ]
      }
};
//滑动按钮统一处理事件
new Slideicon($(".slide-button"),{
    callback:function (data) {
        var category = data.father,
            current = data.child;
            if (category=="myTab") {
                $("." + current).fadeIn();
                $("." + myTab[current]).fadeOut();
                if(current=="multiple-model"){
                     $(".day-hour").removeClass('single')
                }else {
                    $(".day-hour").addClass('single')
                }
            }
            if (category =="daytype") {
                var $li = $("#hour li"),
                    initselect = daytype[current].params;
                for (var i=0 ;i<initselect.length; i++){
                    $(initselect[i].container).html(initselect[i].value);
                    $(initselect[i].container).attr('parameter',initselect[i].parameter)
                }
                $("." + daytype[current].show).show();
                $("." + daytype[current].hide).hide();
                if (daytype[current]._class=="active"){
                      $li.eq(0).addClass('active');
                      $li.removeClass('forbid');
                }else {
                      $li.removeClass('active');
                      $li.addClass('forbid');
                }
                getOptions()
            }
            getImgurl()
    }
});
//添加参数查询面板的点击事件
$(".primary-btn").on('click','li',function () {
    var _class = $(this).attr("class");
    if(_class == "forbid"){return}
    var id = $(this).parent().attr('id'),
        brotherList = initdata.brotherList;
    if (id=="areatype"){
        $("#areaselect").removeClass('active')
    }
    if (id=="gas"||id=="water"){
        for(var i=0 ;i<brotherList.length;i++){
            if(!(brotherList[i]==id)){
                $("#"+brotherList[i]+" li").removeClass('active')
            }
        }
    }
    $(this).prevAll().removeClass('active');
    $(this).nextAll().removeClass('active');
    $(this).addClass('active');
    getImgurl();
});
//单模式下拉菜单
new Dropdown($(".dropdown"),{
    callback:function () {
        getOptions();
        getImgurl();
    }
});
//

//获取单模式参数
function getsingleparam() {
    var arr = [],
        gas = mockdata.gas;
    var usedparams = $(".single-model .dropdown").find('button');
    for (var i=0;i<usedparams.length;i++){
        var obj = {};
        obj.name = $(usedparams[i]).attr('parameter');
        obj.container = $(usedparams[i]).attr('id');
        for (var j=0;j<gas.length;j++){
            if (obj.name==gas[j].title){
                obj.type = "gas"
            }
        }
        arr.push(obj)
    }
    return arr
}
//获取单模型下拉菜单可选择的参数
function getFutureparam() {
    var arr = [];
    var data = mockdata.gas.concat(mockdata.areaWeather);
    var dataType = getDaydata();
    var single = getsingleparam();
    for (var i=0;i<data.length;i++){
        if(data[i].type==undefined || data[i].type==dataType){
            arr.push(data[i]);
        }
    }
    return arr.remove(single)
}
//获取可选择选项
function getOptions() {
    var obj  = getFutureparam(),
        str  = "";
    for (var i=0;i<obj.length;i++){
        var name = obj[i].name;
        if(obj[i].first){
              name =  obj[i].first+"<small class='subscript'>"+obj[i].family+"</small>"
        }
        str += "<li type='"+obj[i].title+"' >"+name+"</li>"
    }
    $(".single-model .dropdown-menu").html(str)
}
//获取所有的条件为img赋值
function getImgurl() {
    var conditions = getAllparams();
    var area       = conditions.area,
        cityname   = "cityname";
    console.log("--->")
    console.log(area)
    if(area=="四川省"){
        cityname   = "";
        mapUrl =" http://221.237.189.35:3390/product/"
    }else {
        cityname = util.getArea(area)+"/";
        mapUrl ="http://www.scnewair.cn:3392/product/"
    }
    var params = conditions.dataType;
    if(params){
        for (var i=0;length=params.length,i<length;i++){
            var title = params[i].title || params[i].name;
            var url = mapUrl+conditions.pictureType+"/"+cityname+conditions.date+"/"+conditions.dateType+"_"+params[i].name+conditions.time+conditions.pictureSuffix;
            var img = "<img src='"+url+"' onerror='getNewUrl(this)'/>";
            var str = conditions.area+util.getGasTitle(title);
            $(params[i].container).prev().find('.multiple-title').html(str);
            $(params[i].container).html(img)
        }
    }else {
        throw Error("数据丢失")
    }
}
function getNewUrl(obj) {
    var defaulturl = '../../img/bg/nohave.jpg';
    $(obj).attr('src',defaulturl)
}
//切换预测的日期
function gettimelist(start,end,model) {
    console.log("时间---->")
    console.log(start)
    console.log(end)
    console.log(model)
    var datedata = getChartDate(start,end,model),
        str = '',
        _class = '';
    console.log(datedata)

    for (var i=0;i<datedata.length;i++){
        _class = i==0?"active":"";
        str +="<li type='"+i+"' class='"+_class+"'>"+datedata[i].month+"月"+datedata[i].day+"日"+"</li>";
    }
    $("#time").html(str)
}