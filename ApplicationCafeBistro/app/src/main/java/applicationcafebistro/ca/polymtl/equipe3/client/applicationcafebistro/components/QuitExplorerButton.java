package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.components;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Explorer;

public class QuitExplorerButton extends FloatingActionButton implements View.OnClickListener {
    private final Context context;

    public QuitExplorerButton(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public QuitExplorerButton(Context context, AttributeSet attrs) {
        super(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
        this.context = context;
        init();
    }

    public QuitExplorerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (Explorer.instance != null) {
            try {
                Explorer.instance.finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
