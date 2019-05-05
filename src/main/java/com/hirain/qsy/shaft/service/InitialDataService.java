package com.hirain.qsy.shaft.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.model.InitialAxleData;
import com.hirain.qsy.shaft.model.InitialData;

@CacheConfig(cacheNames = "InitialDataService")
public interface InitialDataService extends IService<InitialData> {

	List<InitialData> getInitialData(List<List<Object>> excelData, String mapName, String tClass);

	boolean isNull(List<Object> rows, List<Object> rowhead, Map<String, String> map);

	List<InitialData> initialDatas(String path, String tClass, String mapName, Map<String, String> headerMapper);
	// 将excel数据转化为对象
	// List<InitialData> readBean(List<List<Object>> excelDate,Map<String,String >
	// headerMapper, String tClass);

	// 按trainid和时间查询数据
	List<List<InitialAxleData>> findByTrainInfoAndTime(Integer trainId, String startDate, String endDate);

	List<List<InitialAxleData>> retrieveEveryAxleData(List<InitialData> listdata);

	Map<String, String> getTrainInfos();

	List<List<Object>> getExcelData();

	/**
	 * 批量插入
	 * 
	 * @param listInitialData
	 *            接收到的对象集合
	 * @return
	 */
	@CacheEvict(allEntries = true)
	int save(List<InitialData> listInitialData);// 批量插入

	// 按trainid和时间查询数据
	@Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')+ (#p2 != null ? #p2.toString() : '')")
	List<InitialData> findByTrainInfoAndTime(Long trainId, Date startDate, Date endDate);

	/**
	 * @param initialData
	 *            单条数据
	 * @return
	 */
	@CacheEvict(allEntries = true)
	int save(InitialData initialData);// 单条插入

	@CacheEvict(allEntries = true)
	void deleteByTrainNumAndTime(Long[] trainNums, Date deadline);
}
