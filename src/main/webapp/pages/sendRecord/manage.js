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
			keyword: null,
			
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
			var params = {keyword: self.keyword};
			Najax.post('sendRecord/getSendRecordsCount', params).then(function(res){
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
		
		reset: function() {
			var self = this;
			self.keyword = null;
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
				Najax.post('sendRecord/getSendRecords', params).then(function(res){
					self.totalData = res.result.data.sendRecords;
				}).catch(function(error){
					console.log(error);
				});
				return false;
			}
		},
		
	}
};