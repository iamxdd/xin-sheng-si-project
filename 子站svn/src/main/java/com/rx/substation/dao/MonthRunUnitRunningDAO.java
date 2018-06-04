package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.provider.OrderProvider;

public interface MonthRunUnitRunningDAO {
	
	@Select("select DISTINCT top 1 * from station_run_data where monthTime >= #{start}  and monthTime <= #{end} and runUnitId = #{runUnitId} order by runUnitId,runQuality")
	Map<String,Object> findRunitQualityById(@Param("start") String start, @Param("end") String end, @Param("runUnitId") String runUnitId);
	
	@SelectProvider(type = OrderProvider.class, method = "findCompleteStatusByConditions")
	Map<String, Object> findRunUnitQualityByConditions(@Param("start") String start, @Param("end") String end, @Param("runUnitId") String runUnitId);
	
	@Insert("insert into station_run_data (runUnitId,status1,status2,status3,status4,status5,status6,runQuality,monthTime) "
			+ "values (#{runUnitId},#{status1},#{status2},#{status3},#{status4},#{status5},#{status6},#{runQuality},#{monthTime})")
	void saveMonthRunUnit(Map<String, Object> saveMonthRunUnitCheck);

	
	@Delete("delete from station_run_data where runUnitId = #{runUnitId} and monthTime = #{monthTime}")
	void delete(@Param("runUnitId") String runUnitId, @Param("monthTime") String monthTime);
	
	
	
}
