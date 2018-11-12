package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;

public class SendMusicService {

    private String url ;
    private Context context;

    public SendMusicService(Context context) {
        this.context = context;
        this.url = context.getResources().getString(R.string.send_music);
    }

    /**
     * Send a post request with music.mp3 encoded in Base64
     * @param music File  The .mp3 file which we send to the server
     * @return void
     */
    public void sendMusic(File music, String title) {
        final String stringMusic = FileEncoder.encodeFileToBase64(music);
        RequestQueue requestQueue = Volley.newRequestQueue(this.context);
        String urlParameter = null;
        try {
            urlParameter = URLEncoder.encode(title,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url +"483923496"+"?title=" +urlParameter , new Response.Listener<String>() {
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
            public byte[] getBody() {
                try {
                    return stringMusic.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}
