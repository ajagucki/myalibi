package com.neutralspace.alibi;

import junit.framework.Test;
import junit.framework.TestSuite;

import android.test.suitebuilder.TestSuiteBuilder;

/**
 * A test suite containing all tests for My Alibi.
 *
 * To run all suites found in this apk:
 * $ adb shell am instrument -w \
 *   com.neutralspace.alibi/android.test.InstrumentationTestRunner
 *
 * To run just this suite from the command line:
 * $ adb shell am instrument -w \
 *   -e class com.neutralspace.alibi.AllTests \
 *   com.neutralspace.alibi.tests/android.test.InstrumentationTestRunner
 *
 * To run an individual test case, e.g. {@link com.neutralspace.alibi.SetupTest}:
 * $ adb shell am instrument -w \
 *   -e class com.neutralspace.alibi.SetupTest \
 *   com.neutralspace.alibi.tests/android.test.InstrumentationTestRunner
 *
 * To run an individual test, e.g. {@link com.neutralspace.alibi.SetupTest#testDelayPicker()}:
 * $ adb shell am instrument -w \
 *   -e class com.neutralspace.alibi.SetupTest#testDelayPicker \
 *   com.neutralspace.alibi.tests/android.test.InstrumentationTestRunner
 */
public class AllTests extends TestSuite {

    public static Test suite() {
        return new TestSuiteBuilder(AllTests.class)
                .includeAllPackagesUnderHere()
                .build();
    }
}
