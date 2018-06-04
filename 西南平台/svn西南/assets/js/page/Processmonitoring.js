
var  columns = [
          {
          	  field:'select',
              checkbox: true, // 显示一个勾选框
              align: 'center' // 居中显示
          },{
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
              valign: 'middle',
              formatter: function (value, row, index){ // 单元格格式化函数
              	var _text = '';
                  if(value=="1"){
                  	_text='成功'
                  }else{
                  	_text='失败'
                  }
                  return _text
              }
          },{
              field: 'codetime', // 返回json数据中的name
              title: '成功接收时间', // 表格表头显示文字
              align: 'center', // 左右居中
              valign: 'middle', // 上下居中
              formatter: function (value, row, index){ // 单元格格式化函数
                  return new Date(new Date(value)).format("yyyy-MM-dd hh:mm:ss");
              }
          },{
              title: "操作",
//            align: 'center',
              valign: 'middle',
              width: 180, // 定义列的宽度，单位为像素px
              formatter: function (value, row, index) {
                  return '<button  type="button"  class="btn btn-warning" onclick="download(\'' + row.id + '\')"><i class="iconfont icon-xiazaidownload140"></i>下载</button>'
                        +'<button type="button" class="btn btn-success" onclick="reissue(\'' + row.id + '\')"><i class="iconfont icon-bufa"></i>补发</button>';
                  
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

initableData("http://127.0.0.1:8020/svn西南/assets/js/page/data.json")
function initableData(url){
	$.ajax({  
            type : "GET",  
            url : url,  
            contentType: "application/json;charset=utf-8",  
            dataType: "json",  
            success:function (msg) {            
									 	$("#table").bootstrapTable({ // 对应table标签的id
							      	    data:msg.databufa.data,
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
							            sidePagination: "client", //服务端处理分页
							            pageNumber:msg.databufa.pageNumber,
							            pageSize:msg.databufa.rows,
							            pageList:[5,10, 25, 50, 100],
							            undefinedText:'--',
							            uniqueId: "id", //每一行的唯一标识，一般为主键列
							            queryParamsType:'',
							            queryParams: '',//传递参数（*）
							            columns: columns,        
											})
            },  
            error:function(msg,s){  
                console.log('error',s)
            }  
   			}); 

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
//      $("#table").bootstrapTable('refresh');
        initableData("http://127.0.0.1:8020/svn西南/assets/js/page/data.json")
    }
    
 /*下载*/
function download(){
	console.log('下载');
}
 /*补发*/
function reissue(){
	console.log('补发');
}