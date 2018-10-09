package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


public class ServerCommunication {
   /* public RequestQueue queue;
    public static JSONObject get(JSONObject requestJSON, String url){
        RequestQueue queue = Volley.newRequestQueue(this);
        final JSONObject[] jsonResponseArray = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, requestJSON, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Attente de réponse");
                        jsonResponseArray[0] = response;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Pas de réponse");
                    }
                });
        return jsonResponseArray[0];
    }
    public static void post(JSONObject requestJSON, String url){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestJSON, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
    }*/
}
