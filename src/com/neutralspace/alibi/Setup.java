package com.neutralspace.alibi;

import android.app.Activity;
import android.content.Intent;
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

public class Setup extends Activity {
    
    public static final Uri CALENDARS_URI = Uri.parse("content://calendar/calendars");
    public static final String[] projection = new String[] { "_id", "displayName" };
    
    private Spinner calendarS;
    private CheckBox remindCb;
    private DelayPicker reminderDp;
    private Button doneButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup);
		
		Bundle extras = savedInstanceState != null ? savedInstanceState
                : getIntent().getExtras();
	
		// Initialize widgets
		calendarS = (Spinner) findViewById(R.id.calendar_spinner);
        Cursor c = getContentResolver().query(CALENDARS_URI, projection, null, null, null);
		String[] from = new String[] { "displayName" };
		int[] to = new int[] { android.R.id.text1 };
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		calendarS.setAdapter(adapter);
		int calendarId = extras.getInt(Alibi.PREF_CALENDAR_ID);
		for (int pos = 0; pos < calendarS.getCount(); pos++) {
		    if (calendarS.getItemIdAtPosition(pos) == calendarId) {
		        calendarS.setSelection(pos);
		        break;
		    }
		}
		
		reminderDp = (DelayPicker) findViewById(R.id.delay_picker);
        int reminderDelay = extras.getInt(Alibi.PREF_REMIND_DELAY);
        int days = reminderDelay / 1440;
        int hours = (reminderDelay % 1440) / 60;
        int minutes = (reminderDelay % 1440) % 60;
        reminderDp.updateDelay(days, hours, minutes);
        
		remindCb = (CheckBox) findViewById(R.id.reminder_checkbox);
		remindCb.setOnCheckedChangeListener(remindCbListener);
		remindCb.setChecked(extras.getBoolean(Alibi.PREF_REMIND));
		reminderDp.setEnabled(remindCb.isChecked());
		
		doneButton = (Button) findViewById(R.id.done_button);
		doneButton.setOnClickListener(doneButtonListener);
	}
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Alibi.PREF_CALENDAR_ID, (int) calendarS.getSelectedItemId());
        outState.putBoolean(Alibi.PREF_REMIND, remindCb.isChecked());
        outState.putInt(Alibi.PREF_REMIND_DELAY, getReminderDelay());
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
            Bundle bundle = new Bundle();
            bundle.putInt(Alibi.PREF_CALENDAR_ID, (int) calendarS.getSelectedItemId());
            bundle.putBoolean(Alibi.PREF_REMIND, remindCb.isChecked());
            bundle.putInt(Alibi.PREF_REMIND_DELAY, getReminderDelay());
            
            Intent intent = new Intent();
            intent.putExtras(bundle);
            setResult(Alibi.RESULT_SETTINGS, intent);
            finish();
        }
	    
	};
	
	private int getReminderDelay() {
	    int reminderDelay = 0;
	    reminderDelay += (reminderDp.getDays() * 1440);
	    reminderDelay += (reminderDp.getHours() * 60);
	    reminderDelay +=  reminderDp.getMinutes();
	    return reminderDelay;
	}

}
