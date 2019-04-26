package com.hirain.qsy.shaft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.common.util.ExceptionDataUtils;
import com.hirain.qsy.shaft.dao.ExceptionDataMapper;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.TrainInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("exceptionDataService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExceptionDataServiceImpl extends BaseService<ExceptionData> implements ExceptionDataService {

	@Autowired
	private ExceptionDataMapper exceptionDataMapper;

	@Autowired
	TrainInfoService trainInfoService;

	@Value("${exceptiondata.column}")
	int COLUMN;

	@Override
	public int save(Integer trainId, List<Date> acquisitionTimeList, String exceptionDataString) {
		List<ExceptionData> list = ExceptionDataUtils.pythonToExceptionDataList(trainId, acquisitionTimeList, COLUMN, exceptionDataString);
		return exceptionDataMapper.insertList(list);
	}

	@Override
	public int savenolist(Integer trainId, String exceptionDataString) {
		List<ExceptionData> list = ExceptionDataUtils.pythonToExceptionDataListNoList(trainId, COLUMN, exceptionDataString);
		return exceptionDataMapper.insertList(list);
	}

	@Override
	public List<ExceptionData> findByTrainInfoAndTime(Integer trainId, Date startDate, Date endDate) {
		Example example = new Example(ExceptionData.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("trainId", trainId);
		// 需要处理异常比如null值
		// 直接抛？？？还是处理默认
		criteria.andBetween("acquisitionTime", startDate, endDate);
		try {

			return exceptionDataMapper.selectByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<ExceptionData> findStatisticsResultByCar(StatisticsRequest statisticsRequest, List<String> trainNums) {
		// Example example = new Example(ExceptionData.class);
		// Criteria criteria = example.createCriteria();
		// criteria.andIn("trainId", trainNums);
		// Date startTime = statisticsRequest.getStartTime();
		// if (startTime != null) {
		// criteria.andCondition("acquisition_time>=", startTime);
		// }
		// Date endTime = statisticsRequest.getEndTime();
		// if (endTime != null) {
		// criteria.andCondition("acquisition_time<=", endTime);
		// }
		return exceptionDataMapper.findStatisticsResult(trainNums, statisticsRequest.getStartTime(), statisticsRequest.getEndTime());

	}

	@Override
	public List<ExceptionData> findStatisticsResultByPoint(DataRequest dataRequest) {
		// Example example = new Example(ExceptionData.class);
		// Criteria criteria = example.createCriteria();
		// criteria.andEqualTo("trainId", dataRequest.getTrainNum());
		// Date startTime = dataRequest.getStartTime();
		// if (startTime != null) {
		// criteria.andCondition("acquisition_time>=", startTime);
		// }
		// Date endTime = dataRequest.getEndTime();
		// if (endTime != null) {
		// criteria.andCondition("acquisition_time<=", endTime);
		// }
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(dataRequest.getTrainNum()));
		return exceptionDataMapper.findStatisticsResult(list, dataRequest.getStartTime(), dataRequest.getEndTime());
	}

}
