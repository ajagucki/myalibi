package com.neutralspace.alibi;

import android.test.ActivityInstrumentationTestCase;

/**
 * Make sure that the main launcher activity opens up properly, which will be
 * verified by {@link ActivityInstrumentationTestCase#testActivityTestCaseSetUpProperly}.
 */
public class AlibiTest extends ActivityInstrumentationTestCase<Main> {

    public AlibiTest() {
        super("com.neutralspace.alibi", Main.class);
    }

}
