package com.rx.substation.domain;

import java.util.Date;

/**
 * @author dcx
 *
 */
public class CalTotalScore {
	//站点编号
	private String stationCode;
	//站点名称
	private String stationName;
	//考核月份
	private Date monthTime;
	//传输率得分
	private Double tansferScore;
	//有效天数得分
	private Double validateDaysScore;
	//完成情况得分
	private Double completeScore;
	//质检扣分
	private Double qcDeductScore;
	//总分
	private Double totalScore;
	//运行费
	private Double totalCost;
	
	
	public CalTotalScore() {
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

	public Double getTansferScore() {
		return tansferScore;
	}

	public void setTansferScore(Double tansferScore) {
		this.tansferScore = tansferScore;
	}

	public Double getValidateDaysScore() {
		return validateDaysScore;
	}

	public void setValidateDaysScore(Double validateDaysScore) {
		this.validateDaysScore = validateDaysScore;
	}

	public Double getCompleteScore() {
		return completeScore;
	}

	public void setCompleteScore(Double completeScore) {
		this.completeScore = completeScore;
	}

	public Double getQcDeductScore() {
		return qcDeductScore;
	}

	public void setQcDeductScore(Double qcDeductScore) {
		this.qcDeductScore = qcDeductScore;
	}

	public Double getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Double totalScore) {
		this.totalScore = totalScore;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "CalTotalScore [stationCode=" + stationCode + ", stationName=" + stationName + ", monthTime=" + monthTime
				+ ", tansferScore=" + tansferScore + ", validateDaysScore=" + validateDaysScore + ", completeScore="
				+ completeScore + ", qcDeductScore=" + qcDeductScore + ", totalScore=" + totalScore + ", totalCost="
				+ totalCost + "]";
	}

	

	
	
}
