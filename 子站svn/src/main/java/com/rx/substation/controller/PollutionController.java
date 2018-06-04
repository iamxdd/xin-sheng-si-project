package com.rx.substation.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rx.substation.service.*;
import com.rx.substation.util.DateCycleUtil;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by dcx on 2017/6/19 0019.
 */
@RestController
@RequestMapping(value = "/scair/pollution")
public class PollutionController {

    @Autowired
    RegionalPollutionService regionalPollutionService;

    @Autowired
    BurnPollutionService burnPollutionService;

    @Autowired
    SandPollutionService sandPollutionService;

    @Autowired
    MaxMoleService maxMoleService;

    @Autowired
    CityService cityService;

    /**
     * 区域污染概况
     * @param rq
     * @return
     */
    @RequestMapping(value="/regionalpollution")
    public Map<String, Object> getAllPollutionAea(HttpServletRequest rq){
        String time = rq.getParameter("monthtime").toString();
        String start = "";
        String end = "";
        try {
            start = GetMonthUtil.getFirstDay(time);
            end = GetMonthUtil.getLastDay(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> datamap = new HashMap<>();
        //判断是否做了区域污染的判定
        int days  = regionalPollutionService.getDays(start,end);
        List<Map<String, Object>> allPollutionAea = regionalPollutionService.getAllPollutionAea(start, end);

        datamap.put("days",days);
        datamap.put("allPollutionAea",allPollutionAea);
        return datamap;
    }

    /**
     * 区域污染月详情
     * @param rq
     * @return
     */
    @RequestMapping(value="/regionalpollutionmsg")
    public List<Map<String, Object>> getPollutionAea(HttpServletRequest rq){
        String start = rq.getParameter("start").toString();
        String end = rq.getParameter("end").toString();
        return regionalPollutionService.getPollutionAea(start, end);
    }

    /**
     * 秸秆焚烧影响判定
     * @param rq
     * @return
     */
    @RequestMapping(value="/burnpollution")
    public Map<String, Object> getBurnPollution(HttpServletRequest rq){
        String time = rq.getParameter("monthtime").toString();
        String start = "";
        String end = "";
        try {
            start = GetMonthUtil.getFirstDay(time);
            end = GetMonthUtil.getLastDay(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String citycode = rq.getParameter("citycode").toString();
        Map<String, Object> datamap = new HashMap<>();
        //判断是否做了秸秆焚烧的判定
        int days = burnPollutionService.getDays(start,end,citycode);
        List<Map<String, Object>> burnPollution = burnPollutionService.getBurnPollution(start, end,citycode);
        datamap.put("days",days);
        datamap.put("burnPollution",burnPollution);
        return datamap;
    }


    /**
     * 受沙暴影响统计
     * @param rq
     * @return
     */
    @RequestMapping(value="/sandpollution")
    public Map<String, Object> getSandPollution(HttpServletRequest rq){
        String time = rq.getParameter("monthtime").toString();
        String start = "";
        String end = "";
        try {
            start = GetMonthUtil.getFirstDay(time);
            end = GetMonthUtil.getLastDay(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String citycode = rq.getParameter("citycode").toString();
        Map<String, Object> datamap = new HashMap<>();
        //判断是否做了秸秆焚烧的判定
        int days = sandPollutionService.getDays(start,end,citycode);
        List<Map<String, Object>> sandPollution = sandPollutionService.getSandPollution(start, end,citycode);
        datamap.put("days",days);
        datamap.put("sandPollution",sandPollution);
        return datamap;
    }


    /**
     * 区域性污染判定结果修改保存
     * @param rq
     * @return
     */
    @RequestMapping(value="/saveareapollution")
    //事务管理
    @Transactional(value="txManager")
    public Map<String,Object> savepollution(HttpServletRequest rq){
        String jsondata = rq.getParameter("jsondata").toString();
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        Map<String,Object> result = new HashMap<>();
//        String jsondata ="[{\"monthtime\": \"2017-05-01\",\"status\": 1},{\"monthtime\": \"2017-05-02\",\"status\": 0}]";
        //type为0是保存1为修改
        if(type == 0){
            if(jsondata!=null&&!"".equals(jsondata)) {
                List<Map<String, Object>> listMap = JSON.parseObject(jsondata, new TypeReference<List<Map<String, Object>>>() {
                });
                for (int i = 0; i < listMap.size(); i++) {
                    Map<String, Object> datamap = listMap.get(i);
                    String monthtime = datamap.get("monthtime").toString();
                    String status = datamap.get("status").toString();
                    regionalPollutionService.saveAreaPollution(monthtime, status);
                    result.put("result",1);
                }
            }
        }else if(type == 1){
            if(jsondata!=null&&!"".equals(jsondata)) {
                List<Map<String, Object>> listMap = JSON.parseObject(jsondata, new TypeReference<List<Map<String, Object>>>() {
                });
                for (int i = 0; i < listMap.size(); i++) {
                    Map<String, Object> datamap = listMap.get(i);
                    String monthtime = datamap.get("monthtime").toString();
                    String status = datamap.get("status").toString();
                    regionalPollutionService.updateAreaPollution(monthtime, status);
                    result.put("result",2);
                }
            }
        }else {
            result.put("result",0);
        }
        return result;
    }

    /**
     * 秸秆焚烧影响保存
     * @param rq
     */
    @RequestMapping(value="/saveburnpollution")
    //事务管理
    @Transactional(value="txManager")
    public Map<String,Object> saveBurnPollution(HttpServletRequest rq){
        String jsondata = rq.getParameter("jsondata").toString();
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        Map<String,Object> result = new HashMap<>();
//        String jsondata ="[{\"no2\": 58,\"pm2_5\": 77,\"cityname\": \"成都\",\"citycode\": 5101,\"o3\": 200,\"pm2_5pm10\": 0.61,\"so2\": 12,\"pm10\": 126,\"co\": 0.9,\"status\": 1,\"monthtime\": \"2017-05-01\"},{\"no2\": 58,\"pm2_5\": 77,\"cityname\": \"成都\",\"citycode\": 5101,\"o3\": 200,\"pm2_5pm10\": 0.61,\"so2\": 12,\"pm10\": 126,\"co\": 0.9,\"status\": 0,\"monthtime\": \"2017-05-02\"}]";
        if(type == 0){
            if(jsondata!=null&&!"".equals(jsondata)){
                List<Map<String, Object>> listMap = JSON.parseObject(jsondata, new TypeReference<List<Map<String,Object>>>(){});
                for (int i = 0; i < listMap.size(); i++) {
                    Map<String,Object> datamap =listMap.get(i);
                    String monthtime = datamap.get("monthtime").toString();
                    String citycode = datamap.get("citycode").toString();
                    String cityname = datamap.get("cityname").toString();
                    String so2 = datamap.get("so2").toString();
                    String no2 = datamap.get("no2").toString();
                    String o3 = datamap.get("o3").toString();
                    String co = datamap.get("co").toString();
                    String pm2_5 = datamap.get("pm2_5").toString();
                    String pm10 = datamap.get("pm10").toString();
                    String status = datamap.get("status").toString();
                    burnPollutionService.saveBurnPollutionService(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,status);
                    result.put("result",1);
                }
            }
        }else if(type == 1){
            if(jsondata!=null&&!"".equals(jsondata)){
                List<Map<String, Object>> listMap = JSON.parseObject(jsondata, new TypeReference<List<Map<String,Object>>>(){});
                for (int i = 0; i < listMap.size(); i++) {
                    Map<String,Object> datamap =listMap.get(i);
                    String monthtime = datamap.get("monthtime").toString();
                    String citycode = datamap.get("citycode").toString();
                    String cityname = datamap.get("cityname").toString();
                    String so2 = datamap.get("so2").toString();
                    String no2 = datamap.get("no2").toString();
                    String o3 = datamap.get("o3").toString();
                    String co = datamap.get("co").toString();
                    String pm2_5 = datamap.get("pm2_5").toString();
                    String pm10 = datamap.get("pm10").toString();
                    String status = datamap.get("status").toString();
                    burnPollutionService.updateBurnPollutionService(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,status);
                    result.put("result",2);
                }
            }
        }else {
            result.put("result",0);
        }
        return  result;
    }

    /**
     * 沙暴影响统计保存
     * @param rq
     */
    @RequestMapping(value="/savesandpollution")
    //事务管理
    @Transactional(value="txManager")
    public Map<String,Object> saveSandPollution(HttpServletRequest rq){
        String jsondata = rq.getParameter("jsondata").toString();
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        Map<String,Object> result = new HashMap<>();
//        String jsondata ="[{\"no2\": 58,\"pm2_5\": 77,\"cityname\": \"成都\",\"citycode\": 5101,\"o3\": 200,\"pm2_5pm10\": 0.61,\"so2\": 12,\"pm10\": 126,\"co\": 0.9,\"status\": 1,\"monthtime\": \"2017-05-01\"},{\"no2\": 58,\"pm2_5\": 77,\"cityname\": \"成都\",\"citycode\": 5101,\"o3\": 200,\"pm2_5pm10\": 0.61,\"so2\": 12,\"pm10\": 126,\"co\": 0.9,\"status\": 0,\"monthtime\": \"2017-05-02\"}]";
        if(type == 0){
            if(jsondata!=null&&!"".equals(jsondata)){
                List<Map<String, Object>> listMap = JSON.parseObject(jsondata, new TypeReference<List<Map<String,Object>>>(){});
                for (int i = 0; i < listMap.size(); i++) {
                    Map<String,Object> datamap =listMap.get(i);
                    String monthtime = datamap.get("monthtime").toString();
                    String citycode = datamap.get("citycode").toString();
                    String cityname = datamap.get("cityname").toString();
                    String so2 = datamap.get("so2").toString();
                    String no2 = datamap.get("no2").toString();
                    String o3 = datamap.get("o3").toString();
                    String co = datamap.get("co").toString();
                    String pm2_5 = datamap.get("pm2_5").toString();
                    String pm10 = datamap.get("pm10").toString();
                    String pm2_5pm10 = datamap.get("pm2_5pm10").toString();
                    String status = datamap.get("status").toString();
                    sandPollutionService.saveSandPollution(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,pm2_5pm10,status);
                    result.put("result",1);
                }
            }
        }else if(type == 1){
            if(jsondata!=null&&!"".equals(jsondata)){
                List<Map<String, Object>> listMap = JSON.parseObject(jsondata, new TypeReference<List<Map<String,Object>>>(){});
                for (int i = 0; i < listMap.size(); i++) {
                    Map<String,Object> datamap =listMap.get(i);
                    String monthtime = datamap.get("monthtime").toString();
                    String citycode = datamap.get("citycode").toString();
                    String cityname = datamap.get("cityname").toString();
                    String so2 = datamap.get("so2").toString();
                    String no2 = datamap.get("no2").toString();
                    String o3 = datamap.get("o3").toString();
                    String co = datamap.get("co").toString();
                    String pm2_5 = datamap.get("pm2_5").toString();
                    String pm10 = datamap.get("pm10").toString();
                    String pm2_5pm10 = datamap.get("pm2_5pm10").toString();
                    String status = datamap.get("status").toString();
                    sandPollutionService.updateSandPollution(monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,pm2_5pm10,status);
                    result.put("result",2);
                }
            }
        }else {
            result.put("result",0);
        }
        return result;
    }

    /**
     * 区域污染统计
     * @return
     */
    @RequestMapping(value="/regionalpollutioncount")
    public List<Map<String, Object>> regionalpollutioncount(HttpServletRequest rq){
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        double pm2_5goal = Double.valueOf(rq.getParameter("pm2_5goal").toString());
        double pm10goal = Double.valueOf(rq.getParameter("pm10goal").toString());
        double opm2_5goal = Double.valueOf(rq.getParameter("opm2_5goal").toString());
        double opm10goal = Double.valueOf(rq.getParameter("opm10goal").toString());
        String starttime = rq.getParameter("start").toString();
        String endtime = rq.getParameter("end").toString();

        List<Map<String, Object>> dataList = new ArrayList<>();
        //以月统计
        if(type == 1){
            //得到当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //算出两个时间差中的时间月份
            List<String> months = new ArrayList<>();
            try {
                months = GetMonthUtil.getMonthBetween(starttime,endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //遍历查询
            for (int i = 0; i <months.size() ; i++) {
                try {
                    String start = GetMonthUtil.getFirstDay(months.get(i));
                    String end = GetMonthUtil.getLastDay(months.get(i));
                    //获得去年此月月初
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sdf.parse(start));
                    cal.add(Calendar.YEAR, -1);
                    cal.set(Calendar.DATE, 1);
                    String ostart = sdf.format(cal.getTime());
                    //获得去年此月月末
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(sdf.parse(start));
                    cal2.add(Calendar.YEAR, -1);
                    cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String oend = sdf.format(cal2.getTime());
                    Map<String,Object> datamap = calregionalpollution(start,end,ostart,oend,pm2_5goal,pm10goal,opm2_5goal,opm10goal);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //以季度统计
        }else if(type == 2){
            String startTime4=starttime;//季度数据计算总起始时间，如2016-1-1
            String endTime4=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,3), 1),-1);//季度数据计算总结束时间，如2016-3-31
            String timePointNow=startTime4;//当前时间
            while(!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime4, 1),1))){
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 0);
                String ostart = "";
                String oend = "";
                try {
                    ostart = GetMonthUtil.getLastYearDay(start);
                    oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calregionalpollution(start,end,ostart,oend,pm2_5goal,pm10goal,opm2_5goal,opm10goal);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 1);//下个季度1号
            }

            //以半年统计
        }else if(type == 3){
            String startTime5=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime5=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,6), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime5;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime5, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 0);
                String ostart = "";
                String oend = "";
                try {
                    ostart = GetMonthUtil.getLastYearDay(start);
                    oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calregionalpollution(start,end,ostart,oend,pm2_5goal,pm10goal,opm2_5goal,opm10goal);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 1);//下个半年1号
            }

            //以年统计
        }else if(type == 4){
            String startTime6=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime6=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,12), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime6;//当前时间
            //遍历时间段内的每一年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime6, 1),1))) {//当前半年1号到最后一年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 0);
                String ostart = "";
                String oend = "";
                try {
                    ostart = GetMonthUtil.getLastYearDay(start);
                    oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calregionalpollution(start,end,ostart,oend,pm2_5goal,pm10goal,opm2_5goal,opm10goal);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 1);//下个半年1号
            }
        }
        return dataList;
    }


    /**
     * 焚烧秸秆污染统计
     * @return
     */
    @RequestMapping(value="/burnpollutioncount")
    public List<Map<String, Object>> burnpollutioncount(HttpServletRequest rq){
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        double goalpm2_5 = Double.valueOf(rq.getParameter("pm2_5goal").toString());
        double goalpm10 = Double.valueOf(rq.getParameter("pm10goal").toString());
        String starttime = rq.getParameter("start").toString();
        String endtime = rq.getParameter("end").toString();


        List<Map<String, Object>> dataList = new ArrayList<>();
        //以月统计
        if(type == 1){
            //得到当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //算出两个时间差中的时间月份
            List<String> months = new ArrayList<>();
            try {
                months = GetMonthUtil.getMonthBetween(starttime,endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //遍历查询
            for (int i = 0; i <months.size() ; i++) {
                try {
                    String start = GetMonthUtil.getFirstDay(months.get(i));
                    String end = GetMonthUtil.getLastDay(months.get(i));
                    Map<String,Object> datamap = calburnpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //以季度统计
        }else if(type == 2){
            String startTime4=starttime;//季度数据计算总起始时间，如2016-1-1
            String endTime4=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,3), 1),-1);//季度数据计算总结束时间，如2016-3-31
            String timePointNow=startTime4;//当前时间
            while(!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime4, 1),1))){
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 0);
                try {
                    Map<String,Object> datamap = calburnpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 1);//下个季度1号
            }
        //以半年统计
        }else if(type == 3){
            String startTime5=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime5=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,6), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime5;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime5, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 0);
                try {
                    Map<String,Object> datamap = calburnpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 1);//下个半年1号
            }

            //以年统计
        }else if(type == 4){
            String startTime6=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime6=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,12), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime6;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime6, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 0);
                try {
                    Map<String,Object> datamap = calburnpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 1);//下个半年1号
            }
        }
        return dataList;
    }


    /**
     * 沙暴污染统计
     * @return
     */
    @RequestMapping(value="/sandpollutioncount")
    public List<Map<String, Object>> sandpollutioncount(HttpServletRequest rq){
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        double goalpm2_5 = Double.valueOf(rq.getParameter("pm2_5goal").toString());
        double goalpm10 = Double.valueOf(rq.getParameter("pm10goal").toString());
        String starttime = rq.getParameter("start").toString();
        String endtime = rq.getParameter("end").toString();


        List<Map<String, Object>> dataList = new ArrayList<>();
        //以月统计
        if(type == 1){
            //得到当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //算出两个时间差中的时间月份
            List<String> months = new ArrayList<>();
            try {
                months = GetMonthUtil.getMonthBetween(starttime,endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //遍历查询
            for (int i = 0; i <months.size() ; i++) {
                try {
                    String start = GetMonthUtil.getFirstDay(months.get(i));
                    String end = GetMonthUtil.getLastDay(months.get(i));
                    Map<String,Object> datamap = calsandpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //以季度统计
        }else if(type == 2){
            String startTime4=starttime;//季度数据计算总起始时间，如2016-1-1
            String endTime4=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,3), 1),-1);//季度数据计算总结束时间，如2016-3-31
            String timePointNow=startTime4;//当前时间
            while(!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime4, 1),1))){
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 0);
                try {
                    Map<String,Object> datamap = calsandpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 1);//下个季度1号
            }

            //以半年统计
        }else if(type == 3){
            String startTime5=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime5=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,6), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime5;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime5, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 0);
                try {
                    Map<String,Object> datamap = calsandpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 1);//下个半年1号
            }

            //以年统计
        }else if(type == 4){
            String startTime6=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime6=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,12), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime6;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime6, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 0);
                try {
                    Map<String,Object> datamap = calsandpollution(start,end,goalpm2_5,goalpm10);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 1);//下个半年1号
            }
        }
        return dataList;
    }

    /**
     * 时间段最大浓度统计
     * @return
     */
    @RequestMapping(value="/maxbytimecount")
    public List<Map<String, Object>> maxByTimecount(HttpServletRequest rq){
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        String starttime = rq.getParameter("start").toString();
        String endtime = rq.getParameter("end").toString();
        String param = rq.getParameter("param").toString();
        List<Map<String, Object>> dataList = new ArrayList<>();
        //以月统计
        if(type == 1){
            //得到当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //算出两个时间差中的时间月份
            List<String> months = new ArrayList<>();
            try {
                months = GetMonthUtil.getMonthBetween(starttime,endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //遍历查询
            for (int i = 0; i <months.size() ; i++) {
                try {
                    String start = GetMonthUtil.getFirstDay(months.get(i));
                    String end = GetMonthUtil.getLastDay(months.get(i));
                    //获得去年此月月初
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sdf.parse(start));
                    cal.add(Calendar.YEAR, -1);
                    cal.set(Calendar.DATE, 1);
                    String ostart = sdf.format(cal.getTime());
                    //获得去年此月月末
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(sdf.parse(start));
                    cal2.add(Calendar.YEAR, -1);
                    cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String oend = sdf.format(cal2.getTime());
                    Map<String,Object> datamap = calMaxByTime(start,end,param);
                    Map<String,Object> odatamap = calMaxByTime(ostart,oend,param);
                    double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                    double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                    String otimepoint = odatamap.get("timepoint").toString();
                    String ocityName = odatamap.get("cityName").toString();
                    String oairlevel = odatamap.get("airlevel").toString();
                    String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                    double YOYmax = 0.0;
                    if(omaxdata != 0.0){
                        YOYmax = (maxdata-omaxdata)/omaxdata;
                    }else{
                        YOYmax = 0.0;
                    }
                    datamap.put("otimepoint",otimepoint);
                    datamap.put("ocityName",ocityName);
                    datamap.put("omaxdata",omaxdata);
                    datamap.put("oairlevel",oairlevel);
                    datamap.put("oprimaryPollutant",oprimaryPollutant);
                    datamap.put("YOYmax",YOYmax);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        //以季度统计
        }else if(type == 2){
            String startTime4=starttime;//季度数据计算总起始时间，如2016-1-1
            String endTime4=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,3), 1),-1);//季度数据计算总结束时间，如2016-3-31
            String timePointNow=startTime4;//当前时间
            while(!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime4, 1),1))){
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 0);
                try {
                    String ostart = GetMonthUtil.getLastYearDay(start);
                    String oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calMaxByTime(start,end,param);
                    Map<String,Object>odatamap = calMaxByTime(ostart,oend,param);
                    double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                    double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                    String otimepoint = odatamap.get("timepoint").toString();
                    String ocityName = odatamap.get("cityName").toString();
                    String oairlevel = odatamap.get("airlevel").toString();
                    String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                    double YOYmax = 0.0;
                    if(omaxdata != 0.0){
                        YOYmax = (maxdata-omaxdata)/omaxdata;
                    }else{
                        YOYmax = 0.0;
                    }
                    datamap.put("otimepoint",otimepoint);
                    datamap.put("ocityName",ocityName);
                    datamap.put("omaxdata",omaxdata);
                    datamap.put("oairlevel",oairlevel);
                    datamap.put("oprimaryPollutant",oprimaryPollutant);
                    datamap.put("YOYmax",YOYmax);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 1);//下个季度1号
            }

            //以半年统计
        }else if(type == 3){
            String startTime5=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime5=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,6), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime5;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime5, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 0);
                try {
                    String ostart = GetMonthUtil.getLastYearDay(start);
                    String oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calMaxByTime(start,end,param);
                    Map<String,Object> odatamap = calMaxByTime(ostart,oend,param);
                    double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                    double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                    String otimepoint = odatamap.get("timepoint").toString();
                    String ocityName = odatamap.get("cityName").toString();
                    String oairlevel = odatamap.get("airlevel").toString();
                    String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                    double YOYmax = 0.0;
                    if(omaxdata != 0.0){
                        YOYmax = (maxdata-omaxdata)/omaxdata;
                    }else{
                        YOYmax = 0.0;
                    }
                    datamap.put("otimepoint",otimepoint);
                    datamap.put("ocityName",ocityName);
                    datamap.put("omaxdata",omaxdata);
                    datamap.put("oairlevel",oairlevel);
                    datamap.put("oprimaryPollutant",oprimaryPollutant);
                    datamap.put("YOYmax",YOYmax);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 1);//下个半年1号
            }

        //以年统计
        }else if(type == 4){
            String startTime6=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime6=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,12), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime6;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime6, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 0);
                try {
                    String ostart = GetMonthUtil.getLastYearDay(start);
                    String oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calMaxByTime(start,end,param);
                    Map<String,Object> odatamap = calMaxByTime(ostart,oend,param);
                    double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                    double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                    String otimepoint = odatamap.get("timepoint").toString();
                    String ocityName = odatamap.get("cityName").toString();
                    String oairlevel = odatamap.get("airlevel").toString();
                    String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                    double YOYmax = 0.0;
                    if(omaxdata != 0.0){
                        YOYmax = (maxdata-omaxdata)/omaxdata;
                    }else{
                        YOYmax = 0.0;
                    }
                    datamap.put("otimepoint",otimepoint);
                    datamap.put("ocityName",ocityName);
                    datamap.put("omaxdata",omaxdata);
                    datamap.put("oairlevel",oairlevel);
                    datamap.put("oprimaryPollutant",oprimaryPollutant);
                    datamap.put("YOYmax",YOYmax);
                    dataList.add(datamap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 1);//下个半年1号
            }
        //任意时间段
        }else{
            try {
                String ostart = GetMonthUtil.getLastYearDay(starttime);
                String oend = GetMonthUtil.getLastYearDay(endtime);
                Map<String,Object> datamap = calMaxByTime(starttime,endtime,param);
                Map<String,Object> odatamap = calMaxByTime(ostart,oend,param);
                double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                String otimepoint = odatamap.get("timepoint").toString();
                String ocityName = odatamap.get("cityName").toString();
                String oairlevel = odatamap.get("airlevel").toString();
                String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                double YOYmax = 0.0;
                if(omaxdata != 0.0){
                    YOYmax = (maxdata-omaxdata)/omaxdata;
                }else{
                    YOYmax = 0.0;
                }
                datamap.put("otimepoint",otimepoint);
                datamap.put("ocityName",ocityName);
                datamap.put("omaxdata",omaxdata);
                datamap.put("oairlevel",oairlevel);
                datamap.put("oprimaryPollutant",oprimaryPollutant);
                datamap.put("YOYmax",YOYmax);
                dataList.add(datamap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * 时间段内城市最大浓度
     * @param rq
     * @return
     */
    @RequestMapping(value="/maxbycitycount")
    public List<Map<String, Object>> maxByCitycount(HttpServletRequest rq){
        Integer type = Integer.valueOf( rq.getParameter("type").toString());
        String starttime = rq.getParameter("start").toString();
        String endtime = rq.getParameter("end").toString();
        String param = rq.getParameter("param").toString();
        List<Map<String, Object>> dataList = new ArrayList<>();
        //以月统计
        if(type == 1){
            //得到当前时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //算出两个时间差中的时间月份
            List<String> months = new ArrayList<>();

            try {
                months = GetMonthUtil.getMonthBetween(starttime,endtime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //遍历查询
            for (int i = 0; i <months.size() ; i++) {
                try {
                    String start = GetMonthUtil.getFirstDay(months.get(i));
                    String end = GetMonthUtil.getLastDay(months.get(i));
                    //获得去年此月月初
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(sdf.parse(start));
                    cal.add(Calendar.YEAR, -1);
                    cal.set(Calendar.DATE, 1);
                    String ostart = sdf.format(cal.getTime());
                    //获得去年此月月末
                    Calendar cal2 = Calendar.getInstance();
                    cal2.setTime(sdf.parse(start));
                    cal2.add(Calendar.YEAR, -1);
                    cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
                    String oend = sdf.format(cal2.getTime());
                    List<Map<String,Object>> cityList = cityService.getAllCityMsg();
                    for (int j = 0; j < cityList.size() ; j++) {
                        Map<String, Object> citymap = cityList.get(j);
                        String citycode = citymap.get("CityCode").toString();
                        Map<String,Object> datamap = calMaxByCity(start,end,citycode,param);
                        Map<String,Object> odatamap = calMaxByCity(ostart,oend,citycode,param);
                        double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                        double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                        String otimepoint = odatamap.get("timepoint").toString();
                        String oairlevel = odatamap.get("airlevel").toString();
                        String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                        double YOYmax = 0.0;
                        if(omaxdata != 0.0){
                            YOYmax = (maxdata-omaxdata)/omaxdata;
                        }else{
                            YOYmax = 0.0;
                        }
                        datamap.put("otimepoint",otimepoint);
                        datamap.put("omaxdata",omaxdata);
                        datamap.put("oairlevel",oairlevel);
                        datamap.put("oprimaryPollutant",oprimaryPollutant);
                        datamap.put("YOYmax",YOYmax);
                        dataList.add(datamap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        //以季度统计
        }else if(type == 2){
            String startTime4=starttime;//季度数据计算总起始时间，如2016-1-1
            String endTime4=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,3), 1),-1);//季度数据计算总结束时间，如2016-3-31
            String timePointNow=startTime4;//当前时间
            while(!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime4, 1),1))){
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 0);
                try {
                    List<Map<String,Object>> cityList = cityService.getAllCityMsg();
                    for (int j = 0; j < cityList.size() ; j++) {
                        Map<String, Object> citymap = cityList.get(j);
                        String citycode = citymap.get("CityCode").toString();
                        String ostart = GetMonthUtil.getLastYearDay(start);
                        String oend = GetMonthUtil.getLastYearDay(end);
                        Map<String,Object> datamap = calMaxByCity(start,end,citycode,param);
                        Map<String,Object> odatamap = calMaxByCity(ostart,oend,citycode,param);
                        double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                        double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                        String otimepoint = odatamap.get("timepoint").toString();
                        String oairlevel = odatamap.get("airlevel").toString();
                        String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                        double YOYmax = 0.0;
                        if(omaxdata != 0.0){
                            YOYmax = (maxdata-omaxdata)/omaxdata;
                        }else{
                            YOYmax = 0.0;
                        }
                        datamap.put("otimepoint",otimepoint);
                        datamap.put("omaxdata",omaxdata);
                        datamap.put("oairlevel",oairlevel);
                        datamap.put("oprimaryPollutant",oprimaryPollutant);
                        datamap.put("YOYmax",YOYmax);
                        dataList.add(datamap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,3), 1);//下个季度1号
            }

        //以半年统计
        }else if(type == 3){
            String startTime5=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime5=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,6), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime5;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime5, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 0);
                try {
                    List<Map<String,Object>> cityList = cityService.getAllCityMsg();
                    for (int j = 0; j < cityList.size() ; j++) {
                        Map<String, Object> citymap = cityList.get(j);
                        String citycode = citymap.get("CityCode").toString();
                        String ostart = GetMonthUtil.getLastYearDay(start);
                        String oend = GetMonthUtil.getLastYearDay(end);
                        Map<String,Object> datamap = calMaxByCity(start,end,citycode,param);
                        Map<String,Object> odatamap = calMaxByCity(ostart,oend,citycode,param);
                        double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                        double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                        String otimepoint = odatamap.get("timepoint").toString();
                        String oairlevel = odatamap.get("airlevel").toString();
                        String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                        double YOYmax = 0.0;
                        if(omaxdata != 0.0){
                            YOYmax = (maxdata-omaxdata)/omaxdata;
                        }else{
                            YOYmax = 0.0;
                        }
                        datamap.put("otimepoint",otimepoint);
                        datamap.put("omaxdata",omaxdata);
                        datamap.put("oairlevel",oairlevel);
                        datamap.put("oprimaryPollutant",oprimaryPollutant);
                        datamap.put("YOYmax",YOYmax);
                        dataList.add(datamap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,6), 1);//下个半年1号
            }

        //以年统计
        }else if(type == 4){
            String startTime6=starttime;//半年数据计算总起始时间，如2016-1-1
            String endTime6=DateCycleUtil.dateAddNumDay(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endtime,12), 1), -1);//半年数据计算总结束时间，如2016-12-31
            String timePointNow=startTime6;//当前时间
            //遍历时间段内的每一个半年
            while (!DateCycleUtil.dateAddNumMonth(timePointNow, 0).equals(DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(endTime6, 1),1))) {//当前半年1号到最后一个半年下一年的第一天（1月1号）
                String start = timePointNow;
                String end = DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 0);
                try {
                    List<Map<String,Object>> cityList = cityService.getAllCityMsg();
                    for (int j = 0; j < cityList.size() ; j++) {
                        Map<String, Object> citymap = cityList.get(j);
                        String citycode = citymap.get("CityCode").toString();
                        String ostart = GetMonthUtil.getLastYearDay(start);
                        String oend = GetMonthUtil.getLastYearDay(end);
                        Map<String,Object> datamap = calMaxByCity(start,end,citycode,param);
                        Map<String,Object> odatamap = calMaxByCity(ostart,oend,citycode,param);
                        double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                        double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                        String otimepoint = odatamap.get("timepoint").toString();
                        String oairlevel = odatamap.get("airlevel").toString();
                        String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                        double YOYmax = 0.0;
                        if(omaxdata != 0.0){
                            YOYmax = (maxdata-omaxdata)/omaxdata;
                        }else{
                            YOYmax = 0.0;
                        }
                        datamap.put("otimepoint",otimepoint);
                        datamap.put("omaxdata",omaxdata);
                        datamap.put("oairlevel",oairlevel);
                        datamap.put("oprimaryPollutant",oprimaryPollutant);
                        datamap.put("YOYmax",YOYmax);
                        dataList.add(datamap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                timePointNow=DateCycleUtil.dateSetNumDay(DateCycleUtil.dateAddNumMonth(timePointNow,12), 1);//下个半年1号
            }
        }else{
            String start = starttime;
            String end = endtime;
            try {
                List<Map<String,Object>> cityList = cityService.getAllCityMsg();
                for (int j = 0; j < cityList.size() ; j++) {
                    Map<String, Object> citymap = cityList.get(j);
                    String citycode = citymap.get("CityCode").toString();
                    String ostart = GetMonthUtil.getLastYearDay(start);
                    String oend = GetMonthUtil.getLastYearDay(end);
                    Map<String,Object> datamap = calMaxByCity(start,end,citycode,param);
                    Map<String,Object> odatamap = calMaxByCity(ostart,oend,citycode,param);
                    double maxdata = Double.valueOf(datamap.get("max"+param).toString());
                    double omaxdata = Double.valueOf(odatamap.get("max"+param).toString());
                    String otimepoint = odatamap.get("timepoint").toString();
                    String oairlevel = odatamap.get("airlevel").toString();
                    String oprimaryPollutant = odatamap.get("primaryPollutant").toString();
                    double YOYmax = 0.0;
                    if(omaxdata != 0.0){
                        YOYmax = (maxdata-omaxdata)/omaxdata;
                    }else{
                        YOYmax = 0.0;
                    }
                    datamap.put("otimepoint",otimepoint);
                    datamap.put("omaxdata",omaxdata);
                    datamap.put("oairlevel",oairlevel);
                    datamap.put("oprimaryPollutant",oprimaryPollutant);
                    datamap.put("YOYmax",YOYmax);
                    dataList.add(datamap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    /**
     * ------------------------------------------计算方法------------------------------------------------------------
     */

    /**
     * 区域污染统计
     * @param start
     * @param end
     * @param ostart
     * @param oend
     * @param pm2_5gole
     * @param pm10gole
     * @param opm2_5gole
     * @param opm10gole
     * @return
     * @throws ParseException
     */
    public Map<String, Object> calregionalpollution(String start,String end,String ostart,String oend,
                                                    double pm2_5gole,double pm10gole,double opm2_5gole,double opm10gole) throws ParseException {
        //得到本月的区域性污染天数
        int areapollutiondays = regionalPollutionService.getAreaPollutionDays(start,end);
        //得到时间段的天数
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date lastTime = sdf.parse(start);
        Date thisTime = sdf.parse(end);
        Calendar wCalendar1 = Calendar.getInstance();
        Calendar wCalendar2 = Calendar.getInstance();
        wCalendar1.setTime(lastTime);
        wCalendar2.setTime(thisTime);
        int daysBetween = DateCycleUtil.getDaysBetween(wCalendar1,wCalendar2);
        //得到本月的非区域性污染天数
        int noareapollutiondays = daysBetween - areapollutiondays;
        //得到去年同月的区域性污染天数
        int oareapollutiondays = regionalPollutionService.getAreaPollutionDays(ostart,oend);
        //得到时间段的天数
        Date olastTime = sdf.parse(ostart);
        Date othisTime = sdf.parse(oend);
        Calendar owCalendar1 = Calendar.getInstance();
        Calendar owCalendar2 = Calendar.getInstance();
        owCalendar1.setTime(olastTime);
        owCalendar2.setTime(othisTime);
        int odaysBetween = DateCycleUtil.getDaysBetween(owCalendar1,owCalendar2);
        //得到去年同月的区域性污染天数
        int onoareapollutiondays = odaysBetween - areapollutiondays;
        //天数同比YOYdays
//        if(oareapollutiondays != 0){
//            double YOYdays = (double)(areapollutiondays-oareapollutiondays)/oareapollutiondays;
//        }
        int YOYdays = areapollutiondays - oareapollutiondays;

        //得到本月区域性污染浓度(出现区域性污染天的日均值的平均值)
        List<Map<String, Object>> datalist = regionalPollutionService.getPollutionAeaM(start,end);
        List<Map<String, Object>> odatalist = regionalPollutionService.getPollutionAeaM(ostart,oend);

        double countpm2_5 = 0.0;
        double countpm10 = 0.0;
        double ocountpm2_5 = 0.0;
        double ocountpm10 = 0.0;
        double pm2_5 = 0.0;
        double pm10 = 0.0;
        double opm2_5 = 0.0;
        double opm10 = 0.0;
        if(datalist.size()>0){
            for (int i = 0; i <datalist.size(); i++) {
                Map<String, Object> datamap = datalist.get(i);
                if(datamap.get("PM2_5") == null){
                    pm2_5 = 0.0;
                }else {
                    pm2_5 = Double.valueOf(datamap.get("PM2_5").toString());
                }
                if(datamap.get("PM10") == null){
                    pm10 = 0.0;
                }else {
                    pm10 =  Double.valueOf(datamap.get("PM10").toString());
                }
                countpm2_5 = countpm2_5 + pm2_5;
                countpm10 = countpm10 + pm10;
            }
        }
        //得到去年同月区域性污染浓度
        if(odatalist.size()>0){
            for (int i = 0; i <odatalist.size(); i++) {
                Map<String, Object> odatamap = odatalist.get(i);
                if(odatamap.get("PM2_5") == null){
                    opm2_5 = 0.0;
                }else {
                    opm2_5 = Double.valueOf(odatamap.get("PM2_5").toString());
                }
                if(odatamap.get("PM10") == null){
                    opm10 = 0.0;
                }else {
                    opm10 =  Double.valueOf(odatamap.get("PM10").toString());
                }
                ocountpm2_5 = ocountpm2_5 + opm2_5;
                ocountpm10 = ocountpm10 + opm10;
            }

        }
        double avgpm2_5 = 0.0;
        double avgpm10 = 0.0;
        double avgopm2_5 = 0.0;
        double avgopm10 = 0.0;
        if(areapollutiondays!=0){
            avgpm2_5 = countpm2_5/(21* areapollutiondays);
            avgpm10 = countpm10/(21* areapollutiondays);
        }
        if(oareapollutiondays!=0){
            avgopm2_5 = ocountpm2_5/(21* oareapollutiondays);
            avgopm10 = ocountpm10/(21* oareapollutiondays);
        }
        //浓度同比
        double YOYpm2_5 = 0.0;
        double YOYpm10 = 0.0;
        if(avgopm2_5 != 0.0){
            YOYpm2_5 = (avgpm2_5 - avgopm2_5)/avgopm2_5;
        }
        if(avgopm10 != 0.0){
            YOYpm10 = (avgpm10 - avgopm10)/avgopm10;
        }

        //得到本月常态浓度(当月除区域性污染天以外其余天数日均值的平均值)
        List<Map<String, Object>> nomaldata = regionalPollutionService.getNormalPollution(start,end);
        double normalpm2_5 = 0.0;
        double normalpm10 = 0.0;
        double cnormalpm2_5 = 0.0;
        double cnormalpm10 = 0.0;
        if(nomaldata.size()>0){
            for (int i = 0; i <nomaldata.size() ; i++) {
                Map<String, Object> normalmap = nomaldata.get(i);
                if(normalmap.get("PM2_5") == null){
                    normalpm2_5 = 0.0;
                }else {
                    normalpm2_5 =  Double.valueOf(normalmap.get("PM2_5").toString());
                }
                if(normalmap.get("PM10") == null){
                    normalpm10 = 0.0;
                }else {
                    normalpm10 =  Double.valueOf(normalmap.get("PM10").toString());
                }
                cnormalpm2_5 = cnormalpm2_5 + normalpm2_5;
                cnormalpm10 = cnormalpm10 + normalpm10;
            }
        }
        List<Map<String, Object>> onomaldata = regionalPollutionService.getNormalPollution(ostart,oend);
        double onormalpm2_5 = 0.0;
        double onormalpm10 = 0.0;
        double ocnormalpm2_5 = 0.0;
        double ocnormalpm10 = 0.0;
        if(onomaldata.size()>0){
            for (int i = 0; i <onomaldata.size() ; i++) {
                Map<String, Object> onormalmap = onomaldata.get(i);
                if(onormalmap.get("PM2_5") == null){
                    onormalpm2_5 = 0.0;
                }else {
                    onormalpm2_5 =  Double.valueOf(onormalmap.get("PM2_5").toString());
                }
                if(onormalmap.get("PM10") == null){
                    onormalpm10 = 0.0;
                }else {
                    onormalpm10 =  Double.valueOf(onormalmap.get("PM10").toString());
                }
                ocnormalpm2_5 = ocnormalpm2_5 + onormalpm2_5;
                ocnormalpm10 = ocnormalpm10 + onormalpm10;
            }
        }
        double avgnormalpm2_5 = 0.0;
        double avgnormalpm10 = 0.0;
        double oavgnormalpm2_5 = 0.0;
        double oavgnormalpm10 = 0.0;
        if(areapollutiondays!=0.0){
            avgnormalpm2_5 = cnormalpm2_5/(21 * noareapollutiondays) ;
            avgnormalpm10 = cnormalpm10/(21 * noareapollutiondays);
        }
        if(oareapollutiondays!=0.0){
            oavgnormalpm2_5 = ocnormalpm2_5/ (21 * onoareapollutiondays);
            oavgnormalpm10 = ocnormalpm10/ (21 * onoareapollutiondays);
        }
        //较常态浓度升高倍数(（区域性污染浓度-常态浓度）/常态浓度)
        double pm2_5normaltimes = 0.0;
        double opm2_5normaltimes = 0.0;
        double pm10normaltimes = 0.0;
        double opm10normaltimes = 0.0;
        if(avgnormalpm2_5!=0){
            pm2_5normaltimes = (avgpm2_5 - avgnormalpm2_5)/avgnormalpm2_5;
            opm2_5normaltimes = (avgopm2_5 - avgnormalpm2_5)/avgnormalpm2_5;
        }
        if(avgnormalpm10!=0){
            pm10normaltimes = (avgpm10 - avgnormalpm10)/avgnormalpm10;
            opm10normaltimes = (avgopm10 - avgnormalpm10)/avgnormalpm10;
        }


        //年均浓度贡献（ug/m3）及同比去年(区域性污染浓度*区域性污染天数/全年天数)
        Calendar calendar = Calendar.getInstance();
        String years = String.valueOf(calendar.get(Calendar.YEAR));
        int Ydays = DateCycleUtil.getYearDays(years);
        Calendar calendar1 = Calendar.getInstance();
        String oyears = String.valueOf(calendar.get(Calendar.YEAR)-1);
        int oYdays = DateCycleUtil.getYearDays(oyears);
        double Mpm2_5contribute = (avgpm2_5*areapollutiondays)/Ydays;
        double Mpm10contribute = (avgpm10*areapollutiondays)/Ydays;
        double oMpm2_5contribute = (avgpm2_5*areapollutiondays)/oYdays;
        double oMpm10contribute = (avgpm10*areapollutiondays)/oYdays;
        //年均浓度贡献同比（今年该月区污贡献-去年该月区污贡献）/去年该月区污过程贡献
        double pm2_5contributetimes =0.0;
        double pm10contributetimes = 0.0;
        if (oMpm2_5contribute != 0.0){
            pm2_5contributetimes = (Mpm2_5contribute-oMpm2_5contribute)/oMpm2_5contribute;
        }
        if (oMpm10contribute != 0.0){
            pm10contributetimes = (Mpm10contribute-oMpm10contribute)/oMpm10contribute;
        }
        //年均浓度污染负荷（%）及同比去年（区域性污染浓度*区域性污染天数）/（全年天数*年均浓度（PM2.5/PM10的年度目标值））
        double Mpm2_5load = (avgpm2_5*areapollutiondays)/(Ydays*pm2_5gole);
        double Mpm10load = (avgpm10*areapollutiondays)/(Ydays*pm10gole);
        double oMpm2_5load = (avgpm2_5*areapollutiondays)/(oYdays*opm2_5gole);
        double oMpm10load = (avgpm10*areapollutiondays)/(oYdays*opm10gole);
        //年均浓度污染负荷同比（今年该月区浓度污染负荷-去年该月区浓度污染负荷）/去年该月区污过程负荷
        double pm2_5loadtimes = 0.0;
        double pm10loadtimes = 0.0;
        if(Mpm10load != 0.0){
            pm2_5loadtimes = (Mpm2_5load-oMpm2_5load)/Mpm10load;
        }
        if(oMpm10load != 0.0){
            pm10loadtimes = (Mpm10load-oMpm10load)/oMpm10load;
        }
        //增幅为年均浓度贡献及同比去年（区域性污染浓度-常态浓度）*区域性污染天数/全年天数（ug/m3）
        double Mpm2_5Cgrowth = (avgpm2_5-avgnormalpm2_5)*areapollutiondays/Ydays;
        double Mpm10Cgrowth = (avgpm10 - avgnormalpm10)*areapollutiondays/Ydays;
        double oMpm2_5Cgrowth = (avgopm2_5 - oavgnormalpm2_5)*oareapollutiondays/oYdays;
        double oMpm10Cgrowth = (avgopm10 - oavgnormalpm10)*oareapollutiondays/oYdays;
        //增幅浓度贡献同比
        double pm2_5Cgrowthtimes = 0.0;
        double pm10Cgrowthtimes = 0.0;
        if(oMpm2_5Cgrowth != 0.0){
            pm2_5Cgrowthtimes = (Mpm2_5Cgrowth-oMpm2_5Cgrowth)/oMpm2_5Cgrowth;
        }
        if(oMpm10Cgrowth != 0.0){
            pm10Cgrowthtimes = (Mpm10Cgrowth-oMpm10Cgrowth)/oMpm10Cgrowth;
        }
        //污染强度变化{（今年该月区域性污染浓度-常态浓度）-（去年该月区域性污染浓度-常态浓度）}/（去年该月区域性污染浓度-常态浓度）
        double pm2_5Mstrength = 0.0;
        double pm10Mstrength = 0.0;
        if((avgopm2_5 - oavgnormalpm2_5) != 0.0){
            pm2_5Mstrength = ((avgpm2_5 - avgnormalpm2_5)-(avgopm2_5 - oavgnormalpm2_5))/(avgopm2_5 - oavgnormalpm2_5);
        }
        if((avgopm10 - oavgnormalpm10) != 0.0){
            pm10Mstrength = ((avgpm10 - avgnormalpm10)-(avgopm10 - oavgnormalpm10))/(avgopm10 - oavgnormalpm10);
        }

        Map<String,Object> regionalPmap = new HashMap<>();

        regionalPmap.put("areapollutiondays",areapollutiondays);
        regionalPmap.put("YOYdays",YOYdays);
        regionalPmap.put("avgpm2_5",avgpm2_5);
        regionalPmap.put("avgpm10",avgpm10);
        regionalPmap.put("YOYpm2_5",YOYpm2_5);
        regionalPmap.put("YOYpm10",YOYpm10);
        regionalPmap.put("normalpm2_5",avgnormalpm2_5);
        regionalPmap.put("normalpm10",avgnormalpm10);
        regionalPmap.put("onormalpm2_5",oavgnormalpm2_5);
        regionalPmap.put("onormalpm10",oavgnormalpm10);
        regionalPmap.put("pm2_5normaltimes",pm2_5normaltimes);
        regionalPmap.put("pm10normaltimes",pm10normaltimes);
        regionalPmap.put("Mpm2_5contribute",Mpm2_5contribute);
        regionalPmap.put("Mpm10contribute",Mpm10contribute);
        regionalPmap.put("pm2_5contributetimes",pm2_5contributetimes);
        regionalPmap.put("pm10contributetimes",pm10contributetimes);
        regionalPmap.put("Mpm2_5load",Mpm2_5load);
        regionalPmap.put("Mpm10load",Mpm10load);
        regionalPmap.put("pm2_5loadtimes",pm2_5loadtimes);
        regionalPmap.put("pm10loadtimes",pm10loadtimes);
        regionalPmap.put("Mpm2_5Cgrowth",Mpm2_5Cgrowth);
        regionalPmap.put("Mpm10Cgrowth",Mpm10Cgrowth);
        regionalPmap.put("pm2_5Cgrowthtimes",pm2_5Cgrowthtimes);
        regionalPmap.put("pm10Cgrowthtimes",pm10Cgrowthtimes);
        regionalPmap.put("pm2_5Mstrength",pm2_5Mstrength);
        regionalPmap.put("pm10Mstrength",pm10Mstrength);
        regionalPmap.put("monthTime",start);

        return regionalPmap;
    }

    /**
     * 焚烧污染统计
     * @param start
     * @param end
     * @param goalpm2_5
     * @param goalpm10
     * @return
     * @throws ParseException
     */
    public Map<String, Object> calburnpollution(String start,String end,double goalpm2_5,double goalpm10) throws  ParseException{
        Map<String,Object> burnPmap = new HashMap<>();
        //焚烧期间浓度(首先将受影响城市分开计算，即各城市焚烧期间日均值的平均值；然后把受影响城市平均)
        Map<String,Object> burningMap = burnPollutionService.getBurningM(start,end,"1");
        double burnpm2_5M = 0.0;
        double burnpm10M = 0.0;
        double noburnpm2_5M = 0.0;
        double noburnpm10M = 0.0;
        if(burningMap != null && !burningMap.isEmpty()){
            burnpm2_5M =  Double.valueOf(burningMap.get("avgpm2_5").toString());
            burnpm10M = Double.valueOf(burningMap.get("avgpm10").toString());
        }
        //首先将受影响城市分开计算，即当月除焚烧天以外其余天数日均值的平均值；然后把受影响城市平均（直接在SQL中算）
        Map<String,Object> noBurningMap = burnPollutionService.getBurningM(start,end,"0");
        if(noBurningMap != null && !noBurningMap.isEmpty() ){
            noburnpm2_5M =  Double.valueOf(noBurningMap.get("avgpm2_5").toString());
            noburnpm10M = Double.valueOf(noBurningMap.get("avgpm10").toString());
        }
        //焚烧期间较非焚烧期间上升倍(（所有受影响城市焚烧期间浓度的平均值-非焚烧期间浓度的平均值）/非焚烧期间浓度平均值)
        double burnpm2_5T =0.0;
        double burnpm10T =0.0;
        if(noburnpm2_5M!=0){
            burnpm2_5T = (burnpm2_5M-noburnpm2_5M)/noburnpm2_5M;
        }
        if(noburnpm10M!=0){
            burnpm10T = (burnpm10M-noburnpm10M)/noburnpm10M;
        }


        //为全省年均浓度贡献(受影响城市分开计算，然后加和.)
        double countpm2_5M = 0.0;
        double countpm10M = 0.0;
        // （某市焚烧期间浓度-非焚烧期间浓度）*某市焚烧天数/（年均浓度（PM2.5/PM10的年度目标值）*市州数（21固定值）*全年天数（按每年实际天数365/366））)
        List<Map<String,Object>> cityList = cityService.getAllCityMsg();
        for (int i = 0; i < cityList.size() ; i++) {
            Map <String,Object> citymap = cityList.get(i);
            String citycode = citymap.get("CityCode").toString();
            double burnpm2_5Mc = 0.0;
            double burnpm10Mc = 0.0;
            double noburnpm2_5Mc = 0.0;
            double noburnpm10Mc = 0.0;

            Map<String,Object> burnmbycity = burnPollutionService.getBurningMByCity(start,end,citycode,"1");
            Map<String,Object> noburnmbycity = burnPollutionService.getBurningMByCity(start,end,citycode,"0");
            int burndays = burnPollutionService.getBurnPollutionDays(start,end,citycode,"1");

            if(burnmbycity != null && !burnmbycity.isEmpty()  ){
                burnpm2_5Mc = Double.valueOf(burnmbycity.get("pm2_5").toString());
                burnpm10Mc = Double.valueOf(burnmbycity.get("pm10").toString());
            }
            if(noburnmbycity != null && !noburnmbycity.isEmpty()){
                noburnpm2_5Mc = Double.valueOf(noburnmbycity.get("pm2_5").toString());
                noburnpm10Mc = Double.valueOf(noburnmbycity.get("pm10").toString());
            }
            double calpm2_5 = ((burnpm2_5Mc - noburnpm2_5Mc) * burndays) / (goalpm2_5*21*DateCycleUtil.getYearDays(start.substring(0,4)));
            double calpm10 = ((burnpm10Mc - noburnpm10Mc) * burndays) / (goalpm10*21*DateCycleUtil.getYearDays(start.substring(0,4)));
            countpm2_5M = countpm2_5M + calpm2_5;
            countpm10M = countpm10M + calpm10;
        }
        burnPmap.put("timepoint",start);
        burnPmap.put("burnpm2_5M",burnpm2_5M);
        burnPmap.put("burnpm10M",burnpm10M);
        burnPmap.put("noburnpm2_5M",noburnpm2_5M);
        burnPmap.put("noburnpm10M",noburnpm10M);
        burnPmap.put("burnpm2_5T",burnpm2_5T);
        burnPmap.put("burnpm10T",burnpm10T);
        burnPmap.put("countpm2_5M",countpm2_5M);
        burnPmap.put("countpm10M",countpm10M);

        return burnPmap;
    }

    /**
     * 沙暴污染统计
     * @param start
     * @param end
     * @param goalpm2_5
     * @param goalpm10
     * @return
     * @throws ParseException
     */
    public Map<String, Object> calsandpollution(String start,String end,double goalpm2_5,double goalpm10) throws ParseException{
        Map<String,Object> sandPmap = new HashMap<>();
        //为全省年均浓度贡献,受影响城市分开计算然后加和
        double countpm2_5M = 0.0;
        double countpm10M = 0.0;
        //（某市沙尘影响浓度-非沙尘期间浓度）*受影响天数/（PM10年均浓度*市州数（21固定值）*全年天数（按每年实际天数365/366））
        List<Map<String,Object>> cityList = cityService.getAllCityMsg();
        for (int i = 0; i < cityList.size() ; i++) {
            Map <String,Object> citymap = cityList.get(i);
            String citycode = citymap.get("CityCode").toString();
            double sandpm2_5Mc = 0.0;
            double sandpm10Mc = 0.0;
            double nosandpm2_5Mc = 0.0;
            double nosandpm10Mc = 0.0;

            Map<String,Object> sandmbycity = sandPollutionService.getSandMByCity(start,end,citycode,"1");
            Map<String,Object> nosandmbycity = sandPollutionService.getSandMByCity(start,end,citycode,"0");
            int sanddays = sandPollutionService.getSandPollutionDays(start,end,citycode,"1");

            if(sandmbycity != null && !sandmbycity.isEmpty()){
                sandpm2_5Mc = Double.valueOf(sandmbycity.get("pm2_5").toString());
                sandpm10Mc = Double.valueOf(sandmbycity.get("pm10").toString());
            }
            if(nosandmbycity != null&&!nosandmbycity.isEmpty()){
                nosandpm2_5Mc = Double.valueOf(nosandmbycity.get("pm2_5").toString());
                nosandpm10Mc = Double.valueOf(nosandmbycity.get("pm10").toString());
            }
            double calpm2_5 = ((sandpm2_5Mc - nosandpm2_5Mc) * sanddays) / (goalpm2_5*21*DateCycleUtil.getYearDays(start.substring(0,4)));
            double calpm10 = ((sandpm10Mc - nosandpm10Mc) * sanddays) / (goalpm10*21*DateCycleUtil.getYearDays(start.substring(0,4)));
            countpm2_5M = countpm2_5M + calpm2_5;
            countpm10M = countpm10M + calpm10;
        }
        sandPmap.put("timepoint",start);
        sandPmap.put("countpm2_5M",countpm2_5M);
        sandPmap.put("countpm10M",countpm10M);
        return sandPmap;
    }


    /**
     * 最大浓度按时间段最大值
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public Map<String, Object> calMaxByTime(String start,String end,String param) throws ParseException{
        Map<String,Object> maxMmap = new HashMap<>();
        maxMmap  = maxMoleService.getMaxMoleByTime(start,end,param);
        return maxMmap;
    }

    /**
     * 最大浓度按城市最大值
     * @param start
     * @param end
     * @param citycode
     * @return
     * @throws ParseException
     */
    public Map<String, Object> calMaxByCity(String start,String end,String citycode,String param) throws ParseException{
        Map<String,Object> maxMmap = new HashMap<>();
        maxMmap  = maxMoleService.getMaxMoleByCity(start,end,citycode,param);
        return maxMmap;
    }


}
