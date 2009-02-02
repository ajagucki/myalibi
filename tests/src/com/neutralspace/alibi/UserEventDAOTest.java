package com.neutralspace.alibi;

import android.database.SQLException;
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
        UserEvent userEvent = UserEventTest.getFakeUserEvent();
        
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
	
	public void testDeleteUserEvent() {
        UserEvent userEvent = UserEventTest.getFakeUserEvent();
        
        userEventDAO.open();
        
        // Add to db
        long id = userEventDAO.createUserEvent(userEvent);
        assertEquals(false, id < 0);
        
        // Delete from db
        assertTrue(userEventDAO.deleteUserEvent(id));
        
        try {
			userEventDAO.fetchUserEvent(id);
			// Exception should be thrown
			assertTrue(false);
		}
        catch (SQLException e) {
		}
		
        userEventDAO.close();
	}
	
	public void testFetchCurrentId() {
		long id = 42;
		
		userEventDAO.open();
		
		assertEquals(-1, userEventDAO.fetchCurrentId());
		
		userEventDAO.updateCurrentId(id);
		assertEquals(id, userEventDAO.fetchCurrentId());
		
		userEventDAO.updateCurrentId(-1);
		userEventDAO.close();
	}
	
	public void testUpdateCurrentId() {
		long id = 9001;
		
		userEventDAO.open();
		
		assertTrue(userEventDAO.updateCurrentId(id));
		assertEquals(id, userEventDAO.fetchCurrentId());
		
		userEventDAO.updateCurrentId(-1);
		userEventDAO.close();
	}
	
	public void testDeleteAll() {
		userEventDAO.open();
		userEventDAO.createUserEvent(UserEventTest.getFakeUserEvent());
		userEventDAO.updateCurrentId(1);
		assertTrue(userEventDAO.deleteAll());
		userEventDAO.close();
	}

	@Override
	protected void tearDown() throws Exception {
		userEventDAO.open();
		userEventDAO.deleteAll();
		userEventDAO.close();
		super.tearDown();
	}
}
