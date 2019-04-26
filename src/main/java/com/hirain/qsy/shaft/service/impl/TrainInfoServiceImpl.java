package com.hirain.qsy.shaft.service.impl;

import java.util.List;

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
