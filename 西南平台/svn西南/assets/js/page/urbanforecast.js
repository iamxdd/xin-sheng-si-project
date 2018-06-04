/**
 * Created by Administrator on 2018/16/4 0010.
 */
//初始化数据
var day1 =new Date(new Date()-24*60*60*1000).format("yyyy-MM-dd");
var day2 =new Date(new Date()).format("yyyy-MM-dd");
var search = {
    time:day1,
    day:7,
    gas:['aqi'],
    provinces:[24,25,26,27,28]
};
/*西南五省数据*/
var inputcheckData = [
	{
		"valName":"西南五省",
		"child":[
			{
				"value":25,
				"name":"四川省",
				"check":true
			},
			{
				"value":24,
				"name":"重庆市",
				"check":true
			},
			{
				"value":27,
				"name":"云南省",
				"check":true
			},
			{
				"value":26,
				"name":"贵州省",
				"check":true
			},
			{
				"value":28,
				"name":"西藏自治区",
				"check":true
			}
		]
	}
];
/*预报天数*/
var select_day = [
	{
		value:1,
		name:'1天'
	},
	{	value:2,
		name:'2天'
	},
	{	value:3,
		name:'3天'
	},
	{	value:4,
		name:'4天'
	},
	{	value:5,
		name:'5天'
	},
	{
		value:6,
		name:'6天'
	},
	{
		value:7,
		name:'7天'
	}
];
  var newarrs=[
		{
			value:'AQI',
			rowspan:0,
			colspan:0
		},
		{
			value:'污染等级',
			rowspan:0,
			colspan:0
		},
		{
			value:'首要污染物',
			rowspan:0,
			colspan:0
		},
		{
			value:'PM2.5',
			rowspan:0,
			colspan:0
		}
	];
