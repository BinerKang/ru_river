var temp = require('authMail.html');
var Najax = require('../utils/najax')

module.exports = {
	template: temp,
	
	data: function(){
		return {
			token: this.$route.params.token,
			hintMsg: '',
			success: false
		};
	},
	
	created: function(){
		var self = this;
		if(self.token) {
			var data = {
				code: $.trim(self.token)
			}
			Najax.postMask('user/token/authMail', data).then(function(res){
				var res = res.result;
				var code = res.code;
				var msg = res.msg;
				if (code == 0) {
					msg = "认证成功！";
					self.success = true;
					sessionStorage.setItem("user", res.data.userInfo);
				} else {
					sessionStorage.removeItem("user");
					msg = "认证失败！"
				}
				self.hintMsg = msg;
				self.showResult(msg, code);
			}).catch(function(error){
				alert(error);
			});
		}
	},
	
	mounted: function(){
	},
	
	methods: {
		goToHome: function(){
			// 对应canvas不显示
			sessionStorage.setItem('needRefresh', true);
			this.$router.push("/");
		},
		goToLogin: function(){
			this.$router.push("/login");
		},
		showResult: function(msg, status) {
			if (status) {
				$("#warn_div").removeClass("alert-success");
				$("#warn_div").addClass("alert-warning");
			} else {
				$("#warn_div").removeClass("alert-warning");
				$("#warn_div").addClass("alert-success");
			}
			$("#warn_div").show();
		},
	}
};