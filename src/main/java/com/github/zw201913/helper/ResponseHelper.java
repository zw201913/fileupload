package com.github.zw201913.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.zw201913.enumeration.ExceptionType;
import com.github.zw201913.util.JsonUtil;
import com.github.zw201913.common.UtilException;
import com.github.zw201913.enumeration.DateFormatPattern;

import lombok.Data;

public final class ResponseHelper {

	/**
	 * 私有构造函数
	 */
	private ResponseHelper() {

	}
	
	/**
	 * 正确的响应结果(没有数据)
	 * 
	 * @return
	 * @throws UtilException
	 */
	public static String ok() throws UtilException {
		return JsonUtil.getJsonString(build(ExceptionType.SUCCESS, null, null, null));
	}
	/**
	 * 
	 * @param content
	 * @return
	 * @throws UtilException
	 */
	public static String okString() {
		return "success";
	}
	/**
	 * 失败
	 * @return
	 * @throws UtilException
	 */
	public static String filure() throws UtilException {
		return JsonUtil.getJsonString(build(ExceptionType.FILURE, null, null, null));
	}
	/**
	 * 失败
	 * @param content
	 * @return
	 * @throws UtilException
	 */
	public static String filureString() {
		return "filure";
	}
	
	/**
	 * 正确的响应结果(包含数据)
	 * 
	 * @param data
	 * @return
	 * @throws JsonProcessingException
	 */
	public static <T> String okToJsonString(T data) throws UtilException {
		return JsonUtil.getJsonString(build(ExceptionType.SUCCESS, data, null, null));
	}

	/**
	 * 正确的响应结果(包含数据,可设置时间格式)
	 * 
	 * @param data
	 * @param pattern
	 * @return
	 * @throws JsonProcessingException
	 */
	public static <T> String okToJsonString(T data, DateFormatPattern pattern) throws UtilException {
		return JsonUtil.getJsonString(build(ExceptionType.SUCCESS, data, null, pattern));
	}

	/**
	 * 创建一个错误的响应结果
	 * 
	 * @param code
	 * @param errorMsg
	 * @return
	 * @throws JsonProcessingException
	 */
	public static <S> String build(ExceptionType type, S errorMsg) throws UtilException {
		return JsonUtil.getJsonString(build(type, null, errorMsg, null));
	}

	/**
	 * 创建一个错误的响应结果(可设置时间格式)
	 * 
	 * @param code
	 * @param errorMsg
	 * @param pattern
	 * @return
	 * @throws JsonProcessingException
	 */
	public static <S> String build(ExceptionType type, S errorMsg, DateFormatPattern pattern) throws UtilException {
		return JsonUtil.getJsonString(build(type, null, errorMsg, pattern));
	}

	/**
	 * 创建一个响应数据
	 * 
	 * @param code
	 * @param data
	 * @param errorMsg
	 * @param pattern
	 * @return
	 * @throws UtilException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static <T, S> String build(ExceptionType type, T data, S errorMsg, DateFormatPattern pattern)
			throws UtilException {
		Response res = new Response();
		res.setCode(type.getCode());
		res.setData(data);
		res.setShow(type.isShow());
		if (errorMsg != null) {
			res.setErrorMsg(errorMsg);
		} else {
			res.setErrorMsg(type.getMessage());
		}
		return JsonUtil.getJsonString(res, pattern);
	}

	/**
	 * 响应实体类
	 * 
	 * @author zouwei
	 *
	 * @param <T>
	 */
	@Data
	private static class Response<T, S> {
		private String code;
		private T data;
		private S errorMsg;
		private boolean show;
	}
}
