
function setCookie(name, value, hours, path) {
	var name = escape(name);
	var value = escape(value);
	var expires = new Date();
	expires.setTime(expires.getTime() + hours * 3600000);
	path = path == "" ? "" : ";path=" + path;
	_expires = (typeof hours) == "string" ? "" : ";expires="
			+ expires.toUTCString();
	document.cookie = name + "=" + value + _expires + path;
}

// 获取cookie值
function getCookieValue(name) {
	var name = escape(name);
	var allcookies = document.cookie;
	name += "=";
	var pos = allcookies.indexOf(name);
	if (pos != -1) { 
		var start = pos + name.length; 
		var end = allcookies.indexOf(";", start); 
		if (end == -1){
			end = allcookies.length; // 如果end值为-1说明cookie列表里只有一个cookie
		}
		var value = allcookies.substring(start, end); // 提取cookie的值
		return unescape(value); // 对它解码
	} else
		return ""; // 搜索失败，返回空字符串
}
// 删除cookie
function deleteCookie(name, path) {
	var name = escape(name);
	var expires = new Date(0);
	path = path == "" ? "" : ";path=" + path;
	document.cookie = name + "=" + ";expires=" + expires.toUTCString() + path;
}
