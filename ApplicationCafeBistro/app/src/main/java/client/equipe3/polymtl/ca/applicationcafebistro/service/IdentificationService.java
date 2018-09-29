package client.equipe3.polymtl.ca.applicationcafebistro.service;


import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class IdentificationService {
    private String url = "http://132.207.89.35/indentification";
    private Context applicationContext;

    public IdentificationService(Context context){
      applicationContext = context;
    }

    public void identification() {
        String macAddress = getMacAddress(applicationContext);
        System.out.println(macAddress);
        /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
                */
    }

    public String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String macAddress = wifiManager.getConnectionInfo().getMacAddress();
        if (macAddress == null) {
            macAddress = "failure getting the MAC address";
        }
        return macAddress;
    }

    public JSONArray createJsonObject(String macAddress, String ipAddress, String identifier){
        JSONArray objectToSend = new JSONArray();
        return objectToSend;
    }
}
