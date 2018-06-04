package com.rx.substation.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by dcx on 2017/6/27 0027.
 */
public interface MaxMoleDAO {

    @Select("SELECT top 1 * from  " +
            "(select MAX((CASE when ${param}_24h in ('—','','NA') then null else ${param}_24h end)) max${param} " +
            " from AQICityDayDataPublishHistory t2 where TimePoint>=#{start} and TimePoint<=#{end}) a " +
            " LEFT JOIN " +
            "(select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint,primaryPollutant,dbo.calAQILevel(AQI) airlevel, " +
            "(CASE when ${param}_24h in ('—','','NA') then null else ${param}_24h end) ${param} from AQICityDayDataPublishHistory  where TimePoint>=#{start} and TimePoint<=#{end}) b " +
            " on b.${param} = a.max${param} order by b.airlevel desc")
    Map<String,Object> getMaxMoleByTime(@Param("start") String start, @Param("end") String end, @Param("param") String param);


    @Select("SELECT top 1 * from  " +
            "(select MAX((CASE when ${param}_24h in ('—','','NA') then null else ${param}_24h end)) max${param} " +
            " from AQICityDayDataPublishHistory t2 where TimePoint>=#{start} and TimePoint<=#{end} and citycode = #{citycode}) a " +
            " LEFT JOIN " +
            "(select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint,primaryPollutant,dbo.calAQILevel(AQI) airlevel, " +
            "(CASE when ${param}_24h in ('—','','NA') then null else ${param}_24h end) ${param} from AQICityDayDataPublishHistory  where TimePoint>=#{start} and TimePoint<=#{end} and citycode = #{citycode}) b " +
            " on b.${param} = a.max${param} order by b.airlevel desc")
    Map<String,Object> getMaxMoleByCity(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode, @Param("param") String param);
}
