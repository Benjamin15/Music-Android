package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.View;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Home;


public class DeleteSongButton extends AppCompatImageButton implements View.OnClickListener {
    private Context context;
    private Music music;
    /**
     * The section number for the fragment owning this button.
     */

    public DeleteSongButton(Context context){
        super(context);
        this.context = context;
        setOnClickListener(this);
    }

    public DeleteSongButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        this.context = context;
    }

    public DeleteSongButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setMusic(Music music){
        this.music = music;
    }

    @Override
    public void onClick(View v) {
        try {
           /* CommunicationRest communication = new CommunicationRest(
                    getResources().getString(R.string.delete_music_test)
                    + music.getUser().getId() + "/" + music.getId(),
                    "DELETE",
                    context,
                    Home.listMusicUser);
            communication.send();*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
