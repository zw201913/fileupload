package com.github.zw201913.enumeration;

public enum ExceptionType {
	SUCCESS("200", true, "success"),
	FILURE("-99", true, "请稍后再试"),
	UNKNOW_EXCEPTION("-1", false, "未知异常"),
	FILE_DOWNLOAD_FAIL_EXCEPTION("-20003",true,"下载失败"),
	FILE_NOT_EXISTS_EXCEPTION("-20002",true,"文件不存在"),
	FILE_SAVE_EXCEPTON("-20000", true, "文件保存失败"),
	FILE_NAME_COUNT_NOT_FORMAT("-20001", true, "文件名和文件数量不匹配"),
	JSON_PROCESS_ERROR("-100002", false, "json转换异常"),
    MD5_EXCEPTION("-499", false, "MD5加密异常");
	

	private final String code;

	private final boolean show;

	private final String message;

	ExceptionType(String code, boolean show, String message) {
		this.code = code;
		this.show = show;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public boolean isShow() {
		return show;
	}
}
