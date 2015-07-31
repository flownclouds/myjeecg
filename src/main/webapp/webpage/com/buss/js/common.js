var m = document.uniqueID /*IE*/
	&& document.compatMode /*>=IE6*/
	&& !window.XMLHttpRequest /*<=IE6*/
	&& document.execCommand;
try {
	if (!!m) {
		m("BackgroundImageCache", false, true) /* = IE6 only */
	}
} catch (oh) { };
var SysEC = {};
var ua = navigator.userAgent.toLowerCase();
var s;
(s = ua.match(/msie ([\d.]+)/)) ? SysEC.ie = s[1] :
	(s = ua.match(/firefox\/([\d.]+)/)) ? SysEC.firefox = s[1] :
		(s = ua.match(/chrome\/([\d.]+)/)) ? SysEC.chrome = s[1] :
			(s = ua.match(/opera.([\d.]+)/)) ? SysEC.opera = s[1] :
				(s = ua.match(/version\/([\d.]+).*safari/)) ? SysEC.safari = s[1] : 0;
var version;
if (SysEC.ie) { version = 'ie_' + SysEC.ie.substring(0, 1); }
if (SysEC.firefox) { version = 'firefox_' + SysEC.firefox.substring(0, 1); }
if (SysEC.chrome) { version = 'chrome_' + SysEC.chrome.substring(0, 1); }
if (SysEC.opera) { version = 'opera_' + SysEC.opera.substring(0, 1); }
if (SysEC.safari) { version = 'safari_' + SysEC.safari.substring(0, 1); }
document.documentElement.className = version;

//Namespace
window.usingNamespace = function (a) {
	var ro = window;
	if (!(typeof (a) === "string" && a.length != 0)) {
		return ro;
	}
	var co = ro;
	var nsp = a.split(".");
	for (var i = 0; i < nsp.length; i++) {
		var cp = nsp[i];
		if (!ro[cp]) {
			ro[cp] = {}; //定义个空对象
		};
		co = ro = ro[cp];
	};
	return co;
};

jQuery.extend({
	isloading: function (value) {
		ik.common.NoLoading = !value;
	},
	alert: function (content, callback) {
		art.dialog({
			lock: true,
			ok: true,
			focus: true,
			width: '300px',
			content: content,
			close: callback || true
		});
	},
	confirm: function (content, okCallback, cancelCallback) {
		art.dialog({
			lock: true,
			content: content,
			ok: okCallback,
			cancelVal: '取消',
			cancel: cancelCallback || true,
			id: "dialogconfirm"
		});
	},
	ezairportfilter:function () {
		$(".autoairport").ezfilter(true, info_ud_airportcitycountry);
	},
	getUrl: function (url) { if (0 == url.indexOf("~/")) url = url.replace("~/", ""); return $.InfoSky.BuildUrl("RootSite") + url; }
});
$.fn.serializeObject = function()
{
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name]) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

