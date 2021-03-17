package com.founder.bdyx.model;
/**
* @Description 查询病人日清单
* @author yang.xuefeng
* @version 创建时间：2020年1月2日 下午11:37:03
*/
public class queryPatDayCharge extends BaseQuery {

	private String patientId;
	private int times;
	
	private String startDate;
	private String endDate;
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public int getTimes() {
		return times;
	}
	public void setTimes(int times) {
		this.times = times;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
