package com.neutralspace.alibi;

import com.neutralspace.alibi.widget.DelayPicker;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class SetupTest extends ActivityUnitTestCase<Setup> {
    
    private Intent startIntent;
    
    private Spinner calendarS;
    private CheckBox remindCb;
    private DelayPicker reminderDp;
    private Button doneButton;

    public SetupTest() {
        super(Setup.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // In setUp, you can create any shared test data, or set up mock components to inject
        // into your Activity.  But do not call startActivity() until the actual test methods.
        Bundle settingsBundle = new Bundle();
        settingsBundle.putInt(Alibi.PREF_CALENDAR_ID, 1);
        settingsBundle.putBoolean(Alibi.PREF_REMIND, true);
        settingsBundle.putInt(Alibi.PREF_REMIND_DELAY, 60);
        startIntent = new Intent();
        startIntent.putExtras(settingsBundle);
    }

    /**
     * The name 'test preconditions' is a convention to signal that if this
     * test doesn't pass, the test case was not set up properly and it might
     * explain any and all failures in other tests.  This is not guaranteed
     * to run before other tests, as junit uses reflection to find the tests.
     */
    public void testPreconditions() {
        startActivity(startIntent, null, null);
        calendarS = (Spinner) getActivity().findViewById(R.id.calendar_spinner);
        remindCb = (CheckBox) getActivity().findViewById(R.id.reminder_checkbox);
        reminderDp = (DelayPicker) getActivity().findViewById(R.id.delay_picker);
        doneButton = (Button) getActivity().findViewById(R.id.done_button);
        
        assertNotNull(getActivity());
        assertNotNull(calendarS);
        assertNotNull(remindCb);
        assertNotNull(reminderDp);
        assertNotNull(doneButton);
    }
    
    /**
     * This test demonstrates examining the way that activity calls startActivity() to launch 
     * other activities.
     */
    public void testSubLaunch() {
//        Forwarding activity = startActivity(mStartIntent, null, null);
//        mButton = (Button) activity.findViewById(R.id.go);
//        
//        // This test confirms that when you click the button, the activity attempts to open
//        // another activity (by calling startActivity) and close itself (by calling finish()).
//        mButton.performClick();
//        
//        assertNotNull(getStartedActivityIntent());
//        assertTrue(isFinishCalled());
    }
    
    /**
     * This test demonstrates ways to exercise the Activity's life cycle.
     */
    public void testLifeCycleCreate() {
//        Setup activity = startActivity(startIntent, null, null);
//        
//        // At this point, onCreate() has been called, but nothing else
//        // Complete the startup of the activity
//        getInstrumentation().callActivityOnStart(activity);
//        getInstrumentation().callActivityOnResume(activity);
//        
//        // At this point you could test for various configuration aspects, or you could 
//        // use a Mock Context to confirm that your activity has made certain calls to the system
//        // and set itself up properly.
//        
//        getInstrumentation().callActivityOnPause(activity);
//        
//        // At this point you could confirm that the activity has paused properly, as if it is
//        // no longer the topmost activity on screen.
//        
//        getInstrumentation().callActivityOnStop(activity);
//        
//        // At this point, you could confirm that the activity has shut itself down appropriately,
//        // or you could use a Mock Context to confirm that your activity has released any system
//        // resources it should no longer be holding.
//
//        // ActivityUnitTestCase.tearDown(), which is always automatically called, will take care
//        // of calling onDestroy().
    }
    
}