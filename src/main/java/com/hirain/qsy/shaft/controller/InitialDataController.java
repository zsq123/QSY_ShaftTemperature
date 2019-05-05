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
import com.hirain.qsy.shaft.model.AttributeMappingConfigurationData;
import com.hirain.qsy.shaft.model.InitialAxleData;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.InitialDataService;
import com.hirain.qsy.shaft.service.TrainInfoService;

@RequestMapping("/initialdata")
@RestController
public class InitialDataController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InitialDataService initialDataService;

	@Autowired
	TrainInfoService trainInfoService;

	@Log("上传文件")
	@PostMapping("/uploadfile")
	public ResponseBo saveInitialData(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
		AttributeMappingConfigurationData Data = new AttributeMappingConfigurationData("mapData");
		File targetFile = null;
		try {

			if (file != null) {
				String originalFilename = URLDecoder.decode(file.getOriginalFilename(), "uft-8");

				String realPath = request.getSession().getServletContext().getRealPath("/");

				targetFile = new File(realPath, originalFilename);

				file.transferTo(targetFile);

				// 获取温度信息
				List<InitialData> list = initialDataService.initialDatas(realPath + "/" + originalFilename, "com.hirain.qsy.shaft.model.InitialData",
						"mapData", Data.getMapData());

				// 测试python接口数据
				// DataToPythonUtils pythonUtils=new DataToPythonUtils();
				// String
				// ssString=pythonUtils.objectToPythonJson(initialDataService.getExcelData());
				// System.out.print(ssString);

				// 获取机车类型以及id
				List<TrainInfo> listTrainInfos = trainInfoService.mapDataToTrainObject(initialDataService.getTrainInfos());
				try {
					if (list.size() > 800) {
						int multData = list.size() / 800;
						for (int i = 1; i <= multData; i++) {
							List<InitialData> subListInitial = list.subList(800 * (i - 1), 800 * (i));
							this.initialDataService.save(subListInitial);
						}
						List<InitialData> subListInitial = list.subList(800 * multData, list.size());
						this.initialDataService.save(subListInitial);
						targetFile.delete();
						// return ResponseBo.ok("数据导入成功！");
					} else {
						if (this.initialDataService.save(list) > 0) {
							targetFile.delete();
							// return ResponseBo.ok("数据导入成功！");
						}
					}
					if (listTrainInfos.size() > 0) {
						trainInfoService.saveTrainInfor(listTrainInfos);
					}
					return ResponseBo.ok("数据导入成功！");

				} catch (Exception e) {
					targetFile.delete();
					return ResponseBo.error("数据导入错误，请联系网站管理员！");
					// TODO: handle exception
				}
			}

		} catch (IllegalStateException e) {
			targetFile.delete();
			log.error("数据导入失败", e);
		}
		targetFile.delete();
		return ResponseBo.error("数据导入错误，请联系网站管理员！");
	}

	@PostMapping("/getdata")
	public ResponseBo getInitialData(@RequestParam Integer trainId, @RequestParam String startDate, @RequestParam String endDate) {

		try {
			List<List<InitialAxleData>> list = initialDataService.findByTrainInfoAndTime(trainId, startDate, endDate);
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
