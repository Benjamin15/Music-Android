package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;


public class IdentificationService {
    private String url ;
    private Context context;
    private PrepareJsonObject prepareJsonObject;

    public IdentificationService(Context context) {
        this.context = context;
        this.url = context.getResources().getString(R.string.identification);
        this.prepareJsonObject = new PrepareJsonObject();
    }

    /**
     * Send a get request with identification informations stored in a JSON Object
     * @param login String  the client identifier
     * @return void
     */
    public void identification(String login) throws JSONException {
        String macAddress = IdentificationManager.getMACAddress("eth0");
        String ipv4 = IdentificationManager.getIPAddress(true);
        Map<String,String> requestMap = createMap(macAddress,ipv4,login);
        JSONObject requestJSON = prepareJsonObject.createJsonObject(requestMap);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url + "?nom=kas", requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.networkResponse);
                    }
                }){
        };

        try {
            System.out.println(jsonObjectRequest.getHeaders());
            System.out.println(jsonObjectRequest.getBody());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Create a Map from the identification informations
     * @param macAddress  the String client's MAC address
     * @param ipAddress   the String client's IP address
     * @param identifier   the String client's identifier
     * @return  address or empty string
     */
    public Map<String, String> createMap(String macAddress, String ipAddress, String identifier) {
        Map<String, String> map = new HashMap<>();
        map.put("ip",ipAddress);
        map.put("MAC",macAddress);
        map.put("nom",identifier);
        return map;
    }
}
