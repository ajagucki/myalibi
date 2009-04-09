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

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.neutralspace.alibi.widget.DelayPicker;

public class SetupTest extends ActivityUnitTestCase<Setup> {
    
    private Alibi alibi;
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
        
        alibi = new Alibi();
        alibi.setSettingsManager(new SettingsManager(getInstrumentation().getTargetContext()));
        setApplication(alibi);
        
        startIntent = new Intent();
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
        
        assertNotNull(calendarS);
        assertNotNull(remindCb);
        assertNotNull(reminderDp);
        assertNotNull(doneButton);
    }
    
    /**
     * This test demonstrates ways to exercise the Activity's life cycle.
     */
    public void testLifeCycleCreate() {
        Setup activity = startActivity(startIntent, null, null);
        
        // At this point, onCreate() has been called, but nothing else
        // Complete the startup of the activity
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);
        
        // At this point you could test for various configuration aspects, or you could 
        // use a Mock Context to confirm that your activity has made certain calls to the system
        // and set itself up properly.
        
        getInstrumentation().callActivityOnPause(activity);
        
        // At this point you could confirm that the activity has paused properly, as if it is
        // no longer the topmost activity on screen.
        
        getInstrumentation().callActivityOnStop(activity);
        
        // At this point, you could confirm that the activity has shut itself down appropriately,
        // or you could use a Mock Context to confirm that your activity has released any system
        // resources it should no longer be holding.

        // ActivityUnitTestCase.tearDown(), which is always automatically called, will take care
        // of calling onDestroy().
    }
    
}
