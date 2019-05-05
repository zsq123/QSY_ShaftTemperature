package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

@Table(name = "t_train_info")
@Data
public class TrainInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2302648330912410622L;

	@KeySql(useGeneratedKeys = true)
	private Integer id;

	@Column(name = "train_num")
	private Long trainNum;

	@Column(name = "train_type")
	private String trainType;

	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Transient
	private String trainNums;

	@Override
	public boolean equals(Object obj) {
		TrainInfo s = (TrainInfo) obj;
		return trainNum.equals(s.trainNum) && trainType.equals(s.trainType);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return (trainNum + trainType).hashCode();
	}
}