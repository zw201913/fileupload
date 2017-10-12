package com.github.zw201913.common;

import com.github.zw201913.enumeration.ExceptionType;

/**
 * 工具类异常
 * 
 * @author zouwei
 *
 */
public class UtilException extends ProjectException {

	private static final long serialVersionUID = -7025528122447867639L;

	public UtilException(ExceptionType type) {
		super(type);
	}
}
