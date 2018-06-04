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
public interface BurnPollutionDAO {

    /**
     * 城市燃烧污染
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
            "primaryPollutant " +
            "from AQICityDayDataPublishHistory t where TimePoint>=#{start} and TimePoint<=#{end} and citycode = #{citycode}" +
            ") a LEFT JOIN (select monthTime,citycode,status from burnpollution) b on a.TimePoint=b.monthTime and a.citycode = b.citycode")
    List<Map<String,Object>> getBurnPollution(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode);

    /**
     * 保存燃烧判定
     * @param monthtime
     * @param citycode
     * @param cityname
     * @param so2
     * @param no2
     * @param o3
     * @param co
     * @param pm2_5
     * @param pm10
     * @param status
     */
    @Insert("Insert into burnpollution (monthtime,citycode,cityname,so2,no2,o3,co,pm2_5,pm10,status) values " +
            "(#{monthtime},#{citycode},#{cityname},#{so2},#{no2},#{o3},#{co},#{pm2_5},#{pm10},#{status})")
    void saveBurnPollution(@Param("monthtime") String monthtime, @Param("citycode") String citycode,
                           @Param("cityname") String cityname,
                           @Param("so2") String so2, @Param("no2") String no2,
                           @Param("o3") String o3, @Param("co") String co,
                           @Param("pm2_5") String pm2_5, @Param("pm10") String pm10,
                           @Param("status") String status);

    /**
     * 直接算全省焚烧浓度日平均的平均值
     * @param start
     * @param end
     * @param status
     * @return
     */
    @Select("select  AVG(a.no2) avgno2,AVG(a.so2) avgso2,AVG(a.co) avgco,AVG(a.o3) avgo3,AVG(a.pm2_5) avgpm2_5,AVG(a.pm10) avgpm10 from " +
            "(select citycode,AVG(no2) no2,AVG(so2) so2,AVG(co) co,AVG(o3) o3,AVG(pm2_5) pm2_5,AVG(pm10) pm10 from" +
            " burnpollution where status = #{status} AND monthtime >= #{start} and monthtime <= #{end} GROUP BY citycode) a")
    Map<String,Object> getBurningM(@Param("start") String start, @Param("end") String end, @Param("status") String status);


    /**
     * 城市焚烧浓度日均的平均值
     * @param start
     * @param end
     * @param citycode
     * @param status
     * @return
     */
    @Select("select citycode,AVG(no2) no2,AVG(so2) so2,AVG(co) co,AVG(o3) o3,AVG(pm2_5) pm2_5,AVG(pm10) pm10 from burnpollution " +
            "where status = #{status} AND monthtime >= #{start} and monthtime <= #{end} and citycode = #{citycode} GROUP BY citycode")
    Map<String,Object> getBurningMByCity(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode, @Param("status") String status);

    /**
     * 城市焚烧浓度
     * @param start
     * @param end
     * @param citycode
     * @param status
     * @return
     */
    @Select("select COUNT(*) burnpollutiondays from burnpollution where status = #{status} AND monthtime >= #{start} and monthtime <= #{end} and citycode = #{citycode}")
    int getBurningDays(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode, @Param("status") String status);

    /**
     * 全省每个城市日均平均值
     * @param start
     * @param end
     * @param status
     * @return
     */
    @Select("select citycode,AVG(no2) no2,AVG(so2) so2,AVG(co) co,AVG(o3) o3,AVG(pm2_5) pm2_5,AVG(pm10) pm10 from burnpollution " +
            "where status = #{status} AND monthtime >= #{start} and monthtime <= #{end} GROUP BY citycode")
    List<Map<String,Object>> getBurningMAllCity(@Param("start") String start, @Param("end") String end, @Param("status") String status);

    /**
     * 修改燃烧判定
     * @param monthtime
     * @param citycode
     * @param cityname
     * @param so2
     * @param no2
     * @param o3
     * @param co
     * @param pm2_5
     * @param pm10
     * @param status
     */
    @Update("update burnpollution set cityname = #{cityname},so2=#{so2},no2=#{no2},o3=#{o3},co=#{co},pm2_5=#{pm2_5},pm10=#{pm10},status=#{status} where " +
            "monthtime = #{monthtime} and citycode = #{citycode}")
    void updateBurnPollution(@Param("monthtime") String monthtime, @Param("citycode") String citycode,
                             @Param("cityname") String cityname,
                             @Param("so2") String so2, @Param("no2") String no2,
                             @Param("o3") String o3, @Param("co") String co,
                             @Param("pm2_5") String pm2_5, @Param("pm10") String pm10,
                             @Param("status") String status);


    /**
     * 秸秆焚烧天数
     * @param start
     * @param end
     * @return
     */
    @Select("select COUNT(*) burnpollutiondays from burnpollution where monthtime >= #{start} and monthtime <= #{end} and citycode = #{citycode}")
    int getDays(@Param("start") String start, @Param("end") String end, @Param("citycode") String citycode);
}
