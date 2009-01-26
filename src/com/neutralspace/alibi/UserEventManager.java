package com.neutralspace.alibi;

import java.util.Calendar;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

/**
 * Manages Alibi's user events.
 * 
 * This manager acts as an interface for exchanging data with Android's 
 * Calendar application.
 */
public class UserEventManager {
    
    private Context context;
    private UserEvent currentEvent;
    private SettingsManager settingsManager;
    
    private final Uri CALENDAR_EVENTS_URI = Uri.parse("content://calendar/events");
    
    public UserEventManager(Context context) {
        super();
        this.context = context;
        this.settingsManager = ((Alibi) context).getSettingsManager();
    }
    
    /**
     * Starts a user event.
     * 
     * @param userEvent the event to start
     * @throws Exception thrown if the event could not be started
     */
    public void begin(UserEvent userEvent) throws Exception {
        if (currentEvent != null) {
            Log.w(Alibi.TAG, "Beginning an event before the current finished.");
        }
        currentEvent = userEvent;
        
        // TODO: Persist current event in the database so if the application
        // ends we can restore upon next load.
    }

    /**
     * Ends the current event and adds the event to the calendar.
     * 
     * If the current event does not have its end-time set, the current event 
     * gets its end-time set to the time that this method is called.
     * 
     * @throws Exception thrown if the calendar event could not be inserted
     */
    public void finish() throws Exception {
        if (currentEvent == null) {
            Log.w(Alibi.TAG, "Tried to finish before the current event began.");
            return;
        }
        
        // TODO: Do we need to coerce java.util.Calendar to use the phone's locale?
        if (currentEvent.getEndTime() <= 0) {
            currentEvent.setEndTime(Calendar.getInstance().getTimeInMillis());
        }
        
        ContentResolver cr = context.getContentResolver();
        ContentValues values = getCalendarContentValues(currentEvent);
        
        Uri rowUri = cr.insert(CALENDAR_EVENTS_URI, values);
        if (rowUri == null) {
            throw new Exception("Failed to insert calendar event.");
        }
        
        currentEvent = null;
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
        String eventTimezone = "PST"; // TODO: Get timezone of phone here
        
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
     * Cancels the current event.
     */
    public void cancel() {
        currentEvent = null;
        // TODO: Remove current event from local database so the event is not
        // restored upon next application start-up
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
