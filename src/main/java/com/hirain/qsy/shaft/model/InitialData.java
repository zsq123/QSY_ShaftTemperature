package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
@Table(name = "t_initial_data")
public class InitialData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	@Column(name="train_id")
	private Integer trainId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
	@Column(name="acquisition_time")
	private Date acquisitionTime;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
	@Column(name="create_time")
	private Date createTime;

	@Column(name = "ambient_temperature_1")
	private Float ambientTemperature1;

	@Column(name = "ambient_temperature_2")
	private Float ambientTemperature2;
	
	@Column(name = "generator_temperature")
	private Float generatorTemperature;

	@Column(name = "fan1_temperature")
	private Float fan1Temperature;

	@Column(name = "fan2_temperature")
	private Float fan2Temperature;

	@Column(name = "axle1_1")
	private Float axle11;

	@Column(name = "axle1_2")
	private Float axle12;

	@Column(name = "axle1_3")
	private Float axle13;

	@Column(name = "axle1_4")
	private Float axle14;

	@Column(name = "axle1_5")
	private Float axle15;
	
	@Column(name = "axle1_6")
	private Float axle16;

	@Column(name = "axle2_1")
	private Float axle21;

	@Column(name = "axle2_2")
	private Float axle22;

	@Column(name = "axle2_3")
	private Float axle23;

	@Column(name = "axle2_4")
	private Float axle24;

	@Column(name = "axle2_5")
	private Float axle25;
	
	@Column(name = "axle2_6")
	private Float axle26;

	@Column(name = "axle3_1")
	private Float axle31;

	@Column(name = "axle3_2")
	private Float axle32;

	@Column(name = "axle3_3")
	private Float axle33;

	@Column(name = "axle3_4")
	private Float axle34;

	@Column(name = "axle3_5")
	private Float axle35;
	
	@Column(name = "axle3_6")
	private Float axle36;

	@Column(name = "axle4_1")
	private Float axle41;

	@Column(name = "axle4_2")
	private Float axle42;

	@Column(name = "axle4_3")
	private Float axle43;

	@Column(name = "axle4_4")
	private Float axle44;

	@Column(name = "axle4_5")
	private Float axle45;
	
	@Column(name = "axle4_6")
	private Float axle46;

	@Column(name = "axle5_1")
	private Float axle51;

	@Column(name = "axle5_2")
	private Float axle52;

	@Column(name = "axle5_3")
	private Float axle53;

	@Column(name = "axle5_4")
	private Float axle54;

	@Column(name = "axle5_5")
	private Float axle55;
	
	@Column(name = "axle5_6")
	private Float axle56;

	@Column(name = "axle6_1")
	private Float axle61;

	@Column(name = "axle6_2")
	private Float axle62;

	@Column(name = "axle6_3")
	private Float axle63;

	@Column(name = "axle6_4")
	private Float axle64;

	@Column(name = "axle6_5")
	private Float axle65;
	
	@Column(name = "axle6_6")
	private Float axle66;

	@Column(name = "max_temp")
	private Float maxTemp;

	@Column(name = "max_temp_point")
	private Float maxTempPoint;

	@Column(name = "alarm_point")
	private Short alarmPoint;

	@Column(name = "alarm_sign")
	private String alarmSign;

	@Column(name = "alarm_code")
	private String alarmCode;

	@Column(name = "alarm_time")
	private Date alarmTime;

	@Column(name = "alarm_point_temp")
	private Float alarmPointTemp;

	@Column(name = "gps_longitude")
	private Float gpsLongitude;
	
	@Column(name = "gps_latitude")
	private Float gpsLatitude;

	@Column(name = "gps_speed")
	private Integer gpsSpeed;
	
	@Column(name = "original_primary_key")
	private String originalprimarykey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTrainId() {
		return trainId;
	}

	public void setTrainId(Integer trainId) {
		this.trainId = trainId;
	}

	public Date getAcquisitionTime() {
		return acquisitionTime;
	}

	public void setAcquisitionTime(Date acquisitionTime) {
		this.acquisitionTime = acquisitionTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getAmbientTemperature1() {
		return ambientTemperature1;
	}

	public void setAmbientTemperature1(Float ambientTemperature1) {
		this.ambientTemperature1 = ambientTemperature1;
	}

	public Float getAmbientTemperature2() {
		return ambientTemperature2;
	}

	public void setAmbientTemperature2(Float ambientTemperature2) {
		this.ambientTemperature2 = ambientTemperature2;
	}

	public Float getGeneratorTemperature() {
		return generatorTemperature;
	}

	public void setGeneratorTemperature(Float generatorTemperature) {
		this.generatorTemperature = generatorTemperature;
	}

	public Float getFan1Temperature() {
		return fan1Temperature;
	}

	public void setFan1Temperature(Float fan1Temperature) {
		this.fan1Temperature = fan1Temperature;
	}

	public Float getFan2Temperature() {
		return fan2Temperature;
	}

	public void setFan2Temperature(Float fan2Temperature) {
		this.fan2Temperature = fan2Temperature;
	}

	public Float getAxle11() {
		return axle11;
	}

	public void setAxle11(Float axle11) {
		this.axle11 = axle11;
	}

	public Float getAxle12() {
		return axle12;
	}

	public void setAxle12(Float axle12) {
		this.axle12 = axle12;
	}

	public Float getAxle13() {
		return axle13;
	}

	public void setAxle13(Float axle13) {
		this.axle13 = axle13;
	}

	public Float getAxle14() {
		return axle14;
	}

	public void setAxle14(Float axle14) {
		this.axle14 = axle14;
	}

	public Float getAxle15() {
		return axle15;
	}

	public void setAxle15(Float axle15) {
		this.axle15 = axle15;
	}

	public Float getAxle21() {
		return axle21;
	}

	public void setAxle21(Float axle21) {
		this.axle21 = axle21;
	}

	public Float getAxle22() {
		return axle22;
	}

	public void setAxle22(Float axle22) {
		this.axle22 = axle22;
	}

	public Float getAxle23() {
		return axle23;
	}

	public void setAxle23(Float axle23) {
		this.axle23 = axle23;
	}

	public Float getAxle24() {
		return axle24;
	}

	public void setAxle24(Float axle24) {
		this.axle24 = axle24;
	}

	public Float getAxle25() {
		return axle25;
	}

	public void setAxle25(Float axle25) {
		this.axle25 = axle25;
	}

	public Float getAxle31() {
		return axle31;
	}

	public void setAxle31(Float axle31) {
		this.axle31 = axle31;
	}

	public Float getAxle32() {
		return axle32;
	}

	public void setAxle32(Float axle32) {
		this.axle32 = axle32;
	}

	public Float getAxle33() {
		return axle33;
	}

	public void setAxle33(Float axle33) {
		this.axle33 = axle33;
	}

	public Float getAxle34() {
		return axle34;
	}

	public void setAxle34(Float axle34) {
		this.axle34 = axle34;
	}

	public Float getAxle35() {
		return axle35;
	}

	public void setAxle35(Float axle35) {
		this.axle35 = axle35;
	}

	public Float getAxle41() {
		return axle41;
	}

	public void setAxle41(Float axle41) {
		this.axle41 = axle41;
	}

	public Float getAxle42() {
		return axle42;
	}

	public void setAxle42(Float axle42) {
		this.axle42 = axle42;
	}

	public Float getAxle43() {
		return axle43;
	}

	public void setAxle43(Float axle43) {
		this.axle43 = axle43;
	}

	public Float getAxle44() {
		return axle44;
	}

	public void setAxle44(Float axle44) {
		this.axle44 = axle44;
	}

	public Float getAxle45() {
		return axle45;
	}

	public void setAxle45(Float axle45) {
		this.axle45 = axle45;
	}

	public Float getAxle51() {
		return axle51;
	}

	public void setAxle51(Float axle51) {
		this.axle51 = axle51;
	}

	public Float getAxle52() {
		return axle52;
	}

	public void setAxle52(Float axle52) {
		this.axle52 = axle52;
	}

	public Float getAxle53() {
		return axle53;
	}

	public void setAxle53(Float axle53) {
		this.axle53 = axle53;
	}

	public Float getAxle54() {
		return axle54;
	}

	public void setAxle54(Float axle54) {
		this.axle54 = axle54;
	}

	public Float getAxle55() {
		return axle55;
	}

	public void setAxle55(Float axle55) {
		this.axle55 = axle55;
	}

	public Float getAxle61() {
		return axle61;
	}

	public void setAxle61(Float axle61) {
		this.axle61 = axle61;
	}

	public Float getAxle62() {
		return axle62;
	}

	public void setAxle62(Float axle62) {
		this.axle62 = axle62;
	}

	public Float getAxle63() {
		return axle63;
	}

	public void setAxle63(Float axle63) {
		this.axle63 = axle63;
	}

	public Float getAxle64() {
		return axle64;
	}

	public void setAxle64(Float axle64) {
		this.axle64 = axle64;
	}

	public Float getAxle65() {
		return axle65;
	}

	public void setAxle65(Float axle65) {
		this.axle65 = axle65;
	}

	public Float getMaxTemp() {
		return maxTemp;
	}

	public void setMaxTemp(Float maxTemp) {
		this.maxTemp = maxTemp;
	}

	public Float getMaxTempPoint() {
		return maxTempPoint;
	}

	public void setMaxTempPoint(Float maxTempPoint) {
		this.maxTempPoint = maxTempPoint;
	}

	public Short getAlarmPoint() {
		return alarmPoint;
	}

	public void setAlarmPoint(Short alarmPoint) {
		this.alarmPoint = alarmPoint;
	}

	public String getAlarmSign() {
		return alarmSign;
	}

	public void setAlarmSign(String alarmSign) {
		this.alarmSign = alarmSign == null ? null : alarmSign.trim();
	}

	public String getAlarmCode() {
		return alarmCode;
	}

	public void setAlarmCode(String alarmCode) {
		this.alarmCode = alarmCode == null ? null : alarmCode.trim();
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public Float getAlarmPointTemp() {
		return alarmPointTemp;
	}

	public void setAlarmPointTemp(Float alarmPointTemp) {
		this.alarmPointTemp = alarmPointTemp;
	}

	public Float getGpsLongitude() {
		return gpsLongitude;
	}

	public void setGpsLongitude(Float gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public Float getGpsLatitude() {
		return gpsLatitude;
	}

	public void setGpsLatitude(Float gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public Integer getGpsSpeed() {
		return gpsSpeed;
	}

	public void setGpsSpeed(Integer gpsSpeed) {
		this.gpsSpeed = gpsSpeed;
	}
}