package com.neutralspace.alibi;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AboutDialog extends Dialog{

	public AboutDialog(Context context) {
		super(context);
        this.setCancelable(true);
        this.setContentView(R.layout.about_alibi);
        this.setTitle("About myAlibi");
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Button creditsButton = (Button) findViewById(R.id.open_credits);
		Button closeButton = (Button) findViewById(R.id.close_about);
		
		closeButton.setOnClickListener(new CloseAbout(this));
		creditsButton.setOnClickListener(new OpenCredits(this));
	}
	
	private class CloseAbout implements View.OnClickListener{
		AboutDialog aboutDialog;
		
		public CloseAbout(AboutDialog aboutDialog) {
			this.aboutDialog = aboutDialog;
		}
		
		public void onClick(View v) {
			aboutDialog.cancel();			
		}
		
	}
	
	private class OpenCredits implements View.OnClickListener{
		AboutDialog aboutDialog;
		
		public OpenCredits(AboutDialog aboutDialog) {
			this.aboutDialog = aboutDialog;
		}

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
