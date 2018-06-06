var temp = require('home.html');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			ipLocation: '',
			user: '',
			scores: '',
			websocket: null
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
		var self = this;
		self.openWebSocket();
	},
	
	methods: {
		changePage: function(type){
			$("#myModalCloseBtn").click();
			if(type){
				this.$router.push("/login");
			} else {
				this.$router.push("/register");
			}
		},
		openWebSocket: function(){
			var self = this;
			// 建立webSocket
			var domain = document.domain;
			var websocket = self.websocket;
		    if ('WebSocket' in window) {
		        websocket = new WebSocket("ws://" + domain + "/ru_river/websocket/token/socketServer");
		    } else if ('MozWebSocket' in window) {
		        websocket = new MozWebSocket("ws://" + domain + "/ru_river/websocket/token/socketServer");
		    } else {
		        websocket = new SockJS("http://" + domain + "/ru_river/sockjs/token/socketServer");
		    }
		    websocket.onopen = function(openEvt){
		    	
		    };
		    websocket.onmessage = function(evt) {
		    	self.scores = JSON.parse(evt.data);
		    };
		    websocket.onerror = function(){
		    	
		    };
		    websocket.onclose = function(){
		    	
		    };
		    
		},
		sendMsg: function() {
			var self = this;
	        if (self.websocket.readyState == websocket.OPEN) {          
	            var msg = "I'm coming";
	            websocket.send(msg);//调用后台handleTextMessage方法
	            alert("发送成功!");  
	        } else {  
	            alert("连接失败!");  
	        }  
		}
	}
};