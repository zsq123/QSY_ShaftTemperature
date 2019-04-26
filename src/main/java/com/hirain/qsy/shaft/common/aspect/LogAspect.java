package com.hirain.qsy.shaft.common.aspect;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hirain.qsy.shaft.common.util.HttpContextUtils;
import com.hirain.qsy.shaft.common.util.IPUtils;
import com.hirain.qsy.shaft.model.SysLog;
import com.hirain.qsy.shaft.model.User;
import com.hirain.qsy.shaft.service.LogService;

/**
 * AOP 记录用户操作日志
 */
@Aspect
@Component
public class LogAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	LogService logService;

	@Pointcut("@annotation(com.hirain.qsy.shaft.common.annotation.Log)")
	public void pointcut() {

		// do nothing
	}

	@Around("pointcut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		long beginTime = System.currentTimeMillis();
		Object result = null;
		try {
			// 执行方法
			result = joinPoint.proceed();
		} catch (Throwable e) {
			log.error(e.getMessage());
			throw e;
		}
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		SysLog sysLog = new SysLog();
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		sysLog.setUsername(user.getUsername());
		sysLog.setTime(time);
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		String ip = IPUtils.getIpAddr(request);
		sysLog.setIp(ip);
		logService.saveLog(joinPoint, sysLog);
		return result;
	}
}
