package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Alibi extends Activity {
    
    public static final String PREFS_NAME = "AlibiSettings";
    
    // Settings
    private int calendarId;
    private boolean remind;
    private int reminderDelay;  // in minutes
    // Keys for settings bundle
    public static final String PREF_CALENDAR_ID = "calendarId";
    public static final String PREF_REMIND = "remind";
    public static final String PREF_REMIND_DELAY = "reminderDelay";
    // Custom Activity result codes
    public static final int RESULT_SETTINGS = RESULT_FIRST_USER + 1;
    public static final int YOUR_CUSTOM_RESULT_CODE = RESULT_FIRST_USER + 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Restore settings
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        // TODO: Make the default calendar id the currently selected calendar
        setCalendarId(settings.getInt(PREF_CALENDAR_ID, 1));
        setRemind(settings.getBoolean(PREF_REMIND, true));
        setReminderDelay(settings.getInt(PREF_REMIND_DELAY, 30));
        
        // Set listener for 'Settings' button
        Button settingsButton = (Button) findViewById(R.id.settings);
        settingsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Bundle settingsBundle = new Bundle();
                settingsBundle.putInt(PREF_CALENDAR_ID, getCalendarId());
                settingsBundle.putBoolean(PREF_REMIND, isRemind());
                settingsBundle.putInt(PREF_REMIND_DELAY, getReminderDelay());
                
                // Go to Setup screen
                Intent intent = new Intent(view.getContext(), Setup.class);
                intent.putExtras(settingsBundle);
                startActivityForResult(intent, RESULT_SETTINGS);
            }
            
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        Bundle extras = data != null ? data.getExtras() : null;

        switch(requestCode) {
            case RESULT_SETTINGS:
                saveSettings(extras);
                break;
            case YOUR_CUSTOM_RESULT_CODE:
                // Do something
                break;
        }
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
    
    private void saveSettings(Bundle settingsBundle) {
        if (settingsBundle == null) {
            return;
        }
        
        // Save in memory
        setCalendarId(settingsBundle.getInt(PREF_CALENDAR_ID));
        setRemind(settingsBundle.getBoolean(PREF_REMIND));
        setReminderDelay(settingsBundle.getInt(PREF_REMIND_DELAY));
        
        // Save in external storage
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(PREF_CALENDAR_ID, getCalendarId());
        editor.putBoolean(PREF_REMIND, isRemind());
        editor.putInt(PREF_REMIND_DELAY, getReminderDelay());
        editor.commit();
    }
}