package com.github.zw201913.aspect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.github.zw201913.annotation.FileUpload;
import com.github.zw201913.common.ServiceException;
import com.github.zw201913.enumeration.ExceptionType;
import com.github.zw201913.helper.ResponseHelper;
import com.github.zw201913.model.FileSaveResult;
import com.github.zw201913.model.UploadResults;
import com.github.zw201913.util.EncryDigestUtil;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Aspect
@Order(10)
@Component("com.github.zw201913.aspect.FileAspect")
public class FileAspect {
	
	@Value("${fileSaveDir}")
	private String fileSaveDir;

	@Pointcut("execution(public * com.github.zw201913.controller.*.*.*(..))")
	public void check() {

	}

	@Around("check()")
	public Object arround(ProceedingJoinPoint pjp) throws Throwable {
		log.debug("检查文件");
		boolean autoSave = false;// 是否启用自动保存
		boolean digest = false;// 是否对文件进行摘要

		Signature signature = pjp.getSignature();
		// 获取类上的注解

		MethodSignature methodSignature = (MethodSignature) signature;
		Method targetMethod = methodSignature.getMethod();
		if (targetMethod.isAnnotationPresent(FileUpload.class)) {
			FileUpload methodAnnotation = targetMethod.getDeclaredAnnotation(FileUpload.class);
			autoSave = methodAnnotation.autoSave();
			digest = methodAnnotation.digest();
		}
		Parameter[] ps = targetMethod.getParameters();
		Object[] objs = pjp.getArgs();
		Map<String, FileObj> fileMaps = Maps.newHashMap();
		for (int i = 0; i < ps.length; i++) {
			Parameter p = ps[i];
			if ((p.isAnnotationPresent(FileUpload.class) && p.getDeclaredAnnotation(FileUpload.class).autoSave())
					|| (!p.isAnnotationPresent(FileUpload.class) && autoSave)) {
				FileUpload fieldFileUpload = p.getDeclaredAnnotation(FileUpload.class);
				FileObj obj = new FileObj();
				if (fieldFileUpload != null) {
					obj.setDigest(fieldFileUpload.digest());
				} else {
					obj.setDigest(digest);
				}
				obj.setFiles(objs[i]);
				if (p.isAnnotationPresent(RequestPart.class)) {
					RequestPart requestPart = p.getDeclaredAnnotation(RequestPart.class);
					fileMaps.put(requestPart.value(), obj);
				} else if (p.isAnnotationPresent(RequestParam.class)) {
					RequestParam requestParam = p.getDeclaredAnnotation(RequestParam.class);
					fileMaps.put(requestParam.value(), obj);
				}
			}
		}
		UploadResults result = new UploadResults();
		try {
			for (Entry<String, FileObj> e : fileMaps.entrySet()) {
				String key = e.getKey();
				FileObj value = e.getValue();
				Object obj = value.getFiles();
				if (obj instanceof MultipartFile[]) {
					result.put(key, saveMultipartFiles(key, (MultipartFile[]) obj, value.isDigest()));
				} else if (obj instanceof MultipartFile) {
					result.put(key, saveMultipartFile(key, (MultipartFile) obj, value.isDigest()));
				}
			}
		} catch (ServiceException e) {
			return ResponseHelper.build(e.getType(), null);
		}
		for (int n = 0; n < objs.length; n++) {
			Object obj = objs[n];
			if (obj instanceof UploadResults) {
				objs[n] = result;
			}
		}
		return pjp.proceed(objs);
	}

	/**
	 * 保存文件数组
	 * 
	 * @param files
	 * @return
	 * @throws ServiceException
	 */
	private List<FileSaveResult> saveMultipartFiles(String key, MultipartFile[] files, boolean digest)
			throws ServiceException {
		List<FileSaveResult> res = Lists.newArrayList();
		try {
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				InputStream inputStream = file.getInputStream();
				String originalFileName = file.getOriginalFilename();
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				String fileName = fileSaveDir + File.separator + uuid + File.separator + originalFileName;
				if (digest) {
					uuid = EncryDigestUtil.md5File(inputStream);
					String file_suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
					fileName = fileSaveDir + File.separator + uuid + File.separator + uuid + file_suffix;
				}
				inputStream = file.getInputStream();
				File targetFile = saveFileFromInputStream(inputStream, fileSaveDir + File.separator + uuid, fileName,
						digest);
				FileSaveResult result = new FileSaveResult();
				result.setId(uuid);
				result.setIdIsDigest(digest);
				result.setKey(key);
				result.setMultipartFile(file);
				result.setTargetFile(targetFile);
				result.setIndex(i);
				res.add(result);
			}
			return res;
		} catch (Exception e) {
			throw new ServiceException(ExceptionType.FILE_SAVE_EXCEPTON);
		}
	}

	/**
	 * 保存单个文件
	 * 
	 * @param file
	 * @return
	 * @throws ServiceException
	 */
	private List<FileSaveResult> saveMultipartFile(String key, MultipartFile file, boolean digest)
			throws ServiceException {
		List<FileSaveResult> res = Lists.newArrayList();
		try {
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			String originalFileName = file.getOriginalFilename();
			InputStream inputStream = file.getInputStream();
			String fileName = fileSaveDir + File.separator + uuid + File.separator + originalFileName;
			if (digest) {
				uuid = EncryDigestUtil.md5File(inputStream);
				String file_suffix = originalFileName.substring(originalFileName.lastIndexOf("."));
				fileName = fileSaveDir + File.separator + uuid + File.separator + uuid + file_suffix;
			}
			inputStream = file.getInputStream();
			File targetFile = saveFileFromInputStream(inputStream, fileSaveDir + File.separator + uuid, fileName, digest);
			FileSaveResult result = new FileSaveResult();
			result.setId(uuid);
			result.setIdIsDigest(digest);
			result.setKey(key);
			result.setMultipartFile(file);
			result.setTargetFile(targetFile);
			result.setIndex(0);
			res.add(result);
			return res;
		} catch (Exception e) {
			throw new ServiceException(ExceptionType.FILE_SAVE_EXCEPTON);
		}
	}

	/**
	 * 文件存储
	 * 
	 * @param stream
	 * @param path
	 * @param filename
	 * @throws ServiceException
	 */
	private File saveFileFromInputStream(InputStream stream, String dirPath, String filename, boolean digest)
			throws ServiceException {
		if (stream == null) {
			return null;
		}
		File dir = null;
		try {
			dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(filename);
			if (digest && file.exists() && file.isFile()) {
				return file;
			}
			FileOutputStream fs = new FileOutputStream(filename);
			byte[] buffer = new byte[1024 * 1024];
			int bytesum = 0;
			int byteread = 0;
			while ((byteread = stream.read(buffer)) != -1) {
				bytesum += byteread;
				fs.write(buffer, 0, byteread);
				fs.flush();
			}
			fs.close();
			stream.close();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e.getMessage());
			// 谁出问题就删谁
			if (dir.exists()) {
				dir.delete();
			}
			throw new ServiceException(ExceptionType.FILE_SAVE_EXCEPTON);
		}
	}

	@Data
	private class FileObj {
		private Object files;
		private boolean digest;
	}
}
