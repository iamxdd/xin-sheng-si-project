<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>内江空气质量检测平台</title>
  <link rel="icon" href="https://t.alipayobjects.com/images/T1QUBfXo4fXXXXXXXX.png" type="image/x-icon">
  <link rel="stylesheet" href="assets/css/normalize.css" />
  <link rel="stylesheet" href="assets/css/index.css" />
  <link rel="stylesheet" href="assets/css/bootstrap.min.css" />
  <link rel="stylesheet" href="assets/css/jquery.lavalamp.css" />
</head>
<body>
<div class="container-fluid" id="content">
     <!--头部 begin-->
        <div class="row">
            <div class="col-md-12 header-content">
              <div class="row">
                  <div class="col-md-12">
                    <div >
                          <ul class="header">
                            <li class="active"><a href="javascript:;">报表管理</a></l>
                            <li><a href="javascript:;">质控评估管理</a></li>
                            <li><a href="javascript:;">子站基础信息</a></li>
                          </ul>
                    </div>
                    <div>
                    </div>
                  </div>
                  <div class="row quality-control-menu">
                    <div class="col-md-12">
                      <h3>子站管理
                          <i></i>
                          <span>&gt;质控评估管理</span> 
                      </h3>
                      <ul id="qualitySecondMenu">
                        <li class="checkActive"><a>质量情况统计</a></li>
                        <li><a>质控绩效考核</a></li>
                      </ul>
                    </div>
                  </div>
              </div>
          </div>
      </div>
     <!--头部 end-->

     <!--内容 begin-->
     <div class="row">
          <div class="col-md-12 table-content">
              <div class="row main-content">
                <div class="col-md-12 content-title">
                        <span>全屏</span>
                        <h3>参数配置</h3>
                </div>
              </div>
              <div class="row main-content">
                    <div class="col-md-12">
                      <div class="row content-body">
                          <div class="col-md-12 conditions">
                              <label for="unit">
                                  运行单位:
                                  <select id="unit">
                                       <option>选择运行单位</option> 
                                       <option>运行单位A</option> 
                                       <option>运行单位B</option> 
                                  </select>

                              </label>
                               <label>
                                  时间选择:
                                  <select id="startyear">
                                       <option>选择年</option> 
                                       <option>2017年</option> 
                                       <option>2016年</option> 
                                  </select>
                                  <select id="startmonth">
                                       <option>选择月</option> 
                                       <option>3月</option> 
                                       <option>2月</option> 
                                  </select>
                                  至
                                  <select id="endyear">
                                       <option>选择年</option> 
                                       <option>2017年</option> 
                                       <option>2016年</option> 
                                  </select>
                                  <select id="endmonth">
                                       <option>选择月</option> 
                                       <option>3月</option> 
                                       <option>2月</option> 
                                  </select>

                              </label>
                          </div>
                          <div class="col-md-12 conditions">
                              <label for="area">
                                 站点区域:
                                 <select id="area">
                                       <option>选择所在区域</option> 
                                       <option>成都</option> 
                                       <option>绵阳</option> 
                                 </select>
                              </label> 
                              <label for="category">
                                 站点类型:
                                 <select id="category">
                                       <option>选择站点类型</option> 
                                       <option>城市站</option> 
                                       <option>省控站</option> 
                                       <option>农村站</option> 
                                 </select>
                              </label> 
                              <label for="stationchose">
                                 站点选择:
                                 <select id="stationchose">
                                       <option>选择站点名称</option> 
                                       <option>站点A</option> 
                                       <option>站点B</option> 
                                       <option>站点C</option> 
                                 </select>
                              </label> 

                          </div>
                  
                          <div class="textrightalign col-md-8">
                                <input type="button" value="结果查询" onclick="search()"/>
 
                          </div>



                          <div class="col-md-12  contentfortables">
                              <!-- Nav tabs -->
                              <ul class="nav nav-tabs" role="tablist" id="myTabs">
                                <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">环境空气自动检测数据传输率情况评价表</a></li>
                                <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">环境空气自动检测有效天数得分情况评价表</a></li>
                                <li role="presentation"><a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">运行单位日常工作评价表</a></li>
                                <li role="presentation"><a href="#settings" aria-controls="settings" role="tab" data-toggle="tab">质控检查扣分表</a></li>
                                <li role="presentation"><a href="#yunxingzhiliang" aria-controls="yunxingzhiliang" role="tab" data-toggle="tab">运行单位运行质量评估表</a></li>
                                <li role="presentation"><a href="#yunxingkaohe" aria-controls="yunxingkaohe" role="tab" data-toggle="tab">环境空气自动检测运行考核结果</a></li>
                              </ul>

                              <!-- Tab panes -->
                              <div class="tab-content">
                                <div role="tabpanel" class="tab-pane active" id="home">
                                        <div class="table-responsive">
                                            <table class="table">
                                              <tr>
                                                <th colspan="11" style="text-align: center">
                                                  四川省15个农村站环境空气自动检测数据传输率情况评价表
                                                </th>
                                              </tr>
                                              <tr>
                                                  <td>站点</td>
                                                  <td>站点名称</td>
                                                  <td>SO2</td>
                                                  <td>NO2</td>
                                                  <td>O3</td>
                                                  <td>Co</td>
                                                  <td>PM10</td>
                                                  <td>PM2.5</td>
                                                  <td>平均传输值</td>
                                                  <td>是/否</br>合格</td>
                                                  <td>得分</td>
                                              </tr>
                                              <tbody id="chuanshulv"></tbody>
                                            </table>
                                        </div>

                                </div>
                                <div role="tabpanel" class="tab-pane" id="profile">
                                        <div class="table-responsive">
                                            <table class="table">
                                              <tr>
                                                <th colspan="10" style="text-align: center">
                                                  四川省15个农村站环境空气自动检测有效天数得分情况评价表
                                                </th>
                                              </tr>
                                              <tr>
                                                  <td>站点</td>
                                                  <td>站点名称</td>
                                                  <td>理论天数</td>
                                                  <td>停电</td>
                                                  <td>其他</td>
                                                  <td>应检测天数</td>
                                                  <td>有效检测天数</td>
                                                  <td>得分</td>
                                              </tr>
                                              <tbody id="defenpingjiabiao"></tbody>
                                            </table>
                                        </div>

                                </div>
                                <div role="tabpanel" class="tab-pane" id="messages">
                                        <div class="table-responsive">
                                            <table class="table">
                                              <tr>
                                                <th colspan="10" style="text-align: center">
                                                  运行单位日常工作评价表
                                                </th>
                                              </tr>
                                                <tr>
                                                    <td>序号</td>
                                                    <td>评价周期</td>
                                                    <td>上传执行时间</td>
                                                    <td>本次执行时间</td>
                                                    <td>工作内容</td>
                                                    <td>完成情况</td>
                                                    <td>填报周期</td>
                                                    <td>绩效评价</td>
                                                    <td>扣分原因</td> 
                                                </tr>
                                              <tbody id="richanggongzuopingjiabiao">
                                                     
                                              </tbody>
                                            </table>
                                        </div>



                                     

                                </div>
                                <div role="tabpanel" class="tab-pane" id="settings">
                                      <div class="table-responsive">
                                            <table class="table">
                                              <tr>
                                                <th colspan="10" style="text-align: center">
                                                  质控检查扣分表
                                                </th>
                                              </tr>
                                              <tr>
                                                  <td>序号</td>
                                                  <td>子站</td>
                                                  <td>检查项目</td>
                                                  <td>结果</td>
                                                  <td>扣分</td>
                                                  <td>检查时间</td>
                                                  <td>检查人</td>
                                                  <td>备注</td>
                                              </tr>
                                              <tbody id="jianchakoufenbiao"></tbody>
                                            </table>
                                        </div>




                                </div>
                                <div role="tabpanel" class="tab-pane" id="yunxingzhiliang">
                                      <div class="table-responsive">
                                            <table class="table">
                                              <tr>
                                                <th colspan="10" style="text-align: center">
                                                  运行单位运行质量评估表
                                                </th>
                                              </tr>
                                              <tr>
                                                  <td>站点</td>
                                                  <td>站点名称</td>
                                                  <td>数据传输率≥85%</td>
                                                  <td>有效累计天数>27天</td>
                                                  <td>评价总分≥80分</td>
                                                  <td>运行质量结果系数</td>
                                              </tr>
                                              <tbody id="zhiliangpinggubiao"></tbody>
                                            </table>
                                        </div>
                                        


                                </div>
                                <div role="tabpanel" class="tab-pane" id="yunxingkaohe">

                                        <div class="table-responsive">
                                            <table class="table">
                                              <tr>
                                                <th colspan="10" style="text-align: center">
                                                  四川省15个农村站环境空气自动检测考核结果
                                                </th>
                                              </tr>
                                              <tr>
                                                  <td>站点</td>
                                                  <td>数据传输率</td>
                                                  <td>有效天数得分</td>
                                                  <td>运行工作完成情况得分</td>
                                                  <td>质控检查扣分</td>
                                                  <td>总分</td>
                                                  <td>运行费(元)</td>
                                              </tr>
                                              <tbody id="jiancekaohejieguo"></tbody>
                                            </table>
                                        </div>
                                    
                                   
                                </div>
                              </div>

                          </div>

                      </div>
                    </div> 


              </div>
          </div>
     </div>
     <!--内容 end-->

