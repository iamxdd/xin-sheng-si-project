(function() {
	$('.header').lavalamp({
		easing: 'easeOutBack',
		setOnClick: true
	});

	$("#qualitySecondMenu li").click(function() {
		$("#qualitySecondMenu li").removeClass('checkActive');
		$(this).addClass('checkActive');
	});

	$('#tabs').on('click', 'li a', function(e) {
		e.preventDefault();
		//$(this).tab('show')
	});

	//获取所有的查询条件
	getSearchCondition();
	//获取所有的站点
//	common.getallstation({}, function(data) {
//		var str = {
//			list: data
//		};
//		var decreasestationCode = template("decreasestationCodemodal", str);
//		document.getElementById("decreasestationCode").innerHTML = decreasestationCode;
//		//$('#decreasestationCode').comboSelect();
//	});
	//获取检查人名单
//	common.getcheckperson({}, function(data) {
//		var str = {
//			list: data
//		};
//		var checkperson = template("checkpersonmodal", str);
//		document.getElementById("rummager").innerHTML = checkperson;
//	});

	//获取时间段和月份

	var year = new Date().getFullYear();

	var line = year - 2000;

	var str = ('<option>' + year + '</option>');
	for(var i = 1; i < line; i++) {
		str += "<option>" + (year - i) + "</option>"
	}
	$("#startyear").html(str);
	for(var m = 1; m <= 12; m++) {
		if(m < 10) {
			m = "0" + m;
			startmonth += "<option>" + (m) + "</option>"
		}
		if(m >= 10) {
			startmonth += "<option>" + (m) + "</option>"
		}
	}

	$("#startmonth").html(startmonth);

	$("#endyear").html(str);
	for(var m = 1; m <= 12; m++) {
		if(m < 10) {
			m = "0" + m;
			endmonth += "<option>" + (m) + "</option>"
		}
		if(m >= 10) {
			endmonth += "<option>" + (m) + "</option>"
		}
	}
	$("#endmonth").html(endmonth);

	var month = new Date().getMonth()
	var end = document.getElementById('endmonth')
	var starty = document.getElementById('startyear')
	var startm = document.getElementById('startmonth')
	if(month == 0) {
		starty.options[1].selected = true;
		startm.options[11].selected = true;
	} else {
		starty.options[0].selected = true;
		startm.options[month - 1].selected = true;
	}
	end.options[month].selected = true;
}());

//======================================================================================================================
//	日常评价表

function getDaily(params, type) {
	common.daytest(params, function(data) {
		loading(false, 'loader3')
		var talktype = sessionStorage.getItem("talktype");
		if(!type) {
			type = "周表"
		}
		var obj = {
			title: type,
			data: data
		}
		var str = {
			list: obj
		};
		if(talktype.indexOf("3") > 0) {
			var runquality = template('dailytest', str);
			document.getElementById('richanggongzuopingjiabiao').innerHTML = runquality;
		} else {
			var daytest = template('yunxingdaily', str);
			document.getElementById('richanggongzuopingjiabiao').innerHTML = daytest;
		}

	})
}

function getTabledata(type, a) {
	var params = sessionStorage.getItem('params')
	getDaily(params, type)
    sessionStorage.setItem('tabletype',type);
	$('#tabs li').removeClass();
	$(a).parent().addClass('active');
}

//日常评价模态框
function daliy(index) {
	
	var params = sessionStorage.getItem("params");

	common.daytest(params, function(data) {
		var arr = data[index].dataResult;
		for(var key in arr) {
			arr[key].title = $('#tabs li.active a').html();
		}
		var obj={
			title:1,
			data:arr
		}
		var strs = {
			list: obj
		};

		var runquality = template('dailytest', strs);
		document.getElementById('daliytable').innerHTML = runquality;
	})
}

//获取四级联动数据
var areadata = [];

function loading(i, id) {
	if(i) {
		$('.' + id).css('display', 'block')
	} else {
		$('.' + id).css('display', 'none')
	}
}

function getSearchCondition() {

	common.rununit({}, function(data) {
		var str = {
			list: data
		};
		var getrununitinfo = template("getrununitinfo", str);
		document.getElementById("unit").innerHTML = getrununitinfo;
		areadata = data;
		document.getElementById("0").checked = true
		getSearchUnit();

	})

	// document.getElementsByName("unit")[1].checked = true;

	common.stationtype({}, function(data) {
		var str = {
			list: data
		};
		var getrununitinfo = template("getstationtypeinfo", str);
		document.getElementById("category").innerHTML = getrununitinfo;
		areadata = data;
		document.getElementById("stationtype0").checked = true
		getSearchCategory();

	})

}

var unit = $("#unit"),
	category = $("#category"),
	area = $("#area"),
	stationchose = $("#stationchose"),
	talkType = $("#talkType");

//运行单位

var unitj = 0
var categoryj = 0

function getSearchUnit() {
	unitj = 0
	var obj = document.getElementsByName('unit');
	var RunUnit = '';
	for(var i = 0; i < obj.length; i++) {
		if(obj[i].checked) {
			RunUnit += "runUnitIds=" + obj[i].value + '&';
			unitj++;
		} //如果选中，将value添加到变量s中
	}
	var rununit = RunUnit.substring(0, RunUnit.length - 1);
	sessionStorage.setItem("rununit", rununit);
	var params = sessionStorage.getItem("rununit") + '&' + sessionStorage.getItem("stationtype");
	if(unitj == 0 || categoryj == 0) {
		document.getElementById("citylist").innerHTML = '';
	} else {
		getCityList(params);
	}
}
//运行单位
unit.change(function() {
	getSearchUnit()

});
//站点类型
function getSearchCategory() {
	categoryj = 0;
	var obj = document.getElementsByName('stationtype');
	var StationType = '';
	for(var i = 0; i < obj.length; i++) {
		if(obj[i].checked) {
			StationType += "stationTypeIds=" + obj[i].value + '&';
			categoryj++;
		} //如果选中，将value添加到变量s中
	}

	var stationtype = StationType.substring(0, StationType.length - 1);
	sessionStorage.setItem("stationtype", stationtype);
	var params = sessionStorage.getItem("rununit") + '&' + sessionStorage.getItem("stationtype");
	if(unitj == 0 || categoryj == 0) {
		document.getElementById("citylist").innerHTML = '';
	} else {
		getCityList(params);
	}
	if(sessionStorage.getItem("talktype") == "status=2") {
		$('input[name=regionlist]').attr("disabled", true)
		// $(".regionlist").prop("readonly","readonly")
	} else {
		$('input[name=regionlist]').removeAttr("disabled")
	}
}

//站点类型
category.change(function() {
	getSearchCategory();
})

//评估类型
talkType.change(function() {
	var obj = document.getElementsByName('choose');
	var talkType = '';
	for(var i = 0; i < obj.length; i++) {
		// var h = document.getElementById('unit').getElementsByTagName("input").length
		// for (var j = 1; j < h; j++) {
		//     document.getElementById(j).checked = false
		// }
		// document.getElementById("0").checked = true
		if(obj[i].checked) {
			talkType += "status=" + obj[i].value + '&'; //如果选中，将value添加到变量s中
		}
	}

	var talktype = talkType.substring(0, talkType.length - 1);
	sessionStorage.setItem("talktype", talktype);

	if(sessionStorage.getItem("talktype") == "status=2") {
		$('input[name=regionlist]').attr("disabled", true)
		// $(".regionlist").prop("readonly","readonly")
	} else {
		$('input[name=regionlist]').removeAttr("disabled")
	}
})

//查询城市列表
function getCityList(params) {
	common.citylist(params, function(data) {

		var str = {
			list: data
		};
		var citylist = template("chooseCity", str);
		document.getElementById("citylist").innerHTML = citylist;
		document.getElementById("citylist0").checked = true
		$("#citylist0").nextAll("ul").find(".regionlist").prop('checked', true);
		document.getElementById("country_all").checked = false
		$(".cityName").click(function() {
			//阻止input的点击事件
			$(".citylist").click(function() {
				event.stopPropagation();
			})
			$(".regionlist").click(function() {
				event.stopPropagation();
			})

			if($(this).find("ul").css("display") == "block") {
				$(this).find("ul").css("display", "none")
			} else if($(this).find("ul").css("display") == "none") {
				$(this).find("ul").css("display", "block")
			}
		})

		$(".citylist").on("change", function() {
			if($(this).is(':checked')) {
				$(this).nextAll("ul").find(".regionlist").prop('checked', true);
			} else {
				$(this).nextAll("ul").find(".regionlist").removeAttr("checked");
			}
			var j = 0;
			for(var i = 0; i < $('.citylist').length; i++) {
				if(!$($(".citylist")[i]).is(':checked')) {
					j++;
				}
			}
			if(j != 0) {
				document.getElementById("country_all").checked = false
			}
		})
		if(sessionStorage.getItem("talktype") == "status=2") {
			$('input[name=regionlist]').attr("disabled", true)
			// $(".regionlist").prop("readonly","readonly")
		} else {
			$('input[name=regionlist]').removeAttr("disabled")
		}
	});

}

