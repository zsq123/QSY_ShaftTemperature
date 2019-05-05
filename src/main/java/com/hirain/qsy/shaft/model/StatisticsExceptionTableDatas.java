package com.hirain.qsy.shaft.model;

import java.util.Date;

import lombok.Data;

@Data
public class StatisticsExceptionTableDatas {

	private Integer axleNum;

	private Integer pointNum;

	private Date faultTime;

	private String details = "";

	public StatisticsExceptionTableDatas() {

	}

	public StatisticsExceptionTableDatas(Integer axleNum, int pointNum, Date faultTime) {

		this.axleNum = axleNum;
		this.pointNum = pointNum;
		this.faultTime = faultTime;
	}

}
