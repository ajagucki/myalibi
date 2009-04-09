/************************************************************************
Copyright 2009
Adam DiCarlo, Armando Diaz-Jagucki, Kelsey Cairns,
Nathan Ertner, Peter Welte, Sky Cunningham, and
neutralSpace Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
************************************************************************/

package com.neutralspace.alibi;

import android.app.Application;
import android.util.Log;

public class Alibi extends Application {

    public static final String TAG = "myAlibi";
    
    private SettingsManager settingsManager;
    private UserEventManager userEventManager;

    @Override
    public void onCreate() {
        Log.i(TAG, "Starting up.");
        super.onCreate();
        
        Log.i(TAG, "Instantiating managers.");
        settingsManager = new SettingsManager(this);
        userEventManager = new UserEventManager(this);
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

    public UserEventManager getUserEventManager() {
        return userEventManager;
    }

}