//点击按钮
function searchs() {
	var startyear = $("#startyear").val();
	var startmonth = $("#startmonth").val();
	var endyear = $("#endyear").val();
	var endmonth = $("#endmonth").val();
	var start = "start" + '=' + startyear + '-' + startmonth + '-' + "01";
	var end = "end" + '=' + endyear + '-' + endmonth + '-' + "01";
	var starttime = parseInt(start.replace(/-/g, '').substring(6))
	var endtime = parseInt(end.replace(/-/g, '').substring(4))
	if(starttime > endtime) {
		$('.alert_text').html('截止日期不能小于开始日期')
		$('.about_alert').css('visibility', 'visible')
		// alert('截止日期不能小于开始日期')
		return false;
	}
	var datenow = new Date().toLocaleDateString().split("/")
	for(var i = 1; i <= 2; i++) {
		if(datenow[i].length == 1) {
			datenow[i] = 0 + datenow[i]
		}
	}
	var daten = datenow[0] + datenow[1] + datenow[2]
	if(endtime > parseInt(daten)) {
		$('.alert_text').html('截止日期不能大于当前日期')
		$('.about_alert').css('visibility', 'visible')
		// alert('截止日期不能大于当前日期')
		return false;
	}
	var time = start + '&' + end;
	var city = "";
	//  评估类型为1或3传站点,评估类型为2传城市,站点
	var number = 0;
	var number2 = 0;
	if(sessionStorage.getItem("talktype") == "status=1" || sessionStorage.getItem("talktype") == "status=3") {
		for(var i = 0; i < $(".regionlist").length; i++) {
			if($($(".regionlist")[i]).is(':checked')) {
				city += "stationCodes=" + $(".regionlist")[i].value + '&';
				number++;
			}
		}
	}
	if(sessionStorage.getItem("talktype") == "status=2") {
		for(var i = 0; i < $(".citylist").length; i++) {
			if($($(".citylist")[i]).is(':checked')) {
				city += "cityIds=" + $(".citylist")[i].value + '&';
				number2++;
			}
		}
		for(var i = 0; i < $(".regionlist").length; i++) {
			if($($(".regionlist")[i]).is(':checked')) {
				city += "stationCodes=" + $(".regionlist")[i].value + '&';
			}
		}
	}

	var citys = city.substring(0, city.length - 1);

	if(sessionStorage.getItem("talktype") == "status=1" || sessionStorage.getItem("talktype") == "status=3") {
		if(number == 0) {
			$('.alert_text').html('请选择站点')
			$('.about_alert').css('visibility', 'visible')
			// alert("请选择站点")
			return false
		}
	}
	if(sessionStorage.getItem("talktype") == "status=2") {
		if(number2 == 0) {
			$('.alert_text').html('请选择城市')
			$('.about_alert').css('visibility', 'visible')
			// alert("请选择站点")
			return false
		}
	}
	if(sessionStorage.getItem("talktype") == "status=2" || sessionStorage.getItem("talktype") == "status=3") {
		$('#yunxingzhiliangtixitable').hide()
		$('#yunxingzhiliangtixitable_text').show()
	} else {
		$('#yunxingzhiliangtixitable').show()
		$('#yunxingzhiliangtixitable_text').hide()
	}
	var params = sessionStorage.getItem("rununit") + '&' + sessionStorage.getItem("stationtype") + '&' + sessionStorage.getItem("talktype") + '&' + time + '&' + citys;
	sessionStorage.setItem("params", params);

	//

	getDaily(params, sessionStorage.getItem('tabletype'))

	//	1传输率
	for(var i = 1; i <= 7; i++) {
		loading(true, 'loader' + i)
	}
	common.transmission(params, function(data) {
		loading(false, 'loader1')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: data
		};
		if(talktype.indexOf("3") > 0) {
			var chuanshuContent = template('chuanshutables', str);
			document.getElementById('chuanshulv').innerHTML = chuanshuContent;
		} else {
			var chuanshulv = template('chuanshutable', str);
			document.getElementById('chuanshulv').innerHTML = chuanshulv;
		}

	})
	//	1有效天数
	common.workday(params, function(data) {
		loading(false, 'loader2')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: data
		};
		if(talktype.indexOf("3") > 0) {
			var workscoretable = template('workscore', str);
			document.getElementById('defenpingjiabiao').innerHTML = workscoretable;
		} else {
			var workday = template('validtable', str);
			document.getElementById('defenpingjiabiao').innerHTML = workday;
		}

	})
	//	考核结果
	common.qualityscore(params, function(data) {
		loading(false, 'loader5')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: data
		};
		if(talktype.indexOf("3") > 0) {
			var testcalscorce = template('testcalscorce', str);
			document.getElementById('jiancekaohejieguo').innerHTML = testcalscorce;
		} else {
			var qualityscore = template('calscorce', str);
			document.getElementById('jiancekaohejieguo').innerHTML = qualityscore;
		}

	})
	//	1运行单位运行质量
	common.runquality(params, function(data) {
		loading(false, 'loader6')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: data
		};
		if(talktype.indexOf("3") > 0) {
			var runquality = template('runtable', str);
			document.getElementById('zhiliangpinggubiao').innerHTML = runquality;
		} else {
			var runquality = template('runquality', str);
			document.getElementById('zhiliangpinggubiao').innerHTML = runquality;
		}

	})

	//	运行质量体系
	common.qualityunit(params, function(data) {
		loading(false, 'loader7')
		var str1 = []
		for(var i = 0; i < data.length; i++) {
			if(data[i].runCode != null) {
				str1.push(data[i])
			} else {
				continue;
			}
		}
		var str = {
			list: str1
		};

		var runqualitytixi = template('runqualitytixi', str);
		document.getElementById('yunxingzhiliangtixitable').innerHTML = runqualitytixi;
	})
	//	监测仪器启用管理
	common.monitor(params, function(data) {
		loading(false, 'loader8')
		var str1 = []
		for(var i = 0; i < data.length; i++) {
			if(data[i].runCode != null) {
				str1.push(data[i])
			} else {
				continue;
			}
		}
		var str = {
			list: str1
		};

		var jianceyiqis = template('jianceyiqis', str);
		document.getElementById('jianceyiqitable').innerHTML = jianceyiqis;
		for(var i = 0; i < data.length; i++) {
			var oDate1 = new Date(); //转换为yyyy-MM-dd格式
			var oDate2 = new Date(data[i].OpeningTime);
			var iyears = (oDate1.getTime() - oDate2.getTime()) / 1000 / 3600 / 24 / 365;
			if(iyears >= 6.5) {
				$('#jiance' + i).css('color', 'red')
			} else {
				$('#jiance' + i).css('color', 'green')
			}
		}
	})

	common.losepoint(params, function(data) {
		loading(false, 'loader4')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: data
		};
		console.log(data)
		if(talktype.indexOf("3") > 0) {
			var daytest = template('monthdeduct', str);
			document.getElementById('jianchakoufenbiao').innerHTML = daytest;
		} else {
			var daytest = template('losepoint', str);
			document.getElementById('jianchakoufenbiao').innerHTML = daytest;
		}

	})
	
	common.basicsecurity(params, function(data) {
		loading(false, 'loader4')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: data
		};
		if(talktype.indexOf("3") > 0) {
			var _str = {
				list: data[0].dataResult,
				date: data[0].checkTime
			};
			var daytest = template('mybasicdetailModal', _str);
			document.getElementById('jibenbaozhang').innerHTML = daytest;
		} else {
			var daytest = template('basicsecurity', str);
			document.getElementById('jibenbaozhang').innerHTML = daytest;
		}

	});
	var count = 0;
	var _count = 0;
	$.each($('.indexli').children('li'),function(i,v){
		if($(v).hasClass('active')){
			count = i
		}
	});
	switch(count){
		case 0:
		getzhikongtable(0,'周',14);
		break;
		case 1:
		getzhikongtable(1,'月',18);
		break;
		case 2:
		getzhikongtable(2,'季度',11);
		break;
		case 3:
		getzhikongtable(3,'半年度',9);
		break;
		case 4:
		getzhikongtable(4,'年度',8);
		break;
		default:
		break;
	}
	/*调用质控统计表*/
	
	/*调用质控监督()飞行表*/
	$.each($('.ulindexli').children('li'),function(i,v){
		if($(v).hasClass('active')){
			_count = i;
		}
	});
	
		switch(_count){
		case 0:
		getzhikongjiandutable(1,'月',10);
		break;
		case 1:
		getzhikongjiandutable(2,'季度',8);
		break;
		case 2:
		getzhikongjiandutable(3,'半年',8);
		break;
		case 3:
		getzhikongjiandutable(4,'年',8);
		break;
		break;
		default:
		break;
	}
	
}
/*质控统计表*/
function getzhikongtable(tableid,title,row){
	var params = sessionStorage.getItem('params')
	var zhikongparams = params;
	zhikongparams=zhikongparams+'&tableid='+tableid;
	common.qualitytable(zhikongparams, function(data) {
		loading(false, 'loader4')
		
		var talktype = sessionStorage.getItem("talktype");
		switch(row){
			case 14:
			getcreattime(data,5);
			break;
			case 18:
			getcreattime(data,12);
			break;
			case 11:
			getcreattime(data,4);
			break;
			case 9:
			   getcreattime(data,2);
			break;
			case 8:
			   getcreattime(data,1);
			break;
			default:
			break;
		}
	     
		var str  = {
			list: {
				title:title,
				data:data,
				row:row
			}
		};
		var daytest = template('zhijiantongjiModel', str);
		document.getElementById('zhikongtongjitable').innerHTML = daytest;

	})
}

function getcreattime(data,index){
	 var _createDate = {createDate:''};
	 data.map(function(v,i){
		if(v.monthData.stationcomplete.length==0){
			v.monthData.stationcomplete.length = index;
			for(var j=0;j<data[i]['monthData']['stationcomplete'].length;j++){
 			  data[i]['monthData']['stationcomplete'][j] = _createDate;
		    }
		 }
	 })
}
function getclickzhikongtable(tableid,title,row,a){
//	window.localStorage.setItem('liIndex',$(a).parent().index())
	getzhikongtable(tableid,title,row);
	$('#tabs li').removeClass();
	$(a).parent().addClass('active');
}
/*质控监督()飞行表*/
function getzhikongjiandutable(type,title,row){
	var params = sessionStorage.getItem('params')
	var zhikongparams = params;
	zhikongparams=zhikongparams+'&type='+type;
	common.inspectiontable(zhikongparams, function(data) {
		loading(false, 'loader4')
		var talktype = sessionStorage.getItem("talktype");
		var str = {
			list: {
				title:title,
				data:data,
				row:row
			}
		};
		var daytest = template('zhikongjianduModel', str);
		document.getElementById('zhikongjiandutable').innerHTML = daytest;

	})
}

