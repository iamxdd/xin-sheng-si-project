/**
 * Created by Administrator on 2018/16/4 0010.
 */
//初始化数据
var day1 =new Date(new Date()-24*10*60*60*1000).format("yyyy-MM-dd");
var day2 =new Date(new Date()).format("yyyy-MM-dd");
/*状态*/
var select_day = [
	{
		value:0,
		name:'启用'
	},
	{	value:1,
		name:'停用'
	}
];

$('#rangeDatePicker input[name="startime"]').val(day1);
//初始化开始日期
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

/*渲染select_day框*/
var renderDay = function(arr){
	var _renderDayhtml = '';
	$('.select-day').html('');
		_renderDayhtml='<div>'
                    +'<div class="dropdown"><span class="spanword">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</span>'
//                      +'<span class="caret"></span>'
                        +'<button type="button" class="btn dropdown-toggle active" id="areaselect" data-toggle="dropdown">'
                            +'<span class="title seven">启用</span>'
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
    	console.log($(".title ").text())
    }
});
var _selected = 0;

$('.datasearch').on('click','.searchsave',function(e){
	e.stopPropagation();
	$('.dropdown-menu>li').each(function(index,value){
	  	if($(value).html()==$(".title ").text()){
	  		_selected = $(value).attr('data-type');
	  	}
	  });
	var searchparm = {
		time:$('#rangeDatePicker input[name="startime"]').val(),
		selected:_selected
	};
})
