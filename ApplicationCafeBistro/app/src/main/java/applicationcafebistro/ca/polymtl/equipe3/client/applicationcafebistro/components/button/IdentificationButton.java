package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.button;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;


/**
 * This button send the login to the server when we click on it
 */
public class IdentificationButton extends AppCompatButton
        implements View.OnClickListener, ComponentsListener {

    private String login;

    public IdentificationButton(Context context) {
        super(context);
        init();
    }

    public IdentificationButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        init();
    }

    public IdentificationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            final String GET = "GET";
            String macAddress = DeviceInformation.getMACAddress("eth0");
            String ipv4 = DeviceInformation.getIPAddress(true);
            HashMap map = new HashMap();
            System.out.println("MAC :" + macAddress);
            map.put(getContext().getString(R.string.ip), ipv4);
            map.put(getContext().getString(R.string.mac), macAddress);
            map.put(getContext().getString(R.string.login_param), login);
            JSONObject body = new JSONObject(map);

            String urlParameter = URLEncoder.encode(body.toString(), getContext().getString((R.string.utf8)));
            CommunicationRest communication = new CommunicationRest(
                    getResources().getString(R.string.identification) + getContext().getString(R.string.body) + urlParameter,
                    GET,
                    view,
                    this);
            communication.send();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(JSONObject json) {
        try {
            Intent intent = new Intent(getContext(), ListMusic.class);
            DeviceInformation.idUser = json.getInt(getContext().getString(R.string.identify_json));
            intent.putExtra(getContext().getString(R.string.welcome_message_key),
                    json.getString(getContext().getString(R.string.message_parameter)));
            getContext().startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void setLoginText(Editable editText) {
        this.login = editText.toString();
    }
}
