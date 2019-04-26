package com.hirain.qsy.shaft.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hirain.qsy.shaft.common.model.ResponseBo;

@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = UnauthenticatedException.class)
	public Object handleException(HttpServletRequest request, UnauthenticatedException ex) {
		ex.printStackTrace();
		log.error(ex.getMessage());
		return ResponseBo.error("NotLogin");
	}

	@ExceptionHandler(value = Exception.class)
	public Object handleException(HttpServletRequest request, Exception ex) {
		ex.printStackTrace();
		log.error(ex.getMessage());
		return ResponseBo.error("系统异常");
	}

	// private static boolean isAjaxRequest(HttpServletRequest request) {
	//
	// return (request.getHeader("X-Requested-With") != null &&
	// "XMLHttpRequest".equals(request.getHeader("X-Requested-With")));
	// }

}
