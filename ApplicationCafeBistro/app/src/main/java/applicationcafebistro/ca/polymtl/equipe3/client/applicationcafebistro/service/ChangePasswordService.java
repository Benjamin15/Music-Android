package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import	java.net.URLEncoder;

import java.util.HashMap;
import java.util.Map;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;


public class ChangePasswordService {
    private String url ;
    private Context context;
    private PrepareJsonObject prepareJsonObject;

    public ChangePasswordService(Context context) {
        this.context = context;
        this.url = "http://132.207.89.35:80/superviseur/changement_mdp";
        this.prepareJsonObject = new PrepareJsonObject();
    }

    public void changePassword() throws JSONException{
        String oldPwd= "lola09";
        String newPwd= "new";
        Map<String,String> requestMap = createMap(oldPwd,newPwd);
        JSONObject requestJSON = prepareJsonObject.createJsonObject(requestMap);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String urlParameter = null;
        try {
            urlParameter = URLEncoder.encode(requestJSON.toString(),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url +"?name=" + urlParameter, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.networkResponse);
                    }
                }){ @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("Accept", "application/json");
                        return headers;
                    }
        };

        try {
            System.out.println(jsonObjectRequest.getHeaders());
            System.out.println(jsonObjectRequest.getBody());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    public Map<String, String> createMap(String oldPwd, String newPwd) {
        Map<String, String> map = new HashMap<>();
        map.put("ancien",oldPwd);
        map.put("nouveau",newPwd);
        return map;
    }



}
