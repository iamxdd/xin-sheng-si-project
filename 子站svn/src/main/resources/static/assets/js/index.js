(function(){
    $('.header').lavalamp({
		easing: 'easeOutBack',
        setOnClick:true
	});
    
    $("#qualitySecondMenu li").click(function(){
        $("#qualitySecondMenu li").removeClass('checkActive');
        $(this).addClass('checkActive');
        console.log($(this).index())
    })
    $('#myTabs a').click(function (e) {
        e.preventDefault()
        $(this).tab('show')
    })

    
    getAllData({});


}())


function search(){
    /*
    alert($("#unit").val())
    alert($("#startyear").val())
    alert($("#startmonth").val())
    alert($("#endyear").val())
    alert($("#endmonth").val())
    alert($("#area").val())
    alert($("#category").val())
    alert($("#stationchose").val())*/
}



//获取六个表格的数据
function getAllData(params){
    //传输率
    common.monthtransfer(params,function(data){
        var str = {list:data};
        var chuanshulv = template('chuanshutable', str);
        document.getElementById('chuanshulv').innerHTML = chuanshulv;
    })
    //有效天数得分评价表
    common.monthvalidate(params,function(data){
        var str = {list:data};
        var hvalidate = template('validtable', str);
        document.getElementById('defenpingjiabiao').innerHTML = hvalidate;
    })
    //质控检查扣分表
    common.monthdeduct(params,function(data){
        var str = {list:data};
        var monthdeduct = template('monthdeduct',str);
        document.getElementById('jianchakoufenbiao').innerHTML = monthdeduct;
    })
    //运行单位运行质量评估表
    common.monthunitworking(params,function(data){
         var str = {list:data};
        var monthunitworking = template('monthunitworking',str);
        document.getElementById('zhiliangpinggubiao').innerHTML = monthunitworking;
    })
    //运行单位日常工作月评价表
    common.monthRunUnitQC(params,function(data){
         var str = {list:data};
        var monthRunUnitQC = template('monthRunUnitQC',str);
        document.getElementById('richanggongzuopingjiabiao').innerHTML = monthRunUnitQC;
    })
    //考核结果
    common.calscorce(params,function(data){
         var str = {list:data};
        var calscorce = template('calscorce',str);
        document.getElementById('jiancekaohejieguo').innerHTML = calscorce;
    })
  
}






