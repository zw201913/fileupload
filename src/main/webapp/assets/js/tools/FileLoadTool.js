// 用来加载css,js,html等文件的工具
Vencent.ProJS.FileLoadTool = (function() {
	var my = {};
	var jsFiles = [];// 存放js文件名，用于防止重复加载
	var cssFiles = [];// 存放css文件名，用于防止重复加载
	var htmlFiles = [];// 存放html文件名，用于防止重复加载

	function isLoad(name, files) {
		for (var n = 0; n < files.length; n++) {
			if (files[n] == name) {
				return true;
			}
		}
		return false;
	}

	// 加载js
	my.loadJsFile = function(name) {
		// 判断是否已加载
		if (!isLoad(name, jsFiles)) {

			$('head').append(
					'<script type="text/javascript" src=/'
							+ [ Vencent.Constant.project_name,
									Vencent.Constant.js_path, name ].join("")
							+ '.js></script>');
			jsFiles.push(name);
		}
	}

	// 加载css
	my.loadCssFile = function(name) {
		// 判断是否已加载
		if (!isLoad(name, cssFiles)) {

			$('head').append(
					'<link rel="stylesheet" href=/'
							+ [ Vencent.Constant.project_name,
									Vencent.Constant.css_path, name ].join("")
							+ '.css>');
			cssFiles.push(name);
		}
	}

	// 加载html
	my.loadHtmlFile = function(name, obj) {
		var url = [ Vencent.Constant.project_name, Vencent.Constant.html_path,
				name ].join("")
				+ '.html';

		function success(data) {// 成功后加载到指定的节点中去
			obj.append(data);
			htmlFiles.push(name);
		}

		function filure(xhr) {// 失败后打印错误信息
			alert(xhr.statusText);
		}

		$.ajax({
			async : false,
			url : url,
			type : "GET",
			success : success,
			error : filure
		})
	}

	// 加载html的同时加载对应的js，css文件
	my.loadFile = function(name, obj) {
		var url = [ Vencent.Constant.project_name, Vencent.Constant.html_path,
				name ].join("")
				+ '.html';

		function success(data) {// 成功后加载到指定的节点中去
			obj.append(data);
			htmlFiles.push(name);
			my.loadCssFile(name);
			my.loadJsFile(name);
		}

		function filure(xhr) {// 失败后打印错误信息
			alert(xhr.statusText);
		}

		$.ajax({
			async : false,
			url : url,
			type : "GET",
			success : success,
			error : filure
		})
	}

	return my;
})();