function getclickzhikongjiandutable(type,title,row,a){
	getzhikongjiandutable(type,title,row);
	$('#tabs li').removeClass();
	$(a).parent().addClass('active');
}
function exportToExcel(tableid) {

}
// 表格导出
function method1(tableid) { //整个表格拷贝到EXCEL中

	if(tableid == undefined) {
		var id = $(".active table").attr('id')
	} else {
		id = tableid
	}
	// console.log(tableid)
	$('#exportTableForm').remove()
	var str = $(document.getElementById(id)).html()
	if(id == 'scoreDetails') {
		var dec = document.getElementById('decreasestationCode').options
		for(var i = 0; i < $('.decreasestationCodemo').length; i++) {
			if(dec[i].selected) {
				var strs = '<table>' + '<tr>' + '<th>' + dec[i].innerText + '</th>' + '</tr>' + str + '</table>'
			}
		}
	}else if(id=='baseTable'){
		var _locatedCityCode = $('#locatedCityCode').val(),
		    _locatedCountyCode =$('#locatedCountyCode').val(),
		    _stationNameModelCode =$('#stationNameModelCode').val();
		    if(_locatedCityCode==0){
		    	$('.alert_text').html('请选择站点所在地市级');
				$('.about_alert').css('visibility', 'visible');
				return ;
		    }
		    if(_locatedCountyCode==0){
		    	$('.alert_text').html('请选择站点所在地县级');
				$('.about_alert').css('visibility', 'visible');
				return ;
		    }
		    if(_stationNameModelCode==0){
		    	$('.alert_text').html('请选择 监测子站名称');
				$('.about_alert').css('visibility', 'visible');
				return ;
		    }
		    var strs = '<table>' + '<tr>'  
		                +  '<th>' + _locatedCityCode.innerText + '</th>'
		                +  '<th>' + _locatedCountyCode.innerText + '</th>'
		                +  '<th>' + _stationNameModelCode.innerText + '</th>'
		                + '</tr>' + str + '</table>';
	} else {
		var strs = '<table>' + str + '</table>'
	}
	var tablename = $.trim($('#' + id + ' tr:eq(0) th:eq(0)').html())
	// console.log(strs)
	var input1 = $('<input type="hidden" name="excelName" value=' + tablename + '/>')
	var input2 = $('<input type="hidden" name="cellValues"/>')
	var form = $('<form action=' + commonUrl + "scair/excelexport/normalexport" + ' id="exportTableForm" method = "post"></form>')
	var strss = input2.val(strs)
	form.append(input1).append(strss)
	$('body').append(form)
	form.submit()
}
// 站点全选
function cheackall() {
	if($('#country_all').is(':checked')) {
		for(var i = 0; i < $('.citylist').length; i++) {
			document.getElementById("citylist" + i).checked = true
			$("#citylist" + i).nextAll("ul").find(".regionlist").prop('checked', true);
		}

	} else {
		for(var i = 0; i < $('.citylist').length; i++) {
			document.getElementById("citylist" + i).checked = false;
			$("#citylist" + i).nextAll("ul").find(".regionlist").removeAttr("checked");
		}
	}
}

var layShowi = 0
//      传输率模态框赋值
function layShow(index) {
	layShowi = index
	var params = sessionStorage.getItem("params");
	common.transmission(params, function(data) {
		var strs = {
			list: data[index].dataResult
		};
		var chuanshuContent = template('chuanshutables', strs);
		document.getElementById('chuanshuContent').innerHTML = chuanshuContent;
	})
}

// 数据传输排序
function forfind(x) {

	if($(".iconfont_" + x).hasClass('icon-sort-small-copy-copy') || $(".iconfont_" + x).hasClass('icon-sort-up-blue')) {
		var params = sessionStorage.getItem("params");
		params += '&' + 'param=' + x;
		params += '&' + 'sortstatus=1';
	} else {
		var params = sessionStorage.getItem("params");
		params += '&' + 'param=' + x;
		params += '&' + 'sortstatus=0';
	}
	common.transmission(params, function(data) {

		if(sessionStorage.getItem("talktype") == "status=3") {
			var strs2 = {
				list: data
			};
			var chuanshuContent2 = template('chuanshutables', strs2);
			document.getElementById('chuanshulv').innerHTML = chuanshuContent2;
		} else {
			var strs = {
				list: data[layShowi].dataResult
			};
			var chuanshuContent = template('chuanshutables', strs);
			document.getElementById('chuanshuContent').innerHTML = chuanshuContent;
		}
		var str = params.substr(params.length - 1, 1)
		if(str == '0') {
			$(".iconfont_" + x).removeClass('icon-paixu');
			$(".iconfont_" + x).removeClass('icon-sort-up-blue');
			$(".iconfont_" + x).addClass('icon-sort-small-copy-copy');
		} else {
			$(".iconfont_" + x).removeClass('icon-sort-up-blue');
			$(".iconfont_" + x).removeClass('icon-sort-small-copy-copy');
			$(".iconfont_" + x).addClass('icon-paixu');

		}

	})

}

//有效天数模态框

var layindex;

function layShows(index) {
	layindex = index
	var params = sessionStorage.getItem("params");
	console.log(params)
	common.workday(params, function(data) {
		var strs = {
			list: data[index].dataResult
		};
		var workscoretable = template('workscore', strs);
		document.getElementById('scorecontent').innerHTML = workscoretable;
	})
}
// 有限天数排序
function forfind2(x) {
	if($('.iconfont_' + x).hasClass('icon-sort-small-copy-copy') || $('.iconfont_' + x).hasClass('icon-sort-up-blue')) {
		var params = sessionStorage.getItem("params");
		params += '&' + 'param=' + x;
		params += '&' + 'sortstatus=1';
	} else {
		var params = sessionStorage.getItem("params");
		params += '&' + 'param=' + x;
		params += '&' + 'sortstatus=0';
	}
	common.workday(params, function(data) {
		if(sessionStorage.getItem("talktype") == "status=3") {
			var strs2 = {
				list: data
			};
			var workscoretable2 = template('workscore', strs2);
			document.getElementById('defenpingjiabiao').innerHTML = workscoretable2;
		} else {
			var strs = {
				list: data[layindex].dataResult
			};
			var workscoretable = template('workscore', strs);
			document.getElementById('scorecontent').innerHTML = workscoretable;
		}
		var str = params.substr(params.length - 1, 1)
		if(str == '0') {
			$('.iconfont_' + x).removeClass('icon-paixu');
			$('.iconfont_' + x).removeClass('icon-sort-up-blue');
			$('.iconfont_' + x).addClass('icon-sort-small-copy-copy');
		} else {
			$('.iconfont_' + x).removeClass('icon-sort-up-blue');
			$('.iconfont_' + x).removeClass('icon-sort-small-copy-copy');
			$('.iconfont_' + x).addClass('icon-paixu');

		}

	})
}

// 全屏

var styles = {
	'position': 'absolute',
	'top': '-30px',
	'right': 0,
	'left': 0
}
var styles2 = {
	'position': 'relative',
	'top': 0,
	'right': 0,
	'left': 0
}

function fullscreen() {
	var str = $('.screen').html()
	if(str == '全屏') {
		$('.header-content').hide()
		$('.right').hide()
		$('.main-content_').removeClass('col-md-9')
		$('.main-content_').addClass('col-md-12')
		$('.main-content_').css(styles)
		$('.screen').html('缩小')
	} else {
		$('.header-content').show()
		$('.right').show()
		$('.main-content_').removeClass('col-md-12')
		$('.main-content_').addClass('col-md-9')
		$('.main-content_').css(styles2)
		$('.screen').html('全屏')
	}
}
var testindex;
//考核结果模态框
function testcalscorce(index) {
	testindex = index
	var params = sessionStorage.getItem("params");
	common.qualityscore(params, function(data) {
		var strs = {
			list: data[index].dataResult
		};
		var testcalscorce = template('testcalscorce', strs);
		document.getElementById('testcalscorces').innerHTML = testcalscorce;
	})
}


//考核结果排序
function forfind3(x) {
	if($('.iconfont_' + x).hasClass('icon-sort-small-copy-copy') || $('.iconfont_' + x).hasClass('icon-sort-up-blue')) {
		var params = sessionStorage.getItem("params");
		params += '&' + 'param=' + x;
		params += '&' + 'sortstatus=1';
	} else {
		var params = sessionStorage.getItem("params");
		params += '&' + 'param=' + x;
		params += '&' + 'sortstatus=0';
	}
	common.qualityscore(params, function(data) {
		if(sessionStorage.getItem("talktype") == "status=3") {
			var strs2 = {
				list: data
			};
			var testcalscorce2 = template('testcalscorce', strs2);
			document.getElementById('jiancekaohejieguo').innerHTML = testcalscorce2;
		} else {
			var strs = {
				list: data[testindex].dataResult
			};
			var testcalscorce = template('testcalscorce', strs);
			document.getElementById('testcalscorces').innerHTML = testcalscorce;
		}
		var str = params.substr(params.length - 1, 1)
		if(str == '0') {
			$('.iconfont_' + x).removeClass('icon-paixu');
			$('.iconfont_' + x).removeClass('icon-sort-up-blue');
			$('.iconfont_' + x).addClass('icon-sort-small-copy-copy');
		} else {
			$('.iconfont_' + x).removeClass('icon-sort-up-blue');
			$('.iconfont_' + x).removeClass('icon-sort-small-copy-copy');
			$('.iconfont_' + x).addClass('icon-paixu');

		}
	})
}

//运行单位质量评估表模态框
function runquality(index) {
	var params = sessionStorage.getItem("params");
	common.runquality(params, function(data) {

		var strs = {
			list: data[index].dataResult
		};
		var runquality = template('runtable', strs);
		document.getElementById('runqualis').innerHTML = runquality;
	})
}

//运行费用配置表模态框
function runningCost(index) {

	var month = $('#month1').val();
	var year = $('#year1').val();
	var TimePoint = year + "-" + month + "-" + "01";

	common.runningCost({
		TimePoint
	}, function(data) {
		var strs = {
			list: data
		};

		var runquality = template('runCostTable', strs);
		document.getElementById('runCost').innerHTML = runquality;
	})
}
//运行费用保存按鈕
function saveRunCost() {
	var year1 = $("#year1").val();
	var month1 = $("#month1").val();
	var runCost = $("#cost").val();
	var params = [],
		arr = $('#runCost tr');
	for(var i = 2, len = arr.length; i < len; i++) {
		params.push({
			StationCode: $('#runCost tr').eq(i).find('td').eq(1).html(),
			Cost: $('#runCost tr').eq(i).find('td').eq(5).find('input')[0].value || "0",
			TimePoint: year1 + "-" + month1 + "-01"
		})
	}
	console.log(params);
	
	common.saveRunCost({
		dataList: JSON.stringify(params)
	}, function(data) {
		
	});

    // var params2 = [],
    //     arr = $('#runCost tr');
    // for(var i = 2, len = arr.length; i < len; i++) {
    //     params2.push({
    //         StationCode: $('#runCost tr').eq(i).find('td').eq(1).html(),
    //         Cost: $('#runCost tr').eq(i).find('td').eq(5).find('input')[0].value || "0",
    //         TimePoint: year1 + "-" + "02" + "-01"
    //     })
    // }
    // common.saveRunCost({
    //     dataList: JSON.stringify(params2)
    // }, function(data) {
    //
    // })
}

