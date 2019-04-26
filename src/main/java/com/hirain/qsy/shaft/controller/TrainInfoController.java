package com.hirain.qsy.shaft.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.service.TrainInfoService;

@RestController
@RequestMapping("/info")
public class TrainInfoController {

	@Autowired
	TrainInfoService trainInfoService;

	@GetMapping("/getTrainInfos")
	public ResponseBo getTrainInfos() {
		return ResponseBo.ok(trainInfoService.findAllGroupTrainType());
	}
}
