package com.rx.substation.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.rx.substation.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rx.substation.domain.CalTotalScore;
import com.rx.substation.domain.CalValidateScore;
import com.rx.substation.domain.Transfer;
import com.rx.substation.util.DateCycleUtil;
import com.rx.substation.util.MathUtil;

/**
 * 定时计算评分任务
 * @author dcx
 *
 */

@RestController
@Component
@RequestMapping(value = "/scair/comment")
public class CommentController {
	
	@Autowired
	MonthTransferService monthTransferService;
	
	@Autowired
	MonthValidateService monthValidateService;
	
	@Autowired
	MonthCompleteStatusService monthCompleteStatusService;
	
	@Autowired
	MonthQCInspectionService monthQCInspectionService;
	
	@Autowired
	StationService stationService;

	@Autowired
	MonthRunUnitRunningService monthRunUnitRunningService;

	@Autowired
	MonthRunUnitQCService monthRunUnitQCService;

	@Autowired
	CalScorceService calScorceService;

	@Autowired
	DataVerifyLogService dataVerifyLogService;

	@Autowired
	RunCostService	runCostService;
	
	String fileToBeRead = "src/main/resources/Province.xlsx"; // excel位置


	/**
	 * 更新运行单位归属
	 */
	public void updateMsg(){
		System.out.println("更新开始！");
		stationService.updateStationMsg();
		System.out.println("更新结束！");
	}