<!--      ========================================= 表格模板begin ========================================== -->


<!--传输率-->
<script id="chuanshutable" type="text/html">

   {{each list as value i}}
   <tr>
        <td>{{value.stationCode==undefined?'-':value.stationCode}}</td>
        <td>{{value.stationName==undefined?'-':value.stationName}}</td>
        <td>{{value.so2==undefined?'-':value.so2}}</td>
        <td>{{value.no2==undefined?'-':value.no2}}</td>
        <td>{{value.o3==undefined?'-':value.o3}}</td>
        <td>{{value.co==undefined?'-':value.co}}</td>
        <td>{{value.pm10==undefined?'-':value.pm10}}</td>
        <td>{{value.pm2_5==undefined?'-':value.pm2_5}}</td>
        <td>{{value.avgTransfer==undefined?'-':value.avgTransfer}}</td>
        <td>{{value.status==false?'否':'是'}}</td>
        <td>{{value.scores}}</td>
    </tr>
    {{/each}}

</script>



<!--有效天数得分-->
<script id="validtable" type="text/html">

   {{each list as value i}}
   <tr>
        <td>{{value.stationCode==undefined?'-':value.stationCode}}</td>
        <td>{{value.stationName==undefined?'-':value.stationName}}</td>
        <td>{{value.theoryDays==undefined?'-':value.theoryDays}}</td>
        <td>{{value.powerCutDays==undefined?'-':value.powerCutDays}}</td>
        <td>{{value.others==undefined?'-':value.others}}</td>
        <td>{{value.shouldDays==undefined?'-':value.shouldDays}}</td>
        <td>{{value.validatyDays==undefined?'-':value.validatyDays}}</td>
        <td>{{value.scores==undefined?'-':value.scores}}</td>
    </tr>
    {{/each}}

