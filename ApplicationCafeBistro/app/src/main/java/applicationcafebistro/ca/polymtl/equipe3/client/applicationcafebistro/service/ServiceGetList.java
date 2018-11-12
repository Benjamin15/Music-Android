package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;

public class ServiceGetList extends Service {
    private static final long DEFAULT_SYNC_INTERVAL = 5000;
    private static final String TAG = ServiceGetList.class.getSimpleName();
    private Handler mHandler;

    private final Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "run");

            syncData();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "start command");
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return null;
    }

    private synchronized void syncData() {
        Log.i(TAG, "syncData");
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.list_music_test) + Integer.toString(DeviceInformation.idUser),
                "GET",
                ListMusic.view,
                ListMusic.listenerMusic
        );
        communication.send(null);
    }
}