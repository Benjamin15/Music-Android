package client.equipe3.polymtl.ca.applicationcafebistro.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import client.equipe3.polymtl.ca.applicationcafebistro.R;
import client.equipe3.polymtl.ca.applicationcafebistro.model.Music;

public class ListMusicAdminAdapter extends ArrayAdapter<Music> {
    private List<Music> musics;
    private Context context;
    private int resource;

    public ListMusicAdminAdapter(Context context, int resource, List<Music> musics) {
        super(context, resource, musics);
        this.context = context;
        this.resource = resource;
        this.musics = musics;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(resource, null, false);

        TextView textViewTitle = view.findViewById(R.id.title);
        TextView textViewDuration = view.findViewById(R.id.duration);
        TextView textViewSuggestBy = view.findViewById(R.id.suggest_by);
        TextView textViewArtist = view.findViewById(R.id.artist);

        Music music = musics.get(position);

        textViewTitle.setText(music.getTitle());
        textViewDuration.setText(music.getDuration());
        textViewSuggestBy.setText(music.getUser().getName());
        textViewArtist.setText(music.getArtist());

        return view;
    }
}