package com.rx.substation.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateCycleUtil {  
    
	 /** 
     * 获取两个日期之间的实际天数，支持跨年
     */  
	public static int getDaysBetween(Calendar start, Calendar end){  
        if(start.after(end)){  
            Calendar swap = start;  
            start = end;  
            end = swap;  
        }  
        int days = end.get(Calendar.DAY_OF_YEAR)- start.get(Calendar.DAY_OF_YEAR);  
        int y2 = end.get(Calendar.YEAR);  
        if (start.get(Calendar.YEAR) != y2) {  
            start = (Calendar) start.clone();  
            do {  
                days += start.getActualMaximum(Calendar.DAY_OF_YEAR);  
                start.add(Calendar.YEAR, 1);  
            }while(start.get(Calendar.YEAR) != y2);  
        }  
        return days;
    }


    /**
     * 获取两个日期之间的实际天数，支持跨年
     */
    public static int getDaysBetween2 (String start, String end){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        int days=0;
        try {
            one = df.parse(start);
            two = df.parse(end);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = (int)(diff / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取两个日期之间月份数（判断时间段内有几个季度）
     */
    public static int getMonthsBetween (String start, String end,int type){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = df.parse(start);
            toDate = df.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        int fromYear = from.get(Calendar.YEAR);
        int toYear = to.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toMonth = to.get(Calendar.MONTH);

        int year = toYear  -  fromYear;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth)+1;
        int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));



        int result = 0;
        if(type == 0){
            result = day;
        }else if(type == 1){
            result = month;
        }else if(type == 2){
            result = month/3;
        }else if(type == 3){
            result = month/6;
        }else{
            result = year;
        }
        return result;
    }



    //得到年天数
    public static int getYearDays(String years) throws ParseException {
	    int year = Integer.valueOf(years);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_YEAR);
    }


    /**
     * 字符串日期加月数
     * @param dateStr：日期，如："2016-1-1"
     * @param month：月份
     * @return
     */
    public static String dateAddNumMonth(String dateStr,Integer month) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = df.parse(dateStr);//将字符串时间解析成Date类型时间
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return df.format(cal.getTime());
    }


    /**
     * 字符串日期设置日期的天
     * @param dateStr：日期，如："2016-1-1"
     * @param day：天数
     * @return
     */
    public static String dateSetNumDay(String dateStr,Integer day) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = df.parse(dateStr);//将字符串时间解析成Date类型时间
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return df.format(cal.getTime());
    }

    /**
     * 字符串日期加天数
     * @param dateStr：日期，如："2016-1-1"
     * @param day：天数
     * @return
     */
    public static String dateAddNumDay(String dateStr,Integer day) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = df.parse(dateStr);//将字符串时间解析成Date类型时间
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return df.format(cal.getTime());
    }

    /***
     *  取出有效数据
     * @param dates  日期字符数组
     * @param type    判断类型  0 ：周  1： 月 2： 季度 3：半年 4：年
     * @return
     */
    public static List<Map<String,Object>> dateValidate(List<String> dates,int type) {
        //新建返回容器
        List<Map<String,Object>> result = new ArrayList<>();
        int minPoint = 0;
        int maxPoint = 0;
        //周
        if(type == 0){
            if(dates.size() == 1){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
            }else if (dates.size() >= 2){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
                while ( maxPoint <dates.size()) {
                    String time1 = dates.get(minPoint);
                    String time2 = dates.get(maxPoint);
                    int cycle = DateCycleUtil.getDaysBetween2(time1,time2);
                    if(cycle >= 4){
                        Map<String,Object> resultmap2 = new HashMap<>();
                        resultmap2.put("createDate",dates.get(maxPoint));
                        minPoint = maxPoint;
                        maxPoint = maxPoint+1;
                        result.add(resultmap2);
                    }else{
                        maxPoint = maxPoint+1;
                    }
                }
            }else{
                result = null;
            }
        //月
        }else if(type == 1){
            if(dates.size() == 1){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
            }else if (dates.size() >= 2){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
                while ( maxPoint <dates.size()) {
                    String time1 = dates.get(minPoint);
                    String time2 = dates.get(maxPoint);
                    int cycle = DateCycleUtil.getDaysBetween2(time1,time2);
                    if(cycle >= 25){
                        Map<String,Object> resultmap2 = new HashMap<>();
                        resultmap2.put("createDate",dates.get(maxPoint));
                        minPoint = maxPoint;
                        maxPoint = maxPoint+1;
                        result.add(resultmap2);
                    }else{
                        maxPoint = maxPoint+1;
                    }
                }
            }else{
                result = null;
            }
        //季
        }else if(type == 2){
            if(dates.size() == 1){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
            }else if (dates.size() >= 2){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
                while ( maxPoint <dates.size()) {
                    String time1 = dates.get(minPoint);
                    String time2 = dates.get(maxPoint);
                    int cycle = DateCycleUtil.getDaysBetween2(time1,time2);
                    if(cycle >= 75){
                        Map<String,Object> resultmap2 = new HashMap<>();
                        resultmap2.put("createDate",dates.get(maxPoint));
                        minPoint = maxPoint;
                        maxPoint = maxPoint+1;
                        result.add(resultmap2);
                    }else{
                        maxPoint = maxPoint+1;
                    }
                }
            }else{
                result = null;
            }

        //半年
        }else if(type == 3){
            if(dates.size() == 1){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
            }else if (dates.size() >= 2){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
                while ( maxPoint <dates.size()) {
                    String time1 = dates.get(minPoint);
                    String time2 = dates.get(maxPoint);
                    int cycle = DateCycleUtil.getDaysBetween2(time1,time2);
                    if(cycle >= 150){
                        Map<String,Object> resultmap2 = new HashMap<>();
                        resultmap2.put("createDate",dates.get(maxPoint));
                        minPoint = maxPoint;
                        maxPoint = maxPoint+1;
                        result.add(resultmap2);
                    }else{
                        maxPoint = maxPoint+1;
                    }
                }
            }else{
                result = null;
            }

        //年
        }else{
            if(dates.size() == 1){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
            }else if (dates.size() >= 2){
                Map<String,Object> resultmap = new HashMap<>();
                resultmap.put("createDate",dates.get(0));
                result.add(resultmap);
                while ( maxPoint <dates.size()) {
                    String time1 = dates.get(minPoint);
                    String time2 = dates.get(maxPoint);
                    int cycle = DateCycleUtil.getDaysBetween2(time1,time2);
                    if(cycle >= 330){
                        Map<String,Object> resultmap2 = new HashMap<>();
                        resultmap2.put("createDate",dates.get(maxPoint));
                        minPoint = maxPoint;
                        maxPoint = maxPoint+1;
                        result.add(resultmap2);
                    }else{
                        maxPoint = maxPoint+1;
                    }
                }
            }else{
                result = null;
            }

        }
        return result;

    }



}  
