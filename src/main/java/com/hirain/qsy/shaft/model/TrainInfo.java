package com.hirain.qsy.shaft.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Table(name = "t_train_info")
public class TrainInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2781412098220798685L;

	private Integer id;

	private Long trainNum;

	private String trainType;

	@Transient
	private String trainNums;

	private Date createTime;

}