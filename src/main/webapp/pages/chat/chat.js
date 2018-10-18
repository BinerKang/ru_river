var temp = require('chat.html');
var Najax = require('../utils/najax')

module.exports = {
	template: temp,
	
	data: function(){
		return {
			members: [],
			msg: '',
			count: '',
			content: '',
			websocket: null,
			member: ''
		};
	},
	
	created: function(){
		
	},
	
	mounted: function(){
		var self = this;
		self.openWebSocket();
		// 设置 聊天记录的最小高度，把页脚顶下去
    	$(".chat-box").css("height", $(window).height()*0.65);
	},
	
	methods: {
		openWebSocket: function(){
			var self = this;
			// 建立webSocket
			var domain = document.domain;
		    if ('WebSocket' in window) {
		    	self.websocket = new WebSocket("ws://" + domain + "/ru_river/websocket/token/socketServer");
		    } else if ('MozWebSocket' in window) {
		    	self.websocket = new MozWebSocket("ws://" + domain + "/ru_river/websocket/token/socketServer");
		    } else {
		    	self.websocket = new SockJS("http://" + domain + "/ru_river/sockjs/token/socketServer");
		    }
		    self.websocket.onopen = function(openEvt){
		    	
		    };
		    self.websocket.onmessage = function(evt) {
		    	var chatInfo = JSON.parse(evt.data);
		    	var type = chatInfo.type;
		    	var from = chatInfo.from;
		    	var members = chatInfo.members;
		    	if (members) {
		    		for (var i=0;i<members.length;i++) {
		    			self.getLocation(members[i]);
		    		}
		    		self.members = members;
		    	}
		    	if (type == 0) {//普通消息
		    		self.getLocation(from);
		    		self.member = from;
		    		self.msg = chatInfo.msg;
		    		self.appendMsg();
		    	} else if (type == 1) {// 加入
		    		
		    	} else if (type == 2) {// 离开
		    		
		    	}
		    };
		    self.websocket.onerror = function(){
		    	
		    };
		    self.websocket.onclose = function(){
		    	
		    };
		    
		},
		appendMsg: function(){
			var self = this;
			var divs = '<div class="chat-mem"> ' + self.member.ip + '[' + self.member.location + ' ' + new Date().toTimeString()+']: </div>'+
    				   '<div class="chat-content" >' + self.msg + '</div>';		
			$("#chat-board").append($(divs));
		},
		sendMsg: function() {
			var self = this;
	        if (self.websocket.readyState == self.websocket.OPEN) {
	            self.websocket.send(self.content.replace(/\r\n/g, '<br/>').replace(/\n/g, '<br/>').replace(/\s/g, ' '));//调用后台handleTextMessage方法
	            console.log("发送成功!");  
	            self.content = '';
	        } else {
	        	console.log("连接失败!");
	        }
		},
		checkMsg: function(event) {
			var self = this;
			if (self.content) {
				$("#sendBtn").attr("disabled", false);
			} else {
				$("#sendBtn").attr("disabled", true);
			}
			
		},
		getLocation: function(ipInfo) {
			var ipLocation = ipInfo.country?ipInfo.country:'未知asdfasdfasdfasdfasdfasdfadsf';
			if (ipInfo.country != null && ipInfo.subdivision) {
				self.ipLocation += ipInfo.subdivision;
				if (ipInfo.city) {
					ipLocation += ipInfo.city;
				}
			}
			if(ipLocation == '香港' || ipLocation == '台湾' || ipLocation == '澳门') {
				ipLocation = '中国' + ipLocation;
			}
			ipInfo.location = ipLocation;
		}
	}
};