package com.neutralspace.alibi;

import android.test.ActivityInstrumentationTestCase;

/**
 * Make sure that the main launcher activity opens up properly, which will be
 * verified by {@link ActivityInstrumentationTestCase#testActivityTestCaseSetUpProperly}.
 */
public class StartEventTest extends ActivityInstrumentationTestCase<StartEvent> {

    public StartEventTest() {
        super("com.neutralspace.alibi", StartEvent.class);
    }

}
