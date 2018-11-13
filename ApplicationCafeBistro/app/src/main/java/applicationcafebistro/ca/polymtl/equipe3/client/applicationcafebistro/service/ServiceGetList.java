package applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.R;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.communication.CommunicationRest;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.utils.DeviceInformation;
import applicationcafebistro.ca.polymtl.equipe3.client.applicationcafebistro.view.ListMusic;


/**
 * this class is a service which call the get list endpoint in the rest api
 */
public class ServiceGetList extends Service {
    private static final long DEFAULT_SYNC_INTERVAL = 5000;
    private static final String TAG = ServiceGetList.class.getSimpleName();
    private Handler mHandler;

    private final Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "run");

            syncData();
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler = new Handler();
        mHandler.post(runnableService);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private synchronized void syncData() {
        final String GET = "GET";
        CommunicationRest communication = new CommunicationRest(
                getResources().getString(R.string.list_music) + Integer.toString(DeviceInformation.idUser),
                GET,
                ListMusic.view,
                ListMusic.listenerMusic
        );
        communication.send(null);
    }
}