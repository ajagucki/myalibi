package com.neutralspace.alibi;

import java.text.DateFormat;
import java.util.Calendar;

import android.location.Location;
import android.util.Log;

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
    
    public UserEvent(Location location, Category category, long startTime) {
        super();
        this.location = location;
        this.category = category;
        this.startTime = startTime;
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

    private Boolean sameDay(final Calendar left, final Calendar right) {
        return left.get(Calendar.DAY_OF_YEAR) == right.get(Calendar.DAY_OF_YEAR) &&
               left.get(Calendar.YEAR) == right.get(Calendar.YEAR);
    }

    /**
     * Mutates given calendar to be right on the nearest minute.
     * @param cal: The calendar to round
     */
    private void roundToMinutes(Calendar cal) {
        int ms = cal.get(Calendar.MILLISECOND);
        if (ms >= 500) {
            cal.add(Calendar.MILLISECOND, 1000 - ms);
            Log.d("roundToMinutes", "Added " + (1000 - ms) + "ms");
        } else
            cal.set(Calendar.MILLISECOND, 0);

        int second = cal.get(Calendar.SECOND);
        if (second >= 30) {
            cal.add(Calendar.SECOND, 60 - second);
            Log.d("roundToMinutes", "Added " + (60 - second) + "seconds");
        } else
            cal.set(Calendar.SECOND, 0);
    }
    
    /**
     * Formats the time given (in milliseconds since epoch) in a "nice" way,
     * based on the current time. Example: "2:00 PM" if 'time' is during the
     * same day as today; if not, more contextual information is given.
     * @param time
     * @return String representing nicely formatted time
     */
    private String getNiceTime(long time) {
        Calendar now = Calendar.getInstance();
        Calendar someTime = Calendar.getInstance();
        someTime.setTimeInMillis(time);
        assert now.after(someTime) || now.equals(someTime);
        
        roundToMinutes(now);
        roundToMinutes(someTime);
        DateFormat df;
        if (sameDay(someTime, now)) {
            df = DateFormat.getTimeInstance();
            return df.format(someTime.getTime());
        }
        df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
        return df.format(someTime.getTime());
    }
    
    public String getNiceStartTime() {
        return getNiceTime(startTime);
    }

    public String getNiceEndTime() {
        return getNiceTime(endTime);
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
