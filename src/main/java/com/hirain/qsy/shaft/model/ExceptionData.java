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
	private static final long serialVersionUID = 2625890283301228229L;

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

	public ExceptionData() {
	}
	//
	// public Long getId() {
	// return id;
	// }
	//
	// public void setId(Long id) {
	// this.id = id;
	// }
	//
	// public Integer getTrainId() {
	// return trainId;
	// }
	//
	// public void setTrainId(Integer trainId) {
	// this.trainId = trainId;
	// }
	//
	// public Date getAcquisitionTime() {
	// return acquisitionTime;
	// }
	//
	// public void setAcquisitionTime(Date acquisitionTime) {
	// this.acquisitionTime = acquisitionTime;
	// }
	//
	// public Date getCreateTime() {
	// return createTime;
	// }
	//
	// public void setCreateTime(Date createTime) {
	// this.createTime = createTime;
	// }
	//
	// public String getResultAxle11() {
	// return resultAxle11;
	// }
	//
	// public void setResultAxle11(String resultAxle11) {
	// this.resultAxle11 = resultAxle11 == null ? null : resultAxle11.trim();
	// }
	//
	// public String getResultAxle12() {
	// return resultAxle12;
	// }
	//
	// public void setResultAxle12(String resultAxle12) {
	// this.resultAxle12 = resultAxle12 == null ? null : resultAxle12.trim();
	// }
	//
	// public String getResultAxle13() {
	// return resultAxle13;
	// }
	//
	// public void setResultAxle13(String resultAxle13) {
	// this.resultAxle13 = resultAxle13 == null ? null : resultAxle13.trim();
	// }
	//
	// public String getResultAxle14() {
	// return resultAxle14;
	// }
	//
	// public void setResultAxle14(String resultAxle14) {
	// this.resultAxle14 = resultAxle14 == null ? null : resultAxle14.trim();
	// }
	//
	// public String getResultAxle15() {
	// return resultAxle15;
	// }
	//
	// public void setResultAxle15(String resultAxle15) {
	// this.resultAxle15 = resultAxle15 == null ? null : resultAxle15.trim();
	// }
	//
	// public String getResultAxle21() {
	// return resultAxle21;
	// }
	//
	// public void setResultAxle21(String resultAxle21) {
	// this.resultAxle21 = resultAxle21 == null ? null : resultAxle21.trim();
	// }
	//
	// public String getResultAxle22() {
	// return resultAxle22;
	// }
	//
	// public void setResultAxle22(String resultAxle22) {
	// this.resultAxle22 = resultAxle22 == null ? null : resultAxle22.trim();
	// }
	//
	// public String getResultAxle23() {
	// return resultAxle23;
	// }
	//
	// public void setResultAxle23(String resultAxle23) {
	// this.resultAxle23 = resultAxle23 == null ? null : resultAxle23.trim();
	// }
	//
	// public String getResultAxle24() {
	// return resultAxle24;
	// }
	//
	// public void setResultAxle24(String resultAxle24) {
	// this.resultAxle24 = resultAxle24 == null ? null : resultAxle24.trim();
	// }
	//
	// public String getResultAxle25() {
	// return resultAxle25;
	// }
	//
	// public void setResultAxle25(String resultAxle25) {
	// this.resultAxle25 = resultAxle25 == null ? null : resultAxle25.trim();
	// }
	//
	// public String getResultAxle31() {
	// return resultAxle31;
	// }
	//
	// public void setResultAxle31(String resultAxle31) {
	// this.resultAxle31 = resultAxle31 == null ? null : resultAxle31.trim();
	// }
	//
	// public String getResultAxle32() {
	// return resultAxle32;
	// }
	//
	// public void setResultAxle32(String resultAxle32) {
	// this.resultAxle32 = resultAxle32 == null ? null : resultAxle32.trim();
	// }
	//
	// public String getResultAxle33() {
	// return resultAxle33;
	// }
	//
	// public void setResultAxle33(String resultAxle33) {
	// this.resultAxle33 = resultAxle33 == null ? null : resultAxle33.trim();
	// }
	//
	// public String getResultAxle34() {
	// return resultAxle34;
	// }
	//
	// public void setResultAxle34(String resultAxle34) {
	// this.resultAxle34 = resultAxle34 == null ? null : resultAxle34.trim();
	// }
	//
	// public String getResultAxle35() {
	// return resultAxle35;
	// }
	//
	// public void setResultAxle35(String resultAxle35) {
	// this.resultAxle35 = resultAxle35 == null ? null : resultAxle35.trim();
	// }
	//
	// public String getResultAxle41() {
	// return resultAxle41;
	// }
	//
	// public void setResultAxle41(String resultAxle41) {
	// this.resultAxle41 = resultAxle41 == null ? null : resultAxle41.trim();
	// }
	//
	// public String getResultAxle42() {
	// return resultAxle42;
	// }
	//
	// public void setResultAxle42(String resultAxle42) {
	// this.resultAxle42 = resultAxle42 == null ? null : resultAxle42.trim();
	// }
	//
	// public String getResultAxle43() {
	// return resultAxle43;
	// }
	//
	// public void setResultAxle43(String resultAxle43) {
	// this.resultAxle43 = resultAxle43 == null ? null : resultAxle43.trim();
	// }
	//
	// public String getResultAxle44() {
	// return resultAxle44;
	// }
	//
	// public void setResultAxle44(String resultAxle44) {
	// this.resultAxle44 = resultAxle44 == null ? null : resultAxle44.trim();
	// }
	//
	// public String getResultAxle45() {
	// return resultAxle45;
	// }
	//
	// public void setResultAxle45(String resultAxle45) {
	// this.resultAxle45 = resultAxle45 == null ? null : resultAxle45.trim();
	// }
	//
	// public String getResultAxle51() {
	// return resultAxle51;
	// }
	//
	// public void setResultAxle51(String resultAxle51) {
	// this.resultAxle51 = resultAxle51 == null ? null : resultAxle51.trim();
	// }
	//
	// public String getResultAxle52() {
	// return resultAxle52;
	// }
	//
	// public void setResultAxle52(String resultAxle52) {
	// this.resultAxle52 = resultAxle52 == null ? null : resultAxle52.trim();
	// }
	//
	// public String getResultAxle53() {
	// return resultAxle53;
	// }
	//
	// public void setResultAxle53(String resultAxle53) {
	// this.resultAxle53 = resultAxle53 == null ? null : resultAxle53.trim();
	// }
	//
	// public String getResultAxle54() {
	// return resultAxle54;
	// }
	//
	// public void setResultAxle54(String resultAxle54) {
	// this.resultAxle54 = resultAxle54 == null ? null : resultAxle54.trim();
	// }
	//
	// public String getResultAxle55() {
	// return resultAxle55;
	// }
	//
	// public void setResultAxle55(String resultAxle55) {
	// this.resultAxle55 = resultAxle55 == null ? null : resultAxle55.trim();
	// }
	//
	// public String getResultAxle61() {
	// return resultAxle61;
	// }
	//
	// public void setResultAxle61(String resultAxle61) {
	// this.resultAxle61 = resultAxle61 == null ? null : resultAxle61.trim();
	// }
	//
	// public String getResultAxle62() {
	// return resultAxle62;
	// }
	//
	// public void setResultAxle62(String resultAxle62) {
	// this.resultAxle62 = resultAxle62 == null ? null : resultAxle62.trim();
	// }
	//
	// public String getResultAxle63() {
	// return resultAxle63;
	// }
	//
	// public void setResultAxle63(String resultAxle63) {
	// this.resultAxle63 = resultAxle63 == null ? null : resultAxle63.trim();
	// }
	//
	// public String getResultAxle64() {
	// return resultAxle64;
	// }
	//
	// public void setResultAxle64(String resultAxle64) {
	// this.resultAxle64 = resultAxle64 == null ? null : resultAxle64.trim();
	// }
	//
	// public String getResultAxle65() {
	// return resultAxle65;
	// }
	//
	// public void setResultAxle65(String resultAxle65) {
	// this.resultAxle65 = resultAxle65 == null ? null : resultAxle65.trim();
	// }

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