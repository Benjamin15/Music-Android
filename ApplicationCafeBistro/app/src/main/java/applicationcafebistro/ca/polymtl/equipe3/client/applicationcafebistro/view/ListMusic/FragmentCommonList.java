package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.ListMusicAdapter;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.MusicListener;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.list.RecyclerMusicTouchHelper;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.snackbar.SnackBarSuccess;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;


public class FragmentCommonList extends Fragment {

    private View view;
    public static MusicListener listenerMusic;
    public FragmentCommonList(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void initSnackBar(){
        Intent intent = new Intent(getActivity(), ServiceGetList.class);
        SnackBarSuccess.make(view, getContext(), intent.getStringExtra(getString(R.string.welcome_message_key)), 3000);
        SnackBarSuccess.show();
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        ListMusicAdapter mAdapter = new ListMusicAdapter();
        listenerMusic = new MusicListener(mAdapter,getContext().getString(R.string.common_list));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerMusicTouchHelper(0, ItemTouchHelper.LEFT, listenerMusic);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adjustInterface();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.common_list_fragment,container,false);
        initRecyclerView();
        initSnackBar();
        return view;
    }

    private void adjustInterface(){
        if(DeviceInformation.isAdmin){
            CoordinatorLayout layout = getView().findViewById(R.id.common_list_fragment);
            layout.removeView(getView().findViewById(R.id.floating_add_music));
        }
    }
}
