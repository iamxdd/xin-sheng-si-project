package com.rx.substation.domain;

import java.util.Date;

public class Transfer {
	
	private String stationCode;
	
	private String stationName;
	
	private Date monthTime;
	
	private Double so2;
	
	private Double no2;
	
	private Double o3;
	
	private Double co;
	
	private Double pm10;
	
	private Double pm2_5;
	
	private Double avgTransfer;
	
	private boolean status;
	
	private Double scores;

	public Transfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Date getMonthTime() {
		return monthTime;
	}

	public void setMonthTime(Date monthTime) {
		this.monthTime = monthTime;
	}

	public Double getSo2() {
		return so2;
	}

	public void setSo2(Double so2) {
		this.so2 = so2;
	}

	public Double getNo2() {
		return no2;
	}

	public void setNo2(Double no2) {
		this.no2 = no2;
	}

	public Double getO3() {
		return o3;
	}

	public void setO3(Double o3) {
		this.o3 = o3;
	}

	public Double getCo() {
		return co;
	}

	public void setCo(Double co) {
		this.co = co;
	}

	public Double getPm10() {
		return pm10;
	}

	public void setPm10(Double pm10) {
		this.pm10 = pm10;
	}

	public Double getPm2_5() {
		return pm2_5;
	}

	public void setPm2_5(Double pm2_5) {
		this.pm2_5 = pm2_5;
	}

	public Double getAvgTransfer() {
		return avgTransfer;
	}

	public void setAvgTransfer(Double avgTransfer) {
		this.avgTransfer = avgTransfer;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Double getScores() {
		return scores;
	}

	public void setScores(Double scores) {
		this.scores = scores;
	}

	public Transfer(String stationCode, String stationName, Date monthTime, Double so2, Double no2, Double o3,
			Double co, Double pm10, Double pm2_5, Double avgTransfer, boolean status, Double scores) {
		super();
		this.stationCode = stationCode;
		this.stationName = stationName;
		this.monthTime = monthTime;
		this.so2 = so2;
		this.no2 = no2;
		this.o3 = o3;
		this.co = co;
		this.pm10 = pm10;
		this.pm2_5 = pm2_5;
		this.avgTransfer = avgTransfer;
		this.status = status;
		this.scores = scores;
	}

	@Override
	public String toString() {
		return "Transfer [stationCode=" + stationCode + ", stationName=" + stationName + ", monthTime=" + monthTime
				+ ", so2=" + so2 + ", no2=" + no2 + ", o3=" + o3 + ", co=" + co + ", pm10=" + pm10 + ", pm2_5=" + pm2_5
				+ ", avgTransfer=" + avgTransfer + ", status=" + status + ", scores=" + scores + "]";
	}

	
	
}
