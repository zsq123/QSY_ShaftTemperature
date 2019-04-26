package com.hirain.qsy.shaft.service;

import java.util.List;
import java.util.Map;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.DetailDataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.model.StatisticsExceptionTableDatas;

public interface StatisticsService {

	/**
	 * 获取统计结果按车辆层级
	 * 
	 * @param statisticsRequest
	 * @return
	 */
	List<StatisticsChartDataRow> findStatisticsResultByCar(StatisticsRequest statisticsRequest);

	/**
	 * 获取统计结果按测点层级
	 * 
	 * @param dataRequest
	 * @return
	 */
	List<StatisticsChartDataRow> findStatisticsResultByPoint(DataRequest dataRequest);

	List<StatisticsExceptionTableDatas> findTableData(DataRequest dataRequest, Integer axleNum);

	List<Map<String, Object>> findDetailData(DetailDataRequest detailDataRequest);

}
