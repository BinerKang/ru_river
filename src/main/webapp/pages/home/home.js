var temp = require('home.html');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			ipLocation: '',
			user: '',
			scores: ''
		};
	},
	
	created: function(){
		// 为了对应canvas加载不出来，需要刷新当前页
		if (sessionStorage.getItem('needRefresh')) {
			sessionStorage.setItem('needRefresh', '');
			location.reload();
			return;
		}
		
		var self = this;
		self.user = JSON.parse(sessionStorage.getItem("user"));
    	$.ajax({
    		async: true,
    		url: 'common/token/getHomeInfo',
    		dataType: 'json',
    		success: function(res) {
    			var result = res.result;
    			if (result.code == 0) {
    				self.ipLocation = result.data.province;
    				self.scores = result.data.scores;
    				// 将最大值放入sessionStorage,游戏结束时比较
    				sessionStorage.setItem("maxScore", self.scores[0].score);
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
		changePage: function(type){
			$("#myModalCloseBtn").click();
			if(type){
				this.$router.push("/register");
			} else {
				this.$router.push("/login");
			}
		}
	}
};