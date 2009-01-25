package com.neutralspace.alibi;

import android.app.Application;
import android.util.Log;

public class Alibi extends Application {

    public static final String TAG = "Alibi";
    
    private SettingsManager settingsManager;

    @Override
    public void onCreate() {
        Log.i(TAG, "Starting up.");
        super.onCreate();
        
        Log.i(TAG, "Instantiating managers.");
        settingsManager = new SettingsManager(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public void setSettingsManager(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

}
