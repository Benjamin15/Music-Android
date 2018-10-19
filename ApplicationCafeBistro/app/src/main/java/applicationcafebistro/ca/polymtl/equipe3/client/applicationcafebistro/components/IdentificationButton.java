package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import org.json.JSONException;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.IdentificationService;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Home;


public class IdentificationButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    private Context context;
    private String loginText;
    /**
     * The section number for the fragment owning this button.
     */
    private IdentificationService identificationService;

    public IdentificationButton(Context context){
        super(context);
        this.context = context;
        this.identificationService = new IdentificationService(context);
        init();
    }

    public IdentificationButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        this.identificationService = new IdentificationService(context);
        this.context = context;
        init();
    }

    public IdentificationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.identificationService = new IdentificationService(context);
        this.context = context;
        init();
    }

    private void init(){
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        try {
            identificationService.identification(loginText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this.context, Home.class);
        context.startActivity(intent);
    }
    public void setLoginText(Editable editText){
        this.loginText = editText.toString();
    }
}
