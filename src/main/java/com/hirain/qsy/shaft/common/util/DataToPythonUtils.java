package com.hirain.qsy.shaft.common.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.model.PythonData;

public class DataToPythonUtils extends GlobVariableConfig {

	/**
	 * 返回json数据
	 * @param excelData 传入excel数据
	 * @return
	 */
	public String objectToJson(List<List<Object>> excelData) {

		return JSON.toJSONString(analysisData(excelData));
	}

	/**
	 *   获取所有温度的数据
	 * @param excelData
	 * @return
	 */
	private List<PythonData> analysisData(List<List<Object>> excelData) {
		/*
		 * String title[]= {"1轴测试点1温度-℃" ,"1轴测试点2温度-℃" ,"1轴测试点3温度-℃" ,"1轴测试点4温度-℃"
		 * ,"1轴测试点5温度-℃" , "2轴测试点1温度-℃" ,"2轴测试点2温度-℃" ,"2轴测试点3温度-℃" ,"2轴测试点4温度-℃"
		 * ,"2轴测试点5温度-℃" , "3轴测试点1温度-℃" ,"3轴测试点2温度-℃" ,"3轴测试点3温度-℃" ,"3轴测试点4温度-℃"
		 * ,"3轴测试点5温度-℃" , "4轴测试点1温度-℃" ,"4轴测试点2温度-℃" ,"4轴测试点3温度-℃" ,"4轴测试点4温度-℃"
		 * ,"4轴测试点5温度-℃" , "5轴测试点1温度-℃" ,"5轴测试点2温度-℃" ,"5轴测试点3温度-℃" ,"5轴测试点4温度-℃"
		 * ,"5轴测试点5温度-℃" , "6轴测试点1温度-℃" ,"6轴测试点2温度-℃" ,"6轴测试点3温度-℃" ,"6轴测试点4温度-℃"
		 * ,"6轴测试点5温度-℃"};
		 */

		List<String> tempratureOnPoint;// 测点温度
		List<String> gpsSpeed;// gps速度
		List<String> firAmbientTemperature;// 环境一温度
		List<String> secAmbientTemperature;// 环境二温度
		List<PythonData> listPythonData = new ArrayList<>();// 存储PythonData对象的数据

		// 获取gps,环境温度1和环境温度2的index.
		int gpsTitleIndex = excelData.get(0).indexOf("GPS速度");
		int firTempIndex = excelData.get(0).indexOf("环温1-℃");
		int secTempIndex = excelData.get(0).indexOf("环温2-℃");

		if (excelData != null) {
			for (int index = 0; index < title.length; index++) {
				PythonData pData = new PythonData();
				tempratureOnPoint = new ArrayList<>();
				gpsSpeed = new ArrayList<>();
				firAmbientTemperature = new ArrayList<>();
				secAmbientTemperature = new ArrayList<>();
				int titleIndex = excelData.get(0).indexOf(title[index]);
				for (int rIndex = 1; rIndex < excelData.size(); rIndex++) {
					tempratureOnPoint.add(excelData.get(rIndex).get(titleIndex).toString());
					gpsSpeed.add(excelData.get(rIndex).get(gpsTitleIndex).toString());
					firAmbientTemperature.add(excelData.get(rIndex).get(firTempIndex).toString());
					secAmbientTemperature.add(excelData.get(rIndex).get(secTempIndex).toString());
				}

				pData.setTempratureOnPoint(tempratureOnPoint);
				pData.setGpsSpeed(gpsSpeed);
				pData.setFirAmbientTemperature(firAmbientTemperature);
				pData.setSecAmbientTemperature(secAmbientTemperature);
				listPythonData.add(pData);
			}
		}

		return listPythonData;
	}

}
