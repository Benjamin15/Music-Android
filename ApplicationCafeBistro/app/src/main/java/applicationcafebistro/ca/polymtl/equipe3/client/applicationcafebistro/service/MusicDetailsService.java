package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import	android.widget.TextView;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;

public class MusicDetailsService {

    public MusicDetailsService(Context context){

    }

    public void musicDetails(String id) throws JSONException {
        String url= "http://132.207.89.35/usager/chanson/" + id;
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
