package com.qiaqiacloud.app.plugins;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class AutoWifi extends CordovaPlugin {
    private static final String LOG_TAG = "AutoWifi";
    /* cordova actions */
    private static final String ACTION_SET_SSID = "setssid";
    private static final String ACTION_CONNECT = "connect";
    private static final String ACTION_UNCONNECT = "unconnect";
    private static final String ACTION_IS_CONNECTED = "isconnected";
    private static final String ACTION_IS_CONNECTWIFI = "isconnectwifi";

    private WifiToSG wifitosg = null;

    private void init(){
        if (wifitosg == null){
            Context context = cordova.getActivity().getApplicationContext();
            wifitosg = new WifiToSG(context);
        }
    }

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        PluginResult result = null;
        if (ACTION_SET_SSID.equals(action)) {
            result = executeSetWifiInfo(args, callbackContext);
        } else if (ACTION_CONNECT.equals(action)) {
            result = executeConnect(args, callbackContext);
        } else if (ACTION_UNCONNECT.equals(action)) {
            result = executeUnconnect(args, callbackContext);
        } else if (ACTION_IS_CONNECTED.equals(action)) {
            result = executeIsConnected(args, callbackContext);
        } else if (ACTION_IS_CONNECTWIFI.equals(action)) {
            result = executeIsConnectwifi(args, callbackContext);
        } else {
            Log.d(LOG_TAG, String.format("Invalid action passed: %s", action));
            result = new PluginResult(Status.INVALID_ACTION);
        }

        if(result != null) callbackContext.sendPluginResult( result );

        return true;
    }

    private PluginResult executeSetWifiInfo(JSONArray args, final CallbackContext callbackContext){
        Log.w(LOG_TAG, "executeSetWifiInfo");

        init();

        JSONObject options;
        String ssid = null, password = null;
        try {
            options = args.getJSONObject(0);
            ssid = options.getString("ssid");
            password = options.getString("password");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (wifitosg != null)
            wifitosg.SetSSID(ssid, password);
        callbackContext.success();

        return null;
    }

    private PluginResult executeConnect(JSONArray args, final CallbackContext callbackContext){
        Log.w(LOG_TAG, "executeConnect");

        init();

        if (wifitosg != null)
            wifitosg.Connect();
        callbackContext.success();
        return null;
    }

    private PluginResult executeUnconnect(JSONArray args, final CallbackContext callbackContext){
        Log.w(LOG_TAG, "executeUnconnect");

        if (wifitosg != null)
            wifitosg.UnConnect();
        callbackContext.success();
        return null;
    }

    private PluginResult executeIsConnected(JSONArray args, final CallbackContext callbackContext){
        Log.w(LOG_TAG, "executeIsConnected");

        if (wifitosg != null) {
            callbackContext.success(wifitosg.IsConnected() ? 1 : 0);
        } else {
            callbackContext.success(0);
        }

        return null;
    }

    private PluginResult executeIsConnectwifi(JSONArray args, final CallbackContext callbackContext){
        Log.w(LOG_TAG, "executeIsConnectwifi");

        if (wifitosg != null) {
            callbackContext.success(wifitosg.IsConnectedWifi() ? 1 : 0);
        } else {
            callbackContext.success(0);
        }
        return null;
    }
};
