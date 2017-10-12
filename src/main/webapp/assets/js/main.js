$(function() {
	
	Vencent.ProAjax.RequestClient.getRoles(function(data){
		console.log(data);
	},function(error){
		console.log(error);
	});
	
	
});