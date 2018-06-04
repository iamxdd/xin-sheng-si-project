var commonUrl ="http://localhost:8080/";

var common = {
    http:function(url,method,params,fn){
         var part = commonUrl+url;
         $.ajax({
               url:part,
               type:method,
               data:params,
               dataType:'json',
               success:function(data){
                    fn(data)
               },
               complete:function(XMLHttpRequest,status){
                        if (XMLHttpRequest.status==403){
                           
                        }
                        if(status=='error'){
                          
                        }
                        if(status=='timeout'){
                          
                        }
                }

         })
    },
    //传输率
    monthtransfer:function(params,fn){
        this.http("monthtransfer/findall","get",params,fn) 
    },
    //有效天数
    monthvalidate:function(params,fn){
        this.http("monthvalidate/findall","get",params,fn) 
    },
    //扣分表
    monthdeduct:function(params,fn){
        this.http("monthdeduct/findall","get",params,fn)
    },
    //运行单位质量评估
    monthunitworking:function(params,fn){
        this.http("monthrununitworking/findall","get",params,fn)
    },
    //运行单位日常工作月评价表
    monthRunUnitQC:function(params,fn){
        this.http("monthRunUnitQC/findall","get",params,fn)
    },
    //考核结果
    calscorce:function(params,fn){
        this.http("calscorce/findall","get",params,fn)
    }
}