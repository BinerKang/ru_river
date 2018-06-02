
var VueRouter = require('../static/js/vue-router.min');
var routes = require("./routes");
var router = new VueRouter({
	routes: routes
});
new Vue({
    el: '#app',
    router: router,  // 注入到根实例中
    data: {
    	logged: false,
    	
    },
    created: function() {
    	
    },
    mounted: function() {
    },
    methods: {
    	goToHome: function(){
    		// 为了对应canvas加载不出来，需要刷新当前页
			sessionStorage.setItem('needRefresh', true);
			this.$router.push("/");
		},
    }
});
Vue.directive('title', {
  inserted: function (el, binding) {
    document.title = el.innerText
    el.remove()
  }
})
