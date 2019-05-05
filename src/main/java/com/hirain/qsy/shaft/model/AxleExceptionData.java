package com.hirain.qsy.shaft.model;

import java.util.Date;

import lombok.Data;

@Data
public class AxleExceptionData {
	private String actualValue;
	
	private String predicteValue;
	
	private String residuals;
	
	private String threshold;
	
	private String threshold1;
	
	private String threshold2;
	
	private Date acquisitionTime;

}
