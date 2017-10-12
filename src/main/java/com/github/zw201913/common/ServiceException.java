package com.github.zw201913.common;

import com.github.zw201913.enumeration.ExceptionType;

/**
 * 业务层异常
 * 
 * @author zouwei
 *
 */
public class ServiceException extends ProjectException {

	private static final long serialVersionUID = -1533315028552625111L;
	
	
	public ServiceException(ExceptionType type) {
		super(type);
	}
	
	
	public ServiceException(ProjectException exception) {
		super(exception.getType());
	}
}
