/*!
 * jQuery-ajaxTransport-XDomainRequest - v1.0.4 - 2015-03-05
 * https://github.com/MoonScript/jQuery-ajaxTransport-XDomainRequest
 * Copyright (c) 2015 Jason Moon (@JSONMOON)
 * Licensed MIT (/blob/master/LICENSE.txt)
 */
(function(factory) {
	if(typeof define === 'function' && define.amd) {
		// AMD. Register as anonymous module.
		define(['jquery'], factory);
	} else if(typeof exports === 'object') {
		// CommonJS
		module.exports = factory(require('jquery'));
	} else {
		// Browser globals.
		factory(jQuery);
	}
}(function($) {

	// Only continue if we're on IE8/IE9 with jQuery 1.5+ (contains the ajaxTransport function)
	if($.support.cors || !$.ajaxTransport || !window.XDomainRequest) {
		return $;
	}

	var httpRegEx = /^(https?:)?\/\//i;
	var getOrPostRegEx = /^get|post$/i;
	var sameSchemeRegEx = new RegExp('^(\/\/|' + location.protocol + ')', 'i');

	// ajaxTransport exists in jQuery 1.5+
	$.ajaxTransport('* text html xml json', function(options, userOptions, jqXHR) {

		// Only continue if the request is: asynchronous, uses GET or POST method, has HTTP or HTTPS protocol, and has the same scheme as the calling page
		if(!options.crossDomain || !options.async || !getOrPostRegEx.test(options.type) || !httpRegEx.test(options.url) || !sameSchemeRegEx.test(options.url)) {
			return;
		}

		var xdr = null;

		return {
			send: function(headers, complete) {
				var postData = '';
				var userType = (userOptions.dataType || '').toLowerCase();

				xdr = new XDomainRequest();
				if(/^\d+$/.test(userOptions.timeout)) {
					xdr.timeout = userOptions.timeout;
				}

				xdr.ontimeout = function() {
					complete(500, 'timeout');
				};

				xdr.onload = function() {
					var allResponseHeaders = 'Content-Length: ' + xdr.responseText.length + '\r\nContent-Type: ' + xdr.contentType;
					var status = {
						code: 200,
						message: 'success'
					};
					var responses = {
						text: xdr.responseText
					};
					try {
						if(userType === 'html' || /text\/html/i.test(xdr.contentType)) {
							responses.html = xdr.responseText;
						} else if(userType === 'json' || (userType !== 'text' && /\/json/i.test(xdr.contentType))) {
							try {
								responses.json = $.parseJSON(xdr.responseText);
							} catch(e) {
								status.code = 500;
								status.message = 'parseerror';
								//throw 'Invalid JSON: ' + xdr.responseText;
							}
						} else if(userType === 'xml' || (userType !== 'text' && /\/xml/i.test(xdr.contentType))) {
							var doc = new ActiveXObject('Microsoft.XMLDOM');
							doc.async = false;
							try {
								doc.loadXML(xdr.responseText);
							} catch(e) {
								doc = undefined;
							}
							if(!doc || !doc.documentElement || doc.getElementsByTagName('parsererror').length) {
								status.code = 500;
								status.message = 'parseerror';
								throw 'Invalid XML: ' + xdr.responseText;
							}
							responses.xml = doc;
						}
					} catch(parseMessage) {
						throw parseMessage;
					} finally {
						complete(status.code, status.message, responses, allResponseHeaders);
					}
				};

				// set an empty handler for 'onprogress' so requests don't get aborted
				xdr.onprogress = function() {};
				xdr.onerror = function() {
					complete(500, 'error', {
						text: xdr.responseText
					});
				};

				if(userOptions.data) {
					postData = ($.type(userOptions.data) === 'string') ? userOptions.data : $.param(userOptions.data);
				}
				xdr.open(options.type, options.url);
				xdr.send(postData);
			},
			abort: function() {
				if(xdr) {
					xdr.abort();
				}
			}
		};
	});

	return $;

}));
//深拷贝函数
function ObjCopy(obj) {
	var tmp_obj;
	if(typeof obj == 'object') {
		if(obj instanceof Array) {
			tmp_obj = [];
		} else {
			tmp_obj = {};
		}
	} else {
		return obj;
	}
	for(var i in obj) {
		if(typeof obj[i] != 'object') {
			tmp_obj[i] = obj[i];
		} else if(obj[i] instanceof Array) {
			tmp_obj[i] = [];
			for(var j in obj[i]) {
				if(typeof obj[i][j] != 'object') {
					tmp_obj[i][j] = obj[i][j];
				} else {
					tmp_obj[i][j] = ObjCopy(obj[i][j]);
				}
			}
		} else {
			tmp_obj[i] = ObjCopy(obj[i]);
		}
	}
	return tmp_obj;
}

