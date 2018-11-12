package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

/**
 * this class is used to clear or update the explorer.
 */
public class ExplorerReceiver extends BroadcastReceiver {

    private Explorer explorer = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED))
                explorer.setEmpty();
            else if (intent.getAction().equals(Intent.ACTION_MEDIA_MOUNTED)) {
                if (intent.getData() != null)
                    explorer.updateDirectory(new File(intent.getData().toString()));
            }
        }
    }
}
