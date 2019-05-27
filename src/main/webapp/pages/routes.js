module.exports = [
    {
    	path:"/home",
        component: require('./home/manage'),
        children: [
        	{
        		path:"",
        		component: require("./customer/manage")
        	},
        	{
        		path:"contact",
        		component: require("./contact/manage")
        	},
        	{
        		path:"sendRecord",
        		component: require("./sendRecord/manage")
        	},
        	{
        		path:"browserRecord",
        		component: require("./browseRecord/manage")
        	},
        	// 404
            {
                path: '*', 
                component: require('./404/404') 
            }
        ]
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
