package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.button;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;


public class VolumeStatusButton extends FloatingActionButton
        implements View.OnClickListener, ComponentsListener {
    private int currentVolume;
    private boolean muteStatus;

    public VolumeStatusButton(Context context) {
        super(context);
        init();
    }

    public VolumeStatusButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        init();
    }

    public VolumeStatusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.get_volume),
                getResources().getString(R.string.GET),
                view,
                this);
        communication.send();
    }

    @Override
    public void update(JSONObject json) {
        try {
            muteStatus = json.getBoolean(getResources().getString(R.string.sourdine));
            currentVolume = json.getInt(getResources().getString(R.string.volume));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}