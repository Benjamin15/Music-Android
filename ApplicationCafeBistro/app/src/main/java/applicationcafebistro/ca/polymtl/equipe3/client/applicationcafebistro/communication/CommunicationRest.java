package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.snackbar.SnackBarError;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.exception.ManagerException;

public class CommunicationRest {

    private String url;
    private int type;
    private View view;
    private ComponentsListener component;

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

    public void send(){
        this.send(null);
    }

    public void send(Map parameters) {
        JSONObject body = null;
        if (parameters != null) {
            System.out.println("create body");
            body = createJsonObject(parameters);
            System.out.println(body.toString());
        }
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
                        } else {
                            SnackBarError.make(view, view.getContext(), "Le serveur n'est pas accessible.", 3000);
                            SnackBarError.show();
                        }
                        System.out.println("error communication");
                        System.out.println(error.getMessage());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private int getType(String type) {
        return type.equals("GET") ? Request.Method.GET
                : type.equals("POST") ? Request.Method.POST
                : Request.Method.DELETE;
    }

    /**
     * Create a JSON Object from a Map
     *
     * @param map Map<String,String></String,String>
     * @return Json Object create from the map
     */
    public JSONObject createJsonObject(Map<String, String> map) {
        JSONObject object = new JSONObject(map);
        return object;
    }
}
