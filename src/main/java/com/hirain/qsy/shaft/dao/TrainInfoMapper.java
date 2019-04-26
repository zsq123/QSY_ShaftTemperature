package com.hirain.qsy.shaft.dao;

import java.util.List;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.TrainInfo;

public interface TrainInfoMapper extends MyMapper<TrainInfo> {

	List<TrainInfo> findAllGroupTrainType();
}