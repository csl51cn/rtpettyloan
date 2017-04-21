function extendsValidate(){
	$.extend($.fn.validatebox.defaults.rules, {
		idcode: {// 验证身份证
            validator: function (value) {
            	var idtypeCode = $("#IDTYPE_CODE");
            	if(!idtypeCode || idtypeCode.length==0){
            		idtypeCode = $("#idtypeCode");
            	}
            	var idtypeCodeval = $(idtypeCode).combobox("getValue");
            	if(idtypeCodeval=='01'){
            		$.fn.validatebox.defaults.rules.idcode.message = '证件号码不能为空并且长度18位，由全部数字或数字加最末一位大写英文字符组成。';
            		 return /^[0-9]{17}([A-Z]|[0-9]){1}$/i.test(value);
            	}else if(idtypeCodeval == "05"){
            		$.fn.validatebox.defaults.rules.idcode.message = "15位（国别码3位大写字母+ 12位数字";
            		 return /^[A-Z]{3}[0-9]{12}$/i.test(value);
            	}else if(idtypeCodeval == "06"){
            		$.fn.validatebox.defaults.rules.idcode.message = "第一个字母是H或者M，后面共8位数字。当首字母为“H”时国家/地区为“香港”，当首字母为“M”时国家/地区为“澳门”";
           		 return /^(H|M){1}[0-9]{8}$/i.test(value);
            	}else if(idtypeCodeval == "07"){
            		$.fn.validatebox.defaults.rules.idcode.message = "7或8位数字";
            		 return /^[0-9]{7,8}$/i.test(value);
            	}else{
            		$.fn.validatebox.defaults.rules.idcode.message = "只允许输入大写字母和数字、中文";
            		 return /^([\u4E00-\u9FA5]|[0-9]|[A-Z])*$/i.test(value);
            	}
            },
            message: ''
        },
        addIdcode:{//验证 补充证件号码
        	validator: function (value) {
                return /^([A-Z]|[0-9]|[\u4E00-\u9FA5])+$/i.test(value);
            },
            message: '补充证件号码格式不正确，只允许输入大写字母和数字、中文'
        },
        amt:{
        	validator: function (value) {
                return (/^-?\d+(\.\d+)?$/i.test(value) || /[^\d\.-]/g.test(value));
            },
            message: '金额必须是数字可以带小数'
        },
        txccy:{
        	validator: function (value) {
        		var cny = $("#TXCCY").combobox("getValue");
        		if(!cny){
        			cny = $("#txccy").combobox("getValue");
        		}
                return  cny!= "CNY";
            },
            message: '币种不能为“人民币”'
        },
        minLength: {
            validator: function (value, param) {
                return value.length >= param[0];
            },
            message: '请输入至少（2）个字符.'
        },
        length: { validator: function (value, param) {
	            var len = $.trim(value).length;
	            return len >= param[0] && len <= param[1];
        	},
            message: "输入内容长度必须介于{0}和{1}之间."
        },
        intOrFloat: {// 验证整数或小数
            validator: function (value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '请输入数字，并确保格式正确'
        },
        currency: {// 验证货币
            validator: function (value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message: '货币格式不正确'
        },
        integer: {// 验证整数 可正负数
            validator: function (value) {
                //return /^[+]?[1-9]+\d*$/i.test(value);

                return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
            },
            message: '请输入整数'
        },
        chinese: {// 验证中文
            validator: function (value) {
                return /^[\Α-\￥]+$/i.test(value);
            },
            message: '请输入中文'
        },
        english: {// 验证英语
            validator: function (value) {
                return /^[A-Za-z]+$/i.test(value);
            },
            message: '请输入英文'
        },
        unnormal: {// 验证是否包含空格和非法字符
            validator: function (value) {
                return /.+/i.test(value);
            },
            message: '输入值不能为空和包含其他非法字符'
        },
        username: {// 验证用户名
            validator: function (value) {
                return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
            },
            message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
        },
        name: {// 验证姓名，可以是中文或英文
            validator: function (value) {
                return /^(([\u4E00-\u9FA5]|[a-z]|[A-Z]|\.|\-|\·|\s)+)+$/i.test(value);
            },
           // message: '可以是中文或英文或者“.”'
            message: '姓名格式要求为：只允许录入汉字、字母（半角）或符号（仅支持半角格式的点“.”和“-”以及中文格式的“·”）中间允许有空格'
        },
        date: {
            validator: function (value) {
                //格式yyyy-MM-dd或yyyy-M-d
                return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
            },
            message: '请输入合适的日期格式'
        },
        /*hisDate:{
        	 validator: function (value) {
	        	var isfmt = /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
	        	if(isfmt){
	        		var valdate = Date.parse(value);
	        		var nowdate = new Date().getTime();
	        		return valdate <= nowdate;
	        	}
        	},
            message: '请输入合适的日期格式并且日期不能大于当前日期'
        },*/
        plusNum:{
        	 validator: function (value) {
                 return (/^\d+(\.\d+)?$/i.test(value) || /[^\d\.-]/g.test(value));
             },
             message: '输入项必须为数字或金额格式的数字'
        }
    });
}
	/**
	 * 改变证件类型
	 * @param val
	 */
	function switchIdtypeCode(val){
		var fn = switchIdtypeCode;
		fn.switch01 = function(d){
			if(d && d.value=="CHN"){
				ary[ary.length] = d;
			}
		};
		fn.switch04 = function(d){
			if(d && d.value!="CHN" && d.value!="HKG" && d.value!="MAC" && d.value!="TWN" ){
				ary[ary.length] = d;
			}
		};
		fn.switch05 = function(d){
			if(d && d.value!="CHN" && d.value!="HKG" && d.value!="MAC" && d.value!="TWN" ){
				ary[ary.length] = d;
			}
		};
		fn.switch06 = function(d){
			if(d && (d.value=="HKG" || d.value=="MAC" )){
				ary[ary.length] = d;
			}
		};
		fn.switch07 = function(d){
			if(d && d.value=="TWN" ){
				ary[ary.length] = d;
			}
		};
		fn.switch09 = function(d){
			if(d && d.value=="CHN" ){
				ary[ary.length] = d;
			}
		};
		fn.switch10 = function(d){
			if(d && d.value!="CHN" && d.value!="HKG" && d.value!="MAC" && d.value!="TWN" ){
				ary[ary.length] = d;
			}
		};
		var ary=[];
		var aryData = comboboxCache['CTYCODE'];
		for(var i in aryData){
			if(aryData[i] && aryData[i].value==""){
				ary[ary.length] = aryData[i];
				continue;
			}
			switch(val){
				case '01':fn.switch01(aryData[i]);  ;break;//身份证
				case '04':fn.switch04(aryData[i]);  ;break;//外国护照
				case '05':fn.switch05(aryData[i]);  ;break;//外国人持中国永久居留证
				case '06':fn.switch06(aryData[i]);  ;break;//港澳居民来往内地通行证
				case '07':fn.switch07(aryData[i]);  ;break;//台湾居民来往大陆通行证
				case '09':fn.switch09(aryData[i]);  ;break;//中国护照
				case '10':fn.switch10(aryData[i]);  ;break;//外交官证
			};
		};
		$("#CTYCODE").combobox("loadData",ary).combobox("setValue","");
	}
	
	var comboboxCache = {};
function  switchCode(){
	/**
	 * 改变业务类型代码
	 */
	var that =  switchCode;
	that.code01=function(){
		that.toggleCode01(true);
		that.toggleCode02(false);
		that.toggleCode05(false);
		that.toggleCode04(false);
	};
	that.code02=function(){
		that.toggleCode01(false);
		that.toggleCode02(true);
		that.toggleCode05(false);
		that.toggleCode04(false);
	};
	that.code03=function(){
		that.toggleCode01(false);
		that.toggleCode02(false);
		that.toggleCode05(false);
		that.toggleCode04(false);
	};
	that.code04=function(){
		that.toggleCode01(false);
		that.toggleCode02(false);
		that.toggleCode05(false);
		that.toggleCode04(true);
	};
	that.code05=function(){
		that.toggleCode01(false);
		that.toggleCode02(false);
		that.toggleCode05(true);
		that.toggleCode04(false);
	};
	that.toggleCode01 = function(is01){
		 
	};
	that.toggleCode02 = function(is02){
		$("#LCY_ACCT_NO").validatebox({ //,#AGENT_CORP_CODE, #AGENT_CORP_NAME, #INDIV_ORG_CODE,#INDIV_ORG_NAME
			required:is02 
		});
	};
	that.toggleCode05 = function(is05){
		$("#PAY_ORG_CODE").validatebox({ 
			required:is05 
		});
	};
	that.toggleCode04 = function(is04){
		$("#CAPITALNO").validatebox({ 
			required:is04 
		});
	};
	that.toggleLCY_ACCT_NO = function(isR){
		$("#LCY_ACCT_NO").validatebox({ 
			required:isR 
		});
	};
	that.toggleADD_IDCODE = function(isR){
		$("#ADD_IDCODE").validatebox({ 
			required:isR 
		});
	};
	//that.idddds = "#txamt,#purfxCashAmt,#FCY_REMIT_AMT,#fcyAcctAmt,#tchkAmt, #PURFX_CASH_AMT,#FCY_REMIT_AMT,#FCY_ACCT_AMT,#TCHK_AMT,#TXAMT";
	that.toggleGHAmt = function(isGH){
		$(that.idddds).validatebox({ 
			required:isGH 
		});
	}
	/**
	 * 业务校验开始
	 */
	$("#BIZ_TYPE_CODE").combobox({
		onChange:function(n,o){
			switch(n){
				case "01":that.code01();break;
				case "02":that.code02();break;
				case "03":that.code03();break;
				case "04":that.code04();break;
				case "05":that.code05();break;
			}
		}
	});
	$("#SALEFX_SETTLE_CODE").combobox({
		onChange:function(n,o){
			if("03" == n && $("#BIZ_TYPE_CODE").combobox("getValue") !="05"){
				that.toggleLCY_ACCT_NO(true);
			}else{
				that.toggleLCY_ACCT_NO(false);
			}
		}
	});
	var idtypeCode = $("#IDTYPE_CODE");
	if(!idtypeCode || idtypeCode.length==0){
		idtypeCode = $("#idtypeCode");
	}
	$(idtypeCode).combobox({
		onSelect:function(r){
			if("09"==r.value){
				that.toggleADD_IDCODE(true);
			}else{
				that.toggleADD_IDCODE(false);
			}
		}
	});
	var tradeType = $("#TRADE_TYPE");
	$(tradeType).combobox({
		onChange:function(n,o){
			if("GH"==n){
				that.toggleGHAmt(true);
			}else{
				that.toggleGHAmt(false);
			}
		}
	});
}

/**
 *美元折算率查询
 */
function querySafeExRate(obj){
	var txCur = $("#TXCCY").combobox("getValue");
	var txAmt = restoreAmt($(obj).val());
	if(!txCur || !txAmt){
		$("#"+obj.name+"USD").val(0);
		return ;
	}
	if(txAmt==0){
		$("#"+obj.name+"USD").val(txAmt);
	}
	if(txCur=="USD"){
		$("#"+obj.name+"USD").val(txAmt);
		return;
	}
	$.post(basePath+"/spfeLmt.do?method=querySafeExRate",{txCur:txCur,txAmt:txAmt},function(usdAmt){
		$("#"+obj.name+"USD").val(usdAmt);
	});
}

function changeSafeExRate(txCur, amtField) {
	var txAmt = restoreAmt($("#"+amtField).val());
	if(!txCur || !txAmt){
		 return ;
	}
	if(txCur=="USD"){
		$("#"+amtField+"USD").val(txAmt);
		return;
	}
	$.post(basePath+"/spfeLmt.do?method=querySafeExRate",{txCur:txCur,txAmt:txAmt},function(usdAmt){
		$("#"+amtField+"USD").val(usdAmt);
	});
}

function bizTxChnlLsnr(obj){
	$("#channelId").val($("option[value="+obj.value+"]",obj).attr("chinnelId"));
}

/**
 * 预关注风险提示/关注名单告知
 */
function confirmAgentCorpCode(personName, idType, idCode, pubDate, endDate, reason, typeStatus){
	var dueTime = pubDate + " ~ " + endDate;
	var _reason = encodeURI(encodeURI(reason));
	var _idType = encodeURI(encodeURI(idType));
	var method = typeStatus == "02" ? "agentCord" : "riskCue";
	$.dialog({
		title : '预关注风险提示/关注名单告知',
		content :"url:"+basePath+'/spfeLmt.do?method='+method+'&personName='+encodeURI(encodeURI(personName))+'&idType='+_idType+'&idCode='+idCode+'&dueTime='+dueTime+'&reason='+_reason,
		button:[{
			name:'打印',
			callback:function(){
				this.content.doPrint();
				return false;
			},
			focus:true
		}],
		ok : function() {
			var val = this.content.getIsAgree();
			if(val == "Y"){
				var bizTypeCode = $("#BIZ_TYPE_CODE").combobox('getValue');
				var idTypeCode = $("#IDTYPE_CODE").combobox('getValue');					
				var ctyCode = $("#CTYCODE").combobox('getValue');
				$("#BIZ_TYPE_CODE").combobox({disabled:true});
				$("#IDTYPE_CODE").combobox({disabled:true});
				$("#CTYCODE").combobox({disabled:true});
				$("#BIZ_TYPE_CODE").combobox('setValue',bizTypeCode);
				$("#IDTYPE_CODE").combobox('setValue',idTypeCode);
				$("#CTYCODE").combobox('setValue',ctyCode);				
				$("#IDCODE").attr("disabled",true);	
			} 
			$("#SIGNSTATUS").val(val);
			return true;
		},
		cancelVal : '取消',
		cancel : true,
		width : 800,
		height : 350,
		lock : true
	});
}
/**
 * 表单验证
 */
function validateSpfeLmt(prm,dispather){
	var isOk = false;
	$.ajaxSetup({async:false});
	$.post(basePath+"/validate.do?method="+dispather,prm,function(data){
		if(data !=""){
			if(data.indexOf("<html") !=-1 || data.indexOf("<HTML")!=-1){
				alertMsg(data,"error");
			}else{
				$.messager.alert("系统提示", data, "error");
			}
			isOk= false;
		}else{
			isOk= true;
		}
	});
	$.ajaxSetup({async:true});
	return isOk;
}
$(function(){
	$("select").each(function(){
		if(!this || ! this.id) return;
		var data = $("#"+this.id).combobox("getData");
		comboboxCache[this.id] = data;
	});
	switchCode();
	extendsValidate();
});

function showTasklist() {
	window.location.href=basePath+"/flow/tasklistController.do?method=waitTaskList";
}

/**
 * 历史任务dialog
 */
var showTasklistHisDialog = null;
function showTasklistHis(){
	showTasklistHisDialog = $.dialog({
		title : '查询业务数据',
		content :"url:"+basePath+'/spfeMdf.do?method=finishTaskDialog',
		ok : function() {
			return this.content.doChose();
		},
		cancelVal : '取消',
		cancel : true,
		width : 900,
		height : 450,
		lock : true
	});
}

/**
 * 改变结售汇资金属性 add by feng.chen 20151028
 * @param actionType
 * @param bizType
 * @param idType
 */
function changeJSHZJSX(tradeType, bizType, idType) {
	if ("JH" == tradeType) {
		changeJHZJSX(bizType, idType);
	} else if ("GH" == tradeType) {
		changeGHZJSX(bizType, idType);
	}
}

/**
 * 根据证件类型改变结汇资金属性，证件类型为身份证时为境内个人，其他证件类型均为境外个人 add by feng.chen 20151028
 * 
 * @param bizType 业务类型
 * @param idType 证件类型
 */
function changeJHZJSX(bizType, idType) {
	//获取所有结汇资金属性代码
	//var allTxCode = comboboxCache['TX_CODE'];
	var allTxCode = new Array();
	if (bizType == '01') {//占用额度结汇
//		境内个人/境外个人	货物贸易
//		境内个人/境外个人	运输
//		境内个人/境外个人	旅游
//		境内个人/境外个人	金融和保险服务
//		境内个人/境外个人	专有权利使用费和特许费
//		境内个人/境外个人	咨询服务
//		境内个人/境外个人	其他服务
//		境内个人/境外个人	职工报酬和赡家款
//		境内个人/境外个人	投资收益
//		境内个人/境外个人	其他经常转移
//		境内个人/境外个人	资本账户
//		境外个人	投资资本金
//		境内个人	直接投资撤资
//		境外个人	房地产
//		境内个人/境外个人	其他直接投资
//		境内个人	对境外证券投资撤回
//		境内个人/境外个人	证券筹资
//		境内个人/境外个人	其他投资
//		境内个人/境外个人	国内外汇贷款
//		境内个人/境外个人	经批准的资本其他
		var o = new Object();
		o.value = "110";
		o.text = "110-货物贸易";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "121";
		o.text = "121-运输";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "122";
		o.text = "122-旅游";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "123";
		o.text = "123-金融和保险服务";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "124";
		o.text = "124-专有权利使用费和特许费";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "125";
		o.text = "125-咨询服务";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "126";
		o.text = "126-其他服务";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "131";
		o.text = "131-职工报酬和赡家款";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "132";
		o.text = "132-投资收益";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "133";
		o.text = "133-其他经常转移";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "210";
		o.text = "210-资本账户";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "22A";
		o.text = "22A-其他直接投资";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "232";
		o.text = "232-证券筹资";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "24A";
		o.text = "24A-其他投资";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "250";
		o.text = "250-国内外汇贷款";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "270";
		o.text = "270-经批准的资本其他";
		allTxCode.push(o);
		
		if (idType == '01') {
			//身份证，境内个人 ，增加如下属性代码   222-直接投资撤资 231-对境外证券投资撤回
			var o = new Object();
			o.value = "222";
			o.text = "222-直接投资撤资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "231";
			o.text = "231-对境外证券投资撤回";
			allTxCode.push(o);
		} else {
			//境外个人，增加如下属性代码    221-投资资本金  223-房地产
			var o = new Object();
			o.value = "221";
			o.text = "221-投资资本金";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "223";
			o.text = "223-房地产";
			allTxCode.push(o);
		}
	} else {
		//不占用额度
		if (bizType == '02') {//个人贸易结汇 110- 货物贸易
			var o = new Object();
			o.value = "110";
			o.text = "110-货物贸易";
			allTxCode.push(o);
		} else if (bizType == '03') {//提供凭证的经常项目其他结汇 
//			121-运输
//			122-旅游
//			123-金融和保险服务
//			124-专有权利使用费和特许费
//			125-咨询服务
//			126-其他服务
//			131-职工报酬和赡家款
//			132-投资收益
//			133-其他经常转移
			var o = new Object();
			o.value = "121";
			o.text = "121-运输";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "122";
			o.text = "122-旅游";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "123";
			o.text = "123-金融和保险服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "124";
			o.text = "124-专有权利使用费和特许费";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "125";
			o.text = "125-咨询服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "126";
			o.text = "126-其他服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "131";
			o.text = "131-职工报酬和赡家款";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "132";
			o.text = "132-投资收益";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "133";
			o.text = "133-其他经常转移";
			allTxCode.push(o);
		} else if (bizType == '04') {//资本项目结汇
//			境内个人/境外个人                  210-资本账户
//			境外个人                                     221-投 资资本金
//			境内个人                                     222-直 接投资撤资
//			境外个人                                     223-房 地产
//			境内个人/境外个人                  224-其他直接投资
//			境内个人                                     231-对 境外证券投资撤回
//			境内个人/境外个人                  232-证券筹资
//			境内个人/境外个人                  24A-其他投资
//			境内个人/境外个人                  250-国内外汇贷款
//			境内个人/境外个人                  270-经批准的资本其他
			var o = new Object();
			o.value = "210";
			o.text = "210-资本账户";
			allTxCode.push(o);
			if (idType == '01') {
				//境内个人
				o = new Object();
				o.value = "222";
				o.text = "222-直接投资撤资";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "231";
				o.text = "231-对境外证券投资撤回";
				allTxCode.push(o);
			} else {
				//境外个人
				o = new Object();
				o.value = "221";
				o.text = "221-投资资本金";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "223";
				o.text = "223-房地产";
				allTxCode.push(o);
			}
			o = new Object();
			o.value = "22A";
			o.text = "22A-其他直接投资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "232";
			o.text = "232-证券筹资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "24A";
			o.text = "24A-其他投资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "250";
			o.text = "250-国内外汇贷款";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "270";
			o.text = "270-经批准的资本其他";
			allTxCode.push(o);
		}else if (bizType == '05' && idType == '01') { //通过支付机构的结汇,只支持境内个人
//			110-货物贸易
//			121-运输
//			122-旅游
//			123-金融和保险服务
//			124-专有权利使用费和特许费
//			125-咨询服务
//			126-其他服务
			var o = new Object();
			o.value = "110";
			o.text = "110-货物贸易";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "121";
			o.text = "121-运输";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "122";
			o.text = "122-旅游";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "123";
			o.text = "123-金融和保险服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "124";
			o.text = "124-专有权利使用费和特许费";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "125";
			o.text = "125-咨询服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "126";
			o.text = "126-其他服务";
			allTxCode.push(o);
		}
	}
	$("#TX_CODE").combobox("loadData",allTxCode).combobox("setValue","");
}


/**
 * 根据证件类型改变购汇资金属性，证件类型为身份证时为境内个人，其他证件类型均为境外个人 add by feng.chen 20151028
 * 
 * @param bizType 业务类型
 * @param idType 证件类型
 */
function changeGHZJSX(bizType, idType) {
	//获取所有购汇资金属性代码
	//var allTxCode = comboboxCache['TX_CODE'];
	var allTxCode = new Array();
	if (bizType == '01') {//占用额度购汇
//		境内个人/境外个人	货物贸易
//		境内个人/境外个人	运输
//		境内个人/境外个人	旅游项下其他
//		境内个人/境外个人	金融和保险服务
//		境内个人/境外个人	专有权利使用费和特许费
//		境内个人/境外个人	咨询服务
//		境内个人/境外个人	其他服务
//		境内个人/境外个人	职工报酬和赡家款
//		境内个人/境外个人	投资收益
//		境内个人/境外个人	其他经常转移
//		境内个人/境外个人	资本账户
//		境内个人/境外个人	其他直接投资
//		境内个人/境外个人	其他投资
//		境内个人/境外个人	国内外汇贷款
//		境内个人/境外个人	经批准的资本其他

		var o = new Object();
		o.value = "310";
		o.text = "310-货物贸易";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "321";
		o.text = "321-运输";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "3225";
		o.text = "3225-旅游项下其他";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "323";
		o.text = "323-金融和保险服务";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "324";
		o.text = "324-专有权利使用费和特许费";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "325";
		o.text = "325-咨询服务";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "326";
		o.text = "326-其他服务";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "331";
		o.text = "331-职工报酬和赡家款";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "332";
		o.text = "332-投资收益";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "333";
		o.text = "333-其他经常转移";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "410";
		o.text = "410-资本账户";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "42A";
		o.text = "42A-其他直接投资";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "44A";
		o.text = "44A-其他投资";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "450";
		o.text = "450-国内外汇贷款";
		allTxCode.push(o);
		
		o = new Object();
		o.value = "470";
		o.text = "470-经批准的资本其他";
		allTxCode.push(o);
		
		if (idType == '01') {
			//身份证，境内个人
//			境内个人	自费出境学习
//			境内个人	因私旅游
//			境内个人	公务及商务出国
//			境内个人	投资资本金
//			境内个人	对境外证券投资
			var o = new Object();
			o.value = "3221";
			o.text = "3221-自费出境学习";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "3222";
			o.text = "3222-因私旅游";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "3223";
			o.text = "3223-公务及商务出国";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "421";
			o.text = "421-投资资本金";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "431";
			o.text = "431-对境外证券投资";
			allTxCode.push(o);
		} else {
			//境外个人
//			境外个人	直接投资撤资
//			境外个人	房地产
//			境外个人	境外个人原币兑回
//			境外个人	证券投资撤出
			var o = new Object();
			o.value = "3224";
			o.text = "3224-境外个人原币兑回";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "422";
			o.text = "422-直接投资撤资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "423";
			o.text = "423-房地产";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "432";
			o.text = "432-证券投资撤出";
			allTxCode.push(o);
		}
	} else {
		//不占用额度
		if (bizType == '02') {//个人贸易购汇 310- 货物贸易
			var o = new Object();
			o.value = "310";
			o.text = "310-货物贸易";
			allTxCode.push(o);
		} else if (bizType == '03') {//提供凭证的经常项目其他购汇 
//			境内个人/境外个人	运输
//			境内个人	自费出境学习
//			境内个人	因私旅游
//			境内个人	公务及商务出国
//			境外个人	境外个人原币兑回
//			境内个人/境外个人	旅游项下其他
//			境内个人/境外个人	金融和保险服务
//			境内个人/境外个人	专有权利使用费和特许费
//			境内个人/境外个人	咨询服务
//			境内个人/境外个人	其他服务
//			境内个人/境外个人	职工报酬和赡家款
//			境内个人/境外个人	投资收益
//			境内个人/境外个人	其他经常转移
			var o = new Object();
			o.value = "321";
			o.text = "321-运输";
			allTxCode.push(o);
			
			if (idType == '01') {
				o = new Object();
				o.value = "3221";
				o.text = "3221-自费出境学习";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "3222";
				o.text = "3222-因私旅游";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "3223";
				o.text = "3223-公务及商务出国";
				allTxCode.push(o);
			} else {
				o = new Object();
				o.value = "3224";
				o.text = "3224-境外个人原币兑回";
				allTxCode.push(o);
			}
			
			o = new Object();
			o.value = "3225";
			o.text = "3225-旅游项下其他";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "323";
			o.text = "323-金融和保险服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "324";
			o.text = "324-专有权利使用费和特许费";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "325";
			o.text = "325-咨询服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "326";
			o.text = "326-其他服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "331";
			o.text = "331-职工报酬和赡家款";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "332";
			o.text = "332-投资收益";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "333";
			o.text = "333-其他经常转移";
			allTxCode.push(o);
		} else if (bizType == '04') {//资本项目购汇
//			境内个人/境外个人	资本账户
//			境内个人	投资资本金
//			境外个人	直接投资撤资
//			境外个人	房地产
//			境内个人/境外个人	其他直接投资
//			境内个人	对境外证券投资
//			境外个人	证券投资撤出
//			境内个人/境外个人	其他投资
//			境内个人/境外个人	国内外汇贷款
//			境内个人/境外个人	经批准的资本其他

			var o = new Object();
			o.value = "410";
			o.text = "410-资本账户";
			allTxCode.push(o);
			if (idType == '01') {
				//境内个人
				o = new Object();
				o.value = "421";
				o.text = "421-投资资本金";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "431";
				o.text = "431-对境外证券投资";
				allTxCode.push(o);
			} else {
				//境外个人
				o = new Object();
				o.value = "422";
				o.text = "422-直接投资撤资";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "423";
				o.text = "423-房地产";
				allTxCode.push(o);
				
				o = new Object();
				o.value = "432";
				o.text = "432-证券投资撤出";
				allTxCode.push(o);
			}
			o = new Object();
			o.value = "42A";
			o.text = "42A-其他直接投资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "44A";
			o.text = "44A-其他投资";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "450";
			o.text = "450-国内外汇贷款";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "470";
			o.text = "470-经批准的资本其他";
			allTxCode.push(o);
		}else if (bizType == '05' && idType == '01') { //通过支付机构的购汇,只支持境内个人
//			货物贸易
//			运输
//			自费出境学习
//			因私旅游
//			公务及商务出国
//			旅游项下其他
//			金融和保险服务
//			专有权利使用费和特许费
//			咨询服务
//			其他服务

			var o = new Object();
			o.value = "310";
			o.text = "310-货物贸易";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "321";
			o.text = "321-运输";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "3221";
			o.text = "3221-自费出境学习";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "3222";
			o.text = "3222-因私旅游";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "3223";
			o.text = "3223-公务及商务出国";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "3225";
			o.text = "3225-旅游项下其他";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "323";
			o.text = "323-金融和保险服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "324";
			o.text = "324-专有权利使用费和特许费";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "325";
			o.text = "325-咨询服务";
			allTxCode.push(o);
			
			o = new Object();
			o.value = "326";
			o.text = "326-其他服务";
			allTxCode.push(o);
		}
	}
	$("#TX_CODE").combobox("loadData",allTxCode).combobox("setValue","");
}

/**
 * 根据业务类型改变业务办理渠道 add feng.chen 20151028
 * @param bizType
 */
function changeBizChnCode(bizType) {
	var tmpArray = comboboxCache['BIZ_TX_CHNL_CODE'];
	if (bizType == '01') {
		for(var i in tmpArray) {
			if (tmpArray[i].value == "32") {
				tmpArray.remove("32");
			}
		}
	} else {
		var tmp = new Array();
		var o = new Object();
		o.value="12";
		o.text="柜台渠道（接口模式）";
		tmp.push(o);
		
		o = new Object();
		o.value="32";
		o.text="支付机构（接口模式）";
		tmp.push(o);
		tmpArray = tmp;
	}
	$("#BIZ_TX_CHNL_CODE").combobox("loadData",tmpArray).combobox("setValue","");
}

/**
 * 通过业务类型代码改变证件类型代码 by chen.feng
 * 
 * @param bizTypeCode
 */
function setIDTypeCode(bizTypeCode, tradeType) {
	var idTypeArray = new Array();
	if ("JH" == tradeType) {
		if ("01" == bizTypeCode) {
			var o = new Object();
			o.value = "01";
			o.text = "身份证";
			idTypeArray.push(o);
			
			o = new Object();
			o.value = "04";
			o.text = "外国护照";
			idTypeArray.push(o);
			
			o = new Object();
			o.value = "05";
			o.text = "外国人持中国永久居留证";
			idTypeArray.push(o);
			
			o = new Object();
			o.value = "06";
			o.text = "港澳居民来往内地通行证";
			idTypeArray.push(o);
			
			o = new Object();
			o.value = "07";
			o.text = "台湾居民来往大陆通行证";
			idTypeArray.push(o);
			
			o = new Object();
			o.value = "09";
			o.text = "中国护照";
			idTypeArray.push(o);
		} else if ("05" == bizTypeCode) {//支付机构
			var o = new Object();
			o.value = "01";
			o.text = "身份证";
			idTypeArray.push(o);
		}
	} else if ("GH" == tradeType) {
		if ("01" == bizTypeCode) {
			var o = new Object();
			o.value = "01";
			o.text = "身份证";
			idTypeArray.push(o);
			
			o = new Object();
			o.value = "04";
			o.text = "外国人持中国永久居留证";
			idTypeArray.push(o);
		} else if ("05" == bizTypeCode) {//支付机构
			var o = new Object();
			o.value = "01";
			o.text = "身份证";
			idTypeArray.push(o);
		}
	}
	
	if (idTypeArray.length == 0) {
		var o = new Object();
		o.value = "01";
		o.text = "身份证";
		idTypeArray.push(o);
		
		o = new Object();
		o.value = "04";
		o.text = "外国护照";
		idTypeArray.push(o);
		
		o = new Object();
		o.value = "04";
		o.text = "外国人持中国永久居留证";
		idTypeArray.push(o);
		
		o = new Object();
		o.value = "06";
		o.text = "港澳居民来往内地通行证";
		idTypeArray.push(o);
		
		o = new Object();
		o.value = "07";
		o.text = "台湾居民来往大陆通行证";
		idTypeArray.push(o);
		
		o = new Object();
		o.value = "09";
		o.text = "中国护照";
		idTypeArray.push(o);
		
		o = new Object();
		o.value = "10";
		o.text = "外交官证";
		idTypeArray.push(o);
	}
	$("#IDTYPE_CODE").combobox("loadData",idTypeArray).combobox("setValue","");
}

/**
 * 删除数组中的指定元素
 */
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};

/**
 * 检索数组中指定元素的位置
 */
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i].value == val) return i;
	}
	return -1;
};