package client.equipe3.polymtl.ca.applicationcafebistro.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import client.equipe3.polymtl.ca.applicationcafebistro.R;
import client.equipe3.polymtl.ca.applicationcafebistro.service.IdentificationService;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        IdentificationService identificationService = new IdentificationService(getApplicationContext());
    }
}
