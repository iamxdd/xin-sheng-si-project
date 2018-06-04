package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.domain.CalValidateScore;
import com.rx.substation.provider.OrderProvider;

public interface MonthValidateDAO {

//	@Select("select datename(year,o.daydata)+datename(month,o.daydata) datamonth,count(*) validatedays FROM(select * from(select q.daydata,q.covalidate,w.no2validate,e.noxvalidate,r.pm25validate,t.pm10validate FROM"
//			+ "(SELECT CONVERT(VARCHAR(10) ,a.TimePoint,120) daydata,count(*) covalidate from Air_h_2014_${stationCode}_Src a WHERE a.PollutantCode='CO' GROUP BY CONVERT(VARCHAR(10) ,a.TimePoint,120)) q ,"
//			+ "(SELECT CONVERT(VARCHAR(10) ,a.TimePoint,120) daydata,count(*) no2validate from Air_h_2014_${stationCode}_Src a WHERE a.PollutantCode='NO2' GROUP BY CONVERT(VARCHAR(10) ,a.TimePoint,120)) w,"
//			+ "(SELECT CONVERT(VARCHAR(10) ,a.TimePoint,120) daydata,count(*) noxvalidate from Air_h_2014_${stationCode}_Src a WHERE a.PollutantCode='NOx' GROUP BY CONVERT(VARCHAR(10) ,a.TimePoint,120)) e,"
//			+ "(SELECT CONVERT(VARCHAR(10) ,a.TimePoint,120) daydata,count(*) pm25validate from Air_h_2014_${stationCode}_Src a WHERE a.PollutantCode='PM2.5' GROUP BY CONVERT(VARCHAR(10) ,a.TimePoint,120))r,"
//			+ "(SELECT CONVERT(VARCHAR(10) ,a.TimePoint,120) daydata,count(*) pm10validate from Air_h_2014_${stationCode}_Src a WHERE a.PollutantCode='PM10' GROUP BY CONVERT(VARCHAR(10) ,a.TimePoint,120)) t "
//			+ "where q.daydata = w.daydata AND q.daydata = e.daydata and q.daydata = r.daydata and t.daydata = q.daydata) n "
//			+ "where n.covalidate >= '20' and n.no2validate >= '20'and n.noxvalidate >= '20' and n.pm25validate >= '20'and n.pm10validate >= '20' and n.pm10validate >= '20') o where o.daydata >= #{start} and o.daydata <= #{end} "
//			+ "GROUP BY datename(year,o.daydata)+datename(month,o.daydata)")
	@Select("SELECT dbo.GroupTime_month(TimePoint) TimePoint,StationCode,StationName,"
			+ "SUM(CASE WHEN ISNUMERIC(SO2)=1 THEN 1 ELSE 0 END) SO2,"
			+ "SUM(CASE WHEN ISNUMERIC(NO2)=1 THEN 1 ELSE 0 END) NO2,"
			+ "SUM(CASE WHEN ISNUMERIC(CO)=1 THEN 1 ELSE 0 END) CO,"
			+ "SUM(CASE WHEN ISNUMERIC(PM10)=1 THEN 1 ELSE 0 END) PM10,"
			+ "SUM(CASE WHEN ISNUMERIC(PM2_5)=1 THEN 1 ELSE 0 END) PM2_5,"
			+ "SUM(CASE WHEN ISNUMERIC(O3)=1 THEN 1 ELSE 0 END) O3,"
			+ "SUM(CASE WHEN ISNUMERIC(AQI)=1 THEN 1 ELSE 0 END) AQI,"
//			+ "SUM(CASE WHEN (NOT(ISNUMERIC(SO2)=0 and ISNUMERIC(NO2)=0 and ISNUMERIC(CO)=0) and ISNUMERIC(PM10)=1 and ISNUMERIC(PM2_5)=1 and ISNUMERIC(O3)=1 )THEN 1 ELSE 0 END) Total,"
			+ "SUM(CASE WHEN ISNUMERIC(AQI)=1 THEN 1 ELSE 0 END) Total,"
			+ "row_number() OVER(PARTITION BY dbo.GroupTime_month(TimePoint) ORDER BY StationCode) Rank "
			+ "FROM (SELECT b.TimePoint,a.StationCode,a.PositionName StationName,b.SO2_1h SO2,b.NO2_1h NO2,b.O3_1h O3,b.CO_1h CO,"
			+ "b.PM10_1h PM10,b.PM2_5_1h PM2_5,b.AQI FROM Station a LEFT JOIN AQIStationDayDataPublishHistory b "
			+ "ON a.StationCode=b.StationCode WHERE  b.TimePoint>=#{start} AND b.TimePoint<=#{end} AND b.StationCode = #{stationCode}) c "
			+ "GROUP BY dbo.GroupTime_month(TimePoint),StationCode,StationName")
	Map<String, Object> getMonthValidate(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

	@Select("select smt.TimePoint,smt.StationName,smt.SO2,smt.NO2,smt.PM10,smt.PM2_5,smt.CO,smt.O3,smt.total from StationMonthTransfer smt LEFT JOIN"
	+"(select s.StationCode,ssi.BrandId from SubStationInstrument ssi LEFT JOIN Station s on ssi.StationId = s.StationId ) a "
	+"ON smt.StationCode = a.StationCode WHERE a.BrandId=#{BrandId} and smt.TimePoint >= #{start} and smt.TimePoint <= #{end} ORDER BY smt.TimePoint DESC")
	List<CalValidateScore> getMonthValidateByBrand(@Param("start") String start, @Param("end") String end, @Param("stationCode") int BrandId);
	
	
	@Select("select * from validate_score smt where monthTime >= #{start} and monthTime <= #{end}")
	List<CalValidateScore> getMonthValidateByCity(@Param("start") String start, @Param("end") String end);
	
	
	@Select("select smt.* from validate_score smt WHERE monthTime >= #{start} and monthTime <= #{end} AND StationCode = #{stationCode}")
	List<CalValidateScore> getMonthValidateByStation(@Param("start") String start, @Param("end") String end, @Param("StationCode") String stationCode);

	
	@Select("select * from  validate_score  WHERE monthTime >= #{start} and monthTime <= #{end} order by stationCode")
	List<Map<String, Object>> findMonthValidateDays(@Param("start") String start, @Param("end") String end);

	@Insert("insert into validate_score (StationCode,StationName,monthTime,TheoryDays,PowerCutDays,Others,ShouldDays,ValidatyDays,Scores) "
			+ "values (#{stationCode},#{stationName},#{monthTime},#{theoryDays},#{powerCutDays},#{others},#{shouldDays},#{validatyDays},#{scores})")
	void save(CalValidateScore saveMonthValidate);
	
	@SelectProvider(type = OrderProvider.class, method = "findValidateByConditions")
	List<Map<String, Object>> findValidateByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                       @Param("start") String start, @Param("end") String end, @Param("stations") String stations, @Param("param") String param, @Param("sortstatus") String sortstatus);

	@Select("select * from (SELECT CONVERT(VARCHAR(10),a.TimePoint,120) dayData,count(*) validatedata from air_h_2014_${stationCode}_Src a "
			+ "where convert(varchar(8),TimePoint,108) between #{datatime1} and #{datatime2} and a.PollutantCode = 'O3' "
			+ "GROUP BY CONVERT(VARCHAR(10),a.TimePoint,120)) b where b.dayData = '${month}-${day}'")
	Map<String,Object> getMonthO3Validate(@Param("datatime1") String datatime1, @Param("datatime2") String datatime2, @Param("month") String month, @Param("day") String day, @Param("stationCode") String stationCode);
	
}
