package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.model.Music;

public class ListMusicAdapter extends RecyclerView.Adapter<ListMusicAdapter.MyViewHolder> implements MusicTouchHelperAdapter{
    private Context context;
    private ArrayList<Music> cartListMusic;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, artist, duration;
        public ImageView thumbnail;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            artist = view.findViewById(R.id.artist);
            duration = view.findViewById(R.id.duration);
            thumbnail = view.findViewById(R.id.thumbnail);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


    public ListMusicAdapter(Context context) {
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return cartListMusic.size();
    }

    public void removeItem(int position) {
        cartListMusic.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(Music item) {
        cartListMusic.add(item);
        notifyItemInserted(cartListMusic.size() - 1);
    }

    @Override
    public void onItemDismiss(int position) {
        cartListMusic.remove(position);
        notifyItemRemoved(position);
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
        for (Music music : musics)
            cartListMusic.add(music);
        notifyDataSetChanged();
    }

    public ArrayList<Music> getMusics() {
        return cartListMusic;
    }
    public boolean compare(ArrayList<Music> musics) {
        return musics.equals(cartListMusic);
    }
}