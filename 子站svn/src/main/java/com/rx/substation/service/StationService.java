package com.rx.substation.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.StationDAO;

@Service
public class StationService {
	
	@Autowired
	StationDAO stationDAO;

	public List<String> getAllStationCode(String start,String end) {
		return stationDAO.getAllStationCode(start,end);
	}
	
	
	public String getStationNameByStationCode(String stationCode){
		return stationDAO.getStationNameByStationCode(stationCode);
		
	}

    public void updateStationMsg() {
		stationDAO.updateStationMsg();
    }


	public List<Map<String, Object>> getAllCity() {

		return stationDAO.getCityMsg();

	}

	public List<Map<String,Object>> getCounty(String cityCode) {
		return stationDAO.getCounty(cityCode);
	}


	public List<Map<String,Object>> getStationCode(String countyId) {
		return stationDAO.getStationCode(countyId);
	}

    public Map<String,Object> getRunMsgByStationCode(String stationCode) {
		return stationDAO.getRunUnitMsgByStationCode(stationCode);
    }
}
