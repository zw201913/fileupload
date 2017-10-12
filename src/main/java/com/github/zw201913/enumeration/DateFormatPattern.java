package com.github.zw201913.enumeration;

public enum DateFormatPattern {
	DEFAULT_YYYY_MM_DD_HH_MM("yyyy/MM/dd HH:mm");

	private final String pattern;

	DateFormatPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}
}
