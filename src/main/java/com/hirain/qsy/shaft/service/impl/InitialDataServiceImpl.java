package com.hirain.qsy.shaft.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.common.config.GlobVariableConfig;
import com.hirain.qsy.shaft.common.util.OperateExcelUtils;
import com.hirain.qsy.shaft.dao.InitialDataMapper;
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.InitialAxleData;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.service.InitialDataService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("initialDataService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InitialDataServiceImpl extends BaseService<InitialData> implements InitialDataService {

	private Map<String, String> mapData;// 存储initialdata与excel表头映射关系

	public Map<String, String> trainInfor;// 存储列车类型及id

	public List<List<Object>> excelData;// 存储excel数据

	@Autowired
	private InitialDataMapper initialDataMapper;

	// 批量插入
	@Override
	@Transactional
	public int save(List<InitialData> list) {

		try {
			if (CollectionUtils.isEmpty(list)) {
				return -1;
			} else {
				return initialDataMapper.batInitialAdd(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	// 单条插入
	@Override
	@Transactional
	public int save(InitialData data) {
		return initialDataMapper.insert(data);
	}

	/**
	 * 获取excel中的有效数据
	 * 
	 * @param excelData
	 * @param mapName
	 * @return
	 */
	public List<InitialData> getInitialData(List<List<Object>> excelData, String mapName, String tClass) {
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<Object>> initialData = new ArrayList<List<Object>>();
		List<InitialData> list = new ArrayList<>();// 存储初始化数据
		trainInfor = new HashMap<String, String>();
		AttributeMappingConfigurationData Data = new AttributeMappingConfigurationData(mapName);
		mapData = Data.getMapData();
		try {
			// 处理时间格式
			DateConverter dateConverter = new DateConverter();
			// 设置日期格式
			dateConverter.setPatterns(new String[] { "yyyy-MM-dd HH:mm:ss" });
			// 注册格式
			ConvertUtils.register(dateConverter, Date.class);
			for (int rIndex = 0; rIndex < excelData.size(); rIndex++) {
				InitialData t = (InitialData) Class.forName(tClass).newInstance();
				if (rIndex >= 1) {
					// 获取机车信息数据
					if (!trainInfor.containsKey(excelData.get(rIndex).get(0).toString())
							|| !trainInfor.containsValue(excelData.get(rIndex).get(1).toString())) {
						trainInfor.put(excelData.get(rIndex).get(0).toString(), excelData.get(rIndex).get(1).toString());
					}
				}
				List<Object> rowDate = new ArrayList<Object>();// 存储行信息
				if (!isNull(excelData.get(rIndex), excelData.get(0), mapData)) {
					for (int cIndex = 0; cIndex < excelData.get(rIndex).size(); cIndex++) {
						if (mapData.containsKey(excelData.get(0).get(cIndex).toString())) {
							rowDate.add(excelData.get(rIndex).get(cIndex).toString());
							if (rIndex >= 1) {
								Object dateObject = excelData.get(rIndex).get(cIndex).toString();
								if (isDate(dateObject.toString())) {
									dateObject = sdfDateFormat.parse(dateObject.toString());
								}
								String proName = mapData.get(excelData.get(0).get(cIndex).toString());
								BeanUtils.setProperty(t, proName, dateObject);
							}
						}

					}
					initialData.add(rowDate);
					if (rIndex >= 1) {
						t.setCreateTime(new Date());
						list.add(t);
					}

				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.toString());
		}
		excelData = initialData;
		return list;
	}

	/**
	 * 返回到controal的数据接口
	 */
	public List<InitialData> initialDatas(String path, String tClass, String mapName, Map<String, String> headerMapper) {
		List<InitialData> list = new ArrayList<>();
		OperateExcelUtils operateExcelUtils = new OperateExcelUtils();
		try {
			List<List<Object>> excelDataList = operateExcelUtils.getExcelFileData(path);
			if (excelDataList.size() > 0) {
				// List<List<Object>>
				// dataList=getInitialExcelData(excelDataList,mapName,tClass);
				// excelData=dataList;
				list = getInitialData(excelDataList, mapName, tClass);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据机车号，时间查询数据
	 */
	@Override
	public List<List<InitialAxleData>> findByTrainInfoAndTime(Integer trainId, String startDate, String endDate) {
		/**
		 * Example example = new Example(InitialData.class); Criteria criteria =
		 * example.createCriteria(); criteria.andEqualTo("trainId", trainId);
		 * criteria.andBetween("acquisitionTime", startDate, endDate);
		 **/
		return retrieveEveryAxleData(initialDataMapper.avgOfAxleData(trainId, startDate, endDate));
	}

	/**
	 * 获取每个轴的数据
	 * 
	 * @param listdata
	 * @return
	 */
	public List<List<InitialAxleData>> retrieveEveryAxleData(List<InitialData> listdata) {

		// 获取提前定义好的全局变量
		String[] axleName = GlobVariableConfig.axleName;

		// 存储每个轴的数据
		List<List<InitialAxleData>> everyAxleData = new ArrayList<List<InitialAxleData>>();
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
		try {
			for (int axleIndex = 0; axleIndex < 6; axleIndex++) {
				List<InitialAxleData> axleDataList = new ArrayList<>();
				for (InitialData data : listdata) {
					InitialAxleData axleData = new InitialAxleData();
					axleData.setAxle1(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex])));
					axleData.setAxle2(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 1])));
					axleData.setAxle3(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 2])));
					axleData.setAxle4(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 3])));
					axleData.setAxle5(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 4])));
					axleData.setAxle6(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 5])));
					axleData.setAmbientTemperature1(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 6])));
					axleData.setAmbientTemperature2(Float.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 7])));
					axleData.setGpsSpeed(Integer.valueOf(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 8])));
					axleData.setAcquisitionTime(sdf.parse(BeanUtils.getProperty((InitialData) data, axleName[10 * axleIndex + 9])));
					axleDataList.add(axleData);
				}
				everyAxleData.add(axleDataList);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return everyAxleData;

	}

	public List<List<Object>> getExcelData() {
		return excelData;
	}

	/**
	 * 返回机车类型与机车号
	 */
	@Override
	public Map<String, String> getTrainInfos() {
		// TODO Auto-generated method stub
		return trainInfor;
	}

	/**
	 * 判断该行温度是否存在空值
	 * 
	 * @param rows
	 * @param rowhead
	 * @param map
	 * @return
	 */
	public boolean isNull(List<Object> rows, List<Object> rowhead, Map<String, String> map) {
		List<String> nullList = Arrays.asList(GlobVariableConfig.title);// 实例化温度表头
		for (int i = 0; i < rows.size(); i++) {
			if (map.containsKey(rowhead.get(i).toString()) && rows.get(i).toString().equals("-")) {
				if ((nullList.contains(rowhead.get(i).toString())))
					return true;
			}
		}

		return false;
	}

	/**
	 * 判断输入的是不是日期
	 * 
	 * @param strDate
	 * @return
	 */
	private boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<InitialData> findByTrainInfoAndTime(Long trainId, Date startDate, Date endDate) {
		return initialDataMapper.findByParams(trainId, startDate, endDate);

	}

	@Override
	public void deleteByTrainNumAndTime(Long[] trainNums, Date deadline) {
		if (trainNums != null && deadline != null) {
			Example example = new Example(InitialData.class);
			Criteria criteria = example.createCriteria();
			criteria.andIn("trainId", Arrays.asList(trainNums));
			criteria.andCondition("acquisition_time <=", deadline);
			initialDataMapper.deleteByExample(example);
		}
	}
}