	//每月15号凌晨15分开始执行
	@Scheduled(cron="0 6 0 6 * ?")
	@RequestMapping(value="/commentscore")
	//事务管理
	@Transactional(value="txManager")
	public void run() throws Exception{
		//将运行单位分类更新（RunUnit）
//		updateMsg();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		System.out.println("计算任务开始"+df.format(new Date()));

		Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    	//获得上个月月初
    	cal.add(Calendar.MONTH, -1);
	    cal.set(Calendar.DATE, 1);
	    String start = sdf.format(cal.getTime());
	    System.out.println(start);
	    //获得上个月月末
	    Calendar cal2 = Calendar.getInstance();
	    cal2.add(Calendar.MONTH, -1);
	    cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
	    String end = sdf.format(cal2.getTime());
	    System.out.println(end);
        //-------------------------------------------进行运算----------------------------------

    	//本月应查询上个月的有效数据表
	    List<String> stationList = stationService.getAllStationCode(start, end);

    	//遍历扣分表
    	List<Map<String, Object>> monthQCInspection = monthQCInspectionService.getMonthQCInspection(start,end);
        for (int i = 0; i < stationList.size(); i++) {
//    	for (int i = 0; i < 5; i++) {
			String stationCode = stationList.get(i);
			System.out.println(stationCode);
			//得到所有站点的传输率
			Map<String, Object> mt = monthTransferService.getMonthTransfer(start, end, stationCode);
			Map<String, Object> mv = monthValidateService.getMonthValidate(start, end, stationCode);


			//传输率
			String stationname = mt.get("StationName").toString();
			Date monthTime = (Date) mt.get("TimePoint");
			Double so2 = Double.valueOf(mt.get("SO2").toString())/100;
			Double no2 = Double.valueOf(mt.get("NO2").toString())/100;
			Double o3 = Double.valueOf(mt.get("O3").toString())/100;
			Double co = Double.valueOf(mt.get("CO").toString())/100;
			Double pm10 = Double.valueOf(mt.get("PM10").toString())/100;
			Double pm2_5 = Double.valueOf(mt.get("PM2_5").toString())/100;
			Double avgTransfer = (so2 + no2 + o3 + co + pm10 + pm2_5) / 6;
			boolean status = avgTransfer > 0.85 ? true : false;
			Double scores = avgTransfer > 0.85 ? avgTransfer * 35 : 0.0;

			Transfer transfer = new Transfer();
			transfer.setStationName(stationname);
			transfer.setStationCode(stationCode);
			transfer.setMonthTime(monthTime);
			transfer.setSo2(so2);
			transfer.setNo2(no2);
			transfer.setO3(o3);
			transfer.setCo(co);
			transfer.setPm10(pm10);
			transfer.setPm2_5(pm2_5);
			transfer.setAvgTransfer(avgTransfer);
			transfer.setStatus(status);
			transfer.setScores(scores);
			monthTransferService.save(transfer);

			//理论监测天数
			int theoryDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			//通过审核日历得到当月审核后的断电天数与不可抗因素天数
			int powerCutDays = dataVerifyLogService.getPowerCutDays(start, end, stationCode);
			int others = dataVerifyLogService.getOtherDays(start, end, stationCode);
			//应测天数
			int shouldDays = theoryDays - powerCutDays - others;
			//有效天数
			int validatyDays = Integer.valueOf(mv.get("validatedays").toString());
			//分数
			Double validatyscores = 0.0;
			if (cal.getTime().getMonth() == 1) {
				validatyscores = validatyDays >= 25 ? 35 - (shouldDays - validatyDays) : 0.0;
			} else {
				validatyscores = validatyDays >= 27 ? 35 - (shouldDays - validatyDays) : 0.0;
			}
			CalValidateScore calValidateScore = new CalValidateScore();
			calValidateScore.setStationName(stationname);
			calValidateScore.setStationCode(stationCode);
			calValidateScore.setMonthTime(monthTime);
			calValidateScore.setTheoryDays(theoryDays);
			calValidateScore.setPowerCutDays(powerCutDays);
			calValidateScore.setOthers(others);
			calValidateScore.setShouldDays(shouldDays);
			calValidateScore.setValidatyDays(validatyDays);
			calValidateScore.setScores(validatyscores);
			monthValidateService.save(calValidateScore);

			//站点运维报表完成情况
			Map<String, Object> mc = monthCompleteStatusService.getMonthCompleteStatus(start+" 00:00:00", end+" 59:59:59", stationCode);
			String runUnitId = monthRunUnitRunningService.getOperationCompany(stationCode);
			String cityId = monthRunUnitRunningService.getCityId(stationCode);
			List<Map<String, Object>> stationWork = null;
			Double deduct = 0.0;
			try {
				stationWork = getStationWork(mc);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int j = 0; j < stationWork.size(); j++) {
				Map<String, Object> map = stationWork.get(j);
				map.put("id",stationCode+monthTime);
				monthRunUnitQCService.saveStationWork(map);
				Double performance = Double.valueOf(map.get("performance").toString());
				deduct = deduct + performance;
			}


			Map<String,Object> runUnitWork =  new HashMap<>();
			runUnitWork.put("stationCode",stationCode);
			runUnitWork.put("stationName",stationname);
			runUnitWork.put("runUnitId",runUnitId);
			runUnitWork.put("checkTime", monthTime);
			runUnitWork.put("cityId", cityId);
			runUnitWork.put("manager","");
			runUnitWork.put("deduct", 30 - deduct);
			monthRunUnitQCService.saveRunUnitWork(runUnitWork);


			//计算总分
			CalTotalScore calTotalScore = new CalTotalScore();

			calTotalScore.setStationCode(stationCode);
			calTotalScore.setStationName(stationname);
			calTotalScore.setMonthTime(monthTime);
			calTotalScore.setTansferScore(scores);
			calTotalScore.setValidateDaysScore(validatyscores);

			calTotalScore.setCompleteScore(30 - deduct);
			Double totalScore = 100.0;

			//判断该站点是否存在检查扣分
			Double totalDeduct = 0.0;
			if (monthQCInspection != null) {
				for (int j = 0; j < monthQCInspection.size(); j++) {
					Map<String, Object> mq = monthQCInspection.get(j);
					if (calTotalScore.getStationCode().equals(mq.get("stationCode"))) {
						//扣分计算
						totalDeduct = totalDeduct + Double.valueOf(mq.get("deduct").toString());
					}
				}
			}
			calTotalScore.setQcDeductScore(totalDeduct*0.6);
			totalScore = (30 - deduct) + validatyscores + scores - totalDeduct*0.6;
			calTotalScore.setTotalScore(totalScore);
			//计算上月总钱开销（运行费）
			List<Map<String, Object>> costConfigList = new ArrayList<>();
			costConfigList = runCostService.getStationCostMsg(start,stationCode);
			//默认本月运行费和上月一致,每月15日后自定义
			String cost = "";
			if(costConfigList!=null){
				cost = costConfigList.get(0).get("Cost").toString();
			}else {
				//throw new RuntimeException("上月预算未配置");
				cost = "0";
			}
			Double costConfig = 0.0;
			Double stationCost = 0.0;
			costConfig = Double.valueOf(cost);
			if(totalScore >= 80.0 && totalScore < 95.0){
				stationCost = totalScore/100 * costConfig;
			}else if(totalScore >=  95.0) {
				stationCost = costConfig;
			}else{
				stationCost = 0.0;
			}
			calTotalScore.setTotalCost(stationCost);

			calScorceService.saveCalTotalScore(calTotalScore);

			System.out.println(calTotalScore);
		}

	}

	
	private void inputRunDayWorkExcel(String sheetName,String stationCode) {
		 FileInputStream fis = null;
		 OutputStream out = null;
	        try {
	        	//写入站点运行质量信息
	        	 fis = new FileInputStream(fileToBeRead);
	        	 XSSFWorkbook workbook = new XSSFWorkbook(fis);
	             XSSFSheet sheet = workbook.getSheet(sheetName);
	             
	             //写入该站点对应运行单位id
	             String operationCompany = monthRunUnitRunningService.getOperationCompany(stationCode);
	             sheet.getRow(1).createCell(5).setCellValue(operationCompany);
	             //写入该站点对应运行单位的质量评估信息
	             Calendar cal = Calendar.getInstance();
	             SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	             sdf1.setTimeZone(TimeZone.getTimeZone("GMT+8"));
	         	 //获得上个月月初
	         	 cal.add(Calendar.MONTH, -1);
	     	     cal.set(Calendar.DATE, 1);
	     	     String start = sdf1.format(cal.getTime());
	     	     //获得上个月月末
	     	     Calendar cal2 = Calendar.getInstance();
	     	     cal2.add(Calendar.MONTH, -1);
	     	     cal2.set(Calendar.DATE,cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
	     	     String end = sdf1.format(cal2.getTime());
	     	     //获得上上个月月初
	     	     Calendar cal3 = Calendar.getInstance();
	     	     cal3.add(Calendar.MONTH, -2);
	     	     cal3.set(Calendar.DATE, 1);
	     	     String start1 = sdf1.format(cal3.getTime());
	     	     //获得上上个月月末
	     	     Calendar cal4 = Calendar.getInstance();
	     	     cal4.add(Calendar.MONTH, -2);
	     	     cal4.set(Calendar.DATE,cal4.getActualMaximum(Calendar.DAY_OF_MONTH));
	     	     String end1 = sdf1.format(cal4.getTime());
	     	     //获得前三个月月初
	     	     Calendar cal5 = Calendar.getInstance();
	     	     cal5.add(Calendar.MONTH, -3);
	     	     cal5.set(Calendar.DATE, 1);
	     	     String start2 = sdf1.format(cal5.getTime());
	     	     //获得前三个月月末
	     	     Calendar cal6 = Calendar.getInstance();
	     	     cal6.add(Calendar.MONTH, -3);
	     	     cal6.set(Calendar.DATE,cal6.getActualMaximum(Calendar.DAY_OF_MONTH));
	     	     String end2 = sdf1.format(cal6.getTime());
	     	     //获得前四个月月初
	     	     Calendar cal7 = Calendar.getInstance();
	     	     cal7.add(Calendar.MONTH, -4);
	     	     cal7.set(Calendar.DATE, 1);
	     	     String start3 = sdf1.format(cal7.getTime());
	     	     //获得前四个月月末
	     	     Calendar cal8 = Calendar.getInstance();
	     	     cal8.add(Calendar.MONTH, -4);
	     	     cal8.set(Calendar.DATE,cal8.getActualMaximum(Calendar.DAY_OF_MONTH));
	     	     String end3 = sdf1.format(cal8.getTime());
	     	     //有效天数判断（日累计27（25），6参是否存在超过8天无效数据）
	             int j = 0;
	             List<String> stationcodelist = monthRunUnitRunningService.getOperationCompanyStations(stationCode);
	             for (int i = 0; i < stationcodelist.size(); i++) {
	            	 String stationCode1 = stationcodelist.get(i);
//	            	 Double coefficientByStationCode = monthStationRunningDAO.getCoefficientByStationCode(start,end,stationCode);
//	            	 if(coefficientByStationCode !=null && coefficientByStationCode == 0.0){
//	            		 j++;
//	            	 }
					 Map<String,Object> monthValidate = monthValidateService.getMonthValidate(start,end,stationCode1);
					 if(!monthValidate.isEmpty()){
						    String TimePoint = monthValidate.get("TimePoint").toString();
		     	    		int SO2 = Integer.valueOf(monthValidate.get("SO2").toString());
		     	    		int NO2 = Integer.valueOf(monthValidate.get("NO2").toString());
		     	    		int CO = Integer.valueOf(monthValidate.get("CO").toString());
		     	    		int PM10 = Integer.valueOf(monthValidate.get("PM10").toString());
		     	    		int PM2_5 = Integer.valueOf(monthValidate.get("PM2_5").toString());
		     	    		int O3 = Integer.valueOf(monthValidate.get("O3").toString());
		     	    		int Total = Integer.valueOf(monthValidate.get("Total").toString());
		     	    		
		     	    		
		     	    		cal.add(Calendar.MONTH, -1);
		     	    		int upDayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
		     	    		try {
		     	    			if(sdf1.parse(TimePoint).getMonth()==2){
		     	    				if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 25)){
		     	    					j++;
		     	    				} 
		     	    			}else{
		     	    				if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)){
		     	    					j++;
		     	    				}
		     	    			}
		     	    		} catch (ParseException e) {
		     	    			// TODO Auto-generated catch block
		     	    			e.printStackTrace();
		     	    		}
		     	    		
		     	    	}else {
	            	 	continue;
					 }
	            	 
				 }
	             //当月20%站点未达到数据有效性要求
	             if(stationcodelist.size() > 0 && j/stationcodelist.size() > 0.2){
	            	 sheet.getRow(9).createCell(2).setCellValue(1);
	             }else{
	            	 sheet.getRow(9).createCell(2).setCellValue(0);
	             }
	             //当月40%站点未达到数据有效性要求
	             if(stationcodelist.size() > 0 && j/stationcodelist.size() > 0.4){
	            	 sheet.getRow(11).createCell(2).setCellValue(1);
	             }else{
	            	 sheet.getRow(11).createCell(2).setCellValue(0);
	             }
	             
	             int j2 = 0;
	             for (int i = 0; i < stationcodelist.size(); i++) {
	            	 String stationCode1 = stationcodelist.get(i);
//	            	 Double coefficientByStationCode = monthStationRunningDAO.getCoefficientByStationCode(start,end,stationCode);
//	            	 if(coefficientByStationCode !=null && coefficientByStationCode == 0.0){
//	            		 j2++;
//	            	 }
					 Map<String,Object> monthValidate = monthValidateService.getMonthValidate(start,end,stationCode1);
					 if(!monthValidate.isEmpty()){
		     	    		String TimePoint = monthValidate.get("TimePoint").toString();
		     	    		int SO2 = Integer.valueOf(monthValidate.get("SO2").toString());
		     	    		int NO2 = Integer.valueOf(monthValidate.get("NO2").toString());
		     	    		int CO = Integer.valueOf(monthValidate.get("CO").toString());
		     	    		int PM10 = Integer.valueOf(monthValidate.get("PM10").toString());
		     	    		int PM2_5 = Integer.valueOf(monthValidate.get("PM2_5").toString());
		     	    		int O3 = Integer.valueOf(monthValidate.get("O3").toString());
		     	    		int Total = Integer.valueOf(monthValidate.get("Total").toString());
		     	    		
		     	    		
		     	    		cal.add(Calendar.MONTH, -1);
		     	    		int upDayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
		     	    		try {
		     	    			if(sdf1.parse(TimePoint).getMonth()==2){
		     	    				if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 25)){
		     	    					j2++;
		     	    				} 
		     	    			}else{
		     	    				if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)){
		     	    					j2++;
		     	    				}
		     	    			}
		     	    		} catch (ParseException e) {
		     	    			// TODO Auto-generated catch block
		     	    			e.printStackTrace();
		     	    		}
		     	    		
		     	    	}else {
	            	 	continue;
					 }
	            	 
				 }
	             int j3 = 0;
	             for (int i = 0; i < stationcodelist.size(); i++) {
	            	 String stationCode1 = stationcodelist.get(i);
//	            	 Double coefficientByStationCode = monthStationRunningDAO.getCoefficientByStationCode(start1,end1,stationCode);
//	            	 if(coefficientByStationCode !=null && coefficientByStationCode == 0.0){
//	            		 j3++;
//	            	 }
					 Map<String,Object> monthValidate = monthValidateService.getMonthValidate(start1,end1,stationCode1);
					 if(!monthValidate.isEmpty()
							 && !monthRunUnitRunningService.findStationRunningDAO(start1,end1,stationCode1).isEmpty()){
		     	    		String TimePoint = monthValidate.get("TimePoint").toString();
		     	    		int SO2 = Integer.valueOf(monthValidate.get("SO2").toString());
		     	    		int NO2 = Integer.valueOf(monthValidate.get("NO2").toString());
		     	    		int CO = Integer.valueOf(monthValidate.get("CO").toString());
		     	    		int PM10 = Integer.valueOf(monthValidate.get("PM10").toString());
		     	    		int PM2_5 = Integer.valueOf(monthValidate.get("PM2_5").toString());
		     	    		int O3 = Integer.valueOf(monthValidate.get("O3").toString());
		     	    		int Total = Integer.valueOf(monthValidate.get("Total").toString());
		     	    		
		     	    		
		     	    		cal.add(Calendar.MONTH, -1);
		     	    		int upDayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
		     	    		try {
		     	    			if(sdf1.parse(TimePoint).getMonth()==2){
		     	    				if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 25)){
		     	    					j3++;
		     	    				} 
		     	    			}else{
		     	    				if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)){
		     	    					j3++;
		     	    				}
		     	    			}
		     	    		} catch (ParseException e) {
		     	    			// TODO Auto-generated catch block
		     	    			e.printStackTrace();
		     	    		}
		     	    	//特殊，如果上个月无数据及算为有效（针对刚开始的评估）
		     	    	}else{
						 	j3++;
					 }
	            	 
				 }
	             //连续两个月20%未达到数据有效要求
	             if(stationcodelist.size() > 0 && j2/stationcodelist.size()>0.2 && j3/stationcodelist.size()>0.2){
	            	 sheet.getRow(10).createCell(2).setCellValue(1);
	             }else{
	            	 sheet.getRow(10).createCell(2).setCellValue(0);
	             }
	             
	             //同一站点连续两个月未达到数据有效性

