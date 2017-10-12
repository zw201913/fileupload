<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>首页</title>
<!-- 新 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/framework/bootstrap/css/bootstrap.min.css">

<!-- 可选的Bootstrap主题文件（一般不用引入） -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/framework/bootstrap/css/bootstrap-theme.css">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/home.css">

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/framework/jquery/jquery-3.2.1.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/framework/bootstrap/js/bootstrap.min.js"></script>



<!-- Constant.js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/Constant.js"></script>
<!-- CommonTool.js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/tools/CommonTool.js"></script>
<!-- RequestClient.js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/network/RequestClient.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/tools/md5.js"></script>
<!-- index.js -->
<script type="text/javascript"
	src="<%=request.getContextPath()%>/assets/js/index.js"></script>

<title>登录</title>
</head>
<body>
	<!-- <h1>后台综合管理平台</h1> -->
	<h1>文件上传demo</h1>
	<!-- 
	<div id="login-container">
		<div id="publicErrorMsg"></div>
		<div class="login-container-left">
			<p>
				<label for="userName">用户名：</label>
			</p>
			<p>
				<label for="password">密&nbsp;&nbsp;&nbsp;码：</label>
			</p>
		</div>
		<div class="login-container-middle">
			<p>
				<input id="userName" type="text" name="userName">
			</p>
			<p>
				<input id="password" type="text" name="password">
			</p>
			<p>
				<a class="login-btn" href="javascript:;">登录</a>
			</p>
		</div>
	</div> -->
	<div>
		<form class="form-horizontal">
			<div class="form-group">
				<label for="name" class="col-sm-2 control-label">name</label>
				<div class="col-sm-6">
					<input type="name" class="form-control" id="name"
						placeholder="name">
				</div>
			</div>
			<div class="form-group">
				<label for="sign" class="col-sm-2 control-label">sign</label>
				<div class="col-sm-6">
					<input type="sign" class="form-control" id="sign"
						placeholder="sign">
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-sm-2 control-label">Email</label>
				<div class="col-sm-6">
					<input type="email" class="form-control" id="email"
						placeholder="Email">
				</div>
			</div>
			<div class="form-group">
				<label for="Password" class="col-sm-2 control-label">Password</label>
				<div class="col-sm-6">
					<input type="password" class="form-control" id="Password"
						placeholder="Password">
				</div>
			</div>
			<div class="form-group">

				<label class="col-sm-2 control-label">File input</label>
				<div class="col-sm-6">
					<input type="file" id="file1" multiple> <input type="file"
						id="file2"> <input type="file" id="file3"> <input
						type="file" id="file4">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-6 col-sm-offset-2">
					<div id="progress" class="progress hide">
						<div id="progress-bar" class="progress-bar" role="progressbar"
							aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
							style="width: 0%;"></div>
					</div>
					<div id="progress1" class="progress hide">
						<div id="progress-bar1" class="progress-bar" role="progressbar"
							aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
							style="width: 0%;"></div>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-6">
					<input type="button" id="upload1" value="上传(文本类型分开上传)" />
					
					<input type="button" id="upload2" value="上传(文本类型整合成对象上传)" />
				</div>
			</div>
			
			<div class="form-group">
			
				<div id="res1" class="col-sm-offset-2 col-sm-6" style="background:white">
				<div>文本类型上传结果：</div>
					
				</div>
				<br>
				<br>
				<br>
				<br>
				<div id="res2" class="col-sm-offset-2 col-sm-6" style="background:white">
				<div>文本对象上传结果</div>
				</div>
			</div>
		</form>
	</div>
	<!-- <div>
		<input id="file2" type="file" /> <input type="button" id="upload"
			value="上传" />
	</div> -->

	<script type="text/javascript">
