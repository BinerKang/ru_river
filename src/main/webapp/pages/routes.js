module.exports = [
    {
        path:"/home",
        component: require('./home/home')
    },
    {
    	path:"/register",
    	component: require('./register/register')
    },
    // 重定向
    {
        path: '/', 
        redirect: '/home' 
    },
    // 404
    {
        path: '*', 
        component: require('./404/404') 
    }
]