//	             for (int i = 0; i < stationcodelist.size(); i++) {
//	            	 String stationCode1 = stationcodelist.get(i);
//	            	 Double coefficientByStationCode = monthStationRunningDAO.getCoefficientByStationCode(start,end,stationCode1);
//	            	 Double coefficientByStationCode2 = monthStationRunningDAO.getCoefficientByStationCode(start1,end1,stationCode1);
//	            	 if(coefficientByStationCode !=null && coefficientByStationCode == 0.0
//	            			 && coefficientByStationCode2 !=null && coefficientByStationCode2 == 0.0  ){
//	            		 sheet.getRow(12).createCell(2).setCellValue(1);
//	            		 break;
//		             }else{
//		            	 sheet.getRow(12).createCell(2).setCellValue(0);
//		             }
//				 }

				int j4 = 0;
				for (int i = 0; i < stationcodelist.size(); i++) {
					String stationCode1 = stationcodelist.get(i);
					if(!monthValidateService.getMonthValidate(start1,end1,stationCode1).isEmpty()
							&& !monthRunUnitRunningService.findStationRunningDAO(start1,end1,stationCode1).isEmpty()){
						Map<String,Object> monthValidate = monthValidateService.getMonthValidate(start,end,stationCode1);
						Map<String,Object> monthValidate1 = monthValidateService.getMonthValidate(start1,end1,stationCode1);
						String TimePoint = monthValidate.get("TimePoint").toString();
						int SO2 = Integer.valueOf(monthValidate.get("SO2").toString());
						int NO2 = Integer.valueOf(monthValidate.get("NO2").toString());
						int CO = Integer.valueOf(monthValidate.get("CO").toString());
						int PM10 = Integer.valueOf(monthValidate.get("PM10").toString());
						int PM2_5 = Integer.valueOf(monthValidate.get("PM2_5").toString());
						int O3 = Integer.valueOf(monthValidate.get("O3").toString());
						int Total = Integer.valueOf(monthValidate.get("Total").toString());

						String TimePoint1 = monthValidate1.get("TimePoint").toString();
						int SO21 = Integer.valueOf(monthValidate1.get("SO2").toString());
						int NO21 = Integer.valueOf(monthValidate1.get("NO2").toString());
						int CO1 = Integer.valueOf(monthValidate1.get("CO").toString());
						int PM101 = Integer.valueOf(monthValidate1.get("PM10").toString());
						int PM2_51 = Integer.valueOf(monthValidate1.get("PM2_5").toString());
						int O31 = Integer.valueOf(monthValidate1.get("O3").toString());
						int Total1 = Integer.valueOf(monthValidate1.get("Total").toString());


						cal.add(Calendar.MONTH, -1);
						int upDayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						cal2.add(Calendar.MONTH, -1);
						int upDayNum1 = cal2.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						try {
							if(sdf1.parse(TimePoint).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 25)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)){
									j4++;
									break;
								}
							}else if(sdf1.parse(TimePoint1).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 25)){
									j4++;
									break;
								}
							}else{
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)){
									j4++;
									break;
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//特殊，如果上个月无数据及算为有效（针对刚开始的评估）
					}else{
						j4=0;
						break;
					}
				}
				if(j4 > 0){
					sheet.getRow(12).createCell(2).setCellValue(1);
				}else{
					sheet.getRow(12).createCell(2).setCellValue(0);
				}
	             
	             //同一站点连续三个月未达到数据有效性
