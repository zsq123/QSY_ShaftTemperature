package com.hirain.qsy.shaft.dao;

import java.util.Date;
import java.util.List;

import com.hirain.qsy.shaft.model.InitialData;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface InitialDataMapper extends Mapper<InitialData>, InsertListMapper<InitialData> {

	List<InitialData> findByParams(Long trainId, Date starttime, Date endtime);

}