//运行费用模态框  年-月
SYT = "年份";
SMT = "月份";
BYN = 20; //年份范围往前20年
AYN = 0; //年份范围往后0年
function YMDselect() {
	this.SelY = document.getElementsByName(arguments[0])[0];
	this.SelM = document.getElementsByName(arguments[1])[0];
	this.SelD = document.getElementsByName(arguments[2])[0];
	this.DefY = this.SelD ? arguments[3] : arguments[2];
	this.DefM = this.SelD ? arguments[4] : arguments[3];
	this.DefD = this.SelD ? arguments[5] : arguments[4];
	this.SelY.YMD = this;
	this.SelM.YMD = this;
	this.SelY.onchange = function() {
		YMDselect.SetM(this.YMD)
	};
	if(this.SelD) this.SelM.onchange = function() {
		YMDselect.SetD(this.YMD)
	};
	YMDselect.SetY(this)
};
//设置年份
YMDselect.SetY = function(YMD) {
	dDate = new Date();
	dCurYear = dDate.getFullYear();
	YMD.SelY.options.add(new Option(SYT, '0'));
	for(i = dCurYear + AYN; i > (dCurYear - BYN); i--) {
		YMDYT = i;
		YMDYV = i;
		OptY = new Option(YMDYT, YMDYV);
		YMD.SelY.options.add(OptY);
		if(YMD.DefY == YMDYV) OptY.selected = true
	}
	YMDselect.SetM(YMD)
};
//设置月份
YMDselect.SetM = function(YMD) {
	YMD.SelM.length = 0;
	YMD.SelM.options.add(new Option(SMT, '0'));
	if(YMD.SelY.value > 0) {
		for(var i = 1; i <= 12; i++) {
			YMDMT = i;
			YMDMV = i;
			OptM = new Option(YMDMT, YMDMV);
			YMD.SelM.options.add(OptM);
			if(YMD.DefM == YMDMV) OptM.selected = true
		}
	}
	if(YMD.SelD) YMDselect.SetD(YMD)
};

//质量体系模态框
function qualityunit(index) {
	var params = sessionStorage.getItem("params");
	common.qualityunit(params, function(data) {

		var strs = {
			list: data[index].dataResultMap.dataResult
		};
		var runquality = template('unitquality', strs);
		document.getElementById('myModalunits').innerHTML = runquality;
	})
}

//扣分详情

var loseindex;

function losepointDetail(index) {
	loseindex = index
	var params = sessionStorage.getItem("params");
    var cookies =decodeURI(document.cookie.split("username=")[1].split(";")[0]) ;
	common.losepoint(params, function(data) {
        data[index].dataResult.forEach(function (v) {
			if(v.checkPerson==cookies){
				v['show']=true;
			}else{
                v['show']=false;
			}
        })
		var str = {
			list: data[index].dataResult,
			date: data[index].checkTime
		};
		console.log(str.list)
		var daytest = template('monthdeduct', str);
		document.getElementById('losepointDetail').innerHTML = daytest;
	})
}

function change() {
	if($('.icon_position').hasClass('icon-jiantouxiangxia')) {
		$('.icon_position').removeClass('ticon-jianouxiangxia')
		$('.icon_position').addClass('icon-xiangxiajiantou-copy')
	} else {
		$('.icon_position').removeClass('icon-xiangxiajiantou-copy')
		$('.icon_position').addClass('icon-jiantouxiangxia')
	}
}
//============================================================================================================================

//根据条件搜索
function getsearchParams() {
	var startyear = $("#startyear").val();
	var startmonth = $("#startmonth").val();
	var endyear = $("#endyear").val();
	var endmonth = $("#endmonth").val();
	var start = "start" + '=' + startyear + '-' + startmonth + '-' + "01";
	var end = "end" + '=' + endyear + '-' + endmonth + '-' + "01";
	var starttime = parseInt(start.replace(/-/g, '').substring(6))
	var endtime = parseInt(end.replace(/-/g, '').substring(4))

	if(starttime > endtime) {
		$('.alert_text').html('截止日期不能小于开始日期')
		$('.about_alert').css('visibility', 'visible')
		// alert('截止日期不能小于开始日期')
		return false;
	}
	var datenow = new Date().toLocaleDateString().split("/")
	for(var i = 1; i <= 2; i++) {
		if(datenow[i].length == 1) {
			datenow[i] = 0 + datenow[i]
		}
	}
	var daten = datenow[0] + datenow[1] + datenow[2]
	if(endtime > parseInt(daten)) {
		$('.alert_text').html('截止日期不能大于当前日期')
		$('.about_alert').css('visibility', 'visible')
		// alert('截止日期不能大于当前日期')
		return false;
	}
	var time = start + '&' + end;
	var city = "";
	//  评估类型为1或3传站点,评估类型为2传城市,站点
	var number = 0;
	if(sessionStorage.getItem("talktype") == "status=1" || sessionStorage.getItem("talktype") == "status=3") {
		for(var i = 0; i < $(".regionlist").length; i++) {
			if($($(".regionlist")[i]).is(':checked')) {
				city += "stationCodes=" + $(".regionlist")[i].value + '&';
				number++;
			}
		}
	}
	if(sessionStorage.getItem("talktype") == "status=2") {
		for(var i = 0; i < $(".citylist").length; i++) {
			if($($(".citylist")[i]).is(':checked')) {
				city += "cityIds=" + $(".citylist")[i].value + '&';
			}
		}
		for(var i = 0; i < $(".regionlist").length; i++) {
			if($($(".regionlist")[i]).is(':checked')) {
				city += "stationCodes=" + $(".regionlist")[i].value + '&';
			}
		}
	}

	var citys = city.substring(0, city.length - 1);

	if(sessionStorage.getItem("talktype") == "status=1" || sessionStorage.getItem("talktype") == "status=3") {
		if(number == 0) {
			$('.alert_text').html('请选择站点')
			$('.about_alert').css('visibility', 'visible')
			// alert("请选择站点")
			return false
		}
	}
	var params = sessionStorage.getItem("rununit") + '&' + sessionStorage.getItem("stationtype") + '&' + sessionStorage.getItem("talktype") + '&' + time + '&' + citys;
	return params
}

function detailLay(index) {}
//查询条件
function search() {
	if(!getsearchParams()) {
		return
	}
	var params = getsearchParams();
	getData.conditons(params)
}

//查询条件选择
var unit = $("#unit"),
	category = $("#category"),
	area = $("#area"),
	stationchose = $("#stationchose");
var getData = {
	conditons: function(params) {
		//传输率
		common.monthtransferConditions(params, function(data) {
			var month = "";
			if(data.length > 0) {
				month = parseInt(data[0].monthTime.split('-')[1]);
			}
			var str = {
				title: month,
				list: data
			};
			var chuanshulv = template('chuanshutable', str);
			document.getElementById('chuanshulv').innerHTML = chuanshulv;
		});
		//有效天数
		common.monthvalidateConditions(params, function(data) {
			var month = "";
			if(data.length > 0) {
				month = parseInt(data[0].monthTime.split('-')[1]);
			}
			var str = {
				title: month,
				list: data
			};
			var hvalidate = template('validtable', str);
			document.getElementById('defenpingjiabiao').innerHTML = hvalidate;
		});
		//日常工作
		common.monthrununitworkingConditions(params, function(data) {
			var month = "";
			if(data.monthTime) {
				month = parseInt(data.monthTime.split('-')[1]);
			}
			var str = {
				title: month,
				list: data
			};
			var monthunitworking = template('monthunitworking', str);
			document.getElementById('zhiliangpinggubiao').innerHTML = monthunitworking;
		});
		//扣分表
		common.monthdeductConditions(params, function(data) {
			var month = "";
			if(data.length > 0) {
				month = parseInt(data[0].checkTime.split('-')[1]);
			}
			var str = {
				title: month,
				list: data
			};
			var monthdeduct = template('monthdeduct', str);
			document.getElementById('jianchakoufenbiao').innerHTML = monthdeduct;
		});
		//质量评估
		common.monthRunUnitQCConditions(params, function(data) {
			var month = "";
			if(data.length > 0) {
				month = parseInt(data[0].checkTime.split('-')[1]);
			}
			var str = {
				title: month,
				list: data
			};
			var monthRunUnitQC = template('monthRunUnitQC', str);
			document.getElementById('richanggongzuopingjiabiao').innerHTML = monthRunUnitQC;
		});

		//查询得分
		common.calscorceConditions(params, function(data) {
			var month = "";
			if(data.length > 0) {
				month = parseInt((data[0].monthTime.split(' ')[0]).split('-')[1]);
			}
			var str = {
				title: month,
				list: data
			};
			var calscorce = template('calscorce', str);
			document.getElementById('jiancekaohejieguo').innerHTML = calscorce;
		})
	}
};

