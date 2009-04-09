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
	
	public void testUpdateUserEvent() {
	    userEventDAO.open();
	    
	    // Add an event
	    UserEvent userEvent = UserEventTest.getFakeUserEvent();
	    long rowId = userEventDAO.createUserEvent(userEvent);
	    
	    // Update with changed fields
	    UserEvent.Category newCategory = UserEvent.Category.OTHER;
	    long newEndTime = 1337;
	    long newStartTime = 31337;
	    double newLongitude = 42.42;
	    double newLatitude = 13.37;
	    String newNotes = "oh hi";
	    userEvent.setCategory(newCategory);
	    userEvent.setEndTime(newEndTime);
	    userEvent.setStartTime(newStartTime);
	    Location newLocation = userEvent.getLocation();
	    newLocation.setLongitude(newLongitude);
	    newLocation.setLatitude(newLatitude);
	    userEvent.setLocation(newLocation);
	    userEvent.setUserNotes(newNotes);
	    assertTrue(userEventDAO.updateUserEvent(rowId, userEvent));
	    
	    // Compare fetched event with the event we used to update with
	    UserEvent retrievedEvent = userEventDAO.fetchUserEvent(rowId);
	    assertEquals(newCategory, retrievedEvent.getCategory());
	    assertEquals(newEndTime, retrievedEvent.getEndTime());
	    assertEquals(newStartTime, retrievedEvent.getStartTime());
	    assertEquals(newLongitude, retrievedEvent.getLocation().getLongitude());
	    assertEquals(newLatitude, retrievedEvent.getLocation().getLatitude());
	    assertEquals(newNotes, retrievedEvent.getUserNotes());
	    
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
