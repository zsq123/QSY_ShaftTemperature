/*******************************************************************************
 * Copyright (c) 2019, 2019 Hirain Technologies Corporation.
 ******************************************************************************/
package com.hirain.qsy.shaft.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.TrainInfoService;

/**
 * @Version 1.0
 * @Author shiqiang.zhu@hirain.com
 * @Created 2019年4月8日 上午11:21:53
 * @Description
 *              <p>
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 shiqiang.zhu@hirain.com 1.0 create file
 */

@RequestMapping("/traininfo")
@RestController
public class TrainInfoController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TrainInfoService trainInfoService;

	@PostMapping("/trainType")
	public ResponseBo getTrainType() {
		try {
			List<String> list = this.trainInfoService.getAllTrainType();
			return ResponseBo.ok(list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取车辆类型信息失败", e);
			return ResponseBo.error("获取车辆类型信息失败，请联系网站管理员！");
		}

	}

	/**
	 * 通过车辆类型查询所有的车号
	 * 
	 * @param trainType
	 * @return
	 */
	@PostMapping("/trainNum")
	public ResponseBo getTrainNumByTrainType(@RequestParam String trainType) {
		try {
			List<String> list = this.trainInfoService.queryTrainNumByType(trainType);
			return ResponseBo.ok(list);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("获取车辆号信息失败", e);
			return ResponseBo.error("获取车辆号信息失败，请联系网站管理员！");
		}

	}

	@GetMapping("/getTrainInfos")
	public ResponseBo getTrainInfos() {
		return ResponseBo.ok(trainInfoService.findAllGroupTrainType());
	}

}
