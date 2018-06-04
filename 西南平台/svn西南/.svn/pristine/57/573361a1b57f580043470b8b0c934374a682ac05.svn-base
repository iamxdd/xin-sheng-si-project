var basePath = "../json/";
//var apiPath = "http://10.190.1.110:5100/";
//var apiPath="http://10.190.1.110:5000/";//杨环
//var apiPath_a = "http://10.190.1.110:5000/";
var apiPath="http://www.scnewair.cn:3392/";
var	apiPath_a="http://www.scnewair.cn:3392/";
var country = "http://106.37.208.228:8083/ForecastService.asmx/";
var menuurl = "http://127.0.0.1:8020/svn西南/";
var childmenuurl = "http://127.0.0.1:8020/svn西南/pages/province/";
window.pageConfig = {
	"token": '',
	"ajaxUrl": {

		"getAirReportDrow_do": apiPath + "airReport/drow_do/get.do", //获取业务填色
		"updateAirReportDrow": apiPath + "airReport/drow_do/update.do", //更新业务
		"updatePicD": apiPath + "airReport/report_do/updatePicD.do", //图片上传
		"updatePicS": apiPath + "airReport/report_do/updatePicS.do", //无辖区形势图上传
		"updatePicM": apiPath + "airReport/report_do/updatePicM.do", //无辖区形势图上传
		"sichuanGet": apiPath + "airReport/report_do/get.do", //获取四川数据
		"deletePic": apiPath + "airReport/report_do/deletePic.do", //删除图片
		"UploadProvinceData": country + "UploadProvinceData",
		"sendChina": apiPath + "airReport/report_do/sendChina.do",//转发国家
        "xinanupdate" :apiPath+"airReport/report_do/update.do",//西南区域数据上报
		"getExpertsAssess": apiPath_a + "core/getExpertsAssess", //表格
		"getForcastAudateCityDayData": apiPath_a + "core/getForcastAudateCityDayData", //堆叠图
		"sichuanUpdate": apiPath + "airReport/drow_do/updateChina.do", //更新四川数据
		getStation: `${apiPath}java/superset/get/getProvince`, // 获取站点
		scatter: `${apiPath}supersetDraw/get/getPointPic`, // 点状射线图
		getExpertPointPic: `${apiPath}supersetDraw/get/getExpertPointPic`, // 专家评估点状射线图
	},
	superset: {
		url: 'http://172.16.1.156:5000/superset/explore/?form_data=',
		end: '&standalone=true&height=200',
		endAqi: '&standalone=true&height=400',
		board: 'http://172.16.1.156:5000/dashboardmodelview/list'
	},
	menuControl:[
		{
			value:"首页",
			url:menuurl+"index.html",
			children:[],
			active:false,
			icon:"iconfont icon-shouye"

		},
		{
			value:"模式预报",
			url:"",
			children:[
				{
					value:"区域",
					url:childmenuurl+"area.html",
					active:false
				},
				{
					value:"城市",
					url:childmenuurl+"city.html",
					active:false
				},
				{
					value:"城市预报2",
					url:childmenuurl+"citycast.html",
					active:false
				},
				{
					value:"中长期分析",
					url:childmenuurl+"analysis.html",
					active:false
				}
			],
			active:false,
			icon:"iconfont icon-moshiyubao"
		},
		{
			value:"业务填报",
			url:"",
			children:[
				{
					value:"西南区域",
					url:childmenuurl+"southwestRegio.html",
					active:false
				},
				{
					value:"四川省",
					url:childmenuurl+"SichuanProvince.html",
					active:false
				}
			],
			active:false,
			icon:"iconfont icon-yewutianbao"
		},
		{
			value:"预报评估",
			url:"",
			children:[
				{
					value:"专家评估",
					url:childmenuurl+"Expertevaluation.html",
					active:false
				},
				{
					value:"评估汇总",
					url:childmenuurl+"Evaluationsummary.html",
					active:false
				}
			],
			active:false,
			icon:"iconfont icon-yubaopinggu"

		},
		{
			value:"公共信息",
			url:"",
			children:[
				{
					value:"城市预报结果",
					url:childmenuurl+"urbanforecast.html",
					active:false
				},
				{
					value:"区域预报结果",
					url:childmenuurl+"regionalforecast.html",
					active:false
				},
				{
					value:"刷色表",
					url:childmenuurl+"colortable.html",
					active:false
				}
			],
			active:false,
			icon:"iconfont icon-gonggongxinxi"

		},
		{
			value:"污染解析",
			url:childmenuurl+"polluteanalysis.html",
			children:[],
			active:false,
			icon:"iconfont icon-wuranlaiyuanfenxi"
		},
		{
			value:"数据共享",
			url:"",
			children:[
				{
					value:"数据接收设置",
					url:childmenuurl+"Datareceptionsettings.html",
					active:false
				},
				{
					value:"过程监控",
					url:childmenuurl+"Processmonitoring.html",
					active:false
				}
//				,
//				{
//					value:"接收记录",
//					url:childmenuurl+"receiverecord.html",
//					active:false
//				}
			],
			active:false,
			icon:"iconfont icon-shuju"

		}
		
	]
		

}

$(function () {
	

//	let boardUrl = window.pageConfig.superset.board
//	let board = $(`<li>
//    <a href=${boardUrl} target="view_window">
//        <i class="iconfont icon-shujutongji"></i>
//        <span>我的看板</span>
//    </a>
//</li>`)
//	$('.sidebar>ul').append(board)
	 var count = 0;
//	 window.localStorage.setItem('index',0);
//   window.localStorage.setItem('childnum',0)
	 var _count = Number(window.localStorage.getItem('index'));
	 var childnum = 0;
	 var _childnum = Number(window.localStorage.getItem('childnum'));

	window.pageConfig.menuControl.map(function(v,i){
		if(i==_count){
			v['active']=true;
		}else{
			v['active']=false;
		}
		
		if(v.children.length>0){
			window.pageConfig.menuControl[_count].children.map(function(value,index){
				if(index==_childnum){
					value['active'] = true;
				}else{
					value['active'] = false;
				}
			})
		}
	});
	console.log(_childnum)
	menushow(window.pageConfig.menuControl);
	$('.sidebar-menu>li').on('click',function(){
		var _index = $(this).index();	
		 window.localStorage.setItem('index',_index);
		 window.localStorage.setItem('childnum',0);
		 var _url = $(this).children('a').attr('href');
		 if(_url==""){
		 	event.returnValue=false;
		 }
		 $(this).addClass('active');
		 $(this).siblings('li').removeClass('active')
	  });
	
   $('.treeview-menu>li').on('click',function(e){
   	e.stopPropagation()
		var _index = $(this).index();	
		window.localStorage.setItem('childnum',_index)
		
	  });

	function menushow(arr){
		var _menu = '';
	arr.forEach(function(v){
		_menu+='<li class='+(v.active ? "active" : "") +'>'
		      +'<a href='+v.url+'>'
              +'<i class="'+v.icon+'"></i>' 
              +'<span>'+v.value+'</span>'
              +'<i class="fa fa-angle-left pull-right"></i>'
              +'</a>';
              if(v.children.length>0){
              	_menu+='<ul class="treeview-menu">';
              	v.children.forEach(function(value){
              		_menu+='<li class='+(value.active ? "active" : "") +'>'
							+'<a href='+value.url+'>'
							  +value.value
							+'</a>'
						+'</li>'
					});
              	_menu+='</ul>';
              	
              }
              _menu+='</li>'	
	});
	 $('.sidebar>.sidebar-menu').append(_menu)
	}
})