$('#rangeDatePicker input[name="startime"]').val(day1);
//初始化日期
$('#rangeDatePicker').datepicker({
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

var newHeaderarr = [
			{
				rows:[
					{
						value:'序号',
						rowspan:0,
						colspan:0
					},
					{
						value:'省份',
						rowspan:0,
						colspan:0
					},
					{
						value:'分区',
						rowspan:0,
						colspan:0
					},
					{
						value:'城市',
						rowspan:0,
						colspan:0
					}
				]
			}
		];
var newHeaderarrthird= [
			{
				rows:[
					{
						value:'省份',
						rowspan:2,
						colspan:0
					},
					{
						value:'分区',
						rowspan:2,
						colspan:0
					},
					{
						value:'城市',
						rowspan:2,
						colspan:0
					}
				]
			}
		];
var AssessmentHeaderarr = [
					{
						value:'序号',
						rowspan:0,
						colspan:0
					},
					{
						value:'省份',
						rowspan:0,
						colspan:0
					},
					{
						value:'区域',
						rowspan:0,
						colspan:0
					},
					{
						value:'实况(过去一天)',
						rowspan:0,
						colspan:0
					},
					{
						value:'提前24小时对该天的预报',
						rowspan:0,
						colspan:0
					},
					{
						value:'提前48小时对该天的预报',
						rowspan:0,
						colspan:0
					},
					{
						value:'提前72小时对该天的预报',
						rowspan:0,
						colspan:0
					},
					{
						value:'提前24小时对该天的预报偏差',
						rowspan:0,
						colspan:0
					},
					{
						value:'提前48小时对该天的预报偏差',
						rowspan:0,
						colspan:0
					},
					{
						value:'提前72小时对该天的预报偏差',
						rowspan:0,
						colspan:0
					}
				
		];
		
$("#rangeDatePicker").change(function () {
	 setTimeout(function () {
	        var param = getAlldataparams();
	        gettablelist(param)
	    },100);
});

/*初始化表格*/
utiltable(newHeaderarr,'AQI',7,day2,0,0,4,search,3,'aqi');
/*渲染checkbox框*/
var renderBox = function(arr,id){
	$('.urbancheckbox').html('');
	var _urbancheckhtml = '';
	if(id==3){
		arr.length=2;
		arr[1] = {
			"valName":"参数选择",
			"child":[
				{
					"value":'aqi',
					"name":"AQI",
					"check":true
				},
				{
					"value":'quality',
					"name":"污染等级",
					"check":true
				},
				{
					"value":'primarypollutant',
					"name":"首要污染物",
					"check":true
				},
				{
					"value":'pm2_5',
					"name":"PM2.5(μg/m³)",
					"check":true
				}
			]
		};
	}else{
		arr.length=1;
	}
	arr.forEach(function(v){
		_urbancheckhtml += '<div class="spancity"><span>'+v.valName+'</span>';
		         v.child.forEach(function(value){
		         	_urbancheckhtml+='<label>'
                        +'<input type="checkbox"  class="minimal" checked='+value.check+' value='+value.value+'>'
                        +'<span>'+value.name+'</span>'
                    +'</label>';
		         });
		         _urbancheckhtml+='</div>';
	});
	$('.urbancheckbox').append(_urbancheckhtml);
}

/*渲染select_day框*/
var renderDay = function(arr){
	var _renderDayhtml = '';
	$('.select-day').html('');

		_renderDayhtml='<div>'
                    +'<div class="dropdown"><span class="spanword">预报天数:</span>'
                        +'<span class="caret"></span>'
                        +'<button type="button" class="btn dropdown-toggle active" id="areaselect" data-toggle="dropdown">'
                            +'<span class="title seven">7天</span>'
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
renderBox(inputcheckData,0);
renderDay(select_day);
//初始化单选按钮框
$('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
    radioClass   : 'iradio_minimal-blue',
    checkboxClass: 'icheckbox_minimal-blue'
});

//单模式下拉菜单
new Dropdown($(".dropdown"),{
    callback:function (e) {
      setTimeout(function () {
	        var param = getAlldataparams();
	        gettablelist(param)
	    },100);
    }
});
$('input[type="checkbox"].minimal, input[type="radio"].minimal').on('ifClicked', function(event){
    setTimeout(function () {
        var param = getAlldataparams();
        gettablelist(param)
    },100);
});
/*参数切换*/
$('.publicnav').on('click','li',function(e){
	
	e.stopPropagation();
	$('#rangeDatePicker input[name="startime"]').val(day1);
	$('.seven').html('7天')
	var _index = $(this).index();
	var _gas = $(this).attr('data-gas');
	$(this).siblings('li').removeClass('active');
	$(this).addClass('active');
	renderBox(inputcheckData,_index);
	var _day = +$('.seven').html().substring(0,$('.seven').html().indexOf('天'));
	var thetime = $("#rangeDatePicker").find('#datestart').val();
	var _dates = new Date((new Date(thetime)).getTime()+24*60*60*1000).format("yyyy-MM-dd");
	$('.select-day').show()
	if(_index==3){
		search['gas'] = _gas.split(',');
		newHeaderarrthird.length=2;
		newHeaderarrthird[1] = {};
		newHeaderarrthird[0].rows = newHeaderarrthird[0].rows.concat(getnewtablearr(_dates,_day,4));
		newHeaderarrthird[1]['rows'] = getnewtablethirdarr(_day,newarrs)
		utiltable(newHeaderarrthird,_gas,_day,_dates,2,0,3,search,2,_gas,true,4);
		$('.urbantimeshow').html('');
		
	}else if(_index==4){
		$('.select-day').hide(); 
		var parm = {
			 time:_dates,
			 provinces:[24,25,26,27,28]
		};

		pingguform(AssessmentHeaderarr,parm,_dates)
	}else{
		search['gas'][0] = _gas;
		utiltable(newHeaderarr,_gas,_day,_dates,0,0,4,search,3,_gas)
	}
	
  $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
		    radioClass   : 'iradio_minimal-blue',
		    checkboxClass: 'icheckbox_minimal-blue'
	});
  $('input[type="checkbox"].minimal, input[type="radio"].minimal').on('ifClicked', function(event){
    setTimeout(function () {
        var param = getAlldataparams();
        gettablelist(param);
        if(_index!==4){
        	 changethirdrow()
        }
       
    },100);
  });

});
/**
 * Created by Administrator on 2018/4/17 0013.
 */
