package com.rx.substation.domain;

import java.util.Date;

public class Inspection {

	private String stationCode;
	
	private String stationName;
	
	private String checkWork;
	
	private boolean result;
	
	private Double deduct;
	
	private Date checkTime;
	
	private String checkPerson;
	
	private String mark;

	public Inspection() {
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

	public String getCheckWork() {
		return checkWork;
	}

	public void setCheckWork(String checkWork) {
		this.checkWork = checkWork;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public Double getDeduct() {
		return deduct;
	}

	public void setDeduct(Double deduct) {
		this.deduct = deduct;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckPerson() {
		return checkPerson;
	}

	public void setCheckPerson(String checkPerson) {
		this.checkPerson = checkPerson;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Override
	public String toString() {
		return "Inspection [stationCode=" + stationCode + ", stationName=" + stationName + ", checkWork=" + checkWork
				+ ", result=" + result + ", deduct=" + deduct + ", checkTime=" + checkTime + ", checkPerson="
				+ checkPerson + ", mark=" + mark + "]";
	}

	
	
}
