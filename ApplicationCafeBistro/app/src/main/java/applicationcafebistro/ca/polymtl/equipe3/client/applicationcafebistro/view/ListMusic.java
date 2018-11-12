package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;


import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.ListMusicAdapter;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.MusicListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.RecyclerMusicTouchHelper;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.snackbar.SnackBarSuccess;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;

public class ListMusic extends AppCompatActivity {

    public static MusicListener listenerMusic;
    public static View view;

    private static final String TAG = ListMusic.class.getSimpleName();
    private RecyclerView recyclerView;
    private ListMusicAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_music);
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ListMusicAdapter(this);
        listenerMusic = new MusicListener(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        view = getWindow().getDecorView().getRootView();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerMusicTouchHelper(0, ItemTouchHelper.LEFT, listenerMusic);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        Intent intent = new Intent(ListMusic.this, ServiceGetList.class);
        startService(intent);
        SnackBarSuccess.make(view, getApplicationContext(), getIntent().getStringExtra("welcomeMessage"), 3000);
        SnackBarSuccess.show();
    }
}