function utiltable(Headerarr,aqi,day,dates,rowspan,colspan,length,searchparm,num,gas,falg,childlength){	
	$('.tablelist').html('');
	var Headerhtml = '';
	var timearr = [];
	var newarrHeader=[];
	if(falg!==undefined){
		timearr=getnewtablearr(dates,day,childlength);
		$('.urbantimeshow').html('');
		$('.urbantitle').css('display','none');
	}else{
		$('.urbantitle').css('display','block');
		$('.urbantimeshow').html(dates+'四川省环境监测总站');
			var tableName=''
			if(aqi=="aqi" ||aqi=="AQI" ){
				tableName="AQI";
			}else if(aqi=="pm2_5"){
				tableName="PM_2.5";
			}else if(aqi=="O3"){
				tableName="O3";
			}
		$('.urbantitle').html('西南区域城市'+tableName+'预报情况统计表');
		for(var i=0;i<day;i++){
		timearr.push({
			value:computetime(dates,i),
			rowspan:rowspan,
			colspan:colspan
		});
	 }
	}
	
	Headerarr[0].rows.length=length;
	Headerarr[0].rows = Headerarr[0].rows.concat(timearr);
	newarrHeader.push(Headerarr[0].rows.concat(timearr));
	Headerhtml='<thead>' 
		Headerarr.forEach(function(v){
			Headerhtml+='<tr class="trhead">'
			    v.rows.forEach(function(value){
			    	if(value.rowspan==0){
			    		Headerhtml+='<td colspan='+value.colspan+' >'+value.value+'</td>'
			    	}else{
			    		Headerhtml+='<td rowspan='+value.rowspan+'>'+value.value+'</td>'
			    	}
			    	
			    });
			Headerhtml+='</tr>';
		});
	Headerhtml+='</thead>';
	
	$('.tablelist').append(Headerhtml);
	gettableData(searchparm,num,gas);
}


function computetime(arg,day){
        var date1 = new Date(arg);
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+day);
        var times = date2.getFullYear()+"/"+(date2.getMonth()+1)+"/"+date2.getDate();
       return times;
}

function getnewtablearr(arg,day,colspan){
	var newarr=[];
	for(var i=0;i<day;i++){
		newarr.push({
			value:computetime(arg,i),
			rowspan:0,
			colspan:colspan
		})
	}
	return newarr;
}

function getnewtablethirdarr(num,arr){
   var result = [];
 
   for(var i=0;i<num;i++){
   	 for(var j=0;j<arr.length;j++){
   	 	result.push(arr[j]);
   	 }
   }
	return result;
}


