var temp = require('home.html');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			ipLocation: '',
		};
	},
	
	mounted: function(){
		// 为了对应canvas加载不出来，需要刷新当前页
		if (sessionStorage.getItem('needRefresh')) {
			sessionStorage.setItem('needRefresh', '');
			location.reload();
			return;
		}
		
		var self = this;
		
		
    	$.ajax({
    		async: true,
    		url: 'common/token/getIpLocation',
    		dataType: 'json',
    		success: function(res) {
    			var result = res.result;
    			if (result.code == 0) {
    				self.ipLocation = result.data.province;
    			}
    			
    		},
    		error: function(){
    			alert('请求异常');
    		}
    	});
    	
	},
	
	mounted: function(){
    	var game = require('../game');
		game.setup();
	},
	
	methods: {
		
	}
};