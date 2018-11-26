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
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.snackbar.SnackBarSuccess;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.ListMusic;


public class VolumeStatusButton extends FloatingActionButton
        implements View.OnClickListener, ComponentsListener {
    private final Context context;

    public VolumeStatusButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public VolumeStatusButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        this.context = context;
        init();
    }

    public VolumeStatusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
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
            boolean muteStatus = json.getBoolean(getResources().getString(R.string.sourdine));
            int currentVolume = json.getInt(getResources().getString(R.string.volume));
            SnackBarSuccess.make(ListMusic.view, context, getContext().getString(R.string.volume_status_string) + Integer.toString(currentVolume) + "\n" + getContext().getString(R.string.mute_status_string) + Boolean.toString(muteStatus), 3000);
            SnackBarSuccess.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}