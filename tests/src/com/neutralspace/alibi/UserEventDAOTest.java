package com.neutralspace.alibi;

import java.util.Calendar;
import java.util.Date;

import android.database.SQLException;
import android.location.Location;
import android.test.ApplicationTestCase;

public class UserEventDAOTest extends ApplicationTestCase<Alibi> {
	
	private UserEventDAO userEventDAO;
	
    public UserEventDAOTest() {
		super(Alibi.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		createApplication();
		userEventDAO = new UserEventDAO(getContext());
	}
	
	public void testPreconditions() {
		assertNotNull(userEventDAO);
	}
	
	public void testOpen() {
		try {
			userEventDAO.open();
		}
		catch (SQLException e) {
			assertTrue(false);
		}
		userEventDAO.close();
	}
	
	public void testCreateAndFetchUserEvent() {
        UserEvent userEvent = getFakeUserEvent();
        
        userEventDAO.open();
        
        // Add to db
        long id = userEventDAO.createUserEvent(userEvent);
        assertEquals(false, id < 0);
        
        // Fetch from db and compare
        UserEvent fetchedEvent;
        try {
			fetchedEvent = userEventDAO.fetchUserEvent(id);
		}
        catch (SQLException e) {
			assertTrue(false);
			return;
		}
        
        assertEquals(userEvent.getLocation().getLatitude(), fetchedEvent.getLocation().getLatitude());
        assertEquals(userEvent.getLocation().getLongitude(), fetchedEvent.getLocation().getLongitude());
        assertEquals(userEvent.getCategory(), fetchedEvent.getCategory());
        assertEquals(userEvent.getStartTime(), fetchedEvent.getStartTime());
        assertEquals(userEvent.getEndTime(), fetchedEvent.getEndTime());
        assertEquals(userEvent.getUserNotes(), fetchedEvent.getUserNotes());
		
		userEventDAO.close();
	}

	private UserEvent getFakeUserEvent() {
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
		return userEvent;
	}
	
	public void testDeleteUserEvent() {
		
	}

}
