function checkExp(re, s) {
	return re.test(s);
}
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};

function isName(strValue){
	return checkExp(/^([\u4e00-\u9fa5]){2,7}$/, strValue);
}

function isAlphaNumeric(strValue) {
	return checkExp(/^\w*$/gi, strValue);
}
function isPassword(strValue) {
	// 只能是 A-Z a-z 0-9 之间的字母数字 长度6-12
	return checkExp(/^\w{6,12}$/, strValue);
}
function isEmail(strValue) {
	var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
	return checkExp(pattern, strValue);
}
function isChu(strValue) {
	var st ="[A-Z]\\d{9,10}$";
	var re2 = new RegExp(st);
	return re2.test(strValue);
}
function isEmpty(strValue) {
	if (strValue.trim() == "")
		return true;
	else
		return false;
}
function isNumeric(strValue) {
	return checkExp(/^\d+$/g, strValue);
}
function isZhengShu(strValue) {
	return checkExp(/^[1-9]\d*$/, strValue);
}
function isMoney(strValue) {
	return checkExp(/^\d+\.?\d{0,2}$/, strValue);
}
function isPhoneNum(strValue) {
	return checkExp(/^1[3|4|5|6|7|8|9][0-9]\d{8,8}$/, strValue);
}
function isTel(strValue) { 
	return checkExp(/^[0-9]{8,12}$/, strValue);
}
function isChushzheng(strValue) {
	return checkExp(/^[A-Z]\d{9}$/, strValue);
}
function isAge(strValue) {
	return checkExp(/^(\d{1,2})|(1[0-1][0-9])$/, strValue);
}
function isIdcardNum(ID_card) {
	var IDcardLen = ID_card.length;
	var Wi = new Array(); // 位权值数组
	var varArray = new Array();
	var birthday = ''; // 生日

	var numSum = 0; // 数字和
	var regStr = /^\d{15,17}([\dX]{1})?$/;

	if ((IDcardLen != 15) && (IDcardLen != 18)) {
		return false;
	}
	if (!regStr.test(ID_card)) {
		return false;
	}
	if (IDcardLen == 15) {
		birthday = "19" + ID_card.substring(6, 8) + "-"
				+ ID_card.substring(8, 10) + "-" + ID_card.substring(10, 12);
	} else {
		birthday = ID_card.substring(6, 10) + "-" + ID_card.substring(10, 12)
				+ "-" + ID_card.substring(12, 14);
	}
	if (!isDate(birthday)) {
		return false;
	}
	if (IDcardLen == 18) {
		for (var i = 0; i < 17; i++) {
			var k = Math.pow(2, 17 - i);
			Wi[i] = k % 11;
		}
		for (var i = 0; i < IDcardLen - 1; i++) {
			varArray[i] = ID_card.charAt(i);
			varArray[i] = varArray[i] * Wi[i];
			numSum = numSum + varArray[i];
		}
		var checkDigit = 12 - numSum % 11;
		if (checkDigit == 10) {
			checkDigit = 'X';
		} else if (checkDigit == 11) {
			checkDigit = 0;
		} else if (checkDigit == 12) {
			checkDigit = 1;
		}
		if (ID_card.charAt(17).toUpperCase() != checkDigit) {
			return false;
		}
	}
	return true;
}
// 是否 日期格式：YYYY-MM-DD
function isDate(strvalue) {
	var regex = /^(\d{4}-((0[1-9]{1})|(1[0-2]{1}))-((0[1-9]{1})|([1-2]{1}[0-9]{1})|(3[0-1]{1}))){1}$/;
	return checkExp(regex, strvalue);
}
function isPicFile(target) {
	var fileSize = 0;
	var filetypes = [ ".jpg", ".png", ".gif", ".bmp", ".jpeg" ];
	var filemaxsize = 201;//200k
	var filepath = target.value;
	if (!filepath) {
		alert("请选择图片");
		return false;
	}
	var istype = false;
	var fileend = filepath.substring(filepath.lastIndexOf("."));
	fileend = fileend.toLowerCase();
	for (var i = 0; i < filetypes.length; i++) {
		if (filetypes[i] == fileend) {
			istype = true;
			break;
		}
	}
	if (!istype) {
		alert("只接受jgp,png,gif,bmp,jpeg格式图片!");
		return false;
	}
	if (target.files) {
		fileSize = target.files[0].size;
	}
	var size = fileSize / 1024;
	if (size > filemaxsize) {
		alert("图片大小不能大于200k");
		return false;
	}
	return true;
}