usingNamespace("ik")["common"] = {
	Loading: function () {
		if (ik.common.NoLoading != undefined && ik.common.NoLoading == true) {
			return;
		}
		art.dialog({
			title: "正在加载...",
			id: "loading",
			lock: true,
			background: '#000', // 背景色
			opacity: 0.2, // 透明度
			esc: false,
			padding: "20px 50px",
			drag: false,
			resize: false,
			init: function () { $(".aui_close").hide(); },
			fixed: true
		});
	},
	UnLoading: function () {


		$(".aui_close").show();
		if (art.dialog.list['loading'] == undefined)
			return;
		else {
			art.dialog.list['loading'].close();
		}
	},
	gxaAjax: function (type, url, data, datatype, successCallback, errorCallback) {
		$.ajax({
			type: type,
			url: url,
			data: data,
			success: successCallback || ik.common.ajaxDone,
			dataType: datatype || "json",
			error: errorCallback || ik.common.ajaxError
		});
		return false;
	},
	post: function (url, data, callback, datatype, isloading) {
		var loading = true;
		if (isloading != undefined && isloading == false)
			loading = false;
		$.isloading(loading);

		return ik.common.gxaAjax('POST', url, data, datatype, callback);
	},
	get: function (url, data, callback, datatype) {
		return ik.common.gxaAjax('GET', url, data, datatype, callback);
	},
	/*提交表单*/
	postForm: function (form, callback, datatype) {
		var $form = $(form);
		var action = form.method || 'POST';
		var url = $form.attr("action");
		var data = $form.serializeArray();
		var dataType = datatype || "json";
		return ik.common.gxaAjax(action, url, data, datatype, callback);
	},
	/*模拟同步提交表单*/
	mockPostForm: function (url, data,target) {
		var form = document.createElement("form");
		document.body.appendChild(form);
		$.each(data, function (i, item) {
			var input = document.createElement("input");
			input.setAttribute("type", "hidden");
			input.setAttribute("name", item.name);
			input.setAttribute("value", item.value);
			form.appendChild(input);
		});
		form.action = url;
		form.method = "post";
		if(target!=undefined){
			form.target=target;
		}
		form.submit();
		$(form).remove();
	},

	ajaxError: function (xhr, ajaxOptions, thrownError) {
		ik.common.UnLoading();
		if (xhr.readyState == 4) {
			if ("undefined" != typeof alertMsg) {
				alertMsg.error("<div>Http status: " + xhr.status + " " + xhr.statusText + "</div>"
				+ "<div>ajaxOptions: " + ajaxOptions + "</div>"
				+ "<div>thrownError: " + thrownError + "</div>"
				+ "<div>" + xhr.responseText + "</div>");
			} else {
				if (ajaxOptions == "timeout") {
					$.alert("请求超时！");
				} else {
					var prex = $.trim(xhr.responseText) == "" ? "Ajax错误！" : "";
					$.alert(prex + xhr.responseText);
				}
			}
		}
		else {
			$.alert("系统错误!");
		}
	},
	ajaxDone: function (json) {

	},
	initpage: function () {
		$(document).ajaxStart(function () {
			ik.common.Loading();
		}).ajaxStop(function () {
			ik.common.UnLoading();
		});
	},
	newGuid: function () {
		var guid = "";
		for (var i = 1; i <= 32; i++) {
			var n = Math.floor(Math.random() * 16.0).toString(16);
			guid += n;
			if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
				guid += "-";
		}
		return guid;
	},
	//查询序列化数组
	searchSerializeArray: function (arr, name) {
		var obj = null;
		$.each(arr, function (i, item) {
			if (item.name == name) {
				obj = item;
				return false;
			}
		});
		return obj;
	},
	//查询序列化数组并赋值
	setSerializeArray: function (arr, name, value) {
		var obj = ik.common.searchSerializeArray(arr, name);
		if (obj == null)
			arr.push({ name: name, value: value });
		else
			obj.value = value;
	},
	validateAWB: function (awbNo, isMail) {
		//0成功，-1位数不够，-2：不符合模7
		var result = { tag: '0', awbpre: '', awnno: '' };
		var $awbNo = $("#" + awbNo);
		var val_awbNo = $awbNo.val();
		if (isMail != undefined && isMail == true) {
			if ($.trim(val_awbNo).length != 8) {
				result.tag = -1;
				return result;
			}
			if (!$.validator.valicheck("IsMod7", val_awbNo.replace(' ', ''))) {
				try { $awbNo.focus() } catch (err) { };

				result.tag = -2;
				return result;
			}
			result.tag = 0;
			result.awbpre = "000";
			result.awnno = val_awbNo.replace(' ', '');
			return result;
		}
		else {
			if ($.trim(val_awbNo).length != 11) {
				result.tag = -1;
				return result;
			}
			if (!$.validator.valicheck("IsAWBNO", val_awbNo)) {
				try { $awbNo.focus() } catch (err) { };

				result.tag = -2;
				return result;
			}
			var _no = $.trim(val_awbNo);
			result.tag = 0;
			result.awbpre = _no.substr(0, 3);
			result.awnno = _no.substr(3);
			return result;
		}
	},

	validateAWBNo: function (val_awbNo, isMail) {
		//0成功，-1位数不够，-2：不符合模7
		var result = { tag: '0', awbpre: '', awnno: '' };
		if (isMail != undefined && isMail == true) {
			if (val_awbNo.replace('-', '').replace(' ', '').replace(/_/g, '').length < 8) {
				result.tag = -1;
				return result;
			}
			if (!$.validator.valicheck("IsMod7", val_awbNo.replace(' ', ''))) {
				try { $awbNo.focus() } catch (err) { };

				result.tag = -2;
				return result;
			}
			result.tag = 0;
			result.awbpre = "000";
			result.awnno = val_awbNo.replace(' ', '');
			return result;
		}
		else {
			if (val_awbNo.replace('-', '').replace(' ', '').replace(/_/g, '').length < 11) {
				result.tag = -1;
				return result;
			}
			if (!$.validator.valicheck("IsAWBNO", val_awbNo)) {
				try { $awbNo.focus() } catch (err) { };

				result.tag = -2;
				return result;
			}
			var _no = val_awbNo.replace('-', '').replace(' ', '').replace(/_/g, '');
			result.tag = 0;
			result.awbpre = _no.substr(0, 3);
			result.awnno = _no.substr(3);
			return result;
		}
	},

	//浮点数相加
	AccAdd: function (arg1, arg2) {
		var r1, r2, m;
		try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
		try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
		m = Math.pow(10, Math.max(r1, r2));
		return (ik.common.AccMul(arg1, m) + ik.common.AccMul(arg2, m)) / m;
	},
	//乘法
	AccMul: function (arg1, arg2) {
		var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
		try { m += s1.split(".")[1].length } catch (e) { }
		try { m += s2.split(".")[1].length } catch (e) { }
		return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
	},
	//除法
	AccDiv: function (arg1, arg2) {
		var t1 = 0, t2 = 0, r1, r2;
		try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
		try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
		with (Math) {
			r1 = Number(arg1.toString().replace(".", ""));
			r2 = Number(arg2.toString().replace(".", ""));
			return (r1 / r2) * pow(10, t2 - t1);
		}
	},
	//截取小数 , 不超过lenth 位,4舍5入
	CutNumber: function (number, cutlength) {
		number = Number(number);
		var n = number.toString();
		//传入的数值没有小数点时,直接返回
		if (n.indexOf(".") < 0) {
			return Number(number);
		}
		var temp = n.substring(n.indexOf(".") + 1);
		//传入的数值小数位数不大于要截取的位数时,直接返回
		if (temp.length <= cutlength) {
			return Number(number);
		}

		var needNumer = n.substring(0, n.indexOf(".") + 1 + Number(cutlength));
		var afterCutTemp = temp.substr(cutlength, 1);

		//4舍5入
		if (Number(afterCutTemp) > 4) {
			if (cutlength > 0) {
				var needtoadd = "0.";
				for (var i = 0; i < cutlength - 1; i++) {
					needtoadd = needtoadd + "0";
				}
				needtoadd = needtoadd + "1";
				needNumer = ik.common.AccAdd(needNumer, needtoadd);
			} else {
				needNumer = Number(needNumer) + 1;
			}
		}
		return Number(needNumer);
	}


};