/*渲染表格tbody部分*/
function gettableData(searchparm,num,gas){
  var _tbodyhtml = '<tbody id="tbodytable">';
	//获取站点
	urlconfig.getpublictableData(searchparm,function (data) { 
		   var _index = $(".publicnav >.active").index();
		   var col = +$('.seven').html().substring(0,$('.seven').html().indexOf('天'));
		   if(_index!==3){
		   	var type = $(".publicnav >.active").attr('data-type');
		   	 if(data.data.length>0){
		   	 	data.data.forEach(function(v,i){
				   	_tbodyhtml+='<tr><td>'+(i+1)+'</td>';
				   	    _tbodyhtml+='<td >'+v.provincename+'</td>';
				   	    _tbodyhtml+='<td >'+v.areaname+'</td>';
				   	    _tbodyhtml+='<td >'+v.cityname+'</td>';
				   	    v[gas].forEach(function(value,index){
				   	    	var _value ='';
				   	    	if(value!=='-'){
				   	    		_value= value.split('~')[1];
				   	    	}
				   	    	var _levelReturn = util.levelReturn(type,_value);
				   	    	var _levelColor = util.levelColor(_levelReturn);
				   	    	var _background = _levelColor.color;
				   	  	  _tbodyhtml+='<td style="background:'+_background+'">'+value+'</td>'
				   	   });
				     	_tbodyhtml+='</tr>';   
		           });
				   	_tbodyhtml += '</tbody>';
				   	$('.tablelist').css({"width":"1700px"})
		   	  }else{
		   	  	  _tbodyhtml+='<tr><td colspan='+(col*4+3)+'>暂无数据</td></tbody>';
		   	  }
		   }else if(_index==3){
		   	$('.tablelist').find('thead').css({
		   		'background':'#009AD8',
		   		'color':'#fff'
		   		});
		   	var   gaslistthird = $(".spancity").eq(1).find('.checked input');

		   	var day = $("#areaselect span").text();
			    day = day.substring(0, day.length-1);
                day = parseInt(day);

		   	  if(data.data.length>0){
		   	 	data.data.forEach(function(v,i){
				   	_tbodyhtml+='<tr>';
				   	    _tbodyhtml+='<td >'+v.provincename+'</td>';
				   	    _tbodyhtml+='<td >'+v.areaname+'</td>';
				   	    _tbodyhtml+='<td >'+v.cityname+'</td>';
				   	    
				   	    for (var j = 0; j < day; j++) {
                            if (v.aqi) {
                                _tbodyhtml+='<td>'+v.aqi[j]+'</td>';
							}
                            if (v.quality) {
                            	if((v.quality[j]).indexOf("轻度污染")>=0){
                            		v.quality[j] = v.quality[j].replace('轻度污染','轻');
                            	}
                            	if((v.quality[j]).indexOf("重度污染")>=0){
                            		v.quality[j] = v.quality[j].replace('重度污染','重');
                            	}
                            	if((v.quality[j]).indexOf("中度污染")>=0){
                            		v.quality[j] = v.quality[j].replace('中度污染','中');
                            	}
                            	if((v.quality[j]).indexOf("严重污染")>=0){
                            		v.quality[j] = v.quality[j].replace('严重污染','重');
                            	}
                            	 var levelCodenew='';
								switch (v.quality[j]){
									case "-":
								      levelCodenew="0";
									 break;
								   case "优":
								      levelCodenew="1";
									break;
								case "优或良":
								     levelCodenew= "2" ;
									break;
								case "良":
								     levelCodenew="2";
									break;
								case "良或轻":
								      levelCodenew="3";
									break;
								case "轻":
								     levelCodenew="3";
									break;
								case "轻或中":
								      levelCodenew="4";
									break;
								case "中":
								      levelCodenew="4";
									break;
								case "中或重":
								     levelCodenew="5";
									break;
								case "重":
								     levelCodenew="5";
									break;
								case "重或严重":
								     levelCodenew="6";
									break;
								case "严重":
								 	levelCodenew="6";
									break;
								default:
										break;
								}
                            	 var _levelColor = util.levelColor(1, levelCodenew);
					             var _background = _levelColor.color;
					          
                                _tbodyhtml+='<td style="background-color:'+_background+'">'+v.quality[j]+'</td>';
							}
							if (v.primarypollutant) {
								switch (v.primarypollutant[j]){
									case "细颗粒物(PM2.5)":
									    v.primarypollutant[j]="PM2.5";
										break;
									case "臭氧":
									    v.primarypollutant[j]="O3";
										break;
									case "颗粒物(PM10)":
									    v.primarypollutant[j]="PM10";
										break;
									default:
										break;
								}
                                _tbodyhtml+='<td>'+v.primarypollutant[j]+'</td>';
                            }
                            if (v.pm2_5) {
                                _tbodyhtml+='<td>'+v.pm2_5[j]+'</td>';
                            }
						}
				     	_tbodyhtml+='</tr>';   
		           });
				   	_tbodyhtml += '</tbody>';
				   	 if(day>=4 && gaslistthird.length>3){
		   				$('.tablelist').css({
		   					"width":"2441px"
		   					})
		   			}else{
		   				$('.tablelist').css({
		   					"width":"99%"
		   					})
		   			}
		   	  }else{
		   	  	  _tbodyhtml+='<tr><td colspan='+(col*4+3)+'>暂无数据</td></tbody>';
		   	  }
		   }
		  
		   $('.tablelist').find('thead').after(_tbodyhtml);
		   if(_index!=3 && searchparm.provinces.length==1){
		   	MergeCell('tbodytable', 0, 0, 2,3)
		   	}else if(_index==3 && searchparm.provinces.length==1){
		   	MergeCell('tbodytable', 0, 0, 1,2)
		   }
		   MergeCell('tbodytable', 0, 0, 0,num);
		   
		   $('.tablelist tr').each(function(i,v){
		   	$(v).children('td').each(function(_i,_v){
		   		if($(_v).prop('style')['display']=='none'){
		   			$(_v).remove();
		   		}
		   	})
		   
		   })
		  
	});
}
    
