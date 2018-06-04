package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.domain.RunUnitCheck;
import com.rx.substation.provider.OrderProvider;

public interface MonthCompleteStatusDAO {
	
    
    @Select("select * from run_unit_data where checkMonth >= #{start} and checkMonth <= #{end} order by stationCode")
    List<RunUnitCheck> findMonthCompleteStatus(@Param("start") String start, @Param("end") String end);
    
    @SelectProvider(type = OrderProvider.class, method = "findCompleteStatusByConditions")
    Map<String, Object> findCompleteStatusByConditions(@Param("area") String area, @Param("runUnit") String runUnit, @Param("stationTypeId") String stationTypeId,
                                                       @Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    @Select("select coefficient from run_unit_data where stationCode = #{stationCode}")
	Double getCoefficientByStationCode(@Param("stationCode") String stationCode);

    @Select("select  StationCode,createDate  from tableAuditStatus  where createDate >= #{start} and createDate <=#{end} and StationCode = #{stationCode} AND type = 1 and tableid = 0" +
			" ORDER BY StationCode,createDate ")
    List<Map<String, Object>> getWeekCompleteStatus(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    @Select("select TOP 1 StationCode,createDate  from tableAuditStatus  where createDate >= #{start} and createDate <=#{end} and StationCode = #{stationCode} AND type = 1 and tableid = 1" +
			" ORDER BY StationCode,createDate ")
	Map<String, Object> getMonthCompleteStatus(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    @Select("select TOP 1 StationCode,createDate  from tableAuditStatus  where createDate >= #{start} and createDate <=#{end} and StationCode = #{stationCode} AND type = 1 and tableid = 2" +
            " ORDER BY StationCode,createDate ")
    Map<String,Object> getQuarterCompleteStatus(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    @Select("select TOP 1 StationCode,createDate  from tableAuditStatus  where createDate >= #{start} and createDate <=#{end} and StationCode = #{stationCode} AND type = 1 and tableid = 3" +
			" ORDER BY StationCode,createDate ")
	Map<String, Object> getHalfYearCompleteStatus(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    @Select("select TOP 1 StationCode,createDate  from tableAuditStatus  where createDate >= #{start} and createDate <=#{end} and StationCode = #{stationCode} AND type = 1 and tableid = 4" +
			" ORDER BY StationCode,createDate ")
	Map<String, Object> getYearCompleteStatus(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

	@Select("select StationCode,createDate  from tableAuditStatus  where createDate >= #{start} and createDate <=#{end} and StationCode = #{station} and type = 1 and tableid = #{tableid}" +
            " ORDER BY StationCode,createDate")
    List<Map<String,Object>> getTableCompleteStatus(@Param("start") String start, @Param("end") String end, @Param("tableid") Integer tableid, @Param("station") String station);

	@Select("SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.Status,y.stationName,y.runName,y.runCode,x.CountyId,z.CountyName  FROM Station x " +
            "LEFT JOIN RunUnit y ON x.StationCode = y.StationCode  LEFT JOIN County z ON x.CountyId =z.id where x.stationTypeId in ('3','4','7') and x.status = 1 and x.stationCode = #{station} ORDER BY CityId ")
    Map<String,Object> getStationMsg(@Param("station") String station);

    @Select("select Top 1 createDate  from tableAuditStatus  where createDate < #{start}  and StationCode = #{stationCode} AND type = 1 and tableid = 0" +
            " ORDER BY StationCode,createDate DESC")
    String getLastWeekDate(@Param("start") String start, @Param("stationCode") String stationCode);

    @Select("select Top 1 createDate  from tableAuditStatus  where createDate < #{start}  and StationCode = #{stationCode} AND type = 1 and tableid = 1" +
            " ORDER BY StationCode,createDate DESC")
    String getLastMonthDate(@Param("start") String start, @Param("stationCode") String stationCode);

    @Select("select Top 1 createDate  from tableAuditStatus  where createDate < #{start} and StationCode = #{stationCode} AND type = 1 and tableid = 4" +
            " ORDER BY StationCode,createDate DESC")
    String getLastQuarterDate(@Param("start") String start, @Param("stationCode") String stationCode);


    @Select("select Top 1 createDate  from tableAuditStatus  where createDate < #{start}  and StationCode = #{stationCode} AND type = 1 and tableid = 2" +
            " ORDER BY StationCode,createDate DESC")
    String getLastHalfYearDate(@Param("start") String start, @Param("stationCode") String stationCode);

    @Select("select Top 1 createDate  from tableAuditStatus  where createDate < #{start}  and StationCode = #{stationCode} AND type = 1 and tableid = 3" +
            " ORDER BY StationCode,createDate DESC")
    String getLastYearDate(@Param("start") String start, @Param("stationCode") String stationCode);

}