$(function(){
	  $('#upload1').click(function(){
		  var form = new FormData();
		  form.append("name",$('#name').val());
		  form.append("sign", $('#sign').val())//切分的每一块大小
		  form.append("email", $('#email').val());  //slice方法用于切出文件的一部分
		  form.append("password", $('#Password').val());
		  form.append("file1", $("#file1")[0].files[0]);
		  form.append("file1", $("#file2")[0].files[0]);   
		  form.append("file1", $("#file3")[0].files[0]);    
		  form.append("file1", $("#file4")[0].files[0]);
		  /* form.append("file2", $("#file1")[0].files[0]);
		  form.append("file2", $("#file2")[0].files[0]);   
		  form.append("file2", $("#file3")[0].files[0]);    
		  form.append("file2", $("#file4")[0].files[0]); */

		$.ajax({
		    url: "http://localhost:18080/fileupload/fileUpload/fileUp",
		    type: 'POST',
		    data: form,
		    processData: false,//用来回避jquery对formdata的默认序列化，XMLHttpRequest会对其进行正确处理
		    contentType: false,//设为false才会获得正确的conten-Type
		    xhr: function() { //用以显示上传进度
		        var xhr = $.ajaxSettings.xhr();
		        if (xhr.upload) {
		            xhr.upload.addEventListener('progress', function(e) {
		            	$('#progress').removeClass('hide')
		                $('#progress-bar').attr('style', "width:"+(e.loaded/e.total*100).toFixed(2)+"%");
		                $('#progress-bar').text((e.loaded/e.total*100).toFixed(2)+"%")
		            }, false);
		        }
		        return xhr;
		    },
		    async: true
		}).then(function(data) {
		    if (data.IsSuccessful == false) {
		        alert('上传失败');
		    } else {
		    	var obj = JSON.parse(data).data;
		    	var params = obj['params']
		    	var files = obj['files']
		    	var node="";
		    	$.each(params,function(name,value){
		        	node+="<label>"+name+" : "+value+"</label><br>"
		        });
		        $.each(files,function(name,url){
		        	node+="<a href='"+url+"'>"+name+"</a><br>"
		        });
		        $('#res1').append(node);
		    }
		})
	  });
  
	  
	  $('#upload2').click(function(){
		  var param = {
			  "name":$('#name').val(),
			  "sign":$('#sign').val(),
			  "email":$('#email').val(),
			  "password":$('#Password').val()
		  }
		  
		  var form = new FormData();
		  form.append("param", new Blob([JSON.stringify(param)], {type: "application/json"}));
		  form.append("files1", $("#file4")[0].files[0]);
		  form.append("files1", $("#file3")[0].files[0]);   
		  form.append("files1", $("#file3")[0].files[0]);    
		  form.append("files1", $("#file4")[0].files[0]);
		  form.append("files2", $("#file1")[0].files[0]);
		  form.append("files2", $("#file1")[0].files[0]);   
		  form.append("files2", $("#file2")[0].files[0]);    
		  form.append("files2", $("#file2")[0].files[0]);

		  /* baseRequestFunc('POST',form,"http://localhost:18080/toffee-management/fileUpload/bigfileUp1",true,false,'json',false,function(){
			  
		  },null,function(){
			  
		  }) */
		  
		 $.ajax({
		    url: "http://localhost:18080/fileupload/fileUpload/fileUp1",
		    type: 'POST',
		    data: form,
		    dataType:'json',
		    processData: false,//用来回避jquery对formdata的默认序列化，XMLHttpRequest会对其进行正确处理
		    contentType: false,//设为false才会获得正确的conten-Type
		    xhr: function() { //用以显示上传进度
		        var xhr = $.ajaxSettings.xhr();
		        if (xhr.upload) {
		            xhr.upload.addEventListener('progress', function(e) {
		            	$('#progress1').removeClass('hide')
		                $('#progress-bar1').attr('style', "width:"+(e.loaded/e.total*100).toFixed(2)+"%");
		                $('#progress-bar1').text((e.loaded/e.total*100).toFixed(2)+"%")
		            }, false);
		        }
		        return xhr;
		    },
		    async: true
		}).then(function(data) {
		    if (data.IsSuccessful == false) {
		        alert('上传失败');
		    } else {
		    	var obj = JSON.parse(data).data;
		    	var params = obj['params']
		    	var files = obj['files']
		    	var node="";
		    	$.each(params,function(name,value){
		        	node+="<label>"+name+" : "+value+"</label><br>"
		        });
		        $.each(files,function(name,url){
		        	node+="<a href='"+url+"'>"+name+"</a><br>"
		        });
		        $('#res2').append(node);
		    }
		})
	  }); 
	  
	  /* function baseRequestFunc(type,param,url,async,contentType,dataType,processData,opt_suc,paramType,opt_error) {
          var now_url = url;
          //当把参数作为路由的一部分时此时的参数为字符串;
          /* (paramType&&paramType=='url')?(now_url=cur_url+'/'+param+'.json'):(now_url=cur_url+'.json'); */
          //这个里面是最基本的ajax
          /*$.ajax({
              type:type,
              data:param,
              url:now_url,
              async:async,//默认为true
              contentType:contentType,//默认为application/x-www-form-urlencoded
              dataType:dataType,//默认为预期服务器返回的数据类型
              processData:processData,//默认为true
              success:function(data,textStatus,jqXHR){
                  if($.isFunction(opt_suc)){
                      opt_suc(data,textStatus,jqXHR);
                  }
              },
              error:function(jqXHR,textStatus,errorThrown){
　　　　　　　　　　　　//其实，这里应该处理的更加灵活，比如说可以配置为可以选择使用默认的后端错误提示，或者是使用自己写的错误提示方式
                  renderErrorMsg(jqXHR,textStatus,errorThrown);
                  if($.isFunction(opt_error)){
                      opt_error();
                  }
              },
              xhr: function() { //用以显示上传进度
		        var xhr = $.ajaxSettings.xhr();
		        if (xhr.upload) {
		            xhr.upload.addEventListener('progress', function(e) {
		            	$('#progress1').removeClass('hide')
		                $('#progress-bar1').attr('style', "width:"+(e.loaded/e.total*100).toFixed(2)+"%");
		                $('#progress-bar1').text((e.loaded/e.total*100).toFixed(2)+"%")
		            }, false);
		        }
		        return xhr;
		    }
          })
  
      }
	  
	  }) */
	  })
