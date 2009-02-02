package com.neutralspace.alibi;

import android.test.ApplicationTestCase;

public class UserEventManagerTest extends ApplicationTestCase<Alibi> {
	
	private UserEventManager userEventManager;

	public UserEventManagerTest() {
		super(Alibi.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		createApplication();
		userEventManager = new UserEventManager(getApplication());
	}

	public void testPrecondtions() {
		assertNotNull(userEventManager);
	}
	
	public void testBegin() {
		
	}
	
	public void testFinish() {
		
	}
	
	public void testCancel() {
		
	}
	
	public void testGetCurrentEvent() {
		
	}

}