var scoreobj = {
	"score0": 0, //单项得分
	"score1": 0,
	"score2": 0,
	"score3": 0,
	"score4": 0,
	"score5": 0,
	"score6": 0,
	"score7": 0,
	"score8": 0,
	"score9": 0,
	"score10": 0,
	"score11": 0,
	"score12": 0,
	"score13": 0,
	"score14": 0,
	"score15": 0,
	"score16": 0,
	"score17": 0,
	"score18": 0,
	"score19": 0,
	"score20": 0,
	"score21": 0,
	"score22": 0,
	"score23": 0,
	"score24": 0,
	"score25": 0,
	"score26": 0,
    "mark0": "", //单项扣分
    "mark1": "",
    "mark2": "",
    "mark3": "",
    "mark4": "",
    "mark5": "",
    "mark6": "",
    "mark7": "",
    "mark8": "",
    "mark9": "",
    "mark10": "",
    "mark11": "",
    "mark12": "",
    "mark13": "",
    "mark14": "",
    "mark15": "",
    "mark16": "",
    "mark17": "",
    "mark18": "",
    "mark19": "",
    "mark20": "",
    "mark21": "",
    "mark22": "",
    "mark23": "",
    "mark24": "",
    "mark25": "",
    "mark26": "",
	"detail0": "", //横线
	"detail1": "",
	"detail2": "",
	"detail3": "",
	"detail4": "",
	"detail5": "",
	"detail6": "",
	"detail7": "",
	"detail8": "",
	"detail9": "",
	"detail10": "",
	"detail11": "",
	"detail12": "",
	"detail13": "",
	"detail14": "",
	"detail15": "",
	"detail16": "",
	"detail17": "",
	"detail18": "",
	"detail19": "",
	"detail20": "",
	"detail21": "",
	"detail22": "",
	"detail23": "",
	"detail24": "",
	"detail25": "",
	"detail26": "",
	"detail27": "",
	"detail28": "",
	"detail29": "",
	"detail30": "",
	"detail31": "",
	"detail32": "",
	"detail33": "",
	"detail34": "",
	"detail35": "",
	"detail36": "",
	"detail37": "",
	"detail38": "",
	"detail39": "",
	"detail40": "",
	"detail41": "",
	"detail42": "",
	"detail43": "",
	"detail44": "",
	"detail45": "",
	"detail46": "",
	"detail47": "",
	"detail48": "",
	"detail49": "",
	"detail50": "",
	"detail51": "",
	"detail52": "",
	"detail53": "",
	"detail54": "",
	"detail55": "",
	"detail56": "",
	"detail57": "",
	"detail58": "",
	"detail59": "",
	"detail60": "",
	"detail61": "",
	"detail62": "",
	"detail63": "",
	"detail64": "",
	"detail65": "",
	"detail66": "",
	"detail67": "",
	"detail68": "",
	"detail69": "",
	"detail70": "",
	"detail71": "",
	"detail72": "",
	"detail73": "",
	"detail74": "",
	"detail75": "",
	"detail76": "",
	"detail77": "",
	"detail78": "",
	"detail79": "",
	"detail80": "",
	"detail81": "",
	"detail82": "",
	"detail83": "",
	"detail84": "",
	"detail85": "",
	"detail86": "",
	"detail87": "",
	"detail88": "",
	"detail89": "",
	"detail90": "",
	"detail91": "",
	"detail92": "",
	"detail93": "",
	"detail94": "",
	"detail95": "",
	"detail96": "",
	"detail97": "",
	"detail98": "",
	"check0":0, //气密性检查
	"check1":0,
	"check2":0,
	"check3":0,
	"check4":0,
	"check5":0,
	"check6":0,
};
var alertid;
var stylesfocus = {
	'border': '1px solid red'
}
var stylesonblur = {
	'border': '1px solid #ada4a4'
}

function getdatascore(id, score, obj) {
   var count = 0;
   var sumcount = 0;
	alertid = id
	if(id.indexOf("score") >= 0) {
		$('#' + id).css(stylesonblur)
		var reg = /^[1-9]\d*$/;
		var scorevalue = parseInt(($(obj).val()).trim());
		var number = parseInt(score);
		if(reg.test(scorevalue + 1) && scorevalue <= number) {
			scoreobj[id] = $(obj).val();
		} else {
			$(obj).val("0");
			scoreobj[id] = 0;
			document.getElementById(id).focus();
			$('.alert_text2').html('只能填写符合规则的数字，且分数不能大于' + number)
			$('.about_alert2').css('visibility', 'visible')
			return
		}
	} else {
		scoreobj[id] = $(obj).val();
		$('.resettd').each(function(i,v){ 
		   count+=Number($(v).next('td').children('input').val());
		});
		$('#totalscore').text(count);
	}
}

function getDetailData(id, type, obj, bool, num) {
	alertid = id
	var volume = $(obj).val().trim();
//	var reg = /^[1-9]\d*$/;
//只能输入整数或小数的正则表达式
	var reg = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
	if(reg.test(volume) && volume != "") {
		$('#' + id).css(stylesonblur)
		if(bool) {
			scoreobj[id] = volume;
			var isNext = parseInt(num);
			var main = parseInt(type);
			var first = main + 2;
			var second = main + 1;
			var prev = main - 1;
			if(num == "1") {
				var secondValue = ($("#detail" + second).val()).trim();
				var currentValue = ($(obj).val()).trim();
				if(secondValue != "" && currentValue != "") {
					var avg = Math.abs((((currentValue - secondValue) / secondValue) * 100).toFixed(2));
					$("#detail" + first).html(avg);
				}
				scoreobj["detail" + second] = secondValue;
				scoreobj["detail" + type] = currentValue;
				scoreobj["detail" + first] = avg;
			} else {
				var prevValue = ($("#detail" + prev).val()).trim();
				var currentValue = ($(obj).val()).trim();
				if(secondValue != "" && currentValue != "") {
					var avg = Math.abs((((prevValue - currentValue) / currentValue) * 100).toFixed(2));
					$("#detail" + second).html(avg);
				}
				scoreobj["detail" + prev] = prevValue;
				scoreobj["detail" + type] = currentValue;
				scoreobj["detail" + second] = avg;
			}
		} else {
			scoreobj["detail" + type] = $(obj).val();
		}
	} else {
//		$(obj).val('1')
//		document.getElementById(id).focus();
//		$('.alert_text2').html('您所输入的格式不正确')
//		$('.about_alert2').css('visibility', 'visible')
		return
	}
}


function closealert() {
	document.getElementById(alertid).focus();
	$('#' + alertid).css(stylesfocus)
	$('.about_alert2').css('visibility', 'hidden')
}

function closealert2() {
	$('.about_alert').css('visibility', 'hidden')
}

//新增扣分表数据
function adddecreaseGrade() {
	var text = $('#myModalLabel').html()
	var dataparams = getsearchParams();
	for(var i = 0; i < 27; i++) {
		var mark = "mark" + i;
		var score = "score" + i;
		if(scoreobj[mark] == '') {
			scoreobj[mark] = $("#" + mark).val()
		}
//		if(scoreobj[score] == '') {
			if($("#" + score).val() != '') {
				scoreobj[score] = parseInt($("#" + score).val());
			} else {
				scoreobj[score] = 0
			}
//		}
	}
	

	for(var j = 1; j < 99; j++) {
		var detail = "detail" + j;
		var newdata = $("#" + detail).val().trim() || $("#" + detail).html().trim();
		if(scoreobj[detail] == '' && newdata == '') {
//			$('.alert_text').html('质量控制效果数据不能为空，请填写完整')
//			$('.about_alert').css('visibility', 'visible')
//			return
		} else if(scoreobj[detail] == '') {
			scoreobj[detail] = newdata
		}
	}
	for(var k=0;k<4;k++){
		var check = "check"+k;
		if($("#" + check).checked){
			scoreobj[check] =1;
		}else{
			scoreobj[check] =0;
		}
	}
	var _decreasestationCityCode = $("#decreasestationCityCode").val();
	var _decreasestationlocatedCountyCode = $("#decreasestationlocatedCountyCode").val();
	var stationCode = $("#decreasestationstationNameModelCode").val();
	scoreobj["stationCode"] = stationCode;
	scoreobj["timePoint"] = $('#decreasestationbasedate').val();
	scoreobj['checkTime'] = $('#dateinfo').val();
	scoreobj['runTime'] = $('#yunweidateinfo').val();
	scoreobj["checkPerson"] = $('#rummagername').val();
	scoreobj["runPerson"] = $('#yunweirenyuan').val();
	scoreobj["other"] = $('#othertext').val();
	scoreobj["detailId"] = _detailIdspan;
	scoreobj['runName'] = $('#decreasestationbaseunit').val();
	var dateinfo = $("#dateinfo").val();
	var rummager = $("#rummagername").val();
	var updatestationcode = $("#updateCode").val();
	if(updatestationcode != "0") {
		stationCode = updatestationcode;
	}
	if(!dateinfo) {
		$('.alert_text').html('请先选择时间')
		$('.about_alert').css('visibility', 'visible')
		return
	}
	var month = parseInt(((dateinfo.split(' ')[0]).split('-'))[1]);
	var year = parseInt(((dateinfo.split(' ')[0]).split('-'))[0]);
	var tomonth = (new Date().getMonth() + 1);
	var toyear = (new Date().getFullYear());
	var today = (new Date().getDate());
	if(_decreasestationCityCode == 0) {
		$('.alert_text').html('您还没有选择站点市级')
		$('.about_alert').css('visibility', 'visible')
		return
	}
	if(_decreasestationlocatedCountyCode == 0) {
		$('.alert_text').html('您还没有选择站点县级')
		$('.about_alert').css('visibility', 'visible')
		return
	}
	if(stationCode == 0) {
		$('.alert_text').html('您还没有选择子站点')
		$('.about_alert').css('visibility', 'visible')
		return
	}
	if(year != toyear) {
		$('.alert_text').html('你只能选择当前年份')
		$('.about_alert').css('visibility', 'visible')
		return
	}
	if(month != tomonth) {
		$('.alert_text').html('你只能选择当前月份')
		$('.about_alert').css('visibility', 'visible')
		return
	}
	if(text != '扣分新增表') {
		scoreobj["detailId"] = stationCode;
	} else {
		delete scoreobj["detailId"];
	}
	scoreobj["checkTime"] = dateinfo;
	scoreobj["checkPerson"] = rummager;
	var _status = 'status';
	var statusparm = [];
	for(var i=0;i<27;i++){
		statusparm.push(_status+i);
	}
	for(var i=0;i<statusparm.length;i++){
			scoreobj['status'+i] = $('input[name='+statusparm[i]+']:checked').val();
	}
	common.savedeductscore(scoreobj, function(data) {
		console.log(scoreobj)
		if(data.result) {
			//质控检查扣分表
			if(sessionStorage.getItem("talktype") == "status=1" || sessionStorage.getItem("talktype") == "status=2") {
				var params = sessionStorage.getItem("params");
				common.losepoint(params, function(data) {
					var str = {
						list: data[loseindex].dataResult,
						date: data[loseindex].checkTime
					};
					console.log(str)
					_detailIdspan = '';
					var daytest = template('monthdeduct', str);
					document.getElementById('losepointDetail').innerHTML = daytest;
				})
				
				common.losepoint(params, function(data) {
					var str = {
						list: data
					};
					_detailIdspan = '';
					var daytest = template('losepoint', str);
					document.getElementById('jianchakoufenbiao').innerHTML = daytest;
				})
			} else {
				common.monthdeductConditions(dataparams, function(data) {
					var month = "";
					if(data.length > 0) {
						month = parseInt(data[0].checkTime.split('-')[1]);
					}
					var str = {
						title: month,
						list: data
					};
					_detailIdspan = '';
					var monthdeduct = template('monthdeduct', str);
					document.getElementById('jianchakoufenbiao').innerHTML = monthdeduct;
				});
			}
			$('.alert_text').html('提交成功')
			$('.about_alert').css('visibility', 'visible')
			// alert("提交成功");
			$(".btn-default").click();
		}
	})

}

