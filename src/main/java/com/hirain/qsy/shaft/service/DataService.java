package com.hirain.qsy.shaft.service;

import java.util.Date;
import java.util.Map;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.QueryRequest;

public interface DataService {

	Map<String, Object> getList(QueryRequest request, DataRequest dataRequest);

	void delete(Long[] trainNums, Date deadline);

	/**
	 * 获取服务器存储空间
	 * 
	 * @return
	 */
	Map<String, Object> getStorage();
}
