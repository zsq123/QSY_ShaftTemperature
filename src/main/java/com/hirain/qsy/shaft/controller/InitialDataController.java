package com.hirain.qsy.shaft.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.common.util.HttpContextUtils;
import com.hirain.qsy.shaft.common.util.OperateExcelUtils;
import com.hirain.qsy.shaft.model.ExcelToInitialData;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.service.InitialDataService;

@RequestMapping("/initialdata")
@RestController
public class InitialDataController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InitialDataService initialDataService;

	@Log("上传文件")
	@PostMapping("/file")
	public ResponseBo saveInitialData(MultipartFile excelFile) throws IOException {

		OperateExcelUtils operateExcel = new OperateExcelUtils();// 实例化excel处理类
		ExcelToInitialData Data = new ExcelToInitialData();
		File targetFile = null;
		try {

			if (excelFile != null) {
				String originalFilename = URLDecoder.decode(excelFile.getOriginalFilename(), "uft-8");
				// String pafilePath = System.getProperty("catalina.base")
				// +"/upload/application/";
				HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
				String realPath = request.getSession().getServletContext().getRealPath("/");

				targetFile = new File(realPath, originalFilename);

				excelFile.transferTo(targetFile);

				List<InitialData> list = initialDataService.readBean(operateExcel.getExcelFileData(realPath + "/" + originalFilename),
						Data.getMapData(), "com.hirain.qsy.shaft.model.InitialData"); // operateExcel.setExcelFile(realPath+"/"+originalFilename);
				if (this.initialDataService.save(list) == list.size()) {
					targetFile.delete();
					return ResponseBo.ok("数据导入成功！");
				}
			}

		} catch (IllegalStateException e) {
			// targetFile.delete();
			log.error("数据导入失败", e);
		}
		targetFile.delete();
		return ResponseBo.error("数据导入错误，请联系网站管理员！");
	}

	@PostMapping("/get")
	public ResponseBo getInitialData(@RequestParam Long trainId, @RequestParam Date startDate, @RequestParam Date endDate) {

		try {
			List<InitialData> list = initialDataService.findByTrainInfoAndTime(trainId, startDate, endDate);
			return ResponseBo.ok(list);
		} catch (Exception e) {
			log.error("获取用户失败", e);
			return ResponseBo.error("获取用户失败，请联系网站管理员！");
		}
	}

	@InitBinder
	public void initBinder(WebDataBinder binder, WebRequest request) {

		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	}

}
