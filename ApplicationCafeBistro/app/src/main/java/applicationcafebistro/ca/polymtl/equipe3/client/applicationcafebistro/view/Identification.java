package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.DeleteSongButton;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components.IdentificationButton;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.IdentificationService;

public class Identification extends AppCompatActivity {
    private IdentificationButton identificationButton;
    private IdentificationService identificationService;
    private DeleteSongButton deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        identificationService = new IdentificationService(this);
        identificationButton  = findViewById(R.id.identificationButton);
        deleteButton= findViewById(R.id.deleteSongButton);
        final EditText text = findViewById(R.id.identifier);


        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                identificationButton.setLoginText(text.getText());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
}
