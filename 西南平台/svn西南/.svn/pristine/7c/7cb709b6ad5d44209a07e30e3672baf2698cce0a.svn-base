/**
 * Created by Administrator on 2017/10/10 0010.
 */
//获取时间
function getTime() {
    return $("#datepicker").val()||new Date().format('yyyy-MM-dd')
}
//获取时间段
function getRangeTime() {
    var time1 = $("#rangeDatePicker input[name='start']").val(),
        time2 = $("#rangeDatePicker input[name='end']").val();
    var startTime = util.getTimestamp(time1),
        endTime   = util.getTimestamp(time2);
    return {startTimestamp:startTime,endTimestamp:endTime,starTime:time1,endTime:time2}
}
//获取数据类型
function getdataType() {
    var type = $("#daytype .active").attr('type');
    if (type=="DAY"){
        return 1
    }
    return 0
}
//获取时间类型
function getTimeType() {
    return $("#time .active").attr('type')
}
//获取地区
function getArea() {
    var selectList = $(".city-select select"),
        area = {};
    for (var i=0;i<selectList.length;i++){
        var id = selectList.eq(i).parent().attr('id');
        area[id] = selectList.eq(i).val();
    }
    return area
}
//获取地区
function cityInfo() {
    var city   = $("#city .title").html(),
        county = $("#county .title").html();
    return {city:city,county:county}
}
//获取面板参数
function getParams() {
    var paramlist = ($(".control-sidebar .active")),
        obj = {};
    for (var i=0;i<paramlist.length;i++){
        var type = paramlist.eq(i).parent().attr('id');
        obj[type] =  paramlist.eq(i).attr('type')
    }
    return obj
}

//获取所有的气体
function getGas() {
    var gas = [];
    var gaslist = $(".gas .checked input");
    for (var i= 0; i<gaslist.length; i++){
        gas[i]=$(gaslist[i]).val()
    }
    return gas
}
//获取水质环境
function getWater() {
    var water = [];
    var waterList = $(".water .checked input");
    for (var i= 0; i<waterList.length; i++){
        water[i]=$(waterList[i]).val()
    }
    return water
}


//西南五省省市二级联动
function setArea(arr,province,container1,container2) {
    var str = '',
        childstr = '';
    if (province){
        for (var i=0;i<arr.length;i++){
            if (arr[i].province==province){
                str += "<option selected>"+arr[i].province+"</option>";
                for(var j =0 ; j<arr[i].city.length;j++){
                    childstr += "<option>"+arr[i].city[j]+"</option>"
                }
            }else {
                str += "<option>"+arr[i].province+"</option>";
            }
        }
    }
    $(container1).html(str);
    $(container2).html(childstr);
}


