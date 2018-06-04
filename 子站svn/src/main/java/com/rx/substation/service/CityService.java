package com.rx.substation.service;

import com.rx.substation.dao.CityDAO;
import com.rx.substation.dao.StationDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CityService {
	
	@Autowired
	CityDAO citydao;

	public List<Map<String,Object>> getAllCityMsg() {
		return citydao.getAllCityMsg();
	}
}
