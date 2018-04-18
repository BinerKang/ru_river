var tmpl = require('./template/main.html');
var Api = require('../common/api');

var currentindex=localStorage.getItem("tabindex");
var currentitem=localStorage.getItem("tabitem");
if(!currentindex){
    localStorage.setItem("tabindex","")
}
if(!currentitem){
    localStorage.setItem("tabitem","")
}



module.exports = {
	template: tmpl,

	props: [  'title', 'menuGroup', 'menuItem' ],

	data: function() {
		return {
			userName: sessionStorage.getItem('userName'),
			roleName: sessionStorage.getItem('roleName'),
			dealerShopName: sessionStorage.getItem('dealerShopName'),
			dealerShopId:sessionStorage.getItem('dealerShopId'),
			permission:JSON.parse(sessionStorage.permission),
			setOrModify: '', //1代表设置;2代表修改
			tipBox: false,
			dialogSwitch: false,
			setSuc: false,
			telphone: '', 
			valCode:  '',
			origPwd: '',
			setPwd: '',
			confirmPwd: '',
            indexmaskShow:false,
            tabindex:localStorage.getItem("tabindex"),
            tabitem:localStorage.getItem("tabitem"),
            businessinfo:true,
            shopInfo:true,
            insurance:true,
            second:'',
            codeText:false,
            doStopCode:false,
    	    addMessageObj:[],
    	    messageStr:{},
    	    messageobj:[],
    	    allMessageStr:{},
    	    allMessageObj:[],
    	    unReadMessageList:{},
    	    unReadMessageObj:[],
    	    msglength:'',
    	    messageStr_forList:{},
    	    messageObj_forList:[],
    	    allMessageObj_forList:[],
    	    allMessageStr_forList:{},
		}
	},
    mounted: function mounted(){
    },
    computed: {
		inputTitle: function() {
			// console.log('title ' + this.title);
		}
	},

	watch: {
		inputTitle: null	// [hack]观察title的改变
	}, 

	created: function() {
		$.getScript('public/js/neon-api.js');
		$.getScript('public/js/neon-custom.js');
		
		// dataTable default options
		$.extend($.fn.dataTable.defaults, {
			language: {
				"sProcessing": "处理中...",
				"sLengthMenu": "每页显示 _MENU_ 项结果",
				"sZeroRecords": "没有匹配结果",
				"sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				"sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
				"sInfoFiltered": "(由 _MAX_ 项结果过滤)",
				"sInfoPostFix": "",
				"sSearch": "搜索:",
				"sUrl": "",
				"sEmptyTable": "表中数据为空",
				"sLoadingRecords": "载入中...",
				"sInfoThousands": ",",
				"oPaginate": {
					"sFirst": "首页",
					"sPrevious": "上页",
					"sNext": "下页",
					"sLast": "末页"
				},
				"oAria": {
					"sSortAscending": ": 以升序排列此列",
					"sSortDescending": ": 以降序排列此列"
				}
			},
			paginationType: 'bootstrap',
			lengthChange: false, //改变每页显示数据数量
			lengthMenu: [[10, 20, 50, -1], [10, 20, 50, '全部']],
			pageLength: 20,
			searching: false,
			ordering: true,
			order: [[0, 'desc']],
			select: true,
			stateSave: true,
        });
		
	},

	methods: {
		closeMenu:function(){
			var self = this;
            $.ajax({
                url: self.dealerShopId+'/customerManage/dealer/getSwitchStatus.do',
                type: 'POST',
                data: {"flagList":"['businessInfoSwitch','dealerShopInfoSwitch','insuranceSwitch']"},//添加续保管理页面控制开关
                async: false,
                dataType: 'json',
                success: function (data) {
                    var code = data.code;
                    if (code == '01') {
                       var queryList = data.data.queryList; 
                       for(var i in queryList){
                    	   if(queryList[i].dictionaryflag == 'businessInfoSwitch'){
                    		   if(queryList[i].dictionarycode == 'Y'){
                    			   self.businessinfo = true;
                    		   }else{
                    			   self.businessinfo = false;
                    		   }
                    	   }
                    	   if(queryList[i].dictionaryflag == 'dealerShopInfoSwitch'){
                    		   if(queryList[i].dictionarycode == 'Y'){
                    			   self.shopInfo = true;
                    		   }else{
                    			   self.shopInfo = false;
                    		   }
                    	   }
                    	   if(queryList[i].dictionaryflag == 'insuranceSwitch'){
                    		   if(queryList[i].dictionarycode == 'Y'){
                    			   self.insurance = true;
                    		   }else{
                    			   self.insurance = false;
                    		   }
                    	   }
                       }
                    }
                    if('999'== code){
    					window.location.href="login.html";
    					return;
    				}
                },
                error: function (e) {
                    self.tipText(e);
                }
            });
		},
        changMenue:function(a,b){
            this.tabindex=a;
            this.tabitem=b;
            localStorage.setItem("tabindex",a);
            localStorage.setItem("tabitem",b);
        },
		logout: function () {
			sessionStorage.removeItem('dealerShopId');
	 		window.location.replace('./logout.do');
		},
		hadSettedPassword: function() {
            var self = this;
            $.ajax({
                url: 'hadSettedPassword.do',
                type: 'POST',
                data: {},
                async: false,
                dataType: 'json',
                success: function (data) {
                    var code = data.code;
                    if('999'== code){
    					window.location.href="login.html";
    					return;
    				}
                    if (code == '01') {
                        $("#userName").prop("title", '修改密码');
                        self.setOrModify = '2';
                        self.doStopCode = false;
                    } else {
                        $("#userName").prop("title", '设置密码');
                        self.setOrModify = '1';
                    }
                    if (data.telphone){
                    	self.telphone = data.telphone;
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });

        },
        goToSetOrModifyPwd: function() {
        	if (!sessionStorage.getItem('dealerShopId')) {
          		window.location.replace('./login.html');
          		return;
          	}
		    //打开弹出窗
            //console.log("======================>0")
            //console.log(this.$refs);
            //this.$refs.Jumpwindow.doShowMask();
            this.indexmaskShow=true;
            var self = this;
            //清空之前的数据
            self.tipBox = false;
            self.dialogSwitch = false;
            self.setSuc = false;
            if (!self.phone){
            	self.telphone = sessionStorage.getItem('phone');
            }
            self.valCode =  '';
            self.origPwd ='';
            self.setPwd = '';
            self.confirmPwd = '';
            self.hadSettedPassword();
            if (self.setOrModify == '1') {
                $("#dialogEditOrSetPwd").prop("title","设置密码");
                $("#originalPwd").hide();
            } else {
                $("#dialogEditOrSetPwd").prop("title","修改密码");
                $("#originalPwd").show();
            }
            //弹出框  初始化
            $( "#dialogEditOrSetPwd" ).dialog({
                autoOpen: false,
                width: 550,
                buttons: [
                    {
                        text: "确定",
                        id: "pwd_confirm_button",
                        style: "display:none",
                        click: function() {
                            self.setOrModifyPwd();
                        }
                    },
                    {
                        text: "取消",
                        id: "pwd_cancel_button",
                        style: "display:none",
                        click: function() {
                            $( this ).dialog( "close" );

                        }

                    }
                ]
            });
            $("#dialogEditOrSetPwd").dialog("open");
        },

        cancelPwdEdit: function() {
            $("#pwd_cancel_button").click();
        },

        confirmPwdEdit: function() {
            $("#pwd_confirm_button").click();
        },
        cutDownTime:function(){
	  		var self = this;
	  		var second = 60;
    		var stopCode = setInterval(function(){
    			second--;
    			if(self.doStopCode){
    				clearInterval(stopCode);
    			}
    			self.second = second +'s后重新获取';
    			if(second == 0){
    				clearInterval(stopCode);
    				self.codeText = false;
    			}
    		},1000);
	  },
        getValCode : function(){
            var self = this;
            var regEx = /^1[34578]\d{9}$/;
            var phone = self.telphone;
            if(phone == ''){
                this.tipText("手机号不能为空");
                return false;
            }

            if(!(regEx.test(phone))){
                this.tipText("手机号码有误，请重填");
                return false;
            }
            var self = this;
            $.ajax({
                url: 'getCode.do',
                type: 'POST',
                data: {phone:phone},
                dataType: 'json',
                success: function (data) {
                    var code = data.code;
                    var message = data.message;
                    if(code=='1'){
                        self.tipText("验证码发送成功！");
                        self.codeText = true;
                        self.cutDownTime();
                    }else{
                        self.tipText(message);
                    }
                }
            });
        },

        setOrModifyPwd: function() {
            var self = this;
            var phone = self.telphone;
            var code = self.valCode;
            var type = self.setOrModify;
            if(phone == ''){
                this.tipText("手机号不能为空");
                return false;
            }
            if(!(/^1[34578]\d{9}$/.test(phone))){
                this.tipText("手机号码有误，请重填");
                return false;
            }
            if(code == ''){
                this.tipText("验证码不能为空");
                return false;
            }
            if (type == '2') {
            	var origPwd = self.origPwd;
                if (origPwd == '') {
                    this.tipText("原密码不能为空");
                    return false;
                }
            }
            var result = this.checkPassWord();
            if (!result) return;
            result = this.checkPwdSame();
            if (!result) return;
            var setPwd = self.setPwd;
            var confirmPwd = self.confirmPwd;
            var url = (type == '1')?'setOrResetPwd.do':'modifyPassword.do';
            $.ajax({
                url: url,
                type: 'POST',
                data: { phone: phone,
                    code: code,
                    setPwd: setPwd,
                    originalPassword: origPwd,
                    confirmPwd: confirmPwd,
                    type: type
                },
                dataType: 'json',
                success: function (data) {
                    var code = data.code;
                    var message = data.message;
                    if(code=='01'){
                        self.setSuc = true;
                        self.doStopCode = true;

                    }else{
                        self.tipText(message);
                    }
                },
                error: function() {
                    self.tipText("密码设置失败！");
                }
            });
        },

        hideEditPwdDialog: function() {
            $("#pwd_cancel_button").click();
        },

        tipText: function(text) {
            var self = this;
            self.tipBox = true;
            $('.copy-tip').text(text);
            setTimeout(function(){self.tipBox = false;},1500);
        },

        checkPassWord : function() {//必须为字母加数字且长度不小于6位
            var self = this;
            var str = self.setPwd;
            if (str == null || str.length <6) {
                this.tipText('密码至少6位！');
                return false;
            }
            var reg1 = new RegExp(/^[0-9A-Za-z]+$/);
            if (!reg1.test(str)) {
                this.tipText('密码含有非法字符');
                return false;
            }
            var reg = new RegExp(/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/);
            if (reg.test(str)) {
                return true;
            } else {
                this.tipText('密码需包含数字和字母');
                return false;
            }
        },
		checkPwdSame : function() {
            var self = this;
            var pwdSet = self.setPwd;
            var pwdConfirm = self.confirmPwd;
            if (pwdSet != pwdConfirm) {
                this.tipText('确认密码同设置密码不一样！');
                return false;
            }
            return true;
        },
        indexPage:function(){
            this.$router.push('/');
            localStorage.setItem("tabindex","");
            localStorage.setItem("tabitem","");
        },
        /*消息盒子页面*/
		goToMessageBox:function(){
			// 由于页面messageBox没有对应的菜单项， 因此隐藏当前的激活状态
   	        this.changMenue(0, 0);
			this.$router.push("/messageBox");
		},
		
       queryMessageList:function(){
          	var self = this;
			Api.post(self.dealerShopId+'/messageManagement/dealer/getMessageByCondition.do', {
			}).then(function(res) {
				if('01' == res.code||'02' == res.code){ //01后台接口异常   02该用户无权限；分别提示用户res.message或者重新提示
                	self.tipText(res.message);
				}
				if('999'== res.code){
					window.location.href="login.html";
					return;
				}
				if('00'== res.code){
					
					self.addMessageObj = res.data;//每次新增消息list
					self.messageStr = sessionStorage.getItem('messageList_window'),//缓存已有的消息list,用于存放未读信息
					messageStr = self.messageStr;
					if(messageStr != null){
						self.messageObj = JSON.parse(messageStr);
					}else{
						self.messageObj = [];
					}
					
					if (self.addMessageObj.length > 0) {
						sessionStorage.setItem('newMsg', 'Y'); // 设置为Y，messageBox页面检测到有值后，去刷新页面
					}
//					messageStr_forList = sessionStorage.getItem('messageList');//缓存已有的消息list,用于存放未读+已读消息
//					if(messageStr_forList != null){
//						self.messageObj_forList = JSON.parse(messageStr_forList);
//					}else{
//						self.messageObj_forList = [];
//					}
//					
//					messageObj_forList = self.messageObj_forList;
//					self.allMessageObj_forList = messageObj_forList.concat(addMessageObj);
//					allMessageObj_forList = self.allMessageObj_forList;
//					self.allMessageStr_forList = JSON.stringify(allMessageObj_forList);
//					allMessageStr_forList = self.allMessageStr_forList
//					sessionStorage.setItem('messageList',allMessageStr_forList);
					
					addMessageObj = self.addMessageObj;
					messageObj = self.messageObj;
					self.allMessageObj = messageObj.concat(addMessageObj);

					allMessageObj = self.allMessageObj;					
					unReadMessageObj = [];
					for(var i in allMessageObj){
						if(allMessageObj[i].ispush == 0){
							unReadMessageObj.push(allMessageObj[i]);
						}
					}
					
					self.unReadMessageObj = unReadMessageObj;				
					self.unReadMessageList = JSON.stringify(unReadMessageObj);
					unReadMessageList = self.unReadMessageList;
					self.msglength = unReadMessageObj.length;
					sessionStorage.setItem('messageList_window',unReadMessageList);

				}
			}).catch(function(error) {
				console.log(error);
			}); 
       },
       
       addMessageClass:function(){
       		alert($('#messageBoxs').children('.messageBox').length);
			for(var idx = 0; idx < $(".messageBox").length; idx ++){
			    $(".messageBox")[idx].css({'top' : '70px '+ idx*3 +'px'}); 
			    $(".messageBox")[idx].css({'right' : '40px '+ idx*3 +'px'}); 
			}
       },
       
       openMessage:function(messageid,msgtype,status,msgbody){  
   	        var self = this;    
   	        
   	        //处理其他业务
   	    	self.messageStr = sessionStorage.getItem('messageList_window');
   	    	messageStr = self.messageStr;
   	    	if(messageStr != null){
				self.messageObj = JSON.parse(messageStr);
			}else{
				self.messageObj = [];
			}
			
			messageObj = self.messageObj;
			for(var i in messageObj){
				if(messageid == messageObj[i].messageid){
					messageObj[i].ispush = "1";
				}
			}
			unReadMessageObj = [];
			for(var i in messageObj){
				if(messageObj[i].ispush == 0){
					unReadMessageObj.push(messageObj[i]);
				}
			}	
			self.unReadMessageObj = unReadMessageObj;
			self.unReadMessageList = JSON.stringify(unReadMessageObj);
			unReadMessageList = self.unReadMessageList;
			self.msglength = unReadMessageObj.length;
			sessionStorage.setItem('messageList_window',unReadMessageList);
   	    	
   	    	//状态=="0"表示点击消息跳转页面，状态=="1"标示点击消息上的关闭不跳转页面
   	    	if(status == "0"){
   	    		if(msgtype == "160"){  
   	    			// 从msgbody里拿到车牌号
   	       	    	var license = $.trim(msgbody.split('：')[1].split('）')[0]);
		       	    //客户管理-客户数据页面 
   	       	    	this.$router.push({
   		       	    	path: "/customerManager/" + new Date().getTime() ,
   		       	    	query: {
   		       	    		license: license
   		       	    	}
   		       	    });
					self.changMenue(5, 1);
       	    	}else if(msgtype == "30"){
       	    		//续保页面
       	    		this.$router.push("/customerList"); 
					self.changMenue(6, 1);
       	    	}
   	    	}	
       },
	}
};