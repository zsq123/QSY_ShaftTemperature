package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Table(name = "t_exception_data")
public class ExceptionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5764085701843139574L;

	private Long id;

	private Integer trainId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date acquisitionTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;

	@Column(name = "result_axle1_1")
	private String resultAxle11;

	@Column(name = "result_axle1_2")
	private String resultAxle12;

	@Column(name = "result_axle1_3")
	private String resultAxle13;

	@Column(name = "result_axle1_4")
	private String resultAxle14;

	@Column(name = "result_axle1_5")
	private String resultAxle15;

	@Column(name = "result_axle1_6")
	private String resultAxle16;

	@Column(name = "result_axle2_1")
	private String resultAxle21;

	@Column(name = "result_axle2_2")
	private String resultAxle22;

	@Column(name = "result_axle2_3")
	private String resultAxle23;

	@Column(name = "result_axle2_4")
	private String resultAxle24;

	@Column(name = "result_axle2_5")
	private String resultAxle25;

	@Column(name = "result_axle2_6")
	private String resultAxle26;

	@Column(name = "result_axle3_1")
	private String resultAxle31;

	@Column(name = "result_axle3_2")
	private String resultAxle32;

	@Column(name = "result_axle3_3")
	private String resultAxle33;

	@Column(name = "result_axle3_4")
	private String resultAxle34;

	@Column(name = "result_axle3_5")
	private String resultAxle35;

	@Column(name = "result_axle3_6")
	private String resultAxle36;

	@Column(name = "result_axle4_1")
	private String resultAxle41;

	@Column(name = "result_axle4_2")
	private String resultAxle42;

	@Column(name = "result_axle4_3")
	private String resultAxle43;

	@Column(name = "result_axle4_4")
	private String resultAxle44;

	@Column(name = "result_axle4_5")
	private String resultAxle45;

	@Column(name = "result_axle4_6")
	private String resultAxle46;

	@Column(name = "result_axle5_1")
	private String resultAxle51;

	@Column(name = "result_axle5_2")
	private String resultAxle52;

	@Column(name = "result_axle5_3")
	private String resultAxle53;

	@Column(name = "result_axle5_4")
	private String resultAxle54;

	@Column(name = "result_axle5_5")
	private String resultAxle55;

	@Column(name = "result_axle5_6")
	private String resultAxle56;

	@Column(name = "result_axle6_1")
	private String resultAxle61;

	@Column(name = "result_axle6_2")
	private String resultAxle62;

	@Column(name = "result_axle6_3")
	private String resultAxle63;

	@Column(name = "result_axle6_4")
	private String resultAxle64;

	@Column(name = "result_axle6_5")
	private String resultAxle65;

	@Column(name = "result_axle6_6")
	private String resultAxle66;

	public ExceptionData() {
	}

	public ExceptionData(Integer trainId, Date acquisitionTime, Date createTime, String[] resultAxles) {
		this.trainId = trainId;
		this.acquisitionTime = acquisitionTime;
		this.createTime = createTime;
		this.resultAxle11 = resultAxles[0];
		this.resultAxle12 = resultAxles[1];
		this.resultAxle13 = resultAxles[2];
		this.resultAxle14 = resultAxles[3];
		this.resultAxle15 = resultAxles[4];
		this.resultAxle21 = resultAxles[5];
		this.resultAxle22 = resultAxles[6];
		this.resultAxle23 = resultAxles[7];
		this.resultAxle24 = resultAxles[8];
		this.resultAxle25 = resultAxles[9];
		this.resultAxle31 = resultAxles[10];
		this.resultAxle32 = resultAxles[11];
		this.resultAxle33 = resultAxles[12];
		this.resultAxle34 = resultAxles[13];
		this.resultAxle35 = resultAxles[14];
		this.resultAxle41 = resultAxles[15];
		this.resultAxle42 = resultAxles[16];
		this.resultAxle43 = resultAxles[17];
		this.resultAxle44 = resultAxles[18];
		this.resultAxle45 = resultAxles[19];
		this.resultAxle51 = resultAxles[20];
		this.resultAxle52 = resultAxles[21];
		this.resultAxle53 = resultAxles[22];
		this.resultAxle54 = resultAxles[23];
		this.resultAxle55 = resultAxles[24];
		this.resultAxle61 = resultAxles[25];
		this.resultAxle62 = resultAxles[26];
		this.resultAxle63 = resultAxles[27];
		this.resultAxle64 = resultAxles[28];
		this.resultAxle65 = resultAxles[29];
	}
}