//	             for (int i = 0; i < stationcodelist.size(); i++) {

//	            	 String stationCode1 = stationcodelist.get(i);
//	            	 Double coefficientByStationCode = monthStationRunningDAO.getCoefficientByStationCode(start,end,stationCode1);
//	            	 Double coefficientByStationCode2 = monthStationRunningDAO.getCoefficientByStationCode(start1,end1,stationCode1);
//	            	 Double coefficientByStationCode3 = monthStationRunningDAO.getCoefficientByStationCode(start2,end2,stationCode1);
//	            	 if(coefficientByStationCode != null && coefficientByStationCode == 0.0 &&
//	            			 coefficientByStationCode2 != null && coefficientByStationCode2 == 0.0 &&
//	            					 coefficientByStationCode3 != null && coefficientByStationCode3 == 0.0){
//	            		 sheet.getRow(13).createCell(2).setCellValue(1);
//	            		 break;
//		             }else{
//		            	 sheet.getRow(13).createCell(2).setCellValue(0);
//		             }
//				 }
				int j5 = 0;
				for (int i = 0; i < stationcodelist.size(); i++) {
					String stationCode1 = stationcodelist.get(i);
					if(!monthValidateService.getMonthValidate(start1,end1,stationCode1).isEmpty() &&
							!monthValidateService.getMonthValidate(start2,end2,stationCode1).isEmpty()
									&& !monthRunUnitRunningService.findStationRunningDAO(start1,end1,stationCode1).isEmpty()
									&& !monthRunUnitRunningService.findStationRunningDAO(start2,end2,stationCode1).isEmpty()){
						Map<String,Object> monthValidate = monthValidateService.getMonthValidate(start,end,stationCode1);
						Map<String,Object> monthValidate1 = monthValidateService.getMonthValidate(start1,end1,stationCode1);
						Map<String,Object> monthValidate2 = monthValidateService.getMonthValidate(start2,end2,stationCode1);
						String TimePoint = monthValidate.get("TimePoint").toString();
						int SO2 = Integer.valueOf(monthValidate.get("SO2").toString());
						int NO2 = Integer.valueOf(monthValidate.get("NO2").toString());
						int CO = Integer.valueOf(monthValidate.get("CO").toString());
						int PM10 = Integer.valueOf(monthValidate.get("PM10").toString());
						int PM2_5 = Integer.valueOf(monthValidate.get("PM2_5").toString());
						int O3 = Integer.valueOf(monthValidate.get("O3").toString());
						int Total = Integer.valueOf(monthValidate.get("Total").toString());

						String TimePoint1 = monthValidate1.get("TimePoint").toString();
						int SO21 = Integer.valueOf(monthValidate1.get("SO2").toString());
						int NO21 = Integer.valueOf(monthValidate1.get("NO2").toString());
						int CO1 = Integer.valueOf(monthValidate1.get("CO").toString());
						int PM101 = Integer.valueOf(monthValidate1.get("PM10").toString());
						int PM2_51 = Integer.valueOf(monthValidate1.get("PM2_5").toString());
						int O31 = Integer.valueOf(monthValidate1.get("O3").toString());
						int Total1 = Integer.valueOf(monthValidate1.get("Total").toString());

						String TimePoint2 = monthValidate2.get("TimePoint").toString();
						int SO22 = Integer.valueOf(monthValidate2.get("SO2").toString());
						int NO22 = Integer.valueOf(monthValidate2.get("NO2").toString());
						int CO2 = Integer.valueOf(monthValidate2.get("CO").toString());
						int PM102 = Integer.valueOf(monthValidate2.get("PM10").toString());
						int PM2_52 = Integer.valueOf(monthValidate2.get("PM2_5").toString());
						int O32 = Integer.valueOf(monthValidate2.get("O3").toString());
						int Total2 = Integer.valueOf(monthValidate2.get("Total").toString());

						cal.add(Calendar.MONTH, -1);
						int upDayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						cal3.add(Calendar.MONTH, -1);
						int upDayNum1 = cal3.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						cal5.add(Calendar.MONTH, -1);
						int upDayNum2 = cal5.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						try {
							if(sdf1.parse(TimePoint).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 25)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)){
									j5++;
									break;
								}
							}else if(sdf1.parse(TimePoint1).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 25)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)){
									j5++;
								}
							}else if(sdf1.parse(TimePoint2).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 25)){
									j5++;
									break;
								}
							}else{
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)){
									j5++;
									break;
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//特殊，如果上个月无数据及算为有效（针对刚开始的评估）
					}else{
						j5=0;
						break;
					}

				}
				if(j5 > 0){
					sheet.getRow(13).createCell(2).setCellValue(1);
				}else{
					sheet.getRow(13).createCell(2).setCellValue(0);
				}

	             //同一站点连续四个月未达到数据有效性
