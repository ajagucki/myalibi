package com.neutralspace.alibi;

import android.app.Activity;
import android.os.Bundle;

public class Alibi extends Activity {
	public static final String PREFS_NAME = "AlibiSettings";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // XXX: For testing setup page, uncomment:
        //setContentView(R.layout.setup);
    }
}