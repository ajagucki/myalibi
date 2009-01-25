package com.neutralspace.alibi;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.neutralspace.alibi.widget.DelayPicker;

/**
 * The Setup activity is used to examine or change Alibi's settings.
 */
public class Setup extends Activity {
    
    public static final Uri CALENDARS_URI = Uri.parse("content://calendar/calendars");
    public static final String[] calsProjection = new String[] { "_id", "displayName" };
    
    private Spinner calendarS;
    private CheckBox remindCb;
    private DelayPicker reminderDp;
    private Button doneButton;
    
    private SettingsManager settingsManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		
		settingsManager = ((Alibi) getApplication()).getSettingsManager();
	
		// Initialize widgets
		calendarS = (Spinner) findViewById(R.id.calendar_spinner);
        Cursor cursor = getContentResolver().query(CALENDARS_URI, calsProjection, null, null, null);
		String[] from = new String[] { "displayName" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		calendarS.setAdapter(adapter);
		int calendarId = settingsManager.getCalendarId();
		setCalendarId(calendarId);
		
		reminderDp = (DelayPicker) findViewById(R.id.delay_picker);
        reminderDp.updateDelay(settingsManager.getReminderDelay());
        
		remindCb = (CheckBox) findViewById(R.id.reminder_checkbox);
		remindCb.setOnCheckedChangeListener(remindCbListener);
		remindCb.setChecked(settingsManager.isRemind());
		reminderDp.setEnabled(remindCb.isChecked());
		
		doneButton = (Button) findViewById(R.id.done_button);
		doneButton.setOnClickListener(doneButtonListener);
	}

    private void setCalendarId(int calendarId) {
        for (int pos = 0; pos < calendarS.getCount(); pos++) {
		    if (calendarS.getItemIdAtPosition(pos) == calendarId) {
		        calendarS.setSelection(pos);
		        break;
		    }
		}
    }
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SettingsManager.PREF_CALENDAR_ID, (int) calendarS.getSelectedItemId());
        outState.putBoolean(SettingsManager.PREF_REMIND, remindCb.isChecked());
        outState.putInt(SettingsManager.PREF_REMIND_DELAY, reminderDp.getDelayInMinutes());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setCalendarId(savedInstanceState.getInt(SettingsManager.PREF_CALENDAR_ID));
        reminderDp.updateDelay(savedInstanceState.getInt(SettingsManager.PREF_REMIND_DELAY));
        remindCb.setChecked(savedInstanceState.getBoolean(SettingsManager.PREF_REMIND));
        reminderDp.setEnabled(remindCb.isChecked());
    }

    private OnCheckedChangeListener remindCbListener = new OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (reminderDp == null) {
                reminderDp = (DelayPicker) findViewById(R.id.delay_picker);
            }
            reminderDp.setEnabled(isChecked);
        }
	    
	};
	
	private OnClickListener doneButtonListener = new OnClickListener() {

        public void onClick(View v) {
            settingsManager.setCalendarId((int) calendarS.getSelectedItemId());
            settingsManager.setRemind(remindCb.isChecked());
            settingsManager.setReminderDelay(reminderDp.getDelayInMinutes());
            settingsManager.save();

            finish();
        }
	    
	};

}
