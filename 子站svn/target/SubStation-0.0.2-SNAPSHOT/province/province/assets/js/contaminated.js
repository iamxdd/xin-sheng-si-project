var timeday;
(function () {
    $('.header').lavalamp({
        easing: 'easeOutBack',
        setOnClick: true
    });

    var year = new Date().getFullYear();
    var month = new Date().getMonth() + 1
    var line = year - 2000;
    var line2 = year - 2012;
    var str = '';
    var str2 = ''
    for (var i = month; i >= 1; i--) {
        str += '<option>' + year + '年' + i + '月' + '</option>';
        str2 += '<option>' + year + '年' + i + '月' + '</option>';
    }
    for (var i = 1; i < line - 1; i++) {
        for (var j = 12; j >= 1; j--) {
            str += "<option>" + (year - i) + '年' + j + '月' + "</option>"
        }
    }
    for (var i = 1; i < line2 - 1; i++) {
        for (var j = 12; j >= 1; j--) {
            str2 += "<option>" + (year - i) + '年' + j + '月' + "</option>"
        }
    }
    timeday = str
    timeday2 = str2
    $('#time_change').html(str)
    $('#time_change2').html(str)
    $('#time_change3').html(str)
    $('#time_change4').html(str)
    $('#time_change5').html(str)
    $('#time_change6').html(str)
    $('#time_change7').html(str)
    $('#time_change8').html(str)
    $('#time_change9').html(str)
    $('#timecount_time1').html(str2)
    $('#timecount_time2').html(str2)
    $('#citycount_time1').html(str2)
    $('#citycount_time2').html(str2)
    document.getElementById('time_change').options[0].selected = true;
    document.getElementById('time_change2').options[0].selected = true;
    document.getElementById('time_change3').options[0].selected = true;
    document.getElementById('time_change4').options[0].selected = true;
    document.getElementById('time_change5').options[0].selected = true;
    document.getElementById('time_change6').options[0].selected = true;
    document.getElementById('time_change7').options[0].selected = true;
    document.getElementById('time_change8').options[0].selected = true;
    document.getElementById('time_change9').options[0].selected = true;
    document.getElementById('citycount_time1').options[0].selected = true;
    document.getElementById('citycount_time2').options[0].selected = true;
    var city = {
        '自贡市': 5103,
        '泸州市': 5105,
        '内江市': 5110,
        '宜宾市': 5115,
        '眉山市': 5114,
        '乐山市': 5111,
        '资阳市': 5120,
        '成都市': 5101,
        '德阳市': 5106,
        '绵阳市': 5107,
        '雅安市': 5118,
        '达州市': 5117,
        '遂宁市': 5109,
        '广安市': 5116,
        '南充市': 5113,
        '广元市': 5108,
        '巴中市': 5119,
        '攀枝花市': 5104,
        '西昌市': 5134,
        '康定市': 5133,
        '马尔康市': 5132
    }
    var city2 = {
        '广元市': 5108,
        '巴中市': 5119,
        '南充市': 5113,
        '遂宁市': 5109,
        '达州市': 5117,
        '广安市': 5116,
        '绵阳市': 5107,
        '德阳市': 5106,
        '成都市': 5101,
        '资阳市': 5120,
        '眉山市': 5114,
        '乐山市': 5111,
        '雅安市': 5118,
        '内江市': 5110,
        '自贡市': 5103,
        '宜宾市': 5115,
        '泸州市': 5105,
        '攀枝花市': 5104,
        '西昌市': 5134,
        '康定市': 5133,
        '马尔康市': 5132
    }

    for (key in city) {
        if (city[key] == 5103) {
            $('.row_city_button').append('<div class="city_button_width"><div class="city_button city_button' + city[key] + ' activity" data_index =' + city[key] + ' onclick="citychange(' + city[key] + ')">' + key + '</div></div>')
        } else {
            $('.row_city_button').append('<div class="city_button_width"><div class="city_button city_button' + city[key] + '" data_index =' + city[key] + ' onclick="citychange(' + city[key] + ')">' + key + '</div></div>')
        }
    }
    for (key in city2) {
        if (city[key] == 5108) {
            $('.row_city_button2').append('<div class="city_button_width"><div class="city_button2 city_button2' + city[key] + ' activity" data_index =' + city2[key] + ' onclick="citychange2(' + city2[key] + ')">' + key + '</div></div>')
        } else {
            $('.row_city_button2').append('<div class="city_button_width"><div class="city_button2 city_button2' + city[key] + '" data_index =' + city2[key] + ' onclick="citychange2(' + city2[key] + ')">' + key + '</div></div>')
        }
    }


}());
function loading(i, id) {
    if (i) {
        $('.' + id).css('display', 'block')
    } else {
        $('.' + id).css('display', 'none')
    }
}
function citychange(key) {
    $('.city_button').removeClass('activity')
    $('.city_button' + key).addClass('activity')
    search2()
}
function citychange2(key) {
    $('.city_button2').removeClass('activity')
    $('.city_button2' + key).addClass('activity')
    search3()
}
function timechange(key) {
    $('.contami2_button').removeClass('contami2_button-active')
    $('.' + key).addClass('contami2_button-active')
    if (key == 'month') {
        $('#time_change4').html(timeday)
        $('#time_change5').html(timeday)
        document.getElementById('time_change4').options[0].selected = true;
        document.getElementById('time_change5').options[0].selected = true;
    } else if (key == 'quarter') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2000;
        var quarter = 0

        if (month <= 3) {
            quarter = 1
        } else if (month <= 6) {
            quarter = 2
        } else if (month <= 9) {
            quarter = 3
        } else if (month <= 12) {
            quarter = 4
        }

        var str = '';
        for (var i = quarter; i >= 1; i--) {
            str += '<option>' + year + '年第' + i + '季度' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {
            for (var j = 4; j >= 1; j--) {
                str += "<option>" + (year - i) + '年第' + j + '季度' + "</option>"
            }
        }
        $('#time_change4').html(str)
        $('#time_change5').html(str)
        document.getElementById('time_change4').options[0].selected = true;
        document.getElementById('time_change5').options[0].selected = true;

    } else if (key == 'middle_year') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2000;
        var quarter = 0
        if (month <= 6) {
            quarter = 1
        } else if (6 < month <= 12) {
            quarter = 2
        }

        var str = '';
        if (quarter == 1) {
            str += '<option>' + year + '上半年' + '</option>';
        } else {
            str += '<option>' + year + '下半年' + '</option>' + '<option>' + year + '上半年' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {

            str += '<option>' + (year - i) + '下半年' + '</option>' + '<option>' + (year - i) + '上半年' + '</option>';

        }
        $('#time_change4').html(str)
        $('#time_change5').html(str)
        document.getElementById('time_change4').options[0].selected = true;
        document.getElementById('time_change5').options[0].selected = true;
    } else if (key == 'year') {
        var year = new Date().getFullYear();
        var line = year - 2000;
        for (var i = 0; i < line - 1; i++) {
            str += '<option>' + (year - i) + '年' + '</option>';
        }
        $('#time_change4').html(str)
        $('#time_change5').html(str)
        document.getElementById('time_change4').options[0].selected = true;
        document.getElementById('time_change5').options[0].selected = true;
    }
}
function timechange2(key) {
    $('.contami3_button').removeClass('contami3_button-active')
    $('.' + key).addClass('contami3_button-active')
    if (key == 'month2') {
        $('#time_change6').html(timeday)
        $('#time_change7').html(timeday)
        document.getElementById('time_change6').options[0].selected = true;
        document.getElementById('time_change7').options[0].selected = true;
    } else if (key == 'quarter2') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2000;
        var quarter = 0

        if (month <= 3) {
            quarter = 1
        } else if (month <= 6) {
            quarter = 2
        } else if (month <= 9) {
            quarter = 3
        } else if (month <= 12) {
            quarter = 4
        }

        var str = '';
        for (var i = quarter; i >= 1; i--) {
            str += '<option>' + year + '年第' + i + '季度' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {
            for (var j = 4; j >= 1; j--) {
                str += "<option>" + (year - i) + '年第' + j + '季度' + "</option>"
            }
        }
        $('#time_change6').html(str)
        $('#time_change7').html(str)
        document.getElementById('time_change6').options[0].selected = true;
        document.getElementById('time_change7').options[0].selected = true;

    } else if (key == 'middle_year2') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2000;
        var quarter = 0
        if (month <= 6) {
            quarter = 1
        } else if (6 < month <= 12) {
            quarter = 2
        }

        var str = '';
        if (quarter == 1) {
            str += '<option>' + year + '上半年' + '</option>';
        } else {
            str += '<option>' + year + '下半年' + '</option>' + '<option>' + year + '上半年' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {

            str += '<option>' + (year - i) + '下半年' + '</option>' + '<option>' + (year - i) + '上半年' + '</option>';

        }
        $('#time_change6').html(str)
        $('#time_change7').html(str)
        document.getElementById('time_change6').options[0].selected = true;
        document.getElementById('time_change7').options[0].selected = true;
    } else if (key == 'year2') {
        var year = new Date().getFullYear();
        var line = year - 2000;
        for (var i = 0; i < line - 1; i++) {
            str += '<option>' + (year - i) + '年' + '</option>';
        }
        $('#time_change6').html(str)
        $('#time_change7').html(str)
        document.getElementById('time_change6').options[0].selected = true;
        document.getElementById('time_change7').options[0].selected = true;
    }
}
function timechange3(key) {
    $('.contami4_button').removeClass('contami4_button-active')
    $('.' + key).addClass('contami4_button-active')
    if (key == 'month3') {
        $('#time_change8').html(timeday)
        $('#time_change9').html(timeday)
        document.getElementById('time_change8').options[0].selected = true;
        document.getElementById('time_change9').options[0].selected = true;
    } else if (key == 'quarter3') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2000;
        var quarter = 0

        if (month <= 3) {
            quarter = 1
        } else if (month <= 6) {
            quarter = 2
        } else if (month <= 9) {
            quarter = 3
        } else if (month <= 12) {
            quarter = 4
        }

        var str = '';
        for (var i = quarter; i >= 1; i--) {
            str += '<option>' + year + '年第' + i + '季度' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {
            for (var j = 4; j >= 1; j--) {
                str += "<option>" + (year - i) + '年第' + j + '季度' + "</option>"
            }
        }
        $('#time_change8').html(str)
        $('#time_change9').html(str)
        document.getElementById('time_change8').options[0].selected = true;
        document.getElementById('time_change9').options[0].selected = true;

    } else if (key == 'middle_year3') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2000;
        var quarter = 0
        if (month <= 6) {
            quarter = 1
        } else if (6 < month <= 12) {
            quarter = 2
        }

        var str = '';
        if (quarter == 1) {
            str += '<option>' + year + '上半年' + '</option>';
        } else {
            str += '<option>' + year + '下半年' + '</option>' + '<option>' + year + '上半年' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {

            str += '<option>' + (year - i) + '下半年' + '</option>' + '<option>' + (year - i) + '上半年' + '</option>';

        }
        $('#time_change8').html(str)
        $('#time_change9').html(str)
        document.getElementById('time_change8').options[0].selected = true;
        document.getElementById('time_change9').options[0].selected = true;
    } else if (key == 'year3') {
        var year = new Date().getFullYear();
        var line = year - 2000;
        for (var i = 0; i < line - 1; i++) {
            str += '<option>' + (year - i) + '年' + '</option>';
        }
        $('#time_change8').html(str)
        $('#time_change9').html(str)
        document.getElementById('time_change8').options[0].selected = true;
        document.getElementById('time_change9').options[0].selected = true;
    }
}
function timechange4(key) {
    $('.contami5_button').removeClass('contami5_button-active')
    $('.' + key).addClass('contami5_button-active')
    if (key == 'month4') {
        $('#timecount_time1').show()
        $('#timecount_time2').show()
        $('#timeconutinfo').hide()
        $('#timeconutinfo2').hide()
        $('#timecount_time1').html(timeday2)
        $('#timecount_time2').html(timeday2)
        document.getElementById('timecount_time1').options[0].selected = true;
        document.getElementById('timecount_time2').options[0].selected = true;
    } else if (key == 'quarter4') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2012;
        var quarter = 0

        if (month <= 3) {
            quarter = 1
        } else if (month <= 6) {
            quarter = 2
        } else if (month <= 9) {
            quarter = 3
        } else if (month <= 12) {
            quarter = 4
        }

        var str = '';
        for (var i = quarter; i >= 1; i--) {
            str += '<option>' + year + '年第' + i + '季度' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {
            for (var j = 4; j >= 1; j--) {
                str += "<option>" + (year - i) + '年第' + j + '季度' + "</option>"
            }
        }
        $('#timecount_time1').show()
        $('#timecount_time2').show()
        $('#timeconutinfo').hide()
        $('#timeconutinfo2').hide()
        $('#timecount_time1').html(str)
        $('#timecount_time2').html(str)
        document.getElementById('timecount_time1').options[0].selected = true;
        document.getElementById('timecount_time2').options[0].selected = true;

    } else if (key == 'middle_year4') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2012;
        var quarter = 0
        if (month <= 6) {
            quarter = 1
        } else if (6 < month <= 12) {
            quarter = 2
        }

        var str = '';
        if (quarter == 1) {
            str += '<option>' + year + '上半年' + '</option>';
        } else {
            str += '<option>' + year + '下半年' + '</option>' + '<option>' + year + '上半年' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {

            str += '<option>' + (year - i) + '下半年' + '</option>' + '<option>' + (year - i) + '上半年' + '</option>';

        }
        $('#timecount_time1').show()
        $('#timecount_time2').show()
        $('#timeconutinfo').hide()
        $('#timeconutinfo2').hide()
        $('#timecount_time1').html(str)
        $('#timecount_time2').html(str)
        document.getElementById('timecount_time1').options[0].selected = true;
        document.getElementById('timecount_time2').options[0].selected = true;
    } else if (key == 'year4') {
        var year = new Date().getFullYear();
        var line = year - 2012;
        for (var i = 0; i < line - 1; i++) {
            str += '<option>' + (year - i) + '年' + '</option>';
        }
        $('#timecount_time1').show()
        $('#timecount_time2').show()
        $('#timeconutinfo').hide()
        $('#timeconutinfo2').hide()
        $('#timecount_time1').html(str)
        $('#timecount_time2').html(str)
        document.getElementById('timecount_time1').options[0].selected = true;
        document.getElementById('timecount_time2').options[0].selected = true;
    } else if (key == 'soso') {
        $('#timecount_time1').hide()
        $('#timecount_time2').hide()
        $('#timeconutinfo').show()
        $('#timeconutinfo2').show()
    }
}
function timechange5(key) {
    $('.contami6_button').removeClass('contami6_button-active')
    $('.' + key).addClass('contami6_button-active')
    if (key == 'month5') {
        $('#citycount_time1').show()
        $('#citycount_time2').show()
        $('#citycountinfo').hide()
        $('#citycountinfo2').hide()
        $('#citycount_time1').html(timeday2)
        $('#citycount_time2').html(timeday2)
        document.getElementById('citycount_time1').options[0].selected = true;
        document.getElementById('citycount_time2').options[0].selected = true;
    } else if (key == 'quarter5') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2012;
        var quarter = 0

        if (month <= 3) {
            quarter = 1
        } else if (month <= 6) {
            quarter = 2
        } else if (month <= 9) {
            quarter = 3
        } else if (month <= 12) {
            quarter = 4
        }

        var str = '';
        for (var i = quarter; i >= 1; i--) {
            str += '<option>' + year + '年第' + i + '季度' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {
            for (var j = 4; j >= 1; j--) {
                str += "<option>" + (year - i) + '年第' + j + '季度' + "</option>"
            }
        }
        $('#citycount_time1').show()
        $('#citycount_time2').show()
        $('#citycountinfo').hide()
        $('#citycountinfo2').hide()
        $('#citycount_time1').html(str)
        $('#citycount_time2').html(str)
        document.getElementById('citycount_time1').options[0].selected = true;
        document.getElementById('citycount_time2').options[0].selected = true;

    } else if (key == 'middle_year5') {
        var year = new Date().getFullYear();
        var month = new Date().getMonth() + 1
        var line = year - 2012;
        var quarter = 0
        if (month <= 6) {
            quarter = 1
        } else if (6 < month <= 12) {
            quarter = 2
        }

        var str = '';
        if (quarter == 1) {
            str += '<option>' + year + '上半年' + '</option>';
        } else {
            str += '<option>' + year + '下半年' + '</option>' + '<option>' + year + '上半年' + '</option>';
        }
        for (var i = 1; i < line - 1; i++) {

            str += '<option>' + (year - i) + '下半年' + '</option>' + '<option>' + (year - i) + '上半年' + '</option>';

        }
        $('#citycount_time1').show()
        $('#citycount_time2').show()
        $('#citycountinfo').hide()
        $('#citycountinfo2').hide()
        $('#citycount_time1').html(str)
        $('#citycount_time2').html(str)
        document.getElementById('citycount_time1').options[0].selected = true;
        document.getElementById('citycount_time2').options[0].selected = true;
    } else if (key == 'year5') {
        var year = new Date().getFullYear();
        var line = year - 2012;
        for (var i = 0; i < line - 1; i++) {
            str += '<option>' + (year - i) + '年' + '</option>';
        }
        $('#citycount_time1').show()
        $('#citycount_time2').show()
        $('#citycountinfo').hide()
        $('#citycountinfo2').hide()
        $('#citycount_time1').html(str)
        $('#citycount_time2').html(str)
        document.getElementById('citycount_time1').options[0].selected = true;
        document.getElementById('citycount_time2').options[0].selected = true;
    } else if (key == 'soso2') {
        $('#citycount_time1').hide()
        $('#citycount_time2').hide()
        $('#citycountinfo').show()
        $('#citycountinfo2').show()
    }
}
//  区域性污染判定初始化事件
function search() {
    var timeing = $('#time_change').val()
    var time = timeing.split('年')
    var year = parseInt(time[0])
    var month = parseInt(time[1].split('月')[0])
    var params = 'monthtime=' + year + '-' + month + '-01'
    loading(true, 'loader7')
    common.regionalpollution(params, function (data) {
        loading(false, 'loader7')
        if (data.allPollutionAea.length != 0) {
            var str = {list: data.allPollutionAea};
            var regionals = template("regional_pollution", str);
            document.getElementById("regional").innerHTML = regionals;
        } else {
            $('.alert_text_new').html('未查询到任何数据')
            $('.about_alert_new').css('visibility', 'visible')
        }
        for (var i = 0; i < data.allPollutionAea.length; i++) {
            if (data.allPollutionAea[i].status != undefined && data.allPollutionAea[i].status != '0') {
                $(".regional_yes")[i].checked = true
            }
        }
    })
}
//  秸秆焚烧影响判定初始化事件
function search2() {
    var timeing = $('#time_change2').val()
    var time = timeing.split('年')
    var year = parseInt(time[0])
    var month = parseInt(time[1].split('月')[0])
    var params = '';
    for (var i = 0; i < $('.city_button').length; i++) {
        if ($($('.city_button')[i]).hasClass('activity')) {
            params += 'citycode=' + $($('.city_button')[i]).attr('data_index') + '&'
        }
    }
    params += 'monthtime=' + year + '-' + month + '-01'
    loading(true, 'loader7')
    common.burnpollution(params, function (data) {
        loading(false, 'loader7')
        if (data.burnPollution.length != 0) {
            var str = {list: data.burnPollution};
            var straws = template("straw_pollution", str);
            document.getElementById("straw").innerHTML = straws;
        } else {
            $('.alert_text_new').html('未查询到任何数据')
            $('.about_alert_new').css('visibility', 'visible')
        }
        for (var i = 0; i < data.burnPollution.length; i++) {
            if (data.burnPollution[i].status != undefined && data.burnPollution[i].status != '0') {
                $(".straw_yes")[i].checked = true
            }
        }
    })
}
//  沙尘影响判定初始化事件
function search3() {
    var timeing = $('#time_change3').val()
    var time = timeing.split('年')
    var year = parseInt(time[0])
    var month = parseInt(time[1].split('月')[0])
    var params = '';
    for (var i = 0; i < $('.city_button2').length; i++) {
        if ($($('.city_button2')[i]).hasClass('activity')) {
            params += 'citycode=' + $($('.city_button2')[i]).attr('data_index') + '&'
        }
    }
    params += 'monthtime=' + year + '-' + month + '-01'
    loading(true, 'loader7')
    common.sandpollution(params, function (data) {
        loading(false, 'loader7')
        if (data.sandPollution.length != 0) {
            for (var i = 0; i < data.sandPollution.length; i++) {
                data.sandPollution[i].PM2_5PM10 = (parseFloat(data.sandPollution[i].PM2_5PM10) * 100).toFixed(0)
            }
            var str = {list: data.sandPollution};
            var dusts = template("dust_pollution", str);
            document.getElementById("dust").innerHTML = dusts;
        } else {
            $('.alert_text_new').html('未查询到任何数据')
            $('.about_alert_new').css('visibility', 'visible')
        }
        for (var i = 0; i < data.sandPollution.length; i++) {
            if (data.sandPollution[i].status != undefined && data.sandPollution[i].status != '0') {
                $(".dust_yes")[i].checked = true
            }
        }

        for (var i = 0; i < $('.dust_active').length; i++) {
            if ($($('.dust_active')[i]).attr('data-number') * 1 <= 0.4) {
                $($('.dust_active')[i]).addClass('dust_activing')
            } else {
                $($('.dust_active')[i]).removeClass('dust_activing')
            }
        }
    })
}
// 区域性污染判定查询
$('#time_change').change(function () {
    search()
})
// 秸秆焚烧影响判定查询
// function forstraw() {
//     search2()
// }
$('#time_change2').change(function () {
    search2()
})
// 沙尘影响判定查询
// function fordust() {
//     search3()
// }
$('#time_change3').change(function () {
    search3()
})
// 区域性污染判定修改
function save() {
    var timeing = $('#time_change').val()
    var time = timeing.split('年')
    var year = parseInt(time[0])
    var month = parseInt(time[1].split('月')[0])
    var params = 'monthtime=' + year + '-' + month + '-01'
    common.regionalpollution(params, function (data) {
        var arr = []
        var arr2 = []
        for (var i = 0; i < $('.regional_yes').length; i++) {
            if ($($(".regional_yes")[i]).is(':checked')) {
                arr.push(data.allPollutionAea[i])
            } else {
                arr2.push(data.allPollutionAea[i])
            }
        }

        var params2 = []
        for (var i = 0; i < arr.length; i++) {
            if (arr[i].TimePoint == undefined) {
                arr[i].TimePoint = 0
            }
            var strs = '{\"monthtime\": \"' + arr[i].TimePoint.substring(0, 10) + '\",\"status\": 1}'
            params2.push(strs)
        }
        for (var i = 0; i < arr2.length; i++) {
            if (arr2[i].TimePoint == undefined) {
                arr2[i].TimePoint = 0
            }
            var strs = '{\"monthtime\": \"' + arr2[i].TimePoint.substring(0, 10) + '\",\"status\": 0}'
            params2.push(strs)
        }
        var paramss;
        if (data.days == 0) {
            paramss = 'jsondata=[' + params2 + ']&' + 'type=0'
        } else {
            paramss = 'jsondata=[' + params2 + ']&' + 'type=1'
        }
        common.saveareapollution(paramss, function (data) {
            if (data.result == '2') {
                $('.alert_text_new').html('保存成功')
                $('.about_alert_new').css('visibility', 'visible')
            } else if (data.result == '1') {
                $('.alert_text_new').html('添加成功')
                $('.about_alert_new').css('visibility', 'visible')
            } else {
                $('.alert_text_new').html('操作失败')
                $('.about_alert_new').css('visibility', 'visible')
            }
        })

    })

}
// 秸秆焚烧影响判定修改
function savestraw() {
    var timeing = $('#time_change2').val()
    var time = timeing.split('年')
    var year = parseInt(time[0])
    var month = parseInt(time[1].split('月')[0])
    var params = '';
    for (var i = 0; i < $('.city_button').length; i++) {
        if ($($('.city_button')[i]).hasClass('activity')) {
            params += 'citycode=' + $($('.city_button')[i]).attr('data_index') + '&'
        }
    }
    params += 'monthtime=' + year + '-' + month + '-01'
    common.burnpollution(params, function (data) {
        var arr = []
        var arr2 = []
        for (var i = 0; i < $('.straw_yes').length; i++) {
            if ($($(".straw_yes")[i]).is(':checked')) {
                arr.push(data.burnPollution[i])
            } else {
                arr2.push(data.burnPollution[i])
            }
        }

        var keys = {
            'NO2': '',
            'PM2_5': '',
            'cityName': '',
            'citycode': '',
            'O3': '',
            'SO2': '',
            'PM10': '',
            'CO': '',
            'timepoint': ''
        }

        var params2 = []
        for (var i = 0; i < arr.length; i++) {
            for (key in keys) {
                if (arr[i][key] == undefined) {
                    arr[i][key] = 0
                }
            }
            var strs = '{\"no2\":' + arr[i].NO2 + ',\"pm2_5\":' + arr[i].PM2_5 + ',\"cityname\": \"' + arr[i].cityName + '\",\"citycode\":' + arr[i].citycode + ',\"o3\":' + arr[i].O3 + ',\"so2\":' + arr[i].SO2 + ',\"pm10\":' + arr[i].PM10 + ',\"co\": ' + arr[i].CO + ',\"status\":' + 1 + ',\"monthtime\": \"' + arr[i].timepoint.substring(0, 10) + '\"}'
            params2.push(strs)
        }
        for (var i = 0; i < arr2.length; i++) {
            for (key in keys) {
                if (arr2[i][key] == undefined) {
                    arr2[i][key] = 0
                }
            }
            var strs = '{\"no2\":' + arr2[i].NO2 + ',\"pm2_5\":' + arr2[i].PM2_5 + ',\"cityname\": \"' + arr2[i].cityName + '\",\"citycode\":' + arr2[i].citycode + ',\"o3\":' + arr2[i].O3 + ',\"so2\":' + arr2[i].SO2 + ',\"pm10\":' + arr2[i].PM10 + ',\"co\": ' + arr2[i].CO + ',\"status\":' + 0 + ',\"monthtime\": \"' + arr2[i].timepoint.substring(0, 10) + '\"}'
            params2.push(strs)
        }
        var paramss;
        if (data.days == 0) {
            paramss = 'jsondata=[' + params2 + ']&' + 'type=0'
        } else {
            paramss = 'jsondata=[' + params2 + ']&' + 'type=1'
        }

        common.saveburnpollution(paramss, function (data) {
            if (data.result == '2') {
                $('.alert_text_new').html('保存成功')
                $('.about_alert_new').css('visibility', 'visible')
            } else if (data.result == '1') {
                $('.alert_text_new').html('添加成功')
                $('.about_alert_new').css('visibility', 'visible')
            } else {
                $('.alert_text_new').html('操作失败')
                $('.about_alert_new').css('visibility', 'visible')
            }
        })
    })


}
// 沙尘影响判定修改
function savedust() {
    var timeing = $('#time_change3').val()
    var time = timeing.split('年')
    var year = parseInt(time[0])
    var month = parseInt(time[1].split('月')[0])
    var params = '';
    for (var i = 0; i < $('.city_button2').length; i++) {
        if ($($('.city_button2')[i]).hasClass('activity')) {
            params += 'citycode=' + $($('.city_button2')[i]).attr('data_index') + '&'
        }
    }
    params += 'monthtime=' + year + '-' + month + '-01'
    common.sandpollution(params, function (data) {
        var arr = []
        var arr2 = []
        for (var i = 0; i < $('.dust_yes').length; i++) {
            if ($($(".dust_yes")[i]).is(':checked')) {
                arr.push(data.sandPollution[i])
            } else {
                arr2.push(data.sandPollution[i])
            }
        }
        var params2 = []

        var keys = {
            'NO2': '',
            'PM2_5': '',
            'cityName': '',
            'citycode': '',
            'O3': '',
            'PM2_5PM10': '',
            'SO2': '',
            'PM10': '',
            'CO': '',
            'timepoint': ''
        }
        for (var i = 0; i < arr.length; i++) {
            for (key in keys) {
                if (arr[i][key] == undefined) {
                    arr[i][key] = 0
                }
            }
            var strs = '{\"no2\":' + arr[i].NO2 + ',\"pm2_5\":' + arr[i].PM2_5 + ',\"cityname\": \"' + arr[i].cityName + '\",\"citycode\":' + arr[i].citycode + ',\"o3\":' + arr[i].O3 + ',\"pm2_5pm10\": ' + arr[i].PM2_5PM10 + ',\"so2\":' + arr[i].SO2 + ',\"pm10\":' + arr[i].PM10 + ',\"co\": ' + arr[i].CO + ',\"status\":' + 1 + ',\"monthtime\": \"' + arr[i].timepoint.substring(0, 10) + '\"}'
            params2.push(strs)
        }
        for (var i = 0; i < arr2.length; i++) {
            for (key in keys) {
                if (arr2[i][key] == undefined) {
                    arr2[i][key] = 0
                }
            }
            var strs = '{\"no2\":' + arr2[i].NO2 + ',\"pm2_5\":' + arr2[i].PM2_5 + ',\"cityname\": \"' + arr2[i].cityName + '\",\"citycode\":' + arr2[i].citycode + ',\"o3\":' + arr2[i].O3 + ',\"so2\":' + arr2[i].SO2 + ',\"pm2_5pm10\": ' + arr2[i].PM2_5PM10 + ',\"pm10\":' + arr2[i].PM10 + ',\"co\": ' + arr2[i].CO + ',\"status\":' + 0 + ',\"monthtime\": \"' + arr2[i].timepoint.substring(0, 10) + '\"}'
            params2.push(strs)
        }
        var paramss;
        if (data.days == 0) {
            paramss = 'jsondata=[' + params2 + ']&' + 'type=0'
        } else {
            paramss = 'jsondata=[' + params2 + ']&' + 'type=1'
        }

        common.savesandpollution(paramss, function (data) {
            if (data.result == '2') {
                $('.alert_text_new').html('保存成功')
                $('.about_alert_new').css('visibility', 'visible')
            } else if (data.result == '1') {
                $('.alert_text_new').html('添加成功')
                $('.about_alert_new').css('visibility', 'visible')
            } else {
                $('.alert_text_new').html('操作失败')
                $('.about_alert_new').css('visibility', 'visible')
            }
        })

    })

}
// 区域性污染判定详情
function regionalcontent(i) {
    var date = $('.regional_yes')[i].value
    var params = 'start=' + date + '&' + 'end=' + date
    common.regionalpollutionmsg(params, function (data) {
        var str = {list: data};
        var levels = {
            '1': '优',
            '2': '良',
            '3': '轻度污染',
            '4': '中度污染',
            '5': '重度污染',
            '6': '严重污染',
        }
        for (var i = 0; i < data.length; i++) {
            for (key in levels) {
                if (key == data[i].level) {
                    data[i].level = levels[key]
                }
            }
        }
        console.log(str)
        var regionalcontent = template("regionalcontent_pollution", str);
        document.getElementById("regional_content").innerHTML = regionalcontent;
    })
}
// 表格导出
function tableexport(tableid) {//整个表格拷贝到EXCEL中

    if (tableid == undefined) {
        var id = $(".active table").attr('id')
    } else {
        id = tableid
    }
    $('#exportTableForm').remove()
    var str = $(document.getElementById(id)).html()
    if (id == 'scoreDetails') {
        var dec = document.getElementById('decreasestationCode').options
        for (var i = 0; i < $('.decreasestationCodemo').length; i++) {
            if (dec[i].selected) {
                var strs = '<table>' + '<tr>' + '<th>' + dec[i].innerText + '</th>' + '</tr>' + str + '</table>'
            }
        }
    } else {
        var strs = '<table>' + str + '</table>'
    }
    var tablename = $.trim($('#' + id + ' tr:eq(0) th:eq(0)').html())
    var input1 = $('<input type="hidden" name="excelName" value=' + tablename + '/>')
    var input2 = $('<input type="hidden" name="cellValues"/>')
    var form = $('<form action=' + commonUrl + "excelexport/normalexport" + ' id="exportTableForm" method = "post"></form>')
    var strss = input2.val(strs)
    form.append(input1).append(strss)
    $('body').append(form)
    form.submit()
}
// 显示模块
function forwhat(index) {
    $('.navigation').removeClass('navigation_contami')
    $('.navigation_' + index).addClass('navigation_contami')
    $('.contami').hide()
    $('.' + index).show()

}
function forwhat2(index) {
    $('.navigation2').removeClass('navigation2_contami')
    $('.navigation2_' + index).addClass('navigation2_contami')
    $('.contami2').hide()
    $('.' + index + '2').show()

}
function forwhat3(index) {
    $('.navigation3').removeClass('navigation3_contami')
    $('.navigation3_' + index).addClass('navigation3_contami')
    $('.contami3').hide()
    $('.' + index).show()
}
function forfind(index) {
    $('.conta_navigation').removeClass('conta_navigation_active')
    $('.conta_navigation_' + index).addClass('conta_navigation_active')
    $('.conta_body_').hide()
    $('.' + index).show()
}
// 统计区域性污染
function search4() {
    var times = $($('.contami2_button-active')).attr('data-index')
    var params = '';
    var timetitle = ''
    if (times == '月统计数据') {
        timetitle = '月份'
        var timeing = $('#time_change4').val()
        var time = timeing.split('年')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('月')[0])
        var timeing2 = $('#time_change5').val()
        var time2 = timeing2.split('年')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('月')[0])
        params += 'start=' + year + '-' + month + '-01' + '&' + 'end=' + year2 + '-' + month2 + '-01' + '&' + 'type=1'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '季度统计数据') {
        timetitle = '季度'
        var timeing = $('#time_change4').val()
        var time = timeing.split('年第')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('季度')[0])
        var timeing2 = $('#time_change5').val()
        var time2 = timeing2.split('年第')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('季度')[0])
        params += 'start=' + year + '-' + ((month * 3) - 2) + '-01' + '&' + 'end=' + year2 + '-' + ((month2 * 3) - 2) + '-01' + '&' + 'type=2'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '半年统计数据') {
        timetitle = '半年'
        var timeing = $('#time_change4').val()
        var year = timeing.substring(0, 4)
        var month = timeing.substring(4, 7)
        var timeing2 = $('#time_change5').val()
        var year2 = timeing2.substring(0, 4)
        var month2 = timeing2.substring(4, 7)
        var monthing;
        var monthing2;
        if (month == '上半年') {
            monthing = '01'
        } else {
            monthing = '07'
        }
        if (month2 == '上半年') {
            monthing2 = '01'
        } else {
            monthing2 = '07'
        }
        params += 'start=' + year + '-' + monthing + '-01' + '&' + 'end=' + year2 + '-' + monthing2 + '-01' + '&' + 'type=3'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && monthing2 == '01' && monthing == '07') {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else {
        timetitle = '年'
        var timeing = $('#time_change4').val()
        var year = timeing.substring(0, 4)
        var timeing2 = $('#time_change5').val()
        var year2 = timeing2.substring(0, 4)
        params += 'start=' + year + '-' + '01' + '-01' + '&' + 'end=' + year2 + '-' + '01' + '-01' + '&' + 'type=4'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    }
    var pm2_5 = $('.target_pm25-now input').val() * 1
    var pm10 = $('.target_pm10-now input').val() * 1
    var pm2_52 = $('.target_pm25-old input').val() * 1
    var pm102 = $('.target_pm10-old input').val() * 1

    if (pm2_5 == 0 || pm10 == 0 || pm2_52 == 0 || pm102 == 0) {
        $('.alert_text_new').html('目标浓度值不能为空')
        $('.about_alert_new').css('visibility', 'visible')
        return false
    }


    params += '&' + 'pm2_5goal=' + pm2_5 + '&' + 'pm10goal=' + pm10 + '&' + 'opm2_5goal=' + pm2_52 + '&' + 'opm10goal=' + pm102
    loading(true, 'loader7')
    common.regionalpollutioncount(params, function (data) {
        loading(false, 'loader7')
        var name = $($('.contami2_button-active')).attr('data-index')
        for (var i = 0; i < data.length; i++) {
            data[i].YOYdays = (parseFloat(data[i].YOYdays)).toFixed(0)
            data[i].YOYpm2_5 = (parseFloat(data[i].YOYpm2_5) * 100).toFixed(1)
            data[i].YOYpm10 = (parseFloat(data[i].YOYpm10) * 100).toFixed(1)
            data[i].pm2_5Cgrowthtimes = (parseFloat(data[i].pm2_5Cgrowthtimes) * 100).toFixed(1)
            data[i].pm10contributetimes = (parseFloat(data[i].pm10contributetimes) * 100).toFixed(1)
            data[i].pm2_5loadtimes = (parseFloat(data[i].pm2_5loadtimes) * 100).toFixed(1)
            data[i].pm10loadtimes = (parseFloat(data[i].pm10loadtimes) * 100).toFixed(1)
            data[i].Mpm10load = (parseFloat(data[i].Mpm10load) * 100).toFixed(1)
            data[i].Mpm2_5load = (parseFloat(data[i].Mpm2_5load) * 100).toFixed(1)
        }

        if (timetitle == '月份') {
            for (var i = 0; i < data.length; i++) {
                data[i].monthTime = data[i].monthTime.substring(0, 7)
            }
        } else if (timetitle == '季度') {
            for (var i = 0; i < data.length; i++) {
                var arr = data[i].monthTime.split('-')
                if (arr[1] == '01' || arr[1] == '1') {
                    data[i].monthTime = arr[0] + '年第一季度'
                } else if (arr[1] == '04' || arr[1] == '4') {
                    data[i].monthTime = arr[0] + '年第二季度'
                } else if (arr[1] == '07' || arr[1] == '7') {
                    data[i].monthTime = arr[0] + '年第三季度'
                } else if (arr[1] == '10') {
                    data[i].monthTime = arr[0] + '年第四季度'
                }
            }
        } else if (timetitle == '半年') {
            for (var i = 0; i < data.length; i++) {
                var arr = data[i].monthTime.split('-')
                if (arr[1] == '01') {
                    data[i].monthTime = arr[0] + '年上半年'
                } else if (arr[1] == '07') {
                    data[i].monthTime = arr[0] + '年下半年'
                }
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                data[i].monthTime = data[i].monthTime.substring(0, 4) + '年'
            }
        }

        var str = {list: data};
        var timestart = $('#time_change4').val().substring(0, 4)
        var timeend = $('#time_change5').val().substring(0, 4)
        str['name'] = name
        str['timestart'] = timestart
        str['timeend'] = timeend
        str['timetitle'] = timetitle
        var regional2 = template("regional_pollution2", str);
        document.getElementById("regional2").innerHTML = regional2;
    })

}
// 统计区域性污染查询
function forregional() {
    search4()
}
// 秸秆焚烧影响统计
function search5() {
    var times = $($('.contami3_button-active')).attr('data-index')
    var params = '';
    var timetitle = '';
    if (times == '月统计数据') {
        timetitle = '月份'
        var timeing = $('#time_change6').val()
        var time = timeing.split('年')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('月')[0])
        var timeing2 = $('#time_change7').val()
        var time2 = timeing2.split('年')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('月')[0])
        params += 'start=' + year + '-' + month + '-01' + '&' + 'end=' + year2 + '-' + month2 + '-01' + '&' + 'type=1'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '季度统计数据') {
        timetitle = '季度'
        var timeing = $('#time_change6').val()
        var time = timeing.split('年第')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('季度')[0])
        var timeing2 = $('#time_change7').val()
        var time2 = timeing2.split('年第')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('季度')[0])
        params += 'start=' + year + '-' + ((month * 3) - 2) + '-01' + '&' + 'end=' + year2 + '-' + ((month2 * 3) - 2) + '-01' + '&' + 'type=2'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '半年统计数据') {
        timetitle = '半年'
        var timeing = $('#time_change6').val()
        var year = timeing.substring(0, 4)
        var month = timeing.substring(4, 7)
        var timeing2 = $('#time_change7').val()
        var year2 = timeing2.substring(0, 4)
        var month2 = timeing2.substring(4, 7)
        var monthing;
        var monthing2;
        if (month == '上半年') {
            monthing = '01'
        } else {
            monthing = '07'
        }
        if (month2 == '上半年') {
            monthing2 = '01'
        } else {
            monthing2 = '07'
        }
        params += 'start=' + year + '-' + monthing + '-01' + '&' + 'end=' + year2 + '-' + monthing2 + '-01' + '&' + 'type=3'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && monthing2 == '01' && monthing == '07') {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else {
        timetitle = '年'
        var timeing = $('#time_change6').val()
        var year = timeing.substring(0, 4)
        var timeing2 = $('#time_change7').val()
        var year2 = timeing2.substring(0, 4)
        params += 'start=' + year + '-' + '01' + '-01' + '&' + 'end=' + year2 + '-' + '01' + '-01' + '&' + 'type=4'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    }
    var pm2_5 = $('.target_pm25-now2 input').val() * 1
    var pm10 = $('.target_pm10-now2 input').val() * 1
    if (pm2_5 == 0 || pm10 == 0) {
        $('.alert_text_new').html('目标浓度值不能为空')
        $('.about_alert_new').css('visibility', 'visible')
        return false
    }
    params += '&' + 'pm2_5goal=' + pm2_5 + '&' + 'pm10goal=' + pm10
    loading(true, 'loader7')
    common.burnpollutioncount(params, function (data) {
        loading(false, 'loader7')
        var name = $($('.contami3_button-active')).attr('data-index')
        for (var i = 0; i < data.length; i++) {
            data[i].countpm2_5M = (parseFloat(data[i].countpm2_5M) * 100).toFixed(0)
            data[i].countpm10M = (parseFloat(data[i].countpm10M) * 100).toFixed(0)
        }
        if (timetitle == '月份') {
            for (var i = 0; i < data.length; i++) {
                data[i].timepoint = data[i].timepoint.substring(0, 7)
            }
        } else if (timetitle == '季度') {
            for (var i = 0; i < data.length; i++) {
                var arr = data[i].timepoint.split('-')
                if (arr[1] == '01' || arr[1] == '1') {
                    data[i].timepoint = arr[0] + '年第一季度'
                } else if (arr[1] == '04' || arr[1] == '4') {
                    data[i].timepoint = arr[0] + '年第二季度'
                } else if (arr[1] == '07' || arr[1] == '7') {
                    data[i].timepoint = arr[0] + '年第三季度'
                } else if (arr[1] == '10') {
                    data[i].timepoint = arr[0] + '年第四季度'
                }
            }
        } else if (timetitle == '半年') {
            for (var i = 0; i < data.length; i++) {
                var arr = data[i].timepoint.split('-')
                if (arr[1] == '01') {
                    data[i].timepoint = arr[0] + '年上半年'
                } else if (arr[1] == '07') {
                    data[i].timepoint = arr[0] + '年下半年'
                }
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                data[i].timepoint = data[i].timepoint.substring(0, 4) + '年'
            }
        }

        var str = {list: data};
        var timestart = $('#time_change6').val().substring(0, 4)
        var timeend = $('#time_change7').val().substring(0, 4)
        str['name'] = name
        str['timestart'] = timestart
        str['timeend'] = timeend
        str['timetitle'] = timetitle
        console.log(str)
        var straws = template("straw2_pollution2", str);
        document.getElementById("straw2").innerHTML = straws;
    })

}
function forstraw2() {
    search5()
}
// 沙尘影响统计
function search6() {

    var times = $($('.contami4_button-active')).attr('data-index')
    var params = '';
    var timetitle = '';
    if (times == '月统计数据') {
        timetitle = '月份'
        var timeing = $('#time_change8').val()
        var time = timeing.split('年')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('月')[0])
        var timeing2 = $('#time_change9').val()
        var time2 = timeing2.split('年')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('月')[0])
        params += 'start=' + year + '-' + month + '-01' + '&' + 'end=' + year2 + '-' + month2 + '-01' + '&' + 'type=1'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '季度统计数据') {
        timetitle = '季度'
        var timeing = $('#time_change8').val()
        var time = timeing.split('年第')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('季度')[0])
        var timeing2 = $('#time_change9').val()
        var time2 = timeing2.split('年第')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('季度')[0])
        params += 'start=' + year + '-' + ((month * 3) - 2) + '-01' + '&' + 'end=' + year2 + '-' + ((month2 * 3) - 2) + '-01' + '&' + 'type=2'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '半年统计数据') {
        timetitle = '半年'
        var timeing = $('#time_change8').val()
        var year = timeing.substring(0, 4)
        var month = timeing.substring(4, 7)
        var timeing2 = $('#time_change9').val()
        var year2 = timeing2.substring(0, 4)
        var month2 = timeing2.substring(4, 7)
        var monthing;
        var monthing2;
        if (month == '上半年') {
            monthing = '01'
        } else {
            monthing = '07'
        }
        if (month2 == '上半年') {
            monthing2 = '01'
        } else {
            monthing2 = '07'
        }
        params += 'start=' + year + '-' + monthing + '-01' + '&' + 'end=' + year2 + '-' + monthing2 + '-01' + '&' + 'type=3'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && monthing2 == '01' && monthing == '07') {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else {
        timetitle = '年'
        var timeing = $('#time_change8').val()
        var year = timeing.substring(0, 4)
        var timeing2 = $('#time_change9').val()
        var year2 = timeing2.substring(0, 4)
        params += 'start=' + year + '-' + '01' + '-01' + '&' + 'end=' + year2 + '-' + '01' + '-01' + '&' + 'type=4'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    }
    var pm2_5 = $('.target_pm25-now3 input').val() * 1
    var pm10 = $('.target_pm10-now3 input').val() * 1
    if (pm2_5 == 0 || pm10 == 0) {
        $('.alert_text_new').html('目标浓度值不能为空')
        $('.about_alert_new').css('visibility', 'visible')
        return false
    }
    params += '&' + 'pm2_5goal=' + pm2_5 + '&' + 'pm10goal=' + pm10
    loading(true, 'loader7')
    common.sandpollutioncount(params, function (data) {
        loading(false, 'loader7')
        var name = $($('.contami4_button-active')).attr('data-index')
        for (var i = 0; i < data.length; i++) {
            data[i].countpm2_5M = (parseFloat(data[i].countpm2_5M) * 100).toFixed(0)
            data[i].countpm10M = (parseFloat(data[i].countpm10M) * 100).toFixed(0)
        }
        if (timetitle == '月份') {
            for (var i = 0; i < data.length; i++) {
                data[i].timepoint = data[i].timepoint.substring(0, 7)
            }
        } else if (timetitle == '季度') {
            for (var i = 0; i < data.length; i++) {
                var arr = data[i].timepoint.split('-')
                if (arr[1] == '01' || arr[1] == '1') {
                    data[i].timepoint = arr[0] + '年第一季度'
                } else if (arr[1] == '04' || arr[1] == '4') {
                    data[i].timepoint = arr[0] + '年第二季度'
                } else if (arr[1] == '07' || arr[1] == '7') {
                    data[i].timepoint = arr[0] + '年第三季度'
                } else if (arr[1] == '10') {
                    data[i].timepoint = arr[0] + '年第四季度'
                }
            }
        } else if (timetitle == '半年') {
            for (var i = 0; i < data.length; i++) {
                var arr = data[i].timepoint.split('-')
                if (arr[1] == '01') {
                    data[i].timepoint = arr[0] + '年上半年'
                } else if (arr[1] == '07') {
                    data[i].timepoint = arr[0] + '年下半年'
                }
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                data[i].timepoint = data[i].timepoint.substring(0, 4) + '年'
            }
        }

        var str = {list: data};
        var timestart = $('#time_change8').val().substring(0, 4)
        var timeend = $('#time_change9').val().substring(0, 4)
        str['name'] = name
        str['timestart'] = timestart
        str['timeend'] = timeend
        str['timetitle'] = timetitle
        var dusts = template("dust2_pollution2", str);
        document.getElementById("dust2").innerHTML = dusts;
    })
}

