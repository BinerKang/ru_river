var temp = require('register.html');
var Najax = require('../utils/najax')

module.exports = {
	template: temp,
	
	data: function(){
		return {
			username: '',
			password: '',
			password2: '',
			mail: '',
			usernameFlag: false,
			passwordFlag: false,
			password2Flag: false,
			mailFlag: false,
			
		};
	},
	
	created: function(){
	},
	
	mounted: function(){
	},
	
	methods: {
		goToHome: function(){
			// 对应canvas不显示
			sessionStorage.setItem('needRefresh', true);
			this.$router.push("/");
		},
		register: function(){
			var self = this;
			var data = {
				username: self.username,
				password: self.password,
				mail: self.mail	
			}
			Najax.postMask('user/token/register', data).then(function(res){
				console.log(res);
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
			var flag = self.usernameFlag && self.passwordFlag && self.password2Flag && self.mailFlag;
			if(flag) {
				$("#registerBtn").attr("disabled",false);
			} else {
				$("#registerBtn").attr("disabled",true);
			}
		}
	}
};