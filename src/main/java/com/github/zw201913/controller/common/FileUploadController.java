package com.github.zw201913.controller.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.github.zw201913.annotation.FileUpload;
import com.github.zw201913.common.ControllerException;
import com.github.zw201913.common.UtilException;
import com.github.zw201913.enumeration.ExceptionType;
import com.github.zw201913.helper.ResponseHelper;
import com.github.zw201913.model.FileSaveResult;
import com.github.zw201913.model.UploadResults;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequestMapping("/fileUpload")
public class FileUploadController {

	@Value("${fileSaveDir}")
	private String fileSaveDir;
	
	@Autowired
	private HttpServletResponse response;

	/**
	 * 
	 * @param name
	 *            文件名
	 * @param total
	 *            总片数
	 * @param index
	 *            当前片索引
	 * @param shardSize
	 *            每片大小
	 * @param file
	 *            当前片内容
	 * @return
	 * @throws UtilException
	 * @throws ControllerException
	 */

	@ResponseBody
	@RequestMapping(value = "/fileUp", method = RequestMethod.POST)
	public String bigFileUpload(@RequestParam("name") String name, @RequestParam("sign") String sign,
			@RequestParam("email") String email, @RequestParam("password") String password,
			@RequestParam("file1") @FileUpload MultipartFile[] files, UploadResults result) throws ControllerException {
		try {
			List<FileSaveResult> list1 = result.getGroup("file1");
			Map<String, Object> res = Maps.newHashMap();
			log.debug("-------------------name:  " + name + "------------");
			log.debug("-------------------sign:  " + sign + "------------");
			log.debug("-------------------email:  " + email + "------------");
			log.debug("-------------------password:  " + password + "------------");
			Map<String, String> params = Maps.newHashMap();
			params.put("name", name);
			params.put("sign", sign);
			params.put("email", email);
			params.put("password", password);
			res.put("params", params);
			
			return ResponseHelper.okToJsonString(res);
		} catch (UtilException e) {
			throw new ControllerException(e);
		}

	}

	@ResponseBody
	@FileUpload(digest=true)
	@RequestMapping(value = "/fileUp1", method = RequestMethod.POST)
	public String bigFileUpload1(@RequestPart("param") Param param,
			@RequestPart("files1") @FileUpload(digest=false)MultipartFile[] files1,  @RequestPart("files2") MultipartFile[] files2, UploadResults result) throws ControllerException {
		try {
			List<FileSaveResult> list1 = result.getGroup("files1");
			List<FileSaveResult> list2 = result.getGroup("files2");
			
			Map<String, Object> res = Maps.newHashMap();
			log.debug("-------------------name:  " + param.getName() + "------------");
			log.debug("-------------------sign:  " + param.getSign() + "------------");
			log.debug("-------------------email:  " + param.getEmail() + "------------");
			log.debug("-------------------password:  " + param.getPassword() + "------------");
			Map<String, String> params = Maps.newHashMap();
			params.put("name", param.getName());
			params.put("sign", param.getSign());
			params.put("email", param.getEmail());
			params.put("password", param.getPassword());
			res.put("params", params);
			if (files1 != null) {
				log.debug("-------------------文件数量:  " + files1.length + "个文件------------");
			} else {
				log.debug("-------------------文件数量:  " + 0 + "个文件------------");
			}
			if (files2 != null) {
				log.debug("-------------------文件数量:  " + files2.length + "个文件------------");
			} else {
				log.debug("-------------------文件数量:  " + 0 + "个文件------------");
			}
			return ResponseHelper.okToJsonString(res);
		} catch (UtilException e) {
			throw new ControllerException(e);
		}
	}

	
	@RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
	public void download(@PathVariable("fileId") String fileId) throws ControllerException {
		String dirPath = fileSaveDir + File.separator + fileId;
		File dir = new File(dirPath);
		if (!dir.exists()) {
			throw new ControllerException(ExceptionType.FILE_NOT_EXISTS_EXCEPTION);
		}
		String[] filenames = dir.list();
		if (filenames == null || filenames.length <= 0) {
			throw new ControllerException(ExceptionType.FILE_NOT_EXISTS_EXCEPTION);
		}
		File file = new File(dirPath + File.separator + filenames[0]);
		try {
			// 设置文件mimeType
			String mimeType = Files.probeContentType(file.toPath());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			// 设置文件名字
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					"attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
			response.setContentLength((int) file.length());

			// 写入文件到response
			InputStream in = new FileInputStream(file);
			InputStream inputStream = new BufferedInputStream(in);
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			in.close();
			inputStream.close();
		} catch (IOException e) {
			throw new ControllerException(ExceptionType.FILE_DOWNLOAD_FAIL_EXCEPTION);
		}
	}
}
