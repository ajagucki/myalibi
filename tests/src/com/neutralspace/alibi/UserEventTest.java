package com.neutralspace.alibi;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import android.location.Location;

public class UserEventTest extends TestCase {
    
    public void testUserEventParcelability() {
        final double FAKE_LONGITUDE = -121.45356;
        final double FAKE_LATITUDE  = 46.51119;
        final UserEvent.Category FAKE_CATEGORY = UserEvent.Category.PLAY;
        Date time = Calendar.getInstance().getTime();
        final long FAKE_START_TIME = time.getTime();
        time.setHours(time.getHours() + 1);
        final long FAKE_END_TIME = time.getTime();
        final String FAKE_USER_NOTES = "That's what she said.";
        
        // Create fake event
        Location location = new Location("gps");
        location.setLongitude(FAKE_LONGITUDE);
        location.setLatitude(FAKE_LATITUDE);
        UserEvent userEvent = new UserEvent(location, FAKE_CATEGORY, FAKE_START_TIME);
        userEvent.setEndTime(FAKE_END_TIME);
        userEvent.setUserNotes(FAKE_USER_NOTES);
        
        // TODO: finish
        
    }

}