</script>



	<script type="text/javascript">
      $(function(){
    	  $('#upload').click(function(){
    		  var file = $("#file2")[0].files[0],  //文件对象
              name = file.name,        //文件名
              size = file.size,        //总大小
              succeed = 0;
    		  console.log("文件名:"+name+" 文件大小:"+size)
    		  if(/([\s\S]*[^\.])\.(.*)/.test(name)){
    			  name = RegExp.$1;
    			  fileType = RegExp.$2;
    		  }
    		  
    		  var shardSize = 1024*1024,     //以2MB为一个分片
              shardCount = Math.ceil(size / shardSize);   //总片数
              
    		  var r=new FileReader();
    		  //设置加密的大小
    		  var md5_size = 200*1024*1024;
    		  //先判断文件大小
    		  if(md5_size>size){
    			  r.readAsBinaryString(file);
    		  }else{
    			  r.readAsBinaryString(file.slice(0,md5_size));
    		  }
    		  r.onload=function(e){
    			  var md5 = hex_md5(e.target.result);
    			  
    			  params = {
    					  "md5":md5,
    					  "name":name,
    					  "fileType":fileType,
    					  "size":size
    			  }
    			  
    			  
    			  
    			  $.ajax({
                      url: "http://localhost:18080/toffee-management/fileUpload/getFileIndex",
                      type: "GET",
                      data: params,
                      contentType: "application/x-www-form-urlencoded",
                      dataType:"json",
                      success: function(data){
                          var obj = JSON.parse(data);
                          //计算每一片的起始与结束位置
          	                  /* var start = i * shardSize,
          	                      end = Math.min(size, start + shardSize); */
          	                //构造一个表单，FormData是HTML5新增的
          	                  var form = new FormData();
          	                  form.append("md5",md5);
          	                  form.append("shardSize", shardSize)//切分的每一块大小
          	                  form.append("data", file);  //slice方法用于切出文件的一部分
          	                  form.append("data", file);
          	                  form.append("size", size);
          	                  form.append("total", shardCount);   //总片数
          	                  form.append("index", 1);        //当前是第几片
          	                  
          	                var xhrOnProgress=function(fun) {
          	                  xhrOnProgress.onprogress = fun; //绑定监听
          	                  //使用闭包实现监听绑
          	                  return function() {
          	                    //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
          	                    var xhr = $.ajaxSettings.xhr();
          	                    //判断监听函数是否为函数
          	                    if (typeof xhrOnProgress.onprogress !== 'function'){
          	                      return xhr;
          	                    }
          	                    //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
          	                    if (xhrOnProgress.onprogress && xhr.upload) {
          	                      xhr.upload.onprogress = xhrOnProgress.onprogress;
          	                    }
          	                    return xhr;
          	                  }
          	                }
          	                  
          	                  
          	                //Ajax提交
          	                  $.ajax({
          	                      url: "http://localhost:18080/toffee-management/fileUpload/bigfileUp",
          	                      type: "POST",
          	                      data: form,
          	                      async: true,         //异步
          	                      processData: false,  //很重要，告诉jquery不要对form进行处理
          	                      contentType: false,  //很重要，指定为false才能形成正确的Content-Type
          	                      success: function(data){
          	                          ++succeed;
          	                          console.log(succeed + " / " + shardCount);
          	                          console.log(data);
          	                      },
          	                    xhr:xhrOnProgress(function(e){
          	                      var percent=e.loaded / e.total;//计算百分比
          	                      console.log(percent);
          	                    })
          	                  }); 
          	    		  }
                  });
    		  };
    	  });
    	  
    	  
    	  
    	  
    	  
      })
      
      
      
      </script>

</body>
</html>