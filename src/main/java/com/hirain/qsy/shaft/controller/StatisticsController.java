package com.hirain.qsy.shaft.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.DetailDataRequest;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.model.StatisticsRequest;
import com.hirain.qsy.shaft.model.StatisticsChartDataRow;
import com.hirain.qsy.shaft.model.StatisticsExceptionTableDatas;
import com.hirain.qsy.shaft.service.StatisticsService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:45:26
 * @Description
 *              <p>
 *              异常统计controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
public class StatisticsController extends BaseController {

	@Autowired
	StatisticsService statisticsService;

	@GetMapping("/exceptionStatisticsByCar")
	@RequiresAuthentication
	public ResponseBo exceptionStatisticsByCar(StatisticsRequest statisticsRequest) {
		List<StatisticsChartDataRow> rows = statisticsService.findStatisticsResultByCar(statisticsRequest);

		return ResponseBo.ok(rows);
	}

	@GetMapping("/exceptionStatisticsByPoint")
	@RequiresAuthentication
	public ResponseBo exceptionStatisticsByPoint(DataRequest dataRequest) {
		long currentTimeMillis = System.currentTimeMillis();
		List<StatisticsChartDataRow> rows = statisticsService.findStatisticsResultByPoint(dataRequest);
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		return ResponseBo.ok(rows);
	}

	@GetMapping("/findTableData")
	@RequiresAuthentication
	public ResponseBo findTableData(QueryRequest request, DataRequest dataRequest, Integer axleNum) {
		long currentTimeMillis = System.currentTimeMillis();
		List<StatisticsExceptionTableDatas> list = statisticsService.findTableData(dataRequest, axleNum);
		Map<String, Object> rspData = new HashMap<>();
		List<StatisticsExceptionTableDatas> pageList = new ArrayList<>();
		int pageNum = request.getPageNum();
		int pageSize = request.getPageSize();
		int end = pageNum * pageSize;
		if (pageNum * pageSize > list.size()) {
			end = list.size();
		}
		for (int i = (pageNum - 1) * pageSize; i < end; i++) {
			pageList.add(list.get(i));
		}
		rspData.put("rows", pageList);
		rspData.put("total", list.size());
		System.out.println(System.currentTimeMillis() - currentTimeMillis);
		return ResponseBo.ok(rspData);
	}

	@GetMapping("/detail")
	@RequiresAuthentication
	public ResponseBo detail(DetailDataRequest detailDataRequest) {
		List<Map<String, Object>> list = statisticsService.findDetailData(detailDataRequest);
		return ResponseBo.ok(list);
		// List<StatisticsByPointRows> rows = new ArrayList<>();
		// for (int i = 1; i < 7; i++) {
		// Long[] y = new Long[2];
		// StatisticsByPointRows e = new StatisticsByPointRows(i + "号轴");
		// for (int j = 0; j < y.length; j++) {
		// y[j] = (long) ((j + 1) * i);
		// }
		// e.setY(y);
		// rows.add(e);
		//
		// }
		// return ResponseBo.ok(rows);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
