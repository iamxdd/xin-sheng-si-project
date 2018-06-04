package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.domain.Transfer;
import com.rx.substation.provider.OrderProvider;

/**
 * @author dcx
 *
 */
public interface MonthTransferDAO {
	/**
	 * 根据运营单位查询传输率
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param BrandId 运营商id
	 * @return
	 */
	@Select("select smt.TimePoint,smt.StationName,smt.SO2,smt.NO2,smt.PM10,smt.PM2_5,smt.CO,smt.O3,smt.total from StationMonthTransfer smt LEFT JOIN"
	+"(select s.StationCode,ssi.BrandId from SubStationInstrument ssi LEFT JOIN Station s on ssi.StationId = s.StationId ) a "
	+"ON smt.StationCode = a.StationCode WHERE a.BrandId=#{BrandId} and smt.TimePoint >= #{start} and smt.TimePoint <= #{end} ORDER BY smt.TimePoint DESC")
	List<Map<String, Object>> getMonthTransferByBrand(@Param("start") String start, @Param("end") String end, @Param("stationCode") int BrandId);
	
	
	@Select("select * from CityMonthTransfer cmt where TimePoint >= #{start} and TimePoint <= #{end} where cmt.CityCode = #{cityCode}")
	List<Map<String, Object>> getMonthTransferByCity(@Param("start") String start, @Param("end") String end, @Param("cityCode") String cityCode);
	
	
	@Select("select smt.* from StationMonthTransfer smt WHERE TimePoint >= #{start} and TimePoint <= #{end} AND StationCode = #{stationCode}")
	List<Map<String, Object>> getMonthTransferByStation(@Param("start") String start, @Param("end") String end, @Param("StationCode") String stationCode);

	
	@Select("select smt.* from StationMonthTransfer smt WHERE smt.TimePoint >= #{start} and smt.TimePoint <= #{end} and smt.StationCode = #{stationCode} order by smt.StationCode ")
	Map<String, Object> getMonthTransfer(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

	
	@Insert("insert into transfer_scores (stationCode,stationName,monthTime,so2,no2,o3,co,pm10,pm2_5,avgTransfer,status,scores) values "
			+ "(#{stationCode},#{stationName},#{monthTime},#{so2},#{no2},#{o3},#{co},#{pm10},#{pm2_5},#{avgTransfer},#{status},#{scores})")
	void save(Transfer saveMonthTransfer);

	@Select("select ts.* from transfer_scores ts LEFT JOIN station s ON ts.stationCode=s.StationCode WHERE ts.monthTime >= #{start} and ts.monthTime <= #{end} "
			+ "order by ts.monthTime,ts.stationCode")
	List<Map<String, Object>> getMonthTransferScore(@Param("start") String start, @Param("end") String end);

	@SelectProvider(type = OrderProvider.class, method = "findTansferByConditions")
	List<Map<String, Object>> findTansferByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                      @Param("start") String start, @Param("end") String end, @Param("stations") String stations, @Param("param") String param, @Param("sortstatus") String sortstatus);
	
	
	
}
