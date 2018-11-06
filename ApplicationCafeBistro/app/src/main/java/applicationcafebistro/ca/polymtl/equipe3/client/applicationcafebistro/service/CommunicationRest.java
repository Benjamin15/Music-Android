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

import java.util.HashMap;
import java.util.Map;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.Components;

public class CommunicationRest {

    private String url;
    private int type;
    private Context context;
    private Components component;

    public CommunicationRest(String url, String type, Context context, Components component) {
        this.url = url;
        this.type = getType(type);
        this.context = context;
        this.component = component;
    }


    public CommunicationRest(String url, String type, Context context) {
        this.url = url;
        this.type = getType(type);
        this.context = context;
        this.component = null;
    }

    public void send(Map parameters) throws JSONException {
        JSONObject body = null;
        if (parameters != null ) {
            System.out.println("create body");
            body = new PrepareJsonObject().createJsonObject(parameters);
            System.out.println(body.toString());
        }
        System.out.println("send");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (type, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response");
                        if (component != null)
                            component.update(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error communication");
                        System.out.println(error.getMessage());
                        //if (component != null)
                          //  component.cancel();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private int getType(String type) {
        return type == "GET" ? Request.Method.GET
                : type == "POST" ? Request.Method.POST
                : Request.Method.DELETE;
    }
}
