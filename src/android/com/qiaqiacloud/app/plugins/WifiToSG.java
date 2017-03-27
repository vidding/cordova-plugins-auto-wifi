package com.example.administrator.jiance.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
// import android.widget.Toast;

import com.example.administrator.jiance.ui.DevSetAty;

/**
 * Created by suye on 2016/3/31.
 */
public class WifiToSG {

    private boolean bStart;
    private String sgSSID;
    private String sgWifiPwd;
    private boolean bConnected; //表示是否 连接 上 wifi 不一定是 smartgates 热点，
    private boolean bConnectedWifi; //表示连接上 指定wifi，这里指smartgates 热点，
    private boolean bWifiEnabled; //表示 WIFI 是否已经打开
    private WifiAdmin wifiAdmin;
    private Context context;

    public WifiToSG(Context context){

        this.context = context;
        sgSSID = sgWifiPwd = "energia"; // "smartgates";
        sgWifiPwd = "launchpad";
        bStart = false;
        bConnected = false;
        bWifiEnabled = false;
        wifiAdmin = new WifiAdmin(context);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                System.out.println("receive : " + action);

                if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)){

                } else if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action)){
                    int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                    System.out.println("wifi state: " + wifiState);

                    if (wifiState == WifiManager.WIFI_STATE_ENABLED) {

                        if (bStart && !bWifiEnabled) {
                            wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo(sgSSID, sgWifiPwd, 3));
                        }

                        bWifiEnabled = true;
                    } else {
                        bWifiEnabled = false;
                        bConnected = false;
                        bConnectedWifi = false;
                    }

                } else if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)){
                    NetworkInfo networkInfo =  WifiToSG.getActiveNetwork(context);
                    if (networkInfo != null) {
//                System.out.println("networkinfo != null");
//                System.out.println("networkinfo getType = " + networkInfo.getType());
//                System.out.println("networkinfo getSubtype = " + networkInfo.getSubtype());
//                System.out.println("networkinfo getTypeName = " + networkInfo.getTypeName());
//                System.out.println("networkinfo getSubtypeName = " + networkInfo.getSubtypeName());
//                System.out.println("networkinfo getState = " + networkInfo.getState());
//                System.out.println("networkinfo isConnected = " + networkInfo.isConnected());
//                System.out.println("networkinfo isConnectedOrConnecting = " + networkInfo.isConnectedOrConnecting());
                        System.out.println("networkinfo toString = " + networkInfo.toString());
//                System.out.println("networkinfo getExtraInfo = " + networkInfo.getExtraInfo());

                        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                                && networkInfo.getState() == NetworkInfo.State.CONNECTED){

                            bConnected = true;
                            if (networkInfo.getExtraInfo().equals("\""+ sgSSID + "\"")){
                                bConnectedWifi = true;
                                //Toast.makeText(context, "连接smartgates成功...", Toast.LENGTH_LONG).show();
                            } else {
                                bConnectedWifi = false;
                                //Toast.makeText(context, "未连接smartgates，请重新操作", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        System.out.println("networkinfo = null");
                        bConnected = false;
                        bConnectedWifi = false;
                    }
                }
            }
        }, filter);
    }

    public static NetworkInfo getActiveNetwork(Context context){
        if (context == null)
            return null;
        ConnectivityManager mConnMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr == null)
            return null;
        NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo(); // 获取活动网络连接信息
        return aActiveInfo;
    }

    public void SetSSID(String ssid, String pwd){
        sgSSID = ssid;
        sgWifiPwd = pwd;
    }

    public boolean IsConnected(){
        return bConnected;
    }
    public boolean IsConnectedWifi() { return bConnectedWifi; }

    // 开始尝试连接 sgSSID
    public void Connect(){
        bStart = true;
        if (!bConnectedWifi) {
            if (bWifiEnabled) {
                System.out.println("wifi enabled 1");
                wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo(sgSSID, sgWifiPwd, 3));
            } else {
                System.out.println("wifi enabled 2");
                wifiAdmin.openWifi();
            }
        }
    }

    // 断开当前连接
    public void UnConnect(){
        WifiAdmin wifi = new WifiAdmin(context);
        wifi.disconnectWifi(wifi.getNetworkId());
    }
}
