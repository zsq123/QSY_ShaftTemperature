/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.common.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirain.qsy.shaft.model.TrainInfo;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年3月28日 下午3:08:15
 * @Description
 *              <p>
 *              平台数据管理回复
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年3月28日 changwei.zheng@hirain.com 1.0 create file
 */
public class DataResponse extends TrainInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6239822110249174006L;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date earliestTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date latestTime;

	public Date getEarliestTime() {
		return earliestTime;
	}

	public void setEarliestTime(Date earliestTime) {
		this.earliestTime = earliestTime;
	}

	public Date getLatestTime() {
		return latestTime;
	}

	public void setLatestTime(Date latestTime) {
		this.latestTime = latestTime;
	}

}
