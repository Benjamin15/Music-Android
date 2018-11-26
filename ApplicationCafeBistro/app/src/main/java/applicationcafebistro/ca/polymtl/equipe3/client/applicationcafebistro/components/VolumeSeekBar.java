package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;


import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.button.SoundButton;


public class VolumeSeekBar extends android.support.v7.widget.AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener, ComponentsListener {
    private Context context;
    private SoundButton soundButton;
    private int prevProgress;

    public VolumeSeekBar(Context context) {
        super(context);
        init();
        this.context = context;
    }

    public VolumeSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VolumeSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        soundButton = ((Activity)context).findViewById(R.id.floating_sound_button);
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.get_volume) ,
                getResources().getString(R.string.GET),
                this,
                this);
        communication.send();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        soundButton.setVolumeStatusIcon(progress);
        int diff = progress - prevProgress;
        if(diff > 0){
            increaseVolume(diff);
        }
        else{
            decreaseVolume(Math.abs(diff));
        }
        prevProgress = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void increaseVolume(int percent){
        HashMap map = new HashMap();
        map.put(getContext().getString(R.string.pc), percent);
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.increase_volume) ,
                getResources().getString(R.string.POST),
                this,
                this);
        communication.send(map);
    }

    private void decreaseVolume(int percent){
        HashMap map = new HashMap();
        map.put(getContext().getString(R.string.pc), percent);
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.decrease_volume) ,
                getResources().getString(R.string.POST),
                this,
                this);
        communication.send(map);
    }
    @Override
    public void update(JSONObject json) {
        try {
            this.setProgress(json.getInt("volume"));
            soundButton.setVolumeStatusIcon(this.getProgress());
            prevProgress = this.getProgress();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
