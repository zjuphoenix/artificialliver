package com.artificialliver.model;

public class OperationInfo {
	public String patientName;
	public String gender;
	public String age;
	public String treatMethod;
	public String doctor;
	public String time;
	public String operationNo;
	public String extraInfo;
	public OperationInfo(String patientName,String gender,String age, String treatMethod,String doctor,String operationNo,String extraInfo,String time) {
		this.patientName=patientName;
		this.gender=gender;
		this.age=age;
		this.treatMethod=treatMethod;
		this.doctor=doctor;
		this.operationNo=operationNo;
		this.extraInfo=extraInfo;
		this.time=time;
	}
}
