package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.provider.OrderProvider;

public interface MonthRunUnitQCDAO {
	
	@SelectProvider(type = OrderProvider.class, method = "findRunUnitQcByConditions")
	List<Map<String,Object>> findRunUnitQcByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                       @Param("start") String start, @Param("end") String end, @Param("stations") String stations);
	
	@SelectProvider(type = OrderProvider.class, method = "findRunUnitQc2")
	List<Map<String, Object>> findMonthRunUnitQC2(@Param("id") String id);
	
	
	@Select("select * from station_working_data where checkTime >= #{start} and checkTime <= #{end} order by stationCode")
	List<Map<String, Object>> findMonthRunUnitQC1(@Param("start") String start, @Param("end") String end);

	@Insert("insert into station_working_data (stationCode,stationName,checkTime,runUnitId,cityId,manager,deduct) "
			+ "values (#{stationCode},#{stationName},#{checkTime},#{runUnitId},#{cityId},#{manager},#{deduct})")
	void saveRunUnitWork(Map<String, Object> runUnitWork);

	@Insert("insert into station_working2_data (stationCode,type,lastTime,thisTime,theoryCycle,inputCycle,performance,mark,id) "
			+ "values (#{stationCode},#{type},#{lastTime},#{thisTime},#{theoryCycle},#{inputCycle},#{performance},#{mark},#{id})")
	void saveStationWork(Map<String, Object> map);

	
	
}
