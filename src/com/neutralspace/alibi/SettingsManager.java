package com.neutralspace.alibi;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

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
