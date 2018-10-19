package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class PrepareJsonObject {

    public PrepareJsonObject(){
    }

    /**
     * Create a JSON Object from a Map
     * @param map   Map<String,String></String,String>
     * @return  Json Object create from the map
     */
    public JSONObject createJsonObject(Map<String,String>map) throws JSONException {
        JSONObject object = new JSONObject(map);
        return object;
    }
}
