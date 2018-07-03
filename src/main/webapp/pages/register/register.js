var temp = require('register.html');
var Najax = require('../utils/najax');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			username: '',
			password: '',
			password2: '',
			mail: '',
			code: '',
			usernameFlag: false,
			passwordFlag: false,
			password2Flag: false,
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
			// 对应canvas不显示
			sessionStorage.setItem('needRefresh', true);
			this.$router.push("/");
		},
		register: function(){
			var self = this;
			var data = {
				username: $.trim(self.username),
				password: $.trim(self.password),
				mail: $.trim(self.mail),
				code: $.trim(self.code)
			}
			var maxScoreTmp = sessionStorage.getItem("maxScoreTmp");
			if (maxScoreTmp) {
				data.score = maxScoreTmp;
			}
			Najax.postMask('user/token/register', data).then(function(res){
				var res = res.result;
				var code = res.code;
				var msg = res.msg;
				if (code == 0) {
					msg = "注册成功！已经向你的邮箱发了一封验证邮件，请跟随提示验证。";
					self.success = true;
					data.password = '';
					sessionStorage.setItem("user", JSON.stringify(data));
				}
				//刷新验证码，清空验证码
				$("#img").click();
				self.code = '';
				$("#registerBtn").attr("disabled",true);
				self.hintMsg = msg;
				self.showResult(msg, code);
			}).catch(function(error){
				alert(error);
			});
		},
		checkUsername: function() {
			var self = this;
			self.usernameFlag = self.username.length>3?true:false;
			self.hintForm("username_div", self.usernameFlag);
			self.enableRegister();
		},
		checkPassword: function() {
			var self = this;
			self.passwordFlag = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$/.test(self.password);
			self.hintForm("password_div", self.passwordFlag);
			self.enableRegister();
		},
		checkPassword2: function() {
			var self = this;
			self.password2Flag = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,15}$/.test(self.password2) && (self.password == self.password2);
			self.hintForm("password2_div", self.password2Flag);
			self.enableRegister();
		},
		checkMail: function() {
			var self = this;
			self.mailFlag = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(self.mail);
			self.hintForm("mail_div", self.mailFlag);
			self.enableRegister();
		},
		checkCode: function() {
			var self = this;
			self.codeFlag = self.code.length == 4;
			self.hintForm("code_div", self.codeFlag);
			self.enableRegister();
		},
		hintForm: function(eleId, flag) {
			if (flag) {
				$("#" + eleId).removeClass("has-error");
				$("#" + eleId).addClass("has-success");
				$("#" + eleId + " span").removeClass("glyphicon-remove");
				$("#" + eleId + " span").addClass("glyphicon-ok");
			} else {
				$("#" + eleId).removeClass("has-success");
				$("#" + eleId).addClass("has-error");
				$("#" + eleId + " span").removeClass("glyphicon-ok");
				$("#" + eleId + " span").addClass("glyphicon-remove");
			}
		},
		enableRegister: function() {
			var self = this;
			var flag = self.usernameFlag && self.passwordFlag && self.password2Flag && self.mailFlag && self.codeFlag;
			if(flag) {
				$("#registerBtn").attr("disabled",false);
			} else {
				$("#registerBtn").attr("disabled",true);
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