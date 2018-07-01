
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
			// 为了对应canvas加载不出来，需要刷新当前页
			sessionStorage.setItem('needRefresh', true);
			this.$router.push("/");
			// TODO 删除redis
			alert("退出成功");
		}
    }
});
Vue.directive('title', {
  inserted: function (el, binding) {
    document.title = el.innerText
    el.remove()
  }
})
