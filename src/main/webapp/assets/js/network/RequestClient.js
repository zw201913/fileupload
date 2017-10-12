Vencent.ProAjax = {};
Vencent.ProAjax.RequestClient = (function() {// 发送请求的工具
	var baseRequest = (function() {
		var timeout = 3000;// 请求超时时间3秒

		function request(param) {
			$.ajax({
				url : Vencent.ProJS.CommonTool.getUrl(param.url),
				type : param.type,
				contentType : param.contentType,
				dataType : param.dataType,
				data : param.data,
				success : function(data) {
					if (typeof data == "string") {
						data = JSON.parse(data);
					}
					if (data["code"] == "200") {// 响应正常返回
						param.success(data["data"]);
					} else if (data["code"] == "-200") {// 用户未登录，直接跳登录页面
						window.location.href = Vencent.ProJS.CommonTool
								.getUrl(Vencent.Constant.login_url);
					} else if (data["show"]) {// 出现不符合要求的结果，需要提示用户
						var msg = data["errorMsg"];
						if (typeof msg == "string") {
							$("#publicErrorMsg").html(msg);
							return;
						}
						for (index in msg) {
							var obj = msg[index];
							for (key in obj) {
								if (obj.hasOwnProperty(key)) {
									$('#' + key + 'Msg').val(obj[key]);
								}
							}
						}
					} else {// 请求出错，不能展示给用户看
						param.failure({
							errorMsg : data["errorMsg"],
							detail : data["detail"]
						});
					}
				},
				error : function(error) {
					if (error.statusText == "error") {
						param.failure({
							errorMsg : "通信异常",
							detail : error
						})
					} else if (error.statusText == "timeout") {
						param.failure({
							errorMsg : "通信超时",
							detail : error
						})
					}
				},
				timeout : param.timeout
			});
		}

		return {// 基本请求接口
			postJson : function(url, param, success, failure) {// 使用json方式发送请求
				request({
					url : url,
					type : "post",
					contentType : "application/json",
					dataType : "json",
					data : JSON.stringify(param),
					success : success,
					failure : failure,
					timeout : timeout
				});
			},
			postForm : function(url, param, success, failure) {// 使用form方式发送请求
				request({
					url : url,
					type : "post",
					contentType : "application/x-www-form-urlencoded",
					dataType : "json",
					data : param,
					success : success,
					failure : failure,
					timeout : timeout
				});
			},
			get : function(url, param, success, failure) {
				request({
					url : url,
					type : "get",
					contentType : "application/x-www-form-urlencoded",
					dataType : "json",
					data : param,
					success : success,
					failure : failure,
					timeout : timeout
				});
			}
		}
	})();

	return {// 具体业务相关api
		manegementer_login : function(param, success, failure) {// 后台登录请求
			baseRequest
					.postJson("/managementer/login", param, success, failure);
		},
		getRoles : function(success, failure) {// 登录请求
			baseRequest.get("/role/getRoles", null, success, failure);
		},

		createRole : function(param, success, failure) {// 注册请求
			baseRequest.postForm("/role/create", param, success, failure);
		}
	};
})();