/**
 * 去除数组重复
 * [getArray description]
 * @param  {[type]} arr [description]
 * @return {[type]}     [description]
 */
function getArray(arr) {
	var hash = {};
	var len = arr.length;
	var result = [];

	for(var i = 0; i < len; i++) {
		if(!hash[arr[i]]) {
			hash[arr[i]] = true;
			result.push(arr[i]);
		}
	}
	return result;
}

function PostAjax(url, param, header, className) {

	var headers = (header === undefined ? {
		'Content-Type': 'application/json;charset=UTF-8'
	} : header);
	var ajaxObj = $.ajax({
		url: url,
		type: 'post',
		data: param,
		datatype: 'jsonp',
		jsonp: 'callback',
		headers: headers,
		beforeSend: function() {
			if(className) {
				var loadHtml =
					'<div class="common-loading">' +
					'<div class="img"></div>' +
					'</div>';
				var fixDiv = $(className);
				fixDiv.css("position", "relative");
				fixDiv.append(loadHtml);
			}

		},
		complete: function() {
			if(className) {
				var fixDiv = $(className);
				fixDiv.children(".common-loading").remove();
			}
		}
		
		
	});
	return ajaxObj;
};

function PutAjax(url, param, header, className) {
	var headers = (header === undefined ? {
		'Content-Type': 'application/json;charset=UTF-8'
	} : header);
	var ajaxObj = $.ajax({
		url: url,
		type: 'PUT',
		datatype: 'jsonp',
		jsonp: 'callback',
		data: param,
		headers: headers
		
	});
	return ajaxObj;
};

function DeleteAjax(url, header) {
	var headers = (header === undefined ? {
		'Content-Type': 'application/json;charset=UTF-8'
	} : header);
	var ajaxObj = $.ajax({
		url: url,
		type: 'DELETE',
		datatype: 'jsonp',
		jsonp: 'callback',
		headers: headers
	});
	return ajaxObj;
};

function GetAjax(url, header, className) {
	var headers = (header === undefined ? {
		'Content-Type': 'application/json;charset=UTF-8'
	} : header);

	var ajaxObj = $.ajax({
		url: url,
		type: 'GET',
		datatype: 'jsonp',
		jsonp: 'callback',
		async: true, //异步还是同步
		headers: headers,
		beforeSend: function() {
			if(className) {
				var loadHtml =
					'<div class="common-loading">' +
					'<div class="img"></div>' +
					'</div>';
				var fixDiv = $(className);
				fixDiv.css("position", "relative");
				fixDiv.append(loadHtml);
			}

		},
		complete: function() {
			if(className) {
				var fixDiv = $(className);
				fixDiv.children(".common-loading").remove();
			}
		}

	});
	return ajaxObj;
};

function GetThreeArr(arr, count) {
	var result = [];
	for(var i = 0; i < arr.length; i += count) {
		result.push(arr.slice(i, i + count));
	}

	return result;
};

