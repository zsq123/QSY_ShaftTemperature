package com.hirain.qsy.shaft.model;



import java.util.Date;

import lombok.Data;

@Data
public class InitialAxleData {
	
	private Float axle1;
	private Float axle2;
	private Float axle3;
	private Float axle4;
	private Float axle5;
	private Float axle6;
	private Integer gpsSpeed;
	private Float ambientTemperature1;
	private Float ambientTemperature2;
	private Date acquisitionTime;
	

}
