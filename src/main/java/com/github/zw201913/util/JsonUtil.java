package com.github.zw201913.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.zw201913.common.UtilException;
import com.github.zw201913.enumeration.DateFormatPattern;
import com.github.zw201913.enumeration.ExceptionType;

import lombok.extern.log4j.Log4j2;

@Log4j2
public final class JsonUtil {

	/**
	 * 私有构造函数，不能被new
	 */
	private JsonUtil() {

	}
	
	/**
	 * mapper : 转换器
	 */
	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		objectMapper.setDateFormat(defineDateFormat(DateFormatPattern.DEFAULT_YYYY_MM_DD_HH_MM));
	}

	/**
	 * 设置时间转换格式
	 * 
	 * @param pattern
	 * @return
	 */
	private static DateFormat defineDateFormat(DateFormatPattern pattern) {
		return new SimpleDateFormat(pattern.getPattern());
	}

	/**
	 * 将对象转换成json字符串
	 * 
	 * @param data
	 * @return
	 * @throws UtilException
	 */
	public static <T> String getJsonString(T data) throws UtilException {
		return getJsonString(data, null);
	}

	/**
	 * 
	 * @param data
	 * @param pattern
	 * @return
	 * @throws UtilException
	 */
	public static <T> String getJsonString(T data, DateFormatPattern pattern) throws UtilException {
		if (pattern != null && !StringUtils.isEmpty(pattern.getPattern())) {
			objectMapper.setDateFormat(defineDateFormat(pattern));
		}
		try {
			return objectMapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			log.debug(ExceptionUtils.getStackTrace(e));
			throw new UtilException(ExceptionType.JSON_PROCESS_ERROR);
		}
	}

	/**
	 * 把字符串转换成对象
	 * 
	 * @param jsonString
	 * @param clazz
	 * @return
	 * @throws UtilException
	 */
	public static <T> T parseStringToObject(String jsonString, Class<T> clazz) throws UtilException {
		return parseStringToObject(jsonString, clazz, null);
	}

	/**
	 * 把字符串转换成对象
	 * 
	 * @param jsonString
	 * @param clazz
	 * @param pattern
	 * @return
	 * @throws UtilException
	 */
	public static <T> T parseStringToObject(String jsonString, Class<T> clazz, DateFormatPattern pattern)
			throws UtilException {
		if (pattern != null && !StringUtils.isEmpty(pattern.getPattern())) {
			objectMapper.setDateFormat(defineDateFormat(pattern));
		}
		try {
			return objectMapper.readValue(jsonString, clazz);
		} catch (Exception e) {
			log.debug(ExceptionUtils.getStackTrace(e));
			throw new UtilException(ExceptionType.JSON_PROCESS_ERROR);
		}
	}

}