//	             for (int i = 0; i < stationcodelist.size(); i++) {
//	            	 String stationCode1 = stationcodelist.get(i);
//	            	 Double coefficientByStationCode = monthStationRunningDAO.getCoefficientByStationCode(start,end,stationCode1);
//	            	 Double coefficientByStationCode2 = monthStationRunningDAO.getCoefficientByStationCode(start1,end1,stationCode1);
//	            	 Double coefficientByStationCode3 = monthStationRunningDAO.getCoefficientByStationCode(start2,end2,stationCode1);
//	            	 Double coefficientByStationCode4 = monthStationRunningDAO.getCoefficientByStationCode(start3,end3,stationCode1);
//
//	            	 if(coefficientByStationCode != null && coefficientByStationCode == 0.0 &&
//	            			 coefficientByStationCode2 != null && coefficientByStationCode2 == 0.0 &&
//        					 coefficientByStationCode3 != null && coefficientByStationCode3 == 0.0 &&
//        					 coefficientByStationCode4 != null && coefficientByStationCode4 == 0.0){
//	            		 sheet.getRow(14).createCell(2).setCellValue(1);
//	            		 break;
//		             }else{
//		            	 sheet.getRow(14).createCell(2).setCellValue(0);
//		             }
//				 }
				int j6 = 0;
				for (int i = 0; i < stationcodelist.size(); i++) {
					String stationCode1 = stationcodelist.get(i);
					if(!monthValidateService.getMonthValidate(start1,end1,stationCode1).isEmpty()
							&&!monthValidateService.getMonthValidate(start2,end2,stationCode1).isEmpty()
							&&!monthValidateService.getMonthValidate(start3,end3,stationCode1).isEmpty()
							&& !monthRunUnitRunningService.findStationRunningDAO(start1,end1,stationCode1).isEmpty()
							&& !monthRunUnitRunningService.findStationRunningDAO(start2,end2,stationCode1).isEmpty()
							&& !monthRunUnitRunningService.findStationRunningDAO(start3,end3,stationCode1).isEmpty()){
						Map<String,Object> monthValidate = monthValidateService.getMonthValidate(start,end,stationCode1);
						Map<String,Object> monthValidate1 = monthValidateService.getMonthValidate(start1,end1,stationCode1);
						Map<String,Object> monthValidate2 = monthValidateService.getMonthValidate(start2,end2,stationCode1);
						Map<String,Object> monthValidate3 = monthValidateService.getMonthValidate(start3,end3,stationCode1);
						String TimePoint = monthValidate.get("TimePoint").toString();
						int SO2 = Integer.valueOf(monthValidate.get("SO2").toString());
						int NO2 = Integer.valueOf(monthValidate.get("NO2").toString());
						int CO = Integer.valueOf(monthValidate.get("CO").toString());
						int PM10 = Integer.valueOf(monthValidate.get("PM10").toString());
						int PM2_5 = Integer.valueOf(monthValidate.get("PM2_5").toString());
						int O3 = Integer.valueOf(monthValidate.get("O3").toString());
						int Total = Integer.valueOf(monthValidate.get("Total").toString());

						String TimePoint1 = monthValidate1.get("TimePoint").toString();
						int SO21 = Integer.valueOf(monthValidate1.get("SO2").toString());
						int NO21 = Integer.valueOf(monthValidate1.get("NO2").toString());
						int CO1 = Integer.valueOf(monthValidate1.get("CO").toString());
						int PM101 = Integer.valueOf(monthValidate1.get("PM10").toString());
						int PM2_51 = Integer.valueOf(monthValidate1.get("PM2_5").toString());
						int O31 = Integer.valueOf(monthValidate1.get("O3").toString());
						int Total1 = Integer.valueOf(monthValidate1.get("Total").toString());

						String TimePoint2 = monthValidate2.get("TimePoint").toString();
						int SO22 = Integer.valueOf(monthValidate2.get("SO2").toString());
						int NO22 = Integer.valueOf(monthValidate2.get("NO2").toString());
						int CO2 = Integer.valueOf(monthValidate2.get("CO").toString());
						int PM102 = Integer.valueOf(monthValidate2.get("PM10").toString());
						int PM2_52 = Integer.valueOf(monthValidate2.get("PM2_5").toString());
						int O32 = Integer.valueOf(monthValidate2.get("O3").toString());
						int Total2 = Integer.valueOf(monthValidate2.get("Total").toString());

						String TimePoint3 = monthValidate3.get("TimePoint").toString();
						int SO23 = Integer.valueOf(monthValidate3.get("SO2").toString());
						int NO23 = Integer.valueOf(monthValidate3.get("NO2").toString());
						int CO3 = Integer.valueOf(monthValidate3.get("CO").toString());
						int PM103 = Integer.valueOf(monthValidate3.get("PM10").toString());
						int PM2_53 = Integer.valueOf(monthValidate3.get("PM2_5").toString());
						int O33 = Integer.valueOf(monthValidate3.get("O3").toString());
						int Total3 = Integer.valueOf(monthValidate3.get("Total").toString());

						cal.add(Calendar.MONTH, -1);
						int upDayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						cal3.add(Calendar.MONTH, -1);
						int upDayNum1 = cal3.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						cal5.add(Calendar.MONTH, -1);
						int upDayNum2 = cal5.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						cal7.add(Calendar.MONTH, -1);
						int upDayNum3 = cal7.getActualMaximum(Calendar.DAY_OF_MONTH)-8;
						try {
							if(sdf1.parse(TimePoint).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 25)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)
										&&!(SO23 > upDayNum3 && NO23 > upDayNum3 && CO3 > upDayNum3 && PM103 > upDayNum3 && PM2_53 > upDayNum3 && O33 > upDayNum3 && Total3 > 27)){
									j6++;
									break;
								}
							}else if(sdf1.parse(TimePoint1).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 25)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)
										&&!(SO23 > upDayNum3 && NO23 > upDayNum3 && CO3 > upDayNum3 && PM103 > upDayNum3 && PM2_53 > upDayNum3 && O33 > upDayNum3 && Total3 > 27)){
									j6++;
									break;
								}
							}else if(sdf1.parse(TimePoint2).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 25)
										&&!(SO23 > upDayNum3 && NO23 > upDayNum3 && CO3 > upDayNum3 && PM103 > upDayNum3 && PM2_53 > upDayNum3 && O33 > upDayNum3 && Total3 > 27)){
									j6++;
									break;
								}
							}else if(sdf1.parse(TimePoint3).getMonth()==2){
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)
										&&!(SO23 > upDayNum3 && NO23 > upDayNum3 && CO3 > upDayNum3 && PM103 > upDayNum3 && PM2_53 > upDayNum3 && O33 > upDayNum3 && Total3 > 25)){
									j6++;
									break;
								}
							}else{
								if(!(SO2 > upDayNum && NO2 > upDayNum && CO > upDayNum && PM10 > upDayNum && PM2_5 > upDayNum && O3 > upDayNum && Total > 27)
										&&!(SO21 > upDayNum1 && NO21 > upDayNum1 && CO1 > upDayNum1 && PM101 > upDayNum1 && PM2_51 > upDayNum1 && O31 > upDayNum1 && Total1 > 27)
										&&!(SO22 > upDayNum2 && NO22 > upDayNum2 && CO2 > upDayNum2 && PM102 > upDayNum2 && PM2_52 > upDayNum2 && O32 > upDayNum2 && Total2 > 27)
										&&!(SO23 > upDayNum3 && NO23 > upDayNum3 && CO3 > upDayNum3 && PM103 > upDayNum3 && PM2_53 > upDayNum3 && O33 > upDayNum3 && Total3 > 27)){
									j6++;
									break;
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//特殊，如果上个月无数据及算为有效（针对刚开始的评估）
					}else{
						j6=0;
						break;
					}

				}
				if(j6 > 0){
					sheet.getRow(14).createCell(2).setCellValue(1);
				}else{
					sheet.getRow(14).createCell(2).setCellValue(0);
				}


