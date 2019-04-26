package com.hirain.qsy.shaft.model;

import java.util.List;

import lombok.Data;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月10日 下午12:39:58
 * @Description
 *              <p>
 *              异常统计，图表数据
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月10日 changwei.zheng@hirain.com 1.0 create file
 */
@Data
public class StatisticsChartDataRow {

	private String x;

	private List<Integer> y;

	public StatisticsChartDataRow(String x) {
		this.x = x;
	}

	public StatisticsChartDataRow(String x, List<Integer> y) {
		this.x = x;
		this.y = y;
	}

}
