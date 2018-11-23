package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.snackbar.SnackBarSuccess;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;



    public class FragmentUsersList extends Fragment {

        private View view;
        public FragmentUsersList(){

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

        }
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.users_list_fragment,container,false);
            initRecyclerView();
//            initSnackBar();
            return view;
        }
    }