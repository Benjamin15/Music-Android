package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;

public class FragmentPersonalList extends Fragment {
    private View view;

    public FragmentPersonalList(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable
                                     Bundle savedInstanceState){
        view= inflater.inflate(R.layout.personal_list_fragment,container,false);
        return view;
    }
}
