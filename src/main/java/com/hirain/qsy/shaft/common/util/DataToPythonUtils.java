package com.hirain.qsy.shaft.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.model.PythonData;

public class DataToPythonUtils extends GlobVariableConfig {

	/**
	 * 返回json数据
	 * 
	 * @param excelData 传入excel数据
	 * @return
	 */
	public String objectToPythonJson(List<List<Object>> excelData) {

		return JSON.toJSONString(excelDataToPythonData(excelData));
	}

	/**
	 * 获取所有温度的数据
	 * 
	 * @param excelData
	 * @return
	 */
	private PythonData excelDataToPythonData(List<List<Object>> excelData) {
		/*
		 * String title[]= {"1轴测试点1温度-℃" ,"1轴测试点2温度-℃" ,"1轴测试点3温度-℃" ,"1轴测试点4温度-℃"
		 * ,"1轴测试点5温度-℃" , "2轴测试点1温度-℃" ,"2轴测试点2温度-℃" ,"2轴测试点3温度-℃" ,"2轴测试点4温度-℃"
		 * ,"2轴测试点5温度-℃" , "3轴测试点1温度-℃" ,"3轴测试点2温度-℃" ,"3轴测试点3温度-℃" ,"3轴测试点4温度-℃"
		 * ,"3轴测试点5温度-℃" , "4轴测试点1温度-℃" ,"4轴测试点2温度-℃" ,"4轴测试点3温度-℃" ,"4轴测试点4温度-℃"
		 * ,"4轴测试点5温度-℃" , "5轴测试点1温度-℃" ,"5轴测试点2温度-℃" ,"5轴测试点3温度-℃" ,"5轴测试点4温度-℃"
		 * ,"5轴测试点5温度-℃" , "6轴测试点1温度-℃" ,"6轴测试点2温度-℃" ,"6轴测试点3温度-℃" ,"6轴测试点4温度-℃"
		 * ,"6轴测试点5温度-℃"};
		 */
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PythonData pData = null;
		List<String> tempratureOnPoint;// 测点温度
		List<String> trainId = new ArrayList<>();// 机车号
		List<Date> acquisitionTime = new ArrayList<>();// 采集时间
		List<String> gpsSpeed = new ArrayList<>();// gps速度

		List<List<String>> ambientTemperaturesList = new ArrayList<>();
		// List<PythonData> listPythonData = new ArrayList<>();// 存储PythonData对象的数据
		if (excelData.size() > 0) {

			try {// 获取gps,环境温度1和环境温度2的index.

				int gpsTitleIndex = excelData.get(0).indexOf("GPS速度");
				int firTempIndex = excelData.get(0).indexOf("环温1-℃");
				int secTempIndex = excelData.get(0).indexOf("环温2-℃");
				int trainIdIndex = excelData.get(0).indexOf("机车车号");
				int acquisitionTimeIndex = excelData.get(0).indexOf("采集时间");
				pData = new PythonData();
				for (int eIndex = 1; eIndex < excelData.size(); eIndex++) {
					List<String> ambientTemperature = new ArrayList<>();// 环境温度
					List<Object> lists = excelData.get(eIndex);
					trainId.add(lists.get(trainIdIndex).toString());
					acquisitionTime.add(sdfDateFormat.parse(lists.get(acquisitionTimeIndex).toString()));
					gpsSpeed.add(lists.get(gpsTitleIndex).toString());
					ambientTemperature.add(lists.get(firTempIndex).toString());
					ambientTemperature.add(lists.get(secTempIndex).toString());
					ambientTemperaturesList.add(ambientTemperature);
				}
				pData.setTrainid(trainId);
				pData.setAcquisitiontime(acquisitionTime);
				pData.setGpsSpeed(gpsSpeed);
				pData.setAmbientTemperature(ambientTemperaturesList);

				if (excelData != null) {
					for (int index = 0; index < title.length; index++) {
						tempratureOnPoint = new ArrayList<>();
						int titleIndex = excelData.get(0).indexOf(title[index]);
						for (int rIndex = 1; rIndex < excelData.size(); rIndex++) {
							tempratureOnPoint.add(excelData.get(rIndex).get(titleIndex).toString());
						}
						String proName = "tempratureOnPoint" + (index / 6 + 1) + (index - (index / 6) * 6 + 1);
						BeanUtils.setProperty(pData, proName, tempratureOnPoint);

						// pData.setTempratureOnPoint(tempratureOnPoint);
						// listPythonData.add(pData);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return pData;
	}

}
