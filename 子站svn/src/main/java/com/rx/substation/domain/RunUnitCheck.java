package com.rx.substation.domain;

import java.math.BigDecimal;
import java.util.Date;

public class RunUnitCheck {

	
	private String stationCode;
	
	private String stationName;
	
	private Date monthTime;
	
	private Date checkMonth;
	//传输率
	private BigDecimal transferStatus;
	//有效天数
	private int validateStatus;
	//得分
	private Double scoresStatus;
	//系数
	private Double coefficient;
	//运行质量评估 0 无评估因素 就等于coefficient
//	1 当月20%站点（3个农村站）未达到数据有效性要求	 0.5*coefficient
//	2 连续两月20%站点（3个农村站）未达到数据有效性要求	0
//	3 当月40%站点（7个农村站）未达到数据有效性要求	 0
//	4 同一站点连续两个月未达到数据有效性要求	0.75
//	5 同一站点连续三个月未达到数据有效性要求	0.5
//	6 同一站点连续四个月未达到数据有效性要求	0
//	private boolean status1;
//	private boolean status2;
//	private boolean status3;
//	private boolean status4;
//	private boolean status5;
//	private boolean status6;
	//总系数
//	private Double runQuality;
	
	
	public RunUnitCheck() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getStationCode() {
		return stationCode;
	}


	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}


	public Date getMonthTime() {
		return monthTime;
	}


	public void setMonthTime(Date monthTime) {
		this.monthTime = monthTime;
	}


	public Date getCheckMonth() {
		return checkMonth;
	}


	public void setCheckMonth(Date checkMonth) {
		this.checkMonth = checkMonth;
	}


	public BigDecimal getTransferStatus() {
		return transferStatus;
	}


	public void setTransferStatus(BigDecimal transferStatus) {
		this.transferStatus = transferStatus;
	}


	public int getValidateStatus() {
		return validateStatus;
	}


	public void setValidateStatus(int validateStatus) {
		this.validateStatus = validateStatus;
	}


	public Double getScoresStatus() {
		return scoresStatus;
	}


	public void setScoresStatus(Double scoresStatus) {
		this.scoresStatus = scoresStatus;
	}


	public Double getCoefficient() {
		return coefficient;
	}


	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}


	public String getStationName() {
		return stationName;
	}


	public void setStationName(String stationName) {
		this.stationName = stationName;
	}


	@Override
	public String toString() {
		return "RunUnitCheck [stationCode=" + stationCode + ", stationName=" + stationName + ", monthTime=" + monthTime
				+ ", checkMonth=" + checkMonth + ", transferStatus=" + transferStatus + ", validateStatus="
				+ validateStatus + ", scoresStatus=" + scoresStatus + ", coefficient=" + coefficient + "]";
	}

	


	
}
