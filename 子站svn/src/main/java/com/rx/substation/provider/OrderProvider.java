package com.rx.substation.provider;

import java.util.Map;

public class OrderProvider {
	
		/**
		 * 考核结果
		 * @param paras
		 * @return
		 */
	    public String findcalScorceByConditions(Map<String, Object> paras) {
	    	String sql = "SELECT * FROM "
					+ "(SELECT DISTINCT a.*,b.CityId,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName FROM check_score_data a left JOIN "
					+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  "
					+ "on a.StationCode = b.StationCode) "
					+ "m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);
			if(paras.get("start")!=null){
				buffer.append(" AND m.monthTime >= #{start}");
			}

			if(paras.get("end")!=null){
				buffer.append(" AND m.monthTime <= #{end}");
			}

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND m.runCode = #{runUnitId}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
	        	buffer.append(" AND m.CityId in (" + a +")");
	        }

	        if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
	        	buffer.append(" AND m.stationCode in (" + b +")");
	        }
			if(paras.get("param")!=null && "1".equals(paras.get("sortstatus"))){
				buffer.append(" order by "+paras.get("param"));
			}else if(paras.get("param")!=null && "0".equals(paras.get("sortstatus"))){
				buffer.append(" order by "+paras.get("param")+" DESC");
			}else{
				buffer.append(" order by m.monthTime DESC,m.runCode,m.CityId,m.StationCode");
			}
	        return buffer.toString();
		}
		
	    /**
	     * 基本保障
	     * @param paras
	     * @return
	     */
	    public String findBasicCheckByConditions(Map<String, Object> paras) {
	    	String sql = "SELECT * FROM "
					+ "(SELECT DISTINCT a.*,b.CityId,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName FROM BasicCheck a left JOIN "
					+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  "
					+ "on a.StationCode = b.StationCode) "
					+ "m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);

	        if(paras.get("start")!=null){
	        	buffer.append(" AND m.TimePoint >= #{start}");
	        }

	        if(paras.get("end")!=null){
	        	buffer.append(" AND m.TimePoint <= #{end}");
	        }

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND m.runCode = #{runUnitId}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
				buffer.append(" AND m.CityId in (" + a +")");
			}

	        if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
				buffer.append(" AND m.StationCode in (" + b +")");
	        }
			buffer.append(" order by m.TimePoint DESC,m.runCode,m.CityId,m.StationCode");
	        return buffer.toString();
		}


	/**
	 * 扣分表
	 * @param paras
	 * @return
	 */
	public String findDeductByConditions(Map<String, Object> paras) {
		String sql = "SELECT * FROM "
				+ "(SELECT DISTINCT a.*,b.CityId,b.StationTypeId,b.Area,b.CountyId,b.runCode FROM qcInspection a left JOIN "
				+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  "
				+ "on a.StationCode = b.StationCode) "
				+ "m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);

		if(paras.get("start")!=null){
			buffer.append(" AND m.checkTime >= #{start}");
		}

		if(paras.get("end")!=null){
			buffer.append(" AND m.checkTime <= #{end}");
		}

		if(paras.get("runUnitId")!=null){
			buffer.append(" AND m.runCode = #{runUnitId}");
		}

		if(paras.get("citys")!=null){
			String a = paras.get("citys").toString();
			buffer.append(" AND m.CityId in (" + a +")");
		}

		if(paras.get("stations")!=null){
			String b = paras.get("stations").toString();
			buffer.append(" AND m.stationCode in (" + b +")");
		}
		buffer.append(" order by m.checkTime DESC,m.runCode,m.CityId,m.StationCode");
		return buffer.toString();
	}

	    
	    /**
	     * 传输率
	     * @param paras
	     * @return
	     */
	    public String findTansferByConditions(Map<String, Object> paras) {
			String sql ="SELECT * FROM "
					+ "(SELECT DISTINCT a.*,b.CityId,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName FROM transfer_scores a left JOIN "
					+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  "
					+ "on a.StationCode = b.StationCode) "
					+ "m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);

			if(paras.get("start")!=null){
				buffer.append(" AND m.monthTime >= #{start}");
			}

			if(paras.get("end")!=null){

				buffer.append(" AND m.monthTime <= #{end}");
			}

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND m.runCode = #{runUnitId}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
				buffer.append(" AND m.CityId in (" + a +")");
			}

			if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
				buffer.append(" AND m.stationCode in (" + b +")");
			}
			if(paras.get("param")!=null && "1".equals(paras.get("sortstatus"))){
				buffer.append(" order by "+paras.get("param"));
			}else if(paras.get("param")!=null && "0".equals(paras.get("sortstatus"))){
				buffer.append(" order by "+paras.get("param")+" DESC");
			}else{
				buffer.append(" order by m.monthTime DESC,m.runCode,m.CityId,m.StationCode");
			}
	        return buffer.toString();
		}
	    
	    /**
	     * 有效天数
	     * @param paras
	     * @return
	     */
	    public String findValidateByConditions(Map<String, Object> paras) {
	    	String sql ="SELECT * FROM "
					+ "(SELECT DISTINCT a.*,b.CityId,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName FROM validate_score a left JOIN "
					+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  "
					+ "on a.StationCode = b.StationCode) "
					+ "m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
	        
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);
			if(paras.get("start")!=null){
				buffer.append(" AND m.monthTime >= #{start}");
			}

			if(paras.get("end")!=null){
				buffer.append(" AND m.monthTime <= #{end}");
			}

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND m.runCode = #{runUnitId}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
				buffer.append(" AND m.CityId in (" + a +")");
			}

			if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
				buffer.append(" AND m.stationCode in (" + b +")");
			}
			if(paras.get("param")!=null && "1".equals(paras.get("sortstatus"))){
				buffer.append(" order by "+paras.get("param"));
			}else if(paras.get("param")!=null && "0".equals(paras.get("sortstatus"))){
				buffer.append(" order by "+paras.get("param")+" DESC");
			}else{
				buffer.append(" order by m.monthTime DESC,m.runCode,m.CityId,m.StationCode");
			};
	        return buffer.toString();
		}

		/**
		 * 监测仪器启用信息
		 * @param paras
		 * @return
		 */
		public String getOpeningManageData(Map<String, Object> paras) {
			String sql = "SELECT * FROM " +
					"(SELECT DISTINCT b.StationId,b.StationCode,b.StationTypeId,b.CityId,b.Area,b.CountyID,b.stationName,b.runName,b.runCode,a.BrandId Model,a.OpeningTime,a.brand1 siName FROM SubStationInstrument a left JOIN " +
					"(SELECT DISTINCT x.StationId,x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyID,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b " +
					"on a.StationId = b.StationId) " +
					"m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";

			StringBuffer buffer = new StringBuffer();
			buffer.append(sql);
			if(paras.get("start")!=null){
				buffer.append(" AND m.OpeningTime >= #{start}");
			}

			if(paras.get("end")!=null){
				buffer.append(" AND m.OpeningTime <= #{end}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
				buffer.append(" AND m.CityId in (" + a +")");
			}

			if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
				buffer.append(" AND m.stationCode in (" + b +")");
			}
			if("1".equals(paras.get("sortstatus"))){
				buffer.append(" order by OpeningTime");
			}else if("0".equals(paras.get("sortstatus"))){
				buffer.append(" order by OpeningTime"+" DESC");
			}else{
				buffer.append(" order by m.runCode,m.CityId,m.StationCode");
			};
			return buffer.toString();
		}
	    
	    /***************************运行单位日常工作评价*****************************************************/
	    
	    public String findRunUnitQc2(Map<String, Object> paras) {
	    	String sql = "select * from station_working2_data where 1 = 1";
	    	
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);
			if(paras.get("id")!=null){
				buffer.append(" AND id = #{id}");
			}
	        buffer.append(" order by stationCode,type,thisTime");
	        return buffer.toString();
		}
	    		
	    		
	    
	    public String findRunUnitQcByConditions(Map<String, Object> paras) {
	    	String sql = "SELECT * FROM " +
					"(SELECT DISTINCT a.*,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName FROM station_working_data a left JOIN " +
					"(SELECT DISTINCT x.StationCode,x.StationTypeId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  " +
					"on a.StationCode = b.StationCode) " +
					"m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
	    	
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);
			if(paras.get("start")!=null){
				buffer.append(" AND m.checkTime >= #{start}");
			}

			if(paras.get("end")!=null){
				buffer.append(" AND m.checkTime <= #{end}");
			}

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND m.runCode = #{runUnitId}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
				buffer.append(" AND m.CityId in (" + a +")");
			}

			if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
				buffer.append(" AND m.stationCode in (" + b +")");
			}
			buffer.append(" order by m.checkTime DESC,m.runCode,m.CityId,m.StationCode");
	        return buffer.toString();
		}
	    
	    /*************************************运行单位运行质量评估*******************************************/
	    
	    public String findCompleteStatusByConditions(Map<String, Object> paras) {
	    	String sql = "select DISTINCT top 1 * from station_run_data where 1 = 1";
	    	
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);
	        if(paras.get("start")!=null){
	        	buffer.append(" AND monthTime >= #{start}");
	        }

	        if(paras.get("end")!=null){
	        	buffer.append(" AND monthTime <= #{end}");
	        }

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND runUnitId = #{runUnitId}");
			}

	        buffer.append(" order by runQuality");
	        return buffer.toString();
		}

	/***
	 * 基本保障表
	 * @param paras
	 * @return
	 */
	public String findRunitQualityByConditions(Map<String, Object> paras) {
	    	String sql = "SELECT * FROM "
					+ "(SELECT DISTINCT a.*,b.CityId,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.runName FROM run_unit_data a left JOIN "
					+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  "
					+ "on a.StationCode = b.StationCode) "
					+ "m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);

	        if(paras.get("start")!=null){
	        	buffer.append(" AND m.checkMonth >= #{start}");
	        }

	        if(paras.get("end")!=null){
	        	buffer.append(" AND m.checkMonth <= #{end}");
	        }

			if(paras.get("runUnitId")!=null){
				buffer.append(" AND m.runCode = #{runUnitId}");
			}

			if(paras.get("citys")!=null){
				String a = paras.get("citys").toString();
				buffer.append(" AND m.CityId in (" + a +")");
			}

			if(paras.get("stations")!=null){
				String b = paras.get("stations").toString();
				buffer.append(" AND m.stationCode in (" + b +")");
			}
			buffer.append(" order by m.checkMonth DESC,m.runCode,m.CityId,m.StationCode");
	        return buffer.toString();
		}

	/**
	 * 报表统计
	 * @param paras
	 * @return
	 */

	public String findCompleteStatus(Map<String, Object> paras) {
		String sql = "select DISTINCT  * from station_run_data where 1 = 1";

		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);
		if(paras.get("start")!=null){
			buffer.append(" AND monthTime >= #{start}");
		}

		if(paras.get("end")!=null){
			buffer.append(" AND monthTime <= #{end}");
		}
		buffer.append(" order by runUnitId,runQuality");
		return buffer.toString();
	}
	    
	    /***********************************多条件级联*********************************************/
	    
	    public String findStationByCondition(Map<String, Object> paras) {
	    	String sql = "select *  FROM "
	    			+ "(SELECT DISTINCT x.StationCode,x.StationTypeId,x.CityId,x.Area,y.stationName,y.runName,y.runCode  FROM Station x "
	    			+ "LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) "
	    			+ "a LEFT JOIN StationType b on a.stationTypeId = b.stationTypeId where 1 = 1 ";
	    	
	    	StringBuffer buffer = new StringBuffer();
	    	buffer.append(sql);
			if(paras.get("runUnitIds")!=null){
				buffer.append(" AND a.runCode in " +
						"<foreach item='runUnitId' index='index' collection='list'" +
						"                + open='(' separator=',' close=')'>" +
						"                + #{runUnitId}" +
						"            </foreach>);");
			}

			if(paras.get("stationTypeIds")!=null){
				buffer.append(" AND b.StationTypeId in (${stationTypeIds})");
			}

			if(paras.get("cityId")!=null){
				buffer.append(" AND a.CityId = #{cityId}");
			}


//	        return null;
	        return buffer.toString();
		}


		public String findCityByCondition(Map<String, Object> paras) {
			String sql ="select DISTINCT a.CityId,a.Area FROM "
					+ "(SELECT DISTINCT s.StationCode,s.StationTypeId,s.CityId,s.Area,r.stationName,r.runName,r.runCode  FROM Station s "
					+ "LEFT JOIN RunUnit r ON s.StationCode = r.StationCode) "
					+ "a LEFT JOIN StationType st on a.stationTypeId = st.stationTypeId where 1 = 1 ";

			StringBuffer buffer = new StringBuffer();
			buffer.append(sql);

			if(paras.get("stationTypeIds")!=null){
				buffer.append(" AND st.StationTypeId in ${stationTypeIds}");
			}

			if(paras.get("runUnitIds")!=null){
				buffer.append(" AND a.runCode in " +
						" <foreach collection='runUnitIds' item='runUnitId'" +
						"          index='index' open='(' close=')' separator=','>" +
						" #{runUnitId}" +
						"</foreach>");
			}
			buffer.append(" order by a.CityId");
	        return "<script>" + buffer.toString() + "</script>";
		}


		public String findStationTypeByCondition(Map<String, Object> paras) {
			String sql = "select DISTINCT st.StationTypeId,st.StationTypeName,a.CityId,a.Area,a.runCode,a.runName,a.StationCode,a.stationName FROM "
					+ "(SELECT DISTINCT s.StationCode,s.StationTypeId,s.CityId,s.Area,r.stationName,r.runName,r.runCode  FROM Station s "
					+ "LEFT JOIN RunUnit r ON s.StationCode = r.StationCode) "
					+ "a LEFT JOIN StationType st on a.stationTypeId = st.stationTypeId where 1 = 1 ";

			StringBuffer buffer = new StringBuffer();
			buffer.append(sql);

			if(paras.get("stationTypeId")!=null){
				buffer.append(" AND st.StationTypeId = #{stationTypeId}");
			}
			buffer.append(" order by st.StationTypeId");
			return buffer.toString();
		}

		public String findRunUnitByCondition(Map<String, Object> paras) {
			String sql = "select DISTINCT st.StationTypeId,st.StationTypeName,a.CityId,a.Area,a.runCode,a.runName,a.StationCode,a.stationName FROM "
					+ "(SELECT DISTINCT s.StationCode,s.StationTypeId,s.CityId,s.Area,r.stationName,r.runName,r.runCode  FROM Station s "
					+ "LEFT JOIN RunUnit r ON s.StationCode = r.StationCode) "
					+ "a LEFT JOIN StationType st on a.stationTypeId = st.stationTypeId where 1 = 1 ";

			StringBuffer buffer = new StringBuffer();
			buffer.append(sql);

			if(paras.get("runUnitId") != null){
				buffer.append(" AND a.runCode = #{runUnitId}");
			}
			buffer.append(" order by a.runCode");
			return buffer.toString();
		}

	public String getStationCostMsg(Map<String, Object> paras) {
		String sql = "SELECT DISTINCT x.StationCode,x.Area,x.CountyId,y.stationName,y.runName,y.runCode,z.CountyName,n.Cost,n.TimePoint FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode LEFT JOIN County z ON  x.CountyId = z.Id " +
				"RIGHT JOIN StationCostConfig n ON x.StationCode = n.StationCode where 1 = 1 ";

		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);

		if(paras.get("stationCode") != null){
			buffer.append(" AND x.StationCode = #{stationCode}");
		}
		if(paras.get("timePoint") != null){
			buffer.append(" AND n.TimePoint = #{timePoint}");
		}
		buffer.append(" order by n.TimePoint,Area");
		return buffer.toString();
	}


	public String countInspection(Map<String, Object> paras) {
		String sql = "SELECT * FROM " +
				"(SELECT DISTINCT a.*,b.StationTypeId,b.Area,b.CountyId,b.runCode,b.CityId FROM qcInspection a left JOIN " +
				"(SELECT DISTINCT x.StationCode,x.StationTypeId,x.Area,x.CountyId,y.stationName,y.runName,y.runCode,x.CityId  FROM Station x LEFT JOIN RunUnit y ON x.StationCode = y.StationCode) b  " +
				"on a.StationCode = b.StationCode) " +
				"m LEFT JOIN County n ON m.CountyId = n.Id where 1=1";

		StringBuffer buffer = new StringBuffer();
		buffer.append(sql);
		if(paras.get("start")!=null){
			buffer.append(" AND m.checkTime >= #{start}");
		}

		if(paras.get("end")!=null){
			buffer.append(" AND m.checkTime <= #{end}");
		}

		if(paras.get("runUnitId")!=null){
			buffer.append(" AND m.runCode = #{runUnitId}");
		}

		if(paras.get("citys")!=null){
			String a = paras.get("citys").toString();
			buffer.append(" AND m.CityId in (" + a +")");
		}

		if(paras.get("stations")!=null){
			String b = paras.get("stations").toString();
			buffer.append(" AND m.stationCode in (" + b +")");
		}
		buffer.append(" order by m.checkTime DESC,m.runCode,m.CityId,m.StationCode");
		return buffer.toString();
	}

}
