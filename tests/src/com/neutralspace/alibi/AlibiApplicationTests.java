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
       * The name 'test preconditions' is a convention to signal that if this
       * test doesn't pass, the test case was not set up properly and it might
       * explain any and all failures in other tests.  This is not guaranteed
       * to run before other tests, as junit uses reflection to find the tests.
       */
      public void testPreconditions() {
          assertNotNull(getApplication().getSettingsManager());
      }
      
}
