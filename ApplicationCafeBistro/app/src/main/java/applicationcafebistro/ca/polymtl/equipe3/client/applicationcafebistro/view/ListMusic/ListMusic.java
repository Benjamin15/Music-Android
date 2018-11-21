package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.MusicListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;

public class ListMusic extends AppCompatActivity {

    public static MusicListener listenerMusic;
    public static View view;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_music);
        initNavigationMenu();
        initViewPager();
        /*RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ListMusicAdapter mAdapter = new ListMusicAdapter();
        listenerMusic = new MusicListener(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerMusicTouchHelper(0, ItemTouchHelper.LEFT, listenerMusic);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        */
        view = getWindow().getDecorView().getRootView();
        intent = new Intent(ListMusic.this, ServiceGetList.class);
        startService(intent);
    }
    private void initViewPager(){
        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentCommonList(),getResources().getString(R.string.common_list));
        viewPagerAdapter.addFragment(new FragmentPersonalList(),getResources().getString(R.string.personal_list));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_common);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_personal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void initNavigationMenu(){
        NavigationView adminMenu = findViewById(R.id.navigation);
        adminMenu.setItemIconTintList(null);
        DrawerLayout drawerLayout = findViewById(((R.id.drawer)));
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_state_open,
                R.string.drawer_state_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black_color));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        stopService(intent);
        super.onLowMemory();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}