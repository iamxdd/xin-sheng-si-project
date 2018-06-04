package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.rx.substation.domain.Inspection;
import com.rx.substation.provider.OrderProvider;

public interface MonthQCInspectionDAO {
	@Select("select * from qcInspection where checkTime>=#{start} and checkTime<=#{end} order by stationCode")
	List<Map<String,Object>> getMonthQCInspection(@Param("start") String start, @Param("end") String end);
	
	@Select("select deduct from qcInspection where stationCode = #{stationCode}")
	Double getDeductByStation(@Param("stationCode") String stationCode);
	
	@Insert("insert into qcInspection (stationCode,stationName,deduct,checkTime,checkPerson,mark,detailId,runPerson,runTime,runName,timePoint) values "
			+ "(#{stationCode},#{stationName},#{deduct},#{checkTime},#{checkPerson},#{mark},#{detailId},#{runPerson},#{runTime},#{runName},#{timePoint})")
	void saveDeductScore(Map<String, Object> deductMap);

	
	@SelectProvider(type = OrderProvider.class, method = "findDeductByConditions")
	List<Map<String,Object>> findDeductByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                    @Param("start") String start, @Param("end") String end, @Param("stations") String stations);

	@Select("select * from qcInspection2 where detailId = #{detailId}")
	List<Map<String,Object>> findDeductByDetailId(@Param("detailId") String detailId);

	@Insert("insert into qcInspection2 (detailId,checkWorkId,status,score,detail,mark) values "
			+ "(#{detailId},#{checkWorkId},#{status},#{score},#{detail},#{mark})")
    void saveDeductDetail(Map<String, Object> detailMap);

	@Select("select deduct from qcInspection where detailId = #{detailId}")
	Double findTotalDeduct(@Param("detailId") String detailId);

	@Delete("delete from qcInspection where detailId = #{detailId}")
    void deleteDeduct(@Param("detailId") String detailId);

	@Delete("delete from qcInspection2 where detailId = #{detailId}")
	void deleteDetailId(@Param("detailId") String detailId);


	@Update("update qcInspection set stationCode = #{stationCode},stationName = #{stationName}," +
			"deduct = #{deduct},checkTime = #{checkTime},checkPerson = #{checkPerson},mark = #{mark},runPerson = #{runPerson}," +
			"runTime = #{runTime},runName = #{runName}" +
			" where detailId = #{detailId}")
	void updateDeductScore(Map<String, Object> deductMap);

	@Update("update qcInspection2 set detail = #{detail},score = #{score},status = #{status}" +
			" where detailId = #{detailId} and checkWorkId = #{checkWorkId}")
	void updateDeductDetail(Map<String, Object> detailMap);

	@Select("select checkPerson from qcInspection where detailId = #{detailId}")
	String findCheckPerson(@Param("detailId") String detailId);

	@Select("select checkTime from qcInspection where detailId = #{detailId}")
	String findCheckTime(@Param("detailId") String detailId);

	@Select("SELECT * FROM " +
			"(SELECT DISTINCT a.*,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.CityId FROM qcInspection a left JOIN " +
			"(SELECT DISTINCT x.StationCode,x.StationTypeId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode,x.CityId  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  " +
			"on a.StationCode = b.StationCode) " +
			"m LEFT JOIN County n ON m.CountyId = n.Id where detailId = #{detailId}")
	Map<String,Object> findDetailByDetailId(@Param("detailId") String detailId);

	@SelectProvider(type = OrderProvider.class, method = "countInspection")
	List<Map<String,Object>> countInspection(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                             @Param("start") String start, @Param("end") String end, @Param("stations") String stations);

	@Select("select count(0) total from RunUnit where runCode = #{runUnitId} and runCode != '0' ")
	int getTotalByRunUnit(@Param("runUnitId") String runUnitId);

	@Select("select count(0) total from RunUnit where CityId = #{cityId} and runCode != '0'")
	int getTotalByCity(@Param("cityId") String cityId);
}
