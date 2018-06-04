package com.rx.substation.dao;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/26 0026.
 */
public interface CityDAO {

    @Select("select * from City where status = 1")
    List<Map<String,Object>> getAllCityMsg();
}
