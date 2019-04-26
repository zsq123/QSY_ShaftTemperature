/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.common.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hirain.qsy.shaft.model.User;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年3月28日 下午3:08:15
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年3月28日 changwei.zheng@hirain.com 1.0 create file
 */
public class UserRequest extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5531163658939828616L;

	private String roleName;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date starttime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endtime;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	@Override
	public String toString() {
		return "UserRequest [roleName=" + roleName + ", starttime=" + starttime + ", endtime=" + endtime + "]" + super.toString();
	}

}
