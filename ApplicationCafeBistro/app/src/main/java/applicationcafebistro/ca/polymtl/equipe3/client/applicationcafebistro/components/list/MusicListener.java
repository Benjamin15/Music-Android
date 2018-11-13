package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.User;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Identification;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;

/**
 * This class is used to listen events in the listView(recycler)
 * We implement delete (onSwiped), reverse (onMoved) and insert (update) function
 */
public class MusicListener implements RecyclerMusicTouchHelperListener, ComponentsListener {

    private final ListMusicAdapter adapter;
    public MusicListener(ListMusicAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        final String DELETE = "DELETE";
        if (viewHolder instanceof ListMusicAdapter.MyViewHolder) {
            CommunicationRest communication = new CommunicationRest(
                    ListMusic.view.getResources().getString(R.string.delete_music_test) + Integer.toString(DeviceInformation.idUser) + "/" +
                            adapter.getMusics().get(position).getId(),
                    DELETE,
                    ListMusic.view);
            communication.send();
            adapter.removeItem(viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void onMoved(RecyclerView.ViewHolder viewHolder, int positionStart, int positionEnd) {
        if (viewHolder instanceof ListMusicAdapter.MyViewHolder) {
            adapter.onItemMove(positionStart, positionEnd);
        }
    }

    @Override
    public void update(JSONObject json) {
        JSONArray array = null;
        Context context = Identification.context;
        ArrayList<Music> musics = new ArrayList<>();
        try {
            array = json.getJSONArray(context.getString(R.string.chansons_json));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (array != null) {
            for (int i = 0; i < array.length(); i++) {
                try {
                    JSONObject object = (JSONObject) array.get(i);
                    User user = new User(object.getString(context.getString(R.string.suggest_by_json)));
                    Music music = new Music(object.getInt(context.getString(R.string.no_json)),
                            object.getString(context.getString(R.string.title_json)),
                            object.getString(context.getString(R.string.artist_json)),
                            object.getString(context.getString(R.string.duration_json)),
                            user, object.getString(context.getString(R.string.owner_json)).equals("1"));
                    musics.add(music);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (!adapter.compare(musics)) {
                adapter.clear();
                adapter.addAll(musics);
            }
        }
    }
}
