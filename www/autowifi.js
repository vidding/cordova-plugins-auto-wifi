/**
 * Created by suye on 2017/03/27.
 */
function autowifi() {}

/*
 * setssid 配置需要连接的wifi的ssid 和 密码
 */
autowifi.prototype.setssid = function(ssid, password, successCallback, errorCallback) {
    var options = {
        ssid: ssid,
        password: password || ""
    };
    cordova.exec(successCallback, errorCallback, "AutoWifi", "setssid", [options]);
};

/*
 * connect 开始连接wifi
 */
autowifi.prototype.connect = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AutoWifi", "connect", []);
};
/*
 * unconnect 断开wifi
 */
autowifi.prototype.unconnect = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AutoWifi", "unconnect", []);
};
/*
 * isconnected 是否已经连接
 */
autowifi.prototype.isconnected = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AutoWifi", "isconnected", []);
};
/*
 * isconnectwifi 是否连接上指定的WIFI
 */
autowifi.prototype.isconnectwifi = function(successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "AutoWifi", "isconnectwifi", []);
};


autowifi.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.autowifi = new autowifi();
    return window.plugins.autowifi;
};

cordova.addConstructor(autowifi.install);
