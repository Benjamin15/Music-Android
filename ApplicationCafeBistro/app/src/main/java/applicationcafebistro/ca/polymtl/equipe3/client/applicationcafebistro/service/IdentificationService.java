package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;


public class IdentificationService {
    private String url ;
    private Context context;

    public IdentificationService(Context context) {
        this.context = context;
        this.url = context.getResources().getString(R.string.identification);
    }

    public void identification(String login) throws JSONException {
        String macAddress = IdentificationManager.getMACAddress("eth0");
        String ipv4 = IdentificationManager.getIPAddress(true);
        JSONObject requestJSON = createJsonObject(macAddress,ipv4,login);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    public JSONObject createJsonObject(String macAddress, String ipAddress, String identifier) throws JSONException {
        JSONObject JSONIdentification = new JSONObject();
        JSONIdentification.put("ip",ipAddress);
        JSONIdentification.put("MAC",macAddress);
        JSONIdentification.put("nom",identifier);
        return JSONIdentification;
    }
}