</script>



<!--质控检查扣分表-->
<script id="monthdeduct" type="text/html">

   {{each list as value i}}
   <tr>
        <td>{{value.stationCode==undefined?'-':value.stationCode}}</td>
        <td>{{value.stationName==undefined?'-':value.stationName}}</td>
        <td>{{value.checkWork==undefined?'-':value.checkWork}}</td>
        <td>{{value.result==false?'不通过':'通过'}}</td>
        <td>{{value.deduct==undefined?'-':value.deduct}}</td>
        <td>{{value.checkTime==undefined?'-':value.checkTime}}</td>
        <td>{{value.checkPerson==undefined?'-':value.checkPerson}}</td>
        <td>{{value.mark==undefined?'-':value.mark}}</td>
    </tr>
    {{/each}}

</script>

<!--运行单位运行质量评估表-->
<script id="monthunitworking" type="text/html">

   {{each list.StationRunning as value i}}
   <tr>
        <td>{{value.stationCode==undefined?'-':value.stationCode}}</td>
        <td>{{value.stationName==undefined?'-':value.stationName}}</td>
        <td>{{value.transferStatus>=0.85?'是':'否'}}</td>
        <td>{{value.validateStatus>27?'是':'否'}}</td>
        <td>{{value.scoresStatus>=80?'是':'否'}}</td>
        <td>{{value.coefficient==undefined?'-':value.coefficient}}</td>
    </tr>
    {{/each}}
    <tr><td colspan=6>运行单位运行质量评估</td></tr>
    <tr>
      <td>序号</td>
      <td colspan=2>评估因素</td>
      <td>存在/不存在</td>
      <td colspan=2>评估结果</td>
    </tr>
    <tr>
      <td>1</td>
      <td colspan=2>当月20%站点（3个农村站）未达到数据有效性要求</td>
      <td>{{list.status1?'存在':'不存在'}}</td>
      <td colspan=2></td>
    </tr>
    <tr>
      <td>2</td>
      <td colspan=2>连续两月20%站点（3个农村站）未达到数据有效性要求</td>
      <td>{{list.status2?'存在':'不存在'}}</td>
      <td colspan=2></td>
    </tr>
    <tr>
      <td>3</td>
      <td colspan=2>当月40%站点（7个农村站）未达到数据有效性要求</td>
      <td>{{list.status3?'存在':'不存在'}}</td>
      <td colspan=2></td>
    </tr>
    <tr>
      <td>4</td>
      <td colspan=2>同一站点连续两个月未达到数据有效性要求</td>
      <td>{{list.status4?'存在':'不存在'}}</td>
      <td colspan=2></td>
    </tr>
    <tr>
      <td>5</td>
      <td colspan=2>同一站点连续三个月未达到数据有效性要求</td>
      <td>{{list.status5?'存在':'不存在'}}</td>
      <td colspan=2></td>
    </tr>
    <tr>
      <td>6</td>
      <td colspan=2>同一站点连续四个月未达到数据有效性要求</td>
      <td>{{list.status6?'存在':'不存在'}}</td>
      <td colspan=2></td>
    </tr>
    <tr>
      <td>运行质量评估结果</td>
      <td colspan=5>{{list.runQuality}}</td>
    </tr>
