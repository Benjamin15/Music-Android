package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list;

import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ComponentsListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.User;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Home;

public class MusicListener implements RecyclerMusicTouchHelperListener, ComponentsListener {

    ListMusicAdapter adapter;

    public MusicListener(ListMusicAdapter adapter) {
        this.adapter = adapter;
    }
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ListMusicAdapter.MyViewHolder) {
            try {
                System.out.println("try to delete");
                CommunicationRest communication = new CommunicationRest(
                        Home.context.getResources().getString(R.string.delete_music_test) + "1/" +
                                adapter.getMusics().get(position).getId(),
                        "DELETE",
                        Home.context);
                communication.send();
                adapter.removeItem(viewHolder.getAdapterPosition());
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        ArrayList<Music> musics = new ArrayList<>();
        try {
            array = json.getJSONArray("chansons");
        } catch (JSONException e) {
            // Bandeau erreur
        }

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject object = (JSONObject) array.get(i);
                User user = new User(object.getString("proposeePar"));
                Music music = new Music(object.getInt("no"), object.getString("titre"),
                        object.getString("artiste"), object.getString("duree"),
                        user, object.getString("proprietaire") == "0" ? false : true);
                musics.add(music);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (!adapter.compare(musics)) {
            System.out.println("changement");
            adapter.clear();
            adapter.addAll(musics);
        }
    }
}
