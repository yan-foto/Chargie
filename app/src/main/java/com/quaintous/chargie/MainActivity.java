package com.quaintous.chargie;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    // Logging tag
    private final static String TAG = "ChargieMain";

    /**
     * Key for our {@link android.content.SharedPreferences}
     **/
    protected final static String SHARED_PREF_KEY = "com.quaintous.chargie.preferences";
    /**
     * List of resource IDs
     */
    private final static String[] RES_IDS = {"wifi", "bt"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.restorePreferences();
    }

    /**
     * Callback when any of the checkboxes are toggled.
     * It persists data in a {@link SharedPreferences}.
     *
     * @param view source {@link Switch}
     * @see #restorePreferences()
     * @see #SHARED_PREF_KEY
     */
    public void switchToggled(View view) {
        Switch source = (Switch) view;
        String sourceId = getResources().getResourceEntryName(source.getId());
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(sourceId, source.isChecked());
        editor.apply();
    }

    /**
     * Restores the state of preferences from {@link SharedPreferences}.
     *
     * @see #switchToggled(View)
     */
    private void restorePreferences() {
        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        for (String id : RES_IDS) {
            boolean value = preferences.getBoolean(id, false);
            int viewId = getResources().getIdentifier(id, "id", getPackageName());
            Switch view = (Switch) findViewById(viewId);
            view.setChecked(value);
        }
    }
}
