package com.quaintous.chargie;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;

/**
 * Service to monitor charging state and try to optimize it
 * according to given config.
 *
 * @see PowerReceiver
 * @see MainActivity
 */
public class ChargeService extends Service {
    // Logging tag
    private final static String TAG = "CHARGE_SERVICE";

    // Polling interval for power level worker (ms)
    private final static int POLL_INTERVAL = 1000;

    /**
     * Worker to monitor power level
     */
    private PowerLevelWorker worker;

    private class PowerLevelWorker extends Thread {
        private boolean shouldRun;

        public PowerLevelWorker() {
            this.shouldRun = true;
        }

        @Override
        public void run() {
            while (shouldRun) {
                try {
                    Log.d(TAG, Float.toString(getPowerLevel()));
                    this.sleep(POLL_INTERVAL);
                } catch (InterruptedException e) {
                    // Noop
                    Log.d(TAG, "Power worker interrupted!");
                }
            }
        }

        public synchronized void kill() {
            this.shouldRun = false;
            this.notify();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Start thread to monitor power level
        this.worker = new PowerLevelWorker();
        worker.start();

        this.manageComponents();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        // Kill worker thread
        try {
            this.worker.kill();
            this.worker.interrupt();
            this.worker.join();
        } catch (InterruptedException e) {
            Log.e(TAG, "Error while killing power level worker!", e);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @return current battery level or -1 in case of error
     */
    public float getPowerLevel() {
        // Register for sticky battery broadcast
        IntentFilter batteryIFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, batteryIFilter);
        if (batteryStatus != null) {
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            return level / (float) scale;
        } else {
            return -1;
        }
    }

    private void manageComponents() {
        SharedPreferences preferences = getSharedPreferences(MainActivity.SHARED_PREF_KEY, MODE_PRIVATE);

        // Disable WiFi
        // WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // wifiManager.setWifiEnabled(false);

        // Disable Bluetooth
        // BluetoothManager bluetoothManager = (BluetoothManager) getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        // BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // bluetoothAdapter.disable();
    }
}
