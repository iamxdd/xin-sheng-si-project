package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.provider.OrderProvider;

public interface MonthStationRunningDAO {

	@Select("select * from run_unit_data where checkMonth >= #{start}  and checkMonth <= #{end} and stationCode = #{stationCode}")
	List<Map<String,Object>> findStationRunningDAO(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);
	
	
	@Insert("insert into run_unit_data (stationCode,stationName,checkMonth,transferStatus,validateStatus,scoresStatus,coefficient,runUnitId) "
			+ "values (#{stationCode},#{stationName},#{checkMonth},#{transferStatus},#{validateStatus},#{scoresStatus},#{coefficient},#{runUnitId})")
	void saveMonthStation(Map<String, Object> saveMonthStationCheck);
	
	@SelectProvider(type = OrderProvider.class, method = "findRunitQualityByConditions")
	List<Map<String, Object>> findStationRunningByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                             @Param("start") String start, @Param("end") String end, @Param("stations") String stations);
	
	@Select("select coefficient from run_unit_data where stationCode = #{stationCode} and checkMonth >= #{start} and checkMonth <= #{end}")
	Double getCoefficientByStationCode(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

	/**
	 * 得到某个站点所对应运行单位所有站点StationCode
	 * @param stationCode
	 * @return
	 */
	@Select("select DISTINCT stationCode from RunUnit where runCode = (select DISTINCT runCode from RunUnit where stationCode=#{stationCode})")
	List<String> getOperationCompanyStations(@Param("stationCode") String stationCode);

	
	@Select("select b.runCode from Station a left JOIN RunUnit b on a.stationCode = b.StationCode  where a.StationCode=#{stationCode} ")
	String getOperationCompany(@Param("stationCode") String stationCode);

	@Select("select b.CityId from Station a left JOIN RunUnit b on a.stationCode = b.StationCode  where a.StationCode=#{stationCode} ")
	String getCityId(String stationCode);



	

}
