package com.neutralspace.alibi;

import android.content.Context;

/**
 * Manages Alibi's user events.
 * 
 * This manager acts as an interface for exchanging data with Android's 
 * Calendar application.
 */
public class UserEventManager {
    
    private Context context;
    private UserEvent currentEvent;
    
    public UserEventManager(Context context) {
        super();
        this.context = context;
    }
    
    /**
     * Starts a user event.
     * 
     * The user event is added to the calendar and it becomes the
     * 'current' event.
     * 
     * @param userEvent the event to add
     * @throws Exception thrown if the calendar insertion failed
     */
    public void begin(UserEvent userEvent) throws Exception {
        // TODO: implement
    }
    
    /**
     * Ends the current event.
     * 
     * The current event gets its end-time set to the time that this method
     * is called. Its corresponding calendar event is then updated with the
     * end-time.
     * 
     * @throws Exception thrown if the calendar event could not be updated
     */
    public void finish() throws Exception {
        // TODO: implement
    }
    
    /**
     * Cancels the current event.
     * 
     * The current event's associated calendar event is removed from the
     * calendar.
     * 
     * @throws Exception thrown if the calendar event could not be removed
     */
    public void cancel() throws Exception {
        // TODO: implement
    }
    
    /**
     * Returns the current user event.
     * 
     * @return the current user event, or null if there is no current event.
     */
    public UserEvent getCurrentEvent() {
        // TODO: implement
        return null;
    }
    
}
