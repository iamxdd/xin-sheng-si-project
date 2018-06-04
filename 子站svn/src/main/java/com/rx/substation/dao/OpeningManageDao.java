package com.rx.substation.dao;

import com.rx.substation.provider.OrderProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by dcx on 2017/8/31 0031.
 */
public interface OpeningManageDao {

    @SelectProvider(type = OrderProvider.class, method = "getOpeningManageData")
    List<Map<String,Object>> getOpeningManageData(@Param("start") String start, @Param("end") String end, @Param("citys") String citys, @Param("stations") String stations, @Param("sortstatus") String sortstatus);
}
