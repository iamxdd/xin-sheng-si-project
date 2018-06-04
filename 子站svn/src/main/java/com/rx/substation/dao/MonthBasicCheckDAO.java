package com.rx.substation.dao;

import com.rx.substation.provider.OrderProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface MonthBasicCheckDAO {
	@Select("select * from BasicCheck where TimePoint>=#{start} and TimePoint<=#{end} order by stationCode")
	List<Map<String,Object>> getMonthBasicCheck(@Param("start") String start, @Param("end") String end);

	@Insert("insert into BasicCheck (StationCode,StationName,TimePoint,Person1,Person2,CheckTime1,CheckTime2,Others,detailId) values "
			+ "(#{StationCode},#{StationName},#{TimePoint},#{Person1},#{Person2},#{CheckTime1},#{CheckTime2},#{Others},#{DetailId})")
	void saveBasicCheck(Map<String, Object> BasicCheck);


	@SelectProvider(type = OrderProvider.class, method = "findBasicCheckByConditions")
	List<Map<String,Object>> findBasicCheckByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                        @Param("start") String start, @Param("end") String end, @Param("stations") String stations);

	@Select("select * from BasicCheckDetail where detailId = #{detailId}")
	List<Map<String,Object>> findBasicCheckByDetailId(@Param("detailId") String detailId);

	@Insert("insert into BasicCheckDetail (DetailId,Id,DetailMark,Status) values "
			+ "(#{DetailId},#{Id},#{DetailMark},#{Status})")
    void saveBasicCheckDetail(Map<String, Object> detailMap);

	@Delete("delete from BasicCheck where detailId = #{detailId}")
    void deleteBasicCheck(@Param("detailId") String detailId);

	@Delete("delete from BasicCheckDetail where detailId = #{detailId}")
	void deleteBasicCheckDetail(@Param("detailId") String detailId);


	@Update("update BasicCheck set StationCode = #{StationCode},StationName = #{StationName}," +
			"TimePoint = #{TimePoint},Person1 = #{Person1},Person2 = #{Person2},CheckTime1 = #{CheckTime1},CheckTime2 = #{CheckTime2}," +
			"Others = #{Others},DetailId = #{DetailId}" +
			" where DetailId = #{DetailId}")
	void updateBasicCheck(Map<String, Object> deductMap);

	@Update("update BasicCheckDetail set DetailMark = #{DetailMark},Status = #{Status}" +
			" where DetailId = #{DetailId} and Id = #{Id}")
	void updateBasicCheckDetail(Map<String, Object> detailMap);

	@Select("SELECT * FROM " +
			"(SELECT DISTINCT a.*,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName,b.CityId FROM BasicCheck a left JOIN " +
			"(SELECT DISTINCT x.StationCode,x.StationTypeId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode,x.CityId  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  " +
			"on a.StationCode = b.StationCode) " +
			"m LEFT JOIN County n ON m.CountyId = n.Id where detailId = #{detailId}")

	Map<String, Object> findStation(@Param("detailId") String detailId);
}
