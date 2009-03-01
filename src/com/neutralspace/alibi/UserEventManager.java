package com.neutralspace.alibi;

import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.location.Location;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

/**
 * Manages Alibi's user events.
 * 
 * This manager acts as an interface for exchanging data with Android's 
 * Calendar application.
 * 
 * If there is no current event running, getCurrentEvent() will be null.
 */
public class UserEventManager {
    
    private Context context;
    private UserEvent currentEvent;
    private long currentEventId;
    private SettingsManager settingsManager;
    private UserEventDAO userEventDAO;
    
    private final Uri CALENDAR_EVENTS_URI = Uri.parse("content://calendar/events");
    
    public UserEventManager(Context context) {
        super();
        this.context = context;
        this.settingsManager = ((Alibi) context).getSettingsManager();
        this.userEventDAO = new UserEventDAO(context);
        
        // Try to restore an existing current event
        userEventDAO.open();
        currentEventId = userEventDAO.fetchCurrentId();
        if (currentEventId >= 0) {
        	try {
				currentEvent = userEventDAO.fetchUserEvent(currentEventId);
			}
        	catch (SQLException e) {
				Log.e(Alibi.TAG, "Failed to restore current event: " + e.getMessage());
			}
        }
        userEventDAO.close();
    }

    /**
     * Starts a user event.
     * 
     * @param userEvent the event to start
     * @throws Exception thrown if the event could not be started
     */
    public void start(UserEvent userEvent) throws Exception {
        if (currentEvent != null) {
            Log.w(Alibi.TAG, "Beginning an event before the current finished.");
        }

        // Persist current event in the database (to restore application state later)
        userEventDAO.open();
        currentEventId = userEventDAO.createUserEvent(userEvent);
        if (currentEventId == -1) {
            userEventDAO.close();
            Log.e(Alibi.TAG, "Failed to create the user event.");
            throw new Exception("Failed to create the user event.");
        }
        currentEvent = userEvent;
        userEventDAO.updateCurrentId(currentEventId);
        userEventDAO.close();
        setAlarm();
        Log.i(Alibi.TAG, "Started a " + userEvent.getCategory().getTitle()
                + " event (ID: " + currentEventId + ").");
    }

    /**
     * Starts the count down for a Reminder notification for the current user
     * event.
     */
    private void setAlarm() {
        if (!settingsManager.isRemind()) {
            Log.d(Alibi.TAG, "Remind Me off; not setting alarm");
            return;
        }
        
        Intent intent = new Intent(this.context, ReminderAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(this.context, 0, intent, 0);

        long interval = settingsManager.getReminderDelayMillis();        
        long firstTime = SystemClock.elapsedRealtime() + interval;

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, interval, sender);
        Log.d(Alibi.TAG, "Alarm set");
    }

