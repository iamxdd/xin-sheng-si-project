/**
 * Created by Administrator on 2018/16/4 0010.
 */
//初始化数据
var day1 =new Date(new Date()-24*10*60*60*1000).format("yyyy-MM-dd");
var day2 =new Date(new Date()).format("yyyy-MM-dd");
/*预报天数*/
var select_day = [
	{
		value:99999,
		name:'西南区域'
	},
	{	value:510000,
		name:'四川省'
	}
];
var tableheaderdata = [
	{
		value:'区域'
	},
	{
		value:'预报员'
	},
	{
		value:'预报信息'
	},
	{
		value:'上报时间'
	},
	{
		value:'详情'
	}
];
var search ={
  "creatDateED": day2,
  "creatDateST": day1,
  "provinceCode": 99999
};
/*渲染select_day框*/
var renderDay = function(arr){
	var _renderDayhtml = '';
	$('.select-day').html('');
		_renderDayhtml='<div>'
                    +'<div class="dropdown"><span class="spanword">区域:</span>'
                        +'<span class="caret"></span>'
                        +'<button type="button" class="btn dropdown-toggle active" id="areaselect" data-toggle="dropdown">'
                            +'<span class="title seven">西南区域</span>'
                        +'</button>'
                        +'<ul class="dropdown-menu" role="menu" aria-labelledby="areaselect">';
                        arr.forEach(function(v){
                            _renderDayhtml+='<li type="day" data-type='+v.value+'>'+v.name+'</li>'
                         });
                        +'</ul>'
                   + '</div>'
                +'</div>';
	
	$('.select-day').append(_renderDayhtml);
}
renderDay(select_day);
//单模式下拉菜单
new Dropdown($(".dropdown"),{
    callback:function () {
      setTimeout(function () {
	        var param = getAlldataparams();
	        Inittable(tableheaderdata,JSON.stringify(param));
	    },100);
    }
});

$('#rangeDatePickerstart input[name="startime"]').val(day1);
//初始化开始日期
$('#rangeDatePickerstart').datepicker({
    language :  'zh-CN',  //日历显示为中文
    minView  : "month",
    format   : "yyyy-mm-dd",
    todayBtn : "linked",
    keyboardNavigation: false,
    autoclose: true,
    endDate: new Date(),
    beforeShowYear: function (date){
        if (date.getFullYear() == 2007) {
            return false;
        }
    }
});
$('#rangeDatePickerend input[name="endtime"]').val(day2);
//初始化结束日期
$('#rangeDatePickerend').datepicker({
    language :  'zh-CN',  //日历显示为中文
    minView  : "month",
    format   : "yyyy-mm-dd",
    todayBtn : "linked",
    keyboardNavigation: false,
    autoclose: true,
    endDate: new Date(),
    beforeShowYear: function (date){
        if (date.getFullYear() == 2007) {
            return false;
        }
    }
});
Inittable(tableheaderdata,JSON.stringify(search));
var datalist = [];
function Inittable(arr,parm){
	var header = {'Content-Type':'application/json;charset=UTF-8'};
	
	urlconfig.ForecasttableData(parm,function(data){ 
	$('.tablelist').html('');
		
	var _tableheader = '';
	_tableheader='<thead><tr>';
		arr.forEach(function(v){
			_tableheader+='<td>'+v.value+'</td>';
		})
	_tableheader+='</thead></tr>';
	$('.tablelist').append(_tableheader);
	var _tbodyhtml = '<tbody id="tbodytable"></tbody>';
	var trhtml='';
		if(data.Result.length==0){
			trhtml+='<tr><td colspan="5">暂无数据</td></tr>';
		}else{
			
			datalist=data.Result;
			data.Result.forEach(function(_value,index){
				delete _value['detailSceneryImagePath'];
				delete _value['sceneryImagesPath'];
				_value.creatDate = new Date(new Date(_value.creatDate)).format("yyyy-MM-dd");
				trhtml+='<tr class="tbody_tr">'
							+'<td>'+(_value.provinceCode===510000 ? "四川省" : "西南区域" )+'</td>'
							+'<td></td>'
							+'<td>'+(_value.forecastInfo!==null ? _value.forecastInfo : '')+'</td>'
							+'<td>'+_value.creatDate+'</td>'
							+'<td class="detailinfos"><button class="detaliinfo">详情</button></td>'
							+'</tr>';
				
			});
			
		}
		
		$('.tablelist').find('thead').after(_tbodyhtml);
		$('.tablelist').find('#tbodytable').append(trhtml);
		
		
	},header,".urbantable");
}
function getAlldataparams(){
	var parm = {},
	   _date1 = $('#datestart').val(),
	   _date2 = $('#dateend').val(),
	   _province = '';
	  $('.dropdown-menu>li').each(function(index,value){
	  	if($(value).html()==$('.seven').html()){
	  		_province = $(value).attr('data-type');
	  	}
	  });
	parm ={
		  "creatDateED": _date2,
		  "creatDateST": _date1,
		  "provinceCode": _province
	};
	return parm;	
}
$('.search').on('click',function(e){
	e.stopPropagation();
	 $('.tablelist').html('');
	var param = getAlldataparams();
	 Inittable(tableheaderdata,JSON.stringify(param));
})

function closelayer(){
	$(".fixed-div").hide();
}
function detailleftData(_value){
	$('.foreleft>table').html('');
	$('.forecontent').html('');
	var parm = _value;
	var _leftdata='<tr><td>区域</td><td>'+(parm.provinceCode==="510000" ? "四川省" : "西南区域" )+'</td></tr>'
				   +'<tr><td>预报员</td><td></td></tr>'
				   +'<tr><td>上报时间</td><td>'+new Date(new Date(parm.creatDate)).format("yyyy-MM-dd")+'</td></tr>';
	 
	$('.foreleft>table').html(_leftdata);
	$('.forecontent').html(parm.forecastInfo)
}
/*详情事件*/
$('table').on('click','.detaliinfo',function(e){
	e.stopPropagation();
	var _index = $(this).parent('td').parent('tr').index();
	var _parm = datalist[_index];
	$(".fixed-div-thour").toggle();
	detailleftData(_parm);
})
function export_list(){
 	$('#datatable').tableExport({type:'excel',escape:'false',tableName:'export'});
}
function nbsp2Space(str) {
 var arrEntities = {'nbsp' : ' '};
 return str.replace(/&(nbsp);/ig, function(all, t){return arrEntities[t]})
}