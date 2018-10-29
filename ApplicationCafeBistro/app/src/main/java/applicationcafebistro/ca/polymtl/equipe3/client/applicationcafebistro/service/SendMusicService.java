package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;

public class SendMusicService {

    private String url ;
    private Context context;

    public SendMusicService(Context context) {
        this.context = context;
        this.url = context.getResources().getString(R.string.send_music);;
    }

    /**
     * Send a post request with music.mp3 encoded in Base64
     * @param music File  The .mp3 file which we send to the server
     * @return void
     */
    public void sendMusic(File music) {
        final String stringMusic = FileEncoder.encodeFileToBase64(music);
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        requestQueue.start();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url +"9" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return stringMusic == null ? null : stringMusic.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);

      /*  RequestQueue requestQueue = Volley.newRequestQueue(context);
        String stringMusic = FileEncoder.encodeFileToBase64(music);
        requestQueue.start();
        String urlParameter = null;
        try {
            urlParameter = URLEncoder.encode(stringMusic,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject jsonMusic = null;
        try {
            jsonMusic = new JSONObject(stringMusic);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url +"9", jsonMusic, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        requestQueue.add(jsonObjectRequest);*/
    }
}
