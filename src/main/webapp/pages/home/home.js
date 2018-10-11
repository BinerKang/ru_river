var temp = require('home.html');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			ipInfo: '',
			ipLocation:'',
			user: '',
			websocket: null
		};
	},
	
	created: function(){
		
		// 获取home信息
		var self = this;
		self.user = JSON.parse(sessionStorage.getItem("user"));
    	$.ajax({
    		async: true,
    		url: 'common/token/getHomeInfo',
    		dataType: 'json',
    		success: function(res) {
    			var result = res.result;
    			if (result.code == 0) {
    				var ipInfo = result.data.ipInfo;
    				self.ipLocation = ipInfo?ipInfo.country:'亲爱';
    				if (ipInfo != null && ipInfo.subdivision) {
    					self.ipLocation += ipInfo.subdivision;
    					if (ipInfo.city) {
    						self.ipLocation += ipInfo.city;
    					}
    				}
    				if(self.ipLocation == '香港' || self.ipLocation == '台湾' || self.ipLocation == '澳门') {
    					self.ipLocation = '中国' + self.ipLocation;
    				}
    			}
    			
    		},
    		error: function(){
    			console.log('请求异常');
    		}
    	});
    	
	},
	
	mounted: function(){
		var self = this;
//		self.openWebSocket();
		// 对应canvas弹球刷新
		if (sessionStorage.getItem("pinBallReload")) {
			$("#homeNav .selected").removeClass("selected");
			$("#homeNav li").eq(1).children().eq(0).addClass("selected");
		}
	},
	
	methods: {
		changePage: function(index){
			// 变样式
			var selObj = $("#homeNav .selected").removeClass("selected");
			var clickObj = $("#homeNav li").eq(index).children().eq(0).addClass("selected");
			// 跳转页面
			if (index == 0) {
				this.$router.push("/home/");
			} else if (index == 1) {
				this.$router.push("/home/pinball");
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
		    	if (self.scores) {
		    		// 更新最大值
		    		var maxIndex = self.scores.length - 1;
		    		sessionStorage.setItem("minScore", self.scores[maxIndex].score);
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