package com.github.zw201913.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UploadResults {

	private Map<String, List<FileSaveResult>> resultMap = new HashMap<>();

	/**
	 * 分组数量
	 * 
	 * @return
	 */
	public int getGroupSize() {
		return resultMap.size();
	}
	
	/**
	 * 获取分组
	 * @param groupName
	 * @return
	 */
	public List<FileSaveResult> getGroup(String groupName) {
		return resultMap.get(groupName);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public List<FileSaveResult> put(String key, List<FileSaveResult> value) {
		return resultMap.put(key, value);
	}
}
