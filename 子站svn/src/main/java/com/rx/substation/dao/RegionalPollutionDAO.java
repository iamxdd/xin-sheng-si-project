package com.rx.substation.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/6/19 0019.
 */
public interface RegionalPollutionDAO {

    /**
     * 得到区域污染总城市
     * @param start
     * @param end
     * @return
     */
    @Select("SELECT * FROM (" +
            "select Top 31 dbo.GroupTime_day(timepoint) TimePoint,count(*) CountNUM from (select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint," +
            "(CASE when SO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),SO2_24h) end) SO2," +
            "(CASE when NO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),NO2_24h) end) NO2, " +
            "(CASE when PM10_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),PM10_24h) end) PM10, " +
            "(CASE when PM2_5_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),PM2_5_24h) end) PM2_5," +
            "(CASE when O3_8h_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),O3_8h_24h) end) O3," +
            "(CASE when CO_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15,1),CO_24h) end)CO ,AQI,primaryPollutant,dbo.calAQILevel(AQI) airlevel," +
            "case when ISNUMERIC(AQI)=1 and AQI>0 and AQI<=50 then 1 else 0 end level1," +
            "case when ISNUMERIC(AQI)=1 and AQI>50 and AQI<=100 then 1 else 0 end level2," +
            "case when ISNUMERIC(AQI)=1 and AQI>100 and AQI<=150 then 1 else 0 end level3," +
            "case when ISNUMERIC(AQI)=1 and AQI>150 and AQI<=200 then 1 else 0 end level4," +
            "case when ISNUMERIC(AQI)=1 and AQI>200 and AQI<=300 then 1 else 0 end level5," +
            "case when ISNUMERIC(AQI)=1 and AQI>300 then 1 else 0 end level6" +
            " from AQICityDayDataPublishHistory t where TimePoint>=#{start} and TimePoint<=#{end} and dbo.calAQILevel(AQI)>= '3') c " +
            " GROUP BY dbo.GroupTime_day(timepoint) order by TimePoint" +
            ") a LEFT JOIN regionalpollution r on a.TimePoint=r.monthTime"
    )
    List<Map<String,Object>> getAllPollutionAea(@Param("start") String start, @Param("end") String end);

    /**
     * 得到区域污染城市详情
     * @param start
     * @param end
     * @return
     */
    @Select("select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint," +
            "(CASE when SO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),SO2_24h) end) SO2," +
            "(CASE when NO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),NO2_24h) end) NO2, " +
            "(CASE when PM10_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),PM10_24h) end) PM10, " +
            "(CASE when PM2_5_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),PM2_5_24h) end) PM2_5," +
            "(CASE when O3_8h_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),O3_8h_24h) end) O3," +
            "(CASE when CO_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15,1),CO_24h) end)CO ,AQI,primaryPollutant,dbo.calAQILevel(AQI) level," +
            " case when ISNUMERIC(AQI)=1 and AQI>0 and AQI<=50 then 1 else 0 end level1," +
            " case when ISNUMERIC(AQI)=1 and AQI>50 and AQI<=100 then 1 else 0 end level2," +
            " case when ISNUMERIC(AQI)=1 and AQI>100 and AQI<=150 then 1 else 0 end level3," +
            " case when ISNUMERIC(AQI)=1 and AQI>150 and AQI<=200 then 1 else 0 end level4," +
            " case when ISNUMERIC(AQI)=1 and AQI>200 and AQI<=300 then 1 else 0 end level5," +
            " case when ISNUMERIC(AQI)=1 and AQI>300 then 1 else 0 end level6" +
            " from AQICityDayDataPublishHistory t where TimePoint>=#{start} and TimePoint<=#{end} and dbo.calAQILevel(AQI)>= '3' ")
    List<Map<String,Object>> getPollutionAea(@Param("start") String start, @Param("end") String end);


    /**
     * 得到区域污染天数
     * @param start
     * @param end
     * @return
     */
    @Select("select COUNT(*) areapollutiondays from regionalpollution where status = 1 and monthtime >= #{start} and monthtime <= #{end}")
    int getAreaPollutionDays(@Param("start") String start, @Param("end") String end);


    /**
     * 得到判断后非区域污染天数（本应该用总天数-区域污染天数）
     * @param start
     * @param end
     * @return
     */
    @Select("select COUNT(*) areapollutiondays from regionalpollution where status = 0 and monthtime >= #{start} and monthtime <= #{end}")
    int getNoAreaPollutionDays(@Param("start") String start, @Param("end") String end);


