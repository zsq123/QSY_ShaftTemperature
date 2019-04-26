package com.hirain.qsy.shaft.service;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
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
	@Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')+(#p2 != null ? #p2.toString() : '')")
	List<ExceptionData> findByTrainInfoAndTime(Integer trainId, Date startDate, Date endDate);

	@CacheEvict(allEntries = true)
	int save(Integer trainId, List<Date> acquisitionTimeList, String exceptionDataString);// 批量插入

	@CacheEvict(allEntries = true)
	int savenolist(Integer trainId, String exceptionDataString);

	@Cacheable(key = "#p0.toString() ")
	List<ExceptionData> findStatisticsResultByCar(StatisticsRequest statisticsRequest, List<String> trainNums);

	@Cacheable(key = "#p0.toString() ")
	List<ExceptionData> findStatisticsResultByPoint(DataRequest dataRequest);

}
