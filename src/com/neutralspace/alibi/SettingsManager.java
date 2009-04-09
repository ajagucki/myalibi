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

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

/**
 * Keeps track of Alibi's application settings.
 * 
 * The settings persist on external storage through the SharedPreferences
 * Android API.
 *
 */
public class SettingsManager {
    
    public static final String PREFS_NAME = "AlibiSettings";
    
    // Settings
    private int calendarId;
    private boolean remind;
    private int reminderDelay;  // in minutes
    
    // Keys for settings bundle
    public static final String PREF_CALENDAR_ID = "calendarId";
    public static final String PREF_REMIND = "remind";
    public static final String PREF_REMIND_DELAY = "reminderDelay";
    
    private Context context;
    
    public SettingsManager(Context context) {
        super();
        this.context = context;
        
        // Restore settings
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        setCalendarId(settings.getInt(PREF_CALENDAR_ID, getSelectedCalendarId()));
        setRemind(settings.getBoolean(PREF_REMIND, true));
        setReminderDelay(settings.getInt(PREF_REMIND_DELAY, 60));
    }

    /**
     * Returns the ID of the Calendar that is currently selected for system-wide
     * use on the phone.
     * 
     * Do not confuse this selected Calendar ID with that of Alibi's chosen
     * Calendar ID -- the ID returned from this method is used to choose a sane
     * default value for Alibi's Calendar ID setting.
     * 
     * @return the ID of the Calendar that is currently selected on the phone
     */
    private int getSelectedCalendarId() {
        String[] calsProjection = new String[] { "_id", "selected" };
        Cursor cursor = context.getContentResolver().query(Setup.CALENDARS_URI, 
                calsProjection, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndex("selected")) > 0) {
                    return (int) cursor.getLong(cursor.getColumnIndex("_id"));
                }
            } while (cursor.moveToNext());
        }
        Log.w(Alibi.TAG, "Could not find the system's currently selected Calendar ID, using default ID: 1");
        return 1;
    }

    public int getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(int calendarId) {
        this.calendarId = calendarId;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public int getReminderDelay() {
        return reminderDelay;
    }

    /**
     * @return Reminder interval in milliseconds
     */
    public long getReminderDelayMillis() {
        return (long) reminderDelay * 60 * 1000;
    }
    
    public void setReminderDelay(int reminderDelay) {
        this.reminderDelay = reminderDelay;
    }
    
    /**
     * Saves the settings to external storage.
     */
    public void save() {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREF_CALENDAR_ID, getCalendarId());
        editor.putBoolean(PREF_REMIND, isRemind());
        editor.putInt(PREF_REMIND_DELAY, getReminderDelay());
        editor.commit();
        
        Log.i(Alibi.TAG, "Settings updated.");
    }

}
