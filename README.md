# fileupload
文件上传组件
1. 只需要使用@FileUpload这个注解，就可以完成文件的上传存储。
2. @FileUpload这个直接可以同时在方法和参数上使用，示例如下：

  @ResponseBody
	@FileUpload(digest=true)
	@RequestMapping(value = "/fileUp1", method = RequestMethod.POST)
	public String bigFileUpload1(@RequestPart("param") Param param,
			@RequestPart("files1") @FileUpload(digest=false)MultipartFile[] files1,  @RequestPart("files2") MultipartFile[] files2,   UploadResults result){
      //实现业务
      
      }
      
 @FileUpload直接中有两个参数，第一个参数digest设置上传的文件是否需要做摘要，默认是不做摘要；第二个参数autoSave设置文件是否自动保存，默认是自动保 存。
3. UploadResults为文件上传成功后的结果，可以通过getGroup("前端指定的key值")获取指定的上传结果。
4. 需要在配置文件中设置fileSaveDir文件保存路径，该配置文件需要通过spring mvc读入。
