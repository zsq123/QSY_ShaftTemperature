package com.hirain.qsy.shaft.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hirain.qsy.shaft.handler.BatchInitialDataHandler;
import com.hirain.qsy.shaft.model.InitialData;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

@org.apache.ibatis.annotations.Mapper
public interface InitialDataMapper extends Mapper<InitialData>, InsertListMapper<InitialData> {

	/**
	 * 查询历史数据
	 * 
	 * @param train_id
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@Select("select ID,train_id,acquisition_time,create_time,Avg(ambient_temperature_1) AS ambient_temperature_1,Avg(ambient_temperature_2) AS ambient_temperature_2,"
			+ "generator_temperature,fan1_temperature,fan2_temperature,Avg(axle1_1) AS axle1_1,Avg(axle1_2) AS axle1_2,Avg(axle1_3) AS axle1_3,"
			+ "Avg(axle1_4) AS axle1_4,Avg(axle1_5) AS axle1_5,Avg(axle1_6) AS axle1_6,Avg(axle2_1) AS axle2_1,Avg(axle2_2) AS axle2_2,"
			+ "Avg(axle2_3) AS axle2_3,Avg(axle2_4) AS axle2_4,Avg(axle2_5) AS axle2_5,Avg(axle2_6) AS axle2_6,Avg(axle3_1) AS axle3_1,"
			+ "Avg(axle3_2) AS axle3_2,Avg(axle3_3) AS axle3_3,Avg(axle3_4) AS axle3_4,Avg(axle3_5) AS axle3_5,Avg(axle3_6) AS axle3_6,"
			+ "Avg(axle4_1) AS axle4_1,Avg(axle4_2) AS axle4_2,Avg(axle4_3) AS axle4_3,Avg(axle4_4) AS axle4_4,"
			+ "Avg(axle4_5) AS axle4_5,Avg(axle4_6) AS axle4_6,Avg(axle5_1) AS axle5_1,Avg(axle5_2) AS axle5_2,Avg(axle5_3) AS axle5_3,"
			+ "Avg(axle5_4) AS axle5_4,Avg(axle5_5) AS axle5_5,Avg(axle5_6) AS axle5_6,Avg(axle6_1) AS axle6_1,Avg(axle6_2) AS axle6_2,"
			+ "Avg(axle6_3) AS axle6_3,Avg(axle6_4) AS axle6_4,Avg(axle6_5) AS axle6_5,Avg(axle6_6) AS axle6_6,max_temp,"
			+ "max_temp_point,alarm_point,alarm_sign,alarm_code,alarm_time,"
			+ "alarm_point_temp,gps_longitude,gps_latitude,Avg(gps_speed) AS gps_speed FROM "
			+ "t_initial_data WHERE train_id = #{train_id} and acquisition_time BETWEEN #{beginTime} AND #{endTime} GROUP BY acquisition_time ORDER BY acquisition_time")
	List<InitialData> avgOfAxleData(@Param("train_id") Integer train_id, @Param("beginTime") String beginTime, @Param("endTime") String endTime);

	/**
	 * 批量插入同时处理重复数据
	 * 
	 * @param initialDatas
	 * @return
	 */
	@InsertProvider(type = BatchInitialDataHandler.class, method = "batDataAdd")
	Integer batInitialAdd(List<InitialData> initialDatas);

	List<InitialData> findByParams(Long trainId, Date starttime, Date endtime);
}