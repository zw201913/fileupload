package com.github.zw201913.common;

import com.github.zw201913.enumeration.ExceptionType;

public class ControllerException extends ProjectException {

	private static final long serialVersionUID = 5692177621394546993L;

	public ControllerException(ExceptionType type) {
		super(type);
	}

	public ControllerException(ProjectException exception) {
		super(exception.getType());
	}
}
