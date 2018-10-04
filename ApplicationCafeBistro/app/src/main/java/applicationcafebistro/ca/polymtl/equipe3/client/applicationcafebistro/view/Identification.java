package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.IdentificationButton;

public class Identification extends AppCompatActivity {
    private IdentificationButton identificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        identificationButton  = findViewById(R.id.identificationButton);
        final EditText text = (EditText) findViewById(R.id.identifier);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                identificationButton.setLoginText(text.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });

    }
}
