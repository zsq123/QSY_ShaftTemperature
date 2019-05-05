package com.hirain.qsy.shaft.service.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.common.util.ExceptionDataUtils;
import com.hirain.qsy.shaft.common.util.OperateExcelUtils;
import com.hirain.qsy.shaft.dao.ExceptionDataMapper;
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.AxleExceptionData;
import com.hirain.qsy.shaft.model.AxleExceptionStateData;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.service.ExceptionDataService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("exceptionDataService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExceptionDataServiceImpl extends BaseService<ExceptionData> implements ExceptionDataService {

	@Autowired
	private ExceptionDataMapper exceptionDataMapper;

	@Value("${exceptiondata.column}")
	int COLUMN;

	@Override
	public int save(Integer trainId, List<Date> acquisitionTimeList, String exceptionDataString) {
		List<ExceptionData> list = ExceptionDataUtils.pythonToExceptionDataList(trainId, acquisitionTimeList, COLUMN, exceptionDataString);
		return exceptionDataMapper.insertList(list);
	}

	/**
	 * 按车号，时间查询数据
	 */
	@Override
	public List<ExceptionData> findByTrainInfoAndTime(Integer trainId, String startDate, String endDate) {
		Example example = new Example(ExceptionData.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("trainId", trainId);
		criteria.andBetween("acquisitionTime", startDate, endDate);
		try {
			return exceptionDataMapper.selectByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 返回轴的异常状态
	 */
	@Override
	public Set<AxleExceptionStateData> analyseExceptionData(Integer trainId, String startDate, String endDate) {
		// 查询数据
		List<ExceptionData> list = findByTrainInfoAndTime(trainId, startDate, endDate);

		// 获取exceptionData的映射关系
		AttributeMappingConfigurationData aMCDMap = new AttributeMappingConfigurationData("exceptionData");
		Map<String, String> map = aMCDMap.getExcepData();

		// 存储轴异常状态信息
		TreeSet<AxleExceptionStateData> set = new TreeSet<>();

		// 首先将所有测点状态设为正常
		for (int i = 0; i < 36; i++) {
			for (String key : map.keySet())
				set.add(new AxleExceptionStateData(map.get(key).toString(), "0"));
		}
		if (list.size() > 0) {
			for (ExceptionData data : list) {
				for (String key : map.keySet()) {
					try {
						String exceptionDataString = BeanUtils.getProperty((ExceptionData) data, key);
						if (!isException(exceptionDataString)) {
							// set.add(new axleExceptionData(map))
							// 将该轴设为异常
							set.remove(new AxleExceptionStateData(map.get(key).toString(), "0"));
							set.add(new AxleExceptionStateData(map.get(key).toString(), "1"));
						}

					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}

		// TODO Auto-generated method stub
		return set;
	}

	/**
	 * 判断是否存在异常
	 */
	public boolean isException(String exception) {
		try {
			String data[] = exception.split(",");
			if (data.length > 3) {
				if (data[2].toString().equals("1")) {
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;

	}

	/**
	 * 通过轴名称获取轴的信息
	 */
	@Override
	public List<AxleExceptionData> findAxleExceptionData(Integer trainId, String startDate, String endDate, String axleName) {

		String thresholdFilePath = System.getProperty("user.dir") + "\\threshold.xls";
		String threshold = "", threshold1 = "", threshold2 = "";
		List<List<Object>> thresholdList = null;
		OperateExcelUtils operateExcelUtils = new OperateExcelUtils();
		try {
			thresholdList = operateExcelUtils.getExcelFileData(thresholdFilePath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO Auto-generated method stub
		// 查询信息
		List<ExceptionData> list = findByTrainInfoAndTime(trainId, startDate, endDate);

		// 存储轴异常数据
		List<AxleExceptionData> axleListDatas = new ArrayList<AxleExceptionData>();

		for (List<Object> data : thresholdList) {
			if (data.get(0).toString().equals(trainId.toString()) && data.get(1).toString().equals(axleName.toString())) {
				threshold = data.get(2).toString();
				threshold1 = data.get(3).toString();
				threshold2 = data.get(4).toString();
			}
		}

		// 转化时间格式
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		try {
			if (list.size() > 0) {
				for (ExceptionData data : list) {
					AxleExceptionData axleData = new AxleExceptionData();
					// 通过属性名称获取属性值
					String exceptionDataString = BeanUtils.getProperty((ExceptionData) data, "resultAxle" + axleName);

					axleData.setAcquisitionTime(sdf.parse((BeanUtils.getProperty((ExceptionData) data, "acquisitionTime"))));
					String exceptionDataStringSplit[] = exceptionDataString.split(",");
					axleData.setActualValue(exceptionDataStringSplit[4]);
					axleData.setPredicteValue(exceptionDataStringSplit[0]);
					axleData.setResiduals(exceptionDataStringSplit[1]);
					axleData.setThreshold(threshold);
					axleData.setThreshold1(threshold1);
					axleData.setThreshold2(threshold2);
					axleListDatas.add(axleData);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 对list的数据按照时间进行排序
		Collections.sort(axleListDatas, new Comparator<AxleExceptionData>() {

			@Override
			public int compare(AxleExceptionData o1, AxleExceptionData o2) {
				// TODO Auto-generated method stub
				Date date1 = o1.getAcquisitionTime();
				Date date2 = o2.getAcquisitionTime();
				if (date1.after(date2)) {
					return 1;
				} else if (date1 == date2) {
					return 0;
				} else {
					return -1;
				}
			}
		});

		return axleListDatas;
	}

	@Override
	public List<ExceptionData> findStatisticsResultByCar(StatisticsRequest statisticsRequest, List<String> trainNums) {
		return exceptionDataMapper.findStatisticsResult(trainNums, statisticsRequest.getStartTime(), statisticsRequest.getEndTime());

	}

	@Override
	public List<ExceptionData> findStatisticsResultByPoint(DataRequest dataRequest) {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(dataRequest.getTrainNum()));
		return exceptionDataMapper.findStatisticsResult(list, dataRequest.getStartTime(), dataRequest.getEndTime());
	}

}
