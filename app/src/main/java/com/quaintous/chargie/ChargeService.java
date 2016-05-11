package com.quaintous.chargie;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Service to monitor charging state and try to optimize it
 * according to given config.
 *
 * @see PowerReceiver
 * @see MainActivity
 */
public class ChargeService extends Service {
    public ChargeService() {
        super();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Kill for now!
        this.stopSelf();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
