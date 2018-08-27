
var VueRouter = require('../static/js/vue-router.min');
var routes = require("./routes");
var router = new VueRouter({
	routes: routes
});
new Vue({
    el: '#app',
    router: router,  // 注入到根实例中
    data: {
    	user: '',
    },
    created: function() {
    	// 设置app的最小高度，把页脚顶下去
    	$("#app").css("min-height", $(window).height()*0.85);
    	var self = this;
    	if (sessionStorage.getItem("user")) {
    		self.user = JSON.parse(sessionStorage.getItem("user"));
    	}
    },
    mounted: function() {
    },
    methods: {
    	goToHome: function(){
    		// 为了对应canvas加载不出来，需要刷新当前页
			sessionStorage.setItem('needRefresh', true);
			this.$router.push("/");
		},
		logout: function(){
			var self = this;
			sessionStorage.removeItem('user', true);
			// TODO 删除redis
			alert("退出成功");
			// 刷新页面
			window.location.reload();
		}
    }
});
// 页面标题同步
Vue.directive('title', {
  inserted: function (el, binding) {
    document.title = el.innerText
    el.remove()
  }
})