</script>

<!--运行单位日常工作月评价表-->
<script id="monthRunUnitQC" type="text/html">

   {{each list as value i}}
    <tr>
      <td colspan=9>{{value.stationName==undefined?'-':value.stationName}}</td>
      </tr>
      {{each value.stationComplateList as value j}}
      
   <tr>
        <td>{{j+1}}</td>
        <td>
          {{if value.Type==1}}
          每周工作评价
          {{else if value.Type==2}}
          每月工作评价
          {{else if value.Type==3}}
          半年工作评价
          {{else}}
          年工作评价
          {{/if}}
        </td>
       
        <td>{{value.lastTime==undefined?'-':value.lastTime}}</td>
        <td>{{value.thisTime==undefined?'-':value.thisTime}}</td>
        <td>
          {{if value.Type==1}}
          每周工作完成，周报上报
          {{else if value.Type==2}}
          每月工作完成，月报上报
          {{else if value.Type==3}}
          半年工作完成，半年报上报
          {{else}}
          每年工作完成，年报上报
          {{/if}}
        </td>
        <td>
          {{if value.Type==1}}
          已在周日前完成周报
          {{else if value.Type==2}}
          已在月末完成月报
          {{else if value.Type==3}}
          已在6月31日前完成半年报
          {{else}}
          已在12月31日前完成年报
          {{/if}}
        </td>
        <td>{{value.inputCycle==undefined?'-':value.inputCycle}}</td>
        <td>{{value.performance==undefined?'-':value.performance}}</td>
        <td>{{value.mark==undefined?'-':value.mark}}</td>
    </tr>
     {{/each}}
    <tr>
      <td colspan=4>合计扣分</td>
      <td colspan=5>{{value.deduct==undefined?'-':value.deduct}}</td>
      </tr>
    {{/each}}

</script>

<!--考核结果-->
<script id="calscorce" type="text/html">

   {{each list as value i}}
   <tr>
        <td>{{value.stationName==undefined?'-':value.stationName}}</td>
        <td>{{value.tansferScore==undefined?'-':value.tansferScore}}</td>
        <td>{{value.validateDaysScore==undefined?'-':value.validateDaysScore}}</td>
        <td>{{value.completeScore==undefined?'-':value.completeScore}}</td>
        <td>{{value.deduct==undefined?'-':value.deduct}}</td>
        <td>{{value.totalScore==undefined?'-':value.totalScore}}</td>
        <td>{{value.checkPerson==undefined?'-':value.checkPerson}}</td>
    </tr>
    {{/each}}

</script>

<!--      ========================================= 表格模板end ========================================== -->

</div>
<script src="assets/jq/jquery-2.1.0.min.js"></script>
<script src="assets/jq/bootstrap.min.js"></script>
<script src="assets/echart/echarts.common.min.js"></script>
<script src="assets/jq/jquery.lavalamp.min.js"></script>
<script src="assets/jq/template.js"></script>
<script src="assets/js/request.js"></script>
<script src="assets/js/common.js"></script>
<script src="assets/js/index.js"></script>
</body>
</html>