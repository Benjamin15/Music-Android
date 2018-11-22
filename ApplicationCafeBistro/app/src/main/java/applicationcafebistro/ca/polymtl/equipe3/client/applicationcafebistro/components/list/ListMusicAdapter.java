package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.MyViewHolder> implements MusicTouchHelperAdapter {
    private final ArrayList<Music> cartListMusic;
    public ListMusicAdapter() {
        this.cartListMusic = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Music music = cartListMusic.get(position);
        holder.title.setText(music.getTitle());
        holder.artist.setText(music.getArtist());
        holder.duration.setText(music.getDuration());
        holder.suggest_by.setText(music.getUser().getName());
        System.out.println("idUser : " + DeviceInformation.idUser);
        if (music.getUser().getId() == DeviceInformation.idUser)
            holder.viewForeground.setBackgroundResource(R.color.my_music_in_list);
        else
            holder.viewForeground.setBackgroundResource(R.color.other_music_in_list);
    }

    @Override
    public int getItemCount() {
        return cartListMusic.size();
    }

    public void removeItem(int position) {
        if(cartListMusic.get(position).getUser().getId() == DeviceInformation.idUser || DeviceInformation.isAdmin){
            cartListMusic.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void onItemDismiss(int position) {
        if(cartListMusic.get(position).getUser().getId() == DeviceInformation.idUser || DeviceInformation.isAdmin) {
            cartListMusic.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(cartListMusic, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clear() {
        cartListMusic.clear();
    }

    public void addAll(ArrayList<Music> musics) {
        cartListMusic.addAll(musics);
        notifyDataSetChanged();
    }

    public ArrayList<Music> getMusics() {
        return cartListMusic;
    }

    public boolean compare(ArrayList<Music> musics) {
        return musics.equals(cartListMusic);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView title, artist, duration, suggest_by;
        public final RelativeLayout viewForeground;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            artist = view.findViewById(R.id.artist);
            duration = view.findViewById(R.id.duration);
            suggest_by = view.findViewById(R.id.suggest_by);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }
}