function createwindow_my(title, addurl,width,height,callback,id) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
	//--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			id:id,
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
			ok: callback || function(){
				iframe = this.iframe.contentWindow;
				saveObj();
				return false;
			},
			cancelVal: '关闭',
			cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			id:id,
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
			ok: callback ||function(){
				iframe = this.iframe.contentWindow;
				saveObj();
				return false;
			},
			cancelVal: '关闭',
			cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
	//--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数

}
function createwindow_detail_my(title, addurl,width,height,callback,id) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = window.top.document.body.offsetWidth;
		height =window.top.document.body.offsetHeight-100;
	}
	//--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			id:id,
			content: 'url:'+addurl,
			lock : true,
			//zIndex:1990,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
			cancelVal: '关闭',
			cancel: true /*为true等价于function(){}*/
		}).zindex();
	}else{
		W.$.dialog({
			id:id,
			content: 'url:'+addurl,
			lock : true,
			width:width,
			//zIndex:1990,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
			cancelVal: '关闭',
			cancel: true /*为true等价于function(){}*/
		}).zindex();
	}
	//--author：JueYue---------date：20140427---------for：弹出bug修改,设置了zindex()函数

}
function doSubmitForm(url,name){
	if(url==undefined||url==null){
		url=$('#' + name).attr("action");
	}
	var data=$('#' + name).serializeArray();
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : data,
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				alertTip_dialog(msg,"提示",function(){
					windowapi.close();
				});
				//windowapi.close();

			}
		}
	});
}

function alertTip_dialog(msg,title,callback) {
	//$.dialog.setting.zIndex = 1980;
	title = title?title:"提示信息";
	W.$.dialog({
		title:title,
		icon:'tips.gif',
		lock : true,
		parent:windowapi,
		content: msg,
		ok: callback

	}).zindex();
}

