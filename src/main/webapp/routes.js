// 路由配置

module.exports = [
  {
      path: '/',
      component: require('')
  },
  {
      path: '/login',
      component: require('./pages/login/login')
  },
//  {
//      path: '*',	// 未匹配到以上路由
//  	  component: require('./pages/404/404')
//  }
];