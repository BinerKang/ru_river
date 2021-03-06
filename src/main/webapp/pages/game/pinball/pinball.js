var temp = require('pinball.html');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			user: '',
			scores: '',
			websocket: null
		};
	},
	
	created: function(){
		// 为了对应canvas加载不出来，需要刷新当前页
		if (!sessionStorage.getItem("pinBallReload")) {
			location.reload();
			sessionStorage.setItem("pinBallReload", 1);
		}
		
		var self = this;
		self.user = JSON.parse(sessionStorage.getItem("user"));
    	$.ajax({
    		async: true,
    		url: 'game/token/getPinballRank',
    		dataType: 'json',
    		success: function(res) {
    			var result = res.result;
    			if (result.code == 0) {
    				self.scores = result.data.scores;
    				var maxIndex = self.scores.length - 1;
    				// 将最大值放入sessionStorage,游戏结束时比较
    				sessionStorage.setItem("minScore", self.scores[maxIndex].score);
    			}
    			
    		},
    		error: function(){
    			console.log('请求异常');
    		}
    	});
    	
	},
	
	mounted: function(){
    	var game = require('./pinballGame');
		game.setup();
		var self = this;
		self.openWebSocket();
	},
	
	destroyed: function(){
		sessionStorage.removeItem("pinBallReload");
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
		    	var scores = JSON.parse(evt.data);
		    	if (scores.length) {
		    		// 更新最大值
		    		self.scores = scores;
		    		var maxIndex = scores.length - 1;
		    		sessionStorage.setItem("minScore", scores[maxIndex].score);
		    	}
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