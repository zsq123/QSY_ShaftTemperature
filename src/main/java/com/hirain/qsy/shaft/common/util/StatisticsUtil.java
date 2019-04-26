package com.hirain.qsy.shaft.common.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.hirain.qsy.shaft.model.StatisticsExceptionTableDatas;

public class StatisticsUtil {

	public static void sort(List<StatisticsExceptionTableDatas> rows) {
		Collections.sort(rows, new Comparator<StatisticsExceptionTableDatas>() {

			@Override
			public int compare(StatisticsExceptionTableDatas o1, StatisticsExceptionTableDatas o2) {

				return o1.getPointNum() - o2.getPointNum();
			}
		});
	}
}
