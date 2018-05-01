$(function(){
	new Vue({
        el: '#app',
        data: {
        	logged: false,
        	ipLocation: '',
        },
        created: function() {
        	var self = this;
        	$.ajax({
        		async: true,
        		url: 'common/token/getIpLocation',
        		dataType: 'json',
        		success: function(res) {
        			var result = res.result;
        			if (result.code == 0) {
        				self.ipLocation = result.data.province;
        			}
        		},
        		error: function(){
        			alert('请求异常');
        		}
        	});
        },
        mounted: function() {
        },
        methods: {
            
        }
 	})	
});
