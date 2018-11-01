package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.adapter.ListMusicUserAdapter;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.User;

public class ListMusicForUser extends ListView implements Components{
    private List<Music> musics;

    public ListMusicForUser(Context context) {
        super(context);
        musics = new ArrayList();
    }

    public ListMusicForUser(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        musics = new ArrayList();
    }

    public ListMusicForUser(Context context, AttributeSet attrs) {
        super(context, attrs);
        musics = new ArrayList();
    }

    @Override
    public void update(JSONObject json) {
        System.out.println("receive response");
        musics.clear();
        try {
            JSONArray array = json.getJSONArray("chansons");
            for (int i = 0 ; i < array.length() ; i++) {
                JSONObject object = (JSONObject) array.get(i);
                User user = new User(object.getString("proposeePar"));
                Music music = new Music(object.getInt("no"), object.getString("titre"),
                        object.getString("artiste"), object.getString("duree"),
                        user, object.getString("proprietaire") == "0" ? false : true);
                musics.add(music);
            }
            ListMusicUserAdapter adapter = new ListMusicUserAdapter(getContext(),
                    R.layout.component_list_music_user, musics);
            setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
