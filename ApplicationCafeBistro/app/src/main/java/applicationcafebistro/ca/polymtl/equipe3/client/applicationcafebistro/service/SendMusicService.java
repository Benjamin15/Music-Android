package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String stringMusic = FileEncoder.encodeFileToBase64(music);
        requestQueue.start();
        String urlParameter = null;
        try {
            urlParameter = URLEncoder.encode(stringMusic,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url +"9?music=" + urlParameter , null, new Response.Listener<JSONObject>() {

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
}