function OnInput(event) {

	var keyword = $('#decreasestationCode_select').val()
	common.getallstation({}, function(data) {
		var list = []
		var arr = []
		for(var i = 0; i < data.length; i++) {
			list.push(data[i].PositionName)
		}
		for(var i = 0; i < list.length; i++) {
			if(list[i].indexOf(keyword) >= 0) {
				arr.push(list[i]);
			}
		}
		if(keyword == '') {
			var str = {
				list: data
			};
		} else {
			var data2 = [];
			for(var h = 0; h < arr.length; h++) {
				for(var j = 0; j < data.length; j++) {
					if(data[j].PositionName == arr[h]) {
						data2.push(data[j])
					}
				}
			}
			var str = {
				list: data2
			};
		}
		var decreasestationCode = template("decreasestationCodemodal", str);
		document.getElementById("decreasestationCode").innerHTML = decreasestationCode;
		//$('#decreasestationCode').comboSelect();
	});
}
// Internet Explorer
function OnPropChanged(event) {
	if(event.propertyName.toLowerCase() == "value") {
		var keyword = $('#decreasestationCode_select').val()
		common.getallstation({}, function(data) {
			var list = []
			var arr = []
			for(var i = 0; i < data.length; i++) {
				list.push(data[i].PositionName)
			}
			for(var i = 0; i < list.length; i++) {
				if(list[i].indexOf(keyword) >= 0) {
					arr.push(list[i]);
				}
			}
			if(keyword == '') {
				var str = {
					list: data
				};
			} else {
				var data2 = [];
				for(var h = 0; h < arr.length; h++) {
					for(var j = 0; j < data.length; j++) {
						if(data[j].PositionName == arr[h]) {
							data2.push(data[j])
						}
					}
				}
				var str = {
					list: data2
				};
			}
			var decreasestationCode = template("decreasestationCodemodal", str);
			document.getElementById("decreasestationCode").innerHTML = decreasestationCode;
			//$('#decreasestationCode').comboSelect();
		});
	}
}

function decchange() {
	alert('hehe')
	var keyword = $('#decreasestationCode_select').val()
}

var _detailIdspan = '';
$('#myModal').on('show.bs.modal', function(e) {
	
	/*获取扣分表信息*/
	var invoker = $(e.relatedTarget);
	var id = "";
	if(invoker[0].childNodes[1]) {
		var title = invoker[0].title;
		id = invoker[0].childNodes[1].innerHTML;
		var text = title.split('-')[1];
	    _detailIdspan = $('.detailIdspan').text();
		var areatitle = title.split('-')[0];
		var count=0;
		if(text == "详情") {
//			$('#decreasestationCode_select').hide()
			$('.modal-body_Detail').show()
			$("#myModalLabel").html("扣分详情表");
			$("#checkperson").hide();
			$(".modal-footer").hide();
			$("#totalscoretr").show();
			$('#decreasestationCityCode').attr('disabled','disabled');
			$('#decreasestationlocatedCountyCode').attr('disabled','disabled');
			$('#decreasestationstationNameModelCode').attr('disabled','disabled');
			$('#decreasestationbasedate').attr('disabled','disabled');
			$('#decreasestationbasedate').val();
			$('.resettd').each(function(i,v){ 
			   count+=Number($(v).next('td').children('input').val());
			});
		  $('#totalscore').text(count);
//			document.getElementById('decreasestationCode').disabled = "disabled"
		} else {
			$('#decreasestationCode_select').show()
			$('.modal-body_Detail').hide()
			$("#myModalLabel").html("扣分修改");
			$("#checkperson").show();
			$(".modal-footer").show();
			$("#totalscoretr").hide();
			$('#decreasestationCityCode').removeAttr('disabled');
			$('#decreasestationlocatedCountyCode').removeAttr('disabled');
			$('#decreasestationstationNameModelCode').removeAttr('disabled');
			$('#decreasestationbasedate').removeAttr('disabled');
			$('.resettd').each(function(i,v){ 
			   count+=Number($(v).next('td').children('input').val());
			});
			$('#totalscore').text(count);
		}
		var params = {
			detailId: id
		};

		$("#updateCode").val(id);
		$("#checkestation .combo-select ").hide();
		common.findByDetail(params, function(data) {
			
			$('#dateinfo').val(data.checkTime.substr(0, 19));
			$('#othertext').val(data.detail.mark);
			setTimeout(function(){
				getunitStationCode();
			},10);
			
			for(var i = 0; i < data.deductList.length; i++) {
				var num = parseInt(i) + 1-1;
				$("#totalscore").html(data.totalDeduct);
				
				
				if(text == "详情") {
					$("#score" + num).hide();
					$("#score11" + num).show();
					$("#score11" + num).html(data.deductList[i].score);
					$("#mark" + num).hide();
					$("#mark11" + num).show();
					$("#mark11" + num).html(data.deductList[i].mark);
					
					$("#score" + num).attr("disabled", "disabled");
					$("#mark" + num).attr("disabled", "disabled");
					$('#othertext').attr("disabled", "disabled");
					for(var k=0;k<5;k++){
						var check = "check"+k;
						$("#check" + Number(k)).attr("disabled", "disabled");
						if(data.deductList[k].check==1){
							$("#" + check).checked = true;
						}else{
							$("#" + check).checked = false;
						}
				  }
				} else {
					for(var k=0;k<5;k++){
						var check = "check"+k;
						if(data.deductList[k].check==1){
							$("#" + check).checked = true;
						}else{
							$("#" + check).checked = false;
						}
				    }
					$("#score11" + num).hide()
					$("#score" + num).show();
					$("#score" + num).val(data.deductList[i].score);
					$("#mark11" + num).hide();
					$("#mark" + num).show();
					$("#mark" + num).val(data.deductList[i].mark);
					$("#score" + num).removeAttr("disabled");
					$("#mark" + num).removeAttr("disabled");
					$('#othertext').removeAttr("disabled");
				}
			}
			if(text == "详情") {
				var cityarr=[{
					"CityCode":data.detail.CityId,
					"CityName":data.detail.Area
					}];
					var _cityarr = {
						list:cityarr
					};
					var districtarr = [{
						"CountyId":data.detail.CountyId,
						"CountyName":data.detail.CountyName
					}];
					var _districtarr = {
						list:districtarr
					};
					var sitearr = [{
						"StationCode":data.detail.stationCode,
						"StationName":data.detail.stationName
					}];
					var _sitearr = {
						list:sitearr
					};
					var locatedCityModel = template("locatedCityModel", _cityarr);
					document.getElementById("decreasestationCityCode").innerHTML = locatedCityModel;
					var locatedCountyModel = template("locatedCountyModel", _districtarr);
					document.getElementById("decreasestationlocatedCountyCode").innerHTML = locatedCountyModel;
					var stationameModel = template("stationameModel", _sitearr);
					document.getElementById("decreasestationstationNameModelCode").innerHTML = stationameModel;
				for(var j = 0; j < data.detailMap.length; j++) {
					for(var key in data.detailMap[j]) {
						var obj = {};
						obj[key] = data.detailMap[j][key];
						var arr = key.split('l')
//						$("#detail" + arr[1]).hide()
//						$("#detail11" + arr[1]).show()
						$("#detail" + arr[1]).html(data.detailMap[j][key]);
						$("#" + key).attr("disabled", "disabled")
					}
				}
			} else {
					  common.citylevel({}, function(data) {
						var _cityarr = {
										list:data
								};
							var locatedCityModel = template("locatedCityModel", _cityarr);
							document.getElementById("decreasestationCityCode").innerHTML = locatedCityModel;	
						});
						var _parm = 'CityCode='+data.detail.cityCode;
						$("#decreasestationCityCode").find('option[value='+data.detail.CityId+']').attr("selected",true);
						getdistrictlevelData(_parm,2)
						setTimeout(function(){
							$("#decreasestationlocatedCountyCode").find('option[value='+data.detail.CountyId+']').attr("selected",true);
						},50);
						var siteparm='CountyId='+data.detail.CountyId
						getsitelevelData(siteparm,2);
						setTimeout(function(){
							$("#decreasestationstationNameModelCode").find('option[value='+data.detail.stationCode+']').attr("selected",true);
						},80);
						
						$('#yunweirenyuan').val(data.detail.runPerson);
				for(var j = 0; j < data.detailMap.length; j++) {
					for(var key in data.detailMap[j]) {
						var obj = {};
						obj[key] = data.detailMap[j][key];
						var arr = key.split('l')
						$("#detail" + arr[1]).show()
						$("#detail11" + arr[1]).hide()
						$("#" + key).val(data.detailMap[j][key]);
						$("#" + key).html(data.detailMap[j][key]);
						$("#" + key).removeAttr("disabled");
					}
				}
			}

		})
	} else {
//		$('#decreasestationCode_select').show()
		$('.modal-body_Detail').hide()
//		document.getElementById('decreasestationCode').disabled = false
		$("#myModalLabel").html("扣分新增表");
//		var oDate = new Date(); //实例一个时间对象；
//		var year = oDate.getFullYear(); //获取系统的年；
//		var month = oDate.getMonth() + 1; //获取系统月份，由于月份是<a href="https://www.baidu.com/s?wd=%E4%BB%8E0%E5%BC%80%E5%A7%8B&tn=44039180_cpr&fenlei=mv6quAkxTZn0IZRqIHckPjm4nH00T1YLrjDvmhPWmHRLnHnvmWb30ZwV5Hcvrjm3rH6sPfKWUMw85HfYnjn4nH6sgvPsT6KdThsqpZwYTjCEQLGCpyw9Uz4Bmy-bIi4WUvYETgN-TLwGUv3EnHRsrH6krHcsnWbLrjf1PWb3n0" target="_blank" class="baidu-highlight">从0开始</a>计算，所以要加1
//		var date = oDate.getDate(); // 获取系统日，
//		var hour = oDate.getHours(); //获取系统时，
//		var minute = oDate.getMinutes(); //分
//		var second = oDate.getSeconds(); //秒
//		var time = [year, month, date, hour, minute, second]
//		for(var i = 0; i < time.length; i++) {
//			if(parseInt(time[i]) < 10) {
//				time[i] = '0' + time[i]
//			}
//		}
//		var times = time[0] + '-' + time[1] + '-' + time[2] + ' ' + time[3] + ':' + time[4] + ':' + time[5]
//		$('#dateinfo').val(times)
		for(var i = 0; i < 95; i++) {
			$("#detail" + i).show();
			$("#detail11" + i).hide();
			$("#detail" + i).removeAttr("disabled");
			$("#detail" + i).val('');
			$("#detail" + i).html('');
		}
		for(var j = 0; j < 28; j++) {
			$("#score" + j).show();
			$("#score11" + j).hide();
			$("#score" + j).removeAttr("disabled");
			$("#score" + j).val("");
			$("#mark" + j).show();
			$("#mark11" + j).hide();
			$("#mark" + j).removeAttr("disabled");
			$("#mark" + j).val("");
		}
		$("#checkestation").show();
		$("#checkperson").show();
		$(".modal-footer").show();
		$("#totalscoretr").show();
//		$("#stationInfo").html('');
//		$("#updateCode").val('0');
//		$("#checkestation .combo-select ").show();
	}

});

