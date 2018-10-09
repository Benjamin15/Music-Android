package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.IdentificationService;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Home;

/**
 * Created by moguef on 2018-09-29.
 */

public class IdentificationButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    private Context context;
    private Bundle fragmentBundle;
    private String loginText;
    /**
     * The section number for the fragment owning this button.
     */
    public static final String EXTRA_MESSAGE = "com.example.moguef.myapplication.MESSAGE";
    private static final String URL_KEY = "url";
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