function getAlldataparams(){
	var params = {},
	    gas = [],
	    provinces=[],
        provinceslist = $(".spancity").eq(0).find('.checked input'),
        dates = $('#datestart').val(),
        time = new Date((new Date(dates)).getTime()+24*60*60*1000).format("yyyy-MM-dd"),
        day = +$('.seven').html().substring(0,$('.seven').html().indexOf('天'));
        if($(".publicnav >.active").index()!==3){
        	gaslist = $(".publicnav >.active").attr('data-gas');
        	gas = [gaslist];
        }else{
        	var   gaslistthird = $(".spancity").eq(1).find('.checked input');
        	for(var i= 0; i<gaslistthird.length; i++){
	        	gas[i]=$(gaslistthird[i]).val()
	        }
        }
	    for(var i= 0; i<provinceslist.length; i++){
	        provinces[i]=$(provinceslist[i]).val()
	    }
	    if(provinces.length==0){
	    	provinces[0]=0
	    }
	    if(gas.length==0){
	    	gas[0]=0
	    }
	   if($(".publicnav >.active").index()==4){
	   		params = {
	    	time:time,
	    	provinces:getnewpro(provinces)
	    };
	   	
	   }else{
	   	 params = {
	    	time:time,
	    	day:day,
	    	gas:gas,
	    	provinces:getnewpro(provinces)
	    };
	   }
	   
	    return params;

}
function gettablelist(searchparm){
	var _index = $(".publicnav >.active").index();
	var _gas = $(".publicnav >.active").attr('data-gas');
	var _day = +$('.seven').html().substring(0,$('.seven').html().indexOf('天'));
	var thetime = $("#rangeDatePicker").find('#datestart').val();
	var _dates = new Date((new Date(thetime)).getTime()+24*60*60*1000).format("yyyy-MM-dd");
	if(_index==3){
		var   length = $(".spancity").eq(1).find('.checked input').parent('div').siblings('span').length;
		newHeaderarrthird.length=2;
		newHeaderarrthird[1] = {};
		var arrnew = changethirdrow();
		newHeaderarrthird[0].rows = newHeaderarrthird[0].rows.concat(getnewtablearr(_dates,_day,arrnew.length));
		newHeaderarrthird[1]['rows'] = getnewtablethirdarr(_day,arrnew)
		utiltable(newHeaderarrthird,searchparm.gas.join(','),_day,_dates,2,0,3,searchparm,2,_gas,true,length)	
	}else if(_index==4){
		$('.select-day').hide(); 
		pingguform(AssessmentHeaderarr,searchparm,_dates);
	}else{
		utiltable(newHeaderarr,_gas,_day,_dates,0,0,4,searchparm,3,_gas)
	}
}

function changethirdrow(){
	var result = [];
	var   gaslistthird = $(".spancity").eq(1).find('.checked input').parent('div').siblings('span');
        	for(var i= 0; i<gaslistthird.length; i++){
	        	result.push({
	        		value:$(gaslistthird).eq(i).html(),
	        		rowspan:0,
			        colspan:0
	        	});
	        }
      return result;
}
function pingguform(arr,parm,dates){
	$('.urbantitle').css('display','block')
	$('.urbantitle').html('西南区域城市空气质量预报结果每日评估表');
	$('.urbantimeshow').html(dates+'四川省环境监测总站');
	$('.tablelist').html('');
	var theadheader = '';
	     theadheader='<thead><tr>';
			    arr.forEach(function(value){
			    	theadheader+='<td  rowspan='+value.rowspan+' colspan='+value.colspan+' >'+value.value+'</td>'
			    });
			theadheader+='</tr></thead>';	
	$('.tablelist').html(theadheader);
	getfourtablebody(parm);
}
function getfourtablebody(parm){
	var _length = 0;
	//获取站点
	urlconfig.AssessmenttableData(parm,function (data){ 
		var _tbodyhtml = '<tbody id="tbodytable">';
			 if(data.data.hasOwnProperty('cityData')){
			 		 if(data.data.cityData.length>0){
			 		 	_length = data.data.cityData.length;
				   	 	data.data.cityData.forEach(function(v,i){
				   	 		var _le_realColor = (util.levelColor(v,v['le_real'].toString())).color;
				       	  	var _le_24Color = (util.levelColor(v,v['le_24'].toString())).color;
				       	  	var _le_48Color = (util.levelColor(v,v['le_48'].toString())).color;
				       	  	var _le_72Color = (util.levelColor(v,v['le_72'].toString())).color;
						   	_tbodyhtml+='<tr><td>'+(i+1)+'</td>';
						   	    _tbodyhtml+='<td>'+v.provincename+'</td>'
						   	   +'<td>'+v.areaname+'</td>'
						   	   +'<td style="background:'+_le_realColor+'">'+v.quality_real+'</td>'
						   	   +'<td style="background:'+_le_24Color+'">'+(v.quality_24=="-或-" ? "-" : v.quality_24)+'</td>'
						   	   +'<td style="background:'+_le_48Color+'">'+(v.quality_48=="-或-" ? "-": v.quality_48)+'</td>'
						   	   +'<td style="background:'+_le_72Color+'">'+(v.quality_72=="-或-" ? "-": v.quality_72)+'</td>'
						   	   +'<td>'+v.quality_24r+'</td>'
						   	   +'<td>'+v.quality_48r+'</td>'
						   	   +'<td>'+v.quality_72r+'</td></tr>';	      	   
				           });
				           _tbodyhtml+='</tbody>';
				           var percentage_24 = data.data.percentage_24.toFixed(2)+'%',
				          	   percentage_48 = data.data.percentage_48.toFixed(2)+'%',
				           	   percentage_72 = data.data.percentage_72.toFixed(2)+'%';
				           	   
				   	  }else{
				   	  	  _tbodyhtml+='<tr><td colspan="10">暂无数据</td></tr></tbody>';
				   	  	 
				   	  }
				   	   $('.tablelist').css({"width":"1700px"})
			 }else{
			 	 _tbodyhtml+='<tr><td colspan="10">暂无数据</td></tr></tbody>';
			 	 $('.tablelist').css({"width":"1700px"})
			 }
		   
		   	   $('.tablelist').find('thead').after(_tbodyhtml);
		   	   	MergeCell('tbodytable', 0, 0, 0,3);
		   	   	var len=$("#tbodytable tr").length;
		   	   	if(len>1){
		   	   		$("#tbodytable tr").eq(len-1).after('<tr><td colspan="7">当日统计准确率</td>'
				                     +'<td>'+percentage_24+'</td>'
				                     +'<td>'+percentage_48+'</td>'
				                     +'<td>'+percentage_72+'</td>'
				                     +'</tr>');
		   	   	}
		   	   	
	   	   
		   	   
	});
}
///合并表格相同行的内容  
    ///tableId：表格ID（最好是tbody，避免把表尾给合并了)  
    ///startRow：起始行，没有标题就从0开始  
    ///endRow：终止行，此参数是递归时检查的范围，一开始时会自动赋值为最后一行  
    ///col：当前处理的列  
