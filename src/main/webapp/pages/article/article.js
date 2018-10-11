var temp = require('article.html');
var Najax = require('../utils/najax')

module.exports = {
	template: temp,
	
	data: function(){
		return {
			noData: true,
			studyArticles: '',
			pageNo: 1,
			totalPage: 0
		};
	},
	
	created: function(){
		var self = this;
		self.loadPage();
	},
	
	mounted: function(){
	},
	
	methods: {
		loadPage: function() {
			var self = this;
			Najax.post('article/token/getArticlesCount').then(function(res){
				self.totalPage = res.result.data.studyTotalPage;
				self.noData = false;
				self.initPage();
			}).catch(function(error){
				console.log(error);
			});
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
				Najax.post('article/token/getArticles', {pageNo: parseInt(page_index)}).then(function(res){
					self.studyArticles = res.result.data.articles;
				}).catch(function(error){
					console.log(error);
				});
				return false;
			}
		},
		
	}
};