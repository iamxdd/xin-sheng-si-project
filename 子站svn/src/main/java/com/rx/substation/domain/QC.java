package com.rx.substation.domain;

import java.util.Date;

public class QC {
	
	private String stationCode;
	
	private String stationName;
	
	private String brandId;
	
	private Date checkTime;
	
	private String cityId;
	
	private String controllerName;
	
	private Date lastTime;
	
	private Date thisTime;
	
	//理论周期
	private int theorycylcle;
	//填报周期
	private int inputCylcle;
	//工作内容 
	//0每月工作完成，月报填报
	//1半年工作完成，半年报填报
	//2每年工作完成，年报填报
	private int jobContent;
	//完成情况
	private boolean execution;
	//绩效
	private double  performance;
	//扣分情况
	private int pointsReasons;
	//备注
	private String mark;
	
	private double totalScore;
	
	
	
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
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public Date getThisTime() {
		return thisTime;
	}
	public void setThisTime(Date thisTime) {
		this.thisTime = thisTime;
	}
	public int getTheorycylcle() {
		return theorycylcle;
	}
	public void setTheorycylcle(int theorycylcle) {
		this.theorycylcle = theorycylcle;
	}
	public int getInputCylcle() {
		return inputCylcle;
	}
	public void setInputCylcle(int inputCylcle) {
		this.inputCylcle = inputCylcle;
	}
	public int getJobContent() {
		return jobContent;
	}
	public void setJobContent(int jobContent) {
		this.jobContent = jobContent;
	}
	public boolean isExecution() {
		return execution;
	}
	public void setExecution(boolean execution) {
		this.execution = execution;
	}
	public double getPerformance() {
		return performance;
	}
	public void setPerformance(double performance) {
		this.performance = performance;
	}
	public int getPointsReasons() {
		return pointsReasons;
	}
	public void setPointsReasons(int pointsReasons) {
		this.pointsReasons = pointsReasons;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public double getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(double totalScore) {
		this.totalScore = totalScore;
	}
	public QC() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "QC [stationCode=" + stationCode + ", stationName=" + stationName + ", brandId=" + brandId
				+ ", checkTime=" + checkTime + ", cityId=" + cityId + ", controllerName=" + controllerName
				+ ", lastTime=" + lastTime + ", thisTime=" + thisTime + ", theorycylcle=" + theorycylcle
				+ ", inputCylcle=" + inputCylcle + ", jobContent=" + jobContent + ", execution=" + execution
				+ ", performance=" + performance + ", pointsReasons=" + pointsReasons + ", mark=" + mark + "]";
	}
	
	
	
}
