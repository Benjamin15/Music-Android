package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;
import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


public class MusicDetailsService {

    public MusicDetailsService(Context context){

    }

    public void musicDetails(String id) throws JSONException {
        String url = "http://132.207.89.35/usager/chanson/" + id;
        //final TextView mTextView = (TextView) findViewById(android.R.id.text1);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, (JSONObject) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO: Handle response
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
    }
}
