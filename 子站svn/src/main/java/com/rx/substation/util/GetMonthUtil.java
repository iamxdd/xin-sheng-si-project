package com.rx.substation.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GetMonthUtil {
    
	 /** 
     * 获取两个日期之间的所有月份
     */
     public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
         ArrayList<String> result = new ArrayList<String>();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月

         Calendar min = Calendar.getInstance();
         Calendar max = Calendar.getInstance();

         min.setTime(sdf.parse(minDate));
         min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

         max.setTime(sdf.parse(maxDate));
         max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

         Calendar curr = min;
         while (curr.before(max)) {
             result.add(sdf.format(curr.getTime()));
             curr.add(Calendar.MONTH, 1);
         }

         return result;
     }

     //获取某月第一天
     public static String getFirstDay(String datadate)throws Exception{
         Date date = null;
         String day_first = "";
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         date = format.parse(datadate);

         //创建日历
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.set(Calendar.DAY_OF_MONTH, 1);
         day_first = format.format(calendar.getTime());
         return day_first+" 00:00:00";
     }
     //获取某月最后一天
     public static String getLastDay(String datadate)throws Exception{
         Date date = null;
         String day_last = "";
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         date = format.parse(datadate);

         //创建日历
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.MONTH, 1);    //加一个月
         calendar.set(Calendar.DATE, 1);     //设置为该月第一天
         calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
         day_last = format.format(calendar.getTime());
         return day_last+" 23:59:59";
     }

     //获取某天的去年同天
     public static String getLastYearDay(String datadate)throws Exception{
         Date date = null;
         String day_last = "";
         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
         date = format.parse(datadate);

         //创建日历
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.YEAR, -1);    //减一年
         day_last = format.format(calendar.getTime());
         return day_last;
     }

}
