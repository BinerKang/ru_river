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
			isSuccess: false,
			keyword: null,
			
			id: null,
			telphone: null,
			name: null,
			gender: null,
			estate: null,
			address: null,
			url: null,
			phase: null,
			info: null,
			
		};
	},
	
	created: function(){
		var self = this;
		self.loadPage();
	},
	
	mounted: function(){
	},
	
	methods: {
		
		selectAll:function(event){
			var self = this;
		},
		
		loadPage: function() {
			var self = this;
			Najax.post('customer/getCustomersCount').then(function(res){
				self.totalPage = res.result.data.totalPage;
				self.noData = false;
				self.initPage();
			}).catch(function(error){
				console.log(error);
			});
		},
		
		//赋值给弹出框
		assign: function(customer) {
			var self = this
			self.id = customer.id
			self.telphone = customer.telphone
			self.name = customer.name
			self.gender = customer.gender
			self.estate = customer.estate
			self.address = customer.address
			self.url = customer.url
			self.phase = customer.phase
			self.info = customer.info
		},
		
		cancel: function() {
			$("#customer_cancel_button").click();
		},
		
		confirm: function() {
			$("#customer_confirm_button").click();
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
			if(!regex.test(self.telphone)){
				alert("手机号格式不正确!");
				flag = false;
			}
			return flag;
		},
		
		addOrUpdate: function() {
			var self = this;
			if(!self.checkParams()){
				return;
			};
			var url = "saveCustomer";
			if(self.id) {
				url = "updateCustomer";
			}
			
			
		},
		
		add: function() {
			var self = this;
			self.assign({'gender': 'M'});
			self.openDailog();
		},
		
		//编辑
		edit: function(index) {
			var self = this;
			self.assign(self.totalData[index]);
			self.openDailog();
		},
		
		openDailog: function() {
			//弹出框  初始化
			$("#dialogCustomer").dialog({
				autoOpen: false,
				width: 496,
				buttons: [
					{
						 text: "确定",
						 id: "customer_confirm_button",
						 style: "display:none",
						 click: function() {
							self.addOrUpdate();
						 }
					},
					{
							text: "取消",
							id: "customer_cancel_button",
							style: "display:none",
							click: function() {
							$( this ).dialog( "close" );
							$('.mask').hide();
						 }
					}
				]
			});
			$('.mask').show();
			$("#dialogCustomer").dialog("open");
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
				Najax.post('customer/getCustomers', {pageNo: parseInt(page_index)}).then(function(res){
					self.totalData = res.result.data.customers;
				}).catch(function(error){
					console.log(error);
				});
				return false;
			}
		},
		
	}
};