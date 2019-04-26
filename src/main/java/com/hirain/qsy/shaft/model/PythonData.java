package com.hirain.qsy.shaft.model;

import java.util.List;

public class PythonData {

	private List<String> tempratureOnPoint;

	private List<String> gpsSpeed;

	private List<String> firAmbientTemperature;

	private List<String> secAmbientTemperature;

	public List<String> getTempratureOnPoint() {

		return tempratureOnPoint;
	}

	public void setTempratureOnPoint(List<String> tempratureOnPoint) {

		this.tempratureOnPoint = tempratureOnPoint;
	}

	public List<String> getGpsSpeed() {

		return gpsSpeed;
	}

	public void setGpsSpeed(List<String> gpsSpeed) {

		this.gpsSpeed = gpsSpeed;
	}

	public List<String> getFirAmbientTemperature() {

		return firAmbientTemperature;
	}

	public void setFirAmbientTemperature(List<String> firAmbientTemperature) {

		this.firAmbientTemperature = firAmbientTemperature;
	}

	public List<String> getSecAmbientTemperature() {

		return secAmbientTemperature;
	}

	public void setSecAmbientTemperature(List<String> secAmbientTemperature) {

		this.secAmbientTemperature = secAmbientTemperature;
	}

}
