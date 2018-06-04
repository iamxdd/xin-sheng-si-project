var isPicInfo = 0;
var creatDateST = (new Date().getFullYear()) + "-" + (new Date().getMonth() + 1) + "-" + (new Date().getDate());
var creatDateED = creatDateST;
var provinceCode = 510000;
$(function() {
	window.url = pageConfig.ajaxUrl;
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

			"loginName": "510000",
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
				layerAlert.autoclose(data.UploadAreaDataResult);
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
   $(".pop-one").hover(function(){
    	$(".poptips-one").show();
    },function(){
    	$(".poptips-one").hide();
    });
     $(".pop-two").hover(function(){
    	$(".poptips-two").show();
    },function(){
    	$(".poptips-two").hide();
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

function srcbase64(dom) {
	var imgChar = dom.attr("src");
	imgChar = imgChar.split(",")
	imgChar = imgChar[imgChar.length - 1];
	return imgChar
}