package com.hirain.qsy.shaft.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.ExceptionDataService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/exceptiondata")
@Slf4j
public class ExceptionDataController {

	@Autowired
	ExceptionDataService exceptionDataService;

	@PostMapping("/save")
	public ResponseBo save(@RequestParam Integer trainId, @RequestParam List<Date> acquisitionTimeList, @RequestParam String exceptionDataString) {

		try {
			exceptionDataService.save(trainId, acquisitionTimeList, exceptionDataString);
			return ResponseBo.ok("异常数据插入成功！");
		} catch (Exception e) {
			log.error("异常数据插入错误", e);
			return ResponseBo.error("异常数据插入错误，请联系网站管理员！");
		}
	}

//	@PostMapping("/savenolist")
//	public ResponseBo saveNoList(@RequestParam Integer trainId, @RequestParam String exceptionDataString) {
//
//		try {
//			exceptionDataService.savenolist(trainId, exceptionDataString);
//			log.info("成功");
//			log.info(String.valueOf(trainId));
//			return ResponseBo.ok("异常数据插入成功！");
//		} catch (Exception e) {
//			log.error("异常数据插入错误", e);
//			return ResponseBo.error("异常数据插入错误，请联系网站管理员！");
//		}
//	}

	@PostMapping("/getdata")
	public ResponseBo getExceptionStateData(@RequestParam Integer trainId, @RequestParam String startDate, @RequestParam String endDate) {

		return ResponseBo.ok(exceptionDataService.analyseExceptionData(trainId, startDate, endDate)) ;
	}
	
	@PostMapping("/getAxledata")
	public ResponseBo getAxleExceptionData(@RequestParam Integer trainId, @RequestParam String startDate, @RequestParam String endDate,@RequestParam String axleName) {

		return ResponseBo.ok(exceptionDataService.findAxleExceptionData(trainId, startDate, endDate,axleName)) ;
	}
	

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
