/**
 * Created by Administrator on 2018/16/4 0010.
 */
//初始化数据
var day1 =new Date(new Date()-24*60*60*1000).format("yyyy-MM-dd");
var day2 =new Date(new Date()).format("yyyy-MM-dd");
$('#rangeDatePicker input[name="startime"]').val(day1);
//初始化日期
$('#rangeDatePicker').datepicker({
    language : 'zh-CN',  //日历显示为中文
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


var newHeaderarrthird= [
			{
				rows:[
					{
						value:'省份',
					},
					{
						value:'区域',
					}
				]
			}
		];
renderThead(newHeaderarrthird,day2,getparam(day1));

$("#rangeDatePicker").change(function () {
	 setTimeout(function () {
		    var thetime = $("#rangeDatePicker").find('#datestart').val();
			var _dates = new Date((new Date(thetime)).getTime()+24*60*60*1000).format("yyyy-MM-dd");
	        renderThead(newHeaderarrthird,_dates,getparam(new Date(new Date(thetime)).format("yyyy-MM-dd")));
	    },100);
});


function renderThead(arr,dates,param){
	$('.tabledata').html('');
	var _tabledataHtml='';
	var timearr=[];
	for(var i=0;i<10;i++){
		timearr.push({
			value:computetime(dates,i),
		});
	}
	arr[0].rows.length=3;
	arr[0].rows = arr[0].rows.concat(timearr);
		arr.forEach(function(v){
			_tabledataHtml='<thead><tr>'
			    v.rows.forEach(function(value){
			    	_tabledataHtml+='<td>'+value.value+'</td>'
			    });
			_tabledataHtml+='</tr></thead>';
		});	
	$('.tabledata').append(_tabledataHtml);
	gettableData(param)
}
$('.search').on('click',function(){
	 setTimeout(function () {
		    var thetime = $("#rangeDatePicker").find('#datestart').val();
			var _dates = new Date((new Date(thetime)).getTime()+24*60*60*1000).format("yyyy-MM-dd");
	        renderThead(newHeaderarrthird,_dates,getparam(new Date(new Date(thetime)).format("yyyy-MM-dd")));
	    },100);
})
function gettableData(param){
	var _tbodyhtml = '<tbody id="tbodytable">';
	var creatName = '';
	urlconfig.getcolortableData(param,function(data){
		data.Result.forEach(function(v,i){
			       v.provinceZones.forEach(function(value){
			       	 _tbodyhtml+='<tr><td>'+v.provinceName+'</td><td >'+value.zoneName+'</td>';
			       	  if(value.airQualityLvlForecastStatisticsList.length==0){
			       	  	 value.airQualityLvlForecastStatisticsList.length=10;
			       	  	 for(var j=0;j<10;j++){
			       	  	 	value.airQualityLvlForecastStatisticsList[j] = {
			       	  	 		"levelCode":"0",
			       	  	 		"levelName":'-',
			       	  	 		"creatName":''
			       	  	 	}
			       	  	 }
			       	  }
                      if(value.airQualityLvlForecastStatisticsList.length<10){
			       	  	 value.airQualityLvlForecastStatisticsList.length=10;
			       	  	 for(var j=0;j<10;j++){
			       	  	 	if(value.airQualityLvlForecastStatisticsList[j]==undefined){
				       	  	 		value.airQualityLvlForecastStatisticsList[j] = {
				       	  	 		"levelCode":"0",
				       	  	 		"levelName":'-',
			       	  	 			"creatName":''
				       	  	 	}
			       	  	 	}	       	  	 	
			       	  	 }
			       	  }
			       	  value.airQualityLvlForecastStatisticsList.forEach(function(_value){
				       	  	_value['type']=_value['levelCode'];
				       	  
				       	  	var _type='';
				       	  	switch (_value.levelCode){
							case "0":
							     _type="0";
								break;
							case "1":
							     _type="1";
								break;
							case "2":
							     _type="2";
								break;
							case "3":
							      _type="2";
								break;
							case "4":
							     _type="3";
								break;
							case "5":
							      _type="3";
								break;
							case "6":
							     _type="4";
								break;
							case "7":
							     _type="4";
								break;
							case "8":
							     _type="5";
								break;
							case "9":
							     _type="5";
								break;
							case "10":
							     _type="6";
								break;
							case "11":
							     _type="6";
								break;
							default:
								break;
						}
				       	  	var _levelColor = util.levelColor(1,_type);
				       	  	
					   	    var _background = _levelColor.color;
				       	  	_tbodyhtml+='<td style="background:'+_background+'">'+(_value.levelName=="请选择" ? " " : _value.levelName)+'</td>';
				       	  });
				       	  
				      creatName = value.airQualityLvlForecastStatisticsList[0].creatName;
			       	 _tbodyhtml+='</tr>';
			       	 $('.writename').html(creatName);
			       })

		})
		_tbodyhtml +='<tbody>';
		
		 $('.tabledata').find('thead').after(_tbodyhtml);
		 MergeCell('tbodytable',0,0,0,2);
		 $('.tabledata tr').each(function(i,v){
		   	$(v).children('td').each(function(_i,_v){
		   		if($(_v).prop('style')['display']=='none'){
		   			$(_v).remove();
		   		}
		   	})	   
		   })
	})
}
function computetime(arg,day){
        var date1 = new Date(arg);
        var date2 = new Date(date1);
        date2.setDate(date1.getDate()+day);
        var times = date2.getFullYear()+"/"+(date2.getMonth()+1)+"/"+date2.getDate();
       return times;
}
function getparam(dates){
	var time = "creatDate="+dates+" " +"00:00:00";
	return time
}
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
 function export_list(){
 	$('#datatable').tableExport({type:'excel',escape:'false',tableName:'export'});
}