package com.rx.substation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.rx.substation.util.DateCycleUtil;
import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.MonthCompleteStatusDAO;
import com.rx.substation.domain.RunUnitCheck;


@Service
public class MonthCompleteStatusService {
	
	@Autowired
	MonthCompleteStatusDAO monthCompleteStatusDAO;


	/**
	 * 得到站点月完成情况
	 * @param start
	 * @param end
	 * @param stationCode
	 * @return
	 */
	public Map<String, Object> getMonthCompleteStatus(String start, String end,String stationCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date lastMonth = null;
		try {
			lastMonth = sdf.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Map<String,Object> work =  new HashMap<>();
		Calendar c = Calendar.getInstance();
        c.setTime(lastMonth);
        int getyear = c.get(Calendar.YEAR);
        int getmonth = c.get(Calendar.MONTH)+1;

		List<String> list = new ArrayList<>();
//		//查询本月评估之前最后一个时间点
//		String lwd = monthCompleteStatusDAO.getLastWeekDate(start,stationCode);
//		//如果之前存在已完成报表就以此为第一次时间，若未完成则从第一次开始扣分从月初开始算起
//		if(lwd != null){
//			list.add(lwd);
//		}else{
//			list.add(start);
//		}

		List<Map<String,Object>> ws = monthCompleteStatusDAO.getWeekCompleteStatus(start,end,stationCode);

		if(ws != null && ws.size() >= 1){
			for (int i = 0; i < ws.size(); i++) {
				Map<String, Object> map = ws.get(i);
				String time = map.get("createDate").toString();
				list.add(time);
			}
		}
		List<Map<String,Object>> ws1 = DateCycleUtil.dateValidate(list,0);
		work.put("StationCode", stationCode);
		if(ws1 != null && ws1.size() >= 1){
			for (int i = 0; i < 5; i++) {
				work.put("weektime"+i, "1900-01-01");
			}
			for (int i = 0; i < ws1.size(); i++) {
				Map<String, Object> map = ws1.get(ws1.size()-1-i);
				String time = map.get("createDate").toString();
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String lastmonth = sdf.format(lastMonth);
				if(time.indexOf(lastmonth) != -1){
					work.put("weektime"+i, time);
				}else{
					work.put("weektime"+i, "1990-01-01");
				}
			}
		}else{
			for (int i = 0; i < 5; i++) {
				work.put("weektime"+i, "1900-01-01");
			}
		}


		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		sdf1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		//获得上个月月初
		cal.add(Calendar.MONTH, -2);
		cal.set(Calendar.DATE, 1);
		String start1 = sdf1.format(cal.getTime());
		//获得上个月月末
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.MONTH, -2);
		cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
		String end1 = sdf1.format(cal2.getTime());


		Map<String,Object> ms = monthCompleteStatusDAO.getMonthCompleteStatus(start,end,stationCode);
		Map<String,Object> ms1 = monthCompleteStatusDAO.getMonthCompleteStatus(start1,end1,stationCode);

		System.out.println(start1+"  :  "+ end1);
		System.out.println(start+"  :  "+ end);
		if(ms != null && ms1 != null){
			String time = ms.get("createDate").toString();
			String time1 = ms1.get("createDate").toString();

			try {
				time = sdf1.format(sdf1.parse(time));
				time1 = sdf1.format(sdf1.parse(time1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			work.put("monthtime0", time);
			work.put("monthtime1", time1);

		}else if(ms != null && ms1 == null){
			String time = ms.get("createDate").toString();
			try {
				time = sdf1.format(sdf1.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			work.put("monthtime0", time);
			work.put("monthtime1", "1900-01-01");
		}else if(ms == null && ms1 != null){
			String time = ms1.get("createDate").toString();
			try {
				time = sdf1.format(sdf1.parse(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			work.put("monthtime1", time);
			work.put("monthtime0", "1900-01-01");

		}else{
			work.put("monthtime0", "1900-01-01");
			work.put("monthtime1", "1900-01-01");
		}

		if(getmonth == 3 || getmonth == 6|| getmonth == 9|| getmonth == 12  ){
			int year = getyear;
			String start3 = year-1+"-12-01";
			String end3 = year-1+"-12-31";
			if(getmonth == 6){
				start3 = year+"-03-01";
				end3 = year+"-03-31";
			}else if(getmonth == 9){
				start3 = year+"-06-01";
				end3 = year+"-06-30";
			}else if(getmonth == 12){
				start3 = year+"-09-01";
				end3 = year+"-09-30";
			}
			Map<String,Object> qs1 = monthCompleteStatusDAO.getQuarterCompleteStatus(start3,end3,stationCode);
			Map<String,Object> qs = monthCompleteStatusDAO.getQuarterCompleteStatus(start,end,stationCode);
			if(qs != null && qs1 != null ){
					String time = qs.get("createDate").toString();
					String time1 = qs1.get("createDate").toString();
					try {
						time = sdf1.format(sdf1.parse(time));
						time1 = sdf1.format(sdf1.parse(time1));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					work.put("quarter0", time);
					work.put("quarter1", time1);
			}else if(qs != null && qs1 == null){

				String time = qs.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("quarter0", time);
				work.put("quarter1", "1900-01-01");
			}else if(qs == null && qs1 != null){

				String time = qs1.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("quarter1", time);
				work.put("quarter0", "1900-01-01");
			}else{
				work.put("quarter0", "1900-01-01");
				work.put("quarter1", "1900-01-01");
			}
		}else{
			work.put("quarter0", "1900-01-01");
			work.put("quarter1", "1900-01-01");
		}

		if(getmonth == 6 ){
			int year = getyear;
			String start2 = year-1+"-07-01";
			String end2 = year-1+"-12-31";
			Map<String,Object> hys1 = monthCompleteStatusDAO.getHalfYearCompleteStatus(start2,end2,stationCode);
			Map<String,Object> hys = monthCompleteStatusDAO.getHalfYearCompleteStatus(start,end,stationCode);
			System.out.println("-----------------6----半年报-----------------------------");
			if(hys != null && hys1 != null){
				String time = hys.get("createDate").toString();
				String time1 = hys1.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
					time1 = sdf1.format(sdf1.parse(time1));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("halfyear0", time);
				work.put("halfyear1", time1);
			}else if(hys != null && hys1 == null){

				String time = hys.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("halfyear0", time);
				work.put("halfyear1", "1900-01-01");
			}else if(hys == null && hys1 != null){

				String time = hys1.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("halfyear1", time);
				work.put("halfyear0", "1900-01-01");
			}else{
				work.put("halfyear0", "1900-01-01");
				work.put("halfyear1", "1900-01-01");
			}
		}
		if(getmonth == 12 ){
			int year = getyear;
			String start2 = year+"-01-01";
			String end2 = year+"-06-30";
			Map<String,Object> hys = monthCompleteStatusDAO.getHalfYearCompleteStatus(start,end,stationCode);
			Map<String,Object> hys1 = monthCompleteStatusDAO.getHalfYearCompleteStatus(start2,end2,stationCode);
			System.out.println("-----------------12----半年报-----------------------------");
			if(hys != null && hys1 != null){
				String time = hys.get("createDate").toString();
				String time1 = hys1.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
					time1 = sdf1.format(sdf1.parse(time1));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("halfyear0", time);
				work.put("halfyear1", time1);
			}else if(hys != null && hys1 == null){

				String time = hys.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("halfyear0", time);
				work.put("halfyear1", "1900-01-01");
			}else if(hys == null && hys1 != null){

				String time = hys1.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("halfyear1", time);
				work.put("halfyear0", "1900-01-01");
			}else{
				work.put("halfyear0", "1900-01-01");
				work.put("halfyear1", "1900-01-01");
			}
		}else{

			work.put("halfyear0", "1900-01-01");
			work.put("halfyear1", "1900-01-01");
		}
		if(getmonth== 12){
			String start3 = getyear-1+"11-01";
			String end3 = getyear-1+"12-31";
			Map<String,Object> ys = monthCompleteStatusDAO.getYearCompleteStatus(start,end,stationCode);
			Map<String,Object> ys1 = monthCompleteStatusDAO.getYearCompleteStatus(start3,end3,stationCode);
			System.out.println("---------------------年报-----------------------------");
			if(ys != null && ys1 != null){
					String time = ys.get("createDate").toString();
					String time1 = ys1.get("createDate").toString();
					try {
						time = sdf1.format(sdf1.parse(time));
						time1 = sdf1.format(sdf1.parse(time1));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					work.put("year0", time);
					work.put("year1", time1);
			}else if(ys != null && ys1 == null){

				String time = ys.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("year0", time);
				work.put("year1", "1900-01-01");
			}else if(ys == null && ys1 != null){

				String time = ys1.get("createDate").toString();
				try {
					time = sdf1.format(sdf1.parse(time));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				work.put("year1", time);
				work.put("year0", "1900-01-01");
			}else{
				work.put("year0", "1900-01-01");
				work.put("year1", "1900-01-01");
			}
		}else{
			work.put("year0", "1900-01-01");
			work.put("year1", "1900-01-01");
		}

		return work;
	}

	public List<RunUnitCheck> findMonthCompleteStatus(String start, String end) {
		return monthCompleteStatusDAO.findMonthCompleteStatus(start,end);
	}

	public Map<String, Object> findCompleteStatusByConditions(String runUnitId, String cityId, String stationTypeId,
			String start, String end, String stationCode) {
		
		return monthCompleteStatusDAO.findCompleteStatusByConditions(runUnitId,cityId,stationTypeId,start,end,stationCode);
	}

	public List<Map<String,Object>> getTableCompleteStatus (String start, String end, Integer tableid,String[] stationCodes) throws Exception {
		String stations = "";
		List<Map<String,Object>> stationData = new ArrayList<>();
		if(stationCodes!=null){
			for (int i = 0; i < stationCodes.length; i++) {
				if(i>0){
					stations = stations + ",'" + stationCodes[i]+"'";
				}else{
					stations = "'"+ stationCodes[0]+"'";
				}
			}
		}

		for (int i = 0; i < stationCodes.length; i++) {
			String station = stationCodes[i];
			//周报，分月来统计
			List<String> months = GetMonthUtil.getMonthBetween(start,end);
			if (tableid == 0){
				for (int j = 0; j < months.size(); j++) {
					Map<String,Object> stationMsg = monthCompleteStatusDAO.getStationMsg(station);
					Map<String,Object> monthData = new HashMap<>();
					String month = months.get(j);
					String monthStart = GetMonthUtil.getFirstDay(month);
					String monthEnd = GetMonthUtil.getLastDay(month);
					List<Map<String,Object>> stationcomplete = new ArrayList<>();
					stationcomplete = monthCompleteStatusDAO.getTableCompleteStatus(monthStart,monthEnd,tableid,station);
//					String lastWeedDate = monthCompleteStatusDAO.getLastWeekDate(monthStart,station);

					int overTime = 0;
					String nexttime = "";
					if (stationcomplete != null) {
						List<String> stationTime = new ArrayList<>();
//						if(lastWeedDate != null){
//
//							stationTime.add(lastWeedDate);
//						}else {
//							stationTime.add("2018-02-01 00:00:01");
//						}
						for (int k = 0; k < stationcomplete.size(); k++) {
							String time =stationcomplete.get(k).get("createDate").toString();
							stationTime.add(time);
						}
						List<Map<String,Object>> stationValidate = DateCycleUtil.dateValidate(stationTime,0);
						String time1 = "";
						String time2 = "";
						String time3 = "";
						String time4 = "";
						String time5 = "";
						String time6 = "";
						String time7 = "";
						List<Map<String,Object>> data = new ArrayList<>();
						if(stationValidate != null){
							for (int k = 0; k < stationValidate.size(); k++) {
								Map<String, Object> map = stationValidate.get(k);
								Map<String, Object> lastmap = stationValidate.get(stationValidate.size()-1);
								if (k == 0){
									time1 = map.get("createDate").toString();
								}else if(k == 1){
									time2 = map.get("createDate").toString();
									int cycle = DateCycleUtil.getDaysBetween2(time1,time2);
									if(cycle > 7){
										overTime = overTime + 1;
									}
								}else if(k == 2){
									time3 = map.get("createDate").toString();
									int cycle = DateCycleUtil.getDaysBetween2(time2,time3);
									if(cycle > 7){
										overTime = overTime + 1;
									}
								}else if(k == 3){
									time4 = map.get("createDate").toString();
									int cycle = DateCycleUtil.getDaysBetween2(time3,time4);
									if(cycle > 7){
										overTime = overTime + 1;
									}
								}else if(k == 4){
									time5 = map.get("createDate").toString();
									int cycle = DateCycleUtil.getDaysBetween2(time4,time5);
									if(cycle > 7){
										overTime = overTime + 1;
									}
								}else if(k == 5){
									time6 = map.get("createDate").toString();
									int cycle = DateCycleUtil.getDaysBetween2(time5,time6);
									if(cycle > 7){
										overTime = overTime + 1;
									}
								}else if(k == 6){
									time7 = map.get("createDate").toString();
									int cycle = DateCycleUtil.getDaysBetween2(time6,time7);
									if(cycle > 7){
										overTime = overTime + 1;
									}
								}
								String endtime = lastmap.get("createDate").toString();
								String lasttime1 = DateCycleUtil.dateAddNumDay(endtime,5);
								String lasttime2 = DateCycleUtil.dateAddNumDay(endtime,7);
								nexttime = lasttime1+"至"+lasttime2;
							}
							for (int k = 0; k < 7; k++) {
								Map<String,Object> datamap = new HashMap<>();
								if(stationValidate.size() > k){
									datamap =  stationValidate.get(k);
								}else{
									datamap.put("createDate","");
								}
								data.add(datamap);
							}
							monthData.put("stationcomplete",data);
						}else {
							monthData.put("stationcomplete",data);
						}
						monthData.put("month",month);
						monthData.put("nexttime",nexttime);
						monthData.put("overTime",overTime);
					}else{
						monthData.put("month",month);
						monthData.put("nexttime",nexttime);
						monthData.put("overTime",overTime);
					}
					stationMsg.put("monthData",monthData);
					stationData.add(stationMsg);
				}

				//月
			}else if (tableid == 1){
				List<Map<String,Object>> stationcomplete = monthCompleteStatusDAO.getTableCompleteStatus(start,end,tableid,station);
				Map<String,Object> stationMsg = monthCompleteStatusDAO.getStationMsg(station);
				Map<String,Object> monthData = new HashMap<>();
				List<String> stationTime = new ArrayList<>();
				for (int k = 0; k < stationcomplete.size(); k++) {
					String time =stationcomplete.get(k).get("createDate").toString();
					stationTime.add(time);
				}
				List<Map<String,Object>> stationValidate = DateCycleUtil.dateValidate(stationTime,1);
				int time = DateCycleUtil.getMonthsBetween(start,end,1);
				int overTime =0;
				String nexttime ="";
				List<Map<String,Object>> data = new ArrayList<>();
				if(stationValidate != null){
					String endtime = stationValidate.get(stationValidate.size()-1).get("createDate").toString();
					String lasttime1 = DateCycleUtil.dateAddNumDay(endtime,25);
					String lasttime2 = DateCycleUtil.dateAddNumDay(endtime,31);
					nexttime = lasttime1+"至"+lasttime2;
					overTime = time-stationValidate.size();
					for (int k = 0; k < 12; k++) {
						Map<String,Object> datamap = new HashMap<>();
						if(stationValidate.size()-1 >= k){
							datamap =  stationValidate.get(k);
						}else{
							datamap.put("createDate","");
						}
						data.add(datamap);
					}
					monthData.put("stationcomplete",data);
				}else {
					overTime = time;
					monthData.put("stationcomplete",data);
				}
				monthData.put("nexttime",nexttime);
				monthData.put("overTime",overTime);
				stationMsg.put("monthData",monthData);
				stationData.add(stationMsg);
			}else if (tableid == 2){
				List<Map<String,Object>> stationcomplete = monthCompleteStatusDAO.getTableCompleteStatus(start,end,tableid,station);
				Map<String,Object> stationMsg = monthCompleteStatusDAO.getStationMsg(station);
				Map<String,Object> monthData = new HashMap<>();
				List<String> stationTime = new ArrayList<>();
				for (int k = 0; k < stationcomplete.size(); k++) {
					String time =stationcomplete.get(k).get("createDate").toString();
					stationTime.add(time);
				}
				List<Map<String,Object>> stationValidate = DateCycleUtil.dateValidate(stationTime,2);
				int time = DateCycleUtil.getMonthsBetween(start,end,2);
				int overTime =0;
				String nexttime ="";
				List<Map<String,Object>> data = new ArrayList<>();

				if(stationValidate != null){
					String endtime = stationValidate.get(stationValidate.size()-1).get("createDate").toString();
					String lasttime1 = DateCycleUtil.dateAddNumDay(endtime,75);
					String lasttime2 = DateCycleUtil.dateAddNumDay(endtime,90);
					nexttime = lasttime1+"至"+lasttime2;
					overTime = time-stationValidate.size();

					for (int k = 0; k < 4; k++) {
						Map<String,Object> datamap = new HashMap<>();
						if(stationValidate.size()-1 >= k){
							datamap =  stationValidate.get(k);
						}else{
							datamap.put("createDate","");
						}
						data.add(datamap);
					}
					monthData.put("stationcomplete",data);
				}else {
					overTime = time;
					monthData.put("stationcomplete",data);
				}
				monthData.put("nexttime",nexttime);
				monthData.put("overTime",overTime);
				stationMsg.put("monthData",monthData);
				stationData.add(stationMsg);
			}else if (tableid == 3){
				List<Map<String,Object>> stationcomplete = monthCompleteStatusDAO.getTableCompleteStatus(start,end,tableid,station);
				Map<String,Object> stationMsg = monthCompleteStatusDAO.getStationMsg(station);
				Map<String,Object> monthData = new HashMap<>();
				List<String> stationTime = new ArrayList<>();
				for (int k = 0; k < stationcomplete.size(); k++) {
					String time =stationcomplete.get(k).get("createDate").toString();
					stationTime.add(time);
				}
				List<Map<String,Object>> stationValidate = DateCycleUtil.dateValidate(stationTime,3);
				int time = DateCycleUtil.getMonthsBetween(start,end,3);
				int overTime =0;
				String nexttime = "";
				List<Map<String,Object>> data = new ArrayList<>();

				if(stationValidate != null){
					String endtime = stationValidate.get(stationValidate.size()-1).get("createDate").toString();
					String lasttime1 = DateCycleUtil.dateAddNumDay(endtime,150);
					String lasttime2 = DateCycleUtil.dateAddNumDay(endtime,185);
					nexttime = lasttime1+"至"+lasttime2;
					overTime = time-stationValidate.size();
					for (int k = 0; k < 2; k++) {
						Map<String,Object> datamap = new HashMap<>();

						if(stationValidate.size()-1 >= k){
							datamap =  stationValidate.get(k);
						}else{
							datamap.put("createDate","");
						}
						data.add(datamap);
					}
					monthData.put("stationcomplete",data);
				}else {
					overTime = time;
					monthData.put("stationcomplete",data);
				}
				monthData.put("nexttime",nexttime);
				monthData.put("overTime",overTime);
				stationMsg.put("monthData",monthData);
				stationData.add(stationMsg);
			}else if (tableid == 4){
				List<Map<String,Object>> stationcomplete = monthCompleteStatusDAO.getTableCompleteStatus(start,end,tableid,station);
				Map<String,Object> stationMsg = monthCompleteStatusDAO.getStationMsg(station);
				Map<String,Object> monthData = new HashMap<>();
				List<String> stationTime = new ArrayList<>();
				for (int k = 0; k < stationcomplete.size(); k++) {
					String time =stationcomplete.get(k).get("createDate").toString();
					stationTime.add(time);
				}
				List<Map<String,Object>> stationValidate = DateCycleUtil.dateValidate(stationTime,4);
				int time = DateCycleUtil.getMonthsBetween(start,end,4);
				int overTime =0;
				String nexttime = "";
				List<Map<String,Object>> data = new ArrayList<>();
				if(stationValidate != null){
					String endtime = stationValidate.get(stationValidate.size()-1).get("createDate").toString();
					String lasttime1 = DateCycleUtil.dateAddNumDay(endtime,150);
					String lasttime2 = DateCycleUtil.dateAddNumDay(endtime,185);
					nexttime = lasttime1+"至"+lasttime2;
					overTime = time-stationValidate.size();
					monthData.put("stationcomplete",stationValidate);
				}else {
					overTime = time;
					monthData.put("stationcomplete",data);
				}
				monthData.put("nexttime",nexttime);
				monthData.put("overTime",overTime);
				stationMsg.put("monthData",monthData);
				stationData.add(stationMsg);
			}

		}
		return stationData;
	}
}
