$(function() {

	$('.login-btn').on('click', function() {
		var username = $('#userName').val();
		var password = $('#password').val();
		var param = {
			name : username,
			password : password
		}
		
		Vencent.ProAjax.RequestClient.manegementer_login(param, function(data) {// 登录成功
			console.log("登录成功");
			window.location.href = Vencent.ProJS.CommonTool.getUrl("/managementer/main");
			
		}, function(error) {// 登录失败
			console.log("登录失败");
			console.log(error);
		});
	});

})