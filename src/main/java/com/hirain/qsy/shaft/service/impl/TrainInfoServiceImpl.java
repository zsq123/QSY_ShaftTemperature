package com.hirain.qsy.shaft.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.dao.TrainInfoMapper;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.TrainInfoService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TrainInfoServiceImpl extends BaseService<TrainInfo> implements TrainInfoService {

	@Autowired
	private TrainInfoMapper trainInfoMapper;

	@Override
	public TrainInfo findById(Integer tranId) {
		return trainInfoMapper.selectByPrimaryKey(tranId);

	}

	@Override
	public int saveTrainInfor(List<TrainInfo> list) {
		// TODO Auto-generated method stub
		try {
			return trainInfoMapper.insertList(list);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}

	}

	/**
	 * get object of trainInfo and exists
	 * 
	 * @see com.hirain.qsy.shaft.service.TrainInfoService#mapToTrainObject(java.util.Map)
	 */
	@Override
	public List<TrainInfo> mapDataToTrainObject(Map<String, String> map) {
		// TODO Auto-generated method stub
		List<TrainInfo> dataInfos = new ArrayList<TrainInfo>();
		for (Map.Entry<String, String> entry : map.entrySet()) {

			TrainInfo trainInfo = new TrainInfo();
			trainInfo.setTrainNum(Long.valueOf(entry.getValue()));
			trainInfo.setTrainType(entry.getKey());
			trainInfo.setCreateTime(new Date());

			// 判断数据库是否存在该车号与车型
			Example example = new Example(TrainInfo.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("trainNum", entry.getValue());
			criteria.andEqualTo("trainType", entry.getKey());

			if (selectByExample(example).size() == 0)
				dataInfos.add(trainInfo);
		}
		return dataInfos;
	}

	@Override
	public List<String> getAllTrainType() {
		// TODO Auto-generated method stub
		return trainInfoMapper.queryAllTrainType();
	}

	/**
	 * 根据车辆类型查询车号
	 * 
	 * @see com.hirain.qsy.shaft.service.TrainInfoService#queryTrainNumByType(java.lang.String)
	 */
	@Override
	public List<String> queryTrainNumByType(String trainType) {

		return trainInfoMapper.queryTrainNumByType(trainType);
	}

	@Override
	public List<TrainInfo> findList(QueryRequest request, DataRequest dataRequest) {
		if (request != null) {
			PageHelper.startPage(request.getPageNum(), request.getPageSize());
		}
		Example example = new Example(TrainInfo.class);
		Criteria criteria = example.createCriteria();
		if (dataRequest.getTrainNum() != null) {
			criteria.andCondition("train_num like", "%" + dataRequest.getTrainNum() + "%");
		}
		if (StringUtils.isNotBlank(dataRequest.getTrainType())) {
			criteria.andCondition("train_type like", "%" + dataRequest.getTrainType() + "%");
		}
		example.setOrderByClause("train_num");
		return this.selectByExample(example);
	}

	@Override
	public List<TrainInfo> findAllGroupTrainType() {
		return trainInfoMapper.findAllGroupTrainType();
	}

	@Override
	public List<TrainInfo> findTrainInfoByType(String trainType) {
		Example example = new Example(TrainInfo.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(trainType)) {
			criteria.andCondition("train_type =", trainType);
		}
		example.setOrderByClause("train_num");
		return this.selectByExample(example);
	}

}
