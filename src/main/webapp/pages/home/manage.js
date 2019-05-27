var temp = require('manage.html');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			user: null,
		};
	},
	
	created: function(){
		
		// 获取home信息
		var self = this;
		self.user = JSON.parse(sessionStorage.getItem("user"));
		if(!self.user) {
			this.$router.push("/login");
		}
	},
	
	mounted: function(){
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
				this.$router.push("/home/contact");
			} else if (index == 2) {
				this.$router.push("/home/sendRecord");
			} else if (index == 3){
				this.$router.push("/home/browserRecord");
			}
		},
	}
};