package com.neutralspace.alibi;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

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
public class UserEvent implements Parcelable {
    
    private Location location;
    private Category category;
    private long startTime; // in ms
    private long endTime;
    private String userNotes;

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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(location, flags);
        out.writeValue(category);
        out.writeLong(startTime);
        out.writeLong(endTime);
        out.writeString(userNotes);
    }
    
    public static final Parcelable.Creator<UserEvent> CREATOR 
            = new Parcelable.Creator<UserEvent>() {
        public UserEvent createFromParcel(Parcel in) {
            return new UserEvent(in);
        }

        public UserEvent[] newArray(int size) {
            return new UserEvent[size];
        }
    };

    private UserEvent(Parcel in) {
        super();
        setLocation((Location) in.readParcelable(Location.class.getClassLoader()));
        setCategory((Category) in.readValue(Category.class.getClassLoader()));
        setStartTime(in.readLong());
        setEndTime(in.readLong());
        setUserNotes(in.readString());
    }
    
    public UserEvent() {
        super();
    }

    enum Category {
        WORK,
        PLAY,
        EAT,
        OTHER,
    }

}