    /**
     * 得到判定天数
     * @param start
     * @param end
     * @return
     */
    @Select("select COUNT(*) areapollutiondays from regionalpollution where  monthtime >= #{start} and monthtime <= #{end}")
    int getDays(@Param("start") String start, @Param("end") String end);


    /**
     * 得到区域污染浓度
     * @param start
     * @param end
     * @return
     */
    @Select("select * from " +
            "(select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint, " +
            "(CASE when SO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),SO2_24h) end) SO2, " +
            "(CASE when NO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),NO2_24h) end) NO2,  " +
            "(CASE when PM10_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),PM10_24h) end) PM10,  " +
            "(CASE when PM2_5_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),PM2_5_24h) end) PM2_5, " +
            "(CASE when O3_8h_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),O3_8h_24h) end) O3, " +
            "(CASE when CO_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15,1),CO_24h) end)CO ,AQI,primaryPollutant,dbo.calAQILevel(AQI) airlevel, " +
            "case when ISNUMERIC(AQI)=1 and AQI>0 and AQI<=50 then 1 else 0 end level1, " +
            "case when ISNUMERIC(AQI)=1 and AQI>50 and AQI<=100 then 1 else 0 end level2, " +
            "case when ISNUMERIC(AQI)=1 and AQI>100 and AQI<=150 then 1 else 0 end level3, " +
            "case when ISNUMERIC(AQI)=1 and AQI>150 and AQI<=200 then 1 else 0 end level4, " +
            "case when ISNUMERIC(AQI)=1 and AQI>200 and AQI<=300 then 1 else 0 end level5, " +
            "case when ISNUMERIC(AQI)=1 and AQI>300 then 1 else 0 end level6 " +
            "from AQICityDayDataPublishHistory t where TimePoint>=#{start} and TimePoint<=#{end} )a " +
            " LEFT JOIN regionalpollution rp "+
            "on rp.monthtime = a.timepoint where rp.status = 1")
    List<Map<String,Object>> getPollutionAeaM(@Param("start") String start, @Param("end") String end);

    /**
     * 得到常态浓度
     * @param start
     * @param end
     * @return
     */
    @Select("select * from " +
            "(select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint, " +
            "(CASE when SO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),SO2_24h) end) SO2, " +
            "(CASE when NO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),NO2_24h) end) NO2,  " +
            "(CASE when PM10_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),PM10_24h) end) PM10,  " +
            "(CASE when PM2_5_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),PM2_5_24h) end) PM2_5, " +
            "(CASE when O3_8h_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),O3_8h_24h) end) O3, " +
            "(CASE when CO_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15,1),CO_24h) end)CO ,AQI,primaryPollutant,dbo.calAQILevel(AQI) airlevel, " +
            "case when ISNUMERIC(AQI)=1 and AQI>0 and AQI<=50 then 1 else 0 end level1, " +
            "case when ISNUMERIC(AQI)=1 and AQI>50 and AQI<=100 then 1 else 0 end level2, " +
            "case when ISNUMERIC(AQI)=1 and AQI>100 and AQI<=150 then 1 else 0 end level3, " +
            "case when ISNUMERIC(AQI)=1 and AQI>150 and AQI<=200 then 1 else 0 end level4, " +
            "case when ISNUMERIC(AQI)=1 and AQI>200 and AQI<=300 then 1 else 0 end level5, " +
            "case when ISNUMERIC(AQI)=1 and AQI>300 then 1 else 0 end level6 " +
            "from AQICityDayDataPublishHistory t where TimePoint>=#{start} and TimePoint<=#{end} ) a " +
            " LEFT JOIN regionalpollution rp "+
            "on rp.monthtime = a.timepoint where rp.status = 0")
    List<Map<String,Object>> getNormalPollution(@Param("start") String start, @Param("end") String end);

    /**
     * 保存判定
     * @param monthtime
     * @param status
     */
    @Insert("Insert into regionalpollution (monthtime,status) values (#{monthtime},#{status})")
    void saveAreaPollution(@Param("monthtime") String monthtime, @Param("status") String status);

    /**
     * 修改判定
     * @param monthtime
     * @param status
     */
    @Update("Update regionalpollution set status = #{status} where monthtime = #{monthtime}")
    void updateAreaPollution(@Param("monthtime") String monthtime, @Param("status") String status);
}
