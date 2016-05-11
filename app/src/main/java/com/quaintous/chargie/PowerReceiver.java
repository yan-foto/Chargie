package com.quaintous.chargie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receives intents whenever the phone is plugged in or out.
 * Depending on intent, it either start the {@link ChargeService} or
 * ends it.
 *
 * @see Intent#ACTION_POWER_CONNECTED
 * @see Intent#ACTION_POWER_DISCONNECTED
 */
public class PowerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Use this intent to start or stop the ChargeService
        Intent serviceIntent = new Intent(context, ChargeService.class);

        switch (intent.getAction()) {
            case Intent.ACTION_POWER_CONNECTED:
                context.startService(serviceIntent);
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                context.stopService(serviceIntent);
                break;
        }
    }
}
