// Server API

// var jsPromise = Promise.resolve($.ajax('/whatever.json'));
// 上面代码将jQuery生成的deferred对象，转为一个新的Promise对象。

var Spinner = require('./spin');
var opts = {
		  lines: 10, // The number of lines to draw
		  length: 38, // The length of each line
		  width: 18, // The line thickness
		  radius: 37, // The radius of the inner circle
		  scale: 0.45, // Scales overall size of the spinner
		  corners: 1, // Corner roundness (0..1)
		  color: '#dd0a1e', // CSS color or array of colors
		  fadeColor: 'transparent', // CSS color or array of colors
		  speed: 1.5, // Rounds per second
		  rotate: 7, // The rotation offset
		  animation: 'spinner-line-fade-more', // The CSS animation name for the lines
		  direction: 1, // 1: clockwise, -1: counterclockwise
		  zIndex: 2e9, // The z-index (defaults to 2000000000)
		  className: 'spinner', // The CSS class to assign to the spinner
		  top: '47%', // Top position relative to parent
		  left: '50%', // Left position relative to parent
		  shadow: '0 0 1px transparent', // Box-shadow for the lines
		  position: 'absolute' // Element positioning
		};
var spinner = new Spinner(opts);
var ajaxAsync = function(type, method, url, data, cb, needMask) {
	// 将data转化为JSON字符串，便于服务器处理
	var data = {data: JSON.stringify(data)}; 
	if (type === 'json') {
		return new Promise(function(resolve, reject) {
			$.ajax({
				type: method,
				url: url,
				data: data,
				dataType: type,
				xhrFields: {
					withCredentials: true
				}, // 设置跨域访问
				crossDomain: true,
				beforeSend: function(){
					if (needMask) {
						spinner.spin();
						$("#overlay").append(spinner.el);
						$("#overlay").fadeTo(200, 0.5);  
					}
	    		},
	    		complete: function(){
	    			if (needMask) {
		    			spinner.stop();
		    			$("#overlay").fadeOut(200);
	    			}
	    		},
				success: function(data) {
					resolve(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					reject(new Error('Fail at ' + url + ' with ' + textStatus));
				}
			});
		});
	} else if (type === 'jsonp') {
		return new Promise(function(resolve, reject) {
			$.ajax({
				type: method,
				url: url,
				data: data,
				dataType: type,
				jsonp: cb,
				success: function(data) {
					resolve(data);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					reject(new Error('Fail at ' + url + ' with ' + textStatus));
				}
			});
		});
	} else {
		throw Error('Not support ajax dataType: ' + type);
	}
};

var Najax = module.exports = {
	get: function(url) {
		return ajaxAsync('json', 'GET', url);
	},

	post: function(url, data) {
		return ajaxAsync('json', 'POST', url, data);
	},

	getJsonp: function(url, cb) {
		return ajaxAsync('jsonp', 'GET', url, null, cb || 'callback');
	},

	postJsonp: function(url, data, cb) {
		return ajaxAsync('jsonp', 'POST', url, data, cb || 'callback');
	},
	
	postMask: function(url, data) {
		return ajaxAsync('json', 'POST', url, data, null, true);
	},
};