module.exports = [
    {
        path:"/home",
        component: require('./home/home'),
        children: [
        	{
        		path:"",
        		component: require("./article/article")
        	},
        	{
        		path:"pinball",
        		component: require('./game/pinball/pinball')
        	},
        	{
        		path:"trick",
        		component: require('./trick/trick')
        	},
        	{
        		path:"broadcast",
        		component: require('./chat/chat')
        	},
        	 // 404
            {
                path: '*', 
                component: require('./404/404') 
            }
        ]
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
