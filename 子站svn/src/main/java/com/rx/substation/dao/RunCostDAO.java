package com.rx.substation.dao;

import com.rx.substation.provider.OrderProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 17-5-17.
 */
public interface RunCostDAO {

    @SelectProvider(type = OrderProvider.class, method = "findCompleteStatus")
    List<Map<String,Object>> findRunitQualityByConditions(@Param("start") String start, @Param("end") String end);

    @SelectProvider(type = OrderProvider.class, method = "findRunitQualityByConditions")
    List<Map<String,Object>> findStationRunningByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                            @Param("start") String start, @Param("end") String end, @Param("stations") String stations);


    @Select("SELECT StationCode,Area,stationName,runName,CountyName FROM(" +
            "            (SELECT DISTINCT x.stationTypeId,x.CityId,x.StationCode,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode )" +
            "            m LEFT JOIN County n ON m.CountyId = n.Id ) where stationTypeId in ('3','4','7') ORDER BY Area ")
    List<Map<String,Object>> getAllStationMsg();

//    @Select("SELECT DISTINCT x.StationCode,x.Area,x.CountyId,y.stationName,y.runName,y.runCode,z.CountyName,n.Cost,n.TimePoint FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode  and x.stationTypeId in ('3','4','7') LEFT JOIN County z ON  x.CountyId = z.Id " +
//            "RIGHT JOIN StationCostConfig n ON x.StationCode = n.StationCode where n.stationcode = #{stationCode} and n.timePoint = #{timePoint}  order by n.TimePoint,area")
    @SelectProvider(type = OrderProvider.class, method = "getStationCostMsg")
    List<Map<String,Object>> getStationCostMsg(@Param("stationCode") String stationCode, @Param("timePoint") String timePoint);

    @Insert("Insert into StationCostConfig (StationCode,Cost,TimePoint) values (#{StationCode},#{Cost},#{TimePoint}) ")
    void saveRunCost(Map<String, Object> map);

    @Update("Update StationCostConfig set Cost = #{Cost} where StationCode = #{StationCode} and TimePoint = #{TimePoint}")
    void updateRunCost(Map<String, Object> map);

    @Select("SELECT TotalCost FROM check_score_data where monthtime = #{checkMonth} and StationCode = #{stationCode}")
    String findcost(@Param("checkMonth") String checkMonth, @Param("stationCode") String stationCode);
}
