package com.neutralspace.alibi;

import android.location.Location;

/**
 * An Alibi user event whose members correlate to calendar event fields.
 *
 * A user event in Alibi describes what the user did, where they did it, and
 * when. This object is used by UserEventManager to be stored in the phone's
 * calendar as a calendar event.
 * 
 * The mapping of an Alibi user-event's fields to the calendar event fields is
 * as follows:
 * 
 * Location (GPS coords) -> Where
 * Category   -> What
 * Start Time -> From
 * End Time   -> To
 * User Notes -> Description
 * 
 */
public class UserEvent {
    
    private Location location;
    private Category category;
    private long startTime; // in ms
    private long endTime;
    private String userNotes;
    
    public UserEvent(Location location, Category category, long startTime, long endTime) {
        super();
        this.location = location;
        this.category = category;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

	enum Category {
        
        WORK( "Work" ),
        PLAY( "Play" ),
        EAT(  "Eat"  ),
        OTHER("Other");
        
        private final String title;

        public String getTitle() {
            return title;
        }

        private Category(String title) {
            this.title = title;
        }
        
        public static Category getCategory(String title) {
        	if (title.equals(WORK.getTitle())) {
        		return WORK;
        	}
        	else if (title.equals(PLAY.getTitle())) {
        		return PLAY;
        	}
        	else if (title.equals(EAT.getTitle())) {
        		return EAT;
        	}
        	else if (title.equals(OTHER.getTitle())) {
        		return OTHER;
        	}
			return null;
        }
    }

}
