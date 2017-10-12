package com.github.zw201913.model;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileSaveResult {
	private String id;
	private boolean idIsDigest;
	private MultipartFile multipartFile;
	private File targetFile;
	private String key;
	private int index;
	
}
