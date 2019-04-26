package com.hirain.qsy.shaft.service.impl;

import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hirain.qsy.shaft.dao.InitialDataMapper;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.service.InitialDataService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("initialDataService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InitialDataServiceImpl extends BaseService<InitialData> implements InitialDataService {

	@Autowired
	private InitialDataMapper initialDataMapper;

	// 批量插入
	@Override
	@Transactional
	public int save(List<InitialData> list) {
		try {
			if (CollectionUtils.isEmpty(list)) {
				return 0;
			} else {
				return initialDataMapper.insertList(list);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 返回InitialData数组
	 * 
	 * @param excelDate
	 *            excel数据
	 * @param headerMapper
	 *            excel表头
	 * @param tClass
	 *            InitialData路径
	 * @return
	 */
	@Override
	public List<InitialData> readBean(List<List<Object>> excelDate, Map<String, String> headerMapper, String tClass) {
		List<InitialData> list = new ArrayList<>();
		SimpleDateFormat sdfDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PropertyDescriptor propertyDescriptor = null;
		if (excelDate != null) {
			try {
				// 处理时间格式
				DateConverter dateConverter = new DateConverter();
				// 设置日期格式
				dateConverter.setPatterns(new String[] { "yyyy-MM-dd HH:mm:ss" });
				// 注册格式
				ConvertUtils.register(dateConverter, Date.class);

				for (int k = 1; k < excelDate.size(); k++) {
					InitialData t = (InitialData) Class.forName(tClass).newInstance();

					for (int num = 0; num < excelDate.get(0).size(); num++) {
						if (excelDate.get(0).get(num) != null) {
							boolean bo = headerMapper.containsKey(excelDate.get(0).get(num));
							if (bo) {
								String proName = headerMapper.get(excelDate.get(0).get(num)).toString();
								Object dateObject = excelDate.get(k).get(num);
								if (isDate(dateObject.toString())) {
									dateObject = sdfDateFormat.parse(dateObject.toString());
								}
								propertyDescriptor = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(t,
										headerMapper.get(excelDate.get(0).get(num)).toString());
								if (propertyDescriptor != null) {
									BeanUtils.setProperty(t, proName, dateObject);
								}
							}
						}
					}
					t.setCreateTime(new Date());
					list.add(t);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return list;
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

	// 单条插入
	@Override
	@Transactional
	public int save(InitialData data) {
		return initialDataMapper.insert(data);
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
