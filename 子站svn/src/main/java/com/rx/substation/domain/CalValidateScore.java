package com.rx.substation.domain;

import java.util.Date;

/**
 * @author dcx
 *
 */
public class CalValidateScore {
	//站点编号
	private String stationCode;
	
	private String stationName;
	
	private Date monthTime;
	
	//理论天数
	private int theoryDays;
	//断电天数
	private int powerCutDays;
	//其他原因天数
	private int others;
	//应测天数
	private int shouldDays;
	//有效天数
	private int validatyDays;
	//分数
	private Double scores;

	public CalValidateScore() {
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

	public int getTheoryDays() {
		return theoryDays;
	}

	public void setTheoryDays(int theoryDays) {
		this.theoryDays = theoryDays;
	}

	public int getPowerCutDays() {
		return powerCutDays;
	}

	public void setPowerCutDays(int powerCutDays) {
		this.powerCutDays = powerCutDays;
	}

	public int getOthers() {
		return others;
	}

	public void setOthers(int others) {
		this.others = others;
	}

	public int getShouldDays() {
		return shouldDays;
	}

	public void setShouldDays(int shouldDays) {
		this.shouldDays = shouldDays;
	}

	public int getValidatyDays() {
		return validatyDays;
	}

	public void setValidatyDays(int validatyDays) {
		this.validatyDays = validatyDays;
	}

	public Double getScores() {
		return scores;
	}

	public void setScores(Double scores) {
		this.scores = scores;
	}
	
	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	@Override
	public String toString() {
		return "CalValidateScore [stationCode=" + stationCode + ", monthTime=" + monthTime + ", theoryDays="
				+ theoryDays + ", powerCutDays=" + powerCutDays + ", others=" + others + ", shouldDays=" + shouldDays
				+ ", validatyDays=" + validatyDays + ", scores=" + scores + "]";
	}


}
