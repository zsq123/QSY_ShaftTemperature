package com.hirain.qsy.shaft.model;

import java.util.HashMap;
import java.util.Map;


public class AttributeMappingConfigurationData {
	private Map<String, String> mapData;
	
	private Map<String, String> exceptionData;
	
    public AttributeMappingConfigurationData(String name) 	
    {
    	if(name.equals("mapData")) {
    	Map<String,String> map=new HashMap<>();  	
    	map.put("机车车号" ,"trainId");
		map.put("采集时间" ,"acquisitionTime");
		map.put("环温1-℃" ,"ambientTemperature1");
		map.put("环温2-℃" ,"ambientTemperature2");
		map.put("主发电机温度-℃" ,"generatorTemperature");
		map.put("风机1温度-℃" ,"fan1Temperature");
		map.put("风机2温度-℃" ,"fan2Temperature");
		
		map.put("1轴测试点1温度-℃" ,"axle11");
		map.put("1轴测试点2温度-℃" ,"axle12");
		map.put("1轴测试点3温度-℃" ,"axle13");
		map.put("1轴测试点4温度-℃" ,"axle14");
		map.put("1轴测试点5温度-℃" ,"axle15");
		map.put("1轴测试点6温度-℃" ,"axle16");
		
		map.put("2轴测试点1温度-℃" ,"axle21");
		map.put("2轴测试点2温度-℃" ,"axle22");
		map.put("2轴测试点3温度-℃" ,"axle23");
		map.put("2轴测试点4温度-℃" ,"axle24");
		map.put("2轴测试点5温度-℃" ,"axle25");
		map.put("2轴测试点6温度-℃" ,"axle26");
		
		map.put("3轴测试点1温度-℃" ,"axle31");
		map.put("3轴测试点2温度-℃" ,"axle32");
		map.put("3轴测试点3温度-℃" ,"axle33");
		map.put("3轴测试点4温度-℃" ,"axle34");
		map.put("3轴测试点5温度-℃" ,"axle35");
		map.put("3轴测试点6温度-℃" ,"axle36");
		
		map.put("4轴测试点1温度-℃" ,"axle41");
		map.put("4轴测试点2温度-℃" ,"axle42");
		map.put("4轴测试点3温度-℃" ,"axle43");
		map.put("4轴测试点4温度-℃" ,"axle44");
		map.put("4轴测试点5温度-℃" ,"axle45");
		map.put("4轴测试点6温度-℃" ,"axle46");
		
		map.put("5轴测试点1温度-℃" ,"axle51");
		map.put("5轴测试点2温度-℃" ,"axle52");
		map.put("5轴测试点3温度-℃" ,"axle53");
		map.put("5轴测试点4温度-℃" ,"axle54");
		map.put("5轴测试点5温度-℃" ,"axle55");
		map.put("5轴测试点6温度-℃" ,"axle56");
		
		map.put("6轴测试点1温度-℃" ,"axle61");
		map.put("6轴测试点2温度-℃" ,"axle62");
		map.put("6轴测试点3温度-℃" ,"axle63");
		map.put("6轴测试点4温度-℃" ,"axle64");
		map.put("6轴测试点5温度-℃" ,"axle65");
		map.put("6轴测试点6温度-℃" ,"axle66");
			
		map.put("最大温度-℃" ,"maxTemp");
		map.put("最大温度测点号" ,"maxTempPoint");
		map.put("当前报警测点号" ,"alarmPoint");
		map.put("报警代码" ,"alarmCode");
		map.put("当前报警时间" ,"alarmTime");
		map.put("报警时测点温度-℃" ,"alarmPointTemp");
		map.put("GPS经度" ,"gpsLongitude");
		map.put("GPS纬度" ,"gpsLatitude");
		map.put("GPS速度" ,"gpsSpeed");
		map.put("原始表主键" ,"originalprimarykey");
		this.mapData=map;
		}
    	else if(name.equals("exceptionData")) {
    		Map<String,String> map=new HashMap<>();  
    		map.put( "resultAxle11","11");
    		map.put( "resultAxle12","12");
    		map.put( "resultAxle13","13");
    		map.put( "resultAxle14","14");
    		map.put( "resultAxle15","15");
    		map.put( "resultAxle16","16");
    		
    		map.put( "resultAxle21","21");
    		map.put("resultAxle22", "22");
    		map.put( "resultAxle23","23");
    		map.put( "resultAxle24","24");
    		map.put( "resultAxle25","25");
    		map.put( "resultAxle26","26");
    		
    		map.put( "resultAxle31","31");
    		map.put( "resultAxle32","32");
    		map.put( "resultAxle33","33");
    		map.put( "resultAxle34","34");
    		map.put("resultAxle35","35");
    		map.put("resultAxle36","36");
    		
    		map.put("resultAxle41","41");
    		map.put("resultAxle42","42");
    		map.put( "resultAxle43","43");
    		map.put( "resultAxle44","44");
    		map.put( "resultAxle45","45");
    		map.put( "resultAxle46","46");
    		
    		map.put( "resultAxle51","51");
    		map.put( "resultAxle52","52");
    		map.put("resultAxle53","53");
    		map.put( "resultAxle54","54");
    		map.put("resultAxle55","55");
    		map.put("resultAxle56","56");
    		
    		map.put( "resultAxle61","61");
    		map.put( "resultAxle62","62");
    		map.put( "resultAxle63","63");
    		map.put( "resultAxle64","64");
    		map.put( "resultAxle65","65");
    		map.put( "resultAxle66","66");
    		this.exceptionData=map;
    		
    	}
		
		
		
    }
    
	public Map<String, String> getMapData() {
		return mapData;
	}
	
	public Map<String, String> getExcepData(){
		return exceptionData;
	}

	
	
	
}
