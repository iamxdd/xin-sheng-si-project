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
public interface SandPollutionDAO {

    /**
     * 沙暴污染
     * @param start
     * @param end
     * @param citycode
     * @return
     */
    @Select("SELECT * FROM (" +
            "select cityName,citycode, CONVERT(VARCHAR(20),TimePoint,120) timepoint," +
            "(CASE when SO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),SO2_24h) end) SO2," +
            "(CASE when NO2_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),NO2_24h) end) NO2, " +
            "(CASE when O3_8h_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),O3_8h_24h) end) O3," +
            "(CASE when CO_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15,1),CO_24h) end)CO ," +
            "(CASE when PM10_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),PM10_24h) end) PM10, " +
            "(CASE when PM2_5_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),PM2_5_24h) end) PM2_5," +
            "(CASE when PM2_5_24h  in ('—','','NA') then null else CONVERT(NUMERIC(15),PM2_5_24h) end)/(CASE when PM10_24h in ('—','','NA') then null else CONVERT(NUMERIC(15),PM10_24h) end) PM2_5PM10," +
            "primaryPollutant " +
            "from AQICityDayDataPublishHistory t where TimePoint>=#{start} and TimePoint<=#{end} and citycode = #{citycode}" +
            ") a LEFT JOIN (select monthTime,citycode,status from sandpollution) b on a.TimePoint=b.monthTime and a.citycode=b.citycode")
    List<Map<String,Object>> getSandPollution(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode);

    /**
     * 保存沙暴污染判定
     * @param monthtime
     * @param citycode
     * @param cityname
     * @param so2
     * @param no2
     * @param o3
     * @param co
     * @param pm2_5
     * @param pm10
     * @param pm2_5pm10
     * @param status
     */
    @Insert("Insert into sandpollution (monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,pm2_5pm10,status) values " +
            "(#{monthtime},#{citycode},#{cityname},#{so2},#{no2},#{o3},#{co},#{pm2_5},#{pm10},#{pm2_5pm10},#{status})")
    void saveSandPollution(@Param("monthtime") String monthtime, @Param("citycode") String citycode,
                           @Param("cityname") String cityname,
                           @Param("so2") String so2, @Param("no2") String no2,
                           @Param("o3") String o3, @Param("co") String co,
                           @Param("pm2_5") String pm2_5, @Param("pm10") String pm10,
                           @Param("pm2_5pm10") String pm2_5pm10, @Param("status") String status);


    /**
     * 得到城市平均浓度
     * @param start
     * @param end
     * @param citycode
     * @param status
     * @return
     */
    @Select("select citycode,AVG(no2) no2,AVG(so2) so2,AVG(co) co,AVG(o3) o3,AVG(pm2_5) pm2_5,AVG(pm10) pm10 from sandpollution " +
            "where status = #{status} AND monthtime >= #{start} and monthtime <= #{end} and citycode = #{citycode} GROUP BY citycode")
    Map<String,Object> getSandMByCity(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode, @Param("status") String status);

    /**
     * 得到沙暴影响天数
     * @param start
     * @param end
     * @param citycode
     * @param status
     * @return
     */
    @Select("select COUNT(*) sandpollutiondays from sandpollution where status = #{status} AND monthtime >= #{start} and monthtime <= #{end} and citycode = #{citycode}")
    int getBurningMAllCity(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode, @Param("status") String status);

    /**
     * 修改沙暴影响判定
     * @param monthtime
     * @param citycode
     * @param cityname
     * @param so2
     * @param no2
     * @param o3
     * @param co
     * @param pm2_5
     * @param pm10
     * @param pm2_5pm10
     * @param status
     */
    @Update("update sandpollution set cityname = #{cityname},so2=#{so2},no2=#{no2},o3=#{o3},co=#{co},pm2_5=#{pm2_5},pm10=#{pm10},pm2_5pm10=#{pm2_5pm10},status= #{status}" +
            " where monthtime =#{monthtime} and citycode =#{citycode}")
    void updateSandPollution(@Param("monthtime") String monthtime, @Param("citycode") String citycode,
                             @Param("cityname") String cityname,
                             @Param("so2") String so2, @Param("no2") String no2,
                             @Param("o3") String o3, @Param("co") String co,
                             @Param("pm2_5") String pm2_5, @Param("pm10") String pm10,
                             @Param("pm2_5pm10") String pm2_5pm10, @Param("status") String status);

    @Select("select COUNT(*) sandpollutiondays from sandpollution where  monthtime >= #{start} and monthtime <= #{end} and citycode = #{citycode}")
    int getDays(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode);
}
