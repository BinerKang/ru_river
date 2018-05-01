Vue.use(VueRouter);

const router = new VueRouter({
	mode: 'history',
	base: __dirname,
	routes: [
		{
			path: '/login',
			name: 'login',
			component: ''
		},
		
	]
});