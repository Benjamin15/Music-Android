package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.AddMusicButton;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.IdentificationButton;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ListMusicForUser;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;

public class Home extends AppCompatActivity {

    public static ListMusicForUser listMusicUser;

    private AddMusicButton addMusicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        addMusicButton = findViewById(R.id.floating_add_music);
        Intent intent = new Intent(Home.this, ServiceGetList.class);
        intent.putExtra("listMusic", (Parcelable) listMusicUser);
        startService(intent);
        listMusicUser = findViewById(R.id.list_music);
    }
}