var isPicInfo = 0;
var creatDateST = (new Date().getFullYear()) + "-" + (new Date().getMonth() + 1) + "-" + (new Date().getDate());
var creatDateED = creatDateST;
var provinceCode = 99999;
$(document).ready(function() {
	window.url = pageConfig.ajaxUrl;
	/*区域上报数据与区域颜色切换*/
	$('.area-tab').on("click", "li", function(index) {
		$(this).addClass('li-active');
		$(this).siblings('li').removeClass('li-active');
		if($(this).index()==0){
			$(".first-tab").show();
			$(".second-tab").hide();
		}else{
			$(".first-tab").hide();
			$(".second-tab").show();
			var time_select = $(".time-select").val() + " 00:00:00";
		    fetchDataSecond(time_select);
		}

	});
	$('#rangeDatePicker').datepicker({
		language: 'zh-CN', //日历显示为中文
		autoclose: true,
		minView: "month",
		format: "yyyy-mm-dd"

	});
	//获取当前时间
	var nowTime = new Date(new Date()).format("yyyy-MM-dd");
	$(".time-select").val(nowTime);
	var times = (new Date($(".time-select").val())).getTime();

	var level = [{
		Id: 0,
		Name: "请选择"
	}, {
		Id: 1,
		Name: "优"
	}, {
		Id: 2,
		Name: "优-良"
	}, {
		Id: 3,
		Name: "良"
	}, {
		Id: 4,
		Name: "良-轻"
	}, {
		Id: 5,
		Name: "轻"
	}, {
		Id: 6,
		Name: "轻-中"
	}, {
		Id: 7,
		Name: "中"
	}, {
		Id: 8,
		Name: "中-重"
	}, {
		Id: 9,
		Name: "重"
	}, {
		Id: 10,
		Name: "重-严重"
	}, {
		Id: 11,
		Name: "严重"
	}]

	//日期改变事件
	$("#rangeDatePicker").change(function() {
		var time_select = $(".time-select").val() + " 00:00:00";
		fetchDataSecond(time_select);
	});
   var updateflag=1;
	/*查询数据*/
	function fetchDataSecond(time_select, now_time) {
		var creatDate = $(".time-select").val() + " 00:00:00";
		//页面数据清空
		var parentObj = $("#areaTBody");
		parentObj.empty();
		GetAjax(window.url.getAirReportDrow_do + "?creatDate=" + creatDate,undefined,$(".operate-wrapper")).done(function(data) {
			//数据源为空时，显示提示信息
	
			if(!data || !data.Result || data.Result.length <= 0) {
				var noDataObj = $("#noDataTrTemplate").clone();
				noDataObj.removeAttr("id");
				noDataObj.show();
				parentObj.html(noDataObj);
			}
			var creatName_fill = "-";
			//数据列表
			var list = data.Result;

			//序号索引
			var index = 0;

			//省循环
			$.each(list, function(listIndex, listItem) {

				//区域循环
				var areaList = listItem.provinceZones;
				$.each(areaList, function(areaIndex, areaItem) {
					var trObj = $("#areaTrTemplate").clone()
					trObj.removeAttr("id");
					trObj.show();
                    //序号
					index++;
					trObj.find("td:eq(0)").text(index);

					//省
					trObj.find("td:eq(1)").attr("rowSpan", areaList.length).text(listItem.provinceName);

					//区域
					trObj.find("td:eq(2)").text(areaItem.zoneName);
					trObj.find("td:eq(0)").attr('data-id', areaItem.zid); //写
					//天
					var dayList = areaItem.airQualityLvlForecastStatisticsList;
					
					$.each(dayList, function(dayIndex, dayItem) {
						updateflag=2;
						creatName_fill = dayItem.creatName;
						//获取时间年月日
						var createDate = new Date(dayItem.creatDate.substring(0, 10));
						var setDate = new Date(dayItem.forecastDate.substring(0, 10));

						//天数差
						var dayNum = (setDate.getTime() - createDate.getTime()) / 1000 / 60 / 60 / 24;

						var tdIndex = 2 + dayNum;
						dayItem.levelCodenew='';
						switch (dayItem.levelCode){
							case "0":
							      dayItem.levelCodenew=0;
								break;
							case "1":
							      dayItem.levelCodenew=1;
								break;
							case "2":
							      dayItem.levelCodenew=2;
								break;
							case "3":
							      dayItem.levelCodenew=2;
								break;
							case "4":
							      dayItem.levelCodenew=3;
								break;
							case "5":
							      dayItem.levelCodenew=3;
								break;
							case "6":
							      dayItem.levelCodenew=4;
								break;
							case "7":
							      dayItem.levelCodenew=4;
								break;
							case "8":
							      dayItem.levelCodenew=5;
								break;
							case "9":
							      dayItem.levelCodenew=5;
								break;
							case "10":
							      dayItem.levelCodenew=6;
								break;
							case "11":
							      dayItem.levelCodenew=6;
								break;
							default:
								break;
						}
						
                        var _levelColor = util.levelColor(1,dayItem.levelCodenew.toString());
					    var _background = _levelColor.color;
					    trObj.find("td:eq(" + tdIndex + ")").css({"backgroundColor":_background});
					    trObj.find("td:eq(" + tdIndex + ")").find("select").css({"backgroundColor":_background});
						trObj.find("td:eq(" + tdIndex + ")").find("select").val(dayItem.levelCode);
					});

					//索引大于0时删除省份
					if(areaIndex > 0) {
						trObj.find("td:eq(1)").remove();
					}

					parentObj.append(trObj);
				});
			});

			$("#table_name").html(creatName_fill);
		})

	}
	fetchDataSecond();
	$(".save-btn").on("click", function() {

		//获取当前时间
		var courrent_date = new Date().Format("yyyy-MM-dd HH:mm:ss");

		var hours = new Date().getHours();

		//用户选择的时间
		var time_select = $(".time-select").val();
		var now_time = new Date().Format("yyyy-MM-dd");
		if(time_select != now_time) {
			layerAlert.autoclose("请选择当前日期填报且截至时间15:00");
			return;
		}
		if(time_select == now_time && hours >= 15) {
			layerAlert.autoclose("每日的填报截止时间为15:00");
			return;
		}
		var input_name=$("#input_name").val();
		if(updateflag==2 && input_name==''){
			layerAlert.autoclose("当日数据修改后，填表人必填");
			return;
		}
		var areaTBod_arr = [];
		var levercodeArr = []
		$('#areaTBody tr').map(function(v) {
			
			$.each($(this).children("td"), function(i,value) {
				
				if($(this).children('select').length != 0) {
					levercodeArr.push({
						"levelCode": $(this).children('select').val()
					})
				}
					if($(value).hasClass('province')){
			
				  areaTBod_arr.push({
						"CityName":getcityname($(value).html()),
						"AreaName":$(value).html(),
						"OneDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(0).val()), level),'S'),
						"OneDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(0).val()), level),'N'),
						"TwoDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(1).val()), level),'S'),
						"TwoDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(1).val()), level),'N'),
						"ThreeDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(2).val()), level),'S'),
						"ThreeDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(2).val()), level),'N'),
						"FourDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(3).val()), level),'S'),
						 "FourDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(3).val()), level),'N'),
						 "FiveDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(4).val()), level),'S'),
						 "FiveDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(4).val()), level),'N'),
						 "SixDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(5).val()), level),'S'),
						 "SixDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(5).val()), level),'N'),
						 "SevenDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(6).val()), level),'S'),
						 "SevenDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(6).val()), level),'N'),
						 "EightDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(7).val()), level),'S'),
						 "EightDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(7).val()), level),'N'),
						 "NineDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(8).val()), level),'S'),
						 "NineDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(8).val()), level),'N'),
						 "TenDay_S":gets(numberToText(Number($(this).siblings('td').children('select').eq(9).val()), level),'S'),
						 "TenDay_N":gets(numberToText(Number($(this).siblings('td').children('select').eq(9).val()), level),'N')
					});
			    }
		});

		})
		var newcode = [];
		newcode = chunk(levercodeArr, 10);
		
		var none_slect = false;
		var forecastInfo_arr=[];

		$.each(newcode, function(index, value) {			
			$.each(value, function(_index, _value) {
				if(_index < 5) {
					var levelCode = _value.levelCode;
					if(levelCode == 0) {
						none_slect = true;

					}
				}
			});
		});

		if(none_slect) {
			layerAlert.autoclose("填报至少需完成前五天的填报");
			return
		}
		var creatName = $("#input_name").val();
		if(creatName == '') {
			creatName = "-";
		} else {
			creatName = creatName
		}

		var ayy = [];
		$('#areaTBody tr').map(function(v) {
			var index = 3;
			$.each($(this).children("td"), function(i) {

				if($(this).children('select').length != 0) {

					ayy.push({
						"levelCode": $(this).children('select').val(),
						"forecastDate": addByTransDate(now_time, index - 2) + " 00:00:00",
						"zoneId": $(this).parent().children("td").eq(0).attr("data-id")
					})
					index++;
				}

			});

		})

		var listBeans = [];

		$.each(ayy, function(index, value) {

			listBeans.push({
				"creatDate": courrent_date,
				"creatName": creatName,
				"forecastDate": value.forecastDate,
				"levelCode": value.levelCode,
				"levelName": numberToText(Number(value.levelCode), level),
				"zoneId": value.zoneId
			})
		});
		var date=courrent_date.replace(" ","T");
        var country_pram={
        	"loginName": "900070",
			"password": "dqs@123",
			"forecastInfo": areaTBod_arr,
			"dataType":"AQI10",
			 "date":date,
			 "CityCal":""
			}
        console.log(country_pram);
        PostAjax(window.url.sichuanUpdate, JSON.stringify(country_pram),undefined,$(".operate-wrapper")).done(function(res) {
			if(res.UploadAreaExcelDataResult == "100[@数据上传成功!]") {
				layerAlert.autoclose("保存操作成功");
				var time_select = $(".time-select").val() + " 00:00:00";
				$("#input_name").val('');
				fetchDataSecond(time_select);
			}
		});
		PostAjax(window.url.updateAirReportDrow, JSON.stringify(listBeans),undefined,$(".operate-wrapper")).done(function(res) {
			if(res.success == true) {
				layerAlert.autoclose("保存操作成功");
				var time_select = $(".time-select").val() + " 00:00:00";
				$("#input_name").val('');
				fetchDataSecond(time_select);
			}
		});
	})
	 $(".pop-one").hover(function(){
    	$(".poptips-one").show();
    },function(){
    	$(".poptips-one").hide();
    });
     $(".pop-two").hover(function(){
    	$(".poptips-two").show();
    },function(){
    	//$(".poptips-two").hide();
    });
    $(".pop-three").hover(function(){
    	$(".poptips-three").show();
    },function(){
    	$(".poptips-three").hide();
    });
   $(".pop-four").hover(function(){
    	$(".poptips-four").show();
    },function(){
    	$(".poptips-four").hide();
    });
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////*/
		$("input[name='warning']").get(0).checked = true;
	$("input[name='provencewarning']").get(0).checked = true;
	$("input[name='alert']").get(0).checked = true;
	$("input[name='warningImg']").get(0).checked = true;
	$("#tips_content").addClass("bgd3");
	$("#more-warning-content").hide();

	var healthTips = "";

	var isReportInfo = 0;
	var isWarning = 0;
	var ishealthTips = 0;
	var ResulthealthTips;
	$("#tips_content").val("空气质量指数为0－50，空气质量级别为一级，空气质量状况属于优。此时，空气质量令人满意，基本无空气污染，各类人群可正常活动。     空气质量指数为51－100，空气质量级别为二级，空气质量状况属于良。此时空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响，建议极少数异常敏感人群应减少户外活动。   空气质量指数为101－150，空气质量级别为三级，空气质量状况属于轻度污染。此时，易感人群症状有轻度加剧，健康人群出现刺激症状。建议儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼。 空气质量指数为151－200，空气质量级别为四级，空气质量状况属于中度污染。此时，进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响，建议疾病患者避免长时间、高强度的户外锻练，一般人群适量减少户外运动。 空气质量指数为201－300，空气质量级别为五级，空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动。 空气质量指数大于300，空气质量级别为六级，空气质量状况属于严重污染。此时，健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动");

	//获取数据
	function fetchData() {
		var param = {
			"creatDateST": creatDateST,
			"creatDateED": creatDateED,
			"provinceCode": provinceCode
		}
		PostAjax(window.url.sichuanGet, JSON.stringify(param),undefined,$(".operate-wrapper")).done(function(data) {
			var Result = data.Result;
			if(Result!=null){
				//赋值
			$("#forecastInfo").val(Result.forecastInfo);
			if(Result.isWarning == 0) {
				$("input[name='warning']").get(0).checked = true;
			} else {
				$("input[name='warning']").get(1).checked = true;
			}
			isReportInfo = Result.isReportInfo;
			isWarning = Result.isWarning;
			ishealthTips = Result.ishealthTips;
			if(isReportInfo==null){
             	$("input[name='provencewarning']").get(0).checked = true;
             }
             if(isWarning==null){
             	$("input[name='warning']").get(0).checked = true;
             }
             if(ishealthTips==null){
             	$("input[name='alert']").get(0).checked = true;
             }
             if(Result.isPicInfo==null){
             	$("input[name='warningImg']").get(0).checked = true;
             }
			if(Result.isReportInfo == 0) {
				$("input[name='provencewarning']").get(0).checked = true;
				if( Result.detailSceneryImagePath!=null){
					$("#uploadone img").attr("src", 'data:image/png;base64,' + Result.detailSceneryImagePath);
					$("#uploadone img").attr("dataname",Result.detailSceneryImagePathName);
				}
				

			} else {
				$("input[name='provencewarning']").get(1).checked = true;
				$("#uploadone").hide();
				$("#more-warning-content").show();
				$("#more-warning-content").val(Result.otherInfo);
				$("#uploadone img").attr("src", "../../img/bg/no-xiaqu.png");
			}

			if(Result.isPicInfo == 0) {
				$("input[name='warningImg']").get(0).checked = true;
				if(Result.sceneryImagesPath!=null){
					
					$("#uploadtwo img").attr("src", 'data:image/png;base64,' + Result.sceneryImagesPath);
					$("#uploadtwo img").attr("dataname",Result.sceneryImagesPathName);
				}
				
			} else {
				$("input[name='warningImg']").get(1).checked = true;
				if(Result.mapPath!=null){
					$("#uploadthree img").attr("src", 'data:image/png;base64,' + Result.mapPath);
					$("#uploadthree img").attr("dataname", Result.mapPathName);
				}
				
			}

			if(Result.ishealthTips == 0) {
				$("input[name='alert']").get(0).checked = true;
			} else {
				$("input[name='alert']").get(1).checked = true;
			}
			ResulthealthTips = Result.healthTips;
			$("#tips_content").val(ResulthealthTips);
			}
			

		})
	}
	fetchData();

	$(".hlepTips").on("click", "input", function(index) {
		if($(this).val() == 0) {
			$(".tips-right").show();
			$("#tips_content").attr("disabled", true);
			$("#tips_content").addClass("bgd3");
			$("input[name='alert']").get(0).checked = true;
			//$("#tips_content").attr("placeholder", "空气质量指数为0－50，空气质量级别为一级，空气质量状况属于优。此时，空气质量令人满意，基本无空气污染，各类人群可正常活动。     空气质量指数为51－100，空气质量级别为二级，空气质量状况属于良。此时空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响，建议极少数异常敏感人群应减少户外活动。   空气质量指数为101－150，空气质量级别为三级，空气质量状况属于轻度污染。此时，易感人群症状有轻度加剧，健康人群出现刺激症状。建议儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼。 空气质量指数为151－200，空气质量级别为四级，空气质量状况属于中度污染。此时，进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响，建议疾病患者避免长时间、高强度的户外锻练，一般人群适量减少户外运动。 空气质量指数为201－300，空气质量级别为五级，空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动。 空气质量指数大于300，空气质量级别为六级，空气质量状况属于严重污染。此时，健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动");
			$("#tips_content").val("空气质量指数为0－50，空气质量级别为一级，空气质量状况属于优。此时，空气质量令人满意，基本无空气污染，各类人群可正常活动。     空气质量指数为51－100，空气质量级别为二级，空气质量状况属于良。此时空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响，建议极少数异常敏感人群应减少户外活动。   空气质量指数为101－150，空气质量级别为三级，空气质量状况属于轻度污染。此时，易感人群症状有轻度加剧，健康人群出现刺激症状。建议儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼。 空气质量指数为151－200，空气质量级别为四级，空气质量状况属于中度污染。此时，进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响，建议疾病患者避免长时间、高强度的户外锻练，一般人群适量减少户外运动。 空气质量指数为201－300，空气质量级别为五级，空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动。 空气质量指数大于300，空气质量级别为六级，空气质量状况属于严重污染。此时，健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动");
			healthTips = "";
			isWarning = 0;
		} else {
			$(".tips-right").hide();
			$("#tips_content").attr("disabled", false);
			$("#tips_content").val("");
			$("#tips_content").attr("placeholder", "此处录入预警信息（选填项，信息文字长度最长为300字)");
			$("#tips_content").removeClass("bgd3");
			healthTips = $("#tips_content").val();
			isWarning = 1;
		}
	})
	//健康提示
	$(".auto_alert").on("click", "input", function(index) {

		if($(this).val() == 0) {
			$("#tips_content").attr("disabled", true);
			$("#tips_content").addClass("bgd3");
			$("#tips_content").val("空气质量指数为0－50，空气质量级别为一级，空气质量状况属于优。此时，空气质量令人满意，基本无空气污染，各类人群可正常活动。     空气质量指数为51－100，空气质量级别为二级，空气质量状况属于良。此时空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响，建议极少数异常敏感人群应减少户外活动。   空气质量指数为101－150，空气质量级别为三级，空气质量状况属于轻度污染。此时，易感人群症状有轻度加剧，健康人群出现刺激症状。建议儿童、老年人及心脏病、呼吸系统疾病患者应减少长时间、高强度的户外锻炼。 空气质量指数为151－200，空气质量级别为四级，空气质量状况属于中度污染。此时，进一步加剧易感人群症状，可能对健康人群心脏、呼吸系统有影响，建议疾病患者避免长时间、高强度的户外锻练，一般人群适量减少户外运动。 空气质量指数为201－300，空气质量级别为五级，空气质量状况属于重度污染。此时，心脏病和肺病患者症状显著加剧，运动耐受力降低，健康人群普遍出现症状，建议儿童、老年人和心脏病、肺病患者应停留在室内，停止户外运动，一般人群减少户外运动。 空气质量指数大于300，空气质量级别为六级，空气质量状况属于严重污染。此时，健康人群运动耐受力降低，有明显强烈症状，提前出现某些疾病，建议儿童、老年人和病人应当留在室内，避免体力消耗，一般人群应避免户外活动");
			healthTips = "";
			ishealthTips = 0;
		} else {
			if(ResulthealthTips != undefined) {
				$("#tips_content").val(ResulthealthTips);
			}
			$("#tips_content").attr("disabled", false);
			$("#tips_content").removeClass("bgd3");
			healthTips = $("#tips_content").val();
			ishealthTips = 1;
		}
	});

	//省辖区更多预报信息
	$(".more-warning").on("click", "input", function(index) {

		if($(this).val() == 0) {
			$("#more-warning-content").hide();
			$(".upload-img").show();
			isReportInfo = 0;
		} else {
			$("#more-warning-content").show();
			$(".upload-img").hide();
			isReportInfo = 1;
		}
	});
	//省辖区形势图
	$(".no_img").on("click", "input", function(index) {
		isPicInfo = $(this).val();
		if(isPicInfo == 0) {
			$("#uploadtwo").show();
			$("#uploadthree").hide();
		} else {
			$("#uploadtwo").hide();
			$("#uploadthree").show();
		}

	})

	$("#uploadone").on("click", function() {

		$(".fixed-div-new").attr("tad", "3").attr("deleteone","0").toggle();
		$("#hheader").html("省辖区风景图");
		var imgcode = $("#uploadone img").attr("src");
		$("#returnImgOne").attr("src", imgcode);
	});

	$("#uploadtwo").on("click", function() {
		$(".fixed-div-new").attr("tad", "4").attr("deleteone","1").toggle();
		$(".fixed-div-new fixed-box").html("");
		$("#hheader").html("省辖区风景图");
		var imgcode = $("#uploadtwo img").attr("src");
		$("#returnImgOne").attr("src", imgcode);

	});

	$("#uploadthree").on("click", function() {
		$(".fixed-div-new").attr("tad", "4").attr("deleteone","2").toggle();
		$(".fixed-div-new fixed-box").html("");
		$("#hheader").html("区域辖区形势图");
		var imgcode = $("#uploadthree img").attr("src");
		$("#returnImgOne").attr("src", imgcode);

	});

	$(".btn-close").on("click", function() {
		$(".fixed-div").hide();
	});
	$(".btn-closeAll").on("click", function() {
		$(".fixed-div").hide();
	});
	$(".close-font").on("click", function() {
		$(".fixed-div").hide();
	});

	$(".select-imgUpload-one").on("click", function() {
		uploadfun("fileUpload");
	});

	//预览并上报
	$(".btn-report").on("click", function() {

		var forecastInfo = $("#forecastInfo").val();
		var tips_content = $("#tips_content").val();
		var otherInfo = $("#more-warning-content").val()
		$("#box-one-content").html(forecastInfo); //省辖区预报信息
		$("#box-two-content").html(tips_content); //健康提示
		//验证
		if(forecastInfo == "" || tips_content == "") {

			layerAlert.autoclose("预报信息、预警信息不能为空");
			return;
		}

		if(isReportInfo == 0) {
			//无更多省预报信息
			if(($("#uploadone img")).attr("src") == "../../img/bg/no-xiaqu.png") {
				layerAlert.autoclose("选择无更多省预报信息时，图片不能为空!");
				return;
			}
			$("#box-three-content img").attr("src", $("#uploadone img").attr("src"));
		} else {
			//有更多省预报信息
			if(otherInfo == "") {
				layerAlert.autoclose("选择有更多省预报信息时，更多预报信息不能为空!");
				return;
			}
			$("#box-three-content").html(otherInfo);
		}
		if(isPicInfo == 0) {

			if(($("#uploadtwo img")).attr("src") == "../../img/bg/no-xiaqu.png") {
				layerAlert.autoclose("选择无辖区形势图时,图片不能为空");
				return;
			}
			$("#box-four-content img").attr("src", $("#uploadtwo img").attr("src"));
		} else {
			if(($("#uploadthree img")).attr("src") == "../../img/bg/no-xiaqu.png") {
				layerAlert.autoclose("选择有辖区形势图时, 图片不能为空!");
				return;
			}
			$("#box-four-content img").attr("src", $("#uploadthree img").attr("src"));
		}
		//遍历获取到得图片文件
		$(".fixed-div-thour").toggle();
	});
     
     
    //删除图片
    $(".deleteImg").on("click",function(){
    	$(".sure-tips").show();
    	
    })
    //取消
    $(".btn-cancel").on("click",function(){
    	$(".sure-tips").hide();
    })
    $(".btn-sure").on("click",function(){
    	//删除图片deletePic
    	var deletePic  = $(".fixed-div").attr("deleteone");
    	var param = {
			"creatDateST": creatDateST,
			"creatDateED": creatDateED,
			"provinceCode": provinceCode,
			
		}
    	if(deletePic=="0"){
    		param.deletePic="0";
    	}
    	if(deletePic=="1"){
    		param.deletePic="1";
    	}
    	
    	if(deletePic=="2"){
    		param.deletePic="2";
    	}
    	console.log(param);
		PostAjax(window.url.deletePic, JSON.stringify(param)).done(function(res) {
			console.log(res);
			if(res.msg=="操作成功"){
		var deletePic  = $(".fixed-div").attr("deleteone");
		$("#returnImgOne").attr("src","../../img/bg/no-xiaqu.png");
		if(deletePic=="0"){
		$("#uploadone img").attr("src","../../img/bg/no-xiaqu.png");
		}
		if(deletePic=="1"){
		$("#uploadtwo img").attr("src","../../img/bg/no-xiaqu.png");
		}
		
		if(deletePic=="2"){
		$("#uploadthree img").attr("src","../../img/bg/no-xiaqu.png");
		    }
		$(".sure-tips").hide();
			}
		});
    })
	//确认上报
	$(".sure-report").on("click", function() {

		var otherInfo;
		var healthTips;
		var warningInfo;
		var param;
		//国家
		var isFormGraph;
		var formOrSceneryGraphBytes;
		var moreInfoGraphBytes;
		var moreInfoGraphName='';
		var formOrSceneryGraphName='';
		if(isReportInfo == 0) {
			otherInfo = "";
			var imgChar = $("#uploadone img").attr("src");
				imgChar = imgChar.split(",")
				imgChar = imgChar[imgChar.length - 1];
				moreInfoGraphBytes = imgChar;
			
				moreInfoGraphName=$("#uploadone img").attr("dataname");
			
		} else {
			otherInfo = $("#more-warning-content").val();
			moreInfoGraphBytes=null;
			moreInfoGraphName='';
		}
    	var healthTips_country;
		if(ishealthTips == 0) {
			healthTips = $("#box-two-content").html();
			healthTips_country='';
		} else {
			healthTips = $("#box-two-content").html();
			healthTips_country=healthTips;
		}

		if(isWarning == 0) {
			warningInfo = "";
		} else {
			warningInfo = $("#box-two-content").html();
		}
		if(isPicInfo == 0) {
			isFormGraph = false;
			var imgChar = $("#uploadtwo img").attr("src");
				imgChar = imgChar.split(",")
				imgChar = imgChar[imgChar.length - 1];
				formOrSceneryGraphBytes = imgChar;
				formOrSceneryGraphName=$("#uploadtwo img").attr("dataname");
		} else {
			isFormGraph = true;
			var imgChar = $("#uploadthree img").attr("src");
				imgChar = imgChar.split(",")
				imgChar = imgChar[imgChar.length - 1];
				formOrSceneryGraphBytes = imgChar;
				formOrSceneryGraphName=$("#uploadthree img").attr("dataname");
		}
		param = {
			"creatDate": creatDateST + " 00:00:00",
			"creatDateST": creatDateST,
			"creatDateED": creatDateED,
			"forecastInfo": $("#box-one-content").html(),
			"healthTips": healthTips,
			"isWarning": isWarning,
			"ishealthTips": ishealthTips,
			"otherInfo": otherInfo,
			"provinceCode": provinceCode,
			"warningInfo": warningInfo
		}
		if(isReportInfo == 0) {
			param = param;
		} else {
			param.isReportInfo = isReportInfo;

		}
		var country = {

			"loginName": "900070",
			"password": "dqs@123",
			"forecastInfo": $("#box-one-content").html(),
			"warningInfo": warningInfo,
			"healthTip": healthTips_country,
			"moreInfo": otherInfo,
			"moreInfoGraphBytes": moreInfoGraphBytes,
			"moreInfoGraphName": moreInfoGraphName,
			"isFormGraph": isFormGraph,
			"formOrSceneryGraphBytes": formOrSceneryGraphBytes,
			"formOrSceneryGraphName": formOrSceneryGraphName
		}
		console.log(">>>country>", country);
		//
		PostAjax(window.url.sendChina, JSON.stringify(country)).done(function(data) {
			if(data.msg == "操作成功") {
				layerAlert.autoclose("上报操作成功");
				$(".fixed-div-thour").hide();
				fetchData();
			}else{
				layerAlert.autoclose(data.UploadProvinceDataResult);
			}
		});
		PostAjax(window.url.xinanupdate, JSON.stringify(param),undefined,$(".operate-wrapper")).done(function(data) {
			console.log(">>>", data)
			if(data.msg == "操作成功") {
				layerAlert.autoclose("上报操作成功");
				$(".fixed-div-thour").hide();
				fetchData();
			}else{
				layerAlert.autoclose(data.msg);
			}
		});
	});
	
});
var oneimg_name='';
var twoimg_name='';
function uploadfun(dom) {
	$("#" + dom).val("").click();
	$("#" + dom).on("change", function(data) {

		
		var size = 1000;
		var formData = new FormData();
		var $this = $(this);
		var file = $this.get(0).files[0];
		var url = '';
		var tad = $(".fixed-div").attr("tad");
		if(tad == "3") {

			url = window.url.updatePicD;
			oneimg_name=file.name;
		} else {
			if(isPicInfo == 0) {
				url = window.url.updatePicS;
				twoimg_name=file.name;
			} else {
				url = window.url.updatePicM;
				twoimg_name=file.name;
			}
		}
         
		formData.append('image', file);
		var fiName=file.name ;
		if(file.name == "") {
			layerAlert.autoclose("请上传图片");
			return false;
		} else {
			if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG)$/.test(file.name)) {
				layerAlert.autoclose("图片类型必须是.gif,jpeg,jpg,png中的一种")
				return;
			}
			fiName=fiName.split(",");
			fiName=fiName[0];
			if(file.size > size * 1024) {
				layerAlert.autoclose("图片大小不能超过" + size + "KB！");
				return;
			}
			if(fiName.length>20){
				layerAlert.autoclose("图片名称不能超过20个字符");
				return;
			}

		}

		formData.append("creatDateST", creatDateST);
		formData.append("creatDateED", creatDateED);
		formData.append("provinceCode", provinceCode);
        var formOrSceneryGraphName=file.name;
			console.log(formOrSceneryGraphName);
		$.ajax({
			url: url,
			type: 'POST',
			data: formData,
			dataType: "json",
			async: false,
			cache: false,
			contentType: false,
			processData: false,
			success: function(returndata) {
				if(returndata.msg!="操作成功"){
					layerAlert.autoclose(returndata.msg);
				}
				var obj = returndata.obj;
				if(url == window.url.updatePicD) {
					$("#uploadone img").attr("src", 'data:image/png;base64,' + obj.detailSceneryImagePath).attr("dataname",obj.detailSceneryImagePathName);
					$("#returnImgOne").attr("src", 'data:image/png;base64,' + obj.detailSceneryImagePath);
				}
				if(url == window.url.updatePicS) {
					$("#uploadtwo img").attr("src", 'data:image/png;base64,' + obj.sceneryImagesPath).attr("dataname",obj.sceneryImagesPathName);;
					$("#returnImgOne").attr("src", 'data:image/png;base64,' + obj.sceneryImagesPath);
				}
				if(url == window.url.updatePicM) {
					$("#uploadthree img").attr("src", 'data:image/png;base64,' + obj.mapPath).attr("dataname",obj.mapPathName);;
					$("#returnImgOne").attr("src", 'data:image/png;base64,' + obj.mapPath);
				}

			},
			error: function(returndata) {
				layerAlert.autoclose(returndata);
			}
		});
	});

}
function getcityname(area){
	var cityName = '';
	if(area=="成都平原" || area=="盆地南部" || area=="盆地东北部" || area=="川西及攀西高原"){
		cityName='四川省';
	}
	if(area=="盆地东部"){
		cityName='重庆市';
	}
	if(area=="黔东" || area=="黔中" || area=="黔西北" || area=="黔南及黔西南"){
		cityName='贵州省';
	}
	if(area=="滇东北" || area=="滇东南" || area=="滇中" || area=="滇西南" || area=="滇西北"){
		cityName='云南省';
	}
	if(area=="藏东" || area=="藏中" || area=="藏北" || area=="藏西"){
		cityName='西藏自治区';
	}
	return cityName;
}
function gets(value,location){
	var _value = '';
	if(value.indexOf('-')==-1){
		_value = value;
	}else{
		var arr=value.split('-');
		if(location=='S'){
			_value = arr[0];
		}else if(location=='N'){
			_value = arr[1];
		}
	}
	return _value;
}
