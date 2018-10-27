package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.UnsupportedEncodingException;
import	java.net.URLEncoder;


import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;


public class DeleteSongService {
    private String url;
    private Context context;

    public DeleteSongService(Context context) {
        this.context = context;
        this.url = "";
    }

    public void deleteSong() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this.context);
        String id = "1/";
        String no= "1";
        try {
            id = URLEncoder.encode(id.toString(), "UTF-8");
            no= URLEncoder.encode(id.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

        this.url= "http://132.207.89.35/usager/chanson/1/2";
        queue.start();
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
