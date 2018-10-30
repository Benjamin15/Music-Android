package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ListMusicForUser;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.CommunicationRest;

public class Home extends AppCompatActivity {

    private ListMusicForUser listMusicUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        //IdentificationService identificationService =
        //      new IdentificationService(getApplicationContext());

        listMusicUser = findViewById(R.id.list_music);
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.list_music_test) + "1",
                "GET",
                this.getApplicationContext(),
                listMusicUser);
        try {
            communication.send((JSONObject) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}