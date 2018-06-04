package com.rx.substation.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.rx.substation.domain.CalTotalScore;
import com.rx.substation.provider.OrderProvider;


public interface CalTotalScoreDAO {
    @Insert("insert into check_score_data(stationCode,stationName,monthTime,tansferScore,validateDaysScore,completeScore,qcDeductScore,totalScore,totalCost) " +
            "values(#{stationCode},#{stationName},#{monthTime},#{tansferScore},#{validateDaysScore},#{completeScore},#{qcDeductScore},#{totalScore},#{totalCost})")
	void saveCalTotalScore(CalTotalScore calTotalScore);
    
    
    /**
     * 根据运行单位查询分数
     * @return
     */
    @Select("select * from check_score_data ")
    List<Map<String, Object>> getByBrand(String brandId);
    /**
     * 根据城市查询分数
     * @param cityId
     * @return
     */
    @Select("select * from check_score_data")
    List<Map<String, Object>> getByCity(String cityId);
    
    
    /**
     * 根据站点类型查询分数
     * @param stationType
     * @return
     */
    @Select("select * from check_score_data")
    List<Map<String, Object>> getByStationType(String stationType);
    
    
    /**
     * 查询所有站点分数
     * @return
     */
    @Select("select DISTINCT * from check_score_data where monthTime >= #{start} and monthTime <= #{end} order by stationCode")
	List<Map<String, Object>> getCalTotalScore(@Param("start") String start, @Param("end") String end);

	
    @Select("select TotalScore from check_score_data where monthTime >= #{start} and monthTime <= #{end} and StationCode = #{stationCode} ")
    Map<String, Object> getMonthScore(@Param("start") String start, @Param("end") String end, @Param("stationCode") String stationCode);

    
    /**
     * 多条件查询得分
     * @param start
     * @param end
     * @return
     */
//    @Select({"<script>",
//        "SELECT DISTINCT a.* FROM check_score_data",
//        "left JOIN station b on a.stationCode = b.StationCode",
//        "WHERE 1=1",
//        "<when area!=null>",
//        "AND b.area = #{area}",
//        "<when runUnit!=null>",
//        "AND runUnit = #{runUnit}",
//        "<when stationTypeId!=null>",
//        "AND b.StationTypeId = #{stationTypeId}",
//        "<when start!=null>",
//        "AND a.monthTime >= #{start}",
//        "<when end!=null>",
//        "AND a.monthTime <= #{end}",
//        "<when stationCode!=null>",
//        "AND a.StationCode = #{stationCode}"
//        "</when>",
//        "</script>"})
    @SelectProvider(type = OrderProvider.class, method = "findcalScorceByConditions")
	List<Map<String, Object>> findcalScorceByConditions(@Param("runUnitId") String runUnitId, @Param("citys") String citys,
                                                        @Param("start") String start, @Param("end") String end, @Param("stations") String stations, @Param("param") String param, @Param("sortstatus") String sortstatus);


    
    @Update("update check_score_data set TotalCost = #{stationCost} where StationCode = #{stationCode} and monthTime >= #{start} and monthTime <= #{end}")
	void updateCost(@Param("start") String start, @Param("end") String end, @Param("stationCost") Double stationCost, @Param("stationCode") String stationCode);
}
