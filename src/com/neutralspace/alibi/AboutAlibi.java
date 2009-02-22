package com.neutralspace.alibi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

public class AboutAlibi extends AlibiActivity {

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.about_alibi);
	    }

	    @Override
	    protected Dialog onCreateDialog(int id) {
            ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("Please wait while loading...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(true);
            return dialog;
	    }

	}	
