var temp = require('manage.html');
var Najax = require('../utils/najax')

module.exports = {
	template: temp,
	
	data: function(){
		return {
			noData: true,
			totalData: [],
			pageNo: 1,
			totalPage: 0,
			tipBox: false,
			keyword: null,
			isContacted: '',
			
			id: null,
			telphone: null,
			call: null,
			gender: null,
			ip: null,
			info: null,
			contactedTime: null
		};
	},
	
	created: function(){
		var self = this;
		self.loadPage();
	},
	
	mounted: function(){
	},
	
	beforeDestroy: function() {
		
	},
	
	methods: {
		
		loadPage: function() {
			var self = this;
			var keyword = self.keyword?self.keyword:null;
			var params = {
				keyword: self.keyword,
				isContacted: self.isContacted
			};
			Najax.post('contact/getContactsCount', params).then(function(res){
				self.totalPage = res.result.data.totalPage;
				self.noData = false;
				self.initPage();
			}).catch(function(error){
				console.log(error);
			});
		},

		checkedBox: function($checkboxes, flag) {
			for(var i=0;i<$checkboxes.length;i++) {
				$checkboxes[i].checked = flag;
			}
		},
		
		selectAll: function() {
			var self = this;
			var $checkAll = $("thead input[type='checkbox']")[0];
			self.checkedBox($("tbody input[type='checkbox']"), $checkAll.checked);	
		},
		
		selectOne: function() {
			var self = this;
			if($("tbody input:checked").length != self.totalData.length) {
				self.checkedBox($("thead input[type='checkbox']"), false);
			} else {
				self.checkedBox($("thead input[type='checkbox']"), true);
			}
		},
		
		//赋值给弹出框
		assign: function(contact) {
			var self = this
			self.id = contact.id
			self.ip = contact.ip
			self.telphone = contact.telphone
			self.call = contact.call
			self.gender = contact.gender
			self.info = contact.info
		},
		
		cancel: function() {
			$("#contact_cancel_button").click();
		},
		
		confirm: function() {
			$("#contact_confirm_button").click();
		},
		
		checkParams: function() {
			var self = this;
			var flag = true;
			var regex = {
				mobile: /^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/,
			}
			if(!self.telphone) {
				alert("手机号不能为空!");
				flag = false;
			}
			if(!regex.mobile.test(self.telphone)){
				alert("手机号格式不正确!");
				flag = false;
			}
			return flag;
		},
		
		markContact: function(id) {
			var self = this;
			var params = {
				id: id,
				contactedTime: 'now'
			};
			Najax.post('contact/updateContact', params).then(function(res){
				if (res.result.code == 0 ) {
					//刷新
					self.loadPage();
				}
				alert(res.result.msg);
			}).catch(function(error){
				console.log(error);
			});
		},
		
		addOrUpdate: function() {
			var self = this;
			if(!self.checkParams()){
				return;
			};
			var url = "saveContact";
			if(self.id) {
				url = "updateContact";
			}
			var params = {
				id: self.id,
				telphone: self.telphone,
				call: self.call,
				gender: self.gender,
				info: self.info,
				contactedTime: self.contactedTime
			};
			Najax.post('contact/'+url, params).then(function(res){
				if (res.result.code == 0 ) {
					//关闭弹窗
					self.cancel();
					//刷新
					self.loadPage();
				} else {
					alert(res.result.msg);
				}
			}).catch(function(error){
				console.log(error);
			});
		},
		
		add: function() {
			var self = this;
			self.assign({'gender': 'M'});
			self.openDailog();
		},
		
		reset: function() {
			var self = this;
			self.keyword = null;
		},
		
		//编辑
		edit: function(index) {
			var self = this;
			self.assign(self.totalData[index]);
			self.openDailog();
		},
		
		deleteContact: function(index) {
			var flag = confirm("确认删除吗？");
			if (!flag) {
				return;
			}
			var self = this;
			var params = {id: self.totalData[index].id};
			Najax.post('contact/deleteContact', params).then(function(res){
				alert(res.result.msg);
				//刷新
				self.loadPage();
			}).catch(function(error){
				console.log(error);
			});
		},
		
		openDailog: function() {
			var self = this;
			//弹出框  初始化
			$("#dialogContact").dialog({
				autoOpen: false,
				width: 496,
				buttons: [
					{
						 text: "确定",
						 id: "contact_confirm_button",
						 style: "display:none",
						 click: function() {
							self.addOrUpdate();
						 }
					},
					{
							text: "取消",
							id: "contact_cancel_button",
							style: "display:none",
							click: function() {
							$( this ).dialog( "close" );
							$('.mask').hide();
						 }
					}
				]
			});
			$('.mask').show();
			$("#dialogContact").dialog("open");
		},
		
		//分页， 在分页的footer里面插入页数并调用下面的函数
		initPage: function initPagination() {
			var self = this;
			var page_index = (self.pageNo-1) || '0';
			$("#table-pa").pagination(self.totalPage, {
				num_edge_entries : 1, // 边缘页数
				num_display_entries : 10, // 主体页数
				callback : pageselectCallback,
				items_per_page : 1,// 每页显示1项
				prev_text : '<',
				next_text : '>'
			}, page_index);

			function pageselectCallback(page_index) {
				var params = {pageNo: parseInt(page_index)};
				if(self.keyword) {
					params.keyword = self.keyword;
				}
				params.isContacted = self.isContacted;
				Najax.post('contact/getContacts', params).then(function(res){
					self.totalData = res.result.data.contacts;
				}).catch(function(error){
					console.log(error);
				});
				return false;
			}
		},
		
	}
};