//	            sheet.getRow(14).createCell(2).setCellValue(false);  
//	            sheet.getRow(13).createCell(2).setCellValue(false);
//	            sheet.getRow(12).createCell(2).setCellValue(false);
//	            sheet.getRow(10).createCell(2).setCellValue(false);
	            
	            out =  new FileOutputStream(fileToBeRead);  
		        workbook.write(out);  
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	        	try {
	        		fis.close();
	        		if(out!=null){
	        			out.flush();
	        			out.close();
	        		}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	



	
	private List<Map<String,Object>> getStationWork(Map<String, Object> mc) throws ParseException {
		
		List<Map<String,Object>> stationWorklist = new ArrayList<>();
		Map<String,Object> stationWork1 =  new HashMap<>();
		Map<String,Object> stationWork2 =  new HashMap<>();
		Map<String,Object> stationWork3 =  new HashMap<>();
		Map<String,Object> stationWork4 =  new HashMap<>();
		Map<String,Object> stationWork5 =  new HashMap<>();
		Map<String,Object> stationWork6 =  new HashMap<>();
		Map<String,Object> stationWork7 =  new HashMap<>();
		Map<String,Object> stationWork8 =  new HashMap<>();


		String weektime0 = mc.get("weektime0").toString();
		String weektime1 = mc.get("weektime1").toString();
		String weektime2 = mc.get("weektime2").toString();
		String weektime3 = mc.get("weektime3").toString();
		String weektime4 = mc.get("weektime4").toString();
		String monthtime0 = mc.get("monthtime0").toString();
		String monthtime1 = mc.get("monthtime1").toString();
		String quarter0 = mc.get("quarter0").toString();
		String quarter1 = mc.get("quarter1").toString();
		String halfyear0 = mc.get("halfyear0").toString();
		String halfyear1 = mc.get("halfyear1").toString();
		String year0 = mc.get("year0").toString();
		String year1 = mc.get("year1").toString();
		String StationCode = mc.get("StationCode").toString();


     		
		 stationWork1.put("lastTime",weektime1);
		 stationWork1.put("thisTime",weektime0);
		 stationWork1.put("type", 1);
		 stationWork1.put("theoryCycle", 7);

		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		 int inputCycle = DateCycleUtil.getDaysBetween2(weektime1,weektime0);
		 if(inputCycle > 1000 ){
			 inputCycle = 0;
		 }
		 Double performance = (inputCycle-7)*0.5;
		 stationWork1.put("inputCycle", inputCycle);
		 if(performance>0.0){
			stationWork1.put("performance", performance);
		 }else{
			stationWork1.put("performance", 0.0);
		 }
		 stationWork1.put("mark","");
	//             row1.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
		 stationWork1.put("stationCode",StationCode);


		 stationWork2.put("lastTime",weektime2);
		 stationWork2.put("thisTime",weektime1);
		 stationWork2.put("type", 1);
		 stationWork2.put("theoryCycle", 7);

		 int inputCycle2 = DateCycleUtil.getDaysBetween2(weektime2,weektime1);
		 if(inputCycle2 > 1000 ){
			 inputCycle2 = 0;
		 }
		 Double performance2 = (inputCycle2-7)*0.5;
		 stationWork2.put("inputCycle", inputCycle2);
		 if(performance2>0.0){
			stationWork2.put("performance", performance2);
		 }else{
			stationWork2.put("performance", 0.0);
		 }
		 stationWork2.put("mark","");
		 stationWork2.put("stationCode",StationCode);



		 stationWork3.put("lastTime",weektime3);
		 stationWork3.put("thisTime",weektime2);
		 stationWork3.put("type", 1);
		 stationWork3.put("theoryCycle", 7);


		 int inputCycle3 = DateCycleUtil.getDaysBetween2(weektime3,weektime2);
		 if(inputCycle3 > 1000 ){
			inputCycle3 = 0;
		 }
		 Double performance3 = (inputCycle3-7)*0.5;
		 stationWork3.put("inputCycle", inputCycle3);
		 if(performance3>0.0){
			stationWork3.put("performance", performance3);
		 }else{
			stationWork3.put("performance", 0.0);
		 }
		 stationWork3.put("mark","");
		 stationWork3.put("stationCode",StationCode);


		 stationWork4.put("lastTime",weektime4);
		 stationWork4.put("thisTime",weektime3);
		 stationWork4.put("type", 1);
		 stationWork4.put("theoryCycle", 7);

		 int inputCycle4 = DateCycleUtil.getDaysBetween2(weektime4,weektime3);
		 if(inputCycle4 > 1000 ){
				 inputCycle4 = 0;
		 }
		 Double performance4 = (inputCycle4-7)*0.5;
		 stationWork4.put("inputCycle", inputCycle4);
		 if(performance4>0.0){
			stationWork4.put("performance", performance4);
		 }else{
			stationWork4.put("performance", 0.0);
		 }
		 stationWork4.put("mark","");
		 stationWork4.put("stationCode",StationCode);

/*-----------------------------月-------------------------------------------*/
		 stationWork5.put("lastTime",monthtime1);
		 stationWork5.put("thisTime",monthtime0);
		 stationWork5.put("type", 2);
		 stationWork5.put("theoryCycle", 31);

		 int inputCycle5 = DateCycleUtil.getDaysBetween2(monthtime1,monthtime0);
		 if(inputCycle5 > 1000 ){
			inputCycle5 = 0;
		 }
		 Double performance5 = 0.0;
//		if((monthtime1 == "1900-01-01" && monthtime0 == "1900-01-01")||(monthtime1 == "1900-01-01" && monthtime0 != "1900-01-01")){
		if(monthtime0 != "1900-01-01" && inputCycle5 <= 31){
			 performance5 = 0.0;
		 }else{
			 performance5 = 5.0;
		 }
		 stationWork5.put("inputCycle",inputCycle5);

		 stationWork5.put("performance", performance5);
		 stationWork5.put("mark","");
		 stationWork5.put("stationCode",StationCode);

/*-----------------------------半年------------------------------------ */
		 stationWork6.put("lastTime",halfyear1);
		 stationWork6.put("thisTime",halfyear0);
		 stationWork6.put("type", 4);
		 stationWork6.put("theoryCycle", 185);


		 int inputCycle6 = DateCycleUtil.getDaysBetween2(halfyear1,halfyear0);
		 if(inputCycle6 > 1000 ){
			inputCycle6 = 0;
		 }
		Double performance6 = 0.0;
//		if((halfyear1 == "1900-01-01" && halfyear0 == "1900-01-01" )||(halfyear0 == "1900-01-01" && halfyear0 == "1900-01-01")){
		if(halfyear0 != "1900-01-01"&& inputCycle6 <= 185 || halfyear0 == "1900-01-01" && halfyear1 == "1900-01-01" ){
			performance6 = 0.0;
		}else{
			performance6 = 5.0;
		}
		 stationWork6.put("inputCycle", inputCycle6);

		 stationWork6.put("performance", performance6);
		 stationWork6.put("mark","");
		 stationWork6.put("stationCode",StationCode);

/*-----------------------------年------------------------------------ */


		 stationWork7.put("lastTime",year1);
		 stationWork7.put("thisTime",year0);
		 stationWork7.put("type", 5);
		 stationWork7.put("theoryCycle", 365);

		 int inputCycle7 = DateCycleUtil.getDaysBetween2(year1,year0);
		 if(inputCycle7 > 1000 ){
			inputCycle7 = 0;
		 }
		 Double performance7 = 0.0;
		if(year0 != "1900-01-01" && inputCycle7 <= 365 || year0 == "1900-01-01" && year1 == "1900-01-01" ){
			performance7 = 0.0;
		}else{
			performance7 = 5.0;
		}
		 stationWork7.put("inputCycle", inputCycle7);

		 stationWork7.put("performance", performance7);
		 stationWork7.put("mark","");
		 stationWork7.put("stationCode",StationCode);


		/**
		 * 2017-09-27 新增季表
		 */
		stationWork8.put("lastTime",quarter1);
		stationWork8.put("thisTime",quarter0);
		stationWork8.put("type", 3);
		stationWork8.put("theoryCycle", 90);

		int inputCycle8 = DateCycleUtil.getDaysBetween2(quarter1,quarter0);
		if(inputCycle8 > 1000 ){
			inputCycle8 = 0;
		}
		Double performance8 = 0.0;
		//判断是否是第二批次上收的站点，如果是，这些站点就不计算季表
		boolean mark = true;
		String second = "1063E,1064E,1065E,1066E,1067E,1062E,1068E,1069E,1070E,1071E,1127E," +
				"1131E,1128E,1129E,1136E,1132E,1138E,1133E,1134E,1135E,1137E,1139E,1130E,1076E," +
				"1074E,1075E,1072E,1079E,1077E,1081E,1080E,1078E,1073E,1119E,1110E,1161E,1122E," +
				"1120E,1123E,1114E,1118E,1121E,1112E,1117E,1115E,1124E,1111E,1116E,1113E,1126E," +
				"1125E,1155E,1156E,1140E,1149E,1146E,1147E,1151E,1142E,1150E,1153E,1148E,1143E," +
				"1144E,1152E,1154E,1157E,1141E,1107E,1105E,1109E,1106E,1103E,1108E,1104E,1005C";
		if(second.contains(StationCode)){
			mark = false;
		}
		if(mark==false || quarter0 != "1900-01-01" &&  inputCycle8 <= 90 || (quarter0 == "1900-01-01" && quarter1 == "1900-01-01" ) ){
			performance8 = 0.0;
		}else{
			performance8 = 5.0;
		}
		stationWork8.put("inputCycle", inputCycle8);

		stationWork8.put("performance", performance8);
		stationWork8.put("mark","");
		stationWork8.put("stationCode",StationCode);

		 stationWorklist.add(stationWork1);
		 stationWorklist.add(stationWork2);
		 stationWorklist.add(stationWork3);
		 stationWorklist.add(stationWork4);
		 stationWorklist.add(stationWork5);
		 stationWorklist.add(stationWork6);
		 stationWorklist.add(stationWork7);
		 stationWorklist.add(stationWork8);


		 return stationWorklist;
	}
	
	/**
	 * 得到运行单位运行质量
	 * @return
	 */
	private Map<String,Object> saveMonthRunUnitCheck() {
		
		Map<String,Object> runUnitCheck =  new HashMap<>();
		
        try {
        	FileInputStream fis = new FileInputStream(fileToBeRead);
        	 XSSFWorkbook workbook = new XSSFWorkbook(fis);
             XSSFSheet sheet = workbook.getSheet("sheet5");
             XSSFRow row = sheet.getRow(1);
             
             XSSFFormulaEvaluator eval=new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
     		 eval.evaluateFormulaCell(row.getCell(1));  
     		 eval.evaluateFormulaCell(row.getCell(2));
             
             
             runUnitCheck.put("runUnitId", row.getCell(5).getStringCellValue());
             
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
             sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
             Date monthTime;
			try {
				monthTime = sdf.parse(row.getCell(4).getStringCellValue());
				runUnitCheck.put("monthTime", monthTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			int status1 = (int)sheet.getRow(9).getCell(2).getNumericCellValue();
			int status2 = (int)sheet.getRow(10).getCell(2).getNumericCellValue();
			int status3 = (int)sheet.getRow(11).getCell(2).getNumericCellValue();
			int status4 = (int)sheet.getRow(12).getCell(2).getNumericCellValue();
			int status5 = (int)sheet.getRow(13).getCell(2).getNumericCellValue();
			int status6 = (int)sheet.getRow(14).getCell(2).getNumericCellValue();
			
			runUnitCheck.put("status1", status1);
			runUnitCheck.put("status2", status2);
			runUnitCheck.put("status3", status3);
			runUnitCheck.put("status4", status4);
			runUnitCheck.put("status5", status5);
			runUnitCheck.put("status6", status6);
			Double a = 1.0;
			Double b = 1.0;
			if(status2 == 1 ||status3 == 1||status6 == 1){
				a = 0.0;
			}else if(status1 == 1 && status4 == 1 && status5 == 1){
				b = 0.5 ;
			}else if(status1 == 1 && status4 == 0 && status5 == 1){
				b = 0.5 ;                                         
			}else if(status1 == 1 && status4 == 1 && status5 == 0){
				b = 0.5 ;                                         
			}else if(status1 == 0 && status4 == 1 && status5 == 1){
				b = 0.5 ;                                         
			}else if(status1 == 0 && status4 == 0 && status5 == 1){
				b = 0.5 ;                                         
			}else if(status1 == 0 && status4 == 1 && status5 == 0){
				b = 0.75 ;                                        
			}else if(status1 == 1 && status4 == 0 && status5 == 0){
				b = 0.5 ;
			}
			runUnitCheck.put("runQuality", a * b);
			
            fis.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return runUnitCheck;
	}
	
	private Map<String,Object> saveMonthStationCheck() {

		Map<String,Object> stationCheck =  new HashMap<>();
		
        try {
        	 FileInputStream fis = new FileInputStream(fileToBeRead);
        	 XSSFWorkbook workbook = new XSSFWorkbook(fis);
             XSSFSheet sheet = workbook.getSheet("sheet5");
             XSSFRow row = sheet.getRow(1);
             XSSFRow row3 = sheet.getRow(3);
             XSSFRow row4 = sheet.getRow(4);
             XSSFRow row5 = sheet.getRow(5);
             
             XSSFFormulaEvaluator eval=new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
     		 eval.evaluateFormulaCell(row.getCell(1));  
     		 eval.evaluateFormulaCell(row.getCell(2));  
     		 eval.evaluateFormulaCell(row3.getCell(3));  
     		 eval.evaluateFormulaCell(row4.getCell(3)); 
     		 eval.evaluateFormulaCell(row5.getCell(3));
             
             
             stationCheck.put("runUnitId", row.getCell(5).getStringCellValue());
             
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
             sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
             Date checkTime;
			try {
				checkTime = sdf.parse(row.getCell(4).getStringCellValue());
				stationCheck.put("checkMonth", checkTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			stationCheck.put("stationCode",row.getCell(1).getStringCellValue());
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			stationCheck.put("stationName",row.getCell(2).getStringCellValue());
			stationCheck.put("transferStatus",sheet.getRow(3).getCell(3).getNumericCellValue());
			stationCheck.put("validateStatus",(int)(sheet.getRow(4).getCell(3).getNumericCellValue()));
			stationCheck.put("scoresStatus",sheet.getRow(5).getCell(3).getNumericCellValue());
			double coefficient = sheet.getRow(6).getCell(1).getNumericCellValue();
//			if(sheet.getRow(3).getCell(3).getNumericCellValue()<0.85 || (int)(sheet.getRow(4).getCell(3).getNumericCellValue())<27 || 
//					sheet.getRow(5).getCell(3).getNumericCellValue()<80){
//				coefficient=0.0;
//			}
			stationCheck.put("coefficient", coefficient);
            fis.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return stationCheck;
	}
	//每月15号凌晨15分开始执行
	@RequestMapping(value="/test")
	//事务管理
	@Transactional(value="txManager")
	 public void  test(){
		Map<String,Object> mw = monthCompleteStatusService.getMonthCompleteStatus("2018-02-01","2018-02-28","1085E");
		for (String k : mw.keySet()){
			String key = k;
			String value = mw.get(k).toString();
		}
	}

}
