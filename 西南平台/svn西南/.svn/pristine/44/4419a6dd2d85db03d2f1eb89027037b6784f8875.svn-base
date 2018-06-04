/**
 * Created by Administrator on 2017/10/30 0030.
 */
//数组去除选定元素
Array.prototype.remove = function (arr1) {
    var arr = this;
    for (var i=0 ;i<arr1.length;i++){
        for (var j=0;j<arr.length;j++){
            if(arr[j].title == arr1[i].name){
                this.splice(j,1)
            }
        }
    }
    return this
};

//下拉菜单
var Dropdown = function (elenemt,options) {
    this.element = elenemt;
    this.options = {
        callback:options.callback
    };
    this.init()
};
Dropdown.prototype.init = function () {
    var _this = this;
    this.element.on("click","li",function () {
        var txt = $(this).html(),
            type = $(this).attr('type');
        if ($(this).attr('type')=="area"){
            $("#areatype li").removeClass('active')
        }
        $(this).parent().prev().find('.title').html(txt);
        $(this).parent().prev().addClass('active');
        $(this).parent().prev().attr('parameter',type);
        _this.options.callback();
        
    })
};


//地区三级联动菜单
function Area(data,option) {
    this.data = data;
    this.option = {
        callback:option.callback,
        initProvince:option.initProvince,
        initCity:option.initCity,
        container:option.container
    };
    this.init()
}
Area.prototype.init = function () {
    var element = this.option.container;
    var _this = this;
    var selectProvince = this.option.initProvince,
        selectCity     = this.option.initCity,
        selectCounty   = "";
    element.on("click","li",function () {
        var id  = $(this).parent().prev().attr("id"),
            txt = $(this).html(),
            data = _this.data;
        if (id=="province"){
            selectProvince = txt;
            selectCity     = data[txt][0].CityName;
            selectCounty   = data[txt][0].station[0]
        }else if (id=="city"){
            selectCity = txt;
            var stationlist = data[selectProvince];
            for (var p=0;length = stationlist.length,p<length;p++){
                if (txt==stationlist[p].CityName){
                    selectCounty = stationlist[p].station[0]
                }
            }
        }else if (id=="county"){
            selectCounty = txt
        }
        _this.getAlldata(selectProvince,selectCity,selectCounty);
    });
    this.getAlldata(selectProvince,selectCity);

};
Area.prototype.getAlldata = function (selectProvince,selectCity,selectCounty) {
    var  province       = [],
        city           = [],
        county         = [],
        provinceStr    = "",
        cityStr        = "",
        countyStr      = "",
        cityCode       = "";
    if (this.data){
        for (key in this.data){
            province.push(key)
        }
    }
    var citylist = (this.data[selectProvince]);
    for (var i=0;length = citylist.length,i<length;i++){
        var cityObj = {};
        if (citylist[i].CityName==selectCity){
            county = citylist[i].station;
        }
        cityObj.name = citylist[i].CityName;
        cityObj.code = citylist[i].CityCode;
        city.push(cityObj)
    }
    for (var j=0;j<province.length;j++){
        if (province[j]!=selectProvince){
            provinceStr += "<li>"+province[j]+"</li>"
        }
    }
    for (var t=0;t<city.length;t++){
        if (city[t].name!=selectCity){
            cityStr += "<li type='"+city[t].code+"'>"+city[t].name+"</li>"
        }else {
            cityCode = city[t].code
        }
    }
    for (var q=0;q<county.length;q++){
        if (county[q]!=selectCounty){
            countyStr += "<li>"+county[q]+"</li>"
        }
    }
    selectCounty = selectCounty || county[0];
    var str = '<div class="dropdown">'+
                    '<button type="button" class="btn dropdown-toggle" id="province" data-toggle="dropdown" parameter="accPRECIP">'+
                    '<span class="title">'+selectProvince+'</span>'+
                    '<span class="caret"></span>'+
                    '</button>'+
                    '<ul class="dropdown-menu" role="menu" aria-labelledby="province">'+provinceStr+'</ul>'+
              '</div>'+
              '<div class="dropdown center">'+
                    '<button type="button" class="btn dropdown-toggle" id="city" data-toggle="dropdown" parameter="accPRECIP">'+
                    '<span class="title">'+selectCity+'</span>'+
                    '<span class="caret"></span>'+
                    '</button>'+
                    '<ul class="dropdown-menu" role="menu" aria-labelledby="city">'+cityStr+'</ul>'+
              '</div>'+
              '<div class="dropdown">'+
                    '<button type="button" class="btn dropdown-toggle" id="county" data-toggle="dropdown" parameter="accPRECIP">'+
                    '<span class="title">'+selectCounty+'</span>'+
                    '<span class="caret"></span>'+
                    '</button>'+
                    '<ul class="dropdown-menu" role="menu" aria-labelledby="county">'+countyStr+'</ul>'+
             '</div>';
    this.option.container.html(str);
    this.option.callback(selectCounty,cityCode)
};