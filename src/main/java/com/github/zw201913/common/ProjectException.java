package com.github.zw201913.common;

import com.github.zw201913.enumeration.ExceptionType;

public class ProjectException extends Exception {

	private static final long serialVersionUID = -6782565020039957140L;

	private final ExceptionType type;

	public ProjectException(ExceptionType type) {
		super(type.getMessage());
		this.type = type;
	}

	public ExceptionType getType() {
		return type;
	}

}
