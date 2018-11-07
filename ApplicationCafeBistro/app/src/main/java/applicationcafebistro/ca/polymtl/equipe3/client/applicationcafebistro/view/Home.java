package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.ListMusicForUser;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ServiceGetList;

public class Home extends AppCompatActivity {

    public static ListMusicForUser listMusicUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Intent intent = new Intent(Home.this, ServiceGetList.class);
        intent.putExtra("listMusic", (Parcelable) listMusicUser);
        startService(intent);

        listMusicUser = findViewById(R.id.list_music);
    }
}