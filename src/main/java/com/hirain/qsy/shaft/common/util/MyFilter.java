package com.hirain.qsy.shaft.common.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用注解标注过滤器
 * 
 * @WebFilter将一个实现了javax.servlet.Filter接口的类定义为过滤器
 * 												属性filterName声明过滤器的名称,可选
 *                                                属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);

	@Override
	public void destroy() {

		LOGGER.info("-----------过滤器销毁---------");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		// if(isAjax(req)){
		res.setContentType("textml;charset=UTF-8");
		res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "0");
		res.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("XDomainRequestAllowed", "1");
		// }

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {

		LOGGER.info("过滤器初始化");
	}

	// /**
	// * 判断是否是ajax请求
	// * @param request
	// * @return
	// */
	// private boolean isAjax(HttpServletRequest request) {
	//
	// String header = request.getHeader("x-requested-with");
	// if (null != header && "XMLHttpRequest".endsWith(header)) {
	// return true;
	// }
	// return false;
	// }

}
