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

import junit.framework.Test;
import junit.framework.TestSuite;

import android.test.suitebuilder.TestSuiteBuilder;

/**
 * A test suite containing all tests for My Alibi.
 *
 * To run all suites found in this apk:
 * $ adb shell am instrument -w \
 *   com.neutralspace.alibi.tests/android.test.InstrumentationTestRunner
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
