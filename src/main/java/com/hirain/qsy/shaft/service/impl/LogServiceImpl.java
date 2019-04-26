package com.hirain.qsy.shaft.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hirain.qsy.shaft.common.annotation.Log;
import com.hirain.qsy.shaft.model.SysLog;
import com.hirain.qsy.shaft.service.LogService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("logService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends BaseService<SysLog> implements LogService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ObjectMapper objectMapper;

	@Override
	public List<SysLog> findAllLogs(SysLog log) {
		try {
			Example example = new Example(SysLog.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(log.getUsername())) {
				criteria.andCondition("username like", "%" + log.getUsername().toLowerCase() + "%");
			}
			if (StringUtils.isNotBlank(log.getOperation())) {
				criteria.andCondition("operation like", "%" + log.getOperation() + "%");
			}
			if (StringUtils.isNotBlank(log.getIp())) {
				criteria.andCondition("ip like", "%" + log.getIp() + "%");
			}
			if (StringUtils.isNotBlank(log.getStartTime())) {
				criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d %H:%i:%s') >=", log.getStartTime());
			}
			if (StringUtils.isNotBlank(log.getEndTime())) {
				criteria.andCondition("date_format(CREATE_TIME,'%Y-%m-%d %T') <=", log.getEndTime());
			}
			example.setOrderByClause("create_time desc");
			return this.selectByExample(example);
		} catch (Exception e) {
			logger.error("获取系统日志失败", e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public void deleteLogs(String logIds) {
		List<String> list = Arrays.asList(logIds.split(","));
		this.batchDelete(list, "id", SysLog.class);
	}

	@Override
	public void saveLog(ProceedingJoinPoint joinPoint, SysLog log) throws JsonProcessingException {

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Log logAnnotation = method.getAnnotation(Log.class);
		if (logAnnotation != null) {
			// 注解上的描述
			log.setOperation(logAnnotation.value());
		}
		// 请求的类名
		String className = joinPoint.getTarget().getClass().getName();
		// 请求的方法名
		String methodName = signature.getName();
		log.setMethod(className + "." + methodName + "()");
		// 请求的方法参数值
		Object[] args = joinPoint.getArgs();
		// 请求的方法参数名称
		LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
		String[] paramNames = u.getParameterNames(method);
		if (args != null && paramNames != null) {
			StringBuilder params = new StringBuilder();
			params = handleParams(params, args, Arrays.asList(paramNames));
			log.setParams(params.toString());
		}
		log.setCreateTime(new Date());

		// 保存系统日志
		save(log);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) throws JsonProcessingException {
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof Map) {
				Set set = ((Map) args[i]).keySet();
				List list = new ArrayList();
				List paramList = new ArrayList<>();
				for (Object key : set) {
					list.add(((Map) args[i]).get(key));
					paramList.add(key);
				}
				return handleParams(params, list.toArray(), paramList);
			} else {
				if (args[i] instanceof MultipartFile) {
					MultipartFile file = (MultipartFile) args[i];
					params.append("  ").append(paramNames.get(i)).append(": ").append(file.getOriginalFilename());
				} else if (args[i] instanceof Long[]) {
					params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(traversalArray(args[i])));
				} else if (args[i] instanceof Date) {
					params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(formatDate(args[i])));
				} else if (args[i] instanceof String) {
					params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
				} else if (args[i] instanceof Serializable) {
					params.append("  ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(toString(args[i])));
				} else {
					params.append("  ").append(paramNames.get(i)).append(": ").append(args[i]);
				}
			}
		}
		return params;
	}

	/**
	 * 遍历数组
	 * 
	 * @param object
	 * @return
	 */
	private String traversalArray(Object object) {
		StringBuffer buffer = new StringBuffer();
		Long[] values = (Long[]) object;
		for (Long value : values) {
			buffer.append(value);
			buffer.append(",");
		}
		// 删除最后一个逗号
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}

	private String formatDate(Object object) {
		// 转换日期
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = dateFormat.format(object);
		return format;
	}

	private String toString(Object obj) {
		StringBuffer buffer = new StringBuffer();
		// buffer.append(obj.getClass().getName());
		buffer.append("[");
		List<Field> fieldList = new ArrayList<>();
		Class<? extends Object> tempClass = obj.getClass();
		// 当父类为null的时候说明到达了最上层的父类(Object类).
		while (tempClass != null) {
			fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
			// 得到父类,然后赋给自己
			tempClass = tempClass.getSuperclass();
		}
		for (Field f : fieldList) {
			f.setAccessible(true);
			String name = f.getName();
			try {
				Object value = f.get(obj);
				if (value != null && StringUtils.isNotBlank(value.toString()) && !"serialVersionUID".equals(name)) {
					// 当value为集合且集合不为空时才添加
					if (value instanceof List) {
						List<?> list = (List<?>) value;
						if (list.size() != 0) {
							buffer.append(name + "=" + value);
							buffer.append(",");
						}
					} else {
						buffer.append(name + "=" + value);
						buffer.append(",");
					}
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		// 删除最后一个逗号
		if (buffer.indexOf(",") != -1) {
			buffer.deleteCharAt(buffer.length() - 1);
		}
		buffer.append("]");
		return buffer.toString();
	}
}
