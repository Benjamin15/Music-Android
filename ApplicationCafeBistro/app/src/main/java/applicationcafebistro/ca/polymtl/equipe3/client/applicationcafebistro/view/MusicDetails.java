package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;


import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;

public class MusicDetails extends ListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] values = new String[] { "Titre: ", "Artiste: ", "Durée: ",
                "Proposé par: ", "Propriétaire: ", "Numéro: "};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.music_details, values);
        setListAdapter(adapter);

    }

}


