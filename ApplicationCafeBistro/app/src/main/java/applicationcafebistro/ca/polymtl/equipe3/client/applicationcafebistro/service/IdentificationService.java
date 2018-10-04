package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;


import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.IdentificationManager.getIPAddress;
import static applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.IdentificationManager.getMACAddress;

/**
 * Created by moguef on 2018-09-28.
 */

public class IdentificationService {
    private String url = "http://132.207.89.35/indentification";

    public IdentificationService(Context context) {
    }

    public void identification(String login) throws JSONException {
        String macAddress = getMACAddress("eth0");
        String ipv4 = getIPAddress(true); // IPv4
        //String ipv6 = getIPAddress(false); // IPv6
        JSONArray requestJSON = createJsonObject(macAddress,ipv4,login);
    /*
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestJSON, new Response.Listener<JSONObject>() {

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

    public JSONArray createJsonObject(String macAddress, String ipAddress, String identifier) throws JSONException {
        JSONArray JSONIdentification = new JSONArray();
        JSONObject macAddressObject = new JSONObject();
        JSONObject ipAddressObject = new JSONObject();
        JSONObject identifierObject = new JSONObject();
        ipAddressObject.put("ip",ipAddress);
        macAddressObject.put("MAC",macAddress);
        identifierObject.put("nom",identifier);
        JSONIdentification.put(ipAddressObject);
        JSONIdentification.put(macAddressObject);
        JSONIdentification.put(identifierObject);
        String verification = JSONIdentification.toString();
        return JSONIdentification;
    }
}
