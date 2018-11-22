package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.Explorer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;
import java.util.Objects;

/**
 * this class is used to clear or update the explorer.
 */
public class ExplorerReceiver extends BroadcastReceiver {

    private final Explorer explorer = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getAction() != null && intent.getAction().equals(Intent.ACTION_MEDIA_REMOVED))
                explorer.setEmpty();
            else if (Objects.requireNonNull(intent.getAction()).equals(Intent.ACTION_MEDIA_MOUNTED)) {
                if (intent.getData() != null)
                    explorer.updateDirectory(new File(intent.getData().toString()));
            }
        }
    }
}
