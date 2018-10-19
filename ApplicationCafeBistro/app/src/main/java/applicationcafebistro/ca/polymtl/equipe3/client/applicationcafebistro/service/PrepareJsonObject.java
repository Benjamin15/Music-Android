package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class PrepareJsonObject {

    public PrepareJsonObject(){
    }

    public JSONObject createJsonObject(Map<String,String>map) throws JSONException {
        JSONObject object = new JSONObject(map);
        return object;
    }
}
