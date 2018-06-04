package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.rx.substation.provider.OrderProvider;

public interface StationDAO {

	
	@Select("select a.StationCode from StationMonthTransfer a LEFT JOIN " +
			"Station b on a.stationcode = b.stationcode  where  TimePoint >= #{start} AND TimePoint <= #{end} " +
			"and b.status = 1 and b.stationTypeId in ('3','4','7') order by a.StationCode ")


	List<String> getAllStationCode(@Param("start") String start, @Param("end") String end);
	
	@Select("select s.StationCode,s.PositionName,s.StationTypeId,s.Latitude,s.Longitude,st.StationTypeName from Station s left join StationType st " +
			" on s.StationTypeId = st.StationTypeId where s.status = 1 order by StationCode ")
	List<Map<String,Object>> getAllStation();

	@Select("select PositionName from Station where StationCode = #{stationCode}")
	String getStationNameByStationCode(@Param("stationCode") String stationCode);
	
//	@SelectProvider(type = OrderProvider.class, method = "findStationByCondition")
	@Select("select DISTINCT StationCode,PositionName as stationName from Station where cityId = #{cityId}")
	List<Map<String,Object>> getAllStationMassage(@Param("cityId") String cityId);
	
//	@SelectProvider(type = OrderProvider.class, method = "findStationTypeByCondition")
	@Select("select DISTINCT st.StationTypeId,st.StationTypeName FROM "
			+ "(SELECT DISTINCT s.StationCode,s.StationTypeId,s.CityId,s.Area,r.stationName,r.runName,r.runCode  FROM Station s "
			+ "LEFT JOIN RunUnit r ON s.StationCode = r.StationCode) "
			+ "a LEFT JOIN StationType st on a.stationTypeId = st.stationTypeId where a.stationTypeId in ('3','4','7') order by stationTypeId DESC")
	List<Map<String,Object>> getStationTypeId();
	
//	@SelectProvider(type = OrderProvider.class, method = "findCityByCondition")
	@Select("select DISTINCT CityName,CityCode from City where status = 1 order by CityCode")
	List<Map<String, Object>> getCityMsg();

	@Select("select DISTINCT id CountyId,CountyName from County where cityCode = #{cityCode} and status = 1 and CountyName != '市区'")
	List<Map<String,Object>> getCounty(@Param("cityCode") String cityCode);

	@Select("select DISTINCT PositionName StationName,StationCode from Station where countyId = #{countyId} and status = 1 and  StationTypeId in ('3','4','7')")
	List<Map<String,Object>> getStationCode(@Param("countyId") String countyId);



//	@SelectProvider(type = OrderProvider.class, method = "findRunUnitByCondition")
	@Select("select DISTINCT runCode,runName from RunUnit where runCode is not null and	runName != '' ORDER BY runCode DESC")
	List<Map<String, Object>> getRunUnitMsg();

	@Select("select DISTINCT runCode,runName from RunUnit where stationCode = #{stationCode}")
	Map<String, Object> getRunUnitMsgByStationCode(@Param("stationCode") String stationCode);


	//----------------------多条件查询查询出对应所有站点---------------------------
	@Select({"<script>",
			"select DISTINCT a.CityId,a.Area FROM " +
					"(SELECT DISTINCT s.StationCode,s.StationTypeId,s.CityId,s.Area,r.stationName,r.runName,r.runCode FROM " +
					"(select DISTINCT StationCode,StationTypeId,CityId,Area from station left join city on station.CityId = city.id where city.status = 1) s " +
					"LEFT JOIN RunUnit r ON s.StationCode = r.StationCode) " +
					"a LEFT JOIN StationType st on a.stationTypeId = st.stationTypeId where 1 = 1 ",
			"<when test='stationTypeIds!=null'>" ,
					"   AND st.StationTypeId in " +
							" <foreach collection='stationTypeIds' item='stationTypeId'" +
							"          index='index' open='(' close=')' separator=','>" +
							" #{stationTypeId}" +
							"</foreach>"+
					"    </when>",
			"<when test='runUnitIds!=null'>" ,
			" AND a.runCode in " +
					" <foreach collection='runUnitIds' item='runUnitId'" +
					"          index='index' open='(' close=')' separator=','>" +
					" #{runUnitId}" +
					"</foreach>"+
			" </when>"+
					" order by a.CityId"+
			"</script>"})
	//@SelectProvider(type = OrderProvider.class, method = "findCityByCondition")
//	@Select("Select id as CityId,CityName from city a where a.status = 1")
	List<Map<String, Object>> getCityMsgs(@Param("runUnitIds") String[] runUnitIds, @Param("stationTypeIds") String[] stationTypeIds);
	@Select({"<script>",
			"select *  FROM "
					+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.Status,y.stationName,y.runName,y.runCode  FROM Station x "
					+ "LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) "
					+ "a LEFT JOIN StationType b on a.stationTypeId = b.stationTypeId where 1 = 1 and a.Status = 1 ",
			"<when test='cityId!=null'>" ,
			"   AND a.CityId = #{cityId}" ,
			"</when>",
			"<when test='stationTypeIds!=null'>" ,
			"   AND b.StationTypeId in " +
					" <foreach collection='stationTypeIds' item='stationTypeId'" +
					"          index='index' open='(' close=')' separator=','>" +
					" #{stationTypeId}" +
					"</foreach>"+
			"</when>",
			"<when test='runUnitIds!=null'>" ,
			" AND a.runCode in " +
					" <foreach collection='runUnitIds' item='runUnitId'" +
					"          index='index' open='(' close=')' separator=','>" +
					" #{runUnitId}" +
					"</foreach>"+
			" </when>"+
			" order by stationCode"+
			"</script>"})
	List<Map<String,Object>> getAllStationMassages(@Param("runUnitIds") String[] runUnitIds, @Param("stationTypeIds") String[] stationTypes, @Param("cityId") String cityId);


	@Select("merge into RunUnit as a " +
			"using Station as b on a.stationCode=b.StationCode " +
			"when Matched then update set " +
			"a.stationName=b.PositionName,a.runCode=b.runCode,a.runName=b.RunName,a.city=b.Area," +
			"a.cityid = b.CityId " +
			"when Not Matched then Insert(stationCode,stationName,runCode,runName,city,cityid) " +
			"values(b.StationCode,b.PositionName,b.RunId,b.RunName,b.Area,b.CityId);")
//	@Select("merge into Station as a using [Station_city_2018-2-1] as b on a.stationCode=b.StationCode " +
//			"when Matched then update set a.PositionName=b.PositionName;")
	void updateStationMsg();




}
