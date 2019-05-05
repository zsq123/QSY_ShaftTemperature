package com.hirain.dome;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hirain.qsy.shaft.Application;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.service.ExceptionDataService;
import com.hirain.qsy.shaft.service.InitialDataService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ExceptionDataTest {

	@Autowired
	ExceptionDataService exceptionDataService;

	@Autowired
	InitialDataService initialDataService;

	// @Test
	public void test() {
		List<InitialData> list = initialDataService.selectAll();
		// SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(new Date());
		for (InitialData initialData : list) {
			List<Date> dateList = new ArrayList<>();
			rightNow.add(Calendar.HOUR_OF_DAY, 2);
			dateList.add(rightNow.getTime());
			exceptionDataService.save(initialData.getTrainId(), dateList, getException().toString());
		}
	}

	private StringBuilder getException() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		Random random = new Random();
		for (int i = 0; i < 30; i++) {
			builder.append("[");
			// 预测值
			builder.append(random.nextInt(100));
			builder.append(",");
			// 残差
			builder.append(random.nextInt(10));
			builder.append(",");
			// 是否异常
			builder.append(i % 2);
			builder.append(",");
			// 不确定性
			builder.append(random.nextFloat());
			builder.append(",");
			// 实测值
			builder.append(random.nextInt(100));
			builder.append("],");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append("]");
		return builder;
	}
}
