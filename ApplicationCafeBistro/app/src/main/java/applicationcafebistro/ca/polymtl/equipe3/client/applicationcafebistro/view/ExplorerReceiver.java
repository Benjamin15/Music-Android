package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

public class ExplorerReceiver extends BroadcastReceiver {

    public ExplorerReceiver(){
        super();
    }

    private Explorer explorer = null;

    public ExplorerReceiver(Explorer explorer){
        super();
        this.explorer = explorer;
    }

    @Override
    public void onReceive(Context context, Intent intent){
        if(intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED))
            explorer.setEmpty();
        else if(intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED))
            explorer.updateDirectory(new File(intent.getData().toString()));
    }
}