function deletescoreTable(id) {
	var params = {
		"detailId": id
	};
	common.deletedeductscore(params, function(data) {
		if(data.result == "true") {
			$('.alert_text').html('删除成功')
			$('.about_alert').css('visibility', 'visible')
			// alert("删除成功");
			//质控检查扣分表
			var dataparams = getsearchParams();
			if(sessionStorage.getItem("talktype") == "status=1" || sessionStorage.getItem("talktype") == "status=2") {
				var params = sessionStorage.getItem("params");
				common.losepoint(params, function(data) {
					var str = {
						list: data[loseindex].dataResult,
						date: data[loseindex].checkTime
					};
					var daytest = template('monthdeduct', str);
					document.getElementById('losepointDetail').innerHTML = daytest;
				})
				common.losepoint(params, function(data) {
					var str = {
						list: data
					};
					var daytest = template('losepoint', str);
					document.getElementById('jianchakoufenbiao').innerHTML = daytest;
				})
			} else {
				common.monthdeductConditions(dataparams, function(data) {
					var month = "";
					if(data.length > 0) {
						month = parseInt(data[0].checkTime.split('-')[1]);
					}
					var str = {
						title: month,
						list: data
					};
					var monthdeduct = template('monthdeduct', str);
					document.getElementById('jianchakoufenbiao').innerHTML = monthdeduct;
				});
			}
		}
	})
}


 /**
	 * Created by duhong on 2018/3/24
	 */
//市级
function getcitylevelData(id) {
	if(id==1){
		$('.modal-body_Detail').css('display','none');
		$('#myModalLabeljiben').text('基本保障新增表');
		$('#checkperson1').attr('disabled','disabled');
		$('#checkperson2').val('');
		 var cookies = document.cookie.split("username=")[1].split(";")[0];
		 $('#checkperson1').val(decodeURI(cookies));
	}
	if(id==2){
		var cout = 0;
		var cookies = document.cookie.split("username=")[1].split(";")[0];
        $('#rummagername').val(decodeURI(cookies));
        $('.resettd').each(function(i,v){ 
		   cout +=Number($(v).prev('td').text());
		
		});
		$('#totalsum').text(cout)
	}
	common.citylevel({}, function(data) {
		data.unshift({
			"CityCode":0,
			"CityName":"请选择"
		});
		var str = {
			list: data
		};
		var districtarr = [{
			"CountyId":0,
			"CountyName":"请选择"
		}];
		var _districtarr = {
			list:districtarr
		};
		var sitearr = [{
			"StationCode":0,
			"StationName":"请选择"
		}];
		var _sitearr={
			list:sitearr
		};
		if(id==1){
			gettableData();
			var locatedCityModel = template("locatedCityModel", str);
			document.getElementById("locatedCityCode").innerHTML = locatedCityModel;
			var locatedCountyModel = template("locatedCountyModel", _districtarr);
			document.getElementById("locatedCountyCode").innerHTML = locatedCountyModel;
			var stationameModel = template("stationameModel", _sitearr);
			document.getElementById("stationNameModelCode").innerHTML = stationameModel;
			document.getElementById("baseunit").value="";
			$('#locatedCityCode').removeAttr('disabled');
			$('#locatedCountyCode').removeAttr('disabled');
			$('#stationNameModelCode').removeAttr('disabled');
		}else{
			var locatedCityModel = template("locatedCityModel", str);
			document.getElementById("decreasestationCityCode").innerHTML = locatedCityModel;
			var locatedCountyModel = template("locatedCountyModel", _districtarr);
			document.getElementById("decreasestationlocatedCountyCode").innerHTML = locatedCountyModel;
			var stationameModel = template("stationameModel", _sitearr);
			document.getElementById("decreasestationstationNameModelCode").innerHTML = stationameModel;
			$('#decreasestationCityCode').removeAttr('disabled');
			$('#decreasestationlocatedCountyCode').removeAttr('disabled');
			$('#decreasestationstationNameModelCode').removeAttr('disabled');
			$('#othertext').removeAttr('disabled');
		}
	
	});
}

var districtlevelData =[];
function selectCity(id){
	if(id==1){
		var _parm = $('#locatedCityCode').val();
	}else{
		var _parm = $('#decreasestationCityCode').val();
	}
	
	if(_parm==0) return;
	_parm = 'CityCode='+_parm;
	getdistrictlevelData(_parm,id);
	
}
//区县
function getdistrictlevelData(parm,id) {
	common.districtlevel(parm, function(data) {
		if(id==1){
			districtlevelData = data;
		}else{
			districtlevelData = [];
		}
		
		data.unshift({
			"CountyId":0,
			"CountyName":"请选择"
		});
		var str = {
			list: data
		};
		if(id==1){
			var locatedCountyModel = template("locatedCountyModel", str);
		    document.getElementById("locatedCountyCode").innerHTML = locatedCountyModel;	
		}else{
			var locatedCountyModel = template("locatedCountyModel", str);
			document.getElementById("decreasestationlocatedCountyCode").innerHTML = locatedCountyModel;
		}
			
	})
}

function selectdistrict(id){
	if(id==1){
		var _parm = $('#locatedCountyCode').val();
	}else{
		var _parm = $('#decreasestationlocatedCountyCode').val();
	}
	if(_parm==0) return;
	_parm = 'CountyId='+_parm;
	var _str = '';
	getsitelevelData(_parm,id);
	
	if(id==1){
		districtlevelData.forEach(function(v){
		if(v.CountyId !==0){
			if(v.CountyId==$('#locatedCountyCode').val()){
				_str=v.CountyName;
		 }
		}		
	 });
	  $("#baseunit").val(_str);	
	}
	
}
//站点
function getsitelevelData(parm,id) {
	common.sitelevel(parm, function(data) {
		data.unshift({
			"StationCode":0,
			"StationName":"请选择"
		});
		var str = {
			list: data
		};
		if(id==1){
			var stationameModel = template("stationameModel", str);
		     document.getElementById("stationNameModelCode").innerHTML = stationameModel;
		}else{
			var stationameModel = template("stationameModel", str);
		    document.getElementById("decreasestationstationNameModelCode").innerHTML = stationameModel;
		}
		
	})
}
var tabledataArr = [];
function gettableData(){
	document.getElementById("baseTable").innerHTML = "";
	 $.ajax({
            url: "assets/js/data.json",
            type: 'get',
            dataType: 'json',
            success: function (data) {  
            	tabledataArr = data;
            	var str = {
					list: data.list
				};
                var baseTableModel = template("baseTableModel", str);
		        document.getElementById("baseTable").innerHTML = baseTableModel;
            },
            complete: function (XMLHttpRequest, status) {
                if (XMLHttpRequest.status == 403) {
			
                }
                if (status == 'error') {

                }
                if (status == 'timeout') {

                }
            }

        })
}

/*基本保障新增保存数据*/
var baseDetailId='';
function savebasiccheck(){
	var radionName=['debris','water','guarantee','time','contaminated','stop','select','space','knock','nowater','damage','height','noarrears',
	'nobreakage','repair','communicate','Withintime','ground'];
	var parm = {
		"StationCode":$('#stationNameModelCode').val(),
		"TimePoint":$('#basedate').val(),
		"CheckTime1":$('#checkbasedate').val(),
		"CheckTime2":$('#departbasedate').val(),
		"Person1":$('#checkperson1').val(),
		"Person2":$('#checkperson2').val(),
		"Others":$('#Othersquestion').val(),
		"DetailId":baseDetailId
	};
	for(var i=0;i<radionName.length;i++){
		parm['Status'+(i+1)] = $('input[name='+radionName[i]+']:checked').val();
		parm['DetailMark'+(i+1)] = $('input[name="remarkquestion"]').eq(i).val();
	}
	var rununit = sessionStorage.getItem("rununit") + '&';
	var startyear = $("#startyear").val();
	var startmonth = $("#startmonth").val();
	var endyear = $("#endyear").val();
	var endmonth = $("#endmonth").val();
	var start = "start" + '=' + startyear + '-' + startmonth+'-01' ;
	var end = "end" + '=' + endyear + '-' + endmonth+'-01';
	var city='',citystationCodes='';
	
   for(var i = 0; i < $(".citylist").length; i++) {
			if($($(".citylist")[i]).is(':checked')) {
				city += "cityIds=" + $(".citylist")[i].value + '&';
			}
		}
   for(var i = 0; i < $(".regionlist").length; i++) {
			if($($(".regionlist")[i]).is(':checked')) {
				citystationCodes += "stationCodes=" + $(".regionlist")[i].value + '&';
			}
		}
   var _param = "status="+$('input[name="choose"]:checked').val()+"&"+rununit+city+citystationCodes+start+"&"+end;
   if($('#locatedCityCode').val()==0){
   			$('.alert_text').html('请选择站点所在地市级');
			$('.about_alert').css('visibility', 'visible');
			return ;
   }
   if($('#locatedCountyCode').val()==0){
   			$('.alert_text').html('请选择站点所在地县级');
			$('.about_alert').css('visibility', 'visible');
			return ;
   }
   if($('#stationNameModelCode').val()==0){
   			$('.alert_text').html('请选择 监测子站名称');
			$('.about_alert').css('visibility', 'visible');
			return ;
   }
   if($('#checkperson1').val().replace(/\s/g, "").length==0){
   		    $('.alert_text').html('请填写检查人员 ');
			$('.about_alert').css('visibility', 'visible');
			return ;
   }
   if($('#checkperson2').val().replace(/\s/g, "").length==0){
   		    $('.alert_text').html('地方环保部门人员 ');
			$('.about_alert').css('visibility', 'visible');
			return ;
   }
   var  _basedate= timeCheck($('#basedate').val());
   var  _checkbasedate= timeCheck($('#checkbasedate').val());
   var  _departbasedate= timeCheck($('#departbasedate').val());
      if(!_basedate) return;
      if(!_checkbasedate) return;
      if(!_departbasedate) return;
      
   common.savebasiccheck(parm, function(data) {
		if(data.result=="true"){
			$('.alert_text').html('提交成功')
			$('.about_alert').css('visibility', 'visible')
			// alert("提交成功");
			$(".btn-default").click();
			common.basicsecurity(_param, function(data) {
			loading(false, 'loader4')
			var str = {
				list: data
			};
			var daytest = template('basicsecurity', str);
		    document.getElementById('jibenbaozhang').innerHTML = daytest;
		    var params = sessionStorage.getItem("params");
			common.basicsecurity(params, function(data) {
				var str = {
					list: data[baseIndex].dataResult,
					date: data[baseIndex].checkTime
				};
			console.log('add=>',str)
				var daytest = template('mybasicdetailModal', str);
				document.getElementById('mybaseDetail').innerHTML = daytest;
			});
		 });
		}
	});
}


