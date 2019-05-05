package com.hirain.qsy.shaft.dao;

import java.util.Date;
import java.util.List;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.ExceptionData;

public interface ExceptionDataMapper extends MyMapper<ExceptionData> {

	List<ExceptionData> findStatisticsResult(List<String> trainNums, Date startTime, Date endTime);
}