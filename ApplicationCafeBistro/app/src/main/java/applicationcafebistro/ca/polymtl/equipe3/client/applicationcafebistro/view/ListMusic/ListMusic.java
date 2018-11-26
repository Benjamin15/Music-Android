package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.MusicListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.Admin.FragmentBlackList;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.Admin.FragmentStatistics;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.Admin.FragmentUsersList;

public class ListMusic extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static View view;
    private ActionBarDrawerToggle toggle;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private Intent intent;
    private DrawerLayout drawerLayout;
    private final List<Integer> icons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_music);
        initNavigationMenu();
        initViewPager();
        view = getWindow().getDecorView().getRootView();
        adjustInterface();
        intent = new Intent(ListMusic.this, ServiceGetList.class);
        startService(intent);
    }

    private void adjustInterface(){
        if(!DeviceInformation.isAdmin){
            DrawerLayout drawer = findViewById(R.id.drawer);
            drawer.removeView(findViewById(R.id.navigation));
            drawer.removeView(findViewById(R.id.appBarLayout));
        }
    }

    private void initViewPager(){
        tabLayout = findViewById(R.id.tabLayout_id);
        viewPager = findViewById(R.id.viewpager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new FragmentCommonList(),getResources().getString(R.string.common_list));
        if(!DeviceInformation.isAdmin){
            viewPagerAdapter.addFragment(new FragmentPersonalList(),getResources().getString(R.string.personal_list));
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_common);
        icons.add(R.drawable.ic_common);
        if(!DeviceInformation.isAdmin){
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_personal);
            icons.add(R.drawable.ic_personal);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initNavigationMenu(){
        NavigationView adminMenu = findViewById(R.id.navigation);
        adminMenu.setNavigationItemSelectedListener(this);
        adminMenu.setItemIconTintList(null);
        drawerLayout = findViewById(((R.id.drawer)));
        toggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.drawer_state_open,
                R.string.drawer_state_close);
        if(DeviceInformation.isAdmin) {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black_color));
        }else {
            toggle.setDrawerIndicatorEnabled(false);
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorAccent));
        }
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onDestroy() {
        DeviceInformation.isAdmin = false;
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.user_list:
                if(!viewPagerAdapter.checkIfContains(getResources().getString(R.string.users_list))) {
                    viewPagerAdapter.addFragment(new FragmentUsersList(), getResources().getString(R.string.users_list));
                    icons.add(R.drawable.ic_view_list_tab_black_24dp);
                    viewPagerAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(icons.size()-1);
                    fillTabIcons();
                }
                break;
            case R.id.banned_users_list:
                if(!viewPagerAdapter.checkIfContains(getResources().getString(R.string.black_list))) {
                    viewPagerAdapter.addFragment(new FragmentBlackList(), getResources().getString(R.string.black_list));
                    icons.add(R.drawable.ic_block_black_tab_24dp);
                    viewPagerAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(icons.size()-1);
                    fillTabIcons();
                }
                break;
            case R.id.statistics:
                if(!viewPagerAdapter.checkIfContains(getResources().getString(R.string.statistics))) {
                    viewPagerAdapter.addFragment(new FragmentStatistics(), getResources().getString(R.string.statistics));
                    icons.add(R.drawable.ic_statistics_fragment);
                    viewPagerAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(icons.size()-1);
                    fillTabIcons();
                }
                break;
        }
        return true;
    }

    private void fillTabIcons(){
        for(int i=0;i<icons.size();i++){
            tabLayout.getTabAt(i).setIcon(icons.get(i));
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}