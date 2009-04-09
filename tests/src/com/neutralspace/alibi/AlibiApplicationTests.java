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

import android.test.ApplicationTestCase;

/**
 * This is a simple framework for a test of an Application.  See 
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on 
 * how to write and extend Application tests.
 * 
 * To run this test, you can type:
 * adb shell am instrument -w \
 *   -e class com.neutralspace.alibi.AlibiApplicationTests \
 *   com.neutralspace.alibi.tests/android.test.InstrumentationTestRunner
 */
public class AlibiApplicationTests extends ApplicationTestCase<Alibi> {

    public AlibiApplicationTests() {
		super(Alibi.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		createApplication();
	}

	/**
	 * The name 'test preconditions' is a convention to signal that if this test
	 * doesn't pass, the test case was not set up properly and it might explain
	 * any and all failures in other tests. This is not guaranteed to run before
	 * other tests, as junit uses reflection to find the tests.
	 */
	public void testPreconditions() {
		assertNotNull(getApplication().getSettingsManager());
	}
      
}
