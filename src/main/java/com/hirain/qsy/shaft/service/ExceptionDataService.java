package com.hirain.qsy.shaft.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.model.AxleExceptionData;
import com.hirain.qsy.shaft.model.AxleExceptionStateData;
import com.hirain.qsy.shaft.model.ExceptionData;

@CacheConfig(cacheNames = "ExceptionDataService")
public interface ExceptionDataService extends IService<ExceptionData> {

	/**
	 * find exceptionData by TrainId between StartTime and endTime
	 * *************DateFormat yyyy-MM-dd HH:mm:ss********************
	 * 
	 * @param trainId
	 *            Front transfer TrainId directly
	 * @param startDate
	 *            default now - 7days ****front set****
	 * @param endDate
	 *            default now ******front set********
	 * @return
	 */
	List<ExceptionData> findByTrainInfoAndTime(Integer trainId, String startDate, String endDate);

	// int savenolist(Integer trainId, String exceptionDataString);

	Set<AxleExceptionStateData> analyseExceptionData(Integer trainId, String startDate, String endDate);

	boolean isException(String exception);

	List<AxleExceptionData> findAxleExceptionData(Integer trainId, String startDate, String endDate, String axleName);

	@CacheEvict(allEntries = true)
	int save(Integer trainId, List<Date> acquisitionTimeList, String exceptionDataString);// 批量插入

	@Cacheable(key = "#p0.toString() ")
	List<ExceptionData> findStatisticsResultByCar(StatisticsRequest statisticsRequest, List<String> trainNums);

	@Cacheable(key = "#p0.toString() ")
	List<ExceptionData> findStatisticsResultByPoint(DataRequest dataRequest);

}
