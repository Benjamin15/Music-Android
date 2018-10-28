package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.MusicDetailsService;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.MusicDetails;

public class MusicDetailsButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    private Context context;
    private Bundle fragmentBundle;
    private String id; //to be changed by the id from the http request

    private MusicDetailsService musicDetailsService;

    public MusicDetailsButton(Context context){
        super(context);
        this.context=context;
    }

    private void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        try{
            musicDetailsService.musicDetails(id);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Intent intent=new Intent (this.context, MusicDetails.class);
        context.startActivity(intent);
    }

}