function MergeCell(tableId, startRow, endRow, col,num) { 
        var tb = document.getElementById(tableId);  
        if (col >= tb.rows[0].cells.length) {  
            return;  
        }  
        //当检查第0列时检查所有行  
        if (col == 0 || endRow == 0) {  
            endRow = tb.rows.length - 1;  
        }  
        for (var i = startRow; i < endRow; i++) {  
            //程序是自左向右合并  
            if (tb.rows[startRow].cells[col].innerHTML == tb.rows[i + 1].cells[col].innerHTML && col<num) {  
                //如果相同则删除下一行的第0列单元格  
                tb.rows[i + 1].cells[col].style.display='none';  
                //更新rowSpan属性  
                
                tb.rows[startRow].cells[col].rowSpan = (tb.rows[startRow].cells[col].rowSpan | 0) + 1;  
                //当循环到终止行前一行并且起始行和终止行不相同时递归(因为上面的代码已经检查了i+1行，所以此处只到endRow-1)  
                if (i == endRow - 1 && startRow != endRow) {  
                    MergeCell(tableId, startRow, endRow, col + 1);  
                }  
            } else {  
                //起始行，终止行不变，检查下一列  
                MergeCell(tableId, startRow, i, col + 1,num);  
                //增加起始行  
                startRow = i + 1;  
            }  
        }  
   }  
   function newApi(format) {
                return ExcellentExport.convert({
                    anchor: 'anchorNewApi-' + format,
                    filename: 'data_123.' + format,
                    format: format
                }, [{
                    name: 'Sheet Name Here 1',
                    from: {
                        table: 'datatable'
                    }
                }]);
            }
// function export_list(){
//      $("#datatable").table2excel({
//          // exclude CSS class
//          exclude: ".noExl",
//          name: "Worksheet Name",
//          filename: "xxxxx表" ,//do not include extension
//          exclude_img: true,
//          exclude_links: true,
//          exclude_inputs: true
//      });
//  }
   function export_list(){
   	var _index = $(".publicnav >.active").index();
   	var param = getAlldataparams();
   	var _gas = '';
   	if(_index==3){
   		_gas = param.gas.join(',')
   	}else if(_index==4){
   		_gas = '空气质量';
   	}else{
   		_gas =param.gas[0];
   	}
   	var _tableName = '西南区域城市'+_gas +'预报情况登记表'
	   $('#datatable').tableExport(
	   	{
	   		type:'excel',
	   		escape:'false',
	   		tableName:_tableName,
	   		
	   	}
   );
}
function getnewpro(arr){
	var result=[];
	for(var i=0;i<arr.length;i++){
		if(result.indexOf(arr[i])==-1){
			result.push(arr[i])
		}
	}
	return result;
}
