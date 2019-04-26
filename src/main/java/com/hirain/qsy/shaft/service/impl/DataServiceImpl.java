package com.hirain.qsy.shaft.service.impl;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hirain.qsy.shaft.common.model.DataRequest;
import com.hirain.qsy.shaft.common.model.DataResponse;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.model.InitialData;
import com.hirain.qsy.shaft.model.TrainInfo;
import com.hirain.qsy.shaft.service.DataService;
import com.hirain.qsy.shaft.service.InitialDataService;
import com.hirain.qsy.shaft.service.TrainInfoService;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	InitialDataService initialDataService;

	@Autowired
	TrainInfoService trainInfoService;

	public Map<String, Object> getList(QueryRequest request, DataRequest dataRequest) {

		List<TrainInfo> trainInfoList = trainInfoService.findList(request, dataRequest);
		List<DataResponse> dataResponses = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		for (TrainInfo trainInfo : trainInfoList) {
			List<InitialData> initialDataList = initialDataService.findByTrainInfoAndTime(trainInfo.getTrainNum(), dataRequest.getStartTime(),
					dataRequest.getEndTime());
			if (initialDataList.size() > 0) {
				DataResponse dataResponse = new DataResponse();
				dataResponse.setTrainNum(trainInfo.getTrainNum());
				dataResponse.setTrainType(trainInfo.getTrainType());
				dataResponse.setEarliestTime(initialDataList.get(0).getAcquisitionTime());
				dataResponse.setLatestTime(initialDataList.get(initialDataList.size() - 1).getAcquisitionTime());
				dataResponses.add(dataResponse);
			}
		}
		map.put("rows", dataResponses);
		map.put("total", trainInfoList.size());
		return map;
	}

	@Override
	public void delete(Long[] trainNums, Date deadline) {
		initialDataService.deleteByTrainNumAndTime(trainNums, deadline);

	}

	@Override
	public Map<String, Object> getStorage() {
		Long total = 0L;
		Long free = 0L;
		File[] listRoots = File.listRoots();
		for (File file : listRoots) {
			total += file.getTotalSpace();
			free += file.getFreeSpace();
		}
		DecimalFormat df = new DecimalFormat("#.00");
		String totalString = df.format(total / 1024d / 1024d / 1024d);
		String freeString = df.format(free / 1024d / 1024d / 1024d);
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalString);
		map.put("free", freeString);
		return map;
	}
}
