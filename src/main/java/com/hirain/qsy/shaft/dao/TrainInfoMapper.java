package com.hirain.qsy.shaft.dao;



import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.hirain.qsy.shaft.common.config.MyMapper;
import com.hirain.qsy.shaft.model.TrainInfo;


@org.apache.ibatis.annotations.Mapper
public interface TrainInfoMapper extends MyMapper<TrainInfo>{
	
	/**
	 * 查询所有的车型
	 * @return
	 */
	@Select("select distinct train_type from t_train_info")
	List<String> queryAllTrainType();
	
	@Select("select distinct train_num from t_train_info where train_type=#{traintype}")
	List<String> queryTrainNumByType(@Param("traintype") String traintype);
	
	List<TrainInfo> findAllGroupTrainType();
}