package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.button;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.VolumeSeekBar;
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
        this.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        getVolume(view, this);
    }

    private void getVolume(View view, ComponentsListener componentsListener){
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.get_volume),
                getResources().getString(R.string.GET),
                view,
                componentsListener);
        communication.send();
    }
    @Override
    public void update(JSONObject json) {
        try {
            boolean muteStatus = json.getString(getResources().getString(R.string.sourdine)).equals("1");
            int currentVolume = json.getInt(getResources().getString(R.string.volume));
            VolumeSeekBar volumeSeekBar = ((Activity)context).findViewById(R.id.seekBar);
            if(volumeSeekBar.getProgress() != currentVolume){
                volumeSeekBar.setInitialized(false);
                SoundButton soundButton = ((Activity)context).findViewById(R.id.floating_sound_button);
                soundButton.setVolumeStatusIcon(currentVolume);
                getVolume(this, volumeSeekBar);
            }
            SnackBarSuccess.make(ListMusic.view, context, getContext().getString(R.string.volume_status_string) + Integer.toString(currentVolume) + " " + getContext().getString(R.string.mute_status_string) + Boolean.toString(muteStatus), 3000);
            SnackBarSuccess.show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}