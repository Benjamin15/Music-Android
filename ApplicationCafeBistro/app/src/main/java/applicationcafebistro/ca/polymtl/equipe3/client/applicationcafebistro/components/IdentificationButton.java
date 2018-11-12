package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.DeviceInformation;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;


public class IdentificationButton extends android.support.v7.widget.AppCompatButton
        implements View.OnClickListener, ComponentsListener {
    private Context context;
    private String login;

    /**
     * The section number for the fragment owning this button.
     */

    public IdentificationButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public IdentificationButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        this.context = context;
        init();
    }

    public IdentificationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            String macAddress = DeviceInformation.getMACAddress("eth0");
            String ipv4 = DeviceInformation.getIPAddress(true);
            Map<String, String> map = new HashMap();
            map.put("ip", ipv4);
            map.put("MAC", macAddress);
            map.put("nom", login);
            JSONObject body = new JSONObject(map);

            String urlParameter = URLEncoder.encode(body.toString(), "UTF-8");
            CommunicationRest communication = new CommunicationRest(
                    getResources().getString(R.string.identification) + "?body=" + urlParameter,
                    "GET",
                    view,
                    this);
            communication.send();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(JSONObject json) {
        try {
            Intent intent = new Intent(this.context, ListMusic.class);
            System.out.println(json.getString("message"));
            System.out.println(json.getString("identificateur"));
            DeviceInformation.idUser = json.getInt("identificateur");
            intent.putExtra("welcomeMessage", json.getString("message"));
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setLoginText(Editable editText) {
        this.login = editText.toString();
    }
}
