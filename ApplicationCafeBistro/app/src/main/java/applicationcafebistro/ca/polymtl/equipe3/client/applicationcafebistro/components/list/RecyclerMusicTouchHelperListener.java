package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list;

import android.support.v7.widget.RecyclerView;

public interface RecyclerMusicTouchHelperListener {

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    void onMoved(RecyclerView.ViewHolder viewHolder, int positionStart, int positionEnd);
}