function fordust2() {
    search6()
}
// 时间段最大浓度统计
function search7() {

    var times = $($('.contami5_button-active')).attr('data-index')
    var params = '';
    var title = '';
    var time22 = '';
    var time23 = '';
    if (times == '月统计数据') {
        title = '各月';
        var timeing = $('#timecount_time1').val()
        var time = timeing.split('年')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('月')[0])
        var timeing2 = $('#timecount_time2').val()
        var time2 = timeing2.split('年')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('月')[0])
        time22 = year
        time23 = year2
        params += 'start=' + year + '-' + month + '-01' + '&' + 'end=' + year2 + '-' + month2 + '-01' + '&' + 'type=1'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '季度统计数据') {
        title = '各季度'
        var timeing = $('#timecount_time1').val()
        var time = timeing.split('年第')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('季度')[0])
        var timeing2 = $('#timecount_time2').val()
        var time2 = timeing2.split('年第')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('季度')[0])
        time22 = year
        time23 = year2
        params += 'start=' + year + '-' + ((month * 3) - 2) + '-01' + '&' + 'end=' + year2 + '-' + ((month2 * 3) - 2) + '-01' + '&' + 'type=2'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '半年统计数据') {
        title = '每半年'
        var timeing = $('#timecount_time1').val()
        var year = timeing.substring(0, 4)
        var month = timeing.substring(4, 7)
        var timeing2 = $('#timecount_time2').val()
        var year2 = timeing2.substring(0, 4)
        var month2 = timeing2.substring(4, 7)
        time22 = year
        time23 = year2
        var monthing;
        var monthing2;
        if (month == '上半年') {
            monthing = '01'
        } else {
            monthing = '07'
        }
        if (month2 == '上半年') {
            monthing2 = '01'
        } else {
            monthing2 = '07'
        }
        params += 'start=' + year + '-' + monthing + '-01' + '&' + 'end=' + year2 + '-' + monthing2 + '-01' + '&' + 'type=3'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && monthing2 == '01' && monthing == '07') {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '年统计数据') {
        title = '各年'
        var timeing = $('#timecount_time1').val()
        var year = timeing.substring(0, 4)
        var timeing2 = $('#timecount_time2').val()
        var year2 = timeing2.substring(0, 4)
        time22 = year
        time23 = year2
        params += 'start=' + year + '-' + '01' + '-01' + '&' + 'end=' + year2 + '-' + '01' + '-01' + '&' + 'type=4'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else {
        title = '各日'
        var timeing = $('#timeconutinfo').val()
        var timeing2 = $('#timeconutinfo2').val()
        var time1 = timeing.split('-')
        var time2 = timeing2.split('-')
        var year2 = new Date().getFullYear();
        var month = new Date().getMonth() + 1;
        var day = new Date().getDate();
        time22 = time1[0]
        time23 = time2[0]
        params += 'start=' + timeing + '&' + 'end=' + timeing2 + '&' + 'type=5'
        if (parseInt(time1[0]) > parseInt(time2[0])) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time1[0]) == parseInt(time2[0]) && parseInt(time1[1]) > parseInt(time2[1])) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time1[0]) == parseInt(time2[0]) && parseInt(time1[1]) == parseInt(time2[1]) && parseInt(time1[2]) > parseInt(time2[2])) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time2[0]) > year2) {
            $('.alert_text_new').html('截止时间不能大于当前日期')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time2[0]) == year2 && parseInt(time2[1]) > month) {
            $('.alert_text_new').html('截止时间不能大于当前日期')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time2[0]) == year2 && parseInt(time2[1]) == month && parseInt(time2[2]) > day) {
            $('.alert_text_new').html('截止时间不能大于当前日期')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    }

    var timecount_parameter = $('#timecount_parameter').val()
    if (timecount_parameter == 'O3') {
        params += '&' + 'param=O3_8h'
    } else if (timecount_parameter == 'PM2.5') {
        params += '&' + 'param=PM2_5'
    } else {
        params += '&' + 'param=' + timecount_parameter
    }
    loading(true, 'loader7')
    common.maxbytimecount(params, function (data) {
        loading(false, 'loader7')
        var name = $('#timecount_parameter').val()
        var year = new Date().getFullYear();
        for (var i = 0; i < data.length; i++) {
            data[i].YOYmax = (parseFloat(data[i].YOYmax) * 100).toFixed(0)
        }

        if (name == 'O3') {
            for (var i = 0; i < data.length; i++) {
                var key = 'abc'
                var key2 = 'def'
                var name2 = 'max' + name
                data[i][key] = data[i]['O3_8h']
                data[i][key2] = data[i]['maxO3_8h']
            }
        }else if (name == 'PM2.5') {
            for (var i = 0; i < data.length; i++) {
                var key = 'abc'
                var key2 = 'def'
                var name2 = 'max' + name
                data[i][key] = data[i]['PM2_5']
                data[i][key2] = data[i]['maxPM2_5']
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                var key = 'abc'
                var key2 = 'def'
                var name2 = 'max' + name
                data[i][key] = data[i][name]
                data[i][key2] = data[i][name2]
            }
        }
        var level = {
            '1': '优',
            '2': '良',
            '3': '轻度污染',
            '4': '中度污染',
            '5': '重度污染',
            '6': '严重污染',
        }
        for (var i = 0; i < data.length; i++) {
            for (key in level) {
                if (key == data[i].airlevel) {
                    data[i].airlevel = level[key]
                }
                if (key == data[i].oairlevel) {
                    data[i].oairlevel = level[key]
                }
            }
        }

        var str = {list: data};
        str['name'] = name;
        str['title'] = title;
        str['time22'] = time22;
        str['time23'] = time23;
        var timecount = template("timecountable", str);
        document.getElementById("timecount_table").innerHTML = timecount;
    })
}
function fortimecount() {
    search7()
}
// 时间段内城市最大浓度
function search8() {
    var times = $($('.contami6_button-active')).attr('data-index')
    var params = '';
    var title = '';
    var time22 = '';
    var time23 = '';
    if (times == '月统计数据') {
        title = '各月'
        var timeing = $('#citycount_time1').val()
        var time = timeing.split('年')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('月')[0])
        var timeing2 = $('#citycount_time2').val()
        var time2 = timeing2.split('年')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('月')[0])
        time22 = year
        time23 = year2
        params += 'start=' + year + '-' + month + '-01' + '&' + 'end=' + year2 + '-' + month2 + '-01' + '&' + 'type=1'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '季度统计数据') {
        title = '各季度'
        var timeing = $('#citycount_time1').val()
        var time = timeing.split('年第')
        var year = parseInt(time[0])
        var month = parseInt(time[1].split('季度')[0])
        var timeing2 = $('#citycount_time2').val()
        var time2 = timeing2.split('年第')
        var year2 = parseInt(time2[0])
        var month2 = parseInt(time2[1].split('季度')[0])
        time22 = year
        time23 = year2
        params += 'start=' + year + '-' + ((month * 3) - 2) + '-01' + '&' + 'end=' + year2 + '-' + ((month2 * 3) - 2) + '-01' + '&' + 'type=2'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && parseInt(month) > parseInt(month2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '半年统计数据') {
        title = '每半年'
        var timeing = $('#citycount_time1').val()
        var year = timeing.substring(0, 4)
        var month = timeing.substring(4, 7)
        var timeing2 = $('#citycount_time2').val()
        var year2 = timeing2.substring(0, 4)
        var month2 = timeing2.substring(4, 7)
        time22 = year
        time23 = year2
        var monthing;
        var monthing2;
        if (month == '上半年') {
            monthing = '01'
        } else {
            monthing = '07'
        }
        if (month2 == '上半年') {
            monthing2 = '01'
        } else {
            monthing2 = '07'
        }
        params += 'start=' + year + '-' + monthing + '-01' + '&' + 'end=' + year2 + '-' + monthing2 + '-01' + '&' + 'type=3'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(year) == parseInt(year2) && monthing2 == '01' && monthing == '07') {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else if (times == '年统计数据') {
        title = '各年'
        var timeing = $('#citycount_time1').val()
        var year = timeing.substring(0, 4)
        var timeing2 = $('#citycount_time2').val()
        var year2 = timeing2.substring(0, 4)
        time22 = year
        time23 = year2
        params += 'start=' + year + '-' + '01' + '-01' + '&' + 'end=' + year2 + '-' + '01' + '-01' + '&' + 'type=4'
        if (parseInt(year) > parseInt(year2)) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    } else {
        title = '各日'
        var timeing = $('#citycountinfo').val()
        var timeing2 = $('#citycountinfo2').val()
        var time1 = timeing.split('-')
        var time2 = timeing2.split('-')
        var year2 = new Date().getFullYear();
        var month = new Date().getMonth() + 1;
        var day = new Date().getDate();
        time22 = time1[0]
        time23 = time2[0]
        params += 'start=' + timeing + '&' + 'end=' + timeing2 + '&' + 'type=5'
        if (parseInt(time1[0]) > parseInt(time2[0])) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time1[0]) == parseInt(time2[0]) && parseInt(time1[1]) > parseInt(time2[1])) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time1[0]) == parseInt(time2[0]) && parseInt(time1[1]) == parseInt(time2[1]) && parseInt(time1[2]) > parseInt(time2[2])) {
            $('.alert_text_new').html('起始时间不能大于截止时间')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time2[0]) > year2) {
            $('.alert_text_new').html('截止时间不能大于当前日期')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time2[0]) == year2 && parseInt(time2[1]) > month) {
            $('.alert_text_new').html('截止时间不能大于当前日期')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        } else if (parseInt(time2[0]) == year2 && parseInt(time2[1]) == month && parseInt(time2[2]) > day) {
            $('.alert_text_new').html('截止时间不能大于当前日期')
            $('.about_alert_new').css('visibility', 'visible')
            return false
        }
    }

    var citycount_parameter = $('#citycount_parameter').val()
    if (citycount_parameter == 'O3') {
        params += '&' + 'param=O3_8h'
    } else if (citycount_parameter == 'PM2.5') {
        params += '&' + 'param=PM2_5'
    } else {
        params += '&' + 'param=' + citycount_parameter
    }
    loading(true, 'loader7')
    common.maxbycitycount(params, function (data) {
        loading(false, 'loader7')
        var name = $('#citycount_parameter').val()
        var year = new Date().getFullYear();
        for (var i = 0; i < data.length; i++) {
            data[i].YOYmax = (parseFloat(data[i].YOYmax) * 100).toFixed(0)
        }

        if (name == 'O3') {
            for (var i = 0; i < data.length; i++) {
                var key = 'abc'
                var key2 = 'def'
                var name2 = 'max' + name
                data[i][key] = data[i]['O3_8h']
                data[i][key2] = data[i]['maxO3_8h']
            }
        } else if (name == 'PM2.5') {
            for (var i = 0; i < data.length; i++) {
                var key = 'abc'
                var key2 = 'def'
                var name2 = 'max' + name
                data[i][key] = data[i]['PM2_5']
                data[i][key2] = data[i]['maxPM2_5']
            }
        } else {
            for (var i = 0; i < data.length; i++) {
                var key = 'abc'
                var key2 = 'def'
                var name2 = 'max' + name
                data[i][key] = data[i][name]
                data[i][key2] = data[i][name2]
            }
        }
        var level = {
            '1': '优',
            '2': '良',
            '3': '轻度污染',
            '4': '中度污染',
            '5': '重度污染',
            '6': '严重污染',
        }
        for (var i = 0; i < data.length; i++) {
            for (key in level) {
                if (key == data[i].airlevel) {
                    data[i].airlevel = level[key]
                }
                if (key == data[i].oairlevel) {
                    data[i].oairlevel = level[key]
                }
            }
        }
        var str = {list: data};
        str['name'] = name;
        str['title'] = title;
        str['time22'] = time22;
        str['time23'] = time23;
        var citycount = template("citycountable", str);
        document.getElementById("citycount_table").innerHTML = citycount;
    })
}
function forcitycount() {
    search8()
}
function closed() {
    $('.about_alert_new').css('visibility', 'hidden')
}