//对手机号码验证
function phoneCheck(val) {
	var flag = true;
	var pattern = /^1[3|4|5|8][0-9]\d{4,8}$/;
	// var patternTwo = /^0\d{2,3}-?\d{7,8}$/;
	if(pattern.test(val)) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

/*页面跳转*/
var linkFun = function(URLS) {
	window.location.href = URLS + ".html";
}

//检验名称
function NameCheck(val) {
	var flag = true;
	var patternName = /^[\u4E00-\u9FA5a-zA-Z0-9_]{1,16}$/;
	if(patternName.test(val)) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}
//检验金额
var moneyCheck = function(val) {
	var flag = true;
	var patternName = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	if(patternName.test(val)) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

function isNumber(value) {
	var patrn = /^[0-9]*$/;
	if(patrn.exec(value) == null || value == "") {
		return false
	} else {
		return true
	}
}
//对手机号码验证
function phoneNumCheck(val) {
	var flag = true;
	var pattern = /^1[3|4|5|8][0-9]\d{4,8}$/;
	var patternTwo = /^(0\\d{2}-\\d{8}(-\\d{1,4})?)|(0\\d{3}-\\d{7,8}(-\\d{1,4})?)$/;
	if(pattern.test(val) || patternTwo.test(val)) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

//对邮箱验证
function mailCheck(val) {
	var flag = true;
	var pattern = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	if(pattern.test(val)) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

/**
 *[errorAlert]警告出现
 */
function errorAlert(dom, text) {
	dom.text(text);
}

/**
 * [getnewDay description]
 * @return {[type]} [description] 生成日期
 */
function getnewDay(Day, flag) {
	var tody = new Date(Day);
	var year = tody.getFullYear();
	var month = tody.getMonth() + 1;
	var day = tody.getDate();
	var hour = tody.getHours();
	var min = tody.getMinutes();
	var newDay = (flag == true ? (year + "-" + zero(month) + "-" + zero(day) + ' ' + zero(hour) + ':' + zero(min)) : (year + "-" + zero(month) + "-" + zero(day)));
	return newDay;
}
/**
 * [zero description]
 * @param  {[type]} value [description]
 * @return {[type]}       [description]
 */
function zero(value) {
	var _value = '';
	if(value > 9) {
		_value = value
	} else {
		_value = '0' + value;
	}
	return _value
}
/**
 * [getnewPassword description]
 * @param  {[type]} password [description]
 * @return {[type]}          [description] 得到新的转换后的密码
 */
function getnewPassword(password) {
	var base = new Base64();
	var newpassword = '';
	var _password = password + ' ' + getnewDay();
	/*先转成base64*/

	var _newpassword = base.encode(_password);
	newpassword = _newpassword.replace(/([a-z]*)(.*?)([A-Z]*)/g, function(m, m1, m2, m3) {
		return m1.toUpperCase() + m2 + m3.toLowerCase();
	});
	return newpassword;
}

/**
 * [guid description]
 * @return {[type]} [description] 唯一码生成
 */
function guid() {
	return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		var r = Math.random() * 16 | 0,
			v = c == 'x' ? r : (r & 0x3 | 0x8);
		return v.toString(16);
	});
}

/**
 * [passwordFlag 匹配一个有英文和数字组成的长度为6～18的字符串,必须包含至少一个字母和一个数字]
 * @param  {[type]} val [description]
 * @return {[type]}     [description]
 */
function passwordFlag(val) {
	var pattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,18}$/;
	var flag = true;
	if(pattern.test(val)) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

/********************************************兼容IE7 8 时间****************************************/
function dateFormat(dateString, format) {
	if(!dateString) return "";

	var time = new Date($.trim(dateString.replace(/-/g, '/').replace(/T|Z/g, ' ')));
	var o = {
		"M+": time.getMonth() + 1, //月份
		"d+": time.getDate(), //日
		"h+": time.getHours(), //小时
		"m+": time.getMinutes(), //分
		"s+": time.getSeconds(), //秒
		"q+": Math.floor((time.getMonth() + 3) / 3), //季度
		"S": time.getMilliseconds() //毫秒
	};
	if(/(y+)/.test(format)) format = format.replace(RegExp.$1, (time.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return format;
}

/********************************************兼容IE7 8 ****************************************/
if(typeof Array.prototype.forEach != 'function') {
	Array.prototype.forEach = function(callback) {
		for(var i = 0; i < this.length; i++) {
			callback.apply(this, [this[i], i, this]);
		}
	};
}

/*********************************************兼容处理*********************************************/

if(!window.JSON) {
	window.JSON = {
		parse: function(jsonStr) {
			return eval('(' + jsonStr + ')');
		},
		stringify: function(jsonObj) {
			var result = '',
				curVal;
			if(jsonObj === null) {
				return String(jsonObj);
			}
			switch(typeof jsonObj) {
				case 'number':
				case 'boolean':
					return String(jsonObj);
				case 'string':
					return '"' + jsonObj + '"';
				case 'undefined':
				case 'function':
					return undefined;
			}

			switch(Object.prototype.toString.call(jsonObj)) {
				case '[object Array]':
					result += '[';
					for(var i = 0, len = jsonObj.length; i < len; i++) {
						curVal = JSON.stringify(jsonObj[i]);
						result += (curVal === undefined ? null : curVal) + ",";
					}
					if(result !== '[') {
						result = result.slice(0, -1);
					}
					result += ']';
					return result;
				case '[object Date]':
					return '"' + (jsonObj.toJSON ? jsonObj.toJSON() : jsonObj.toString()) + '"';
				case '[object RegExp]':
					return "{}";
				case '[object Object]':
					result += '{';
					for(i in jsonObj) {
						if(jsonObj.hasOwnProperty(i)) {
							curVal = JSON.stringify(jsonObj[i]);
							if(curVal !== undefined) {
								result += '"' + i + '":' + curVal + ',';
							}
						}
					}
					if(result !== '{') {
						result = result.slice(0, -1);
					}
					result += '}';
					return result;

				case '[object String]':
					return '"' + jsonObj.toString() + '"';
				case '[object Number]':
				case '[object Boolean]':
					return jsonObj.toString();
			}
		}
	};
}

// /*********************************************兼容IE7 8  localStorage*********************************************/

/*******************************************弹框*****************************************/
var area = ['360px', 'auto'], //设置弹出框大小
	btn = ['确定', '取消'], //设置弹出框按钮组
	shift = 5,
	shadeClose = false; //点击遮罩关闭层
var layerAlert = {

	error: function(text, title) {
		var def_title = '出错啦！';
		title = title ? title : def_title;
		layer.open({
			title: title,
			shadeClose: shadeClose,
			area: area,
			btn: btn,
			btnAlign: 'c',
			content: text,
			icon: 2,
			shift: shift
		});
	},

	iframe: function(title, url) {
		var def_title = '弹出窗口';
		title = title ? title : def_title;
		layer.open({
			type: 2,
			title: title,
			maxmin: true,
			shadeClose: false, //点击遮罩关闭层
			area: ['100%', '100%'],
			content: url
		});
	},

	autoclose: function(text, title, time) {
		var def_title = '提示！';
		title = title ? title : def_title;
		time = time ? time : 2000;
		if(arguments.length === 1) {
			layer.alert(text);
		} else {
			layer.open({
				title: title,
				shadeClose: shadeClose,
				area: area,
				btn: btn,
				btnAlign: 'c',
				content: text,
				icon: 0,
				shift: shift
			});
		}
		setTimeout(function(index, layero) {
			layer.closeAll();
		}, time);
	},
	success: function(text, title) {
		var def_title = '成功啦！';
		title = title ? title : def_title;
		layer.open({
			title: title,
			shadeClose: shadeClose,
			area: ['360px', 'auto'],
			btn: btn,
			btnAlign: 'c',
			content: text,
			icon: 1,
			shift: shift
		});
	},
	info: function(text, title) {
		var def_title = '提示！';
		title = title ? title : def_title;
		layer.open({
			title: title,
			shadeClose: shadeClose,
			area: area,
			btn: btn,
			btnAlign: 'c',
			content: text,
			icon: 0,
			shift: shift
		});
	},

	confirm: function(text, todo, title) {
		var def_title = '提示：';
		title = title ? title : def_title;
		layer.open({
			title: title,
			shadeClose: shadeClose,
			area: area,
			btn: btn,
			btnAlign: 'c',
			content: text,
			icon: 0,
			shift: shift,
			yes: function(index, layero) {
				if(todo) todo();
				layer.close(index);
			}
		});
	},

	checkone: function() {
		/*==========================================*/
		/*arguments=[title,function1,function2,btn1Text,btn2Text,btn1ClickedClose,btn1ClickedClose,text];
		* title:窗口显示标题
		* function1：回调函数1
		* function2：回调函数2
		* btn1Text:按钮1文本
		* btn2Text:按钮2文本
		* btn1ClickedClose:回调函数1执行完成是否关闭窗口
		* btn2ClickedClose:回调函数2执行完成是否关闭窗口
		* text:窗口显示内容
		/*==========================================*/
		var def_title = '提示:';
		var def_text = '请选择要执行的操作';
		var title = arguments[0] ? arguments[0] : def_title;
		var _btn = arguments[3] && arguments[4] ? [arguments[3], arguments[4]] : btn;
		var text = arguments[7] ? arguments[7] : def_text;
		if(typeof arguments[0] == "function" && typeof arguments[1] == "function") {
			var btn1ClickedClose = arguments[4],
				btn2ClickedClose = arguments[5];
			title = def_title;
			_btn = arguments[2] && arguments[3] ? [arguments[2], arguments[3]] : btn;
			var fun1 = arguments[0],
				fun2 = arguments[1];
			layer.open({
				title: title,
				shadeClose: shadeClose,
				area: area,
				btn: _btn,
				btnAlign: 'c',
				content: text,
				icon: 0,
				shift: shift,
				btn1: function(index, layero) {
					fun1();
					if(btn1ClickedClose) layer.close(index);
				},
				btn2: function(index, layero) {
					fun2();
					if(!btn2ClickedClose) return false;
				}
			});
		} else {
			var fun1 = arguments[1],
				fun2 = arguments[2],
				btn1ClickedClose = arguments[5],
				btn2ClickedClose = arguments[6];
			layer.open({
				title: title,
				shadeClose: shadeClose,
				area: area,
				btn: _btn,
				btnAlign: 'c',
				content: text,
				icon: 0,
				shift: shift,
				btn1: function(index, layero) {
					if(fun1) fun1();
					if(btn1ClickedClose) layer.close(index);
				},
				btn2: function(index, layero) {
					if(fun2) fun2();
					if(!btn2ClickedClose) return false;
				}
			});
		}

	}
};
/*日期格式化方法*/
Date.prototype.Format = function(fmt) { //author: meizz   
	var o = {
		"M+": this.getMonth() + 1, //月份   
		"d+": this.getDate(), //日   
		"H+": this.getHours(), //小时   
		"m+": this.getMinutes(), //分   
		"s+": this.getSeconds(), //秒   
		"q+": Math.floor((this.getMonth() + 3) / 3), //季度   
		"S": this.getMilliseconds() //毫秒   
	};
	if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

function tabTime(date1, date2) {
	var oDate1 = new Date(date1); //转为date对象进行比较
	var oDate2 = new Date(date2);
	var flag;
	if(oDate1.getTime() > oDate2.getTime()) {
		flag = 1;
	} else {
		flag = 2;
	}
	return flag;
}

function addByTransDate(dateParameter, num) {
	var translateDate = "",
		dateString = "",
		monthString = "",
		dayString = "";
	translateDate = dateParameter.replace("-", "/").replace("-", "/");;
	var newDate = new Date(translateDate);
	newDate = newDate.valueOf();
	newDate = newDate + num * 24 * 60 * 60 * 1000; //备注 如果是往前计算日期则为减号 否则为加号
	newDate = new Date(newDate);
	//如果月份长度少于2，则前加 0 补位  
	if((newDate.getMonth() + 1).toString().length == 1) {
		monthString = 0 + "" + (newDate.getMonth() + 1).toString();
	} else {
		monthString = (newDate.getMonth() + 1).toString();
	}
	//如果天数长度少于2，则前加 0 补位  
	if(newDate.getDate().toString().length == 1) {
		dayString = 0 + "" + newDate.getDate().toString();
	} else {
		dayString = newDate.getDate().toString();
	}
	dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
	return dateString;
}
function reduceByTransDate(dateParameter, num) {
	var translateDate = "",
		dateString = "",
		monthString = "",
		dayString = "";
	translateDate = dateParameter.replace("-", "/").replace("-", "/");;
	var newDate = new Date(translateDate);
	newDate = newDate.valueOf();
	newDate = newDate - num * 24 * 60 * 60 * 1000; //备注 如果是往前计算日期则为减号 否则为加号
	newDate = new Date(newDate);
	//如果月份长度少于2，则前加 0 补位  
	if((newDate.getMonth() + 1).toString().length == 1) {
		monthString = 0 + "" + (newDate.getMonth() + 1).toString();
	} else {
		monthString = (newDate.getMonth() + 1).toString();
	}
	//如果天数长度少于2，则前加 0 补位  
	if(newDate.getDate().toString().length == 1) {
		dayString = 0 + "" + newDate.getDate().toString();
	} else {
		dayString = newDate.getDate().toString();
	}
	dateString = newDate.getFullYear() + "-" + monthString + "-" + dayString;
	return dateString;
}
/*数字转换为对应的文字*/
var numberToText = function(n, array) {
	var _text;
	for(var i = 0; i < array.length; i++) {
		if(array[i].Id === n) {
			_text = array[i].Name;
		}
	}
	if(!_text) {
		_text = "暂无";
	}
	return _text;
};


        function chunk(array, size) {
            //获取数组的长度，如果你传入的不是数组，那么获取到的就是undefined
            const length = array.length
            //判断不是数组，或者size没有设置，size小于1，就返回空数组
            if (!length || !size || size < 1) {
              return []
            }
            //核心部分
            let index = 0 //用来表示切割元素的范围start
            let resIndex = 0 //用来递增表示输出数组的下标
          
            //根据length和size算出输出数组的长度，并且创建它。
            let result = new Array(Math.ceil(length / size))
            //进行循环
            while (index < length) {
              //循环过程中设置result[0]和result[1]的值。该值根据array.slice切割得到。
              result[resIndex++] = array.slice(index, (index += size))
            }
            //输出新数组
            return result
          }
        function run(input_file,get_data){
        /*input_file：文件按钮对象*/
        /*get_data: 转换成功后执行的方法*/
        if ( typeof(FileReader) === 'undefined' ){
            alert("抱歉，你的浏览器不支持 FileReader，不能将图片转换为Base64，请使用现代浏览器操作！");
        } else {
            try{
                /*图片转Base64 核心代码*/
                //var file = input_file.files[0];
                var file = input_file;
                //这里我们判断下类型如果不是图片就返回 去掉就可以上传任意文件
                if(!/image\/\w+/.test(file.type)){
                    alert("请确保文件为图像类型");
                    return false;
                }
                var reader = new FileReader();
                reader.onload = function(){
                    get_data(this.result);
                }
                reader.readAsDataURL(file);
            }catch (e){
                alert('图片转Base64出错啦！'+ e.toString())
            }
        }
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