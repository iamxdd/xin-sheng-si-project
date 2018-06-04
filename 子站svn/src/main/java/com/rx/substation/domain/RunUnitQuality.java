package com.rx.substation.domain;

import java.util.List;

public class RunUnitQuality {
	
	//运行质量评估 0 无评估因素 就等于coefficient
//	1 当月20%站点（3个农村站）未达到数据有效性要求	 0.5*coefficient
//	2 连续两月20%站点（3个农村站）未达到数据有效性要求	0
//	3 当月40%站点（7个农村站）未达到数据有效性要求	 0
//	4 同一站点连续两个月未达到数据有效性要求	0.75
//	5 同一站点连续三个月未达到数据有效性要求	0.5
//	6 同一站点连续四个月未达到数据有效性要求	0
	private boolean status1;
	private boolean status2;
	private boolean status3;
	private boolean status4;
	private boolean status5;
	private boolean status6;
	//总系数
	private Double runQuality;
	
	private List<RunUnitCheck> runUnitCheck;

	public boolean isStatus1() {
		return status1;
	}

	public void setStatus1(boolean status1) {
		this.status1 = status1;
	}

	public boolean isStatus2() {
		return status2;
	}

	public void setStatus2(boolean status2) {
		this.status2 = status2;
	}

	public boolean isStatus3() {
		return status3;
	}

	public void setStatus3(boolean status3) {
		this.status3 = status3;
	}

	public boolean isStatus4() {
		return status4;
	}

	public void setStatus4(boolean status4) {
		this.status4 = status4;
	}

	public boolean isStatus5() {
		return status5;
	}

	public void setStatus5(boolean status5) {
		this.status5 = status5;
	}

	public boolean isStatus6() {
		return status6;
	}

	public void setStatus6(boolean status6) {
		this.status6 = status6;
	}

	public Double getRunQuality() {
		return runQuality;
	}

	public void setRunQuality(Double runQuality) {
		this.runQuality = runQuality;
	}

	public List<RunUnitCheck> getRunUnitCheck() {
		return runUnitCheck;
	}

	public void setRunUnitCheck(List<RunUnitCheck> runUnitCheck) {
		this.runUnitCheck = runUnitCheck;
	}

	public RunUnitQuality() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
