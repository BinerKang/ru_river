var temp = require('trick.html');
var Najax = require('../utils/najax');

module.exports = {
	template: temp,
	
	data: function(){
		return {
			content: null,
			font: 'sub-zero',
			art: null
		};
	},
	
	created: function(){
	},
	
	mounted: function(){
		
	},
	
	methods: {
		convert: function() {
			var self = this;
			var data = {
					content: self.content,
					font: self.font
			}
			Najax.postMask('common/token/trickConvert', data).then(function(res){
				var res = res.result;
				var code = res.code;
				var art = '';
				if (code == 0) {
					art = res.data.art;
					$("#copyBtn").attr("disabled",false);
					self.art = art;
				} else {
					art = "转换异常";
				}
				$("#art_div").html(art);
			}).catch(function(error){
				alert(error);
			});
		},
		checkContent: function(){
			var self = this;
			var flag = /^[A-Za-z0-9,-\\*\\+()&'\\$%"#@ !]+$/.test($.trim(self.content));
			self.hintForm("content_div", flag);
			self.enable(flag);
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
		enable: function(flag) {
			if(flag) {
				$("#confirmBtn").attr("disabled",false);
			} else {
				$("#confirmBtn").attr("disabled",true);
			}
		},
		selectFont: function(font){
			var self = this;
			self.font = font;
			$('html,body').animate({scrollTop:0}, 100);
		},
		copyToBoard: function(){
			var self = this;
			const textarea = document.createElement('textarea');
			textarea.setAttribute('readonly', 'readonly');
			textarea.value = self.art;
		    document.body.appendChild(textarea);
		    textarea.setSelectionRange(0, 9999);
			if (document.execCommand('copy')) {
				textarea.select();
				document.execCommand('copy');
				console.log('复制成功');
			}
		    document.body.removeChild(textarea);
		}
	}
};