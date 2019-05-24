var temp = require('login.html');
var Najax = require('../utils/najax')

module.exports = {
	template: temp,
	
	data: function(){
		return {
			password: '',
			mail: '',
			code: '',
			passwordFlag: false,
			mailFlag: false,
			codeFlag: false,
			hintMsg: '',
			success: false
		};
	},
	
	created: function(){
	},
	
	mounted: function(){
	},
	
	methods: {
		changeImg : function(){
		    var img = document.getElementById("img"); 
		    img.src = "authImage?date=" + new Date().getTime();
		},
		goToHome: function(){
			this.$router.push("/");
			// 刷新页面
			window.location.reload();
		},
		login: function(){
			var self = this;
			var data = {
				password: $.trim(self.password),
				mail: $.trim(self.mail),
				code: $.trim(self.code)
			}
			var maxScoreTmp = sessionStorage.getItem("maxScoreTmp");
			if (maxScoreTmp) {
				data.score = maxScoreTmp;
			}
			Najax.postMask('user/token/login', data).then(function(res){
				var res = res.result;
				var code = res.code;
				var msg = res.msg;
				var user = res.data.userInfo;
				if (code == 0) {
					msg = "登录成功！";
					self.success = true;
					sessionStorage.setItem("user", JSON.stringify(user));
					self.goToHome();
				}
				//刷新验证码，清空验证码
				$("#img").click();
				self.code = '';
				$("#loginBtn").attr("disabled",true);
				self.hintMsg = msg;
				self.showResult(msg, code);
			}).catch(function(error){
				alert(error);
			});
		},
		checkPassword: function() {
			var self = this;
			self.passwordFlag = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$/.test(self.password);
			self.hintForm("password_div", self.passwordFlag);
			self.enableRegister();
		},
		checkMail: function() {
			var self = this;
//			self.mailFlag = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(self.mail);
			self.mailFlag = true;
			self.hintForm("mail_div", self.mailFlag);
			self.enableRegister();
		},
		checkCode: function(ev) {
			var self = this;
			self.codeFlag = self.code.length == 4;
			self.hintForm("code_div", self.codeFlag);
			self.enableRegister();
			//按了回车键
			if(ev.keyCode == 13){
				$("#loginBtn").click();
            }
		},
		hintForm: function(eleId, flag) {
			if (flag) {
				$("#" + eleId).removeClass("has-error");
				$("#" + eleId).addClass("has-success");
				$("#hint_div").hide();
				$("#" + eleId + " span").removeClass("glyphicon-remove");
				$("#" + eleId + " span").addClass("glyphicon-ok");
			} else {
				$("#" + eleId).removeClass("has-success");
				$("#" + eleId).addClass("has-error");
				$("#hint_div").show();
				$("#" + eleId + " span").removeClass("glyphicon-ok");
				$("#" + eleId + " span").addClass("glyphicon-remove");
			}
		},
		enableRegister: function() {
			var self = this;
			var flag = self.passwordFlag && self.mailFlag && self.codeFlag;
			if(flag) {
				$("#loginBtn").attr("disabled",false);
			} else {
				$("#loginBtn").attr("disabled",true);
			}
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
		}
	}
};