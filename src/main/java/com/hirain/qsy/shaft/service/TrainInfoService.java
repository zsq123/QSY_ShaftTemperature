package com.hirain.qsy.shaft.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.model.TrainInfo;

@CacheConfig(cacheNames = "TrainInfoService")
public interface TrainInfoService extends IService<TrainInfo> {

	/**
	 * find train by primary key
	 * 
	 * @param tranId
	 * @return
	 */
	TrainInfo findById(Integer tranId);

	@Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')")
	List<TrainInfo> findList(QueryRequest request, DataRequest dataRequest);

	List<TrainInfo> findAllGroupTrainType();

	List<TrainInfo> findTrainInfoByType(String trainType);
}
