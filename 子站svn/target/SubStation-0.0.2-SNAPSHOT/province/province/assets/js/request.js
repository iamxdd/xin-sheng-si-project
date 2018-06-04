// var commonUrl = "http://10.190.1.9:8080/";
var commonUrl ="/SubStation-0.0.2-SNAPSHOT/";
var common = {
    http: function (url, method, params, fn) {
        var part = commonUrl + url;
        $.ajax({
            url: part,
            type: method,
            data: params,
            dataType: 'json',
            success: function (data) {
                fn(data)
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
    },
    //传输率
    monthtransfer: function (params, fn) {
        this.http("scair/monthtransfer/findall", "get", params, fn)
    },
    //有效天数
    monthvalidate: function (params, fn) {
        this.http("scair/monthvalidate/findall", "get", params, fn)
    },
    //扣分表
    monthdeduct: function (params, fn) {
        this.http("scair/monthdeduct/findall", "get", params, fn)
    },
    //运行单位质量评估
    monthunitworking: function (params, fn) {
        this.http("scair/monthrununitworking/findall", "get", params, fn)
    },
    //运行单位日常工作月评价表
    monthRunUnitQC: function (params, fn) {
        this.http("scair/monthRunUnitQC/findall", "get", params, fn)
    },
    //考核结果
    calscorce: function (params, fn) {
        this.http("scair/calscorce/findall", "get", params, fn)
    },
    //新增获取站点
    getallstation: function (params, fn) {
        this.http("scair/sendqureydata/getallstation", "get", params, fn)
    },
    //获取检查人名单
    getcheckperson: function (params, fn) {
        this.http("scair/sendqureydata/getcheckperson", "get", params, fn)
    },
    //扣分表新增保存
    savedeductscore: function (params, fn) {
        this.http("scair/monthdeduct/savedeductscore", "get", params, fn)
    },
    //通过站点得到运行单位
    getRunMsgByStationCode: function (params, fn) {
        this.http("scair/sendqureydata/getRunMsgByStationCode", "get", params, fn)
    },
    //运行单位
    getrununit: function (params, fn) {
        this.http("scair/sendqureydata/getrununit", "get", params, fn)
    },
    //站点区域
    getcity: function (params, fn) {
        this.http("scair/sendqureydata/getcity", "get", params, fn)
    },
    //站点类型
    getstationtype: function (params, fn) {
        this.http("scair/sendqureydata/getstationtype", "get", params, fn)
    },
    //查询条件站点
    getstationmsg: function (params, fn) {
        this.http("scair/sendqureydata/getstationmsg", "get", params, fn)
    },
    //多条件查询传输率
    monthtransferConditions: function (params, fn) {
        this.http("scair/monthtransfer/findByConditions", "get", params, fn)
    },
    //多条件查询有效天数
    monthvalidateConditions: function (params, fn) {
        this.http("scair/monthvalidate/findByConditions", "get", params, fn)
    },
    //多条件查询日常工作
    monthrununitworkingConditions: function (params, fn) {
        this.http("scair/monthrununitworking/findByConditions", "get", params, fn)
    },
    //多条件查询质检扣分
    monthdeductConditions: function (params, fn) {
        this.http("scair/monthdeduct/findByConditions", "get", params, fn)
    },
    //多条件查询运行单位运行质量评估
    monthRunUnitQCConditions: function (params, fn) {
        this.http("scair/monthRunUnitQC/findByConditions", "get", params, fn)
    },
    //多条件查询得分
    calscorceConditions: function (params, fn) {
        this.http("scair/calscorce/findByConditions", "get", params, fn)
    },
    //查询所有扣分
    findByDetail: function (params, fn) {
        this.http("scair/monthdeduct/findByDetail", "get", params, fn)
    },
    //点击运行单位
    getrununitCode: function (params, fn) {
        this.http("scair/sendqureydata/getrununit", "get", params, fn)
    },
    //点击城市
    getcityCode: function (params, fn) {
        this.http("scair/sendqureydata/getcity", "get", params, fn)
    },
    //点击站点类型
    getstationtypeCode: function (params, fn) {
        this.http("scair/sendqureydata/getstationtype", "get", params, fn)
    },
    //删除扣分表
    deletedeductscore: function (params, fn) {
        this.http("scair/monthdeduct/deletedeductscore", "get", params, fn)
    },


//  ============================================================================
    //站点类型
    stationtype: function (params, fn) {
        this.http("scair/sendqureydata/getstationtype", "get", params, fn)
    },
//	运行单位
    rununit: function (params, fn) {
        this.http("scair/sendqureydata/getrununit", "get", params, fn)
    },
    //城市列表
    citylist: function (params, fn) {
        this.http("scair/sendqureydata/getstationmsgs", "get", params, fn)
    },
//	传输率
    transmission: function (params, fn) {
        this.http("scair/monthtransfer/findByConditions", "get", params, fn)
    },
//	有效天数
    workday: function (params, fn) {
        this.http("scair/monthvalidate/findByConditions", "get", params, fn)
    },
//	运行质量得分
    qualityscore: function (params, fn) {
        this.http('scair/calscorce/findByConditions', "get", params, fn)
    },
//运行单位运行质量
    runquality: function (params, fn) {
        this.http('scair/runcost/findByConditions', "get", params, fn)
    },
//运行费用配置
    runningCost: function (params, fn) {
        this.http('scair/runcost/getAllStationMsg', "get", params, fn)
    },
//运行费用保存
    saveRunCost: function (params, fn) {
        this.http('scair/runcost/saveRunCost', "post", params, fn)
    },
//	日常评估表
    daytest: function (params, fn) {
        this.http('scair/monthRunUnitQC/findByConditions', "get", params, fn)
    },
//	运行单位质量体系
    qualityunit: function (params, fn) {
        this.http('scair/monthrununitworking/findByConditions', "get", params, fn)
    },
//	监测仪器启用管理
    monitor: function (params, fn) {
        this.http('scair/openingmanage/findByConditions', "get", params, fn)
    },
//	扣分
    losepoint: function (params, fn) {
        this.http('scair/monthdeduct/findByConditions', "get", params, fn)
    },
//	基本保障
    basicsecurity: function (params, fn) {
        this.http('scair/basiccheck/findByConditions', "get", params, fn)
    },
//	导出数据
    excelexport: function (params, fn) {
        this.http('scair/excelexport/normalexport', "post", params, fn)
    },
    // 区域性污染判定
    regionalpollution: function (params, fn) {
        this.http('scair/pollution/regionalpollution', "post", params, fn)
    },
    // 保存区域性污染判定（人工判定）
    saveareapollution: function (params, fn) {
        this.http('scair/pollution/saveareapollution', "post", params, fn)
    },
    // 区域性污染判定详情
    regionalpollutionmsg: function (params, fn) {
        this.http('scair/pollution/regionalpollutionmsg', "post", params, fn)
    },
    // 秸秆焚烧影响判定
    burnpollution: function (params, fn) {
        this.http('scair/pollution/burnpollution', "post", params, fn)
    },
    // 保存秸秆焚烧影响
    saveburnpollution: function (params, fn) {
        this.http('scair/pollution/saveburnpollution', "post", params, fn)
    },
    // 沙尘影响判定
    sandpollution: function (params, fn) {
        this.http('scair/pollution/sandpollution', "post", params, fn)
    },
    // 保存沙尘影响判定
    savesandpollution: function (params, fn) {
        this.http('scair/pollution/savesandpollution', "post", params, fn)
    },
    // 区域性污染统计
    regionalpollutioncount: function (params, fn) {
        this.http('scair/pollution/regionalpollutioncount', "post", params, fn)
    },
    // 秸秆焚烧影响统计
    burnpollutioncount: function (params, fn) {
        this.http('scair/pollution/burnpollutioncount', "post", params, fn)
    },
    // 沙尘影响统计
    sandpollutioncount: function (params, fn) {
        this.http('scair/pollution/sandpollutioncount', "post", params, fn)
    },
    // 时间段最大浓度统计
    maxbytimecount: function (params, fn) {
        this.http('scair/pollution/maxbytimecount', "post", params, fn)
    },
    // 时间段内城市最大浓度
    maxbycitycount: function (params, fn) {
        this.http('scair/pollution/maxbycitycount', "post", params, fn)
    },
  
   /**
	 * Created by duhong on 2018/3/24
	 */
	//基本保障三级联动市级
	citylevel:function (params, fn) {
        this.http('scair/sendqureydata/getAllCity', "get", params, fn)
    },
    //基本保障三级联动区县
	districtlevel:function (params, fn) {
        this.http('scair/sendqureydata/getCounty', "get", params, fn)
    },
    //基本保障三级联动站点
	sitelevel:function (params, fn) {
        this.http('scair/sendqureydata/getStationCode', "get", params, fn)
   },
   
    //基本保障新增保存
	savebasiccheck:function (params, fn) {
        this.http('scair/basiccheck/savebasiccheck', "post", params, fn)
   },
   
   //基本保障详情表
	mybaseDetail:function (params, fn) {
        this.http('scair/basiccheck/findByDetail', "get", params, fn)
   },
   //基本保障详情表删除
	mybaseDetaildelete:function (params, fn) {
        this.http('scair/basiccheck/deletebasiccheck', "get", params, fn)
   },
   
   //质检统计表
    qualitytable:function (params, fn) {
        this.http('scair/monthRunUnitQC/getStationQCCount', "post", params, fn)
   },
   
   //质检监督(飞行)
    inspectiontable:function (params, fn) {
        this.http('scair/monthdeduct/countInspection', "post", params, fn)
   },
};
