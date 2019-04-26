package com.hirain.qsy.shaft.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.DataService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月2日 下午5:15:28
 * @Description
 *              <p>
 *              平台数据管理controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月2日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/data")
public class DataController extends BaseController {

	@Autowired
	DataService dataService;

	@GetMapping("/list")
	@RequiresAuthentication
	public ResponseBo list(QueryRequest request, DataRequest dataRequest) {
		Map<String, Object> map = dataService.getList(request, dataRequest);
		return ResponseBo.ok(map);
	}

	@GetMapping("/getStorage")
	@RequiresAuthentication
	public ResponseBo getStorage() {
		Map<String, Object> map = dataService.getStorage();
		return ResponseBo.ok(map);
	}

	@Log("删除平台数据")
	@PostMapping("/delete")
	@RequiresAuthentication
	public ResponseBo delete(Long[] trainNums, Date deadline) {
		dataService.delete(trainNums, deadline);
		return ResponseBo.ok();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}
}
