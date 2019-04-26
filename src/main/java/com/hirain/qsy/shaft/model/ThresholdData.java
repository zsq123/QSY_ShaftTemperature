package com.hirain.qsy.shaft.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ThresholdData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6861136453191591009L;

	private String trainId;

	private String testNum;

	private String parameter1;

	private String parameter2;

	private String parameter3;

}