/*基本保障详情表*/
var baseIndex = 0;
function mybasicDetail(index){
	baseIndex = index;
	var params = sessionStorage.getItem("params");
	common.basicsecurity(params, function(data) {
		var str = {
			list: data[index].dataResult,
			date: data[index].checkTime
		};
		console.log(str)
		var daytest = template('mybasicdetailModal', str);
		document.getElementById('mybaseDetail').innerHTML = daytest;
	})
}

/*基本保障详情表删除*/
function deletebaseTable(id){
	var params = {
		"detailId": id
	};
	common.mybaseDetaildelete(params, function(data) {
		if(data.result == "true") {
			$('.alert_text').html('删除成功')
			$('.about_alert').css('visibility', 'visible');
			var params = sessionStorage.getItem("params");
			common.basicsecurity(params, function(data) {
				var str = {
					list: data[baseIndex].dataResult,
					date: data[baseIndex].checkTime
				};
				var daytest = template('mybasicdetailModal', str);
				document.getElementById('mybaseDetail').innerHTML = daytest;
			});
		 }
		});
	  
}
/*基本保障详情表详情 修改*/
function getbasedetail(index,id){
	if(index==1){
		$('.modal-body_Detail').css('display','block');
		$('#myModalLabeljiben').text('基本保障详情表');
		$('.addtijiao').css('display','none');
		$('#checkperson2').val('');
		$('#checkperson1').attr('disabled','disabled');
		var cookies = document.cookie.split("username=")[1].split(";")[0];
        $('#checkperson1').val(decodeURI(cookies));
	}else{
		$('.modal-body_Detail').css('display','none');
		$('.addtijiao').css('display','inline-block');
	}
	if(index==0){
		$('#myModalLabeljiben').text('基本保障新增表');
		$('.modal-body_Detail').css('display','none');
	}
	if(index==2){
		$('#myModalLabeljiben').text('基本保障修改');
		$('.modal-body_Detail').css('display','none');
	}
	baseDetailId = id;
	$(".modal-backdrop").hide();
	var params = "detailId="+id;
	common.mybaseDetail(params, function(data) {
       getnewbasedetails(index,data);
	});
}

//通过站点得到运行单位
function getunitStationCode(){
	var StationCode = $('#decreasestationstationNameModelCode').val();
	if(StationCode==0) return;
	var _param = "StationCode="+StationCode;
	common.getRunMsgByStationCode(_param,function(data){
		if(data.runName==''){
			$('#decreasestationbaseunit').val('—');
		}else{
			$('#decreasestationbaseunit').val(data.runName);
		}
		
	})
}
//表格数据重新渲染
function getnewbasedetails(index,newdata){
	
	if(index!==0){
		gettableData();
			var radionName=['debris','water','guarantee','time','contaminated','stop','select','space','knock','nowater','damage','height','noarrears',
	'nobreakage','repair','communicate','Withintime','ground'];
	setTimeout(function(){
		for(var i=0;i<radionName.length;i++){
			for(var j=0;j<$('input[name='+radionName[i]+']').length;j++){
				if(newdata.deductList[i].Status==($('input[name='+radionName[i]+']').eq(j).val())){
			   	   $('input[name='+radionName[i]+']').eq(j).attr('checked',true);
			   }
			}
		   $('input[name="remarkquestion"]').eq(i).val(newdata.deductList[i].DetailMark);
		}
		 $('#Othersquestion').val(newdata.stationmap.Others);
		 $('#basedate').value=newdata.stationmap.TimePoint;
		 $('#baseunit').val(newdata.stationmap.CountyName);
		 $("#baseTable").find("textarea").innerHTML = newdata.stationmap.Others;
		 $("#checkperson1").value=newdata.stationmap.CheckTime1;
		 $("#checkperson2").value=newdata.stationmap.CheckTime2;
		 $("#checkbasedate").value=newdata.stationmap.Person1;
		 $("#departbasedate").value=newdata.stationmap.Person2;
	},50);
		
	}
	switch(index){
		case 0:
		getcitylevelData(1);
		break;
		case 1:
		var cityarr=[{
			"CityCode":newdata.stationmap.CityId,
			"CityName":newdata.stationmap.Area
		}];
		var _cityarr = {
			list:cityarr
		};
		var districtarr = [{
			"CountyId":newdata.stationmap.CountyId,
			"CountyName":newdata.stationmap.CountyName
		}];
		var _districtarr = {
			list:districtarr
		};
		var sitearr = [{
			"StationCode":newdata.stationmap.StationCode,
			"StationName":newdata.stationmap.StationName
		}];
		var _sitearr = {
			list:sitearr
		};
		var locatedCityModel = template("locatedCityModel", _cityarr);
		document.getElementById("locatedCityCode").innerHTML = locatedCityModel;
		var locatedCountyModel = template("locatedCountyModel", _districtarr);
		document.getElementById("locatedCountyCode").innerHTML = locatedCountyModel;
		var stationameModel = template("stationameModel", _sitearr);
		document.getElementById("stationNameModelCode").innerHTML = stationameModel;
		 $('#locatedCityCode').attr("disabled","disabled");
		 $('#locatedCountyCode').attr("disabled","disabled");
		 $('#stationNameModelCode').attr("disabled","disabled");
		 setTimeout(function(){
		 	 $("#baseTable").find('input[name="remarkquestion"]').attr("disabled","disabled");
			 $("#baseTable").find('input[type="radio"]').attr("disabled","disabled");
			 $("#baseTable").find("textarea").attr("disabled","disabled");
		 },200);
		 $("#checkperson1").attr("disabled","disabled");
		 $("#checkperson2").attr("disabled","disabled");
		 $("#checkbasedate").attr("disabled","disabled");
		 $("#departbasedate").attr("disabled","disabled");
		break;
		case 2:
		common.citylevel({}, function(data) {
				var _cityarr = {
						list:data
				};
				var locatedCityModel = template("locatedCityModel", _cityarr);
				document.getElementById("locatedCityCode").innerHTML = locatedCityModel;
				$("#locatedCityCode").find('option[value='+newdata.stationmap.cityCode+']').attr("selected",true);
		});
		var _parm = 'CityCode='+newdata.stationmap.cityCode;
		getdistrictlevelData(_parm,1)
		setTimeout(function(){
			$("#locatedCountyCode").find('option[value='+newdata.stationmap.CountyId+']').attr("selected",true);
		},50)
		console.log(newdata.stationmap.CountyId)
		var siteparm='CountyId='+newdata.stationmap.CountyId
		getsitelevelData(siteparm,1);
		setTimeout(function(){
			$("#stationNameModelCode").find('option[value='+newdata.stationmap.StationCode+']').attr("selected",true);
		},80);
		

		 $('#locatedCityCode').removeAttr("disabled");
		 $('#locatedCountyCode').removeAttr("disabled");
		 $('#stationNameModelCode').removeAttr("disabled");
		 setTimeout(function(){
		 	 $("#baseTable").find('input[name="remarkquestion"]').removeAttr("disabled");
			 $("#baseTable").find('input[type="radio"]').removeAttr("disabled");
			 $("#baseTable").find("textarea").removeAttr("disabled");
		 },200);
		 $("#checkperson1").removeAttr("disabled");
		 $("#checkperson2").removeAttr("disabled");
		 $("#checkbasedate").removeAttr("disabled");
		 $("#departbasedate").removeAttr("disabled");
		default:
		break;
		
	}	
}
/******************检验时间是否合法*******************/
function timeCheck(dates){
	var flag = true;
	var month = parseInt(((dates.split(' ')[0]).split('-'))[1]);
	var year = parseInt(((dates.split(' ')[0]).split('-'))[0]);
	var tomonth = (new Date().getMonth() + 1);
	var toyear = (new Date().getFullYear());
	var today = (new Date().getDate());
	if(year != toyear) {
		$('.alert_text').html('你只能选择当前年份');
		$('.about_alert').css('visibility', 'visible');
		flag = false;
		return
	}
	if(month != tomonth) {
		$('.alert_text').html('你只能选择当前月份');
		$('.about_alert').css('visibility', 'visible');
		flag = false;
		return
	}
	
	return flag;
}

function getCookie(Name) 
{ 
    var search = Name + "=" 
    if(document.cookie.length > 0) 
    { 
        offset = document.cookie.indexOf(search) 
        if(offset != -1) 
        { 
            offset += search.length 
            end = document.cookie.indexOf(";", offset) 
            if(end == -1) end = document.cookie.length 
            return unescape(document.cookie.substring(offset, end)) 
        } 
        else return "" 
    } 
} 