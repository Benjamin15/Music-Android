package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.listener;


import android.view.View;
import android.widget.ListView;


public interface DragListener {

    void onStartDrag(View itemView);

    void onDrag(int x, int y, ListView listView);

    void onStopDrag(View itemView);
}