# cordova-plugins-auto-wifi
A cordova plugin, for auto connect specified WiFi, until connected.

# Function
- set ssid & password what want to connected
  >设置需要连接的SSID和密码
- start connecting, until the wifi with the specified ssid is connected
  >启动连接，直到连接上指定的SSID
- stop connect wifi
  >停止连接

# API
- 设置需要连接的SSID和密码
<pre><code>setssid(ssid, pwd)</code></pre>
    
- 开始连接，连接过程会断开已连接的WIFI，然后扫描WIFI，并尝试连接WIFI
<pre><code>connect(function(ret) { 
  if (ret == 1) {
    console.log("successful connect to wifi");
  }
})</code></pre>
   
- 断开WIFI连接
<pre><code>unconnect()</code></pre>
    
- 判断是否连接上网络，不限WIFI
<pre><code>isconnected(callback)</code></pre>
    
- 判断是否连接上指定的WIFI
<pre><code>isconnectwifi(callback)</code></pre>
    
