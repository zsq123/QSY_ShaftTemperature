package com.hirain.qsy.shaft.common.model;

import java.util.Date;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月9日 下午1:45:07
 * @Description
 *              <p>
 *              异常统计详细数据查看数据请求
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月9日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class DetailDataRequest {

	private Long trainNum;

	private Integer axleNum;

	private Integer pointNum;

	private Date faultTime;

	/**
	 * 0表示最近一天，1表示最近2天，2表示最近一周，3表示最近两周
	 */
	private Integer timeRange;

}
