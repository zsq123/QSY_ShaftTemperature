package com.hirain.qsy.shaft.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hirain.qsy.shaft.common.controller.BaseController;
import com.hirain.qsy.shaft.common.model.QueryRequest;
import com.hirain.qsy.shaft.common.model.ResponseBo;
import com.hirain.qsy.shaft.model.SysLog;
import com.hirain.qsy.shaft.service.LogService;

/**
 * @Version 1.0
 * @Author changwei.zheng@hirain.com
 * @Created 2019年4月8日 上午10:47:02
 * @Description
 *              <p>
 *              日志controller
 * @Modification
 *               <p>
 *               Date Author Version Description
 *               <p>
 *               2019年4月8日 changwei.zheng@hirain.com 1.0 create file
 */
@RestController
@RequestMapping("/log")
public class LogController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LogService logService;

	@RequestMapping("/list")
	@RequiresAuthentication
	public ResponseBo logList(QueryRequest request, SysLog log) {
		return ResponseBo.ok(super.selectByPageNumSize(request, () -> this.logService.findAllLogs(log)));
	}

	@RequestMapping("/delete")
	@RequiresAuthentication
	public ResponseBo deleteLogss(String ids) {
		try {
			this.logService.deleteLogs(ids);
			return ResponseBo.ok("删除日志成功！");
		} catch (Exception e) {
			logger.error("删除日志失败", e);
			return ResponseBo.error("删除日志失败，请联系网站管理员！");
		}
	}
}
