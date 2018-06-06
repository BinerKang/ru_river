module.exports = [
    {
        path:"/home",
        component: require('./home/home')
    },
    {
    	path:"/register",
    	component: require('./register/register')
    },
    {
    	path:"/authMail/:token",
    	component: require('./authMail/authMail')
    },
    {
    	path:"/login",
    	component: require('./login/login')
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
