package com.neutralspace.alibi;

import android.test.ActivityInstrumentationTestCase;

/**
 * Make sure that the main launcher activity opens up properly, which will be
 * verified by {@link ActivityInstrumentationTestCase#testActivityTestCaseSetUpProperly}.
 */
public class MainTest extends ActivityInstrumentationTestCase<Main> {

    public MainTest() {
        super("com.neutralspace.alibi", Main.class);
    }

}
