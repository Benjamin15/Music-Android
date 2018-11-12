package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.exception.ManagerException;

public class CommunicationRest {

    private final String url;
    private final int type;
    private final View view;
    private final ComponentsListener component;

    public CommunicationRest(String url, String type, View view, ComponentsListener component) {
        this.url = url;
        this.type = getType(type);
        this.view = view;
        this.component = component;
    }


    public CommunicationRest(String url, String type, View view) {
        this.url = url;
        this.type = getType(type);
        this.view = view;
        this.component = null;
    }

    /**
     * This method send a message to the rest api
     */
    public void send() {
        this.send(null);
    }

    /**
     * this method send a message to the rest api with parameters in the body
     * @param parameters
     */
    public void send(Map parameters) {
        JSONObject body = null;
        if (parameters != null)
            body = createJsonObject(parameters);
        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.start();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (type, url, body, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (component != null)
                            component.update(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int codeError = error.networkResponse.statusCode;
                            String message = new String(error.networkResponse.data);
                            System.out.println(codeError + message);
                            ManagerException managerException = new ManagerException(codeError, message, view);
                            managerException.findError();
                            managerException.display();
                        }
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * this methode transform the type to a Request.Method.type
     * @param type
     * @return
     */
    private int getType(String type) {
        final String GET = "GET";
        final String POST = "POST";
        return type.equals(GET) ? Request.Method.GET
                : type.equals(POST) ? Request.Method.POST
                : Request.Method.DELETE;
    }

    /**
     * Create a JSON Object from a Map
     *
     * @param map Map<String,String></String,String>
     * @return Json Object create from the map
     */
    private JSONObject createJsonObject(Map<String, String> map) {
        return new JSONObject(map);
    }
}