# cordova-plugins-auto-wifi
A cordova plugin, for auto connect specified WiFi, until connected.

# 功能
- set ssid & password what want to connected
    设置需要连接的SSID和密码
- start connecting, until the wifi with the specified ssid is connected
    启动连接，直到连接上指定的SSID
- stop connect wifi
    停止连接

# API
- setssid(ssid, pwd)
    设置需要连接的SSID和密码
- connect
    开始连接，连接过程会断开已连接的WIFI，然后扫描WIFI，并尝试连接WIFI
- unconnect
    断开WIFI连接
- isconnected
    判断是否连接上网络，不限WIFI
- isconnectwifi
    判断是否连接上指定的WIFI