    /**
     * Stops the current waiting-to-wake-up Reminder alarm and notification.
     */
    private void cancelAlarm() {
        // Settings *should not* be able to change during an event, so if
        // "Remind Me" option is off now, it was off when event started, also 
        if (!settingsManager.isRemind()) {
            Log.d(Alibi.TAG, "Remind Me off; not canceling alarm");
            return;
        }

        Intent intent = new Intent(this.context, ReminderAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(this.context, 0, intent, 0);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
        ReminderAlarm.cancelNotification(this.context);
        Log.d(Alibi.TAG, "Alarm canceled");
    }

    /**
     * Ends the current event and adds the event to the calendar.
     * 
     * If the current event does not have its end-time set, the current event 
     * gets its end-time set to the time that this method is called.
     * 
     * @returns URI for the Calendar event which was created
     * @throws Exception thrown if the calendar event could not be inserted
     */
    public Uri stop() throws Exception {
        if (currentEvent == null) {
            Log.w(Alibi.TAG, "Tried to finish before the current event began.");
            throw new Exception("Tried to finish before the current event began.");
        }

        if (currentEvent.getEndTime() <= 0) {
            currentEvent.setEndTime(Calendar.getInstance().getTimeInMillis());
        }
        
        ContentResolver cr = context.getContentResolver();
        ContentValues values = getCalendarContentValues(currentEvent);
        
        Uri rowUri = cr.insert(CALENDAR_EVENTS_URI, values);
        if (rowUri == null) {
            throw new Exception("Failed to insert calendar event.");
        }

        Log.i(Alibi.TAG, "Stopped a " + currentEvent.getCategory().getTitle()
				+ " event (ID: " + currentEventId + ").");
        cancelAlarm();
        clearCurrentEventDb();
        return rowUri;
    }

    /**
     * Ends the current event and discards it.
     */
    public void cancel() {
        cancelAlarm();
        clearCurrentEventDb();
    }

    /**
     * Deletes the current event from Alibi (not from the phone's calendar).
     */
	private void clearCurrentEventDb() {
        long oldEventId = currentEventId;
        currentEvent = null;
        currentEventId = -1;

	    userEventDAO.open();
		if (userEventDAO.deleteUserEvent(oldEventId) == false) {
        	Log.e(Alibi.TAG, "Failed to delete the current user event.");
        }
        if (userEventDAO.updateCurrentId(-1) == false) {
        	Log.e(Alibi.TAG, "Failed to clear the current user event id.");
        }
        userEventDAO.close();
	}

	/**
	 * Updates the current event in Alibi's database (not the phone's
	 * calendar). Assumes the EventId has not changed.
	 * @param 
	 * @return
	 */
	private void updateCurrentEventDb() {
	    userEventDAO.open();
	    userEventDAO.updateUserEvent(currentEventId, currentEvent);
	    userEventDAO.close();
	}
	
    private ContentValues getCalendarContentValues(UserEvent userEvent) {
        String title = "My Alibi: " + userEvent.getCategory().getTitle();
        String eventLocation = Double.toString(userEvent.getLocation()
                .getLatitude())
                + "," + Double.toString(userEvent.getLocation().getLongitude());
        String description = userEvent.getUserNotes() != null ? userEvent
                .getUserNotes() : "";
        long dtstart = userEvent.getStartTime();
        long dtend = userEvent.getEndTime();
        
        // Use default TimeZone of the phone
        String eventTimezone = TimeZone.getDefault().getID();
        
        ContentValues values = new ContentValues();
        values.put("eventTimezone", eventTimezone);
        values.put("calendar_id", settingsManager.getCalendarId());
        values.put("title", title);
        values.put("allDay", 0);
        values.put("dtstart", dtstart);
        values.put("dtend", dtend);
        values.put("description", description);
        values.put("eventLocation", eventLocation);
        values.put("transparency", 0);
        values.put("visibility", 0);
        values.put("hasAlarm", 0);

        return values;
    }

    /**
     * Sets the Category of the current UserEvent.
     * @param category new category
     * @throws Exception Thrown if there's no current UserEvent
     */
    public void setCategory(UserEvent.Category category) throws Exception {
        if (currentEvent == null) {
            Log.e(Alibi.TAG, "setCategory: no current event");
            throw new Exception("setCategory: no current event");
        }
        assert currentEventId >= 0; // if we have event, should have valid ID
        currentEvent.setCategory(category);
        updateCurrentEventDb();
        ReminderAlarm.updateNotification(context);
    }
    
    /**
     * Sets the Location of the current UserEvent.
     * @param location   new location
     * @throws Exception Thrown if there's no current UserEvent
     */
    public void setLocation(Location location) throws Exception {
        if (currentEvent == null) {
            Log.e(Alibi.TAG, "setLocation: no current event");
            throw new Exception("setLocation: no current event");
        }
        assert currentEventId >= 0; // if we have event, should have valid ID
        currentEvent.setLocation(location);
        updateCurrentEventDb();
    }
    
    /**
     * Returns the current user event.
     * 
     * @return the current user event, or null if there is no current event.
     */
    public UserEvent getCurrentEvent() {
        return currentEvent;
    }
}
