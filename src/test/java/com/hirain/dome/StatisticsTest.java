package com.hirain.dome;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.qsy.shaft.Application;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.service.StatisticsService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StatisticsTest {

	@Autowired
	StatisticsService statisticsService;

	@Test
	public void findStatisticsResultByCar() {
		StatisticsRequest statisticsRequest = new StatisticsRequest();
		statisticsRequest.setDimensionality(0);
		statisticsRequest.setType("0");
		List<StatisticsChartDataRow> result = statisticsService.findStatisticsResultByCar(statisticsRequest);
		System.out.println(result);
	}

}
