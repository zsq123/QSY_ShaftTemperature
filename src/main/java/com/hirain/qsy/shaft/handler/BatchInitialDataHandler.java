package com.hirain.qsy.shaft.handler;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.hirain.qsy.shaft.model.InitialData;

public class BatchInitialDataHandler {
	
	@SuppressWarnings("unchecked")
	public String batDataAdd(Map<?, ?> map) {
		List<InitialData> initialDatas= (List<InitialData>) map.get("list");
		StringBuilder sb = new StringBuilder();
        sb.append("insert ignore  into t_initial_data(train_id, acquisition_time, create_time, ambient_temperature_1, "
        		+ "ambient_temperature_2, generator_temperature, fan1_temperature, fan2_temperature, axle1_1, axle1_2, axle1_3, "
        		+ "axle1_4, axle1_5, axle1_6, axle2_1, axle2_2, axle2_3, axle2_4, axle2_5,  axle2_6,axle3_1, axle3_2, axle3_3, axle3_4, axle3_5,axle3_6, axle4_1, "
        		+ "axle4_2, axle4_3, axle4_4, axle4_5,axle4_6, axle5_1, axle5_2, axle5_3, axle5_4, axle5_5,axle5_6, axle6_1, axle6_2, axle6_3, axle6_4, "
        		+ "axle6_5,axle6_6, max_temp, max_temp_point, alarm_point, alarm_sign, alarm_code, alarm_time, alarm_point_temp, "
        		+ "gps_longitude, gps_latitude, gps_speed, original_primary_key) values ");
        MessageFormat mf = new MessageFormat(
                "(#'{'list[{0}].trainId},#'{'list[{0}].acquisitionTime},#'{'list[{0}].createTime},#'{'list[{0}].ambientTemperature1},"
                + "#'{'list[{0}].ambientTemperature2},#'{'list[{0}].generatorTemperature},#'{'list[{0}].fan1Temperature},#'{'list[{0}].fan2Temperature},#'{'list[{0}].axle11},"
                + "#'{'list[{0}].axle12},#'{'list[{0}].axle13},#'{'list[{0}].axle14},#'{'list[{0}].axle15},#'{'list[{0}].axle16},"
                + "#'{'list[{0}].axle21},#'{'list[{0}].axle22},#'{'list[{0}].axle23},#'{'list[{0}].axle24},#'{'list[{0}].axle25},#'{'list[{0}].axle26},"
                + "#'{'list[{0}].axle31},#'{'list[{0}].axle32},#'{'list[{0}].axle33},#'{'list[{0}].axle34},#'{'list[{0}].axle35},#'{'list[{0}].axle36},"
                + "#'{'list[{0}].axle41},#'{'list[{0}].axle42},#'{'list[{0}].axle43},#'{'list[{0}].axle44},#'{'list[{0}].axle45},#'{'list[{0}].axle46},"
                + "#'{'list[{0}].axle51},#'{'list[{0}].axle52},#'{'list[{0}].axle53},#'{'list[{0}].axle54},#'{'list[{0}].axle55},#'{'list[{0}].axle56},"
                + "#'{'list[{0}].axle61},#'{'list[{0}].axle62},#'{'list[{0}].axle63},#'{'list[{0}].axle64},#'{'list[{0}].axle65},#'{'list[{0}].axle66},"
                + "#'{'list[{0}].maxTemp},#'{'list[{0}].maxTempPoint},#'{'list[{0}].alarmPoint},#'{'list[{0}].alarmSign},#'{'list[{0}].alarmCode},"
                + "#'{'list[{0}].alarmTime},#'{'list[{0}].alarmPointTemp},#'{'list[{0}].gpsLongitude},#'{'list[{0}].gpsLatitude},#'{'list[{0}].gpsSpeed},#'{'list[{0}].originalprimarykey})"
        );
        for (int i = 0; i < initialDatas.size(); i++) {
            sb.append(mf.format(new Object[]{i}));
            if (i < initialDatas.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
	}

}
