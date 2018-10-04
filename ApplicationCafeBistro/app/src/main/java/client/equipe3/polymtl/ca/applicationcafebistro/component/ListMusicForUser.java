package client.equipe3.polymtl.ca.applicationcafebistro.component;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import client.equipe3.polymtl.ca.applicationcafebistro.R;
import client.equipe3.polymtl.ca.applicationcafebistro.adapter.ListMusicUserAdapter;
import client.equipe3.polymtl.ca.applicationcafebistro.model.Music;
import client.equipe3.polymtl.ca.applicationcafebistro.model.User;

public class ListMusicForUser extends ListView {
    private List<Music> musics;

    public ListMusicForUser(Context context) {
        super(context);
        init();
    }

    public ListMusicForUser(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ListMusicForUser(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        musics = new ArrayList();

        ArrayList<User> users = new ArrayList();
        users.add(new User(1, "benjamin", "127.0.0.1", "AB:AA:AC:AE:ED:AB"));
        users.add(new User(2, "Wassimouche", "143.34.213.4", "AB:BB:CC:DD:EE:FF"));
        users.add(new User(3, "Souki", "10.20.30.40", "AB:AA:AC:AE:ED:AB"));
        users.add(new User(4, "SegaSon", "50.60.70.80", "AB:AA:AC:AE:ED:AB"));

        musics.add(new Music(1, "title1", "artist1", "01:02", users.get(0),
                false));
        musics.add(new Music(2, "title2", "artist2", "11:32", users.get(1),
                false));
        musics.add(new Music(3, "title3", "artist3", "01:42", users.get(2),
                false));
        musics.add(new Music(4, "title4", "artist4", "02:42", users.get(3),
                true));
        ListMusicUserAdapter adapter = new ListMusicUserAdapter(getContext(),
                R.layout.component_list_music_user, musics);
        setAdapter(adapter);
    }
}
