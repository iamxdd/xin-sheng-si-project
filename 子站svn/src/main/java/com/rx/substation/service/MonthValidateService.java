package com.rx.substation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.rx.substation.util.GetMonthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rx.substation.dao.MonthValidateDAO;
import com.rx.substation.domain.CalValidateScore;
@Service
public class MonthValidateService {
	
	@Autowired
	MonthValidateDAO monthValidateDAO;

	public Map<String, Object> getMonthValidate(String start,String end,String stationCode) {
		
		Map<String, Object> monthValidate = new HashMap<>();
		if(monthValidateDAO.getMonthValidate(start,end,stationCode) != null){
			monthValidate = monthValidateDAO.getMonthValidate(start,end,stationCode);
			String TimePoint = monthValidate.get("TimePoint").toString();
			int Total = Integer.valueOf(monthValidate.get("Total").toString());
		 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
			 try {
				monthValidate.put("datamonth",sdf.parse(TimePoint));
				monthValidate.put("validatedays",Total);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		return monthValidate;
	}
	
	
	public List<Map<String, Object>> findMonthValidateDays(String start, String end) {
		
		return monthValidateDAO.findMonthValidateDays(start,end);
	}


	public void save(CalValidateScore saveMonthValidate) {
		// TODO Auto-generated method stub
		monthValidateDAO.save(saveMonthValidate);
	}

	/**
	 * 多条件查询有效天数
	 * @param runUnitIds
	 * @param cityIds
	 * @param start
	 * @param end
	 * @param stationCodes
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> findValidateByConditions(String[] runUnitIds,String[] cityIds,String start,
		String end, String[] stationCodes,String status,String param,String sortstatus) {
		String runUnitId = null;
		String cityId = null;
		String citys = null;
		String stations = null;
		List<Map<String, Object>> result = new ArrayList<>();
		if(cityIds!=null){
			for (int i = 0; i < cityIds.length; i++) {
				if(i>0){
					citys = citys + "," + cityIds[i];
				}else{
					citys = cityIds[0];
				}
			}
		}
		if(stationCodes!=null){
			for (int i = 0; i < stationCodes.length; i++) {
				if(i>0){
					stations = stations + ",'" + stationCodes[i]+"'";
				}else{
					stations = "'"+ stationCodes[0]+"'";
				}
			}
		}
		//以运行单位来进行展示
		if(status.equals("1")){
			if(runUnitIds!=null){
				for (int i = 0; i < runUnitIds.length; i++){
					runUnitId = runUnitIds[i];
					try {
						List<String> months = GetMonthUtil.getMonthBetween(start,end);
						for (int j = 0; j < months.size(); j++) {
							String month = months.get(j);
							try {
								String monthStart = GetMonthUtil.getFirstDay(month);
								String monthEnd = GetMonthUtil.getLastDay(month);
								List<Map<String, Object>> dataResult =monthValidateDAO.findValidateByConditions(runUnitId,citys,monthStart,monthEnd,stations,param,sortstatus);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String runName = null;
									String runCode = null;
									String monthTime =null;
									int totaltheoryDays = 0;
									int totalpowerCutDays = 0;
									int totalothers = 0;
									int totalshouldDays = 0;
									int totalvalidatyDays = 0;
									Double totalscores = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										if(!map.isEmpty()){
											runName = map.get("runName").toString();
											runCode = map.get("runCode").toString();
											monthTime = map.get("monthTime").toString();
											int theoryDays = Integer.valueOf(map.get("theoryDays").toString());
											int powerCutDays = Integer.valueOf(map.get("powerCutDays").toString());
											int others = Integer.valueOf(map.get("others").toString());
											int shouldDays = Integer.valueOf(map.get("shouldDays").toString());
											int validatyDays = Integer.valueOf(map.get("validatyDays").toString());
											Double scores = Double.valueOf(map.get("scores").toString());
											totaltheoryDays = totaltheoryDays + theoryDays;
											totalpowerCutDays = totalpowerCutDays + powerCutDays;
											totalothers = totalothers + others;
											totalshouldDays = totalshouldDays + shouldDays;
											totalvalidatyDays = totalvalidatyDays + validatyDays;
											totalscores = totalscores + scores;
										}
									}
									dataMap.put("runName",runName);
									dataMap.put("runCode",runCode);
									dataMap.put("monthTime",monthTime.substring(0,7));
									dataMap.put("CtheoryDays",totaltheoryDays/dataResult.size());
									dataMap.put("CpowerCutDays",totalpowerCutDays/dataResult.size());
									dataMap.put("Cothers",totalothers/dataResult.size());
									dataMap.put("CshouldDays",totalshouldDays/dataResult.size());
									dataMap.put("CvalidatyDays",totalvalidatyDays/dataResult.size());
									dataMap.put("Cavgscores",totalscores/dataResult.size());
									dataMap.put("dataResult",dataResult);
									result.add(dataMap);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

			//以城市分类进行展示
		}else if (status.equals("2")){
			if(cityIds!=null){
				for (int i = 0; i < cityIds.length; i++) {
					cityId = cityIds[i];
					try {
						List<String> months = GetMonthUtil.getMonthBetween(start,end);
						for (int j = 0; j < months.size(); j++) {
							String month = months.get(j);
							try {
								String monthStart = GetMonthUtil.getFirstDay(month);
								String monthEnd = GetMonthUtil.getLastDay(month);
								List<Map<String, Object>> dataResult =monthValidateDAO.findValidateByConditions(runUnitId,cityId,monthStart,monthEnd,stations,param,sortstatus);
								if(dataResult.size()>0){
									Map<String, Object> dataMap = new HashMap<>();
									String Area = null;
									String CityId = null;
									String monthTime =null;
									int totaltheoryDays = 0;
									int totalpowerCutDays = 0;
									int totalothers = 0;
									int totalshouldDays = 0;
									int totalvalidatyDays = 0;
									Double totalscores = 0.0;
									for (int k = 0; k < dataResult.size(); k++) {
										Map<String, Object> map = dataResult.get(k);
										Area = map.get("Area").toString();
										CityId = map.get("CityId").toString();
										monthTime = map.get("monthTime").toString();
										int theoryDays = Integer.valueOf(map.get("theoryDays").toString());
										int powerCutDays = Integer.valueOf(map.get("powerCutDays").toString());
										int others = Integer.valueOf(map.get("others").toString());
										int shouldDays = Integer.valueOf(map.get("shouldDays").toString());
										int validatyDays = Integer.valueOf(map.get("validatyDays").toString());
										Double scores = Double.valueOf(map.get("scores").toString());
										totaltheoryDays = totaltheoryDays + theoryDays;
										totalpowerCutDays = totalpowerCutDays + powerCutDays;
										totalothers = totalothers + others;
										totalshouldDays = totalshouldDays + shouldDays;
										totalvalidatyDays = totalvalidatyDays + validatyDays;
										totalscores = totalscores + scores;
									}
									dataMap.put("Area",Area);
									dataMap.put("CityId",CityId);
									dataMap.put("monthTime",monthTime.substring(0,7));
									dataMap.put("CtheoryDays",totaltheoryDays/dataResult.size());
									dataMap.put("CpowerCutDays",totalpowerCutDays/dataResult.size());
									dataMap.put("Cothers",totalothers/dataResult.size());
									dataMap.put("CshouldDays",totalshouldDays/dataResult.size());
									dataMap.put("CvalidatyDays",totalvalidatyDays/dataResult.size());
									dataMap.put("Cavgscores",totalscores/dataResult.size());
									dataMap.put("dataResult",dataResult);
									result.add(dataMap);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
			//以站点进行展示
		}else{
			result = monthValidateDAO.findValidateByConditions(runUnitId,citys,start,end,stations,param,sortstatus);
		}
		return result;
	}

	
	//O3滑动8小时判断
	private int findvalidateo3(String stationCode){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar cal = Calendar.getInstance();
       //获得上个月月初
    	cal.add(Calendar.MONTH, -1);
	    cal.set(Calendar.DATE, 1);
	    String month = sdf.format(cal.getTime());
	    
	    int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    int y = count;
	    for (int i = 1; i <= y; i++) {
		    int x = 0;
		    String datatime1 = null;
		    String datatime2 = null;
		    for(int j = 0 ; j<=16 ;j++){
		    	SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
		    	try {
		    		String a = String.valueOf(j)+":00:00";
					Date timedata = sdf1.parse(a);
					datatime1 = sdf1.format(timedata);
					String b = String.valueOf(j+7)+":00:00";
					Date timedata2 = sdf1.parse(String.valueOf(b));
					datatime2 = sdf1.format(timedata2);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	String day = null;
		    	SimpleDateFormat sdf2 = new SimpleDateFormat("dd");
		    	try {
		    		day = sdf2.format(sdf2.parse(String.valueOf(i)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	Map<String, Object> monthO3Validate = monthValidateDAO.getMonthO3Validate(datatime1,datatime2,month,day,stationCode);
		    	int z = 0;
		    	if(monthO3Validate != null&&monthO3Validate.size()>0 ){
		    		z = (int) monthO3Validate.get("validatedata");
		    	}
			    if( z < 6){
		 		   x++;
			    }
		    }
		    if(x!=0){
		 	   count--;
		    }
	    }
	    return count;
	    
	}

	
	
}
