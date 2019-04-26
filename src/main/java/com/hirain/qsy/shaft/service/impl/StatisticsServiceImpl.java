package com.hirain.qsy.shaft.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.model.CoreConstant;
import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.DetailDataRequest;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.common.util.DateUtil;
import com.hirain.qsy.shaft.common.util.ReadExcelUtil;
import com.hirain.qsy.shaft.common.util.StatisticsUtil;
import com.hirain.qsy.shaft.model.ExceptionData;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.model.StatisticsExceptionTableDatas;
import com.hirain.qsy.shaft.model.ThresholdData;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.StatisticsService;
import com.hirain.qsy.shaft.service.TrainInfoService;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StatisticsServiceImpl implements StatisticsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExceptionDataService exceptionDataService;

	@Autowired
	private TrainInfoService trainInfoService;

	@Override
	public List<StatisticsChartDataRow> findStatisticsResultByCar(StatisticsRequest statisticsRequest) {
		List<StatisticsChartDataRow> statisticsByCarRows = new ArrayList<>();
		switch (statisticsRequest.getDimensionality()) {
		// 按车型统计，按车型统计只关注时间
		case 0:
			List<TrainInfo> trainInfos = trainInfoService.findAllGroupTrainType();
			for (TrainInfo trainInfo : trainInfos) {
				List<String> trainNums = Arrays.asList(trainInfo.getTrainNums().split(","));
				List<ExceptionData> exceptionDatas = exceptionDataService.findStatisticsResultByCar(statisticsRequest, trainNums);
				List<Integer> list = new ArrayList<>();
				list.add(getCountAllType(exceptionDatas));
				statisticsByCarRows.add(new StatisticsChartDataRow(trainInfo.getTrainType(), list));
			}
			break;
		// 按时间统计,按时间统计只关注车型
		case 1:
			Example example = new Example(ExceptionData.class);
			if (!CoreConstant.allCarType.equals(statisticsRequest.getType())) {
				List<TrainInfo> trainInfoList = trainInfoService.findTrainInfoByType(statisticsRequest.getType());
				List<Long> trainNums = new ArrayList<>();
				for (TrainInfo trainInfo : trainInfoList) {
					trainNums.add(trainInfo.getTrainNum());
				}
				example.createCriteria().andIn("trainId", trainNums);
			}
			example.orderBy("acquisitionTime");
			List<ExceptionData> exceptionDatas = exceptionDataService.selectByExample(example);
			List<ExceptionData> tempExceptionDatas = new ArrayList<>();
			Date oldDate = null;
			for (ExceptionData exceptionData : exceptionDatas) {
				Date acquisitionTime = exceptionData.getAcquisitionTime();
				if (oldDate != null) {
					boolean isSame = DateUtil.isSame(oldDate, acquisitionTime, statisticsRequest.getGranularityTime());
					if (!isSame) {
						addStatisticsData(statisticsRequest, statisticsByCarRows, tempExceptionDatas);
					}
				}
				tempExceptionDatas.add(exceptionData);
				oldDate = acquisitionTime;
			}
			if (tempExceptionDatas.size() > 0) {
				addStatisticsData(statisticsRequest, statisticsByCarRows, tempExceptionDatas);
			}
			break;
		default:
			break;
		}
		return statisticsByCarRows;
	}

	/**
	 * 添加统计数据
	 * 
	 * @param statisticsRequest
	 * @param statisticsByCarRows
	 * @param tempExceptionDatas
	 */
	private void addStatisticsData(StatisticsRequest statisticsRequest, List<StatisticsChartDataRow> statisticsByCarRows,
			List<ExceptionData> tempExceptionDatas) {
		Date time = tempExceptionDatas.get(0).getAcquisitionTime();
		List<Integer> list = new ArrayList<>();
		list.add(getCountAllType(tempExceptionDatas));
		switch (statisticsRequest.getGranularityTime()) {
		case 0:
			SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
			statisticsByCarRows.add(new StatisticsChartDataRow(format.format(time), list));
			break;
		case 1:
			statisticsByCarRows.add(new StatisticsChartDataRow(DateUtil.getFirstDayOfWeek(time), list));
			break;
		case 2:
			statisticsByCarRows.add(new StatisticsChartDataRow(DateUtil.getCurrentMonth(time), list));
			break;
		case 3:
			statisticsByCarRows.add(new StatisticsChartDataRow(DateUtil.getCurrentYear(time), list));
			break;
		default:
			break;
		}
		tempExceptionDatas.clear();
	}

	@Override
	public List<StatisticsChartDataRow> findStatisticsResultByPoint(DataRequest dataRequest) {
		List<ExceptionData> exceptionDatas = exceptionDataService.findStatisticsResultByPoint(dataRequest);
		return getCount(exceptionDatas);

	}

	@Override
	public List<StatisticsExceptionTableDatas> findTableData(DataRequest dataRequest, Integer axleNum) {
		List<ExceptionData> exceptionDatas = exceptionDataService.findStatisticsResultByPoint(dataRequest);
		return getTable(exceptionDatas, axleNum);
	}

	@Override
	public List<Map<String, Object>> findDetailData(DetailDataRequest detailDataRequest) {
		List<ExceptionData> exceptionDatas = getDetailExceptionData(detailDataRequest);
		return getDetailData(exceptionDatas, detailDataRequest);
	}

	private List<ExceptionData> getDetailExceptionData(DetailDataRequest detailDataRequest) {
		// Example example = new Example(ExceptionData.class);
		// Criteria criteria = example.createCriteria();
		// criteria.andEqualTo("trainId", detailDataRequest.getTrainNum());
		Date faultTime = detailDataRequest.getFaultTime();
		// criteria.andCondition("acquisition_time<=", faultTime);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(faultTime);
		switch (detailDataRequest.getTimeRange()) {
		case 0:
			rightNow.add(Calendar.DAY_OF_YEAR, -1);
			break;
		case 1:
			rightNow.add(Calendar.DAY_OF_YEAR, -2);
			break;
		case 2:
			rightNow.add(Calendar.DAY_OF_YEAR, -7);
			break;
		case 3:
			rightNow.add(Calendar.DAY_OF_YEAR, -14);
			break;
		default:
			break;
		}
		// criteria.andCondition("acquisition_time>=", rightNow.getTime());
		// example.orderBy("acquisitionTime");
		DataRequest dataRequest = new DataRequest();
		dataRequest.setTrainNum(detailDataRequest.getTrainNum());
		dataRequest.setStartTime(rightNow.getTime());
		dataRequest.setEndTime(faultTime);
		List<ExceptionData> exceptionDatas = exceptionDataService.findStatisticsResultByPoint(dataRequest);
		return exceptionDatas;
	}

	private List<Map<String, Object>> getDetailData(List<ExceptionData> exceptionDatas, DetailDataRequest detailDataRequest) {
		List<ThresholdData> thresholdDatas = ReadExcelUtil.getThresholdDatas();
		List<Map<String, Object>> list = new ArrayList<>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (ExceptionData exceptionData : exceptionDatas) {
			Field[] fields = exceptionData.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					Object value = f.get(exceptionData);
					if (value instanceof String) {
						String name = f.getName();
						// 获取到后两位数字，index=0的为轴号，index=1的为测点
						String num = name.substring(name.length() - 2, name.length());
						if (Integer.valueOf(String.valueOf(num.charAt(0))) == detailDataRequest.getAxleNum()
								&& Integer.valueOf(String.valueOf(num.charAt(1))) == detailDataRequest.getPointNum()) {
							String vString = (String) value;
							vString = vString.replace("[", "");
							vString = vString.replace("]", "");
							if (vString.contains(",")) {
								String[] split = vString.split(",");
								if (split.length == 5) {
									Map<String, Object> map = new HashMap<>();
									map.put("x", format.format(exceptionData.getAcquisitionTime()));
									// 预测值
									map.put("t1", split[0]);
									// 实测值
									map.put("t2", split[4]);
									// 残差
									map.put("t3", split[1]);
									ThresholdData thresholdData = getThresholdData(thresholdDatas, detailDataRequest.getTrainNum(), num);
									// 门限
									map.put("t4", thresholdData);
									list.add(map);
								}
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
		return list;
	}

	private ThresholdData getThresholdData(List<ThresholdData> thresholdDatas, Long trainNum, String num) {
		for (ThresholdData thresholdData : thresholdDatas) {
			if (thresholdData.getTrainId().equals(String.valueOf(trainNum)) && thresholdData.getTestNum().equals(num)) {
				return thresholdData;
			}
		}
		return null;
	}

	/**
	 * 计算每个车型的异常触发次数
	 * 
	 * @param exceptionDatas
	 * @return
	 */
	private Integer getCountAllType(List<ExceptionData> exceptionDatas) {
		Integer count = 0;
		for (ExceptionData exceptionData : exceptionDatas) {
			Field[] fields = exceptionData.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					Object value = f.get(exceptionData);
					if (value instanceof String) {
						String vString = (String) value;
						if (vString.contains(",")) {
							String[] split = vString.split(",");
							if (split.length == 5) {
								// 1表示异常
								if (split[2].equals("1")) {
									count++;
									break;
								}
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
		return count;
	}

	/**
	 * 计算每个轴每个测点的异常触发次数
	 * 
	 * @param exceptionDatas
	 * @param rows
	 * @return
	 */
	private List<StatisticsChartDataRow> getCount(List<ExceptionData> exceptionDatas) {
		List<StatisticsChartDataRow> rows = new ArrayList<>();
		for (int i = 1; i < 7; i++) {
			List<Integer> list = new LinkedList<>();
			for (int j = 0; j < 5; j++) {
				list.add(0);
			}
			rows.add(new StatisticsChartDataRow(i + "号轴", list));
		}
		for (ExceptionData exceptionData : exceptionDatas) {
			Field[] fields = exceptionData.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					Object value = f.get(exceptionData);
					if (value instanceof String) {
						String[] split = ((String) value).split(",");
						// 1表示异常
						if (split[2].equals("1")) {
							String name = f.getName();
							// 获取到后两位数字，个位为测点，十位为轴号
							String num = name.substring(name.length() - 2, name.length());
							// 获取对应轴的数据
							StatisticsChartDataRow statisticsByPointRow = rows.get(Integer.valueOf(String.valueOf(num.charAt(0))) - 1);
							List<Integer> y = statisticsByPointRow.getY();
							Integer valueOf = Integer.valueOf(String.valueOf(num.charAt(1))) - 1;
							y.set(valueOf, y.get(valueOf) + 1);
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
		return rows;
	}

	private List<StatisticsExceptionTableDatas> getTable(List<ExceptionData> exceptionDatas, Integer axleNum) {
		List<StatisticsExceptionTableDatas> rows = new ArrayList<>();
		for (ExceptionData exceptionData : exceptionDatas) {
			Field[] fields = exceptionData.getClass().getDeclaredFields();
			for (Field f : fields) {
				f.setAccessible(true);
				try {
					Object value = f.get(exceptionData);
					if (value instanceof String) {
						String name = f.getName();
						// 获取到后两位数字，index=0的为轴号，index=1的为测点
						String num = name.substring(name.length() - 2, name.length());
						if (Integer.valueOf(String.valueOf(num.charAt(0))) == axleNum) {
							String vString = (String) value;
							if (vString.contains(",")) {
								String[] split = vString.split(",");
								if (split.length == 5) {
									// 1表示异常
									if (split[2].equals("1")) {
										rows.add(new StatisticsExceptionTableDatas(axleNum, Integer.valueOf(String.valueOf(num.charAt(1))),
												exceptionData.getAcquisitionTime()));
									}
								}
							}
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			}
		}
		StatisticsUtil.sort(rows);
		return rows;
	}
}
