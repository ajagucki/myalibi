/************************************************************************
Copyright 2009
Adam DiCarlo, Armando Diaz-Jagucki, Kelsey Cairns,
Nathan Ertner, Peter Welte, Sky Cunningham, and
neutralSpace Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
************************************************************************/

package com.neutralspace.alibi;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;
import android.location.Location;

public class UserEventTest extends TestCase {
    
    public void testUserEventCreation() {
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
        
        assertEquals(FAKE_LATITUDE, userEvent.getLocation().getLatitude());
        assertEquals(FAKE_LONGITUDE, userEvent.getLocation().getLongitude());
        assertEquals(FAKE_CATEGORY, userEvent.getCategory());
        assertEquals(FAKE_START_TIME, userEvent.getStartTime());
        assertEquals(FAKE_END_TIME, userEvent.getEndTime());
        assertEquals(FAKE_USER_NOTES, userEvent.getUserNotes());
        
    }
    
    public static UserEvent getFakeUserEvent() {
		final double FAKE_LONGITUDE = -121.45356;
        final double FAKE_LATITUDE  = 46.51119;
        final UserEvent.Category FAKE_CATEGORY = UserEvent.Category.PLAY;
        Date time = Calendar.getInstance().getTime();
        final long FAKE_START_TIME = time.getTime();
        time.setHours(time.getHours() + 1);
        final long FAKE_END_TIME = time.getTime();
        final String FAKE_USER_NOTES = "THIS IS A FAKE USER EVENT FOR TESTING.";
        
        // Create fake event
        Location location = new Location("gps");
        location.setLongitude(FAKE_LONGITUDE);
        location.setLatitude(FAKE_LATITUDE);
        UserEvent userEvent = new UserEvent(location, FAKE_CATEGORY, FAKE_START_TIME);
        userEvent.setEndTime(FAKE_END_TIME);
        userEvent.setUserNotes(FAKE_USER_NOTES);
		return userEvent;
    }

}
