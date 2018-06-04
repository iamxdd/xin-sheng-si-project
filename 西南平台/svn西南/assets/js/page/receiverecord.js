
var  columns = [
          {
              field: 'codedate', // 返回json数据中的name
              title: '日期', // 表格表头显示文字
              align: 'center', // 左右居中
              valign: 'middle', // 上下居中
              formatter: function (value, row, index){ // 单元格格式化函数
                  return new Date(new Date(value)).format("yyyy-MM-dd");
              }
          },{
              field: 'name',
              title: '数据来源',
              align: 'center',
              valign: 'middle'
          },{
              field: 'calcMode',
              title: '数据包容量(MB)',
              align: 'center',
              valign: 'middle'
          },{
              field: 'state',
              title: '接收状态',
              align: 'center',
              valign: 'middle'
          },{
              field: 'codetime', // 返回json数据中的name
              title: '成功接收时间', // 表格表头显示文字
              align: 'center', // 左右居中
              valign: 'middle', // 上下居中
              formatter: function (value, row, index){ // 单元格格式化函数
                  return new Date(new Date(value)).format("yyyy-MM-dd hh:mm:ss");
              }
          }];

//开始时间
$('#rangeDatePickerstart').datepicker({
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
//结束时间
$('#rangeDatePickerend').datepicker({
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

inintData("http://127.0.0.1:8020/svn西南/assets/js/page/data.json",columns);
 function inintData(url,columns){
 	$("#table").bootstrapTable({ // 对应table标签的id
        	method: 'get',
//      contentType: "application/x-www-form-urlencoded",//必须要有！！！！
       		url:url,//要请求数据的文件路径

            dataType: "json",
            contentType: "application/x-www-form-urlencoded",
            striped:true,//隔行变色
            cache:false,  //是否使用缓存
            showColumns:false,// 列
            pagination: true, //分页
            paginationLoop:false,
            paginationPreText:'上一页',
            paginationNextText:'下一页',

            showPaginationSwitch:false,//是否显示数据条数选择框
            sortable: false,           //是否启用排序
            singleSelect: false,
            search:false, //显示搜索框
            buttonsAlign: "right", //按钮对齐方式
            showRefresh:false,//是否显示刷新按钮
            sidePagination: "server", //服务端处理分页
            pageNumber:1,
            pageSize:5,
            pageList:[5,10, 25, 50, 100],
            undefinedText:'--',
            uniqueId: "id", //每一行的唯一标识，一般为主键列
            queryParamsType:'',
            queryParams: queryParams,//传递参数（*）
            columns: columns,
            onLoadSuccess: function(res){  //加载成功时执行
            return res
      		},
		    onLoadError: function(){  //加载失败时执行
		            console.info("加载数据失败");
		    }

})
 }


//请求服务数据时所传参数
    function queryParams(params){
        return{
            //每页多少条数据
            pageSize: params.limit,
            //请求第几页
            pageIndex:params.pageNumber,
            datafrom:$('.datafrom').val(),
            startime:new Date($('.datestart').val()).getTime()/1000,
            endtime:new Date($('.dateend').val()).getTime()/1000,
            state:$('.datastate').val()
        }
    }
    
 //搜索
    function serachUser() {
        $("#table").bootstrapTable('refresh');
    }