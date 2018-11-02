package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import org.json.JSONException;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service.ChangePasswordService;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Home;


public class ChangePasswordButton extends android.support.v7.widget.AppCompatButton implements View.OnClickListener {
    private Context context;
    private ChangePasswordService changePasswordService;

    public ChangePasswordButton(Context context){
        super(context);
        this.context = context;
        this.changePasswordService = new ChangePasswordService(context);
        init();
    }

    public ChangePasswordButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        this.changePasswordService= new ChangePasswordService(context);
        this.context = context;
        init();
    }

    public ChangePasswordButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.changePasswordService = new ChangePasswordService(context);
        this.context = context;
        init();
    }

    private void init() {
        setOnClickListener(this);
    }
    @Override
    public void onClick(View view){
        try{
            changePasswordService.changePassword();
        }catch (JSONException e){
            e.printStackTrace();
        }
        Intent intent = new Intent(this.context, Home.class);
        context.startActivity(intent);
    }

}
